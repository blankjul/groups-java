package com.julzz;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.julzz.evolutionary.GroupFactoryTest;
import com.julzz.evolutionary.GroupVariableTest;
import com.julzz.groups.model.MemberTest;

@RunWith(Suite.class)
@SuiteClasses({ GroupFactoryTest.class, GroupVariableTest.class, MemberTest.class})
public class AllTests {

}
