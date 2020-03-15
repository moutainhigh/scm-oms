package com.chinaservices.oms.edi.event.tms;

import com.chinaservices.event.Event;

/**
 * 订单下发TMS事件
 * @author Samuel
 */
public class CreateOrderEvent extends Event {

	private Integer[] taskIds;
	
	private Integer operator;

	public Integer[] getTaskIds() {
		return taskIds;
	}

	public Integer getOperator() {
		return operator;
	}

	public CreateOrderEvent(Integer[] taskIds, Integer operator) {
		this.taskIds = taskIds;
		this.operator = operator;
	}
	
}
