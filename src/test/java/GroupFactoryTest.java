import java.util.Set;

import com.julzz.groups.io.ProblemReader;
import com.julzz.groups.model.GroupVariable;
import com.julzz.groups.model.Member;
import com.julzz.groups.model.Problem;
import com.julzz.groups.recombination.GroupFactory;
import com.msu.moo.util.MyRandom;

import junit.framework.TestCase;

public class GroupFactoryTest extends TestCase {
	
	public void testHasAllMembers() {
		
		Problem p = new ProblemReader().read("src/main/resources/december2015.json").build();
		GroupVariable v = new GroupFactory(p.getDescription()).next(new MyRandom(123456));
		
		for(Set<Member> set : v.decode()) {
			for (Member m : set) {
				assertTrue(p.getDescription().getMembers().contains(m));
			}
		}
		
	}

}
