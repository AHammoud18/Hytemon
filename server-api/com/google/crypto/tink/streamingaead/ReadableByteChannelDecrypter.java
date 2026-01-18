/*     */ package com.google.crypto.tink.streamingaead;
/*     */ 
/*     */ import com.google.crypto.tink.StreamingAead;
/*     */ import com.google.crypto.tink.subtle.RewindableReadableByteChannel;
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.ReadableByteChannel;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ final class ReadableByteChannelDecrypter
/*     */   implements ReadableByteChannel
/*     */ {
/*     */   @GuardedBy("this")
/*  67 */   ReadableByteChannel attemptingChannel = null; @GuardedBy("this")
/*  68 */   ReadableByteChannel matchingChannel = null; @GuardedBy("this")
/*  69 */   RewindableReadableByteChannel ciphertextChannel; Deque<StreamingAead> remainingPrimitives = new ArrayDeque<>(); public ReadableByteChannelDecrypter(List<StreamingAead> allPrimitives, ReadableByteChannel ciphertextChannel, byte[] associatedData) {
/*  70 */     for (StreamingAead primitive : allPrimitives) {
/*  71 */       this.remainingPrimitives.add(primitive);
/*     */     }
/*  73 */     this.ciphertextChannel = new RewindableReadableByteChannel(ciphertextChannel);
/*  74 */     this.associatedData = (byte[])associatedData.clone();
/*     */   }
/*     */   byte[] associatedData;
/*     */   @GuardedBy("this")
/*     */   private synchronized ReadableByteChannel nextAttemptingChannel() throws IOException {
/*  79 */     while (!this.remainingPrimitives.isEmpty()) {
/*  80 */       StreamingAead streamingAead = this.remainingPrimitives.removeFirst();
/*     */       try {
/*  82 */         ReadableByteChannel decChannel = streamingAead.newDecryptingChannel((ReadableByteChannel)this.ciphertextChannel, this.associatedData);
/*     */         
/*  84 */         return decChannel;
/*  85 */       } catch (GeneralSecurityException e) {
/*     */         
/*  87 */         this.ciphertextChannel.rewind();
/*     */       } 
/*     */     } 
/*  90 */     throw new IOException("No matching key found for the ciphertext in the stream.");
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized int read(ByteBuffer dst) throws IOException {
/*  95 */     if (dst.remaining() == 0) {
/*  96 */       return 0;
/*     */     }
/*  98 */     if (this.matchingChannel != null) {
/*  99 */       return this.matchingChannel.read(dst);
/*     */     }
/* 101 */     if (this.attemptingChannel == null) {
/* 102 */       this.attemptingChannel = nextAttemptingChannel();
/*     */     }
/*     */     while (true) {
/*     */       try {
/* 106 */         int retValue = this.attemptingChannel.read(dst);
/* 107 */         if (retValue == 0)
/*     */         {
/*     */           
/* 110 */           return 0;
/*     */         }
/*     */         
/* 113 */         this.matchingChannel = this.attemptingChannel;
/* 114 */         this.attemptingChannel = null;
/* 115 */         this.ciphertextChannel.disableRewinding();
/* 116 */         return retValue;
/* 117 */       } catch (IOException e) {
/*     */ 
/*     */ 
/*     */         
/* 121 */         this.ciphertextChannel.rewind();
/* 122 */         this.attemptingChannel = nextAttemptingChannel();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void close() throws IOException {
/* 130 */     this.ciphertextChannel.close();
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized boolean isOpen() {
/* 135 */     return this.ciphertextChannel.isOpen();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\streamingaead\ReadableByteChannelDecrypter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */