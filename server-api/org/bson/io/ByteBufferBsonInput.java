/*     */ package org.bson.io;
/*     */ 
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.charset.Charset;
/*     */ import org.bson.BsonSerializationException;
/*     */ import org.bson.ByteBuf;
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
/*     */ public class ByteBufferBsonInput
/*     */   implements BsonInput
/*     */ {
/*  34 */   private static final Charset UTF8_CHARSET = Charset.forName("UTF-8");
/*     */   
/*  36 */   private static final String[] ONE_BYTE_ASCII_STRINGS = new String[128];
/*     */   
/*     */   static {
/*  39 */     for (int b = 0; b < ONE_BYTE_ASCII_STRINGS.length; b++) {
/*  40 */       ONE_BYTE_ASCII_STRINGS[b] = String.valueOf((char)b);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ByteBuf buffer;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBufferBsonInput(ByteBuf buffer) {
/*  53 */     if (buffer == null) {
/*  54 */       throw new IllegalArgumentException("buffer can not be null");
/*     */     }
/*  56 */     this.buffer = buffer;
/*  57 */     buffer.order(ByteOrder.LITTLE_ENDIAN);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPosition() {
/*  62 */     ensureOpen();
/*  63 */     return this.buffer.position();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte readByte() {
/*  69 */     ensureOpen();
/*  70 */     ensureAvailable(1);
/*  71 */     return this.buffer.get();
/*     */   }
/*     */ 
/*     */   
/*     */   public void readBytes(byte[] bytes) {
/*  76 */     ensureOpen();
/*  77 */     ensureAvailable(bytes.length);
/*  78 */     this.buffer.get(bytes);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readBytes(byte[] bytes, int offset, int length) {
/*  83 */     ensureOpen();
/*  84 */     ensureAvailable(length);
/*  85 */     this.buffer.get(bytes, offset, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public long readInt64() {
/*  90 */     ensureOpen();
/*  91 */     ensureAvailable(8);
/*  92 */     return this.buffer.getLong();
/*     */   }
/*     */ 
/*     */   
/*     */   public double readDouble() {
/*  97 */     ensureOpen();
/*  98 */     ensureAvailable(8);
/*  99 */     return this.buffer.getDouble();
/*     */   }
/*     */ 
/*     */   
/*     */   public int readInt32() {
/* 104 */     ensureOpen();
/* 105 */     ensureAvailable(4);
/* 106 */     return this.buffer.getInt();
/*     */   }
/*     */ 
/*     */   
/*     */   public ObjectId readObjectId() {
/* 111 */     ensureOpen();
/* 112 */     byte[] bytes = new byte[12];
/* 113 */     readBytes(bytes);
/* 114 */     return new ObjectId(bytes);
/*     */   }
/*     */ 
/*     */   
/*     */   public String readString() {
/* 119 */     ensureOpen();
/* 120 */     int size = readInt32();
/* 121 */     if (size <= 0)
/* 122 */       throw new BsonSerializationException(String.format("While decoding a BSON string found a size that is not a positive number: %d", new Object[] {
/* 123 */               Integer.valueOf(size)
/*     */             })); 
/* 125 */     ensureAvailable(size);
/* 126 */     return readString(size);
/*     */   }
/*     */ 
/*     */   
/*     */   public String readCString() {
/* 131 */     int mark = this.buffer.position();
/* 132 */     skipCString();
/* 133 */     int size = this.buffer.position() - mark;
/* 134 */     this.buffer.position(mark);
/* 135 */     return readString(size);
/*     */   }
/*     */   
/*     */   private String readString(int size) {
/* 139 */     if (size == 2) {
/* 140 */       byte asciiByte = this.buffer.get();
/* 141 */       byte b1 = this.buffer.get();
/* 142 */       if (b1 != 0) {
/* 143 */         throw new BsonSerializationException("Found a BSON string that is not null-terminated");
/*     */       }
/* 145 */       if (asciiByte < 0) {
/* 146 */         return UTF8_CHARSET.newDecoder().replacement();
/*     */       }
/* 148 */       return ONE_BYTE_ASCII_STRINGS[asciiByte];
/*     */     } 
/* 150 */     byte[] bytes = new byte[size - 1];
/* 151 */     this.buffer.get(bytes);
/* 152 */     byte nullByte = this.buffer.get();
/* 153 */     if (nullByte != 0) {
/* 154 */       throw new BsonSerializationException("Found a BSON string that is not null-terminated");
/*     */     }
/* 156 */     return new String(bytes, UTF8_CHARSET);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void skipCString() {
/* 162 */     ensureOpen();
/* 163 */     boolean checkNext = true;
/* 164 */     while (checkNext) {
/* 165 */       if (!this.buffer.hasRemaining()) {
/* 166 */         throw new BsonSerializationException("Found a BSON string that is not null-terminated");
/*     */       }
/* 168 */       checkNext = (this.buffer.get() != 0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void skip(int numBytes) {
/* 174 */     ensureOpen();
/* 175 */     this.buffer.position(this.buffer.position() + numBytes);
/*     */   }
/*     */ 
/*     */   
/*     */   public BsonInputMark getMark(int readLimit) {
/* 180 */     return new BsonInputMark() {
/* 181 */         private int mark = ByteBufferBsonInput.this.buffer.position();
/*     */         
/*     */         public void reset() {
/* 184 */           ByteBufferBsonInput.this.ensureOpen();
/* 185 */           ByteBufferBsonInput.this.buffer.position(this.mark);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasRemaining() {
/* 192 */     ensureOpen();
/* 193 */     return this.buffer.hasRemaining();
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {
/* 198 */     this.buffer.release();
/* 199 */     this.buffer = null;
/*     */   }
/*     */   
/*     */   private void ensureOpen() {
/* 203 */     if (this.buffer == null)
/* 204 */       throw new IllegalStateException("Stream is closed"); 
/*     */   }
/*     */   
/*     */   private void ensureAvailable(int bytesNeeded) {
/* 208 */     if (this.buffer.remaining() < bytesNeeded)
/* 209 */       throw new BsonSerializationException(String.format("While decoding a BSON document %d bytes were required, but only %d remain", new Object[] {
/* 210 */               Integer.valueOf(bytesNeeded), Integer.valueOf(this.buffer.remaining())
/*     */             })); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\io\ByteBufferBsonInput.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */