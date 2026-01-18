/*    */ package io.netty.handler.ssl;
/*    */ 
/*    */ import io.netty.util.internal.EmptyArrays;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class IdentityCipherSuiteFilter
/*    */   implements CipherSuiteFilter
/*    */ {
/* 32 */   public static final IdentityCipherSuiteFilter INSTANCE = new IdentityCipherSuiteFilter(true);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 37 */   public static final IdentityCipherSuiteFilter INSTANCE_DEFAULTING_TO_SUPPORTED_CIPHERS = new IdentityCipherSuiteFilter(false);
/*    */   
/*    */   private final boolean defaultToDefaultCiphers;
/*    */ 
/*    */   
/*    */   private IdentityCipherSuiteFilter(boolean defaultToDefaultCiphers) {
/* 43 */     this.defaultToDefaultCiphers = defaultToDefaultCiphers;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String[] filterCipherSuites(Iterable<String> ciphers, List<String> defaultCiphers, Set<String> supportedCiphers) {
/* 49 */     if (ciphers == null) {
/* 50 */       return this.defaultToDefaultCiphers ? 
/* 51 */         defaultCiphers.<String>toArray(EmptyArrays.EMPTY_STRINGS) : 
/* 52 */         supportedCiphers.<String>toArray(EmptyArrays.EMPTY_STRINGS);
/*    */     }
/* 54 */     List<String> newCiphers = new ArrayList<>(supportedCiphers.size());
/* 55 */     for (String c : ciphers) {
/* 56 */       if (c == null) {
/*    */         break;
/*    */       }
/* 59 */       newCiphers.add(c);
/*    */     } 
/* 61 */     return newCiphers.<String>toArray(EmptyArrays.EMPTY_STRINGS);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\IdentityCipherSuiteFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */