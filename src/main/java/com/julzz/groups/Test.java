/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.julzz.groups;

import com.julzz.groups.io.ProblemReader;
import com.julzz.groups.model.GroupVariable;
import com.julzz.groups.model.Problem;
import com.julzz.groups.recombination.GroupCrossover;
import com.julzz.groups.recombination.GroupFactory;
import com.julzz.groups.recombination.GroupMutation;
import com.msu.moo.algorithms.single.SingleObjectiveEvolutionaryAlgorithm;
import com.msu.moo.model.evaluator.StandardEvaluator;
import com.msu.moo.util.Builder;
import com.msu.moo.util.MyRandom;


public class Test {
    public static void main(String[] args) {
        Problem  problem = new ProblemReader().read("src/main/resources/small.json").build();
        
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
		System.out.println(algorithm.getPopulation());
		
    }
}
