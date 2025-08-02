
package in.devesh;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FirstServlet extends HttpServlet
{
public void service(HttpServletRequest req,HttpServletResponse res) throws IOException
{
  res.setContentType("text/html");
   PrintWriter out = res.getWriter();
   out.println("Response from First Servlet");
   out.close();
}
}

