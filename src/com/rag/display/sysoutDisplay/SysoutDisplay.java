package com.rag.display.sysoutDisplay;

import com.rag.display.GenericDisplay;

public class SysoutDisplay implements GenericDisplay {

	@Override
	public void displayText(String displayText) {
		System.out.println(displayText);
		
	}

	@Override
	public void clearDisplay() {
		// TODO Auto-generated method stub
		
	}

}
