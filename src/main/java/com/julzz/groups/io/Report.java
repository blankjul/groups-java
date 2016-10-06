package com.julzz.groups.io;

import java.util.Collection;
import java.util.Set;

import com.julzz.groups.evolutionary.GroupVariable;
import com.julzz.groups.model.Member;
import com.julzz.groups.model.Problem;

public class Report {
	
	
	
	public static String create(Problem problem, GroupVariable var) {
		
		Collection<Set<Member>> subgroups = var.decode();
		
		int numOfPreferences = 0;
		int numOfRejections = 0;
		int allPreferences = 0;
		int allOfRejections = 0;
		
		StringBuilder sb = new StringBuilder();
		for (Set<Member> group : subgroups) {

			for (Member name : group) {
				sb.append("----------------------------------------\n");

				sb.append(String.format("%s: \n\n", name));

				if (!name.getPreferences().isEmpty()) sb.append(String.format("Preferences (x if fulflilled):  \n"));
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
		for (Set<Member> forbiddenInOneGroup : problem.getDescription().getNotInGroup()) {
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
		for (Set<Member> forcedGroup : problem.getDescription().getInGroup()) {
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
		sb.append(String.format("Preferences fulfilled: %s / %s\n", numOfPreferences, allPreferences));
		sb.append(String.format("Rejections considered: %s / %s\n", numOfRejections, allOfRejections));

		return sb.toString();

	}

}
