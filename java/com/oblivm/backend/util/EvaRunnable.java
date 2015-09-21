package com.oblivm.backend.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

import org.apache.commons.cli.ParseException;

import com.oblivm.backend.flexsc.CompEnv;
import com.oblivm.backend.flexsc.Flag;
import com.oblivm.backend.flexsc.Mode;
import com.oblivm.backend.flexsc.Party;

public abstract  class EvaRunnable<T> extends com.oblivm.backend.network.Client implements Runnable {
	public abstract void prepareInput(CompEnv<T> gen) throws Exception;
	public abstract void secureCompute(CompEnv<T> gen) throws Exception;
	public abstract void prepareOutput(CompEnv<T> gen) throws Exception;
	Mode m;
	int port;
	String host;
	protected String[] args;
	public boolean verbose = true;

	public void setParameter(Mode m, String host, int port, String[] args){
		this.m = m;
		this.port = port;
		this.host = host;
		this.args = args;
	}

	public void setParameter(Mode m, String host, int port){
		this.m = m;
		this.port = port;
		this.host = host;
	}

	public void runCore() throws Exception {
		if(verbose)
			System.out.println("connecting");
		connect(host, port);
		if(verbose)
			System.out.println("connected");

		@SuppressWarnings("unchecked")
		CompEnv<T> env = CompEnv.getEnv(m, Party.Bob, this);

		double s = System.nanoTime();
		Flag.sw.startTotal();
		prepareInput(env);
		os.flush();
		secureCompute(env);
		os.flush();
		prepareOutput(env);
		os.flush();
		Flag.sw.stopTotal();
		double e = System.nanoTime();
		disconnect();
		if(verbose){
			System.out.println("Eva running time:"+(e-s)/1e9);
			System.out.println("Number Of AND Gates:"+env.numOfAnds);
		}
	}
	
	public void run() {
		try {
			runCore();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void loadConfig() {
		loadConfig("Config.conf");
	}
	
	public void loadConfig(String fileName) {
		File file = new File(fileName);
		Scanner scanner;
		String host = null;
		int port = 0;
		Mode mode = null;

		try {
			scanner = new Scanner(file);
			while(scanner.hasNextLine()) {
				String a = scanner.nextLine();
				String[] content = a.split(":");
				if(content.length == 2) {
					if(content[0].equals("Host"))
						host = content[1].replace(" ", "");
					else if(content[0].equals("Port"))
						port = new Integer(content[1].replace(" ", ""));
					else if(content[0].equals("Mode"))
						mode = Mode.getMode(content[1].replace(" ", ""));
					else{}
				}
			}
			scanner.close();			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.setParameter(mode, host, port);
	}
	
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ParseException, ClassNotFoundException {

		Class<?> clazz = Class.forName(args[0]+"$Evaluator");
		EvaRunnable run = (EvaRunnable) clazz.newInstance();
		run.run();
		run.loadConfig();
		if(Flag.CountTime)
			Flag.sw.print();
		if(Flag.countIO)
			run.printStatistic();
	}
}