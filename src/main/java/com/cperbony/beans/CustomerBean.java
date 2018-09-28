package com.cperbony.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.cperbony.model.Customer;

@ManagedBean(name = "customerBean", eager = true)
@RequestScoped
public class CustomerBean {

	private DataSource dataSource;
	private Customer customer;

	public CustomerBean() {
		Context ctx;
		try {
			ctx = new InitialContext();
			dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/mysql");
		} catch (NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public List<Customer> findAll() throws SQLException {
		Connection conn = dataSource.getConnection();
		if (conn == null) {
			throw new SQLException();
		}
		PreparedStatement statement = conn.prepareStatement("SELECT * FROM customers");
		ResultSet rs = statement.executeQuery();

		List<Customer> customers = new ArrayList<Customer>();

		while (rs.next()) {
			Customer customer = new Customer(rs.getLong("id"), rs.getString("name"), rs.getInt("age"));
			customers.add(customer);
		}
		rs.close();
		statement.close();
		return customers;
	}
	
	public String create() throws SQLException {
		Connection conn = dataSource.getConnection();
		
		if(conn == null) {
			throw new SQLException();
		}
		
		//ALOCANDO STRING DENTRO SQL
		
//		PreparedStatement statement = conn.prepareStatement(
//				"INSERT INTO customers(name, age) VALUES ("
//				+ "'" + customer.getName() + "',"
//						+ "'" + customer.getAge() +"')"
//						);
//		statement.executeQuery();
//		return "app";
		
		//SEGUNDA FORMA PASSANDO PARÂMETROS
		
		PreparedStatement statement = conn.prepareStatement(
				"INSERT INTO customers(name, age) VALUES (?, ?)");
		statement.setString(1, this.customer.getName());
		statement.setInt(2, this.customer.getAge());
		statement.executeUpdate();
		return "app";
		
	}
	
	public Customer getCustomer() {
		return customer;
	}
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	

}
