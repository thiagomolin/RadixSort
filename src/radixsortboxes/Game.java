import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {

	public static final long serialVersionUID = 1L;
	public static final int WINDOW_WIDTH = 1300, WINDOW_HEIGHT = 800;
	public static final String title = "Radix-Sort algorithm!";
		
	public static final int NUMBER_OF_ITEMS_TO_SORT = 650;
	
	public static final int BOX_WIDTH = 2;
	public static final int BOX_HEIGHT = 1;
	
        public final int TICKS = 100;
        
	private boolean running = false;
	
	private Thread thread;
	
	private Graphics g;
	
	private Sorter sorter;
		
	public Game(){	
		new Window(WINDOW_WIDTH, WINDOW_HEIGHT, title, this);
		sorter = new Sorter(NUMBER_OF_ITEMS_TO_SORT);
	}
	
	public static void main(String[] a){
		new Game();
	}
		
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		
		g = bs.getDrawGraphics();
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

		sorter.render(g);
		

		g.dispose();
		bs.show();
		
	}

	private void tick() {
		sorter.tick();				
	}
	
	public  void run() {
		int ticks = 0;
		long lastTime = System.nanoTime();
		double amountOfTicks = TICKS;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta>= 1){
				
				tick();
				
				ticks++;
				delta--;
			}
			if(running){
				render();
			}
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				System.out.println("FPS: " + frames + " TICKS: " + ticks);
				frames = 0;
				ticks = 0;
			}
		}
		
	}
	
	public static int clamp(int var, int min, int max){
		if(var >= max)
			return max;
		if(var <= min)
			return min;
		else
			return var;
	}	
	
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;		
	}
	
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
	}
}
