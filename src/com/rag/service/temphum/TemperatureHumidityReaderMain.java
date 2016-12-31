package com.rag.service.temphum;

import java.util.concurrent.TimeUnit;

public class TemperatureHumidityReaderMain {
	public static void main(String []s){
		TemperatureHumidityMonitor monitor = new TemperatureHumidityMonitor();
		while(true){
			monitor.readAndPersitTemperatureAndHumidity();
			try{
			TimeUnit.MINUTES.sleep(5);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

}
