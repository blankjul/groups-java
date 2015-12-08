package com.julzz.clique.group;

import java.util.List;
import java.util.Set;

import com.msu.soo.ASingleObjectiveProblem;
import com.msu.util.exceptions.EvaluationException;

public class Problem extends ASingleObjectiveProblem<GroupVariable> {

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
	protected void evaluate_(GroupVariable var, List<Double> objectives, List<Double> constraintViolations) {

		if (var.size() != desc.numOfMembers())
			throw new EvaluationException(
					String.format("Unable to evaluate. Size of Variable %s, but size of all members %s.", var.size(),
							desc.getNumOfPersonsInGroup()));

		
		double objective = 0;
		double constraints = 0;

		List<Set<Member>> subgroups = var.getSubgroups(desc.groupLimits);

		for (Set<Member> group : subgroups) {

			for (Member member : group) {

				// add points for found preferences
				double max = 0;
				for (int i = 0; i < member.getPreferences().size(); i++) max += Math.pow(0.9, i);
				int counter = 0;
				int value = 0;
				for (Member other : group) if (member.prefers(other)) value += Math.pow(0.9, counter++);
				
				objective -= value / max;
				
				// penalize rejections
				max = 0;
				for (int i = 0; i < member.getRejections().size(); i++) max += Math.pow(0.9, i);
				counter = 0;
				value = 0;
				for (Member other : group) if (member.rejects(other)) value += Math.pow(0.9, counter++);
				
				objective += value / max;
				
			}

		}

		// check for hard constraints
		for (Set<Member> forbiddenInOneGroup : desc.notInOneGroup) {
			boolean isViolated = false;
			for (Set<Member> group : subgroups) {
				if (group.containsAll(forbiddenInOneGroup)) {
					isViolated = true;
					break;
				}
			}
			if (isViolated) constraints++;
		}
		
		// check for hard constraints 
		for (Set<Member> forcedGroup : desc.inOneGroup) {
			boolean isPresent = false;
			for (Set<Member> group : subgroups) {
				if (group.containsAll(forcedGroup)) {
					isPresent = true;
					break;
				}
			}
			if (!isPresent) constraints++;
		}

		constraintViolations.add(constraints);
		objectives.add(objective);

	}
	
	

	public ProblemDescription getDescription() {
		return desc;
	}

}
