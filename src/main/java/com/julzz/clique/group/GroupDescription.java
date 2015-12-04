package com.julzz.clique.group;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class defines the problem and could be read or parsed from a file.
 *
 */
public class GroupDescription {
	
	//! persons in a group for the final result
	protected int numOfPersonsInGroup;

	//! all members that exist in the search space
	protected Set<Member> members = new HashSet<Member>();
	
	protected Map<String,Member> hash = new HashMap<>();
	
	//! hard constraint which constellation in one group is not allowed
	protected Set<Set<String>> notInOneGroup = new HashSet<Set<String>>();

	
	public boolean addMember(String name) {
		Member m = new Member(name);
		hash.put(name, m);
		return members.add(m);
	}
	
	
	public void addMember(List<String> names) {
		for (String n : names) members.add(new Member(n));
	}
	
	
	public void addPreference(String name, List<String> inGroupWith) {
		for(String other : inGroupWith) hash.get(name).addPreference(hash.get(other));
	}
	
	public void addRejection(String name, List<String> notInGroupWith) {
		for(String other : notInGroupWith) hash.get(name).addRejection(hash.get(other));
	}
	
	public boolean addForbiddenGroup(Set<String> forbidden) {
		return notInOneGroup.add(forbidden);
	}
	
	public boolean addForbiddenGroup(List<String> forbidden) {
		return notInOneGroup.add(new HashSet<String>(forbidden));
	}
	

	
	public Set<Set<String>> getForbiddenGroupConstelation() {
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
