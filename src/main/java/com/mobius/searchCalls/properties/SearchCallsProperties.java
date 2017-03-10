/**
 * 
 */
package com.mobius.searchCalls.properties;

import java.net.URI;
import java.util.Properties;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mobius.searchCalls.util.SearchConstants;
//import mobius.spark.fraudDetection.util.BTCGConstant;


public class SearchCallsProperties {
	
	public static Logger log = LoggerFactory.getLogger(SearchCallsProperties.class);
	
//	public String hiveUrl;
//	public String hiveUser;
//	public String hivePassword;
//	
	public String fraudDBName;
	public String coreDBName;
	
	
	public String dbFraud;
	public String dbCore;
	
	public String dbDriver;
	public String dbUrl;

	public int runBNumberUpdater;
	public int runDetectionDetailUpdater;
	public int runDailyUpdater;
	public int runKpiUpdater;

	public String TABLE_NAME_CALLS_NODUP;
	public String TABLE_NAME_TCG_DETECTIONS;
	public String TABLE_NAME_DETECTIONS_DETAILS;
	public String TABLE_NAME_DAILY_KPI;
	public String TABLE_NAME_DAILY_KPI_DETAIL;
	public String TABLE_NAME_HOT_BNUMBER;
	public String TABLE_NAME_SUBSCRIBER_SUMMARY;
	public String TABLE_NAME_MAKE_MODEL;
	public String TABLE_NAME_DECODE_INFO;

	public String ORIGINAL_DATA_TIMEZONE;
	public String APPLICATION_TIMEZONE;

	public int TCG_DETECTION_DURATION;

	public int CALL_NO_DUP_QUERY_NUM;
	
	public long RUNNING_START_TIME;
	public int PROCESS_START_HOUR;
	
	public int TIME_DIFFERENCE_BETWEEN_DB_AND_SERVER;

	public String COUNTRY_CODE;
	
	public String REPORT_SOURCE_CODE_ARR;
	public String REPORT_SOURCE_NAME_ARR;
	public String REPORT_SOURCE_ID_ARR;
	
	private static SearchCallsProperties instance = null;

	public static Properties DB_PROPERTIES;

	/**
	 * Constructors
	 *
	 */
	private SearchCallsProperties(){

	}

	private  void initSearchCallsProperties(){
		try{
			
			Properties properties = new Properties();
			
			System.out.println("Initialize search calls properties");
			
			
			
			// get properties file by hadoop
			URI uri = URI.create(SearchConstants.PATH_PROPERTIES);
			Configuration conf = new Configuration();
			FileSystem file = FileSystem.get(uri, conf);
			FSDataInputStream in = file.open(new Path(uri));
			properties.load(in);
			
			
			System.out.println(properties.toString());
			
			dbDriver = properties.getProperty("phoenix.jdbc.driver");
			dbUrl = properties.getProperty("phoenix.jdbc.url");
			
			System.out.println("In SearchCAllsProperties dbDriver:" + dbDriver);
			
			
//			hiveUrl = properties.getProperty("HIVE_URL");
//			hiveUser = properties.getProperty("HIVE_USER");
//			hivePassword = properties.getProperty("HIVE_PASSWORD");
//			
			dbFraud = properties.getProperty("HBASE_DB_FRAUD");
			dbCore = properties.getProperty("HBASE_DB_CORE");

		//	runBNumberUpdater = Integer.valueOf(properties.getProperty("RUN_B_NUMBER_UPDATER"));
		//	runDetectionDetailUpdater = Integer.valueOf(properties.getProperty("RUN_DETECTION_DETAIL_UPDATER"));
		//	runDailyUpdater = Integer.valueOf(properties.getProperty("RUN_DAILY_UPDATER"));
		//	runKpiUpdater = Integer.valueOf(properties.getProperty("RUN_KPI_UPDATER"));
			
		//	TABLE_NAME_CALLS_NODUP = properties.getProperty("TABLE_NAME_CALLS_NODUP", "CALLS_NO_DUP" );
		//	TABLE_NAME_TCG_DETECTIONS = properties.getProperty("TABLE_NAME_TCG_DETECTIONS", "TCG_DETECTIONS");
		//	TABLE_NAME_DETECTIONS_DETAILS = properties.getProperty("TABLE_NAME_DETECTIONS_DETAILS", "DETECTION_DETAILS");
		//	TABLE_NAME_DAILY_KPI = properties.getProperty("TABLE_NAME_DAILY_KPI", "DAILY_KPI");
		//	TABLE_NAME_DAILY_KPI_DETAIL = properties.getProperty("TABLE_NAME_DAILY_KPI_DETAIL","DAILY_KPI_DETAIL");
		//	TABLE_NAME_HOT_BNUMBER = properties.getProperty("TABLE_NAME_HOT_BNUMBER","HOT_BNUMBER");
		//	TABLE_NAME_SUBSCRIBER_SUMMARY = properties.getProperty("TABLE_NAME_SUBSCRIBER_SUMMARY", "SUBSCRIBER_SUMMARY");
		//	TABLE_NAME_MAKE_MODEL = properties.getProperty("TABLE_NAME_MAKE_MODEL", "MAKE_MODEL");
		//	TABLE_NAME_DECODE_INFO = properties.getProperty("TABLE_NAME_DECODE_INFO", "DECODE_INFO");

			/*TCG_DETECTION_DURATION = Integer.valueOf(properties.getProperty("TCG_DETECTION_DURATION","10"));

			ORIGINAL_DATA_TIMEZONE = properties.getProperty("ORIGINAL_DATA_TIMEZONE","");
			APPLICATION_TIMEZONE = properties.getProperty("APPLICATION_TIMEZONE","");
			
			RUNNING_START_TIME = Long.valueOf(properties.getProperty("RUNNING_START_TIME","2016030100"));

			PROCESS_START_HOUR = Integer.valueOf(properties.getProperty("PROCESS_START_HOUR","10"));
			
			TIME_DIFFERENCE_BETWEEN_DB_AND_SERVER = Integer.valueOf(properties.getProperty("TIME_DIFFERENCE_BETWEEN_DB_AND_SERVER","0"));*/
			
			REPORT_SOURCE_CODE_ARR = properties.getProperty("REPORT_SOURCE_CODE_ARR");
			REPORT_SOURCE_NAME_ARR = properties.getProperty("REPORT_SOURCE_NAME_ARR");
			REPORT_SOURCE_ID_ARR = properties.getProperty("REPORT_SOURCE_ID_ARR");
			
			COUNTRY_CODE = properties.getProperty("COUNTRY_CODE");
			
			fraudDBName=properties.getProperty("FRAUD_DBNAME","");
			coreDBName=properties.getProperty("CORE_DBNAME","");
			
			
		} catch (Exception e) {
			log.info(e.getMessage());
			log.error(e.getMessage(),e);
		}
	}

	/**
	 * getInstance
	 * @return DetectorProperties
	 */
	public synchronized static SearchCallsProperties getInstance(){
		if(instance == null){
			instance = new SearchCallsProperties();
			instance.initSearchCallsProperties();
		}
		return instance;
	}

	/**
	 * reloadProperties
	 * @return
	 */
	public synchronized static void reloadProperties(){
		instance.initSearchCallsProperties();
	}

}
