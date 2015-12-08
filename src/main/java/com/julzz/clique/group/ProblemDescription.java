package com.julzz.clique.group;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class defines the problem and could be read or parsed from a file.
 *
 */
public class ProblemDescription {
	
	//! persons in a group for the final result
	protected List<Integer> groupLimits;

	//! all members that exist in the search space
	protected Set<Member> members = new HashSet<Member>();
	
	//! hard constraint which constellation in one group is not allowed
	protected Set<Set<Member>> notInOneGroup = new HashSet<Set<Member>>();

	//! hard constraint which constellation in one group should always hold
	protected Set<Set<Member>> inOneGroup = new HashSet<Set<Member>>();
	
	
	public ProblemDescription(List<Integer> groupLimits, Set<Member> members, Set<Set<Member>> notInOneGroup, Set<Set<Member>> inOneGroup) {
		super();
		this.groupLimits = groupLimits;
		this.members = members;
		this.notInOneGroup = notInOneGroup;
		this.inOneGroup = inOneGroup;
	}

	public Set<Set<Member>> getForbiddenGroupConstelation() {
		return notInOneGroup;
	}

	public Set<Member> getMembers() {
		return members;
	}

	public int getNumOfPersonsInGroup() {
		return groupLimits.stream().mapToInt(i -> i.intValue()).sum();
	}

	public int numOfMembers() {
		return members.size();
	}
	
}
