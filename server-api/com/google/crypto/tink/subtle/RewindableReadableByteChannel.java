/*     */ package com.google.crypto.tink.subtle;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.ReadableByteChannel;
/*     */ import javax.annotation.concurrent.GuardedBy;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class RewindableReadableByteChannel
/*     */   implements ReadableByteChannel
/*     */ {
/*     */   @GuardedBy("this")
/*     */   final ReadableByteChannel baseChannel;
/*     */   @GuardedBy("this")
/*     */   ByteBuffer buffer;
/*     */   @GuardedBy("this")
/*     */   boolean canRewind;
/*     */   @GuardedBy("this")
/*     */   boolean directRead;
/*     */   
/*     */   public RewindableReadableByteChannel(ReadableByteChannel baseChannel) {
/*  52 */     this.baseChannel = baseChannel;
/*  53 */     this.buffer = null;
/*  54 */     this.canRewind = true;
/*  55 */     this.directRead = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void disableRewinding() {
/*  65 */     this.canRewind = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void rewind() throws IOException {
/*  72 */     if (!this.canRewind) {
/*  73 */       throw new IOException("Cannot rewind anymore.");
/*     */     }
/*  75 */     if (this.buffer != null) {
/*  76 */       this.buffer.position(0);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized void setBufferLimit(int newLimit) {
/*  86 */     if (this.buffer.capacity() < newLimit) {
/*  87 */       int pos = this.buffer.position();
/*  88 */       int newBufferCapacity = Math.max(2 * this.buffer.capacity(), newLimit);
/*  89 */       ByteBuffer newBuffer = ByteBuffer.allocate(newBufferCapacity);
/*  90 */       this.buffer.rewind();
/*  91 */       newBuffer.put(this.buffer);
/*  92 */       newBuffer.position(pos);
/*  93 */       this.buffer = newBuffer;
/*     */     } 
/*  95 */     this.buffer.limit(newLimit);
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized int read(ByteBuffer dst) throws IOException {
/* 100 */     if (this.directRead) {
/* 101 */       return this.baseChannel.read(dst);
/*     */     }
/* 103 */     int bytesToReadCount = dst.remaining();
/* 104 */     if (bytesToReadCount == 0) {
/* 105 */       return 0;
/*     */     }
/* 107 */     if (this.buffer == null) {
/* 108 */       if (!this.canRewind) {
/* 109 */         this.directRead = true;
/* 110 */         return this.baseChannel.read(dst);
/*     */       } 
/* 112 */       this.buffer = ByteBuffer.allocate(bytesToReadCount);
/* 113 */       int i = this.baseChannel.read(this.buffer);
/*     */       
/* 115 */       this.buffer.flip();
/* 116 */       if (i > 0) {
/* 117 */         dst.put(this.buffer);
/*     */       }
/* 119 */       return i;
/*     */     } 
/*     */     
/* 122 */     if (this.buffer.remaining() >= bytesToReadCount) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 128 */       int limit = this.buffer.limit();
/* 129 */       this.buffer.limit(this.buffer.position() + bytesToReadCount);
/* 130 */       dst.put(this.buffer);
/* 131 */       this.buffer.limit(limit);
/* 132 */       if (!this.canRewind && !this.buffer.hasRemaining()) {
/* 133 */         this.buffer = null;
/* 134 */         this.directRead = true;
/*     */       } 
/* 136 */       return bytesToReadCount;
/*     */     } 
/* 138 */     int bytesFromBufferCount = this.buffer.remaining();
/* 139 */     int stillToReadCount = bytesToReadCount - bytesFromBufferCount;
/*     */ 
/*     */     
/* 142 */     int currentReadPos = this.buffer.position();
/* 143 */     int contentLimit = this.buffer.limit();
/*     */ 
/*     */     
/* 146 */     setBufferLimit(contentLimit + stillToReadCount);
/* 147 */     this.buffer.position(contentLimit);
/* 148 */     int baseReadResult = this.baseChannel.read(this.buffer);
/*     */     
/* 150 */     this.buffer.flip();
/* 151 */     this.buffer.position(currentReadPos);
/* 152 */     dst.put(this.buffer);
/* 153 */     if (bytesFromBufferCount == 0 && baseReadResult < 0) {
/* 154 */       return -1;
/*     */     }
/* 156 */     int bytesCount = this.buffer.position() - currentReadPos;
/* 157 */     if (!this.canRewind && !this.buffer.hasRemaining()) {
/* 158 */       this.buffer = null;
/* 159 */       this.directRead = true;
/*     */     } 
/* 161 */     return bytesCount;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void close() throws IOException {
/* 166 */     this.canRewind = false;
/* 167 */     this.directRead = true;
/* 168 */     this.baseChannel.close();
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized boolean isOpen() {
/* 173 */     return this.baseChannel.isOpen();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\subtle\RewindableReadableByteChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */