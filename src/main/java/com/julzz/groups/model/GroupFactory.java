package com.julzz.groups.model;
import java.util.ArrayList;
import java.util.List;

import com.msu.moo.interfaces.IFactory;
import com.msu.moo.util.MyRandom;

/**
 * This class creates random group constellations in the beginning. It simply shuffles a list.
 */
public class GroupFactory implements IFactory<GroupVariable> {

	protected Problem problem;
	
	
	public GroupFactory(Problem problem) {
		super();
		this.problem = problem;
	}

	@Override
	public GroupVariable next(MyRandom rand) {
		List<Member> members = new ArrayList<Member>(problem.getDescription().getMembers());
		rand.shuffle(members);
		return new GroupVariable(problem.desc, members);
	}

	@Override
	public boolean hasNext() {
		return true;
	}

}
