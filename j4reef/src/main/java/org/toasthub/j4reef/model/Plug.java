package org.toasthub.j4reef.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pi4j.io.gpio.GpioPinDigitalOutput;

public class Plug implements Serializable {

	private static final long serialVersionUID = 1L;
	public final static String MODE_WAVE = "WAVE";
	public final static String MODE_CONTINUOUS = "CONTINUOUS";
	public final static String MODE_OFF = "OFF";
	public final static String FEED_ON_CONTNIUOUS = "FEED_ON_CONTINUOUS";
	public final static String FEED_ON_WAVE = "FEED_ON_WAVE";
	public final static String FEED_OFF = "FEED_OFF";
	
	protected Long id;
	protected String name;
	protected String code;
	protected Boolean active;
	protected List<Schedule> schedules;
	protected GpioPinDigitalOutput gpio;
	protected String feedingMode;
	protected Long activeSchedule;
	protected Thread thread;
	
	// Constructor
	public Plug() {
	}
	
	public Plug(Long id, String name) {
		this.setId(id);
		this.setName(name);
		this.setCode(name);
		this.setActive(false);
		this.setFeedingMode(FEED_OFF);
	}

	public Plug(Long id, String name, Boolean active, String mode) {
		this.setId(id);
		this.setName(name);
		this.setActive(active);
		this.setFeedingMode(mode);
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

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public Boolean isActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	
	public List<Schedule> getSchedules() {
		return schedules;
	}
	public void setSchedules(List<Schedule> schedules) {
		this.schedules = schedules;
	}

	@JsonIgnore
	public GpioPinDigitalOutput getGpio() {
		return gpio;
	}
	public void setGpio(GpioPinDigitalOutput gpio) {
		this.gpio = gpio;
	}

	public String getFeedingMode() {
		return feedingMode;
	}
	public void setFeedingMode(String feedingMode) {
		this.feedingMode = feedingMode;
	}

	public Long getActiveSchedule() {
		return activeSchedule;
	}

	public void setActiveSchedule(Long activeSchedule) {
		this.activeSchedule = activeSchedule;
	}

	@JsonIgnore
	public Thread getThread() {
		return thread;
	}
	public void setThread(Thread thread) {
		this.thread = thread;
	}


}
