package com.julzz.clique.group;

import java.util.List;
import java.util.Set;

import com.msu.soo.ASingleObjectiveProblem;
import com.msu.util.exceptions.EvaluationException;

public class GroupProblem extends ASingleObjectiveProblem<GroupVariable> {

	// ! object that contains all the preferences, rejections and constrains
	protected GroupDescription desc;

	public GroupProblem(GroupDescription desc) {
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

		List<Set<Member>> subgroups = var.getSubgroups(desc.getNumOfPersonsInGroup());

		for (Set<Member> group : subgroups) {

			for (Member member : group) {

				// add points for found preferences
				List<Member> prefs = member.getPreferences();
				for (int i = 0; i < prefs.size(); i++) {
					if (group.contains(prefs.get(i))) objective -= Math.pow(0.9, i);
				}
				
				// penalize rejections
				List<Member> rejs  = member.getRejections();
				for (int i = 0; i < rejs.size(); i++) {
					if (group.contains(rejs.get(i))) objective += Math.pow(0.9, i);
				}

			}

		}

		// check for hard constraints
		for (Set<String> forbiddenInOneGroup : desc.notInOneGroup) {
			boolean isViolated = false;
			for (Set<Member> group : subgroups) {
				if (group.containsAll(forbiddenInOneGroup)) {
					isViolated = true;
					break;
				}
			}
			if (isViolated) constraints++;
		}

		constraintViolations.add(constraints);
		objectives.add(objective);

	}

	public GroupDescription getDescription() {
		return desc;
	}

}
