package com.challenge.almundo.callcenter;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.PriorityBlockingQueue;

import org.junit.Before;
import org.junit.Test;

import com.challenge.almundo.callcenter.call.Call;
import com.challenge.almundo.callcenter.dispatcher.Dispatcher;
import com.challenge.almundo.callcenter.employee.Director;
import com.challenge.almundo.callcenter.employee.Employee;
import com.challenge.almundo.callcenter.employee.Manager;
import com.challenge.almundo.callcenter.employee.Operator;

import lombok.Data;

@Data
public class AppTest {

	private Dispatcher dispatcher = Dispatcher.getInstance();

	@Before
	public void setUp() throws Exception {
		this.getDispatcher().setEmployees(new PriorityBlockingQueue<Employee>());

		Operator op1 = new Operator(11, "Juan", "Perez");
		Operator op2 = new Operator(12, "Alberto", "Rodriguez");
		Operator op3 = new Operator(13, "Juana", "Hermida");
		Manager man1 = new Manager(14, "Ezequiel", "Gutierrez");
		Manager man2 = new Manager(15, "Juan", "Perez");
		Director dir1 = new Director(100, "Maria Eugenia", "Villa");
		Operator op4 = new Operator(16, "Juan", "Perez");
		Operator op5 = new Operator(17, "Alberto", "Rodriguez");
		Operator op6 = new Operator(18, "Juana", "Hermida");
		Manager man3 = new Manager(19, "Ezequiel", "Gutierrez");

		this.getDispatcher().getEmployees().add(op1);
		this.getDispatcher().getEmployees().add(op2);
		this.getDispatcher().getEmployees().add(op3);
		this.getDispatcher().getEmployees().add(man1);
		this.getDispatcher().getEmployees().add(man2);
		this.getDispatcher().getEmployees().add(dir1);
		this.getDispatcher().getEmployees().add(op4);
		this.getDispatcher().getEmployees().add(op5);
		this.getDispatcher().getEmployees().add(op6);
		this.getDispatcher().getEmployees().add(man3);
	}

	@Test
	public void testSizeLoadedEmployees() {
		assertEquals(10, this.getDispatcher().getEmployees().size());
	}

	@Test
	public void testOrderEmployees() {
		assertEquals(Operator.class, this.getDispatcher().getEmployeeToAssign().getClass());
	}

	@Test
	public void test10Calls() {
		this.getDispatcher().setWaitingCalls(new ConcurrentLinkedQueue<>());

		Dispatcher.getInstance().dispatchCall(generateCall());
		Dispatcher.getInstance().dispatchCall(generateCall());
		Dispatcher.getInstance().dispatchCall(generateCall());
		Dispatcher.getInstance().dispatchCall(generateCall());
		Dispatcher.getInstance().dispatchCall(generateCall());
		Dispatcher.getInstance().dispatchCall(generateCall());
		Dispatcher.getInstance().dispatchCall(generateCall());
		Dispatcher.getInstance().dispatchCall(generateCall());
		Dispatcher.getInstance().dispatchCall(generateCall());
		Dispatcher.getInstance().dispatchCall(generateCall());

		assertEquals(0, this.getDispatcher().getWaitingCalls().size());
	}

	@Test
	public void testMoreThan10Calls() {
		this.getDispatcher().setWaitingCalls(new ConcurrentLinkedQueue<>());
		Dispatcher.getInstance().dispatchCall(generateCall());
		Dispatcher.getInstance().dispatchCall(generateCall());
		Dispatcher.getInstance().dispatchCall(generateCall());
		Dispatcher.getInstance().dispatchCall(generateCall());
		Dispatcher.getInstance().dispatchCall(generateCall());
		Dispatcher.getInstance().dispatchCall(generateCall());
		Dispatcher.getInstance().dispatchCall(generateCall());
		Dispatcher.getInstance().dispatchCall(generateCall());
		Dispatcher.getInstance().dispatchCall(generateCall());
		Dispatcher.getInstance().dispatchCall(generateCall());
		Dispatcher.getInstance().dispatchCall(generateCall());
		Dispatcher.getInstance().dispatchCall(generateCall());

		assertEquals(2, this.getDispatcher().getWaitingCalls().size());
	}

	private Call generateCall() {
		return new Call();
	}

	@Test
	public void testMoreCallsThanThreadWithEnoughEmployees() {
		Operator opp = new Operator(18, "Juana", "Hermida");
		Manager mann = new Manager(19, "Ezequiel", "Gutierrez");

		this.getDispatcher().getEmployees().add(opp);
		this.getDispatcher().getEmployees().add(mann);

		this.getDispatcher().setWaitingCalls(new ConcurrentLinkedQueue<>());
		Dispatcher.getInstance().dispatchCall(generateCall());
		Dispatcher.getInstance().dispatchCall(generateCall());
		Dispatcher.getInstance().dispatchCall(generateCall());
		Dispatcher.getInstance().dispatchCall(generateCall());
		Dispatcher.getInstance().dispatchCall(generateCall());
		Dispatcher.getInstance().dispatchCall(generateCall());
		Dispatcher.getInstance().dispatchCall(generateCall());
		Dispatcher.getInstance().dispatchCall(generateCall());
		Dispatcher.getInstance().dispatchCall(generateCall());
		Dispatcher.getInstance().dispatchCall(generateCall());
		Dispatcher.getInstance().dispatchCall(generateCall());
		Dispatcher.getInstance().dispatchCall(generateCall());

		assertEquals(0, this.getDispatcher().getWaitingCalls().size());
	}

	@Test
	public void testNobodyAttend() {
		this.getDispatcher().setEmployees(new PriorityBlockingQueue<Employee>());
		this.getDispatcher().setWaitingCalls(new ConcurrentLinkedQueue<>());
		Dispatcher.getInstance().dispatchCall(generateCall());
		Dispatcher.getInstance().dispatchCall(generateCall());
		Dispatcher.getInstance().dispatchCall(generateCall());

		assertEquals(3, this.getDispatcher().getWaitingCalls().size());
	}

}
