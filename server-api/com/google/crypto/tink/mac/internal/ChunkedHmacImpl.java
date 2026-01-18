/*    */ package com.google.crypto.tink.mac.internal;
/*    */ 
/*    */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*    */ import com.google.crypto.tink.mac.ChunkedMac;
/*    */ import com.google.crypto.tink.mac.ChunkedMacComputation;
/*    */ import com.google.crypto.tink.mac.ChunkedMacVerification;
/*    */ import com.google.crypto.tink.mac.HmacKey;
/*    */ import com.google.crypto.tink.util.Bytes;
/*    */ import com.google.errorprone.annotations.Immutable;
/*    */ import java.security.GeneralSecurityException;
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
/*    */ public final class ChunkedHmacImpl
/*    */   implements ChunkedMac
/*    */ {
/* 31 */   private static final TinkFipsUtil.AlgorithmFipsCompatibility FIPS = TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_REQUIRES_BORINGCRYPTO;
/*    */ 
/*    */   
/*    */   private final HmacKey key;
/*    */ 
/*    */   
/*    */   public ChunkedHmacImpl(HmacKey key) throws GeneralSecurityException {
/* 38 */     if (!FIPS.isCompatible()) {
/* 39 */       throw new GeneralSecurityException("Can not use HMAC in FIPS-mode, as BoringCrypto module is not available.");
/*    */     }
/*    */     
/* 42 */     this.key = key;
/*    */   }
/*    */ 
/*    */   
/*    */   public ChunkedMacComputation createComputation() throws GeneralSecurityException {
/* 47 */     return new ChunkedHmacComputation(this.key);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ChunkedMacVerification createVerification(byte[] tag) throws GeneralSecurityException {
/* 53 */     if (tag.length < this.key.getOutputPrefix().size()) {
/* 54 */       throw new GeneralSecurityException("Tag too short");
/*    */     }
/* 56 */     if (!this.key.getOutputPrefix().equals(Bytes.copyFrom(tag, 0, this.key.getOutputPrefix().size()))) {
/* 57 */       throw new GeneralSecurityException("Wrong tag prefix");
/*    */     }
/* 59 */     return ChunkedMacVerificationFromComputation.create(new ChunkedHmacComputation(this.key), tag);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\mac\internal\ChunkedHmacImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */