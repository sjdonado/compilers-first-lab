/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.compilers.first.lab;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author sjdonado
 */
public class AbstractSyntaxTree {
    private final Node root;
    private final String regex;
    private enum Operator {
        BAR(1), DOT(2), STAR(3), PLUS(3), QUESTION_MARK(3);
        final int precedence;
        Operator(int p) { precedence = p; }
    }
    
    public AbstractSyntaxTree(String regex) {
        this.regex = regex + "#";
        this.root = shuntingYard(this.regex);
        printTree(root);
    }
    
    private final static Map<String, Operator> operators = new HashMap<String, Operator>() {{
        put("|", Operator.BAR);
        put(".", Operator.DOT);
        put("*", Operator.STAR);
        put("+", Operator.PLUS);
        put("?", Operator.QUESTION_MARK);
    }};
    
    private ArrayList<String> addConcatNodesToRegexArr(String[] regexArr) {
        int index = 0;
        ArrayList<String> parsedRegex = new ArrayList<>();
        while (index < regexArr.length) {
            if (index + 1 < regexArr.length) {
                boolean isSyntaxKey = operators.containsKey(regexArr[index]);
                boolean isOpenParenthesis = regexArr[index].equals("(");
                boolean isClosedParenthesis = regexArr[index].equals(")");
                
                boolean nextIsSyntaxKey = operators.containsKey(regexArr[index + 1]);
                boolean nextIsOpenParenthesis = regexArr[index + 1].equals("(");
                boolean nextIsClosedParenthesis = regexArr[index + 1].equals(")");
                if ((!isSyntaxKey && !isParenthesis(regexArr[index]) 
                        && !nextIsSyntaxKey && !isParenthesis(regexArr[index + 1]))
                        || (nextIsOpenParenthesis && !regexArr[index].equals("|")
                            && !isOpenParenthesis)
                        || (isClosedParenthesis && !regexArr[index + 1].equals("|")
                            && !nextIsClosedParenthesis)) {
                    parsedRegex.add(regexArr[index]);
                    if (!regexArr[index + 1].equals(".")) {
                        parsedRegex.add("."); 
                    }
                } else {
                    parsedRegex.add(regexArr[index]);
                }

            } else {
                parsedRegex.add(regexArr[index]);
            }
            index++;
        }
        System.out.println(parsedRegex);
        return parsedRegex;
    }
    
    private Node shuntingYard(String regex) {
        int index = 0, position = 1;
        ArrayList<String> parsedRegex = addConcatNodesToRegexArr(regex.split(""));

        Deque<Node> operandStack = new LinkedList<>();
        Deque<Node> operatorStack = new LinkedList<>();
        
        while (index < parsedRegex.size()) {
            String token = parsedRegex.get(index);
            // operator
            if (operators.containsKey(token)) {
                while (!operatorStack.isEmpty() && isHigerPrec(token, operatorStack.peek().getToken()))
                    process(operandStack, operatorStack);
                operatorStack.push(new Node(token, -1));
            // left parenthesis
            } else if (token.equals("(")) {
                operatorStack.push(new Node(token, -1));
            // right parenthesis
            } else if (token.equals(")")) {
                while (!operatorStack.peek().getToken().equals("(")) process(operandStack, operatorStack);
                operatorStack.pop();
            // operand
            } else {
                Node newOperand;
//                if (index +  1 < parsedRegex.size() &&
//                        (parsedRegex.get(index + 1).equals("*")
//                            || parsedRegex.get(index + 1).equals("+")
//                            || parsedRegex.get(index + 1).equals("?"))) {
//                    newOperand = new Node(parsedRegex.get(index + 1), -1);
//                    newOperand.setLeftChild(new Node(token, position));
//                    index++;
//                } else {
//                }
                newOperand = new Node(token, position);
                operandStack.push(newOperand);
                position++;
            }
            index++;
        }

        while (!operatorStack.isEmpty()) process(operandStack, operatorStack);
        
        System.out.println(Arrays.asList(operandStack.toArray()).stream().map(n -> ((Node) n).getToken()).collect(Collectors.toList()).toString());
        return operandStack.peek();
    }
    
