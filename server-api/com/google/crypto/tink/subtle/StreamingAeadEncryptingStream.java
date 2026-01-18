/*     */ package com.google.crypto.tink.subtle;
/*     */ 
/*     */ import java.io.FilterOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.Buffer;
/*     */ import java.nio.ByteBuffer;
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
/*     */ 
/*     */ class StreamingAeadEncryptingStream
/*     */   extends FilterOutputStream
/*     */ {
/*     */   private StreamSegmentEncrypter encrypter;
/*     */   private int plaintextSegmentSize;
/*     */   ByteBuffer ptBuffer;
/*     */   ByteBuffer ctBuffer;
/*     */   boolean open;
/*     */   
/*     */   private static Buffer toBuffer(ByteBuffer b) {
/*  43 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public StreamingAeadEncryptingStream(NonceBasedStreamingAead streamAead, OutputStream ciphertextChannel, byte[] associatedData) throws GeneralSecurityException, IOException {
/*  49 */     super(ciphertextChannel);
/*  50 */     this.encrypter = streamAead.newStreamSegmentEncrypter(associatedData);
/*  51 */     this.plaintextSegmentSize = streamAead.getPlaintextSegmentSize();
/*  52 */     this.ptBuffer = ByteBuffer.allocate(this.plaintextSegmentSize);
/*  53 */     this.ctBuffer = ByteBuffer.allocate(streamAead.getCiphertextSegmentSize());
/*  54 */     toBuffer(this.ptBuffer).limit(this.plaintextSegmentSize - streamAead.getCiphertextOffset());
/*  55 */     ByteBuffer header = this.encrypter.getHeader();
/*  56 */     byte[] headerBytes = new byte[header.remaining()];
/*  57 */     header.get(headerBytes);
/*  58 */     this.out.write(headerBytes);
/*  59 */     this.open = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(int b) throws IOException {
/*  64 */     write(new byte[] { (byte)b });
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(byte[] b) throws IOException {
/*  69 */     write(b, 0, b.length);
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void write(byte[] pt, int offset, int length) throws IOException {
/*  74 */     if (!this.open) {
/*  75 */       throw new IOException("Trying to write to closed stream");
/*     */     }
/*  77 */     int startPosition = offset;
/*  78 */     int remaining = length;
/*  79 */     while (remaining > this.ptBuffer.remaining()) {
/*  80 */       int sliceSize = this.ptBuffer.remaining();
/*  81 */       ByteBuffer slice = ByteBuffer.wrap(pt, startPosition, sliceSize);
/*  82 */       startPosition += sliceSize;
/*  83 */       remaining -= sliceSize;
/*     */       try {
/*  85 */         toBuffer(this.ptBuffer).flip();
/*  86 */         toBuffer(this.ctBuffer).clear();
/*  87 */         this.encrypter.encryptSegment(this.ptBuffer, slice, false, this.ctBuffer);
/*  88 */       } catch (GeneralSecurityException ex) {
/*  89 */         throw new IOException(ex);
/*     */       } 
/*  91 */       toBuffer(this.ctBuffer).flip();
/*  92 */       this.out.write(this.ctBuffer.array(), toBuffer(this.ctBuffer).position(), this.ctBuffer.remaining());
/*  93 */       toBuffer(this.ptBuffer).clear();
/*  94 */       toBuffer(this.ptBuffer).limit(this.plaintextSegmentSize);
/*     */     } 
/*  96 */     this.ptBuffer.put(pt, startPosition, remaining);
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void close() throws IOException {
/* 101 */     if (!this.open) {
/*     */       return;
/*     */     }
/*     */     try {
/* 105 */       toBuffer(this.ptBuffer).flip();
/* 106 */       toBuffer(this.ctBuffer).clear();
/* 107 */       this.encrypter.encryptSegment(this.ptBuffer, true, this.ctBuffer);
/* 108 */     } catch (GeneralSecurityException ex) {
/* 109 */       throw new IOException("ptBuffer.remaining():" + this.ptBuffer
/*     */           
/* 111 */           .remaining() + " ctBuffer.remaining():" + this.ctBuffer
/*     */           
/* 113 */           .remaining(), ex);
/*     */     } 
/*     */     
/* 116 */     toBuffer(this.ctBuffer).flip();
/* 117 */     this.out.write(this.ctBuffer.array(), this.ctBuffer.position(), this.ctBuffer.remaining());
/* 118 */     this.open = false;
/* 119 */     super.close();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\subtle\StreamingAeadEncryptingStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */