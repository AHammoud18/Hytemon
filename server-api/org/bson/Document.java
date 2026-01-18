/*     */ package org.bson;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.io.StringWriter;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.bson.assertions.Assertions;
/*     */ import org.bson.codecs.Decoder;
/*     */ import org.bson.codecs.DecoderContext;
/*     */ import org.bson.codecs.DocumentCodec;
/*     */ import org.bson.codecs.Encoder;
/*     */ import org.bson.codecs.EncoderContext;
/*     */ import org.bson.codecs.configuration.CodecRegistry;
/*     */ import org.bson.conversions.Bson;
/*     */ import org.bson.json.JsonMode;
/*     */ import org.bson.json.JsonReader;
/*     */ import org.bson.json.JsonWriter;
/*     */ import org.bson.json.JsonWriterSettings;
/*     */ import org.bson.types.ObjectId;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Document
/*     */   implements Map<String, Object>, Serializable, Bson
/*     */ {
/*     */   private static final long serialVersionUID = 6297731997167536582L;
/*     */   private final LinkedHashMap<String, Object> documentAsMap;
/*     */   
/*     */   public Document() {
/*  62 */     this.documentAsMap = new LinkedHashMap<>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Document(String key, Object value) {
/*  72 */     this.documentAsMap = new LinkedHashMap<>();
/*  73 */     this.documentAsMap.put(key, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Document(Map<String, Object> map) {
/*  82 */     this.documentAsMap = new LinkedHashMap<>(map);
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
/*     */   public static Document parse(String json) {
/*  95 */     return parse(json, (Decoder<Document>)new DocumentCodec());
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
/*     */   public static Document parse(String json, Decoder<Document> decoder) {
/* 108 */     Assertions.notNull("codec", decoder);
/* 109 */     JsonReader bsonReader = new JsonReader(json);
/* 110 */     return (Document)decoder.decode((BsonReader)bsonReader, DecoderContext.builder().build());
/*     */   }
/*     */ 
/*     */   
/*     */   public <C> BsonDocument toBsonDocument(Class<C> documentClass, CodecRegistry codecRegistry) {
/* 115 */     return new BsonDocumentWrapper<>(this, (Encoder<Document>)codecRegistry.get(Document.class));
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
/*     */   public Document append(String key, Object value) {
/* 128 */     this.documentAsMap.put(key, value);
/* 129 */     return this;
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
/*     */   public <T> T get(Object key, Class<T> clazz) {
/* 144 */     Assertions.notNull("clazz", clazz);
/* 145 */     return clazz.cast(this.documentAsMap.get(key));
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
/*     */   public <T> T get(Object key, T defaultValue) {
/* 161 */     Assertions.notNull("defaultValue", defaultValue);
/* 162 */     Object value = this.documentAsMap.get(key);
/* 163 */     return (value == null) ? defaultValue : (T)value;
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
/*     */   public <T> T getEmbedded(List<?> keys, Class<T> clazz) {
/* 184 */     Assertions.notNull("keys", keys);
/* 185 */     Assertions.isTrue("keys", !keys.isEmpty());
/* 186 */     Assertions.notNull("clazz", clazz);
/* 187 */     return getEmbeddedValue(keys, clazz, null);
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
/*     */   public <T> T getEmbedded(List<?> keys, T defaultValue) {
/* 208 */     Assertions.notNull("keys", keys);
/* 209 */     Assertions.isTrue("keys", !keys.isEmpty());
/* 210 */     Assertions.notNull("defaultValue", defaultValue);
/* 211 */     return getEmbeddedValue(keys, null, defaultValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private <T> T getEmbeddedValue(List<?> keys, Class<T> clazz, T defaultValue) {
/* 219 */     Object value = this;
/* 220 */     Iterator<?> keyIterator = keys.iterator();
/* 221 */     while (keyIterator.hasNext()) {
/* 222 */       Object key = keyIterator.next();
/* 223 */       value = ((Document)value).get(key);
/* 224 */       if (!(value instanceof Document)) {
/* 225 */         if (value == null)
/* 226 */           return defaultValue; 
/* 227 */         if (keyIterator.hasNext()) {
/* 228 */           throw new ClassCastException(String.format("At key %s, the value is not a Document (%s)", new Object[] { key, value
/* 229 */                   .getClass().getName() }));
/*     */         }
/*     */       } 
/*     */     } 
/* 233 */     return (clazz != null) ? clazz.cast(value) : (T)value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getInteger(Object key) {
/* 244 */     return (Integer)get(key);
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
/*     */   public int getInteger(Object key, int defaultValue) {
/* 256 */     return ((Integer)get(key, Integer.valueOf(defaultValue))).intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Long getLong(Object key) {
/* 267 */     return (Long)get(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Double getDouble(Object key) {
/* 278 */     return (Double)get(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getString(Object key) {
/* 289 */     return (String)get(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean getBoolean(Object key) {
/* 300 */     return (Boolean)get(key);
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
/*     */   public boolean getBoolean(Object key, boolean defaultValue) {
/* 312 */     return ((Boolean)get(key, Boolean.valueOf(defaultValue))).booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectId getObjectId(Object key) {
/* 323 */     return (ObjectId)get(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Date getDate(Object key) {
/* 334 */     return (Date)get(key);
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
/*     */   public <T> List<T> getList(Object key, Class<T> clazz) {
/* 349 */     Assertions.notNull("clazz", clazz);
/* 350 */     return constructValuesList(key, clazz, null);
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
/*     */   public <T> List<T> getList(Object key, Class<T> clazz, List<T> defaultValue) {
/* 366 */     Assertions.notNull("defaultValue", defaultValue);
/* 367 */     Assertions.notNull("clazz", clazz);
/* 368 */     return constructValuesList(key, clazz, defaultValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private <T> List<T> constructValuesList(Object key, Class<T> clazz, List<T> defaultValue) {
/* 376 */     List<?> value = get(key, List.class);
/* 377 */     if (value == null) {
/* 378 */       return defaultValue;
/*     */     }
/*     */     
/* 381 */     for (Object item : value) {
/* 382 */       if (!clazz.isAssignableFrom(item.getClass())) {
/* 383 */         throw new ClassCastException(String.format("List element cannot be cast to %s", new Object[] { clazz.getName() }));
/*     */       }
/*     */     } 
/* 386 */     return (List)value;
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
/*     */   public String toJson() {
/* 400 */     return toJson(JsonWriterSettings.builder().outputMode(JsonMode.RELAXED).build());
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
/*     */   public String toJson(JsonWriterSettings writerSettings) {
/* 413 */     return toJson(writerSettings, (Encoder<Document>)new DocumentCodec());
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
/*     */   public String toJson(Encoder<Document> encoder) {
/* 427 */     return toJson(JsonWriterSettings.builder().outputMode(JsonMode.RELAXED).build(), encoder);
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
/*     */   public String toJson(JsonWriterSettings writerSettings, Encoder<Document> encoder) {
/* 439 */     JsonWriter writer = new JsonWriter(new StringWriter(), writerSettings);
/* 440 */     encoder.encode((BsonWriter)writer, this, EncoderContext.builder().build());
/* 441 */     return writer.getWriter().toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 448 */     return this.documentAsMap.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 453 */     return this.documentAsMap.isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsValue(Object value) {
/* 458 */     return this.documentAsMap.containsValue(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsKey(Object key) {
/* 463 */     return this.documentAsMap.containsKey(key);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object get(Object key) {
/* 468 */     return this.documentAsMap.get(key);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object put(String key, Object value) {
/* 473 */     return this.documentAsMap.put(key, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object remove(Object key) {
/* 478 */     return this.documentAsMap.remove(key);
/*     */   }
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends String, ?> map) {
/* 483 */     this.documentAsMap.putAll(map);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 488 */     this.documentAsMap.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<String> keySet() {
/* 493 */     return this.documentAsMap.keySet();
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<Object> values() {
/* 498 */     return this.documentAsMap.values();
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<Map.Entry<String, Object>> entrySet() {
/* 503 */     return this.documentAsMap.entrySet();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 508 */     if (this == o) {
/* 509 */       return true;
/*     */     }
/* 511 */     if (o == null || getClass() != o.getClass()) {
/* 512 */       return false;
/*     */     }
/*     */     
/* 515 */     Document document = (Document)o;
/*     */     
/* 517 */     if (!this.documentAsMap.equals(document.documentAsMap)) {
/* 518 */       return false;
/*     */     }
/*     */     
/* 521 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 526 */     return this.documentAsMap.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 531 */     return "Document{" + this.documentAsMap + '}';
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\Document.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */