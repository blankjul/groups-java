package com.julzz.clique.group;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
public class GroupVariable extends ListVariable<Member> {

	//! problem description of the variable
	protected ProblemDescription desc;
	
	//! encoded as subgroups
	protected Collection<Set<Member>> subgroups;
	
	public GroupVariable(ProblemDescription desc, List<Member> names) {
		super(names);
		this.desc = desc;
	}

	public Collection<Set<Member>> getSubgroups() {
		
		if (subgroups == null) {
			subgroups = new HashSet<>();
			Set<Member> current = new HashSet<Member>();
			int counter = 0;
			for (int i = 0; i < obj.size(); i++) {
				current.add(obj.get(i));
				if (current.size() == desc.groupLimits.get(counter)) {
					subgroups.add(current);
					counter++;
					current = new HashSet<Member>();
				}
			}
			if (!current.isEmpty())
				subgroups.add(current);
		}
		
		return subgroups;
	}

	@Override
	public IVariable copy() {
		return new GroupVariable(desc, new ArrayList<Member>(obj));
	}

	public String print() {
		StringBuilder sb = new StringBuilder();
		int counter = 0;
		for (Set<Member> group : getSubgroups()) {
			sb.append("Team " + ++counter + ": ");
			sb.append(Arrays.toString(group.toArray()));
			sb.append("\n");
		}
		return sb.toString();
	}


	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		GroupVariable other = (GroupVariable) obj;
		return getSubgroups().equals(other.getSubgroups());
	}

	@Override
	public int hashCode() {
		return getSubgroups().hashCode();
	}

	
	public String report() {
		Collection<Set<Member>> subgroups = getSubgroups();
		
		int numOfPreferences = 0;
		int numOfRejections = 0;
		int allPreferences = 0;
		int allOfRejections = 0;
		
		StringBuilder sb = new StringBuilder();
		for (Set<Member> group : subgroups) {

			for (Member name : group) {
				sb.append("----------------------------------------\n");

				sb.append(String.format("%s: \n\n", name));

				if (!name.getPreferences().isEmpty()) sb.append(String.format("Preferences (x if fullflilled):  \n"));
				for (Member pref : name.getPreferences()) {
					if (group.contains(pref)) {
						sb.append(String.format("\tx %s\n", pref));
						numOfPreferences++;
					} else {
						sb.append(String.format("\t  %s\n", pref));
					}
					allPreferences++;
				}

				// penalize rejections
				if (!name.getRejections().isEmpty())  sb.append(String.format("Rejections (x if considered): \n"));
				for (Member rej : name.getRejections()) {
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
		for (Set<Member> forbiddenInOneGroup : desc.notInOneGroup) {
			boolean isViolated = false;
			for (Set<Member> group : subgroups) {
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
		
		sb.append(String.format("Constraints (x if in the same group): \n"));
		// check for hard constraints
		for (Set<Member> forcedGroup : desc.inOneGroup) {
			boolean isPresent = false;
			for (Set<Member> group : subgroups) {
				if (group.containsAll(forcedGroup)) {
					isPresent = true;
					break;
				}
			}
			if (isPresent) {
				sb.append(String.format("\tx %s\n", forcedGroup));
			} else {
				sb.append(String.format("\t  %s\n", forcedGroup));
			}
		}

		
		sb.append("----------------------------------------\n");
		sb.append(String.format("Preferences fullfilled: %s / %s\n", numOfPreferences, allPreferences));
		sb.append(String.format("Rejections considered: %s / %s\n", numOfRejections, allOfRejections));

		return sb.toString();

	}

}
