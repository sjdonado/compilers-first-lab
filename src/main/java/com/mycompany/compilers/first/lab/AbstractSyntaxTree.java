/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.compilers.first.lab;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
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
    private int position;
    
    private enum Operator {
        BAR(1), DOT(2), STAR(3), PLUS(3), QUESTION_MARK(3);
        final int precedence;
        Operator(int p) { precedence = p; }
    }
    
    public AbstractSyntaxTree(String regex) {
        this.regex = regex;
        this.root = shuntingYard(this.regex);
//        printTree(root);
    }
    
    private final static Map<String, Operator> operators = new HashMap<String, Operator>() {{
        put("|", Operator.BAR);
        put(".", Operator.DOT);
        put("*", Operator.STAR);
        put("+", Operator.PLUS);
        put("?", Operator.QUESTION_MARK);
    }};
    
    private ArrayList<String> parseRegexArr(String[] regexArr) {
        int index = 0;
        ArrayList<String> parsedRegex = new ArrayList<>();
        while (index < regexArr.length) {
            if (index + 1 < regexArr.length) {
                boolean isSyntaxToken = isSyntaxToken(regexArr[index]);
//                boolean isOperator = operators.containsKey(regexArr[index]);
                boolean isOpenParenthesis = regexArr[index].equals("(");
                boolean isClosedParenthesis = regexArr[index].equals(")");
                
                boolean nextIsSyntaxToken = isSyntaxToken(regexArr[index + 1]);
                boolean nextIsOperator = operators.containsKey(regexArr[index + 1]);
                boolean nextIsOpenParenthesis = regexArr[index + 1].equals("(");
                boolean nextIsClosedParenthesis = regexArr[index + 1].equals(")");
                
                if ((!isSyntaxToken && !nextIsSyntaxToken)
                        || (isClosedParenthesis && nextIsOpenParenthesis)
                        || (isClosedParenthesis && !nextIsOperator && !isParenthesis(regexArr[index + 1]))
                        || (isOperandToken(regexArr[index + 1]) && !nextIsSyntaxToken)
                        || (isOperandToken(regexArr[index]) && !nextIsSyntaxToken)) {
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
        int index = 0;
        position = 1;
        
        if (!regex.contains(")") && !regex.contains("(")) regex = "(" + regex + ")";
        regex += "#";
        ArrayList<String> parsedRegex = parseRegexArr(regex.split(""));

        Deque<Node> operandStack = new LinkedList<>();
        Deque<Node> operatorStack = new LinkedList<>();
        
        while (index < parsedRegex.size()) {
            String token = parsedRegex.get(index);
            // operator
            if (operators.containsKey(token)) {
                while (!operatorStack.isEmpty() && isHigherPrec(token, operatorStack.peek().getToken()))
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
                if ((index + 1 < parsedRegex.size() 
                        && !isSyntaxToken(parsedRegex.get(index)))
                        && isOperandToken(parsedRegex.get(index + 1))) {
                    newOperand = new Node(parsedRegex.get(index + 1), -1);
                    newOperand.setLeftChild(new Node(token, position));
                    index++;
                } else {
                    if (token.equals("&")) {
                        newOperand = new Node(token, -1);
                        position--;
                    } else {
                        newOperand = new Node(token, position);
                    }
                }
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
        if (isOperandToken(operator.getToken())
                && operandStack.size() > 1) {
            operator.setLeftChild(operandStack.pop());
            
            Node concat = new Node(".", -1);
            if (operandStack.size() > 0) {
                concat.setLeftChild(operandStack.pop());
                concat.setRightChild(operator);
            } else {
                concat.setLeftChild(operator);
            }
            operandStack.push(concat); 
        } else {
            if (operandStack.size() > 1) {
//                System.out.println("SET RIGHT CHILD => parent:" + operator.getToken() + " child : " + operandStack.peek().getToken());
                operator.setRightChild(operandStack.pop());
                operator.setLeftChild(operandStack.pop());
            } else {
                operator.setLeftChild(operandStack.pop());
            }
            operandStack.push(operator); 
        }
    }
    
    private boolean isHigherPrec(String op, String sub) {
        return (operators.containsKey(sub) && operators.get(sub).precedence >= operators.get(op).precedence);
    }
    
    private boolean isParenthesis(String token) {
        return token.equals("(") || token.equals(")");
    }
    
    private boolean isSyntaxToken(String token) {
        return operators.containsKey(token) || token.equals("(")
            || token.equals(")");
    }
    
    public boolean isOperandToken(String token) {
        return token.equals("+") || token.equals("*") || token.equals("?");
    }

    public Node getRoot() {
        return this.root;
    }
    
    public int getPosition() {
        return this.position - 1;
    }
    
    public int getHeight(Node root) {
        if (root == null) return 0;
        return Math.max(getHeight(root.getLeftChild()), getHeight(root.getRightChild())) + 1;
    }
    
//    private void printTree(Node n) {
//        if (n == null) return;
//        printTree(n.getLeftChild());
//        System.out.println(n.getToken());
//        printTree(n.getRightChild());
//    }
    
    public ArrayList<String[]> getTreePositions() {
        ArrayList<String[]> positions = new ArrayList<>();
        getNodePositions(this.root, positions);
        return positions;
    }
    
    private void getNodePositions(Node node, ArrayList<String[]> positions) {
        String firstPositions = getFirstPositionsAsString(node);
        String lastPositions = getLastPositionsAsString(node);
        String nextPositions = getNextPositionsAsString(node);
        if (node.getLeftChild() != null) {
            getNodePositions(node.getLeftChild(), positions);
        }
        positions.add(new String[]{ node.getToken(),
            firstPositions, lastPositions, nextPositions });
        if (node.getRightChild() != null) {
            getNodePositions(node.getRightChild(), positions);
        }
    }
    
    public String getFirstPositionsAsString(Node node) {
        return "{" + StringUtils.join(ArrayUtils.toObject(getFirstPositions(node)), ",") + "}";
    }
    
    public int[] getFirstPositions(Node node) {
        if (node != null) {
            if (node.getToken().equals("&")) {
                return new int[]{};
            }
            if (isOperandToken(node.getToken())) {
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
            if (node.getLeftChild() == null && node.getRightChild() == null) {
                return new int[]{ node.getPosition() };
            }
        }
        return new int[]{};
    }
    
    public String getLastPositionsAsString(Node node) {
        return "{" + StringUtils.join(ArrayUtils.toObject(getLastPositions(node)), ",") + "}";
    }
    
    private int[] getLastPositions(Node node) {
        if (node != null) {
            if (node.getToken().equals("&")) {
                return new int[]{};
            }
            if (isOperandToken(node.getToken())) {
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
            if (node.getLeftChild() == null && node.getRightChild() == null) {
                return new int[]{ node.getPosition() };
            }
        }
        return new int[]{};
    }
    
    public String getNextPositionsAsString(Node node) {
        return "{" + StringUtils.join(ArrayUtils
            .toObject(getNextPositionsSorted(node)), ",") + "}";
    }
    
    public int[] getNextPositionsSorted(Node node) {
        int[] nextPositions = getNextPositions(new ArrayList(), root, node.getPosition());
        Arrays.sort(nextPositions);
        return nextPositions;
    }
    
    private int[] getNextPositions(ArrayList<Integer> nexPositions, Node node, int position) {
        if (node != null) {
            if (isOperandToken(node.getToken())
                    && Arrays.binarySearch(getLastPositions(node.getLeftChild()), position) >= 0) {
                addNextPositionsToArrayList(nexPositions, getFirstPositions(node.getLeftChild()));
            }
            if (node.getToken().equals(".")
                    && Arrays.binarySearch(getLastPositions(node.getLeftChild()), position) >= 0) {
                addNextPositionsToArrayList(nexPositions, getFirstPositions(node.getRightChild()));
            }
            if (node.getLeftChild() != null) {
                getNextPositions(nexPositions, node.getLeftChild(), position);
            }
            if (node.getRightChild() != null) {
                getNextPositions(nexPositions, node.getRightChild(), position);
            }
        }
        return nexPositions.stream().mapToInt(i->i).toArray();
    }
    
    private void addNextPositionsToArrayList(ArrayList<Integer> positions, int [] nextPositions) {
        for (int nexPosition : nextPositions) {
            if (!positions.contains(nexPosition)) positions.add(nexPosition);
        }
    }
    
    private boolean isNullable(Node node) {
        if (node == null || node.getToken().equals("*")
                || node.getToken().equals("?")
                || node.getToken().equals("&")) {
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
    
    public String getAlphabetAsString() {
        return "{" + StringUtils.join(getAlphabet(), ",") + "}";
    }
    
    public String[] getAlphabet() {
        ArrayList<Node> nodes = nodesList(new ArrayList(), root);
        nodes.remove(nodes.size() - 1);
        return nodes.stream().map(n -> n.getToken()).distinct().toArray(String[]::new);
    }
    
    public Node[] getNodes() {
        ArrayList<Node> nodes = nodesList(new ArrayList(), root);
        
        Comparator<Node> comparator = (Node n1, Node n2) ->
            (new Integer(n1.getPosition())).compareTo(new Integer(n2.getPosition()));
        Collections.sort(nodes, comparator);
        
        return nodes.stream().toArray(Node[]::new);
    }
    
    private ArrayList<Node> nodesList(ArrayList<Node> nodes, Node node) {
        if (node != null) {
            if (!operators.containsKey(node.getToken())
                    && !nodes.contains(node) && !node.getToken().equals("&"))
                nodes.add(node);
            
            if (node.getLeftChild() != null)
                nodesList(nodes, node.getLeftChild());
            
            if (node.getRightChild() != null)
                nodesList(nodes, node.getRightChild());
        }
        return nodes;
    }
}
