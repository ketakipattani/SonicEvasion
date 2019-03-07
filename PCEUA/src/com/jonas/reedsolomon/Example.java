package com.jonas.reedsolomon;


public class Example implements Settings {
	static String msg = "The fat cat in the hat sat on a rat.";
	static byte codeword[] = new byte[256];

	/* Some debugging routines to introduce errors or erasures into a codeword. */

	/* Introduce a byte error at LOC */
	static void byte_err(int err, int loc, byte[] dst) {
		System.out.println("Adding Error at loc " + loc + ", data 0x"
				+ Integer.toHexString(dst[loc - 1]));
		dst[loc - 1] ^= err;
	}

	/*
	 * Pass in location of error (first byte position is labeled starting at 1,
	 * not 0), and the codeword.
	 */
	static void byte_erasure(int loc, byte dst[]) {
		System.out.println("Erasure at loc " + loc + ", data 0x"
				+ Integer.toHexString(dst[loc - 1]));
		dst[loc - 1] = 0;
	}
	
	/*
	 * Trim off excess zeros and parity bytes.
	 */
	static byte[] rtrim(byte[] bytes) {
		int t = bytes.length - 1;
		while (bytes[t] == 0)
			t -= 1;
		byte[] trimmed = new byte[(t+1) - Settings.kParityBytes];
		for (int i = 0; i < trimmed.length; i++) {
			trimmed[i] = bytes[i];
		}
		return trimmed;
	}

	public static void main(String[] args) {
		int erasures[] = new int[16];
		int nerasures = 0;

		/* Initialization the ECC library */

		RS.initialize_ecc();

		/* ************** */

		/* Encode data into codeword, adding kParityBytes parity bytes */
		RS.encode_data(msg.getBytes(), msg.length(), codeword);

		System.out.println("Encoded data is: " + new String(rtrim(codeword)));

		int ML = msg.length() + Settings.kParityBytes;

		/* Add one error and two erasures */
		byte_err(0x35, 3, codeword);

		byte_erasure(17, codeword);
		byte_erasure(19, codeword);

		System.out.println("With some errors: " + new String(rtrim(codeword)));

		/*
		 * We need to indicate the position of the erasures. Erasure positions
		 * are indexed (1 based) from the end of the message...
		 */

		erasures[nerasures++] = ML - 17;
		erasures[nerasures++] = ML - 19;

		/* Now decode -- encoded codeword size must be passed */
		RS.decode_data(codeword, ML);

		/* check if syndrome is all zeros */
		if (RS.check_syndrome() != 0) {
			Berlekamp.correct_errors_erasures(codeword, ML, nerasures, erasures);

			System.out.println("Corrected codeword: " + new String(rtrim(codeword)));
		}
	}
}
