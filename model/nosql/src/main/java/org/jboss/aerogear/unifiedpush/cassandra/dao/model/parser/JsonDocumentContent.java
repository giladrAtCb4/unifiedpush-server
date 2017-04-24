package org.jboss.aerogear.unifiedpush.cassandra.dao.model.parser;

import org.jboss.aerogear.unifiedpush.cassandra.dao.impl.DocumentKey;
import org.jboss.aerogear.unifiedpush.cassandra.dao.model.DocumentContent;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class JsonDocumentContent extends DocumentContent {
	private static final String CONTENT_TYPE = "application/json";

	public JsonDocumentContent() {
		super();
		super.setContentType(CONTENT_TYPE);
	}

	public JsonDocumentContent(DocumentKey key, String content, String documentId) {
		super(key, content, documentId);
	}

	public JsonDocumentContent(DocumentKey key, String content) {
		super(key, content);
	}

	@JsonRawValue
	@Override
	public String getContent() {
		return super.getContent();
	}

	@JsonDeserialize(using = JsonRawValueDeserializer.class)
	@Override
	public void setContent(String content) {
		super.setContent(content.toString());
	}

	@Override
	public void setContentType(String contentType) {
		if (!CONTENT_TYPE.equals(contentType)) {
			throw new UnsupportedContentTypeException(String.format("Only %s type is permitted.", CONTENT_TYPE));
		}
		super.setContentType(contentType);
	}
}