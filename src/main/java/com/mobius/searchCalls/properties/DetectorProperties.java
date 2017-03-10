package com.mobius.searchCalls.properties;

import java.io.Serializable;
import java.net.URI;
import java.util.Properties;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import com.mobius.searchCalls.util.SearchConstants;

public class DetectorProperties implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public String fraudDBName;
	public String coreDBName;
	
	public String server1;
	public String server2;
	public String server3;
	public String serverPort;
	
	public String phoenixJdbcDriver;
	public String phoenixJdbcUrl;
	
	public String filePath;
	public String destArchivePath;
	public String dateFile;
	
	public String reportFinalPath;

	private static DetectorProperties instance = null;

	/**
	 * Constructors
	 *
	 */
	private DetectorProperties() {

	}

	private void initDetectorProperties() {

		try {
			Properties properties = new Properties();

			// get properties file by hadoop

			URI uri = URI.create(SearchConstants.SEARCH_CALLS_PATH+"searchCalls.properties");
			Configuration conf = new Configuration();
			
			System.out.println("Th configurations are: " + uri.toString() + "conf ::" + conf.toString());
			
						
			FileSystem file = FileSystem.get(uri, conf);
			FSDataInputStream in = file.open(new Path(uri));
			properties.load(in);

			// properties.load(new FileInputStream(GkConstant.PATH_PROPERTIES));
			fraudDBName=properties.getProperty("FRAUD_DBNAME","");
			coreDBName=properties.getProperty("CORE_DBNAME","");
			
			server1=properties.getProperty("server1","");
			server2=properties.getProperty("server2","");
			server3=properties.getProperty("server3","");
			serverPort=properties.getProperty("server.port","");
			
			phoenixJdbcDriver = properties.getProperty("phoenix.jdbc.driver","");
			phoenixJdbcUrl = properties.getProperty("phoenix.jdbc.url","");
			
			filePath=properties.getProperty("FEED_FILE_PATH","");
			destArchivePath=properties.getProperty("DEST_ARCHIVE_PATH","");
			dateFile=properties.getProperty("FRAUDDEC_DATE_FILE","");
	
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	/**
	 * getInstance
	 * 
	 * @return DetectorProperties
	 */
	public synchronized static DetectorProperties getInstance() {

		if (instance == null) {
			instance = new DetectorProperties();
			instance.initDetectorProperties();
		}

		return instance;
	}

	/**
	 * reloadProperties
	 * 
	 * @return
	 */
	public synchronized static void reloadProperties() {
		instance.initDetectorProperties();
	}
	
}
