/*     */ package org.bson;
/*     */ 
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.io.StringWriter;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.util.Collection;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.bson.codecs.BsonDocumentCodec;
/*     */ import org.bson.codecs.DecoderContext;
/*     */ import org.bson.codecs.EncoderContext;
/*     */ import org.bson.codecs.configuration.CodecRegistry;
/*     */ import org.bson.conversions.Bson;
/*     */ import org.bson.io.BasicOutputBuffer;
/*     */ import org.bson.io.BsonOutput;
/*     */ import org.bson.json.JsonMode;
/*     */ import org.bson.json.JsonReader;
/*     */ import org.bson.json.JsonWriter;
/*     */ import org.bson.json.JsonWriterSettings;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BsonDocument
/*     */   extends BsonValue
/*     */   implements Map<String, BsonValue>, Cloneable, Bson, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  52 */   private final Map<String, BsonValue> map = new LinkedHashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BsonDocument parse(String json) {
/*  63 */     return (new BsonDocumentCodec()).decode((BsonReader)new JsonReader(json), DecoderContext.builder().build());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BsonDocument(List<BsonElement> bsonElements) {
/*  72 */     for (BsonElement cur : bsonElements) {
/*  73 */       put(cur.getName(), cur.getValue());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BsonDocument(String key, BsonValue value) {
/*  84 */     put(key, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <C> BsonDocument toBsonDocument(Class<C> documentClass, CodecRegistry codecRegistry) {
/*  95 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public BsonType getBsonType() {
/* 100 */     return BsonType.DOCUMENT;
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 105 */     return this.map.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 110 */     return this.map.isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsKey(Object key) {
/* 115 */     return this.map.containsKey(key);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsValue(Object value) {
/* 120 */     return this.map.containsValue(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public BsonValue get(Object key) {
/* 125 */     return this.map.get(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BsonDocument getDocument(Object key) {
/* 136 */     throwIfKeyAbsent(key);
/* 137 */     return get(key).asDocument();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BsonArray getArray(Object key) {
/* 148 */     throwIfKeyAbsent(key);
/*     */     
/* 150 */     return get(key).asArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BsonNumber getNumber(Object key) {
/* 161 */     throwIfKeyAbsent(key);
/* 162 */     return get(key).asNumber();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BsonInt32 getInt32(Object key) {
/* 173 */     throwIfKeyAbsent(key);
/* 174 */     return get(key).asInt32();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BsonInt64 getInt64(Object key) {
/* 185 */     throwIfKeyAbsent(key);
/* 186 */     return get(key).asInt64();
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
/*     */   public BsonDecimal128 getDecimal128(Object key) {
/* 198 */     throwIfKeyAbsent(key);
/* 199 */     return get(key).asDecimal128();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BsonDouble getDouble(Object key) {
/* 210 */     throwIfKeyAbsent(key);
/* 211 */     return get(key).asDouble();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BsonBoolean getBoolean(Object key) {
/* 222 */     throwIfKeyAbsent(key);
/* 223 */     return get(key).asBoolean();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BsonString getString(Object key) {
/* 234 */     throwIfKeyAbsent(key);
/* 235 */     return get(key).asString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BsonDateTime getDateTime(Object key) {
/* 246 */     throwIfKeyAbsent(key);
/* 247 */     return get(key).asDateTime();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BsonTimestamp getTimestamp(Object key) {
/* 258 */     throwIfKeyAbsent(key);
/* 259 */     return get(key).asTimestamp();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BsonObjectId getObjectId(Object key) {
/* 270 */     throwIfKeyAbsent(key);
/* 271 */     return get(key).asObjectId();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BsonRegularExpression getRegularExpression(Object key) {
/* 282 */     throwIfKeyAbsent(key);
/* 283 */     return get(key).asRegularExpression();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BsonBinary getBinary(Object key) {
/* 294 */     throwIfKeyAbsent(key);
/* 295 */     return get(key).asBinary();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNull(Object key) {
/* 305 */     if (!containsKey(key)) {
/* 306 */       return false;
/*     */     }
/* 308 */     return get(key).isNull();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDocument(Object key) {
/* 318 */     if (!containsKey(key)) {
/* 319 */       return false;
/*     */     }
/* 321 */     return get(key).isDocument();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isArray(Object key) {
/* 331 */     if (!containsKey(key)) {
/* 332 */       return false;
/*     */     }
/* 334 */     return get(key).isArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNumber(Object key) {
/* 344 */     if (!containsKey(key)) {
/* 345 */       return false;
/*     */     }
/* 347 */     return get(key).isNumber();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInt32(Object key) {
/* 357 */     if (!containsKey(key)) {
/* 358 */       return false;
/*     */     }
/* 360 */     return get(key).isInt32();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInt64(Object key) {
/* 370 */     if (!containsKey(key)) {
/* 371 */       return false;
/*     */     }
/* 373 */     return get(key).isInt64();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDecimal128(Object key) {
/* 384 */     if (!containsKey(key)) {
/* 385 */       return false;
/*     */     }
/* 387 */     return get(key).isDecimal128();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDouble(Object key) {
/* 398 */     if (!containsKey(key)) {
/* 399 */       return false;
/*     */     }
/* 401 */     return get(key).isDouble();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBoolean(Object key) {
/* 411 */     if (!containsKey(key)) {
/* 412 */       return false;
/*     */     }
/* 414 */     return get(key).isBoolean();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isString(Object key) {
/* 424 */     if (!containsKey(key)) {
/* 425 */       return false;
/*     */     }
/* 427 */     return get(key).isString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDateTime(Object key) {
/* 437 */     if (!containsKey(key)) {
/* 438 */       return false;
/*     */     }
/* 440 */     return get(key).isDateTime();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTimestamp(Object key) {
/* 450 */     if (!containsKey(key)) {
/* 451 */       return false;
/*     */     }
/* 453 */     return get(key).isTimestamp();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isObjectId(Object key) {
/* 463 */     if (!containsKey(key)) {
/* 464 */       return false;
/*     */     }
/* 466 */     return get(key).isObjectId();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBinary(Object key) {
/* 476 */     if (!containsKey(key)) {
/* 477 */       return false;
/*     */     }
/* 479 */     return get(key).isBinary();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BsonValue get(Object key, BsonValue defaultValue) {
/* 490 */     BsonValue value = get(key);
/* 491 */     return (value != null) ? value : defaultValue;
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
/*     */   public BsonDocument getDocument(Object key, BsonDocument defaultValue) {
/* 504 */     if (!containsKey(key)) {
/* 505 */       return defaultValue;
/*     */     }
/* 507 */     return get(key).asDocument();
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
/*     */   public BsonArray getArray(Object key, BsonArray defaultValue) {
/* 520 */     if (!containsKey(key)) {
/* 521 */       return defaultValue;
/*     */     }
/* 523 */     return get(key).asArray();
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
/*     */   public BsonNumber getNumber(Object key, BsonNumber defaultValue) {
/* 536 */     if (!containsKey(key)) {
/* 537 */       return defaultValue;
/*     */     }
/* 539 */     return get(key).asNumber();
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
/*     */   public BsonInt32 getInt32(Object key, BsonInt32 defaultValue) {
/* 552 */     if (!containsKey(key)) {
/* 553 */       return defaultValue;
/*     */     }
/* 555 */     return get(key).asInt32();
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
/*     */   public BsonInt64 getInt64(Object key, BsonInt64 defaultValue) {
/* 568 */     if (!containsKey(key)) {
/* 569 */       return defaultValue;
/*     */     }
/* 571 */     return get(key).asInt64();
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
/*     */   public BsonDecimal128 getDecimal128(Object key, BsonDecimal128 defaultValue) {
/* 585 */     if (!containsKey(key)) {
/* 586 */       return defaultValue;
/*     */     }
/* 588 */     return get(key).asDecimal128();
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
/*     */   public BsonDouble getDouble(Object key, BsonDouble defaultValue) {
/* 601 */     if (!containsKey(key)) {
/* 602 */       return defaultValue;
/*     */     }
/* 604 */     return get(key).asDouble();
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
/*     */   public BsonBoolean getBoolean(Object key, BsonBoolean defaultValue) {
/* 617 */     if (!containsKey(key)) {
/* 618 */       return defaultValue;
/*     */     }
/* 620 */     return get(key).asBoolean();
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
/*     */   public BsonString getString(Object key, BsonString defaultValue) {
/* 633 */     if (!containsKey(key)) {
/* 634 */       return defaultValue;
/*     */     }
/* 636 */     return get(key).asString();
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
/*     */   public BsonDateTime getDateTime(Object key, BsonDateTime defaultValue) {
/* 649 */     if (!containsKey(key)) {
/* 650 */       return defaultValue;
/*     */     }
/* 652 */     return get(key).asDateTime();
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
/*     */   public BsonTimestamp getTimestamp(Object key, BsonTimestamp defaultValue) {
/* 665 */     if (!containsKey(key)) {
/* 666 */       return defaultValue;
/*     */     }
/* 668 */     return get(key).asTimestamp();
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
/*     */   public BsonObjectId getObjectId(Object key, BsonObjectId defaultValue) {
/* 681 */     if (!containsKey(key)) {
/* 682 */       return defaultValue;
/*     */     }
/* 684 */     return get(key).asObjectId();
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
/*     */   public BsonBinary getBinary(Object key, BsonBinary defaultValue) {
/* 697 */     if (!containsKey(key)) {
/* 698 */       return defaultValue;
/*     */     }
/* 700 */     return get(key).asBinary();
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
/*     */   public BsonRegularExpression getRegularExpression(Object key, BsonRegularExpression defaultValue) {
/* 713 */     if (!containsKey(key)) {
/* 714 */       return defaultValue;
/*     */     }
/* 716 */     return get(key).asRegularExpression();
/*     */   }
/*     */ 
/*     */   
/*     */   public BsonValue put(String key, BsonValue value) {
/* 721 */     if (value == null) {
/* 722 */       throw new IllegalArgumentException(String.format("The value for key %s can not be null", new Object[] { key }));
/*     */     }
/* 724 */     if (key.contains("\000")) {
/* 725 */       throw new BSONException(String.format("BSON cstring '%s' is not valid because it contains a null character at index %d", new Object[] { key, 
/* 726 */               Integer.valueOf(key.indexOf(false)) }));
/*     */     }
/* 728 */     return this.map.put(key, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public BsonValue remove(Object key) {
/* 733 */     return this.map.remove(key);
/*     */   }
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends String, ? extends BsonValue> m) {
/* 738 */     for (Map.Entry<? extends String, ? extends BsonValue> cur : m.entrySet()) {
/* 739 */       put(cur.getKey(), cur.getValue());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 745 */     this.map.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<String> keySet() {
/* 750 */     return this.map.keySet();
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<BsonValue> values() {
/* 755 */     return this.map.values();
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<Map.Entry<String, BsonValue>> entrySet() {
/* 760 */     return this.map.entrySet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BsonDocument append(String key, BsonValue value) {
/* 771 */     put(key, value);
/* 772 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFirstKey() {
/* 783 */     return keySet().iterator().next();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BsonReader asBsonReader() {
/* 794 */     return new BsonDocumentReader(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 799 */     if (this == o) {
/* 800 */       return true;
/*     */     }
/* 802 */     if (!(o instanceof BsonDocument)) {
/* 803 */       return false;
/*     */     }
/*     */     
/* 806 */     BsonDocument that = (BsonDocument)o;
/*     */     
/* 808 */     return entrySet().equals(that.entrySet());
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 813 */     return entrySet().hashCode();
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
/*     */   public String toJson() {
/* 826 */     return toJson(JsonWriterSettings.builder().outputMode(JsonMode.RELAXED).build());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toJson(JsonWriterSettings settings) {
/* 835 */     StringWriter writer = new StringWriter();
/* 836 */     (new BsonDocumentCodec()).encode((BsonWriter)new JsonWriter(writer, settings), this, EncoderContext.builder().build());
/* 837 */     return writer.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 842 */     return toJson();
/*     */   }
/*     */ 
/*     */   
/*     */   public BsonDocument clone() {
/* 847 */     BsonDocument to = new BsonDocument();
/* 848 */     for (Map.Entry<String, BsonValue> cur : entrySet()) {
/* 849 */       switch (((BsonValue)cur.getValue()).getBsonType()) {
/*     */         case DOCUMENT:
/* 851 */           to.put(cur.getKey(), ((BsonValue)cur.getValue()).asDocument().clone());
/*     */           continue;
/*     */         case ARRAY:
/* 854 */           to.put(cur.getKey(), ((BsonValue)cur.getValue()).asArray().clone());
/*     */           continue;
/*     */         case BINARY:
/* 857 */           to.put(cur.getKey(), BsonBinary.clone(((BsonValue)cur.getValue()).asBinary()));
/*     */           continue;
/*     */         case JAVASCRIPT_WITH_SCOPE:
/* 860 */           to.put(cur.getKey(), BsonJavaScriptWithScope.clone(((BsonValue)cur.getValue()).asJavaScriptWithScope()));
/*     */           continue;
/*     */       } 
/* 863 */       to.put(cur.getKey(), cur.getValue());
/*     */     } 
/*     */     
/* 866 */     return to;
/*     */   }
/*     */   
/*     */   private void throwIfKeyAbsent(Object key) {
/* 870 */     if (!containsKey(key)) {
/* 871 */       throw new BsonInvalidOperationException("Document does not contain key " + key);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private Object writeReplace() {
/* 877 */     return new SerializationProxy(this);
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream stream) throws InvalidObjectException {
/* 882 */     throw new InvalidObjectException("Proxy required");
/*     */   }
/*     */   
/*     */   public BsonDocument() {}
/*     */   
/*     */   private static class SerializationProxy implements Serializable {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     SerializationProxy(BsonDocument document) {
/* 891 */       BasicOutputBuffer buffer = new BasicOutputBuffer();
/* 892 */       (new BsonDocumentCodec()).encode(new BsonBinaryWriter((BsonOutput)buffer), document, EncoderContext.builder().build());
/* 893 */       this.bytes = new byte[buffer.size()];
/* 894 */       int curPos = 0;
/* 895 */       for (ByteBuf cur : buffer.getByteBuffers()) {
/* 896 */         System.arraycopy(cur.array(), cur.position(), this.bytes, curPos, cur.limit());
/* 897 */         curPos += cur.position();
/*     */       } 
/*     */     }
/*     */     private final byte[] bytes;
/*     */     private Object readResolve() {
/* 902 */       return (new BsonDocumentCodec()).decode(new BsonBinaryReader(ByteBuffer.wrap(this.bytes)
/* 903 */             .order(ByteOrder.LITTLE_ENDIAN)), 
/* 904 */           DecoderContext.builder().build());
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\BsonDocument.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */