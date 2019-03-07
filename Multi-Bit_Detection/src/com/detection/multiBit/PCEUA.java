package com.detection.multiBit;

import java.math.BigInteger;
import java.util.Random;

public class PCEUA {
	
	
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
		System.out.println(binaryX+ " bin");
		int num = 0;
		for (int j = 0; j < binaryX.length(); j++) {
			
			int n = getRandomNumberInRange(3, 20);
			
			if (binaryX.charAt(j) == '1') {
				
				    int candidate, count;
				    for(candidate = 2, count = 0; count < n; ++candidate) {
				        if (isPrime(candidate)) {
				            ++count;
				        }
				    }
				    num=candidate-1;
				   // System.out.println("1: "+n+"th Prime: "+(num));
				    // The candidate has been incremented once after the count reached n
				   
			  }//if ends
			
			else if(binaryX.charAt(j) == '0') {
				 int candidate, count;
				    for(candidate = 2, count = 0; count < n; ++candidate) {
				        if (isComposite(candidate)) {
				            ++count;
				        }
				    }
				    num=candidate-1;
				    //System.out.println("0: "+n+"th Composite: "+(num));
				    // The candidate has been incremented once after the count reached n		
			
			}
		
		
			
			if (isPrime(num)) {
				R += "1";
				//flag.set(i, true);
			} else if (isComposite(num)){
				R += "0";
				//flag.set(i, true);
			}
		}
		System.out.println(R);
		R1=R.substring(0, R.length()/5);
		R2=R.substring(R.length()/5,(R.length()/5)*2);
		R3=R.substring((R.length()/5)*2,(R.length()/5)*3);
		R4=R.substring((R.length()/5)*3,(R.length()/5)*4);
		
		R5=R.substring((R.length()/5)*4,(R.length()/5)*5);
		
		
		PCEUA.sendSMS(R3);
		PCEUA.sendSMS(R2);
		PCEUA.sendSMS(R1);
		PCEUA.sendSMS(R5);
		PCEUA.sendSMS(R4);
		
	}
	
	private static boolean isPrime(int n) {
	    for(int i = 2; i < n; ++i) {
	        if (n % i == 0) {
	            // We are naive, but not stupid, if
	            // the number has a divisor other
	            // than 1 or itself, we return immediately.
	            return false;
	        }
	    }
	    return true;
	}
	
	private static boolean isComposite(int n) {
		
	        // Corner cases 
	        if (n <= 1)  
	        //System.out.println("False"); 
	          
	        if (n <= 3)  
	        //System.out.println("False"); 
	  
	        // This is checked so that we can skip  
	        // middle five numbers in below loop 
	        if (n % 2 == 0 || n % 3 == 0) return true; 
	  
	        for (int i = 5; i * i <= n; i = i + 6) {
	            if (n % i == 0 || n % (i + 2) == 0) 
	            return true; 
	        }
	        return false; 
	   
	}
	
	private static int getRandomNumberInRange(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

	public static void sendSMS(String R) {
		System.out.println("I am leaking the data : " + R);
	}
}