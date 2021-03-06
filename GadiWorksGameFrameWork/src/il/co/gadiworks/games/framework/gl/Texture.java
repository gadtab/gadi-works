package il.co.gadiworks.games.framework.gl;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.opengl.GLUtils;

import il.co.gadiworks.games.framework.FileIO;
import il.co.gadiworks.games.framework.impl.GLGame;
import il.co.gadiworks.games.framework.impl.GLGraphics;

public class Texture {
	GLGraphics glGraphics;
	FileIO fileIO;
	String fileName;
	int textureId;
	int minFilter;
	int magFilter;
	public int width;
    public int height;
    boolean mipmapped;
    
    public Texture(GLGame glGame, String fileName) {
    	this(glGame, fileName, false);
    }
	
	public Texture(GLGame glGame, String fileName, boolean mipmapped) {
		this.glGraphics = glGame.getGLGraphics();
		this.fileIO = glGame.getFileIO();
		this.fileName = fileName;
		this.mipmapped = mipmapped;
		
		load();
	}

	private void load() {
		GL10 gl = this.glGraphics.getGL();
		
		int[] textureIds = new int[1];
		gl.glGenTextures(1, textureIds, 0);
		this.textureId = textureIds[0];
		
		InputStream in = null;
		try {
			in = this.fileIO.readAsset(this.fileName);
			Bitmap bitmap = BitmapFactory.decodeStream(in);
			if (this.mipmapped) {
				createMipmaps(gl, bitmap);
			}
			else{
				gl.glBindTexture(GL10.GL_TEXTURE_2D, this.textureId);
				GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
				setFilters(GL10.GL_NEAREST, GL10.GL_NEAREST);
				gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);
				this.width = bitmap.getWidth();
				this.height = bitmap.getHeight();
	            bitmap.recycle();
			}
		}
		catch (IOException e) {
			throw new RuntimeException("Couldn't load texture '" + this.fileName + "'", e);
		}
		finally {
			if (in != null) {
				try {
					in.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void createMipmaps(GL10 gl, Bitmap bitmap) {
		gl.glBindTexture(GL10.GL_TEXTURE_2D, this.textureId);
		this.width = bitmap.getWidth();
		this.height = bitmap.getHeight();
		setFilters(GL10.GL_LINEAR_MIPMAP_NEAREST, GL10.GL_LINEAR);
		
		int level = 0;
		int newWidth = this.width;
		int newHeight = this.height;
		
		while (true) {
			GLUtils.texImage2D(GL10.GL_TEXTURE_2D, level, bitmap, 0);
			newWidth /= 2;
			newHeight /=2;
			if (newWidth <= 0) {
				break;
			}
			
			Bitmap newBitmap = Bitmap.createBitmap(newWidth, newHeight, bitmap.getConfig());
			Canvas canvas = new Canvas(newBitmap);
			canvas.drawBitmap(bitmap, 
							  new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), 
							  new Rect(0, 0, newWidth, newHeight), 
							  null);
			bitmap.recycle();
			bitmap = newBitmap;
			level++;
		}
		
		gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);
		bitmap.recycle();
	}

	public void reload() {
		load();
		bind();
		setFilters(this.minFilter, this.magFilter);
		this.glGraphics.getGL().glBindTexture(GL10.GL_TEXTURE_2D, 0);
	}
	
	public void setFilters(int minFilter, int magFilter) {
		this.minFilter = minFilter;
		this.magFilter = magFilter;
		
		GL10 gl = this.glGraphics.getGL();
		
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, this.minFilter);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, this.magFilter);
	}
	
	public void bind() {
		GL10 gl = this.glGraphics.getGL();
		gl.glBindTexture(GL10.GL_TEXTURE_2D, this.textureId);
	}
	
	public void dispose() {
		GL10 gl = this.glGraphics.getGL();
		gl.glBindTexture(GL10.GL_TEXTURE_2D, this.textureId);
		int[] textureIds = {this.textureId};
		gl.glDeleteTextures(1, textureIds, 0);
	}
}