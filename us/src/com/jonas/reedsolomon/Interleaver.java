package com.jonas.reedsolomon;

public class Interleaver {
	public static final int kSkipFactor = 4;

	public static void interleave(byte[] items) {
		int jump = (int) Math.ceil(items.length / kSkipFactor);
		byte[] result = new byte[items.length];
		int k = 0;
		for (int i = 0; i < jump; ++i) {
			for (int j = i; j < items.length; j += jump) {
				result[k++] = items[j];
			}
		}
		for (int i=0; i<result.length; i++)
			items[i] = result[i];
	}
	
	public static void deinterleave(byte[] items) {
		int jump = (int) Math.ceil(items.length / kSkipFactor);
		byte[] result = new byte[items.length];
		int k = 0;
		for (int i = 0; i < jump; ++i) {
			for (int j = i; j < items.length; j += jump) {
				result[j] = items[k++];
			}
		}
		for (int i=0; i<result.length; i++)
			items[i] = result[i];
	}
}