    private void process(Deque<Node> operandStack, Deque<Node> operatorStack) {
        Node operator = operatorStack.pop();
        if (operandStack.size() > 1) {
            operator.setRightChild(operandStack.pop());
            operator.setLeftChild(operandStack.pop());
        } else {
            operator.setLeftChild(operandStack.pop());
        }
        operandStack.push(operator); 
    }
    
    private boolean isHigerPrec(String op, String sub) {
        return (operators.containsKey(sub) && operators.get(sub).precedence >= operators.get(op).precedence);
    }
    
    private boolean isParenthesis(String token) {
        return token.equals("(") || token.equals(")");
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
    
    public ArrayList<String[]> getTreePositions() {
        ArrayList<String[]> positions = new ArrayList<>();
        getNodePositions(this.root, positions);
        return positions;
    }
    
    private void getNodePositions(Node root, ArrayList<String[]> positions) {
        String firstPositions = getFirstPositionsAsString(root);
        String lastPositions = getLastPositionsAsString(root);
        String nextPositions = "{}";
        if (root.getLeftChild() != null) {
            getNodePositions(root.getLeftChild(), positions);
        }
        positions.add(new String[]{ root.getToken(),
            firstPositions, lastPositions, nextPositions });
        if (root.getRightChild() != null) {
            getNodePositions(root.getRightChild(), positions);
        }
    }
    
    public String getFirstPositionsAsString(Node node) {
        return "{" + StringUtils.join(ArrayUtils.toObject(getFirstPositions(node)), " , ") + "}";
    }
    
    public int[] getFirstPositions(Node node) {
        if (node != null) {
            if (node.getLeftChild() == null && node.getRightChild() == null) {
                return new int[]{ node.getPosition() };
            }
            if (node.getToken().equals("*") || node.getToken().equals("+")) {
                return getFirstPositions(node.getLeftChild());
            }
            if (node.getToken().equals("|")) {
                return ArrayUtils.addAll(
                    getFirstPositions(node.getLeftChild()),
                    getFirstPositions(node.getRightChild())
                );
            }
            if (node.getToken().equals(".")) {
                if (isNullable(node.getLeftChild())) {
                    return ArrayUtils.addAll(
                        getFirstPositions(node.getLeftChild()),
                        getFirstPositions(node.getRightChild())
                    );
                } else {
                    return getFirstPositions(node.getLeftChild());
                }
            }
        }
        return new int[]{};
    }
    
    public String getLastPositionsAsString(Node node) {
        return "{" + StringUtils.join(ArrayUtils.toObject(getLastPositions(node)), " , ") + "}";
    }
    
    private int[] getLastPositions(Node node) {
              if (node != null) {
            if (node.getLeftChild() == null && node.getRightChild() == null) {
                return new int[]{ node.getPosition() };
            }
            if (node.getToken().equals("*") || node.getToken().equals("+")) {
                return getLastPositions(node.getLeftChild());
            }
            if (node.getToken().equals("|")) {
                return ArrayUtils.addAll(
                    getLastPositions(node.getLeftChild()),
                    getLastPositions(node.getRightChild())
                );
            }
            if (node.getToken().equals(".")) {
                if (isNullable(node.getRightChild())) {
                    return ArrayUtils.addAll(
                        getLastPositions(node.getLeftChild()),
                        getLastPositions(node.getRightChild())
                    );
                } else {
                    return getLastPositions(node.getRightChild());
                }
            }
        }
        return new int[]{};  
    }
    
    private boolean isNullable(Node node) {
        if (node == null || node.getToken().equals("*") || node.getToken().equals("&")) {
            return true;
        }
        if (node.getToken().equals("|")) {
            return isNullable(node.getLeftChild()) || isNullable(node.getRightChild());
        }
        if (node.getToken().equals(".")) {
            return isNullable(node.getLeftChild()) && isNullable(node.getRightChild());
        }
        if (node.getToken().equals("+")) {
            return isNullable(node.getLeftChild());
        }
        return false;
    }
    
//    private int[] getNextPos(Node node) {
//        this.root.
//    }
}
