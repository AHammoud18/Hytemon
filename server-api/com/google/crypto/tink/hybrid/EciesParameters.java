/*     */ package com.google.crypto.tink.hybrid;
/*     */ 
/*     */ import com.google.crypto.tink.Parameters;
/*     */ import com.google.crypto.tink.aead.AesCtrHmacAeadParameters;
/*     */ import com.google.crypto.tink.aead.AesGcmParameters;
/*     */ import com.google.crypto.tink.aead.XChaCha20Poly1305Parameters;
/*     */ import com.google.crypto.tink.daead.AesSivParameters;
/*     */ import com.google.crypto.tink.internal.TinkBugException;
/*     */ import com.google.crypto.tink.util.Bytes;
/*     */ import com.google.errorprone.annotations.CanIgnoreReturnValue;
/*     */ import com.google.errorprone.annotations.Immutable;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
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
/*     */ public final class EciesParameters
/*     */   extends HybridParameters
/*     */ {
/*     */   private final CurveType curveType;
/*     */   private final HashType hashType;
/*     */   
/*     */   private static Set<Parameters> listAcceptedDemParameters() throws GeneralSecurityException {
/*  53 */     HashSet<Parameters> acceptedDemParameters = new HashSet<>();
/*     */     
/*  55 */     acceptedDemParameters.add(
/*  56 */         AesGcmParameters.builder()
/*  57 */         .setIvSizeBytes(12)
/*  58 */         .setKeySizeBytes(16)
/*  59 */         .setTagSizeBytes(16)
/*  60 */         .setVariant(AesGcmParameters.Variant.NO_PREFIX)
/*  61 */         .build());
/*     */     
/*  63 */     acceptedDemParameters.add(
/*  64 */         AesGcmParameters.builder()
/*  65 */         .setIvSizeBytes(12)
/*  66 */         .setKeySizeBytes(32)
/*  67 */         .setTagSizeBytes(16)
/*  68 */         .setVariant(AesGcmParameters.Variant.NO_PREFIX)
/*  69 */         .build());
/*     */     
/*  71 */     acceptedDemParameters.add(
/*  72 */         AesCtrHmacAeadParameters.builder()
/*  73 */         .setAesKeySizeBytes(16)
/*  74 */         .setHmacKeySizeBytes(32)
/*  75 */         .setTagSizeBytes(16)
/*  76 */         .setIvSizeBytes(16)
/*  77 */         .setHashType(AesCtrHmacAeadParameters.HashType.SHA256)
/*  78 */         .setVariant(AesCtrHmacAeadParameters.Variant.NO_PREFIX)
/*  79 */         .build());
/*     */     
/*  81 */     acceptedDemParameters.add(
/*  82 */         AesCtrHmacAeadParameters.builder()
/*  83 */         .setAesKeySizeBytes(32)
/*  84 */         .setHmacKeySizeBytes(32)
/*  85 */         .setTagSizeBytes(32)
/*  86 */         .setIvSizeBytes(16)
/*  87 */         .setHashType(AesCtrHmacAeadParameters.HashType.SHA256)
/*  88 */         .setVariant(AesCtrHmacAeadParameters.Variant.NO_PREFIX)
/*  89 */         .build());
/*     */     
/*  91 */     acceptedDemParameters.add(XChaCha20Poly1305Parameters.create());
/*     */     
/*  93 */     acceptedDemParameters.add(
/*  94 */         AesSivParameters.builder()
/*  95 */         .setKeySizeBytes(64)
/*  96 */         .setVariant(AesSivParameters.Variant.NO_PREFIX)
/*  97 */         .build());
/*  98 */     return Collections.unmodifiableSet(acceptedDemParameters);
/*     */   }
/*     */   @Nullable
/*     */   private final PointFormat nistCurvePointFormat; private final Variant variant; private final Parameters demParameters;
/* 102 */   private static final Set<Parameters> acceptedDemParameters = (Set<Parameters>)TinkBugException.exceptionIsBug(() -> listAcceptedDemParameters());
/*     */   @Nullable
/*     */   private final Bytes salt;
/*     */   
/*     */   @Immutable
/*     */   public static final class Variant {
/* 108 */     public static final Variant TINK = new Variant("TINK");
/*     */ 
/*     */     
/* 111 */     public static final Variant CRUNCHY = new Variant("CRUNCHY");
/*     */ 
/*     */     
/* 114 */     public static final Variant NO_PREFIX = new Variant("NO_PREFIX");
/*     */     
/*     */     private final String name;
/*     */     
/*     */     private Variant(String name) {
/* 119 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 124 */       return this.name;
/*     */     }
/*     */   }
/*     */   
/*     */   @Immutable
/*     */   public static final class CurveType
/*     */   {
/* 131 */     public static final CurveType NIST_P256 = new CurveType("NIST_P256");
/* 132 */     public static final CurveType NIST_P384 = new CurveType("NIST_P384");
/* 133 */     public static final CurveType NIST_P521 = new CurveType("NIST_P521");
/* 134 */     public static final CurveType X25519 = new CurveType("X25519");
/*     */     
/*     */     private final String name;
/*     */     
/*     */     private CurveType(String name) {
/* 139 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 144 */       return this.name;
/*     */     }
/*     */   }
/*     */   
/*     */   @Immutable
/*     */   public static final class HashType
/*     */   {
/* 151 */     public static final HashType SHA1 = new HashType("SHA1");
/* 152 */     public static final HashType SHA224 = new HashType("SHA224");
/* 153 */     public static final HashType SHA256 = new HashType("SHA256");
/* 154 */     public static final HashType SHA384 = new HashType("SHA384");
/* 155 */     public static final HashType SHA512 = new HashType("SHA512");
/*     */     
/*     */     private final String name;
/*     */     
/*     */     private HashType(String name) {
/* 160 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 165 */       return this.name;
/*     */     }
/*     */   }
/*     */   
/*     */   @Immutable
/*     */   public static final class PointFormat
/*     */   {
/* 172 */     public static final PointFormat COMPRESSED = new PointFormat("COMPRESSED");
/* 173 */     public static final PointFormat UNCOMPRESSED = new PointFormat("UNCOMPRESSED");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 179 */     public static final PointFormat LEGACY_UNCOMPRESSED = new PointFormat("LEGACY_UNCOMPRESSED");
/*     */     
/*     */     private final String name;
/*     */     
/*     */     private PointFormat(String name) {
/* 184 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 189 */       return this.name;
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class Builder
/*     */   {
/* 195 */     private EciesParameters.CurveType curveType = null;
/* 196 */     private EciesParameters.HashType hashType = null;
/* 197 */     private EciesParameters.PointFormat nistCurvePointFormat = null;
/* 198 */     private Parameters demParameters = null;
/* 199 */     private EciesParameters.Variant variant = EciesParameters.Variant.NO_PREFIX; @Nullable
/* 200 */     private Bytes salt = null;
/*     */ 
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setCurveType(EciesParameters.CurveType curveType) {
/* 206 */       this.curveType = curveType;
/* 207 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setHashType(EciesParameters.HashType hashType) {
/* 212 */       this.hashType = hashType;
/* 213 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setNistCurvePointFormat(EciesParameters.PointFormat pointFormat) {
/* 218 */       this.nistCurvePointFormat = pointFormat;
/* 219 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setDemParameters(Parameters demParameters) throws GeneralSecurityException {
/* 228 */       if (!EciesParameters.acceptedDemParameters.contains(demParameters)) {
/* 229 */         throw new GeneralSecurityException("Invalid DEM parameters " + demParameters + "; only AES128_GCM_RAW, AES256_GCM_RAW, AES128_CTR_HMAC_SHA256_RAW, AES256_CTR_HMAC_SHA256_RAW XCHACHA20_POLY1305_RAW and AES256_SIV_RAW are currently supported.");
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 236 */       this.demParameters = demParameters;
/* 237 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setVariant(EciesParameters.Variant variant) {
/* 242 */       this.variant = variant;
/* 243 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setSalt(Bytes salt) {
/* 249 */       if (salt.size() == 0) {
/* 250 */         this.salt = null;
/* 251 */         return this;
/*     */       } 
/*     */       
/* 254 */       this.salt = salt;
/* 255 */       return this;
/*     */     }
/*     */     
/*     */     public EciesParameters build() throws GeneralSecurityException {
/* 259 */       if (this.curveType == null) {
/* 260 */         throw new GeneralSecurityException("Elliptic curve type is not set");
/*     */       }
/* 262 */       if (this.hashType == null) {
/* 263 */         throw new GeneralSecurityException("Hash type is not set");
/*     */       }
/* 265 */       if (this.demParameters == null) {
/* 266 */         throw new GeneralSecurityException("DEM parameters are not set");
/*     */       }
/* 268 */       if (this.variant == null) {
/* 269 */         throw new GeneralSecurityException("Variant is not set");
/*     */       }
/*     */       
/* 272 */       if (this.curveType != EciesParameters.CurveType.X25519 && this.nistCurvePointFormat == null) {
/* 273 */         throw new GeneralSecurityException("Point format is not set");
/*     */       }
/* 275 */       if (this.curveType == EciesParameters.CurveType.X25519 && this.nistCurvePointFormat != null) {
/* 276 */         throw new GeneralSecurityException("For Curve25519 point format must not be set");
/*     */       }
/* 278 */       return new EciesParameters(this.curveType, this.hashType, this.nistCurvePointFormat, this.demParameters, this.variant, this.salt);
/*     */     }
/*     */ 
/*     */ 
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
/*     */ 
/*     */ 
/*     */   
/*     */   private EciesParameters(CurveType curveType, HashType hashType, @Nullable PointFormat pointFormat, Parameters demParameters, Variant variant, Bytes salt) {
/* 297 */     this.curveType = curveType;
/* 298 */     this.hashType = hashType;
/* 299 */     this.nistCurvePointFormat = pointFormat;
/* 300 */     this.demParameters = demParameters;
/* 301 */     this.variant = variant;
/* 302 */     this.salt = salt;
/*     */   }
/*     */   
/*     */   public static Builder builder() {
/* 306 */     return new Builder();
/*     */   }
/*     */   
/*     */   public CurveType getCurveType() {
/* 310 */     return this.curveType;
/*     */   }
/*     */   
/*     */   public HashType getHashType() {
/* 314 */     return this.hashType;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public PointFormat getNistCurvePointFormat() {
/* 319 */     return this.nistCurvePointFormat;
/*     */   }
/*     */   
/*     */   public Parameters getDemParameters() {
/* 323 */     return this.demParameters;
/*     */   }
/*     */   
/*     */   public Variant getVariant() {
/* 327 */     return this.variant;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Bytes getSalt() {
/* 339 */     return this.salt;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasIdRequirement() {
/* 344 */     return (this.variant != Variant.NO_PREFIX);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 349 */     if (!(o instanceof EciesParameters)) {
/* 350 */       return false;
/*     */     }
/* 352 */     EciesParameters that = (EciesParameters)o;
/* 353 */     return (Objects.equals(that.getCurveType(), getCurveType()) && 
/* 354 */       Objects.equals(that.getHashType(), getHashType()) && 
/* 355 */       Objects.equals(that.getNistCurvePointFormat(), getNistCurvePointFormat()) && 
/* 356 */       Objects.equals(that.getDemParameters(), getDemParameters()) && 
/* 357 */       Objects.equals(that.getVariant(), getVariant()) && 
/* 358 */       Objects.equals(that.getSalt(), getSalt()));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 363 */     return Objects.hash(new Object[] { EciesParameters.class, this.curveType, this.hashType, this.nistCurvePointFormat, this.demParameters, this.variant, this.salt });
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
/*     */   public String toString() {
/* 375 */     return String.format("EciesParameters(curveType=%s, hashType=%s, pointFormat=%s, demParameters=%s, variant=%s, salt=%s)", new Object[] { this.curveType, this.hashType, this.nistCurvePointFormat, this.demParameters, this.variant, this.salt });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\hybrid\EciesParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */