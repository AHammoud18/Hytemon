/*     */ package com.nimbusds.jose.jwk;
/*     */ 
/*     */ import com.nimbusds.jose.Algorithm;
/*     */ import com.nimbusds.jose.JOSEException;
/*     */ import com.nimbusds.jose.shaded.jcip.Immutable;
/*     */ import com.nimbusds.jose.util.Base64;
/*     */ import com.nimbusds.jose.util.Base64URL;
/*     */ import com.nimbusds.jose.util.ByteUtils;
/*     */ import com.nimbusds.jose.util.IntegerOverflowException;
/*     */ import com.nimbusds.jose.util.JSONObjectUtils;
/*     */ import java.net.URI;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.Key;
/*     */ import java.security.KeyStore;
/*     */ import java.security.KeyStoreException;
/*     */ import java.security.UnrecoverableKeyException;
/*     */ import java.text.ParseException;
/*     */ import java.util.Date;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import javax.crypto.SecretKey;
/*     */ import javax.crypto.spec.SecretKeySpec;
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
/*     */ @Immutable
/*     */ public final class OctetSequenceKey
/*     */   extends JWK
/*     */   implements SecretJWK
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private final Base64URL k;
/*     */   
/*     */   public static class Builder
/*     */   {
/*     */     private final Base64URL k;
/*     */     private KeyUse use;
/*     */     private Set<KeyOperation> ops;
/*     */     private Algorithm alg;
/*     */     private String kid;
/*     */     private URI x5u;
/*     */     @Deprecated
/*     */     private Base64URL x5t;
/*     */     private Base64URL x5t256;
/*     */     private List<Base64> x5c;
/*     */     private Date exp;
/*     */     private Date nbf;
/*     */     private Date iat;
/*     */     private KeyRevocation revocation;
/*     */     private KeyStore ks;
/*     */     
/*     */     public Builder(Base64URL k) {
/* 187 */       this.k = Objects.<Base64URL>requireNonNull(k);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder(byte[] key) {
/* 199 */       this(Base64URL.encode(key));
/*     */       
/* 201 */       if (key.length == 0) {
/* 202 */         throw new IllegalArgumentException("The key must have a positive length");
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder(SecretKey secretKey) {
/* 215 */       this(secretKey.getEncoded());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder(OctetSequenceKey octJWK) {
/* 227 */       this.k = octJWK.k;
/* 228 */       this.use = octJWK.getKeyUse();
/* 229 */       this.ops = octJWK.getKeyOperations();
/* 230 */       this.alg = octJWK.getAlgorithm();
/* 231 */       this.kid = octJWK.getKeyID();
/* 232 */       this.x5u = octJWK.getX509CertURL();
/* 233 */       this.x5t = octJWK.getX509CertThumbprint();
/* 234 */       this.x5t256 = octJWK.getX509CertSHA256Thumbprint();
/* 235 */       this.x5c = octJWK.getX509CertChain();
/* 236 */       this.exp = octJWK.getExpirationTime();
/* 237 */       this.nbf = octJWK.getNotBeforeTime();
/* 238 */       this.iat = octJWK.getIssueTime();
/* 239 */       this.revocation = octJWK.getKeyRevocation();
/* 240 */       this.ks = octJWK.getKeyStore();
/*     */     }
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
/*     */     public Builder keyUse(KeyUse use) {
/* 255 */       this.use = use;
/* 256 */       return this;
/*     */     }
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
/*     */     public Builder keyOperations(Set<KeyOperation> ops) {
/* 271 */       this.ops = ops;
/* 272 */       return this;
/*     */     }
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
/*     */     public Builder algorithm(Algorithm alg) {
/* 286 */       this.alg = alg;
/* 287 */       return this;
/*     */     }
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
/*     */     public Builder keyID(String kid) {
/* 303 */       this.kid = kid;
/* 304 */       return this;
/*     */     }
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
/*     */     public Builder keyIDFromThumbprint() throws JOSEException {
/* 324 */       return keyIDFromThumbprint("SHA-256");
/*     */     }
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
/*     */     public Builder keyIDFromThumbprint(String hashAlg) throws JOSEException {
/* 347 */       LinkedHashMap<String, String> requiredParams = new LinkedHashMap<>();
/* 348 */       requiredParams.put("k", this.k.toString());
/* 349 */       requiredParams.put("kty", KeyType.OCT.getValue());
/* 350 */       this.kid = ThumbprintUtils.compute(hashAlg, requiredParams).toString();
/* 351 */       return this;
/*     */     }
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
/*     */     public Builder x509CertURL(URI x5u) {
/* 365 */       this.x5u = x5u;
/* 366 */       return this;
/*     */     }
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
/*     */     @Deprecated
/*     */     public Builder x509CertThumbprint(Base64URL x5t) {
/* 382 */       this.x5t = x5t;
/* 383 */       return this;
/*     */     }
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
/*     */     public Builder x509CertSHA256Thumbprint(Base64URL x5t256) {
/* 398 */       this.x5t256 = x5t256;
/* 399 */       return this;
/*     */     }
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
/*     */     public Builder x509CertChain(List<Base64> x5c) {
/* 413 */       this.x5c = x5c;
/* 414 */       return this;
/*     */     }
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
/*     */     public Builder expirationTime(Date exp) {
/* 428 */       this.exp = exp;
/* 429 */       return this;
/*     */     }
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
/*     */     public Builder notBeforeTime(Date nbf) {
/* 443 */       this.nbf = nbf;
/* 444 */       return this;
/*     */     }
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
/*     */     public Builder issueTime(Date iat) {
/* 458 */       this.iat = iat;
/* 459 */       return this;
/*     */     }
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
/*     */     public Builder keyRevocation(KeyRevocation revocation) {
/* 473 */       this.revocation = revocation;
/* 474 */       return this;
/*     */     }
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
/*     */     public Builder keyStore(KeyStore keyStore) {
/* 488 */       this.ks = keyStore;
/* 489 */       return this;
/*     */     }
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
/*     */     public OctetSequenceKey build() {
/*     */       try {
/* 504 */         return new OctetSequenceKey(this.k, this.use, this.ops, this.alg, this.kid, this.x5u, this.x5t, this.x5t256, this.x5c, this.exp, this.nbf, this.iat, this.revocation, this.ks);
/*     */       }
/* 506 */       catch (IllegalArgumentException e) {
/*     */         
/* 508 */         throw new IllegalStateException(e.getMessage(), e);
/*     */       } 
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
/*     */   @Deprecated
/*     */   public OctetSequenceKey(Base64URL k, KeyUse use, Set<KeyOperation> ops, Algorithm alg, String kid, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c, KeyStore ks) {
/* 543 */     this(k, use, ops, alg, kid, x5u, x5t, x5t256, x5c, (Date)null, (Date)null, (Date)null, ks);
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
/*     */   @Deprecated
/*     */   public OctetSequenceKey(Base64URL k, KeyUse use, Set<KeyOperation> ops, Algorithm alg, String kid, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c, Date exp, Date nbf, Date iat, KeyStore ks) {
/* 582 */     this(k, use, ops, alg, kid, x5u, x5t, x5t256, x5c, exp, nbf, iat, (KeyRevocation)null, ks);
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
/*     */   public OctetSequenceKey(Base64URL k, KeyUse use, Set<KeyOperation> ops, Algorithm alg, String kid, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c, Date exp, Date nbf, Date iat, KeyRevocation revocation, KeyStore ks) {
/* 624 */     super(KeyType.OCT, use, ops, alg, kid, x5u, x5t, x5t256, x5c, exp, nbf, iat, revocation, ks);
/* 625 */     this.k = Objects.<Base64URL>requireNonNull(k, "The key value must not be null");
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
/*     */   public Base64URL getKeyValue() {
/* 637 */     return this.k;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] toByteArray() {
/* 648 */     return getKeyValue().decode();
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
/*     */   public SecretKey toSecretKey() {
/* 661 */     return toSecretKey("NONE");
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
/*     */   public SecretKey toSecretKey(String jcaAlg) {
/* 675 */     return new SecretKeySpec(toByteArray(), jcaAlg);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LinkedHashMap<String, ?> getRequiredParams() {
/* 683 */     LinkedHashMap<String, String> requiredParams = new LinkedHashMap<>();
/* 684 */     requiredParams.put("k", this.k.toString());
/* 685 */     requiredParams.put("kty", getKeyType().toString());
/* 686 */     return requiredParams;
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
/*     */   public boolean isPrivate() {
/* 699 */     return true;
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
/*     */   public OctetSequenceKey toPublicJWK() {
/* 712 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OctetSequenceKey toRevokedJWK(KeyRevocation keyRevocation) {
/* 719 */     if (getKeyRevocation() != null) {
/* 720 */       throw new IllegalStateException("Already revoked");
/*     */     }
/*     */     
/* 723 */     return (new Builder(this))
/* 724 */       .keyRevocation(Objects.<KeyRevocation>requireNonNull(keyRevocation))
/* 725 */       .build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/*     */     try {
/* 733 */       return ByteUtils.safeBitLength(this.k.decode());
/* 734 */     } catch (IntegerOverflowException e) {
/* 735 */       throw new ArithmeticException(e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, Object> toJSONObject() {
/* 743 */     Map<String, Object> o = super.toJSONObject();
/*     */ 
/*     */     
/* 746 */     o.put("k", this.k.toString());
/*     */     
/* 748 */     return o;
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
/*     */ 
/*     */   
/*     */   public static OctetSequenceKey parse(String s) throws ParseException {
/* 766 */     return parse(JSONObjectUtils.parse(s));
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static OctetSequenceKey parse(Map<String, Object> jsonObject) throws ParseException {
/* 786 */     if (!KeyType.OCT.equals(JWKMetadata.parseKeyType(jsonObject))) {
/* 787 */       throw new ParseException("The key type kty must be " + KeyType.OCT.getValue(), 0);
/*     */     }
/*     */ 
/*     */     
/* 791 */     Base64URL k = JSONObjectUtils.getBase64URL(jsonObject, "k");
/*     */     
/*     */     try {
/* 794 */       return new OctetSequenceKey(k, 
/* 795 */           JWKMetadata.parseKeyUse(jsonObject), 
/* 796 */           JWKMetadata.parseKeyOperations(jsonObject), 
/* 797 */           JWKMetadata.parseAlgorithm(jsonObject), 
/* 798 */           JWKMetadata.parseKeyID(jsonObject), 
/* 799 */           JWKMetadata.parseX509CertURL(jsonObject), 
/* 800 */           JWKMetadata.parseX509CertThumbprint(jsonObject), 
/* 801 */           JWKMetadata.parseX509CertSHA256Thumbprint(jsonObject), 
/* 802 */           JWKMetadata.parseX509CertChain(jsonObject), 
/* 803 */           JWKMetadata.parseExpirationTime(jsonObject), 
/* 804 */           JWKMetadata.parseNotBeforeTime(jsonObject), 
/* 805 */           JWKMetadata.parseIssueTime(jsonObject), 
/* 806 */           JWKMetadata.parseKeyRevocation(jsonObject), null);
/*     */     
/*     */     }
/* 809 */     catch (Exception e) {
/* 810 */       throw new ParseException(e.getMessage(), 0);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static OctetSequenceKey load(KeyStore keyStore, String alias, char[] pin) throws KeyStoreException, JOSEException {
/*     */     Key key;
/*     */     try {
/* 834 */       key = keyStore.getKey(alias, pin);
/* 835 */     } catch (UnrecoverableKeyException|java.security.NoSuchAlgorithmException e) {
/* 836 */       throw new JOSEException("Couldn't retrieve secret key (bad pin?): " + e.getMessage(), e);
/*     */     } 
/*     */     
/* 839 */     if (!(key instanceof SecretKey)) {
/* 840 */       return null;
/*     */     }
/*     */     
/* 843 */     return (new Builder((SecretKey)key))
/* 844 */       .keyID(alias)
/* 845 */       .keyStore(keyStore)
/* 846 */       .build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 852 */     if (this == o) return true; 
/* 853 */     if (!(o instanceof OctetSequenceKey)) return false; 
/* 854 */     if (!super.equals(o)) return false; 
/* 855 */     OctetSequenceKey that = (OctetSequenceKey)o;
/* 856 */     return Objects.equals(this.k, that.k);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 862 */     return Objects.hash(new Object[] { Integer.valueOf(super.hashCode()), this.k });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\jwk\OctetSequenceKey.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */