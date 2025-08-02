
package in.devesh;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/update")
public class UpdateServlet extends HttpServlet {
    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException {
        int dep = Integer.parseInt(req.getParameter("deptcode"));
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb", "root", "root");
            PreparedStatement ps = con.prepareStatement("SELECT * FROM dept WHERE deptcode = ?");
            ps.setInt(1, dep);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                out.println("<!DOCTYPE html>");
                out.println("<html lang='en'>");
                out.println("<head>");
                out.println("<meta charset='UTF-8'>");
                out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
                out.println("<title>Update Department</title>");
                out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css' rel='stylesheet'>");
                out.println("<style>");
                out.println("body { background: #f0f2f5; padding: 40px; font-family: 'Segoe UI', sans-serif; }");
                out.println(".container { max-width: 600px; background: #fff; padding: 30px; border-radius: 10px; box-shadow: 0px 0px 10px rgba(0,0,0,0.1); }");
                out.println("</style>");
                out.println("</head>");
                out.println("<body>");
                out.println("<div class='container'>");
                out.println("<h2 class='mb-4'>Update Department Information</h2>");
                out.println("<form action='modify' method='get'>");

                // Hidden deptcode field
                out.println("<input type='hidden' name='deptcode' value='" + rs.getInt(1) + "'>");

                out.println("<div class='mb-3'>");
                out.println("<label for='dname' class='form-label'>Department Name</label>");
                out.println("<input type='text' class='form-control' id='dname' name='dname' value='" + rs.getString(2) + "' required>");
                out.println("</div>");

                out.println("<div class='mb-3'>");
                out.println("<label for='location' class='form-label'>Location</label>");
                out.println("<input type='text' class='form-control' id='location' name='location' value='" + rs.getString(3) + "' required>");
                out.println("</div>");

                out.println("<button type='submit' class='btn btn-primary w-100'>Update</button>");
                out.println("</form>");
                out.println("</div>");
                out.println("</body>");
                out.println("</html>");
            } else {
                res.sendRedirect("errorpage.html");
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            out.println("<h3 style='color:red;'>Database error occurred. Try again later.</h3>");
        } finally {
            out.close();
        }
    }
}
