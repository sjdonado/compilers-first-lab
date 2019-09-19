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
import java.util.stream.Stream;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author sjdonado
 */
public class AbstractSyntaxTree {
    private Node root;
    private String regex;
    private static final char[] SYNTAX_TOKENS = new char[] {
        '(', ')', '*', '+', '|', '?', '.'
    };
    
    public AbstractSyntaxTree(String regex) {
        this.regex = regex + "#";
        List<Character> regexList = new ArrayList<>(
            regex.chars()
            .mapToObj(e -> (char) e)
            .collect(Collectors.toList())
        );
        
        this.root = addConcatNode('#');
        buildTreeFromRegex(this.root, regexList, regexList.size() - 1, 1);
        printTree(this.root);
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
    
    public Node getRoot() {
        return this.root;
    }
    
    public int getHeight(Node root) {
        if (root == null) return 0;
        return Math.max(getHeight(root.getLeftChild()), getHeight(root.getRightChild())) + 1;
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
    
    public ArrayList<String[]> getTreePositions() {
        ArrayList<String[]> positions = new ArrayList<>();
        getNodePositions(this.root, positions);
        return positions;
    }
    
    private void getNodePositions(Node root, ArrayList<String[]> positions) {
        String firstPositions = StringUtils.join(ArrayUtils.toObject(getFirstPositions(root)), " , ");
        String lastPositions = "";
        String nextPositions = "";
        positions.add(new String[]{ Character.toString(root.getToken()), firstPositions, lastPositions, nextPositions });
        if (root.getRightChild() != null) {
            getNodePositions(root.getRightChild(), positions);
        }
        if (root.getLeftChild() != null) {
            getNodePositions(root.getLeftChild(), positions);
        }
    }
    
    public int[] getFirstPositions(Node node) {
        if (node.getLeftChild() == null && node.getRightChild() == null) {
            return new int[]{ this.regex.indexOf(node.getToken())  };
        }
        if (node.getToken() == SYNTAX_TOKENS[2]) {
            return getFirstPositions(node.getRightChild());
        }
        if (node.getToken() == SYNTAX_TOKENS[4]) {
            return ArrayUtils.addAll(getFirstPositions(node.getLeftChild()),
                getFirstPositions(node.getRightChild()));
        }
        if (node.getToken() == SYNTAX_TOKENS[6]) {
            if (isNullable(node.getLeftChild())) {
                return ArrayUtils.addAll(getFirstPositions(node.getLeftChild()),
                    getFirstPositions(node.getRightChild()));
            } else {
                getFirstPositions(node.getRightChild());
            }
        }
        return new int[]{};
    }
    
//    public int lastPos(Node node) {
//        
//    }
    
    private boolean isNullable(Node node) {
        if (node == null || node.getToken() == SYNTAX_TOKENS[2]) {
            return true;
        }
        if (node.getToken() == SYNTAX_TOKENS[4]) {
            return isNullable(node.getLeftChild()) || isNullable(node.getRightChild());
        }
        if (node.getToken() == SYNTAX_TOKENS[6]) {
            return isNullable(node.getLeftChild()) && isNullable(node.getRightChild());
        }
        return false;
    }
}
