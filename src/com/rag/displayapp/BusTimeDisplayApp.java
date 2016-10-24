package com.rag.displayapp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import com.pi4j.component.lcd.impl.GpioLcdDisplay;
import com.pi4j.io.gpio.RaspiPin;
import com.rag.display.GenericDisplay;
import com.rag.display.lcdDisplay.LcdDisplay;
import com.rag.display.sysoutDisplay.SysoutDisplay;
import com.rag.parser.BusScheduleParser;
import com.rag.parser.impl.TFLBusScheduleParserImpl;
import com.rag.service.BusScheduleFetcher;
import com.rag.service.impl.BusScheduleFetcherImpl;

public class BusTimeDisplayApp {
	public final static int LCD_ROWS = 2;
	public final static int LCD_ROW_1 = 0;
	public final static int LCD_ROW_2 = 1;
	public final static int LCD_COLUMNS = 16;

	

	public static void main(String[] s) {
		BusTimeDisplayGenerator busTimeDisplayGenerator = new BusTimeDisplayGenerator();
		BusScheduleFetcher busScheduleFetcher = new BusScheduleFetcherImpl();
		BusScheduleParser busScheduleParser = new TFLBusScheduleParserImpl();
		busScheduleFetcher.setBusScheduleParser(busScheduleParser);
		busTimeDisplayGenerator.setBusScheduleFetcher(busScheduleFetcher);
		GenericDisplay display = null;
		//display=new LcdDisplay(lcd, LCD_ROW_2);
		display = new SysoutDisplay();
		try{
		while (true) {
			Process p = Runtime.getRuntime().exec("sudo ./AdafruitDHT.py 11 4");
			BufferedReader buff = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String str = buff.readLine();
			//lcd.clear();
			//lcd.write(str, LCD_ROW_1);
			busTimeDisplayGenerator.setBusDisplay(display);
			busTimeDisplayGenerator.displayBusTimesOf("490008254S");
			TimeUnit.SECONDS.sleep(30);
			//lcd.clear();
		}
		}catch(Exception e){
			System.out.println(e);
		}
		

	}

}
