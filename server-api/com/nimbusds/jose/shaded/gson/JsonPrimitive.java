/*     */ package com.nimbusds.jose.shaded.gson;
/*     */ 
/*     */ import com.nimbusds.jose.shaded.gson.internal.LazilyParsedNumber;
/*     */ import com.nimbusds.jose.shaded.gson.internal.NumberLimits;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class JsonPrimitive
/*     */   extends JsonElement
/*     */ {
/*     */   private final Object value;
/*     */   
/*     */   public JsonPrimitive(Boolean bool) {
/*  49 */     this.value = Objects.requireNonNull(bool);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonPrimitive(Number number) {
/*  59 */     this.value = Objects.requireNonNull(number);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonPrimitive(String string) {
/*  69 */     this.value = Objects.requireNonNull(string);
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
/*     */   public JsonPrimitive(Character c) {
/*  85 */     this.value = ((Character)Objects.<Character>requireNonNull(c)).toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonPrimitive deepCopy() {
/*  95 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBoolean() {
/* 104 */     return this.value instanceof Boolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getAsBoolean() {
/* 115 */     if (isBoolean()) {
/* 116 */       return ((Boolean)this.value).booleanValue();
/*     */     }
/*     */     
/* 119 */     return Boolean.parseBoolean(getAsString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNumber() {
/* 128 */     return this.value instanceof Number;
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
/*     */   public Number getAsNumber() {
/* 140 */     if (this.value instanceof Number)
/* 141 */       return (Number)this.value; 
/* 142 */     if (this.value instanceof String) {
/* 143 */       return (Number)new LazilyParsedNumber((String)this.value);
/*     */     }
/* 145 */     throw new UnsupportedOperationException("Primitive is neither a number nor a string");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isString() {
/* 154 */     return this.value instanceof String;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAsString() {
/* 160 */     if (this.value instanceof String)
/* 161 */       return (String)this.value; 
/* 162 */     if (isNumber())
/* 163 */       return getAsNumber().toString(); 
/* 164 */     if (isBoolean()) {
/* 165 */       return ((Boolean)this.value).toString();
/*     */     }
/* 167 */     throw new AssertionError("Unexpected value type: " + this.value.getClass());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getAsDouble() {
/* 175 */     return isNumber() ? getAsNumber().doubleValue() : Double.parseDouble(getAsString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BigDecimal getAsBigDecimal() {
/* 183 */     return (this.value instanceof BigDecimal) ? 
/* 184 */       (BigDecimal)this.value : 
/* 185 */       NumberLimits.parseBigDecimal(getAsString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BigInteger getAsBigInteger() {
/* 193 */     return (this.value instanceof BigInteger) ? 
/* 194 */       (BigInteger)this.value : (
/* 195 */       isIntegral(this) ? 
/* 196 */       BigInteger.valueOf(getAsNumber().longValue()) : 
/* 197 */       NumberLimits.parseBigInteger(getAsString()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getAsFloat() {
/* 205 */     return isNumber() ? getAsNumber().floatValue() : Float.parseFloat(getAsString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getAsLong() {
/* 216 */     return isNumber() ? getAsNumber().longValue() : Long.parseLong(getAsString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public short getAsShort() {
/* 224 */     return isNumber() ? getAsNumber().shortValue() : Short.parseShort(getAsString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAsInt() {
/* 232 */     return isNumber() ? getAsNumber().intValue() : Integer.parseInt(getAsString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getAsByte() {
/* 240 */     return isNumber() ? getAsNumber().byteValue() : Byte.parseByte(getAsString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public char getAsCharacter() {
/* 251 */     String s = getAsString();
/* 252 */     if (s.isEmpty()) {
/* 253 */       throw new UnsupportedOperationException("String value is empty");
/*     */     }
/* 255 */     return s.charAt(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 262 */     if (this.value == null) {
/* 263 */       return 31;
/*     */     }
/*     */     
/* 266 */     if (isIntegral(this)) {
/* 267 */       long value = getAsNumber().longValue();
/* 268 */       return (int)(value ^ value >>> 32L);
/*     */     } 
/* 270 */     if (this.value instanceof Number) {
/* 271 */       long value = Double.doubleToLongBits(getAsNumber().doubleValue());
/* 272 */       return (int)(value ^ value >>> 32L);
/*     */     } 
/* 274 */     return this.value.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 283 */     if (this == obj) {
/* 284 */       return true;
/*     */     }
/* 286 */     if (obj == null || getClass() != obj.getClass()) {
/* 287 */       return false;
/*     */     }
/* 289 */     JsonPrimitive other = (JsonPrimitive)obj;
/* 290 */     if (this.value == null) {
/* 291 */       return (other.value == null);
/*     */     }
/* 293 */     if (isIntegral(this) && isIntegral(other)) {
/* 294 */       return (this.value instanceof BigInteger || other.value instanceof BigInteger) ? 
/* 295 */         getAsBigInteger().equals(other.getAsBigInteger()) : (
/* 296 */         (getAsNumber().longValue() == other.getAsNumber().longValue()));
/*     */     }
/* 298 */     if (this.value instanceof Number && other.value instanceof Number) {
/* 299 */       if (this.value instanceof BigDecimal && other.value instanceof BigDecimal)
/*     */       {
/* 301 */         return (getAsBigDecimal().compareTo(other.getAsBigDecimal()) == 0);
/*     */       }
/*     */       
/* 304 */       double thisAsDouble = getAsDouble();
/* 305 */       double otherAsDouble = other.getAsDouble();
/*     */       
/* 307 */       return (thisAsDouble == otherAsDouble || (
/* 308 */         Double.isNaN(thisAsDouble) && Double.isNaN(otherAsDouble)));
/*     */     } 
/* 310 */     return this.value.equals(other.value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isIntegral(JsonPrimitive primitive) {
/* 318 */     if (primitive.value instanceof Number) {
/* 319 */       Number number = (Number)primitive.value;
/* 320 */       return (number instanceof BigInteger || number instanceof Long || number instanceof Integer || number instanceof Short || number instanceof Byte);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 326 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\shaded\gson\JsonPrimitive.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */