package com.julzz.groups.evaluator;

import java.util.Set;

import com.julzz.groups.model.Member;

/**
 * Evaluate the satisfaction of each member according to the avoided rejections.
 * 
 */
public class RejectionEvaluator extends AbstractEvaluator
{
	
	@Override
	public double evaluate(Member m, Set<Member> group) {
		final int allRejs = m.getRejections().size();
		if (allRejs == 0) return 1.0;
		final int activeRejs = EvaluationUtil.intersect(group, m.getRejections()).size();
		return  (allRejs - activeRejs) / (double) allRejs;
	}

}
