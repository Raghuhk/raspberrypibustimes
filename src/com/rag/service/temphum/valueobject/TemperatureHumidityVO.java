package com.rag.service.temphum.valueobject;

import java.util.Date;

public class TemperatureHumidityVO {
	private Date time;
	private Double temperature;
	private Double humidity;
	public TemperatureHumidityVO(Double temperature, Double humidity) {
		this.temperature=temperature;
		this.humidity=humidity;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public Double getTemperature() {
		return temperature;
	}
	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}
	public Double getHumidity() {
		return humidity;
	}
	public void setHumidity(Double humidity) {
		this.humidity = humidity;
	}
	@Override
	public String toString() {
		return "TemperatureHumidityVO [time=" + time + ", temperature=" + temperature + ", humidity=" + humidity + "]";
	}
	
	

}
