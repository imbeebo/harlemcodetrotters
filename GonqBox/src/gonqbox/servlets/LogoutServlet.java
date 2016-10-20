package gonqbox.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gonqbox.Pages;

@WebServlet(name = "logout", urlPatterns = { "/logout" })
public class LogoutServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("user") != null){
			request.getSession().setAttribute("user", null);
			request.setAttribute("index_messenger","Logout Successful");
		}else{
        	request.setAttribute("index_messenger_err","Unable to successfully log out.");
		}        
    	request.getRequestDispatcher(Pages.INDEX.toString()).forward(request,response);

    }
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
		this.doPost(request, response);
	}
	
}
