/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.compilers.first.lab;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.lang3.ArrayUtils;

/**
 *
 * @author sjdonado
 */
public class GUI extends javax.swing.JFrame {
    private AbstractSyntaxTree tree;
    private AFD afd;
    private DefaultTableModel jTableTreeModel;
    private DefaultTableModel jTableTranDModel;
    private DefaultTableModel jTableAFDStatuses;

    /**
     * Creates new form GUI
     */
    public GUI() {
        initComponents();
        jPanelTree.setBorder(new EmptyBorder(5, 5, 5, 5));
        jPanelTree.setLayout(new BorderLayout(0, 0));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        textAlphabet = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnBuildRegexTree = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableAbstractSyntaxTree = new javax.swing.JTable();
        jPanelTree = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        editEvaulateRegex = new javax.swing.JTextField();
        btnEvaluateRegex = new javax.swing.JButton();
        textEvaluationResult = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        editRegex = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableAFDStatuses = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableTRAND = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        textAlphabet.setEditable(false);
        textAlphabet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textAlphabetActionPerformed(evt);
            }
        });

        jLabel1.setText("Expresión regular:");

        btnBuildRegexTree.setText("Aceptar");
        btnBuildRegexTree.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuildRegexTreeActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(tableAbstractSyntaxTree);

        jPanelTree.setPreferredSize(new java.awt.Dimension(869, 682));

        javax.swing.GroupLayout jPanelTreeLayout = new javax.swing.GroupLayout(jPanelTree);
        jPanelTree.setLayout(jPanelTreeLayout);
        jPanelTreeLayout.setHorizontalGroup(
            jPanelTreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanelTreeLayout.setVerticalGroup(
            jPanelTreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 434, Short.MAX_VALUE)
        );

        jLabel2.setText("Reconocer cadena:");

        editEvaulateRegex.setText("abbabb");
        editEvaulateRegex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editEvaulateRegexActionPerformed(evt);
            }
        });

        btnEvaluateRegex.setText("Aceptar");
        btnEvaluateRegex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEvaluateRegexActionPerformed(evt);
            }
        });

        textEvaluationResult.setText(" ");

        jLabel3.setText("Tabla TRAND");

        editRegex.setText("((a|c)*b((a|c)b|(a|c))*b?|b)");
        editRegex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editRegexActionPerformed(evt);
            }
        });

        jScrollPane2.setViewportView(tableAFDStatuses);

        jLabel4.setText("Estados AFD:");

        jLabel5.setText("Alfabeto:");

        jLabel6.setText("Tabla árbol Sintactico");

        jLabel7.setForeground(new java.awt.Color(0, 51, 255));
        jLabel7.setText("PrimeraPos");

        jLabel8.setForeground(new java.awt.Color(255, 0, 0));
        jLabel8.setText("ÚltimaPos");

        jScrollPane3.setViewportView(tableTRAND);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanelTree, javax.swing.GroupLayout.DEFAULT_SIZE, 1204, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editRegex, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuildRegexTree)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editEvaulateRegex, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEvaluateRegex)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textEvaluationResult, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 12, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel7))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textAlphabet, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(btnBuildRegexTree)
                    .addComponent(editRegex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(editEvaulateRegex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEvaluateRegex)
                    .addComponent(textEvaluationResult))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(textAlphabet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelTree, javax.swing.GroupLayout.PREFERRED_SIZE, 434, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuildRegexTreeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuildRegexTreeActionPerformed
        this.tree = new AbstractSyntaxTree(editRegex.getText());
        this.afd = new AFD(tree);
        
        JPanel drawTree = new DrawTree(tree);
        jPanelTree.removeAll();
        jPanelTree.add(drawTree);
        revalidate();
        repaint();
        
        ArrayList<String[]> treePositions = tree.getTreePositions();
        jTableTreeModel = new DefaultTableModel(treePositions.size(), 4);
        jTableTreeModel.setColumnIdentifiers(new String[] { "Nodo", "PrimeraPos",
            "ÚltimaPos", "SiguientePos"});
        
        for (int row = 0; row < treePositions.size(); row++) {
            String[] nodePositions = treePositions.get(row);
            jTableTreeModel.setValueAt(nodePositions[0], row, 0);
            jTableTreeModel.setValueAt(nodePositions[1], row, 1);
            jTableTreeModel.setValueAt(nodePositions[2], row, 2);
            jTableTreeModel.setValueAt(nodePositions[3], row, 3);
        }
        
        tableAbstractSyntaxTree.removeAll();
        tableAbstractSyntaxTree.setModel(jTableTreeModel);
        
        textAlphabet.setText(tree.getAlphabetAsString());
        
//        AFD
        String[][] trandD = afd.getTrandD();
        String[] alphabet = tree.getAlphabet();
        String[][] statuses = afd.getStatuses();
        
        jTableAFDStatuses = new DefaultTableModel(statuses.length, 2);
        jTableAFDStatuses.setColumnIdentifiers(new String[] { "Estado", "Posiciones"});
        
        jTableTranDModel = new DefaultTableModel(statuses.length, alphabet.length);
        jTableTranDModel.setColumnIdentifiers(
                ArrayUtils.addAll(new String[] { "Estado\\Símbolo"}, alphabet));
        
        for (int row = 0; row < statuses.length; row++) {
            jTableTranDModel.setValueAt(statuses[row][0], row, 0);
            jTableAFDStatuses.setValueAt(statuses[row][0], row, 0);
            jTableAFDStatuses.setValueAt(statuses[row][1], row, 1);
            for (int column = 0; column < alphabet.length; column++) {
                jTableTranDModel.setValueAt(trandD[row][column], row, column + 1);
            }
        }
        
        tableAFDStatuses.removeAll();
        tableAFDStatuses.setModel(jTableAFDStatuses);
        
        tableTRAND.removeAll();
        tableTRAND.setModel(jTableTranDModel);
    }//GEN-LAST:event_btnBuildRegexTreeActionPerformed

    private void textAlphabetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textAlphabetActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textAlphabetActionPerformed

    private void editEvaulateRegexActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editEvaulateRegexActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editEvaulateRegexActionPerformed

    private void btnEvaluateRegexActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEvaluateRegexActionPerformed
        if (afd.validateString(editEvaulateRegex.getText())) {
            textEvaluationResult.setText("Valida");
        } else {
            textEvaluationResult.setText("No Valida");
        }
    }//GEN-LAST:event_btnEvaluateRegexActionPerformed

    private void editRegexActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editRegexActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editRegexActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame jframe = new GUI();
                jframe.setLocationRelativeTo(null);
                jframe.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuildRegexTree;
    private javax.swing.JButton btnEvaluateRegex;
    private javax.swing.JTextField editEvaulateRegex;
    private javax.swing.JTextField editRegex;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanelTree;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tableAFDStatuses;
    private javax.swing.JTable tableAbstractSyntaxTree;
    private javax.swing.JTable tableTRAND;
    private javax.swing.JTextField textAlphabet;
    private javax.swing.JLabel textEvaluationResult;
    // End of variables declaration//GEN-END:variables
}
