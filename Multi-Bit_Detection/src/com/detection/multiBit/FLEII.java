package com.detection.multiBit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FLEII {
	public static BigInteger X = new BigInteger("861645039139142");
	public static String binaryX = X.toString(2);
	public static String R = "";
	public static String R3 = "";
	public static String R4 = "";
	public static String R5 = "";
	public static String R2 = "";
	public static String R1 = "";	
	
	
	public static void main(String[] args) throws IOException {
		startTechnique();
	}
	
	public static void startTechnique() throws IOException {
		
		
		
		for (int i = 0; i < binaryX.length(); i++) {
			FileLock lock = null;
			File f = new File("src/x.txt");
			f.createNewFile();
			FileOutputStream os = null;
			FileChannel channel=null;
			
			
			if (binaryX.charAt(i) == '1') {
				
				 os = new FileOutputStream(f.getAbsolutePath(), true);
			     	channel = os.getChannel();
			        lock = channel.tryLock();
			       
				
				
			}
			else if (binaryX.charAt(i) == '0') {
				
				 os = new FileOutputStream(f.getAbsolutePath(), true);
			        channel = os.getChannel();
			        lock = channel.tryLock();
			        lock.release();
			        if(lock!=null && lock.isValid()) {
			        	lock.release();
			        }
			       
				//System.out.println("0 Lock acquired: " + lock.isValid());
				
			}
			
			if (lock.isValid()) {
				
				R += "1";
				lock.release();
				
			} else if(lock.isValid()==false){
				R += "0";
				
			}
			os.close();
			channel.close();
			if(f.delete()) 
	        { 
	            
	        } 
	        else
	        { 
	            System.out.println("Failed to delete the file"); 
	        } 
		}
		R1=R.substring(0, R.length()/5);

		R2=R.substring(R.length()/5,(R.length()/5)*2);
		R3=R.substring((R.length()/5)*2,(R.length()/5)*3);
		R4=R.substring((R.length()/5)*3,(R.length()/5)*4);
		
		R5=R.substring((R.length()/5)*4,(R.length()/5)*5);
		
		
		
		FLEII.sendSMS(R1);
		FLEII.sendSMS(R2);
		FLEII.sendSMS(R3);
		FLEII.sendSMS(R4);
		FLEII.sendSMS(R5);
			}
	
	public static void sendSMS(String R) {
		System.out.println("I am leaking the data : " + R);
	}
}
