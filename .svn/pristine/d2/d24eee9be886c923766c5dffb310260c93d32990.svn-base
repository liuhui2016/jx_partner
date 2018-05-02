package com.game.smvc.util;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

public class ByteOutputStream extends ByteArrayOutputStream {
	public ByteOutputStream() {
	}

	public ByteOutputStream(int size) {
		super(size);
	}

	public String getBuf() {
		String bufStr = null;
		try {
			if (this.buf != null) {
				bufStr = new String(this.buf, "ISO-8859-1");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return bufStr;
	}

	public int getCount() {
		return this.count;
	}
}
