package com.employee.services;

import java.util.List;

import com.employees.dao.EmployeeDAO;
import com.employees.model.Employee;

public class EmployeeService {
	EmployeeDAO dao;
	public EmployeeService() {
		dao = new EmployeeDAO();
	}
	public EmployeeService(String url, String user, String pass) {
		dao = new EmployeeDAO(url,user,pass);
	}
	public List<Employee>retrieveAll ()
	{
		return dao.retrieveAll();
	}
	public Employee retrieve (String id)
	{
		return dao.retrieve(id);
	}
	public int create (Employee emp)
	{
		String msg = emp.validate();
		if(msg.charAt(0)=='9')
			return 901;
			
		int retval = dao.create(emp);
		if (retval==-1)
			return 502;
		else
			return 200;
		
	}
	public int delete (String id)
	{
		return dao.delete(id);
	}
}
