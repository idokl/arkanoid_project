package arkanoid;

import animation.Animation;
import animation.AnimationRunner;
import animation.KeyPressStoppableAnimation;
import biuoop.KeyboardSensor;
import core.Collidable;
import core.Velocity;
import core.Counter;
import core.Sprite;
import geometry.Point;
import geometry.Rectangle;
import biuoop.DrawSurface;
import io.LevelInformation;

import java.awt.Color;

/**
 * arkanoid.GameLevel - put the game together (sprites, environment, gui, keyboard sensor).
 */
public class GameLevel implements Animation {
    private SpriteCollection spriteCollection;
    private GameEnvironment gameEnvironment;
    private ScoreIndicator scoreIndicator;
    private LivesIndicator livesIndicator;
    private LevelIndicator levelIndicator;
    private Counter blockCounter;
    private Counter ballCounter;
    private Counter score;
    private Counter numberOfLives;
    private int guiWidth;
    private int guiHeight;
    private int borderThickness;
    private AnimationRunner runner;
    private boolean running;
    private LevelInformation levelInformation;
    private KeyboardSensor keyboardSensor;
    private Paddle paddle;

    /**
     * @param levelInformation - levelInformation to this level
     * @param animationRunner  - animationRunner to run this gameLevel
     * @param keyboardSensor   - keyboard sensor
     * @param score            - Counter that maintains score that was collected during the former levels of the game
     * @param numberOfLives    - Counter that maintains number of lives that remained
     * @param guiWidth         - width of the GUI
     * @param guiHeight        - height of the GUI
     */
    public GameLevel(LevelInformation levelInformation, AnimationRunner animationRunner, KeyboardSensor keyboardSensor,
                     Counter score, Counter numberOfLives, int guiWidth, int guiHeight) {
        this.spriteCollection = new SpriteCollection();
        this.gameEnvironment = new GameEnvironment();
        this.score = score;
        this.numberOfLives = numberOfLives;
        this.scoreIndicator = new ScoreIndicator(this.score);
        this.livesIndicator = new LivesIndicator(this.numberOfLives);
        this.blockCounter = new Counter(0);
        this.ballCounter = new Counter(0);
        this.guiWidth = guiWidth;
        this.guiHeight = guiHeight;
        this.borderThickness = 25;
        this.levelInformation = levelInformation;
        this.levelIndicator = new LevelIndicator(levelInformation.levelName());
        this.keyboardSensor = keyboardSensor;
        this.runner = animationRunner;
    }

    /**
     * @param c - add collidable objects (blocks and paddle).
     */
    public void addCollidable(Collidable c) {
        this.gameEnvironment.addCollidable(c);
    }

    /**
     * @param s - add sprites.
     */
    public void addSprite(Sprite s) {
        this.spriteCollection.addSprite(s);
    }

    /**
     * @param c - Collidable to remove from this game
     */
    public void removeCollidable(Collidable c) {
        this.gameEnvironment.removeCollidable(c);

    }

    /**
     * @param s - Sprite to remove from this game
     */
    public void removeSprite(Sprite s) {
        this.spriteCollection.removeSprite(s);
    }

    /**
     * @return counter that counts how many lives remain
     */
    public Counter getNumberOfLives() {
        return this.numberOfLives;
    }

    /**
     * @return counter that counts how many blocks remain
     */
    public Counter getBlockCounter() {
        return this.blockCounter;
    }

    /**
     * create the background Sprite of the level and add it to the game.
     */
    public void makeBackground() {
        this.addSprite(levelInformation.getBackground());
    }

    /**
     * create the borders Blocks (up, left, right and deathBlock-down) and add them to the game.
     */
    public void makeBorders() {
        BallRemover ballRemover = new BallRemover(this, this.ballCounter);
        //create top border of the screen.
        Rectangle recUp = new Rectangle((new Point(0, this.borderThickness)), this.guiWidth, this.borderThickness);
        Block blockUp = new Block(recUp, 0, Color.gray, null);
        //create bottom border of the screen.
        Rectangle deathRectangle = new Rectangle((new Point(10, this.guiHeight)),
                this.guiWidth, 0);
        Block deathBlock = new Block(deathRectangle, 0, Color.lightGray, null);
        deathBlock.addHitListener(ballRemover);
        //create left border of the screen.
        Rectangle recLeft = new Rectangle((new Point(0, borderThickness + borderThickness)),
                borderThickness, this.guiHeight);
        Block blockLeft = new Block(recLeft, 0, Color.gray, null);
        //create right border of the screen.
        Rectangle recRight = new Rectangle((new Point(this.guiWidth - this.borderThickness,
                this.borderThickness + this.borderThickness)),
                this.borderThickness, this.guiHeight);
        Block blockRight = new Block(recRight, 0, Color.gray, null);
        //add the borders to the game.
        blockUp.addToGame(this);
        blockLeft.addToGame(this);
        blockRight.addToGame(this);
        deathBlock.addToGame(this);
    }

