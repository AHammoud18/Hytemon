/*     */ package io.netty.handler.codec.compression;
/*     */ 
/*     */ import com.aayushatharva.brotli4j.encoder.BrotliEncoderChannel;
/*     */ import com.aayushatharva.brotli4j.encoder.Encoder;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelHandler.Sharable;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.handler.codec.MessageToByteEncoder;
/*     */ import io.netty.util.AttributeKey;
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.ClosedChannelException;
/*     */ import java.nio.channels.WritableByteChannel;
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
/*     */ @Sharable
/*     */ public final class BrotliEncoder
/*     */   extends MessageToByteEncoder<ByteBuf>
/*     */ {
/*  45 */   private static final AttributeKey<Writer> ATTR = AttributeKey.valueOf("BrotliEncoderWriter");
/*     */ 
/*     */   
/*     */   private final Encoder.Parameters parameters;
/*     */   
/*     */   private final boolean isSharable;
/*     */   
/*     */   private Writer writer;
/*     */ 
/*     */   
/*     */   public BrotliEncoder() {
/*  56 */     this(BrotliOptions.DEFAULT);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BrotliEncoder(BrotliOptions brotliOptions) {
/*  66 */     this(brotliOptions.parameters());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BrotliEncoder(Encoder.Parameters parameters) {
/*  76 */     this(parameters, true);
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
/*     */   
/*     */   public BrotliEncoder(Encoder.Parameters parameters, boolean isSharable) {
/*  96 */     super(ByteBuf.class);
/*  97 */     this.parameters = (Encoder.Parameters)ObjectUtil.checkNotNull(parameters, "Parameters");
/*  98 */     this.isSharable = isSharable;
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
/* 103 */     Writer writer = new Writer(this.parameters, ctx);
/* 104 */     if (this.isSharable) {
/* 105 */       ctx.channel().attr(ATTR).set(writer);
/*     */     } else {
/* 107 */       this.writer = writer;
/*     */     } 
/* 109 */     super.handlerAdded(ctx);
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
/* 114 */     finish(ctx);
/* 115 */     super.handlerRemoved(ctx);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) throws Exception {}
/*     */ 
/*     */   
/*     */   protected ByteBuf allocateBuffer(ChannelHandlerContext ctx, ByteBuf msg, boolean preferDirect) throws Exception {
/*     */     Writer writer;
/* 125 */     if (!msg.isReadable()) {
/* 126 */       return Unpooled.EMPTY_BUFFER;
/*     */     }
/*     */ 
/*     */     
/* 130 */     if (this.isSharable) {
/* 131 */       writer = (Writer)ctx.channel().attr(ATTR).get();
/*     */     } else {
/* 133 */       writer = this.writer;
/*     */     } 
/*     */ 
/*     */     
/* 137 */     if (writer == null) {
/* 138 */       return Unpooled.EMPTY_BUFFER;
/*     */     }
/* 140 */     writer.encode(msg, preferDirect);
/* 141 */     return writer.writableBuffer;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSharable() {
/* 147 */     return this.isSharable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void finish(ChannelHandlerContext ctx) throws IOException {
/* 157 */     finishEncode(ctx, ctx.newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   private ChannelFuture finishEncode(ChannelHandlerContext ctx, ChannelPromise promise) throws IOException {
/*     */     Writer writer;
/* 163 */     if (this.isSharable) {
/* 164 */       writer = (Writer)ctx.channel().attr(ATTR).getAndSet(null);
/*     */     } else {
/* 166 */       writer = this.writer;
/*     */     } 
/*     */     
/* 169 */     if (writer != null) {
/* 170 */       writer.close();
/* 171 */       this.writer = null;
/*     */     } 
/* 173 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */   
/*     */   public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/* 178 */     ChannelFuture f = finishEncode(ctx, ctx.newPromise());
/* 179 */     EncoderUtil.closeAfterFinishEncode(ctx, f, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class Writer
/*     */     implements WritableByteChannel
/*     */   {
/*     */     private ByteBuf writableBuffer;
/*     */     
/*     */     private final BrotliEncoderChannel brotliEncoderChannel;
/*     */     
/*     */     private final ChannelHandlerContext ctx;
/*     */     private boolean isClosed;
/*     */     
/*     */     private Writer(Encoder.Parameters parameters, ChannelHandlerContext ctx) throws IOException {
/* 194 */       this.brotliEncoderChannel = new BrotliEncoderChannel(this, parameters);
/* 195 */       this.ctx = ctx;
/*     */     }
/*     */     
/*     */     private void encode(ByteBuf msg, boolean preferDirect) throws Exception {
/*     */       try {
/* 200 */         allocate(preferDirect);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 209 */         ByteBuffer nioBuffer = CompressionUtil.safeReadableNioBuffer(msg);
/* 210 */         int position = nioBuffer.position();
/* 211 */         this.brotliEncoderChannel.write(nioBuffer);
/* 212 */         msg.skipBytes(nioBuffer.position() - position);
/* 213 */         this.brotliEncoderChannel.flush();
/* 214 */       } catch (Exception e) {
/* 215 */         ReferenceCountUtil.release(msg);
/* 216 */         throw e;
/*     */       } 
/*     */     }
/*     */     
/*     */     private void allocate(boolean preferDirect) {
/* 221 */       if (preferDirect) {
/* 222 */         this.writableBuffer = this.ctx.alloc().ioBuffer();
/*     */       } else {
/* 224 */         this.writableBuffer = this.ctx.alloc().buffer();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int write(ByteBuffer src) throws IOException {
/* 230 */       if (!isOpen()) {
/* 231 */         throw new ClosedChannelException();
/*     */       }
/*     */       
/* 234 */       return this.writableBuffer.writeBytes(src).readableBytes();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isOpen() {
/* 239 */       return !this.isClosed;
/*     */     }
/*     */ 
/*     */     
/*     */     public void close() {
/* 244 */       final ChannelPromise promise = this.ctx.newPromise();
/*     */       
/* 246 */       this.ctx.executor().execute(new Runnable()
/*     */           {
/*     */             public void run() {
/*     */               try {
/* 250 */                 BrotliEncoder.Writer.this.finish(promise);
/* 251 */               } catch (IOException ex) {
/* 252 */                 promise.setFailure(new IllegalStateException("Failed to finish encoding", ex));
/*     */               } 
/*     */             }
/*     */           });
/*     */     }
/*     */     
/*     */     public void finish(ChannelPromise promise) throws IOException {
/* 259 */       if (!this.isClosed) {
/*     */         
/* 261 */         allocate(true);
/*     */         
/*     */         try {
/* 264 */           this.brotliEncoderChannel.close();
/* 265 */           this.isClosed = true;
/* 266 */         } catch (Exception ex) {
/* 267 */           promise.setFailure(ex);
/*     */ 
/*     */ 
/*     */           
/* 271 */           ReferenceCountUtil.release(this.writableBuffer);
/*     */           
/*     */           return;
/*     */         } 
/* 275 */         this.ctx.writeAndFlush(this.writableBuffer, promise);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\compression\BrotliEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */