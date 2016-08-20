/**
 *
 */
package com.mytest.app.common;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yqwu
 *
 */
public class ContainUtils {

	public static boolean containsInList(List<Long> list, Long value) {
		for (int i = 0; i < list.size(); i++) {
			if (new BigInteger(value.toString()) == new BigInteger(list
					.get(i)
					.toString())) {
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		List<Long> ids = new ArrayList<Long>();
		ids.add(10l);
		ids.add(98978787867l);
		Long s = 10l;
		System.out.println(ContainUtils.containsInList(ids, s));
	}

}
