/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.compilers.first.lab;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author sjdonado
 */
public class AbstractSyntaxTree {
    private Node root;
    private static final char[] SYNTAX_TOKENS = new char[] {
        '(', ')', '*', '+', '|', '?'
    };
//    private ArrayList<Node> nodes;
    
    public AbstractSyntaxTree(String regex) {
//        this.nodes = new ArrayList<>();
//        StringBuilder input = new StringBuilder(); 
//        input.append(regex);

        List<Character> regexList = new ArrayList<>(
            regex.chars()
            .mapToObj(e -> (char) e)
            .collect(Collectors.toList())
        );
        
        this.root = buildTreeFromRegex(regexList);
    }
    
    public Node getRoot() {
        return this.root;
    }
    
    public int getheight(Node root) {
        if (root == null) return 0;
        return Math.max(getheight(root.getLeftChild()), getheight(root.getRightChild())) + 1;
    }
    
    private Node buildTreeFromRegex(List<Character> regexList) {
        Logger.getLogger(AbstractSyntaxTree.class.getName())
            .log(Level.INFO, "RegexList => {0}", regexList);
        Node newChildNode;
        int regexListLastCharIndex = regexList.size() - 1;
        Node parent = new Node(regexList.get(regexListLastCharIndex));
        int index = regexListLastCharIndex;
        while (index > 0 && index < regexList.size()) {
            Character character = regexList.get(index);
            System.out.println(character);
            Logger.getLogger(AbstractSyntaxTree.class.getName())
                .log(Level.INFO, "Character => {0}", character);
            if (Arrays.binarySearch(SYNTAX_TOKENS, character) == 1) {
                Node subParent = new Node(character);
                parent.setLeftChild(subParent);
                int leftLimit = searchSyntaxToken(regexList.subList(0, index), SYNTAX_TOKENS[0]);
                int breakPoint = searchSyntaxToken(regexList.subList(leftLimit, index), SYNTAX_TOKENS[4]);
                if (breakPoint != -1) {
                    Node breakPointNode = new Node(SYNTAX_TOKENS[breakPoint]);
                    subParent.setLeftChild(breakPointNode);
                    List<Character> rightRegex = regexList.subList(breakPoint + 1, index);
                    breakPointNode.setRightChild(buildTreeFromRegex(rightRegex));

                    List<Character> letfRegex = regexList.subList(leftLimit, breakPoint);
                    newChildNode = buildTreeFromRegex(letfRegex);
                    breakPointNode.setLeftChild(newChildNode);
                } else {
                    List<Character> subRegex = regexList.subList(leftLimit, index);
                    newChildNode = buildTreeFromRegex(subRegex);
                    subParent.setRightChild(newChildNode);
                }
                index = leftLimit;
            } else {
                newChildNode = new Node(character);
                parent.setLeftChild(newChildNode);
            }
            parent = newChildNode;
            index--;
        }
        return parent;
    }
    
//    private void addNodeWithOneLeftChild(char value) {
//        Node newNode = new Node(value);
//        Node leftNode = this.nodes.get(this.nodes.size() - 1);
//        newNode.setLeftChild(leftNode);
//        this.nodes.add(newNode);
//    }
    
//    private int getNextSyntaxTokenPos(List<Character> regexList,
//            int currentTokenPost) {
//        int index = 0, syntaxTokenPos = -1;
//        while (index < regexList.size()) {
//            if ((syntaxTokenPos = currentTokenPost) != -1) {
//                break;
//            }
//            index++;
//        }
//        return syntaxTokenPos;
//    }
    
    private int searchSyntaxToken(List<Character> regexList, char syntaxToken) {
        int index = 0;
        while (index < regexList.size()) {
            if (regexList.get(index) == syntaxToken) {
                return index;
            }
            index++;
        }
        return -1;
    }
}
