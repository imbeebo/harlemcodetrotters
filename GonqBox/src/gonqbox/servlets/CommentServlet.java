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

/** Purpose: Servlet to add new comment to files in comment section
 *  
 * @author Mathew Boland
 * @version 0.2
 */
public class CommentServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	//DAO Instance to access database
	private DAO dao = DAO.getInstance();
	
	/**
     * Purpose: To add the comment into the database and 
     * update the current comment section with the new comment
     * @param request and response
     * @return void
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
    	//todo
        response.setContentType("text/html");  
        PrintWriter out = response.getWriter();  
        
        String n=request.getParameter("body");  
        String p=request.getParameter("userpass"); 
        
        HttpSession session = request.getSession(false);
        if(session!=null)
        session.setAttribute("name", n);

        User user = dao.loginUser(new User(n, p, ""));
        
        //check if user found
        if(user != null){  
            RequestDispatcher rd=request.getRequestDispatcher("welcome.jsp");  
            rd.forward(request,response);  
        }  
        else{
        	request.setAttribute("error","Invalid Username or Password");
            RequestDispatcher rd=request.getRequestDispatcher("index.jsp");      
            rd.include(request,response);  
        }  

        out.close();  
    }  
}
