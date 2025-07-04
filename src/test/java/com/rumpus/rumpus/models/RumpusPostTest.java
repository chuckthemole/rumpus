package com.rumpus.rumpus.models;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RumpusPostTest {

    @Test
    public void testCreateMethod() {
        RumpusPost post = RumpusPost.create(
            "Title",
            "Content",
            "authorID",
            "threadID",
            "2024-08-30T12:00:00Z",
            "2024-08-30T12:30:00Z",
            "parentPostID"
        );

        assertNotNull(post);
        assertEquals("Title", post.getTitle());
        assertEquals("Content", post.getContent());
        assertEquals("authorID", post.getAuthorID());
        assertEquals("threadID", post.getThreadID());
        assertEquals("2024-08-30T12:00:00Z", post.getCreated());
        assertEquals("2024-08-30T12:30:00Z", post.getUpdated());
        assertEquals("parentPostID", post.getParentPostID());
    }

    @Test
    public void testJsonSerialization() throws IOException {
        RumpusPost post = RumpusPost.create(
            "Title",
            "Content",
            "authorID",
            "threadID",
            "2024-08-30T12:00:00Z",
            "2024-08-30T12:30:00Z",
            "parentPostID"
        );

        final RumpusPostSerializer serializer = new RumpusPostSerializer();
        final TypeAdapter<RumpusPost> adapter = serializer.getTypeAdapter();
        StringWriter writer = new StringWriter();
        JsonWriter jsonWriter = new JsonWriter(writer);

        adapter.write(jsonWriter, post);
        jsonWriter.close();

        String expectedJson = "{\"title\":\"Title\",\"content\":\"Content\",\"author\":\"authorID\",\"threadID\":\"threadID\",\"created\":\"2024-08-30T12:00:00Z\",\"updated\":\"2024-08-30T12:30:00Z\",\"parentPostID\":\"parentPostID\"}";
        assertEquals(expectedJson, writer.toString());
    }

    @Test
    public void testJsonDeserialization() throws IOException {
        String json = "{\"title\":\"Title\",\"content\":\"Content\",\"author\":\"authorID\",\"threadID\":\"threadID\",\"created\":\"2024-08-30T12:00:00Z\",\"updated\":\"2024-08-30T12:30:00Z\",\"parentPostID\":\"parentPostID\"}";

        RumpusPost post = RumpusPost.create("", "", "", "", "", "", "");
        final RumpusPostSerializer serializer = new RumpusPostSerializer();
        final TypeAdapter<RumpusPost> adapter = serializer.getTypeAdapter();
        JsonReader jsonReader = new JsonReader(new StringReader(json));

        RumpusPost deserializedPost = adapter.read(jsonReader);

        assertNotNull(deserializedPost);
        assertEquals("Title", deserializedPost.getTitle());
        assertEquals("Content", deserializedPost.getContent());
        assertEquals("authorID", deserializedPost.getAuthorID());
        assertEquals("threadID", deserializedPost.getThreadID());
        assertEquals("2024-08-30T12:00:00Z", deserializedPost.getCreated());
        assertEquals("2024-08-30T12:30:00Z", deserializedPost.getUpdated());
        assertEquals("parentPostID", deserializedPost.getParentPostID());
    }
}
