/*    */ package com.google.crypto.tink.jwt;
/*    */ 
/*    */ import com.google.crypto.tink.Key;
/*    */ import com.google.crypto.tink.Parameters;
/*    */ import com.google.crypto.tink.PrivateKey;
/*    */ import com.google.errorprone.annotations.Immutable;
/*    */ import java.util.Optional;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Immutable
/*    */ public abstract class JwtSignaturePrivateKey
/*    */   extends Key
/*    */   implements PrivateKey
/*    */ {
/*    */   public Optional<String> getKid() {
/* 50 */     return getPublicKey().getKid();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Integer getIdRequirementOrNull() {
/* 59 */     return getPublicKey().getIdRequirementOrNull();
/*    */   }
/*    */   
/*    */   public abstract JwtSignaturePublicKey getPublicKey();
/*    */   
/*    */   public abstract JwtSignatureParameters getParameters();
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\jwt\JwtSignaturePrivateKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */