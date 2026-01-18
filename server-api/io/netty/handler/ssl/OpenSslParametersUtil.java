/*    */ package io.netty.handler.ssl;
/*    */ 
/*    */ import io.netty.util.internal.PlatformDependent;
/*    */ import java.lang.invoke.MethodHandle;
/*    */ import java.lang.invoke.MethodHandles;
/*    */ import java.lang.invoke.MethodType;
/*    */ import java.security.AccessController;
/*    */ import javax.net.ssl.SSLParameters;
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
/*    */ final class OpenSslParametersUtil
/*    */ {
/*    */   private static final MethodHandle GET_NAMED_GROUPS;
/*    */   private static final MethodHandle SET_NAMED_GROUPS;
/*    */   
/*    */   static {
/* 33 */     MethodHandle getNamedGroups = null;
/* 34 */     MethodHandle setNamedGroups = null;
/* 35 */     if (PlatformDependent.javaVersion() >= 20) {
/* 36 */       MethodHandles.Lookup lookup = MethodHandles.lookup();
/* 37 */       getNamedGroups = obtainHandle(lookup, "getNamedGroups", 
/* 38 */           MethodType.methodType(String[].class));
/* 39 */       setNamedGroups = obtainHandle(lookup, "setNamedGroups", 
/* 40 */           MethodType.methodType(void.class, String[].class));
/*    */     } 
/* 42 */     GET_NAMED_GROUPS = getNamedGroups;
/* 43 */     SET_NAMED_GROUPS = setNamedGroups;
/*    */   }
/*    */ 
/*    */   
/*    */   private static MethodHandle obtainHandle(MethodHandles.Lookup lookup, String methodName, MethodType type) {
/* 48 */     return AccessController.<MethodHandle>doPrivileged(() -> {
/*    */           try {
/*    */             return lookup.findVirtual(SSLParameters.class, methodName, type);
/* 51 */           } catch (UnsupportedOperationException|SecurityException|NoSuchMethodException|IllegalAccessException e) {
/*    */             return null;
/*    */           } 
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   static String[] getNamesGroups(SSLParameters parameters) {
/* 60 */     if (GET_NAMED_GROUPS == null) {
/* 61 */       return null;
/*    */     }
/*    */     try {
/* 64 */       return GET_NAMED_GROUPS.invoke(parameters);
/* 65 */     } catch (Throwable t) {
/*    */       
/* 67 */       return null;
/*    */     } 
/*    */   }
/*    */   
/*    */   static void setNamesGroups(SSLParameters parameters, String[] names) {
/* 72 */     if (SET_NAMED_GROUPS == null) {
/*    */       return;
/*    */     }
/*    */     try {
/* 76 */       SET_NAMED_GROUPS.invoke(parameters, names);
/* 77 */     } catch (Throwable throwable) {}
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\OpenSslParametersUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */