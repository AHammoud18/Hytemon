/*     */ package org.bson;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.channels.Channels;
/*     */ import java.nio.channels.WritableByteChannel;
/*     */ import java.util.AbstractMap;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.regex.Pattern;
/*     */ import org.bson.io.BsonInput;
/*     */ import org.bson.io.ByteBufferBsonInput;
/*     */ import org.bson.types.BSONTimestamp;
/*     */ import org.bson.types.Binary;
/*     */ import org.bson.types.Code;
/*     */ import org.bson.types.CodeWScope;
/*     */ import org.bson.types.MaxKey;
/*     */ import org.bson.types.MinKey;
/*     */ import org.bson.types.Symbol;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LazyBSONObject
/*     */   implements BSONObject
/*     */ {
/*     */   private final byte[] bytes;
/*     */   private final int offset;
/*     */   private final LazyBSONCallback callback;
/*     */   
/*     */   public LazyBSONObject(byte[] bytes, LazyBSONCallback callback) {
/*  68 */     this(bytes, 0, callback);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LazyBSONObject(byte[] bytes, int offset, LazyBSONCallback callback) {
/*  79 */     this.bytes = bytes;
/*  80 */     this.callback = callback;
/*  81 */     this.offset = offset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getOffset() {
/*  90 */     return this.offset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected byte[] getBytes() {
/*  99 */     return this.bytes;
/*     */   }
/*     */   
/*     */   public Object get(String key) {
/*     */     Object value;
/* 104 */     BsonBinaryReader reader = getBsonReader();
/*     */     
/*     */     try {
/* 107 */       reader.readStartDocument();
/* 108 */       value = null;
/* 109 */       while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
/* 110 */         if (key.equals(reader.readName())) {
/* 111 */           value = readValue(reader);
/*     */           break;
/*     */         } 
/* 114 */         reader.skipValue();
/*     */       } 
/*     */     } finally {
/*     */       
/* 118 */       reader.close();
/*     */     } 
/* 120 */     return value;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsField(String s) {
/* 125 */     BsonBinaryReader reader = getBsonReader();
/*     */     try {
/* 127 */       reader.readStartDocument();
/* 128 */       while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
/* 129 */         if (reader.readName().equals(s)) {
/* 130 */           return true;
/*     */         }
/* 132 */         reader.skipValue();
/*     */       } 
/*     */     } finally {
/*     */       
/* 136 */       reader.close();
/*     */     } 
/* 138 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<String> keySet() {
/* 143 */     Set<String> keys = new LinkedHashSet<>();
/* 144 */     BsonBinaryReader reader = getBsonReader();
/*     */     try {
/* 146 */       reader.readStartDocument();
/* 147 */       while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
/* 148 */         keys.add(reader.readName());
/* 149 */         reader.skipValue();
/*     */       } 
/* 151 */       reader.readEndDocument();
/*     */     } finally {
/* 153 */       reader.close();
/*     */     } 
/* 155 */     return Collections.unmodifiableSet(keys); } Object readValue(BsonBinaryReader reader) { byte binarySubType; BsonBinary binary;
/*     */     BsonRegularExpression regularExpression;
/*     */     BsonDbPointer dbPointer;
/*     */     BsonTimestamp timestamp;
/* 159 */     switch (reader.getCurrentBsonType()) {
/*     */       case DOCUMENT:
/* 161 */         return readDocument(reader);
/*     */       case ARRAY:
/* 163 */         return readArray(reader);
/*     */       case DOUBLE:
/* 165 */         return Double.valueOf(reader.readDouble());
/*     */       case STRING:
/* 167 */         return reader.readString();
/*     */       case BINARY:
/* 169 */         binarySubType = reader.peekBinarySubType();
/* 170 */         binary = reader.readBinaryData();
/* 171 */         if (binarySubType == BsonBinarySubType.BINARY.getValue() || binarySubType == BsonBinarySubType.OLD_BINARY.getValue()) {
/* 172 */           return binary.getData();
/*     */         }
/* 174 */         return new Binary(binary.getType(), binary.getData());
/*     */       
/*     */       case NULL:
/* 177 */         reader.readNull();
/* 178 */         return null;
/*     */       case UNDEFINED:
/* 180 */         reader.readUndefined();
/* 181 */         return null;
/*     */       case OBJECT_ID:
/* 183 */         return reader.readObjectId();
/*     */       case BOOLEAN:
/* 185 */         return Boolean.valueOf(reader.readBoolean());
/*     */       case DATE_TIME:
/* 187 */         return new Date(reader.readDateTime());
/*     */       case REGULAR_EXPRESSION:
/* 189 */         regularExpression = reader.readRegularExpression();
/* 190 */         return Pattern.compile(regularExpression
/* 191 */             .getPattern(), 
/* 192 */             BSON.regexFlags(regularExpression.getOptions()));
/*     */       
/*     */       case DB_POINTER:
/* 195 */         dbPointer = reader.readDBPointer();
/* 196 */         return this.callback.createDBRef(dbPointer.getNamespace(), dbPointer.getId());
/*     */       case JAVASCRIPT:
/* 198 */         return new Code(reader.readJavaScript());
/*     */       case SYMBOL:
/* 200 */         return new Symbol(reader.readSymbol());
/*     */       case JAVASCRIPT_WITH_SCOPE:
/* 202 */         return new CodeWScope(reader.readJavaScriptWithScope(), (BSONObject)readJavaScriptWithScopeDocument(reader));
/*     */       case INT32:
/* 204 */         return Integer.valueOf(reader.readInt32());
/*     */       case TIMESTAMP:
/* 206 */         timestamp = reader.readTimestamp();
/* 207 */         return new BSONTimestamp(timestamp.getTime(), timestamp.getInc());
/*     */       case INT64:
/* 209 */         return Long.valueOf(reader.readInt64());
/*     */       case DECIMAL128:
/* 211 */         return reader.readDecimal128();
/*     */       case MIN_KEY:
/* 213 */         reader.readMinKey();
/* 214 */         return new MinKey();
/*     */       case MAX_KEY:
/* 216 */         reader.readMaxKey();
/* 217 */         return new MaxKey();
/*     */     } 
/* 219 */     throw new IllegalArgumentException("unhandled BSON type: " + reader.getCurrentBsonType()); }
/*     */ 
/*     */ 
/*     */   
/*     */   private Object readArray(BsonBinaryReader reader) {
/* 224 */     int position = reader.getBsonInput().getPosition();
/* 225 */     reader.skipValue();
/* 226 */     return this.callback.createArray(this.bytes, this.offset + position);
/*     */   }
/*     */   
/*     */   private Object readDocument(BsonBinaryReader reader) {
/* 230 */     int position = reader.getBsonInput().getPosition();
/* 231 */     reader.skipValue();
/* 232 */     return this.callback.createObject(this.bytes, this.offset + position);
/*     */   }
/*     */   
/*     */   private Object readJavaScriptWithScopeDocument(BsonBinaryReader reader) {
/* 236 */     int position = reader.getBsonInput().getPosition();
/* 237 */     reader.readStartDocument();
/* 238 */     while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
/* 239 */       reader.skipName();
/* 240 */       reader.skipValue();
/*     */     } 
/* 242 */     reader.readEndDocument();
/* 243 */     return this.callback.createObject(this.bytes, this.offset + position);
/*     */   }
/*     */   
/*     */   BsonBinaryReader getBsonReader() {
/* 247 */     ByteBuffer buffer = getBufferForInternalBytes();
/* 248 */     return new BsonBinaryReader((BsonInput)new ByteBufferBsonInput(new ByteBufNIO(buffer)));
/*     */   }
/*     */   
/*     */   private ByteBuffer getBufferForInternalBytes() {
/* 252 */     ByteBuffer buffer = ByteBuffer.wrap(this.bytes, this.offset, this.bytes.length - this.offset).slice();
/* 253 */     buffer.order(ByteOrder.LITTLE_ENDIAN);
/* 254 */     buffer.limit(buffer.getInt());
/* 255 */     buffer.rewind();
/* 256 */     return buffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 265 */     return (keySet().size() == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBSONSize() {
/* 274 */     return getBufferForInternalBytes().getInt();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int pipe(OutputStream os) throws IOException {
/* 285 */     WritableByteChannel channel = Channels.newChannel(os);
/* 286 */     return channel.write(getBufferForInternalBytes());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<Map.Entry<String, Object>> entrySet() {
/* 295 */     final List<Map.Entry<String, Object>> entries = new ArrayList<>();
/* 296 */     BsonBinaryReader reader = getBsonReader();
/*     */     try {
/* 298 */       reader.readStartDocument();
/* 299 */       while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
/* 300 */         entries.add(new AbstractMap.SimpleImmutableEntry<>(reader.readName(), readValue(reader)));
/*     */       }
/* 302 */       reader.readEndDocument();
/*     */     } finally {
/* 304 */       reader.close();
/*     */     } 
/* 306 */     return new Set<Map.Entry<String, Object>>()
/*     */       {
/*     */         public int size() {
/* 309 */           return entries.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean isEmpty() {
/* 314 */           return entries.isEmpty();
/*     */         }
/*     */ 
/*     */         
/*     */         public Iterator<Map.Entry<String, Object>> iterator() {
/* 319 */           return entries.iterator();
/*     */         }
/*     */ 
/*     */         
/*     */         public Object[] toArray() {
/* 324 */           return entries.toArray();
/*     */         }
/*     */ 
/*     */         
/*     */         public <T> T[] toArray(T[] a) {
/* 329 */           return (T[])entries.toArray((Object[])a);
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean contains(Object o) {
/* 334 */           return entries.contains(o);
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean containsAll(Collection<?> c) {
/* 339 */           return entries.containsAll(c);
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean add(Map.Entry<String, Object> stringObjectEntry) {
/* 344 */           throw new UnsupportedOperationException();
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean remove(Object o) {
/* 349 */           throw new UnsupportedOperationException();
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean addAll(Collection<? extends Map.Entry<String, Object>> c) {
/* 354 */           throw new UnsupportedOperationException();
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean retainAll(Collection<?> c) {
/* 359 */           throw new UnsupportedOperationException();
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean removeAll(Collection<?> c) {
/* 364 */           throw new UnsupportedOperationException();
/*     */         }
/*     */ 
/*     */         
/*     */         public void clear() {
/* 369 */           throw new UnsupportedOperationException();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 376 */     int result = 1;
/* 377 */     int size = getBSONSize();
/* 378 */     for (int i = this.offset; i < this.offset + size; i++) {
/* 379 */       result = 31 * result + this.bytes[i];
/*     */     }
/* 381 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 386 */     if (this == o) {
/* 387 */       return true;
/*     */     }
/* 389 */     if (o == null || getClass() != o.getClass()) {
/* 390 */       return false;
/*     */     }
/* 392 */     LazyBSONObject other = (LazyBSONObject)o;
/*     */     
/* 394 */     if (this.bytes == other.bytes && this.offset == other.offset) {
/* 395 */       return true;
/*     */     }
/* 397 */     if (this.bytes == null || other.bytes == null) {
/* 398 */       return false;
/*     */     }
/*     */     
/* 401 */     if (this.bytes.length == 0 || other.bytes.length == 0) {
/* 402 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 406 */     int length = this.bytes[this.offset];
/* 407 */     if (other.bytes[other.offset] != length) {
/* 408 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 412 */     for (int i = 0; i < length; i++) {
/* 413 */       if (this.bytes[this.offset + i] != other.bytes[other.offset + i]) {
/* 414 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 418 */     return true;
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
/*     */   public Object put(String key, Object v) {
/* 434 */     throw new UnsupportedOperationException("Object is read only");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAll(BSONObject o) {
/* 445 */     throw new UnsupportedOperationException("Object is read only");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAll(Map m) {
/* 456 */     throw new UnsupportedOperationException("Object is read only");
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
/*     */   public Object removeField(String key) {
/* 468 */     throw new UnsupportedOperationException("Object is read only");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Map toMap() {
/* 474 */     Map<String, Object> map = new LinkedHashMap<>();
/* 475 */     for (Map.Entry<String, Object> entry : entrySet()) {
/* 476 */       map.put(entry.getKey(), entry.getValue());
/*     */     }
/* 478 */     return Collections.unmodifiableMap(map);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\LazyBSONObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */