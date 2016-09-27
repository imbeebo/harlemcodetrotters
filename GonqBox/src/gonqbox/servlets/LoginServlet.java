/*code written by Douglas King
 * edited by Mathew Boland
 */

package gonqbox.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import gonqbox.dao.DAO;
import gonqbox.models.User;

public class LoginServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	private DAO dao = DAO.getInstance();
    public void doPost(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  

        response.setContentType("text/html");  
        PrintWriter out = response.getWriter();  
        
        String n=request.getParameter("username");  
        String p=request.getParameter("userpass"); 
        
        HttpSession session = request.getSession(false);
        if(session!=null)
        session.setAttribute("name", n);

        User user = dao.loginUser(n, p);
        
        //check if user found
        if(user != null){  
            RequestDispatcher rd=request.getRequestDispatcher("welcome.jsp");  
            rd.forward(request,response);  
        }  
        else{  
            out.print("<p style=\"color:red\">Sorry username or password error</p>");  
            RequestDispatcher rd=request.getRequestDispatcher("index.jsp");  
            rd.include(request,response);  
        }  

        out.close();  
    }  
}
