/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.julzz.groups.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.julzz.groups.model.Member;
import com.julzz.groups.model.Problem;
import com.julzz.groups.model.ProblemDescription;
import com.msu.moo.util.io.AWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ProblemWriter extends AWriter<Problem> {

    protected void write_(ProblemBuilder problem, OutputStream os) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(problem);
        os.write(json.getBytes());
        os.close();
    }

    protected void write_(Problem problem, OutputStream os) throws IOException {

        ProblemDescription desc = problem.getDescription();

        ProblemBuilder b = new ProblemBuilder();
        b.groupLimits = desc.getGroupLimits();

        for (Member m : desc.getMembers()) {
            b.addMember(m.getName());

            List<String> prefs = new ArrayList<>();
            for (Member other : m.getPreferences()) {
                prefs.add(other.getName());
            }
            b.addPreference(m.getName(), prefs);

            List<String> rejs = new ArrayList<>();
            for (Member other : m.getRejections()) {
                rejs.add(other.getName());
            }
            b.addRejection(m.getName(), rejs);

        }

        for (Set<Member> g : desc.getNotInGroup()) {
            List<String> group = new ArrayList<>();
            for (Member other : g) {
                group.add(other.getName());
            }
            b.addForbiddenGroup(group);
        }

        for (Set<Member> g : desc.getInGroup()) {
            List<String> group = new ArrayList<>();
            for (Member other : g) {
                group.add(other.getName());
            }
            b.addForcedGroup(group);
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(b);
        System.out.println(json);

    }

}
