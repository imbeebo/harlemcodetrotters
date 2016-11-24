package gonqbox.servlets;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.Random;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import gonqbox.Config;
import gonqbox.Result;
import gonqbox.Util;
import gonqbox.dao.DAO;
import gonqbox.models.Folder;
import gonqbox.models.User;

@WebServlet("/upload")
@MultipartConfig(maxFileSize = 100 * 1024 * 1024)
public class UploadServlet extends HttpServlet {
	private static final String RANDNAME_CHARSET = "abcdefghijklmnopqrstuvwxyz0123456789";
	private static final long serialVersionUID = 1L;
	private static final int MAX_TRIES = 16;
	private ResourceBundle bundle = null;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		bundle = Util.getResourceBundle(request);

		User user = (User)request.getSession().getAttribute("user");
		if(user == null){
			return;
		}

		/* Workaround for mutable variables access from within a lambda: wrap in a one-element array, or
		 * a container class, as all that matters is that the *reference* is effectively final. */
		boolean success[] = {true};
		String errors[] = {""};

		for(Part p: request.getParts())
		{
			if(p.getName().equals("upload-files"))
			{
				DAO dao = DAO.getInstance();
				Folder folder = dao.getUserFolder(user.getUserID());

				if(folder.getSize() + p.getSize() <= Config.maxFolderSize)
				{
					upload(p, user, folder, dao)
						.match(
							ok -> folder.setSize(folder.getSize() + p.getSize()),
							errmsg -> {success[0] = false; errors[0] += "<li>" + errmsg + "</li>";});
				} else {
					success[0] = false;
					errors[0] += "<li>" + bundle.getObject("noSpaceForFile") + " " + p.getSubmittedFileName() + "</li>";
				}
			}
		}

		response.setStatus(success[0]? HttpServletResponse.SC_OK : HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		if(request.getParameter("ajax") != null) {
			response.getWriter().append(errors[0]);
		} else {
			response.getWriter().append("<!DOCTYPE html><html><head><title>Non-AJAX upload placeholder</title></head>"
					+"<body><ul>" + errors[0] + "</ul></body></html>");
		}
		response.flushBuffer();
	}

	private Result<Void, String> upload(Part p, User user, Folder folder, DAO dao) {
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

				/* One last size check; to handle concurrent uploads possibly exceeding the limit */
				if(dao.getUserFolder(user.getUserID()).getSize() + p.getSize() > Config.maxFolderSize) {
					return Result.err(bundle.getObject("noSpaceForFile") + " " + p.getSubmittedFileName());
				}

				java.sql.Date now = new java.sql.Date(new java.util.Date().getTime());
				if(dao.addFile(new gonqbox.models.File(Paths.get(p.getSubmittedFileName()).getFileName().toString(), filename,
						user.getUserID(), folder.getFolderID(),
						/*checksum*/"0", /*checksumDate*/now, /*checksumDateLastChecked*/now, file.length()))) {
					return Result.ok(null);
				} else {
					file.delete();
					return Result.err(bundle.getObject("fileUploadError").toString());
				}
			} catch (IOException e) {
				e.printStackTrace();
				file.delete();
			}
		}

		return Result.err(bundle.getObject("fileUploadError").toString());
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
