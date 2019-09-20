/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.compilers.first.lab;

/**
 *
 * @author sjdonado
 */
public class Node {
    private Node rightChild;
    private Node leftChild;
    private final char token;
    private final int position;
    
    public Node(char token, int position) {
        this.rightChild = null;
        this.leftChild = null;
        this.position = position;
        this.token = token;
    }

    public void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
    }

    public void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
    }
    
    public Node getRightChild() {
        return rightChild;
    }

    public Node getLeftChild() {
        return leftChild;
    }

    public char getToken() {
        return token;
    }
    
    public int getPosition() {
        return position;
    }
}
