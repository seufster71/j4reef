package org.toasthub.j4reef.model;

import java.io.Serializable;

public class Config implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected Controller controller;
	
	protected Plug plug1; //gpio1
	protected Plug plug2; //gpio2
	protected Plug plug3; //gpio3
	protected Plug plug4; //gpio4
	protected Plug plug5; //gpio5
	protected Plug plug6; //gpio6
	protected Plug plug7; //gpio7
	protected Plug plug8; //gpio8
	
	protected Switch switch1;
	protected Switch switch2; 
	protected Switch switch3;
	protected Switch switch4;
	
	protected Light light1;
	protected Light light2;
	protected Light light3;

	// Constructor
	public Config() {
	}

	// Methods
	public Controller getController() {
		return controller;
	}
	public void setController(Controller controller) {
		this.controller = controller;
	}
	
	public Plug getPlug1() {
		return plug1;
	}
	public void setPlug1(Plug plug1) {
		this.plug1 = plug1;
	}

	public Plug getPlug2() {
		return plug2;
	}
	public void setPlug2(Plug plug2) {
		this.plug2 = plug2;
	}

	public Plug getPlug3() {
		return plug3;
	}
	public void setPlug3(Plug plug3) {
		this.plug3 = plug3;
	}

	public Plug getPlug4() {
		return plug4;
	}
	public void setPlug4(Plug plug4) {
		this.plug4 = plug4;
	}

	public Plug getPlug5() {
		return plug5;
	}
	public void setPlug5(Plug plug5) {
		this.plug5 = plug5;
	}

	public Plug getPlug6() {
		return plug6;
	}
	public void setPlug6(Plug plug6) {
		this.plug6 = plug6;
	}

	public Plug getPlug7() {
		return plug7;
	}
	public void setPlug7(Plug plug7) {
		this.plug7 = plug7;
	}

	public Plug getPlug8() {
		return plug8;
	}
	public void setPlug8(Plug plug8) {
		this.plug8 = plug8;
	}

	public Switch getSwitch1() {
		return switch1;
	}
	public void setSwitch1(Switch switch1) {
		this.switch1 = switch1;
	}

	public Switch getSwitch2() {
		return switch2;
	}
	public void setSwitch2(Switch switch2) {
		this.switch2 = switch2;
	}

	public Switch getSwitch3() {
		return switch3;
	}
	public void setSwitch3(Switch switch3) {
		this.switch3 = switch3;
	}

	public Switch getSwitch4() {
		return switch4;
	}
	public void setSwitch4(Switch switch4) {
		this.switch4 = switch4;
	}

	public Light getLight1() {
		return light1;
	}
	public void setLight1(Light light1) {
		this.light1 = light1;
	}

	public Light getLight2() {
		return light2;
	}
	public void setLight2(Light light2) {
		this.light2 = light2;
	}

	public Light getLight3() {
		return light3;
	}
	public void setLight3(Light light3) {
		this.light3 = light3;
	}
	
}
