package com.gk.bootjsp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.gk.bootjsp.model.Employee;
import com.gk.bootjsp.service.EmployeeService;

@Controller
@SessionAttributes("name")
public class EmployeeController {

	@Autowired
	EmployeeService service;

	@RequestMapping(value = "/employee/{name}", method = RequestMethod.GET)
	public String fetchByEmployee(@PathVariable("name") String name, ModelMap model) {
		System.out.println("Name: " + name);
		List<Employee> employees = service.fetchEmployees(name);
		System.out.println("Employees: " + employees.toString());
		model.put("employee", service.fetchEmployees(name));
		return "emp-list";
	}

	@RequestMapping(value = "/employee/list", method = RequestMethod.GET)
	public String listAllEmployees(@RequestParam("name") String name, ModelMap model) {
		model.put("employees", service.fetchAllEmployees());
		model.put("name", name);
		return "emp-list";
	}

}
