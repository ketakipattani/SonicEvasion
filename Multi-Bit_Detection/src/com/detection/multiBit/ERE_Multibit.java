package com.detection.multiBit;

import java.math.BigInteger;
import java.util.BitSet;

public class ERE_Multibit {
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
		int p = 1;
		for (int i = 0; i < binaryX.length(); i++) {
			try {
				p /= Character.getNumericValue(binaryX.charAt(i));
				R += "1";
				
			} catch (Exception e) {
				R += "0";
				
				p = 1;
			}
		}
		R1=R.substring(0, R.length()/5);
		R2=R.substring(R.length()/5,(R.length()/5)*2);
		R3=R.substring((R.length()/5)*2,(R.length()/5)*3);
		R4=R.substring((R.length()/5)*3,(R.length()/5)*4);
		
		R5=R.substring((R.length()/5)*4,(R.length()/5)*5);
		
		
		ERE_Multibit.sendSMS(R3);
		ERE_Multibit.sendSMS(R2);
		ERE_Multibit.sendSMS(R1);
		ERE_Multibit.sendSMS(R5);
		ERE_Multibit.sendSMS(R4);
	}
	
	public static void sendSMS(String R) {
		System.out.println("I am leaking the data : " + new BigInteger(R, 2));
	}
}