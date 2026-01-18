/*     */ package com.google.crypto.tink.streamingaead;
/*     */ 
/*     */ import com.google.crypto.tink.StreamingAead;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.security.GeneralSecurityException;
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
/*     */ final class InputStreamDecrypter
/*     */   extends InputStream
/*     */ {
/*     */   @GuardedBy("this")
/*     */   boolean attemptedMatching;
/*     */   @GuardedBy("this")
/*     */   InputStream matchingStream;
/*     */   @GuardedBy("this")
/*     */   InputStream ciphertextStream;
/*     */   List<StreamingAead> primitives;
/*     */   byte[] associatedData;
/*     */   
/*     */   public InputStreamDecrypter(List<StreamingAead> primitives, InputStream ciphertextStream, byte[] associatedData) {
/*  62 */     this.attemptedMatching = false;
/*  63 */     this.matchingStream = null;
/*  64 */     this.primitives = primitives;
/*     */     
/*  66 */     if (ciphertextStream.markSupported()) {
/*  67 */       this.ciphertextStream = ciphertextStream;
/*     */     } else {
/*  69 */       this.ciphertextStream = new BufferedInputStream(ciphertextStream);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  76 */     this.ciphertextStream.mark(2147483647);
/*  77 */     this.associatedData = (byte[])associatedData.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @GuardedBy("this")
/*     */   private void rewind() throws IOException {
/*  85 */     this.ciphertextStream.reset();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @GuardedBy("this")
/*     */   private void disableRewinding() throws IOException {
/*  96 */     this.ciphertextStream.mark(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean markSupported() {
/* 106 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   @GuardedBy("this")
/*     */   public synchronized int available() throws IOException {
/* 112 */     if (this.matchingStream == null) {
/* 113 */       return 0;
/*     */     }
/* 115 */     return this.matchingStream.available();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @GuardedBy("this")
/*     */   public synchronized int read() throws IOException {
/* 122 */     byte[] oneByte = new byte[1];
/* 123 */     if (read(oneByte) == 1) {
/* 124 */       return oneByte[0] & 0xFF;
/*     */     }
/* 126 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   @GuardedBy("this")
/*     */   public synchronized int read(byte[] b) throws IOException {
/* 132 */     return read(b, 0, b.length);
/*     */   }
/*     */ 
/*     */   
/*     */   @GuardedBy("this")
/*     */   public synchronized int read(byte[] b, int offset, int len) throws IOException {
/* 138 */     if (len == 0) {
/* 139 */       return 0;
/*     */     }
/* 141 */     if (this.matchingStream != null) {
/* 142 */       return this.matchingStream.read(b, offset, len);
/*     */     }
/* 144 */     if (this.attemptedMatching) {
/* 145 */       throw new IOException("No matching key found for the ciphertext in the stream.");
/*     */     }
/* 147 */     this.attemptedMatching = true;
/* 148 */     for (StreamingAead streamingAead : this.primitives) {
/*     */       
/*     */       try {
/* 151 */         InputStream attemptedStream = streamingAead.newDecryptingStream(this.ciphertextStream, this.associatedData);
/* 152 */         int retValue = attemptedStream.read(b, offset, len);
/* 153 */         if (retValue == 0)
/*     */         {
/* 155 */           throw new IOException("Could not read bytes from the ciphertext stream");
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 161 */         this.matchingStream = attemptedStream;
/* 162 */         disableRewinding();
/* 163 */         return retValue;
/* 164 */       } catch (IOException e) {
/*     */ 
/*     */ 
/*     */         
/* 168 */         rewind();
/*     */       }
/* 170 */       catch (GeneralSecurityException e) {
/*     */         
/* 172 */         rewind();
/*     */       } 
/*     */     } 
/*     */     
/* 176 */     throw new IOException("No matching key found for the ciphertext in the stream.");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @GuardedBy("this")
/*     */   public synchronized void close() throws IOException {
/* 183 */     this.ciphertextStream.close();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\streamingaead\InputStreamDecrypter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */