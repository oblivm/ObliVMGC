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
public class Stack<t__T, T extends IWritable<T,t__T>> {
	public t__T[] size;
	public t__T[] root;
	public CircuitOram<t__T> oram;

	public CompEnv<t__T> env;
	public IntegerLib<t__T> intLib;
	public FloatLib<t__T> floatLib;
	private NoClass<t__T> noclass;
	private T factoryT;
	private int m;

	public Stack(CompEnv<t__T> env, int m, T factoryT) throws Exception {
		this.env = env;
		this.intLib = new IntegerLib<t__T>(env);
		this.floatLib = new FloatLib<t__T>(env, 24, 8);
		this.noclass = new NoClass<t__T>(env);
		this.m = m;
		this.factoryT = factoryT;
		this.size = env.inputOfAlice(Utils.fromInt(0, m));
		this.root = intLib.randBools(m);
		this.oram = new CircuitOram(env, (1)<<(m), new StackNode<t__T, T>(env, m, factoryT).numBits());
	}

	public void push(T operand, t__T __isPhantom) throws Exception {
		operand = operand.muxFake(__isPhantom);
		StackNode<t__T, T> node = new StackNode<t__T, T>(env, m, factoryT);
		t__T _t_state = env.inputOfAlice(false);
		int _t_count = 0;
		t__T[] f_tmp_10 = this.root;
		t__T[] __tmp0 = f_tmp_10;
		StackNode<t__T, T> f_tmp_11 = new StackNode<t__T, T>(env, m, factoryT);
		f_tmp_11.next = __tmp0;
		f_tmp_11.data = operand;
		StackNode<t__T, T> __tmp1 = f_tmp_11;
		StackNode<t__T, T> __tmp2 = new StackNode<t__T, T>(env, m, factoryT).newObj(intLib.mux(node.getBits(), __tmp1.getBits(),__isPhantom));
		node = __tmp2;
		t__T[] __tmp3 = noclass.intLib.randBools(m);
		t__T[] f_tmp_13 = this.root;
		t__T[] __tmp4 = f_tmp_13;
		t__T[] __tmp5 = intLib.mux(__tmp4, __tmp3,__isPhantom);
		this.root = __tmp5;
		t__T[] f_tmp_15 = this.size;
		t__T[] __tmp6 = f_tmp_15;
		int __tmp7 = 1;
		t__T[] f_tmp_18 = env.inputOfAlice(Utils.fromInt(__tmp7, m));
		t__T[] f_tmp_17 = intLib.add(__tmp6,f_tmp_18);
		t__T[] __tmp8 = f_tmp_17;
		t__T[] f_tmp_19 = this.size;
		t__T[] __tmp9 = f_tmp_19;
		t__T[] __tmp10 = intLib.mux(__tmp9, __tmp8,__isPhantom);
		this.size = __tmp10;
		t__T[] f_tmp_21 = this.size;
		t__T[] __tmp11 = f_tmp_21;
		t__T[] f_tmp_22 = this.root;
		t__T[] __tmp12 = f_tmp_22;
		t__T __tmp14 = __isPhantom;
		CircuitOram<t__T> f_tmp_23 = this.oram;
		CircuitOram<t__T> __tmp13 = f_tmp_23;
		__tmp13.conditionalPutBack(__tmp11, __tmp12, node.getBits(), __tmp14);

	}
	public T pop(t__T __isPhantom) throws Exception {
		T ret = factoryT.newObj(factoryT.getBits());
		t__T[] next = intLib.randBools(m);
		StackNode<t__T, T> r = new StackNode<t__T, T>(env, m, factoryT);
		t__T _t_state = env.inputOfAlice(false);
		int _t_count = 0;
		t__T[] f_tmp_24 = this.size;
		t__T[] __tmp16 = f_tmp_24;
		t__T[] f_tmp_25 = this.root;
		t__T[] __tmp17 = f_tmp_25;
		t__T __tmp19 = __isPhantom;
		CircuitOram<t__T> f_tmp_26 = this.oram;
		CircuitOram<t__T> __tmp18 = f_tmp_26;
		StackNode<t__T, T> __tmp20 = new StackNode<t__T, T>(env, m, factoryT).newObj(__tmp18.conditionalReadAndRemove(__tmp16, __tmp17, __tmp19));
		StackNode<t__T, T> __tmp21 = new StackNode<t__T, T>(env, m, factoryT).newObj(intLib.mux(r.getBits(), __tmp20.getBits(),__isPhantom));
		r = __tmp21;
		t__T[] __tmp22 = r.next;
		T __tmp23 = r.data;
		T __tmp24 = this.factoryT.newObj(intLib.mux(ret.getBits(), __tmp23.getBits(),__isPhantom));
		ret = __tmp24;
		t__T[] f_tmp_29 = this.root;
		t__T[] __tmp25 = f_tmp_29;
		t__T[] __tmp26 = intLib.mux(__tmp25, __tmp22,__isPhantom);
		this.root = __tmp26;
		t__T[] f_tmp_31 = this.size;
		t__T[] __tmp27 = f_tmp_31;
		int __tmp28 = 1;
		t__T[] f_tmp_34 = env.inputOfAlice(Utils.fromInt(__tmp28, m));
		t__T[] f_tmp_33 = intLib.sub(__tmp27,f_tmp_34);
		t__T[] __tmp29 = f_tmp_33;
		t__T[] f_tmp_35 = this.size;
		t__T[] __tmp30 = f_tmp_35;
		t__T[] __tmp31 = intLib.mux(__tmp30, __tmp29,__isPhantom);
		this.size = __tmp31;
		return ret;

	}
	public T op(t__T[] operand, T value) throws Exception {
		T ret = factoryT.newObj(factoryT.getBits());
		t__T _t_state = env.inputOfAlice(false);
		int _t_count = 0;
		int __tmp32 = 0;
		t__T[] f_tmp_39 = env.inputOfAlice(Utils.fromInt(__tmp32, 32));
		t__T f_tmp_38 = intLib.eq(operand, f_tmp_39);
		t__T __tmp33 = f_tmp_38;
		this.push(value, __tmp33);
		t__T f_tmp_40 = intLib.not(__tmp33);
		t__T __tmp35 = f_tmp_40;
		T __tmp36 = this.pop(__tmp35);
		T __tmp37 = this.factoryT.newObj(intLib.mux(ret.getBits(), __tmp36.getBits(),__tmp35));
		ret = __tmp37;
		return ret;

	}
}
