package com.rubberdroid.sudoiku;

import android.content.Context;
import android.media.MediaPlayer;

public class SoundManager {
	private SudoikuPreferences preferences;
	private MediaPlayer touchSound;
	private MediaPlayer crashSound;
	private MediaPlayer winningSound;

	public SoundManager(Context context, SudoikuPreferences preferences) {
		this.preferences = preferences;
		touchSound = MediaPlayer.create(context, R.raw.adriantnt_glass);
		crashSound = MediaPlayer.create(context, R.raw.glass_shatter);
		winningSound = MediaPlayer.create(context, R.raw.winner);
	}

	public void playTouch() {
		play(touchSound);
	}

	public void playCrash() {
		play(crashSound);
	}

	private void play(MediaPlayer mp) {
		if (preferences.soundFx()) {
			mp.seekTo(0);
			mp.start();
		}
	}

	public void playWinning() {
		play(winningSound);
	}

	public void stop() {
		touchSound.stop();
		crashSound.stop();
		winningSound.stop();
	}

}
