package com.oblivm.backend.lang.inter;

import java.lang.reflect.Array;
import java.util.Arrays;

import com.oblivm.backend.circuits.arithmetic.IntegerLib;
import com.oblivm.backend.flexsc.CompEnv;
import com.oblivm.backend.oram.SecureArray;
import com.oblivm.backend.util.Utils;

public class Util {
	public static<T> T[][] intToArray(T[] intInput, int bitSize, int arraySize) {
		@SuppressWarnings("unchecked")
		T[][] ret = (T[][])Array.newInstance((Class<T>)intInput[0].getClass(), arraySize, bitSize);
		for(int i=0; i<arraySize; ++i) {
			for(int j=0; j<bitSize; ++j) {
				ret[i][j] = intInput[i * bitSize + j];
			}
		}
		return ret;
	}
	
	public static<T> SecureArray<T> intToSecArray(CompEnv<T> env, T[] intInput, int bitSize, int arraySize) throws Exception {
		SecureArray<T> secArray = new SecureArray<T>(env, arraySize, bitSize);
		IntegerLib<T> intLib = new IntegerLib<T>(env); 
		for(int i=0; i<arraySize; ++i) {
			secArray.write(intLib.toSignals(i), Arrays.copyOfRange(intInput, i*bitSize, (i+1)*bitSize));
		}
//		for(int i=0; i<arraySize; ++i) {
//			System.out.println("Input["+i+"] = "+Utils.toInt(env.outputToAlice(secArray.read(intLib.toSignals(i)))));
//		}
		return secArray;
	}
}
