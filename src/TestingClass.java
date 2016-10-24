import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Iterator;
import java.util.TreeSet;

import org.json.JSONArray;
import org.json.JSONObject;

import com.pi4j.component.lcd.impl.GpioLcdDisplay;

public class TestingClass {
public static void main(String []s){
	
	
	
}

public static String getBusString1(){
	//displayScrollText("This is a long text to be displayed in a scrolling view", null);
		//String jsonStr = "{routes:[{\"$type\":\"Tfl.Api.Presentation.Entities.Prediction, Tfl.Api.Presentation.Entities\",\"id\":\"-1638915672\",\"operationType\":1,\"vehicleId\":\"PA04CYK\",\"naptanId\":\"490005230S\",\"stationName\":\"Kenton Library\",\"lineId\":\"114\",\"lineName\":\"114\",\"platformName\":\"S\",\"direction\":\"outbound\",\"bearing\":\"154\",\"destinationNaptanId\":\"\",\"destinationName\":\"Ruislip\",\"timestamp\":\"2016-05-15T14:22:42.483Z\",\"timeToStation\":40,\"currentLocation\":\"\",\"towards\":\"Kenton or Harrow\",\"expectedArrival\":\"2016-05-15T14:23:23Z\",\"timeToLive\":\"2016-05-15T14:23:53Z\",\"modeName\":\"bus\"},{\"$type\":\"Tfl.Api.Presentation.Entities.Prediction, Tfl.Api.Presentation.Entities\",\"id\":\"221078966\",\"operationType\":1,\"vehicleId\":\"PO54ACF\",\"naptanId\":\"490005230S\",\"stationName\":\"Kenton Library\",\"lineId\":\"114\",\"lineName\":\"114\",\"platformName\":\"S\",\"direction\":\"outbound\",\"bearing\":\"154\",\"destinationNaptanId\":\"\",\"destinationName\":\"Field End Road, Victoria Road\",\"timestamp\":\"2016-05-15T14:23:50.003Z\",\"timeToStation\":578,\"currentLocation\":\"\",\"towards\":\"Kenton or Harrow\",\"expectedArrival\":\"2016-05-15T14:33:29Z\",\"timeToLive\":\"2016-05-15T14:33:59Z\",\"modeName\":\"bus\"},{\"$type\":\"Tfl.Api.Presentation.Entities.Prediction, Tfl.Api.Presentation.Entities\",\"id\":\"1348373624\",\"operationType\":1,\"vehicleId\":\"PO54ACX\",\"naptanId\":\"490005230S\",\"stationName\":\"Kenton Library\",\"lineId\":\"114\",\"lineName\":\"114\",\"platformName\":\"S\",\"direction\":\"outbound\",\"bearing\":\"154\",\"destinationNaptanId\":\"\",\"destinationName\":\"Ruislip\",\"timestamp\":\"2016-05-15T14:23:39.591Z\",\"timeToStation\":1450,\"currentLocation\":\"\",\"towards\":\"Kenton or Harrow\",\"expectedArrival\":\"2016-05-15T14:47:50Z\",\"timeToLive\":\"2016-05-15T14:48:20Z\",\"modeName\":\"bus\"},{\"$type\":\"Tfl.Api.Presentation.Entities.Prediction, Tfl.Api.Presentation.Entities\",\"id\":\"1466243119\",\"operationType\":1,\"vehicleId\":\"YX59BYF\",\"naptanId\":\"490005230S\",\"stationName\":\"Kenton Library\",\"lineId\":\"h10\",\"lineName\":\"H10\",\"platformName\":\"S\",\"direction\":\"outbound\",\"bearing\":\"154\",\"destinationNaptanId\":\"\",\"destinationName\":\"Harrow\",\"timestamp\":\"2016-05-15T14:24:10.019Z\",\"timeToStation\":925,\"currentLocation\":\"\",\"towards\":\"Kenton or Harrow\",\"expectedArrival\":\"2016-05-15T14:39:36Z\",\"timeToLive\":\"2016-05-15T14:40:06Z\",\"modeName\":\"bus\"}]}";
		String jsonStr = getJsonStr();
		JSONObject json = new JSONObject(jsonStr);
		JSONArray jsonArray = json.getJSONArray("routes");
		
		
		String vehicleId= jsonArray.getJSONObject(0).getString("vehicleId");
		String routeName= jsonArray.getJSONObject(0).getString("lineName");
		Double timeToStation = jsonArray.getJSONObject(0).getDouble("timeToStation");
		//System.out.println(vehicleId+"::"+routeName+"::Expected in ::"+timeToStation/60);
		TreeSet<BusTimeWrapper> uniqueBusList = new TreeSet<BusTimeWrapper>();
		BusTimeWrapper bus = null;
		int i=0;
		while(i<jsonArray.length()){
			vehicleId= jsonArray.getJSONObject(i).getString("vehicleId");
			routeName= jsonArray.getJSONObject(i).getString("lineName");
			timeToStation = jsonArray.getJSONObject(i).getDouble("timeToStation");
			//System.out.println(vehicleId+"::"+routeName+"::Expected in ::"+timeToStation/60);
			bus= new BusTimeWrapper();
			bus.setRouteName(routeName);
			//bus.setTimeToStation(timeToStation);
			bus.setVehicleId(vehicleId);
			uniqueBusList.add(bus);
			i++;
		}
		StringBuffer returnStr=new StringBuffer();
		for (Iterator iterator = uniqueBusList.iterator(); iterator.hasNext();) {
			BusTimeWrapper busTimeWrapper = (BusTimeWrapper) iterator.next();
			returnStr.append(busTimeWrapper.getRouteName()+":"+busTimeWrapper.getTimeToStation()/60+"||");
		}
		
		return returnStr.toString();
	
}

public static String getJsonStr(){
	StringBuffer jsonStr = new StringBuffer();
	jsonStr.append("{routes:");
	try{
		URL url = new URL("https://api.tfl.gov.uk/StopPoint/490005230S/arrivals");
		BufferedReader buff = new BufferedReader(new InputStreamReader(url.openStream()));
		jsonStr.append(buff.readLine());
		jsonStr.append("}");
	}catch(Exception e){
		e.printStackTrace();
	}
	System.out.println(jsonStr.toString());
	return jsonStr.toString();
}

public static void displayScrollText(String message, GpioLcdDisplay lcd){
	try{
	int length = message.length();
	int lcdLength = 8;
	for(int i=lcdLength;i<=length;i++){
		String subStr = message.substring(i-lcdLength, i);
		//lcd.write(subStr);
		System.out.println(subStr);
		Thread.sleep(300);
	}
	}catch(Exception e){
		e.printStackTrace();
	}
}
}
