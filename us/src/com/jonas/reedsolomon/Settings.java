package com.jonas.reedsolomon;

public interface Settings {
	/*
	 * Below is kParityBytes, the only parameter you should have to
	 * modify.
	 *	  
	 * It is the number of parity bytes that will be appended to
	 * your data to create a codeword.
	 * 
	 * In general, with E errors, and K erasures, you will need
	 * 2E + K bytes of parity to be able to correct the codeword
	 * back to recover the original message data.
	 *
	 * You could say that each error 'consumes' two bytes of the parity,
	 * whereas each erasure 'consumes' one byte.
	 *	
	 * Note that the maximum codeword size is 255, so the
	 * sum of your message length plus parity should be less than
	 * or equal to this maximum limit.
	 * 
	 * In practice, you will get slow error correction and decoding
	 * if you use more than a reasonably small number of parity bytes.
	 * (say, 10 or 20)
	 */
	public static final int kParityBytes = 4;
	/* maximum degree of various polynomials */
	public static final int kMaxDeg = (kParityBytes * 2);
	/* print debugging info */
	public static final boolean kDebug = false;
}
