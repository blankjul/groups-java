import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.julzz.groups.io.ProblemReader;
import com.julzz.groups.model.GroupVariable;
import com.julzz.groups.model.Member;
import com.julzz.groups.model.Problem;
import com.julzz.groups.recombination.GroupFactory;
import com.msu.moo.util.MyRandom;

import junit.framework.TestCase;

public class MemberTest extends TestCase {

	protected Problem p;
	protected GroupVariable members1;
	protected GroupVariable members2;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		p = new ProblemReader().read("src/main/resources/december2015.json").build();
		members1 = new GroupFactory(p.getDescription()).next(new MyRandom(123456));
		members2 = new GroupVariable(new HashSet<>(members1.decode()));
	}

	@Test
	public void testEqualTrue() {
		assertTrue(members1.equals(members2));
	}

	@Test
	public void testEqualFalse() {
		members2 = new GroupFactory(p.getDescription()).next(new MyRandom(1234567));
		assertTrue(!members1.equals(members2));
	}

	public void testHashMemberEqualNameDifferentHash() {
		Member m1 = new Member("Ann");
		Member m2 = new Member("Ann");
		assertTrue(m1.hashCode() != m2.hashCode());
	}

	public void testHashToSet() {
		Member m = new Member("Ann");
		Set<Member> s = new HashSet<>();
		s.add(m);
		assertTrue(s.contains(m));
	}

	

}
