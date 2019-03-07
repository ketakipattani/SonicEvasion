package com.detection.multiBit;

import java.math.BigInteger;
import java.util.BitSet;

public class TechniqueOne {
	
	public static BigInteger X = new BigInteger("861645039139142");
	public static String binaryX = X.toString(2);
	public static String R = "";	
		
	
	
	
	public static void main(String[] args) {
		startTechnique();
	}
	
	public static void startTechnique() {
		//int length = binaryX.length();
		
		
		for (int i = 0; i < binaryX.length(); i++) {
			int timeFormat = 12;
			
			if (binaryX.charAt(i) == '1') {
				timeFormat = 24;
			}
			
			if (timeFormat == 24) {
				R += "1";
				//flag.set(i, true);
			} else {
				R += "0";
				//flag.set(i, true);
			}
		}
			
		TechniqueOne.sendSMS(R);
		
	}
	
	public static void sendSMS(String R) {
		System.out.println("I am leaking the data : " + R);
	}
}