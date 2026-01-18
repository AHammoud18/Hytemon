/*     */ package org.bson;
/*     */ 
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.bson.codecs.Encoder;
/*     */ import org.bson.codecs.EncoderContext;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class BsonDocumentWrapper<T>
/*     */   extends BsonDocument
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private final transient T wrappedDocument;
/*     */   private final transient Encoder<T> encoder;
/*     */   private BsonDocument unwrapped;
/*     */   
/*     */   public static BsonDocument asBsonDocument(Object document, CodecRegistry codecRegistry) {
/*  57 */     if (document == null) {
/*  58 */       return null;
/*     */     }
/*  60 */     if (document instanceof BsonDocument) {
/*  61 */       return (BsonDocument)document;
/*     */     }
/*  63 */     return new BsonDocumentWrapper(document, (Encoder)codecRegistry.get(document.getClass()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BsonDocumentWrapper(T wrappedDocument, Encoder<T> encoder) {
/*  74 */     if (wrappedDocument == null) {
/*  75 */       throw new IllegalArgumentException("Document can not be null");
/*     */     }
/*  77 */     this.wrappedDocument = wrappedDocument;
/*  78 */     this.encoder = encoder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T getWrappedDocument() {
/*  87 */     return this.wrappedDocument;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Encoder<T> getEncoder() {
/*  96 */     return this.encoder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUnwrapped() {
/* 105 */     return (this.unwrapped != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 110 */     return getUnwrapped().size();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 115 */     return getUnwrapped().isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsKey(Object key) {
/* 120 */     return getUnwrapped().containsKey(key);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsValue(Object value) {
/* 125 */     return getUnwrapped().containsValue(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public BsonValue get(Object key) {
/* 130 */     return getUnwrapped().get(key);
/*     */   }
/*     */ 
/*     */   
/*     */   public BsonValue put(String key, BsonValue value) {
/* 135 */     return getUnwrapped().put(key, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public BsonValue remove(Object key) {
/* 140 */     return getUnwrapped().remove(key);
/*     */   }
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends String, ? extends BsonValue> m) {
/* 145 */     super.putAll(m);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 150 */     super.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<String> keySet() {
/* 155 */     return getUnwrapped().keySet();
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<BsonValue> values() {
/* 160 */     return getUnwrapped().values();
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<Map.Entry<String, BsonValue>> entrySet() {
/* 165 */     return getUnwrapped().entrySet();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 170 */     return getUnwrapped().equals(o);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 175 */     return getUnwrapped().hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 180 */     return getUnwrapped().toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public BsonDocument clone() {
/* 185 */     return getUnwrapped().clone();
/*     */   }
/*     */   
/*     */   private BsonDocument getUnwrapped() {
/* 189 */     if (this.encoder == null) {
/* 190 */       throw new BsonInvalidOperationException("Can not unwrap a BsonDocumentWrapper with no Encoder");
/*     */     }
/* 192 */     if (this.unwrapped == null) {
/* 193 */       BsonDocument unwrapped = new BsonDocument();
/* 194 */       BsonWriter writer = new BsonDocumentWriter(unwrapped);
/* 195 */       this.encoder.encode(writer, this.wrappedDocument, EncoderContext.builder().build());
/* 196 */       this.unwrapped = unwrapped;
/*     */     } 
/* 198 */     return this.unwrapped;
/*     */   }
/*     */ 
/*     */   
/*     */   private Object writeReplace() {
/* 203 */     return getUnwrapped();
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream stream) throws InvalidObjectException {
/* 208 */     throw new InvalidObjectException("Proxy required");
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\BsonDocumentWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */