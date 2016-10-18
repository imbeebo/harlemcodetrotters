/*code written by Douglas King
 * edited by Mathew Boland
 */

package gonqbox.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gonqbox.Pages;
import gonqbox.dao.DAO;
import gonqbox.models.User;

/** Purpose: Servlet to login user. Redirects to homepage 
 * on success. Reloads index.jsp on failure.
 * @author Mathew Boland
 * @version 0.1
 */
@WebServlet(name = "login", urlPatterns = { "/loginServlet" })
public class LoginServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	//DAO Instance to access database
	private DAO dao = DAO.getInstance();
	
	/**
     * Purpose: To check the given username and password
     * @param request and response
     * @return void
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
            	
        String username = request.getParameter("username");  
        String password = request.getParameter("userpass"); 
        
        User user = dao.loginUser(username, password);
        
        //check if user found
        if(user != null){
        	request.getSession().setAttribute("user", user);
        	request.setAttribute("login_messenger","Login Successful, welcome " + user.getUsername());
        } else {
        	request.setAttribute("login_messenger_err","Invalid Username or Password");
        }
        
    	request.getRequestDispatcher(Pages.INDEX.toString()).forward(request,response);

    }  
}
