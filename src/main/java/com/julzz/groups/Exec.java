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

import com.julzz.groups.evolutionary.GroupVariable;
import com.julzz.groups.io.ProblemReader;
import com.julzz.groups.io.Report;
import com.julzz.groups.model.Problem;
import com.msu.moo.interfaces.ISolution;
import com.msu.moo.model.solution.SolutionSet;

/**
 * The command line class which is responsible for parsing the arguments and
 * perform the optimization.
 *
 */
public class Exec {

	public static void main(String[] args) throws ParseException, FileNotFoundException {

		// create Options object
		Options options = new Options();

		options.addOption(Option.builder("nameOfFile").required(false).hasArg()
				.desc("If the output should be printed to a file, provide here the path where the solutions should be saved.")
				.build());

		options.addOption(Option.builder("numberOfSolutions").required(false).hasArg()
				.desc("Number of solutions that should be present when the algorithm is finished. Could not be higher than numberOfPopulation.")
				.build());

		options.addOption(Option.builder("numberOfPopulation").required(false).hasArg()
				.desc("Evolutionary algorithms use a population for their optimization process. This determines the number of inividuals for each generation. DEFAULT:100")
				.build());

		options.addOption(Option.builder("maxEvaluations").required(false).hasArg()
				.desc("Maximal number of evaluations. DEFAULT: 100000").build());

		options.addOption("noDescription", false,
				"If set as argument, the description of each solution is not printed. This is an overview which preferences and rejections are fulfilled.");

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

		Problem problem = null;
		int numOfPopulation = 100;
		int maxEvaluations = 100000;

		try {
			problem = new ProblemReader().read(pathToFile).build();
		} catch (Exception e) {
			System.err.println("Could not find JSON file.");
			return;
		}

		if (cmd.hasOption("numberOfPopulation")) {
			numOfPopulation = Integer.valueOf(cmd.getOptionValue("numberOfPopulation"));
		}

		if (cmd.hasOption("maxEvaluations")) {
			maxEvaluations = Integer.valueOf(cmd.getOptionValue("maxEvaluations"));
		}

		final boolean printDescription = !cmd.hasOption("noDescription");

		SolutionSet<ISolution<GroupVariable>> set = Solver.solveWithEvolutionaryAlgorithm(problem, numOfPopulation,
				maxEvaluations);

		OutputStream os = (cmd.hasOption("nameOfFile"))
				? new FileOutputStream(new File(cmd.getOptionValue("nameOfFile"))) : System.out;
		PrintWriter pw = new PrintWriter(os);

		final int numberOfSolutions = (cmd.hasOption("numberOfSolutions"))
				? Integer.valueOf(cmd.getOptionValue("numberOfSolutions")) : 1;

		for (int i = 0; i < numberOfSolutions && i < set.size(); i++) {
			pw.println("-----------------------------------------------------------------------------");
			pw.println(String.format("Solution %s", i + 1));
			pw.println("-----------------------------------------------------------------------------");
			GroupVariable var = set.get(i).getVariable();
			pw.println(var.print());
			if (printDescription) pw.println(Report.create(problem,var));
		}
		pw.println();
		pw.close();

	}

}
