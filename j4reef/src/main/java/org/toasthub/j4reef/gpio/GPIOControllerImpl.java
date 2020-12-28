package org.toasthub.j4reef.gpio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PreDestroy;

import org.toasthub.j4reef.model.Config;
import org.toasthub.j4reef.model.Controller;
import org.toasthub.j4reef.model.GlobalConstant;
import org.toasthub.j4reef.model.Light;
import org.toasthub.j4reef.model.Plug;
import org.toasthub.j4reef.model.Schedule;
import org.toasthub.j4reef.model.Switch;
import org.toasthub.j4reef.utils.Request;
import org.toasthub.j4reef.utils.Response;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.sun.management.OperatingSystemMXBean;

@PropertySource(value = { "classpath:application.properties" })
@Service("GPIOController")
public class GPIOControllerImpl implements GPIOController {

	public static GpioController gpio;
	public static OperatingSystemMXBean os;
	protected boolean isMock = false;
	
	// Config
	protected Config config;
	
	private String configFile = "/opt/j4reef/config.json";

	
	public GPIOControllerImpl() {
		os = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		if ("x86_64".equals(os.getArch())) {
			isMock = true;
		} else {
			try {
				gpio = GpioFactory.getInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		initGPIO();
	}
	
	@PreDestroy
	public void destroy() {
		gpio.shutdown();
	}
	
	public void initGPIO() {
		
		
		getConfig();
		
		if (config.getController() == null) {
			config.setController(new Controller(1l, "Central", Controller.MODE_CENTRAL));
		}
		
		if (config.getPlug1() == null) {
			config.setPlug1(new Plug(1l, "P1"));
			List<Schedule> schedules = new ArrayList<Schedule>();
			schedules.add(new Schedule());
			config.getPlug1().setSchedules(schedules);
		}
		if (gpio != null) { config.getPlug1().setGpio(gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00,"R1", PinState.HIGH)); }
		
		if (config.getPlug2() == null) {
			config.setPlug2(new Plug(2l, "P2"));
			List<Schedule> schedules = new ArrayList<Schedule>();
			schedules.add(new Schedule());
			config.getPlug2().setSchedules(schedules);
		}
		if (gpio != null) { config.getPlug2().setGpio(gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01,"R2", PinState.HIGH)); }
		
		if (config.getPlug3() == null) {
			config.setPlug3(new Plug(3l, "P3"));
			List<Schedule> schedules = new ArrayList<Schedule>();
			schedules.add(new Schedule());
			config.getPlug3().setSchedules(schedules);
		}
		if (gpio != null) { config.getPlug3().setGpio(gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02,"R3", PinState.HIGH)); }
		
