package com.detection.multiBit;

import java.math.BigInteger;
import java.util.BitSet;

public class TechniqueTwo {
	public static BigInteger X = new BigInteger("861645039139142");
	public static String binaryX = X.toString(2);
	public static String R = "";
	public static BitSet flag = new BitSet(binaryX.length());
	
	public static void main(String[] args) {
		startTechnique();
	}
	
	public static void startTechnique() {
		int p = 1;
		for (int i = 0; i < binaryX.length(); i++) {
			try {
				p /= Character.getNumericValue(binaryX.charAt(i));
				R += "1";
				flag.set(i, true);
			} catch (Exception e) {
				R += "0";
				flag.set(i, true);
				p = 1;
			}
		}
		TechniqueTwo.sendSMS(R);
	}
	
	public static void sendSMS(String R) {
		System.out.println("I am leaking the data : " + new BigInteger(R, 2));
	}
}