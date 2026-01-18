/*     */ package com.google.protobuf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class IterableByteBufferInputStream
/*     */   extends InputStream
/*     */ {
/*     */   private Iterator<ByteBuffer> iterator;
/*     */   private ByteBuffer currentByteBuffer;
/*     */   private int dataSize;
/*     */   private int currentIndex;
/*     */   private int currentByteBufferPos;
/*     */   private boolean hasArray;
/*     */   private byte[] currentArray;
/*     */   private int currentArrayOffset;
/*     */   private long currentAddress;
/*     */   
/*     */   IterableByteBufferInputStream(Iterable<ByteBuffer> data) {
/*  56 */     this.iterator = data.iterator();
/*  57 */     this.dataSize = 0;
/*  58 */     for (ByteBuffer unused : data) {
/*  59 */       this.dataSize++;
/*     */     }
/*  61 */     this.currentIndex = -1;
/*     */     
/*  63 */     if (!getNextByteBuffer()) {
/*  64 */       this.currentByteBuffer = Internal.EMPTY_BYTE_BUFFER;
/*  65 */       this.currentIndex = 0;
/*  66 */       this.currentByteBufferPos = 0;
/*  67 */       this.currentAddress = 0L;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean getNextByteBuffer() {
/*     */     while (true) {
/*  75 */       this.currentIndex++;
/*  76 */       if (!this.iterator.hasNext()) {
/*  77 */         return false;
/*     */       }
/*  79 */       this.currentByteBuffer = this.iterator.next();
/*  80 */       if (this.currentByteBuffer.hasRemaining()) {
/*  81 */         this.currentByteBufferPos = this.currentByteBuffer.position();
/*  82 */         if (this.currentByteBuffer.hasArray()) {
/*  83 */           this.hasArray = true;
/*  84 */           this.currentArray = this.currentByteBuffer.array();
/*  85 */           this.currentArrayOffset = this.currentByteBuffer.arrayOffset();
/*     */         } else {
/*  87 */           this.hasArray = false;
/*  88 */           this.currentAddress = UnsafeUtil.addressOffset(this.currentByteBuffer);
/*  89 */           this.currentArray = null;
/*     */         } 
/*  91 */         return true;
/*     */       } 
/*     */     } 
/*     */   } private void updateCurrentByteBufferPos(int numberOfBytesRead) {
/*  95 */     this.currentByteBufferPos += numberOfBytesRead;
/*  96 */     if (this.currentByteBufferPos == this.currentByteBuffer.limit()) {
/*  97 */       getNextByteBuffer();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int read() throws IOException {
/* 103 */     if (this.currentIndex == this.dataSize) {
/* 104 */       return -1;
/*     */     }
/* 106 */     if (this.hasArray) {
/* 107 */       int i = this.currentArray[this.currentByteBufferPos + this.currentArrayOffset] & 0xFF;
/* 108 */       updateCurrentByteBufferPos(1);
/* 109 */       return i;
/*     */     } 
/* 111 */     int result = UnsafeUtil.getByte(this.currentByteBufferPos + this.currentAddress) & 0xFF;
/* 112 */     updateCurrentByteBufferPos(1);
/* 113 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int read(byte[] output, int offset, int length) throws IOException {
/* 119 */     if (this.currentIndex == this.dataSize) {
/* 120 */       return -1;
/*     */     }
/* 122 */     int remaining = this.currentByteBuffer.limit() - this.currentByteBufferPos;
/* 123 */     if (length > remaining) {
/* 124 */       length = remaining;
/*     */     }
/* 126 */     if (this.hasArray) {
/* 127 */       System.arraycopy(this.currentArray, this.currentByteBufferPos + this.currentArrayOffset, output, offset, length);
/*     */       
/* 129 */       updateCurrentByteBufferPos(length);
/*     */     } else {
/* 131 */       int prevPos = this.currentByteBuffer.position();
/* 132 */       Java8Compatibility.position(this.currentByteBuffer, this.currentByteBufferPos);
/* 133 */       this.currentByteBuffer.get(output, offset, length);
/* 134 */       Java8Compatibility.position(this.currentByteBuffer, prevPos);
/* 135 */       updateCurrentByteBufferPos(length);
/*     */     } 
/* 137 */     return length;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\IterableByteBufferInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */