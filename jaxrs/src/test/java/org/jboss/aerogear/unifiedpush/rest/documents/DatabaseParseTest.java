package org.jboss.aerogear.unifiedpush.rest.documents;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.jboss.aerogear.unifiedpush.api.Installation;
import org.jboss.aerogear.unifiedpush.cassandra.dao.impl.DocumentKey;
import org.jboss.aerogear.unifiedpush.cassandra.dao.model.DocumentContent;
import org.jboss.aerogear.unifiedpush.cassandra.dao.model.parser.JsonDocumentContent;
import org.jboss.aerogear.unifiedpush.rest.RestEndpointTest;
import org.jboss.aerogear.unifiedpush.rest.util.CommonUtils;
import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DatabaseParseTest {

	@Test
	public void testDocumentContent() {
		ObjectMapper mapper = new ObjectMapper();
		Installation exampleObject = RestEndpointTest.getIosDefaultInstallation();

		DocumentContent content = null;

		try {
			content = new JsonDocumentContent(new DocumentKey(UUID.randomUUID(), "DEVICES"),
					mapper.writeValueAsString(exampleObject));
			content.setDocumentId(UUID.randomUUID().toString());
		} catch (JsonProcessingException e) {
			Assert.fail("Error while parsing device Installation !!!");
		}

		try {
			String value = mapper.writeValueAsString(content);
			assertThat(value.contains("content\":{")).isTrue();

			DocumentContent content2 = mapper.readValue(value, JsonDocumentContent.class);
			assertThat(content2.getContent().equals(content.getContent())).isTrue();
		} catch (JsonProcessingException e) {
			Assert.fail("Error while parsing DocumentContent !!!");
		} catch (IOException e) {
			Assert.fail("Error while reading DocumentContent !!!");
		}

	}

	@Test
	public void testValidJSON() {
		try {
			String jsonString1 = IOUtils.toString(this.getClass().getResourceAsStream(
					"/org/jboss/aerogear/unifiedpush/rest/util/document-list-eample1.json"), "UTF-8");

			String jsonString2 = IOUtils.toString(this.getClass().getResourceAsStream(
					"/org/jboss/aerogear/unifiedpush/rest/util/document-list-eample2.jsonbad"), "UTF-8");

			assertThat(CommonUtils.isValidJSON(jsonString1));
			assertThat(!CommonUtils.isValidJSON(jsonString2));
		} catch (IOException e) {
			Assert.fail("Error while reading DocumentList !!!");
		}
	}

	@Test
	public void validJSONLatencyTest() {
		try {
			String jsonString = IOUtils.toString(this.getClass().getResourceAsStream(
					"/org/jboss/aerogear/unifiedpush/rest/util/document-list-eample1.json"), "UTF-8");

			for (int i=0; i<1000; i++ ){
				assertThat(CommonUtils.isValidJSON(jsonString));
			}
		} catch (IOException e) {
			Assert.fail("Error while reading DocumentList !!!");
		}
	}
}
