	package com.mygdx.arkaxoid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.Json;

public class Progress {

	private String totalBlocksHit;
	private ArrayMap<String,String> scoreRooms;

	public Progress(){
		scoreRooms = new ArrayMap<String, String>();
	}
	
	
	public String getTotalBlocksHit() {
		return totalBlocksHit;
	}

	public void setTotalBlocksHit(String totalBlocksHit) {
		this.totalBlocksHit = totalBlocksHit;
	}

	public void saveProgress(){
		FileHandle file = Gdx.files.local("progress");
		
		Json json = new Json();
	
		json.toJson(this, Progress.class, file);
		
	}
	
	public ArrayMap<String,String> getScoreRooms(){
		return scoreRooms;
	}
	
	public void setScoreToRoom(String room, String score){
		scoreRooms.put(room, score);
	}
	
	public void setInitialScoreRooms(Array<RoomWrapper> array){
		for(RoomWrapper room: array){
			if(room != null){
				scoreRooms.put(room.name, "0");				
			}
		}
	}
	
	public String getScoreFromRoom(String room){
		return scoreRooms.get(room);
	}
	
	
	public String toString(){
		return "totalBlocksHitted: "+totalBlocksHit;
	}

}
