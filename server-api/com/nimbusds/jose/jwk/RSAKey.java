/*      */ package com.nimbusds.jose.jwk;
/*      */ 
/*      */ import com.nimbusds.jose.Algorithm;
/*      */ import com.nimbusds.jose.JOSEException;
/*      */ import com.nimbusds.jose.shaded.jcip.Immutable;
/*      */ import com.nimbusds.jose.util.Base64;
/*      */ import com.nimbusds.jose.util.Base64URL;
/*      */ import com.nimbusds.jose.util.ByteUtils;
/*      */ import com.nimbusds.jose.util.IntegerOverflowException;
/*      */ import com.nimbusds.jose.util.JSONArrayUtils;
/*      */ import com.nimbusds.jose.util.JSONObjectUtils;
/*      */ import java.io.Serializable;
/*      */ import java.math.BigInteger;
/*      */ import java.net.URI;
/*      */ import java.security.GeneralSecurityException;
/*      */ import java.security.Key;
/*      */ import java.security.KeyFactory;
/*      */ import java.security.KeyPair;
/*      */ import java.security.KeyStore;
/*      */ import java.security.KeyStoreException;
/*      */ import java.security.MessageDigest;
/*      */ import java.security.NoSuchAlgorithmException;
/*      */ import java.security.PrivateKey;
/*      */ import java.security.PublicKey;
/*      */ import java.security.UnrecoverableKeyException;
/*      */ import java.security.cert.Certificate;
/*      */ import java.security.cert.CertificateEncodingException;
/*      */ import java.security.cert.X509Certificate;
/*      */ import java.security.interfaces.RSAMultiPrimePrivateCrtKey;
/*      */ import java.security.interfaces.RSAPrivateCrtKey;
/*      */ import java.security.interfaces.RSAPrivateKey;
/*      */ import java.security.interfaces.RSAPublicKey;
/*      */ import java.security.spec.InvalidKeySpecException;
/*      */ import java.security.spec.RSAMultiPrimePrivateCrtKeySpec;
/*      */ import java.security.spec.RSAOtherPrimeInfo;
/*      */ import java.security.spec.RSAPrivateCrtKeySpec;
/*      */ import java.security.spec.RSAPrivateKeySpec;
/*      */ import java.security.spec.RSAPublicKeySpec;
/*      */ import java.text.ParseException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Objects;
/*      */ import java.util.Set;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ @Immutable
/*      */ public final class RSAKey
/*      */   extends JWK
/*      */   implements AsymmetricJWK
/*      */ {
/*      */   private static final long serialVersionUID = 1L;
/*      */   private final Base64URL n;
/*      */   private final Base64URL e;
/*      */   private final Base64URL d;
/*      */   private final Base64URL p;
/*      */   private final Base64URL q;
/*      */   private final Base64URL dp;
/*      */   private final Base64URL dq;
/*      */   private final Base64URL qi;
/*      */   private final List<OtherPrimesInfo> oth;
/*      */   private final PrivateKey privateKey;
/*      */   
/*      */   @Immutable
/*      */   public static class OtherPrimesInfo
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 1L;
/*      */     private final Base64URL r;
/*      */     private final Base64URL d;
/*      */     private final Base64URL t;
/*      */     
/*      */     public OtherPrimesInfo(Base64URL r, Base64URL d, Base64URL t) {
/*  177 */       this.r = Objects.<Base64URL>requireNonNull(r);
/*  178 */       this.d = Objects.<Base64URL>requireNonNull(d);
/*  179 */       this.t = Objects.<Base64URL>requireNonNull(t);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public OtherPrimesInfo(RSAOtherPrimeInfo oth) {
/*  192 */       this.r = Base64URL.encode(oth.getPrime());
/*  193 */       this.d = Base64URL.encode(oth.getExponent());
/*  194 */       this.t = Base64URL.encode(oth.getCrtCoefficient());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Base64URL getPrimeFactor() {
/*  205 */       return this.r;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Base64URL getFactorCRTExponent() {
/*  217 */       return this.d;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Base64URL getFactorCRTCoefficient() {
/*  230 */       return this.t;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static List<OtherPrimesInfo> toList(RSAOtherPrimeInfo[] othArray) {
/*  247 */       List<OtherPrimesInfo> list = new ArrayList<>();
/*      */       
/*  249 */       if (othArray == null)
/*      */       {
/*      */         
/*  252 */         return list;
/*      */       }
/*      */       
/*  255 */       for (RSAOtherPrimeInfo oth : othArray)
/*      */       {
/*  257 */         list.add(new OtherPrimesInfo(oth));
/*      */       }
/*      */       
/*  260 */       return list;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class Builder
/*      */   {
/*      */     private final Base64URL n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final Base64URL e;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Base64URL d;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Base64URL p;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Base64URL q;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Base64URL dp;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Base64URL dq;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Base64URL qi;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private List<RSAKey.OtherPrimesInfo> oth;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private PrivateKey priv;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private KeyUse use;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Set<KeyOperation> ops;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Algorithm alg;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String kid;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private URI x5u;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     private Base64URL x5t;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Base64URL x5t256;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private List<Base64> x5c;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Date exp;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Date nbf;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Date iat;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private KeyRevocation revocation;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private KeyStore ks;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder(Base64URL n, Base64URL e) {
/*  449 */       this.n = Objects.<Base64URL>requireNonNull(n);
/*  450 */       this.e = Objects.<Base64URL>requireNonNull(e);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder(RSAPublicKey pub) {
/*  462 */       this.n = Base64URL.encode(pub.getModulus());
/*  463 */       this.e = Base64URL.encode(pub.getPublicExponent());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder(RSAKey rsaJWK) {
/*  475 */       this.n = rsaJWK.n;
/*  476 */       this.e = rsaJWK.e;
/*  477 */       this.d = rsaJWK.d;
/*  478 */       this.p = rsaJWK.p;
/*  479 */       this.q = rsaJWK.q;
/*  480 */       this.dp = rsaJWK.dp;
/*  481 */       this.dq = rsaJWK.dq;
/*  482 */       this.qi = rsaJWK.qi;
/*  483 */       this.oth = rsaJWK.oth;
/*  484 */       this.priv = rsaJWK.privateKey;
/*  485 */       this.use = rsaJWK.getKeyUse();
/*  486 */       this.ops = rsaJWK.getKeyOperations();
/*  487 */       this.alg = rsaJWK.getAlgorithm();
/*  488 */       this.kid = rsaJWK.getKeyID();
/*  489 */       this.x5u = rsaJWK.getX509CertURL();
/*  490 */       this.x5t = rsaJWK.getX509CertThumbprint();
/*  491 */       this.x5t256 = rsaJWK.getX509CertSHA256Thumbprint();
/*  492 */       this.x5c = rsaJWK.getX509CertChain();
/*  493 */       this.exp = rsaJWK.getExpirationTime();
/*  494 */       this.nbf = rsaJWK.getNotBeforeTime();
/*  495 */       this.iat = rsaJWK.getIssueTime();
/*  496 */       this.revocation = rsaJWK.getKeyRevocation();
/*  497 */       this.ks = rsaJWK.getKeyStore();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder privateExponent(Base64URL d) {
/*  513 */       this.d = d;
/*  514 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder privateKey(RSAPrivateKey priv) {
/*  530 */       if (priv instanceof RSAPrivateCrtKey) {
/*  531 */         return privateKey((RSAPrivateCrtKey)priv);
/*      */       }
/*      */       
/*  534 */       if (priv instanceof RSAMultiPrimePrivateCrtKey) {
/*  535 */         return privateKey((RSAMultiPrimePrivateCrtKey)priv);
/*      */       }
/*      */       
/*  538 */       if (priv != null) {
/*  539 */         this.d = Base64URL.encode(priv.getPrivateExponent());
/*      */       } else {
/*  541 */         this.d = null;
/*      */       } 
/*      */       
/*  544 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder privateKey(PrivateKey priv) {
/*  562 */       if (priv instanceof RSAPrivateKey)
/*      */       {
/*  564 */         return privateKey((RSAPrivateKey)priv);
/*      */       }
/*      */       
/*  567 */       if (priv != null && !"RSA".equalsIgnoreCase(priv.getAlgorithm())) {
/*  568 */         throw new IllegalArgumentException("The private key algorithm must be RSA");
/*      */       }
/*      */       
/*  571 */       this.priv = priv;
/*  572 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder firstPrimeFactor(Base64URL p) {
/*  589 */       this.p = p;
/*  590 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder secondPrimeFactor(Base64URL q) {
/*  607 */       this.q = q;
/*  608 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder firstFactorCRTExponent(Base64URL dp) {
/*  626 */       this.dp = dp;
/*  627 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder secondFactorCRTExponent(Base64URL dq) {
/*  645 */       this.dq = dq;
/*  646 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder firstCRTCoefficient(Base64URL qi) {
/*  664 */       this.qi = qi;
/*  665 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder otherPrimes(List<RSAKey.OtherPrimesInfo> oth) {
/*  680 */       this.oth = oth;
/*  681 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder privateKey(RSAPrivateCrtKey priv) {
/*  703 */       if (priv != null) {
/*  704 */         this.d = Base64URL.encode(priv.getPrivateExponent());
/*  705 */         this.p = Base64URL.encode(priv.getPrimeP());
/*  706 */         this.q = Base64URL.encode(priv.getPrimeQ());
/*  707 */         this.dp = Base64URL.encode(priv.getPrimeExponentP());
/*  708 */         this.dq = Base64URL.encode(priv.getPrimeExponentQ());
/*  709 */         this.qi = Base64URL.encode(priv.getCrtCoefficient());
/*      */       } else {
/*  711 */         this.d = null;
/*  712 */         this.p = null;
/*  713 */         this.q = null;
/*  714 */         this.dp = null;
/*  715 */         this.dq = null;
/*  716 */         this.qi = null;
/*      */       } 
/*      */       
/*  719 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder privateKey(RSAMultiPrimePrivateCrtKey priv) {
/*  742 */       if (priv != null) {
/*  743 */         this.d = Base64URL.encode(priv.getPrivateExponent());
/*  744 */         this.p = Base64URL.encode(priv.getPrimeP());
/*  745 */         this.q = Base64URL.encode(priv.getPrimeQ());
/*  746 */         this.dp = Base64URL.encode(priv.getPrimeExponentP());
/*  747 */         this.dq = Base64URL.encode(priv.getPrimeExponentQ());
/*  748 */         this.qi = Base64URL.encode(priv.getCrtCoefficient());
/*  749 */         this.oth = RSAKey.OtherPrimesInfo.toList(priv.getOtherPrimeInfo());
/*      */       } else {
/*  751 */         this.d = null;
/*  752 */         this.p = null;
/*  753 */         this.q = null;
/*  754 */         this.dp = null;
/*  755 */         this.dq = null;
/*  756 */         this.qi = null;
/*  757 */         this.oth = null;
/*      */       } 
/*      */       
/*  760 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder keyUse(KeyUse use) {
/*  775 */       this.use = use;
/*  776 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder keyOperations(Set<KeyOperation> ops) {
/*  791 */       this.ops = ops;
/*  792 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder algorithm(Algorithm alg) {
/*  806 */       this.alg = alg;
/*  807 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder keyID(String kid) {
/*  823 */       this.kid = kid;
/*  824 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder keyIDFromThumbprint() throws JOSEException {
/*  844 */       return keyIDFromThumbprint("SHA-256");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder keyIDFromThumbprint(String hashAlg) throws JOSEException {
/*  867 */       LinkedHashMap<String, Object> requiredParams = new LinkedHashMap<>();
/*  868 */       requiredParams.put("e", this.e.toString());
/*  869 */       requiredParams.put("kty", KeyType.RSA.getValue());
/*  870 */       requiredParams.put("n", this.n.toString());
/*  871 */       this.kid = ThumbprintUtils.compute(hashAlg, requiredParams).toString();
/*  872 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder x509CertURL(URI x5u) {
/*  886 */       this.x5u = x5u;
/*  887 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Builder x509CertThumbprint(Base64URL x5t) {
/*  903 */       this.x5t = x5t;
/*  904 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder x509CertSHA256Thumbprint(Base64URL x5t256) {
/*  919 */       this.x5t256 = x5t256;
/*  920 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder x509CertChain(List<Base64> x5c) {
/*  934 */       this.x5c = x5c;
/*  935 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder expirationTime(Date exp) {
/*  949 */       this.exp = exp;
/*  950 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder notBeforeTime(Date nbf) {
/*  964 */       this.nbf = nbf;
/*  965 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder issueTime(Date iat) {
/*  979 */       this.iat = iat;
/*  980 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder keyRevocation(KeyRevocation revocation) {
/*  994 */       this.revocation = revocation;
/*  995 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder keyStore(KeyStore keyStore) {
/* 1009 */       this.ks = keyStore;
/* 1010 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public RSAKey build() {
/*      */       try {
/* 1026 */         return new RSAKey(this.n, this.e, this.d, this.p, this.q, this.dp, this.dq, this.qi, this.oth, this.priv, this.use, this.ops, this.alg, this.kid, this.x5u, this.x5t, this.x5t256, this.x5c, this.exp, this.nbf, this.iat, this.revocation, this.ks);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/* 1032 */       catch (IllegalArgumentException e) {
/*      */         
/* 1034 */         throw new IllegalStateException(e.getMessage(), e);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public RSAKey(Base64URL n, Base64URL e, KeyUse use, Set<KeyOperation> ops, Algorithm alg, String kid, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c, KeyStore ks) {
/* 1149 */     this(n, e, (Base64URL)null, (Base64URL)null, (Base64URL)null, (Base64URL)null, (Base64URL)null, (Base64URL)null, (List<OtherPrimesInfo>)null, (PrivateKey)null, use, ops, alg, kid, x5u, x5t, x5t256, x5c, (Date)null, (Date)null, (Date)null, ks);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public RSAKey(Base64URL n, Base64URL e, KeyUse use, Set<KeyOperation> ops, Algorithm alg, String kid, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c, Date exp, Date nbf, Date iat, KeyStore ks) {
/* 1196 */     this(n, e, (Base64URL)null, (Base64URL)null, (Base64URL)null, (Base64URL)null, (Base64URL)null, (Base64URL)null, (List<OtherPrimesInfo>)null, (PrivateKey)null, use, ops, alg, kid, x5u, x5t, x5t256, x5c, exp, nbf, iat, ks);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RSAKey(Base64URL n, Base64URL e, KeyUse use, Set<KeyOperation> ops, Algorithm alg, String kid, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c, Date exp, Date nbf, Date iat, KeyRevocation revocation, KeyStore ks) {
/* 1248 */     this(n, e, (Base64URL)null, (Base64URL)null, (Base64URL)null, (Base64URL)null, (Base64URL)null, (Base64URL)null, (List<OtherPrimesInfo>)null, (PrivateKey)null, use, ops, alg, kid, x5u, x5t, x5t256, x5c, exp, nbf, iat, revocation, ks);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public RSAKey(Base64URL n, Base64URL e, Base64URL d, KeyUse use, Set<KeyOperation> ops, Algorithm alg, String kid, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c, KeyStore ks) {
/* 1292 */     this(n, e, d, (Base64URL)null, (Base64URL)null, (Base64URL)null, (Base64URL)null, (Base64URL)null, (List<OtherPrimesInfo>)null, (PrivateKey)null, use, ops, alg, kid, x5u, x5t, x5t256, x5c, (Date)null, (Date)null, (Date)null, ks);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public RSAKey(Base64URL n, Base64URL e, Base64URL d, KeyUse use, Set<KeyOperation> ops, Algorithm alg, String kid, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c, Date exp, Date nbf, Date iat, KeyStore ks) {
/* 1342 */     this(n, e, d, use, ops, alg, kid, x5u, x5t, x5t256, x5c, exp, nbf, iat, (KeyRevocation)null, ks);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RSAKey(Base64URL n, Base64URL e, Base64URL d, KeyUse use, Set<KeyOperation> ops, Algorithm alg, String kid, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c, Date exp, Date nbf, Date iat, KeyRevocation revocation, KeyStore ks) {
/* 1396 */     this(n, e, d, (Base64URL)null, (Base64URL)null, (Base64URL)null, (Base64URL)null, (Base64URL)null, (List<OtherPrimesInfo>)null, (PrivateKey)null, use, ops, alg, kid, x5u, x5t, x5t256, x5c, exp, nbf, iat, revocation, ks);
/*      */ 
/*      */     
/* 1399 */     Objects.requireNonNull(d, "The private exponent must not be null");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public RSAKey(Base64URL n, Base64URL e, Base64URL p, Base64URL q, Base64URL dp, Base64URL dq, Base64URL qi, List<OtherPrimesInfo> oth, KeyUse use, Set<KeyOperation> ops, Algorithm alg, String kid, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c, KeyStore ks) {
/* 1459 */     this(n, e, (Base64URL)null, p, q, dp, dq, qi, oth, (PrivateKey)null, use, ops, alg, kid, x5u, x5t, x5t256, x5c, (Date)null, (Date)null, (Date)null, ks);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public RSAKey(Base64URL n, Base64URL e, Base64URL p, Base64URL q, Base64URL dp, Base64URL dq, Base64URL qi, List<OtherPrimesInfo> oth, KeyUse use, Set<KeyOperation> ops, Algorithm alg, String kid, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c, Date exp, Date nbf, Date iat, KeyStore ks) {
/* 1528 */     this(n, e, p, q, dp, dq, qi, oth, use, ops, alg, kid, x5u, x5t, x5t256, x5c, exp, nbf, iat, (KeyRevocation)null, ks);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RSAKey(Base64URL n, Base64URL e, Base64URL p, Base64URL q, Base64URL dp, Base64URL dq, Base64URL qi, List<OtherPrimesInfo> oth, KeyUse use, Set<KeyOperation> ops, Algorithm alg, String kid, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c, Date exp, Date nbf, Date iat, KeyRevocation revocation, KeyStore ks) {
/* 1603 */     this(n, e, (Base64URL)null, p, q, dp, dq, qi, oth, (PrivateKey)null, use, ops, alg, kid, x5u, x5t, x5t256, x5c, exp, nbf, iat, revocation, ks);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1608 */     Objects.requireNonNull(p, "The first prime factor must not be null");
/* 1609 */     Objects.requireNonNull(q, "The second prime factor must not be null");
/* 1610 */     Objects.requireNonNull(dp, "The first factor CRT exponent must not be null");
/* 1611 */     Objects.requireNonNull(dq, "The second factor CRT exponent must not be null");
/* 1612 */     Objects.requireNonNull(qi, "The first CRT coefficient must not be null");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public RSAKey(Base64URL n, Base64URL e, Base64URL d, Base64URL p, Base64URL q, Base64URL dp, Base64URL dq, Base64URL qi, List<OtherPrimesInfo> oth, KeyUse use, Set<KeyOperation> ops, Algorithm alg, String kid, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c) {
/* 1680 */     this(n, e, d, p, q, dp, dq, qi, oth, (PrivateKey)null, use, ops, alg, kid, x5u, x5t, x5t256, x5c, (KeyStore)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public RSAKey(Base64URL n, Base64URL e, Base64URL d, Base64URL p, Base64URL q, Base64URL dp, Base64URL dq, Base64URL qi, List<OtherPrimesInfo> oth, PrivateKey prv, KeyUse use, Set<KeyOperation> ops, Algorithm alg, String kid, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c, KeyStore ks) {
/* 1756 */     this(n, e, d, p, q, dp, dq, qi, oth, prv, use, ops, alg, kid, x5u, x5t, x5t256, x5c, (Date)null, (Date)null, (Date)null, ks);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public RSAKey(Base64URL n, Base64URL e, Base64URL d, Base64URL p, Base64URL q, Base64URL dp, Base64URL dq, Base64URL qi, List<OtherPrimesInfo> oth, PrivateKey prv, KeyUse use, Set<KeyOperation> ops, Algorithm alg, String kid, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c, Date exp, Date nbf, Date iat, KeyStore ks) {
/* 1835 */     this(n, e, d, p, q, dp, dq, qi, oth, prv, use, ops, alg, kid, x5u, x5t, x5t256, x5c, exp, nbf, iat, (KeyRevocation)null, ks);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RSAKey(Base64URL n, Base64URL e, Base64URL d, Base64URL p, Base64URL q, Base64URL dp, Base64URL dq, Base64URL qi, List<OtherPrimesInfo> oth, PrivateKey prv, KeyUse use, Set<KeyOperation> ops, Algorithm alg, String kid, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c, Date exp, Date nbf, Date iat, KeyRevocation revocation, KeyStore ks) {
/* 1915 */     super(KeyType.RSA, use, ops, alg, kid, x5u, x5t, x5t256, x5c, exp, nbf, iat, revocation, ks);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1920 */     this.n = Objects.<Base64URL>requireNonNull(n, "The modulus value must not be null");
/* 1921 */     this.e = Objects.<Base64URL>requireNonNull(e, "The public exponent value must not be null");
/*      */     
/* 1923 */     if (getParsedX509CertChain() != null && 
/* 1924 */       !matches(getParsedX509CertChain().get(0))) {
/* 1925 */       throw new IllegalArgumentException("The public subject key info of the first X.509 certificate in the chain must match the JWK type and public parameters");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1930 */     this.d = d;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1935 */     if (p != null && q != null && dp != null && dq != null && qi != null) {
/*      */ 
/*      */       
/* 1938 */       this.p = p;
/* 1939 */       this.q = q;
/* 1940 */       this.dp = dp;
/* 1941 */       this.dq = dq;
/* 1942 */       this.qi = qi;
/*      */ 
/*      */       
/* 1945 */       if (oth != null) {
/* 1946 */         this.oth = Collections.unmodifiableList(oth);
/*      */       } else {
/* 1948 */         this.oth = Collections.emptyList();
/*      */       }
/*      */     
/* 1951 */     } else if (p == null && q == null && dp == null && dq == null && qi == null && oth == null) {
/*      */ 
/*      */       
/* 1954 */       this.p = null;
/* 1955 */       this.q = null;
/* 1956 */       this.dp = null;
/* 1957 */       this.dq = null;
/* 1958 */       this.qi = null;
/*      */       
/* 1960 */       this.oth = Collections.emptyList();
/*      */     } else {
/* 1962 */       if (p != null || q != null || dp != null || dq != null || qi != null) {
/*      */         
/* 1964 */         Objects.requireNonNull(p, "Incomplete second private (CRT) representation: The first prime factor must not be null");
/* 1965 */         Objects.requireNonNull(q, "Incomplete second private (CRT) representation: The second prime factor must not be null");
/* 1966 */         Objects.requireNonNull(dp, "Incomplete second private (CRT) representation: The first factor CRT exponent must not be null");
/* 1967 */         Objects.requireNonNull(dq, "Incomplete second private (CRT) representation: The second factor CRT exponent must not be null");
/* 1968 */         throw new IllegalArgumentException("Incomplete second private (CRT) representation: The first CRT coefficient must not be null");
/*      */       } 
/*      */       
/* 1971 */       this.p = null;
/* 1972 */       this.q = null;
/* 1973 */       this.dp = null;
/* 1974 */       this.dq = null;
/* 1975 */       this.qi = null;
/* 1976 */       this.oth = Collections.emptyList();
/*      */     } 
/*      */     
/* 1979 */     this.privateKey = prv;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public RSAKey(RSAPublicKey pub, KeyUse use, Set<KeyOperation> ops, Algorithm alg, String kid, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c, KeyStore ks) {
/* 2012 */     this(pub, use, ops, alg, kid, x5u, x5t, x5t256, x5c, (Date)null, (Date)null, (Date)null, ks);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public RSAKey(RSAPublicKey pub, KeyUse use, Set<KeyOperation> ops, Algorithm alg, String kid, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c, Date exp, Date nbf, Date iat, KeyStore ks) {
/* 2051 */     this(pub, use, ops, alg, kid, x5u, x5t, x5t256, x5c, exp, nbf, iat, (KeyRevocation)null, ks);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RSAKey(RSAPublicKey pub, KeyUse use, Set<KeyOperation> ops, Algorithm alg, String kid, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c, Date exp, Date nbf, Date iat, KeyRevocation revocation, KeyStore ks) {
/* 2093 */     this(Base64URL.encode(pub.getModulus()), 
/* 2094 */         Base64URL.encode(pub.getPublicExponent()), use, ops, alg, kid, x5u, x5t, x5t256, x5c, exp, nbf, iat, revocation, ks);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public RSAKey(RSAPublicKey pub, RSAPrivateKey priv, KeyUse use, Set<KeyOperation> ops, Algorithm alg, String kid, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c, KeyStore ks) {
/* 2134 */     this(pub, priv, use, ops, alg, kid, x5u, x5t, x5t256, x5c, (Date)null, (Date)null, (Date)null, ks);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public RSAKey(RSAPublicKey pub, RSAPrivateKey priv, KeyUse use, Set<KeyOperation> ops, Algorithm alg, String kid, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c, Date exp, Date nbf, Date iat, KeyStore ks) {
/* 2176 */     this(pub, priv, use, ops, alg, kid, x5u, x5t, x5t256, x5c, exp, nbf, iat, (KeyRevocation)null, ks);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RSAKey(RSAPublicKey pub, RSAPrivateKey priv, KeyUse use, Set<KeyOperation> ops, Algorithm alg, String kid, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c, Date exp, Date nbf, Date iat, KeyRevocation revocation, KeyStore ks) {
/* 2221 */     this(Base64URL.encode(pub.getModulus()), 
/* 2222 */         Base64URL.encode(pub.getPublicExponent()), 
/* 2223 */         Base64URL.encode(priv.getPrivateExponent()), use, ops, alg, kid, x5u, x5t, x5t256, x5c, exp, nbf, iat, revocation, ks);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public RSAKey(RSAPublicKey pub, RSAPrivateCrtKey priv, KeyUse use, Set<KeyOperation> ops, Algorithm alg, String kid, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c, KeyStore ks) {
/* 2263 */     this(pub, priv, use, ops, alg, kid, x5u, x5t, x5t256, x5c, (Date)null, (Date)null, (Date)null, ks);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public RSAKey(RSAPublicKey pub, RSAPrivateCrtKey priv, KeyUse use, Set<KeyOperation> ops, Algorithm alg, String kid, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c, Date exp, Date nbf, Date iat, KeyStore ks) {
/* 2305 */     this(Base64URL.encode(pub.getModulus()), 
/* 2306 */         Base64URL.encode(pub.getPublicExponent()), 
/* 2307 */         Base64URL.encode(priv.getPrivateExponent()), 
/* 2308 */         Base64URL.encode(priv.getPrimeP()), 
/* 2309 */         Base64URL.encode(priv.getPrimeQ()), 
/* 2310 */         Base64URL.encode(priv.getPrimeExponentP()), 
/* 2311 */         Base64URL.encode(priv.getPrimeExponentQ()), 
/* 2312 */         Base64URL.encode(priv.getCrtCoefficient()), (List<OtherPrimesInfo>)null, (PrivateKey)null, use, ops, alg, kid, x5u, x5t, x5t256, x5c, exp, nbf, iat, ks);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RSAKey(RSAPublicKey pub, RSAPrivateCrtKey priv, KeyUse use, Set<KeyOperation> ops, Algorithm alg, String kid, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c, Date exp, Date nbf, Date iat, KeyRevocation revocation, KeyStore ks) {
/* 2363 */     this(Base64URL.encode(pub.getModulus()), 
/* 2364 */         Base64URL.encode(pub.getPublicExponent()), 
/* 2365 */         Base64URL.encode(priv.getPrivateExponent()), 
/* 2366 */         Base64URL.encode(priv.getPrimeP()), 
/* 2367 */         Base64URL.encode(priv.getPrimeQ()), 
/* 2368 */         Base64URL.encode(priv.getPrimeExponentP()), 
/* 2369 */         Base64URL.encode(priv.getPrimeExponentQ()), 
/* 2370 */         Base64URL.encode(priv.getCrtCoefficient()), (List<OtherPrimesInfo>)null, (PrivateKey)null, use, ops, alg, kid, x5u, x5t, x5t256, x5c, exp, nbf, iat, revocation, ks);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public RSAKey(RSAPublicKey pub, RSAMultiPrimePrivateCrtKey priv, KeyUse use, Set<KeyOperation> ops, Algorithm alg, String kid, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c, KeyStore ks) {
/* 2413 */     this(pub, priv, use, ops, alg, kid, x5u, x5t, x5t256, x5c, (Date)null, (Date)null, (Date)null, ks);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public RSAKey(RSAPublicKey pub, RSAMultiPrimePrivateCrtKey priv, KeyUse use, Set<KeyOperation> ops, Algorithm alg, String kid, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c, Date exp, Date nbf, Date iat, KeyStore ks) {
/* 2456 */     this(Base64URL.encode(pub.getModulus()), 
/* 2457 */         Base64URL.encode(pub.getPublicExponent()), 
/* 2458 */         Base64URL.encode(priv.getPrivateExponent()), 
/* 2459 */         Base64URL.encode(priv.getPrimeP()), 
/* 2460 */         Base64URL.encode(priv.getPrimeQ()), 
/* 2461 */         Base64URL.encode(priv.getPrimeExponentP()), 
/* 2462 */         Base64URL.encode(priv.getPrimeExponentQ()), 
/* 2463 */         Base64URL.encode(priv.getCrtCoefficient()), 
/* 2464 */         OtherPrimesInfo.toList(priv.getOtherPrimeInfo()), (PrivateKey)null, use, ops, alg, kid, x5u, x5t, x5t256, x5c, exp, nbf, iat, ks);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RSAKey(RSAPublicKey pub, RSAMultiPrimePrivateCrtKey priv, KeyUse use, Set<KeyOperation> ops, Algorithm alg, String kid, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c, Date exp, Date nbf, Date iat, KeyRevocation revocation, KeyStore ks) {
/* 2515 */     this(Base64URL.encode(pub.getModulus()), 
/* 2516 */         Base64URL.encode(pub.getPublicExponent()), 
/* 2517 */         Base64URL.encode(priv.getPrivateExponent()), 
/* 2518 */         Base64URL.encode(priv.getPrimeP()), 
/* 2519 */         Base64URL.encode(priv.getPrimeQ()), 
/* 2520 */         Base64URL.encode(priv.getPrimeExponentP()), 
/* 2521 */         Base64URL.encode(priv.getPrimeExponentQ()), 
/* 2522 */         Base64URL.encode(priv.getCrtCoefficient()), 
/* 2523 */         OtherPrimesInfo.toList(priv.getOtherPrimeInfo()), (PrivateKey)null, use, ops, alg, kid, x5u, x5t, x5t256, x5c, exp, nbf, iat, revocation, ks);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public RSAKey(RSAPublicKey pub, PrivateKey priv, KeyUse use, Set<KeyOperation> ops, Algorithm alg, String kid, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c, KeyStore ks) {
/* 2564 */     this(pub, priv, use, ops, alg, kid, x5u, x5t, x5t256, x5c, (Date)null, (Date)null, (Date)null, ks);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public RSAKey(RSAPublicKey pub, PrivateKey priv, KeyUse use, Set<KeyOperation> ops, Algorithm alg, String kid, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c, Date exp, Date nbf, Date iat, KeyStore ks) {
/* 2606 */     this(pub, priv, use, ops, alg, kid, x5u, x5t, x5t256, x5c, exp, nbf, iat, (KeyRevocation)null, ks);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RSAKey(RSAPublicKey pub, PrivateKey priv, KeyUse use, Set<KeyOperation> ops, Algorithm alg, String kid, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c, Date exp, Date nbf, Date iat, KeyRevocation revocation, KeyStore ks) {
/* 2651 */     this(Base64URL.encode(pub.getModulus()), 
/* 2652 */         Base64URL.encode(pub.getPublicExponent()), (Base64URL)null, (Base64URL)null, (Base64URL)null, (Base64URL)null, (Base64URL)null, (Base64URL)null, (List<OtherPrimesInfo>)null, priv, use, ops, alg, kid, x5u, x5t, x5t256, x5c, exp, nbf, iat, revocation, ks);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Base64URL getModulus() {
/* 2676 */     return this.n;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Base64URL getPublicExponent() {
/* 2688 */     return this.e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Base64URL getPrivateExponent() {
/* 2702 */     return this.d;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Base64URL getFirstPrimeFactor() {
/* 2716 */     return this.p;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Base64URL getSecondPrimeFactor() {
/* 2730 */     return this.q;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Base64URL getFirstFactorCRTExponent() {
/* 2745 */     return this.dp;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Base64URL getSecondFactorCRTExponent() {
/* 2760 */     return this.dq;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Base64URL getFirstCRTCoefficient() {
/* 2775 */     return this.qi;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<OtherPrimesInfo> getOtherPrimes() {
/* 2788 */     return this.oth;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RSAPublicKey toRSAPublicKey() throws JOSEException {
/* 2805 */     BigInteger modulus = this.n.decodeToBigInteger();
/* 2806 */     BigInteger exponent = this.e.decodeToBigInteger();
/*      */     
/* 2808 */     RSAPublicKeySpec spec = new RSAPublicKeySpec(modulus, exponent);
/*      */     
/*      */     try {
/* 2811 */       KeyFactory factory = KeyFactory.getInstance("RSA");
/*      */       
/* 2813 */       return (RSAPublicKey)factory.generatePublic(spec);
/*      */     }
/* 2815 */     catch (NoSuchAlgorithmException|InvalidKeySpecException e) {
/*      */       
/* 2817 */       throw new JOSEException(e.getMessage(), e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RSAPrivateKey toRSAPrivateKey() throws JOSEException {
/*      */     RSAPrivateKeySpec spec;
/* 2836 */     if (this.d == null)
/*      */     {
/* 2838 */       return null;
/*      */     }
/*      */     
/* 2841 */     BigInteger modulus = this.n.decodeToBigInteger();
/* 2842 */     BigInteger privateExponent = this.d.decodeToBigInteger();
/*      */ 
/*      */ 
/*      */     
/* 2846 */     if (this.p == null) {
/*      */       
/* 2848 */       spec = new RSAPrivateKeySpec(modulus, privateExponent);
/*      */     }
/*      */     else {
/*      */       
/* 2852 */       BigInteger publicExponent = this.e.decodeToBigInteger();
/* 2853 */       BigInteger primeP = this.p.decodeToBigInteger();
/* 2854 */       BigInteger primeQ = this.q.decodeToBigInteger();
/* 2855 */       BigInteger primeExponentP = this.dp.decodeToBigInteger();
/* 2856 */       BigInteger primeExponentQ = this.dq.decodeToBigInteger();
/* 2857 */       BigInteger crtCoefficient = this.qi.decodeToBigInteger();
/*      */       
/* 2859 */       if (this.oth != null && !this.oth.isEmpty()) {
/*      */         
/* 2861 */         RSAOtherPrimeInfo[] otherInfo = new RSAOtherPrimeInfo[this.oth.size()];
/*      */         
/* 2863 */         for (int i = 0; i < this.oth.size(); i++) {
/*      */           
/* 2865 */           OtherPrimesInfo opi = this.oth.get(i);
/*      */           
/* 2867 */           BigInteger otherPrime = opi.getPrimeFactor().decodeToBigInteger();
/* 2868 */           BigInteger otherPrimeExponent = opi.getFactorCRTExponent().decodeToBigInteger();
/* 2869 */           BigInteger otherCrtCoefficient = opi.getFactorCRTCoefficient().decodeToBigInteger();
/*      */           
/* 2871 */           otherInfo[i] = new RSAOtherPrimeInfo(otherPrime, otherPrimeExponent, otherCrtCoefficient);
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 2876 */         spec = new RSAMultiPrimePrivateCrtKeySpec(modulus, publicExponent, privateExponent, primeP, primeQ, primeExponentP, primeExponentQ, crtCoefficient, otherInfo);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2887 */         spec = new RSAPrivateCrtKeySpec(modulus, publicExponent, privateExponent, primeP, primeQ, primeExponentP, primeExponentQ, crtCoefficient);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 2899 */       KeyFactory factory = KeyFactory.getInstance("RSA");
/*      */       
/* 2901 */       return (RSAPrivateKey)factory.generatePrivate(spec);
/*      */     }
/* 2903 */     catch (InvalidKeySpecException|NoSuchAlgorithmException e) {
/*      */       
/* 2905 */       throw new JOSEException(e.getMessage(), e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PublicKey toPublicKey() throws JOSEException {
/* 2914 */     return toRSAPublicKey();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrivateKey toPrivateKey() throws JOSEException {
/* 2922 */     PrivateKey prv = toRSAPrivateKey();
/*      */     
/* 2924 */     if (prv != null)
/*      */     {
/* 2926 */       return prv;
/*      */     }
/*      */ 
/*      */     
/* 2930 */     return this.privateKey;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public KeyPair toKeyPair() throws JOSEException {
/* 2950 */     return new KeyPair(toRSAPublicKey(), toPrivateKey());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RSAKey toRevokedJWK(KeyRevocation keyRevocation) {
/* 2957 */     if (getKeyRevocation() != null) {
/* 2958 */       throw new IllegalStateException("Already revoked");
/*      */     }
/*      */     
/* 2961 */     return (new Builder(this))
/* 2962 */       .keyRevocation(Objects.<KeyRevocation>requireNonNull(keyRevocation))
/* 2963 */       .build();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean matches(X509Certificate cert) {
/*      */     RSAPublicKey certRSAKey;
/*      */     try {
/* 2972 */       certRSAKey = (RSAPublicKey)((X509Certificate)getParsedX509CertChain().get(0)).getPublicKey();
/* 2973 */     } catch (ClassCastException ex) {
/* 2974 */       return false;
/*      */     } 
/* 2976 */     if (!this.e.decodeToBigInteger().equals(certRSAKey.getPublicExponent())) {
/* 2977 */       return false;
/*      */     }
/* 2979 */     return this.n.decodeToBigInteger().equals(certRSAKey.getModulus());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LinkedHashMap<String, ?> getRequiredParams() {
/* 2987 */     LinkedHashMap<String, String> requiredParams = new LinkedHashMap<>();
/* 2988 */     requiredParams.put("e", this.e.toString());
/* 2989 */     requiredParams.put("kty", getKeyType().getValue());
/* 2990 */     requiredParams.put("n", this.n.toString());
/* 2991 */     return requiredParams;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isPrivate() {
/* 2999 */     return (this.d != null || this.p != null || this.privateKey != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int size() {
/*      */     try {
/* 3007 */       return ByteUtils.safeBitLength(this.n.decode());
/* 3008 */     } catch (IntegerOverflowException e) {
/* 3009 */       throw new ArithmeticException(e.getMessage());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RSAKey toPublicJWK() {
/* 3022 */     return new RSAKey(
/* 3023 */         getModulus(), getPublicExponent(), 
/* 3024 */         getKeyUse(), getKeyOperations(), getAlgorithm(), getKeyID(), 
/* 3025 */         getX509CertURL(), getX509CertThumbprint(), getX509CertSHA256Thumbprint(), getX509CertChain(), 
/* 3026 */         getExpirationTime(), getNotBeforeTime(), getIssueTime(), getKeyRevocation(), 
/* 3027 */         getKeyStore());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<String, Object> toJSONObject() {
/* 3034 */     Map<String, Object> o = super.toJSONObject();
/*      */ 
/*      */     
/* 3037 */     o.put("n", this.n.toString());
/* 3038 */     o.put("e", this.e.toString());
/* 3039 */     if (this.d != null) {
/* 3040 */       o.put("d", this.d.toString());
/*      */     }
/* 3042 */     if (this.p != null) {
/* 3043 */       o.put("p", this.p.toString());
/*      */     }
/* 3045 */     if (this.q != null) {
/* 3046 */       o.put("q", this.q.toString());
/*      */     }
/* 3048 */     if (this.dp != null) {
/* 3049 */       o.put("dp", this.dp.toString());
/*      */     }
/* 3051 */     if (this.dq != null) {
/* 3052 */       o.put("dq", this.dq.toString());
/*      */     }
/* 3054 */     if (this.qi != null) {
/* 3055 */       o.put("qi", this.qi.toString());
/*      */     }
/* 3057 */     if (this.oth != null && !this.oth.isEmpty()) {
/*      */       
/* 3059 */       List<Object> a = JSONArrayUtils.newJSONArray();
/*      */       
/* 3061 */       for (OtherPrimesInfo other : this.oth) {
/*      */         
/* 3063 */         Map<String, Object> oo = JSONObjectUtils.newJSONObject();
/* 3064 */         oo.put("r", other.r.toString());
/* 3065 */         oo.put("d", other.d.toString());
/* 3066 */         oo.put("t", other.t.toString());
/*      */         
/* 3068 */         a.add(oo);
/*      */       } 
/*      */       
/* 3071 */       o.put("oth", a);
/*      */     } 
/*      */     
/* 3074 */     return o;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static RSAKey parse(String s) throws ParseException {
/* 3092 */     return parse(JSONObjectUtils.parse(s));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static RSAKey parse(Map<String, Object> jsonObject) throws ParseException {
/* 3112 */     if (!KeyType.RSA.equals(JWKMetadata.parseKeyType(jsonObject))) {
/* 3113 */       throw new ParseException("The key type \"kty\" must be RSA", 0);
/*      */     }
/*      */ 
/*      */     
/* 3117 */     Base64URL n = JSONObjectUtils.getBase64URL(jsonObject, "n");
/* 3118 */     Base64URL e = JSONObjectUtils.getBase64URL(jsonObject, "e");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3123 */     Base64URL d = JSONObjectUtils.getBase64URL(jsonObject, "d");
/*      */ 
/*      */     
/* 3126 */     Base64URL p = JSONObjectUtils.getBase64URL(jsonObject, "p");
/* 3127 */     Base64URL q = JSONObjectUtils.getBase64URL(jsonObject, "q");
/* 3128 */     Base64URL dp = JSONObjectUtils.getBase64URL(jsonObject, "dp");
/* 3129 */     Base64URL dq = JSONObjectUtils.getBase64URL(jsonObject, "dq");
/* 3130 */     Base64URL qi = JSONObjectUtils.getBase64URL(jsonObject, "qi");
/*      */     
/* 3132 */     List<OtherPrimesInfo> oth = null;
/* 3133 */     if (jsonObject.containsKey("oth")) {
/*      */       
/* 3135 */       List<Object> arr = JSONObjectUtils.getJSONArray(jsonObject, "oth");
/* 3136 */       if (arr != null) {
/* 3137 */         oth = new ArrayList<>(arr.size());
/*      */         
/* 3139 */         for (Object o : arr) {
/*      */           
/* 3141 */           if (o instanceof Map) {
/* 3142 */             Map<String, Object> otherJson = (Map<String, Object>)o;
/*      */             
/* 3144 */             Base64URL r = JSONObjectUtils.getBase64URL(otherJson, "r");
/* 3145 */             Base64URL odq = JSONObjectUtils.getBase64URL(otherJson, "dq");
/* 3146 */             Base64URL t = JSONObjectUtils.getBase64URL(otherJson, "t");
/*      */             try {
/* 3148 */               oth.add(new OtherPrimesInfo(r, odq, t));
/* 3149 */             } catch (IllegalArgumentException iae) {
/* 3150 */               throw new ParseException(iae.getMessage(), 0);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*      */     try {
/* 3158 */       return new RSAKey(n, e, d, p, q, dp, dq, qi, oth, null, 
/* 3159 */           JWKMetadata.parseKeyUse(jsonObject), 
/* 3160 */           JWKMetadata.parseKeyOperations(jsonObject), 
/* 3161 */           JWKMetadata.parseAlgorithm(jsonObject), 
/* 3162 */           JWKMetadata.parseKeyID(jsonObject), 
/* 3163 */           JWKMetadata.parseX509CertURL(jsonObject), 
/* 3164 */           JWKMetadata.parseX509CertThumbprint(jsonObject), 
/* 3165 */           JWKMetadata.parseX509CertSHA256Thumbprint(jsonObject), 
/* 3166 */           JWKMetadata.parseX509CertChain(jsonObject), 
/* 3167 */           JWKMetadata.parseExpirationTime(jsonObject), 
/* 3168 */           JWKMetadata.parseNotBeforeTime(jsonObject), 
/* 3169 */           JWKMetadata.parseIssueTime(jsonObject), 
/* 3170 */           JWKMetadata.parseKeyRevocation(jsonObject), null);
/*      */     
/*      */     }
/* 3173 */     catch (Exception ex) {
/*      */ 
/*      */       
/* 3176 */       throw new ParseException(ex.getMessage(), 0);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static RSAKey parse(X509Certificate cert) throws JOSEException {
/* 3205 */     if (!(cert.getPublicKey() instanceof RSAPublicKey)) {
/* 3206 */       throw new JOSEException("The public key of the X.509 certificate is not RSA");
/*      */     }
/*      */     
/* 3209 */     RSAPublicKey publicKey = (RSAPublicKey)cert.getPublicKey();
/*      */     
/*      */     try {
/* 3212 */       MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
/*      */       
/* 3214 */       return (new Builder(publicKey))
/* 3215 */         .keyUse(KeyUse.from(cert))
/* 3216 */         .keyID(cert.getSerialNumber().toString(10))
/* 3217 */         .x509CertChain(Collections.singletonList(Base64.encode(cert.getEncoded())))
/* 3218 */         .x509CertSHA256Thumbprint(Base64URL.encode(sha256.digest(cert.getEncoded())))
/* 3219 */         .expirationTime(cert.getNotAfter())
/* 3220 */         .notBeforeTime(cert.getNotBefore())
/* 3221 */         .build();
/* 3222 */     } catch (NoSuchAlgorithmException e) {
/* 3223 */       throw new JOSEException("Couldn't encode x5t parameter: " + e.getMessage(), e);
/* 3224 */     } catch (CertificateEncodingException e) {
/* 3225 */       throw new JOSEException("Couldn't encode x5c parameter: " + e.getMessage(), e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static RSAKey load(KeyStore keyStore, String alias, char[] pin) throws KeyStoreException, JOSEException {
/*      */     Key key;
/* 3252 */     Certificate cert = keyStore.getCertificate(alias);
/*      */     
/* 3254 */     if (!(cert instanceof X509Certificate)) {
/* 3255 */       return null;
/*      */     }
/*      */     
/* 3258 */     X509Certificate x509Cert = (X509Certificate)cert;
/*      */     
/* 3260 */     if (!(x509Cert.getPublicKey() instanceof RSAPublicKey)) {
/* 3261 */       throw new JOSEException("Couldn't load RSA JWK: The key algorithm is not RSA");
/*      */     }
/*      */     
/* 3264 */     RSAKey rsaJWK = parse(x509Cert);
/*      */ 
/*      */     
/* 3267 */     rsaJWK = (new Builder(rsaJWK)).keyID(alias).keyStore(keyStore).build();
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 3272 */       key = keyStore.getKey(alias, pin);
/* 3273 */     } catch (UnrecoverableKeyException|NoSuchAlgorithmException e) {
/* 3274 */       throw new JOSEException("Couldn't retrieve private RSA key (bad pin?): " + e.getMessage(), e);
/*      */     } 
/*      */     
/* 3277 */     if (key instanceof RSAPrivateKey)
/*      */     {
/* 3279 */       return (new Builder(rsaJWK))
/* 3280 */         .privateKey((RSAPrivateKey)key)
/* 3281 */         .build(); } 
/* 3282 */     if (key instanceof PrivateKey && "RSA".equalsIgnoreCase(key.getAlgorithm()))
/*      */     {
/* 3284 */       return (new Builder(rsaJWK))
/* 3285 */         .privateKey((PrivateKey)key)
/* 3286 */         .build();
/*      */     }
/* 3288 */     return rsaJWK;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object o) {
/* 3295 */     if (this == o) return true; 
/* 3296 */     if (!(o instanceof RSAKey)) return false; 
/* 3297 */     if (!super.equals(o)) return false; 
/* 3298 */     RSAKey rsaKey = (RSAKey)o;
/* 3299 */     return (Objects.equals(this.n, rsaKey.n) && 
/* 3300 */       Objects.equals(this.e, rsaKey.e) && 
/* 3301 */       Objects.equals(this.d, rsaKey.d) && 
/* 3302 */       Objects.equals(this.p, rsaKey.p) && 
/* 3303 */       Objects.equals(this.q, rsaKey.q) && 
/* 3304 */       Objects.equals(this.dp, rsaKey.dp) && 
/* 3305 */       Objects.equals(this.dq, rsaKey.dq) && 
/* 3306 */       Objects.equals(this.qi, rsaKey.qi) && 
/* 3307 */       Objects.equals(this.oth, rsaKey.oth) && 
/* 3308 */       Objects.equals(this.privateKey, rsaKey.privateKey));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 3314 */     return Objects.hash(new Object[] { Integer.valueOf(super.hashCode()), this.n, this.e, this.d, this.p, this.q, this.dp, this.dq, this.qi, this.oth, this.privateKey });
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\jwk\RSAKey.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */