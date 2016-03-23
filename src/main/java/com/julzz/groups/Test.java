/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.julzz.groups;

import com.julzz.groups.io.ProblemReader;
import com.julzz.groups.model.GroupFactory;
import com.julzz.groups.model.GroupVariable;
import com.julzz.groups.model.Problem;
import com.msu.moo.algorithms.single.SingleObjectiveEvolutionaryAlgorithm;
import com.msu.moo.model.evaluator.StandardEvaluator;
import com.msu.moo.operators.crossover.permutation.OrderedCrossover;
import com.msu.moo.operators.mutation.SwapMutation;
import com.msu.moo.util.Builder;
import com.msu.moo.util.MyRandom;

/**
 *
 * @author julesy
 */
public class Test {
    public static void main(String[] args) {
        Problem  problem = new ProblemReader().read("src/main/resources/small.json").build();
        
        Builder<SingleObjectiveEvolutionaryAlgorithm<GroupVariable, Problem>> ea = new Builder<>(SingleObjectiveEvolutionaryAlgorithm.class);
		ea
			.set("populationSize", 100)
			.set("probMutation", 0.3)
			.set("factory", new GroupFactory(problem))
			.set("crossover", new OrderedCrossover<>())
			.set("mutation", new SwapMutation<>());

		SingleObjectiveEvolutionaryAlgorithm<GroupVariable, Problem> algorithm = ea.build();
		algorithm.run(problem, new StandardEvaluator(10000), new MyRandom());
        
		System.out.println(algorithm.getPopulation());
		
    }
}
