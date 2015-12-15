package com.julzz.groups;

import com.julzz.groups.io.ProblemReader;
import com.julzz.groups.model.GroupFactory;
import com.julzz.groups.model.Problem;
import com.msu.builder.Builder;
import com.msu.model.Evaluator;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.operators.crossover.permutation.OrderedCrossover;
import com.msu.operators.mutation.SwapMutation;
import com.msu.soo.SingleObjectiveEvolutionaryAlgorithm;
import com.msu.util.MyRandom;

public class Solver {

	protected Problem problem;
	
	protected int populationSize = 100;
	
	
	
	public void setProblem(String path) {
		problem = new ProblemReader().read(path);
	}
	
	
	public SolutionSet execute() {
		
		Builder<SingleObjectiveEvolutionaryAlgorithm> ea = new Builder<SingleObjectiveEvolutionaryAlgorithm>(SingleObjectiveEvolutionaryAlgorithm.class);
		ea
			.set("populationSize", populationSize)
			.set("probMutation", 0.3)
			.set("factory", new GroupFactory())
			.set("crossover", new OrderedCrossover<>())
			.set("mutation", new SwapMutation<>());

		SingleObjectiveEvolutionaryAlgorithm algorithm = ea.build();
		algorithm.run(problem, new Evaluator(100000), new MyRandom());
		return algorithm.getPopulation();
	}
	
	

	

}
