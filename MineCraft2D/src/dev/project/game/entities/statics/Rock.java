package dev.project.game.entities.statics;

import java.awt.Graphics;

import dev.project.game.Handler;
import dev.project.game.gfx.Assets;
import dev.project.game.items.Item;
import dev.project.game.tiles.Tile;

public class Rock extends StaticEntity {

	public Rock(Handler handler, float x, float y) {
		super(handler, x, y, Tile.TILEWIDTH, Tile.TILEHEIGHT);
		
		bounds.x = 3;
		bounds.y = (int) (height / 5f);
		bounds.width = width - 6;
		bounds.height = (int) (height - height / 2f);
	}

	@Override
	public void update() {
		
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.rock, (int) (x - handler.getGameCamera().getxOffset() ), (int) (y - handler.getGameCamera().getyOffset() ), width, height, null);
		
	}

	@Override
	public void die() {
		handler.getWorld().getItemManager().addItem(Item.rockItem.createNew((int) x, (int) y) );
	}

}
