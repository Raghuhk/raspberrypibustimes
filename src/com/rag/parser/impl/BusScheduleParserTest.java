package com.rag.parser.impl;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.rag.data.BusDetail;

public class BusScheduleParserTest {
	
	@Test
	public void testParseResponseString(){
		String sampleResponse = "{routes:[{\"$type\":\"Tfl.Api.Presentation.Entities.Prediction, Tfl.Api.Presentation.Entities\",\"id\":\"-1075914231\",\"operationType\":1,\"vehicleId\":\"LK16DFD\",\"naptanId\":\"490005230S\",\"stationName\":\"Kenton Library\",\"lineId\":\"114\",\"lineName\":\"114\",\"platformName\":\"S\",\"direction\":\"outbound\",\"bearing\":\"154\",\"destinationNaptanId\":\"\",\"destinationName\":\"Ruislip\",\"timestamp\":\"2016-10-03T19:39:19Z\",\"timeToStation\":1594,\"currentLocation\":\"\",\"towards\":\"Kenton or Harrow\",\"expectedArrival\":\"2016-10-03T20:05:53.4091623Z\",\"timeToLive\":\"2016-10-03T20:06:23.4091623Z\",\"modeName\":\"bus\"},{\"$type\":\"Tfl.Api.Presentation.Entities.Prediction, Tfl.Api.Presentation.Entities\",\"id\":\"1966192181\",\"operationType\":1,\"vehicleId\":\"LK16DFP\",\"naptanId\":\"490005230S\",\"stationName\":\"Kenton Library\",\"lineId\":\"114\",\"lineName\":\"114\",\"platformName\":\"S\",\"direction\":\"outbound\",\"bearing\":\"154\",\"destinationNaptanId\":\"\",\"destinationName\":\"Ruislip\",\"timestamp\":\"2016-10-03T19:39:19Z\",\"timeToStation\":874,\"currentLocation\":\"\",\"towards\":\"Kenton or Harrow\",\"expectedArrival\":\"2016-10-03T19:53:53.4091623Z\",\"timeToLive\":\"2016-10-03T19:54:23.4091623Z\",\"modeName\":\"bus\"},{\"$type\":\"Tfl.Api.Presentation.Entities.Prediction, Tfl.Api.Presentation.Entities\",\"id\":\"1352339728\",\"operationType\":1,\"vehicleId\":\"LK16DGO\",\"naptanId\":\"490005230S\",\"stationName\":\"Kenton Library\",\"lineId\":\"114\",\"lineName\":\"114\",\"platformName\":\"S\",\"direction\":\"outbound\",\"bearing\":\"154\",\"destinationNaptanId\":\"\",\"destinationName\":\"Ruislip\",\"timestamp\":\"2016-10-03T19:39:19Z\",\"timeToStation\":348,\"currentLocation\":\"\",\"towards\":\"Kenton or Harrow\",\"expectedArrival\":\"2016-10-03T19:45:07.4091623Z\",\"timeToLive\":\"2016-10-03T19:45:37.4091623Z\",\"modeName\":\"bus\"},{\"$type\":\"Tfl.Api.Presentation.Entities.Prediction, Tfl.Api.Presentation.Entities\",\"id\":\"-1812555697\",\"operationType\":1,\"vehicleId\":\"YX09HKM\",\"naptanId\":\"490005230S\",\"stationName\":\"Kenton Library\",\"lineId\":\"h10\",\"lineName\":\"H10\",\"platformName\":\"S\",\"direction\":\"outbound\",\"bearing\":\"154\",\"destinationNaptanId\":\"\",\"destinationName\":\"Harrow\",\"timestamp\":\"2016-10-03T19:39:19Z\",\"timeToStation\":1350,\"currentLocation\":\"\",\"towards\":\"Kenton or Harrow\",\"expectedArrival\":\"2016-10-03T20:01:49.4091623Z\",\"timeToLive\":\"2016-10-03T20:02:19.4091623Z\",\"modeName\":\"bus\"},{\"$type\":\"Tfl.Api.Presentation.Entities.Prediction, Tfl.Api.Presentation.Entities\",\"id\":\"1757454619\",\"operationType\":1,\"vehicleId\":\"YX11FZR\",\"naptanId\":\"490005230S\",\"stationName\":\"Kenton Library\",\"lineId\":\"h10\",\"lineName\":\"H10\",\"platformName\":\"S\",\"direction\":\"outbound\",\"bearing\":\"154\",\"destinationNaptanId\":\"\",\"destinationName\":\"Harrow\",\"timestamp\":\"2016-10-03T19:39:19Z\",\"timeToStation\":500,\"currentLocation\":\"\",\"towards\":\"Kenton or Harrow\",\"expectedArrival\":\"2016-10-03T19:47:39.4091623Z\",\"timeToLive\":\"2016-10-03T19:48:09.4091623Z\",\"modeName\":\"bus\"}]}";
		Set<BusDetail> busDetails = new TFLBusScheduleParserImpl().parseBusSchedule(sampleResponse);
		Assert.assertEquals(new Integer(2), new Integer(busDetails.size()));
	}
	

}
