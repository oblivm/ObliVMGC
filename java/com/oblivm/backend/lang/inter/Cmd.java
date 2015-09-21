/***
 * Copyright (C) 2015 by Chang Liu <liuchang@cs.umd.edu>
 */
package com.oblivm.backend.lang.inter;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import com.oblivm.backend.flexsc.Flag;
import com.oblivm.backend.util.EvaRunnable;
import com.oblivm.backend.util.GenRunnable;

/**
 * A single entry point to call EvaRunnable and GenRunnable. This class should be used as the entry point for the jar file.
 *
 */
public class Cmd {
	
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws Exception {
		ArgumentParser ap = ArgumentParsers.newArgumentParser("Cmd");
		ap.addArgument("file").nargs("*").help("File to compile");
		ap.addArgument("--config").setDefault("Config.conf").help("Config file");
		ap.addArgument("-la", "--lenghAlice")
			.help("input length of Alice");
		ap.addArgument("-t", "--type")
			.choices("gen", "eva")
			.setDefault("gen")
			.help("Whether it is the generator or the evaluator");
		ap.addArgument("-i", "--input")
			.help("Input file");
		ap.addArgument("-c", "--class")
			.help("Runnable Class");
		Namespace ns = null;
		try {
			ns = ap.parseArgs(args);
		} catch (ArgumentParserException e) {
			ap.handleError(e);
			System.exit(1);
		}

		if(ns.getString("type").equals("gen")) {
			GenRunnable gen = new MainRunnable.Generator(ns.getString("class"), ns.getString("input"));
			gen.loadConfig(ns.getString("config"));
			gen.runCore();
			if(Flag.CountTime)
				Flag.sw.print();
		} else {
			EvaRunnable eva = new MainRunnable.Evaluator(ns.getString("class"), ns.getString("input"));
			eva.loadConfig(ns.getString("config"));
			eva.runCore();
			if(Flag.CountTime)
				Flag.sw.print();
			if(Flag.countIO)
				eva.printStatistic();
		}
	}
}
