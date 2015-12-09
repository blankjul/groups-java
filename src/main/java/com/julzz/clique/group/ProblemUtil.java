package com.julzz.clique.group;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ProblemUtil {
	
	
	
	/**
	 * @param m1 the first group
	 * @param m2 the second group
	 * @return a set of members which are in both groups
	 */
	public static Set<Member> intersect(Collection<Member> m1, Collection<Member> m2) {
		Set<Member> hash = new HashSet<>(m1);
		Set<Member> result = new HashSet<>();
		for (Member m : m2) if (hash.contains(m)) result.add(m);
		return result;
	}
	
	
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
			if (isPresent) counter++;
		}
		return counter;
	}
	
	
	public static Map<Member, Integer> calcPresentPreferences(Collection<Set<Member>> subgroups) {
		Map<Member, Integer> map = new HashMap<>();
		for (Set<Member> group : subgroups) {
			for (Member member : group) {
				int pref = ProblemUtil.intersect(group, member.getPreferences()).size();
				map.put(member, pref);
			}
		}
		return map;
	}
	
	
	public static Map<Member, Integer> calcConsideredRejections(Collection<Set<Member>> subgroups) {
		Map<Member, Integer> map = new HashMap<>();
		for (Set<Member> group : subgroups) {
			for (Member member : group) {
				
				int rejs = member.getRejections().size() - 
						ProblemUtil.intersect(group, member.getRejections()).size();
				
				map.put(member, rejs);
			}
		}
		return map;
	}
	

}
