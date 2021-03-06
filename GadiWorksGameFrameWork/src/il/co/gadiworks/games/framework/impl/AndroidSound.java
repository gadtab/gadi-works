package il.co.gadiworks.games.framework.impl;

import android.media.SoundPool;
import il.co.gadiworks.games.framework.Sound;

public class AndroidSound implements Sound {
	int soundId;
	SoundPool soundPool;
	
	public AndroidSound(SoundPool soundPool, int soundId) {
		this.soundId = soundId;
		this.soundPool = soundPool;
	}

	@Override
	public void play(float volume) {
		this.soundPool.play(this.soundId, volume, volume, 0, 0, 1);
	}

	@Override
	public void dispose() {
		this.soundPool.unload(this.soundId);
	}
}