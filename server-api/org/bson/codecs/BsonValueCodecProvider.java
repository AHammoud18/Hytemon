/*     */ package org.bson.codecs;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.bson.BsonArray;
/*     */ import org.bson.BsonBinary;
/*     */ import org.bson.BsonBoolean;
/*     */ import org.bson.BsonDateTime;
/*     */ import org.bson.BsonDbPointer;
/*     */ import org.bson.BsonDecimal128;
/*     */ import org.bson.BsonDocument;
/*     */ import org.bson.BsonDocumentWrapper;
/*     */ import org.bson.BsonDouble;
/*     */ import org.bson.BsonInt32;
/*     */ import org.bson.BsonInt64;
/*     */ import org.bson.BsonJavaScript;
/*     */ import org.bson.BsonJavaScriptWithScope;
/*     */ import org.bson.BsonMaxKey;
/*     */ import org.bson.BsonMinKey;
/*     */ import org.bson.BsonNull;
/*     */ import org.bson.BsonObjectId;
/*     */ import org.bson.BsonRegularExpression;
/*     */ import org.bson.BsonString;
/*     */ import org.bson.BsonSymbol;
/*     */ import org.bson.BsonTimestamp;
/*     */ import org.bson.BsonType;
/*     */ import org.bson.BsonUndefined;
/*     */ import org.bson.BsonValue;
/*     */ import org.bson.RawBsonDocument;
/*     */ import org.bson.codecs.configuration.CodecProvider;
/*     */ import org.bson.codecs.configuration.CodecRegistry;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BsonValueCodecProvider
/*     */   implements CodecProvider
/*     */ {
/*     */   private static final BsonTypeClassMap DEFAULT_BSON_TYPE_CLASS_MAP;
/*  58 */   private final Map<Class<?>, Codec<?>> codecs = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BsonValueCodecProvider() {
/*  64 */     addCodecs();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Class<? extends BsonValue> getClassForBsonType(BsonType bsonType) {
/*  74 */     return (Class)DEFAULT_BSON_TYPE_CLASS_MAP.get(bsonType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BsonTypeClassMap getBsonTypeClassMap() {
/*  84 */     return DEFAULT_BSON_TYPE_CLASS_MAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> Codec<T> get(Class<T> clazz, CodecRegistry registry) {
/*  90 */     if (this.codecs.containsKey(clazz)) {
/*  91 */       return (Codec<T>)this.codecs.get(clazz);
/*     */     }
/*     */     
/*  94 */     if (clazz == BsonJavaScriptWithScope.class) {
/*  95 */       return new BsonJavaScriptWithScopeCodec(registry.get(BsonDocument.class));
/*     */     }
/*     */     
/*  98 */     if (clazz == BsonValue.class) {
/*  99 */       return new BsonValueCodec(registry);
/*     */     }
/*     */     
/* 102 */     if (clazz == BsonDocumentWrapper.class) {
/* 103 */       return new BsonDocumentWrapperCodec(registry.get(BsonDocument.class));
/*     */     }
/*     */     
/* 106 */     if (clazz == RawBsonDocument.class) {
/* 107 */       return new RawBsonDocumentCodec();
/*     */     }
/*     */     
/* 110 */     if (BsonDocument.class.isAssignableFrom(clazz)) {
/* 111 */       return new BsonDocumentCodec(registry);
/*     */     }
/*     */     
/* 114 */     if (BsonArray.class.isAssignableFrom(clazz)) {
/* 115 */       return new BsonArrayCodec(registry);
/*     */     }
/*     */     
/* 118 */     return null;
/*     */   }
/*     */   
/*     */   private void addCodecs() {
/* 122 */     addCodec(new BsonNullCodec());
/* 123 */     addCodec(new BsonBinaryCodec());
/* 124 */     addCodec(new BsonBooleanCodec());
/* 125 */     addCodec(new BsonDateTimeCodec());
/* 126 */     addCodec(new BsonDBPointerCodec());
/* 127 */     addCodec(new BsonDoubleCodec());
/* 128 */     addCodec(new BsonInt32Codec());
/* 129 */     addCodec(new BsonInt64Codec());
/* 130 */     addCodec(new BsonDecimal128Codec());
/* 131 */     addCodec(new BsonMinKeyCodec());
/* 132 */     addCodec(new BsonMaxKeyCodec());
/* 133 */     addCodec(new BsonJavaScriptCodec());
/* 134 */     addCodec(new BsonObjectIdCodec());
/* 135 */     addCodec(new BsonRegularExpressionCodec());
/* 136 */     addCodec(new BsonStringCodec());
/* 137 */     addCodec(new BsonSymbolCodec());
/* 138 */     addCodec(new BsonTimestampCodec());
/* 139 */     addCodec(new BsonUndefinedCodec());
/*     */   }
/*     */   
/*     */   private <T extends BsonValue> void addCodec(Codec<T> codec) {
/* 143 */     this.codecs.put(codec.getEncoderClass(), codec);
/*     */   }
/*     */   
/*     */   static {
/* 147 */     Map<BsonType, Class<?>> map = new HashMap<>();
/*     */     
/* 149 */     map.put(BsonType.NULL, BsonNull.class);
/* 150 */     map.put(BsonType.ARRAY, BsonArray.class);
/* 151 */     map.put(BsonType.BINARY, BsonBinary.class);
/* 152 */     map.put(BsonType.BOOLEAN, BsonBoolean.class);
/* 153 */     map.put(BsonType.DATE_TIME, BsonDateTime.class);
/* 154 */     map.put(BsonType.DB_POINTER, BsonDbPointer.class);
/* 155 */     map.put(BsonType.DOCUMENT, BsonDocument.class);
/* 156 */     map.put(BsonType.DOUBLE, BsonDouble.class);
/* 157 */     map.put(BsonType.INT32, BsonInt32.class);
/* 158 */     map.put(BsonType.INT64, BsonInt64.class);
/* 159 */     map.put(BsonType.DECIMAL128, BsonDecimal128.class);
/* 160 */     map.put(BsonType.MAX_KEY, BsonMaxKey.class);
/* 161 */     map.put(BsonType.MIN_KEY, BsonMinKey.class);
/* 162 */     map.put(BsonType.JAVASCRIPT, BsonJavaScript.class);
/* 163 */     map.put(BsonType.JAVASCRIPT_WITH_SCOPE, BsonJavaScriptWithScope.class);
/* 164 */     map.put(BsonType.OBJECT_ID, BsonObjectId.class);
/* 165 */     map.put(BsonType.REGULAR_EXPRESSION, BsonRegularExpression.class);
/* 166 */     map.put(BsonType.STRING, BsonString.class);
/* 167 */     map.put(BsonType.SYMBOL, BsonSymbol.class);
/* 168 */     map.put(BsonType.TIMESTAMP, BsonTimestamp.class);
/* 169 */     map.put(BsonType.UNDEFINED, BsonUndefined.class);
/*     */     
/* 171 */     DEFAULT_BSON_TYPE_CLASS_MAP = new BsonTypeClassMap(map);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\BsonValueCodecProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */