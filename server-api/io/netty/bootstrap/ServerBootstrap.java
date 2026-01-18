/*     */ package io.netty.bootstrap;
/*     */ 
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelInboundHandlerAdapter;
/*     */ import io.netty.channel.ChannelInitializer;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.channel.EventLoopGroup;
/*     */ import io.netty.channel.ServerChannel;
/*     */ import io.netty.util.AttributeKey;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.util.Collection;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
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
/*     */ public class ServerBootstrap
/*     */   extends AbstractBootstrap<ServerBootstrap, ServerChannel>
/*     */ {
/*  48 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(ServerBootstrap.class);
/*     */ 
/*     */ 
/*     */   
/*  52 */   private final Map<ChannelOption<?>, Object> childOptions = new LinkedHashMap<>();
/*  53 */   private final Map<AttributeKey<?>, Object> childAttrs = new ConcurrentHashMap<>();
/*  54 */   private final ServerBootstrapConfig config = new ServerBootstrapConfig(this);
/*     */   
/*     */   private volatile EventLoopGroup childGroup;
/*     */   
/*     */   private volatile ChannelHandler childHandler;
/*     */   
/*     */   private ServerBootstrap(ServerBootstrap bootstrap) {
/*  61 */     super(bootstrap);
/*  62 */     this.childGroup = bootstrap.childGroup;
/*  63 */     this.childHandler = bootstrap.childHandler;
/*  64 */     synchronized (bootstrap.childOptions) {
/*  65 */       this.childOptions.putAll(bootstrap.childOptions);
/*     */     } 
/*  67 */     this.childAttrs.putAll(bootstrap.childAttrs);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServerBootstrap group(EventLoopGroup group) {
/*  75 */     return group(group, group);
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
/*     */   public ServerBootstrap group(EventLoopGroup parentGroup, EventLoopGroup childGroup) {
/*  87 */     super.group(parentGroup);
/*  88 */     if (this.childGroup != null) {
/*  89 */       throw new IllegalStateException("childGroup set already");
/*     */     }
/*  91 */     this.childGroup = (EventLoopGroup)ObjectUtil.checkNotNull(childGroup, "childGroup");
/*  92 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> ServerBootstrap childOption(ChannelOption<T> childOption, T value) {
/* 101 */     ObjectUtil.checkNotNull(childOption, "childOption");
/* 102 */     synchronized (this.childOptions) {
/* 103 */       if (value == null) {
/* 104 */         this.childOptions.remove(childOption);
/*     */       } else {
/* 106 */         this.childOptions.put(childOption, value);
/*     */       } 
/*     */     } 
/* 109 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> ServerBootstrap childAttr(AttributeKey<T> childKey, T value) {
/* 117 */     ObjectUtil.checkNotNull(childKey, "childKey");
/* 118 */     if (value == null) {
/* 119 */       this.childAttrs.remove(childKey);
/*     */     } else {
/* 121 */       this.childAttrs.put(childKey, value);
/*     */     } 
/* 123 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServerBootstrap childHandler(ChannelHandler childHandler) {
/* 130 */     this.childHandler = (ChannelHandler)ObjectUtil.checkNotNull(childHandler, "childHandler");
/* 131 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   void init(Channel channel) throws Throwable {
/* 136 */     setChannelOptions(channel, newOptionsArray(), logger);
/* 137 */     setAttributes(channel, newAttributesArray());
/*     */     
/* 139 */     ChannelPipeline p = channel.pipeline();
/*     */     
/* 141 */     final EventLoopGroup currentChildGroup = this.childGroup;
/* 142 */     final ChannelHandler currentChildHandler = this.childHandler;
/* 143 */     final Map.Entry[] currentChildOptions = (Map.Entry[])newOptionsArray(this.childOptions);
/* 144 */     final Map.Entry[] currentChildAttrs = (Map.Entry[])newAttributesArray(this.childAttrs);
/* 145 */     final Collection<ChannelInitializerExtension> extensions = getInitializerExtensions();
/*     */     
/* 147 */     p.addLast(new ChannelHandler[] { (ChannelHandler)new ChannelInitializer<Channel>()
/*     */           {
/*     */             public void initChannel(final Channel ch) {
/* 150 */               final ChannelPipeline pipeline = ch.pipeline();
/* 151 */               ChannelHandler handler = ServerBootstrap.this.config.handler();
/* 152 */               if (handler != null) {
/* 153 */                 pipeline.addLast(new ChannelHandler[] { handler });
/*     */               }
/*     */               
/* 156 */               ch.eventLoop().execute(new Runnable()
/*     */                   {
/*     */                     public void run() {
/* 159 */                       pipeline.addLast(new ChannelHandler[] { (ChannelHandler)new ServerBootstrap.ServerBootstrapAcceptor(this.val$ch, this.this$1.val$currentChildGroup, this.this$1.val$currentChildHandler, (Map.Entry<ChannelOption<?>, Object>[])this.this$1.val$currentChildOptions, (Map.Entry<AttributeKey<?>, Object>[])this.this$1.val$currentChildAttrs, this.this$1.val$extensions) });
/*     */                     }
/*     */                   });
/*     */             }
/*     */           } });
/*     */ 
/*     */     
/* 166 */     if (!extensions.isEmpty() && channel instanceof ServerChannel) {
/* 167 */       ServerChannel serverChannel = (ServerChannel)channel;
/* 168 */       for (ChannelInitializerExtension extension : extensions) {
/*     */         try {
/* 170 */           extension.postInitializeServerListenerChannel(serverChannel);
/* 171 */         } catch (Exception e) {
/* 172 */           logger.warn("Exception thrown from postInitializeServerListenerChannel", e);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ServerBootstrap validate() {
/* 180 */     super.validate();
/* 181 */     if (this.childHandler == null) {
/* 182 */       throw new IllegalStateException("childHandler not set");
/*     */     }
/* 184 */     if (this.childGroup == null) {
/* 185 */       logger.warn("childGroup is not set. Using parentGroup instead.");
/* 186 */       this.childGroup = this.config.group();
/*     */     } 
/* 188 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   private static class ServerBootstrapAcceptor
/*     */     extends ChannelInboundHandlerAdapter
/*     */   {
/*     */     private final EventLoopGroup childGroup;
/*     */     
/*     */     private final ChannelHandler childHandler;
/*     */     private final Map.Entry<ChannelOption<?>, Object>[] childOptions;
/*     */     private final Map.Entry<AttributeKey<?>, Object>[] childAttrs;
/*     */     private final Runnable enableAutoReadTask;
/*     */     private final Collection<ChannelInitializerExtension> extensions;
/*     */     
/*     */     ServerBootstrapAcceptor(final Channel channel, EventLoopGroup childGroup, ChannelHandler childHandler, Map.Entry<ChannelOption<?>, Object>[] childOptions, Map.Entry<AttributeKey<?>, Object>[] childAttrs, Collection<ChannelInitializerExtension> extensions) {
/* 204 */       this.childGroup = childGroup;
/* 205 */       this.childHandler = childHandler;
/* 206 */       this.childOptions = childOptions;
/* 207 */       this.childAttrs = childAttrs;
/* 208 */       this.extensions = extensions;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 215 */       this.enableAutoReadTask = new Runnable()
/*     */         {
/*     */           public void run() {
/* 218 */             channel.config().setAutoRead(true);
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void channelRead(ChannelHandlerContext ctx, Object msg) {
/* 226 */       final Channel child = (Channel)msg;
/*     */       
/* 228 */       child.pipeline().addLast(new ChannelHandler[] { this.childHandler });
/*     */       
/*     */       try {
/* 231 */         AbstractBootstrap.setChannelOptions(child, this.childOptions, ServerBootstrap.logger);
/* 232 */       } catch (Throwable cause) {
/* 233 */         forceClose(child, cause);
/*     */         return;
/*     */       } 
/* 236 */       AbstractBootstrap.setAttributes(child, this.childAttrs);
/*     */       
/* 238 */       if (!this.extensions.isEmpty()) {
/* 239 */         for (ChannelInitializerExtension extension : this.extensions) {
/*     */           try {
/* 241 */             extension.postInitializeServerChildChannel(child);
/* 242 */           } catch (Exception e) {
/* 243 */             ServerBootstrap.logger.warn("Exception thrown from postInitializeServerChildChannel", e);
/*     */           } 
/*     */         } 
/*     */       }
/*     */       
/*     */       try {
/* 249 */         this.childGroup.register(child).addListener((GenericFutureListener)new ChannelFutureListener()
/*     */             {
/*     */               public void operationComplete(ChannelFuture future) throws Exception {
/* 252 */                 if (!future.isSuccess()) {
/* 253 */                   ServerBootstrap.ServerBootstrapAcceptor.forceClose(child, future.cause());
/*     */                 }
/*     */               }
/*     */             });
/* 257 */       } catch (Throwable t) {
/* 258 */         forceClose(child, t);
/*     */       } 
/*     */     }
/*     */     
/*     */     private static void forceClose(Channel child, Throwable t) {
/* 263 */       child.unsafe().closeForcibly();
/* 264 */       ServerBootstrap.logger.warn("Failed to register an accepted channel: {}", child, t);
/*     */     }
/*     */ 
/*     */     
/*     */     public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
/* 269 */       ChannelConfig config = ctx.channel().config();
/* 270 */       if (config.isAutoRead()) {
/*     */ 
/*     */         
/* 273 */         config.setAutoRead(false);
/* 274 */         ctx.channel().eventLoop().schedule(this.enableAutoReadTask, 1L, TimeUnit.SECONDS);
/*     */       } 
/*     */ 
/*     */       
/* 278 */       ctx.fireExceptionCaught(cause);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ServerBootstrap clone() {
/* 285 */     return new ServerBootstrap(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public EventLoopGroup childGroup() {
/* 296 */     return this.childGroup;
/*     */   }
/*     */   
/*     */   final ChannelHandler childHandler() {
/* 300 */     return this.childHandler;
/*     */   }
/*     */   
/*     */   final Map<ChannelOption<?>, Object> childOptions() {
/* 304 */     synchronized (this.childOptions) {
/* 305 */       return copiedMap(this.childOptions);
/*     */     } 
/*     */   }
/*     */   
/*     */   final Map<AttributeKey<?>, Object> childAttrs() {
/* 310 */     return copiedMap(this.childAttrs);
/*     */   }
/*     */ 
/*     */   
/*     */   public final ServerBootstrapConfig config() {
/* 315 */     return this.config;
/*     */   }
/*     */   
/*     */   public ServerBootstrap() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\bootstrap\ServerBootstrap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */