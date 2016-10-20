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
import gonqbox.models.Folder;
import gonqbox.models.User;

@WebServlet(name = "folder", urlPatterns = { "/folder" })
public class FolderServlet extends HttpServlet {
	private static final long serialVersionUID = 3394968208833318767L;
	private ResourceBundle bundle = null;
	DAO dao = DAO.getInstance();
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
    	String loc = Config.get(req.getSession(), Config.FMT_LOCALE).toString();
    	
    	bundle = ResourceBundle.getBundle("ui_"+loc);
		
		User user = (User)req.getSession().getAttribute("user");
		if(user != null){
			Folder folder = dao.getUserFolder(user.getUserID());
					
			if(folder != null){
				req.setAttribute("folder_owner", user.getUsername());
				req.setAttribute("folder_file_count", folder.getFileCount());
				req.setAttribute("folder_size", folder.getSize());
			}
			req.setAttribute("files", dao.getFolderFiles(folder.getFolderID()));
			
	        RequestDispatcher rd=req.getRequestDispatcher(Pages.FOLDER.toString());  
	        rd.forward(req,resp);  
		}else{ 
			req.setAttribute("index_messenger_err",bundle.getObject("noUserInSession"));
	        RequestDispatcher rd=req.getRequestDispatcher(Pages.INDEX.toString());  
	        rd.forward(req,resp);  
		}
	}
	
}
