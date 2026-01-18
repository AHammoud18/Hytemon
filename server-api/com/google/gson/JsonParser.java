/*     */ package com.google.gson;
/*     */ 
/*     */ import com.google.errorprone.annotations.InlineMe;
/*     */ import com.google.gson.internal.Streams;
/*     */ import com.google.gson.stream.JsonReader;
/*     */ import com.google.gson.stream.JsonToken;
/*     */ import com.google.gson.stream.MalformedJsonException;
/*     */ import java.io.IOException;
/*     */ import java.io.Reader;
/*     */ import java.io.StringReader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class JsonParser
/*     */ {
/*     */   public static JsonElement parseString(String json) throws JsonSyntaxException {
/*  92 */     return parseReader(new StringReader(json));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JsonElement parseReader(Reader reader) throws JsonIOException, JsonSyntaxException {
/*     */     try {
/* 109 */       JsonReader jsonReader = new JsonReader(reader);
/* 110 */       JsonElement element = parseReader(jsonReader);
/* 111 */       if (!element.isJsonNull() && jsonReader.peek() != JsonToken.END_DOCUMENT) {
/* 112 */         throw new JsonSyntaxException("Did not consume the entire document.");
/*     */       }
/* 114 */       return element;
/* 115 */     } catch (MalformedJsonException|NumberFormatException e) {
/* 116 */       throw new JsonSyntaxException(e);
/* 117 */     } catch (IOException e) {
/* 118 */       throw new JsonIOException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JsonElement parseReader(JsonReader reader) throws JsonIOException, JsonSyntaxException {
/* 138 */     Strictness strictness = reader.getStrictness();
/* 139 */     if (strictness == Strictness.LEGACY_STRICT)
/*     */     {
/* 141 */       reader.setStrictness(Strictness.LENIENT);
/*     */     }
/*     */     try {
/* 144 */       return Streams.parse(reader);
/* 145 */     } catch (StackOverflowError|OutOfMemoryError e) {
/* 146 */       throw new JsonParseException("Failed parsing JSON source: " + reader + " to Json", e);
/*     */     } finally {
/* 148 */       reader.setStrictness(strictness);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   @InlineMe(replacement = "JsonParser.parseString(json)", imports = {"com.google.gson.JsonParser"})
/*     */   public JsonElement parse(String json) throws JsonSyntaxException {
/* 158 */     return parseString(json);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   @InlineMe(replacement = "JsonParser.parseReader(json)", imports = {"com.google.gson.JsonParser"})
/*     */   public JsonElement parse(Reader json) throws JsonIOException, JsonSyntaxException {
/* 167 */     return parseReader(json);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   @InlineMe(replacement = "JsonParser.parseReader(json)", imports = {"com.google.gson.JsonParser"})
/*     */   public JsonElement parse(JsonReader json) throws JsonIOException, JsonSyntaxException {
/* 176 */     return parseReader(json);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\gson\JsonParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */