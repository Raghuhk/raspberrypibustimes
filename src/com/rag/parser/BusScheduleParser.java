package com.rag.parser;

import java.util.Set;

import com.rag.data.BusDetail;

public interface BusScheduleParser {
	public Set<BusDetail> parseBusSchedule(String busScheduleString);

}
