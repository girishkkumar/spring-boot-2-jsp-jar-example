package com.gk.bootjsp.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gk.bootjsp.model.Employee;
import com.gk.bootjsp.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private static List<Employee> employeesList = new ArrayList<Employee>();
	private static int employeesCount = 6;

	static {
		employeesList.add(new Employee(1, "James", "Butt", "CEO"));
		employeesList.add(new Employee(2, "Josephine", "Darakjy", "COO"));
		employeesList.add(new Employee(3, "Art", "Venere", "VP"));
		employeesList.add(new Employee(4, "Lenna", "Paprocki", "Manager"));
		employeesList.add(new Employee(5, "Donette", "Foller", "Developer"));
		employeesList.add(new Employee(6, "administrator", "", "admin"));
	}

	@Override
	public List<Employee> fetchEmployees(String user) {
		List<Employee> filteredEmployees = new ArrayList<Employee>();
		for (Employee todo : employeesList) {
			if (todo.getFirstName().equals(user)) {
				filteredEmployees.add(todo);
			}
		}
		return filteredEmployees;
	}

	@Override
	public void addEmployee(String firstName, String lastName, String description) {
		employeesList.add(new Employee(++employeesCount, firstName, lastName, description));
	}

	@Override
	public void deleteEmployee(int id) {
		Iterator<Employee> iterator = employeesList.iterator();
		while (iterator.hasNext()) {
			Employee emp = iterator.next();
			if (emp.getId() == id) {
				iterator.remove();
			}
		}
	}

	@Override
	public List<Employee> fetchAllEmployees() {
		return employeesList;
	}

}
