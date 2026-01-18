/*     */ package com.google.gson;
/*     */ 
/*     */ import com.google.errorprone.annotations.CanIgnoreReturnValue;
/*     */ import com.google.gson.internal.NonNullElementWrapperList;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class JsonArray
/*     */   extends JsonElement
/*     */   implements Iterable<JsonElement>
/*     */ {
/*     */   private final ArrayList<JsonElement> elements;
/*     */   
/*     */   public JsonArray() {
/*  48 */     this.elements = new ArrayList<>();
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
/*     */   public JsonArray(int capacity) {
/*  60 */     this.elements = new ArrayList<>(capacity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonArray deepCopy() {
/*  70 */     if (!this.elements.isEmpty()) {
/*  71 */       JsonArray result = new JsonArray(this.elements.size());
/*  72 */       for (JsonElement element : this.elements) {
/*  73 */         result.add(element.deepCopy());
/*     */       }
/*  75 */       return result;
/*     */     } 
/*  77 */     return new JsonArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(Boolean bool) {
/*  87 */     this.elements.add((bool == null) ? JsonNull.INSTANCE : new JsonPrimitive(bool));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(Character character) {
/*  97 */     this.elements.add((character == null) ? JsonNull.INSTANCE : new JsonPrimitive(character));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(Number number) {
/* 107 */     this.elements.add((number == null) ? JsonNull.INSTANCE : new JsonPrimitive(number));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(String string) {
/* 117 */     this.elements.add((string == null) ? JsonNull.INSTANCE : new JsonPrimitive(string));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(JsonElement element) {
/* 126 */     if (element == null) {
/* 127 */       element = JsonNull.INSTANCE;
/*     */     }
/* 129 */     this.elements.add(element);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAll(JsonArray array) {
/* 138 */     this.elements.addAll(array.elements);
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
/*     */   @CanIgnoreReturnValue
/*     */   public JsonElement set(int index, JsonElement element) {
/* 151 */     return this.elements.set(index, (element == null) ? JsonNull.INSTANCE : element);
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
/*     */   @CanIgnoreReturnValue
/*     */   public boolean remove(JsonElement element) {
/* 164 */     return this.elements.remove(element);
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
/*     */   @CanIgnoreReturnValue
/*     */   public JsonElement remove(int index) {
/* 179 */     return this.elements.remove(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(JsonElement element) {
/* 190 */     return this.elements.contains(element);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 199 */     return this.elements.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 209 */     return this.elements.isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<JsonElement> iterator() {
/* 220 */     return this.elements.iterator();
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
/*     */   public JsonElement get(int i) {
/* 232 */     return this.elements.get(i);
/*     */   }
/*     */   
/*     */   private JsonElement getAsSingleElement() {
/* 236 */     int size = this.elements.size();
/* 237 */     if (size == 1) {
/* 238 */       return this.elements.get(0);
/*     */     }
/* 240 */     throw new IllegalStateException("Array must have size 1, but has size " + size);
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
/* 253 */     return getAsSingleElement().getAsNumber();
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
/* 266 */     return getAsSingleElement().getAsString();
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
/*     */   public double getAsDouble() {
/* 279 */     return getAsSingleElement().getAsDouble();
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
/*     */   public BigDecimal getAsBigDecimal() {
/* 293 */     return getAsSingleElement().getAsBigDecimal();
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
/*     */   public BigInteger getAsBigInteger() {
/* 307 */     return getAsSingleElement().getAsBigInteger();
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
/*     */   public float getAsFloat() {
/* 320 */     return getAsSingleElement().getAsFloat();
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
/*     */   public long getAsLong() {
/* 333 */     return getAsSingleElement().getAsLong();
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
/*     */   public int getAsInt() {
/* 346 */     return getAsSingleElement().getAsInt();
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
/*     */   public byte getAsByte() {
/* 359 */     return getAsSingleElement().getAsByte();
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
/*     */   @Deprecated
/*     */   public char getAsCharacter() {
/* 375 */     return getAsSingleElement().getAsCharacter();
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
/*     */   public short getAsShort() {
/* 388 */     return getAsSingleElement().getAsShort();
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
/* 401 */     return getAsSingleElement().getAsBoolean();
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
/*     */   public List<JsonElement> asList() {
/* 416 */     return (List<JsonElement>)new NonNullElementWrapperList(this.elements);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 425 */     return (o == this || (o instanceof JsonArray && ((JsonArray)o).elements.equals(this.elements)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 434 */     return this.elements.hashCode();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\gson\JsonArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */