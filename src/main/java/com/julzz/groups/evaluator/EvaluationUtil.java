package com.julzz.groups.evaluator;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class EvaluationUtil {

	/**
	 * @param m1
	 *            the first group
	 * @param m2
	 *            the second group
	 * @return a set of members which are in both groups
	 */
	public static <T> Set<T> intersect(Collection<T> m1, Collection<T> m2) {
		Set<T> hash = new HashSet<>(m1);
		Set<T> result = new HashSet<>();
		for (T m : m2)
			if (hash.contains(m))
				result.add(m);
		return result;
	}
	
	
}
