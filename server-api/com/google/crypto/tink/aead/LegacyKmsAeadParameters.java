/*    */ package com.google.crypto.tink.aead;
/*    */ 
/*    */ import com.google.errorprone.annotations.Immutable;
/*    */ import java.security.GeneralSecurityException;
/*    */ import java.util.Objects;
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
/*    */ public final class LegacyKmsAeadParameters
/*    */   extends AeadParameters
/*    */ {
/*    */   private final String keyUri;
/*    */   private final Variant variant;
/*    */   
/*    */   @Immutable
/*    */   public static final class Variant
/*    */   {
/* 32 */     public static final Variant TINK = new Variant("TINK");
/* 33 */     public static final Variant NO_PREFIX = new Variant("NO_PREFIX");
/*    */     
/*    */     private final String name;
/*    */     
/*    */     private Variant(String name) {
/* 38 */       this.name = name;
/*    */     }
/*    */ 
/*    */     
/*    */     public String toString() {
/* 43 */       return this.name;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private LegacyKmsAeadParameters(String keyUri, Variant variant) {
/* 51 */     this.keyUri = keyUri;
/* 52 */     this.variant = variant;
/*    */   }
/*    */   
/*    */   public static LegacyKmsAeadParameters create(String keyUri) throws GeneralSecurityException {
/* 56 */     return new LegacyKmsAeadParameters(keyUri, Variant.NO_PREFIX);
/*    */   }
/*    */   
/*    */   public static LegacyKmsAeadParameters create(String keyUri, Variant variant) {
/* 60 */     return new LegacyKmsAeadParameters(keyUri, variant);
/*    */   }
/*    */   
/*    */   public String keyUri() {
/* 64 */     return this.keyUri;
/*    */   }
/*    */   
/*    */   public Variant variant() {
/* 68 */     return this.variant;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasIdRequirement() {
/* 73 */     return (this.variant != Variant.NO_PREFIX);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 78 */     if (!(o instanceof LegacyKmsAeadParameters)) {
/* 79 */       return false;
/*    */     }
/* 81 */     LegacyKmsAeadParameters that = (LegacyKmsAeadParameters)o;
/* 82 */     return (that.keyUri.equals(this.keyUri) && that.variant.equals(this.variant));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 87 */     return Objects.hash(new Object[] { LegacyKmsAeadParameters.class, this.keyUri, this.variant });
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 92 */     return "LegacyKmsAead Parameters (keyUri: " + this.keyUri + ", variant: " + this.variant + ")";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\aead\LegacyKmsAeadParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */