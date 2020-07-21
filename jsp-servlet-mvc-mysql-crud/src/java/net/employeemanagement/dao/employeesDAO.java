
package net.employeemanagement.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import net.employeemanagement.model.employees;

//this class has all the JDBC related stuff 
// JDBC -> Java Database Connectivity
//DAO -> Data Access Objects 
// provides CRUD database operations 
public class employeesDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/employeecrud?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "";
    
    //users table operations 
    private static final String INSERT_EMPLOYEES_SQL = "INSERT INTO Employees" + "(name, email, nic) VALUES" + "(?, ?, ?)";  
    private static final String SELECT_EMPLOYEE_BY_ID = "select id, name, email, nic from employees where id = ?";
    private static final String SELECT_ALL_EMPLOYEES = "select * from employees";
    private static final String DELETE_EMPLOYEES_SQL = "delete from employees where id = ? ;";
    private static final String UPDATE_EMPLOYEES_SQL = "update employees set name =? , email = ?, nic = ? where id = ? ;" ;
    
    protected Connection getConnection(){
        Connection connection = null; 
        
        try{
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
            Class.forName("com.mysql.jdbc.Driver");
        } catch (SQLException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e ) {
            e.printStackTrace();
        }
        return connection;
    }
    
    //insert employee
    public void insertEmployee (employees employee) throws SQLException{
        try (Connection connection = getConnection();PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EMPLOYEES_SQL))
        {
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setString(2, employee.getEmail());
            preparedStatement.setInt(3, employee.getNic());
        } catch (Exception e){
            e.printStackTrace();
          }
    }
    
    //update employee
    public boolean updateEmployee (employees employee) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_EMPLOYEES_SQL))
        {
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setString(2, employee.getEmail());
            preparedStatement.setInt(3, employee.getNic());
            preparedStatement.setInt(4,employee.getId());
            
            rowUpdated = preparedStatement.executeUpdate() > 0;
            
        }
        
        return rowUpdated;
    }
    
    //select employee from an id
    public employees selectEmployee(int id) {
        employees employee = null;
        // Step 1: Establishing a Connection
        try (Connection connection = getConnection();
            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EMPLOYEE_BY_ID);) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                int nic = rs.getInt("nic");
                employee = new employees (id, name, email, nic);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return employee;
    }
    
    //delete employee from ID
    public boolean deleteEmployee (int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(DELETE_EMPLOYEES_SQL);) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }
    
    //list all the employees
    public List < employees > selectAllEmployees() {

        // using try-with-resources to avoid closing resources (boiler plate code)
        List < employees > employeeset = new ArrayList < > ();
        // Step 1: Establishing a Connection
        try (Connection connection = getConnection();

            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_EMPLOYEES);) {
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                int nic = rs.getInt("nic");
                employeeset.add(new employees(id, name, email, nic));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return employeeset;
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }

}

  
     
