package com.julzz.groups.model;

import java.util.Collection;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.msu.model.AProblem;
import com.msu.util.exceptions.EvaluationException;

public class Problem extends AProblem<GroupVariable> {

	// ! object that contains all the preferences, rejections and constrains
	protected ProblemDescription desc;

	public Problem(ProblemDescription desc) {
		super();
		this.desc = desc;
	}

	@Override
	public int getNumberOfConstraints() {
		return 1;
	}
	
	@Override
	public int getNumberOfObjectives() {
		return 1;
	}


	@Override
	protected void evaluate_(GroupVariable var, List<Double> objectives, List<Double> constraintViolations) {

		if (var.size() != desc.numOfMembers())
			throw new EvaluationException(
					String.format("Unable to evaluate. Size of Variable %s, but size of all members %s.", var.size(),
							desc.getNumOfPersonsInGroup()));

		// all subgroups created from the decoded List
		Collection<Set<Member>> subgroups = var.getSubgroups();

		// check for hard constraints
		int violatedIsInGroup = desc.inOneGroup.size() - ProblemUtil.calcPresentSubsetGroups(subgroups, desc.inOneGroup);
		int violatedNotInGroup = ProblemUtil.calcPresentSubsetGroups(subgroups, desc.notInOneGroup);
		constraintViolations.add((double) violatedIsInGroup + violatedNotInGroup);
		
		// calculate preferences and rejections of each member
		Map<Member, Integer> prefs = ProblemUtil.calcPresentPreferences(subgroups);
		Map<Member, Integer> rejs = ProblemUtil.calcConsideredRejections(subgroups);

		// calculate the individual satisfaction of each member
		DoubleSummaryStatistics statistics = new DoubleSummaryStatistics();
		for (Member m : desc.members) {
			double satisfaction = prefs.get(m) + rejs.get(m);
			statistics.accept(satisfaction);		
		}
		
		objectives.add(-statistics.getSum());
		//objectives.add(-statistics.getMin());
		
	}
	
	

	

	public ProblemDescription getDescription() {
		return desc;
	}


}
