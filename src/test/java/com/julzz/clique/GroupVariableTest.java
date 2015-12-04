package com.julzz.clique;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.julzz.clique.group.GroupVariable;

public class GroupVariableTest {

	
	@Test
	public void testSubgroups() {
		GroupVariable var = new GroupVariable(Arrays.asList("Julian", "Peter", "Max", "Ali", "Robert"));
		List<Set<String>> subgroups = var.getSubgroups(2);
		
		List<Set<String>> exspected = new ArrayList<Set<String>>();
		exspected.add(new HashSet<String>(Arrays.asList("Julian", "Peter")));
		exspected.add(new HashSet<String>(Arrays.asList("Max", "Ali")));
		exspected.add(new HashSet<String>(Arrays.asList("Robert")));
		
		assertEquals(exspected, subgroups);
	}
	
	
	
	
	
}