    /**
     * create the Blocks and add them to the game.
     */
    public void makeGameBlocks() {
        BlockRemover blockRemover = new BlockRemover(this, this.blockCounter);
        ScoreTrackingListener scoreTrackingListener = new ScoreTrackingListener(this.score);

        for (Block e : levelInformation.blocks()) {
            e.addToGame(this);
            e.addHitListener(blockRemover);
            e.addHitListener(scoreTrackingListener);
            e.addHitListener(this.paddle);
            this.blockCounter.increase(1);
        }
    }

    /**
     * create the Balls and add them to the game.
     */
    public void makeBalls() {
        for (Velocity vel : this.levelInformation.initialBallVelocities()) {
            //create two balls and add them to the game.
            Ball ball = new Ball(this.guiWidth / 2, this.guiHeight - 35, 5,
                    Color.white, this.gameEnvironment);
            ball.setVelocity(vel.getDeltaX(), vel.getDeltaY());
            this.ballCounter.increase(1);
            ball.addToGame(this);

        }
    }

    /**
     * create the Paddle and add it to the game.
     */
    public void makePaddle() {
        // create paddle and add it to game.
        Rectangle paddleRec = new Rectangle((new Point(this.guiWidth / 2 - levelInformation.paddleWidth() / 2,
                this.guiHeight - 30)), this.levelInformation.paddleWidth(), 10);
        this.paddle = new Paddle(paddleRec, Color.orange, this.keyboardSensor, this.guiWidth, this.borderThickness,
                this.levelInformation.paddleSpeed());
        this.paddle.addToGame(this);

    }


    /**
     * Initialize a new game: create the Blocks and the Balls and add them to the game.
     */
    public void initialize() {
        this.makeBackground();
        this.makeGameBlocks();
        this.makeBorders();
        this.scoreIndicator.addToGame(this);
        this.livesIndicator.addToGame(this);
        this.levelIndicator.addToGame(this);
    }


    /**
     * Run the game -- start the animation loop.
     */
    public void playOneTurn() {
        this.makeBalls();
        this.makePaddle();
        // countdown before turn starts.
        this.runner.run(new CountdownAnimation(2, 3, this.spriteCollection));
        this.running = true;
        this.runner.run(this);
    }

    /**
     * stopping condition to the running of the level.
     *
     * @return true, if the level over because all the blocks have removed or all the balls have fallen.
     * false, otherwise.
     */
    @Override
    public boolean shouldStop() {
        return !this.running;
    }

    /**
     * @param d  - DrawSurface to draw the sprites of the Animation on
     * @param dt amount of seconds passed since the last frame
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        //draw all sprites and notify them about the passing time.
        this.spriteCollection.drawAllOn(d);
        this.spriteCollection.notifyAllTimePassed(dt);
        if (this.blockCounter.getValue() == 0) {
            this.score.increase(100);
            this.paddle.removeFromGame(this);
            this.running = false;
        }
        if (this.ballCounter.getValue() == 0) {
            this.getNumberOfLives().decrease(1);
            this.paddle.removeFromGame(this);
            this.running = false;
        }
        KeyboardSensor keyboardSensor1 = keyboardSensor;
        if(score.getValue() >= 100){

            keyboardSensor = new MyKS(keyboardSensor);
        }
        if( score.getValue() >= 1200){
            keyboardSensor = keyboardSensor1;
        }
        if (keyboardSensor.isPressed("p")) {
            this.runner.run(new KeyPressStoppableAnimation(keyboardSensor, KeyboardSensor.SPACE_KEY,
                    new PauseScreen(keyboardSensor)));
        }
    }
}