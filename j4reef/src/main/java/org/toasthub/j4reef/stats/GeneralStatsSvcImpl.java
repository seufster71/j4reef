package org.toasthub.j4reef.stats;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.toasthub.j4reef.utils.Request;
import org.toasthub.j4reef.utils.Response;
import org.springframework.stereotype.Service;

import com.pi4j.platform.PlatformManager;
import com.pi4j.system.NetworkInfo;
import com.pi4j.system.SystemInfo;
import com.sun.management.OperatingSystemMXBean;

@Service("GeneralStatsSvc")
public class GeneralStatsSvcImpl implements GeneralStatsSvc{

	public static OperatingSystemMXBean os;
	
	@Override
	public void systemStats(Request request, Response response) {
        response.addParam("Arch", os.getArch());
        response.addParam("Name", os.getName());
        response.addParam("Version", os.getVersion());
        response.addParam("Available cores", Runtime.getRuntime().availableProcessors());
      
	    /* Total amount of free memory available to the JVM */
        response.addParam("Free memory (bytes)", Runtime.getRuntime().freeMemory());

	    /* This will return Long.MAX_VALUE if there is no preset limit */
	    long maxMemory = Runtime.getRuntime().maxMemory();
	    /* Maximum amount of memory the JVM will attempt to use */
	    response.addParam("Maximum memory (bytes)", (maxMemory == Long.MAX_VALUE ? "no limit" : maxMemory));

	    /* Total memory currently available to the JVM */
	    response.addParam("Total memory available to JVM (bytes)", Runtime.getRuntime().totalMemory());

	    /* Get a list of all filesystem roots on this system */
	    File[] roots = File.listRoots();

	    /* For each filesystem root, print some info */
	    for (File root : roots) {
	    	response.addParam("File system root " + root.getName(), root.getAbsolutePath());
	    	response.addParam("Total space (bytes) " + root.getName(), root.getTotalSpace());
	    	response.addParam("Free space (bytes) " + root.getName(), root.getFreeSpace());
	    	response.addParam("Usable space (bytes) " + root.getName(), root.getUsableSpace());
	    }
		
	}

	@Override
	public void prefStats(Request request, Response response) {
		response.addParam("Processor count", os.getAvailableProcessors());
		response.addParam("Load average", os.getSystemLoadAverage());
		response.addParam("Physical Memory Size", os.getTotalPhysicalMemorySize());
		response.addParam("Free Physical Memory", os.getFreePhysicalMemorySize());
		response.addParam("Free Swap Size", os.getFreeSwapSpaceSize());
		response.addParam("Commited Virtual Memory Size", os.getCommittedVirtualMemorySize());
	}

