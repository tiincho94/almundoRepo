package com.challenge.almundo.callcenter.employee;

import com.challenge.almundo.callcenter.employee.state.CategoryEmployee;

/*
 * Class that represents a Operator. To more information see the abstract class @com.challenge-almundo.callcenter.Employee
 */
public class Operator extends Employee {

	public Operator(int id, String name, String surname) {
		super(id, name, surname);
	}

	@Override
	public int getOrder() {
		return CategoryEmployee.OPERATOR;
	}

}
