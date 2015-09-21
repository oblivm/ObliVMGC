package com.oblivm.backend.lang.inter;

import java.io.IOException;
import java.lang.reflect.Constructor;

import com.oblivm.backend.flexsc.CompEnv;
/***
 * Copyright (C) 2015 by Chang Liu <liuchang@cs.umd.edu>
 */
import com.oblivm.backend.gc.BadLabelException;
import com.oblivm.backend.lang.inter.input.BitFileInput;
import com.oblivm.backend.util.EvaRunnable;
import com.oblivm.backend.util.GenRunnable;
import com.oblivm.backend.util.Utils;

public class MainRunnable {
	
	public static class Generator<T> extends GenRunnable<T> {
		public Input inputAlice;
		public String className; 
		public int lenA, lenB;
		
		public Generator(String className, String aliceInputFile) {
			this.className = className;
			try {
				inputAlice = BitFileInput.open(aliceInputFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		ISecureRunnable<T> runnable;

		T[] inputA;
		T[] inputB;
		T[] scResult;
		
		@SuppressWarnings("unchecked")
		@Override
		public void prepareInput(CompEnv<T> env) {
			try {
				Class<?> cl = Class.forName(className);
				Constructor<?> ctor = cl.getConstructors()[0];
				runnable = (ISecureRunnable<T>)ctor.newInstance(env);
			} catch (Exception e) {
				e.printStackTrace();
			}
			boolean[] in = inputAlice.readAll();
			lenA = in.length;
			lenB = 0;
			try {
				os.write(Utils.toByte(lenA));
				os.flush();
				lenB = Utils.fromByte(this.readBytes(4));
				this.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			inputA = env.inputOfAlice(in);
			env.flush();
			inputB = env.inputOfBob(new boolean[lenB]);
			
//			System.out.println("LenA="+lenA+"\tLenB="+lenB);
		}
		
		@Override
		public void secureCompute(CompEnv<T> env) throws Exception {
			scResult = runnable.main(lenA, lenB, inputA, inputB);
		}
		@Override
		public void prepareOutput(CompEnv<T> gen) throws Exception {
			System.out.println(Utils.toInt(gen.outputToAlice(scResult)));
			gen.channel.os.write(new byte[]{0});
			gen.flush();
		}
		
	}
	
	public static class Evaluator<T> extends EvaRunnable<T> {
		public Input inputBob;
		public String className; 
		public int lenA, lenB;
		
		public Evaluator(String className, String bobInputFile) {
			this.className = className;
			try {
				inputBob = BitFileInput.open(bobInputFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		ISecureRunnable<T> runnable;
		
		T[] inputA;
		T[] inputB;
		T[] scResult;
		
		@SuppressWarnings("unchecked")
		@Override
		public void prepareInput(CompEnv<T> env) {
			boolean[] in = inputBob.readAll();
			lenA = 0;
			lenB = in.length;
			try {
				lenA = Utils.fromByte(this.readBytes(4));
				os.write(Utils.toByte(lenB));
				os.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				Class<?> cl = Class.forName(className);
				Constructor<?> ctor = cl.getConstructors()[0];
				runnable = (ISecureRunnable<T>)ctor.newInstance(env);
			} catch (Exception e) {
				e.printStackTrace();
			}
			inputA = env.inputOfAlice(new boolean[lenA]);
			env.flush();
			inputB = env.inputOfBob(in);
		}
		
		@Override
		public void secureCompute(CompEnv<T> env) throws Exception {
			scResult = runnable.main(lenA, lenB, inputA, inputB);
		}
		
		@Override
		public void prepareOutput(CompEnv<T> env) throws Exception {
			env.outputToAlice(scResult);
			env.channel.is.read(new byte[]{0});
			env.flush();
		}
	}

}
