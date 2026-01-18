/*    */ package com.google.crypto.tink.prf;
/*    */ 
/*    */ import com.google.errorprone.annotations.Immutable;
/*    */ import java.security.GeneralSecurityException;
/*    */ import java.util.Map;
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
/*    */ 
/*    */ @Immutable
/*    */ public abstract class PrfSet
/*    */ {
/*    */   public abstract int getPrimaryId();
/*    */   
/*    */   public abstract Map<Integer, Prf> getPrfs() throws GeneralSecurityException;
/*    */   
/*    */   public byte[] computePrimary(byte[] input, int outputLength) throws GeneralSecurityException {
/* 44 */     return ((Prf)getPrfs().get(Integer.valueOf(getPrimaryId()))).compute(input, outputLength);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\prf\PrfSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */