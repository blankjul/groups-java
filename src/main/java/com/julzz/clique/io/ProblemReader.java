package com.julzz.clique.io;

import java.io.BufferedReader;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.julzz.clique.group.Problem;
import com.msu.util.io.AReader;

public class ProblemReader extends AReader<Problem> {

	@Override
	protected Problem read_(BufferedReader br) throws IOException{
		try {
			Gson gson = new Gson();
			ProblemBuilder b = gson.fromJson(br, ProblemBuilder.class);
			return b.build();
			
		} catch (JsonSyntaxException e) {
			System.err.println("Input file contains errors and could not be read!");
			return null;
		}
	}

}
