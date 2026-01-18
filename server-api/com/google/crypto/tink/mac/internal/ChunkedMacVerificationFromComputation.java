/*    */ package com.google.crypto.tink.mac.internal;
/*    */ 
/*    */ import com.google.crypto.tink.mac.ChunkedMacComputation;
/*    */ import com.google.crypto.tink.mac.ChunkedMacVerification;
/*    */ import com.google.crypto.tink.util.Bytes;
/*    */ import java.nio.ByteBuffer;
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
/*    */ 
/*    */ 
/*    */ final class ChunkedMacVerificationFromComputation
/*    */   implements ChunkedMacVerification
/*    */ {
/*    */   private final Bytes tag;
/*    */   private final ChunkedMacComputation macComputation;
/*    */   
/*    */   private ChunkedMacVerificationFromComputation(ChunkedMacComputation macComputation, byte[] tag) {
/* 33 */     this.macComputation = macComputation;
/* 34 */     this.tag = Bytes.copyFrom(tag);
/*    */   }
/*    */ 
/*    */   
/*    */   public void update(ByteBuffer data) throws GeneralSecurityException {
/* 39 */     this.macComputation.update(data);
/*    */   }
/*    */ 
/*    */   
/*    */   public void verifyMac() throws GeneralSecurityException {
/* 44 */     byte[] other = this.macComputation.computeMac();
/* 45 */     if (!this.tag.equals(Bytes.copyFrom(other))) {
/* 46 */       throw new GeneralSecurityException("invalid MAC");
/*    */     }
/*    */   }
/*    */   
/*    */   static ChunkedMacVerification create(ChunkedMacComputation macComputation, byte[] tag) {
/* 51 */     return new ChunkedMacVerificationFromComputation(macComputation, tag);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\mac\internal\ChunkedMacVerificationFromComputation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */