package il.co.gadiworks.games.framework.gl;

public class Animation {
	public static final int ANIMATION_LOOPING = 0;
	public static final int ANIMATION_NONLOOPING = 1;
	
	final TextureRegion[] KEY_FRAMES;
	final float FRAME_DURATION;
	
	public Animation(float frameDuration, TextureRegion ... keyFrames) {
		this.FRAME_DURATION = frameDuration;
		this.KEY_FRAMES = keyFrames;
	}
	
	public TextureRegion getKeyFrame(float stateTime, int mode) {
		int frameNumber = (int) (stateTime / this.FRAME_DURATION);
		
		if (mode == ANIMATION_NONLOOPING) {
			frameNumber = Math.min(this.KEY_FRAMES.length - 1, frameNumber);
		}
		else {
			frameNumber %= this.KEY_FRAMES.length;
		}
		
		return this.KEY_FRAMES[frameNumber];
	}
}
