/*    */ package io.netty.channel.socket.nio;
/*    */ 
/*    */ import io.netty.channel.socket.SocketProtocolFamily;
/*    */ import io.netty.util.internal.PlatformDependent;
/*    */ import io.netty.util.internal.logging.InternalLogger;
/*    */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*    */ import java.io.IOException;
/*    */ import java.lang.reflect.InvocationTargetException;
/*    */ import java.lang.reflect.Method;
/*    */ import java.net.ProtocolFamily;
/*    */ import java.net.StandardProtocolFamily;
/*    */ import java.nio.channels.spi.SelectorProvider;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class SelectorProviderUtil
/*    */ {
/* 32 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(SelectorProviderUtil.class);
/*    */   
/*    */   static Method findOpenMethod(String methodName) {
/* 35 */     if (PlatformDependent.javaVersion() >= 15) {
/*    */       try {
/* 37 */         return SelectorProvider.class.getMethod(methodName, new Class[] { ProtocolFamily.class });
/* 38 */       } catch (Throwable e) {
/* 39 */         logger.debug("SelectorProvider.{}(ProtocolFamily) not available, will use default", methodName, e);
/*    */       } 
/*    */     }
/* 42 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static <C extends java.nio.channels.Channel> C newChannel(Method method, SelectorProvider provider, Object family) throws IOException {
/* 53 */     if (family != null && method != null) {
/*    */       
/*    */       try {
/* 56 */         return (C)method.invoke(provider, new Object[] { family });
/*    */       }
/* 58 */       catch (InvocationTargetException|IllegalAccessException e) {
/* 59 */         throw new IOException(e);
/*    */       } 
/*    */     }
/* 62 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   static <C extends java.nio.channels.Channel> C newChannel(Method method, SelectorProvider provider, SocketProtocolFamily family) throws IOException {
/* 67 */     if (family != null) {
/* 68 */       return newChannel(method, provider, family.toJdkFamily());
/*    */     }
/* 70 */     return null;
/*    */   }
/*    */   
/*    */   static <C extends java.nio.channels.Channel> C newDomainSocketChannel(Method method, SelectorProvider provider) throws IOException {
/* 74 */     return newChannel(method, provider, StandardProtocolFamily.valueOf("UNIX"));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\socket\nio\SelectorProviderUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */