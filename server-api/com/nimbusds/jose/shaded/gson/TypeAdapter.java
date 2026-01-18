/*     */ package com.nimbusds.jose.shaded.gson;
/*     */ 
/*     */ import com.nimbusds.jose.shaded.gson.internal.bind.JsonTreeReader;
/*     */ import com.nimbusds.jose.shaded.gson.internal.bind.JsonTreeWriter;
/*     */ import com.nimbusds.jose.shaded.gson.stream.JsonReader;
/*     */ import com.nimbusds.jose.shaded.gson.stream.JsonToken;
/*     */ import com.nimbusds.jose.shaded.gson.stream.JsonWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.Reader;
/*     */ import java.io.StringReader;
/*     */ import java.io.StringWriter;
/*     */ import java.io.Writer;
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
/*     */ public abstract class TypeAdapter<T>
/*     */ {
/*     */   public abstract void write(JsonWriter paramJsonWriter, T paramT) throws IOException;
/*     */   
/*     */   public final void toJson(Writer out, T value) throws IOException {
/* 143 */     JsonWriter writer = new JsonWriter(out);
/* 144 */     write(writer, value);
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
/*     */   public final String toJson(T value) {
/* 160 */     StringWriter stringWriter = new StringWriter();
/*     */     try {
/* 162 */       toJson(stringWriter, value);
/* 163 */     } catch (IOException e) {
/* 164 */       throw new JsonIOException(e);
/*     */     } 
/* 166 */     return stringWriter.toString();
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
/*     */   public final JsonElement toJsonTree(T value) {
/*     */     try {
/* 180 */       JsonTreeWriter jsonWriter = new JsonTreeWriter();
/* 181 */       write((JsonWriter)jsonWriter, value);
/* 182 */       return jsonWriter.get();
/* 183 */     } catch (IOException e) {
/* 184 */       throw new JsonIOException(e);
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
/*     */   public abstract T read(JsonReader paramJsonReader) throws IOException;
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
/*     */   public final T fromJson(Reader in) throws IOException {
/* 211 */     JsonReader reader = new JsonReader(in);
/* 212 */     return read(reader);
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
/*     */   public final T fromJson(String json) throws IOException {
/* 230 */     return fromJson(new StringReader(json));
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
/*     */   public final T fromJsonTree(JsonElement jsonTree) {
/*     */     try {
/* 243 */       JsonTreeReader jsonTreeReader = new JsonTreeReader(jsonTree);
/* 244 */       return read((JsonReader)jsonTreeReader);
/* 245 */     } catch (IOException e) {
/* 246 */       throw new JsonIOException(e);
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
/*     */   public final TypeAdapter<T> nullSafe() {
/* 292 */     if (!(this instanceof NullSafeTypeAdapter)) {
/* 293 */       return new NullSafeTypeAdapter();
/*     */     }
/* 295 */     return this;
/*     */   }
/*     */   
/*     */   private final class NullSafeTypeAdapter
/*     */     extends TypeAdapter<T> {
/*     */     public void write(JsonWriter out, T value) throws IOException {
/* 301 */       if (value == null) {
/* 302 */         out.nullValue();
/*     */       } else {
/* 304 */         TypeAdapter.this.write(out, value);
/*     */       } 
/*     */     }
/*     */     private NullSafeTypeAdapter() {}
/*     */     
/*     */     public T read(JsonReader reader) throws IOException {
/* 310 */       if (reader.peek() == JsonToken.NULL) {
/* 311 */         reader.nextNull();
/* 312 */         return null;
/*     */       } 
/* 314 */       return TypeAdapter.this.read(reader);
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 319 */       return "NullSafeTypeAdapter[" + TypeAdapter.this + "]";
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\shaded\gson\TypeAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */