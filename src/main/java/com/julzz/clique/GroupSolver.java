package com.julzz.clique;
import com.julzz.clique.group.GroupFactory;
import com.julzz.clique.group.GroupProblem;
import com.julzz.clique.group.GroupVariable;
import com.msu.builder.Builder;
import com.msu.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.operators.crossover.permutation.OrderedCrossover;
import com.msu.operators.mutation.SwapMutation;
import com.msu.soo.SingleObjectiveEvolutionaryAlgorithm;
import com.msu.util.MyRandom;

public class GroupSolver {

	public static void main(String[] args) {
		
		if (args.length !=1) {
			System.err.println("Please give a problem file as an argument!");
			return;
		}
		
	    GroupProblem problem = new GroupProblemReader().read(args[0]);
		
		Builder<SingleObjectiveEvolutionaryAlgorithm> ea = 
				new Builder<SingleObjectiveEvolutionaryAlgorithm>(SingleObjectiveEvolutionaryAlgorithm.class);
		ea
			.set("populationSize", 100)
			.set("probMutation", 0.3)
			.set("factory", new GroupFactory())
			.set("crossover", new OrderedCrossover<String>())
			.set("mutation", new SwapMutation<String>());
	
		
		NonDominatedSolutionSet set = ea.build().run(problem, new Evaluator(500000), new MyRandom());
		
		// System.out.println(set);
		
		GroupVariable var = (GroupVariable) set.get(0).getVariable();
		System.out.println(var.report(problem.getDescription()));
		System.out.println("----------------------------------------\n");
		System.out.println(var.print(problem.getDescription().getNumOfPersonsInGroup()));
		
		
	}
	
}
