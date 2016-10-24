package com.rag.displayapp;

import org.junit.Test;

import com.rag.display.sysoutDisplay.SysoutDisplay;

public class BusTimeDisplayGeneratorTest {
	@Test
	public void testDisplayBusTimesOf(){
		BusTimeDisplayGenerator bustimeDisplayGenerator = new BusTimeDisplayGenerator();
		bustimeDisplayGenerator.setBusDisplay(new SysoutDisplay());
		bustimeDisplayGenerator.displayBusTimesOf("490008254S");
	}

}
