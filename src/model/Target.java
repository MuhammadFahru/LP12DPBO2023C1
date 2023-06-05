/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import view.Game;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author fahru
 */
public class Target  extends GameObject {
    /**
     * Constructor.
     */
    
    // Default constructor.
    public Target()
    {
        super(0, 0, "Target");
        super.setHeight(50);
    }
    
    // Constructor with player position.
    public Target(int x, int y)
    {
        super(x, y, "Target");
        super.setHeight(50);
    }
    
    /**
     * Override interface.
     */
    
    @Override
    public void render(Graphics object)
    {
        // Set target shape.
        object.setColor(Color.green);
        object.fillRect(x, y, 30, 30);
    }
    
    @Override
    public void loop()
    {
        // Initialize velocity, so object can move.
        this.x += this.velX;
        this.y += this.velY;
        
        // Initialize player bound, so it won't get offset the display.
        x = Game.clamp(x, 0, (Game.width - 50));
        y = Game.clamp(y, 0, (Game.height - 70));
    }
}
