/*     */ package org.bson;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class BsonValue
/*     */ {
/*     */   public abstract BsonType getBsonType();
/*     */   
/*     */   public BsonDocument asDocument() {
/*  47 */     throwIfInvalidType(BsonType.DOCUMENT);
/*  48 */     return (BsonDocument)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BsonArray asArray() {
/*  58 */     throwIfInvalidType(BsonType.ARRAY);
/*  59 */     return (BsonArray)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BsonString asString() {
/*  69 */     throwIfInvalidType(BsonType.STRING);
/*  70 */     return (BsonString)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BsonNumber asNumber() {
/*  80 */     if (getBsonType() != BsonType.INT32 && getBsonType() != BsonType.INT64 && getBsonType() != BsonType.DOUBLE)
/*  81 */       throw new BsonInvalidOperationException(String.format("Value expected to be of a numerical BSON type is of unexpected type %s", new Object[] {
/*  82 */               getBsonType()
/*     */             })); 
/*  84 */     return (BsonNumber)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BsonInt32 asInt32() {
/*  94 */     throwIfInvalidType(BsonType.INT32);
/*  95 */     return (BsonInt32)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BsonInt64 asInt64() {
/* 105 */     throwIfInvalidType(BsonType.INT64);
/* 106 */     return (BsonInt64)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BsonDecimal128 asDecimal128() {
/* 117 */     throwIfInvalidType(BsonType.DECIMAL128);
/* 118 */     return (BsonDecimal128)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BsonDouble asDouble() {
/* 128 */     throwIfInvalidType(BsonType.DOUBLE);
/* 129 */     return (BsonDouble)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BsonBoolean asBoolean() {
/* 139 */     throwIfInvalidType(BsonType.BOOLEAN);
/* 140 */     return (BsonBoolean)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BsonObjectId asObjectId() {
/* 150 */     throwIfInvalidType(BsonType.OBJECT_ID);
/* 151 */     return (BsonObjectId)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BsonDbPointer asDBPointer() {
/* 161 */     throwIfInvalidType(BsonType.DB_POINTER);
/* 162 */     return (BsonDbPointer)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BsonTimestamp asTimestamp() {
/* 172 */     throwIfInvalidType(BsonType.TIMESTAMP);
/* 173 */     return (BsonTimestamp)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BsonBinary asBinary() {
/* 183 */     throwIfInvalidType(BsonType.BINARY);
/* 184 */     return (BsonBinary)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BsonDateTime asDateTime() {
/* 194 */     throwIfInvalidType(BsonType.DATE_TIME);
/* 195 */     return (BsonDateTime)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BsonSymbol asSymbol() {
/* 205 */     throwIfInvalidType(BsonType.SYMBOL);
/* 206 */     return (BsonSymbol)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BsonRegularExpression asRegularExpression() {
/* 216 */     throwIfInvalidType(BsonType.REGULAR_EXPRESSION);
/* 217 */     return (BsonRegularExpression)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BsonJavaScript asJavaScript() {
/* 227 */     throwIfInvalidType(BsonType.JAVASCRIPT);
/* 228 */     return (BsonJavaScript)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BsonJavaScriptWithScope asJavaScriptWithScope() {
/* 238 */     throwIfInvalidType(BsonType.JAVASCRIPT_WITH_SCOPE);
/* 239 */     return (BsonJavaScriptWithScope)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNull() {
/* 249 */     return this instanceof BsonNull;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDocument() {
/* 258 */     return this instanceof BsonDocument;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isArray() {
/* 267 */     return this instanceof BsonArray;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isString() {
/* 276 */     return this instanceof BsonString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNumber() {
/* 285 */     return (isInt32() || isInt64() || isDouble());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInt32() {
/* 294 */     return this instanceof BsonInt32;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInt64() {
/* 303 */     return this instanceof BsonInt64;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDecimal128() {
/* 313 */     return this instanceof BsonDecimal128;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDouble() {
/* 322 */     return this instanceof BsonDouble;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBoolean() {
/* 332 */     return this instanceof BsonBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isObjectId() {
/* 342 */     return this instanceof BsonObjectId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDBPointer() {
/* 351 */     return this instanceof BsonDbPointer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTimestamp() {
/* 360 */     return this instanceof BsonTimestamp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBinary() {
/* 369 */     return this instanceof BsonBinary;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDateTime() {
/* 378 */     return this instanceof BsonDateTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSymbol() {
/* 387 */     return this instanceof BsonSymbol;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRegularExpression() {
/* 396 */     return this instanceof BsonRegularExpression;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isJavaScript() {
/* 405 */     return this instanceof BsonJavaScript;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isJavaScriptWithScope() {
/* 414 */     return this instanceof BsonJavaScriptWithScope;
/*     */   }
/*     */   
/*     */   private void throwIfInvalidType(BsonType expectedType) {
/* 418 */     if (getBsonType() != expectedType)
/* 419 */       throw new BsonInvalidOperationException(String.format("Value expected to be of type %s is of unexpected type %s", new Object[] { expectedType, 
/* 420 */               getBsonType() })); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\BsonValue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */