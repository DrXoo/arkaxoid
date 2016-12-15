package com.mygdx.arkaxoid;

public class RoomWrapper {
	String 	name;
	String 	type;
	String 	boss;
	int[] 	limit;
	float[] probs;

	public RoomWrapper(){

	}

	public RoomWrapper(String n, String a, String b, int[] l, float[] p){
		name = n;
		type = a;
		boss = b;
		limit = l;
		probs = p;
	}
}
