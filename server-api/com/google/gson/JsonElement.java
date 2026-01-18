/*     */ package com.google.gson;
/*     */ 
/*     */ import com.google.errorprone.annotations.CanIgnoreReturnValue;
/*     */ import com.google.gson.internal.Streams;
/*     */ import com.google.gson.stream.JsonWriter;
/*     */ import java.io.IOException;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class JsonElement
/*     */ {
/*     */   public abstract JsonElement deepCopy();
/*     */   
/*     */   public boolean isJsonArray() {
/* 120 */     return this instanceof JsonArray;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isJsonObject() {
/* 129 */     return this instanceof JsonObject;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isJsonPrimitive() {
/* 138 */     return this instanceof JsonPrimitive;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isJsonNull() {
/* 148 */     return this instanceof JsonNull;
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
/*     */   public JsonObject getAsJsonObject() {
/* 161 */     if (isJsonObject()) {
/* 162 */       return (JsonObject)this;
/*     */     }
/* 164 */     throw new IllegalStateException("Not a JSON Object: " + this);
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
/*     */   public JsonArray getAsJsonArray() {
/* 176 */     if (isJsonArray()) {
/* 177 */       return (JsonArray)this;
/*     */     }
/* 179 */     throw new IllegalStateException("Not a JSON Array: " + this);
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
/*     */   public JsonPrimitive getAsJsonPrimitive() {
/* 192 */     if (isJsonPrimitive()) {
/* 193 */       return (JsonPrimitive)this;
/*     */     }
/* 195 */     throw new IllegalStateException("Not a JSON Primitive: " + this);
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
/*     */   @CanIgnoreReturnValue
/*     */   public JsonNull getAsJsonNull() {
/* 209 */     if (isJsonNull()) {
/* 210 */       return (JsonNull)this;
/*     */     }
/* 212 */     throw new IllegalStateException("Not a JSON Null: " + this);
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
/*     */   public boolean getAsBoolean() {
/* 225 */     throw new UnsupportedOperationException(getClass().getSimpleName());
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
/*     */   public Number getAsNumber() {
/* 238 */     throw new UnsupportedOperationException(getClass().getSimpleName());
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
/*     */   public String getAsString() {
/* 251 */     throw new UnsupportedOperationException(getClass().getSimpleName());
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
/*     */   public double getAsDouble() {
/* 265 */     throw new UnsupportedOperationException(getClass().getSimpleName());
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
/*     */   public float getAsFloat() {
/* 279 */     throw new UnsupportedOperationException(getClass().getSimpleName());
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
/*     */   public long getAsLong() {
/* 293 */     throw new UnsupportedOperationException(getClass().getSimpleName());
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
/*     */   public int getAsInt() {
/* 307 */     throw new UnsupportedOperationException(getClass().getSimpleName());
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
/*     */   public byte getAsByte() {
/* 322 */     throw new UnsupportedOperationException(getClass().getSimpleName());
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
/*     */   @Deprecated
/*     */   public char getAsCharacter() {
/* 339 */     throw new UnsupportedOperationException(getClass().getSimpleName());
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
/*     */   public BigDecimal getAsBigDecimal() {
/* 354 */     throw new UnsupportedOperationException(getClass().getSimpleName());
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
/*     */   public BigInteger getAsBigInteger() {
/* 369 */     throw new UnsupportedOperationException(getClass().getSimpleName());
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
/*     */   public short getAsShort() {
/* 383 */     throw new UnsupportedOperationException(getClass().getSimpleName());
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
/*     */   public String toString() {
/*     */     try {
/* 424 */       StringBuilder stringBuilder = new StringBuilder();
/* 425 */       JsonWriter jsonWriter = new JsonWriter(Streams.writerForAppendable(stringBuilder));
/*     */ 
/*     */       
/* 428 */       jsonWriter.setStrictness(Strictness.LENIENT);
/* 429 */       Streams.write(this, jsonWriter);
/* 430 */       return stringBuilder.toString();
/* 431 */     } catch (IOException e) {
/* 432 */       throw new AssertionError(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\gson\JsonElement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */