/**
 *
 */
package com.mytest.app.common;

import java.io.IOException;

import org.apache.commons.lang3.time.DateUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonTokenId;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.UntypedObjectDeserializer;

/**
 * @author yqwu
 *
 */
public class CustomDateDeseralizer extends UntypedObjectDeserializer {
	private static final long serialVersionUID = -2275951539867772400L;

	@Override
	public Object deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException {

		if (jp.getCurrentTokenId() == JsonTokenId.ID_STRING) {
			try {
				return DateUtils.parseDate(jp.getText(), new String[] {
						"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss" });
			} catch (Exception e) {
				return super.deserialize(jp, ctxt);
			}
		} else {
			return super.deserialize(jp, ctxt);
		}
	}
}
