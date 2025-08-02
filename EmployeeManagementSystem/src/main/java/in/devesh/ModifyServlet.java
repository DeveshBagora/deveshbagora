
package in.devesh;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/modify")
public class ModifyServlet extends HttpServlet {

    // Common method to process both GET and POST requests
    public void processRequest(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        try {
            // Fetch form parameters
            int code = Integer.parseInt(req.getParameter("deptcode"));
            String name = req.getParameter("dname");
            String loc = req.getParameter("location");

            // Load JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish Connection
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb", "root", "root");

            // Prepare and execute the SQL update query
            PreparedStatement ps = con.prepareStatement(
                "UPDATE dept SET dname = ?, location = ? WHERE deptcode = ?");
            ps.setString(1, name);
            ps.setString(2, loc);
            ps.setInt(3, code);

            int result = ps.executeUpdate();

            // Check result and respond
            if (result > 0) {
                out.println("<h2>Record Updated Successfully!</h2>");
            } else {
                out.println("<h2>Record Not Found or Not Updated!</h2>");
            }

            out.println("<br><a href='homepage.html'>Return to Homepage</a>");

            con.close();
        } catch (ClassNotFoundException e) {
            out.println("<h3>Error loading JDBC driver.</h3>");
            e.printStackTrace(out);
        } catch (SQLException e) {
            out.println("<h3>Database error occurred.</h3>");
            e.printStackTrace(out);
        } catch (NumberFormatException e) {
            out.println("<h3>Invalid input format for Department Code.</h3>");
        } finally {
            out.close();
        }
    }

    // Handle GET request
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        processRequest(req, res);
    }

    // Handle POST request
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        processRequest(req, res);
    }
}

