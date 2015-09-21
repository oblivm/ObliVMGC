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
public class NoClass<t__T> implements IWritable<NoClass<t__T>, t__T>, ISecureRunnable<t__T> {

	public CompEnv<t__T> env;
	public IntegerLib<t__T> intLib;
	public FloatLib<t__T> floatLib;
	public NoClass<t__T> noclass = this;

	public NoClass(CompEnv<t__T> env) throws Exception {
		this.env = env;
		this.intLib = new IntegerLib<t__T>(env);
		this.floatLib = new FloatLib<t__T>(env, 24, 8);
	}

	public int numBits() {
		return 0;
	}

	public t__T[] getBits() throws Exception {
		t__T[] ret = env.newTArray(this.numBits());
		t__T[] tmp_b;
		t__T tmp;
		int now = 0;
		return ret;
	}

	public NoClass<t__T> newObj(t__T[] data) throws Exception {
		if(data == null) {
			data = env.newTArray(this.numBits());
			for(int i=0; i<this.numBits(); ++i) { data[i] = intLib.SIGNAL_ZERO; }
		}
		if(data.length != this.numBits()) return null;
		NoClass<t__T> ret = new NoClass<t__T>(env);
		t__T[] tmp;
		int now = 0;
		return ret;
	}

	public NoClass<t__T> fake() throws Exception {
		NoClass<t__T> ret = new NoClass<t__T>(env);
		return ret;
	}

	public NoClass<t__T> muxFake(t__T __isDummy) throws Exception {
		NoClass<t__T> ret = new NoClass<t__T>(env);
		return ret;
	}

	public t__T[] main(int n, SecureArray<t__T> x, SecureArray<t__T> y) throws Exception {
		Stack<t__T, BoxedInt<t__T>> stack = new Stack<t__T, BoxedInt<t__T>>(env, 10, new BoxedInt<t__T>(env, env.inputOfAlice(Utils.fromInt(0, 32))));
		int i = 0;
		t__T _t_state = env.inputOfAlice(false);
		int _t_count = 0;
		int __tmp38 = 0;
		i = __tmp38;
		boolean f_tmp_1 = i < n;
		boolean __tmp39 = f_tmp_1;
		while(__tmp39) {
			t__T[] f_tmp_3 = env.inputOfAlice(Utils.fromInt(i, 32));
			t__T[] f_tmp_2 = y.read(f_tmp_3);
			t__T[] __tmp40 = f_tmp_2;
			t__T[] f_tmp_5 = env.inputOfAlice(Utils.fromInt(i, 32));
			t__T[] f_tmp_4 = x.read(f_tmp_5);
			t__T[] __tmp41 = f_tmp_4;
			stack.op(__tmp40, new BoxedInt(env, __tmp41));
			int __tmp43 = 1;
			int f_tmp_7 = i + __tmp43;
			int __tmp44 = f_tmp_7;
			i = __tmp44;
			boolean f_tmp_8 = i < n;
			__tmp39 = f_tmp_8;
		}
		t__T __tmp45 = env.inputOfAlice(true);
		t__T[] __tmp46 = stack.pop(__tmp45).value;
		return __tmp46;

	}
	public t__T[] main (int __n, int __m, t__T[] x, t__T[] y) throws Exception {
		int n = __n / (32);
		if ( (n) * (32) != __n ) {
			throw new RuntimeException("input size doesn't match");
		}
		if ( (n) * (32) != __m ) {
			throw new RuntimeException("input size doesn't match");
		}
		return main(n, com.oblivm.backend.lang.inter.Util.intToSecArray(env, x, 32, n), com.oblivm.backend.lang.inter.Util.intToSecArray(env, y, 32, n));
	}
}
