package in.devesh;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String dept = request.getParameter("deptcode");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            // Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to database
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/testdb", "root", "root");

            String query = "SELECT * FROM dept WHERE deptcode = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, dept);

            ResultSet rs = stmt.executeQuery();

            // Start HTML output
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<title>Search Result</title>");
            out.println("<style>");
            out.println("body { font-family: Arial, sans-serif; background-color: #f0f2f5; margin: 0; padding: 20px; }");
            out.println(".container { max-width: 600px; margin: auto; background: white; padding: 30px; border-radius: 10px; box-shadow: 0px 0px 10px rgba(0,0,0,0.1); }");
            out.println("h2 { text-align: center; color: #333; }");
            out.println("p { font-size: 16px; color: #555; line-height: 1.6; }");
            out.println("a { display: inline-block; margin-top: 20px; text-decoration: none; color: #007BFF; }");
            out.println("a:hover { text-decoration: underline; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='container'>");
            out.println("<h2>Department Search Result</h2>");

            // Check and display result
            if (rs.next()) {
                out.println("<p><strong>Dept Code:</strong> " + rs.getString("deptcode") + "</p>");
                out.println("<p><strong>Dept Name:</strong> " + rs.getString("dname") + "</p>");
                out.println("<p><strong>Location:</strong> " + rs.getString("location") + "</p>");
            } else {
                out.println("<p>No department found with code: <strong>" + dept + "</strong></p>");
            }

            out.println("<a href='search.html'>&larr; Back to Search</a>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");

            // Close connection
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<p>Error: " + e.getMessage() + "</p>");
        }
    }
}
