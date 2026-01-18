/*     */ package com.google.crypto.tink.aead;
/*     */ 
/*     */ import com.google.errorprone.annotations.Immutable;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class XAesGcmParameters
/*     */   extends AeadParameters
/*     */ {
/*     */   private final Variant variant;
/*     */   private final int saltSizeBytes;
/*     */   
/*     */   @Immutable
/*     */   public static final class Variant
/*     */   {
/*  32 */     public static final Variant TINK = new Variant("TINK");
/*  33 */     public static final Variant NO_PREFIX = new Variant("NO_PREFIX");
/*     */     
/*     */     private final String name;
/*     */     
/*     */     private Variant(String name) {
/*  38 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  43 */       return this.name;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static XAesGcmParameters create(Variant variant, int saltSizeBytes) throws GeneralSecurityException {
/*  60 */     if (saltSizeBytes < 8 || saltSizeBytes > 12) {
/*  61 */       throw new GeneralSecurityException("Salt size must be between 8 and 12 bytes");
/*     */     }
/*  63 */     return new XAesGcmParameters(variant, saltSizeBytes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private XAesGcmParameters(Variant variant, int saltSizeBytes) {
/*  70 */     this.variant = variant;
/*  71 */     this.saltSizeBytes = saltSizeBytes;
/*     */   }
/*     */ 
/*     */   
/*     */   public Variant getVariant() {
/*  76 */     return this.variant;
/*     */   }
/*     */   
/*     */   public int getSaltSizeBytes() {
/*  80 */     return this.saltSizeBytes;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  85 */     if (!(o instanceof XAesGcmParameters)) {
/*  86 */       return false;
/*     */     }
/*  88 */     XAesGcmParameters that = (XAesGcmParameters)o;
/*  89 */     return (that.getVariant() == getVariant() && that.getSaltSizeBytes() == getSaltSizeBytes());
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  94 */     return Objects.hash(new Object[] { XAesGcmParameters.class, this.variant, Integer.valueOf(this.saltSizeBytes) });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasIdRequirement() {
/*  99 */     return (this.variant != Variant.NO_PREFIX);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 104 */     return "X-AES-GCM Parameters (variant: " + this.variant + "salt_size_bytes: " + this.saltSizeBytes + ")";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\aead\XAesGcmParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */