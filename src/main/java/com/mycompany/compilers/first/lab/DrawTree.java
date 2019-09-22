/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.compilers.first.lab;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author sjdonado
 */
public class DrawTree extends JPanel {
	
    private FontMetrics fm;
    private final AbstractSyntaxTree tree;

    public DrawTree(AbstractSyntaxTree tree){
        this.tree = tree;
    }

    @Override
    protected void paintComponent(Graphics g) {
        this.fm = g.getFontMetrics();
        //g.drawString(String.valueOf(tree.root.data), this.getWidth()/2, 30);
//        drawNode(g, tree.getRoot(), 100, 50, 2);
        int levelHeight = (int) Math.floor(getHeight() / tree.getHeight(tree.getRoot()));
        drawTree(g, 0, getWidth(), this.fm.getHeight() - levelHeight, levelHeight, tree.getRoot());
    }
	
    public void drawTree(Graphics g, int startWidth, int endWidth,
        int startHeight, int levelHeight, Node node) {

        int nextStartWidth, nextEndWidth, nextStartHeight;
        String token = node.getToken();
        int tokenWidth = this.fm.stringWidth(token);
        int x = getNodeXPosition(startWidth, endWidth) - tokenWidth / 2;
        int y = startHeight + levelHeight + this.fm.getHeight() / 2;
        
        g.setFont(new Font("Courier", Font.BOLD, 18));
        g.drawString(token, x, y);
        
        g.setFont(new Font("Courier", Font.ITALIC, 10));
        if (node.getPosition() != -1) {
            g.drawString(Integer.toString(node.getPosition()), x + tokenWidth / 2, y + (int) (this.fm.getHeight() / 1.5));
        }
        
        g.setColor(Color.BLUE);
        String firstPositions = this.tree.getFirstPositionsAsString(node);
        g.drawString(firstPositions, x + tokenWidth * 2,  y);
        
        g.setColor(Color.RED);
        String lastPositions = this.tree.getLastPositionsAsString(node);
        g.drawString(lastPositions, x - tokenWidth - this.fm.stringWidth(lastPositions),  y);
        
        g.setColor(Color.BLACK);

        if (node.getLeftChild() != null) {
            nextStartWidth = startWidth;
            nextEndWidth = getNodeXPosition(startWidth, endWidth);
            nextStartHeight = startHeight + levelHeight;
            
            g.drawLine(x, y + this.fm.getHeight() / 2, getNodeXPosition(nextStartWidth, nextEndWidth), nextStartHeight + levelHeight - this.fm.getHeight() / 2);
            drawTree(g, nextStartWidth, nextEndWidth, nextStartHeight, levelHeight, node.getLeftChild());
        }           
            
        if (node.getRightChild() != null) {
            nextStartWidth = getNodeXPosition(startWidth, endWidth);
            nextEndWidth = endWidth;
            nextStartHeight = startHeight + levelHeight;

            g.drawLine(x, y + this.fm.getHeight() / 2, getNodeXPosition(nextStartWidth, nextEndWidth), nextStartHeight + levelHeight - this.fm.getHeight() / 2);
            drawTree(g, nextStartWidth, nextEndWidth, nextStartHeight, levelHeight, node.getRightChild());
        }
    }
    
    private int getNodeXPosition(int startWidth, int endWidth) {
        return (int) Math.floor((startWidth + endWidth) / 1.9);
    }
    
//    private int getNodeNextXPosition(int nextStartWidth, int nextEndWidth) {
//        return ((int) Math.floor((nextStartWidth + nextEndWidth) / 1.2)) - nextStartWidth / 4;
//    }
}
