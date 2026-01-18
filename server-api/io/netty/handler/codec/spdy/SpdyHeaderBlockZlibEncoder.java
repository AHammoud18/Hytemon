/*     */ package io.netty.handler.codec.spdy;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.util.zip.Deflater;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class SpdyHeaderBlockZlibEncoder
/*     */   extends SpdyHeaderBlockRawEncoder
/*     */ {
/*     */   private final Deflater compressor;
/*     */   private boolean finished;
/*     */   
/*     */   SpdyHeaderBlockZlibEncoder(SpdyVersion spdyVersion, int compressionLevel) {
/*  34 */     super(spdyVersion);
/*  35 */     if (compressionLevel < 0 || compressionLevel > 9) {
/*  36 */       throw new IllegalArgumentException("compressionLevel: " + compressionLevel + " (expected: 0-9)");
/*     */     }
/*     */     
/*  39 */     this.compressor = new Deflater(compressionLevel);
/*  40 */     this.compressor.setDictionary(SpdyCodecUtil.SPDY_DICT);
/*     */   }
/*     */   
/*     */   private int setInput(ByteBuf decompressed) {
/*  44 */     int len = decompressed.readableBytes();
/*     */     
/*  46 */     if (decompressed.hasArray()) {
/*  47 */       this.compressor.setInput(decompressed.array(), decompressed.arrayOffset() + decompressed.readerIndex(), len);
/*     */     } else {
/*  49 */       byte[] in = new byte[len];
/*  50 */       decompressed.getBytes(decompressed.readerIndex(), in);
/*  51 */       this.compressor.setInput(in, 0, in.length);
/*     */     } 
/*     */     
/*  54 */     return len;
/*     */   }
/*     */   
/*     */   private ByteBuf encode(ByteBufAllocator alloc, int len) {
/*  58 */     ByteBuf compressed = alloc.heapBuffer(len);
/*  59 */     boolean release = true;
/*     */     try {
/*  61 */       while (compressInto(compressed))
/*     */       {
/*  63 */         compressed.ensureWritable(compressed.capacity() << 1);
/*     */       }
/*  65 */       release = false;
/*  66 */       return compressed;
/*     */     } finally {
/*  68 */       if (release) {
/*  69 */         compressed.release();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean compressInto(ByteBuf compressed) {
/*  75 */     byte[] out = compressed.array();
/*  76 */     int off = compressed.arrayOffset() + compressed.writerIndex();
/*  77 */     int toWrite = compressed.writableBytes();
/*  78 */     int numBytes = this.compressor.deflate(out, off, toWrite, 2);
/*  79 */     compressed.writerIndex(compressed.writerIndex() + numBytes);
/*  80 */     return (numBytes == toWrite);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf encode(ByteBufAllocator alloc, SpdyHeadersFrame frame) throws Exception {
/*  85 */     ObjectUtil.checkNotNullWithIAE(alloc, "alloc");
/*  86 */     ObjectUtil.checkNotNullWithIAE(frame, "frame");
/*     */     
/*  88 */     if (this.finished) {
/*  89 */       return Unpooled.EMPTY_BUFFER;
/*     */     }
/*     */     
/*  92 */     ByteBuf decompressed = super.encode(alloc, frame);
/*     */     try {
/*  94 */       if (!decompressed.isReadable()) {
/*  95 */         return Unpooled.EMPTY_BUFFER;
/*     */       }
/*     */       
/*  98 */       int len = setInput(decompressed);
/*  99 */       return encode(alloc, len);
/*     */     } finally {
/* 101 */       decompressed.release();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void end() {
/* 107 */     if (this.finished) {
/*     */       return;
/*     */     }
/* 110 */     this.finished = true;
/* 111 */     this.compressor.end();
/* 112 */     super.end();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\spdy\SpdyHeaderBlockZlibEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */