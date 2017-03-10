package com.mobius.searchCalls.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.zeroturnaround.zip.ZipUtil;

public class FileUtils {
	public static Boolean fileExists(String path, String fileNamePrefix) {
		URI uri = URI.create(path);
		Configuration conf = new Configuration();
		
		
		try {
			FileSystem hdfs = FileSystem.get(uri, conf);
		
			Path hdfInputPath = new Path(path);
			
			System.out.println("The inputPath is :" + hdfInputPath);
			
			//the second boolean parameter here sets the recursion to true
		    RemoteIterator<LocatedFileStatus> fileStatusListIterator = hdfs.listFiles(
		           hdfInputPath, false);
		  
		    
		    while(fileStatusListIterator.hasNext()){
		    	
		    	//System.out.println("FileStatus list iterator");
		        
		    	LocatedFileStatus fileStatus = fileStatusListIterator.next();
		        
		    //	System.out.println("FileStatus ::" + fileStatus.isFile() + " :::" +fileStatus.getPath().getName() + ":::" + fileNamePrefix);
		        
		        String fileName = fileStatus.getPath().getName();
		        
		        
		        if (fileName.contains(fileNamePrefix))
		        {
		        	//System.out.println("Should return true");
		        	
		        	return true;
		        }
		        
		    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	
	
	public static void fileRename(String inputPath, String destPath, String fileNamePrefix) {
		
		
		URI uri = URI.create(SearchConstants.pathExportReport);
		Configuration conf = new Configuration();
		
		
		try {
			FileSystem hdfs = FileSystem.get(uri, conf);
			System.out.println("The inputPath is :" + inputPath);
			 System.out.println("The file prefix is: " + fileNamePrefix);
			Path hdfInputPath = new Path(inputPath);
			Path desArchPath = new Path(destPath);
			System.out.println("The inputPath is :" + hdfInputPath);
			
			//the second boolean parameter here sets the recursion to true
		    RemoteIterator<LocatedFileStatus> fileStatusListIterator = hdfs.listFiles(
		           hdfInputPath, false);
		   
		    while(fileStatusListIterator.hasNext()){
		        LocatedFileStatus fileStatus = fileStatusListIterator.next();
		        String fileName = fileStatus.getPath().getName();
		        
		        if (fileName.contains(fileNamePrefix))
		        {
		        	System.out.println("Contains works: "+ fileStatus.getPath().getName());
		        	
		        	hdfs.rename(fileStatus.getPath(),desArchPath);
		        }
		        
		    }
		    
		   
		    
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static void fileMove(String inputPath, String destPath) {
		
		URI uri = URI.create(SearchConstants.pathExportReport);
		Configuration conf = new Configuration();
		try {
			FileSystem hdfs = FileSystem.get(uri, conf);
			System.out.println("The inputPath is :" + inputPath);
			
			Path hdfInputPath = new Path(inputPath);
			Path desArchPath = new Path(destPath);
			System.out.println("The inputPath is :" + hdfInputPath);
		
		        	
		        	hdfs.rename(hdfInputPath,desArchPath);
		        	
		        	
		        	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static List<String> getFileNames(String path){
		URI uri = URI.create(path);
		Configuration conf = new Configuration();
		List<String> fileNamesList = new ArrayList<String>();
		
		try {
			FileSystem hdfs = FileSystem.get(uri, conf);
		
			Path hdfInputPath = new Path(path);
			
			System.out.println("The inputPath is :" + hdfInputPath);
			
			//the second boolean parameter here sets the recursion to true
		    RemoteIterator<LocatedFileStatus> fileStatusListIterator = hdfs.listFiles(
		           hdfInputPath, false);
		  
		    
		    while(fileStatusListIterator.hasNext()){
		    	
		    	//System.out.println("FileStatus list iterator");
		        
		    	LocatedFileStatus fileStatus = fileStatusListIterator.next();
		        
		    //	System.out.println("FileStatus ::" + fileStatus.isFile() + " :::" +fileStatus.getPath().getName() + ":::" + fileNamePrefix);
		        
		        String fileName = fileStatus.getPath().getName();
		        
		        
		        if (fileName != null && fileName.endsWith(".txt"))
		        {
		        	//System.out.println("Should return true");
		        	fileNamesList.add(fileName);
		        	
		        }
		        
		    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fileNamesList;
		
		
	}
	
	public static void test(){
		
	}
	
	
	
	public static void writeFiles(String callString, String indFileNames, String fileName, String filePath){
		
		BufferedWriter bwTarget=null;

		FSDataOutputStream fos = null;
		
		
		try {
		// get properties file by hadoop
		URI uri = URI.create(SearchConstants.pathExportReport);
		Configuration conf = new Configuration();
		FileSystem hdfs = FileSystem.get(uri, conf);
		
		//Path fileExportPath = new Path(SearchConstants.pathExportReport,fileName+".txt");
		
		Path fileExportPath = new Path(filePath, indFileNames+".txt");
		
		//System.out.println("In the ShutdownFileService.createShutdowns :" + shutDownFilePath);
		
		if (!hdfs.exists(fileExportPath)) {
			 fos = hdfs.create(fileExportPath);
		}else{
			 fos = hdfs.append(fileExportPath);
		}
		
		bwTarget = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));
		bwTarget.append(callString + "\n");
		
		
		FileUtil fts = new FileUtil();
		
		/*
		System.out.println("The path is : " +new Path(filePath));
		hdfs.copyToLocalFile(new Path(filePath), new Path("/opt/hive/filesToExport/"));
		//Zip all the files in the directory
		ZipUtil.pack(new File("/opt/hive/filesToExport/"), new File("/opt/hive/filesToExport/"+fileName+".zip"));
		*/
		
		}catch (IOException e) {
			
			}
		finally{
			try {
				if (bwTarget !=null){
				bwTarget.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	public static void zipFiles(String filePath, String fileName){
		
		// get properties file by hadoop
				try {
						URI uri = URI.create(SearchConstants.pathExportReport);
						Configuration conf = new Configuration();
						FileSystem hdfs = FileSystem.get(uri, conf);
					
						System.out.println("The path in zip is : " +new Path(filePath));
						hdfs.copyToLocalFile(true, new Path(filePath), new Path(SearchConstants.localExportPath));
						
						
						
						//Zip all the files in the directory
						ZipUtil.pack(new File(SearchConstants.localExportPath), new File(SearchConstants.localZipFilePath+fileName));
						
						
						
						//Now put it back in hadoop
						hdfs.copyFromLocalFile(true, new Path(SearchConstants.localZipFilePath+fileName), new Path(filePath+"/"+fileName));
						
						//Clean up
						hdfs.delete(new Path(SearchConstants.initialHDFSPath));
						//org.zeroturnaround.zip.commons.FileUtils.deleteDirectory(new File(SearchConstants.localExportPath));
						org.apache.commons.io.FileUtils.deleteDirectory(new File(SearchConstants.localExportPath));
						
						
						
						} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						}catch (Exception ex){
							ex.printStackTrace();
						}
				
	}
		
		
	
	
}