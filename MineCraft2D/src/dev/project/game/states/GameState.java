package dev.project.game.states;

import java.awt.Graphics;


import dev.project.game.Handler;
import dev.project.game.worlds.World;

public class GameState extends States {

	private World world;
	
	public GameState(Handler handler){
		super(handler);
		world = new World(handler, "res/worlds/world1.txt");
		handler.setWorld(world);		
	}
	
	@Override
	public void update() {	
		world.update();
	}

	@Override
	public void render(Graphics g) {
		world.render(g);	
	}

}
