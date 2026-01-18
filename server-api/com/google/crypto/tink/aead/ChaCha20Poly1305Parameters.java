/*    */ package com.google.crypto.tink.aead;
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
/*    */ public final class ChaCha20Poly1305Parameters
/*    */   extends AeadParameters
/*    */ {
/*    */   private final Variant variant;
/*    */   
/*    */   @Immutable
/*    */   public static final class Variant
/*    */   {
/* 32 */     public static final Variant TINK = new Variant("TINK");
/* 33 */     public static final Variant CRUNCHY = new Variant("CRUNCHY");
/* 34 */     public static final Variant NO_PREFIX = new Variant("NO_PREFIX");
/*    */     
/*    */     private final String name;
/*    */     
/*    */     private Variant(String name) {
/* 39 */       this.name = name;
/*    */     }
/*    */ 
/*    */     
/*    */     public String toString() {
/* 44 */       return this.name;
/*    */     }
/*    */   }
/*    */   
/*    */   public static ChaCha20Poly1305Parameters create() {
/* 49 */     return new ChaCha20Poly1305Parameters(Variant.NO_PREFIX);
/*    */   }
/*    */   
/*    */   public static ChaCha20Poly1305Parameters create(Variant variant) {
/* 53 */     return new ChaCha20Poly1305Parameters(variant);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private ChaCha20Poly1305Parameters(Variant variant) {
/* 59 */     this.variant = variant;
/*    */   }
/*    */ 
/*    */   
/*    */   public Variant getVariant() {
/* 64 */     return this.variant;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 69 */     if (!(o instanceof ChaCha20Poly1305Parameters)) {
/* 70 */       return false;
/*    */     }
/* 72 */     ChaCha20Poly1305Parameters that = (ChaCha20Poly1305Parameters)o;
/* 73 */     return (that.getVariant() == getVariant());
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 78 */     return Objects.hash(new Object[] { ChaCha20Poly1305Parameters.class, this.variant });
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasIdRequirement() {
/* 83 */     return (this.variant != Variant.NO_PREFIX);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 88 */     return "ChaCha20Poly1305 Parameters (variant: " + this.variant + ")";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\aead\ChaCha20Poly1305Parameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */