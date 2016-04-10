package connectionprovider;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesLookup {
	Properties prop = new Properties();
	String filename = "properties.properties";
	InputStream input = null;
	private static PropertiesLookup instance = new PropertiesLookup();
	
	public PropertiesLookup() {
		try {
			input = getClass().getClassLoader().getResourceAsStream(filename);
			if (input == null) {
				System.out.println("Sorry, unable to find " + filename);
				return;
			}
			prop.load(input);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//get instance - singleton
	public static PropertiesLookup getInstance() {
		if (instance == null) {
			instance = new PropertiesLookup();
		}
		return instance;
	}
	
	//return property value
	public String getProperty(String key) {
		return prop.getProperty(key);
	}
}
