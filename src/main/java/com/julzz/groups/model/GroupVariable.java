package com.julzz.groups.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.msu.moo.model.variable.Variable;

/**
 * This is the group variable which defines a variable for the given problem.
 * 
 * The List of Strings defines by using the order the subgroups depending on the
 * subgroup size.
 *
 */
public class GroupVariable extends Variable<Set<Set<Member>>> {


	public GroupVariable(Set<Set<Member>> names) {
		super(names);
	}
	
	@Override
	public GroupVariable copy() {
		Set<Set<Member>> next = new HashSet<>();
		for (Set<Member> group : obj) {
			next.add(new HashSet<>(group));
		}
		return new GroupVariable(next);
	}
	
	
	@Override
	public GroupVariable build(Set<Set<Member>> obj) {
		return new GroupVariable(obj);
	}
	
	public Set<Member> getGroupOf(Member m) {
		for (Set<Member> group : obj) {
			if (group.contains(m)) return group;
		}
		return null;
	}

	@Override
	public boolean equals(Object obj) {
		GroupVariable other = (GroupVariable) obj;
		for (Set<Member> group : this.obj) {
			if (!other.obj.contains(group)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		
		List<Integer> hashes = new ArrayList<>();
		obj.forEach(group -> hashes.add(group.hashCode()));
		Collections.sort(hashes);
		
		final int prime = 31;
		int result = 0;
		for (int n : hashes) {
			result = prime * result + n;
		}
		return result;
	}
	
	
	public boolean isValid(ProblemDescription desc) {
		
		// has duplicates
		Set<Member> all = new HashSet<>();
		obj.forEach(group -> group.forEach(m -> all.add(m)));
	    if (desc.getNumOfMembers() != all.size()) return false;
		
	    // check if all groups with correct size do exist in variable
	    List<Integer> groupsOrg = new ArrayList<>(desc.getGroupLimits());
	    Collections.sort(groupsOrg);
	    
	    List<Integer> groupsCounts = new ArrayList<>();
	    obj.forEach(group -> groupsCounts.add(group.size()));
	    Collections.sort(groupsCounts);
	    
	    if (!groupsOrg.equals(groupsCounts)) return false;
	    
	    return true;
	}
	
	

	public String print() {
		StringBuilder sb = new StringBuilder();
		int counter = 0;
		for (Set<Member> group : obj) {
			sb.append(String.format("Group %s: ", ++counter));
			for (Member m : group) {
				sb.append(String.format("%s | ", m));
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	


}
