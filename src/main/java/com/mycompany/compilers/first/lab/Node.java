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
//    private Node parent;
    private final String token;
    private final int position;
    
    public Node(String token, int position) {
//        this.parent = parent;
        this.rightChild = null;
        this.leftChild = null;
        this.token = token;
        this.position = position;
    }
    
//    public void setParent(Node parent) {
//        this.parent = parent;
//    }

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
    
//    public Node getParent() {
//        return parent;
//    }

    public String getToken() {
        return token;
    }
    
    public int getPosition() {
        return position;
    }
}
