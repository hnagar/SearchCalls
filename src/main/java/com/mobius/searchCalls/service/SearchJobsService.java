package com.mobius.searchCalls.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.mobius.searchCalls.dao.BasePhoenixDao;
import com.mobius.searchCalls.model.Jobs;
import com.mobius.searchCalls.properties.DetectorProperties;

public class SearchJobsService {
	
	private static Logger logger = LogManager.getLogger(SearchJobsService.class);
	private static String phoenixConnectionString = DetectorProperties.getInstance().phoenixJdbcUrl;
	private static String fraudDBName = DetectorProperties.getInstance().fraudDBName;

	public static List<Jobs> getPendingJobs(String traffic_time) throws SQLException {
		
		List<Jobs> jobsList = new ArrayList<Jobs>();
		
		//Connection con = DriverManager.getConnection(phoenixConnectionString);
	   // Statement stmt = con.createStatement();
		BasePhoenixDao baseDao = new BasePhoenixDao();
		try{
		
		
			  System.out.println(phoenixConnectionString);
			
		
		
	    ResultSet rs = null;
	    String reportState = "";
	    traffic_time="PENDING";
	    
	    String sql1 = "select * from "+fraudDBName+".jobs where job_state='" + traffic_time + "'";
	    //PreparedStatement statement = con.prepareStatement(sql1);
	    logger.info(sql1);
	  System.out.println(sql1);
	    
	    rs = baseDao.executeQuery(sql1);
	    
	    System.out.println("The result set is : " + rs);
	    
	    
	    while(rs.next()) {
	    	Jobs job = new Jobs();
	    	
	    	job.setUser(rs.getString("USER"));
	    	job.setJobName(rs.getString("JOB_NAME"));
	    	job.setJobType(rs.getString("JOB_TYPE"));
	    	job.setJobData(rs.getString("JOB_DATA"));
	    	job.setSearchTimeStart(rs.getString("SEARCH_TIME_START"));
	    	job.setSearchTimeEnd(rs.getString("SEARCH_TIME_END"));
	    	job.setJobState(rs.getString("JOB_STATE"));
	    	
	    	job.setPathDownloadFile(rs.getString("PATH_DOWNLOAD_FILE"));
	    	job.setDownLoadFileName(rs.getString("DOWNLOAD_FILE_NAME"));
	    	
	    	
	    	
	    	
	    	jobsList.add(job);
	    	
	    	System.out.println("rs ::" + rs.getString(3));
	    	logger.info("rs ::" + rs.getString(3));	
	    }
	   
	    return jobsList; 
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		try {
			baseDao.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}	
  
		 return null;
	}
	
}
