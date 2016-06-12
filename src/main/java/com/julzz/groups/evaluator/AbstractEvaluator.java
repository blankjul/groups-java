package com.julzz.groups.evaluator;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.julzz.groups.evolutionary.GroupVariable;
import com.julzz.groups.model.Member;
import com.julzz.groups.model.ProblemDescription;

/**
 * This class represents an abstract object for every evaluation class (subjective for each member).
 * 
 * A complex evaluation might have several sub evaluations that are necessary.
 * Therefore this class is a template for all sub evaluations that consists of
 * the subject view of one member.
 * 
 *
 */
public abstract class AbstractEvaluator {

	abstract public double evaluate(Member m, Set<Member> group);

	public Map<Member, Double> evaluate(GroupVariable v, ProblemDescription desc) {
		Map<Member, Double> map = new HashMap<>();
		for (Set<Member> group : v.decode()) {
			for (Member m : group) {
				if (!desc.getMembers().contains(m))
					throw new RuntimeException(
							String.format("Evaluating Member %s which is not contained in members.", m));

				map.put(m, evaluate(m, group));

			}
		}
		return map;
	}

}
