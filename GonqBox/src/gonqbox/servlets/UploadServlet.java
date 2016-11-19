package gonqbox.servlets;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import gonqbox.Config;
import gonqbox.dao.DAO;
import gonqbox.models.User;

@WebServlet("/upload")
@MultipartConfig(maxFileSize = 100 * 1024 * 1024)
public class UploadServlet extends HttpServlet {
	private static final String RANDNAME_CHARSET = "abcdefghijklmnopqrstuvwxyz0123456789";
	private static final long serialVersionUID = 1L;
	private static final int MAX_TRIES = 16;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User)request.getSession().getAttribute("user");
		boolean success = true;
		if(user == null){
			return;
		}
		for(Part p: request.getParts())
		{
			if(p.getName().equals("upload-files"))
			{
				success &= upload(p, user);
			}
		}

		if(request.getParameter("ajax") != null) {
			//System.out.println("  AJAX upload");
		} else {
			//System.out.println("  Not AJAX upload");
		}
		response.setStatus(success? HttpServletResponse.SC_OK : HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		response.getWriter().append("<!DOCTYPE html><html><head><title>Non-AJAX upload placeholder</title></head><body></body></html>");
		response.flushBuffer();
	}

	private boolean upload(Part p, User user) {
		File file = null;
		String filename = null;
		try {
			for(int i = 0; i < MAX_TRIES; i++) {
				filename = pickRandomName();
				File t = createFile(filename);
				if(t.createNewFile()) {
					file = t;
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		if(file != null) {
			try(InputStream in = p.getInputStream(); OutputStream out = new FileOutputStream(file)) {
				byte buffer[] = new byte[65536];
				int read = 0;
				while((read = in.read(buffer)) != -1) {
					out.write(buffer, 0, read);
				}

				DAO dao = DAO.getInstance();
				java.sql.Date now = new java.sql.Date(new java.util.Date().getTime());
				if(dao.addFile(new gonqbox.models.File(Paths.get(p.getSubmittedFileName()).getFileName().toString(), filename,
						user.getUserID(), dao.getUserFolder(user.getUserID()).getFolderID(),
						/*checksum*/"0", /*checksumDate*/now, /*checksumDateLastChecked*/now, file.length()))) {
					return true;
				} else {
					file.delete();
					return false;
				}
			} catch (IOException e) {
				e.printStackTrace();
				file.delete();
			}
		}

		return false;
	}

	private File createFile(String name) {
		File f = new File(Config.uploadDirectory, name);
		f.getParentFile().mkdirs();
		return f;
	}

	private String pickRandomName() {
		return randName(2) + "/" + randName(16);
	}

	private String randName(int chars) {
		Random rand = new Random();
		char ch[] = new char[chars];
		for(int i = 0; i < chars; i++) {
			ch[i] = RANDNAME_CHARSET.charAt(rand.nextInt(RANDNAME_CHARSET.length()));
		}

		return new String(ch);
	}
}
