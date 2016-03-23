package com.julzz.groups.model;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Member which could be part of a group or not. Preference to other members or
 * rejections are stored an could be asked.
 */
public class Member {

	// name of the member
	String name;

	// preferences to be in a group
	Set<Member> preferences = new LinkedHashSet<>();

	// rejects to be in a group with
	Set<Member> rejections = new LinkedHashSet<>();

	
	public Member(String name) {
		super();
		this.name = name;
	}

	public boolean addPreference(Member m) {
		return preferences.add(m);
	}

	public boolean addRejection(Member m) {
		return rejections.add(m);
	}

	public boolean prefers(Member m) {
		if (preferences.contains(m))
			return true;
		return false;
	}

	public boolean rejects(Member m) {
		if (rejections.contains(m))
			return true;
		return false;
	}

	public Set<Member> getPreferences() {
		return preferences;
	}

	public Set<Member> getRejections() {
		return rejections;
	}
        
        public String getName() {
            return name;
        }

	@Override
	public String toString() {
		return name;
	}
	
	
	

}
