package com.adminView;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/DeleteServlet")
public class DeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    
    public DeleteServlet() {
        super();
        
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userid = Integer.parseInt(request.getParameter("userid"));
        
        deleteRecord(userid);
        
        boolean deleted = deleteRecord(userid);

        if (deleted) {
            HttpSession session = request.getSession();
            session.setAttribute("message", "Record with user ID " + userid + " deleted successfully.");
        } else { 
            HttpSession session = request.getSession();
            session.setAttribute("message", "No record found with user ID " + userid + ". No deletion performed.");
        }

        response.sendRedirect("Select");
    }
    
    private boolean deleteRecord(int userid) {
        try {
            // Step 1: Register Driver
            Class.forName("com.mysql.jdbc.Driver");

            // Step 2: Create Connection
            String url = "jdbc:mysql://localhost/studentdata";
            Connection con = DriverManager.getConnection(url, "root", "Hsushune12345@@");

            // Step 3: Create a Statement
            Statement stmt = con.createStatement();

            // Execute SQL DELETE statement
            String deleteSQL = "DELETE FROM `studentdata`.`register` WHERE userid = " + userid;
            int rowsDeleted = stmt.executeUpdate(deleteSQL);

            if (rowsDeleted > 0) {
                System.out.println("Registeration of userid " + userid + " deleted successfully!!!");
            } else {
                System.out.println("No record found with userid " + userid + ". No deletion performed.");
            }

            // Step 4: Close the statement and connection
            stmt.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
