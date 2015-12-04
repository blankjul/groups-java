package com.julzz.clique.group;

import java.util.ArrayList;
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
	protected Set<String> members = new HashSet<String>();
	
	//! map of each member to be in a group with a set of others
	protected Map<String,List<String>> preferenes = new HashMap<String,List<String>>();
	
	//! map of each member to rejected members 
	protected Map<String,List<String>> rejections = new HashMap<String,List<String>>();
	
	//! hard constraint which constellation in one group is not allowed
	protected Set<Set<String>> notInOneGroup = new HashSet<Set<String>>();

	
	public boolean addMember(String name) {
		return members.add(name);
	}
	
	public void addMember(List<String> names) {
		members.addAll(names);
	}
	
	
	public void addPreference(String name, List<String> inGroupWith) {
		preferenes.put(name, inGroupWith);
	}
	
	public void addRejection(String name, List<String> notInGroupWith) {
		rejections.put(name, notInGroupWith);
	}
	
	public boolean addForbiddenGroup(Set<String> forbidden) {
		return notInOneGroup.add(forbidden);
	}
	
	public boolean addForbiddenGroup(List<String> forbidden) {
		return notInOneGroup.add(new HashSet<String>(forbidden));
	}
	
	public List<String> getPreferenceOf(String name) {
		if (!preferenes.containsKey(name)) return new ArrayList<String>(0);
		return preferenes.get(name);
	}
	
	public List<String> getRejectionsOf(String name) {
		if (!rejections.containsKey(name)) return new ArrayList<String>(0);
		return rejections.get(name);
	}
	
	public Set<Set<String>> getForbiddenGroupConstelation() {
		return notInOneGroup;
	}

	public Set<String> getMembers() {
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
