package com.jonas.reedsolomon;
public class CRCGen implements Settings {

	
	public static int crc_16_ccitt(byte[] msg, int len) {
		int crc = 0xFFFF; // initial value
		int polynomial = 0x1021; // 0001 0000 0010 0001 (0, 5, 12)

		boolean bit, c15;
		for (int b = 0; b < len; b++) {
			for (int i = 0; i < 8; i++) {
				bit = ((msg[b] >> (7 - i) & 1) == 1);
				c15 = ((crc >> 15 & 1) == 1);
				crc <<= 1;
				if (c15 ^ bit)
					crc ^= polynomial;
			}
		}
		crc &= 0xffff;
		
		return crc;
	}
	
	/* Computes the CRC-8-CCITT checksum on array of byte data, length len */
	public static byte crc_8_ccitt(byte[] msg, int len) {
		int crc = (byte) 0xFF; // initial value
		int polynomial = 0x07; // (0, 1, 2) : 0x07 / 0xE0 / 0x83

		boolean bit, c7;
		for (int b = 0; b < len; b++) {
			for (int i = 0; i < 8; i++) {
				bit = ((msg[b] >> (7 - i) & 1) == 1);
				c7 = ((crc >> 7 & 1) == 1);
				crc <<= 1;
				if (c7 ^ bit)
					crc ^= polynomial;
			}
		}
		crc &= 0xffff;

		return (byte) crc;
	}
	
	/* Computes the CRC-CCITT checksum on array of byte data, length len */
	public static int crc_ccitt(byte[] msg, int len) {
		int i, acc = 0;

		for (i = 0; i < len; i++) {
			acc = crchware((0xFF & (int)msg[i]), 0x1021, acc);
		}
		
		return acc;
	}
	
	/* models crc hardware (minor variation on polynomial division algorithm) */
	public static int crchware(int data, int genpoly, int accum) {
		int i;
		data <<= 8;
		for (i = 8; i > 0; i--) {
			if (((data ^ accum) & 0x8000) == 1)
				accum = (((accum << 1) ^ genpoly) & 0xFFFF);
			else
				accum = ((accum << 1) & 0xFFFF);
			data = ((data << 1) & 0xFFFF);
		}
		return accum;
	}
}
