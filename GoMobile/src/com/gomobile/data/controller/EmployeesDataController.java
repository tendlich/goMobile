/**
 * 
 */
package com.gomobile.data.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;

import com.gomobile.model.Employee;
import com.gomobile.model.RepairOrder;
import com.gomobile.technicalservices.MySqlConnector;

/**
 * Class for managing data about employees.
 * @author Anton
 *
 */
public class EmployeesDataController {

	private final MySqlConnector sqlConnector;
	
	public EmployeesDataController(){
		sqlConnector = new MySqlConnector();
		
	}
	
	/**
	 * Returns the employee with the specified id or null if the id does not correspond to any employee.
	 * @param id
	 * @return
	 */
	public Employee getEmployeeById(long id){
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("where_condition", "id = " + id));
		sqlConnector.setQueryResultString( sqlConnector.getPHPRequestOutput("get_employee_data.php", nameValuePairs) );

		Employee employee = null;
		
		try{
			String[][] jsonArray = sqlConnector.queryResultToArray(new String[]{"id", "firstname", "lastname"});

			String firstName = jsonArray[0][1], lastName = jsonArray[0][2];
			employee = new Employee(id, firstName, lastName);
		
		}
		catch(ClassNotFoundException cnfe){
			Log.e("EMPLOYEE DATA RETRIEVING ERROR", cnfe.getLocalizedMessage());
		}
		catch(SQLException se){
			Log.e("EMPLOYEE DATA RETRIEVING ERROR", se.getLocalizedMessage());
		}
		catch(Exception e){
			Log.e("EMPLOYEE DATA RETRIEVING ERROR", e.getLocalizedMessage());
		}
		
		return employee;
	}
	
	/**
	 * Return all employees with the given name.
	 * @param fullName the full name of an employee.
	 * @return a list of all employees having the given name.
	 */
	public List<Employee> getEmployeeByName(String fullName){
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		String[] splittedName = fullName.split(" ");
		nameValuePairs.add(new BasicNameValuePair("where_condition", "firstname = '" + splittedName[0] + "' AND lastname = '" + splittedName[1] + "'"));
		sqlConnector.setQueryResultString( sqlConnector.getPHPRequestOutput("get_employee_data.php", nameValuePairs) );

		List<Employee> resultList = new ArrayList<Employee>();
		
		try{
			String[][] jsonArray = sqlConnector.queryResultToArray(new String[]{"id", "firstname", "lastname"});
			int rowCount = jsonArray.length;

			for(int i = 0; i < rowCount; i++){
				Employee employee = new Employee(Long.valueOf(jsonArray[i][0]), jsonArray[i][1], jsonArray[i][2]);
				resultList.add(employee);
			}
		
		}
		catch(ClassNotFoundException cnfe){
			Log.e("EMPLOYEE DATA RETRIEVING ERROR", cnfe.getLocalizedMessage());
		}
		catch(SQLException se){
			Log.e("EMPLOYEE DATA RETRIEVING ERROR", se.getLocalizedMessage());
		}
		catch(Exception e){
			Log.e("EMPLOYEE DATA RETRIEVING ERROR", e.getLocalizedMessage());
		}
		
		return resultList;
	}
	
	/**
	 * Returns all employees.
	 * @return a list of all employees stored in the database.
	 */
	public List<Employee> getAllEmployees(){
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("where_condition", "1"));

		System.out.println("QUERY ALL EMPLOYEES:");
		sqlConnector.setQueryResultString( sqlConnector.getPHPRequestOutput("get_employee_data.php", nameValuePairs) );

		List<Employee> resultList = new ArrayList<Employee>();
		
		try{
			String[][] jsonArray = sqlConnector.queryResultToArray(new String[]{"id", "firstname", "lastname"});
			int rowCount = jsonArray.length;

			for(int i = 0; i < rowCount; i++){
				Employee employee = new Employee(Long.valueOf(jsonArray[i][0]), jsonArray[i][1], jsonArray[i][2]);
				resultList.add(employee);
			}
		
		}
		catch(ClassNotFoundException cnfe){
			Log.e("EMPLOYEE DATA RETRIEVING ERROR", cnfe.getLocalizedMessage());
		}
		catch(SQLException se){
			Log.e("EMPLOYEE DATA RETRIEVING ERROR", se.getLocalizedMessage());
		}
		catch(Exception e){
			Log.e("EMPLOYEE DATA RETRIEVING ERROR", e.getLocalizedMessage());
		}
		
		return resultList;
	}
	
	/**
	 * Returns all repair orders the given employee is assigned to.
	 * @param employee the employee
	 * @return the list of repair orders
	 */
	public List<RepairOrder> getAssignedOrders(Employee employee){
		BikeDataController bikeDataController = new BikeDataController();
		
		return bikeDataController.getRepairOrders("employeeid = " + employee.getId());
	}

	public MySqlConnector getSqlConnector() {
		return sqlConnector;
	}
}
