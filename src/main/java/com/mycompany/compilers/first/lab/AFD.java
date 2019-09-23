/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.compilers.first.lab;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author sjdonado
 */

class StatusD {
    private static int indexCounter = 0;
    String token;
    int[] positions;
    boolean marked;
    int index;

    public StatusD(int[] positions) {
        this.token = getNewToken();
        this.positions = positions;
        this.marked = false;
        this.index = indexCounter;
        indexCounter++;
    }
    
    private String getNewToken() {
        return Character.toString((char)(65 + indexCounter));
    }
}

public class AFD {
    private final AbstractSyntaxTree tree;
    private final ArrayList<StatusD> statuses;
    private final String[][] trandD;
//    private final ArrayList<ArrayList<String>> trandD = new ArrayList();
    
    public AFD(AbstractSyntaxTree tree) {
        this.tree = tree;
        this.statuses = new ArrayList();
        trandD = new String[500][this.tree.getAlphabet().length];
        statuses.add(new StatusD(this.tree.getFirstPositions(this.tree.getRoot())));
        build();
    }
    
    private void build() {
        StatusD status, u;
        int tokenPos;
        String[] alphabet = tree.getAlphabet();
        Node[] nodes = tree.getNodes();
        while((status = getUnmarkedStatus()) != null) {
            status.marked = true;
            ArrayList<Integer> positions;
            tokenPos = 0;
            for (String token : alphabet) {
                positions = new ArrayList();
                for (int position : status.positions) {
                    if (nodes[position - 1].getToken().equals(token)) {
                        for (int nextPosition : tree.getNextPositionsSorted(nodes[position - 1])) {
                            if (!positions.contains(nextPosition))
                                positions.add(nextPosition);
                        }
                    }
                }
                u = getStatusDByPositions(positions);
                if (positions.size() > 0 && u == null) {
                    u = new StatusD(positions.stream().mapToInt(i -> i).toArray());
                    statuses.add(u);
                }
//                System.out.println("STATUS => " + status.token + " U => " + u.token + " POSITIONS =>" + positions.toString());
                trandD[status.index][tokenPos] = u.token;
                tokenPos++;
            }
        }
    }
    
    private StatusD getUnmarkedStatus() {
        int index = 0;
        while (index < statuses.size()) {
            if (!statuses.get(index).marked) return statuses.get(index);
            index++;
        }
        return null;
    }
    
    private StatusD getStatusDByPositions(ArrayList<Integer> positions) {
        int index = 0;
        while (index < statuses.size()) {
            if (Arrays.equals(positions.stream().mapToInt(i -> i).toArray(),
                    statuses.get(index).positions))
                return statuses.get(index);
            index++;
        }
        return null;
    }
    
    public String[][] getTrandD() {
        return this.trandD;
    }
    
    public String getStatusesTokensAsString(){
        return "{" + StringUtils.join(getStatusesTokens(), ",") + "}";
    }
    
    public String[] getStatusesTokens() {
        return statuses.stream().map(s -> s.token).toArray(String[]::new);
    }
    
    public boolean validateString(String regex) throws Exception {
//        int index = 0;
//        String[] regexArr = regex.split("");
//        while (index < regexArr.length - 1) {
//            Node node = searchNode(root, regexArr[index]);
//            Node nextNode = searchNode(root, regexArr[index + 1]);
//            if (node == null || nextNode == null) throw new Exception("Node not found");
//            if (Arrays.binarySearch(getNextPositionsSorted(node), nextNode.getPosition()) < 0)
//                return false;
//            index += 2;
//        }
        return true;
    }
}
