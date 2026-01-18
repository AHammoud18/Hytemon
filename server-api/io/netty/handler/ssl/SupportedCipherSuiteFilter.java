/*    */ package io.netty.handler.ssl;
/*    */ 
/*    */ import io.netty.util.internal.EmptyArrays;
/*    */ import io.netty.util.internal.ObjectUtil;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Set;
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
/*    */ 
/*    */ public final class SupportedCipherSuiteFilter
/*    */   implements CipherSuiteFilter
/*    */ {
/* 30 */   public static final SupportedCipherSuiteFilter INSTANCE = new SupportedCipherSuiteFilter();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String[] filterCipherSuites(Iterable<String> ciphers, List<String> defaultCiphers, Set<String> supportedCiphers) {
/*    */     List<String> newCiphers;
/* 37 */     ObjectUtil.checkNotNull(defaultCiphers, "defaultCiphers");
/* 38 */     ObjectUtil.checkNotNull(supportedCiphers, "supportedCiphers");
/*    */ 
/*    */     
/* 41 */     if (ciphers == null) {
/* 42 */       newCiphers = new ArrayList<>(defaultCiphers.size());
/* 43 */       ciphers = defaultCiphers;
/*    */     } else {
/* 45 */       newCiphers = new ArrayList<>(supportedCiphers.size());
/*    */     } 
/* 47 */     for (String c : ciphers) {
/* 48 */       if (c == null) {
/*    */         break;
/*    */       }
/* 51 */       if (supportedCiphers.contains(c)) {
/* 52 */         newCiphers.add(c);
/*    */       }
/*    */     } 
/* 55 */     return newCiphers.<String>toArray(EmptyArrays.EMPTY_STRINGS);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\SupportedCipherSuiteFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */