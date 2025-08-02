package in.devesh;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/delete")
public class DeleteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String deptCode = request.getParameter("deptcode");

        out.println("<html><head><title>Delete Department</title></head><body>");

        if (deptCode == null || deptCode.trim().isEmpty()) {
            out.println("<h3 style='color:red;'>Department code is required.</h3>");
            out.println("</body></html>");
            return;
        }

        try {
            // Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to database
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/testdb", "root", "root");

            // Prepare SQL query
            String sql = "DELETE FROM dept WHERE deptcode = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, deptCode);

            // Execute query
            int rowsAffected = stmt.executeUpdate();

            // Show result to user
            if (rowsAffected > 0) {
                out.println("<h3 style='color:green;'>Department with code '" + deptCode + "' deleted successfully.</h3>");
            } else {
                out.println("<h3 style='color:orange;'>Department with code '" + deptCode + "' not found.</h3>");
            }

            conn.close();

        } catch (Exception e) {
            out.println("<h3 style='color:red;'>Error occurred: " + e.getMessage() + "</h3>");
            e.printStackTrace(out);
        }

        out.println("<br><a href='index.html'>Go Back</a>");
        out.println("</body></html>");
    }
}
