
package com.julzz.groups.ui;

import com.julzz.groups.io.ProblemBuilder;
import com.julzz.groups.model.GroupVariable;
import com.msu.moo.model.solution.SolutionSet;


public class Storage {
    
    public static ProblemBuilder bProblem = new ProblemBuilder();
    
    public static SolutionSet<GroupVariable> result = null;
    
    public static int population = 100;
    
    public static int evaluations = 1000;
    
    
}
