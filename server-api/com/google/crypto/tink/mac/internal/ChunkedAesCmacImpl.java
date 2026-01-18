/*    */ package com.google.crypto.tink.mac.internal;
/*    */ 
/*    */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*    */ import com.google.crypto.tink.internal.ConscryptUtil;
/*    */ import com.google.crypto.tink.mac.AesCmacKey;
/*    */ import com.google.crypto.tink.mac.ChunkedMac;
/*    */ import com.google.crypto.tink.mac.ChunkedMacComputation;
/*    */ import com.google.crypto.tink.mac.ChunkedMacVerification;
/*    */ import com.google.crypto.tink.util.Bytes;
/*    */ import com.google.errorprone.annotations.Immutable;
/*    */ import java.security.GeneralSecurityException;
/*    */ import java.security.Provider;
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
/*    */ @Immutable
/*    */ public final class ChunkedAesCmacImpl
/*    */   implements ChunkedMac
/*    */ {
/* 33 */   private static final TinkFipsUtil.AlgorithmFipsCompatibility FIPS = TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_NOT_FIPS;
/*    */ 
/*    */   
/*    */   private final AesCmacKey key;
/*    */ 
/*    */ 
/*    */   
/*    */   public ChunkedAesCmacImpl(AesCmacKey key) {
/* 41 */     this.key = key;
/*    */   }
/*    */ 
/*    */   
/*    */   public ChunkedMacComputation createComputation() throws GeneralSecurityException {
/* 46 */     return new ChunkedAesCmacComputation(this.key);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ChunkedMacVerification createVerification(byte[] tag) throws GeneralSecurityException {
/* 52 */     if (tag.length < this.key.getOutputPrefix().size()) {
/* 53 */       throw new GeneralSecurityException("Tag too short");
/*    */     }
/* 55 */     if (!this.key.getOutputPrefix().equals(Bytes.copyFrom(tag, 0, this.key.getOutputPrefix().size()))) {
/* 56 */       throw new GeneralSecurityException("Wrong tag prefix");
/*    */     }
/* 58 */     return ChunkedMacVerificationFromComputation.create(new ChunkedAesCmacComputation(this.key), tag);
/*    */   }
/*    */ 
/*    */   
/*    */   public static ChunkedMac create(AesCmacKey key) throws GeneralSecurityException {
/* 63 */     if (!FIPS.isCompatible()) {
/* 64 */       throw new GeneralSecurityException("Cannot use AES-CMAC in FIPS-mode.");
/*    */     }
/* 66 */     Provider conscrypt = ConscryptUtil.providerOrNull();
/* 67 */     if (conscrypt != null) {
/*    */       
/*    */       try {
/* 70 */         return ChunkedAesCmacConscrypt.create(key, conscrypt);
/* 71 */       } catch (GeneralSecurityException generalSecurityException) {}
/*    */     }
/*    */ 
/*    */     
/* 75 */     return new ChunkedAesCmacImpl(key);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\mac\internal\ChunkedAesCmacImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */