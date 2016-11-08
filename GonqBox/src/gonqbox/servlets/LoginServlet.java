/*code written by Douglas King
 * edited by Mathew Boland
 */

package gonqbox.servlets;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.core.Config;

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
	
	private static final long serialVersionUID = -1719652389516169384L;
	private ResourceBundle bundle = null;
	
	//DAO Instance to access database
	private DAO dao = DAO.getInstance();
	
	/**
     * Purpose: To check the given username and password
     * @param request and response
     * @return void
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String loc = Config.get(request.getSession(), Config.FMT_LOCALE).toString();
    	
    	bundle = ResourceBundle.getBundle("ui_"+loc);
    	
        String username = request.getParameter("username");  
        String password = request.getParameter("userpass"); 
        
        User user = dao.loginUser(new User(username, password, "")).orElse(null);
        
        //check if user found
        if(user != null){
        	request.getSession().setAttribute("user", user);
        	request.setAttribute("index_messenger",bundle.getObject("goodLoginMessage") + user.getUsername());
        } else {
        	request.setAttribute("index_messenger_err",bundle.getObject("invalidCreds"));
        }
        
    	request.getRequestDispatcher(Pages.INDEX.toString()).forward(request,response);

    }  
    
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse responce) throws ServletException, IOException {
    	request.getRequestDispatcher(Pages.INDEX.toString()).forward(request, responce);
	}	
}
