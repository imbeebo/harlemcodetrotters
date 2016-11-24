package gonqbox.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.core.Config;

import java.util.ResourceBundle;

import gonqbox.Pages;
import gonqbox.Util;
import gonqbox.dao.DAO;
import gonqbox.models.User;

@WebServlet(name = "register", urlPatterns = { "/register" })
public class RegisterServlet extends HttpServlet {

	private static final long serialVersionUID = 574793718574570024L;
	private ResourceBundle bundle = null;


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse responce) throws ServletException, IOException {
		bundle = Util.getResourceBundle(request);

		String registerError = null;
		boolean registerSuccess = false;
		boolean validParams = true;
		User user = (User)request.getSession().getAttribute("user");
		
		if(user == null){
			
			String username = request.getParameter("username");
			String email = request.getParameter("email-address");
			String password = request.getParameter("userpass");
			
			// validation
		/*
			if(username == null || username.length() < 5 || !Pattern.matches("[a-zA-Z0-9]", username)){
				if(registerError == null)registerError = "Username error.\n";
				validParams = false;
			}
			if(password == null || password.length() == 0){
				if(registerError == null)registerError = "Password error.\n";
				else registerError += "Password error.\n";
				validParams = false;
			}
			if(email == null || email.length() < 5){
				if(registerError == null)registerError = "Email error.\n";	
				else registerError += "Email error.\n";
				validParams = false;
			}
		*/
			
			if(validParams){
				user = DAO.getInstance().registerUser(username, email, password);
				if(user != null) registerSuccess = true;
			}
			
			if(registerSuccess){
				request.getSession().setAttribute("user", user);
				request.setAttribute("index_messenger",bundle.getObject("goodRegistration") + user.getUsername());
				request.getRequestDispatcher(Pages.INDEX.toString()).forward(request, responce);
			}else{
	        	request.setAttribute("register_messenger_err", registerError);
				request.getRequestDispatcher(Pages.REGISTER_PAGE.toString()).include(request, responce);
			}
		}else{
        	request.setAttribute("register_messenger_err", bundle.getObject("alreadyLoggedIn"));
			request.getRequestDispatcher(Pages.REGISTER_PAGE.toString()).include(request, responce);
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse responce) throws ServletException, IOException {
		bundle = Util.getResourceBundle(request);

		User user = (User)request.getSession().getAttribute("user");

		if (user != null){
        	request.setAttribute("index_messenger_err", bundle.getObject("alreadyLoggedIn"));
			request.getRequestDispatcher(Pages.INDEX.toString()).forward(request, responce);
		}else{
			request.setAttribute("username", "");
			request.setAttribute("email_address", "");
			request.setAttribute("login_error", null);
			request.getRequestDispatcher(Pages.REGISTER_PAGE.toString()).forward(request, responce);
		}
	}	
}
