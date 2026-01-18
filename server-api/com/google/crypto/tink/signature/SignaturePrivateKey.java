/*    */ package com.google.crypto.tink.signature;
/*    */ 
/*    */ import com.google.crypto.tink.Key;
/*    */ import com.google.crypto.tink.Parameters;
/*    */ import com.google.crypto.tink.PrivateKey;
/*    */ import com.google.crypto.tink.util.Bytes;
/*    */ import com.google.errorprone.annotations.Immutable;
/*    */ import javax.annotation.Nullable;
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
/*    */ 
/*    */ @Immutable
/*    */ public abstract class SignaturePrivateKey
/*    */   extends Key
/*    */   implements PrivateKey
/*    */ {
/*    */   public final Bytes getOutputPrefix() {
/* 46 */     return getPublicKey().getOutputPrefix();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Integer getIdRequirementOrNull() {
/* 52 */     return getPublicKey().getIdRequirementOrNull();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SignatureParameters getParameters() {
/* 62 */     return getPublicKey().getParameters();
/*    */   }
/*    */   
/*    */   public abstract SignaturePublicKey getPublicKey();
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\signature\SignaturePrivateKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */