/**
 *
 */
package com.mytest.app.common;

import java.text.SimpleDateFormat;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author yqwu
 *
 */
public class CompanyObjectMapper extends ObjectMapper {

	/**
	 *
	 */
	private static final long serialVersionUID = -1298812276667770526L;

	public CompanyObjectMapper() {
		super();
		getSerializationConfig().with(	new SimpleDateFormat(
												"yyyy-MM-dd HH:mm:ss"));
		getDeserializationConfig()
				.with(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
	}
}
