package com.julzz.groups.recombination;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Set;

import com.julzz.groups.model.GroupVariable;
import com.julzz.groups.model.Member;
import com.msu.moo.interfaces.IMutation;
import com.msu.moo.util.MyRandom;

public class GroupMutation implements IMutation<GroupVariable>{

	@Override
	public void mutate(GroupVariable a, MyRandom rand) {
		
		LinkedList<Set<Member>> groups = new LinkedList<>(a.decode());
		rand.shuffle(groups);
		
		Set<Member> s1 = groups.get(0);
		Set<Member> s2= groups.get(1);
		
		Member m1 = rand.select(new ArrayList<>(s1));
		Member m2 = rand.select(new ArrayList<>(s2));
		
		s1.remove(m1);
		s2.remove(m2);
		
		s1.add(m2);
		s2.add(m1);

		
	}
	
	
	

}
