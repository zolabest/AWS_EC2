package com.employees.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.employees.model.*;

public class EmployeeDAO {
	private Database db;

        public EmployeeDAO()
        {
                db = new Database();
        }
        public EmployeeDAO(String url, String user, String pass)
        {
                db = new Database();
        }
        public static void main (String[]args)
        {
                EmployeeDAO dao = new EmployeeDAO();
                //Employee emp = new Employee ("A01234567", "test last", "test first", "2000-01-01");
                //dao.create(emp);
                List<Employee>list = dao.retrieveAll();
                for (Employee emp:list)
                	System.out.println(emp);
        }
        public int create (Employee emp)
        {
        	try {
        		String insert = 
        				String.format("INSERT INTO Employees(id,firstName,lastName ,dob) VALUES('%s', '%s', '%s', '%s')",
        						emp.getId(),emp.getFirstName(),emp.getLastName(),emp.getDob());
        		System.out.println ("DAO Create: "+insert);
        		int retval = db.execute(insert);
        		return retval;
        	}catch (Exception ex)
            {
        		ex.printStackTrace();
        		return -1;
            }
        }
        public int delete (String id)
        {
        	try {
        		String delete =String.format("DELETE FROM [dbo].[Employees] WHERE id = '%s'", id); 
        				
        		System.out.println ("DAO delete: "+delete);
        		db.execute(delete);
        		return 1;
        	}catch (Exception ex)
            {
        		ex.printStackTrace();
        		return -1;
            }
        }
        public List<Employee> retrieveAll ()
        {
                try {
                        List<Employee>list = new ArrayList<Employee>();
                        ResultSet rs = db.getResultSet("select * FROM Employees");
                        if(rs==null)
                        	return null;
                        while(rs.next())
                        {
                                Employee e = new Employee (
                                                rs.getString("id"),
                                                rs.getString("lastName"),
                                                rs.getString("firstName"),
                                                rs.getString("dob")
                                                );
                                list.add(e);
                        }
                        return list;
                }catch (Exception ex)
                {

                }
                return null;
        }
        public Employee retrieve (String id)
        {
        	System.out.println ("DAO.retrieve: "+id);
                try {
                        ResultSet rs = db.getResultSet("select * FROM Employees WHERE id=?", id);
                        if(rs.next())
                        {
                                Employee e = new Employee (
                                                rs.getString("id"),
                                                rs.getString("lastName"),
                                                rs.getString("firstName"),
                                                rs.getString("dob")
                                                );
                                return e;
                        }
                }catch (Exception ex)
                {

                }
                return null;
        }
}
