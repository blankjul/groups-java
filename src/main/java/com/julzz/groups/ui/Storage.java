
package com.julzz.groups.ui;

import com.julzz.groups.model.GroupVariable;
import com.julzz.groups.model.ProblemDescription;
import com.msu.moo.model.solution.SolutionSet;
import java.util.Locale;
import java.util.ResourceBundle;


public class Storage {

    public static ProblemDescription desc = new ProblemDescription();
    
    public static ResourceBundle bundle = ResourceBundle.getBundle("bundle", new Locale("de", "DE"));
    
    public static SolutionSet<GroupVariable> result = null;
    
    public static int population = 100;
    
    public static int evaluations = 1000;
    
    
    
    
}
