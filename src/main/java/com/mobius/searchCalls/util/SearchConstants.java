package com.mobius.searchCalls.util;

import java.io.File;

import org.apache.hadoop.fs.Path;

/**
 * 
 * 
 */
public interface SearchConstants {

	/**
	 * log
	 */
	String LOG_NAME = "FraudDetection";

	/**
	 * date format
	 */
	String DATE_FORMAT_YEAR_MONTH_DAY_NUM = "yyyyMMdd";
	String DATE_FORMAT_YEAR_MONTH_DAY = "yyyy-MM-dd";
	String DATE_FORMAT_HOUR = "HH";
	String DATE_FORMAT_FULL = "yyyy-MM-dd HH:mm:ss";
	String DATE_FORMAT_HOUR_FULL = "yyyy-MM-dd HH:00:00";
	String DATE_FORMAT_FULL_NUM = "yyyyMMddHHmmss";
	String DATE_FORMAT_DATE_HOUR_NUM = "yyyyMMddHH";
	String DATE_FORMAT_HOUR_MINIUTE_SECOND = "HH:mm:ss";
	String DATE_FORMAT_HOUR_MINUTE_SECOND_NUM = "HHmmss";
	String DATE_FORMAT_DATE_HOUR_MIN = "yyyyMMddHHmm"; 
	
	/**
	 * path
	 */
	String SEARCH_CALLS_PATH = "hdfs://mars02-db01/opt/searchCalls/";
	String CURRENT_SEARCH_DIR = "hdfs:/user/hadoop/callsLoader/";
	String ARCHIVE_SEARCH_DIR = "hdfs:/user/hadoop/callsLoader_archive/";
	
	String PATH_PROPERTIES = "hdfs://mars02-db01/opt/dates/detector.properties";
	
	String PATH_LOG = "config" + File.separator + "logging.xml";

	String pathExportReport = "hdfs:/user/hadoop/fileToExport/";
	
	String localExportPath = "/opt/filesToExport/";
	
	String localZipFilePath = "/opt/tmp/";
	/**
	 * Database Name
	 */
	String DB_NAME_CALLS_NODUP = "calls_nodup_";
	// String DB_NAME_CALLS = "calls_";
	String DB_NAME_CORE = "core_";
	String DB_NAME_FRAUD = "fraud_";

	String initialHDFSPath = "hdfs:/user/hadoop/temp/";
	
	/**
	 * Enum to traverse through calltyes and s_msisdn and o_msisdn
	 * 
	 * @author hemang.nagar
	 *
	 */
	public enum AllCallTypes {

		o_msisdn_in(1), s_msisdn_out(1), o_msisdn_out(2), s_msisdn_in(2);

		private final int cTypeCode;

		private AllCallTypes(int cTypeCode) {
			this.cTypeCode = cTypeCode;
		}

		public int getCTypeCode() {
			return this.cTypeCode;
		}

	}

}
