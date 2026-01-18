/*     */ package com.google.crypto.tink.subtle;
/*     */ 
/*     */ import java.io.FilterInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.Buffer;
/*     */ import java.nio.ByteBuffer;
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
/*     */ class StreamingAeadDecryptingStream
/*     */   extends FilterInputStream
/*     */ {
/*     */   private static final int PLAINTEXT_SEGMENT_EXTRA_SIZE = 16;
/*     */   private final ByteBuffer ciphertextSegment;
/*     */   private final ByteBuffer plaintextSegment;
/*     */   private final int headerLength;
/*     */   private boolean headerRead;
/*     */   private boolean endOfCiphertext;
/*     */   private boolean endOfPlaintext;
/*     */   private boolean decryptionErrorOccured;
/*     */   private final byte[] aad;
/*     */   private int segmentNr;
/*     */   private final StreamSegmentDecrypter decrypter;
/*     */   private final int ciphertextSegmentSize;
/*     */   private final int firstCiphertextSegmentSize;
/*     */   
/*     */   private static Buffer toBuffer(ByteBuffer b) {
/*  88 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public StreamingAeadDecryptingStream(NonceBasedStreamingAead streamAead, InputStream ciphertextStream, byte[] associatedData) throws GeneralSecurityException, IOException {
/*  94 */     super(ciphertextStream);
/*  95 */     this.decrypter = streamAead.newStreamSegmentDecrypter();
/*  96 */     this.headerLength = streamAead.getHeaderLength();
/*  97 */     this.aad = Arrays.copyOf(associatedData, associatedData.length);
/*     */ 
/*     */ 
/*     */     
/* 101 */     this.ciphertextSegmentSize = streamAead.getCiphertextSegmentSize();
/* 102 */     this.ciphertextSegment = ByteBuffer.allocate(this.ciphertextSegmentSize + 1);
/* 103 */     toBuffer(this.ciphertextSegment).limit(0);
/* 104 */     this.firstCiphertextSegmentSize = this.ciphertextSegmentSize - streamAead.getCiphertextOffset();
/* 105 */     this.plaintextSegment = ByteBuffer.allocate(streamAead.getPlaintextSegmentSize() + 16);
/*     */     
/* 107 */     toBuffer(this.plaintextSegment).limit(0);
/* 108 */     this.headerRead = false;
/* 109 */     this.endOfCiphertext = false;
/* 110 */     this.endOfPlaintext = false;
/* 111 */     this.segmentNr = 0;
/* 112 */     this.decryptionErrorOccured = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readHeader() throws IOException {
/* 122 */     if (this.headerRead) {
/* 123 */       setDecryptionErrorOccured();
/* 124 */       throw new IOException("Decryption failed.");
/*     */     } 
/* 126 */     ByteBuffer header = ByteBuffer.allocate(this.headerLength);
/* 127 */     while (header.remaining() > 0) {
/* 128 */       int read = this.in.read(header.array(), toBuffer(header).position(), header.remaining());
/* 129 */       if (read < 0) {
/* 130 */         setDecryptionErrorOccured();
/* 131 */         throw new IOException("Ciphertext is too short");
/*     */       } 
/* 133 */       if (read == 0) {
/* 134 */         throw new IOException("Could not read bytes from the ciphertext stream");
/*     */       }
/* 136 */       toBuffer(header).position(toBuffer(header).position() + read);
/*     */     } 
/* 138 */     toBuffer(header).flip();
/*     */     try {
/* 140 */       this.decrypter.init(header, this.aad);
/* 141 */     } catch (GeneralSecurityException ex) {
/* 142 */       throw new IOException(ex);
/*     */     } 
/* 144 */     this.headerRead = true;
/*     */   }
/*     */   
/*     */   private void setDecryptionErrorOccured() {
/* 148 */     this.decryptionErrorOccured = true;
/* 149 */     toBuffer(this.plaintextSegment).limit(0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadSegment() throws IOException {
/* 155 */     while (!this.endOfCiphertext && this.ciphertextSegment.remaining() > 0) {
/*     */       
/* 157 */       int read = this.in.read(this.ciphertextSegment
/* 158 */           .array(), this.ciphertextSegment
/* 159 */           .position(), this.ciphertextSegment
/* 160 */           .remaining());
/* 161 */       if (read > 0) {
/* 162 */         toBuffer(this.ciphertextSegment).position(toBuffer(this.ciphertextSegment).position() + read); continue;
/* 163 */       }  if (read == -1) {
/* 164 */         this.endOfCiphertext = true; continue;
/* 165 */       }  if (read == 0)
/*     */       {
/* 167 */         throw new IOException("Could not read bytes from the ciphertext stream");
/*     */       }
/* 169 */       throw new IOException("Unexpected return value from in.read");
/*     */     } 
/*     */     
/* 172 */     byte lastByte = 0;
/* 173 */     if (!this.endOfCiphertext) {
/* 174 */       lastByte = this.ciphertextSegment.get(toBuffer(this.ciphertextSegment).position() - 1);
/* 175 */       toBuffer(this.ciphertextSegment).position(toBuffer(this.ciphertextSegment).position() - 1);
/*     */     } 
/* 177 */     toBuffer(this.ciphertextSegment).flip();
/* 178 */     toBuffer(this.plaintextSegment).clear();
/*     */     try {
/* 180 */       this.decrypter.decryptSegment(this.ciphertextSegment, this.segmentNr, this.endOfCiphertext, this.plaintextSegment);
/* 181 */     } catch (GeneralSecurityException ex) {
/*     */ 
/*     */       
/* 184 */       setDecryptionErrorOccured();
/* 185 */       throw new IOException(ex
/* 186 */           .getMessage() + "\n" + 
/*     */           
/* 188 */           toString() + "\nsegmentNr:" + this.segmentNr + " endOfCiphertext:" + this.endOfCiphertext, ex);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 195 */     this.segmentNr++;
/* 196 */     toBuffer(this.plaintextSegment).flip();
/* 197 */     toBuffer(this.ciphertextSegment).clear();
/* 198 */     if (!this.endOfCiphertext) {
/* 199 */       toBuffer(this.ciphertextSegment).clear();
/* 200 */       toBuffer(this.ciphertextSegment).limit(this.ciphertextSegmentSize + 1);
/* 201 */       this.ciphertextSegment.put(lastByte);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int read() throws IOException {
/* 207 */     byte[] oneByte = new byte[1];
/* 208 */     int ret = read(oneByte, 0, 1);
/* 209 */     if (ret == 1)
/* 210 */       return oneByte[0] & 0xFF; 
/* 211 */     if (ret == -1) {
/* 212 */       return ret;
/*     */     }
/* 214 */     throw new IOException("Reading failed");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int read(byte[] dst) throws IOException {
/* 220 */     return read(dst, 0, dst.length);
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized int read(byte[] dst, int offset, int length) throws IOException {
/* 225 */     if (this.decryptionErrorOccured) {
/* 226 */       throw new IOException("Decryption failed.");
/*     */     }
/* 228 */     if (!this.headerRead) {
/* 229 */       readHeader();
/* 230 */       toBuffer(this.ciphertextSegment).clear();
/* 231 */       toBuffer(this.ciphertextSegment).limit(this.firstCiphertextSegmentSize + 1);
/*     */     } 
/* 233 */     if (this.endOfPlaintext) {
/* 234 */       return -1;
/*     */     }
/* 236 */     int bytesRead = 0;
/* 237 */     while (bytesRead < length) {
/* 238 */       if (this.plaintextSegment.remaining() == 0) {
/* 239 */         if (this.endOfCiphertext) {
/* 240 */           this.endOfPlaintext = true;
/*     */           break;
/*     */         } 
/* 243 */         loadSegment();
/*     */       } 
/* 245 */       int sliceSize = Math.min(this.plaintextSegment.remaining(), length - bytesRead);
/* 246 */       this.plaintextSegment.get(dst, bytesRead + offset, sliceSize);
/* 247 */       bytesRead += sliceSize;
/*     */     } 
/* 249 */     if (bytesRead == 0 && this.endOfPlaintext) {
/* 250 */       return -1;
/*     */     }
/* 252 */     return bytesRead;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void close() throws IOException {
/* 258 */     super.close();
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized int available() {
/* 263 */     return this.plaintextSegment.remaining();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void mark(int readlimit) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean markSupported() {
/* 273 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long skip(long n) throws IOException {
/* 292 */     long maxSkipBufferSize = this.ciphertextSegmentSize;
/* 293 */     long remaining = n;
/* 294 */     if (n <= 0L) {
/* 295 */       return 0L;
/*     */     }
/* 297 */     int size = (int)Math.min(maxSkipBufferSize, remaining);
/* 298 */     byte[] skipBuffer = new byte[size];
/* 299 */     while (remaining > 0L) {
/* 300 */       int bytesRead = read(skipBuffer, 0, (int)Math.min(size, remaining));
/* 301 */       if (bytesRead <= 0) {
/*     */         break;
/*     */       }
/* 304 */       remaining -= bytesRead;
/*     */     } 
/* 306 */     return n - remaining;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized String toString() {
/* 312 */     StringBuilder res = new StringBuilder();
/* 313 */     res.append("StreamingAeadDecryptingStream")
/* 314 */       .append("\nsegmentNr:")
/* 315 */       .append(this.segmentNr)
/* 316 */       .append("\nciphertextSegmentSize:")
/* 317 */       .append(this.ciphertextSegmentSize)
/* 318 */       .append("\nheaderRead:")
/* 319 */       .append(this.headerRead)
/* 320 */       .append("\nendOfCiphertext:")
/* 321 */       .append(this.endOfCiphertext)
/* 322 */       .append("\nendOfPlaintext:")
/* 323 */       .append(this.endOfPlaintext)
/* 324 */       .append("\ndecryptionErrorOccured:")
/* 325 */       .append(this.decryptionErrorOccured)
/* 326 */       .append("\nciphertextSgement")
/* 327 */       .append(" position:")
/* 328 */       .append(this.ciphertextSegment.position())
/* 329 */       .append(" limit:")
/* 330 */       .append(this.ciphertextSegment.limit())
/* 331 */       .append("\nplaintextSegment")
/* 332 */       .append(" position:")
/* 333 */       .append(this.plaintextSegment.position())
/* 334 */       .append(" limit:")
/* 335 */       .append(this.plaintextSegment.limit());
/* 336 */     return res.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\subtle\StreamingAeadDecryptingStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */