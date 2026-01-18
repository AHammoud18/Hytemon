/*    */ package com.google.gson.internal.bind;
/*    */ 
/*    */ import com.google.gson.Gson;
/*    */ import com.google.gson.JsonSyntaxException;
/*    */ import com.google.gson.ToNumberPolicy;
/*    */ import com.google.gson.ToNumberStrategy;
/*    */ import com.google.gson.TypeAdapter;
/*    */ import com.google.gson.TypeAdapterFactory;
/*    */ import com.google.gson.reflect.TypeToken;
/*    */ import com.google.gson.stream.JsonReader;
/*    */ import com.google.gson.stream.JsonToken;
/*    */ import com.google.gson.stream.JsonWriter;
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class NumberTypeAdapter
/*    */   extends TypeAdapter<Number>
/*    */ {
/* 35 */   private static final TypeAdapterFactory LAZILY_PARSED_NUMBER_FACTORY = newFactory((ToNumberStrategy)ToNumberPolicy.LAZILY_PARSED_NUMBER);
/*    */   
/*    */   private final ToNumberStrategy toNumberStrategy;
/*    */   
/*    */   private NumberTypeAdapter(ToNumberStrategy toNumberStrategy) {
/* 40 */     this.toNumberStrategy = toNumberStrategy;
/*    */   }
/*    */   
/*    */   private static TypeAdapterFactory newFactory(ToNumberStrategy toNumberStrategy) {
/* 44 */     final NumberTypeAdapter adapter = new NumberTypeAdapter(toNumberStrategy);
/* 45 */     return new TypeAdapterFactory()
/*    */       {
/*    */         public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type)
/*    */         {
/* 49 */           return (type.getRawType() == Number.class) ? adapter : null;
/*    */         }
/*    */       };
/*    */   }
/*    */   
/*    */   public static TypeAdapterFactory getFactory(ToNumberStrategy toNumberStrategy) {
/* 55 */     if (toNumberStrategy == ToNumberPolicy.LAZILY_PARSED_NUMBER) {
/* 56 */       return LAZILY_PARSED_NUMBER_FACTORY;
/*    */     }
/* 58 */     return newFactory(toNumberStrategy);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Number read(JsonReader in) throws IOException {
/* 64 */     JsonToken jsonToken = in.peek();
/* 65 */     switch (jsonToken) {
/*    */       case NULL:
/* 67 */         in.nextNull();
/* 68 */         return null;
/*    */       case NUMBER:
/*    */       case STRING:
/* 71 */         return this.toNumberStrategy.readNumber(in);
/*    */     } 
/* 73 */     throw new JsonSyntaxException("Expecting number, got: " + jsonToken + "; at path " + in
/* 74 */         .getPath());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(JsonWriter out, Number value) throws IOException {
/* 80 */     out.value(value);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\gson\internal\bind\NumberTypeAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */