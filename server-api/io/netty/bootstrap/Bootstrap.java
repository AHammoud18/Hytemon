/*     */ package io.netty.bootstrap;
/*     */ 
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.channel.EventLoop;
/*     */ import io.netty.channel.EventLoopGroup;
/*     */ import io.netty.resolver.AddressResolver;
/*     */ import io.netty.resolver.AddressResolverGroup;
/*     */ import io.netty.resolver.DefaultAddressResolverGroup;
/*     */ import io.netty.util.concurrent.EventExecutor;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.FutureListener;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.util.Collection;
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
/*     */ public class Bootstrap
/*     */   extends AbstractBootstrap<Bootstrap, Channel>
/*     */ {
/*  49 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(Bootstrap.class);
/*     */   
/*  51 */   private final BootstrapConfig config = new BootstrapConfig(this);
/*     */   
/*     */   private ExternalAddressResolver externalResolver;
/*     */   
/*     */   private volatile boolean disableResolver;
/*     */   
/*     */   private volatile SocketAddress remoteAddress;
/*     */   
/*     */   private Bootstrap(Bootstrap bootstrap) {
/*  60 */     super(bootstrap);
/*  61 */     this.externalResolver = bootstrap.externalResolver;
/*  62 */     this.disableResolver = bootstrap.disableResolver;
/*  63 */     this.remoteAddress = bootstrap.remoteAddress;
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
/*     */   public Bootstrap resolver(AddressResolverGroup<?> resolver) {
/*  75 */     this.externalResolver = (resolver == null) ? null : new ExternalAddressResolver(resolver);
/*  76 */     this.disableResolver = false;
/*  77 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Bootstrap disableResolver() {
/*  85 */     this.externalResolver = null;
/*  86 */     this.disableResolver = true;
/*  87 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Bootstrap remoteAddress(SocketAddress remoteAddress) {
/*  95 */     this.remoteAddress = remoteAddress;
/*  96 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Bootstrap remoteAddress(String inetHost, int inetPort) {
/* 103 */     this.remoteAddress = InetSocketAddress.createUnresolved(inetHost, inetPort);
/* 104 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Bootstrap remoteAddress(InetAddress inetHost, int inetPort) {
/* 111 */     this.remoteAddress = new InetSocketAddress(inetHost, inetPort);
/* 112 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture connect() {
/* 119 */     validate();
/* 120 */     SocketAddress remoteAddress = this.remoteAddress;
/* 121 */     if (remoteAddress == null) {
/* 122 */       throw new IllegalStateException("remoteAddress not set");
/*     */     }
/*     */     
/* 125 */     return doResolveAndConnect(remoteAddress, this.config.localAddress());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture connect(String inetHost, int inetPort) {
/* 132 */     return connect(InetSocketAddress.createUnresolved(inetHost, inetPort));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture connect(InetAddress inetHost, int inetPort) {
/* 139 */     return connect(new InetSocketAddress(inetHost, inetPort));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture connect(SocketAddress remoteAddress) {
/* 146 */     ObjectUtil.checkNotNull(remoteAddress, "remoteAddress");
/* 147 */     validate();
/* 148 */     return doResolveAndConnect(remoteAddress, this.config.localAddress());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress) {
/* 155 */     ObjectUtil.checkNotNull(remoteAddress, "remoteAddress");
/* 156 */     validate();
/* 157 */     return doResolveAndConnect(remoteAddress, localAddress);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ChannelFuture doResolveAndConnect(final SocketAddress remoteAddress, final SocketAddress localAddress) {
/* 164 */     ChannelFuture regFuture = initAndRegister();
/* 165 */     final Channel channel = regFuture.channel();
/*     */     
/* 167 */     if (regFuture.isDone()) {
/* 168 */       if (!regFuture.isSuccess()) {
/* 169 */         return regFuture;
/*     */       }
/* 171 */       return doResolveAndConnect0(channel, remoteAddress, localAddress, channel.newPromise());
/*     */     } 
/*     */     
/* 174 */     final AbstractBootstrap.PendingRegistrationPromise promise = new AbstractBootstrap.PendingRegistrationPromise(channel);
/* 175 */     regFuture.addListener((GenericFutureListener)new ChannelFutureListener()
/*     */         {
/*     */           
/*     */           public void operationComplete(ChannelFuture future) throws Exception
/*     */           {
/* 180 */             Throwable cause = future.cause();
/* 181 */             if (cause != null) {
/*     */ 
/*     */               
/* 184 */               promise.setFailure(cause);
/*     */             }
/*     */             else {
/*     */               
/* 188 */               promise.registered();
/* 189 */               Bootstrap.this.doResolveAndConnect0(channel, remoteAddress, localAddress, (ChannelPromise)promise);
/*     */             } 
/*     */           }
/*     */         });
/* 193 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */   
/*     */   private ChannelFuture doResolveAndConnect0(final Channel channel, SocketAddress remoteAddress, final SocketAddress localAddress, final ChannelPromise promise) {
/*     */     try {
/*     */       AddressResolver<SocketAddress> resolver;
/* 200 */       if (this.disableResolver) {
/* 201 */         doConnect(remoteAddress, localAddress, promise);
/* 202 */         return (ChannelFuture)promise;
/*     */       } 
/*     */       
/* 205 */       EventLoop eventLoop = channel.eventLoop();
/*     */       
/*     */       try {
/* 208 */         resolver = ExternalAddressResolver.getOrDefault(this.externalResolver).getResolver((EventExecutor)eventLoop);
/* 209 */       } catch (Throwable cause) {
/* 210 */         channel.close();
/* 211 */         return (ChannelFuture)promise.setFailure(cause);
/*     */       } 
/*     */       
/* 214 */       if (!resolver.isSupported(remoteAddress) || resolver.isResolved(remoteAddress)) {
/*     */         
/* 216 */         doConnect(remoteAddress, localAddress, promise);
/* 217 */         return (ChannelFuture)promise;
/*     */       } 
/*     */       
/* 220 */       Future<SocketAddress> resolveFuture = resolver.resolve(remoteAddress);
/*     */       
/* 222 */       if (resolveFuture.isDone()) {
/* 223 */         Throwable resolveFailureCause = resolveFuture.cause();
/*     */         
/* 225 */         if (resolveFailureCause != null) {
/*     */           
/* 227 */           channel.close();
/* 228 */           promise.setFailure(resolveFailureCause);
/*     */         } else {
/*     */           
/* 231 */           doConnect((SocketAddress)resolveFuture.getNow(), localAddress, promise);
/*     */         } 
/* 233 */         return (ChannelFuture)promise;
/*     */       } 
/*     */ 
/*     */       
/* 237 */       resolveFuture.addListener((GenericFutureListener)new FutureListener<SocketAddress>()
/*     */           {
/*     */             public void operationComplete(Future<SocketAddress> future) throws Exception {
/* 240 */               if (future.cause() != null) {
/* 241 */                 channel.close();
/* 242 */                 promise.setFailure(future.cause());
/*     */               } else {
/* 244 */                 Bootstrap.doConnect((SocketAddress)future.getNow(), localAddress, promise);
/*     */               } 
/*     */             }
/*     */           });
/* 248 */     } catch (Throwable cause) {
/* 249 */       promise.tryFailure(cause);
/*     */     } 
/* 251 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void doConnect(final SocketAddress remoteAddress, final SocketAddress localAddress, final ChannelPromise connectPromise) {
/* 259 */     final Channel channel = connectPromise.channel();
/* 260 */     channel.eventLoop().execute(new Runnable()
/*     */         {
/*     */           public void run() {
/* 263 */             if (localAddress == null) {
/* 264 */               channel.connect(remoteAddress, connectPromise);
/*     */             } else {
/* 266 */               channel.connect(remoteAddress, localAddress, connectPromise);
/*     */             } 
/* 268 */             connectPromise.addListener((GenericFutureListener)ChannelFutureListener.CLOSE_ON_FAILURE);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   void init(Channel channel) throws Throwable {
/* 275 */     ChannelPipeline p = channel.pipeline();
/* 276 */     p.addLast(new ChannelHandler[] { this.config.handler() });
/*     */     
/* 278 */     setChannelOptions(channel, newOptionsArray(), logger);
/*     */     
/* 280 */     setAttributes(channel, newAttributesArray());
/* 281 */     Collection<ChannelInitializerExtension> extensions = getInitializerExtensions();
/* 282 */     if (!extensions.isEmpty()) {
/* 283 */       for (ChannelInitializerExtension extension : extensions) {
/*     */         try {
/* 285 */           extension.postInitializeClientChannel(channel);
/* 286 */         } catch (Exception e) {
/* 287 */           logger.warn("Exception thrown from postInitializeClientChannel", e);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Bootstrap validate() {
/* 295 */     super.validate();
/* 296 */     if (this.config.handler() == null) {
/* 297 */       throw new IllegalStateException("handler not set");
/*     */     }
/* 299 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Bootstrap clone() {
/* 305 */     return new Bootstrap(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Bootstrap clone(EventLoopGroup group) {
/* 314 */     Bootstrap bs = new Bootstrap(this);
/* 315 */     bs.group = group;
/* 316 */     return bs;
/*     */   }
/*     */ 
/*     */   
/*     */   public final BootstrapConfig config() {
/* 321 */     return this.config;
/*     */   }
/*     */   
/*     */   final SocketAddress remoteAddress() {
/* 325 */     return this.remoteAddress;
/*     */   }
/*     */   
/*     */   final AddressResolverGroup<?> resolver() {
/* 329 */     if (this.disableResolver) {
/* 330 */       return null;
/*     */     }
/* 332 */     return ExternalAddressResolver.getOrDefault(this.externalResolver);
/*     */   }
/*     */   
/*     */   public Bootstrap() {}
/*     */   
/*     */   static final class ExternalAddressResolver
/*     */   {
/*     */     final AddressResolverGroup<SocketAddress> resolverGroup;
/*     */     
/*     */     ExternalAddressResolver(AddressResolverGroup<?> resolverGroup) {
/* 342 */       this.resolverGroup = (AddressResolverGroup)resolverGroup;
/*     */     }
/*     */ 
/*     */     
/*     */     static AddressResolverGroup<SocketAddress> getOrDefault(ExternalAddressResolver externalResolver) {
/* 347 */       if (externalResolver == null) {
/* 348 */         return (AddressResolverGroup<SocketAddress>)DefaultAddressResolverGroup.INSTANCE;
/*     */       }
/*     */       
/* 351 */       return externalResolver.resolverGroup;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\bootstrap\Bootstrap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */