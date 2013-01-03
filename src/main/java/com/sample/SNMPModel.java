package com.sample;

public class SNMPModel {

	/**
	 * 10 = experiment using control network
	 */
	private int status;
	public String message;

	public String getMessage() {
		return this.message;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status){
		this.status = status;
		if(status == 2){
			this.message = "eth1 is failed";
		}
	}
	public void setStatusMsg(int status, String msg){
		this.status = status;
		if(status == 9999){
			this.message = msg;
		}
	}
	
}
