

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;

import com.pi4j.component.lcd.LCDTextAlignment;
import com.pi4j.component.lcd.impl.GpioLcdDisplay;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class LcdExample {

	public final static int LCD_ROWS = 2;
	public final static int LCD_ROW_1 = 0;
	public final static int LCD_ROW_2 = 1;
	public final static int LCD_COLUMNS = 16;
	public final static int LCD_BITS = 4;
	public final static int MINIMUM_BUS_TIME=120;//Minimum timeToStation. Buses will be shown only if they are farther than this
	public final static int MAXIMUM_BUS_TIME=60*16;//Maximum timeToStation. BUses farther than this will not be shown
	private static BigDecimal previousEndTime = new BigDecimal(0);
	
	public static void main(String args[]) throws InterruptedException, Exception {

		final GpioController gpio = GpioFactory.getInstance();

		// initialize LCD
		final GpioLcdDisplay lcd = new GpioLcdDisplay(LCD_ROWS, // number of row
																// supported by
																// LCD
				LCD_COLUMNS, // number of columns supported by LCD
				RaspiPin.GPIO_11, // LCD RS pin
				RaspiPin.GPIO_10, // LCD strobe pin
				RaspiPin.GPIO_00, // LCD data bit 1
				RaspiPin.GPIO_01, // LCD data bit 2
				RaspiPin.GPIO_02, // LCD data bit 3
				RaspiPin.GPIO_03); // LCD data bit 4

		
		/*lcd.clear();
		TimeUnit.MILLISECONDS.sleep(1000);
		GpioPinDigitalOutput pin8 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_08, "MyLED", PinState.HIGH);
		TimeUnit.MILLISECONDS.sleep(1000);
		pin8.low();
		TimeUnit.MILLISECONDS.sleep(5000);
		pin8.high();*/
		
		
		
		GpioPinDigitalOutput rangefindertrigger = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_28, "Range Finder Trigger", PinState.LOW);
		 
		  GpioPinDigitalInput rangefinderresult = gpio.provisionDigitalInputPin(RaspiPin.GPIO_29, "Range Pulse Result", PinPullResistance.PULL_DOWN);
		 /* System.out.println(rangefinderresult.getState());
		  TimeUnit.MILLISECONDS.sleep(500);
		  rangefindertrigger.high();
		  TimeUnit.MICROSECONDS.sleep(10);
		  rangefindertrigger.low();
		  double startTime = System.nanoTime();
		  double echoStart = System.nanoTime();
		  double endTime = System.nanoTime();
		  do{
			  echoStart = System.nanoTime();
		  }while(rangefinderresult.getState()!=PinState.HIGH);
		  
		  while(rangefinderresult.getState()!=PinState.LOW){
			  endTime = System.nanoTime();
		  }
		  
		  System.out.println("StartTime::"+startTime);
		  System.out.println("Echo start time::"+echoStart);
		  System.out.println("Echo end time::"+endTime);
		  
		  double timeDiff = (endTime-echoStart)/1000000;
		  
		  System.out.println("Distance::"+timeDiff*165.7);
*/
		  int f=0;
		  while(f<0){
			  long distance = getDistance(rangefindertrigger,rangefinderresult);
			  lcd.clear();
			  lcd.write("Distance:"+distance);
			 // System.out.println("Distance::"+distance);
			  f++;
			  TimeUnit.MILLISECONDS.sleep(1);
		  }
		  
		 
		
		while (true) {
			Process p = Runtime.getRuntime().exec("sudo ./AdafruitDHT.py 11 4");
			BufferedReader buff = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String str = buff.readLine();
			//System.out.println("Temp and hum is:" + str);

			lcd.clear();
			lcd.write(str, LCD_ROW_1);
			//Get the stop id in the URL after hitting search at https://tfl.gov.uk/travel-information/stations-stops-and-piers/
			str = getBusString2("490005230S");
			//lcd.write(str.length() > 16 ? str.substring(0, 16) : str, LCD_ROW_2);
			displayScrollText(str,lcd,LCD_ROW_2);
			TimeUnit.SECONDS.sleep(30);
			lcd.clear();
		}
		// stop all GPIO activity/threads by shutting down the GPIO controller
		// (this method will forcefully shutdown all GPIO monitoring threads and
		// scheduled tasks)
		// gpio.shutdown(); <--- implement this method call if you wish to
		// terminate the Pi4J GPIO controller
		
		
		
		
	}
	
	
	public static long getDistance(GpioPinDigitalOutput rangefindertrigger, GpioPinDigitalInput  rangefinderresult)throws InterruptedException{
		//System.out.println(rangefinderresult.getState());
		rangefindertrigger.low();
		  TimeUnit.MILLISECONDS.sleep(500);
		  rangefindertrigger.high();
		  TimeUnit.MICROSECONDS.sleep(100);
		  //double startTime = System.nanoTime();
		  rangefindertrigger.low();
		  long echoStart = 0;
		  long endTime = echoStart;
		  BigDecimal callTime;
		  
		  do{
			  echoStart = System.nanoTime();
		  }while(rangefinderresult.getState()==PinState.LOW);
//		  echoStart = System.nanoTime();
		  endTime = System.nanoTime();
		  while(rangefinderresult.getState()==PinState.HIGH){
			  endTime = System.nanoTime();  
		  }
		  
		  
		 // System.out.println("StartTime::"+startTime);
		//  System.out.println("Echo start time::"+echoStart);
		 // System.out.println("Echo end time::"+endTime);
		  BigDecimal endTimeBD = new BigDecimal(endTime);
		  BigDecimal startTimeBD = new BigDecimal(echoStart);
		  BigDecimal nanoSecConversion = new BigDecimal(1000000000);
		 		  
		  //System.out.println("Distance::"+timeDiff*34000);
		  BigDecimal pulse = endTimeBD.subtract(startTimeBD);
		  pulse = pulse.divide(nanoSecConversion);
		  BigDecimal soundspeed = new BigDecimal(34000);
		  BigDecimal two = new BigDecimal(2);
		  
		  BigDecimal result = pulse.multiply(soundspeed);
		  result = result.divide(two);
		  callTime = startTimeBD.subtract(previousEndTime);
		  previousEndTime = endTimeBD;
		  
		  System.out.println("C:"+callTime+"S:"+echoStart+"::E:"+endTime+"::D:"+result);
		  return result.longValue();
	}

	public static void displayScrollText(String message, GpioLcdDisplay lcd, int lcdRow) {
		try {
			int length = message.length();
			int lcdLength = lcd.getColumnCount();
			lcd.write(lcdRow,message.substring(0,lcdLength<length?lcdLength:length));
			TimeUnit.SECONDS.sleep(3);
			for (int i = lcdLength; i <= length; i++) {
				String subStr = message.substring(i - lcdLength, i);
				// lcd.clear();
				lcd.write(lcdRow, subStr);
				Thread.sleep(400);
			}
			TimeUnit.SECONDS.sleep(1);
			lcd.write(lcdRow,message.substring(0,lcdLength<length?lcdLength:length));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
/*
	public static String getBusString1(String stopId) {
		StringBuffer returnStr = new StringBuffer();
		String jsonStr = getJsonStr(stopId);
		try{
		JSONObject json = new JSONObject(jsonStr);
		JSONArray jsonArray = json.getJSONArray("routes");

		String vehicleId = null;
		String routeName = null;
		Double timeToStation = null;
		// System.out.println(vehicleId+"::"+routeName+"::Expected in
		// ::"+timeToStation/60);
		TreeSet<BusTimeWrapper> uniqueBusSet = new TreeSet<BusTimeWrapper>();
		BusTimeWrapper busObject = null;
		int i = 0;
		while (i < jsonArray.length()) {
			vehicleId = jsonArray.getJSONObject(i).getString("vehicleId");
			routeName = jsonArray.getJSONObject(i).getString("lineName");
			timeToStation = jsonArray.getJSONObject(i).getDouble("timeToStation");
			// System.out.println(vehicleId+"::"+routeName+"::Expected in
			// ::"+timeToStation/60);
			if(timeToStation>MINIMUM_BUS_TIME){
				busObject = new BusTimeWrapper();
				busObject.setRouteName(routeName);
				busObject.setTimeToStation(timeToStation);
				busObject.setVehicleId(vehicleId);
				uniqueBusSet.add(busObject);
			}
			i++;
		}
		
		DecimalFormat df = new DecimalFormat("##");
		df.setRoundingMode(RoundingMode.FLOOR);
		for (Iterator iterator = uniqueBusSet.iterator(); iterator.hasNext();) {
			BusTimeWrapper busTimeWrapper = (BusTimeWrapper) iterator.next();
			returnStr.append(
					busTimeWrapper.getRouteName() + ":" + df.format(busTimeWrapper.getTimeToStation() / 60) + "||");
		}
		}catch(Exception e){
			System.out.println("EXCEPTION******************");
			System.out.println(jsonStr);
		}

		return returnStr.toString();

	}
	
	*/
	public static String getBusString2(String stopId) {
		StringBuffer returnStr = new StringBuffer();
		String jsonStr = getJsonStr(stopId);
		try{
		JSONObject json = new JSONObject(jsonStr);
		JSONArray jsonArray = json.getJSONArray("routes");

		String vehicleId = null;
		String routeName = null;
		Double timeToStation = null;
		// System.out.println(vehicleId+"::"+routeName+"::Expected in
		// ::"+timeToStation/60);
		HashMap<String,BusTimeWrapper> busMap = new HashMap<String,BusTimeWrapper>();
		TreeSet<BusTimeWrapper> uniqueBusSet = new TreeSet<BusTimeWrapper>();
		BusTimeWrapper busObject = null;
		int i = 0;
		while (i < jsonArray.length()) {
			vehicleId = jsonArray.getJSONObject(i).getString("vehicleId");
			routeName = jsonArray.getJSONObject(i).getString("lineName");
			timeToStation = jsonArray.getJSONObject(i).getDouble("timeToStation");
			TreeSet<Double> timeToStationSet = null;
			// System.out.println(vehicleId+"::"+routeName+"::Expected in
			// ::"+timeToStation/60);
			if(timeToStation>MINIMUM_BUS_TIME && timeToStation < MAXIMUM_BUS_TIME){
				if((busObject = busMap.get(routeName))==null){
					busObject = new BusTimeWrapper();
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
		uniqueBusSet=new TreeSet<BusTimeWrapper>(busMap.values());
		
		for (Iterator iterator = uniqueBusSet.iterator(); iterator.hasNext();) {
			BusTimeWrapper busTimeWrapper = (BusTimeWrapper) iterator.next();
			returnStr.append(
					busTimeWrapper.getRouteName() + ":" + getTimeToStationString(busTimeWrapper.getTimeToStationTreeSet()));
		}
		}catch(Exception e){
			System.out.println("EXCEPTION******************");
			System.out.println(jsonStr);
		}

		return returnStr.toString();

	}
	
	public static String getTimeToStationString(Set<Double> timeToStationSet){
		DecimalFormat df = new DecimalFormat("##");
		df.setRoundingMode(RoundingMode.FLOOR);
		String returnString = "";
		for (Iterator iterator = timeToStationSet.iterator(); iterator.hasNext();) {
			Double double1 = (Double) iterator.next();
			returnString=returnString+df.format(double1/60)+",";
			
		}
		return returnString;
		
	}

	public static String getJsonStr(String stopId) {
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

}