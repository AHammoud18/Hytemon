/*    */ package io.netty.handler.ssl.util;
/*    */ 
/*    */ import io.netty.util.internal.ObjectUtil;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class FingerprintTrustManagerFactoryBuilder
/*    */ {
/*    */   private final String algorithm;
/* 39 */   private final List<String> fingerprints = new ArrayList<>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   FingerprintTrustManagerFactoryBuilder(String algorithm) {
/* 47 */     this.algorithm = (String)ObjectUtil.checkNotNull(algorithm, "algorithm");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public FingerprintTrustManagerFactoryBuilder fingerprints(CharSequence... fingerprints) {
/* 57 */     return fingerprints(Arrays.asList((CharSequence[])ObjectUtil.checkNotNull(fingerprints, "fingerprints")));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public FingerprintTrustManagerFactoryBuilder fingerprints(Iterable<? extends CharSequence> fingerprints) {
/* 67 */     ObjectUtil.checkNotNull(fingerprints, "fingerprints");
/* 68 */     for (CharSequence fingerprint : fingerprints) {
/* 69 */       ObjectUtil.checkNotNullWithIAE(fingerprint, "fingerprint");
/* 70 */       this.fingerprints.add(fingerprint.toString());
/*    */     } 
/* 72 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public FingerprintTrustManagerFactory build() {
/* 81 */     if (this.fingerprints.isEmpty()) {
/* 82 */       throw new IllegalStateException("No fingerprints provided");
/*    */     }
/* 84 */     return new FingerprintTrustManagerFactory(this.algorithm, 
/* 85 */         FingerprintTrustManagerFactory.toFingerprintArray(this.fingerprints));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ss\\util\FingerprintTrustManagerFactoryBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */