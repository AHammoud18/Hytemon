/*     */ package com.google.gson.internal.bind;
/*     */ 
/*     */ import com.google.errorprone.annotations.CanIgnoreReturnValue;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonPrimitive;
/*     */ import com.google.gson.stream.JsonReader;
/*     */ import com.google.gson.stream.JsonToken;
/*     */ import com.google.gson.stream.MalformedJsonException;
/*     */ import java.io.IOException;
/*     */ import java.io.Reader;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
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
/*     */ public final class JsonTreeReader
/*     */   extends JsonReader
/*     */ {
/*  40 */   private static final Reader UNREADABLE_READER = new Reader()
/*     */     {
/*     */       public int read(char[] buffer, int offset, int count)
/*     */       {
/*  44 */         throw new AssertionError();
/*     */       }
/*     */ 
/*     */       
/*     */       public void close() {
/*  49 */         throw new AssertionError();
/*     */       }
/*     */     };
/*  52 */   private static final Object SENTINEL_CLOSED = new Object();
/*     */ 
/*     */   
/*  55 */   private Object[] stack = new Object[32];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  63 */   private int stackSize = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  73 */   private String[] pathNames = new String[32];
/*  74 */   private int[] pathIndices = new int[32];
/*     */   
/*     */   public JsonTreeReader(JsonElement element) {
/*  77 */     super(UNREADABLE_READER);
/*  78 */     push(element);
/*     */   }
/*     */ 
/*     */   
/*     */   public void beginArray() throws IOException {
/*  83 */     expect(JsonToken.BEGIN_ARRAY);
/*  84 */     JsonArray array = (JsonArray)peekStack();
/*  85 */     push(array.iterator());
/*  86 */     this.pathIndices[this.stackSize - 1] = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void endArray() throws IOException {
/*  91 */     expect(JsonToken.END_ARRAY);
/*  92 */     popStack();
/*  93 */     popStack();
/*  94 */     if (this.stackSize > 0) {
/*  95 */       this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void beginObject() throws IOException {
/* 101 */     expect(JsonToken.BEGIN_OBJECT);
/* 102 */     JsonObject object = (JsonObject)peekStack();
/* 103 */     push(object.entrySet().iterator());
/*     */   }
/*     */ 
/*     */   
/*     */   public void endObject() throws IOException {
/* 108 */     expect(JsonToken.END_OBJECT);
/* 109 */     this.pathNames[this.stackSize - 1] = null;
/* 110 */     popStack();
/* 111 */     popStack();
/* 112 */     if (this.stackSize > 0) {
/* 113 */       this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasNext() throws IOException {
/* 119 */     JsonToken token = peek();
/* 120 */     return (token != JsonToken.END_OBJECT && token != JsonToken.END_ARRAY && token != JsonToken.END_DOCUMENT);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonToken peek() throws IOException {
/* 127 */     if (this.stackSize == 0) {
/* 128 */       return JsonToken.END_DOCUMENT;
/*     */     }
/*     */     
/* 131 */     Object o = peekStack();
/* 132 */     if (o instanceof Iterator) {
/* 133 */       boolean isObject = this.stack[this.stackSize - 2] instanceof JsonObject;
/* 134 */       Iterator<?> iterator = (Iterator)o;
/* 135 */       if (iterator.hasNext()) {
/* 136 */         if (isObject) {
/* 137 */           return JsonToken.NAME;
/*     */         }
/* 139 */         push(iterator.next());
/* 140 */         return peek();
/*     */       } 
/*     */       
/* 143 */       return isObject ? JsonToken.END_OBJECT : JsonToken.END_ARRAY;
/*     */     } 
/* 145 */     if (o instanceof JsonObject)
/* 146 */       return JsonToken.BEGIN_OBJECT; 
/* 147 */     if (o instanceof JsonArray)
/* 148 */       return JsonToken.BEGIN_ARRAY; 
/* 149 */     if (o instanceof JsonPrimitive) {
/* 150 */       JsonPrimitive primitive = (JsonPrimitive)o;
/* 151 */       if (primitive.isString())
/* 152 */         return JsonToken.STRING; 
/* 153 */       if (primitive.isBoolean())
/* 154 */         return JsonToken.BOOLEAN; 
/* 155 */       if (primitive.isNumber()) {
/* 156 */         return JsonToken.NUMBER;
/*     */       }
/* 158 */       throw new AssertionError();
/*     */     } 
/* 160 */     if (o instanceof com.google.gson.JsonNull)
/* 161 */       return JsonToken.NULL; 
/* 162 */     if (o == SENTINEL_CLOSED) {
/* 163 */       throw new IllegalStateException("JsonReader is closed");
/*     */     }
/* 165 */     throw new MalformedJsonException("Custom JsonElement subclass " + o
/* 166 */         .getClass().getName() + " is not supported");
/*     */   }
/*     */ 
/*     */   
/*     */   private Object peekStack() {
/* 171 */     return this.stack[this.stackSize - 1];
/*     */   }
/*     */   
/*     */   @CanIgnoreReturnValue
/*     */   private Object popStack() {
/* 176 */     Object result = this.stack[--this.stackSize];
/* 177 */     this.stack[this.stackSize] = null;
/* 178 */     return result;
/*     */   }
/*     */   
/*     */   private void expect(JsonToken expected) throws IOException {
/* 182 */     if (peek() != expected) {
/* 183 */       throw new IllegalStateException("Expected " + expected + " but was " + 
/* 184 */           peek() + locationString());
/*     */     }
/*     */   }
/*     */   
/*     */   private String nextName(boolean skipName) throws IOException {
/* 189 */     expect(JsonToken.NAME);
/* 190 */     Iterator<?> i = (Iterator)peekStack();
/* 191 */     Map.Entry<?, ?> entry = (Map.Entry<?, ?>)i.next();
/* 192 */     String result = (String)entry.getKey();
/* 193 */     this.pathNames[this.stackSize - 1] = skipName ? "<skipped>" : result;
/* 194 */     push(entry.getValue());
/* 195 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public String nextName() throws IOException {
/* 200 */     return nextName(false);
/*     */   }
/*     */ 
/*     */   
/*     */   public String nextString() throws IOException {
/* 205 */     JsonToken token = peek();
/* 206 */     if (token != JsonToken.STRING && token != JsonToken.NUMBER) {
/* 207 */       throw new IllegalStateException("Expected " + JsonToken.STRING + " but was " + token + 
/* 208 */           locationString());
/*     */     }
/* 210 */     String result = ((JsonPrimitive)popStack()).getAsString();
/* 211 */     if (this.stackSize > 0) {
/* 212 */       this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/*     */     }
/* 214 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean nextBoolean() throws IOException {
/* 219 */     expect(JsonToken.BOOLEAN);
/* 220 */     boolean result = ((JsonPrimitive)popStack()).getAsBoolean();
/* 221 */     if (this.stackSize > 0) {
/* 222 */       this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/*     */     }
/* 224 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public void nextNull() throws IOException {
/* 229 */     expect(JsonToken.NULL);
/* 230 */     popStack();
/* 231 */     if (this.stackSize > 0) {
/* 232 */       this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public double nextDouble() throws IOException {
/* 238 */     JsonToken token = peek();
/* 239 */     if (token != JsonToken.NUMBER && token != JsonToken.STRING) {
/* 240 */       throw new IllegalStateException("Expected " + JsonToken.NUMBER + " but was " + token + 
/* 241 */           locationString());
/*     */     }
/* 243 */     double result = ((JsonPrimitive)peekStack()).getAsDouble();
/* 244 */     if (!isLenient() && (Double.isNaN(result) || Double.isInfinite(result))) {
/* 245 */       throw new MalformedJsonException("JSON forbids NaN and infinities: " + result);
/*     */     }
/* 247 */     popStack();
/* 248 */     if (this.stackSize > 0) {
/* 249 */       this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/*     */     }
/* 251 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public long nextLong() throws IOException {
/* 256 */     JsonToken token = peek();
/* 257 */     if (token != JsonToken.NUMBER && token != JsonToken.STRING) {
/* 258 */       throw new IllegalStateException("Expected " + JsonToken.NUMBER + " but was " + token + 
/* 259 */           locationString());
/*     */     }
/* 261 */     long result = ((JsonPrimitive)peekStack()).getAsLong();
/* 262 */     popStack();
/* 263 */     if (this.stackSize > 0) {
/* 264 */       this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/*     */     }
/* 266 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public int nextInt() throws IOException {
/* 271 */     JsonToken token = peek();
/* 272 */     if (token != JsonToken.NUMBER && token != JsonToken.STRING) {
/* 273 */       throw new IllegalStateException("Expected " + JsonToken.NUMBER + " but was " + token + 
/* 274 */           locationString());
/*     */     }
/* 276 */     int result = ((JsonPrimitive)peekStack()).getAsInt();
/* 277 */     popStack();
/* 278 */     if (this.stackSize > 0) {
/* 279 */       this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/*     */     }
/* 281 */     return result;
/*     */   }
/*     */   
/*     */   JsonElement nextJsonElement() throws IOException {
/* 285 */     JsonToken peeked = peek();
/* 286 */     if (peeked == JsonToken.NAME || peeked == JsonToken.END_ARRAY || peeked == JsonToken.END_OBJECT || peeked == JsonToken.END_DOCUMENT)
/*     */     {
/*     */ 
/*     */       
/* 290 */       throw new IllegalStateException("Unexpected " + peeked + " when reading a JsonElement.");
/*     */     }
/* 292 */     JsonElement element = (JsonElement)peekStack();
/* 293 */     skipValue();
/* 294 */     return element;
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 299 */     this.stack = new Object[] { SENTINEL_CLOSED };
/* 300 */     this.stackSize = 1;
/*     */   }
/*     */   
/*     */   public void skipValue() throws IOException {
/*     */     String unused;
/* 305 */     JsonToken peeked = peek();
/* 306 */     switch (peeked) {
/*     */       
/*     */       case NAME:
/* 309 */         unused = nextName(true);
/*     */       
/*     */       case END_ARRAY:
/* 312 */         endArray();
/*     */       
/*     */       case END_OBJECT:
/* 315 */         endObject();
/*     */       
/*     */       case END_DOCUMENT:
/*     */         return;
/*     */     } 
/*     */     
/* 321 */     popStack();
/* 322 */     if (this.stackSize > 0) {
/* 323 */       this.pathIndices[this.stackSize - 1] = this.pathIndices[this.stackSize - 1] + 1;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 331 */     return getClass().getSimpleName() + locationString();
/*     */   }
/*     */   
/*     */   public void promoteNameToValue() throws IOException {
/* 335 */     expect(JsonToken.NAME);
/* 336 */     Iterator<?> i = (Iterator)peekStack();
/* 337 */     Map.Entry<?, ?> entry = (Map.Entry<?, ?>)i.next();
/* 338 */     push(entry.getValue());
/* 339 */     push(new JsonPrimitive((String)entry.getKey()));
/*     */   }
/*     */   
/*     */   private void push(Object newTop) {
/* 343 */     if (this.stackSize == this.stack.length) {
/* 344 */       int newLength = this.stackSize * 2;
/* 345 */       this.stack = Arrays.copyOf(this.stack, newLength);
/* 346 */       this.pathIndices = Arrays.copyOf(this.pathIndices, newLength);
/* 347 */       this.pathNames = Arrays.<String>copyOf(this.pathNames, newLength);
/*     */     } 
/* 349 */     this.stack[this.stackSize++] = newTop;
/*     */   }
/*     */   
/*     */   private String getPath(boolean usePreviousPath) {
/* 353 */     StringBuilder result = (new StringBuilder()).append('$');
/* 354 */     for (int i = 0; i < this.stackSize; i++) {
/* 355 */       if (this.stack[i] instanceof JsonArray) {
/* 356 */         if (++i < this.stackSize && this.stack[i] instanceof Iterator) {
/* 357 */           int pathIndex = this.pathIndices[i];
/*     */ 
/*     */ 
/*     */           
/* 361 */           if (usePreviousPath && pathIndex > 0 && (i == this.stackSize - 1 || i == this.stackSize - 2)) {
/* 362 */             pathIndex--;
/*     */           }
/* 364 */           result.append('[').append(pathIndex).append(']');
/*     */         } 
/* 366 */       } else if (this.stack[i] instanceof JsonObject && 
/* 367 */         ++i < this.stackSize && this.stack[i] instanceof Iterator) {
/* 368 */         result.append('.');
/* 369 */         if (this.pathNames[i] != null) {
/* 370 */           result.append(this.pathNames[i]);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 375 */     return result.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPath() {
/* 380 */     return getPath(false);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPreviousPath() {
/* 385 */     return getPath(true);
/*     */   }
/*     */   
/*     */   private String locationString() {
/* 389 */     return " at path " + getPath();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\gson\internal\bind\JsonTreeReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */