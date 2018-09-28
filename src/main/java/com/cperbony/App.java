package com.cperbony;

import java.sql.Connection;
import java.sql.SQLException;

import javax.faces.bean.ManagedBean;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

@ManagedBean(name="appBean", eager = true)
public class App {
	
//	@Resource(name="jdbc/mysql")
//	private DataSource datasource;
	
	private DataSource dataSource;
	
	public App() throws SQLException{
		try {
			Context ctx = new InitialContext();
			dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/mysql");
			Connection con = dataSource.getConnection();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	
	public String helloWorld() {
		return "Hello World";
	}

}
