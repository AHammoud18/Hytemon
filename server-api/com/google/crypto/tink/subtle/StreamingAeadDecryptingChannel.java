/*     */ package com.google.crypto.tink.subtle;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.ReadableByteChannel;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class StreamingAeadDecryptingChannel
/*     */   implements ReadableByteChannel
/*     */ {
/*     */   private static final int PLAINTEXT_SEGMENT_EXTRA_SIZE = 16;
/*     */   private ReadableByteChannel ciphertextChannel;
/*     */   private ByteBuffer ciphertextSegment;
/*     */   private ByteBuffer plaintextSegment;
/*     */   private ByteBuffer header;
/*     */   private boolean headerRead;
/*     */   private boolean endOfCiphertext;
/*     */   private boolean endOfPlaintext;
/*     */   private boolean definedState;
/*     */   private final byte[] associatedData;
/*     */   private int segmentNr;
/*     */   private final StreamSegmentDecrypter decrypter;
/*     */   private final int ciphertextSegmentSize;
/*     */   private final int firstCiphertextSegmentSize;
/*     */   
/*     */   public StreamingAeadDecryptingChannel(NonceBasedStreamingAead streamAead, ReadableByteChannel ciphertextChannel, byte[] associatedData) throws GeneralSecurityException, IOException {
/*  90 */     this.decrypter = streamAead.newStreamSegmentDecrypter();
/*  91 */     this.ciphertextChannel = ciphertextChannel;
/*  92 */     this.header = ByteBuffer.allocate(streamAead.getHeaderLength());
/*  93 */     this.associatedData = Arrays.copyOf(associatedData, associatedData.length);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  98 */     this.ciphertextSegmentSize = streamAead.getCiphertextSegmentSize();
/*  99 */     this.ciphertextSegment = ByteBuffer.allocate(this.ciphertextSegmentSize + 1);
/* 100 */     this.ciphertextSegment.limit(0);
/* 101 */     this.firstCiphertextSegmentSize = this.ciphertextSegmentSize - streamAead.getCiphertextOffset();
/* 102 */     this.plaintextSegment = ByteBuffer.allocate(streamAead
/* 103 */         .getPlaintextSegmentSize() + 16);
/* 104 */     this.plaintextSegment.limit(0);
/* 105 */     this.headerRead = false;
/* 106 */     this.endOfCiphertext = false;
/* 107 */     this.endOfPlaintext = false;
/* 108 */     this.segmentNr = 0;
/* 109 */     this.definedState = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readSomeCiphertext(ByteBuffer buffer) throws IOException {
/*     */     int read;
/*     */     do {
/* 120 */       read = this.ciphertextChannel.read(buffer);
/* 121 */     } while (read > 0 && buffer.remaining() > 0);
/* 122 */     if (read < -1) {
/* 123 */       throw new IOException("Unexpected return value from ciphertextChannel.read");
/*     */     }
/* 125 */     if (read == -1) {
/* 126 */       this.endOfCiphertext = true;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean tryReadHeader() throws IOException {
/* 138 */     if (this.endOfCiphertext) {
/* 139 */       throw new IOException("Ciphertext is too short");
/*     */     }
/* 141 */     readSomeCiphertext(this.header);
/* 142 */     if (this.header.remaining() > 0) {
/* 143 */       return false;
/*     */     }
/* 145 */     this.header.flip();
/*     */     try {
/* 147 */       this.decrypter.init(this.header, this.associatedData);
/* 148 */       this.headerRead = true;
/* 149 */     } catch (GeneralSecurityException ex) {
/*     */       
/* 151 */       setUndefinedState();
/* 152 */       throw new IOException(ex);
/*     */     } 
/* 154 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private void setUndefinedState() {
/* 159 */     this.definedState = false;
/* 160 */     this.plaintextSegment.limit(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean tryLoadSegment() throws IOException {
/* 168 */     if (!this.endOfCiphertext) {
/* 169 */       readSomeCiphertext(this.ciphertextSegment);
/*     */     }
/* 171 */     if (this.ciphertextSegment.remaining() > 0 && !this.endOfCiphertext)
/*     */     {
/* 173 */       return false;
/*     */     }
/* 175 */     byte lastByte = 0;
/* 176 */     if (!this.endOfCiphertext) {
/* 177 */       lastByte = this.ciphertextSegment.get(this.ciphertextSegment.position() - 1);
/* 178 */       this.ciphertextSegment.position(this.ciphertextSegment.position() - 1);
/*     */     } 
/* 180 */     this.ciphertextSegment.flip();
/* 181 */     this.plaintextSegment.clear();
/*     */     try {
/* 183 */       this.decrypter.decryptSegment(this.ciphertextSegment, this.segmentNr, this.endOfCiphertext, this.plaintextSegment);
/*     */     }
/* 185 */     catch (GeneralSecurityException ex) {
/*     */ 
/*     */       
/* 188 */       setUndefinedState();
/* 189 */       throw new IOException(ex.getMessage() + "\n" + toString() + "\nsegmentNr:" + this.segmentNr + " endOfCiphertext:" + this.endOfCiphertext, ex);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 194 */     this.segmentNr++;
/* 195 */     this.plaintextSegment.flip();
/* 196 */     this.ciphertextSegment.clear();
/* 197 */     if (!this.endOfCiphertext) {
/* 198 */       this.ciphertextSegment.clear();
/* 199 */       this.ciphertextSegment.limit(this.ciphertextSegmentSize + 1);
/* 200 */       this.ciphertextSegment.put(lastByte);
/*     */     } 
/* 202 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized int read(ByteBuffer dst) throws IOException {
/* 207 */     if (!this.definedState) {
/* 208 */       throw new IOException("This StreamingAeadDecryptingChannel is in an undefined state");
/*     */     }
/* 210 */     if (!this.headerRead) {
/* 211 */       if (!tryReadHeader()) {
/* 212 */         return 0;
/*     */       }
/* 214 */       this.ciphertextSegment.clear();
/* 215 */       this.ciphertextSegment.limit(this.firstCiphertextSegmentSize + 1);
/*     */     } 
/* 217 */     if (this.endOfPlaintext) {
/* 218 */       return -1;
/*     */     }
/* 220 */     int startPosition = dst.position();
/* 221 */     while (dst.remaining() > 0) {
/* 222 */       if (this.plaintextSegment.remaining() == 0) {
/* 223 */         if (this.endOfCiphertext) {
/* 224 */           this.endOfPlaintext = true;
/*     */           break;
/*     */         } 
/* 227 */         if (!tryLoadSegment()) {
/*     */           break;
/*     */         }
/*     */       } 
/* 231 */       if (this.plaintextSegment.remaining() <= dst.remaining()) {
/* 232 */         dst.put(this.plaintextSegment); continue;
/*     */       } 
/* 234 */       int sliceSize = dst.remaining();
/* 235 */       ByteBuffer slice = this.plaintextSegment.duplicate();
/* 236 */       slice.limit(slice.position() + sliceSize);
/* 237 */       dst.put(slice);
/* 238 */       this.plaintextSegment.position(this.plaintextSegment.position() + sliceSize);
/*     */     } 
/*     */     
/* 241 */     int bytesRead = dst.position() - startPosition;
/* 242 */     if (bytesRead == 0 && this.endOfPlaintext) {
/* 243 */       return -1;
/*     */     }
/* 245 */     return bytesRead;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void close() throws IOException {
/* 251 */     this.ciphertextChannel.close();
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized boolean isOpen() {
/* 256 */     return this.ciphertextChannel.isOpen();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized String toString() {
/* 263 */     StringBuilder res = new StringBuilder();
/*     */     
/* 265 */     res.append("StreamingAeadDecryptingChannel")
/* 266 */       .append("\nsegmentNr:").append(this.segmentNr)
/* 267 */       .append("\nciphertextSegmentSize:").append(this.ciphertextSegmentSize)
/* 268 */       .append("\nheaderRead:").append(this.headerRead)
/* 269 */       .append("\nendOfCiphertext:").append(this.endOfCiphertext)
/* 270 */       .append("\nendOfPlaintext:").append(this.endOfPlaintext)
/* 271 */       .append("\ndefinedState:").append(this.definedState)
/* 272 */       .append("\nHeader")
/* 273 */       .append(" position:").append(this.header.position())
/* 274 */       .append(" limit:").append(this.header.position())
/* 275 */       .append("\nciphertextSgement")
/* 276 */       .append(" position:").append(this.ciphertextSegment.position())
/* 277 */       .append(" limit:").append(this.ciphertextSegment.limit())
/* 278 */       .append("\nplaintextSegment")
/* 279 */       .append(" position:").append(this.plaintextSegment.position())
/* 280 */       .append(" limit:").append(this.plaintextSegment.limit());
/* 281 */     return res.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\subtle\StreamingAeadDecryptingChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */