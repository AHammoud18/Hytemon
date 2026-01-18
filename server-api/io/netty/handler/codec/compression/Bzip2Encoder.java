/*     */ package io.netty.handler.codec.compression;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.handler.codec.MessageToByteEncoder;
/*     */ import io.netty.util.concurrent.EventExecutor;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.Promise;
/*     */ import io.netty.util.concurrent.PromiseNotifier;
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
/*     */ public class Bzip2Encoder
/*     */   extends MessageToByteEncoder<ByteBuf>
/*     */ {
/*     */   private enum State
/*     */   {
/*  44 */     INIT,
/*  45 */     INIT_BLOCK,
/*  46 */     WRITE_DATA,
/*  47 */     CLOSE_BLOCK;
/*     */   }
/*     */   
/*  50 */   private State currentState = State.INIT;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  55 */   private final Bzip2BitWriter writer = new Bzip2BitWriter();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int streamBlockSize;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int streamCRC;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Bzip2BlockCompressor blockCompressor;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private volatile boolean finished;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private volatile ChannelHandlerContext ctx;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Bzip2Encoder() {
/*  86 */     this(9);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Bzip2Encoder(int blockSizeMultiplier) {
/*  97 */     super(ByteBuf.class);
/*  98 */     if (blockSizeMultiplier < 1 || blockSizeMultiplier > 9) {
/*  99 */       throw new IllegalArgumentException("blockSizeMultiplier: " + blockSizeMultiplier + " (expected: 1-9)");
/*     */     }
/*     */     
/* 102 */     this.streamBlockSize = blockSizeMultiplier * 100000;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void encode(ChannelHandlerContext ctx, ByteBuf in, ByteBuf out) throws Exception {
/* 107 */     if (this.finished) {
/* 108 */       out.writeBytes(in); return;
/*     */     }  while (true) {
/*     */       Bzip2BlockCompressor blockCompressor;
/*     */       int length;
/*     */       int bytesWritten;
/* 113 */       switch (this.currentState) {
/*     */         case INIT:
/* 115 */           out.ensureWritable(4);
/* 116 */           out.writeMedium(4348520);
/* 117 */           out.writeByte(48 + this.streamBlockSize / 100000);
/* 118 */           this.currentState = State.INIT_BLOCK;
/*     */         
/*     */         case INIT_BLOCK:
/* 121 */           this.blockCompressor = new Bzip2BlockCompressor(this.writer, this.streamBlockSize);
/* 122 */           this.currentState = State.WRITE_DATA;
/*     */         
/*     */         case WRITE_DATA:
/* 125 */           if (!in.isReadable()) {
/*     */             return;
/*     */           }
/* 128 */           blockCompressor = this.blockCompressor;
/* 129 */           length = Math.min(in.readableBytes(), blockCompressor.availableSize());
/* 130 */           bytesWritten = blockCompressor.write(in, in.readerIndex(), length);
/* 131 */           in.skipBytes(bytesWritten);
/* 132 */           if (!blockCompressor.isFull()) {
/* 133 */             if (in.isReadable()) {
/*     */               continue;
/*     */             }
/*     */             
/*     */             return;
/*     */           } 
/* 139 */           this.currentState = State.CLOSE_BLOCK;
/*     */         
/*     */         case CLOSE_BLOCK:
/* 142 */           closeBlock(out);
/* 143 */           this.currentState = State.INIT_BLOCK; continue;
/*     */       }  break;
/*     */     } 
/* 146 */     throw new IllegalStateException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void closeBlock(ByteBuf out) {
/* 155 */     Bzip2BlockCompressor blockCompressor = this.blockCompressor;
/* 156 */     if (!blockCompressor.isEmpty()) {
/* 157 */       blockCompressor.close(out);
/* 158 */       int blockCRC = blockCompressor.crc();
/* 159 */       this.streamCRC = (this.streamCRC << 1 | this.streamCRC >>> 31) ^ blockCRC;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isClosed() {
/* 167 */     return this.finished;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture close() {
/* 176 */     return close(ctx().newPromise());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture close(final ChannelPromise promise) {
/* 185 */     ChannelHandlerContext ctx = ctx();
/* 186 */     EventExecutor executor = ctx.executor();
/* 187 */     if (executor.inEventLoop()) {
/* 188 */       return finishEncode(ctx, promise);
/*     */     }
/* 190 */     executor.execute(new Runnable()
/*     */         {
/*     */           public void run() {
/* 193 */             ChannelFuture f = Bzip2Encoder.this.finishEncode(Bzip2Encoder.this.ctx(), promise);
/* 194 */             PromiseNotifier.cascade((Future)f, (Promise)promise);
/*     */           }
/*     */         });
/* 197 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/* 203 */     ChannelFuture f = finishEncode(ctx, ctx.newPromise());
/* 204 */     EncoderUtil.closeAfterFinishEncode(ctx, f, promise);
/*     */   }
/*     */   
/*     */   private ChannelFuture finishEncode(ChannelHandlerContext ctx, ChannelPromise promise) {
/* 208 */     if (this.finished) {
/* 209 */       promise.setSuccess();
/* 210 */       return (ChannelFuture)promise;
/*     */     } 
/* 212 */     this.finished = true;
/*     */     
/* 214 */     ByteBuf footer = ctx.alloc().buffer();
/* 215 */     closeBlock(footer);
/*     */     
/* 217 */     int streamCRC = this.streamCRC;
/* 218 */     Bzip2BitWriter writer = this.writer;
/*     */     try {
/* 220 */       writer.writeBits(footer, 24, 1536581L);
/* 221 */       writer.writeBits(footer, 24, 3690640L);
/* 222 */       writer.writeInt(footer, streamCRC);
/* 223 */       writer.flush(footer);
/*     */     } finally {
/* 225 */       this.blockCompressor = null;
/*     */     } 
/* 227 */     return ctx.writeAndFlush(footer, promise);
/*     */   }
/*     */   
/*     */   private ChannelHandlerContext ctx() {
/* 231 */     ChannelHandlerContext ctx = this.ctx;
/* 232 */     if (ctx == null) {
/* 233 */       throw new IllegalStateException("not added to a pipeline");
/*     */     }
/* 235 */     return ctx;
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
/* 240 */     this.ctx = ctx;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\compression\Bzip2Encoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */