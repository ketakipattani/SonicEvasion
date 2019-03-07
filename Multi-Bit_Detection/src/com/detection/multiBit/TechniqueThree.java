package com.detection.multiBit;

import java.math.BigInteger;
import java.util.BitSet;

public class TechniqueThree {
	public static BigInteger X = new BigInteger("861645039139142");
	public static String binaryX = X.toString(2);
	public static String R = "";
	public static BitSet flag = new BitSet(binaryX.length());
	
	public static void main(String[] args) {
		startTechnique();
	}
	
	public static void startTechnique() {
		for (int i = 0; i < binaryX.length(); i++) {
			String timeZone = "American";
			
			if (binaryX.charAt(i) == '1') {
				timeZone = "Europian";
			}
			
			if (timeZone == "American") {
				R += "0";
				flag.set(i, true);
			} else {
				R += "1";
				flag.set(i, true);
			}
		}
		TechniqueThree.sendSMS(R);
	}
	
	public static void sendSMS(String R) {
		System.out.println("I am leaking the data : " + new BigInteger(R, 2));
	}
}