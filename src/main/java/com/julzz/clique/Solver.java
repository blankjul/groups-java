package com.julzz.clique;

import com.julzz.clique.group.GroupFactory;
import com.julzz.clique.group.GroupVariable;
import com.julzz.clique.group.Problem;
import com.julzz.clique.io.ProblemReader;
import com.msu.builder.Builder;
import com.msu.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.operators.crossover.permutation.OrderedCrossover;
import com.msu.operators.mutation.SwapMutation;
import com.msu.soo.SingleObjectiveEvolutionaryAlgorithm;
import com.msu.util.MyRandom;

public class Solver {

	public static void main(String[] args) {

		if (args.length != 1) {
			System.err.println("Please give a problem file as an argument!");
			return;
		}
		
		/*
		 * 
		ProblemBuilder b = new ProblemBuilder();
		b.setNumOfPersonsInGroup(3);
		b.addMember(Arrays.asList("Julian", "Peter", "Max", "Ali", "Robert", "Alex"));
		b.addPreference("Alex", Arrays.asList("Julian", "Peter"));
		b.addPreference("Max", Arrays.asList("Ali", "Peter"));
		b.addRejection("Alex", Arrays.asList("Ali", "Robert"));
		b.addForbiddenGroup(Arrays.asList("Max", "Julian"));
		System.out.println(b);
		GroupProblem problem = b.build();
		
		*/
		
		
		Problem problem = new ProblemReader().read(args[0]);
		
		
		Builder<SingleObjectiveEvolutionaryAlgorithm> ea = new Builder<SingleObjectiveEvolutionaryAlgorithm>(SingleObjectiveEvolutionaryAlgorithm.class);
		ea
			.set("populationSize", 100)
			.set("probMutation", 0.3)
			.set("factory", new GroupFactory())
			.set("crossover", new OrderedCrossover<>())
			.set("mutation", new SwapMutation<>());

		SingleObjectiveEvolutionaryAlgorithm algorithm = ea.build();
		NonDominatedSolutionSet set = algorithm.run(problem, new Evaluator(100000), new MyRandom());

		System.out.println(set);

		if (set.getSolutions().isEmpty()) {
			System.err.println("No Solution found.");
			return;
		}
		
/*		
		for(Solution s :  algorithm.getPopulation()) {
			System.out.println(s);
		}
		System.out.println("----------------------------------------\n");
		*/
		
		GroupVariable var = (GroupVariable) set.get(0).getVariable();
		System.out.println(var.report());
		System.out.println("----------------------------------------\n");
		System.out.println(var.print());

	}

}
