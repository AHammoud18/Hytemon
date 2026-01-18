/*     */ package com.google.gson.internal.bind;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.ToNumberPolicy;
/*     */ import com.google.gson.ToNumberStrategy;
/*     */ import com.google.gson.TypeAdapter;
/*     */ import com.google.gson.TypeAdapterFactory;
/*     */ import com.google.gson.internal.LinkedTreeMap;
/*     */ import com.google.gson.reflect.TypeToken;
/*     */ import com.google.gson.stream.JsonReader;
/*     */ import com.google.gson.stream.JsonToken;
/*     */ import com.google.gson.stream.JsonWriter;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Deque;
/*     */ import java.util.List;
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
/*     */ public final class ObjectTypeAdapter
/*     */   extends TypeAdapter<Object>
/*     */ {
/*  42 */   private static final TypeAdapterFactory DOUBLE_FACTORY = newFactory((ToNumberStrategy)ToNumberPolicy.DOUBLE);
/*     */   
/*     */   private final Gson gson;
/*     */   private final ToNumberStrategy toNumberStrategy;
/*     */   
/*     */   private ObjectTypeAdapter(Gson gson, ToNumberStrategy toNumberStrategy) {
/*  48 */     this.gson = gson;
/*  49 */     this.toNumberStrategy = toNumberStrategy;
/*     */   }
/*     */   
/*     */   private static TypeAdapterFactory newFactory(final ToNumberStrategy toNumberStrategy) {
/*  53 */     return new TypeAdapterFactory()
/*     */       {
/*     */         public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type)
/*     */         {
/*  57 */           if (type.getRawType() == Object.class) {
/*  58 */             return new ObjectTypeAdapter(gson, toNumberStrategy);
/*     */           }
/*  60 */           return null;
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public static TypeAdapterFactory getFactory(ToNumberStrategy toNumberStrategy) {
/*  66 */     if (toNumberStrategy == ToNumberPolicy.DOUBLE) {
/*  67 */       return DOUBLE_FACTORY;
/*     */     }
/*  69 */     return newFactory(toNumberStrategy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object tryBeginNesting(JsonReader in, JsonToken peeked) throws IOException {
/*  78 */     switch (peeked) {
/*     */       case BEGIN_ARRAY:
/*  80 */         in.beginArray();
/*  81 */         return new ArrayList();
/*     */       case BEGIN_OBJECT:
/*  83 */         in.beginObject();
/*  84 */         return new LinkedTreeMap();
/*     */     } 
/*  86 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Object readTerminal(JsonReader in, JsonToken peeked) throws IOException {
/*  92 */     switch (peeked) {
/*     */       case STRING:
/*  94 */         return in.nextString();
/*     */       case NUMBER:
/*  96 */         return this.toNumberStrategy.readNumber(in);
/*     */       case BOOLEAN:
/*  98 */         return Boolean.valueOf(in.nextBoolean());
/*     */       case NULL:
/* 100 */         in.nextNull();
/* 101 */         return null;
/*     */     } 
/*     */     
/* 104 */     throw new IllegalStateException("Unexpected token: " + peeked);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object read(JsonReader in) throws IOException {
/* 112 */     JsonToken peeked = in.peek();
/*     */     
/* 114 */     Object current = tryBeginNesting(in, peeked);
/* 115 */     if (current == null) {
/* 116 */       return readTerminal(in, peeked);
/*     */     }
/*     */     
/* 119 */     Deque<Object> stack = new ArrayDeque();
/*     */     
/*     */     while (true) {
/* 122 */       while (in.hasNext()) {
/* 123 */         String name = null;
/*     */         
/* 125 */         if (current instanceof Map) {
/* 126 */           name = in.nextName();
/*     */         }
/*     */         
/* 129 */         peeked = in.peek();
/* 130 */         Object value = tryBeginNesting(in, peeked);
/* 131 */         boolean isNesting = (value != null);
/*     */         
/* 133 */         if (value == null) {
/* 134 */           value = readTerminal(in, peeked);
/*     */         }
/*     */         
/* 137 */         if (current instanceof List) {
/*     */           
/* 139 */           List<Object> list = (List<Object>)current;
/* 140 */           list.add(value);
/*     */         } else {
/*     */           
/* 143 */           Map<String, Object> map = (Map<String, Object>)current;
/* 144 */           map.put(name, value);
/*     */         } 
/*     */         
/* 147 */         if (isNesting) {
/* 148 */           stack.addLast(current);
/* 149 */           current = value;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 154 */       if (current instanceof List) {
/* 155 */         in.endArray();
/*     */       } else {
/* 157 */         in.endObject();
/*     */       } 
/*     */       
/* 160 */       if (stack.isEmpty()) {
/* 161 */         return current;
/*     */       }
/*     */       
/* 164 */       current = stack.removeLast();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(JsonWriter out, Object value) throws IOException {
/* 171 */     if (value == null) {
/* 172 */       out.nullValue();
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 177 */     TypeAdapter<Object> typeAdapter = this.gson.getAdapter(value.getClass());
/* 178 */     if (typeAdapter instanceof ObjectTypeAdapter) {
/* 179 */       out.beginObject();
/* 180 */       out.endObject();
/*     */       
/*     */       return;
/*     */     } 
/* 184 */     typeAdapter.write(out, value);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\gson\internal\bind\ObjectTypeAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */