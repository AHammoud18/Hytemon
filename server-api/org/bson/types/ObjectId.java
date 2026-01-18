/*     */ package org.bson.types;
/*     */ 
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.security.SecureRandom;
/*     */ import java.util.Date;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import org.bson.assertions.Assertions;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ObjectId
/*     */   implements Comparable<ObjectId>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private static final int OBJECT_ID_LENGTH = 12;
/*     */   private static final int LOW_ORDER_THREE_BYTES = 16777215;
/*     */   private static final int RANDOM_VALUE1;
/*     */   private static final short RANDOM_VALUE2;
/*  60 */   private static final AtomicInteger NEXT_COUNTER = new AtomicInteger((new SecureRandom()).nextInt());
/*     */   
/*  62 */   private static final char[] HEX_CHARS = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
/*     */ 
/*     */   
/*     */   private final int timestamp;
/*     */ 
/*     */   
/*     */   private final int counter;
/*     */ 
/*     */   
/*     */   private final int randomValue1;
/*     */   
/*     */   private final short randomValue2;
/*     */ 
/*     */   
/*     */   public static ObjectId get() {
/*  77 */     return new ObjectId();
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
/*     */   public static ObjectId getSmallestWithDate(Date date) {
/*  92 */     return new ObjectId(dateToTimestampSeconds(date), 0, (short)0, 0, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isValid(String hexString) {
/* 103 */     if (hexString == null) {
/* 104 */       throw new IllegalArgumentException();
/*     */     }
/*     */     
/* 107 */     int len = hexString.length();
/* 108 */     if (len != 24) {
/* 109 */       return false;
/*     */     }
/*     */     
/* 112 */     for (int i = 0; i < len; i++) {
/* 113 */       char c = hexString.charAt(i);
/* 114 */       if (c < '0' || c > '9')
/*     */       {
/*     */         
/* 117 */         if (c < 'a' || c > 'f')
/*     */         {
/*     */           
/* 120 */           if (c < 'A' || c > 'F')
/*     */           {
/*     */ 
/*     */             
/* 124 */             return false; }  } 
/*     */       }
/*     */     } 
/* 127 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectId() {
/* 134 */     this(new Date());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectId(Date date) {
/* 143 */     this(dateToTimestampSeconds(date), NEXT_COUNTER.getAndIncrement() & 0xFFFFFF, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectId(Date date, int counter) {
/* 154 */     this(dateToTimestampSeconds(date), counter, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectId(int timestamp, int counter) {
/* 165 */     this(timestamp, counter, true);
/*     */   }
/*     */   
/*     */   private ObjectId(int timestamp, int counter, boolean checkCounter) {
/* 169 */     this(timestamp, RANDOM_VALUE1, RANDOM_VALUE2, counter, checkCounter);
/*     */   }
/*     */ 
/*     */   
/*     */   private ObjectId(int timestamp, int randomValue1, short randomValue2, int counter, boolean checkCounter) {
/* 174 */     if ((randomValue1 & 0xFF000000) != 0) {
/* 175 */       throw new IllegalArgumentException("The machine identifier must be between 0 and 16777215 (it must fit in three bytes).");
/*     */     }
/* 177 */     if (checkCounter && (counter & 0xFF000000) != 0) {
/* 178 */       throw new IllegalArgumentException("The counter must be between 0 and 16777215 (it must fit in three bytes).");
/*     */     }
/* 180 */     this.timestamp = timestamp;
/* 181 */     this.counter = counter & 0xFFFFFF;
/* 182 */     this.randomValue1 = randomValue1;
/* 183 */     this.randomValue2 = randomValue2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectId(String hexString) {
/* 193 */     this(parseHexString(hexString));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectId(byte[] bytes) {
/* 203 */     this(ByteBuffer.wrap((byte[])Assertions.isTrueArgument("bytes has length of 12", bytes, (((byte[])Assertions.notNull("bytes", bytes)).length == 12))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectId(ByteBuffer buffer) {
/* 214 */     Assertions.notNull("buffer", buffer);
/* 215 */     Assertions.isTrueArgument("buffer.remaining() >=12", (buffer.remaining() >= 12));
/*     */ 
/*     */ 
/*     */     
/* 219 */     this.timestamp = makeInt(buffer.get(), buffer.get(), buffer.get(), buffer.get());
/* 220 */     this.randomValue1 = makeInt((byte)0, buffer.get(), buffer.get(), buffer.get());
/* 221 */     this.randomValue2 = makeShort(buffer.get(), buffer.get());
/* 222 */     this.counter = makeInt((byte)0, buffer.get(), buffer.get(), buffer.get());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] toByteArray() {
/* 231 */     ByteBuffer buffer = ByteBuffer.allocate(12);
/* 232 */     putToByteBuffer(buffer);
/* 233 */     return buffer.array();
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
/*     */   public void putToByteBuffer(ByteBuffer buffer) {
/* 245 */     Assertions.notNull("buffer", buffer);
/* 246 */     Assertions.isTrueArgument("buffer.remaining() >=12", (buffer.remaining() >= 12));
/*     */     
/* 248 */     buffer.put(int3(this.timestamp));
/* 249 */     buffer.put(int2(this.timestamp));
/* 250 */     buffer.put(int1(this.timestamp));
/* 251 */     buffer.put(int0(this.timestamp));
/* 252 */     buffer.put(int2(this.randomValue1));
/* 253 */     buffer.put(int1(this.randomValue1));
/* 254 */     buffer.put(int0(this.randomValue1));
/* 255 */     buffer.put(short1(this.randomValue2));
/* 256 */     buffer.put(short0(this.randomValue2));
/* 257 */     buffer.put(int2(this.counter));
/* 258 */     buffer.put(int1(this.counter));
/* 259 */     buffer.put(int0(this.counter));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTimestamp() {
/* 268 */     return this.timestamp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Date getDate() {
/* 277 */     return new Date((this.timestamp & 0xFFFFFFFFL) * 1000L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toHexString() {
/* 286 */     char[] chars = new char[24];
/* 287 */     int i = 0;
/* 288 */     for (byte b : toByteArray()) {
/* 289 */       chars[i++] = HEX_CHARS[b >> 4 & 0xF];
/* 290 */       chars[i++] = HEX_CHARS[b & 0xF];
/*     */     } 
/* 292 */     return new String(chars);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 297 */     if (this == o) {
/* 298 */       return true;
/*     */     }
/* 300 */     if (o == null || getClass() != o.getClass()) {
/* 301 */       return false;
/*     */     }
/*     */     
/* 304 */     ObjectId objectId = (ObjectId)o;
/*     */     
/* 306 */     if (this.counter != objectId.counter) {
/* 307 */       return false;
/*     */     }
/* 309 */     if (this.timestamp != objectId.timestamp) {
/* 310 */       return false;
/*     */     }
/*     */     
/* 313 */     if (this.randomValue1 != objectId.randomValue1) {
/* 314 */       return false;
/*     */     }
/*     */     
/* 317 */     if (this.randomValue2 != objectId.randomValue2) {
/* 318 */       return false;
/*     */     }
/*     */     
/* 321 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 326 */     int result = this.timestamp;
/* 327 */     result = 31 * result + this.counter;
/* 328 */     result = 31 * result + this.randomValue1;
/* 329 */     result = 31 * result + this.randomValue2;
/* 330 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(ObjectId other) {
/* 335 */     if (other == null) {
/* 336 */       throw new NullPointerException();
/*     */     }
/*     */     
/* 339 */     byte[] byteArray = toByteArray();
/* 340 */     byte[] otherByteArray = other.toByteArray();
/* 341 */     for (int i = 0; i < 12; i++) {
/* 342 */       if (byteArray[i] != otherByteArray[i]) {
/* 343 */         return ((byteArray[i] & 0xFF) < (otherByteArray[i] & 0xFF)) ? -1 : 1;
/*     */       }
/*     */     } 
/* 346 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 351 */     return toHexString();
/*     */   }
/*     */ 
/*     */   
/*     */   private Object writeReplace() {
/* 356 */     return new SerializationProxy(this);
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream stream) throws InvalidObjectException {
/* 361 */     throw new InvalidObjectException("Proxy required");
/*     */   }
/*     */   
/*     */   private static class SerializationProxy
/*     */     implements Serializable {
/*     */     private static final long serialVersionUID = 1L;
/*     */     private final byte[] bytes;
/*     */     
/*     */     SerializationProxy(ObjectId objectId) {
/* 370 */       this.bytes = objectId.toByteArray();
/*     */     }
/*     */     
/*     */     private Object readResolve() {
/* 374 */       return new ObjectId(this.bytes);
/*     */     }
/*     */   }
/*     */   
/*     */   static {
/*     */     try {
/* 380 */       SecureRandom secureRandom = new SecureRandom();
/* 381 */       RANDOM_VALUE1 = secureRandom.nextInt(16777216);
/* 382 */       RANDOM_VALUE2 = (short)secureRandom.nextInt(32768);
/* 383 */     } catch (Exception e) {
/* 384 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static byte[] parseHexString(String s) {
/* 389 */     if (!isValid(s)) {
/* 390 */       throw new IllegalArgumentException("invalid hexadecimal representation of an ObjectId: [" + s + "]");
/*     */     }
/*     */     
/* 393 */     byte[] b = new byte[12];
/* 394 */     for (int i = 0; i < b.length; i++) {
/* 395 */       b[i] = (byte)Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16);
/*     */     }
/* 397 */     return b;
/*     */   }
/*     */   
/*     */   private static int dateToTimestampSeconds(Date time) {
/* 401 */     return (int)(time.getTime() / 1000L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int makeInt(byte b3, byte b2, byte b1, byte b0) {
/* 408 */     return b3 << 24 | (b2 & 0xFF) << 16 | (b1 & 0xFF) << 8 | b0 & 0xFF;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static short makeShort(byte b1, byte b0) {
/* 417 */     return (short)((b1 & 0xFF) << 8 | b0 & 0xFF);
/*     */   }
/*     */ 
/*     */   
/*     */   private static byte int3(int x) {
/* 422 */     return (byte)(x >> 24);
/*     */   }
/*     */   
/*     */   private static byte int2(int x) {
/* 426 */     return (byte)(x >> 16);
/*     */   }
/*     */   
/*     */   private static byte int1(int x) {
/* 430 */     return (byte)(x >> 8);
/*     */   }
/*     */   
/*     */   private static byte int0(int x) {
/* 434 */     return (byte)x;
/*     */   }
/*     */   
/*     */   private static byte short1(short x) {
/* 438 */     return (byte)(x >> 8);
/*     */   }
/*     */   
/*     */   private static byte short0(short x) {
/* 442 */     return (byte)x;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\types\ObjectId.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */