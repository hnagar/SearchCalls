package com.mobius.searchCalls

import java.util.Map
import org.apache.log4j.LogManager
import org.apache.spark.SparkContext
import com.mobius.searchCalls.model.CallCols
import com.mobius.searchCalls.util.SearchConstants
import com.mobius.searchCalls.util.FileUtils
import com.mobius.searchCalls.model.Jobs


class SearchCalls (val sc: SparkContext){
  
   var logger = LogManager.getLogger(classOf[SearchCalls])

    val sqlContext = new org.apache.spark.sql.SQLContext(sc)
   
    import sqlContext.implicits._

    
    def searchMSISDN( startTime: String, endTime:String, searchData: String, searchType:String, fileName: String, filePath:String){
     
     
    /*
     val searchFileData = sc.textFile(SearchConstants.SEARCH_CALLS_PATH+fileName)
     
      val searchData = searchFileData.mapPartitions(
                    lines => {
                        lines.map(line => {
                            line.split(" ").map(_.trim)
                        })
                    }).first()
     
                    
                    
          println(searchData(0)) 
          println(searchData(1))
          println(searchData(2))
          println(searchData(3))
      */    
          //scala> for (f<- 0 to numberofDays) yield dt1.plusDays(f).toString.substring(0,10).replaceAll("-","")
					import org.joda.time.DateTime
					import org.joda.time.Days
					// val dt1= new DateTime(2017,01,10,10,00,00)
					//val dt2= new DateTime(2017, 01, 30, 10, 00, 00)
					//val numberofDays = Days.daysBetween(dt1, dt2).getDays()
          
          val startDate = new DateTime(startTime.substring(0,4).toInt, startTime.substring(4,6).toInt,startTime.substring(6,8).toInt, 0, 0, 0)
          
          val endDate = new DateTime(endTime.substring(0,4).toInt, endTime.substring(4,6).toInt,endTime.substring(6,8).toInt, 0, 0, 0)
          val numberofDays = Days.daysBetween(startDate, endDate).getDays()
          
       //   println(for (f<- 0 to numberofDays) yield startDate.plusDays(f).toString().substring(0,10).replaceAll("-",""))
          
          
          val searchRange = for (f<- 0 to numberofDays) yield startDate.plusDays(f).toString().substring(0,10).replaceAll("-","")
          
          //var callsFound= searchCallsByDays(searchRange, searchData(2))
          var totalCalls = scala.collection.mutable.Map[String, String]()
          
          
          //searchRange.foreach { searchDate => 
            
          
           // val mapOfFoundCalls = searchCallsByDays(searchDate, searchData(2))
            
           // totalCalls +=  mapOfFoundCalls
          
          
        //  }
          
          var fdCalls =       new String()
           val searchVals = searchData.split(",")       
     var totalRecords =0;
          searchVals.foreach { searchString => 
                            totalRecords +=  searchCallsByValue(searchString, searchRange, searchType, fileName, filePath) } 
            
          println("The total records for the Job are : " + totalRecords)
          
          if (totalRecords >0)
          FileUtils.zipFiles(filePath, fileName)
          
   }
   
   
   
   
     def searchMSISDN( job: Jobs){
     
     
    /*
     val searchFileData = sc.textFile(SearchConstants.SEARCH_CALLS_PATH+fileName)
     
      val searchData = searchFileData.mapPartitions(
                    lines => {
                        lines.map(line => {
                            line.split(" ").map(_.trim)
                        })
                    }).first()
     
                    
                    
          println(searchData(0)) 
          println(searchData(1))
          println(searchData(2))
          println(searchData(3))
      */    
          //scala> for (f<- 0 to numberofDays) yield dt1.plusDays(f).toString.substring(0,10).replaceAll("-","")
					import org.joda.time.DateTime
					import org.joda.time.Days
					// val dt1= new DateTime(2017,01,10,10,00,00)
					//val dt2= new DateTime(2017, 01, 30, 10, 00, 00)
					//val numberofDays = Days.daysBetween(dt1, dt2).getDays()
          
          val startDate = new DateTime(job.searchTimeStart.substring(0,4).toInt, job.searchTimeStart.substring(4,6).toInt,job.searchTimeStart.substring(6,8).toInt, 0, 0, 0)
          
          val endDate = new DateTime(job.searchTimeEnd.substring(0,4).toInt, job.searchTimeEnd.substring(4,6).toInt,job.searchTimeEnd.substring(6,8).toInt, 0, 0, 0)
          val numberofDays = Days.daysBetween(startDate, endDate).getDays()
          
       //   println(for (f<- 0 to numberofDays) yield startDate.plusDays(f).toString().substring(0,10).replaceAll("-",""))
          
          
          val searchRange = for (f<- 0 to numberofDays) yield startDate.plusDays(f).toString().substring(0,10).replaceAll("-","")
          
          //var callsFound= searchCallsByDays(searchRange, searchData(2))
          var totalCalls = scala.collection.mutable.Map[String, String]()
          
          
          //searchRange.foreach { searchDate => 
            
          
           // val mapOfFoundCalls = searchCallsByDays(searchDate, searchData(2))
            
           // totalCalls +=  mapOfFoundCalls
          
          
        //  }
          
          var fdCalls =       new String()
           val searchVals = job.getJobData().split(",")       
     
          searchVals.foreach { searchString => 
                      searchCallsByValue(searchString, searchRange, job.jobType, job.downLoadFileName, job.pathDownloadFile) } 
            
					
					
					FileUtils.zipFiles(job.pathDownloadFile, job.downLoadFileName)
          
   }
   
   
   
   
   
   
   
   
   
   
   def searchCallsByDays(searchDate: String, searchData:String, searchType:String): Array[Array[String]]={
     
     
   // var fdCalls= Array[Array[String])]()
      var fdCalls =       Array[Array[String]]()
     //  var fdCallsStr = new String()         
          try{
            
             
           val callData = sc.textFile(SearchConstants.CURRENT_SEARCH_DIR +  searchDate + "*.csv") 
     
           if (callData == null || callData.isEmpty()){
             val callData = sc.textFile(SearchConstants.ARCHIVE_SEARCH_DIR +  searchDate + "*.csv") 
           }
           
           
            if (callData == null || callData.isEmpty()){
              val partitionedCalls = callData.mapPartitions(
                    lines => {
                        lines.map(line => {
                            line.split(",").map(_.trim)
                        })
                    })
             
             if (searchType == "MSISDN"){
             val filtered24HourCalls = partitionedCalls.filter { x => x.length == 20 }.filter { arry => arry(CallCols.s_msisdn) == searchData || arry(CallCols.o_msisdn) == searchData} 
            fdCalls = filtered24HourCalls.collect()
            }
            else if (searchType == "IMSI"){
               val filtered24HourCalls = partitionedCalls.filter { x => x.length == 20 }.filter { arry => arry(CallCols.s_imsi) == searchData} 
            fdCalls = filtered24HourCalls.collect()
            }
            else if (searchType == "IMEI"){
               val filtered24HourCalls = partitionedCalls.filter { x => x.length == 20 }.filter { arry => arry(CallCols.s_imei) == searchData} 
            fdCalls = filtered24HourCalls.collect()
              
            }
             println("The fdCalls Size is : " + fdCalls.size)
              
             // fdCalls = filtered24HourCalls.map(line =>  (searchData, line)).collect()
              
            //  fdCalls.foreach { x => println(x +":::" + fdCalls.length) }
            }  
              
              
    
     } catch{
       case e: Exception => e.printStackTrace
     }
      return fdCalls
     
   }
   
   
   
   def searchCallsByValue(searchVal: String, searchRange:IndexedSeq[String], searchType: String, fileName: String, filePath:String): Int={
   var fdCalls= new String()
     var totalCalls =0
         searchRange.foreach { searchDate => 
        var callsFoundEachDay = searchCallsByDays(searchDate, searchVal, searchType)
        
        totalCalls += callsFoundEachDay.size
       
        if (totalCalls >0){
         callsFoundEachDay.foreach { x => 
                     FileUtils.writeFiles(x.mkString(","), searchType+"_"+searchVal, fileName, filePath) }
        }
    }
   
  
 //  println(fdCalls)
   
     println("The number of calls found each msisdn is: " +totalCalls)
    return totalCalls
   }
    
  
}