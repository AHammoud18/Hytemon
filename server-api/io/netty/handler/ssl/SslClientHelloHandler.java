/*     */ package io.netty.handler.ssl;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufUtil;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelOutboundHandler;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.handler.codec.ByteToMessageDecoder;
/*     */ import io.netty.handler.codec.DecoderException;
/*     */ import io.netty.handler.codec.TooLongFrameException;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.FutureListener;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.net.SocketAddress;
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
/*     */ public abstract class SslClientHelloHandler<T>
/*     */   extends ByteToMessageDecoder
/*     */   implements ChannelOutboundHandler
/*     */ {
/*     */   public static final int MAX_CLIENT_HELLO_LENGTH = 16777215;
/*  48 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(SslClientHelloHandler.class);
/*     */   
/*     */   private final int maxClientHelloLength;
/*     */   private boolean handshakeFailed;
/*     */   private boolean suppressRead;
/*     */   private boolean readPending;
/*     */   private ByteBuf handshakeBuffer;
/*     */   
/*     */   public SslClientHelloHandler() {
/*  57 */     this(16777215);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected SslClientHelloHandler(int maxClientHelloLength) {
/*  63 */     this
/*  64 */       .maxClientHelloLength = ObjectUtil.checkInRange(maxClientHelloLength, 0, 16777215, "maxClientHelloLength");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
/*  69 */     if (!this.suppressRead && !this.handshakeFailed) {
/*     */       try {
/*  71 */         int readerIndex = in.readerIndex();
/*  72 */         int readableBytes = in.readableBytes();
/*  73 */         int handshakeLength = -1;
/*     */ 
/*     */         
/*  76 */         while (readableBytes >= 5) {
/*  77 */           int len, majorVersion, contentType = in.getUnsignedByte(readerIndex);
/*  78 */           switch (contentType) {
/*     */             
/*     */             case 20:
/*     */             case 21:
/*  82 */               len = SslUtils.getEncryptedPacketLength(in, readerIndex, true);
/*     */ 
/*     */               
/*  85 */               if (len == -2) {
/*  86 */                 this.handshakeFailed = true;
/*     */                 
/*  88 */                 NotSslRecordException e = new NotSslRecordException("not an SSL/TLS record: " + ByteBufUtil.hexDump(in));
/*  89 */                 in.skipBytes(in.readableBytes());
/*  90 */                 ctx.fireUserEventTriggered(new SniCompletionEvent(e));
/*  91 */                 SslUtils.handleHandshakeFailure(ctx, e, true);
/*  92 */                 throw e;
/*     */               } 
/*  94 */               if (len == -1) {
/*     */                 return;
/*     */               }
/*     */ 
/*     */               
/*  99 */               select(ctx, null);
/*     */               return;
/*     */             case 22:
/* 102 */               majorVersion = in.getUnsignedByte(readerIndex + 1);
/*     */               
/* 104 */               if (majorVersion == 3) {
/* 105 */                 int packetLength = in.getUnsignedShort(readerIndex + 3) + 5;
/*     */ 
/*     */                 
/* 108 */                 if (readableBytes < packetLength) {
/*     */                   return;
/*     */                 }
/* 111 */                 if (packetLength == 5) {
/* 112 */                   select(ctx, null);
/*     */                   
/*     */                   return;
/*     */                 } 
/* 116 */                 int endOffset = readerIndex + packetLength;
/*     */ 
/*     */                 
/* 119 */                 if (handshakeLength == -1) {
/* 120 */                   if (readerIndex + 4 > endOffset) {
/*     */                     return;
/*     */                   }
/*     */ 
/*     */                   
/* 125 */                   int handshakeType = in.getUnsignedByte(readerIndex + 5);
/*     */ 
/*     */ 
/*     */ 
/*     */                   
/* 130 */                   if (handshakeType != 1) {
/* 131 */                     select(ctx, null);
/*     */ 
/*     */                     
/*     */                     return;
/*     */                   } 
/*     */                   
/* 137 */                   handshakeLength = in.getUnsignedMedium(readerIndex + 5 + 1);
/*     */ 
/*     */                   
/* 140 */                   if (handshakeLength > this.maxClientHelloLength && this.maxClientHelloLength != 0) {
/* 141 */                     TooLongFrameException e = new TooLongFrameException("ClientHello length exceeds " + this.maxClientHelloLength + ": " + handshakeLength);
/*     */ 
/*     */                     
/* 144 */                     in.skipBytes(in.readableBytes());
/* 145 */                     ctx.fireUserEventTriggered(new SniCompletionEvent((Throwable)e));
/* 146 */                     SslUtils.handleHandshakeFailure(ctx, (Throwable)e, true);
/* 147 */                     throw e;
/*     */                   } 
/*     */                   
/* 150 */                   readerIndex += 4;
/* 151 */                   packetLength -= 4;
/*     */                   
/* 153 */                   if (handshakeLength + 4 + 5 <= packetLength) {
/*     */ 
/*     */                     
/* 156 */                     readerIndex += 5;
/* 157 */                     select(ctx, in.retainedSlice(readerIndex, handshakeLength));
/*     */                     return;
/*     */                   } 
/* 160 */                   if (this.handshakeBuffer == null) {
/* 161 */                     this.handshakeBuffer = ctx.alloc().buffer(handshakeLength);
/*     */                   } else {
/*     */                     
/* 164 */                     this.handshakeBuffer.clear();
/*     */                   } 
/*     */                 } 
/*     */ 
/*     */ 
/*     */                 
/* 170 */                 this.handshakeBuffer.writeBytes(in, readerIndex + 5, packetLength - 5);
/*     */                 
/* 172 */                 readerIndex += packetLength;
/* 173 */                 readableBytes -= packetLength;
/* 174 */                 if (handshakeLength <= this.handshakeBuffer.readableBytes()) {
/* 175 */                   ByteBuf clientHello = this.handshakeBuffer.setIndex(0, handshakeLength);
/* 176 */                   this.handshakeBuffer = null;
/*     */                   
/* 178 */                   select(ctx, clientHello);
/*     */                   return;
/*     */                 } 
/*     */                 continue;
/*     */               } 
/*     */               break;
/*     */           } 
/*     */           
/* 186 */           select(ctx, null);
/*     */           
/*     */           return;
/*     */         } 
/* 190 */       } catch (NotSslRecordException e) {
/*     */         
/* 192 */         throw e;
/* 193 */       } catch (TooLongFrameException e) {
/*     */         
/* 195 */         throw e;
/* 196 */       } catch (Exception e) {
/*     */         
/* 198 */         if (logger.isDebugEnabled()) {
/* 199 */           logger.debug("Unexpected client hello packet: " + ByteBufUtil.hexDump(in), e);
/*     */         }
/* 201 */         select(ctx, null);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private void releaseHandshakeBuffer() {
/* 207 */     releaseIfNotNull(this.handshakeBuffer);
/* 208 */     this.handshakeBuffer = null;
/*     */   }
/*     */   
/*     */   private static void releaseIfNotNull(ByteBuf buffer) {
/* 212 */     if (buffer != null) {
/* 213 */       buffer.release();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void select(final ChannelHandlerContext ctx, ByteBuf clientHello) throws Exception {
/*     */     try {
/* 220 */       Future<T> future = lookup(ctx, clientHello);
/* 221 */       if (future.isDone()) {
/* 222 */         onLookupComplete(ctx, future);
/*     */       } else {
/* 224 */         this.suppressRead = true;
/* 225 */         final ByteBuf finalClientHello = clientHello;
/* 226 */         future.addListener((GenericFutureListener)new FutureListener<T>()
/*     */             {
/*     */               public void operationComplete(Future<T> future) {
/* 229 */                 SslClientHelloHandler.releaseIfNotNull(finalClientHello);
/*     */                 try {
/* 231 */                   SslClientHelloHandler.this.suppressRead = false;
/*     */                   try {
/* 233 */                     SslClientHelloHandler.this.onLookupComplete(ctx, future);
/* 234 */                   } catch (DecoderException err) {
/* 235 */                     ctx.fireExceptionCaught((Throwable)err);
/* 236 */                   } catch (Exception cause) {
/* 237 */                     ctx.fireExceptionCaught((Throwable)new DecoderException(cause));
/* 238 */                   } catch (Throwable cause) {
/* 239 */                     ctx.fireExceptionCaught(cause);
/*     */                   } 
/*     */                 } finally {
/* 242 */                   if (SslClientHelloHandler.this.readPending) {
/* 243 */                     SslClientHelloHandler.this.readPending = false;
/* 244 */                     ctx.read();
/*     */                   } 
/*     */                 } 
/*     */               }
/*     */             });
/*     */ 
/*     */         
/* 251 */         clientHello = null;
/*     */       } 
/* 253 */     } catch (Throwable cause) {
/* 254 */       PlatformDependent.throwException(cause);
/*     */     } finally {
/* 256 */       releaseIfNotNull(clientHello);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void handlerRemoved0(ChannelHandlerContext ctx) throws Exception {
/* 262 */     releaseHandshakeBuffer();
/*     */     
/* 264 */     super.handlerRemoved0(ctx);
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
/*     */   public void read(ChannelHandlerContext ctx) throws Exception {
/* 302 */     if (this.suppressRead) {
/* 303 */       this.readPending = true;
/*     */     } else {
/* 305 */       ctx.read();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
/* 311 */     ctx.bind(localAddress, promise);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
/* 317 */     ctx.connect(remoteAddress, localAddress, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/* 322 */     ctx.disconnect(promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/* 327 */     ctx.close(promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/* 332 */     ctx.deregister(promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
/* 337 */     ctx.write(msg, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public void flush(ChannelHandlerContext ctx) throws Exception {
/* 342 */     ctx.flush();
/*     */   }
/*     */   
/*     */   protected abstract Future<T> lookup(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf) throws Exception;
/*     */   
/*     */   protected abstract void onLookupComplete(ChannelHandlerContext paramChannelHandlerContext, Future<T> paramFuture) throws Exception;
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\SslClientHelloHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */