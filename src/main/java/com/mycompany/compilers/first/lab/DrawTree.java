/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.compilers.first.lab;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author sjdonado
 */
public class DrawTree extends JPanel {
	
    public AbstractSyntaxTree tree;

    public DrawTree(AbstractSyntaxTree tree){
        this.tree = tree;
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setFont(new Font("Tahoma", Font.BOLD, 15));
        //g.drawString(String.valueOf(tree.root.data), this.getWidth()/2, 30);
//        drawNode(g, tree.getRoot(), 100, 50, 2);
        drawTree(g, 100, getWidth(), 50, getHeight() / tree.getHeight(tree.getRoot()), tree.getRoot());
    }

//    public void drawNode(Graphics g,Node n,int w,int h,int q){
//        g.setFont(new Font("Tahoma", Font.BOLD, 20));
//        if (n!=null) {
//            g.drawString(String.valueOf(n.getToken()), (this.getWidth()/q)+w, h);
//            if(n.getLeftChild() !=null)
//                drawNode(g, n.getLeftChild(), -w, h*2, q);
//                //DrawNode(g, n.left, -w, h*2, q);
//                //g.drawString(String.valueOf(n.left.data), (this.getWidth()/q)-w, h+50);
//            if(n.getRightChild() !=null)
//                drawNode(g, n.getRightChild(), w*2, h*2, q);
//            //g.drawString(String.valueOf(n.right.data), (this.getWidth()/q)+w, h+50);
//        }
//    }
	
    public void drawTree(Graphics g, int startWidth, int endWidth,
        int startHeight, int level, Node node) {
        String data = String.valueOf(node.getToken());
//        g.setFont(new Font("Tahoma", Font.BOLD, 20));
        FontMetrics fm = g.getFontMetrics();
        int dataWidth = fm.stringWidth(data);
        g.drawString(data, (startWidth + endWidth) / 2 - dataWidth / 2, startHeight + level / 2);

        if (node.getLeftChild() != null)            
            drawTree(g, startWidth, (startWidth + endWidth) / 2, startHeight + level, level, node.getLeftChild());
        
        if (node.getRightChild() != null)
            drawTree(g, (startWidth + endWidth) / 2, endWidth, startHeight + level, level, node.getRightChild());
    }
}
