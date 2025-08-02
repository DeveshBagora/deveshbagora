package in.devesh;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/LoginTest")
public class LoginServlet extends HttpServlet {
    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String user = req.getParameter("userName");
        String pwd = req.getParameter("password");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb", "root", "root");
            PreparedStatement ps = con.prepareStatement("SELECT * FROM user WHERE username=? AND password=?");
            ps.setString(1, user);
            ps.setString(2, pwd);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                res.sendRedirect("homepage.html");  // login success
            } else {
                res.sendRedirect("errorpage.html"); // login failed
            }

            con.close();
        } catch (Exception e) {
            // Send error info to browser
            res.setContentType("text/html");
            PrintWriter out = res.getWriter();
            out.println("<html><body><h3>Database Error: " + e.getMessage() + "</h3></body></html>");
            e.printStackTrace();
        }
    }
}
