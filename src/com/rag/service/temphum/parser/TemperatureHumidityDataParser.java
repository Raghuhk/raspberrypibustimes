package com.rag.service.temphum.parser;

import com.rag.service.temphum.valueobject.TemperatureHumidityVO;

public class TemperatureHumidityDataParser {
	private static final String TEMPERATURE_HUMIDITY_SEPARATOR = ":::";
	public static TemperatureHumidityVO parseData(String inputString){
		TemperatureHumidityVO tempHumidData = null;
		if(validateInput(inputString)){
			int breakIndex = inputString.indexOf(TEMPERATURE_HUMIDITY_SEPARATOR);
			String temperatureString = inputString.substring(0, breakIndex);
			String humidityString= inputString.substring(breakIndex+TEMPERATURE_HUMIDITY_SEPARATOR.length());
			Double temperature=null;
			Double humidity=null;
			if(temperatureString!=null){
				temperature=Double.parseDouble(temperatureString);
			}
			if(humidityString!=null){
				humidity=Double.parseDouble(humidityString);
			}
			tempHumidData = new TemperatureHumidityVO(temperature,humidity);
		}
		return tempHumidData;
	}
	
	private static Boolean validateInput(String inputString){
		Boolean validData=true;
		if(inputString==null || inputString.trim().length()==0){
			validData=false;
		}
		return validData;
	}

}
