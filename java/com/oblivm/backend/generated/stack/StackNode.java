package com.oblivm.backend.generated.stack;
import java.util.Arrays;
import java.util.Random;
import java.lang.reflect.Array;
import java.util.BitSet;
import java.security.SecureRandom;
import com.oblivm.backend.oram.SecureArray;
import com.oblivm.backend.oram.CircuitOram;
import com.oblivm.backend.flexsc.Mode;
import com.oblivm.backend.flexsc.Party;
import com.oblivm.backend.flexsc.CompEnv;
import com.oblivm.backend.circuits.arithmetic.IntegerLib;
import com.oblivm.backend.circuits.arithmetic.FloatLib;
import com.oblivm.backend.util.Utils;
import com.oblivm.backend.gc.regular.GCEva;
import com.oblivm.backend.gc.regular.GCGen;
import com.oblivm.backend.gc.GCSignal;
import com.oblivm.backend.flexsc.IWritable;
import com.oblivm.backend.lang.inter.*;
import com.oblivm.backend.flexsc.Comparator;
public class StackNode<t__T, T extends IWritable<T,t__T>> implements IWritable<StackNode<t__T, T>, t__T> {
	public t__T[] next;
	public T data;

	public CompEnv<t__T> env;
	public IntegerLib<t__T> intLib;
	public FloatLib<t__T> floatLib;
	private NoClass<t__T> noclass;
	private T factoryT;
	private int m;

	public StackNode(CompEnv<t__T> env, int m, T factoryT) throws Exception {
		this.env = env;
		this.intLib = new IntegerLib<t__T>(env);
		this.floatLib = new FloatLib<t__T>(env, 24, 8);
		this.noclass = new NoClass<t__T>(env);
		this.m = m;
		this.factoryT = factoryT;
		this.next = intLib.randBools(m);
		this.data = factoryT.newObj(factoryT.getBits());
	}

	public int numBits() {
		int sum = 0;
		sum += next.length;
		sum += factoryT.numBits();
		return sum;
	}

	public t__T[] getBits() throws Exception {
		t__T[] ret = env.newTArray(this.numBits());
		t__T[] tmp_b;
		t__T tmp;
		int now = 0;
		tmp_b = next;
		System.arraycopy(tmp_b, 0, ret, now, tmp_b.length);
		now += tmp_b.length;
		tmp_b = this.data.getBits();
		System.arraycopy(tmp_b, 0, ret, now, tmp_b.length);
		now += tmp_b.length;
		return ret;
	}

	public StackNode<t__T, T> newObj(t__T[] data) throws Exception {
		if(data == null) {
			data = env.newTArray(this.numBits());
			for(int i=0; i<this.numBits(); ++i) { data[i] = intLib.SIGNAL_ZERO; }
		}
		if(data.length != this.numBits()) return null;
		StackNode<t__T, T> ret = new StackNode<t__T, T>(env, m, factoryT);
		t__T[] tmp;
		int now = 0;
		ret.next = env.newTArray(m);
		System.arraycopy(data, now, ret.next, 0, m);
		now += m;
		tmp = env.newTArray(this.factoryT.numBits());
		System.arraycopy(data, now, tmp, 0, tmp.length);
		now += tmp.length;
		ret.data = ret.factoryT.newObj(tmp);
		return ret;
	}

	public StackNode<t__T, T> fake() throws Exception {
		StackNode<t__T, T> ret = new StackNode<t__T, T>(env, m, factoryT);
		ret.next = intLib.randBools(this.next.length);
		ret.data = this.data.fake();
		return ret;
	}

	public StackNode<t__T, T> muxFake(t__T __isDummy) throws Exception {
		StackNode<t__T, T> ret = new StackNode<t__T, T>(env, m, factoryT);
		ret.next = intLib.mux(this.next, intLib.randBools(this.next.length), __isDummy);
		ret.data = this.data.muxFake(__isDummy);
		return ret;
	}

}
