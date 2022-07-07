package com.employees.web;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.employee.services.EmployeeService;

import com.employees.model.Employee;



/**
 * ControllerServlet.java
 * This servlet acts as a page controller for the application, handling all
 * requests from the user.
 * @email Ramesh Fadatare
 */

@WebServlet("/")
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	EmployeeService service;
	
	public void init() {
		service=new EmployeeService();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();
		HttpSession session = request.getSession();
System.out.println ("get: "+action);
		try {
			switch (action) {
			case "/new":
				showNewForm(request, response);
				break;
			case "/add":
				insertEmployee(request, response);
				break;
			case "/delete":
				deleteEmployee(request, response);
				break;
			case "/find":
				findEmployee(request, response);
				break;
			
			case "/employees":
				listEmployees(request, response);
				break;
			default:
				RequestDispatcher dispatcher = request.getRequestDispatcher("landing.jsp");
				dispatcher.forward(request, response);
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	private void listEmployees(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<Employee> list = service.retrieveAll();
		request.setAttribute("employees", list);
		RequestDispatcher dispatcher = request.getRequestDispatcher("employees.jsp");
		dispatcher.forward(request, response);
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("addEmployee.jsp");
		dispatcher.forward(request, response);
	}

	private void findEmployee(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		String id = request.getParameter("id");
		if(id==null)
		{
			RequestDispatcher dispatcher = request.getRequestDispatcher("findEmployee.jsp");
			request.setAttribute("message", "Please enter an employee id");
			dispatcher.forward(request, response);
			return;
		}
		Employee emp = service.retrieve(id);
		System.out.println ("Found: "+emp);
		if(emp==null)
		{
			request.setAttribute("message", "Result Code: 801 Description: no match found");
		}else {
			request.setAttribute("message", String.format("Found: %s %s<br/>Result Code: 000 Description: Success", 
					emp.getFirstName(), emp.getLastName()));
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("findEmployee.jsp");
		
		dispatcher.forward(request, response);
	}

	private void insertEmployee(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException, ServletException {
		String id = request.getParameter("id");
		String lastName = request.getParameter("lastName");
		String firstName = request.getParameter("firstName");
		String dob = request.getParameter("dob");
		Employee emp = new Employee(id,lastName,firstName,dob);
		System.out.println ("Insert "+emp);
		int retval = service.create(emp);
		System.out.println ("Insert code:"+retval);
		if(retval==200) {
			request.setAttribute("message", "Result Code: 200 Description: Success");
		}
		else if (retval==901) {
			request.setAttribute("message", "Result Code: 901 Description: invalid employee data");
		}else {
			request.setAttribute("message", "Result Code: 502 Description: ID already exists");
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("addEmployee.jsp");
		dispatcher.forward(request, response);
	}
	private Employee newEmployee(HttpServletRequest request) 
			throws IOException {
		String id = request.getParameter("id");
		String lastName = request.getParameter("lastName");
		String firstName = request.getParameter("firstName");
		String dob = request.getParameter("dob");
		Employee emp = new Employee(id,lastName,firstName,dob);
		return emp;
	}
	

	private void deleteEmployee(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException, ServletException {
		String id = request.getParameter("id");
		if(id==null)
		{
			RequestDispatcher dispatcher = request.getRequestDispatcher("deleteEmployee.jsp");
			dispatcher.forward(request, response);
			return;
		}
		service.delete(id);
		if(service.delete(id)>0)
			request.setAttribute("message", "Result Code: 200 Description: Success");
		else
			request.setAttribute("message", "Result Code: 901 Description: Failed");
		RequestDispatcher dispatcher = request.getRequestDispatcher("deleteEmployee.jsp");
		dispatcher.forward(request, response);

	}

}