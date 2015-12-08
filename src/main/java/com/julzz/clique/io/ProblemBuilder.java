package com.julzz.clique.io;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.julzz.clique.group.Member;
import com.julzz.clique.group.Problem;
import com.julzz.clique.group.ProblemDescription;

/**
 * This class defines the problem and could be read or parsed from a file.
 *
 */
public class ProblemBuilder {

	// ! persons in a group for the final result
	protected List<Integer> groupLimits = new ArrayList<>();

	// ! all members that exist in the search space
	protected Set<PlainObjectMember> members = new HashSet<>();

	// ! hard constraint which constellation in one group is not allowed
	protected Set<Set<String>> notInOneGroup = new HashSet<Set<String>>();

	// ! hard constraint which constellation in one group is allowed
	protected Set<Set<String>> inOneGroup = new HashSet<Set<String>>();
	
	// ! map of each member to be in a group with a set of others
	private Map<String, PlainObjectMember> hash = new HashMap<String, PlainObjectMember>();

	
	public boolean addMember(String name) {
		PlainObjectMember m = new PlainObjectMember(name);

		// check if member does exist
		if (hash.containsKey(name))
			throw new RuntimeException(String.format("Member %s does exist. Please choose another name to avoid duplicates!", name));

		hash.put(name, m);
		return members.add(m);
	}

	/**
	 * Add several member at once
	 * 
	 * @param names
	 *            list of names as string
	 */
	public void addMember(Collection<String> names) {
		for (String n : names) {
			addMember(n); 
		}
	}
	
	private PlainObjectMember getMember(String name) {
		if (!hash.containsKey(name)) memberNotFound(name);
		return hash.get(name);
	}

	public void addPreference(String name, Collection<String> inGroupWith) {
		getMember(name).preferences.addAll(inGroupWith);
	}

	public void addRejection(String name, Collection<String> notInGroupWith) {
		getMember(name).rejections.addAll(notInGroupWith);
	}


	public boolean addForbiddenGroup(Collection<String> forbidden) {
		return notInOneGroup.add(new HashSet<String>(forbidden));
	}
	
	public boolean addForcedGroup(Collection<String> group) {
		return inOneGroup.add(new HashSet<String>(group));
	}



	public Problem build() {

		final int sumOfGroups = groupLimits.stream().mapToInt(i -> i.intValue()).sum();
		if (members.size() != sumOfGroups) 
			throw new RuntimeException(String.format("Groups are %s which is in sum %s, but there are %s members!",
					Arrays.toString(groupLimits.toArray()), sumOfGroups, members.size()));
		
		Map<String, Member> objMembers = new HashMap<String, Member>();
		
		// create all members
		for (PlainObjectMember pojo : members) {
			Member obj = new Member(pojo.name);
			objMembers.put(pojo.name, obj);
		}

		// for every member
		for (PlainObjectMember pojo : members) {
			Member obj = objMembers.get(pojo.name);

			// add all the preferences
			for (String pref : pojo.preferences) {
				if (!objMembers.containsKey(pref)) memberNotFoundButAdded(pref, pojo.name);
				obj.addPreference(objMembers.get(pref));
			}

			// add all the rejections
			for (String rej : pojo.rejections) {
				if (!objMembers.containsKey(rej)) memberNotFoundButAdded(rej, pojo.name);
				obj.addRejection(objMembers.get(rej));
			}

		}
		
		
		// create mebers as objects for forbidden groups
		Set<Set<Member>> objNotInOneGroup = createMemberSet(notInOneGroup, objMembers);
		Set<Set<Member>> objinOneGroup = createMemberSet(inOneGroup, objMembers);
		
		
		
		return new Problem(new ProblemDescription(groupLimits, new HashSet<>(objMembers.values()), objNotInOneGroup, objinOneGroup));

	}

	private Set<Set<Member>> createMemberSet(Set<Set<String>> group, Map<String, Member> mapping) {
		Set<Set<Member>> result = new HashSet<>();
		for (Set<String> set : group) {
			Set<Member> objGroup = new HashSet<>();
			for (String s : set) {
				if (!mapping.containsKey(s)) memberNotFound(s);
				objGroup.add(mapping.get(s));
			}
			result.add(objGroup);
		}
		return result;
	}
	
	@Override
	public String toString() {
		Gson gson =  new GsonBuilder().setPrettyPrinting().create();
		JsonObject obj = gson.toJsonTree(this).getAsJsonObject();
		obj.remove("hash");
		String json = gson.toJson(obj);
		return json;
	}
	
	
	
	public void setGroupLimits(List<Integer> groupLimits) {
		this.groupLimits = groupLimits;
	}

	private void memberNotFound(String name) throws RuntimeException{
		throw new RuntimeException(String.format("Member %s not found, but added either as preference or rejection.", name));
	}
	
	private void memberNotFoundButAdded(String name, String added) throws RuntimeException{
		throw new RuntimeException(String.format("Member %s not found, but added either as preference or rejection to %s.", name, added));
	}

}