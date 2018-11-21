import java.awt.Color;

public class ChickenGame {

	// Constants for the game
	static final int MAX_SCREEN_X = 1024;
	static final int MAX_SCREEN_Y = 768;
	static final int CHICKEN_WINS = 1;
	static final int CHICKEN_DIES = 2;
	static final int CHICKEN_RUNNING = 0;
	static final int MAX_TRUCKS = 5;
	
	public static void main(String[] args) {

		// At the start of the game, the chicken is in running state.
		int chickenState = CHICKEN_RUNNING;
		
		// Setup EZ graphics system.
		EZ.initialize(MAX_SCREEN_X, MAX_SCREEN_Y);

		// Draw the road.
		EZ.addImage("road.jpg", MAX_SCREEN_X / 2, MAX_SCREEN_Y / 2);
		//EZ.addImage("sky.jpg", MAX_SCREEN_X / 2, MAX_SCREEN_Y / 2);

		// Make a Chicken
		Chicken myChicken = new Chicken("Chicken.png", 50, MAX_SCREEN_Y / 2);
		//Chicken myChicken = new Chicken("rocket.png", 50, MAX_SCREEN_Y / 2);
		
		// Make 10 trucks
		Obstacle truck[] = new Obstacle[MAX_TRUCKS];

		for (int i = 0; i < MAX_TRUCKS; i++){
			truck[i] = new Obstacle("truck.png", MAX_SCREEN_X, MAX_SCREEN_Y);
			//truck[i] = new Obstacle("asteroid.png", MAX_SCREEN_X, MAX_SCREEN_Y);
		}
		
		// Load sound effects
		EZSound chickenSound = EZ.addSound("chicken.wav");
		//EZSound chickenSound = EZ.addSound("explosion.wav");
		
		EZSound truckSound = EZ.addSound("chicken_dance.wav");
		//EZSound truckSound = EZ.addSound("spaceship.wav");		
		
		EZSound crowSound = EZ.addSound("cheehoo.wav");
		
		truckSound.loop();
		
		// This is the main game loop.
		while (chickenState == CHICKEN_RUNNING) {

			// Steer the Chicken
			myChicken.ControlIt();

			// Check to see if Chicken has hit a truck.
			for (int i = 0; i < MAX_TRUCKS; i++) {
				
				// Move the truck.
				truck[i].move();
				
				// Check to see if the chicken is touching the truck.
				if ((truck[i].isInside(myChicken.getX() - 30, myChicken.getY() - 30))
						|| (truck[i].isInside(myChicken.getX() + 30, myChicken.getY() - 30))
						|| (truck[i].isInside(myChicken.getX() - 30, myChicken.getY() + 30))
						|| (truck[i].isInside(myChicken.getX() + 30, myChicken.getY() + 30))) {
					truckSound.stop();
					chickenState = CHICKEN_DIES;
					chickenSound.play();
				}

			}

			// If chicken moves off the screen then you win!
			if (myChicken.getX() > MAX_SCREEN_X) {
				chickenState = CHICKEN_WINS;
				truckSound.stop();
				crowSound.play();
			}
			// Make sure to do this or else the graphics wonʻt refresh
			EZ.refreshScreen();

		}


		if (chickenState == CHICKEN_DIES) {
			
			// Draw McNuggets.
			EZ.addImage("nuggets.png", myChicken.getX(), myChicken.getY());
			//EZ.addImage("explosion.png", myChicken.getX(), myChicken.getY());

			// Show the message: You are McNuggets
			Color c = new Color(0, 10, 150);
			int fontsize = 50;
			
			EZText text = EZ.addText(512, 300, "You are McNuggets", c, fontsize);
			//EZText text = EZ.addText(512, 300, "FRAK", c, fontsize);
			
			text.setFont("8-BIT WONDER.TTF");
			//text.setFont("ethnocentric rg.ttf");
		}

		// Show the message: Youʻre safe!
		if (chickenState == CHICKEN_WINS) {
			Color c = new Color(0, 255, 100);
			int fontsize = 100;
			
			EZText text = EZ.addText(MAX_SCREEN_X/2-100, MAX_SCREEN_Y/2, "SAFE", c, fontsize);
			text.setFont("8-BIT WONDER.TTF");
			//text.setFont("ethnocentric rg.ttf");
		}
	}
}