	@Override
	public void piStats(Request request, Response response) {

        try {
        	response.addParam("Platform Name", PlatformManager.getPlatform().getLabel());
        } catch (UnsupportedOperationException ex){}
        try {
        	response.addParam("Platform ID", PlatformManager.getPlatform().getId());
        } catch (UnsupportedOperationException ex){}
        try {
        	response.addParam("Serial Number", SystemInfo.getSerial());
        } catch (UnsupportedOperationException | IOException | InterruptedException ex){}
        try {
        	response.addParam("CPU Revision", SystemInfo.getCpuRevision());
        } catch (UnsupportedOperationException | IOException | InterruptedException ex){}
        try {
        	response.addParam("CPU Architecture", SystemInfo.getCpuArchitecture());
        } catch (UnsupportedOperationException | IOException | InterruptedException ex){}
        try {
        	response.addParam("CPU Part", SystemInfo.getCpuPart());
        } catch (UnsupportedOperationException | IOException | InterruptedException ex){}
        try { 
        	response.addParam("CPU Temperature", SystemInfo.getCpuTemperature());
        } catch (UnsupportedOperationException | IOException | InterruptedException ex){}
        try { 
        	response.addParam("CPU Core Voltage", SystemInfo.getCpuVoltage());
        } catch (UnsupportedOperationException | IOException | InterruptedException ex){}
        try { 
        	response.addParam("CPU Model Name", SystemInfo.getModelName());
        } catch (UnsupportedOperationException | IOException | InterruptedException ex){}
        try { 
        	response.addParam("Processor", SystemInfo.getProcessor());
        } catch (UnsupportedOperationException | IOException | InterruptedException ex){}
        try { 
        	response.addParam("Hardware", SystemInfo.getHardware());
        } catch (UnsupportedOperationException | IOException | InterruptedException ex){}
        try { 
        	response.addParam("Hardware Revision", SystemInfo.getRevision());
        } catch (UnsupportedOperationException | IOException | InterruptedException ex){}
        try { 
        	response.addParam("Is Hard Float ABI", SystemInfo.isHardFloatAbi());
        } catch(UnsupportedOperationException ex){}
        try { 
        	response.addParam("Board Type", SystemInfo.getBoardType().name());
        } catch (UnsupportedOperationException | IOException | InterruptedException ex){}

        try { 
        	response.addParam("Total Memory", SystemInfo.getMemoryTotal());
        } catch (UnsupportedOperationException | IOException | InterruptedException ex){}
        try { 
        	response.addParam("Used Memory", SystemInfo.getMemoryUsed());
        } catch (UnsupportedOperationException | IOException | InterruptedException ex){}
        try { 
        	response.addParam("Free Memory", SystemInfo.getMemoryFree());
        } catch (UnsupportedOperationException | IOException | InterruptedException ex){}
        try { 
        	response.addParam("Shared Memory", SystemInfo.getMemoryShared());
        } catch (UnsupportedOperationException | IOException | InterruptedException ex){}
        try {
        	response.addParam("Memory Buffers", SystemInfo.getMemoryBuffers());
        } catch (UnsupportedOperationException | IOException | InterruptedException ex){}
        try { 
        	response.addParam("Cached Memory", SystemInfo.getMemoryCached());
        } catch (UnsupportedOperationException | IOException | InterruptedException ex){}
        try {
        	response.addParam("SDRAM_C Voltage", SystemInfo.getMemoryVoltageSDRam_C());
        } catch (UnsupportedOperationException | IOException | InterruptedException ex){}
        try {
        	response.addParam("SDRAM_I Voltage", SystemInfo.getMemoryVoltageSDRam_I());
        } catch (UnsupportedOperationException | IOException | InterruptedException ex){}
        try {
        	response.addParam("SDRAM_P Voltage", SystemInfo.getMemoryVoltageSDRam_P());
        } catch (UnsupportedOperationException | IOException | InterruptedException ex){}

        try {
        	response.addParam("OS Name", SystemInfo.getOsName());
        } catch (UnsupportedOperationException ex){}
        try {
        	response.addParam("OS Version", SystemInfo.getOsVersion());
        } catch (UnsupportedOperationException ex){}
        try {
        	response.addParam("OS Architecture", SystemInfo.getOsArch());
        } catch (UnsupportedOperationException ex){}
        try {
        	response.addParam("OS Firmware Build", SystemInfo.getOsFirmwareBuild());
        } catch (UnsupportedOperationException | IOException | InterruptedException ex){}
        try {
        	response.addParam("OS Firmware Date", SystemInfo.getOsFirmwareDate());
        } catch (UnsupportedOperationException | IOException | InterruptedException | ParseException ex){}
    
        response.addParam("Java Vendor", SystemInfo.getJavaVendor());
        response.addParam("Java Vendor URL", SystemInfo.getJavaVendorUrl());
        response.addParam("Java Version", SystemInfo.getJavaVersion());
        response.addParam("Java VM", SystemInfo.getJavaVirtualMachine());
        response.addParam("Java Runtime", SystemInfo.getJavaRuntime());


        // display some of the network information
        try {
        	response.addParam("Hostname", NetworkInfo.getHostname());
		
        	List<String> ipAddresses = new ArrayList<String>();
			for (String ipAddress : NetworkInfo.getIPAddresses()) {
				ipAddresses.add(ipAddress);
			}
			response.addParam("IP Addresses", ipAddresses);
			
			List<String> fqdns = new ArrayList<String>();
			for (String fqdn : NetworkInfo.getFQDNs()) {
				fqdns.add(fqdn);
			}
			response.addParam("FQDN", fqdns);
			
			List<String> nameserveres = new ArrayList<String>();
			for (String nameserver : NetworkInfo.getNameservers()) {
				nameserveres.add(nameserver);
			}
			response.addParam("Nameserver", nameserveres);
        } catch (IOException | InterruptedException e) {}
        

        try {
        	response.addParam("H264 Codec Enabled", SystemInfo.getCodecH264Enabled());
        } catch (UnsupportedOperationException | IOException | InterruptedException ex){}
        try { 
        	response.addParam("MPG2 Codec Enabled", SystemInfo.getCodecMPG2Enabled());
        } catch (UnsupportedOperationException | IOException | InterruptedException ex){}
        try {
        	response.addParam("WVC1 Codec Enabled", SystemInfo.getCodecWVC1Enabled());
        } catch (UnsupportedOperationException | IOException | InterruptedException ex){}
		
	}

}
