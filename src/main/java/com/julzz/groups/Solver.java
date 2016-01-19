package com.julzz.groups;

import com.julzz.groups.io.ProblemReader;
import com.julzz.groups.model.GroupFactory;
import com.julzz.groups.model.GroupVariable;
import com.julzz.groups.model.Problem;
import com.msu.moo.algorithms.single.SingleObjectiveEvolutionaryAlgorithm;
import com.msu.moo.model.evaluator.StandardEvaluator;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.moo.operators.crossover.permutation.OrderedCrossover;
import com.msu.moo.operators.mutation.SwapMutation;
import com.msu.moo.util.Builder;
import com.msu.moo.util.MyRandom;

public class Solver {

	protected Problem problem;
	
	protected int populationSize = 100;
	
	protected int maxEvaluations = 100000;
	
	public void setProblem(String path) {
		problem = new ProblemReader().read(path);
	}
	
		
	public SolutionSet<GroupVariable> execute() {
		
		Builder<SingleObjectiveEvolutionaryAlgorithm<GroupVariable, Problem>> ea = new Builder<SingleObjectiveEvolutionaryAlgorithm<GroupVariable, Problem>>(SingleObjectiveEvolutionaryAlgorithm.class);
		ea
			.set("populationSize", populationSize)
			.set("probMutation", 0.3)
			.set("factory", new GroupFactory(problem))
			.set("crossover", new OrderedCrossover<>())
			.set("mutation", new SwapMutation<>());

		SingleObjectiveEvolutionaryAlgorithm<GroupVariable, Problem> algorithm = ea.build();
		algorithm.run(problem, new StandardEvaluator(maxEvaluations), new MyRandom());
		return algorithm.getFinalPopulation();
	}
	
	

	

}
