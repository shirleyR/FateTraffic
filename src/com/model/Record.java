package com.model;

public class Record {
	
	private String plate;
	private String timeslot;
	
	private String monitorid;
	private String tomonitorid;
	private long num;
	private int id;
	public long getNum() {
		return num;
	}
	public void setNum(long num) {
		this.num = num;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPlate() {
		return plate;
	}
	public void setPlate(String plate) {
		this.plate = plate;
	}
	public String getTimeslot() {
		return timeslot;
	}
	public void setTimeslot(String timeslot) {
		this.timeslot = timeslot;
	}
	public String getMonitorid() {
		return monitorid;
	}
	public void setMonitorid(String monitorid) {
		this.monitorid = monitorid;
	}
	public String gettoMonitorid() {
		return tomonitorid;
	}
	public void settoMonitorid(String monitorid) {
		this.tomonitorid = monitorid;
	}
	
}
