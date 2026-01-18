/*     */ package com.google.gson.internal.bind;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonNull;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonPrimitive;
/*     */ import com.google.gson.TypeAdapter;
/*     */ import com.google.gson.internal.LazilyParsedNumber;
/*     */ import com.google.gson.stream.JsonReader;
/*     */ import com.google.gson.stream.JsonToken;
/*     */ import com.google.gson.stream.JsonWriter;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Deque;
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
/*     */ class JsonElementTypeAdapter
/*     */   extends TypeAdapter<JsonElement>
/*     */ {
/*  36 */   static final JsonElementTypeAdapter ADAPTER = new JsonElementTypeAdapter();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JsonElement tryBeginNesting(JsonReader in, JsonToken peeked) throws IOException {
/*  45 */     switch (peeked) {
/*     */       case BEGIN_ARRAY:
/*  47 */         in.beginArray();
/*  48 */         return (JsonElement)new JsonArray();
/*     */       case BEGIN_OBJECT:
/*  50 */         in.beginObject();
/*  51 */         return (JsonElement)new JsonObject();
/*     */     } 
/*  53 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private JsonElement readTerminal(JsonReader in, JsonToken peeked) throws IOException {
/*     */     String number;
/*  59 */     switch (peeked) {
/*     */       case STRING:
/*  61 */         return (JsonElement)new JsonPrimitive(in.nextString());
/*     */       case NUMBER:
/*  63 */         number = in.nextString();
/*  64 */         return (JsonElement)new JsonPrimitive((Number)new LazilyParsedNumber(number));
/*     */       case BOOLEAN:
/*  66 */         return (JsonElement)new JsonPrimitive(Boolean.valueOf(in.nextBoolean()));
/*     */       case NULL:
/*  68 */         in.nextNull();
/*  69 */         return (JsonElement)JsonNull.INSTANCE;
/*     */     } 
/*     */     
/*  72 */     throw new IllegalStateException("Unexpected token: " + peeked);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonElement read(JsonReader in) throws IOException {
/*  79 */     if (in instanceof JsonTreeReader) {
/*  80 */       return ((JsonTreeReader)in).nextJsonElement();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  85 */     JsonToken peeked = in.peek();
/*     */     
/*  87 */     JsonElement current = tryBeginNesting(in, peeked);
/*  88 */     if (current == null) {
/*  89 */       return readTerminal(in, peeked);
/*     */     }
/*     */     
/*  92 */     Deque<JsonElement> stack = new ArrayDeque<>();
/*     */     
/*     */     while (true) {
/*  95 */       while (in.hasNext()) {
/*  96 */         String name = null;
/*     */         
/*  98 */         if (current instanceof JsonObject) {
/*  99 */           name = in.nextName();
/*     */         }
/*     */         
/* 102 */         peeked = in.peek();
/* 103 */         JsonElement value = tryBeginNesting(in, peeked);
/* 104 */         boolean isNesting = (value != null);
/*     */         
/* 106 */         if (value == null) {
/* 107 */           value = readTerminal(in, peeked);
/*     */         }
/*     */         
/* 110 */         if (current instanceof JsonArray) {
/* 111 */           ((JsonArray)current).add(value);
/*     */         } else {
/* 113 */           ((JsonObject)current).add(name, value);
/*     */         } 
/*     */         
/* 116 */         if (isNesting) {
/* 117 */           stack.addLast(current);
/* 118 */           current = value;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 123 */       if (current instanceof JsonArray) {
/* 124 */         in.endArray();
/*     */       } else {
/* 126 */         in.endObject();
/*     */       } 
/*     */       
/* 129 */       if (stack.isEmpty()) {
/* 130 */         return current;
/*     */       }
/*     */       
/* 133 */       current = stack.removeLast();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(JsonWriter out, JsonElement value) throws IOException {
/* 140 */     if (value == null || value.isJsonNull()) {
/* 141 */       out.nullValue();
/* 142 */     } else if (value.isJsonPrimitive()) {
/* 143 */       JsonPrimitive primitive = value.getAsJsonPrimitive();
/* 144 */       if (primitive.isNumber()) {
/* 145 */         out.value(primitive.getAsNumber());
/* 146 */       } else if (primitive.isBoolean()) {
/* 147 */         out.value(primitive.getAsBoolean());
/*     */       } else {
/* 149 */         out.value(primitive.getAsString());
/*     */       }
/*     */     
/* 152 */     } else if (value.isJsonArray()) {
/* 153 */       out.beginArray();
/* 154 */       for (JsonElement e : value.getAsJsonArray()) {
/* 155 */         write(out, e);
/*     */       }
/* 157 */       out.endArray();
/*     */     }
/* 159 */     else if (value.isJsonObject()) {
/* 160 */       out.beginObject();
/* 161 */       for (Map.Entry<String, JsonElement> e : (Iterable<Map.Entry<String, JsonElement>>)value.getAsJsonObject().entrySet()) {
/* 162 */         out.name(e.getKey());
/* 163 */         write(out, e.getValue());
/*     */       } 
/* 165 */       out.endObject();
/*     */     } else {
/*     */       
/* 168 */       throw new IllegalArgumentException("Couldn't write " + value.getClass());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\gson\internal\bind\JsonElementTypeAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */