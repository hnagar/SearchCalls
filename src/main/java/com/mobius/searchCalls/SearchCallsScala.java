package com.mobius.searchCalls;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaSparkContext;

import com.mobius.searchCalls.model.Jobs;
import com.mobius.searchCalls.service.SearchJobsService;
import com.mobius.searchCalls.util.SearchConstants;
import com.mobius.searchCalls.util.WaitBox;

public class SearchCallsScala implements Runnable {

	
	private static Logger logger = LogManager.getLogger(SearchCallsScala.class);  
	public static String operator;
	
	static SparkConf conf = new SparkConf().setAppName("Search Calls");
	static SparkContext context = new SparkContext(conf);
	
	static JavaSparkContext jsc = new JavaSparkContext(context);

	private SimpleDateFormat timeformat = new SimpleDateFormat(SearchConstants.DATE_FORMAT_FULL);
	private String startDate;
	private String endDate;
	private String msisdn;
	
	public SearchCallsScala(String name) {
		// TODO Auto-generated constructor stub
		operator = name;
		//this.startDate= startDate;
		//this.endDate = endDate;
		//this.msisdn = msisdn;
	}

	public void run() {
		// TODO Auto-generated method stub
	searchCalls();
	}

	private void searchCalls() {
		// TODO Auto-generated method stub
		//Initialize Scala class with the SparkContext
			SearchCalls sc = new SearchCalls(context);
				
				//The path in HDFS where all the CSV files are located
				String filePath = SearchConstants.SEARCH_CALLS_PATH;
				
				
				
				
				Configuration hadoopConf = jsc.hadoopConfiguration();
				FileSystem fileSys = null;
				try {
					 fileSys = FileSystem.get(hadoopConf);
				} catch (IOException e1) {
				logger.error("Get hdfs system failed , app exit!",e1);
					System.out.println("Get hdfs system failed , app exit!");
					return;
				}
			/*
				//Get all the files in the directory
				List<String> fileNames = FileUtils.getFileNames(filePath);
				
				//Now call the scala class for each file
				for (String fileName : fileNames) {
					
					System.out.println("The startDate is :" + fileName);
					sc.searchMSISDN(fileName);
					//scala> for (f<- 0 to numberofDays) yield dt1.plusDays(f).toString.substring(0,10).replaceAll("-","")
					//import org.joda.time.DateTime
					// val dt1= new DateTime(2017,01,10,10,00,00)
					//val dt2= new DateTime(2017, 01, 30, 10, 00, 00)
					//val numberofDays = Days.daysBetween(dt1, dt2).getDays()
					
					
					
				}
			*/	
			
				WaitBox wb =  WaitBox.getWaitBox(fileSys);
				
//				notifyThread = new Thread(new LoaderDateWatcher(hadoopConf));
//				notifyThread.start();
				
				// loop to calculate illegal odds.
				for(;;){	
				
				
			//Query the jobs table for any pending jobs
				try {
					List<Jobs> jobsList =SearchJobsService.getPendingJobs("pending");
					
					for(Jobs job : jobsList ){
						
						System.out.println("GEt the jobs" + job.getJobName());
						sc.searchMSISDN(job.getSearchTimeStart(), job.getSearchTimeEnd(), job.getJobData(), job.getJobType(), job.getDownLoadFileName(), job.getPathDownloadFile());
					//	sc.searchMSISDN(job);
					
					}
					
					//test
					//sc.searchMSISDN("2017030100","2017030800","21623710650,21623300234","MSISDN","testZipping.zip", "/user/hadoop/fileToExport/testZips");
					
					
				
					
					
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				}
				
	}
	
	
	

	

}
