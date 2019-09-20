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
    
    public AbstractSyntaxTree(String regex) throws Exception {
        this.regex = regex + "#";
        List<Character> regexList = new ArrayList<>(
            regex.chars()
            .mapToObj(e -> (char) e)
            .collect(Collectors.toList())
        );
        int position = getRegexLastPosition(regex) + 1;
        this.root = addConcatNode('#', position);
        buildTreeFromRegex(root, regexList, regexList.size() - 1, position - 1, 1);
        printTree(root);
    }
    
//    Position: left => 1, right => 0
    private Node buildTreeFromRegex(Node root, List<Character> regexList, int index, int position, int orientation) throws Exception {
        Node newChild;
        if (index >= 0 && regexList.size() > 0) {
            Logger.getLogger(AbstractSyntaxTree.class.getName())
                .log(Level.INFO, "REGEX: {0}, INDEX: {1}, POSITION: {2}", new Object[]{regexList, index, position});
            Character character = regexList.get(index);
            Logger.getLogger(AbstractSyntaxTree.class.getName()).log(Level.INFO, "CHAR: {0}", character);
            int syntaxTokenPos = Arrays.binarySearch(SYNTAX_TOKENS, character);
            switch (syntaxTokenPos) {
                case 0:
                    buildTreeFromRegex(root, regexList.subList(0, index), index - 1, position, orientation);
                    break;
                case 1:
                    int leftLimit = regexList.subList(0, index).lastIndexOf(SYNTAX_TOKENS[0]);
                    if (leftLimit == -1) throw new Exception("Close parantheses not found");
                    List<Character> subList = regexList.subList(leftLimit + 1, index);
                    newChild = buildTreeFromRegex(root, subList, subList.size() - 1, position, 0);
                    root.setRightChild(newChild);
                    subList = regexList.subList(0, leftLimit);
                    buildTreeFromRegex(root, subList, subList.size() - 1, position - 2, 1);
                    break;
                case 2:
                case 3:
                case 5:
                    newChild = new Node(character, -1);
                    setNodeByOrientation(root, newChild, orientation);
                    buildTreeFromRegex(newChild, regexList.subList(0, index), index - 1, position, 0);
                    break;
                default:
                    int parenthesesPoint = regexList.subList(0, index).lastIndexOf(SYNTAX_TOKENS[1]);
                    int orPoint = regexList.subList(0, index).lastIndexOf(SYNTAX_TOKENS[4]);
                    if (parenthesesPoint == -1 && orPoint != -1) {
                        Node breakPointNode = new Node(SYNTAX_TOKENS[4], -1);
                        setNodeByOrientation(root, breakPointNode, 0);

                        List<Character> rightRegex = regexList.subList(orPoint + 1, index + 1);
                        Logger.getLogger(AbstractSyntaxTree.class.getName())
                            .log(Level.INFO, "rightRegex => {0}", rightRegex);
                        buildTreeFromRegex(breakPointNode, rightRegex, rightRegex.size() - 1, position, 0);

                        List<Character> letfRegex = regexList.subList(0, orPoint);
                        Logger.getLogger(AbstractSyntaxTree.class.getName())
                            .log(Level.INFO, "letfRegex => {0}", letfRegex);
                        buildTreeFromRegex(breakPointNode, letfRegex, letfRegex.size() - 1, position - 1, 1);
                        
                        root = breakPointNode;
                    } else {
                        syntaxTokenPos = new String(SYNTAX_TOKENS).lastIndexOf(root.getToken());
                        if (syntaxTokenPos >= 0 && syntaxTokenPos <= 5) {
                            newChild = new Node(character, position);
                        } else {
                            newChild = addConcatNode(character, position);
                        }
                        setNodeByOrientation(root, newChild, orientation);
                        buildTreeFromRegex(newChild, regexList.subList(0, index), index - 1, position - 1, 1);
                    }
                    break;
            }
        }
        return root;
    }
    
    private int getRegexLastPosition(String regex) {
        for (char syntaxToken : SYNTAX_TOKENS) {
            regex = regex.replace(Character.toString(syntaxToken), "");
        }
        return regex.length();
    }
    
    public Node getRoot() {
        return this.root;
    }
    
    public int getHeight(Node root) {
        if (root == null) return 0;
        return Math.max(getHeight(root.getLeftChild()), getHeight(root.getRightChild())) + 1;
    }
    
    private void printTree(Node n) {
        if (n == null) return;
        printTree(n.getLeftChild());
        System.out.print(n.getToken());
        printTree(n.getRightChild());
    }
    
    private void setNodeByOrientation(Node root, Node child, int orientation) {
        if (orientation == 0) {
            root.setRightChild(child);
        } else {
            root.setLeftChild(child);
        }
    }
    
    private Node addConcatNode(Character character, int position) {
        Node concat = new Node(SYNTAX_TOKENS[6], -1);
        Node newChild = new Node(character, position);
        concat.setRightChild(newChild);
        return concat;
    }
    
    public ArrayList<String[]> getTreePositions() {
        ArrayList<String[]> positions = new ArrayList<>();
        getNodePositions(this.root, positions);
        return positions;
    }
    
    private void getNodePositions(Node root, ArrayList<String[]> positions) {
        String firstPositions = getFirstPositionsAsString(root);
        String lastPositions = "{}";
        String nextPositions = "{}";
        if (root.getLeftChild() != null) {
            getNodePositions(root.getLeftChild(), positions);
        }
        positions.add(new String[]{ Character.toString(root.getToken()),
            firstPositions, lastPositions, nextPositions });
        if (root.getRightChild() != null) {
            getNodePositions(root.getRightChild(), positions);
        }
    }
    
    public String getFirstPositionsAsString(Node node) {
        return "{" + StringUtils.join(ArrayUtils.toObject(getFirstPositions(node)), " , ") + "}";
    }
    
//    PrimeraPos '(', ')', '*', '+', '|', '?', '.'
    public int[] getFirstPositions(Node node) {
        if (node != null) {
            if (node.getLeftChild() == null && node.getRightChild() == null) {
                return new int[]{ node.getPosition() };
            }
            if (node.getToken() == SYNTAX_TOKENS[2]) {
                return getFirstPositions(node.getRightChild());
            }
            if (node.getToken() == SYNTAX_TOKENS[4]) {
                return ArrayUtils.addAll(
                    getFirstPositions(node.getLeftChild()),
                    getFirstPositions(node.getRightChild())
                );
            }
            if (node.getToken() == SYNTAX_TOKENS[6]) {
//                isNullable(node.getLeftChild()
                if (isNullable(node.getLeftChild())) {
                    return ArrayUtils.addAll(
                        getFirstPositions(node.getLeftChild()),
                        getFirstPositions(node.getRightChild())
                    );
                } else {
                    return getFirstPositions(node.getRightChild());
                }
            }
        }
        return new int[]{};
    }
    
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
