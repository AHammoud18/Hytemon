/*     */ package org.bson;
/*     */ 
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.util.AbstractList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.ListIterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import org.bson.assertions.Assertions;
/*     */ import org.bson.io.BsonInput;
/*     */ import org.bson.io.ByteBufferBsonInput;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RawBsonArray
/*     */   extends BsonArray
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 2L;
/*     */   private static final String IMMUTABLE_MSG = "RawBsonArray instances are immutable";
/*     */   private final transient RawBsonArrayList delegate;
/*     */   
/*     */   public RawBsonArray(byte[] bytes) {
/*  54 */     this((byte[])Assertions.notNull("bytes", bytes), 0, bytes.length);
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
/*     */   public RawBsonArray(byte[] bytes, int offset, int length) {
/*  67 */     this(new RawBsonArrayList(bytes, offset, length));
/*     */   }
/*     */   
/*     */   private RawBsonArray(RawBsonArrayList values) {
/*  71 */     super(values, false);
/*  72 */     this.delegate = values;
/*     */   }
/*     */   
/*     */   ByteBuf getByteBuffer() {
/*  76 */     return this.delegate.getByteBuffer();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean add(BsonValue bsonValue) {
/*  81 */     throw new UnsupportedOperationException("RawBsonArray instances are immutable");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean remove(Object o) {
/*  86 */     throw new UnsupportedOperationException("RawBsonArray instances are immutable");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addAll(Collection<? extends BsonValue> c) {
/*  91 */     throw new UnsupportedOperationException("RawBsonArray instances are immutable");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addAll(int index, Collection<? extends BsonValue> c) {
/*  96 */     throw new UnsupportedOperationException("RawBsonArray instances are immutable");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean removeAll(Collection<?> c) {
/* 101 */     throw new UnsupportedOperationException("RawBsonArray instances are immutable");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean retainAll(Collection<?> c) {
/* 106 */     throw new UnsupportedOperationException("RawBsonArray instances are immutable");
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 111 */     throw new UnsupportedOperationException("RawBsonArray instances are immutable");
/*     */   }
/*     */ 
/*     */   
/*     */   public BsonValue set(int index, BsonValue element) {
/* 116 */     throw new UnsupportedOperationException("RawBsonArray instances are immutable");
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(int index, BsonValue element) {
/* 121 */     throw new UnsupportedOperationException("RawBsonArray instances are immutable");
/*     */   }
/*     */ 
/*     */   
/*     */   public BsonValue remove(int index) {
/* 126 */     throw new UnsupportedOperationException("RawBsonArray instances are immutable");
/*     */   }
/*     */ 
/*     */   
/*     */   public BsonArray clone() {
/* 131 */     return new RawBsonArray((byte[])this.delegate.bytes.clone(), this.delegate.offset, this.delegate.length);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 136 */     return super.equals(o);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 141 */     return super.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   private Object writeReplace() {
/* 146 */     return new SerializationProxy(this.delegate.bytes, this.delegate.offset, this.delegate.length);
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream stream) throws InvalidObjectException {
/* 151 */     throw new InvalidObjectException("Proxy required");
/*     */   }
/*     */   
/*     */   private static class SerializationProxy
/*     */     implements Serializable {
/*     */     private static final long serialVersionUID = 1L;
/*     */     private final byte[] bytes;
/*     */     
/*     */     SerializationProxy(byte[] bytes, int offset, int length) {
/* 160 */       if (bytes.length == length) {
/* 161 */         this.bytes = bytes;
/*     */       } else {
/* 163 */         this.bytes = new byte[length];
/* 164 */         System.arraycopy(bytes, offset, this.bytes, 0, length);
/*     */       } 
/*     */     }
/*     */     
/*     */     private Object readResolve() {
/* 169 */       return new RawBsonArray(this.bytes);
/*     */     }
/*     */   }
/*     */   
/*     */   static class RawBsonArrayList extends AbstractList<BsonValue> {
/*     */     private static final int MIN_BSON_ARRAY_SIZE = 5;
/*     */     private Integer cachedSize;
/*     */     private final byte[] bytes;
/*     */     private final int offset;
/*     */     private final int length;
/*     */     
/*     */     RawBsonArrayList(byte[] bytes, int offset, int length) {
/* 181 */       Assertions.notNull("bytes", bytes);
/* 182 */       Assertions.isTrueArgument("offset >= 0", (offset >= 0));
/* 183 */       Assertions.isTrueArgument("offset < bytes.length", (offset < bytes.length));
/* 184 */       Assertions.isTrueArgument("length <= bytes.length - offset", (length <= bytes.length - offset));
/* 185 */       Assertions.isTrueArgument("length >= 5", (length >= 5));
/* 186 */       this.bytes = bytes;
/* 187 */       this.offset = offset;
/* 188 */       this.length = length;
/*     */     }
/*     */ 
/*     */     
/*     */     public BsonValue get(int index) {
/* 193 */       if (index < 0) {
/* 194 */         throw new IndexOutOfBoundsException();
/*     */       }
/* 196 */       int curIndex = 0;
/* 197 */       BsonBinaryReader bsonReader = createReader();
/*     */       try {
/* 199 */         bsonReader.readStartDocument();
/* 200 */         while (bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
/* 201 */           bsonReader.skipName();
/* 202 */           if (curIndex == index) {
/* 203 */             return RawBsonValueHelper.decode(this.bytes, bsonReader);
/*     */           }
/* 205 */           bsonReader.skipValue();
/* 206 */           curIndex++;
/*     */         } 
/* 208 */         bsonReader.readEndDocument();
/*     */       } finally {
/* 210 */         bsonReader.close();
/*     */       } 
/* 212 */       throw new IndexOutOfBoundsException();
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 217 */       if (this.cachedSize != null) {
/* 218 */         return this.cachedSize.intValue();
/*     */       }
/* 220 */       int size = 0;
/* 221 */       BsonBinaryReader bsonReader = createReader();
/*     */       try {
/* 223 */         bsonReader.readStartDocument();
/* 224 */         while (bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
/* 225 */           size++;
/* 226 */           bsonReader.readName();
/* 227 */           bsonReader.skipValue();
/*     */         } 
/* 229 */         bsonReader.readEndDocument();
/*     */       } finally {
/* 231 */         bsonReader.close();
/*     */       } 
/* 233 */       this.cachedSize = Integer.valueOf(size);
/* 234 */       return this.cachedSize.intValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public Iterator<BsonValue> iterator() {
/* 239 */       return new Itr();
/*     */     }
/*     */ 
/*     */     
/*     */     public ListIterator<BsonValue> listIterator() {
/* 244 */       return new ListItr(0);
/*     */     }
/*     */ 
/*     */     
/*     */     public ListIterator<BsonValue> listIterator(int index) {
/* 249 */       return new ListItr(index);
/*     */     }
/*     */     
/*     */     private class Itr implements Iterator<BsonValue> {
/* 253 */       private int cursor = 0;
/*     */       private BsonBinaryReader bsonReader;
/* 255 */       private int currentPosition = 0;
/*     */       
/*     */       Itr() {
/* 258 */         this(0);
/*     */       }
/*     */       
/*     */       Itr(int cursorPosition) {
/* 262 */         setIterator(cursorPosition);
/*     */       }
/*     */       
/*     */       public boolean hasNext() {
/* 266 */         boolean hasNext = (this.cursor != RawBsonArray.RawBsonArrayList.this.size());
/* 267 */         if (!hasNext) {
/* 268 */           this.bsonReader.close();
/*     */         }
/* 270 */         return hasNext;
/*     */       }
/*     */       
/*     */       public BsonValue next() {
/* 274 */         while (this.cursor > this.currentPosition && this.bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
/* 275 */           this.bsonReader.skipName();
/* 276 */           this.bsonReader.skipValue();
/* 277 */           this.currentPosition++;
/*     */         } 
/*     */         
/* 280 */         if (this.bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
/* 281 */           this.bsonReader.skipName();
/*     */           
/* 283 */           this.currentPosition = ++this.cursor;
/* 284 */           return RawBsonValueHelper.decode(RawBsonArray.RawBsonArrayList.this.bytes, this.bsonReader);
/*     */         } 
/* 286 */         this.bsonReader.close();
/* 287 */         throw new NoSuchElementException();
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       public void remove() {
/* 293 */         throw new UnsupportedOperationException("RawBsonArray instances are immutable");
/*     */       }
/*     */       
/*     */       public int getCursor() {
/* 297 */         return this.cursor;
/*     */       }
/*     */       
/*     */       public void setCursor(int cursor) {
/* 301 */         this.cursor = cursor;
/*     */       }
/*     */       
/*     */       void setIterator(int cursorPosition) {
/* 305 */         this.cursor = cursorPosition;
/* 306 */         this.currentPosition = 0;
/* 307 */         if (this.bsonReader != null) {
/* 308 */           this.bsonReader.close();
/*     */         }
/* 310 */         this.bsonReader = RawBsonArray.RawBsonArrayList.this.createReader();
/* 311 */         this.bsonReader.readStartDocument();
/*     */       }
/*     */     }
/*     */     
/*     */     private class ListItr extends Itr implements ListIterator<BsonValue> {
/*     */       ListItr(int index) {
/* 317 */         super(index);
/*     */       }
/*     */       
/*     */       public boolean hasPrevious() {
/* 321 */         return (getCursor() != 0);
/*     */       }
/*     */       
/*     */       public BsonValue previous() {
/*     */         try {
/* 326 */           BsonValue previous = RawBsonArray.RawBsonArrayList.this.get(previousIndex());
/* 327 */           setIterator(previousIndex());
/* 328 */           return previous;
/* 329 */         } catch (IndexOutOfBoundsException e) {
/* 330 */           throw new NoSuchElementException();
/*     */         } 
/*     */       }
/*     */       
/*     */       public int nextIndex() {
/* 335 */         return getCursor();
/*     */       }
/*     */       
/*     */       public int previousIndex() {
/* 339 */         return getCursor() - 1;
/*     */       }
/*     */ 
/*     */       
/*     */       public void set(BsonValue bsonValue) {
/* 344 */         throw new UnsupportedOperationException("RawBsonArray instances are immutable");
/*     */       }
/*     */ 
/*     */       
/*     */       public void add(BsonValue bsonValue) {
/* 349 */         throw new UnsupportedOperationException("RawBsonArray instances are immutable");
/*     */       }
/*     */     }
/*     */     
/*     */     private BsonBinaryReader createReader() {
/* 354 */       return new BsonBinaryReader((BsonInput)new ByteBufferBsonInput(getByteBuffer()));
/*     */     }
/*     */     
/*     */     ByteBuf getByteBuffer() {
/* 358 */       ByteBuffer buffer = ByteBuffer.wrap(this.bytes, this.offset, this.length);
/* 359 */       buffer.order(ByteOrder.LITTLE_ENDIAN);
/* 360 */       return new ByteBufNIO(buffer);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\RawBsonArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */