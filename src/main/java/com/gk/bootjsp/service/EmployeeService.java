package com.gk.bootjsp.service;

import java.util.List;

import com.gk.bootjsp.model.Employee;

public interface EmployeeService {

	List<Employee> fetchEmployees(String name);

	void addEmployee(String firstName, String lastName, String description);

	void deleteEmployee(int id);

	List<Employee> fetchAllEmployees();

}
