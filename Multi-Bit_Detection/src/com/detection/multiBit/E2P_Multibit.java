package com.detection.multiBit;

import java.math.BigInteger;
import java.util.BitSet;

public class E2P_Multibit {
	public static BigInteger X = new BigInteger("861645039139142");
	public static String binaryX = X.toString(2);
	public static String R = "";
	public static String R3 = "";
	public static String R4 = "";
	public static String R5 = "";
	public static String R2 = "";
	public static String R1 = "";	
	
	
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
				
			} else {
				R += "1";
				
			}
		}
		R1=R.substring(0, R.length()/5);
		R2=R.substring(R.length()/5,(R.length()/5)*2);
		R3=R.substring((R.length()/5)*2,(R.length()/5)*3);
		R4=R.substring((R.length()/5)*3,(R.length()/5)*4);
		
		R5=R.substring((R.length()/5)*4,(R.length()/5)*5);
		
		
		E2P_Multibit.sendSMS(R3);
		E2P_Multibit.sendSMS(R2);
		E2P_Multibit.sendSMS(R1);
		E2P_Multibit.sendSMS(R5);
		E2P_Multibit.sendSMS(R4);
	}
	
	public static void sendSMS(String R) {
		System.out.println("I am leaking the data : " + R);
	}
}