package com.rag.display.lcdDisplay;

import com.pi4j.component.lcd.impl.GpioLcdDisplay;
import com.rag.display.GenericDisplay;

public class LcdDisplay implements GenericDisplay {

	private GpioLcdDisplay gpioLcdDisplay;
	private Integer lcdRow;	
	
	
	public LcdDisplay(GpioLcdDisplay gpioLcdDisplay, Integer lcdRow){
		this.gpioLcdDisplay=gpioLcdDisplay;
		this.lcdRow = lcdRow;
	}
	@Override
	public void displayText(String displayText) {
		gpioLcdDisplay.write(displayText,lcdRow);

	}
	
	@Override
	public void clearDisplay(){
		gpioLcdDisplay.clear();
	}

}
