
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

@WebServlet("/insert")
public class InsertServlet extends HttpServlet {

    public void processRequest(HttpServletRequest req, HttpServletResponse res) throws IOException {
        int code = Integer.parseInt(req.getParameter("deptcode"));
        String name = req.getParameter("dname");
        String loc = req.getParameter("location");

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb", "root", "root");
            PreparedStatement ps = con.prepareStatement("INSERT INTO dept VALUES (?, ?, ?)");
            ps.setInt(1, code);
            ps.setString(2, name);
            ps.setString(3, loc);

            int k = ps.executeUpdate();

            // Begin HTML response
            out.println("<!DOCTYPE html>");
            out.println("<html lang='en'>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
            out.println("<title>Insert Department</title>");
            out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css' rel='stylesheet'>");
            out.println("<style>");
            out.println("body { background: linear-gradient(to right, #00c6ff, #0072ff); color: white; height: 100vh; display: flex; justify-content: center; align-items: center; }");
            out.println(".card { padding: 30px; border-radius: 15px; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='card bg-dark text-center text-white shadow-lg'>");

            if (k > 0) {
                out.println("<h2 class='mb-3'>✅ Department Record Inserted Successfully</h2>");
            } else {
                out.println("<h2 class='mb-3'>❌ Failed to Insert Department Record</h2>");
            }

            out.println("<a href='insert-form.html' class='btn btn-light m-2'>Insert Another</a>");
            out.println("<a href='homepage.html' class='btn btn-outline-light m-2'>Go to Homepage</a>");

            out.println("</div>");
            out.println("</body>");
            out.println("</html>");

        } catch (ClassNotFoundException | SQLException e) {
            out.println("<h3 style='color:red;'>Error: " + e.getMessage() + "</h3>");
            e.printStackTrace();
        } finally {
            out.close();
        }
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        processRequest(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        processRequest(req, res);
    }
}
