package com.julzz.groups.evolutionary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.julzz.groups.model.Member;
import com.julzz.groups.model.ProblemDescription;
import com.msu.moo.interfaces.ICrossover;
import com.msu.moo.util.MyRandom;

public class GroupCrossover implements ICrossover<GroupVariable> {

	private ProblemDescription desc;

	public GroupCrossover(ProblemDescription desc) {
		super();
		this.desc = desc;
	}

	@Override
	public List<GroupVariable> crossover(GroupVariable a, GroupVariable b, MyRandom rand) {

		Set<Set<Member>> result = new HashSet<>();

		List<Member> members = new ArrayList<>(desc.getMembers());
		Member next = rand.select(members);
		Set<Member> added = new HashSet<>();
		Set<Member> left = new HashSet<>(desc.getMembers());

		for (int nunOfMember : desc.getGroupLimits()) {
			Set<Member> group = new HashSet<>();
			for (int i = 0; i < nunOfMember; i++) {

				Set<Member> friends = new HashSet<>();
				friends.addAll(a.getGroupOf(next));
				friends.addAll(b.getGroupOf(next));
				friends.remove(next);
				friends.removeAll(added);

				if (!friends.isEmpty())
					next = rand.select(new ArrayList<>(friends));
				else
					next = rand.select(new ArrayList<>(left));

				group.add(next);
				added.add(next);
				left.remove(next);
			}
			result.add(group);

		}

		GroupVariable var = new GroupVariable(result);

		return Arrays.asList(var);
	}

}
