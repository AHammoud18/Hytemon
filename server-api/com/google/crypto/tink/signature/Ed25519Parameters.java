/*    */ package com.google.crypto.tink.signature;
/*    */ 
/*    */ import com.google.errorprone.annotations.Immutable;
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
/*    */ 
/*    */ 
/*    */ public final class Ed25519Parameters
/*    */   extends SignatureParameters
/*    */ {
/*    */   private final Variant variant;
/*    */   
/*    */   @Immutable
/*    */   public static final class Variant
/*    */   {
/* 32 */     public static final Variant TINK = new Variant("TINK");
/* 33 */     public static final Variant CRUNCHY = new Variant("CRUNCHY");
/* 34 */     public static final Variant LEGACY = new Variant("LEGACY");
/* 35 */     public static final Variant NO_PREFIX = new Variant("NO_PREFIX");
/*    */     
/*    */     private final String name;
/*    */     
/*    */     private Variant(String name) {
/* 40 */       this.name = name;
/*    */     }
/*    */ 
/*    */     
/*    */     public String toString() {
/* 45 */       return this.name;
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public static Ed25519Parameters create() {
/* 51 */     return new Ed25519Parameters(Variant.NO_PREFIX);
/*    */   }
/*    */ 
/*    */   
/*    */   public static Ed25519Parameters create(Variant variant) {
/* 56 */     return new Ed25519Parameters(variant);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private Ed25519Parameters(Variant variant) {
/* 62 */     this.variant = variant;
/*    */   }
/*    */ 
/*    */   
/*    */   public Variant getVariant() {
/* 67 */     return this.variant;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 72 */     if (!(o instanceof Ed25519Parameters)) {
/* 73 */       return false;
/*    */     }
/* 75 */     Ed25519Parameters that = (Ed25519Parameters)o;
/* 76 */     return (that.getVariant() == getVariant());
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 81 */     return Objects.hash(new Object[] { Ed25519Parameters.class, this.variant });
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasIdRequirement() {
/* 86 */     return (this.variant != Variant.NO_PREFIX);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 91 */     return "Ed25519 Parameters (variant: " + this.variant + ")";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\signature\Ed25519Parameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */