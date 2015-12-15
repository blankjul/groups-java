package com.julzz.groups.io;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Member which could be part of a group or not. Preference to other members or
 * rejections are stored an could be asked.
 */
public class PlainObjectMember {

	// name of the member
	protected String name;

	// preferences to be in a group
	protected Set<String> preferences = new LinkedHashSet<>();

	// rejects to be in a group with
	protected Set<String> rejections = new LinkedHashSet<>();

	
	public PlainObjectMember(String name) {
		super();
		this.name = name;
	}



}
