package com.julzz.evolutionary;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.julzz.groups.evolutionary.GroupFactory;
import com.julzz.groups.evolutionary.GroupMutation;
import com.julzz.groups.evolutionary.GroupVariable;
import com.julzz.groups.io.ProblemReader;
import com.julzz.groups.model.Member;
import com.julzz.groups.model.Problem;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.util.MyRandom;

import junit.framework.TestCase;

public class GroupVariableTest extends TestCase {

	protected Problem p = new ProblemReader().read("src/main/resources/december2015.json").build();;
	protected GroupVariable var;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		var = new GroupFactory(p.getDescription()).next(new MyRandom(123456));
	}
	
	@Test
	public void testEqualDifferentSetsSameContent() {
		assertTrue(var.equals(var.copy()));
	}
	
	@Test
	public void testNotEqualDifferentHash() {
		GroupVariable other = var.copy();
		new GroupMutation().mutate(other, new MyRandom(123456));
		assertFalse(var.hashCode() == other.hashCode());
	}
	
	@Test
	public void testEqualSolution() {
		Solution<GroupVariable> s = p.evaluate(var);
		Solution<GroupVariable> other = p.evaluate(var.copy());
		assertTrue(s.hashCode() == other.hashCode());
		assertTrue(s.equals(other));
		assertTrue(s.getVariable().equals(other.getVariable()));
	}
	
	@Test
	public void testGroupContainsGroup() {
		GroupVariable other = var.copy();
		for (Set<Member> group : var.decode()) {
			assertTrue(other.decode().contains(group));
		}
	}

	
	@Test
	public void testSwapGroups() {
		List<Member> l1 = Arrays.asList(new Member("Felix"),new Member("Steffen"),new Member("Manuel"));
		List<Member> l2 = Arrays.asList(new Member("Frank"),new Member("Alex"));
		var = new GroupVariable(new HashSet<>(Arrays.asList(new HashSet<>(l1), new HashSet<>(l2))));
		GroupVariable var2 = new GroupVariable(new HashSet<>(Arrays.asList(new HashSet<>(l2), new HashSet<>(l1))));
		assertTrue(var.hashCode() == var2.hashCode());
		assertTrue(var.equals(var2));
	}

}
