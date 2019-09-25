/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.compilers.first.lab;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;

import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author sjdonado
 */

class StatusD {
    static int indexCounter = 0;
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
        StatusD.indexCounter = 0;
        this.tree = tree;
        this.statuses = new ArrayList();
        trandD = new String[1000][this.tree.getAlphabet().length];
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
                trandD[status.index][tokenPos] = u == null ? null : u.token;
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
    
    public String[][] getStatuses() {
        return statuses.stream().map(s -> new String[]{s.token, "{" + StringUtils.join(ArrayUtils.toObject(s.positions), ",") + "}"}).toArray(String[][]::new);
    }
    
    private String[] getStatusesTokens() {
        return statuses.stream().map(s -> s.token).toArray(String[]::new);
    }
    
    public boolean validateString(String regex) {
//        regex = regex.replace("&", "");
        List<String> statusesTokens = Arrays.asList(getStatusesTokens());
        List<String> alphabet = Arrays.asList(tree.getAlphabet());
        int index = 0, statusIndex = 0, letterPos;
        String[] regexArr = regex.split("");
        while (index < regexArr.length) {
            letterPos = alphabet.indexOf(regexArr[index]);
            if (letterPos == -1 || statusIndex == -1) return false;
//            System.out.println("NEXT_STATUS: " + trandD[statusIndex][letterPos] + " SYM: " + regexArr[index]);
            statusIndex = statusesTokens.indexOf(trandD[statusIndex][letterPos]);
            index ++;
        }
        return statusIndex == statusesTokens.size() - 1;
    }
}
