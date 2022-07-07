package com.employees.dao;

import java.sql.ResultSet;

public interface ListInterface {
	public int process (ResultSet rs);
	public String getQuery ();
	public Object[]getValues();
}
