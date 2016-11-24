package gonqbox;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

// Note: To add a new configuration value, there are currently four places that need to be updated.
// There is currently a comment at the start of each:
// 1) The field itself, 2) its default value, 3) load value from file, and 4) save value to file
public class Config {
	// 1) Configuration values:
	public static String uploadDirectory;
	public static long maxFolderSize;
	public static String dbUser;
	public static String dbPassword;

	private static final String CONFIG_LOCATION;
	private static final Properties props;
	private static final Properties defaults;
	static {
		defaults = new Properties();
		// Default values:
		defaults.setProperty("uploadDirectory", "uploads");
		defaults.setProperty("maxFolderSize", "" + (64 * 1024 * 1024));
		defaults.setProperty("dbUser", "gonqbox");
		defaults.setProperty("dbPassword", "");

		CONFIG_LOCATION = "gonqbox.cfg";
		props = new Properties(defaults);

		try {
			loadConfig();
			saveConfig();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void loadConfig() throws IOException {
		try{
			props.load(new FileReader(CONFIG_LOCATION));
		} catch (FileNotFoundException e) {
		}

		// 3) Copy properties into config fields:
		uploadDirectory = props.getProperty("uploadDirectory");
		dbUser = props.getProperty("dbUser");
		dbPassword = props.getProperty("dbPassword");
		try {
			maxFolderSize = Long.parseLong(props.getProperty("maxFolderSize"));
		} catch(NumberFormatException e) {
			e.printStackTrace();
		}
	}

	public static void saveConfig() throws IOException {
		// 4) Copy config fields into properties:
		props.setProperty("uploadDirectory", uploadDirectory);
		props.setProperty("maxFolderSize", "" + maxFolderSize);
		props.setProperty("dbUser", dbUser);
		props.setProperty("dbPassword", dbPassword);

		props.store(new FileWriter(CONFIG_LOCATION), null);
	}
}
