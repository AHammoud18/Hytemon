/*     */ package org.bson;
/*     */ 
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.io.StringWriter;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.bson.assertions.Assertions;
/*     */ import org.bson.codecs.BsonDocumentCodec;
/*     */ import org.bson.codecs.Codec;
/*     */ import org.bson.codecs.Decoder;
/*     */ import org.bson.codecs.DecoderContext;
/*     */ import org.bson.codecs.EncoderContext;
/*     */ import org.bson.codecs.RawBsonDocumentCodec;
/*     */ import org.bson.io.BasicOutputBuffer;
/*     */ import org.bson.io.BsonInput;
/*     */ import org.bson.io.BsonOutput;
/*     */ import org.bson.io.ByteBufferBsonInput;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class RawBsonDocument
/*     */   extends BsonDocument
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private static final int MIN_BSON_DOCUMENT_SIZE = 5;
/*     */   private final byte[] bytes;
/*     */   private final int offset;
/*     */   private final int length;
/*     */   
/*     */   public static RawBsonDocument parse(String json) {
/*  69 */     Assertions.notNull("json", json);
/*  70 */     return (new RawBsonDocumentCodec()).decode((BsonReader)new JsonReader(json), DecoderContext.builder().build());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RawBsonDocument(byte[] bytes) {
/*  81 */     this((byte[])Assertions.notNull("bytes", bytes), 0, bytes.length);
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
/*     */   public RawBsonDocument(byte[] bytes, int offset, int length) {
/*  95 */     Assertions.notNull("bytes", bytes);
/*  96 */     Assertions.isTrueArgument("offset >= 0", (offset >= 0));
/*  97 */     Assertions.isTrueArgument("offset < bytes.length", (offset < bytes.length));
/*  98 */     Assertions.isTrueArgument("length <= bytes.length - offset", (length <= bytes.length - offset));
/*  99 */     Assertions.isTrueArgument("length >= 5", (length >= 5));
/* 100 */     this.bytes = bytes;
/* 101 */     this.offset = offset;
/* 102 */     this.length = length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> RawBsonDocument(T document, Codec<T> codec) {
/* 113 */     Assertions.notNull("document", document);
/* 114 */     Assertions.notNull("codec", codec);
/* 115 */     BasicOutputBuffer buffer = new BasicOutputBuffer();
/* 116 */     BsonBinaryWriter writer = new BsonBinaryWriter((BsonOutput)buffer);
/*     */     try {
/* 118 */       codec.encode(writer, document, EncoderContext.builder().build());
/* 119 */       this.bytes = buffer.getInternalBuffer();
/* 120 */       this.offset = 0;
/* 121 */       this.length = buffer.getPosition();
/*     */     } finally {
/* 123 */       writer.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuf getByteBuffer() {
/* 134 */     ByteBuffer buffer = ByteBuffer.wrap(this.bytes, this.offset, this.length);
/* 135 */     buffer.order(ByteOrder.LITTLE_ENDIAN);
/* 136 */     return new ByteBufNIO(buffer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T decode(Codec<T> codec) {
/* 147 */     return decode((Decoder<T>)codec);
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
/*     */   public <T> T decode(Decoder<T> decoder) {
/* 159 */     BsonBinaryReader reader = createReader();
/*     */     try {
/* 161 */       return (T)decoder.decode(reader, DecoderContext.builder().build());
/*     */     } finally {
/* 163 */       reader.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 169 */     throw new UnsupportedOperationException("RawBsonDocument instances are immutable");
/*     */   }
/*     */ 
/*     */   
/*     */   public BsonValue put(String key, BsonValue value) {
/* 174 */     throw new UnsupportedOperationException("RawBsonDocument instances are immutable");
/*     */   }
/*     */ 
/*     */   
/*     */   public BsonDocument append(String key, BsonValue value) {
/* 179 */     throw new UnsupportedOperationException("RawBsonDocument instances are immutable");
/*     */   }
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends String, ? extends BsonValue> m) {
/* 184 */     throw new UnsupportedOperationException("RawBsonDocument instances are immutable");
/*     */   }
/*     */ 
/*     */   
/*     */   public BsonValue remove(Object key) {
/* 189 */     throw new UnsupportedOperationException("RawBsonDocument instances are immutable");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 194 */     BsonBinaryReader bsonReader = createReader();
/*     */     try {
/* 196 */       bsonReader.readStartDocument();
/* 197 */       if (bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
/* 198 */         return false;
/*     */       }
/* 200 */       bsonReader.readEndDocument();
/*     */     } finally {
/* 202 */       bsonReader.close();
/*     */     } 
/*     */     
/* 205 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 210 */     int size = 0;
/* 211 */     BsonBinaryReader bsonReader = createReader();
/*     */     try {
/* 213 */       bsonReader.readStartDocument();
/* 214 */       while (bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
/* 215 */         size++;
/* 216 */         bsonReader.readName();
/* 217 */         bsonReader.skipValue();
/*     */       } 
/* 219 */       bsonReader.readEndDocument();
/*     */     } finally {
/* 221 */       bsonReader.close();
/*     */     } 
/*     */     
/* 224 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<Map.Entry<String, BsonValue>> entrySet() {
/* 229 */     return toBaseBsonDocument().entrySet();
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<BsonValue> values() {
/* 234 */     return toBaseBsonDocument().values();
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<String> keySet() {
/* 239 */     return toBaseBsonDocument().keySet();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getFirstKey() {
/* 244 */     BsonBinaryReader bsonReader = createReader();
/*     */     try {
/* 246 */       bsonReader.readStartDocument();
/*     */ 
/*     */     
/*     */     }
/*     */     finally {
/*     */ 
/*     */       
/* 253 */       bsonReader.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsKey(Object key) {
/* 259 */     if (key == null) {
/* 260 */       throw new IllegalArgumentException("key can not be null");
/*     */     }
/*     */     
/* 263 */     BsonBinaryReader bsonReader = createReader();
/*     */     try {
/* 265 */       bsonReader.readStartDocument();
/* 266 */       while (bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
/* 267 */         if (bsonReader.readName().equals(key)) {
/* 268 */           return true;
/*     */         }
/* 270 */         bsonReader.skipValue();
/*     */       } 
/* 272 */       bsonReader.readEndDocument();
/*     */     } finally {
/* 274 */       bsonReader.close();
/*     */     } 
/*     */     
/* 277 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsValue(Object value) {
/* 282 */     BsonBinaryReader bsonReader = createReader();
/*     */     try {
/* 284 */       bsonReader.readStartDocument();
/* 285 */       while (bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
/* 286 */         bsonReader.skipName();
/* 287 */         if (RawBsonValueHelper.decode(this.bytes, bsonReader).equals(value)) {
/* 288 */           return true;
/*     */         }
/*     */       } 
/* 291 */       bsonReader.readEndDocument();
/*     */     } finally {
/* 293 */       bsonReader.close();
/*     */     } 
/*     */     
/* 296 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public BsonValue get(Object key) {
/* 301 */     Assertions.notNull("key", key);
/*     */     
/* 303 */     BsonBinaryReader bsonReader = createReader();
/*     */     try {
/* 305 */       bsonReader.readStartDocument();
/* 306 */       while (bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
/* 307 */         if (bsonReader.readName().equals(key)) {
/* 308 */           return RawBsonValueHelper.decode(this.bytes, bsonReader);
/*     */         }
/* 310 */         bsonReader.skipValue();
/*     */       } 
/* 312 */       bsonReader.readEndDocument();
/*     */     } finally {
/* 314 */       bsonReader.close();
/*     */     } 
/*     */     
/* 317 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toJson() {
/* 323 */     return toJson(JsonWriterSettings.builder().outputMode(JsonMode.RELAXED).build());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toJson(JsonWriterSettings settings) {
/* 328 */     StringWriter writer = new StringWriter();
/* 329 */     (new RawBsonDocumentCodec()).encode((BsonWriter)new JsonWriter(writer, settings), this, EncoderContext.builder().build());
/* 330 */     return writer.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 335 */     return toBaseBsonDocument().equals(o);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 340 */     return toBaseBsonDocument().hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public BsonDocument clone() {
/* 345 */     return new RawBsonDocument((byte[])this.bytes.clone(), this.offset, this.length);
/*     */   }
/*     */   
/*     */   private BsonBinaryReader createReader() {
/* 349 */     return new BsonBinaryReader((BsonInput)new ByteBufferBsonInput(getByteBuffer()));
/*     */   }
/*     */ 
/*     */   
/*     */   private BsonDocument toBaseBsonDocument() {
/* 354 */     BsonBinaryReader bsonReader = createReader();
/*     */     try {
/* 356 */       return (new BsonDocumentCodec()).decode(bsonReader, DecoderContext.builder().build());
/*     */     } finally {
/* 358 */       bsonReader.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private Object writeReplace() {
/* 364 */     return new SerializationProxy(this.bytes, this.offset, this.length);
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream stream) throws InvalidObjectException {
/* 369 */     throw new InvalidObjectException("Proxy required");
/*     */   }
/*     */   
/*     */   private static class SerializationProxy
/*     */     implements Serializable {
/*     */     private static final long serialVersionUID = 1L;
/*     */     private final byte[] bytes;
/*     */     
/*     */     SerializationProxy(byte[] bytes, int offset, int length) {
/* 378 */       if (bytes.length == length) {
/* 379 */         this.bytes = bytes;
/*     */       } else {
/* 381 */         this.bytes = new byte[length];
/* 382 */         System.arraycopy(bytes, offset, this.bytes, 0, length);
/*     */       } 
/*     */     }
/*     */     
/*     */     private Object readResolve() {
/* 387 */       return new RawBsonDocument(this.bytes);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\RawBsonDocument.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */