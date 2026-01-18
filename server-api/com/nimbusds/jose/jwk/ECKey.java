/*      */ package com.nimbusds.jose.jwk;
/*      */ 
/*      */ import com.nimbusds.jose.Algorithm;
/*      */ import com.nimbusds.jose.JOSEException;
/*      */ import com.nimbusds.jose.crypto.utils.ECChecks;
/*      */ import com.nimbusds.jose.shaded.jcip.Immutable;
/*      */ import com.nimbusds.jose.util.Base64;
/*      */ import com.nimbusds.jose.util.Base64URL;
/*      */ import com.nimbusds.jose.util.BigIntegerUtils;
/*      */ import com.nimbusds.jose.util.JSONObjectUtils;
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
/*      */ import java.security.Provider;
/*      */ import java.security.PublicKey;
/*      */ import java.security.UnrecoverableKeyException;
/*      */ import java.security.cert.Certificate;
/*      */ import java.security.cert.CertificateEncodingException;
/*      */ import java.security.cert.X509Certificate;
/*      */ import java.security.interfaces.ECPrivateKey;
/*      */ import java.security.interfaces.ECPublicKey;
/*      */ import java.security.spec.ECParameterSpec;
/*      */ import java.security.spec.ECPoint;
/*      */ import java.security.spec.ECPrivateKeySpec;
/*      */ import java.security.spec.ECPublicKeySpec;
/*      */ import java.text.ParseException;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.HashSet;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Objects;
/*      */ import java.util.Set;
/*      */ import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*      */ public final class ECKey
/*      */   extends JWK
/*      */   implements AsymmetricJWK, CurveBasedJWK
/*      */ {
/*      */   private static final long serialVersionUID = 1L;
/*  117 */   public static final Set<Curve> SUPPORTED_CURVES = Collections.unmodifiableSet(new HashSet<>(
/*  118 */         Arrays.asList(new Curve[] { Curve.P_256, Curve.SECP256K1, Curve.P_384, Curve.P_521 })));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Curve crv;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Base64URL x;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Base64URL y;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Base64URL d;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final PrivateKey privateKey;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class Builder
/*      */   {
/*      */     private final Curve crv;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final Base64URL x;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final Base64URL y;
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
/*      */     private PrivateKey priv;
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
/*      */     private Set<KeyOperation> ops;
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
/*      */     private String kid;
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
/*      */     @Deprecated
/*      */     private Base64URL x5t;
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
/*      */     private List<Base64> x5c;
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
/*      */     private Date nbf;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Date iat;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private KeyRevocation revocation;
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
/*      */     public Builder(Curve crv, Base64URL x, Base64URL y) {
/*  263 */       this.crv = Objects.<Curve>requireNonNull(crv, "The curve must not be null");
/*  264 */       this.x = Objects.<Base64URL>requireNonNull(x, "The x coordinate must not be null");
/*  265 */       this.y = Objects.<Base64URL>requireNonNull(y, "The y coordinate must not be null");
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
/*      */     public Builder(Curve crv, ECPublicKey pub) {
/*  279 */       this(crv, 
/*  280 */           ECKey.encodeCoordinate(pub.getParams().getCurve().getField().getFieldSize(), pub.getW().getAffineX()), 
/*  281 */           ECKey.encodeCoordinate(pub.getParams().getCurve().getField().getFieldSize(), pub.getW().getAffineY()));
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
/*      */     public Builder(ECKey ecJWK) {
/*  293 */       this.crv = ecJWK.crv;
/*  294 */       this.x = ecJWK.x;
/*  295 */       this.y = ecJWK.y;
/*  296 */       this.d = ecJWK.d;
/*  297 */       this.priv = ecJWK.privateKey;
/*  298 */       this.use = ecJWK.getKeyUse();
/*  299 */       this.ops = ecJWK.getKeyOperations();
/*  300 */       this.alg = ecJWK.getAlgorithm();
/*  301 */       this.kid = ecJWK.getKeyID();
/*  302 */       this.x5u = ecJWK.getX509CertURL();
/*  303 */       this.x5t = ecJWK.getX509CertThumbprint();
/*  304 */       this.x5t256 = ecJWK.getX509CertSHA256Thumbprint();
/*  305 */       this.x5c = ecJWK.getX509CertChain();
/*  306 */       this.exp = ecJWK.getExpirationTime();
/*  307 */       this.nbf = ecJWK.getNotBeforeTime();
/*  308 */       this.iat = ecJWK.getIssueTime();
/*  309 */       this.revocation = ecJWK.getKeyRevocation();
/*  310 */       this.ks = ecJWK.getKeyStore();
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
/*      */     public Builder d(Base64URL d) {
/*  328 */       this.d = d;
/*  329 */       return this;
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
/*      */     public Builder privateKey(ECPrivateKey priv) {
/*  346 */       if (priv != null) {
/*  347 */         this.d = ECKey.encodeCoordinate(priv.getParams().getCurve().getField().getFieldSize(), priv.getS());
/*      */       } else {
/*  349 */         this.d = null;
/*      */       } 
/*      */       
/*  352 */       return this;
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
/*      */     public Builder privateKey(PrivateKey priv) {
/*  369 */       if (priv instanceof ECPrivateKey)
/*      */       {
/*  371 */         return privateKey((ECPrivateKey)priv);
/*      */       }
/*      */       
/*  374 */       if (priv != null && !"EC".equalsIgnoreCase(priv.getAlgorithm())) {
/*  375 */         throw new IllegalArgumentException("The private key algorithm must be EC");
/*      */       }
/*      */       
/*  378 */       this.priv = priv;
/*  379 */       return this;
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
/*  394 */       this.use = use;
/*  395 */       return this;
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
/*      */     public Builder keyOperations(Set<KeyOperation> ops) {
/*  409 */       this.ops = ops;
/*  410 */       return this;
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
/*  424 */       this.alg = alg;
/*  425 */       return this;
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
/*  441 */       this.kid = kid;
/*  442 */       return this;
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
/*  462 */       return keyIDFromThumbprint("SHA-256");
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
/*  485 */       LinkedHashMap<String, String> requiredParams = new LinkedHashMap<>();
/*  486 */       requiredParams.put("crv", this.crv.toString());
/*  487 */       requiredParams.put("kty", KeyType.EC.getValue());
/*  488 */       requiredParams.put("x", this.x.toString());
/*  489 */       requiredParams.put("y", this.y.toString());
/*  490 */       this.kid = ThumbprintUtils.compute(hashAlg, requiredParams).toString();
/*  491 */       return this;
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
/*  505 */       this.x5u = x5u;
/*  506 */       return this;
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
/*  522 */       this.x5t = x5t;
/*  523 */       return this;
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
/*  538 */       this.x5t256 = x5t256;
/*  539 */       return this;
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
/*  553 */       this.x5c = x5c;
/*  554 */       return this;
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
/*  568 */       this.exp = exp;
/*  569 */       return this;
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
/*  583 */       this.nbf = nbf;
/*  584 */       return this;
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
/*  598 */       this.iat = iat;
/*  599 */       return this;
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
/*  613 */       this.revocation = revocation;
/*  614 */       return this;
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
/*  628 */       this.ks = keyStore;
/*  629 */       return this;
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
/*      */     public ECKey build() {
/*      */       try {
/*  644 */         if (this.d == null && this.priv == null)
/*      */         {
/*  646 */           return new ECKey(this.crv, this.x, this.y, this.use, this.ops, this.alg, this.kid, this.x5u, this.x5t, this.x5t256, this.x5c, this.exp, this.nbf, this.iat, this.revocation, this.ks);
/*      */         }
/*      */         
/*  649 */         if (this.priv != null)
/*      */         {
/*  651 */           return new ECKey(this.crv, this.x, this.y, this.priv, this.use, this.ops, this.alg, this.kid, this.x5u, this.x5t, this.x5t256, this.x5c, this.exp, this.nbf, this.iat, this.revocation, this.ks);
/*      */         }
/*      */ 
/*      */         
/*  655 */         return new ECKey(this.crv, this.x, this.y, this.d, this.use, this.ops, this.alg, this.kid, this.x5u, this.x5t, this.x5t256, this.x5c, this.exp, this.nbf, this.iat, this.revocation, this.ks);
/*      */       }
/*  657 */       catch (IllegalArgumentException e) {
/*  658 */         throw new IllegalStateException(e.getMessage(), e);
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
/*      */   public static Base64URL encodeCoordinate(int fieldSize, BigInteger coordinate) {
/*  678 */     byte[] notPadded = BigIntegerUtils.toBytesUnsigned(coordinate);
/*      */     
/*  680 */     int bytesToOutput = (fieldSize + 7) / 8;
/*      */     
/*  682 */     if (notPadded.length >= bytesToOutput)
/*      */     {
/*      */       
/*  685 */       return Base64URL.encode(notPadded);
/*      */     }
/*      */     
/*  688 */     byte[] padded = new byte[bytesToOutput];
/*      */     
/*  690 */     System.arraycopy(notPadded, 0, padded, bytesToOutput - notPadded.length, notPadded.length);
/*      */     
/*  692 */     return Base64URL.encode(padded);
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
/*      */   private static void ensurePublicCoordinatesOnCurve(Curve crv, Base64URL x, Base64URL y) {
/*  736 */     if (!SUPPORTED_CURVES.contains(crv)) {
/*  737 */       throw new IllegalArgumentException("Unknown / unsupported curve: " + crv);
/*      */     }
/*      */     
/*  740 */     if (!ECChecks.isPointOnCurve(x.decodeToBigInteger(), y.decodeToBigInteger(), crv.toECParameterSpec())) {
/*  741 */       throw new IllegalArgumentException("Invalid EC JWK: The 'x' and 'y' public coordinates are not on the " + crv + " curve");
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
/*      */   @Deprecated
/*      */   public ECKey(Curve crv, Base64URL x, Base64URL y, KeyUse use, Set<KeyOperation> ops, Algorithm alg, String kid, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c, KeyStore ks) {
/*  782 */     this(crv, x, y, use, ops, alg, kid, x5u, x5t, x5t256, x5c, (Date)null, (Date)null, (Date)null, ks);
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
/*      */   public ECKey(Curve crv, Base64URL x, Base64URL y, Base64URL d, KeyUse use, Set<KeyOperation> ops, Algorithm alg, String kid, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c, KeyStore ks) {
/*  826 */     this(crv, x, y, d, use, ops, alg, kid, x5u, x5t, x5t256, x5c, (Date)null, (Date)null, (Date)null, ks);
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
/*      */   public ECKey(Curve crv, Base64URL x, Base64URL y, PrivateKey priv, KeyUse use, Set<KeyOperation> ops, Algorithm alg, String kid, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c, KeyStore ks) {
/*  869 */     this(crv, x, y, priv, use, ops, alg, kid, x5u, x5t, x5t256, x5c, (Date)null, (Date)null, (Date)null, ks);
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
/*      */   @Deprecated
/*      */   public ECKey(Curve crv, ECPublicKey pub, KeyUse use, Set<KeyOperation> ops, Algorithm alg, String kid, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c, KeyStore ks) {
/*  903 */     this(crv, 
/*  904 */         encodeCoordinate(pub.getParams().getCurve().getField().getFieldSize(), pub.getW().getAffineX()), 
/*  905 */         encodeCoordinate(pub.getParams().getCurve().getField().getFieldSize(), pub.getW().getAffineY()), use, ops, alg, kid, x5u, x5t, x5t256, x5c, (Date)null, (Date)null, (Date)null, ks);
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
/*      */   public ECKey(Curve crv, ECPublicKey pub, ECPrivateKey priv, KeyUse use, Set<KeyOperation> ops, Algorithm alg, String kid, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c, KeyStore ks) {
/*  945 */     this(crv, 
/*  946 */         encodeCoordinate(pub.getParams().getCurve().getField().getFieldSize(), pub.getW().getAffineX()), 
/*  947 */         encodeCoordinate(pub.getParams().getCurve().getField().getFieldSize(), pub.getW().getAffineY()), 
/*  948 */         encodeCoordinate(priv.getParams().getCurve().getField().getFieldSize(), priv.getS()), use, ops, alg, kid, x5u, x5t, x5t256, x5c, (Date)null, (Date)null, (Date)null, ks);
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
/*      */   public ECKey(Curve crv, ECPublicKey pub, PrivateKey priv, KeyUse use, Set<KeyOperation> ops, Algorithm alg, String kid, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c, KeyStore ks) {
/*  989 */     this(crv, 
/*      */         
/*  991 */         encodeCoordinate(pub.getParams().getCurve().getField().getFieldSize(), pub.getW().getAffineX()), 
/*  992 */         encodeCoordinate(pub.getParams().getCurve().getField().getFieldSize(), pub.getW().getAffineY()), priv, use, ops, alg, kid, x5u, x5t, x5t256, x5c, (Date)null, (Date)null, (Date)null, ks);
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
/*      */   public ECKey(Curve crv, Base64URL x, Base64URL y, KeyUse use, Set<KeyOperation> ops, Algorithm alg, String kid, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c, Date exp, Date nbf, Date iat, KeyStore ks) {
/* 1042 */     this(crv, x, y, use, ops, alg, kid, x5u, x5t, x5t256, x5c, exp, nbf, iat, (KeyRevocation)null, ks);
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
/*      */   public ECKey(Curve crv, Base64URL x, Base64URL y, KeyUse use, Set<KeyOperation> ops, Algorithm alg, String kid, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c, Date exp, Date nbf, Date iat, KeyRevocation revocation, KeyStore ks) {
/* 1091 */     super(KeyType.EC, use, ops, alg, kid, x5u, x5t, x5t256, x5c, exp, nbf, iat, revocation, ks);
/* 1092 */     this.crv = Objects.<Curve>requireNonNull(crv, "The curve must not be null");
/* 1093 */     this.x = Objects.<Base64URL>requireNonNull(x, "The x coordinate must not be null");
/* 1094 */     this.y = Objects.<Base64URL>requireNonNull(y, "The y coordinate must not be null");
/* 1095 */     ensurePublicCoordinatesOnCurve(crv, x, y);
/* 1096 */     ensureMatches(getParsedX509CertChain());
/* 1097 */     this.d = null;
/* 1098 */     this.privateKey = null;
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
/*      */   public ECKey(Curve crv, Base64URL x, Base64URL y, Base64URL d, KeyUse use, Set<KeyOperation> ops, Algorithm alg, String kid, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c, Date exp, Date nbf, Date iat, KeyStore ks) {
/* 1148 */     this(crv, x, y, d, use, ops, alg, kid, x5u, x5t, x5t256, x5c, exp, nbf, iat, (KeyRevocation)null, ks);
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
/*      */   public ECKey(Curve crv, Base64URL x, Base64URL y, Base64URL d, KeyUse use, Set<KeyOperation> ops, Algorithm alg, String kid, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c, Date exp, Date nbf, Date iat, KeyRevocation revocation, KeyStore ks) {
/* 1201 */     super(KeyType.EC, use, ops, alg, kid, x5u, x5t, x5t256, x5c, exp, nbf, iat, revocation, ks);
/* 1202 */     this.crv = Objects.<Curve>requireNonNull(crv, "The curve must not be null");
/* 1203 */     this.x = Objects.<Base64URL>requireNonNull(x, "The x coordinate must not be null");
/* 1204 */     this.y = Objects.<Base64URL>requireNonNull(y, "The y coordinate must not be null");
/* 1205 */     ensurePublicCoordinatesOnCurve(crv, x, y);
/* 1206 */     ensureMatches(getParsedX509CertChain());
/* 1207 */     this.d = Objects.<Base64URL>requireNonNull(d, "The d coordinate must not be null");
/* 1208 */     this.privateKey = null;
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
/*      */   @Deprecated
/*      */   public ECKey(Curve crv, Base64URL x, Base64URL y, PrivateKey priv, KeyUse use, Set<KeyOperation> ops, Algorithm alg, String kid, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c, Date exp, Date nbf, Date iat, KeyStore ks) {
/* 1257 */     this(crv, x, y, priv, use, ops, alg, kid, x5u, x5t, x5t256, x5c, exp, nbf, iat, (KeyRevocation)null, ks);
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
/*      */   public ECKey(Curve crv, Base64URL x, Base64URL y, PrivateKey priv, KeyUse use, Set<KeyOperation> ops, Algorithm alg, String kid, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c, Date exp, Date nbf, Date iat, KeyRevocation revocation, KeyStore ks) {
/* 1309 */     super(KeyType.EC, use, ops, alg, kid, x5u, x5t, x5t256, x5c, exp, nbf, iat, revocation, ks);
/* 1310 */     this.crv = Objects.<Curve>requireNonNull(crv, "The curve must not be null");
/* 1311 */     this.x = Objects.<Base64URL>requireNonNull(x, "The x coordinate must not be null");
/* 1312 */     this.y = Objects.<Base64URL>requireNonNull(y, "The y coordinate must not be null");
/* 1313 */     ensurePublicCoordinatesOnCurve(crv, x, y);
/* 1314 */     ensureMatches(getParsedX509CertChain());
/* 1315 */     this.d = null;
/* 1316 */     this.privateKey = priv;
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
/*      */   public ECKey(Curve crv, ECPublicKey pub, KeyUse use, Set<KeyOperation> ops, Algorithm alg, String kid, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c, Date exp, Date nbf, Date iat, KeyStore ks) {
/* 1356 */     this(crv, pub, use, ops, alg, kid, x5u, x5t, x5t256, x5c, exp, nbf, iat, (KeyRevocation)null, ks);
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
/*      */   public ECKey(Curve crv, ECPublicKey pub, KeyUse use, Set<KeyOperation> ops, Algorithm alg, String kid, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c, Date exp, Date nbf, Date iat, KeyRevocation revocation, KeyStore ks) {
/* 1399 */     this(crv, 
/* 1400 */         encodeCoordinate(pub.getParams().getCurve().getField().getFieldSize(), pub.getW().getAffineX()), 
/* 1401 */         encodeCoordinate(pub.getParams().getCurve().getField().getFieldSize(), pub.getW().getAffineY()), use, ops, alg, kid, x5u, x5t, x5t256, x5c, exp, nbf, iat, revocation, ks);
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
/*      */   @Deprecated
/*      */   public ECKey(Curve crv, ECPublicKey pub, ECPrivateKey priv, KeyUse use, Set<KeyOperation> ops, Algorithm alg, String kid, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c, Date exp, Date nbf, Date iat, KeyStore ks) {
/* 1447 */     this(crv, pub, priv, use, ops, alg, kid, x5u, x5t, x5t256, x5c, exp, nbf, iat, (KeyRevocation)null, ks);
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
/*      */   public ECKey(Curve crv, ECPublicKey pub, ECPrivateKey priv, KeyUse use, Set<KeyOperation> ops, Algorithm alg, String kid, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c, Date exp, Date nbf, Date iat, KeyRevocation revocation, KeyStore ks) {
/* 1492 */     this(crv, 
/* 1493 */         encodeCoordinate(pub.getParams().getCurve().getField().getFieldSize(), pub.getW().getAffineX()), 
/* 1494 */         encodeCoordinate(pub.getParams().getCurve().getField().getFieldSize(), pub.getW().getAffineY()), 
/* 1495 */         encodeCoordinate(priv.getParams().getCurve().getField().getFieldSize(), priv.getS()), use, ops, alg, kid, x5u, x5t, x5t256, x5c, exp, nbf, iat, revocation, ks);
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
/*      */   public ECKey(Curve crv, ECPublicKey pub, PrivateKey priv, KeyUse use, Set<KeyOperation> ops, Algorithm alg, String kid, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c, Date exp, Date nbf, Date iat, KeyStore ks) {
/* 1542 */     this(crv, 
/*      */         
/* 1544 */         encodeCoordinate(pub.getParams().getCurve().getField().getFieldSize(), pub.getW().getAffineX()), 
/* 1545 */         encodeCoordinate(pub.getParams().getCurve().getField().getFieldSize(), pub.getW().getAffineY()), priv, use, ops, alg, kid, x5u, x5t, x5t256, x5c, exp, nbf, iat, ks);
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
/*      */   public ECKey(Curve crv, ECPublicKey pub, PrivateKey priv, KeyUse use, Set<KeyOperation> ops, Algorithm alg, String kid, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c, Date exp, Date nbf, Date iat, KeyRevocation revocation, KeyStore ks) {
/* 1595 */     this(crv, 
/*      */         
/* 1597 */         encodeCoordinate(pub.getParams().getCurve().getField().getFieldSize(), pub.getW().getAffineX()), 
/* 1598 */         encodeCoordinate(pub.getParams().getCurve().getField().getFieldSize(), pub.getW().getAffineY()), priv, use, ops, alg, kid, x5u, x5t, x5t256, x5c, exp, nbf, iat, revocation, ks);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Curve getCurve() {
/* 1609 */     return this.crv;
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
/*      */   public Base64URL getX() {
/* 1621 */     return this.x;
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
/*      */   public Base64URL getY() {
/* 1633 */     return this.y;
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
/*      */   public Base64URL getD() {
/* 1648 */     return this.d;
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
/*      */   public ECPublicKey toECPublicKey() throws JOSEException {
/* 1666 */     return toECPublicKey((Provider)null);
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
/*      */   public ECPublicKey toECPublicKey(Provider provider) throws JOSEException {
/* 1686 */     ECParameterSpec spec = this.crv.toECParameterSpec();
/*      */     
/* 1688 */     if (spec == null) {
/* 1689 */       throw new JOSEException("Couldn't get EC parameter spec for curve " + this.crv);
/*      */     }
/*      */     
/* 1692 */     ECPoint w = new ECPoint(this.x.decodeToBigInteger(), this.y.decodeToBigInteger());
/*      */     
/* 1694 */     ECPublicKeySpec publicKeySpec = new ECPublicKeySpec(w, spec);
/*      */     
/*      */     try {
/*      */       KeyFactory keyFactory;
/*      */       
/* 1699 */       if (provider == null) {
/* 1700 */         keyFactory = KeyFactory.getInstance("EC");
/*      */       } else {
/* 1702 */         keyFactory = KeyFactory.getInstance("EC", provider);
/*      */       } 
/*      */       
/* 1705 */       return (ECPublicKey)keyFactory.generatePublic(publicKeySpec);
/*      */     }
/* 1707 */     catch (NoSuchAlgorithmException|java.security.spec.InvalidKeySpecException e) {
/*      */       
/* 1709 */       throw new JOSEException(e.getMessage(), e);
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
/*      */   public ECPrivateKey toECPrivateKey() throws JOSEException {
/* 1729 */     return toECPrivateKey((Provider)null);
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
/*      */   public ECPrivateKey toECPrivateKey(Provider provider) throws JOSEException {
/* 1750 */     if (this.d == null)
/*      */     {
/* 1752 */       return null;
/*      */     }
/*      */     
/* 1755 */     ECParameterSpec spec = this.crv.toECParameterSpec();
/*      */     
/* 1757 */     if (spec == null) {
/* 1758 */       throw new JOSEException("Couldn't get EC parameter spec for curve " + this.crv);
/*      */     }
/*      */     
/* 1761 */     ECPrivateKeySpec privateKeySpec = new ECPrivateKeySpec(this.d.decodeToBigInteger(), spec);
/*      */     
/*      */     try {
/*      */       KeyFactory keyFactory;
/*      */       
/* 1766 */       if (provider == null) {
/* 1767 */         keyFactory = KeyFactory.getInstance("EC");
/*      */       } else {
/* 1769 */         keyFactory = KeyFactory.getInstance("EC", provider);
/*      */       } 
/*      */       
/* 1772 */       return (ECPrivateKey)keyFactory.generatePrivate(privateKeySpec);
/*      */     }
/* 1774 */     catch (NoSuchAlgorithmException|java.security.spec.InvalidKeySpecException e) {
/*      */       
/* 1776 */       throw new JOSEException(e.getMessage(), e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PublicKey toPublicKey() throws JOSEException {
/* 1785 */     return toECPublicKey();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrivateKey toPrivateKey() throws JOSEException {
/* 1793 */     PrivateKey prv = toECPrivateKey();
/*      */     
/* 1795 */     if (prv != null)
/*      */     {
/* 1797 */       return prv;
/*      */     }
/*      */ 
/*      */     
/* 1801 */     return this.privateKey;
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
/* 1821 */     return toKeyPair((Provider)null);
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
/*      */   public KeyPair toKeyPair(Provider provider) throws JOSEException {
/* 1843 */     if (this.privateKey != null)
/*      */     {
/* 1845 */       return new KeyPair(toECPublicKey(provider), this.privateKey);
/*      */     }
/* 1847 */     return new KeyPair(toECPublicKey(provider), toECPrivateKey(provider));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ECKey toRevokedJWK(KeyRevocation keyRevocation) {
/* 1855 */     if (getKeyRevocation() != null) {
/* 1856 */       throw new IllegalStateException("Already revoked");
/*      */     }
/*      */     
/* 1859 */     return (new Builder(this))
/* 1860 */       .keyRevocation(Objects.<KeyRevocation>requireNonNull(keyRevocation))
/* 1861 */       .build();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean matches(X509Certificate cert) {
/*      */     ECPublicKey certECKey;
/*      */     try {
/* 1870 */       certECKey = (ECPublicKey)((X509Certificate)getParsedX509CertChain().get(0)).getPublicKey();
/* 1871 */     } catch (ClassCastException ex) {
/* 1872 */       return false;
/*      */     } 
/*      */ 
/*      */     
/* 1876 */     if (!getX().decodeToBigInteger().equals(certECKey.getW().getAffineX())) {
/* 1877 */       return false;
/*      */     }
/* 1879 */     return getY().decodeToBigInteger().equals(certECKey.getW().getAffineY());
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
/*      */   private void ensureMatches(List<X509Certificate> chain) {
/* 1896 */     if (chain == null) {
/*      */       return;
/*      */     }
/* 1899 */     if (!matches(chain.get(0))) {
/* 1900 */       throw new IllegalArgumentException("The public subject key info of the first X.509 certificate in the chain must match the JWK type and public parameters");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LinkedHashMap<String, ?> getRequiredParams() {
/* 1908 */     LinkedHashMap<String, String> requiredParams = new LinkedHashMap<>();
/* 1909 */     requiredParams.put("crv", this.crv.toString());
/* 1910 */     requiredParams.put("kty", getKeyType().getValue());
/* 1911 */     requiredParams.put("x", this.x.toString());
/* 1912 */     requiredParams.put("y", this.y.toString());
/* 1913 */     return requiredParams;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isPrivate() {
/* 1920 */     return (this.d != null || this.privateKey != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int size() {
/* 1927 */     ECParameterSpec ecParameterSpec = this.crv.toECParameterSpec();
/*      */     
/* 1929 */     if (ecParameterSpec == null) {
/* 1930 */       throw new UnsupportedOperationException("Couldn't determine field size for curve " + this.crv.getName());
/*      */     }
/*      */     
/* 1933 */     return ecParameterSpec.getCurve().getField().getFieldSize();
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
/*      */   public ECKey toPublicJWK() {
/* 1946 */     return new ECKey(
/* 1947 */         getCurve(), getX(), getY(), 
/* 1948 */         getKeyUse(), getKeyOperations(), getAlgorithm(), getKeyID(), 
/* 1949 */         getX509CertURL(), getX509CertThumbprint(), getX509CertSHA256Thumbprint(), getX509CertChain(), 
/* 1950 */         getExpirationTime(), getNotBeforeTime(), getIssueTime(), getKeyRevocation(), 
/* 1951 */         getKeyStore());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<String, Object> toJSONObject() {
/* 1958 */     Map<String, Object> o = super.toJSONObject();
/*      */ 
/*      */     
/* 1961 */     o.put("crv", this.crv.toString());
/* 1962 */     o.put("x", this.x.toString());
/* 1963 */     o.put("y", this.y.toString());
/*      */     
/* 1965 */     if (this.d != null) {
/* 1966 */       o.put("d", this.d.toString());
/*      */     }
/*      */     
/* 1969 */     return o;
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
/*      */   public static ECKey parse(String s) throws ParseException {
/* 1987 */     return parse(JSONObjectUtils.parse(s));
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
/*      */   public static ECKey parse(Map<String, Object> jsonObject) throws ParseException {
/*      */     Curve crv;
/* 2007 */     if (!KeyType.EC.equals(JWKMetadata.parseKeyType(jsonObject))) {
/* 2008 */       throw new ParseException("The key type \"kty\" must be EC", 0);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 2014 */       crv = Curve.parse(JSONObjectUtils.getString(jsonObject, "crv"));
/* 2015 */     } catch (IllegalArgumentException e) {
/* 2016 */       throw new ParseException(e.getMessage(), 0);
/*      */     } 
/*      */     
/* 2019 */     Base64URL x = JSONObjectUtils.getBase64URL(jsonObject, "x");
/* 2020 */     Base64URL y = JSONObjectUtils.getBase64URL(jsonObject, "y");
/*      */ 
/*      */     
/* 2023 */     Base64URL d = JSONObjectUtils.getBase64URL(jsonObject, "d");
/*      */     
/*      */     try {
/* 2026 */       if (d == null)
/*      */       {
/* 2028 */         return new ECKey(crv, x, y, 
/* 2029 */             JWKMetadata.parseKeyUse(jsonObject), 
/* 2030 */             JWKMetadata.parseKeyOperations(jsonObject), 
/* 2031 */             JWKMetadata.parseAlgorithm(jsonObject), 
/* 2032 */             JWKMetadata.parseKeyID(jsonObject), 
/* 2033 */             JWKMetadata.parseX509CertURL(jsonObject), 
/* 2034 */             JWKMetadata.parseX509CertThumbprint(jsonObject), 
/* 2035 */             JWKMetadata.parseX509CertSHA256Thumbprint(jsonObject), 
/* 2036 */             JWKMetadata.parseX509CertChain(jsonObject), 
/* 2037 */             JWKMetadata.parseExpirationTime(jsonObject), 
/* 2038 */             JWKMetadata.parseNotBeforeTime(jsonObject), 
/* 2039 */             JWKMetadata.parseIssueTime(jsonObject), 
/* 2040 */             JWKMetadata.parseKeyRevocation(jsonObject), null);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 2045 */       return new ECKey(crv, x, y, d, 
/* 2046 */           JWKMetadata.parseKeyUse(jsonObject), 
/* 2047 */           JWKMetadata.parseKeyOperations(jsonObject), 
/* 2048 */           JWKMetadata.parseAlgorithm(jsonObject), 
/* 2049 */           JWKMetadata.parseKeyID(jsonObject), 
/* 2050 */           JWKMetadata.parseX509CertURL(jsonObject), 
/* 2051 */           JWKMetadata.parseX509CertThumbprint(jsonObject), 
/* 2052 */           JWKMetadata.parseX509CertSHA256Thumbprint(jsonObject), 
/* 2053 */           JWKMetadata.parseX509CertChain(jsonObject), 
/* 2054 */           JWKMetadata.parseExpirationTime(jsonObject), 
/* 2055 */           JWKMetadata.parseNotBeforeTime(jsonObject), 
/* 2056 */           JWKMetadata.parseIssueTime(jsonObject), 
/* 2057 */           JWKMetadata.parseKeyRevocation(jsonObject), null);
/*      */ 
/*      */     
/*      */     }
/* 2061 */     catch (Exception ex) {
/*      */ 
/*      */       
/* 2064 */       throw new ParseException(ex.getMessage(), 0);
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
/*      */   public static ECKey parse(X509Certificate cert) throws JOSEException {
/* 2096 */     if (!(cert.getPublicKey() instanceof ECPublicKey)) {
/* 2097 */       throw new JOSEException("The public key of the X.509 certificate is not EC");
/*      */     }
/*      */     
/* 2100 */     ECPublicKey publicKey = (ECPublicKey)cert.getPublicKey();
/*      */     
/*      */     try {
/* 2103 */       JcaX509CertificateHolder certHolder = new JcaX509CertificateHolder(cert);
/*      */       
/* 2105 */       String oid = certHolder.getSubjectPublicKeyInfo().getAlgorithm().getParameters().toString();
/*      */       
/* 2107 */       Curve crv = Curve.forOID(oid);
/*      */       
/* 2109 */       if (crv == null) {
/* 2110 */         throw new JOSEException("Couldn't determine EC JWK curve for OID " + oid);
/*      */       }
/*      */       
/* 2113 */       MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
/*      */       
/* 2115 */       return (new Builder(crv, publicKey))
/* 2116 */         .keyUse(KeyUse.from(cert))
/* 2117 */         .keyID(cert.getSerialNumber().toString(10))
/* 2118 */         .x509CertChain(Collections.singletonList(Base64.encode(cert.getEncoded())))
/* 2119 */         .x509CertSHA256Thumbprint(Base64URL.encode(sha256.digest(cert.getEncoded())))
/* 2120 */         .expirationTime(cert.getNotAfter())
/* 2121 */         .notBeforeTime(cert.getNotBefore())
/* 2122 */         .build();
/* 2123 */     } catch (NoSuchAlgorithmException e) {
/* 2124 */       throw new JOSEException("Couldn't encode x5t parameter: " + e.getMessage(), e);
/* 2125 */     } catch (CertificateEncodingException e) {
/* 2126 */       throw new JOSEException("Couldn't encode x5c parameter: " + e.getMessage(), e);
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
/*      */   public static ECKey load(KeyStore keyStore, String alias, char[] pin) throws KeyStoreException, JOSEException {
/*      */     Key key;
/* 2154 */     Certificate cert = keyStore.getCertificate(alias);
/*      */     
/* 2156 */     if (!(cert instanceof X509Certificate)) {
/* 2157 */       return null;
/*      */     }
/*      */     
/* 2160 */     X509Certificate x509Cert = (X509Certificate)cert;
/*      */     
/* 2162 */     if (!(x509Cert.getPublicKey() instanceof ECPublicKey)) {
/* 2163 */       throw new JOSEException("Couldn't load EC JWK: The key algorithm is not EC");
/*      */     }
/*      */     
/* 2166 */     ECKey ecJWK = parse(x509Cert);
/*      */ 
/*      */     
/* 2169 */     ecJWK = (new Builder(ecJWK)).keyID(alias).keyStore(keyStore).build();
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 2174 */       key = keyStore.getKey(alias, pin);
/* 2175 */     } catch (UnrecoverableKeyException|NoSuchAlgorithmException e) {
/* 2176 */       throw new JOSEException("Couldn't retrieve private EC key (bad pin?): " + e.getMessage(), e);
/*      */     } 
/*      */     
/* 2179 */     if (key instanceof ECPrivateKey)
/*      */     {
/* 2181 */       return (new Builder(ecJWK))
/* 2182 */         .privateKey((ECPrivateKey)key)
/* 2183 */         .build(); } 
/* 2184 */     if (key instanceof PrivateKey && "EC".equalsIgnoreCase(key.getAlgorithm()))
/*      */     {
/* 2186 */       return (new Builder(ecJWK))
/* 2187 */         .privateKey((PrivateKey)key)
/* 2188 */         .build();
/*      */     }
/* 2190 */     return ecJWK;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object o) {
/* 2197 */     if (this == o) return true; 
/* 2198 */     if (!(o instanceof ECKey)) return false; 
/* 2199 */     if (!super.equals(o)) return false; 
/* 2200 */     ECKey ecKey = (ECKey)o;
/* 2201 */     return (Objects.equals(this.crv, ecKey.crv) && 
/* 2202 */       Objects.equals(this.x, ecKey.x) && 
/* 2203 */       Objects.equals(this.y, ecKey.y) && 
/* 2204 */       Objects.equals(this.d, ecKey.d) && 
/* 2205 */       Objects.equals(this.privateKey, ecKey.privateKey));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 2211 */     return Objects.hash(new Object[] { Integer.valueOf(super.hashCode()), this.crv, this.x, this.y, this.d, this.privateKey });
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\jwk\ECKey.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */