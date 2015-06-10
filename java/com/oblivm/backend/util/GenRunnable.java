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

public abstract class GenRunnable<T> extends com.oblivm.backend.network.Server implements Runnable {

	Mode m;
	int port;
	protected String[] args;
	public boolean verbose = true;
	public void setParameter(Mode m, int port, String[] args) {
		this.m = m;
		this.port = port;
		this.args = args;
	}

	public void setParameter(Mode m, int port) {
		this.m = m;
		this.port = port;
	}

	public abstract void prepareInput(CompEnv<T> gen) throws Exception;
	public abstract void secureCompute(CompEnv<T> gen) throws Exception;
	public abstract void prepareOutput(CompEnv<T> gen) throws Exception;

	public void runCore() throws Exception {
		if(verbose)
			System.out.println("connecting");
		listen(port);
		if(verbose)
			System.out.println("connected");

		@SuppressWarnings("unchecked")
		CompEnv<T> env = CompEnv.getEnv(m, Party.Alice, this);

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
		if(verbose)
			System.out.println("Gen running time:"+(e-s)/1e9);

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
		File file = new File("Config.conf");

		Scanner scanner;
		int port=0;
		Mode mode=null;

		try {
			scanner = new Scanner(file);
			while(scanner.hasNextLine()) {
				String a = scanner.nextLine();
				String[] content = a.split(":");
				if(content.length == 2) {
					if(content[0].equals("Port"))
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
		this.setParameter(mode, port);
	}
	
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws ParseException, ClassNotFoundException, InstantiationException, IllegalAccessException { 

		Class<?> clazz = Class.forName(args[0]+"$Generator");
		GenRunnable run = (GenRunnable) clazz.newInstance();
		run.loadConfig();
		run.run();
		if(Flag.CountTime)
			Flag.sw.print();
	}
}

