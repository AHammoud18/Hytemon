/*     */ package io.netty.handler.codec.compression;
/*     */ 
/*     */ import com.aayushatharva.brotli4j.decoder.DecoderJNI;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.handler.codec.ByteToMessageDecoder;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.nio.ByteBuffer;
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
/*     */ public final class BrotliDecoder
/*     */   extends ByteToMessageDecoder
/*     */ {
/*     */   private final int inputBufferSize;
/*     */   private DecoderJNI.Wrapper decoder;
/*     */   private boolean destroyed;
/*     */   private boolean needsRead;
/*     */   
/*     */   private enum State
/*     */   {
/*  36 */     DONE, NEEDS_MORE_INPUT, ERROR;
/*     */   }
/*     */   
/*     */   static {
/*     */     try {
/*  41 */       Brotli.ensureAvailability();
/*  42 */     } catch (Throwable throwable) {
/*  43 */       throw new ExceptionInInitializerError(throwable);
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
/*     */   
/*     */   public BrotliDecoder() {
/*  56 */     this(8192);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BrotliDecoder(int inputBufferSize) {
/*  64 */     this.inputBufferSize = ObjectUtil.checkPositive(inputBufferSize, "inputBufferSize");
/*     */   }
/*     */   
/*     */   private void forwardOutput(ChannelHandlerContext ctx) {
/*  68 */     ByteBuffer nativeBuffer = this.decoder.pull();
/*     */     
/*  70 */     ByteBuf copy = ctx.alloc().buffer(nativeBuffer.remaining());
/*  71 */     copy.writeBytes(nativeBuffer);
/*  72 */     this.needsRead = false;
/*  73 */     ctx.fireChannelRead(copy);
/*     */   } private State decompress(ChannelHandlerContext ctx, ByteBuf input) {
/*     */     while (true) {
/*     */       ByteBuffer decoderInputBuffer;
/*     */       int readBytes;
/*  78 */       switch (this.decoder.getStatus()) {
/*     */         case DONE:
/*  80 */           return State.DONE;
/*     */         
/*     */         case OK:
/*  83 */           this.decoder.push(0);
/*     */           continue;
/*     */         
/*     */         case NEEDS_MORE_INPUT:
/*  87 */           if (this.decoder.hasOutput()) {
/*  88 */             forwardOutput(ctx);
/*     */           }
/*     */           
/*  91 */           if (!input.isReadable()) {
/*  92 */             return State.NEEDS_MORE_INPUT;
/*     */           }
/*     */           
/*  95 */           decoderInputBuffer = this.decoder.getInputBuffer();
/*  96 */           decoderInputBuffer.clear();
/*  97 */           readBytes = readBytes(input, decoderInputBuffer);
/*  98 */           this.decoder.push(readBytes);
/*     */           continue;
/*     */         
/*     */         case NEEDS_MORE_OUTPUT:
/* 102 */           forwardOutput(ctx); continue;
/*     */       } 
/*     */       break;
/*     */     } 
/* 106 */     return State.ERROR;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int readBytes(ByteBuf in, ByteBuffer dest) {
/* 112 */     int limit = Math.min(in.readableBytes(), dest.remaining());
/* 113 */     ByteBuffer slice = dest.slice();
/* 114 */     slice.limit(limit);
/* 115 */     in.readBytes(slice);
/* 116 */     dest.position(dest.position() + limit);
/* 117 */     return limit;
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
/* 122 */     this.decoder = new DecoderJNI.Wrapper(this.inputBufferSize);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
/* 127 */     this.needsRead = true;
/* 128 */     if (this.destroyed) {
/*     */       
/* 130 */       in.skipBytes(in.readableBytes());
/*     */       
/*     */       return;
/*     */     } 
/* 134 */     if (!in.isReadable()) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/* 139 */       State state = decompress(ctx, in);
/* 140 */       if (state == State.DONE) {
/* 141 */         destroy();
/* 142 */       } else if (state == State.ERROR) {
/* 143 */         throw new DecompressionException("Brotli stream corrupted");
/*     */       } 
/* 145 */     } catch (Exception e) {
/* 146 */       destroy();
/* 147 */       throw e;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void destroy() {
/* 152 */     if (!this.destroyed) {
/* 153 */       this.destroyed = true;
/* 154 */       this.decoder.destroy();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void handlerRemoved0(ChannelHandlerContext ctx) throws Exception {
/*     */     try {
/* 161 */       destroy();
/*     */     } finally {
/* 163 */       super.handlerRemoved0(ctx);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext ctx) throws Exception {
/*     */     try {
/* 170 */       destroy();
/*     */     } finally {
/* 172 */       super.channelInactive(ctx);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
/* 179 */     discardSomeReadBytes();
/*     */     
/* 181 */     if (this.needsRead && !ctx.channel().config().isAutoRead()) {
/* 182 */       ctx.read();
/*     */     }
/* 184 */     ctx.fireChannelReadComplete();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\compression\BrotliDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */