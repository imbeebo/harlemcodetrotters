package gonqbox.servlets;

import java.io.IOException;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.core.Config;

import gonqbox.Pages;

@WebServlet(name = "logout", urlPatterns = { "/logout" })
public class LogoutServlet extends HttpServlet{
	private static final long serialVersionUID = -5041959610547406132L;
	private ResourceBundle bundle = null;

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String loc = Config.get(request.getSession(), Config.FMT_LOCALE).toString();
    	bundle = ResourceBundle.getBundle("ui_"+loc);
    	
		if(request.getSession().getAttribute("user") != null){
			request.getSession().setAttribute("user", null);
			request.setAttribute("index_messenger",bundle.getObject("logoutSuccess"));
		}else{
        	request.setAttribute("index_messenger_err",bundle.getObject("logoutUnsuccessful"));
		}        
    	request.getRequestDispatcher(Pages.INDEX.toString()).forward(request,response);

    }
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
		this.doPost(request, response);
	}
	
}
