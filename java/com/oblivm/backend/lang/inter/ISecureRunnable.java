/***
 * Copyright (C) 2015 by Chang Liu <liuchang@cs.umd.edu>
 */
package com.oblivm.backend.lang.inter;

public interface ISecureRunnable<T> {
	public T[] main(int lenA, int lenB, T[] x, T[] y) throws Exception;
}
