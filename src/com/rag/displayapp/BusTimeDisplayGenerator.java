package com.rag.displayapp;


import com.rag.display.GenericDisplay;
import com.rag.service.BusScheduleFetcher;

public class BusTimeDisplayGenerator {
	private GenericDisplay busDisplay;
	private BusScheduleFetcher busScheduleFetcher;
	
	
	
	public BusScheduleFetcher getBusScheduleFetcher() {
		return busScheduleFetcher;
	}

	public void setBusScheduleFetcher(BusScheduleFetcher busScheduleFetcher) {
		this.busScheduleFetcher = busScheduleFetcher;
	}

	public GenericDisplay getBusDisplay() {
		return busDisplay;
	}

	public void setBusDisplay(GenericDisplay busDisplay) {
		this.busDisplay = busDisplay;
	}
	

	public void displayBusTimesOf(String stopId){
		String busScheduleString = busScheduleFetcher.fetchBusScheduleString(stopId);
		//busDisplay.clearDisplay();
		busDisplay.displayText(busScheduleString);

	}

}
