package com.textures;

import java.nio.ByteBuffer;

public class TextureData {
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public ByteBuffer getBuffer() {
		return buffer;
	}

	private int width;
	private int height;
	private ByteBuffer buffer;
	
	public TextureData(int width, int height, ByteBuffer buffer) {
		super();
		this.width = width;
		this.height = height;
		this.buffer = buffer;
	}
	
	
}
