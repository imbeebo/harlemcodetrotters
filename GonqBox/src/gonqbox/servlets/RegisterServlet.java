package gonqbox.servlets;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gonqbox.dao.DAO;
import gonqbox.models.User;

@WebServlet(name = "register", urlPatterns = { "/register" })
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String
		REGISTER_PAGE				= "register.jsp",
		ALREADY_REGISTERED_PAGE		= "already-registered.jsp",
		REGISTER_SUCCESS_PAGE		= "registration-complete.jsp";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Optional<User> user = getSession(req);

		if (user.isPresent())
			req.getRequestDispatcher(ALREADY_REGISTERED_PAGE).forward(req, resp);
		else
		{
			req.setAttribute("username", "");
			req.setAttribute("email_address", "");
			req.setAttribute("login_error", null);
			req.getRequestDispatcher(REGISTER_PAGE).forward(req, resp);
		}
	}

	/**
	 * Placeholder for method; probably on DAO or something. Should check for existing session (by default, persists for
	 * 60 minutes of inactivity), failing that check for a "remember me" cookie and create a new User session with it,
	 * then finally return Optional.empty() if the request is not coming from a logged-in user.
	 */
	private Optional<User> getSession(HttpServletRequest req) {
		return Optional.ofNullable((User) req.getSession().getAttribute("user-object"));
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Optional<User> user = getSession(req);

		if (user.isPresent())
			req.getRequestDispatcher(ALREADY_REGISTERED_PAGE).forward(req, resp);
		else
		{
			String username = req.getParameter("username");
			String email = req.getParameter("email-address");
			String password = req.getParameter("userpass");

			String error = check_username(username);

			if(error == null)
				error = check_email(email);

			if(error == null)
				error = check_password(password);

			if(error == null)
			{
				User u = DAO.getInstance().registerUser(username, password, email);

				if(u == null)
					error = "Username or email already in use.";
			}

			if(error != null)
			{
				req.setAttribute("username", username != null? username : "");
				req.setAttribute("email_address", email != null? email : "");
				req.setAttribute("login_error", error);
				req.getRequestDispatcher(REGISTER_PAGE).forward(req, resp);
			}
			else
			{
				req.getRequestDispatcher(REGISTER_SUCCESS_PAGE).forward(req, resp);
			}
		}
	}

	private String check_username(String username) {
		if(username == null || username.trim().isEmpty())
			return "Username required";
		else
			return null;
	}

	private String check_email(String email) {
		if(email == null || email.trim().isEmpty())
			return "EMail required";
		else
			return null;
	}

	private String check_password(String password) {
		if(password == null || password.isEmpty()) // Not trimmed: leading/trailing whitespace is part of PW.
			return "Password required";
		else
			return null;
	}
}
