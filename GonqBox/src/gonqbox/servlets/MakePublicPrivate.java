package gonqbox.servlets;

import java.io.IOException;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.core.Config;

import gonqbox.Pages;
import gonqbox.dao.DAO;
import gonqbox.models.User;

@WebServlet(name = "makePublicPrivate", urlPatterns = { "/makePublicPrivate" })
public class MakePublicPrivate extends HttpServlet {

	private static final long serialVersionUID = 6409933650931180714L;
	private ResourceBundle bundle = null;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse responce) throws ServletException, IOException {

    	String loc = Config.get(request.getSession(), Config.FMT_LOCALE).toString();
    	bundle = ResourceBundle.getBundle("ui_"+loc);
		
    	if(request.getSession().getAttribute("user") == null){
    		request.setAttribute("index_messenger_err",bundle.getObject("noUserInSession"));
	        RequestDispatcher rd=request.getRequestDispatcher(Pages.INDEX.toString());  
	        rd.forward(request,responce); 
    	}

		boolean checkedState = Boolean.parseBoolean(request.getParameter("checkedState"));
		int fileID = Integer.parseInt(request.getParameter("fileID"));
		DAO.getInstance().changePublicity(fileID, checkedState);
	}
}
