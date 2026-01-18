/*    */ package com.google.crypto.tink.hybrid;
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
/*    */ @Immutable
/*    */ public abstract class HybridPrivateKey
/*    */   extends Key
/*    */   implements PrivateKey
/*    */ {
/*    */   public final Bytes getOutputPrefix() {
/* 41 */     return getPublicKey().getOutputPrefix();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Integer getIdRequirementOrNull() {
/* 47 */     return getPublicKey().getIdRequirementOrNull();
/*    */   }
/*    */ 
/*    */   
/*    */   public HybridParameters getParameters() {
/* 52 */     return getPublicKey().getParameters();
/*    */   }
/*    */   
/*    */   public abstract HybridPublicKey getPublicKey();
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\hybrid\HybridPrivateKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */