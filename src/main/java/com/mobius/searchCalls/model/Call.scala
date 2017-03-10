package com.mobius.searchCalls.model

import scala.beans.BeanProperty

class Call () extends Serializable{
  
  @BeanProperty
  var call_date_hour: String = _
  @BeanProperty
  var sw_id: Int = _
  @BeanProperty
  var s_imsi: String = _
  @BeanProperty
  var s_imei: String = _
  @BeanProperty
  var s_ci: Int = _
  @BeanProperty
  var s_lac: Int = _
  @BeanProperty
  var trunk_in: String = _
  @BeanProperty
  var trunk_out: String = _
  @BeanProperty
  var term_cause: Int = _
  @BeanProperty
  var term_reason: Int = _
  @BeanProperty
  var ss_code: Int = _
  @BeanProperty
  var charge_indicator: Int = _
  @BeanProperty
  var call_id: Long = _
  @BeanProperty
  var o_msisdn: String = _
  @BeanProperty
  var call_start_time: String = _
  @BeanProperty
  var duration: Int = _
  @BeanProperty
  var o_call_id: Long = _
  @BeanProperty
  var call_type: Int = _
  @BeanProperty
  var call_end_time: String = _
  @BeanProperty
  var s_msisdn: String = _
  
}