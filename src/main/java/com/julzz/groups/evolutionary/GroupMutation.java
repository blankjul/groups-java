package com.julzz.groups.evolutionary;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Set;

import com.julzz.groups.model.Member;
import com.msu.moo.interfaces.IMutation;
import com.msu.moo.util.MyRandom;

/**
 * This class represents the mutation operation for the GroupVariable.
 * 
 * One Swap from one member to another group is performed. Since the
 * representation is a set of sets it is ensured that one swap is done.
 *
 */
public class GroupMutation implements IMutation<GroupVariable> {

	@Override
	public void mutate(GroupVariable a, MyRandom rand) {

		LinkedList<Set<Member>> groups = new LinkedList<>(a.decode());
		rand.shuffle(groups);

		Set<Member> s1 = groups.get(0);
		Set<Member> s2 = groups.get(1);

		Member m1 = rand.select(new ArrayList<>(s1));
		Member m2 = rand.select(new ArrayList<>(s2));

		s1.remove(m1);
		s2.remove(m2);

		s1.add(m2);
		s2.add(m1);

	}

}
