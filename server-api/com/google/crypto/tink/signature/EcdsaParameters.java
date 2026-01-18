/*     */ package com.google.crypto.tink.signature;
/*     */ 
/*     */ import com.google.crypto.tink.internal.EllipticCurvesUtil;
/*     */ import com.google.errorprone.annotations.CanIgnoreReturnValue;
/*     */ import com.google.errorprone.annotations.Immutable;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.spec.ECParameterSpec;
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
/*     */ public final class EcdsaParameters
/*     */   extends SignatureParameters
/*     */ {
/*     */   private final SignatureEncoding signatureEncoding;
/*     */   private final CurveType curveType;
/*     */   private final HashType hashType;
/*     */   private final Variant variant;
/*     */   
/*     */   @Immutable
/*     */   public static final class Variant
/*     */   {
/*  36 */     public static final Variant TINK = new Variant("TINK");
/*  37 */     public static final Variant CRUNCHY = new Variant("CRUNCHY");
/*  38 */     public static final Variant LEGACY = new Variant("LEGACY");
/*  39 */     public static final Variant NO_PREFIX = new Variant("NO_PREFIX");
/*     */     
/*     */     private final String name;
/*     */     
/*     */     private Variant(String name) {
/*  44 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  49 */       return this.name;
/*     */     }
/*     */   }
/*     */   
/*     */   @Immutable
/*     */   public static final class SignatureEncoding
/*     */   {
/*  56 */     public static final SignatureEncoding IEEE_P1363 = new SignatureEncoding("IEEE_P1363");
/*  57 */     public static final SignatureEncoding DER = new SignatureEncoding("DER");
/*     */     
/*     */     private final String name;
/*     */     
/*     */     private SignatureEncoding(String name) {
/*  62 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  67 */       return this.name;
/*     */     }
/*     */   }
/*     */   
/*     */   @Immutable
/*     */   public static final class CurveType
/*     */   {
/*  74 */     public static final CurveType NIST_P256 = new CurveType("NIST_P256", EllipticCurvesUtil.NIST_P256_PARAMS);
/*     */     
/*  76 */     public static final CurveType NIST_P384 = new CurveType("NIST_P384", EllipticCurvesUtil.NIST_P384_PARAMS);
/*     */     
/*  78 */     public static final CurveType NIST_P521 = new CurveType("NIST_P521", EllipticCurvesUtil.NIST_P521_PARAMS);
/*     */     
/*     */     private final String name;
/*     */     
/*     */     private final ECParameterSpec spec;
/*     */ 
/*     */     
/*     */     private CurveType(String name, ECParameterSpec spec) {
/*  86 */       this.name = name;
/*  87 */       this.spec = spec;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  92 */       return this.name;
/*     */     }
/*     */     
/*     */     public ECParameterSpec toParameterSpec() {
/*  96 */       return this.spec;
/*     */     }
/*     */ 
/*     */     
/*     */     public static CurveType fromParameterSpec(ECParameterSpec spec) throws GeneralSecurityException {
/* 101 */       if (EllipticCurvesUtil.isSameEcParameterSpec(spec, NIST_P256.toParameterSpec())) {
/* 102 */         return NIST_P256;
/*     */       }
/* 104 */       if (EllipticCurvesUtil.isSameEcParameterSpec(spec, NIST_P384.toParameterSpec())) {
/* 105 */         return NIST_P384;
/*     */       }
/* 107 */       if (EllipticCurvesUtil.isSameEcParameterSpec(spec, NIST_P521.toParameterSpec())) {
/* 108 */         return NIST_P521;
/*     */       }
/* 110 */       throw new GeneralSecurityException("unknown ECParameterSpec");
/*     */     }
/*     */   }
/*     */   
/*     */   @Immutable
/*     */   public static final class HashType
/*     */   {
/* 117 */     public static final HashType SHA256 = new HashType("SHA256");
/* 118 */     public static final HashType SHA384 = new HashType("SHA384");
/* 119 */     public static final HashType SHA512 = new HashType("SHA512");
/*     */     
/*     */     private final String name;
/*     */     
/*     */     private HashType(String name) {
/* 124 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 129 */       return this.name;
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class Builder
/*     */   {
/* 135 */     private EcdsaParameters.SignatureEncoding signatureEncoding = null;
/* 136 */     private EcdsaParameters.CurveType curveType = null;
/* 137 */     private EcdsaParameters.HashType hashType = null;
/* 138 */     private EcdsaParameters.Variant variant = EcdsaParameters.Variant.NO_PREFIX;
/*     */ 
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setSignatureEncoding(EcdsaParameters.SignatureEncoding signatureEncoding) {
/* 144 */       this.signatureEncoding = signatureEncoding;
/* 145 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setCurveType(EcdsaParameters.CurveType curveType) {
/* 150 */       this.curveType = curveType;
/* 151 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setHashType(EcdsaParameters.HashType hashType) {
/* 156 */       this.hashType = hashType;
/* 157 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setVariant(EcdsaParameters.Variant variant) {
/* 162 */       this.variant = variant;
/* 163 */       return this;
/*     */     }
/*     */     
/*     */     public EcdsaParameters build() throws GeneralSecurityException {
/* 167 */       if (this.signatureEncoding == null) {
/* 168 */         throw new GeneralSecurityException("signature encoding is not set");
/*     */       }
/* 170 */       if (this.curveType == null) {
/* 171 */         throw new GeneralSecurityException("EC curve type is not set");
/*     */       }
/* 173 */       if (this.hashType == null) {
/* 174 */         throw new GeneralSecurityException("hash type is not set");
/*     */       }
/* 176 */       if (this.variant == null) {
/* 177 */         throw new GeneralSecurityException("variant is not set");
/*     */       }
/*     */       
/* 180 */       if (this.curveType == EcdsaParameters.CurveType.NIST_P256 && 
/* 181 */         this.hashType != EcdsaParameters.HashType.SHA256) {
/* 182 */         throw new GeneralSecurityException("NIST_P256 requires SHA256");
/*     */       }
/*     */       
/* 185 */       if (this.curveType == EcdsaParameters.CurveType.NIST_P384 && 
/* 186 */         this.hashType != EcdsaParameters.HashType.SHA384 && this.hashType != EcdsaParameters.HashType.SHA512) {
/* 187 */         throw new GeneralSecurityException("NIST_P384 requires SHA384 or SHA512");
/*     */       }
/*     */       
/* 190 */       if (this.curveType == EcdsaParameters.CurveType.NIST_P521 && 
/* 191 */         this.hashType != EcdsaParameters.HashType.SHA512) {
/* 192 */         throw new GeneralSecurityException("NIST_P521 requires SHA512");
/*     */       }
/*     */       
/* 195 */       return new EcdsaParameters(this.signatureEncoding, this.curveType, this.hashType, this.variant);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Builder() {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private EcdsaParameters(SignatureEncoding signatureEncoding, CurveType curveType, HashType hashType, Variant variant) {
/* 209 */     this.signatureEncoding = signatureEncoding;
/* 210 */     this.curveType = curveType;
/* 211 */     this.hashType = hashType;
/* 212 */     this.variant = variant;
/*     */   }
/*     */   
/*     */   public static Builder builder() {
/* 216 */     return new Builder();
/*     */   }
/*     */   
/*     */   public SignatureEncoding getSignatureEncoding() {
/* 220 */     return this.signatureEncoding;
/*     */   }
/*     */   
/*     */   public CurveType getCurveType() {
/* 224 */     return this.curveType;
/*     */   }
/*     */   
/*     */   public HashType getHashType() {
/* 228 */     return this.hashType;
/*     */   }
/*     */   
/*     */   public Variant getVariant() {
/* 232 */     return this.variant;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 237 */     if (!(o instanceof EcdsaParameters)) {
/* 238 */       return false;
/*     */     }
/* 240 */     EcdsaParameters that = (EcdsaParameters)o;
/* 241 */     return (that.getSignatureEncoding() == getSignatureEncoding() && that
/* 242 */       .getCurveType() == getCurveType() && that
/* 243 */       .getHashType() == getHashType() && that
/* 244 */       .getVariant() == getVariant());
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 249 */     return Objects.hash(new Object[] { EcdsaParameters.class, this.signatureEncoding, this.curveType, this.hashType, this.variant });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasIdRequirement() {
/* 254 */     return (this.variant != Variant.NO_PREFIX);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 259 */     return "ECDSA Parameters (variant: " + this.variant + ", hashType: " + this.hashType + ", encoding: " + this.signatureEncoding + ", curve: " + this.curveType + ")";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\signature\EcdsaParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */