package com.julzz.groups.evaluator;

import java.util.Set;

import com.julzz.groups.model.Member;

/**
 * Evaluate the satisfaction of each member according to the fulfilled
 * preferences.
 */
public class PreferenceEvaluator extends AbstractEvaluator {

	@Override
	public double evaluate(Member m, Set<Member> group) {
		final int allPrefs = m.getPreferences().size();
		if (allPrefs == 0)
			return 1.0;
		final int activePrefs = EvaluationUtil.intersect(group, m.getPreferences()).size();
		return activePrefs / (double) allPrefs;
	}

}
