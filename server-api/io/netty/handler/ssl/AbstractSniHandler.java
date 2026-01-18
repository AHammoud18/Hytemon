/*     */ package io.netty.handler.ssl;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.util.CharsetUtil;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.ScheduledFuture;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.util.Locale;
/*     */ import java.util.concurrent.TimeUnit;
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
/*     */ public abstract class AbstractSniHandler<T>
/*     */   extends SslClientHelloHandler<T>
/*     */ {
/*     */   protected final long handshakeTimeoutMillis;
/*     */   private ScheduledFuture<?> timeoutFuture;
/*     */   private String hostname;
/*     */   
/*     */   private static String extractSniHostname(ByteBuf in) {
/*  59 */     int offset = in.readerIndex();
/*  60 */     int endOffset = in.writerIndex();
/*  61 */     offset += 34;
/*     */     
/*  63 */     if (endOffset - offset >= 6) {
/*  64 */       int sessionIdLength = in.getUnsignedByte(offset);
/*  65 */       offset += sessionIdLength + 1;
/*     */       
/*  67 */       int cipherSuitesLength = in.getUnsignedShort(offset);
/*  68 */       offset += cipherSuitesLength + 2;
/*     */       
/*  70 */       int compressionMethodLength = in.getUnsignedByte(offset);
/*  71 */       offset += compressionMethodLength + 1;
/*     */       
/*  73 */       int extensionsLength = in.getUnsignedShort(offset);
/*  74 */       offset += 2;
/*  75 */       int extensionsLimit = offset + extensionsLength;
/*     */ 
/*     */       
/*  78 */       if (extensionsLimit <= endOffset) {
/*  79 */         while (extensionsLimit - offset >= 4) {
/*  80 */           int extensionType = in.getUnsignedShort(offset);
/*  81 */           offset += 2;
/*     */           
/*  83 */           int extensionLength = in.getUnsignedShort(offset);
/*  84 */           offset += 2;
/*     */           
/*  86 */           if (extensionsLimit - offset < extensionLength) {
/*     */             break;
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*  92 */           if (extensionType == 0) {
/*  93 */             offset += 2;
/*  94 */             if (extensionsLimit - offset < 3) {
/*     */               break;
/*     */             }
/*     */             
/*  98 */             int serverNameType = in.getUnsignedByte(offset);
/*  99 */             offset++;
/*     */             
/* 101 */             if (serverNameType == 0) {
/* 102 */               int serverNameLength = in.getUnsignedShort(offset);
/* 103 */               offset += 2;
/*     */               
/* 105 */               if (extensionsLimit - offset < serverNameLength) {
/*     */                 break;
/*     */               }
/*     */               
/* 109 */               String hostname = in.toString(offset, serverNameLength, CharsetUtil.US_ASCII);
/* 110 */               return hostname.toLowerCase(Locale.US);
/*     */             } 
/*     */ 
/*     */             
/*     */             break;
/*     */           } 
/*     */           
/* 117 */           offset += extensionLength;
/*     */         } 
/*     */       }
/*     */     } 
/* 121 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractSniHandler(long handshakeTimeoutMillis) {
/* 132 */     this(0, handshakeTimeoutMillis);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractSniHandler(int maxClientHelloLength, long handshakeTimeoutMillis) {
/* 140 */     super(maxClientHelloLength);
/* 141 */     this.handshakeTimeoutMillis = ObjectUtil.checkPositiveOrZero(handshakeTimeoutMillis, "handshakeTimeoutMillis");
/*     */   }
/*     */   
/*     */   public AbstractSniHandler() {
/* 145 */     this(0, 0L);
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
/* 150 */     if (ctx.channel().isActive()) {
/* 151 */       checkStartTimeout(ctx);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelActive(ChannelHandlerContext ctx) throws Exception {
/* 157 */     ctx.fireChannelActive();
/* 158 */     checkStartTimeout(ctx);
/*     */   }
/*     */   
/*     */   private void checkStartTimeout(final ChannelHandlerContext ctx) {
/* 162 */     if (this.handshakeTimeoutMillis <= 0L || this.timeoutFuture != null) {
/*     */       return;
/*     */     }
/* 165 */     this.timeoutFuture = ctx.executor().schedule(new Runnable()
/*     */         {
/*     */           public void run() {
/* 168 */             if (ctx.channel().isActive()) {
/* 169 */               SslHandshakeTimeoutException exception = new SslHandshakeTimeoutException("handshake timed out after " + AbstractSniHandler.this.handshakeTimeoutMillis + "ms");
/*     */               
/* 171 */               ctx.fireUserEventTriggered(new SniCompletionEvent(exception));
/* 172 */               ctx.close();
/*     */             } 
/*     */           }
/*     */         },  this.handshakeTimeoutMillis, TimeUnit.MILLISECONDS);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Future<T> lookup(ChannelHandlerContext ctx, ByteBuf clientHello) throws Exception {
/* 180 */     this.hostname = (clientHello == null) ? null : extractSniHostname(clientHello);
/*     */     
/* 182 */     return lookup(ctx, this.hostname);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onLookupComplete(ChannelHandlerContext ctx, Future<T> future) throws Exception {
/* 187 */     if (this.timeoutFuture != null) {
/* 188 */       this.timeoutFuture.cancel(false);
/*     */     }
/*     */     try {
/* 191 */       onLookupComplete(ctx, this.hostname, future);
/*     */     } finally {
/* 193 */       fireSniCompletionEvent(ctx, this.hostname, future);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract Future<T> lookup(ChannelHandlerContext paramChannelHandlerContext, String paramString) throws Exception;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void onLookupComplete(ChannelHandlerContext paramChannelHandlerContext, String paramString, Future<T> paramFuture) throws Exception;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void fireSniCompletionEvent(ChannelHandlerContext ctx, String hostname, Future<?> future) {
/* 214 */     Throwable cause = future.cause();
/* 215 */     if (cause == null) {
/* 216 */       ctx.fireUserEventTriggered(new SniCompletionEvent(hostname));
/*     */     } else {
/* 218 */       ctx.fireUserEventTriggered(new SniCompletionEvent(hostname, cause));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\AbstractSniHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */