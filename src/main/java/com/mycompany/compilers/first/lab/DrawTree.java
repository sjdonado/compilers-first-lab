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
        if (levelHeight > 50) levelHeight = 50;
        drawTree(g, getWidth() - 250, 50 + this.fm.getHeight() - levelHeight, levelHeight, tree.getRoot(), false);
    }
	
    public void drawTree(Graphics g, int x, int y, int levelHeight, Node node, boolean bias) {
        int nextXLeft, nextXRight, nextYLeft, nextYRight;
        String token = node.getToken();
        int marginY = this.fm.getHeight() / 2;
        
        g.setFont(new Font("Courier", Font.BOLD, 18));
        g.drawString(token, x , y);
        
        g.setFont(new Font("Courier", Font.ITALIC, 10));
        if (node.getPosition() != -1) {
            g.drawString(Integer.toString(node.getPosition()), x, y + marginY * 2);
        }
        
        g.setColor(Color.BLUE);
        String firstPositions = this.tree.getFirstPositionsAsString(node);
        g.drawString(firstPositions, x + this.fm.stringWidth(token) * 2,  y);
        
        g.setColor(Color.RED);
        String lastPositions = this.tree.getLastPositionsAsString(node);
        g.drawString(lastPositions, x - this.fm.stringWidth(token) - this.fm.stringWidth(lastPositions),  y);
        
        g.setColor(Color.BLACK);
        
        nextYLeft = y + levelHeight;
        nextYRight = y + levelHeight;
        nextXLeft = x - levelHeight * 2;
        nextXRight = x + levelHeight * 2;

        if (tree.isOperandToken(node.getToken())) {
            g.drawLine(x, y + marginY, x, nextYLeft - marginY);
            drawTree(g, x, nextYLeft, levelHeight, node.getLeftChild(), bias);
        } else {
            if (node.getLeftChild() != null
                    && node.getRightChild() != null
                    && node.getLeftChild().getRightChild() != null
                    && node.getRightChild().getLeftChild() != null) {
                nextYLeft -= (int) levelHeight / 1.4;
                nextXLeft -= (int) levelHeight / 1.25;
                nextXRight += (int) levelHeight / 1.25;
                bias = !bias;
            }
            if (node.getLeftChild() != null) {
                g.drawLine(x, y + marginY, nextXLeft, nextYLeft - marginY);
                drawTree(g, nextXLeft, nextYLeft, levelHeight, node.getLeftChild(), bias);
            }           
            if (node.getRightChild() != null) {
                g.drawLine(x, y + marginY, nextXRight, nextYRight - marginY);
                drawTree(g, nextXRight, nextYRight, levelHeight, node.getRightChild(), bias);
            }
        }
    }
}
