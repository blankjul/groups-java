package com.julzz.clique.group;
import java.util.ArrayList;
import java.util.List;

import com.msu.interfaces.IProblem;
import com.msu.interfaces.IVariable;
import com.msu.model.AVariableFactory;
import com.msu.util.MyRandom;

/**
 * This class creates random group constellations in the beginning. It simply shuffles a list.
 */
public class GroupFactory extends AVariableFactory {

	public IVariable next(IProblem problem, MyRandom rand) {
		GroupProblem group = (GroupProblem) problem;
		List<String> members = new ArrayList<String>(group.getDescription().getMembers());
		rand.shuffle(members);
		return new GroupVariable(members);
	}

}
