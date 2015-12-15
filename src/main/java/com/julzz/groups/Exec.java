package com.julzz.groups;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.julzz.groups.model.GroupVariable;
import com.msu.moo.model.solution.SolutionSet;

public class Exec {

	public static void main(String[] args) throws ParseException, FileNotFoundException {

		// create Options object
		Options options = new Options();
		
		options.addOption(Option.builder("nameOfFile")
				.required(false)
				.hasArg()
				.desc("If the output should be printed to file, provide here the path where the solutions shuold be saved.")
				.build());

		options.addOption(Option.builder("numberOfSolutions")
				.required(false)
				.hasArg()
				.desc("Number of solutions that showed be present when the algorithm is finished. Could not be higher than numberOfPopulation.")
				.build());
		
		options.addOption(Option.builder("numberOfPopulation").required(false)
				.hasArg()
				.desc("Evoluationary algorithms use a population for their optimization process. This sets the number of inividuals for each generation")
				.build());
		
		options.addOption("noDescription", false, "If set the description of each solution is not printed. This is an overview which preferences and rejections are fullfilled.");
		

		// create the parser
		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = null;
		String pathToFile = null;
		try {
			cmd = parser.parse(options, args);
			if (cmd.getArgList().size() == 0)
				throw new ParseException("Please provide the JSON file with all the information");
			pathToFile = cmd.getArgList().get(0);
		} catch (ParseException exp) {
			new HelpFormatter().printHelp("java -jar groups.jar [OPTION]... [JSONFILE]\n", options);
			return;
		}

		Solver solver = new Solver();
		
		
		try {
			solver.setProblem(pathToFile);
		} catch (Exception e) {
			System.err.println("Could not find JSON file.");
			return;
		}

		if (cmd.hasOption("numberOfPopulation")) {
			solver.populationSize = Integer.valueOf(cmd.getOptionValue("numberOfPopulation"));
		}
		
		final boolean printDescription = !cmd.hasOption("noDescription");
		
		
		SolutionSet set = solver.execute();
		OutputStream os = (cmd.hasOption("nameOfFile")) ? new FileOutputStream(new File(cmd.getOptionValue("nameOfFile"))) : System.out;
		PrintWriter pw = new PrintWriter(os);
		
		final int numberOfSolutions = (cmd.hasOption("numberOfSolutions")) ? Integer.valueOf(cmd.getOptionValue("numberOfSolutions")) : 1;
		
		for (int i = 0; i < numberOfSolutions && i < set.size(); i++) {
			pw.println("-----------------------------------------------------------------------------");
			pw.println(String.format("Solution %s", i+1));
			pw.println("-----------------------------------------------------------------------------");
			GroupVariable var = (GroupVariable) set.get(i).getVariable();
			pw.println(var.print());
			if (printDescription) pw.println(var.report());
		}
		pw.println();
		pw.close();
		
	}

}
