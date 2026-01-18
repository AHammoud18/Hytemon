/*     */ package io.netty.handler.ssl;
/*     */ 
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.handler.codec.DecoderException;
/*     */ import io.netty.util.AsyncMapping;
/*     */ import io.netty.util.DomainNameMapping;
/*     */ import io.netty.util.Mapping;
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.Promise;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.PlatformDependent;
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
/*     */ public class SniHandler
/*     */   extends AbstractSniHandler<SslContext>
/*     */ {
/*  38 */   private static final Selection EMPTY_SELECTION = new Selection(null, null);
/*     */   
/*     */   protected final AsyncMapping<String, SslContext> mapping;
/*     */   
/*  42 */   private volatile Selection selection = EMPTY_SELECTION;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SniHandler(Mapping<? super String, ? extends SslContext> mapping) {
/*  51 */     this(new AsyncMappingAdapter(mapping, null));
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
/*     */   public SniHandler(Mapping<? super String, ? extends SslContext> mapping, int maxClientHelloLength, long handshakeTimeoutMillis) {
/*  64 */     this(new AsyncMappingAdapter(mapping, null), maxClientHelloLength, handshakeTimeoutMillis);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SniHandler(DomainNameMapping<? extends SslContext> mapping) {
/*  74 */     this((Mapping)mapping);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SniHandler(AsyncMapping<? super String, ? extends SslContext> mapping) {
/*  85 */     this(mapping, 0, 0L);
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
/*     */   public SniHandler(AsyncMapping<? super String, ? extends SslContext> mapping, int maxClientHelloLength, long handshakeTimeoutMillis) {
/*  99 */     super(maxClientHelloLength, handshakeTimeoutMillis);
/* 100 */     this.mapping = (AsyncMapping<String, SslContext>)ObjectUtil.checkNotNull(mapping, "mapping");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SniHandler(Mapping<? super String, ? extends SslContext> mapping, long handshakeTimeoutMillis) {
/* 111 */     this(new AsyncMappingAdapter(mapping, null), handshakeTimeoutMillis);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SniHandler(AsyncMapping<? super String, ? extends SslContext> mapping, long handshakeTimeoutMillis) {
/* 122 */     this(mapping, 0, handshakeTimeoutMillis);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String hostname() {
/* 129 */     return this.selection.hostname;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SslContext sslContext() {
/* 136 */     return this.selection.context;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Future<SslContext> lookup(ChannelHandlerContext ctx, String hostname) throws Exception {
/* 147 */     return this.mapping.map(hostname, ctx.executor().newPromise());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void onLookupComplete(ChannelHandlerContext ctx, String hostname, Future<SslContext> future) throws Exception {
/* 153 */     if (!future.isSuccess()) {
/* 154 */       Throwable cause = future.cause();
/* 155 */       if (cause instanceof Error) {
/* 156 */         throw (Error)cause;
/*     */       }
/* 158 */       throw new DecoderException("failed to get the SslContext for " + hostname, cause);
/*     */     } 
/*     */     
/* 161 */     SslContext sslContext = (SslContext)future.getNow();
/* 162 */     this.selection = new Selection(sslContext, hostname);
/*     */     try {
/* 164 */       replaceHandler(ctx, hostname, sslContext);
/* 165 */     } catch (Throwable cause) {
/* 166 */       this.selection = EMPTY_SELECTION;
/* 167 */       PlatformDependent.throwException(cause);
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
/*     */   
/*     */   protected void replaceHandler(ChannelHandlerContext ctx, String hostname, SslContext sslContext) throws Exception {
/* 181 */     SslHandler sslHandler = null;
/*     */     try {
/* 183 */       sslHandler = newSslHandler(sslContext, ctx.alloc());
/* 184 */       ctx.pipeline().replace((ChannelHandler)this, SslHandler.class.getName(), (ChannelHandler)sslHandler);
/* 185 */       sslHandler = null;
/*     */     
/*     */     }
/*     */     finally {
/*     */       
/* 190 */       if (sslHandler != null) {
/* 191 */         ReferenceCountUtil.safeRelease(sslHandler.engine());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SslHandler newSslHandler(SslContext context, ByteBufAllocator allocator) {
/* 201 */     SslHandler sslHandler = context.newHandler(allocator);
/* 202 */     sslHandler.setHandshakeTimeoutMillis(this.handshakeTimeoutMillis);
/* 203 */     return sslHandler;
/*     */   }
/*     */   
/*     */   private static final class AsyncMappingAdapter implements AsyncMapping<String, SslContext> {
/*     */     private final Mapping<? super String, ? extends SslContext> mapping;
/*     */     
/*     */     private AsyncMappingAdapter(Mapping<? super String, ? extends SslContext> mapping) {
/* 210 */       this.mapping = (Mapping<? super String, ? extends SslContext>)ObjectUtil.checkNotNull(mapping, "mapping");
/*     */     }
/*     */ 
/*     */     
/*     */     public Future<SslContext> map(String input, Promise<SslContext> promise) {
/*     */       SslContext context;
/*     */       try {
/* 217 */         context = (SslContext)this.mapping.map(input);
/* 218 */       } catch (Throwable cause) {
/* 219 */         return (Future<SslContext>)promise.setFailure(cause);
/*     */       } 
/* 221 */       return (Future<SslContext>)promise.setSuccess(context);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class Selection {
/*     */     final SslContext context;
/*     */     final String hostname;
/*     */     
/*     */     Selection(SslContext context, String hostname) {
/* 230 */       this.context = context;
/* 231 */       this.hostname = hostname;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\SniHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */