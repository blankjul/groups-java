package com.julzz.clique.group;

import java.util.HashSet;
import java.util.Set;

/**
 * This class defines the problem and could be read or parsed from a file.
 *
 */
public class ProblemDescription {
	
	//! persons in a group for the final result
	protected int numOfPersonsInGroup;

	//! all members that exist in the search space
	protected Set<Member> members = new HashSet<Member>();
	
	//! hard constraint which constellation in one group is not allowed
	protected Set<Set<Member>> notInOneGroup = new HashSet<Set<Member>>();

	
	public ProblemDescription(int numOfPersonsInGroup, Set<Member> members, Set<Set<Member>> notInOneGroup) {
		super();
		this.numOfPersonsInGroup = numOfPersonsInGroup;
		this.members = members;
		this.notInOneGroup = notInOneGroup;
	}

	public Set<Set<Member>> getForbiddenGroupConstelation() {
		return notInOneGroup;
	}

	public Set<Member> getMembers() {
		return members;
	}

	public int getNumOfPersonsInGroup() {
		return numOfPersonsInGroup;
	}

	public void setNumOfPersonsInGroup(int numOfPersonsInGroup) {
		this.numOfPersonsInGroup = numOfPersonsInGroup;
	}
	
	public int numOfMembers() {
		return members.size();
	}
	
}
