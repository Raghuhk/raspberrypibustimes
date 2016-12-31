package com.rag.service.temphum;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.rag.service.temphum.dao.TemperatureHumidityDao;
import com.rag.service.temphum.parser.TemperatureHumidityDataParser;
import com.rag.service.temphum.valueobject.TemperatureHumidityVO;

public class TemperatureHumidityMonitor {
	public Boolean readAndPersitTemperatureAndHumidity(){
		TemperatureHumidityVO tempHumidDetail = readTemperatureHumidity();
		Boolean success = false;
		System.out.println(tempHumidDetail);
		if(tempHumidDetail!=null){
			success=	persistTemperatureHumidity(tempHumidDetail);
		}
		return success;
	}

	private Boolean persistTemperatureHumidity(TemperatureHumidityVO tempHumidDetail) {
		TemperatureHumidityDao dao = new TemperatureHumidityDao();
		return dao.writeToDB(tempHumidDetail);
		
	}

	private TemperatureHumidityVO readTemperatureHumidity() {
		TemperatureHumidityVO tempHumidDetail = null;
		try{
			
				Process p = Runtime.getRuntime().exec("sudo ./AdafruitDHT.py 11 4");
				BufferedReader buff = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String str = buff.readLine();
				tempHumidDetail = TemperatureHumidityDataParser.parseData(str);
				
			
			}catch(Exception e){
				System.out.println(e);
			}
		return tempHumidDetail;
	}

}
