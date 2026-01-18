/*     */ package io.netty.handler.codec.compression;
/*     */ 
/*     */ import com.github.luben.zstd.Zstd;
/*     */ import com.github.luben.zstd.ZstdInputStreamNoFinalizer;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.handler.codec.ByteToMessageDecoder;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.io.Closeable;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ZstdDecoder
/*     */   extends ByteToMessageDecoder
/*     */ {
/*     */   private final int maximumAllocationSize;
/*     */   private final MutableByteBufInputStream inputStream;
/*     */   private ZstdInputStreamNoFinalizer zstdIs;
/*     */   private boolean needsRead;
/*     */   private State currentState;
/*     */   
/*     */   private enum State
/*     */   {
/*  55 */     DECOMPRESS_DATA,
/*  56 */     CORRUPTED;
/*     */   }
/*     */   
/*     */   public ZstdDecoder() {
/*  60 */     this(4194304); } public ZstdDecoder(int maximumAllocationSize) { try {
/*     */       Zstd.ensureAvailability();
/*     */     } catch (Throwable throwable) {
/*     */       throw new ExceptionInInitializerError(throwable);
/*  64 */     }  this.inputStream = new MutableByteBufInputStream(); this.currentState = State.DECOMPRESS_DATA; this.maximumAllocationSize = ObjectUtil.checkPositiveOrZero(maximumAllocationSize, "maximumAllocationSize"); }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
/*  69 */     this.needsRead = true;
/*     */     try {
/*  71 */       if (this.currentState == State.CORRUPTED) {
/*  72 */         in.skipBytes(in.readableBytes());
/*     */         
/*     */         return;
/*     */       } 
/*  76 */       this.inputStream.current = in;
/*     */       
/*  78 */       ByteBuf outBuffer = null;
/*     */       
/*  80 */       int compressedLength = in.readableBytes(); try {
/*     */         long uncompressedLength;
/*     */         int w;
/*  83 */         if (in.isDirect()) {
/*  84 */           uncompressedLength = Zstd.getFrameContentSize(
/*  85 */               CompressionUtil.safeNioBuffer(in, in.readerIndex(), in.readableBytes()));
/*     */         } else {
/*  87 */           uncompressedLength = Zstd.getFrameContentSize(in
/*  88 */               .array(), in.readerIndex() + in.arrayOffset(), in.readableBytes());
/*     */         } 
/*  90 */         if (uncompressedLength <= 0L)
/*     */         {
/*     */           
/*  93 */           uncompressedLength = compressedLength * 2L;
/*     */         }
/*     */ 
/*     */         
/*     */         do {
/*  98 */           if (outBuffer == null) {
/*  99 */             outBuffer = ctx.alloc().heapBuffer(
/* 100 */                 (int)((this.maximumAllocationSize == 0) ? uncompressedLength : Math.min(this.maximumAllocationSize, uncompressedLength)));
/*     */           }
/*     */           do {
/* 103 */             w = outBuffer.writeBytes((InputStream)this.zstdIs, outBuffer.writableBytes());
/* 104 */           } while (w != -1 && outBuffer.isWritable());
/* 105 */           if (!outBuffer.isReadable())
/* 106 */             continue;  this.needsRead = false;
/* 107 */           ctx.fireChannelRead(outBuffer);
/* 108 */           outBuffer = null;
/*     */         }
/* 110 */         while (w != -1);
/*     */       } finally {
/* 112 */         if (outBuffer != null) {
/* 113 */           outBuffer.release();
/*     */         }
/*     */       } 
/* 116 */     } catch (Exception e) {
/* 117 */       this.currentState = State.CORRUPTED;
/* 118 */       throw new DecompressionException(e);
/*     */     } finally {
/* 120 */       this.inputStream.current = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
/* 127 */     discardSomeReadBytes();
/*     */     
/* 129 */     if (this.needsRead && !ctx.channel().config().isAutoRead()) {
/* 130 */       ctx.read();
/*     */     }
/* 132 */     ctx.fireChannelReadComplete();
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
/* 137 */     super.handlerAdded(ctx);
/* 138 */     this.zstdIs = new ZstdInputStreamNoFinalizer(this.inputStream);
/* 139 */     this.zstdIs.setContinuous(true);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void handlerRemoved0(ChannelHandlerContext ctx) throws Exception {
/*     */     try {
/* 145 */       closeSilently((Closeable)this.zstdIs);
/*     */     } finally {
/* 147 */       super.handlerRemoved0(ctx);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void closeSilently(Closeable closeable) {
/* 152 */     if (closeable != null)
/*     */       try {
/* 154 */         closeable.close();
/* 155 */       } catch (IOException iOException) {} 
/*     */   }
/*     */   
/*     */   private static final class MutableByteBufInputStream
/*     */     extends InputStream
/*     */   {
/*     */     ByteBuf current;
/*     */     
/*     */     private MutableByteBufInputStream() {}
/*     */     
/*     */     public int read() {
/* 166 */       if (this.current == null || !this.current.isReadable()) {
/* 167 */         return -1;
/*     */       }
/* 169 */       return this.current.readByte() & 0xFF;
/*     */     }
/*     */ 
/*     */     
/*     */     public int read(byte[] b, int off, int len) {
/* 174 */       int available = available();
/* 175 */       if (available == 0) {
/* 176 */         return -1;
/*     */       }
/*     */       
/* 179 */       len = Math.min(available, len);
/* 180 */       this.current.readBytes(b, off, len);
/* 181 */       return len;
/*     */     }
/*     */ 
/*     */     
/*     */     public int available() {
/* 186 */       return (this.current == null) ? 0 : this.current.readableBytes();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\compression\ZstdDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */