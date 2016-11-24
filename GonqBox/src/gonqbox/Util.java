package gonqbox;

import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.jstl.core.Config;

public class Util {

	public static ResourceBundle getResourceBundle(HttpServletRequest request) {
		String loc;
		try {
			loc = Config.get(request.getSession(), Config.FMT_LOCALE).toString();
		} catch(NullPointerException e) {
			loc = "en_CA";
		}

		return ResourceBundle.getBundle("ui_"+loc);
	}

}
