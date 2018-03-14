package upl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@MultipartConfig
@WebServlet(name = "UploadServlet", urlPatterns = { "/uploadservlet" })
public class UploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private String outputDirectory = "d:/temp/"; 
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        String var = System.getenv("YUNDA_UPLOAD_DIR");          // Now you can control the directory files are written to by setting this OS environment variable
        if (var != null) {
            outputDirectory = var;
        }
        else {
            var = getServletContext().getInitParameter("YUNDA_UPLOAD_DIR"); 
            if (var != null) {
                outputDirectory = var;
            }
        }
                
    }

    /*     public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>UploadServlet</title></head>");
        out.println("<body>");
        out.println("<p>The servlet has received a POST or GET. This is the reply.</p>");
        out.println("</body></html>");
        out.close();
        Part file = request.getPart("file");
        String filename = getFilename(file);
        InputStream filecontent = file.getInputStream();
        // ... Do your file saving job here.
        System.out.println("File " + filename + " successfully uploaded");
    } */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       System.out.println("Post received");
       Part file = request.getPart("file");
       // System.out.println("Post received:" + file);
       String filename = getFilename(file);
       InputStream filecontent = file.getInputStream();
       // ... Do your file saving job here.
       // System.out.println("File " + filename + " successfully uploaded");
       FileOutputStream outputStream =  new FileOutputStream(new File(outputDirectory + filename));
                       
       int read = 0;
       byte[] bytes = new byte[1024];
    
       while ((read = filecontent.read(bytes)) != -1) {
               outputStream.write(bytes, 0, read);
       }
       outputStream.close();   
    
       response.setContentType("text/plain");
       response.setCharacterEncoding("UTF-8");
        
       //response.getWriter().write("File " + filename + " successfully uploaded.<br>");
       response.getWriter().println("File is " + filename  + " and client IP is " + getClientIp(request));
       //response.getWriter().println("var xyz = " + filename);
    }
    
    private static String getFilename(Part part) {
       for (String cd : part.getHeader("content-disposition").split(";")) {
           if (cd.trim().startsWith("filename")) {
               String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
               return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
           }
       }
       return null;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("get received");
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>UploadServlet</title></head>");
        out.println("<body>");
        out.println("<p>The servlet has received a GET. This is the reply.</p>");
        out.println("</body></html>");
        out.close();
    }
    
    private static String getClientIp(HttpServletRequest request) {

            String remoteAddr = "";

            if (request != null) {
                remoteAddr = request.getHeader("X-FORWARDED-FOR");
                if (remoteAddr == null || "".equals(remoteAddr)) {
                    remoteAddr = request.getRemoteAddr();
                }
            }

            return remoteAddr;
        }
}
