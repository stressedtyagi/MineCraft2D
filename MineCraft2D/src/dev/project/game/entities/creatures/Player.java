package dev.project.game.entities.creatures;

import java.awt.Graphics;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import dev.project.game.Handler;
import dev.project.game.entities.Entity;
import dev.project.game.gfx.Animations;
import dev.project.game.gfx.Assets;
import dev.project.game.inventory.Inventory;

public class Player extends Creature {

	
	//Animations 
	private Animations animDown, animUp, animLeft, animRight;
	
	// Attack Time
	private long lastAttackTimer, attackCoolDown = 200 , attackTimer = attackCoolDown;
	
	
	// Inventory
	public Inventory inventory;
	
	public Player(Handler handler, float x, float y) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
		
		bounds.x = 0;
		bounds.y = 30;
		bounds.width = 32;
		bounds.height = 32;
		
		//Animations
		animDown = new Animations(500, Assets.player_down);
		animUp = new Animations(500, Assets.player_up);
		animLeft = new Animations(500, Assets.player_left);
		animRight = new Animations(500, Assets.player_right);
		
		//Inventory
		inventory = new Inventory(handler);
	}

	@Override
	public void update() {
		//Animations
		animDown.update();
		animUp.update();
		animLeft.update();
		animRight.update();
		
		//Movement
		getInput();
		move();
		handler.getGameCamera().centerOnEntity(this);
		
		// Attack
		checkAttack();
		
		//Inventory
		inventory.update();
	}
	
	private void checkAttack(){
		
		attackTimer += System.currentTimeMillis() - lastAttackTimer;
		lastAttackTimer = System.currentTimeMillis();
		
		if(attackTimer < attackCoolDown)
			return;
			
		if(inventory.isActive())
			return;
		
		Rectangle cb = getCollisionBounds(0, 0);
		Rectangle ar = new Rectangle();
		int arSize = 20;
		ar.width = arSize;
		ar.height = arSize;
		
		if(handler.getKeyManager().aUp){
				ar.x = cb.x + cb.width / 2 - arSize;
				ar.y = cb.y - arSize;
		}else if(handler.getKeyManager().aDown){
				ar.x = cb.x + cb.width / 2 - arSize;
				ar.y = cb.y + cb.height;
		}else if(handler.getKeyManager().aLeft){
			ar.x = cb.x - arSize;
			ar.y = cb.y + cb.height / 2 - arSize / 2;
		}else if(handler.getKeyManager().aRight){
			ar.x = cb.x + cb.width;
			ar.y = cb.y + cb.height / 2 - arSize / 2;
		}else{
			return;
		}
		
		attackTimer = 0;
		
		for(Entity e : handler.getWorld().getEntityManager().getEntities()){
				if(e.equals(this))
					continue;
				if(e.getCollisionBounds(0, 0).intersects(ar)){
					e.hurt(1);
					return;
				}
		}

	}
	
	@Override
	public void die(){
		System.out.println("YOU LOSE");
	}
	
	private void getInput(){
		xMove = 0;
		yMove = 0;
		
		if(inventory.isActive())
			return;
		
		if(handler.getKeyManager().up)
			yMove = -speed;
		if(handler.getKeyManager().down)
			yMove = speed;
		if(handler.getKeyManager().left)
			xMove = -speed;
		if(handler.getKeyManager().right)
			xMove = speed;
			
	}
	@Override
	public void render(Graphics g) {
		g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset() ), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
	}
	
	public void postRender(Graphics g){
		inventory.render(g);
	}

	private BufferedImage getCurrentAnimationFrame(){
		if(xMove < 0){
			return animLeft.getCurrentFrame();
		}else if(xMove > 0){
			return animRight.getCurrentFrame();
		}else if(yMove < 0){
			return animUp.getCurrentFrame();
		}else{
			return animDown.getCurrentFrame();
		}

	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
	
	
}
