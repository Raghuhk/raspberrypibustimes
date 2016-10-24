package com.rag.service;
import com.rag.parser.BusScheduleParser;

public interface BusScheduleFetcher {
	
	public BusScheduleParser getBusScheduleParser();

	public void setBusScheduleParser(BusScheduleParser busScheduleParser);

	public String fetchBusScheduleString(String stopId);
	
	
}
