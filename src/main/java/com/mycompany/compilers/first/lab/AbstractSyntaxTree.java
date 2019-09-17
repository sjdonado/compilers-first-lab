/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.compilers.first.lab;

import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 *
 * @author sjdonado
 */
public class AbstractSyntaxTree {
    private static final char[] SYNTAX_TOKENS = new char[] {
        '(', ')', '*', '+', '|', '?'
    };
    private ArrayList<Node> nodes;
    
    public AbstractSyntaxTree(String regex) {
        this.nodes = new ArrayList<>();
        
        StringBuilder input = new StringBuilder(); 
        input.append(regex);

        ArrayList<Character> parsedRegex = new ArrayList<>(
            (input.reverse().toString()).chars()
            .mapToObj(e -> (char) e)
            .collect(Collectors.toList())
        );
        
        for (int index = 0; index < parsedRegex.size(); index++) {
            System.out.println(parsedRegex.get(index));
            int syntaxTokenPos;
            if ((syntaxTokenPos = Arrays.binarySearch(SYNTAX_TOKENS,
                    parsedRegex.get(index))) != -1) {
            } else {
                if (this.nodes.size() > 0) {
                    addNodeWithOneLeftChild(parsedRegex.get(index));
                } else {
                    this.nodes.add(new Node(null, null, parsedRegex.get(index)));
                }
            }
        }
    }
    
    private void addNodeWithOneLeftChild(char value) {
        Node leftNode = this.nodes.get(this.nodes.size() - 1);
        this.nodes.add(new Node(leftNode, null, value));
    }
    
//    private char getNextSyntaxToken(List<Character> parsedRegex) {
//        
//    }
}
