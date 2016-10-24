package com.rag.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Set;

import com.rag.data.BusDetail;
import com.rag.parser.BusScheduleParser;
import com.rag.service.BusScheduleFetcher;

public class BusScheduleFetcherImpl implements BusScheduleFetcher {
	public final static int MINIMUM_BUS_TIME_SECONDS=1*60;//Minimum timeToStation. Buses will be shown only if they are farther than this
	public final static int MAXIMUM_BUS_TIME_SECONDS=30*60;//Maximum timeToStation. BUses farther than this will not be shown
	
	private BusScheduleParser busScheduleParser;
	
	public BusScheduleParser getBusScheduleParser() {
		return busScheduleParser;
	}

	public void setBusScheduleParser(BusScheduleParser busScheduleParser) {
		this.busScheduleParser = busScheduleParser;
	}

	public String fetchBusScheduleString(String stopId){
		String responseFromTFL=fetchBusScheduleFromTFL(stopId);
		Set<BusDetail> busDetails = busScheduleParser.parseBusSchedule(responseFromTFL);
		String busDetailsString =convertBusDetailsToString(busDetails);
		return busDetailsString;
		
	}
	
	public String fetchBusScheduleFromTFL(String stopId){		
		StringBuffer jsonStr = new StringBuffer();
		jsonStr.append("{routes:");
		try {
			URL url = new URL("https://api.tfl.gov.uk/StopPoint/"+stopId+"/arrivals");
			BufferedReader buff = new BufferedReader(new InputStreamReader(url.openStream()));
			jsonStr.append(buff.readLine());
			jsonStr.append("}");
		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.println(jsonStr.toString());
		return jsonStr.toString();
	}
	
	public String convertBusDetailsToString(Set<BusDetail> busDetails){
		StringBuffer busDetailsStringBuff = new StringBuffer();
		for (BusDetail busDetail:busDetails) {
			busDetailsStringBuff.append(
					busDetail.getRouteName() + ":" + getTimeToStationString(busDetail.getTimeToStationTreeSet()));
		}
		return busDetailsStringBuff.toString();
	}
	
	public String getTimeToStationString(Set<Double> timeToStationSet){
		DecimalFormat df = new DecimalFormat("##");
		df.setRoundingMode(RoundingMode.FLOOR);
		String returnString = "";
		for (Double timeToStation:timeToStationSet) {
			returnString=returnString+df.format(timeToStation/60)+",";
			
		}
		return returnString;
		
	}


}
