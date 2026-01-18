/*     */ package io.netty.handler.codec.spdy;
/*     */ 
/*     */ import com.jcraft.jzlib.Deflater;
/*     */ import com.jcraft.jzlib.JZlib;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.handler.codec.compression.CompressionException;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class SpdyHeaderBlockJZlibEncoder
/*     */   extends SpdyHeaderBlockRawEncoder
/*     */ {
/*  30 */   private final Deflater z = new Deflater();
/*     */   
/*     */   private boolean finished;
/*     */ 
/*     */   
/*     */   SpdyHeaderBlockJZlibEncoder(SpdyVersion version, int compressionLevel, int windowBits, int memLevel) {
/*  36 */     super(version);
/*  37 */     if (compressionLevel < 0 || compressionLevel > 9) {
/*  38 */       throw new IllegalArgumentException("compressionLevel: " + compressionLevel + " (expected: 0-9)");
/*     */     }
/*     */     
/*  41 */     if (windowBits < 9 || windowBits > 15) {
/*  42 */       throw new IllegalArgumentException("windowBits: " + windowBits + " (expected: 9-15)");
/*     */     }
/*     */     
/*  45 */     if (memLevel < 1 || memLevel > 9) {
/*  46 */       throw new IllegalArgumentException("memLevel: " + memLevel + " (expected: 1-9)");
/*     */     }
/*     */ 
/*     */     
/*  50 */     int resultCode = this.z.deflateInit(compressionLevel, windowBits, memLevel, JZlib.W_ZLIB);
/*     */     
/*  52 */     if (resultCode != 0) {
/*  53 */       throw new CompressionException("failed to initialize an SPDY header block deflater: " + resultCode);
/*     */     }
/*     */     
/*  56 */     resultCode = this.z.deflateSetDictionary(SpdyCodecUtil.SPDY_DICT, SpdyCodecUtil.SPDY_DICT.length);
/*  57 */     if (resultCode != 0) {
/*  58 */       throw new CompressionException("failed to set the SPDY dictionary: " + resultCode);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void setInput(ByteBuf decompressed) {
/*     */     byte[] in;
/*  65 */     int offset, len = decompressed.readableBytes();
/*     */ 
/*     */ 
/*     */     
/*  69 */     if (decompressed.hasArray()) {
/*  70 */       in = decompressed.array();
/*  71 */       offset = decompressed.arrayOffset() + decompressed.readerIndex();
/*     */     } else {
/*  73 */       in = new byte[len];
/*  74 */       decompressed.getBytes(decompressed.readerIndex(), in);
/*  75 */       offset = 0;
/*     */     } 
/*  77 */     this.z.next_in = in;
/*  78 */     this.z.next_in_index = offset;
/*  79 */     this.z.avail_in = len;
/*     */   }
/*     */   
/*     */   private ByteBuf encode(ByteBufAllocator alloc) {
/*  83 */     boolean release = true;
/*  84 */     ByteBuf out = null;
/*     */     try {
/*  86 */       int resultCode, oldNextInIndex = this.z.next_in_index;
/*  87 */       int oldNextOutIndex = this.z.next_out_index;
/*     */       
/*  89 */       int maxOutputLength = (int)Math.ceil(this.z.next_in.length * 1.001D) + 12;
/*  90 */       out = alloc.heapBuffer(maxOutputLength);
/*  91 */       this.z.next_out = out.array();
/*  92 */       this.z.next_out_index = out.arrayOffset() + out.writerIndex();
/*  93 */       this.z.avail_out = maxOutputLength;
/*     */ 
/*     */       
/*     */       try {
/*  97 */         resultCode = this.z.deflate(2);
/*     */       } finally {
/*  99 */         out.skipBytes(this.z.next_in_index - oldNextInIndex);
/*     */       } 
/* 101 */       if (resultCode != 0) {
/* 102 */         throw new CompressionException("compression failure: " + resultCode);
/*     */       }
/*     */       
/* 105 */       int outputLength = this.z.next_out_index - oldNextOutIndex;
/* 106 */       if (outputLength > 0) {
/* 107 */         out.writerIndex(out.writerIndex() + outputLength);
/*     */       }
/* 109 */       release = false;
/* 110 */       return out;
/*     */     
/*     */     }
/*     */     finally {
/*     */ 
/*     */       
/* 116 */       this.z.next_in = null;
/* 117 */       this.z.next_out = null;
/* 118 */       if (release && out != null) {
/* 119 */         out.release();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf encode(ByteBufAllocator alloc, SpdyHeadersFrame frame) throws Exception {
/* 126 */     ObjectUtil.checkNotNullWithIAE(alloc, "alloc");
/* 127 */     ObjectUtil.checkNotNullWithIAE(frame, "frame");
/*     */     
/* 129 */     if (this.finished) {
/* 130 */       return Unpooled.EMPTY_BUFFER;
/*     */     }
/*     */     
/* 133 */     ByteBuf decompressed = super.encode(alloc, frame);
/*     */     try {
/* 135 */       if (!decompressed.isReadable()) {
/* 136 */         return Unpooled.EMPTY_BUFFER;
/*     */       }
/*     */       
/* 139 */       setInput(decompressed);
/* 140 */       return encode(alloc);
/*     */     } finally {
/* 142 */       decompressed.release();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void end() {
/* 148 */     if (this.finished) {
/*     */       return;
/*     */     }
/* 151 */     this.finished = true;
/* 152 */     this.z.deflateEnd();
/* 153 */     this.z.next_in = null;
/* 154 */     this.z.next_out = null;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\spdy\SpdyHeaderBlockJZlibEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */