/*      */ package com.nimbusds.jose.jwk;
/*      */ 
/*      */ import com.nimbusds.jose.Algorithm;
/*      */ import com.nimbusds.jose.JOSEException;
/*      */ import com.nimbusds.jose.shaded.jcip.Immutable;
/*      */ import com.nimbusds.jose.util.Base64;
/*      */ import com.nimbusds.jose.util.Base64URL;
/*      */ import com.nimbusds.jose.util.ByteUtils;
/*      */ import com.nimbusds.jose.util.JSONObjectUtils;
/*      */ import java.net.URI;
/*      */ import java.security.KeyPair;
/*      */ import java.security.KeyStore;
/*      */ import java.security.PrivateKey;
/*      */ import java.security.PublicKey;
/*      */ import java.security.cert.X509Certificate;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*      */ public class OctetKeyPair
/*      */   extends JWK
/*      */   implements AsymmetricJWK, CurveBasedJWK
/*      */ {
/*      */   private static final long serialVersionUID = 1L;
/*   99 */   public static final Set<Curve> SUPPORTED_CURVES = Collections.unmodifiableSet(new HashSet<>(
/*  100 */         Arrays.asList(new Curve[] { Curve.Ed25519, Curve.Ed448, Curve.X25519, Curve.X448 })));
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
/*      */   private final byte[] decodedX;
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
/*      */   private final byte[] decodedD;
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
/*      */     private Base64URL d;
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
/*      */     private String kid;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private URI x5u;
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
/*      */     private Base64URL x5t256;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private List<Base64> x5c;
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
/*      */     public Builder(Curve crv, Base64URL x) {
/*  227 */       this.crv = Objects.<Curve>requireNonNull(crv, "The curve must not be null");
/*  228 */       this.x = Objects.<Base64URL>requireNonNull(x, "The x coordinate must not be null");
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
/*      */     public Builder(OctetKeyPair okpJWK) {
/*  240 */       this.crv = okpJWK.crv;
/*  241 */       this.x = okpJWK.x;
/*  242 */       this.d = okpJWK.d;
/*  243 */       this.use = okpJWK.getKeyUse();
/*  244 */       this.ops = okpJWK.getKeyOperations();
/*  245 */       this.alg = okpJWK.getAlgorithm();
/*  246 */       this.kid = okpJWK.getKeyID();
/*  247 */       this.x5u = okpJWK.getX509CertURL();
/*  248 */       this.x5t = okpJWK.getX509CertThumbprint();
/*  249 */       this.x5t256 = okpJWK.getX509CertSHA256Thumbprint();
/*  250 */       this.x5c = okpJWK.getX509CertChain();
/*  251 */       this.exp = okpJWK.getExpirationTime();
/*  252 */       this.nbf = okpJWK.getNotBeforeTime();
/*  253 */       this.iat = okpJWK.getIssueTime();
/*  254 */       this.revocation = okpJWK.getKeyRevocation();
/*  255 */       this.ks = okpJWK.getKeyStore();
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
/*      */     public Builder d(Base64URL d) {
/*  269 */       this.d = d;
/*  270 */       return this;
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
/*  285 */       this.use = use;
/*  286 */       return this;
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
/*  300 */       this.ops = ops;
/*  301 */       return this;
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
/*  315 */       this.alg = alg;
/*  316 */       return this;
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
/*  332 */       this.kid = kid;
/*  333 */       return this;
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
/*  353 */       return keyIDFromThumbprint("SHA-256");
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
/*  376 */       LinkedHashMap<String, String> requiredParams = new LinkedHashMap<>();
/*  377 */       requiredParams.put("crv", this.crv.toString());
/*  378 */       requiredParams.put("kty", KeyType.OKP.getValue());
/*  379 */       requiredParams.put("x", this.x.toString());
/*  380 */       this.kid = ThumbprintUtils.compute(hashAlg, requiredParams).toString();
/*  381 */       return this;
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
/*  395 */       this.x5u = x5u;
/*  396 */       return this;
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
/*  412 */       this.x5t = x5t;
/*  413 */       return this;
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
/*  428 */       this.x5t256 = x5t256;
/*  429 */       return this;
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
/*  443 */       this.x5c = x5c;
/*  444 */       return this;
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
/*  458 */       this.exp = exp;
/*  459 */       return this;
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
/*  473 */       this.nbf = nbf;
/*  474 */       return this;
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
/*  488 */       this.iat = iat;
/*  489 */       return this;
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
/*  503 */       this.revocation = revocation;
/*  504 */       return this;
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
/*  518 */       this.ks = keyStore;
/*  519 */       return this;
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
/*      */     public OctetKeyPair build() {
/*      */       try {
/*  534 */         if (this.d == null)
/*      */         {
/*  536 */           return new OctetKeyPair(this.crv, this.x, this.use, this.ops, this.alg, this.kid, this.x5u, this.x5t, this.x5t256, this.x5c, this.exp, this.nbf, this.iat, this.revocation, this.ks);
/*      */         }
/*      */ 
/*      */         
/*  540 */         return new OctetKeyPair(this.crv, this.x, this.d, this.use, this.ops, this.alg, this.kid, this.x5u, this.x5t, this.x5t256, this.x5c, this.exp, this.nbf, this.iat, this.revocation, this.ks);
/*      */       }
/*  542 */       catch (IllegalArgumentException e) {
/*  543 */         throw new IllegalStateException(e.getMessage(), e);
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
/*      */   @Deprecated
/*      */   public OctetKeyPair(Curve crv, Base64URL x, KeyUse use, Set<KeyOperation> ops, Algorithm alg, String kid, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c, KeyStore ks) {
/*  612 */     this(crv, x, use, ops, alg, kid, x5u, x5t, x5t256, x5c, (Date)null, (Date)null, (Date)null, ks);
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
/*      */   public OctetKeyPair(Curve crv, Base64URL x, Base64URL d, KeyUse use, Set<KeyOperation> ops, Algorithm alg, String kid, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c, KeyStore ks) {
/*  646 */     this(crv, x, d, use, ops, alg, kid, x5u, x5t, x5t256, x5c, (Date)null, (Date)null, (Date)null, ks);
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
/*      */   public OctetKeyPair(Curve crv, Base64URL x, KeyUse use, Set<KeyOperation> ops, Algorithm alg, String kid, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c, Date exp, Date nbf, Date iat, KeyStore ks) {
/*  685 */     this(crv, x, use, ops, alg, kid, x5u, x5t, x5t256, x5c, exp, nbf, iat, (KeyRevocation)null, ks);
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
/*      */   public OctetKeyPair(Curve crv, Base64URL x, KeyUse use, Set<KeyOperation> ops, Algorithm alg, String kid, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c, Date exp, Date nbf, Date iat, KeyRevocation revocation, KeyStore ks) {
/*  728 */     super(KeyType.OKP, use, ops, alg, kid, x5u, x5t, x5t256, x5c, exp, nbf, iat, revocation, ks);
/*      */     
/*  730 */     Objects.requireNonNull(crv, "The curve must not be null");
/*  731 */     if (!SUPPORTED_CURVES.contains(crv)) {
/*  732 */       throw new IllegalArgumentException("Unknown / unsupported curve: " + crv);
/*      */     }
/*      */     
/*  735 */     this.crv = crv;
/*      */     
/*  737 */     this.x = Objects.<Base64URL>requireNonNull(x, "The x parameter must not be null");
/*  738 */     this.decodedX = x.decode();
/*      */     
/*  740 */     this.d = null;
/*  741 */     this.decodedD = null;
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
/*      */   public OctetKeyPair(Curve crv, Base64URL x, Base64URL d, KeyUse use, Set<KeyOperation> ops, Algorithm alg, String kid, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c, Date exp, Date nbf, Date iat, KeyStore ks) {
/*  781 */     this(crv, x, d, use, ops, alg, kid, x5u, x5t, x5t256, x5c, exp, nbf, iat, (KeyRevocation)null, ks);
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
/*      */   public OctetKeyPair(Curve crv, Base64URL x, Base64URL d, KeyUse use, Set<KeyOperation> ops, Algorithm alg, String kid, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c, Date exp, Date nbf, Date iat, KeyRevocation revocation, KeyStore ks) {
/*  826 */     super(KeyType.OKP, use, ops, alg, kid, x5u, x5t, x5t256, x5c, exp, nbf, iat, revocation, ks);
/*      */     
/*  828 */     Objects.requireNonNull(crv, "The curve must not be null");
/*  829 */     if (!SUPPORTED_CURVES.contains(crv)) {
/*  830 */       throw new IllegalArgumentException("Unknown / unsupported curve: " + crv);
/*      */     }
/*  832 */     this.crv = crv;
/*      */     
/*  834 */     this.x = Objects.<Base64URL>requireNonNull(x, "The x parameter must not be null");
/*  835 */     this.decodedX = x.decode();
/*      */     
/*  837 */     this.d = Objects.<Base64URL>requireNonNull(d, "The d parameter must not be null");
/*  838 */     this.decodedD = d.decode();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Curve getCurve() {
/*  845 */     return this.crv;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Base64URL getX() {
/*  856 */     return this.x;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] getDecodedX() {
/*  867 */     return (byte[])this.decodedX.clone();
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
/*      */   public Base64URL getD() {
/*  879 */     return this.d;
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
/*      */   public byte[] getDecodedD() {
/*  891 */     return (this.decodedD == null) ? null : (byte[])this.decodedD.clone();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PublicKey toPublicKey() throws JOSEException {
/*  899 */     throw new JOSEException("Export to java.security.PublicKey not supported");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrivateKey toPrivateKey() throws JOSEException {
/*  907 */     throw new JOSEException("Export to java.security.PrivateKey not supported");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public KeyPair toKeyPair() throws JOSEException {
/*  915 */     throw new JOSEException("Export to java.security.KeyPair not supported");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean matches(X509Certificate cert) {
/*  922 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LinkedHashMap<String, ?> getRequiredParams() {
/*  930 */     LinkedHashMap<String, String> requiredParams = new LinkedHashMap<>();
/*  931 */     requiredParams.put("crv", this.crv.toString());
/*  932 */     requiredParams.put("kty", getKeyType().getValue());
/*  933 */     requiredParams.put("x", this.x.toString());
/*  934 */     return requiredParams;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isPrivate() {
/*  941 */     return (this.d != null);
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
/*      */   public OctetKeyPair toPublicJWK() {
/*  954 */     return new OctetKeyPair(
/*  955 */         getCurve(), getX(), 
/*  956 */         getKeyUse(), getKeyOperations(), getAlgorithm(), getKeyID(), 
/*  957 */         getX509CertURL(), getX509CertThumbprint(), getX509CertSHA256Thumbprint(), getX509CertChain(), 
/*  958 */         getExpirationTime(), getNotBeforeTime(), getIssueTime(), getKeyRevocation(), 
/*  959 */         getKeyStore());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public OctetKeyPair toRevokedJWK(KeyRevocation keyRevocation) {
/*  966 */     if (getKeyRevocation() != null) {
/*  967 */       throw new IllegalStateException("Already revoked");
/*      */     }
/*      */     
/*  970 */     return (new Builder(this))
/*  971 */       .keyRevocation(Objects.<KeyRevocation>requireNonNull(keyRevocation))
/*  972 */       .build();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<String, Object> toJSONObject() {
/*  979 */     Map<String, Object> o = super.toJSONObject();
/*      */ 
/*      */     
/*  982 */     o.put("crv", this.crv.toString());
/*  983 */     o.put("x", this.x.toString());
/*      */     
/*  985 */     if (this.d != null) {
/*  986 */       o.put("d", this.d.toString());
/*      */     }
/*      */     
/*  989 */     return o;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int size() {
/*  996 */     return ByteUtils.bitLength(this.x.decode());
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
/*      */   public static OctetKeyPair parse(String s) throws ParseException {
/* 1014 */     return parse(JSONObjectUtils.parse(s));
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
/*      */   public static OctetKeyPair parse(Map<String, Object> jsonObject) throws ParseException {
/*      */     Curve crv;
/* 1034 */     if (!KeyType.OKP.equals(JWKMetadata.parseKeyType(jsonObject))) {
/* 1035 */       throw new ParseException("The key type kty must be " + KeyType.OKP.getValue(), 0);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1041 */       crv = Curve.parse(JSONObjectUtils.getString(jsonObject, "crv"));
/* 1042 */     } catch (IllegalArgumentException e) {
/* 1043 */       throw new ParseException(e.getMessage(), 0);
/*      */     } 
/*      */     
/* 1046 */     Base64URL x = JSONObjectUtils.getBase64URL(jsonObject, "x");
/*      */ 
/*      */     
/* 1049 */     Base64URL d = JSONObjectUtils.getBase64URL(jsonObject, "d");
/*      */     
/*      */     try {
/* 1052 */       if (d == null)
/*      */       {
/* 1054 */         return new OctetKeyPair(crv, x, 
/* 1055 */             JWKMetadata.parseKeyUse(jsonObject), 
/* 1056 */             JWKMetadata.parseKeyOperations(jsonObject), 
/* 1057 */             JWKMetadata.parseAlgorithm(jsonObject), 
/* 1058 */             JWKMetadata.parseKeyID(jsonObject), 
/* 1059 */             JWKMetadata.parseX509CertURL(jsonObject), 
/* 1060 */             JWKMetadata.parseX509CertThumbprint(jsonObject), 
/* 1061 */             JWKMetadata.parseX509CertSHA256Thumbprint(jsonObject), 
/* 1062 */             JWKMetadata.parseX509CertChain(jsonObject), 
/* 1063 */             JWKMetadata.parseExpirationTime(jsonObject), 
/* 1064 */             JWKMetadata.parseNotBeforeTime(jsonObject), 
/* 1065 */             JWKMetadata.parseIssueTime(jsonObject), 
/* 1066 */             JWKMetadata.parseKeyRevocation(jsonObject), null);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1071 */       return new OctetKeyPair(crv, x, d, 
/* 1072 */           JWKMetadata.parseKeyUse(jsonObject), 
/* 1073 */           JWKMetadata.parseKeyOperations(jsonObject), 
/* 1074 */           JWKMetadata.parseAlgorithm(jsonObject), 
/* 1075 */           JWKMetadata.parseKeyID(jsonObject), 
/* 1076 */           JWKMetadata.parseX509CertURL(jsonObject), 
/* 1077 */           JWKMetadata.parseX509CertThumbprint(jsonObject), 
/* 1078 */           JWKMetadata.parseX509CertSHA256Thumbprint(jsonObject), 
/* 1079 */           JWKMetadata.parseX509CertChain(jsonObject), 
/* 1080 */           JWKMetadata.parseExpirationTime(jsonObject), 
/* 1081 */           JWKMetadata.parseNotBeforeTime(jsonObject), 
/* 1082 */           JWKMetadata.parseIssueTime(jsonObject), 
/* 1083 */           JWKMetadata.parseKeyRevocation(jsonObject), null);
/*      */ 
/*      */     
/*      */     }
/* 1087 */     catch (Exception ex) {
/*      */ 
/*      */       
/* 1090 */       throw new ParseException(ex.getMessage(), 0);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object o) {
/* 1097 */     if (this == o) return true; 
/* 1098 */     if (!(o instanceof OctetKeyPair)) return false; 
/* 1099 */     if (!super.equals(o)) return false; 
/* 1100 */     OctetKeyPair that = (OctetKeyPair)o;
/* 1101 */     return (Objects.equals(this.crv, that.crv) && 
/* 1102 */       Objects.equals(this.x, that.x) && 
/* 1103 */       Arrays.equals(this.decodedX, that.decodedX) && 
/* 1104 */       Objects.equals(this.d, that.d) && 
/* 1105 */       Arrays.equals(this.decodedD, that.decodedD));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1111 */     int result = Objects.hash(new Object[] { Integer.valueOf(super.hashCode()), this.crv, this.x, this.d });
/* 1112 */     result = 31 * result + Arrays.hashCode(this.decodedX);
/* 1113 */     result = 31 * result + Arrays.hashCode(this.decodedD);
/* 1114 */     return result;
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\jwk\OctetKeyPair.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */