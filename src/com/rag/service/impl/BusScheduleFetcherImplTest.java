package com.rag.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.rag.data.BusDetail;
import com.rag.parser.BusScheduleParser;
import com.rag.parser.impl.TFLBusScheduleParserImpl;
import com.rag.service.BusScheduleFetcher;



public class BusScheduleFetcherImplTest {
	private static final String HINDES_ROAD_STOP_ID = "490008254S";
	@Test
	public void testFetchBusScheduleFromTFL(){		
		String response = new BusScheduleFetcherImpl().fetchBusScheduleFromTFL(HINDES_ROAD_STOP_ID);
		Assert.assertNotEquals("", response);
		Assert.assertNotNull(response);
		System.out.println(response);
	}
	
	@Test
	public void testFetchBusScheduleString(){ 
		BusScheduleFetcher busScheduleFetcher = new BusScheduleFetcherImpl();
		BusScheduleParser busScheduleParser = new TFLBusScheduleParserImpl();
		busScheduleFetcher.setBusScheduleParser(busScheduleParser);
		busScheduleFetcher.setBusScheduleParser(busScheduleParser);
		String response = busScheduleFetcher.fetchBusScheduleString(HINDES_ROAD_STOP_ID);
		Assert.assertNotEquals("", response);
		Assert.assertNotNull(response);
		Boolean stringContains = response.contains("Tfl.Api.Presentation.Entities");
		Assert.assertEquals(true, stringContains);
	}
	
	
	@Test
	public void testConvertBusDetailsToString(){
		Set<BusDetail> busDetails = generateSampleBusDetails();
		String expectedString = "";
		String busDetailsString  = new BusScheduleFetcherImpl().convertBusDetailsToString(busDetails);
		Assert.assertEquals(busDetailsString, expectedString);
		
	}
	
	private Set<BusDetail> generateSampleBusDetails(){
		Set<BusDetail> sampleBusDetails = new HashSet<BusDetail>();
		return sampleBusDetails;
	}

}
