package com.chinaservices.oms.cust.model;

import com.chinaservices.sdk.binding.annotation.Table;
import com.jfinal.plugin.activerecord.Model;

@SuppressWarnings("serial")
@Table(tableName = "cs_service_area_block", pkName = "id")
public class CsServiceAreaBlock extends Model<CsServiceAreaBlock>  {
	public static final CsServiceAreaBlock dao = new CsServiceAreaBlock();
}
