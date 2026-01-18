/*     */ package com.google.crypto.tink.subtle;
/*     */ 
/*     */ import com.google.errorprone.annotations.CanIgnoreReturnValue;
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.ClosedChannelException;
/*     */ import java.nio.channels.WritableByteChannel;
/*     */ import java.security.GeneralSecurityException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class StreamingAeadEncryptingChannel
/*     */   implements WritableByteChannel
/*     */ {
/*     */   private WritableByteChannel ciphertextChannel;
/*     */   private StreamSegmentEncrypter encrypter;
/*     */   ByteBuffer ptBuffer;
/*     */   ByteBuffer ctBuffer;
/*     */   private int plaintextSegmentSize;
/*     */   boolean open = true;
/*     */   
/*     */   @CanIgnoreReturnValue
/*     */   private int writeWithCheck(WritableByteChannel dst, ByteBuffer src) throws IOException {
/*  44 */     int r = src.remaining();
/*  45 */     int n = dst.write(src);
/*  46 */     if (n < 0 || n > r) {
/*  47 */       throw new IOException("Invalid return value from dst.write: n = " + n + ", r = " + r);
/*     */     }
/*     */     
/*  50 */     if (src.remaining() != r - n) {
/*  51 */       throw new IOException("Unexpected state after of src after writing to dst:  src.remaining() = " + src
/*     */ 
/*     */           
/*  54 */           .remaining() + " != r - n = " + r + " - " + n);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  60 */     return n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StreamingAeadEncryptingChannel(NonceBasedStreamingAead streamAead, WritableByteChannel ciphertextChannel, byte[] associatedData) throws GeneralSecurityException, IOException {
/*  67 */     this.ciphertextChannel = ciphertextChannel;
/*  68 */     this.encrypter = streamAead.newStreamSegmentEncrypter(associatedData);
/*  69 */     this.plaintextSegmentSize = streamAead.getPlaintextSegmentSize();
/*  70 */     this.ptBuffer = ByteBuffer.allocate(this.plaintextSegmentSize);
/*  71 */     this.ptBuffer.limit(this.plaintextSegmentSize - streamAead.getCiphertextOffset());
/*  72 */     this.ctBuffer = ByteBuffer.allocate(streamAead.getCiphertextSegmentSize());
/*     */ 
/*     */ 
/*     */     
/*  76 */     this.ctBuffer.put(this.encrypter.getHeader());
/*  77 */     this.ctBuffer.flip();
/*  78 */     writeWithCheck(ciphertextChannel, this.ctBuffer);
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized int write(ByteBuffer pt) throws IOException {
/*  83 */     if (!this.open) {
/*  84 */       throw new ClosedChannelException();
/*     */     }
/*  86 */     if (this.ctBuffer.remaining() > 0) {
/*  87 */       writeWithCheck(this.ciphertextChannel, this.ctBuffer);
/*     */     }
/*  89 */     int startPosition = pt.position();
/*  90 */     while (pt.remaining() > this.ptBuffer.remaining()) {
/*  91 */       if (this.ctBuffer.remaining() > 0) {
/*  92 */         return pt.position() - startPosition;
/*     */       }
/*  94 */       int sliceSize = this.ptBuffer.remaining();
/*  95 */       ByteBuffer slice = pt.slice();
/*  96 */       slice.limit(sliceSize);
/*  97 */       pt.position(pt.position() + sliceSize);
/*     */       try {
/*  99 */         this.ptBuffer.flip();
/* 100 */         this.ctBuffer.clear();
/* 101 */         if (slice.remaining() != 0) {
/* 102 */           this.encrypter.encryptSegment(this.ptBuffer, slice, false, this.ctBuffer);
/*     */         } else {
/* 104 */           this.encrypter.encryptSegment(this.ptBuffer, false, this.ctBuffer);
/*     */         } 
/* 106 */       } catch (GeneralSecurityException ex) {
/* 107 */         throw new IOException(ex);
/*     */       } 
/* 109 */       this.ctBuffer.flip();
/* 110 */       writeWithCheck(this.ciphertextChannel, this.ctBuffer);
/* 111 */       this.ptBuffer.clear();
/* 112 */       this.ptBuffer.limit(this.plaintextSegmentSize);
/*     */     } 
/* 114 */     this.ptBuffer.put(pt);
/* 115 */     return pt.position() - startPosition;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void close() throws IOException {
/* 120 */     if (!this.open) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 125 */     while (this.ctBuffer.remaining() > 0) {
/* 126 */       int n = writeWithCheck(this.ciphertextChannel, this.ctBuffer);
/* 127 */       if (n <= 0) {
/* 128 */         throw new IOException("Failed to write ciphertext before closing");
/*     */       }
/*     */     } 
/*     */     try {
/* 132 */       this.ctBuffer.clear();
/* 133 */       this.ptBuffer.flip();
/* 134 */       this.encrypter.encryptSegment(this.ptBuffer, true, this.ctBuffer);
/* 135 */     } catch (GeneralSecurityException ex) {
/* 136 */       throw new IOException(ex);
/*     */     } 
/* 138 */     this.ctBuffer.flip();
/* 139 */     while (this.ctBuffer.remaining() > 0) {
/* 140 */       int n = writeWithCheck(this.ciphertextChannel, this.ctBuffer);
/* 141 */       if (n <= 0) {
/* 142 */         throw new IOException("Failed to write ciphertext before closing");
/*     */       }
/*     */     } 
/* 145 */     this.ciphertextChannel.close();
/* 146 */     this.open = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOpen() {
/* 151 */     return this.open;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\subtle\StreamingAeadEncryptingChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */