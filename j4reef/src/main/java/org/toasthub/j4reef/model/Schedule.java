package org.toasthub.j4reef.model;

import java.io.Serializable;


public class Schedule implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected Long id;
	protected String name;
	
	protected Integer startHour;
	protected Integer startMinute;
	protected Integer startSecond;
	
	protected Integer endHour;
	protected Integer endMinute;
	protected Integer endSecond;
	
	// continuous or wave
	protected String mode;
	
	// for wave form
	protected Integer timeOn;
	protected Integer timeOff;
	
	// Constructor
	public Schedule() {
		this.setId(1l);
		this.setName("All Day");
		this.setStartHour(0);
		this.setStartMinute(0);
		this.setStartSecond(0);
		this.setEndHour(23);
		this.setEndMinute(59);
		this.setEndSecond(59);
		this.setMode(Plug.MODE_CONTINUOUS);
	}
	
	public Schedule(Long id, String name, Integer startHour, Integer startMinute, Integer startSecond, Integer endHour, Integer endMinute, Integer endSecond, String mode) {
		this.setId(id);
		this.setName(name);
		this.setStartHour(startHour);
		this.setStartMinute(startMinute);
		this.setStartSecond(startSecond);
		this.setEndHour(endHour);
		this.setEndMinute(endMinute);
		this.setEndSecond(endSecond);
		this.setMode(mode);
	}
	
	// Methods

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Integer getStartHour() {
		return startHour;
	}
	public void setStartHour(Integer startHour) {
		this.startHour = startHour;
	}

	public Integer getStartMinute() {
		return startMinute;
	}
	public void setStartMinute(Integer startMinute) {
		this.startMinute = startMinute;
	}

	public Integer getStartSecond() {
		return startSecond;
	}
	public void setStartSecond(Integer startSecond) {
		this.startSecond = startSecond;
	}

	public Integer getEndHour() {
		return endHour;
	}
	public void setEndHour(Integer endHour) {
		this.endHour = endHour;
	}

	public Integer getEndMinute() {
		return endMinute;
	}
	public void setEndMinute(Integer endMinute) {
		this.endMinute = endMinute;
	}

	public Integer getEndSecond() {
		return endSecond;
	}
	public void setEndSecond(Integer endSecond) {
		this.endSecond = endSecond;
	}

	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}

	public Integer getTimeOn() {
		return timeOn;
	}
	public void setTimeOn(Integer timeOn) {
		this.timeOn = timeOn;
	}

	public Integer getTimeOff() {
		return timeOff;
	}
	public void setTimeOff(Integer timeOff) {
		this.timeOff = timeOff;
	}

	
}
