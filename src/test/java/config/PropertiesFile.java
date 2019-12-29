package config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PropertiesFile {

	Logger logger ;
	protected static Properties prop ;
	String projectPath ;
	
	public PropertiesFile() {
		logger = LogManager.getLogger(PropertiesFile.class);
		prop  = new Properties();
		projectPath = System.getProperty("user.dir");
		this.getPropertiesFile();
	}
	
	public void getPropertiesFile() {
		try {
			InputStream input = new FileInputStream(projectPath+"/src/test/java/config/config.properties");
			prop.load(input);
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
			logger.error(e.getCause());
			logger.error(e);
		} catch (IOException e) {
			logger.error(e.getMessage());
			logger.error(e.getCause());
			logger.error(e);
		}
	}
	
	public Object getProperties(String key) {
		return prop.get(key);
	}
	
	public void setProperties(String Propertie , String Value) {
		
		try {
			OutputStream output = new FileOutputStream(projectPath+"/src/test/java/config/config.properties");
			prop.setProperty(Propertie, Value);
			prop.store(output, null);
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
			logger.error(e.getCause());
			logger.error(e);
		} catch (IOException e) {
			logger.error(e.getMessage());
			logger.error(e.getCause());
			logger.error(e);
		}
	}
}
