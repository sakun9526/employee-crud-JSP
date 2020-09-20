
package net.employeemanagement.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import net.employeemanagement.model.Employees;

//this class has all the JDBC related stuff 
// JDBC -> Java Database Connectivity
//DAO -> Data Access Objects 
// provides CRUD database operations 
public class employeesDAO {
    private final String jdbcURL = "jdbc:mysql://localhost:3306/employeecrud";
    private final String jdbcUsername = "root";
    private final String jdbcPassword = "";
    
    //users table operations 
    private static final String INSERT_EMPLOYEES_SQL = "INSERT INTO employees (name, email, nic) VALUES (?,?,?);";  
    private static final String SELECT_EMPLOYEE_BY_ID = "SELECT id, name, email, nic FROM employees WHERE id = ?";
    private static final String SELECT_ALL_EMPLOYEES = "SELECT * FROM employees";
    private static final String DELETE_EMPLOYEES_SQL = "DELETE FROM employees where id = ? ;";
    private static final String UPDATE_EMPLOYEES_SQL = "UPDATE employees SET name =? , email = ?, nic = ? WHERE id = ? ;" ;
    
    public employeesDAO(){
        
    }
    protected Connection getConnection(){
        Connection connection = null; 
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return connection;
    }
    
    //insert employee
    public boolean insertEmployee (Employees employees) throws SQLException{
        boolean rowInserted = false;
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EMPLOYEES_SQL);)
        {
            preparedStatement.setString(1, employees.getName());
            preparedStatement.setString(2, employees.getEmail());
            preparedStatement.setString(3, employees.getNic());
            
            rowInserted = preparedStatement.executeUpdate() > 0;
            
        } catch (SQLException e) {
            printSQLException(e);
        }  
        
        return rowInserted;
    }   
    
    //update employee
    public boolean updateEmployee (Employees employees) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_EMPLOYEES_SQL))
        {
            preparedStatement.setString(1, employees.getName());
            preparedStatement.setString(2, employees.getEmail());
            preparedStatement.setString(3, employees.getNic());
            preparedStatement.setInt(4,employees.getId());
            
            rowUpdated = preparedStatement.executeUpdate() > 0;
            
        }
        
        return rowUpdated;
    }
    
    //select employee from an id
    public Employees selectEmployee(int id) {
        Employees employees = null;
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
                String nic = rs.getString("nic");
                employees = new Employees (id, name, email, nic);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return employees;
    }
    
    //delete employee from ID
    public boolean deleteEmployee (int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection(); 
                PreparedStatement statement = connection.prepareStatement(DELETE_EMPLOYEES_SQL);) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }
    
    //list all the employees
    public List < Employees > selectAllEmployees() {

        // using try-with-resources to avoid closing resources (boiler plate code)
        List < Employees > employees = new ArrayList < > ();
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
                String nic = rs.getString("nic");
                employees.add(new Employees(id, name, email, nic));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return employees;
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

  
     
