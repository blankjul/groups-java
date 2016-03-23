/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.julzz.groups.ui.panels;

import com.julzz.groups.model.GroupFactory;
import com.julzz.groups.model.GroupVariable;
import com.julzz.groups.model.Problem;
import com.julzz.groups.ui.AbstractPanel;
import com.julzz.groups.ui.Storage;
import com.msu.moo.algorithms.single.SingleObjectiveEvolutionaryAlgorithm;
import com.msu.moo.model.evaluator.StandardEvaluator;
import com.msu.moo.operators.crossover.permutation.OrderedCrossover;
import com.msu.moo.operators.mutation.SwapMutation;
import com.msu.moo.util.Builder;
import com.msu.moo.util.MyRandom;
import java.util.logging.Level;
import java.util.logging.Logger;


public class AlgorithmPanel extends AbstractPanel {

    /**
     * Creates new form AlgorithmPanel
     */
    public AlgorithmPanel() {
        initComponents();
    }

    @Override
    public void save() {
        
        Problem p = Storage.bProblem.build();
        
        Builder<SingleObjectiveEvolutionaryAlgorithm<GroupVariable, Problem>> ea = new Builder<>(SingleObjectiveEvolutionaryAlgorithm.class);
        ea
                .set("populationSize", Storage.population)
                .set("probMutation", 0.3)
                .set("factory", new GroupFactory(p))
                .set("crossover", new OrderedCrossover<>())
                .set("mutation", new SwapMutation<>());

        SingleObjectiveEvolutionaryAlgorithm<GroupVariable, Problem> algorithm = ea.build();
        
         algorithm.run(p, new StandardEvaluator(Storage.evaluations), new MyRandom());
         Storage.result = algorithm.getPopulation();
        
        Thread t = new Thread(() -> {
            
        });
        
        t.start();

        try {
            t.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(AlgorithmPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.print("Test");
        
        
        
    }
    
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
