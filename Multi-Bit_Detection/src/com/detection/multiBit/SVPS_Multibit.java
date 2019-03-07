package com.detection.multiBit;

import java.math.BigInteger;
import java.util.BitSet;

public class SVPS_Multibit {
	
	public static BigInteger X = new BigInteger("861645039139142");
	public static String binaryX = X.toString(2);
	//public static String binaryY = new String("11111111");
	//public static BitSet flag = new BitSet(binaryX.length());
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
		System.out.println(R.length());
		R1=R.substring(0, R.length()/5);
		R2=R.substring(R.length()/5,(R.length()/5)*2);
		R3=R.substring((R.length()/5)*2,(R.length()/5)*3);
		R4=R.substring((R.length()/5)*3,(R.length()/5)*4);
		
		R5=R.substring((R.length()/5)*4,(R.length()/5)*5);
		
		
		SVPS_Multibit.sendSMS(R3);
		SVPS_Multibit.sendSMS(R2);
		SVPS_Multibit.sendSMS(R1);
		SVPS_Multibit.sendSMS(R5);
		SVPS_Multibit.sendSMS(R4);
		
	}
	
	public static void sendSMS(String R) {
		System.out.println("I am leaking the data : " + R);
	}
}