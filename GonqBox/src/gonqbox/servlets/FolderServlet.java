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
import gonqbox.Util;
import gonqbox.dao.DAO;
import gonqbox.models.Folder;
import gonqbox.models.User;

@WebServlet(name = "folder", urlPatterns = { "/folder" })
public class FolderServlet extends HttpServlet {
	private static final long serialVersionUID = 3394968208833318767L;
	private ResourceBundle bundle = null;
	DAO dao = DAO.getInstance();
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		if(req.getSession() == null){
	        RequestDispatcher rd=req.getRequestDispatcher(Pages.INDEX.toString());  
	        rd.forward(req,resp); 
		}
		bundle = Util.getResourceBundle(req);

		User userMe = (User)req.getSession().getAttribute("user");
		if(userMe == null){
    		req.setAttribute("index_messenger_err",bundle.getObject("noUserInSession"));
	        RequestDispatcher rd=req.getRequestDispatcher(Pages.INDEX.toString());  
	        rd.forward(req,resp); 
			return;
    	}
    	
    	int userID = -1;
    	String userName = "";
    	User user = null;
    	boolean otherUser = false;
    	if(req.getParameter("userID") != null) {
    		otherUser = true;
    		userID = Integer.parseInt(req.getParameter("userID").toString());
			user = dao.getUserByID(userID);
    		userName = user.getUsername();
    	}
    	else {
    		userID = userMe.getUserID();
    		userName = userMe.getUsername();
    	}
    	if(userMe.getUserID() == userID) otherUser= false;
		
    	user = userMe;
	
		Folder folder = dao.getUserFolder(userID);
				
		if(folder != null){
			req.setAttribute("otherUser", otherUser);
			req.setAttribute("folder_owner", userName);
			req.setAttribute("folder_file_count", folder.getFileCount());
			req.setAttribute("folder_size", folder.getSize());
			if(otherUser){
				req.setAttribute("files", dao.getPublicFolderFiles(folder.getFolderID()));
			}
			else {
				req.setAttribute("files", dao.getFolderFiles(folder.getFolderID()));
			}
			req.setAttribute("user_list", dao.getListOfUsers());
		}
		
        RequestDispatcher rd=req.getRequestDispatcher(Pages.FOLDER.toString());  
        rd.forward(req,resp); 
			
	}
	
}
