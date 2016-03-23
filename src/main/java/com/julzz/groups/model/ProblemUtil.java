package com.julzz.groups.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ProblemUtil {

	/**
	 * @param m1
	 *            the first group
	 * @param m2
	 *            the second group
	 * @return a set of members which are in both groups
	 */
	public static Set<Member> intersect(Collection<Member> m1, Collection<Member> m2) {
		Set<Member> hash = new HashSet<>(m1);
		Set<Member> result = new HashSet<>();
		for (Member m : m2)
			if (hash.contains(m))
				result.add(m);
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
			if (isPresent)
				counter++;
		}
		return counter;
	}

	public static Map<String, Integer> calcPresentPreferences(Collection<Set<Member>> subgroups) {
		Map<String, Integer> map = new HashMap<>();
		for (Set<Member> group : subgroups) {
			for (Member member : group) {
				int pref = ProblemUtil.intersect(group, member.getPreferences()).size();
				map.put(member.name, pref);
			}
		}
		return map;
	}

	public static Map<String, Integer> calcConsideredRejections(Collection<Set<Member>> subgroups) {
		Map<String, Integer> map = new HashMap<>();
		for (Set<Member> group : subgroups) {
			for (Member member : group) {

				int rejs = member.getRejections().size() - ProblemUtil.intersect(group, member.getRejections()).size();

				map.put(member.name, rejs);
			}
		}
		return map;
	}

	public static Collection<Set<Member>> getSubgroups(List<Integer> groupLimits, GroupVariable gp) {

		Collection<Set<Member>> subgroups = new HashSet<>();
		List<Member> obj = gp.decode();

		subgroups = new HashSet<>();
		Set<Member> current = new HashSet<>();
		int counter = 0;
		for (int i = 0; i < obj.size(); i++) {
			current.add(obj.get(i));
			if (current.size() == groupLimits.get(counter)) {
				subgroups.add(current);
				counter++;
				current = new HashSet<>();
			}
		}
		if (!current.isEmpty())
			subgroups.add(current);

		return subgroups;
	}

}
