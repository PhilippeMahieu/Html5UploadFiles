package upl;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Collection;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "UploadServletv2", urlPatterns = { "/uploadservletv2" })
public class UploadServletv2 extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("UploadServletv2 - get received");
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>UploadServlet</title></head>");
        out.println("<body>");
        out.println("<p>The servlet has received a GET. This is the reply.</p>");
        out.println("</body></html>");
        out.close();
    }
        
    @Override
       protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Post received - V2");
        String[] darray=request.getParameterValues("file");
        
        for(int i=0; i<darray.length ; i++)                
        {
            String partName = darray[i];

            System.out.println("Part=" + partName);

        }
    }
}
