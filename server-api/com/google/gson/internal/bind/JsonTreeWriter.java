/*     */ package com.google.gson.internal.bind;
/*     */ 
/*     */ import com.google.errorprone.annotations.CanIgnoreReturnValue;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonNull;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonPrimitive;
/*     */ import com.google.gson.stream.JsonWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
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
/*     */ public final class JsonTreeWriter
/*     */   extends JsonWriter
/*     */ {
/*  34 */   private static final Writer UNWRITABLE_WRITER = new Writer()
/*     */     {
/*     */       public void write(char[] buffer, int offset, int counter)
/*     */       {
/*  38 */         throw new AssertionError();
/*     */       }
/*     */ 
/*     */       
/*     */       public void flush() {
/*  43 */         throw new AssertionError();
/*     */       }
/*     */ 
/*     */       
/*     */       public void close() {
/*  48 */         throw new AssertionError();
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*  53 */   private static final JsonPrimitive SENTINEL_CLOSED = new JsonPrimitive("closed");
/*     */ 
/*     */   
/*  56 */   private final List<JsonElement> stack = new ArrayList<>();
/*     */ 
/*     */   
/*     */   private String pendingName;
/*     */ 
/*     */   
/*  62 */   private JsonElement product = (JsonElement)JsonNull.INSTANCE;
/*     */   
/*     */   public JsonTreeWriter() {
/*  65 */     super(UNWRITABLE_WRITER);
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonElement get() {
/*  70 */     if (!this.stack.isEmpty()) {
/*  71 */       throw new IllegalStateException("Expected one JSON element but was " + this.stack);
/*     */     }
/*  73 */     return this.product;
/*     */   }
/*     */   
/*     */   private JsonElement peek() {
/*  77 */     return this.stack.get(this.stack.size() - 1);
/*     */   }
/*     */   
/*     */   private void put(JsonElement value) {
/*  81 */     if (this.pendingName != null) {
/*  82 */       if (!value.isJsonNull() || getSerializeNulls()) {
/*  83 */         JsonObject object = (JsonObject)peek();
/*  84 */         object.add(this.pendingName, value);
/*     */       } 
/*  86 */       this.pendingName = null;
/*  87 */     } else if (this.stack.isEmpty()) {
/*  88 */       this.product = value;
/*     */     } else {
/*  90 */       JsonElement element = peek();
/*  91 */       if (element instanceof JsonArray) {
/*  92 */         ((JsonArray)element).add(value);
/*     */       } else {
/*  94 */         throw new IllegalStateException();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @CanIgnoreReturnValue
/*     */   public JsonWriter beginArray() throws IOException {
/* 102 */     JsonArray array = new JsonArray();
/* 103 */     put((JsonElement)array);
/* 104 */     this.stack.add(array);
/* 105 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @CanIgnoreReturnValue
/*     */   public JsonWriter endArray() throws IOException {
/* 111 */     if (this.stack.isEmpty() || this.pendingName != null) {
/* 112 */       throw new IllegalStateException();
/*     */     }
/* 114 */     JsonElement element = peek();
/* 115 */     if (element instanceof JsonArray) {
/* 116 */       this.stack.remove(this.stack.size() - 1);
/* 117 */       return this;
/*     */     } 
/* 119 */     throw new IllegalStateException();
/*     */   }
/*     */ 
/*     */   
/*     */   @CanIgnoreReturnValue
/*     */   public JsonWriter beginObject() throws IOException {
/* 125 */     JsonObject object = new JsonObject();
/* 126 */     put((JsonElement)object);
/* 127 */     this.stack.add(object);
/* 128 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @CanIgnoreReturnValue
/*     */   public JsonWriter endObject() throws IOException {
/* 134 */     if (this.stack.isEmpty() || this.pendingName != null) {
/* 135 */       throw new IllegalStateException();
/*     */     }
/* 137 */     JsonElement element = peek();
/* 138 */     if (element instanceof JsonObject) {
/* 139 */       this.stack.remove(this.stack.size() - 1);
/* 140 */       return this;
/*     */     } 
/* 142 */     throw new IllegalStateException();
/*     */   }
/*     */ 
/*     */   
/*     */   @CanIgnoreReturnValue
/*     */   public JsonWriter name(String name) throws IOException {
/* 148 */     Objects.requireNonNull(name, "name == null");
/* 149 */     if (this.stack.isEmpty() || this.pendingName != null) {
/* 150 */       throw new IllegalStateException("Did not expect a name");
/*     */     }
/* 152 */     JsonElement element = peek();
/* 153 */     if (element instanceof JsonObject) {
/* 154 */       this.pendingName = name;
/* 155 */       return this;
/*     */     } 
/* 157 */     throw new IllegalStateException("Please begin an object before writing a name.");
/*     */   }
/*     */ 
/*     */   
/*     */   @CanIgnoreReturnValue
/*     */   public JsonWriter value(String value) throws IOException {
/* 163 */     if (value == null) {
/* 164 */       return nullValue();
/*     */     }
/* 166 */     put((JsonElement)new JsonPrimitive(value));
/* 167 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @CanIgnoreReturnValue
/*     */   public JsonWriter value(boolean value) throws IOException {
/* 173 */     put((JsonElement)new JsonPrimitive(Boolean.valueOf(value)));
/* 174 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @CanIgnoreReturnValue
/*     */   public JsonWriter value(Boolean value) throws IOException {
/* 180 */     if (value == null) {
/* 181 */       return nullValue();
/*     */     }
/* 183 */     put((JsonElement)new JsonPrimitive(value));
/* 184 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @CanIgnoreReturnValue
/*     */   public JsonWriter value(float value) throws IOException {
/* 190 */     if (!isLenient() && (Float.isNaN(value) || Float.isInfinite(value))) {
/* 191 */       throw new IllegalArgumentException("JSON forbids NaN and infinities: " + value);
/*     */     }
/* 193 */     put((JsonElement)new JsonPrimitive(Float.valueOf(value)));
/* 194 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @CanIgnoreReturnValue
/*     */   public JsonWriter value(double value) throws IOException {
/* 200 */     if (!isLenient() && (Double.isNaN(value) || Double.isInfinite(value))) {
/* 201 */       throw new IllegalArgumentException("JSON forbids NaN and infinities: " + value);
/*     */     }
/* 203 */     put((JsonElement)new JsonPrimitive(Double.valueOf(value)));
/* 204 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @CanIgnoreReturnValue
/*     */   public JsonWriter value(long value) throws IOException {
/* 210 */     put((JsonElement)new JsonPrimitive(Long.valueOf(value)));
/* 211 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @CanIgnoreReturnValue
/*     */   public JsonWriter value(Number value) throws IOException {
/* 217 */     if (value == null) {
/* 218 */       return nullValue();
/*     */     }
/*     */     
/* 221 */     if (!isLenient()) {
/* 222 */       double d = value.doubleValue();
/* 223 */       if (Double.isNaN(d) || Double.isInfinite(d)) {
/* 224 */         throw new IllegalArgumentException("JSON forbids NaN and infinities: " + value);
/*     */       }
/*     */     } 
/*     */     
/* 228 */     put((JsonElement)new JsonPrimitive(value));
/* 229 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @CanIgnoreReturnValue
/*     */   public JsonWriter nullValue() throws IOException {
/* 235 */     put((JsonElement)JsonNull.INSTANCE);
/* 236 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonWriter jsonValue(String value) throws IOException {
/* 241 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public void flush() throws IOException {}
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 249 */     if (!this.stack.isEmpty()) {
/* 250 */       throw new IOException("Incomplete document");
/*     */     }
/* 252 */     this.stack.add(SENTINEL_CLOSED);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\gson\internal\bind\JsonTreeWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */