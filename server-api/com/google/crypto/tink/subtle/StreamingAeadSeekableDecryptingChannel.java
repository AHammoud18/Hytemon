/*     */ package com.google.crypto.tink.subtle;
/*     */ 
/*     */ import com.google.errorprone.annotations.CanIgnoreReturnValue;
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.ClosedChannelException;
/*     */ import java.nio.channels.NonWritableChannelException;
/*     */ import java.nio.channels.SeekableByteChannel;
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
/*     */ class StreamingAeadSeekableDecryptingChannel
/*     */   implements SeekableByteChannel
/*     */ {
/*     */   private static final int PLAINTEXT_SEGMENT_EXTRA_SIZE = 16;
/*     */   private final SeekableByteChannel ciphertextChannel;
/*     */   private final ByteBuffer ciphertextSegment;
/*     */   private final ByteBuffer plaintextSegment;
/*     */   private final ByteBuffer header;
/*     */   private final long ciphertextChannelSize;
/*     */   private final int numberOfSegments;
/*     */   private final int lastCiphertextSegmentSize;
/*     */   private final byte[] aad;
/*     */   private final StreamSegmentDecrypter decrypter;
/*     */   private long plaintextPosition;
/*     */   private long plaintextSize;
/*     */   private boolean headerRead;
/*     */   private boolean isCurrentSegmentDecrypted;
/*     */   private int currentSegmentNr;
/*     */   private boolean isopen;
/*     */   private final int plaintextSegmentSize;
/*     */   private final int ciphertextSegmentSize;
/*     */   private final int ciphertextOffset;
/*     */   private final int firstSegmentOffset;
/*     */   
/*     */   public StreamingAeadSeekableDecryptingChannel(NonceBasedStreamingAead streamAead, SeekableByteChannel ciphertext, byte[] associatedData) throws IOException, GeneralSecurityException {
/*  66 */     this.decrypter = streamAead.newStreamSegmentDecrypter();
/*  67 */     this.ciphertextChannel = ciphertext;
/*  68 */     this.header = ByteBuffer.allocate(streamAead.getHeaderLength());
/*  69 */     this.ciphertextSegmentSize = streamAead.getCiphertextSegmentSize();
/*  70 */     this.ciphertextSegment = ByteBuffer.allocate(this.ciphertextSegmentSize);
/*  71 */     this.plaintextSegmentSize = streamAead.getPlaintextSegmentSize();
/*  72 */     this.plaintextSegment = ByteBuffer.allocate(this.plaintextSegmentSize + 16);
/*  73 */     this.plaintextPosition = 0L;
/*  74 */     this.headerRead = false;
/*  75 */     this.currentSegmentNr = -1;
/*  76 */     this.isCurrentSegmentDecrypted = false;
/*  77 */     this.ciphertextChannelSize = this.ciphertextChannel.size();
/*  78 */     this.aad = Arrays.copyOf(associatedData, associatedData.length);
/*  79 */     this.isopen = this.ciphertextChannel.isOpen();
/*  80 */     int fullSegments = (int)(this.ciphertextChannelSize / this.ciphertextSegmentSize);
/*  81 */     int remainder = (int)(this.ciphertextChannelSize % this.ciphertextSegmentSize);
/*  82 */     int ciphertextOverhead = streamAead.getCiphertextOverhead();
/*  83 */     if (remainder > 0) {
/*  84 */       this.numberOfSegments = fullSegments + 1;
/*  85 */       if (remainder < ciphertextOverhead) {
/*  86 */         throw new IOException("Invalid ciphertext size");
/*     */       }
/*  88 */       this.lastCiphertextSegmentSize = remainder;
/*     */     } else {
/*  90 */       this.numberOfSegments = fullSegments;
/*  91 */       this.lastCiphertextSegmentSize = this.ciphertextSegmentSize;
/*     */     } 
/*  93 */     this.ciphertextOffset = streamAead.getCiphertextOffset();
/*  94 */     this.firstSegmentOffset = this.ciphertextOffset - streamAead.getHeaderLength();
/*  95 */     if (this.firstSegmentOffset < 0) {
/*  96 */       throw new IOException("Invalid ciphertext offset or header length");
/*     */     }
/*  98 */     long overhead = this.numberOfSegments * ciphertextOverhead + this.ciphertextOffset;
/*  99 */     if (overhead > this.ciphertextChannelSize) {
/* 100 */       throw new IOException("Ciphertext is too short");
/*     */     }
/* 102 */     this.plaintextSize = this.ciphertextChannelSize - overhead;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized String toString() {
/*     */     String ctChannel;
/* 112 */     StringBuilder res = new StringBuilder();
/*     */ 
/*     */     
/*     */     try {
/* 116 */       ctChannel = "position:" + this.ciphertextChannel.position();
/* 117 */     } catch (IOException ex) {
/* 118 */       ctChannel = "position: n/a";
/*     */     } 
/* 120 */     res.append("StreamingAeadSeekableDecryptingChannel")
/* 121 */       .append("\nciphertextChannel").append(ctChannel)
/* 122 */       .append("\nciphertextChannelSize:").append(this.ciphertextChannelSize)
/* 123 */       .append("\nplaintextSize:").append(this.plaintextSize)
/* 124 */       .append("\nciphertextSegmentSize:").append(this.ciphertextSegmentSize)
/* 125 */       .append("\nnumberOfSegments:").append(this.numberOfSegments)
/* 126 */       .append("\nheaderRead:").append(this.headerRead)
/* 127 */       .append("\nplaintextPosition:").append(this.plaintextPosition)
/* 128 */       .append("\nHeader")
/* 129 */       .append(" position:").append(this.header.position())
/* 130 */       .append(" limit:").append(this.header.limit())
/* 131 */       .append("\ncurrentSegmentNr:").append(this.currentSegmentNr)
/* 132 */       .append("\nciphertextSgement")
/* 133 */       .append(" position:").append(this.ciphertextSegment.position())
/* 134 */       .append(" limit:").append(this.ciphertextSegment.limit())
/* 135 */       .append("\nisCurrentSegmentDecrypted:").append(this.isCurrentSegmentDecrypted)
/* 136 */       .append("\nplaintextSegment")
/* 137 */       .append(" position:").append(this.plaintextSegment.position())
/* 138 */       .append(" limit:").append(this.plaintextSegment.limit());
/* 139 */     return res.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized long position() {
/* 148 */     return this.plaintextPosition;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @CanIgnoreReturnValue
/*     */   public synchronized SeekableByteChannel position(long newPosition) {
/* 158 */     this.plaintextPosition = newPosition;
/* 159 */     return this;
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
/*     */   private boolean tryReadHeader() throws IOException {
/* 172 */     this.ciphertextChannel.position((this.header.position() + this.firstSegmentOffset));
/* 173 */     int headerSize = this.header.remaining();
/* 174 */     int numBytesRead = this.ciphertextChannel.read(this.header);
/* 175 */     if (numBytesRead == -1) {
/* 176 */       throw new IOException("Unexpected end-of-stream: ciphertextChannel.read(header) returned -1");
/*     */     }
/*     */     
/* 179 */     if (numBytesRead != headerSize - this.header.remaining()) {
/* 180 */       throw new IOException("Unexpected return value from ciphertextChannel.read(header). headerSize = " + headerSize + ", header.remaining() = " + this.header
/*     */ 
/*     */ 
/*     */           
/* 184 */           .remaining() + ", but read returned " + numBytesRead);
/*     */     }
/*     */ 
/*     */     
/* 188 */     if (this.header.remaining() > 0) {
/* 189 */       return false;
/*     */     }
/* 191 */     this.header.flip();
/*     */     try {
/* 193 */       this.decrypter.init(this.header, this.aad);
/* 194 */       this.headerRead = true;
/* 195 */     } catch (GeneralSecurityException ex) {
/*     */       
/* 197 */       throw new IOException(ex);
/*     */     } 
/* 199 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private int getSegmentNr(long plaintextPosition) {
/* 204 */     return (int)((plaintextPosition + this.ciphertextOffset) / this.plaintextSegmentSize);
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
/*     */   private boolean tryLoadSegment(int segmentNr) throws IOException {
/* 217 */     if (segmentNr < 0 || segmentNr >= this.numberOfSegments) {
/* 218 */       throw new IOException("Invalid position");
/*     */     }
/* 220 */     boolean isLast = (segmentNr == this.numberOfSegments - 1);
/* 221 */     if (segmentNr == this.currentSegmentNr) {
/* 222 */       if (this.isCurrentSegmentDecrypted) {
/* 223 */         return true;
/*     */       }
/*     */     } else {
/*     */       
/* 227 */       long ciphertextPosition = segmentNr * this.ciphertextSegmentSize;
/* 228 */       int segmentSize = this.ciphertextSegmentSize;
/* 229 */       if (isLast) {
/* 230 */         segmentSize = this.lastCiphertextSegmentSize;
/*     */       }
/* 232 */       if (segmentNr == 0) {
/* 233 */         segmentSize -= this.ciphertextOffset;
/* 234 */         ciphertextPosition = this.ciphertextOffset;
/*     */       } 
/* 236 */       this.ciphertextChannel.position(ciphertextPosition);
/* 237 */       this.ciphertextSegment.clear();
/* 238 */       this.ciphertextSegment.limit(segmentSize);
/* 239 */       this.currentSegmentNr = segmentNr;
/* 240 */       this.isCurrentSegmentDecrypted = false;
/*     */     } 
/* 242 */     if (this.ciphertextSegment.remaining() > 0) {
/* 243 */       int remainingDataBeforeRead = this.ciphertextSegment.remaining();
/* 244 */       int numBytesRead = this.ciphertextChannel.read(this.ciphertextSegment);
/* 245 */       if (numBytesRead == -1) {
/* 246 */         throw new IOException("Unexpected end-of-stream: ciphertextChannel.read(ciphertextSegment) returned -1");
/*     */       }
/*     */       
/* 249 */       if (numBytesRead != remainingDataBeforeRead - this.ciphertextSegment.remaining()) {
/* 250 */         throw new IOException("Unexpected return value from ciphertextChannel.read(ciphertextSegment). remainingDataBeforeRead = " + remainingDataBeforeRead + ", ciphertextSegment.remaining() = " + this.ciphertextSegment
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 255 */             .remaining() + ", but read returned " + numBytesRead);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 260 */     if (this.ciphertextSegment.remaining() > 0) {
/* 261 */       return false;
/*     */     }
/* 263 */     this.ciphertextSegment.flip();
/* 264 */     this.plaintextSegment.clear();
/*     */     try {
/* 266 */       this.decrypter.decryptSegment(this.ciphertextSegment, segmentNr, isLast, this.plaintextSegment);
/* 267 */     } catch (GeneralSecurityException ex) {
/*     */ 
/*     */       
/* 270 */       this.currentSegmentNr = -1;
/* 271 */       throw new IOException("Failed to decrypt", ex);
/*     */     } 
/* 273 */     this.plaintextSegment.flip();
/* 274 */     this.isCurrentSegmentDecrypted = true;
/* 275 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean reachedEnd() {
/* 283 */     return (this.plaintextPosition == this.plaintextSize && this.isCurrentSegmentDecrypted && this.currentSegmentNr == this.numberOfSegments - 1 && this.plaintextSegment
/*     */ 
/*     */       
/* 286 */       .remaining() == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized int read(ByteBuffer dst, long start) throws IOException {
/* 296 */     long oldPosition = position();
/*     */     try {
/* 298 */       position(start);
/* 299 */       return read(dst);
/*     */     } finally {
/* 301 */       position(oldPosition);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized int read(ByteBuffer dst) throws IOException {
/* 307 */     if (!this.isopen) {
/* 308 */       throw new ClosedChannelException();
/*     */     }
/* 310 */     if (!this.headerRead && 
/* 311 */       !tryReadHeader()) {
/* 312 */       return 0;
/*     */     }
/*     */     
/* 315 */     int startPos = dst.position();
/* 316 */     while (dst.remaining() > 0 && this.plaintextPosition < this.plaintextSize) {
/*     */ 
/*     */       
/* 319 */       int segmentOffset, segmentNr = getSegmentNr(this.plaintextPosition);
/*     */       
/* 321 */       if (segmentNr == 0) {
/* 322 */         segmentOffset = (int)this.plaintextPosition;
/*     */       } else {
/* 324 */         segmentOffset = (int)((this.plaintextPosition + this.ciphertextOffset) % this.plaintextSegmentSize);
/*     */       } 
/*     */       
/* 327 */       if (tryLoadSegment(segmentNr)) {
/* 328 */         this.plaintextSegment.position(segmentOffset);
/* 329 */         if (this.plaintextSegment.remaining() <= dst.remaining()) {
/* 330 */           this.plaintextPosition += this.plaintextSegment.remaining();
/* 331 */           dst.put(this.plaintextSegment); continue;
/*     */         } 
/* 333 */         int sliceSize = dst.remaining();
/* 334 */         ByteBuffer slice = this.plaintextSegment.duplicate();
/* 335 */         slice.limit(slice.position() + sliceSize);
/* 336 */         dst.put(slice);
/* 337 */         this.plaintextPosition += sliceSize;
/* 338 */         this.plaintextSegment.position(this.plaintextSegment.position() + sliceSize);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 344 */     int read = dst.position() - startPos;
/* 345 */     if (read == 0 && reachedEnd()) {
/* 346 */       return -1;
/*     */     }
/* 348 */     return read;
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
/*     */   public long size() {
/* 360 */     return this.plaintextSize;
/*     */   }
/*     */   
/*     */   public synchronized long verifiedSize() throws IOException {
/* 364 */     if (tryLoadSegment(this.numberOfSegments - 1)) {
/* 365 */       return this.plaintextSize;
/*     */     }
/* 367 */     throw new IOException("could not verify the size");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SeekableByteChannel truncate(long size) throws NonWritableChannelException {
/* 373 */     throw new NonWritableChannelException();
/*     */   }
/*     */ 
/*     */   
/*     */   public int write(ByteBuffer src) throws NonWritableChannelException {
/* 378 */     throw new NonWritableChannelException();
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void close() throws IOException {
/* 383 */     this.ciphertextChannel.close();
/* 384 */     this.isopen = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized boolean isOpen() {
/* 389 */     return this.isopen;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\subtle\StreamingAeadSeekableDecryptingChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */