/*     */ package io.netty.handler.stream;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.io.InputStream;
/*     */ import java.io.PushbackInputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChunkedStream
/*     */   implements ChunkedInput<ByteBuf>
/*     */ {
/*     */   static final int DEFAULT_CHUNK_SIZE = 8192;
/*     */   private final PushbackInputStream in;
/*     */   private final int chunkSize;
/*     */   private long offset;
/*     */   private boolean closed;
/*     */   
/*     */   public ChunkedStream(InputStream in) {
/*  49 */     this(in, 8192);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChunkedStream(InputStream in, int chunkSize) {
/*  59 */     ObjectUtil.checkNotNull(in, "in");
/*  60 */     ObjectUtil.checkPositive(chunkSize, "chunkSize");
/*     */     
/*  62 */     if (in instanceof PushbackInputStream) {
/*  63 */       this.in = (PushbackInputStream)in;
/*     */     } else {
/*  65 */       this.in = new PushbackInputStream(in);
/*     */     } 
/*  67 */     this.chunkSize = chunkSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long transferredBytes() {
/*  74 */     return this.offset;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEndOfInput() throws Exception {
/*  79 */     if (this.closed) {
/*  80 */       return true;
/*     */     }
/*  82 */     if (this.in.available() > 0) {
/*  83 */       return false;
/*     */     }
/*     */     
/*  86 */     int b = this.in.read();
/*  87 */     if (b < 0) {
/*  88 */       return true;
/*     */     }
/*  90 */     this.in.unread(b);
/*  91 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws Exception {
/*  97 */     this.closed = true;
/*  98 */     this.in.close();
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public ByteBuf readChunk(ChannelHandlerContext ctx) throws Exception {
/* 104 */     return readChunk(ctx.alloc());
/*     */   }
/*     */   
/*     */   public ByteBuf readChunk(ByteBufAllocator allocator) throws Exception {
/*     */     int chunkSize;
/* 109 */     if (isEndOfInput()) {
/* 110 */       return null;
/*     */     }
/*     */     
/* 113 */     int availableBytes = this.in.available();
/*     */     
/* 115 */     if (availableBytes <= 0) {
/* 116 */       chunkSize = this.chunkSize;
/*     */     } else {
/* 118 */       chunkSize = Math.min(this.chunkSize, this.in.available());
/*     */     } 
/*     */     
/* 121 */     boolean release = true;
/* 122 */     ByteBuf buffer = allocator.buffer(chunkSize);
/*     */     
/*     */     try {
/* 125 */       int written = buffer.writeBytes(this.in, chunkSize);
/* 126 */       if (written < 0) {
/* 127 */         return null;
/*     */       }
/* 129 */       this.offset += written;
/* 130 */       release = false;
/* 131 */       return buffer;
/*     */     } finally {
/* 133 */       if (release) {
/* 134 */         buffer.release();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public long length() {
/* 141 */     return -1L;
/*     */   }
/*     */ 
/*     */   
/*     */   public long progress() {
/* 146 */     return this.offset;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\stream\ChunkedStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */