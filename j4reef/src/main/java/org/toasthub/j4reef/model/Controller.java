package org.toasthub.j4reef.model;

import java.io.Serializable;

public class Controller implements Serializable {

	private static final long serialVersionUID = 1L;
	public final static String MODE_CENTRAL = "CENTRAL";
	public final static String MODE_REMOTE = "REMOTE";
	
	protected Long id;
	protected String name;
	protected String mode;
	protected Boolean active;
	
	// Constructor
	public Controller(){}
	
	public Controller(Long id, String name, String mode){
		this.setId(id);
		this.setName(name);
		this.setMode(mode);
		this.setActive(true);
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

	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}

	public Boolean isActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	
}
