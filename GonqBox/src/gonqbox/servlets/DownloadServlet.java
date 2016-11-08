package gonqbox.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gonqbox.Config;
import gonqbox.Pages;
import gonqbox.dao.DAO;

@WebServlet("/download")
public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		}
		catch(NumberFormatException | NullPointerException e) {
			displayError("download.invalid_id", request, response);
			return;
		}
		Optional<gonqbox.models.File> file = DAO.getInstance().getFileByID(id);
		if(file.isPresent()) {
			java.io.File sourceFile = new java.io.File(Config.uploadDirectory, file.get().getSequence());
			String filename = URLEncoder.encode(file.get().getName(), "UTF-8");

			response.setStatus(HttpServletResponse.SC_OK);
			response.addHeader("Content-Disposition", "attachment; filename=\"" + filename + '"');
			sendFile(sourceFile, response.getOutputStream());
			response.flushBuffer();
		} else {
			displayError("download.no_such_file", request, response);
		}
	}

	private void sendFile(File sourceFile, ServletOutputStream out) {
		try(FileInputStream in = new FileInputStream(sourceFile))
		{
			byte buffer[] = new byte[65536];
			int len;

			while((len = in.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

	private void displayError(String locale_key, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("error_key", locale_key);
		request.getRequestDispatcher(Pages.GENERIC_ERROR.toString()).forward(request, response);
	}
}
