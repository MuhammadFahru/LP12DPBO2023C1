/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import model.GameObject;
import model.Player;
import model.Target;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.util.Random;

/**
 *
 * @author Satria Ramadhani
 */
public class Game extends Canvas implements Runnable
{
    /**
     * 
     * Attribute declaration.
     */
    
    /* View-related attributes. */
    public static final int width = 640;
    public static final int height = 480;
    private Display display;
    
    /* Process-related attributes. */
    private boolean running;
    private Handler handler;
    private Thread thread;
    
    /* Animation-related attributes. */
    private boolean startCounting = false;
    private int scoreMove = 0;
    private int scoreHit = 0;
    private int counter = 0;
    private int stateCounter = 0;
    private int direction = 0;
    
    Random random = new Random();
    
    // Default constructor.
    public Game()
    {
        try
        {
            // Initialize display.
            display = new Display(width, height, "LP7C1DPBO2023 - Muhammad Fahru Rozi");
            display.open(this); 
            
            // Initialize game handler.
            handler = new Handler();
            
            // Initialize controller (keyboard input).
            this.setFocusable(true);
            this.requestFocus();
            this.addKeyListener(new Controller(this, handler));
            
            // Initialize all object.
            running = true;
            if(running)
            {
                // Player
                handler.add(new Player(320, 160));
                
                // Target
                int x = random.nextInt(width / 30) * 30;
                int y = random.nextInt(height / 30) * 30;
                handler.add(new Target(x, y));
            }
        } catch(Exception e)
        {
            System.err.println("Failed to instance data.");
        }
    }
    
    /**
     * 
     * Getter and Setter.
     */
    
    /* Game running status. */
    
    public boolean isRunning()
    {
        return running;
    }

    public void setRunning(boolean running)
    {
        this.running = running;
    }
    
    /* Game score. */
    
    public int getScoreMove()
    {
        return scoreMove;
    }

    public void setScoreMove(int score)
    {
        this.scoreMove += score;
    }
    
    public int getScoreHit()
    {
        return scoreHit;
    }

    public void setScoreHit(int score)
    {
        this.scoreHit += score;
    }
    
    /**
     * 
     * Public methods.
     */
    
    // Clamp, so player won't get offset the display bound.
    public static int clamp(int var, int min, int max)
    {
        if(var >= max)
        {
            return var = max;
        }
        else if(var <= min)
        {
            return var = min;
        }
        
        return var;
    }
    
    // Close display.
    public void close()
    {
        display.close();
    }
    
    /**
     * 
     * Game controller.
     */
    
    // Start threading.
    public synchronized void start()
    {
        thread = new Thread(this);
        thread.start(); running = true;
        
    }
    
    // Stop threading.
    public synchronized void stop()
    {
        try
        {
            thread.join();
            running = false;
        }
        catch(InterruptedException e)
        {
            System.out.println("Thread error : " + e.getMessage());
        }
    }
    
    // Initialize game when it run for the first time.
    public void render()
    {
        // Use buffer strategy.
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null)
        {
            this.createBufferStrategy(SOMEBITS);
            return;
        }
        
        // Initialize graphics.
        Graphics g = bs.getDrawGraphics();
        Image bg = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/assets/game.jpg"));
        g.drawImage(bg, 0, 0, null);
        
        if(running == true)
        {
            // Render handler.
            handler.render(g);
            
            // Render score.
            Font oldFont = g.getFont();
            Font newFont = oldFont.deriveFont(oldFont.getSize() * 1.3f);
            g.setFont(newFont);
            
            g.setColor(Color.white);
            g.drawString("Score Move : " + Integer.toString(scoreMove), 20, 30);
            
            g.setColor(Color.white);
            g.drawString("Score Hit : " + Integer.toString(scoreHit), 20, 50);
            
        }
        
        // Loop the process so it seems like "FPS".
        g.dispose();
        bs.show();
    }
    
    // Main loop proccess.
    public void loop()
    {
        GameObject player = null;
        GameObject target = null;
        
        handler.loop();
        if(this.running)
        {   
            counter++;
            if(startCounting)
            {
                stateCounter++;
            }
            
            if(stateCounter >= 40)
            {
                stateCounter = 0;
                startCounting = false;
            }
            
            if(counter >= 50)
            {
                direction = (direction == 0) ? 1 : 0;
                counter = 0;
            }
            
            for(int i = 0; i < handler.count(); i++)
            {
                if(handler.get(i).getType().equals("Player"))
                {
                    player = handler.get(i);
                }
                if(handler.get(i).getType().equals("Target"))
                {
                    target = handler.get(i);
                }
            }
            
            if(handler.hit(player, target))
            {
                scoreHit += 5;
                System.out.println("HIT Target +5 Score Hit");
                target.setRandomPos(width, height);
            }
        }
    }
    
    /**
     * 
     * Override interface.
     */
    
    @Override
    public void run()
    {
        double fps = 60.0;
        double ns = (1000000000 / fps);
        double delta = 0;
        
        // Timer attributes.
        long time = System.nanoTime();
        long now = 0;
        long timer = System.currentTimeMillis();
        
        int frames = 0;
        while(running)
        {
            now = System.nanoTime();
            delta += ((now - time) / ns);
            time = now;
            
            while(delta > 1)
            {
                loop();
                delta--;
            }
            
            if(running)
            {
                render();
                frames++;
            }
            
            if((System.currentTimeMillis() - timer) > 1000)
            {
                timer += 1000;
                frames = 0;
            }
        }
        
        stop();
    }
}
