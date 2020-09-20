
package net.employeemanagement.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
//import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.employeemanagement.dao.employeesDAO;
import net.employeemanagement.model.Employees;

// * This servlet acts as a page controller for the application, handling all
// * requests from the user.
@WebServlet(name = "employeeServlet", urlPatterns = {"/"})
public class employeeServlet extends HttpServlet {
    
   // private static final long serialVersionUID = 1 L;
    private employeesDAO employeesDao;
    
    /**
     *
     */
    @Override
    public void init(){
        employeesDao = new employeesDAO();
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                doGet(request, response);
        }
    
    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String action = request.getServletPath();

        switch (action) {
            case "/new":
                showNewForm(request, response);
                break;
            case "/insert":
                try{
                 insertEmployee(request, response);
                } catch(SQLException e){
                }
                break;
            case "/delete":
                try{
                 deleteEmployee(request, response);
                } catch(SQLException e){
                }
                break;
            case "/edit":
                try{
                 showEditForm(request, response);
                } catch(SQLException e){
                }
                break;
            case "/update":
                try{
                 updateEmployee(request, response);
                }catch(SQLException e){
                }
                break;
            default:
                try{
                    listEmployee(request, response);
                }catch(SQLException e){
                }
                break;
        }
    }
    private void listEmployee(HttpServletRequest request, HttpServletResponse response)
    throws SQLException, IOException, ServletException {
        List < Employees > listEmployee = employeesDao.selectAllEmployees();
        request.setAttribute("listEmployee", listEmployee);
        RequestDispatcher dispatcher = request.getRequestDispatcher("employee-list.jsp");
        dispatcher.forward(request, response);
    }
        
    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("employee-form.jsp");
        dispatcher.forward(request, response);
    }
    
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
    throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Employees existingEmployee = employeesDao.selectEmployee(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("employee-form.jsp");
        request.setAttribute("employees", existingEmployee);
        dispatcher.forward(request, response);

    }
    
    private void insertEmployee(HttpServletRequest request, HttpServletResponse response)
    throws SQLException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        //String duo = request.getParameter("nic");
        //int nic = Integer.parseInt(duo);
        String nic = request.getParameter("nic");
        Employees employees = new Employees(name, email,nic);
        employeesDao.insertEmployee(employees);
        response.sendRedirect("list");
    }
    
    private void updateEmployee(HttpServletRequest request, HttpServletResponse response)
    throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String nic = request.getParameter("nic");

        Employees updatedEmployee = new Employees(id, name, email, nic);
        employeesDao.updateEmployee(updatedEmployee);
        response.sendRedirect("list");
    }
    
    private void deleteEmployee(HttpServletRequest request, HttpServletResponse response)
    throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        employeesDao.deleteEmployee(id);
        response.sendRedirect("list");

    }

}



