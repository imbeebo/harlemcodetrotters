/*code written by Douglas King
 * edited by Mathew Boland
 */

package gonqbox.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;

import gonqbox.Pages;
import gonqbox.dao.DAO;
import gonqbox.models.Comment;
import gonqbox.models.User;

/** Purpose: Servlet to add new comment to files in comment section
 *  
 * @author Mathew Boland
 * @version 0.2
 */
@WebServlet(name = "AddComment", urlPatterns = { "/AddComment" })
public class AddCommentServlet extends HttpServlet{
	
	private static final long serialVersionUID = 2194000724171138285L;

	private ResourceBundle bundle = null;
	
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
    	String loc = Config.get(request.getSession(), Config.FMT_LOCALE).toString();	
        bundle = ResourceBundle.getBundle("ui_"+loc);
        Comment commentToAdd = null;
        //get comment entered by user
        String comment = request.getParameter("comment");  
        
        //get file id 
        int fID=Integer.parseInt(request.getParameter("fileID")); 
        
        //get uploader id
        User user = (User)request.getSession().getAttribute("user");
        int uID = user.getUserID();
        commentToAdd = new Comment(comment, uID, fID);
        
        dao.addComment(commentToAdd);

        request.setAttribute("comments", dao.getCommentsByFileID(fID));
        RequestDispatcher rd=request.getRequestDispatcher(Pages.COMMENT.toString());  
        rd.forward(request,response);  
        
    }  
}

