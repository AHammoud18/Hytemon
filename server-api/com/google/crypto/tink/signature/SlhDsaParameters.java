/*     */ package com.google.crypto.tink.signature;
/*     */ 
/*     */ import com.google.errorprone.annotations.Immutable;
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
/*     */ public class SlhDsaParameters
/*     */   extends SignatureParameters
/*     */ {
/*     */   public static final int SLH_DSA_128_PRIVATE_KEY_SIZE_BYTES = 64;
/*     */   private final HashType hashType;
/*     */   private final SignatureType signatureType;
/*     */   private final Variant variant;
/*     */   private final int privateKeySize;
/*     */   
/*     */   @Immutable
/*     */   public static final class Variant
/*     */   {
/*  33 */     public static final Variant TINK = new Variant("TINK");
/*  34 */     public static final Variant NO_PREFIX = new Variant("NO_PREFIX");
/*     */     
/*     */     private final String name;
/*     */     
/*     */     private Variant(String name) {
/*  39 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  44 */       return this.name;
/*     */     }
/*     */   }
/*     */   
/*     */   @Immutable
/*     */   public static final class HashType
/*     */   {
/*  51 */     public static final HashType SHA2 = new HashType("SHA2");
/*  52 */     public static final HashType SHAKE = new HashType("SHAKE");
/*     */     
/*     */     private final String name;
/*     */     
/*     */     private HashType(String name) {
/*  57 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  62 */       return this.name;
/*     */     }
/*     */   }
/*     */   
/*     */   @Immutable
/*     */   public static final class SignatureType
/*     */   {
/*  69 */     public static final SignatureType FAST_SIGNING = new SignatureType("F");
/*  70 */     public static final SignatureType SMALL_SIGNATURE = new SignatureType("S");
/*     */     
/*     */     private final String name;
/*     */     
/*     */     private SignatureType(String name) {
/*  75 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  80 */       return this.name;
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
/*     */   public static SlhDsaParameters createSlhDsaWithSha2And128S(Variant variant) {
/*  97 */     return new SlhDsaParameters(HashType.SHA2, 64, SignatureType.SMALL_SIGNATURE, variant);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private SlhDsaParameters(HashType hashType, int privateKeySizeBytes, SignatureType signatureType, Variant variant) {
/* 103 */     this.hashType = hashType;
/* 104 */     this.privateKeySize = privateKeySizeBytes;
/* 105 */     this.signatureType = signatureType;
/* 106 */     this.variant = variant;
/*     */   }
/*     */   
/*     */   public HashType getHashType() {
/* 110 */     return this.hashType;
/*     */   }
/*     */   
/*     */   public SignatureType getSignatureType() {
/* 114 */     return this.signatureType;
/*     */   }
/*     */   
/*     */   public Variant getVariant() {
/* 118 */     return this.variant;
/*     */   }
/*     */   
/*     */   public int getPrivateKeySize() {
/* 122 */     return this.privateKeySize;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 127 */     if (!(o instanceof SlhDsaParameters)) {
/* 128 */       return false;
/*     */     }
/* 130 */     SlhDsaParameters other = (SlhDsaParameters)o;
/* 131 */     return (other.getHashType() == getHashType() && other
/* 132 */       .getSignatureType() == getSignatureType() && other
/* 133 */       .getVariant() == getVariant() && other
/* 134 */       .getPrivateKeySize() == getPrivateKeySize());
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 139 */     return Objects.hash(new Object[] { SlhDsaParameters.class, this.hashType, Integer.valueOf(this.privateKeySize), this.signatureType, this.variant });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasIdRequirement() {
/* 144 */     return (this.variant != Variant.NO_PREFIX);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 149 */     return "SLH-DSA-" + this.hashType
/* 150 */       .toString() + "-" + (this.privateKeySize * 2) + this.signatureType + " instance, variant: " + this.variant;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\signature\SlhDsaParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */