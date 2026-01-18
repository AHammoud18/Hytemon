/*     */ package io.sentry;
/*     */ 
/*     */ import io.sentry.vendor.gson.stream.JsonWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ public final class JsonObjectWriter
/*     */   implements ObjectWriter {
/*     */   @NotNull
/*     */   private final JsonWriter jsonWriter;
/*     */   
/*     */   public JsonObjectWriter(@NotNull Writer out, int maxDepth) {
/*  15 */     this.jsonWriter = new JsonWriter(out);
/*  16 */     this.jsonObjectSerializer = new JsonObjectSerializer(maxDepth);
/*     */   }
/*     */   @NotNull
/*     */   private final JsonObjectSerializer jsonObjectSerializer;
/*     */   public JsonObjectWriter beginArray() throws IOException {
/*  21 */     this.jsonWriter.beginArray();
/*  22 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonObjectWriter endArray() throws IOException {
/*  27 */     this.jsonWriter.endArray();
/*  28 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonObjectWriter beginObject() throws IOException {
/*  33 */     this.jsonWriter.beginObject();
/*  34 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonObjectWriter endObject() throws IOException {
/*  39 */     this.jsonWriter.endObject();
/*  40 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonObjectWriter name(@NotNull String name) throws IOException {
/*  45 */     this.jsonWriter.name(name);
/*  46 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonObjectWriter value(@Nullable String value) throws IOException {
/*  51 */     this.jsonWriter.value(value);
/*  52 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ObjectWriter jsonValue(@Nullable String value) throws IOException {
/*  57 */     this.jsonWriter.jsonValue(value);
/*  58 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonObjectWriter nullValue() throws IOException {
/*  63 */     this.jsonWriter.nullValue();
/*  64 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonObjectWriter value(boolean value) throws IOException {
/*  69 */     this.jsonWriter.value(value);
/*  70 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonObjectWriter value(@Nullable Boolean value) throws IOException {
/*  75 */     this.jsonWriter.value(value);
/*  76 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonObjectWriter value(double value) throws IOException {
/*  81 */     this.jsonWriter.value(value);
/*  82 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonObjectWriter value(long value) throws IOException {
/*  87 */     this.jsonWriter.value(value);
/*  88 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonObjectWriter value(@Nullable Number value) throws IOException {
/*  93 */     this.jsonWriter.value(value);
/*  94 */     return this;
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
/*     */   public JsonObjectWriter value(@NotNull ILogger logger, @Nullable Object object) throws IOException {
/* 108 */     this.jsonObjectSerializer.serialize(this, logger, object);
/* 109 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLenient(boolean lenient) {
/* 114 */     this.jsonWriter.setLenient(lenient);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setIndent(@Nullable String indent) {
/* 119 */     this.jsonWriter.setIndent(indent);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getIndent() {
/* 125 */     return this.jsonWriter.getIndent();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\JsonObjectWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */