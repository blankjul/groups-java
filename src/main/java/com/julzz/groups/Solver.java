package com.julzz.groups;

import com.julzz.groups.evolutionary.GroupFactory;
import com.julzz.groups.evolutionary.GroupVariable;
import com.julzz.groups.model.Problem;
import com.msu.moo.algorithms.impl.single.SingleObjectiveEvolutionaryAlgorithm;
import com.msu.moo.interfaces.ISolution;
import com.msu.moo.model.evaluator.StandardEvaluator;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.moo.operators.crossover.permutation.OrderedCrossover;
import com.msu.moo.operators.mutation.SwapMutation;
import com.msu.moo.util.Builder;
import com.msu.moo.util.MyRandom;

public class Solver {


	
	public static SolutionSet<ISolution<GroupVariable>> solveWithEvolutionaryAlgorithm(Problem problem, int populationSize, int maxEvaluations) {

		Builder<SingleObjectiveEvolutionaryAlgorithm<GroupVariable, Problem>> ea = new Builder<SingleObjectiveEvolutionaryAlgorithm<GroupVariable, Problem>>(
				SingleObjectiveEvolutionaryAlgorithm.class);
		ea
			.set("populationSize", populationSize)
			.set("probMutation", 0.3)
			.set("factory", new GroupFactory(problem.getDescription()))
			.set("crossover", new OrderedCrossover<>())
			.set("mutation", new SwapMutation<>());

		SingleObjectiveEvolutionaryAlgorithm<GroupVariable, Problem> algorithm = ea.build();
		algorithm.run(problem, new StandardEvaluator(maxEvaluations), new MyRandom());
		return algorithm.getPopulation();
		
	}
	
	public static SolutionSet<ISolution<GroupVariable>> solveWithHillClimbing(Problem problem, int maxEvaluations) {
		return null;
		
	}
	
	

}