		if (config.getPlug4() == null) {
			config.setPlug4(new Plug(4l, "P4"));
			List<Schedule> schedules = new ArrayList<Schedule>();
			schedules.add(new Schedule());
			config.getPlug4().setSchedules(schedules);
		}
		if (gpio != null) { config.getPlug4().setGpio(gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03,"R4", PinState.HIGH)); }
		
		if (config.getPlug5() == null) {
			config.setPlug5(new Plug(5l, "P5"));
			List<Schedule> schedules = new ArrayList<Schedule>();
			schedules.add(new Schedule());
			config.getPlug5().setSchedules(schedules);
		}
		if (gpio != null) { config.getPlug5().setGpio(gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04,"R5", PinState.HIGH)); }
		
		if (config.getPlug6() == null) {
			config.setPlug6(new Plug(6l, "P6"));
			List<Schedule> schedules = new ArrayList<Schedule>();
			schedules.add(new Schedule());
			config.getPlug6().setSchedules(schedules);
		}
		if (gpio != null) { config.getPlug6().setGpio(gpio.provisionDigitalOutputPin(RaspiPin.GPIO_05,"R6", PinState.HIGH)); }
		
		if (config.getPlug7() == null) {
			config.setPlug7(new Plug(7l, "P7"));
			List<Schedule> schedules = new ArrayList<Schedule>();
			schedules.add(new Schedule());
			config.getPlug7().setSchedules(schedules);
		}
		if (gpio != null) { config.getPlug7().setGpio(gpio.provisionDigitalOutputPin(RaspiPin.GPIO_06,"R7", PinState.HIGH)); }
		
		if (config.getPlug8() == null) {
			config.setPlug8(new Plug(8l, "P8"));
			List<Schedule> schedules = new ArrayList<Schedule>();
			schedules.add(new Schedule());
			config.getPlug8().setSchedules(schedules);
		}
		if (gpio != null) { config.getPlug8().setGpio(gpio.provisionDigitalOutputPin(RaspiPin.GPIO_07,"R8", PinState.HIGH)); }
				
		// Lights
		if (config.getLight1() == null) {
			config.setLight1(new Light(1l, "L1"));
		}
		if (gpio != null) { config.getLight1().setGpio(gpio.provisionDigitalOutputPin(RaspiPin.GPIO_27,"LED1", PinState.LOW)); }
		if (config.getLight2() == null) {
			config.setLight2(new Light(2l, "L2"));
		}
		if (gpio != null) { config.getLight2().setGpio(gpio.provisionDigitalOutputPin(RaspiPin.GPIO_28,"LED2", PinState.LOW)); }
		if (config.getLight3() == null) {
			config.setLight3(new Light(3l, "L3"));
		}
		if (gpio != null) { config.getLight3().setGpio(gpio.provisionDigitalOutputPin(RaspiPin.GPIO_29,"LED3", PinState.LOW)); }
		
		
		// Switches
		if (config.getSwitch1() == null) {
			config.setSwitch1(new Switch(1l, "S1"));
		}
		if (gpio != null) {	
			config.getSwitch1().setGpio(gpio.provisionDigitalInputPin(RaspiPin.GPIO_25, PinPullResistance.PULL_DOWN)); 
			config.getSwitch1().getGpio().setShutdownOptions(true);
			config.getSwitch1().getGpio().addListener(new GpioPinListenerDigital() {
	            @Override
	            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
	                // display pin state on console
	                System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
	                try {
	                	if ("HIGH".equals(event.getState().toString())) {
	                		config.getLight2().getGpio().high();
	                		//config.getPlug2().getGpio().low();
	                	} else {
	                		config.getLight2().getGpio().low();
	                		//config.getPlug2().getGpio().high();
	                	}
	                	//led29.high();
	                } catch (Exception e) {
	                	e.printStackTrace();
	                }
	            }

	        });
		}
			
		if (config.getSwitch2() == null) {
			config.setSwitch2(new Switch(2l, "S2"));
		}
		if (gpio != null) {	
			config.getSwitch2().setGpio(gpio.provisionDigitalInputPin(RaspiPin.GPIO_26, PinPullResistance.PULL_DOWN)); 
			config.getSwitch2().getGpio().setShutdownOptions(true);
			config.getSwitch2().getGpio().addListener(new GpioPinListenerDigital() {
	            @Override
	            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
	                // display pin state on console
	                System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
	                try {
	                	if ("HIGH".equals(event.getState().toString())) {
	                		config.getLight3().getGpio().high();
	                		//config.getPlug3().getGpio().low();
	                	} else {
	                		config.getLight3().getGpio().low();
	                		//config.getPlug3().getGpio().high();
	                	}
	                	//led29.high();
	                } catch (Exception e) {
	                	e.printStackTrace();
	                }
	            }

	        });
		}

		
		
	}
	
	@Scheduled(cron="0 * * * * ?")
	public void schedulesTask() {
		checkSchedules();
	}
	
	
	private void checkSchedules() {
		try {
			System.out.println("Checking schedules");
			LocalDateTime now = LocalDateTime.now();
			List<Plug> plugs = getPlugs();
			for (Plug plug : plugs) {
				if (plug.isActive() && plug.getSchedules() != null && plug.getSchedules().size() > 0) {
					for(Schedule schedule : plug.getSchedules()) {
						LocalDateTime startTime = LocalDateTime.now().with(LocalTime.of(schedule.getStartHour(), schedule.getStartMinute(),0));
						LocalDateTime endTime = LocalDateTime.now().with(LocalTime.of(schedule.getEndHour(), schedule.getEndMinute(),59));
						System.out.println("Plug: " + plug.getName() +" Schedule: " + schedule.getName());
						System.out.println("   Time now: "+ now );
						System.out.println("      Start time: "+ startTime );
						System.out.println("      End time: "+ endTime );
						if (startTime.isBefore(now) && endTime.isAfter(now)) {
							if (plug.getActiveSchedule() == schedule.getId()) {
								// do nothing still in the same schedule
								System.out.println("      In same schedule doing nothing");
							} else {
								if (schedule.getMode().equals(Plug.MODE_WAVE)) {
									System.out.println("      Mode "+ schedule.getMode() +" setting Blink on "+schedule.getTimeOn()+ " off "+schedule.getTimeOff());
									if (!isMock) {
										this.blink(plug,schedule.getTimeOn(),schedule.getTimeOff());
									}
								} else if (schedule.getMode().equals(Plug.MODE_CONTINUOUS)){
									System.out.println("       Mode "+ schedule.getMode() +" setting low");
									if (!isMock) {
										if (plug.getThread() != null) { 
											plug.getThread().interrupt(); 
											plug.setThread(null);
										}
										plug.getGpio().low();
									}
								} else {
									System.out.println("       Mode "+ schedule.getMode() +" setting high");
									if (!isMock) {
										if (plug.getThread() != null) { 
											plug.getThread().interrupt(); 
											plug.setThread(null);
										}
										plug.getGpio().high();
									}
								}
								plug.setActiveSchedule(schedule.getId());
							}
						} else {
							System.out.println("      This schedule is not active");
						}
						
					}
				} else {
					if (!isMock) {
						plug.getGpio().high();
					}
					System.out.println("Plug " + plug.getName() +" is disabled");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void test(Request request, Response response) {
		response.getParams().put("response", "test good");
		
	}
	
	@Override
	public void blink(Request request, Response response) {
		
		response.getParams().put("response", "blinking");
		try {
			if (!isMock) {
				config.getLight1().getGpio().blink(1000,15000);
			}
			System.out.println("Light is: Blinking");
	       
			
		} catch (Exception e) {
			if (!isMock) {
				config.getLight1().getGpio().low();
				//gpio.shutdown();
			}
			e.printStackTrace();
		}
	}

	@Override
	public void on(Request request, Response response) {
		
		response.getParams().put("response", "on");
		try {
			if (!isMock) {
				config.getLight1().getGpio().high();
			}
			System.out.println("Light is: ON");
			saveConfig();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void off(Request request, Response response) {
		
		response.getParams().put("response", "off");
		try {
			if (!isMock) {
				config.getLight1().getGpio().low();
			}
			System.out.println("Light is: OFF");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void listPlug(Request request, Response response) {
		List<Plug> plugs = getPlugs();
		response.addParam(GlobalConstant.ITEMCOUNT, plugs.size());
		response.addParam(GlobalConstant.ITEMS, plugs);
	}
	
	private List<Plug> getPlugs() {
		List<Plug> plugs = new ArrayList<Plug>();
		plugs.add(config.getPlug1());
		plugs.add(config.getPlug2());
		plugs.add(config.getPlug3());
		plugs.add(config.getPlug4());
		plugs.add(config.getPlug5());
		plugs.add(config.getPlug6());
		plugs.add(config.getPlug7());
		plugs.add(config.getPlug8());
		return plugs;
	}
	
	@Override
	public void listLight(Request request, Response response) {
		List<Light> lights = new ArrayList<Light>();
		lights.add(config.getLight1());
		lights.add(config.getLight2());
		lights.add(config.getLight3());
		response.addParam(GlobalConstant.ITEMCOUNT, lights.size());
		response.addParam(GlobalConstant.ITEMS, lights);
	}
	
	@Override
	public void listSwitch(Request request, Response response) {
		List<Switch> switches = new ArrayList<Switch>();
		switches.add(config.getSwitch1());
		switches.add(config.getSwitch2());
		switches.add(config.getSwitch3());
		response.addParam(GlobalConstant.ITEMCOUNT, switches);
		response.addParam(GlobalConstant.ITEMS, switches);
	}
	
	@Override
	public void saveConfig() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			FileWriter file = new FileWriter(configFile);
			String jsonInString = mapper.writeValueAsString(config);
			file.write(jsonInString);
			file.close();
		} catch (IOException e) {
			System.out.println("Error writing to file");
			e.printStackTrace();
		}
		
	}
	
	private void getConfig() {
		ObjectMapper mapper = new ObjectMapper();
		System.out.println("Get file "+ configFile);
		
			File file = new File(configFile);
			if (file.exists()) {
				try {
					config = mapper.readValue(new FileReader(configFile), Config.class);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JsonParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JsonMappingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (config == null) {
				config = new Config();
			}
		
	}

	@Override
	public void listController(Request request, Response response) {
		List<Controller> controllers = new ArrayList<Controller>();
		controllers.add(config.getController());
		response.addParam(GlobalConstant.ITEMS, controllers);
		
	}

	@Override
	public void countController(Request request, Response response) {
		response.addParam(GlobalConstant.ITEMCOUNT, 1l);
		
	}

	@Override
	public void listSchedule(Request request, Response response) {
		if (request.containsParam("plugId")) {
			switch ((Integer) request.getParam("plugId")) {
			case 1:
				response.addParam(GlobalConstant.ITEMCOUNT, config.getPlug1().getSchedules().size());
				response.addParam(GlobalConstant.ITEMS, config.getPlug1().getSchedules());
				break;
			case 2:
				response.addParam(GlobalConstant.ITEMCOUNT, config.getPlug2().getSchedules().size());
				response.addParam(GlobalConstant.ITEMS, config.getPlug2().getSchedules());
				break;
			case 3:
				response.addParam(GlobalConstant.ITEMCOUNT, config.getPlug3().getSchedules().size());
				response.addParam(GlobalConstant.ITEMS, config.getPlug3().getSchedules());
				break;
			case 4:
				response.addParam(GlobalConstant.ITEMCOUNT, config.getPlug4().getSchedules().size());
				response.addParam(GlobalConstant.ITEMS, config.getPlug4().getSchedules());
				break;
			case 5:
				response.addParam(GlobalConstant.ITEMCOUNT, config.getPlug5().getSchedules().size());
				response.addParam(GlobalConstant.ITEMS, config.getPlug5().getSchedules());
				break;
			case 6:
				response.addParam(GlobalConstant.ITEMCOUNT, config.getPlug6().getSchedules().size());
				response.addParam(GlobalConstant.ITEMS, config.getPlug6().getSchedules());
				break;
			case 7:
				response.addParam(GlobalConstant.ITEMCOUNT, config.getPlug7().getSchedules().size());
				response.addParam(GlobalConstant.ITEMS, config.getPlug7().getSchedules());
				break;
			case 8:
				response.addParam(GlobalConstant.ITEMCOUNT, config.getPlug8().getSchedules().size());
				response.addParam(GlobalConstant.ITEMS, config.getPlug8().getSchedules());
				break;
			default:
				
				break;
			}
		}
		
	}

	@Override
	public void getPlug(Request request, Response response) {
		if (request.containsParam(GlobalConstant.ITEMID)) {
			switch ((Integer) request.getParam(GlobalConstant.ITEMID)) {
			case 1:
				response.addParam(GlobalConstant.ITEM, config.getPlug1());
				break;
			case 2:
				response.addParam(GlobalConstant.ITEM, config.getPlug2());
				break;
			case 3:
				response.addParam(GlobalConstant.ITEM, config.getPlug3());
				break;
			case 4:
				response.addParam(GlobalConstant.ITEM, config.getPlug4());
				break;
			case 5:
				response.addParam(GlobalConstant.ITEM, config.getPlug5());
				break;
			case 6:
				response.addParam(GlobalConstant.ITEM, config.getPlug6());
				break;
			case 7:
				response.addParam(GlobalConstant.ITEM, config.getPlug7());
				break;
			case 8:
				response.addParam(GlobalConstant.ITEM, config.getPlug8());
				break;
			default:
				
				break;
			}
		}
	}
	
	@Override
	public void checkPlugActive(Request request, Response response) {
		try {
			if (request.containsParam(GlobalConstant.ITEMID)) {
				Plug plug = null;
				switch ((Integer) request.getParam(GlobalConstant.ITEMID)) {
				case 1:
					plug = config.getPlug1();
					break;
				case 2:
					plug = config.getPlug2();
					break;
				case 3:
					plug = config.getPlug3();
					break;
				case 4:
					plug = config.getPlug4();
					break;
				case 5:
					plug = config.getPlug5();
					break;
				case 6:
					plug = config.getPlug6();
					break;
				case 7:
					plug = config.getPlug7();
					break;
				case 8:
					plug = config.getPlug8();
					break;
				default:
					
					break;
				}
				if (plug != null) {
					if (plug.isActive()) {
						if (!isMock) {
							plug.getGpio().low();
						}
						System.out.println("Plug enabled set to low");
					} else {
						if (!isMock) {
							plug.getGpio().high();
							plug.setActiveSchedule(null);
							if (plug.getThread() != null) {
								plug.getThread().interrupt();
							}
							plug.setThread(null);
						}
						System.out.println("Plug disabled set to high");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void getSchedule(Request request, Response response) {
		if (request.containsParam(GlobalConstant.PARENTID) && request.containsParam(GlobalConstant.ITEMID)) {
			List<Schedule> schedules = null;
			switch ((Integer) request.getParam(GlobalConstant.PARENTID)) {
			case 1:
				schedules = config.getPlug1().getSchedules();
				break;
			case 2:
				schedules = config.getPlug2().getSchedules();
				break;
			case 3:
				schedules = config.getPlug3().getSchedules();
				break;
			case 4:
				schedules = config.getPlug4().getSchedules();
				break;
			case 5:
				schedules = config.getPlug5().getSchedules();
				break;
			case 6:
				schedules = config.getPlug6().getSchedules();
				break;
			case 7:
				schedules = config.getPlug7().getSchedules();
				break;
			case 8:
				schedules = config.getPlug8().getSchedules();
				break;
			default:
				
				break;
			}
			for(Schedule schedule : schedules) {
				if (schedule.getId().equals(new Long((Integer) request.getParam(GlobalConstant.ITEMID)))) {
					response.addParam(GlobalConstant.ITEM, schedule);
				}
			}
		}
		
	}

	@Override
	public void addSchedule(Request request, Response response) {
		if ("PLUG".equals(request.getParam(GlobalConstant.PARENTTYPE))) {
			Schedule schedule = (Schedule)request.getParam(GlobalConstant.ITEM);
			Plug plug = null;
			// set id
			Long x = 1l;
			switch ((Integer) request.getParam(GlobalConstant.PARENTID)) {
			case 1:
				plug = config.getPlug1();
				break;
			case 2:
				plug = config.getPlug2();
				break;
			case 3:
				plug = config.getPlug3();
				break;
			case 4:
				plug = config.getPlug4();
				break;
			case 5:
				plug = config.getPlug5();
				break;
			case 6:
				plug = config.getPlug6();
				break;
			case 7:
				plug = config.getPlug7();
				break;
			case 8:
				plug = config.getPlug8();
				break;
			default:
				break;
			}
			if (plug.getActiveSchedule() == schedule.getId()) {
				plug.setActiveSchedule(null);
				if (plug.getThread() != null) {
					plug.setThread(null);
				}
			}
			x = plug.getSchedules().get(plug.getSchedules().size() - 1).getId() + 1l;
			schedule.setId(x);
			plug.getSchedules().add(schedule);
		}
		
	}

	@Override
	public void deleteSchedule(Request request, Response response) {
		if ("PLUG".equals(request.getParam(GlobalConstant.PARENTTYPE))) {
			
			Plug plug = null;
			switch ((Integer) request.getParam(GlobalConstant.PARENTID)) {
			case 1:
				plug = config.getPlug1();
				break;
			case 2:
				plug = config.getPlug2();
				break;
			case 3:
				plug = config.getPlug3();
				break;
			case 4:
				plug = config.getPlug4();
				break;
			case 5:
				plug = config.getPlug5();
				break;
			case 6:
				plug = config.getPlug6();
				break;
			case 7:
				plug = config.getPlug7();
				break;
			case 8:
				plug = config.getPlug8();
				break;
			default:
				break;
			}
			Schedule schedule = null;
			for(Schedule s : plug.getSchedules()) {
				if (s.getId().equals(new Long((Integer) request.getParam(GlobalConstant.ITEMID)))) {
					schedule = s;
					break;
				}
			}
			
			if (schedule != null) {
				if (plug.getActiveSchedule() == schedule.getId()) {
					plug.setActiveSchedule(null);
					if (plug.getThread() != null) {
						plug.setThread(null);
					}
				}
				plug.getSchedules().remove(schedule);
			}
		}
	}

	@Override
	public void clearActiveSchedule(Request request, Response response) {
		if (request.containsParam(GlobalConstant.PARENTID) && request.containsParam(GlobalConstant.ITEMID)) {
			List<Schedule> schedules = null;
			switch ((Integer) request.getParam(GlobalConstant.PARENTID)) {
			case 1:
				config.getPlug1().setActiveSchedule(null);
				break;
			case 2:
				config.getPlug2().setActiveSchedule(null);
				break;
			case 3:
				config.getPlug3().setActiveSchedule(null);
				break;
			case 4:
				config.getPlug4().setActiveSchedule(null);
				break;
			case 5:
				config.getPlug5().setActiveSchedule(null);
				break;
			case 6:
				config.getPlug6().setActiveSchedule(null);
				break;
			case 7:
				config.getPlug7().setActiveSchedule(null);
				break;
			case 8:
				config.getPlug8().setActiveSchedule(null);
				break;
			default:
				
				break;
			}
		}
		
	}
	
	private void blink(Plug plug, int onDuration, int offDuration) {
		if (plug.getThread() != null) {
			plug.getThread().interrupt();
			plug.setThread(null);
		}
		plug.setThread(new Thread(new Runnable() {

            @Override
            public void run() {
                while (plug.isActive()) {
                	if (Thread.interrupted()) {
                        break;
                    }
                    try {
                        plug.getGpio().high();
                        Thread.sleep(offDuration * 1000);
                        plug.getGpio().low();
                        Thread.sleep(onDuration * 1000);
                    } catch (InterruptedException e) {
                        plug.getGpio().low();
                        Thread.currentThread().interrupt();
                        e.printStackTrace();
                    }
                }
            }
        }));
        plug.getThread().start();
	}
}
