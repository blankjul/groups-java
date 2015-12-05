package com.julzz.clique.io;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.julzz.clique.group.ProblemDescription;
import com.julzz.clique.group.Problem;
import com.julzz.clique.group.Member;

/**
 * This class defines the problem and could be read or parsed from a file.
 *
 */
public class ProblemBuilder {

	// ! persons in a group for the final result
	protected int numOfPersonsInGroup;

	// ! all members that exist in the search space
	protected Set<PlainObjectMember> members = new HashSet<>();

	// ! hard constraint which constellation in one group is not allowed
	protected Set<Set<String>> notInOneGroup = new HashSet<Set<String>>();

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
	public void addMember(List<String> names) {
		for (String n : names) {
			addMember(n); 
		}
	}
	
	private PlainObjectMember getMember(String name) {
		if (!hash.containsKey(name)) throw new RuntimeException(String.format("Member %s not found, but added either as preference or rejection.", name));
		return hash.get(name);
	}

	public void addPreference(String name, List<String> inGroupWith) {
		getMember(name).preferences.addAll(inGroupWith);
	}

	public void addRejection(String name, List<String> notInGroupWith) {
		getMember(name).rejections.addAll(notInGroupWith);
	}

	public boolean addForbiddenGroup(Set<String> forbidden) {
		return notInOneGroup.add(forbidden);
	}

	public boolean addForbiddenGroup(List<String> forbidden) {
		return notInOneGroup.add(new HashSet<String>(forbidden));
	}
	

	public void setNumOfPersonsInGroup(int numOfPersonsInGroup) {
		this.numOfPersonsInGroup = numOfPersonsInGroup;
	}

	public Problem build() {

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
				obj.addPreference(objMembers.get(pref));
			}

			// add all the rejections
			for (String rej : pojo.rejections) {
				obj.addRejection(objMembers.get(rej));
			}

		}
		
		
		// create mebers as objects for forbidden groups
		Set<Set<Member>> objNotInOneGroup = new HashSet<>();
		for (Set<String> set : notInOneGroup) {
			Set<Member> objGroup = new HashSet<>();
			for (String s : set) {
				objGroup.add(objMembers.get(s));
			}
			objNotInOneGroup.add(objGroup);
		}
		
		return new Problem(new ProblemDescription(numOfPersonsInGroup, new HashSet<>(objMembers.values()), objNotInOneGroup));

	}

	@Override
	public String toString() {
		Gson gson =  new GsonBuilder().setPrettyPrinting().create();
		JsonObject obj = gson.toJsonTree(this).getAsJsonObject();
		obj.remove("hash");
		String json = gson.toJson(obj);
		return json;
	}
	
	
	

}