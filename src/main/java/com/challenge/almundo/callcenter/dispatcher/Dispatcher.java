package com.challenge.almundo.callcenter.dispatcher;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.challenge.almundo.callcenter.call.Call;
import com.challenge.almundo.callcenter.employee.Employee;

import lombok.Data;

/**
 * This class represents the Dispatcher. It has the main operations of the
 * Challenge and the control of the ThreadPool to execute the Tasks.
 */
@Data
public class Dispatcher {

	private static final Logger logger = LoggerFactory.getLogger(Dispatcher.class);
	private static final Dispatcher instance = new Dispatcher();
	private static final int MAX_CONCURRENTS = 10;

	/**
	 * This is the queue of the calls that cannot be attended, and are waiting to be
	 * taken. The calls are saved in a queue ordered by FIFO. The data structure was
	 * selected because saves the calls in FIFO order and is thread safe.
	 */
	private Queue<Call> waitingCalls = new ConcurrentLinkedQueue<>();

	/**
	 * This is the queue of the available employees ready to attend a call. The data
	 * structure was selected because it is thread safe and it has the elements
	 * ordered by the getOrden Employee.
	 */
	private PriorityBlockingQueue<Employee> employees = new PriorityBlockingQueue<Employee>();

	ExecutorService pool = Executors.newFixedThreadPool(MAX_CONCURRENTS);

	protected Dispatcher() {
	}

	public static Dispatcher getInstance() {
		return instance;
	}

	/**
	 * This method is used to assign an employee to a call if its possible, if not
	 * the call is sent to a queue to be attend as soon as possible.
	 * 
	 * @param Call The call to be attended
	 */
	public void dispatchCall(Call call) {
		logger.info("Dispatching call {}", call.getId());
		Employee employeeToAssign = getEmployeeToAssign();
		if (employeeToAssign != null) {
			logger.info("Answering call {}", call.getId());
			this.manageCall(employeeToAssign, call);
		} else {
			logger.info("Pending call {}", call.getId());
			logger.info("All employees are bussy");
			this.getWaitingCalls().add(call);
		}
	}

	/**
	 * This method is used to get an employee to a attend. The queue is ordered by
	 * order of Employee and the employee has to be free. If there is not any
	 * employee available will return null, if not return the employee.
	 */
	public Employee getEmployeeToAssign() {
		return this.getEmployees().poll();
	}

	/**
	 * This method is used to create a new Task with the employee to attend the call
	 * and also the call. It also passes the Task to the pool to be executed.
	 */
	private void manageCall(Employee employeeAssigned, Call call) {
		pool.execute(() -> new Task(employeeAssigned, call));
	}

	public void stop() {
		this.getPool().shutdown();
	}

	/**
	 * This method is used to add again the employee to the available list of
	 * employees ordered. If there was any call that could not be attended, someone
	 * is going to attend it. If not the thread pool is shutdown.
	 */
	public void finishCall(Employee employee) {
		this.getEmployees().add(employee);
		Call pendingCall = this.getWaitingCalls().poll();
		if (pendingCall != null) {
			dispatchCall(pendingCall);
		} else {
			stop();
		}
	}
}
