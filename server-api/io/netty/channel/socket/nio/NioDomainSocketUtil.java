/*    */ package io.netty.channel.socket.nio;
/*    */ 
/*    */ import java.lang.reflect.Method;
/*    */ import java.net.SocketAddress;
/*    */ import java.nio.file.Path;
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
/*    */ 
/*    */ final class NioDomainSocketUtil
/*    */ {
/*    */   private static final Method OF_METHOD;
/*    */   private static final Method GET_PATH_METHOD;
/*    */   
/*    */   static {
/*    */     Method ofMethod, getPathMethod;
/*    */     try {
/* 32 */       Class<?> clazz = Class.forName("java.net.UnixDomainSocketAddress");
/* 33 */       ofMethod = clazz.getMethod("of", new Class[] { String.class });
/* 34 */       getPathMethod = clazz.getMethod("getPath", new Class[0]);
/*    */     }
/* 36 */     catch (Throwable error) {
/* 37 */       ofMethod = null;
/* 38 */       getPathMethod = null;
/*    */     } 
/* 40 */     OF_METHOD = ofMethod;
/* 41 */     GET_PATH_METHOD = getPathMethod;
/*    */   }
/*    */   
/*    */   static SocketAddress newUnixDomainSocketAddress(String path) {
/* 45 */     if (OF_METHOD == null) {
/* 46 */       throw new IllegalStateException();
/*    */     }
/*    */     try {
/* 49 */       return (SocketAddress)OF_METHOD.invoke(null, new Object[] { path });
/* 50 */     } catch (IllegalAccessException|java.lang.reflect.InvocationTargetException e) {
/* 51 */       throw new IllegalStateException(e);
/*    */     } 
/*    */   }
/*    */   
/*    */   static void deleteSocketFile(SocketAddress address) {
/* 56 */     if (GET_PATH_METHOD == null) {
/* 57 */       throw new IllegalStateException();
/*    */     }
/*    */     try {
/* 60 */       Path path = (Path)GET_PATH_METHOD.invoke(address, new Object[0]);
/* 61 */       if (path != null) {
/* 62 */         path.toFile().delete();
/*    */       }
/* 64 */     } catch (IllegalAccessException|java.lang.reflect.InvocationTargetException e) {
/* 65 */       throw new IllegalStateException(e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\socket\nio\NioDomainSocketUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */