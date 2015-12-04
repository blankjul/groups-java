package com.julzz.clique.group;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.msu.interfaces.IVariable;
import com.msu.model.variables.ListVariable;

/**
 * This is the group variable which defines a variable for the given problem.
 * 
 * The List of Strings defines by using the order the subgroups depending on the
 * subgroup size.
 *
 */
public class GroupVariable extends ListVariable<String> {

	public GroupVariable(List<String> names) {
		super(names);
	}

	public List<Set<String>> getSubgroups(int sizeOfGroups) {
		List<Set<String>> result = new ArrayList<Set<String>>();

		Set<String> current = new HashSet<String>();
		for (int i = 0; i < obj.size(); i++) {
			current.add(obj.get(i));
			if (current.size() == sizeOfGroups) {
				result.add(current);
				current = new HashSet<String>();
			}
		}
		if (!current.isEmpty())
			result.add(current);
		return result;
	}

	@Override
	public IVariable copy() {
		return new GroupVariable(new ArrayList<String>(obj));
	}

	public String print(int n) {
		StringBuilder sb = new StringBuilder();
		int counter = 0;
		for (Set<String> group : getSubgroups(n)) {
			sb.append("Team " + ++counter + ": ");
			sb.append(Arrays.toString(group.toArray()));
			sb.append("\n");
		}
		return sb.toString();
	}

	@Override
	public boolean isEqual(List<String> o1, List<String> o2) {
		return o1.equals(o2);
	}

	@Override
	public int hashCode() {
		return obj.hashCode();
	}

	public String report(GroupDescription desc) {
		List<Set<String>> subgroups = getSubgroups(desc.getNumOfPersonsInGroup());
		
		int numOfPreferences = 0;
		int numOfRejections = 0;
		int allPreferences = 0;
		int allOfRejections = 0;
		
		StringBuilder sb = new StringBuilder();
		for (Set<String> group : subgroups) {

			for (String name : group) {
				sb.append("----------------------------------------\n");

				sb.append(String.format("%s: \n\n", name));

				if (!desc.getPreferenceOf(name).isEmpty()) sb.append(String.format("Preferences (x if fullflilled):  \n"));
				for (String pref : desc.getPreferenceOf(name)) {
					if (group.contains(pref)) {
						sb.append(String.format("\tx %s\n", pref));
						numOfPreferences++;
					} else {
						sb.append(String.format("\t  %s\n", pref));
					}
					allPreferences++;
				}

				// penalize rejections
				if (!desc.getRejectionsOf(name).isEmpty()) sb.append(String.format("Rejections (x if considered): \n"));
				for (String rej : desc.getRejectionsOf(name)) {
					if (group.contains(rej)) {
						sb.append(String.format("\t  %s\n", rej));
					} else {
						sb.append(String.format("\tx %s\n", rej));
						numOfRejections++;
					}
					allOfRejections++;
				}
				sb.append("\n");

			}

		}

		// check for hard constraints
		sb.append("----------------------------------------\n");
		sb.append(String.format("Constraints (x if not in the same group): \n"));
		// check for hard constraints
		for (Set<String> forbiddenInOneGroup : desc.notInOneGroup) {
			boolean isViolated = false;
			for (Set<String> group : subgroups) {
				if (group.containsAll(forbiddenInOneGroup)) {
					isViolated = true;
					break;
				}
			}
			if (!isViolated) {
				sb.append(String.format("\tx %s\n", forbiddenInOneGroup));
			} else {
				sb.append(String.format("\t  %s\n", forbiddenInOneGroup));
			}
		}
		
		sb.append("----------------------------------------\n");
		sb.append(String.format("Preferences fullfilled: %s / %s\n", numOfPreferences, allPreferences));
		sb.append(String.format("Rejections considered: %s / %s\n", numOfRejections, allOfRejections));

		return sb.toString();

	}

}
