package com.model;

public class Plate {
	private String platename;
	private int id;
	
	private String monitor_id;

	public String getMonitor_id() {
		return monitor_id;
	}

	public void setMonitor_id(String monitor_id) {
		this.monitor_id = monitor_id;
	}

	private int num;
	private String recognized_time;
	public String getRecognized_time() {
		return recognized_time;
	}

	public void setRecognized_time(String recognized_time) {
		this.recognized_time = recognized_time;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getPlatename() {
		return platename;
	}

	public void setPlatename(String platename) {
		this.platename = platename;
	}

}
