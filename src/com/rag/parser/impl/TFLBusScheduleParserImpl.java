package com.rag.parser.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.json.JSONArray;
import org.json.JSONObject;

import com.rag.data.BusDetail;
import com.rag.parser.BusScheduleParser;

public class TFLBusScheduleParserImpl implements BusScheduleParser{

	public final static int MINIMUM_BUS_TIME_SECONDS=1*60;//Minimum timeToStation. Buses will be shown only if they are farther than this
	public final static int MAXIMUM_BUS_TIME_SECONDS=30*60;//Maximum timeToStation. BUses farther than this will not be shown
	
	@Override
	public Set<BusDetail> parseBusSchedule(String busScheduleString) {
		Set<BusDetail> busDetails = new HashSet<BusDetail>();
		String vehicleId = null;
		String routeName = null;
		Double timeToStation = null;
		HashMap<String,BusDetail> busMap = new HashMap<String,BusDetail>();
		
		BusDetail busObject = null;
		int i = 0;
		try{
			JSONObject json = new JSONObject(busScheduleString);
			JSONArray jsonArray = json.getJSONArray("routes");

			
			while (i < jsonArray.length()) {
				vehicleId = jsonArray.getJSONObject(i).getString("vehicleId");
				routeName = jsonArray.getJSONObject(i).getString("lineName");
				timeToStation = jsonArray.getJSONObject(i).getDouble("timeToStation");
				TreeSet<Double> timeToStationSet = null;
				// System.out.println(vehicleId+"::"+routeName+"::Expected in
				// ::"+timeToStation/60);
				if(timeToStation>MINIMUM_BUS_TIME_SECONDS && timeToStation < MAXIMUM_BUS_TIME_SECONDS){
					if((busObject = busMap.get(routeName))==null){
						busObject = new BusDetail();
						busObject.setRouteName(routeName);
						busObject.setVehicleId(vehicleId);
						timeToStationSet = new TreeSet<Double>();
						busObject.setTimeToStationTreeSet(timeToStationSet);
						busMap.put(routeName, busObject);
					}
					else{
						timeToStationSet=busObject.getTimeToStationTreeSet();
					}
					timeToStationSet.add(timeToStation);
				}
				i++;
			}
			busDetails=new TreeSet<BusDetail>(busMap.values());
		}catch(Exception e){
			System.out.println("EXCEPTION******************");
		}
		return busDetails;
	}

}
