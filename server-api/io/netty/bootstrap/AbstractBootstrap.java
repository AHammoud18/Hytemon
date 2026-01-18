/*     */ package io.netty.bootstrap;
/*     */ 
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelFactory;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.channel.DefaultChannelPromise;
/*     */ import io.netty.channel.EventLoopGroup;
/*     */ import io.netty.channel.ReflectiveChannelFactory;
/*     */ import io.netty.util.AttributeKey;
/*     */ import io.netty.util.concurrent.EventExecutor;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import io.netty.util.concurrent.GlobalEventExecutor;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.SocketUtils;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import io.netty.util.internal.SystemPropertyUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
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
/*     */ public abstract class AbstractBootstrap<B extends AbstractBootstrap<B, C>, C extends Channel>
/*     */   implements Cloneable
/*     */ {
/*  57 */   private static final boolean CLOSE_ON_SET_OPTION_FAILURE = SystemPropertyUtil.getBoolean("io.netty.bootstrap.closeOnSetOptionFailure", true);
/*     */ 
/*     */   
/*  60 */   private static final Map.Entry<ChannelOption<?>, Object>[] EMPTY_OPTION_ARRAY = (Map.Entry<ChannelOption<?>, Object>[])new Map.Entry[0];
/*     */   
/*  62 */   private static final Map.Entry<AttributeKey<?>, Object>[] EMPTY_ATTRIBUTE_ARRAY = (Map.Entry<AttributeKey<?>, Object>[])new Map.Entry[0];
/*     */ 
/*     */   
/*     */   volatile EventLoopGroup group;
/*     */   
/*     */   private volatile ChannelFactory<? extends C> channelFactory;
/*     */   
/*     */   private volatile SocketAddress localAddress;
/*     */   
/*  71 */   private final Map<ChannelOption<?>, Object> options = new LinkedHashMap<>();
/*  72 */   private final Map<AttributeKey<?>, Object> attrs = new ConcurrentHashMap<>();
/*     */ 
/*     */   
/*     */   private volatile ChannelHandler handler;
/*     */   
/*     */   private volatile ClassLoader extensionsClassLoader;
/*     */ 
/*     */   
/*     */   AbstractBootstrap(AbstractBootstrap<B, C> bootstrap) {
/*  81 */     this.group = bootstrap.group;
/*  82 */     this.channelFactory = bootstrap.channelFactory;
/*  83 */     this.handler = bootstrap.handler;
/*  84 */     this.localAddress = bootstrap.localAddress;
/*  85 */     synchronized (bootstrap.options) {
/*  86 */       this.options.putAll(bootstrap.options);
/*     */     } 
/*  88 */     this.attrs.putAll(bootstrap.attrs);
/*  89 */     this.extensionsClassLoader = bootstrap.extensionsClassLoader;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public B group(EventLoopGroup group) {
/*  97 */     ObjectUtil.checkNotNull(group, "group");
/*  98 */     if (this.group != null) {
/*  99 */       throw new IllegalStateException("group set already");
/*     */     }
/* 101 */     this.group = group;
/* 102 */     return self();
/*     */   }
/*     */ 
/*     */   
/*     */   private B self() {
/* 107 */     return (B)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public B channel(Class<? extends C> channelClass) {
/* 116 */     return channelFactory((ChannelFactory<? extends C>)new ReflectiveChannelFactory(
/* 117 */           (Class)ObjectUtil.checkNotNull(channelClass, "channelClass")));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public B channelFactory(ChannelFactory<? extends C> channelFactory) {
/* 126 */     ObjectUtil.checkNotNull(channelFactory, "channelFactory");
/* 127 */     if (this.channelFactory != null) {
/* 128 */       throw new IllegalStateException("channelFactory set already");
/*     */     }
/*     */     
/* 131 */     this.channelFactory = channelFactory;
/* 132 */     return self();
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
/*     */   public B channelFactory(ChannelFactory<? extends C> channelFactory) {
/* 144 */     return channelFactory((ChannelFactory<? extends C>)channelFactory);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public B localAddress(SocketAddress localAddress) {
/* 151 */     this.localAddress = localAddress;
/* 152 */     return self();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public B localAddress(int inetPort) {
/* 159 */     return localAddress(new InetSocketAddress(inetPort));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public B localAddress(String inetHost, int inetPort) {
/* 166 */     return localAddress(SocketUtils.socketAddress(inetHost, inetPort));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public B localAddress(InetAddress inetHost, int inetPort) {
/* 173 */     return localAddress(new InetSocketAddress(inetHost, inetPort));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> B option(ChannelOption<T> option, T value) {
/* 181 */     ObjectUtil.checkNotNull(option, "option");
/* 182 */     synchronized (this.options) {
/* 183 */       if (value == null) {
/* 184 */         this.options.remove(option);
/*     */       } else {
/* 186 */         this.options.put(option, value);
/*     */       } 
/*     */     } 
/* 189 */     return self();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> B attr(AttributeKey<T> key, T value) {
/* 197 */     ObjectUtil.checkNotNull(key, "key");
/* 198 */     if (value == null) {
/* 199 */       this.attrs.remove(key);
/*     */     } else {
/* 201 */       this.attrs.put(key, value);
/*     */     } 
/* 203 */     return self();
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
/*     */   public B extensionsClassLoader(ClassLoader classLoader) {
/* 215 */     this.extensionsClassLoader = classLoader;
/* 216 */     return self();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public B validate() {
/* 224 */     if (this.group == null) {
/* 225 */       throw new IllegalStateException("group not set");
/*     */     }
/* 227 */     if (this.channelFactory == null) {
/* 228 */       throw new IllegalStateException("channel or channelFactory not set");
/*     */     }
/* 230 */     return self();
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
/*     */   public ChannelFuture register() {
/* 246 */     validate();
/* 247 */     return initAndRegister();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture bind() {
/* 254 */     validate();
/* 255 */     SocketAddress localAddress = this.localAddress;
/* 256 */     if (localAddress == null) {
/* 257 */       throw new IllegalStateException("localAddress not set");
/*     */     }
/* 259 */     return doBind(localAddress);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture bind(int inetPort) {
/* 266 */     return bind(new InetSocketAddress(inetPort));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture bind(String inetHost, int inetPort) {
/* 273 */     return bind(SocketUtils.socketAddress(inetHost, inetPort));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture bind(InetAddress inetHost, int inetPort) {
/* 280 */     return bind(new InetSocketAddress(inetHost, inetPort));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture bind(SocketAddress localAddress) {
/* 287 */     validate();
/* 288 */     return doBind((SocketAddress)ObjectUtil.checkNotNull(localAddress, "localAddress"));
/*     */   }
/*     */   
/*     */   private ChannelFuture doBind(final SocketAddress localAddress) {
/* 292 */     final ChannelFuture regFuture = initAndRegister();
/* 293 */     final Channel channel = regFuture.channel();
/* 294 */     if (regFuture.cause() != null) {
/* 295 */       return regFuture;
/*     */     }
/*     */     
/* 298 */     if (regFuture.isDone()) {
/*     */       
/* 300 */       ChannelPromise channelPromise = channel.newPromise();
/* 301 */       doBind0(regFuture, channel, localAddress, channelPromise);
/* 302 */       return (ChannelFuture)channelPromise;
/*     */     } 
/*     */     
/* 305 */     final PendingRegistrationPromise promise = new PendingRegistrationPromise(channel);
/* 306 */     regFuture.addListener((GenericFutureListener)new ChannelFutureListener()
/*     */         {
/*     */           public void operationComplete(ChannelFuture future) throws Exception {
/* 309 */             Throwable cause = future.cause();
/* 310 */             if (cause != null) {
/*     */ 
/*     */               
/* 313 */               promise.setFailure(cause);
/*     */             }
/*     */             else {
/*     */               
/* 317 */               promise.registered();
/*     */               
/* 319 */               AbstractBootstrap.doBind0(regFuture, channel, localAddress, (ChannelPromise)promise);
/*     */             } 
/*     */           }
/*     */         });
/* 323 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */   
/*     */   final ChannelFuture initAndRegister() {
/* 328 */     Channel channel = null;
/*     */     try {
/* 330 */       channel = (Channel)this.channelFactory.newChannel();
/* 331 */       init(channel);
/* 332 */     } catch (Throwable t) {
/* 333 */       if (channel != null) {
/*     */         
/* 335 */         channel.unsafe().closeForcibly();
/*     */         
/* 337 */         return (ChannelFuture)(new DefaultChannelPromise(channel, (EventExecutor)GlobalEventExecutor.INSTANCE)).setFailure(t);
/*     */       } 
/*     */       
/* 340 */       return (ChannelFuture)(new DefaultChannelPromise((Channel)new FailedChannel(), (EventExecutor)GlobalEventExecutor.INSTANCE)).setFailure(t);
/*     */     } 
/*     */     
/* 343 */     ChannelFuture regFuture = config().group().register(channel);
/* 344 */     if (regFuture.cause() != null) {
/* 345 */       if (channel.isRegistered()) {
/* 346 */         channel.close();
/*     */       } else {
/* 348 */         channel.unsafe().closeForcibly();
/*     */       } 
/*     */     }
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
/* 361 */     return regFuture;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   Collection<ChannelInitializerExtension> getInitializerExtensions() {
/* 367 */     ClassLoader loader = this.extensionsClassLoader;
/* 368 */     if (loader == null) {
/* 369 */       loader = getClass().getClassLoader();
/*     */     }
/* 371 */     return ChannelInitializerExtensions.getExtensions().extensions(loader);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void doBind0(final ChannelFuture regFuture, final Channel channel, final SocketAddress localAddress, final ChannelPromise promise) {
/* 380 */     channel.eventLoop().execute(new Runnable()
/*     */         {
/*     */           public void run() {
/* 383 */             if (regFuture.isSuccess()) {
/* 384 */               channel.bind(localAddress, promise).addListener((GenericFutureListener)ChannelFutureListener.CLOSE_ON_FAILURE);
/*     */             } else {
/* 386 */               promise.setFailure(regFuture.cause());
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public B handler(ChannelHandler handler) {
/* 396 */     this.handler = (ChannelHandler)ObjectUtil.checkNotNull(handler, "handler");
/* 397 */     return self();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public final EventLoopGroup group() {
/* 407 */     return this.group;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final Map.Entry<ChannelOption<?>, Object>[] newOptionsArray() {
/* 417 */     return newOptionsArray(this.options);
/*     */   }
/*     */   
/*     */   static Map.Entry<ChannelOption<?>, Object>[] newOptionsArray(Map<ChannelOption<?>, Object> options) {
/* 421 */     synchronized (options) {
/* 422 */       return (Map.Entry<ChannelOption<?>, Object>[])(new LinkedHashMap<>(options)).entrySet().toArray((Object[])EMPTY_OPTION_ARRAY);
/*     */     } 
/*     */   }
/*     */   
/*     */   final Map.Entry<AttributeKey<?>, Object>[] newAttributesArray() {
/* 427 */     return newAttributesArray(attrs0());
/*     */   }
/*     */   
/*     */   static Map.Entry<AttributeKey<?>, Object>[] newAttributesArray(Map<AttributeKey<?>, Object> attributes) {
/* 431 */     return (Map.Entry<AttributeKey<?>, Object>[])attributes.entrySet().toArray((Object[])EMPTY_ATTRIBUTE_ARRAY);
/*     */   }
/*     */   
/*     */   final Map<ChannelOption<?>, Object> options0() {
/* 435 */     return this.options;
/*     */   }
/*     */   
/*     */   final Map<AttributeKey<?>, Object> attrs0() {
/* 439 */     return this.attrs;
/*     */   }
/*     */   
/*     */   final SocketAddress localAddress() {
/* 443 */     return this.localAddress;
/*     */   }
/*     */ 
/*     */   
/*     */   final ChannelFactory<? extends C> channelFactory() {
/* 448 */     return this.channelFactory;
/*     */   }
/*     */   
/*     */   final ChannelHandler handler() {
/* 452 */     return this.handler;
/*     */   }
/*     */   
/*     */   final Map<ChannelOption<?>, Object> options() {
/* 456 */     synchronized (this.options) {
/* 457 */       return copiedMap(this.options);
/*     */     } 
/*     */   }
/*     */   
/*     */   final Map<AttributeKey<?>, Object> attrs() {
/* 462 */     return copiedMap(this.attrs);
/*     */   }
/*     */   
/*     */   static <K, V> Map<K, V> copiedMap(Map<K, V> map) {
/* 466 */     if (map.isEmpty()) {
/* 467 */       return Collections.emptyMap();
/*     */     }
/* 469 */     return Collections.unmodifiableMap(new HashMap<>(map));
/*     */   }
/*     */   
/*     */   static void setAttributes(Channel channel, Map.Entry<AttributeKey<?>, Object>[] attrs) {
/* 473 */     for (Map.Entry<AttributeKey<?>, Object> e : attrs) {
/*     */       
/* 475 */       AttributeKey<Object> key = (AttributeKey<Object>)e.getKey();
/* 476 */       channel.attr(key).set(e.getValue());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static void setChannelOptions(Channel channel, Map.Entry<ChannelOption<?>, Object>[] options, InternalLogger logger) throws Throwable {
/* 482 */     for (Map.Entry<ChannelOption<?>, Object> e : options) {
/* 483 */       setChannelOption(channel, e.getKey(), e.getValue(), logger);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void setChannelOption(Channel channel, ChannelOption<?> option, Object value, InternalLogger logger) throws Throwable {
/*     */     try {
/* 491 */       if (!channel.config().setOption(option, value)) {
/* 492 */         logger.warn("Unknown channel option '{}' for channel '{}' of type '{}'", new Object[] { option, channel, channel
/* 493 */               .getClass() });
/*     */       }
/* 495 */     } catch (Throwable t) {
/* 496 */       logger.warn("Failed to set channel option '{}' with value '{}' for channel '{}' of type '{}'", new Object[] { option, value, channel, channel
/*     */             
/* 498 */             .getClass(), t });
/* 499 */       if (CLOSE_ON_SET_OPTION_FAILURE)
/*     */       {
/* 501 */         throw t;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 510 */     StringBuilder buf = (new StringBuilder()).append(StringUtil.simpleClassName(this)).append('(').append(config()).append(')');
/* 511 */     return buf.toString();
/*     */   }
/*     */   AbstractBootstrap() {}
/*     */   public abstract B clone();
/*     */   
/*     */   abstract void init(Channel paramChannel) throws Throwable;
/*     */   
/*     */   public abstract AbstractBootstrapConfig<B, C> config();
/*     */   
/*     */   static final class PendingRegistrationPromise extends DefaultChannelPromise { PendingRegistrationPromise(Channel channel) {
/* 521 */       super(channel);
/*     */     }
/*     */     private volatile boolean registered;
/*     */     void registered() {
/* 525 */       this.registered = true;
/*     */     }
/*     */ 
/*     */     
/*     */     protected EventExecutor executor() {
/* 530 */       if (this.registered)
/*     */       {
/*     */ 
/*     */         
/* 534 */         return super.executor();
/*     */       }
/*     */       
/* 537 */       return (EventExecutor)GlobalEventExecutor.INSTANCE;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\bootstrap\AbstractBootstrap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */