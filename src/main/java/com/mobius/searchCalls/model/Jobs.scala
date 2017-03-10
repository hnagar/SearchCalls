package com.mobius.searchCalls.model

import scala.beans.BeanProperty

class Jobs() extends Serializable {
  
  
   @BeanProperty
   var user: String=_
   @BeanProperty
   var jobName: String=_
    @BeanProperty
   var jobType: String=_
    @BeanProperty
   var jobData: String=_
    @BeanProperty
   var searchTimeStart: String=_
    @BeanProperty
   var searchTimeEnd: String=_
    @BeanProperty
   var jobState: String=_
    @BeanProperty
   var pathJobRequest: String=_
    @BeanProperty
   var pathDownloadFile: String=_
    @BeanProperty
   var records: String=_
    @BeanProperty
   var requestTime: String=_
    @BeanProperty
   var insertTime: String=_
    @BeanProperty
   var downLoadFileName: String=_
   
   
   
}