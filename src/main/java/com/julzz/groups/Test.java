/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.julzz.groups;

import com.julzz.groups.evolutionary.GroupCrossover;
import com.julzz.groups.evolutionary.GroupFactory;
import com.julzz.groups.evolutionary.GroupMutation;
import com.julzz.groups.evolutionary.GroupVariable;
import com.julzz.groups.io.ProblemReader;
import com.julzz.groups.io.Report;
import com.julzz.groups.model.Problem;
import com.msu.moo.algorithms.impl.single.SingleObjectiveEvolutionaryAlgorithm;
import com.msu.moo.interfaces.ISolution;
import com.msu.moo.model.evaluator.StandardEvaluator;
import com.msu.moo.util.Builder;
import com.msu.moo.util.MyRandom;


public class Test {
    public static void main(String[] args) {
        Problem  problem = new ProblemReader().read("src/main/resources/2016_oct_men.json").build();
        
        /*
        problem.getDescription().getGroupLimits().clear();
        for (int i = 0; i < problem.getDescription().getNumOfMembers(); i++) {
			problem.getDescription().getGroupLimits().add(1);
		}
		*/
        
        Builder<SingleObjectiveEvolutionaryAlgorithm<GroupVariable, Problem>> ea = new Builder<>(SingleObjectiveEvolutionaryAlgorithm.class);
		ea
			.set("populationSize", 100)
			.set("probMutation", 0.3)
			.set("factory", new GroupFactory(problem.getDescription()))
			.set("crossover", new GroupCrossover(problem.getDescription()))
			.set("mutation", new GroupMutation());

		SingleObjectiveEvolutionaryAlgorithm<GroupVariable, Problem> algorithm = ea.buildNoClone();

		algorithm.run(problem, new StandardEvaluator(10000), new MyRandom(57686476));
        
		System.out.println(algorithm.getPopulation().size());
		
		for (int i = 0; i < 10; i++) {
			ISolution<GroupVariable> s = algorithm.getPopulation().get(i);
			System.out.println("-----------------------------------------");
			System.out.println("Solution " + (i+1));
			System.out.println("-----------------------------------------");
			System.out.println("Groups: " + s.getVariable());
			System.out.println(Report.create(problem, s.getVariable()));
			System.out.println();
		}
		
		
		//System.out.println(algorithm.getPopulation());
		
    }
}
