package com.dungeons.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class AudioManager {
	
	private Music currentTrack;
	
	public AudioManager() {	
	}
	
	public void setCurrentTrack(String fileName, boolean loop) {
		currentTrack = Gdx.audio.newMusic(Gdx.files.internal("data/audio/" + fileName));
		currentTrack.setLooping(loop);
	}
	
	public Music getCurrentTrack() {
		return currentTrack;
	}
	
	public void pause() {
		if(currentTrack == null) {
			throw new RuntimeException("track cannot be null, use setCurrentTrack");
		}
		currentTrack.pause();
	}
	
	public void play() {
		if(currentTrack == null) {
			throw new RuntimeException("track cannot be null, use setCurrentTrack");
		}
		currentTrack.play();
	}
	
	public void close() {
		currentTrack.stop();
	}
	
	public boolean isPlaying() {
		if(currentTrack == null) {
			return false;
		}
		return currentTrack.isPlaying();
	}

}
