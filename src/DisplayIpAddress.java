import java.io.IOException;
import java.net.Inet4Address;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.pi4j.component.lcd.impl.GpioLcdDisplay;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.system.NetworkInfo;

public class DisplayIpAddress {
	public final static int LCD_ROWS = 2;
	public final static int LCD_ROW_1 = 0;
	public final static int LCD_ROW_2 = 1;
	public final static int LCD_COLUMNS = 16;
	public final static int LCD_BITS = 4;
	public static void main(String []s){
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
		try{
			for (String ipAddress : NetworkInfo.getIPAddresses())
	            lcd.write(ipAddress);
			//lcd.write(hostAddress);
		}catch(InterruptedException uhe){
			lcd.write("Exception");
		}catch(IOException ioe){
			System.out.println("socket exception");
		}
	}
}
