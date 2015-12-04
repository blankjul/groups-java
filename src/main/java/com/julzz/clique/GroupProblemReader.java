package com.julzz.clique;

import java.io.BufferedReader;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.julzz.clique.group.GroupDescription;
import com.julzz.clique.group.GroupProblem;
import com.msu.util.io.AReader;

public class GroupProblemReader extends AReader<GroupProblem> {

	@Override
	protected GroupProblem read_(BufferedReader br) throws IOException{
		try {
			Gson gson = new Gson();
			GroupDescription desc = gson.fromJson(br, GroupDescription.class);
			return new GroupProblem(desc);
		} catch (JsonSyntaxException e) {
			System.err.println("Input file contains errors and could not be read!");
			return null;
		}
	}

}
