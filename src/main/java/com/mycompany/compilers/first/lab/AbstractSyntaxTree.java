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
    
    public AbstractSyntaxTree(String regex) {
        List<Character> regexList = new ArrayList<>(
            regex.chars()
            .mapToObj(e -> (char) e)
            .collect(Collectors.toList())
        );
        
        this.root = new Node('#');
        buildTreeFromRegex(root, regexList, regexList.size() - 1, 1);
        printTree(this.root);
    }
    
    public Node getRoot() {
        return this.root;
    }
    
    public int getHeight(Node root) {
        if (root == null) return 0;
        return Math.max(getHeight(root.getLeftChild()), getHeight(root.getRightChild())) + 1;
    }
    
//    Position: right => 0, left => 1
    private void buildTreeFromRegex(Node root, List<Character> regexList, int index, int position) {
        Node newChild;
        if (index >= 0 && regexList.size() > 0) {
            Logger.getLogger(AbstractSyntaxTree.class.getName())
                .log(Level.INFO, "RegexList => {0}, index => {1}", new Object[]{regexList, index});
            Character character = regexList.get(index);
            int syntaxTokenPos = Arrays.binarySearch(SYNTAX_TOKENS, character);
            
            switch (syntaxTokenPos) {
                case 0:
                    buildTreeFromRegex(root, regexList.subList(0, index), index - 1, 1);
                    break;
                case 1:
                    int leftLimit = searchSyntaxToken(regexList.subList(0, index), SYNTAX_TOKENS[0]);
                    int breakPoint = searchSyntaxToken(regexList.subList(leftLimit, index), SYNTAX_TOKENS[4]);
                    if (breakPoint != -1) {
                        Node breakPointNode = new Node(SYNTAX_TOKENS[4]);
                        setNodeByPosition(root, breakPointNode, position);

                        List<Character> rightRegex = regexList.subList(breakPoint + 1, index);
                        buildTreeFromRegex(breakPointNode, rightRegex, rightRegex.size() - 1, 0);

                        List<Character> letfRegex = regexList.subList(leftLimit, breakPoint);
                        buildTreeFromRegex(breakPointNode, letfRegex, letfRegex.size() - 1, 1);
                    } else {
                        List<Character> subRegex = regexList.subList(leftLimit, index);
                        buildTreeFromRegex(new Node(character), subRegex, index - 1, 1);
                    }
                    break;
                default:
                    newChild = new Node(character);
                    setNodeByPosition(root, newChild, position);

                    buildTreeFromRegex(newChild, regexList.subList(0, index), index - 1, 1);
                    break;
            }
        }
    }
    
    public void printTree(Node n) {
        if (n == null) return;
        printTree(n.getLeftChild());
        System.out.print(n.getToken());
        printTree(n.getRightChild());
    }
    
    private void setNodeByPosition(Node root, Node child, int position) {
        if (position == 0) {
            root.setRightChild(child);
        } else {
            root.setLeftChild(child);
        }
    }
    
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
