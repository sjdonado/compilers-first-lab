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
        '(', ')', '*', '+', '|', '?', '.'
    };
    
    public AbstractSyntaxTree(String regex) {
        List<Character> regexList = new ArrayList<>(
            regex.chars()
            .mapToObj(e -> (char) e)
            .collect(Collectors.toList())
        );
        
        this.root = addConcatNode('#');
        buildTreeFromRegex(this.root, regexList, regexList.size() - 1, 1);

        printTree(this.root);
    }
    
    public Node getRoot() {
        return this.root;
    }
    
    public int getHeight(Node root) {
        if (root == null) return 0;
        return Math.max(getHeight(root.getLeftChild()), getHeight(root.getRightChild())) + 1;
    }
    
//    Position: left => 1, right => 0
    private void buildTreeFromRegex(Node root, List<Character> regexList, int index, int position) {
        Node newChild;
        if (index >= 0 && regexList.size() > 0) {
            Logger.getLogger(AbstractSyntaxTree.class.getName())
                .log(Level.INFO, "RegexList => {0}, index => {1}", new Object[]{regexList, index});
            Character character = regexList.get(index);
            int syntaxTokenPos = Arrays.binarySearch(SYNTAX_TOKENS, character);
            switch (syntaxTokenPos) {
                case 0:
                    buildTreeFromRegex(root, regexList.subList(0, index), index - 1, position);
                    break;
                case 1:
                    int leftLimit = regexList.subList(0, index).indexOf(SYNTAX_TOKENS[0]);
                    int breakPoint = regexList.subList(leftLimit, index).indexOf(SYNTAX_TOKENS[4]);
                    Logger.getLogger(AbstractSyntaxTree.class.getName())
                            .log(Level.INFO, "leftLimit => {0}, breakPoint => {1}", new Object[]{leftLimit, leftLimit + breakPoint});
                    
                    if (leftLimit > 0) {
                        buildTreeFromRegex(root, regexList.subList(0, leftLimit), leftLimit - 1, 1);
                    }
                    
                    if (breakPoint != -1) {
                        breakPoint = leftLimit + breakPoint;
                        Node breakPointNode = new Node(SYNTAX_TOKENS[4]);
                        setNodeByPosition(root, breakPointNode, position);

                        List<Character> rightRegex = regexList.subList(breakPoint + 1, index);
//                        Logger.getLogger(AbstractSyntaxTree.class.getName())
//                            .log(Level.INFO, "rightRegex => {0}, index => {1}", new Object[]{rightRegex, index});
                        buildTreeFromRegex(breakPointNode, rightRegex, rightRegex.size() - 1, 0);

                        List<Character> letfRegex = regexList.subList(leftLimit + 1, breakPoint);
//                        Logger.getLogger(AbstractSyntaxTree.class.getName())
//                            .log(Level.INFO, "letfRegex => {0}, index => {1}", new Object[]{letfRegex, index});
                        buildTreeFromRegex(breakPointNode, letfRegex, letfRegex.size() - 1, 1);
                    } else {
                        buildTreeFromRegex(root, regexList.subList(0, index), index - 1, 1);
                    }
                    break;
                case 2:
                case 3:
                case 5:
                    newChild = new Node(character);
                    setNodeByPosition(root, newChild, position);
                    buildTreeFromRegex(newChild, regexList.subList(0, index), index - 1, 0);
                    break;
                default:
                    syntaxTokenPos = new String(SYNTAX_TOKENS).indexOf(root.getToken());
                    if (syntaxTokenPos >= 0 && syntaxTokenPos <= 5) {
                        newChild = new Node(character);
                    } else {
                        newChild = addConcatNode(character);
                    }
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
    
    private Node addConcatNode(Character character) {
        Node concat = new Node(SYNTAX_TOKENS[6]);
        Node newChild = new Node(character);
        concat.setRightChild(newChild);
        return concat;
    }
    
}
