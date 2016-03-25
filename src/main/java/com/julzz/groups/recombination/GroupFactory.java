package com.julzz.groups.recombination;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import com.julzz.groups.model.GroupVariable;
import com.julzz.groups.model.Member;
import com.julzz.groups.model.ProblemDescription;
import com.msu.moo.interfaces.IFactory;
import com.msu.moo.util.MyRandom;

/**
 * This class creates random group constellations in the beginning. 
 */
public class GroupFactory implements IFactory<GroupVariable> {

	
	protected ProblemDescription desc;
	
	
	public GroupFactory(ProblemDescription problem) {
		super();
		this.desc = problem;
	}

	@Override
	public GroupVariable next(MyRandom rand) {
		
		if (desc.getNumOfPersonsInGroups() != desc.numOfMembers()) {
			throw new RuntimeException(String.format("Error while creating Group variable."));
		}
		
		Set<Set<Member>> obj = new HashSet<>();
		
		// get a list and shuffle
		LinkedList<Member> members = new LinkedList<>(desc.getMembers());
		rand.shuffle(members);
		
		for(int numOfMember : desc.getGroupLimits()) {
			// add an empty group
			Set<Member> group = new HashSet<>();
			for (int j = 0; j < numOfMember; j++) {
				Member toAdd = members.poll();
				group.add(toAdd);
			}
			obj.add(group);
		}
		
		return new GroupVariable(obj);
	}

	@Override
	public boolean hasNext() {
		return true;
	}

	
}
