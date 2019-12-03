package com.challenge.almundo.callcenter.employee;

import org.apache.log4j.Logger;

import com.challenge.almundo.callcenter.call.Call;
import com.challenge.almundo.callcenter.dispatcher.Dispatcher;

import lombok.Data;

/*
 * This is the Abstract Class that represent an Employee. There are three types of employees instanced in their classes.
 */
@Data
public abstract class Employee implements Comparable<Employee> {

	private String name;
	private String surname;
	private int id;

	static final Logger logger = Logger.getLogger(Employee.class);

	public Employee(int id, String name, String surname) {
		this.name = name;
		this.surname = surname;
		this.id = id;
	}

	/**
	 * This method is used to attend the call and when it finishes to inform to the
	 * Dispatcher that the employee is free again.
	 * 
	 * @param Call The call to be attended
	 */
	public void answerCall(Call call) {
		call.attend();
		Dispatcher.getInstance().finishCall(this);
	}

	public abstract int getOrder();

	/**
	 * This method is used to order the queue by the order value. First Operator,
	 * then Manager and finally Director
	 * 
	 * @param Employee to compare
	 */
	@Override
	public int compareTo(Employee o) {
		return this.getOrder() - o.getOrder();
	}

}
