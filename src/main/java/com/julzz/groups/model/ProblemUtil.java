package com.julzz.groups.model;

import java.util.Collection;
import java.util.Set;

public class ProblemUtil {



	public static int calcPresentSubsetGroups(Collection<Set<Member>> subgroups, Set<Set<Member>> subset) {
		int counter = 0;
		for (Set<Member> subsetGroup : subset) {
			boolean isPresent = false;
			for (Set<Member> group : subgroups) {
				if (group.containsAll(subsetGroup)) {
					isPresent = true;
					break;
				}
			}
			if (isPresent)
				counter++;
		}
		return counter;
	}



	

}
