/**
 *  BlueCove - Java library for Bluetooth
 *	Copyright (C) 2007 Eric Wagner
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 *
 */
package com.intel.bluetooth.test;

import java.util.Properties;

import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;
import javax.bluetooth.UUID;

public class RFCOMMTest implements DiscoveryListener {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RFCOMMTest		worker = new RFCOMMTest();
		
		worker.doWork();
	}
	
	public RFCOMMTest(){
	}
	
	public void doWork() {
		
		
		Properties		sysProps = System.getProperties();
		System.out.print(sysProps);
		System.out.println("\n\n-------------------------------------------\n\n");
		System.out.println("API Version = " + LocalDevice.getProperty("bluetooth.api.version"));
		System.out.println("\n\n-------------------------------------------\n\n");	
		sysProps = System.getProperties();
		System.out.print(sysProps);
		System.out.println("\n\n-------------------------------------------\n\n");
		try {
		LocalDevice.getLocalDevice().getDiscoveryAgent().startInquiry(DiscoveryAgent.GIAC,
                this);
		
		Thread.sleep(6000000);
		
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}
	public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod){
		System.out.println("deviceDiscovered:" + btDevice.toString());
		System.out.println("deviceDiscovered DeviceClass: " + cod.toString());
		/* search for RFCOMM */
		int[]			attrs = new int[]{
				0x0005, 0x0006, 7, 8, 9, 10, 11, 12, 13, 0x200, 0x300, 0x301, 0x302,
				0x303, 0x304, 0x305, 0x306, 0x307, 0x308, 0x30C, 0x30A, 0x309, 0x311,
				0x312, 0x313, 0x656e, 0x656f, 0x6570, 0x6672, 0x6673, 0x6674, 0x6573, 0x6574, 0x6575,
				0x7074, 0x7075, 0x7076};
		
		UUID[]			allKnown = new UUID[]{
				new UUID(0x0001), new UUID(0x0002), new UUID(0x0003), new UUID(0x0004),
				new UUID(0x0005), new UUID(0x0006), new UUID(0x0008), new UUID(0x0009),
				new UUID(0x000A), new UUID(0x000C), new UUID(0x000E), new UUID(0x000F),
				new UUID(0x0010), new UUID(0x0011), new UUID(0x0014), new UUID(0x0016),
				new UUID(0x0017), new UUID(0x0019), new UUID(0x001B), new UUID(0x001D),
				new UUID(0x0100)};
		try {
			
		
			LocalDevice.getLocalDevice().getDiscoveryAgent().searchServices(attrs,
				allKnown, btDevice, this);
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		
	}

	
	public void servicesDiscovered(int transID, ServiceRecord[] servRecord){
		
		if(servRecord != null) {
			ServiceRecord	aRecord;
			int				i, count;
			count = servRecord.length;
			for(i=0;i<count;i++) {
				System.out.println("\n\nService Discovered: \n\t" + servRecord[i].toString());
				
			}
		} else System.out.println("No services discovered");
		
	
	}

	
	public void serviceSearchCompleted(int transID, int respCode){
		System.out.println("serviceSearchCompleted");
		
	}
	public void inquiryCompleted(int discType){
		System.out.println("inquiryCompleted");
	
		
		
	}
	
}