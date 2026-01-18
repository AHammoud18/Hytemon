/*     */ package com.google.protobuf;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @CheckReturnValue
/*     */ abstract class AllocatedBuffer
/*     */ {
/*     */   public abstract boolean hasNioBuffer();
/*     */   
/*     */   public abstract boolean hasArray();
/*     */   
/*     */   public abstract ByteBuffer nioBuffer();
/*     */   
/*     */   public abstract byte[] array();
/*     */   
/*     */   public abstract int arrayOffset();
/*     */   
/*     */   public abstract int position();
/*     */   
/*     */   @CanIgnoreReturnValue
/*     */   public abstract AllocatedBuffer position(int paramInt);
/*     */   
/*     */   public abstract int limit();
/*     */   
/*     */   public abstract int remaining();
/*     */   
/*     */   public static AllocatedBuffer wrap(byte[] bytes) {
/* 111 */     return wrapNoCheck(bytes, 0, bytes.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AllocatedBuffer wrap(byte[] bytes, int offset, int length) {
/* 120 */     if (offset < 0 || length < 0 || offset + length > bytes.length) {
/* 121 */       throw new IndexOutOfBoundsException(
/* 122 */           String.format("bytes.length=%d, offset=%d, length=%d", new Object[] { Integer.valueOf(bytes.length), Integer.valueOf(offset), Integer.valueOf(length) }));
/*     */     }
/*     */     
/* 125 */     return wrapNoCheck(bytes, offset, length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AllocatedBuffer wrap(final ByteBuffer buffer) {
/* 133 */     Internal.checkNotNull(buffer, "buffer");
/*     */     
/* 135 */     return new AllocatedBuffer()
/*     */       {
/*     */         public boolean hasNioBuffer()
/*     */         {
/* 139 */           return true;
/*     */         }
/*     */ 
/*     */         
/*     */         public ByteBuffer nioBuffer() {
/* 144 */           return buffer;
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean hasArray() {
/* 149 */           return buffer.hasArray();
/*     */         }
/*     */ 
/*     */         
/*     */         public byte[] array() {
/* 154 */           return buffer.array();
/*     */         }
/*     */ 
/*     */         
/*     */         public int arrayOffset() {
/* 159 */           return buffer.arrayOffset();
/*     */         }
/*     */ 
/*     */         
/*     */         public int position() {
/* 164 */           return buffer.position();
/*     */         }
/*     */ 
/*     */         
/*     */         public AllocatedBuffer position(int position) {
/* 169 */           Java8Compatibility.position(buffer, position);
/* 170 */           return this;
/*     */         }
/*     */ 
/*     */         
/*     */         public int limit() {
/* 175 */           return buffer.limit();
/*     */         }
/*     */ 
/*     */         
/*     */         public int remaining() {
/* 180 */           return buffer.remaining();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   private static AllocatedBuffer wrapNoCheck(final byte[] bytes, final int offset, final int length) {
/* 187 */     return new AllocatedBuffer()
/*     */       {
/*     */         private int position;
/*     */ 
/*     */         
/*     */         public boolean hasNioBuffer() {
/* 193 */           return false;
/*     */         }
/*     */ 
/*     */         
/*     */         public ByteBuffer nioBuffer() {
/* 198 */           throw new UnsupportedOperationException();
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean hasArray() {
/* 203 */           return true;
/*     */         }
/*     */ 
/*     */         
/*     */         public byte[] array() {
/* 208 */           return bytes;
/*     */         }
/*     */ 
/*     */         
/*     */         public int arrayOffset() {
/* 213 */           return offset;
/*     */         }
/*     */ 
/*     */         
/*     */         public int position() {
/* 218 */           return this.position;
/*     */         }
/*     */ 
/*     */         
/*     */         public AllocatedBuffer position(int position) {
/* 223 */           if (position < 0 || position > length) {
/* 224 */             throw new IllegalArgumentException("Invalid position: " + position);
/*     */           }
/* 226 */           this.position = position;
/* 227 */           return this;
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public int limit() {
/* 233 */           return length;
/*     */         }
/*     */ 
/*     */         
/*     */         public int remaining() {
/* 238 */           return length - this.position;
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\AllocatedBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */