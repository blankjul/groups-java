package com.julzz.groups.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.msu.moo.model.variable.Variable;

/**
 * This is the group variable which defines a variable for the given problem.
 * 
 * The List of Strings defines by using the order the subgroups depending on the
 * subgroup size.
 *
 */
public class GroupVariable extends Variable<List<Member>> {


	public GroupVariable(List<Member> names) {
		super(names);
	}
	
	@Override
	public Variable<List<Member>> copy() {
		return new GroupVariable(new ArrayList<Member>(obj));
	}
	
	
	@Override
	public Variable<List<Member>> build(List<Member> obj) {
		return new GroupVariable(obj);
	}


	public String print(Problem p) {
		StringBuilder sb = new StringBuilder();
		int counter = 0;
		for (Set<Member> group : ProblemUtil.getSubgroups(p.getDescription().getGroupLimits(), this)) {
			sb.append("Team " + ++counter + ": ");
			sb.append(Arrays.toString(group.toArray()));
			sb.append("\n");
		}
		return sb.toString();
	}
	


}
