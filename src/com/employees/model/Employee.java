package com.employees.model;



public class Employee {
/*
 * 
SELECT TOP (1000) [ID]
      ,[firstName]
      ,[lastName]
      ,[dob]
  FROM [employees].[dbo].[Employees]
 */
	String id;
	String firstName;
	String lastName;
	String dob;

	
	public Employee() {}
	public Employee(String id, String lastName, String firstName, String dob) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dob = dob;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	@Override
	public String toString() {
		return "Employee [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", dob=" + dob + "]";
	}

	public String validate ()
	{
		if (id.charAt(0)!='A'||id.charAt(1)!='0'|| id.length()!=9 || "".equals(lastName)|| "".equals(firstName))
		{
			return "901 Description: invalid employee data";
		}
		
		char[]digits = id.toCharArray();
		for (int i =1;i<id.length();i++)
		{
			if(digits[i]<'0'||digits[i]>'9')
			{
				return "901 Description: invalid employee data";
			}
		}
		
		return "200 Description: Success";
	}
}