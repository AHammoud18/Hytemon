/*     */ package io.netty.channel;
/*     */ 
/*     */ import io.netty.util.concurrent.FastThreadLocal;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.lang.annotation.ElementType;
/*     */ import java.lang.annotation.Retention;
/*     */ import java.lang.annotation.RetentionPolicy;
/*     */ import java.lang.annotation.Target;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.SocketAddress;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.Map;
/*     */ import java.util.WeakHashMap;
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
/*     */ final class ChannelHandlerMask
/*     */ {
/*  36 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(ChannelHandlerMask.class);
/*     */   
/*     */   static final int MASK_EXCEPTION_CAUGHT = 1;
/*     */   
/*     */   static final int MASK_CHANNEL_REGISTERED = 2;
/*     */   
/*     */   static final int MASK_CHANNEL_UNREGISTERED = 4;
/*     */   
/*     */   static final int MASK_CHANNEL_ACTIVE = 8;
/*     */   
/*     */   static final int MASK_CHANNEL_INACTIVE = 16;
/*     */   
/*     */   static final int MASK_CHANNEL_READ = 32;
/*     */   static final int MASK_CHANNEL_READ_COMPLETE = 64;
/*     */   static final int MASK_USER_EVENT_TRIGGERED = 128;
/*     */   static final int MASK_CHANNEL_WRITABILITY_CHANGED = 256;
/*     */   static final int MASK_BIND = 512;
/*     */   static final int MASK_CONNECT = 1024;
/*     */   static final int MASK_DISCONNECT = 2048;
/*     */   static final int MASK_CLOSE = 4096;
/*     */   static final int MASK_DEREGISTER = 8192;
/*     */   static final int MASK_READ = 16384;
/*     */   static final int MASK_WRITE = 32768;
/*     */   static final int MASK_FLUSH = 65536;
/*     */   static final int MASK_ONLY_INBOUND = 510;
/*     */   private static final int MASK_ALL_INBOUND = 511;
/*     */   static final int MASK_ONLY_OUTBOUND = 130560;
/*     */   private static final int MASK_ALL_OUTBOUND = 130561;
/*     */   
/*  65 */   private static final FastThreadLocal<Map<Class<? extends ChannelHandler>, Integer>> MASKS = new FastThreadLocal<Map<Class<? extends ChannelHandler>, Integer>>()
/*     */     {
/*     */       protected Map<Class<? extends ChannelHandler>, Integer> initialValue()
/*     */       {
/*  69 */         return new WeakHashMap<>(32);
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int mask(Class<? extends ChannelHandler> clazz) {
/*  79 */     Map<Class<? extends ChannelHandler>, Integer> cache = (Map<Class<? extends ChannelHandler>, Integer>)MASKS.get();
/*  80 */     Integer mask = cache.get(clazz);
/*  81 */     if (mask == null) {
/*  82 */       mask = Integer.valueOf(mask0(clazz));
/*  83 */       cache.put(clazz, mask);
/*     */     } 
/*  85 */     return mask.intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int mask0(Class<? extends ChannelHandler> handlerType) {
/*  92 */     int mask = 1;
/*     */     try {
/*  94 */       if (ChannelInboundHandler.class.isAssignableFrom(handlerType)) {
/*  95 */         mask |= 0x1FF;
/*     */         
/*  97 */         if (isSkippable(handlerType, "channelRegistered", new Class[] { ChannelHandlerContext.class })) {
/*  98 */           mask &= 0xFFFFFFFD;
/*     */         }
/* 100 */         if (isSkippable(handlerType, "channelUnregistered", new Class[] { ChannelHandlerContext.class })) {
/* 101 */           mask &= 0xFFFFFFFB;
/*     */         }
/* 103 */         if (isSkippable(handlerType, "channelActive", new Class[] { ChannelHandlerContext.class })) {
/* 104 */           mask &= 0xFFFFFFF7;
/*     */         }
/* 106 */         if (isSkippable(handlerType, "channelInactive", new Class[] { ChannelHandlerContext.class })) {
/* 107 */           mask &= 0xFFFFFFEF;
/*     */         }
/* 109 */         if (isSkippable(handlerType, "channelRead", new Class[] { ChannelHandlerContext.class, Object.class })) {
/* 110 */           mask &= 0xFFFFFFDF;
/*     */         }
/* 112 */         if (isSkippable(handlerType, "channelReadComplete", new Class[] { ChannelHandlerContext.class })) {
/* 113 */           mask &= 0xFFFFFFBF;
/*     */         }
/* 115 */         if (isSkippable(handlerType, "channelWritabilityChanged", new Class[] { ChannelHandlerContext.class })) {
/* 116 */           mask &= 0xFFFFFEFF;
/*     */         }
/* 118 */         if (isSkippable(handlerType, "userEventTriggered", new Class[] { ChannelHandlerContext.class, Object.class })) {
/* 119 */           mask &= 0xFFFFFF7F;
/*     */         }
/*     */       } 
/*     */       
/* 123 */       if (ChannelOutboundHandler.class.isAssignableFrom(handlerType)) {
/* 124 */         mask |= 0x1FE01;
/*     */         
/* 126 */         if (isSkippable(handlerType, "bind", new Class[] { ChannelHandlerContext.class, SocketAddress.class, ChannelPromise.class }))
/*     */         {
/* 128 */           mask &= 0xFFFFFDFF;
/*     */         }
/* 130 */         if (isSkippable(handlerType, "connect", new Class[] { ChannelHandlerContext.class, SocketAddress.class, SocketAddress.class, ChannelPromise.class }))
/*     */         {
/* 132 */           mask &= 0xFFFFFBFF;
/*     */         }
/* 134 */         if (isSkippable(handlerType, "disconnect", new Class[] { ChannelHandlerContext.class, ChannelPromise.class })) {
/* 135 */           mask &= 0xFFFFF7FF;
/*     */         }
/* 137 */         if (isSkippable(handlerType, "close", new Class[] { ChannelHandlerContext.class, ChannelPromise.class })) {
/* 138 */           mask &= 0xFFFFEFFF;
/*     */         }
/* 140 */         if (isSkippable(handlerType, "deregister", new Class[] { ChannelHandlerContext.class, ChannelPromise.class })) {
/* 141 */           mask &= 0xFFFFDFFF;
/*     */         }
/* 143 */         if (isSkippable(handlerType, "read", new Class[] { ChannelHandlerContext.class })) {
/* 144 */           mask &= 0xFFFFBFFF;
/*     */         }
/* 146 */         if (isSkippable(handlerType, "write", new Class[] { ChannelHandlerContext.class, Object.class, ChannelPromise.class }))
/*     */         {
/* 148 */           mask &= 0xFFFF7FFF;
/*     */         }
/* 150 */         if (isSkippable(handlerType, "flush", new Class[] { ChannelHandlerContext.class })) {
/* 151 */           mask &= 0xFFFEFFFF;
/*     */         }
/*     */       } 
/*     */       
/* 155 */       if (isSkippable(handlerType, "exceptionCaught", new Class[] { ChannelHandlerContext.class, Throwable.class })) {
/* 156 */         mask &= 0xFFFFFFFE;
/*     */       }
/* 158 */     } catch (Exception e) {
/*     */       
/* 160 */       PlatformDependent.throwException(e);
/*     */     } 
/*     */     
/* 163 */     return mask;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isSkippable(final Class<?> handlerType, final String methodName, Class<?>... paramTypes) throws Exception {
/* 168 */     return ((Boolean)AccessController.<Boolean>doPrivileged(new PrivilegedExceptionAction<Boolean>()
/*     */         {
/*     */           public Boolean run() throws Exception {
/*     */             Method m;
/*     */             try {
/* 173 */               m = handlerType.getMethod(methodName, paramTypes);
/* 174 */             } catch (NoSuchMethodException e) {
/* 175 */               if (ChannelHandlerMask.logger.isDebugEnabled()) {
/* 176 */                 ChannelHandlerMask.logger.debug("Class {} missing method {}, assume we can not skip execution", new Object[] { this.val$handlerType, this.val$methodName, e });
/*     */               }
/*     */               
/* 179 */               return Boolean.valueOf(false);
/*     */             } 
/* 181 */             return Boolean.valueOf(m.isAnnotationPresent((Class)ChannelHandlerMask.Skip.class));
/*     */           }
/*     */         })).booleanValue();
/*     */   }
/*     */   
/*     */   @Target({ElementType.METHOD})
/*     */   @Retention(RetentionPolicy.RUNTIME)
/*     */   static @interface Skip {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\ChannelHandlerMask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */