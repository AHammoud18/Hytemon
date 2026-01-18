/*     */ package com.google.crypto.tink.streamingaead;
/*     */ 
/*     */ import com.google.crypto.tink.StreamingAead;
/*     */ import com.google.errorprone.annotations.CanIgnoreReturnValue;
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.NonWritableChannelException;
/*     */ import java.nio.channels.SeekableByteChannel;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Deque;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class SeekableByteChannelDecrypter
/*     */   implements SeekableByteChannel
/*     */ {
/*     */   @GuardedBy("this")
/*  65 */   SeekableByteChannel attemptingChannel = null; @GuardedBy("this") SeekableByteChannel ciphertextChannel; @GuardedBy("this") long cachedPosition; @GuardedBy("this")
/*  66 */   SeekableByteChannel matchingChannel = null; @GuardedBy("this")
/*  67 */   long startingPosition; Deque<StreamingAead> remainingPrimitives = new ArrayDeque<>(); byte[] associatedData; public SeekableByteChannelDecrypter(List<StreamingAead> allPrimitives, SeekableByteChannel ciphertextChannel, byte[] associatedData) throws IOException {
/*  68 */     for (StreamingAead primitive : allPrimitives) {
/*  69 */       this.remainingPrimitives.add(primitive);
/*     */     }
/*  71 */     this.ciphertextChannel = ciphertextChannel;
/*     */ 
/*     */ 
/*     */     
/*  75 */     this.cachedPosition = -1L;
/*  76 */     this.startingPosition = ciphertextChannel.position();
/*  77 */     this.associatedData = (byte[])associatedData.clone();
/*     */   }
/*     */   
/*     */   @GuardedBy("this")
/*     */   private synchronized SeekableByteChannel nextAttemptingChannel() throws IOException {
/*  82 */     while (!this.remainingPrimitives.isEmpty()) {
/*  83 */       this.ciphertextChannel.position(this.startingPosition);
/*  84 */       StreamingAead streamingAead = this.remainingPrimitives.removeFirst();
/*     */       
/*     */       try {
/*  87 */         SeekableByteChannel decChannel = streamingAead.newSeekableDecryptingChannel(this.ciphertextChannel, this.associatedData);
/*  88 */         if (this.cachedPosition >= 0L) {
/*  89 */           decChannel.position(this.cachedPosition);
/*     */         }
/*  91 */         return decChannel;
/*  92 */       } catch (GeneralSecurityException generalSecurityException) {}
/*     */     } 
/*     */ 
/*     */     
/*  96 */     throw new IOException("No matching key found for the ciphertext in the stream.");
/*     */   }
/*     */ 
/*     */   
/*     */   @GuardedBy("this")
/*     */   public synchronized int read(ByteBuffer dst) throws IOException {
/* 102 */     if (dst.remaining() == 0) {
/* 103 */       return 0;
/*     */     }
/* 105 */     if (this.matchingChannel != null) {
/* 106 */       return this.matchingChannel.read(dst);
/*     */     }
/* 108 */     if (this.attemptingChannel == null) {
/* 109 */       this.attemptingChannel = nextAttemptingChannel();
/*     */     }
/*     */     while (true) {
/*     */       try {
/* 113 */         int retValue = this.attemptingChannel.read(dst);
/* 114 */         if (retValue == 0)
/*     */         {
/*     */           
/* 117 */           return 0;
/*     */         }
/*     */         
/* 120 */         this.matchingChannel = this.attemptingChannel;
/* 121 */         this.attemptingChannel = null;
/* 122 */         return retValue;
/* 123 */       } catch (IOException e) {
/*     */ 
/*     */ 
/*     */         
/* 127 */         this.attemptingChannel = nextAttemptingChannel();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @CanIgnoreReturnValue
/*     */   @GuardedBy("this")
/*     */   public synchronized SeekableByteChannel position(long newPosition) throws IOException {
/* 137 */     if (this.matchingChannel != null) {
/* 138 */       this.matchingChannel.position(newPosition);
/*     */     } else {
/* 140 */       if (newPosition < 0L) {
/* 141 */         throw new IllegalArgumentException("Position must be non-negative");
/*     */       }
/* 143 */       this.cachedPosition = newPosition;
/* 144 */       if (this.attemptingChannel != null) {
/* 145 */         this.attemptingChannel.position(this.cachedPosition);
/*     */       }
/*     */     } 
/* 148 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @GuardedBy("this")
/*     */   public synchronized long position() throws IOException {
/* 154 */     if (this.matchingChannel != null) {
/* 155 */       return this.matchingChannel.position();
/*     */     }
/* 157 */     return this.cachedPosition;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @GuardedBy("this")
/*     */   public synchronized long size() throws IOException {
/* 164 */     if (this.matchingChannel != null) {
/* 165 */       return this.matchingChannel.size();
/*     */     }
/* 167 */     throw new IOException("Cannot determine size before first read()-call.");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SeekableByteChannel truncate(long size) throws IOException {
/* 173 */     throw new NonWritableChannelException();
/*     */   }
/*     */ 
/*     */   
/*     */   public int write(ByteBuffer src) throws IOException {
/* 178 */     throw new NonWritableChannelException();
/*     */   }
/*     */ 
/*     */   
/*     */   @GuardedBy("this")
/*     */   public synchronized void close() throws IOException {
/* 184 */     this.ciphertextChannel.close();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @GuardedBy("this")
/*     */   public synchronized boolean isOpen() {
/* 191 */     return this.ciphertextChannel.isOpen();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\streamingaead\SeekableByteChannelDecrypter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */