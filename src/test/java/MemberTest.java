import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.julzz.groups.io.ProblemReader;
import com.julzz.groups.model.GroupVariable;
import com.julzz.groups.model.Member;
import com.julzz.groups.model.Problem;
import com.msu.moo.util.Util;

import junit.framework.TestCase;

public class MemberTest extends TestCase{
	
	protected Problem p;
	protected List<Member> members1;
	protected List<Member> members2;
	
	  @Override
	    protected void setUp() throws Exception
	    {
	        super.setUp();
	        p =  new ProblemReader().read("src/main/resources/december2015.json").build();
	        members1 = new ArrayList<>(p.getDescription().getMembers());
			members2 = new ArrayList<>(members1);
	    }
	
	@Test
	public void testEqualTrue() {
		GroupVariable var1 = new GroupVariable(new ArrayList<>(members1));
		GroupVariable var2 = new GroupVariable(new ArrayList<>(members2));
		assertTrue(var1.equals(var2));
	}
	
	@Test
	public void testEqualFalse() {
		Util.swap(members2, 0, 6);
		GroupVariable var1 = new GroupVariable(new ArrayList<>(members1));
		GroupVariable var2 = new GroupVariable(new ArrayList<>(members2));
		assertTrue(!var1.equals(var2));
	}
	
        /*
	@Test
	public void testEqualButSwap() {
		Util.swap(members2, 0, 1);
		GroupVariable var1 = new GroupVariable(new ArrayList<>(members1));
		GroupVariable var2 = new GroupVariable(new ArrayList<>(members2));
		assertTrue(var1.equals(var2));
	}
        */

}
