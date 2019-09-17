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
    private final Node rightChild;
    private final Node leftChild;
    private char token;
    
    public Node(Node leftNode, Node rightNode, char token) {
        this.rightChild = leftNode;
        this.leftChild = rightNode;
        this.token = token;
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

}
