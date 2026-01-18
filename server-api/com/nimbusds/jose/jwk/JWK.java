/*      */ package com.nimbusds.jose.jwk;
/*      */ 
/*      */ import com.nimbusds.jose.Algorithm;
/*      */ import com.nimbusds.jose.JOSEException;
/*      */ import com.nimbusds.jose.util.Base64;
/*      */ import com.nimbusds.jose.util.Base64URL;
/*      */ import com.nimbusds.jose.util.JSONArrayUtils;
/*      */ import com.nimbusds.jose.util.JSONObjectUtils;
/*      */ import com.nimbusds.jose.util.X509CertChainUtils;
/*      */ import com.nimbusds.jose.util.X509CertUtils;
/*      */ import com.nimbusds.jwt.util.DateUtils;
/*      */ import java.io.Serializable;
/*      */ import java.net.URI;
/*      */ import java.security.KeyPair;
/*      */ import java.security.KeyStore;
/*      */ import java.security.KeyStoreException;
/*      */ import java.security.PrivateKey;
/*      */ import java.security.PublicKey;
/*      */ import java.security.cert.Certificate;
/*      */ import java.security.cert.X509Certificate;
/*      */ import java.security.interfaces.ECPrivateKey;
/*      */ import java.security.interfaces.ECPublicKey;
/*      */ import java.security.interfaces.RSAPrivateKey;
/*      */ import java.security.interfaces.RSAPublicKey;
/*      */ import java.security.spec.ECParameterSpec;
/*      */ import java.text.ParseException;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class JWK
/*      */   implements Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 1L;
/*      */   public static final String MIME_TYPE = "application/jwk+json; charset=UTF-8";
/*      */   private final KeyType kty;
/*      */   private final KeyUse use;
/*      */   private final Set<KeyOperation> ops;
/*      */   private final Algorithm alg;
/*      */   private final String kid;
/*      */   private final URI x5u;
/*      */   @Deprecated
/*      */   private final Base64URL x5t;
/*      */   private final Base64URL x5t256;
/*      */   private final List<Base64> x5c;
/*      */   private final Date exp;
/*      */   private final Date nbf;
/*      */   private final Date iat;
/*      */   private final KeyRevocation revocation;
/*      */   private final List<X509Certificate> parsedX5c;
/*      */   private final KeyStore keyStore;
/*      */   
/*      */   @Deprecated
/*      */   protected JWK(KeyType kty, KeyUse use, Set<KeyOperation> ops, Algorithm alg, String kid, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c, KeyStore ks) {
/*  217 */     this(kty, use, ops, alg, kid, x5u, x5t, x5t256, x5c, null, null, null, ks);
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
/*      */   @Deprecated
/*      */   protected JWK(KeyType kty, KeyUse use, Set<KeyOperation> ops, Algorithm alg, String kid, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c, Date exp, Date nbf, Date iat, KeyStore ks) {
/*  262 */     this(kty, use, ops, alg, kid, x5u, x5t, x5t256, x5c, exp, nbf, iat, null, ks);
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
/*      */   protected JWK(KeyType kty, KeyUse use, Set<KeyOperation> ops, Algorithm alg, String kid, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c, Date exp, Date nbf, Date iat, KeyRevocation revocation, KeyStore ks) {
/*  310 */     this.kty = Objects.<KeyType>requireNonNull(kty, "The key type \"kty\" parameter must not be null");
/*      */     
/*  312 */     if (!KeyUseAndOpsConsistency.areConsistent(use, ops)) {
/*  313 */       throw new IllegalArgumentException("The key use \"use\" and key options \"key_ops\" parameters are not consistent, see RFC 7517, section 4.3");
/*      */     }
/*      */ 
/*      */     
/*  317 */     this.use = use;
/*  318 */     this.ops = ops;
/*      */     
/*  320 */     this.alg = alg;
/*  321 */     this.kid = kid;
/*      */     
/*  323 */     this.x5u = x5u;
/*  324 */     this.x5t = x5t;
/*  325 */     this.x5t256 = x5t256;
/*      */     
/*  327 */     if (x5c != null && x5c.isEmpty()) {
/*  328 */       throw new IllegalArgumentException("The X.509 certificate chain \"x5c\" must not be empty");
/*      */     }
/*  330 */     this.x5c = x5c;
/*      */     
/*      */     try {
/*  333 */       this.parsedX5c = X509CertChainUtils.parse(x5c);
/*  334 */     } catch (ParseException e) {
/*  335 */       throw new IllegalArgumentException("Invalid X.509 certificate chain \"x5c\": " + e.getMessage(), e);
/*      */     } 
/*      */     
/*  338 */     this.exp = exp;
/*  339 */     this.nbf = nbf;
/*  340 */     this.iat = iat;
/*  341 */     this.revocation = revocation;
/*      */     
/*  343 */     this.keyStore = ks;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public KeyType getKeyType() {
/*  354 */     return this.kty;
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
/*      */   public KeyUse getKeyUse() {
/*  366 */     return this.use;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<KeyOperation> getKeyOperations() {
/*  377 */     return this.ops;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Algorithm getAlgorithm() {
/*  388 */     return this.alg;
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
/*      */   public String getKeyID() {
/*  402 */     return this.kid;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public URI getX509CertURL() {
/*  413 */     return this.x5u;
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
/*      */   @Deprecated
/*      */   public Base64URL getX509CertThumbprint() {
/*  427 */     return this.x5t;
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
/*      */   public Base64URL getX509CertSHA256Thumbprint() {
/*  440 */     return this.x5t256;
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
/*      */   public List<Base64> getX509CertChain() {
/*  452 */     if (this.x5c == null) {
/*  453 */       return null;
/*      */     }
/*      */     
/*  456 */     return Collections.unmodifiableList(this.x5c);
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
/*      */   public List<X509Certificate> getParsedX509CertChain() {
/*  469 */     if (this.parsedX5c == null) {
/*  470 */       return null;
/*      */     }
/*      */     
/*  473 */     return Collections.unmodifiableList(this.parsedX5c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Date getExpirationTime() {
/*  484 */     return this.exp;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Date getNotBeforeTime() {
/*  495 */     return this.nbf;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Date getIssueTime() {
/*  506 */     return this.iat;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public KeyRevocation getKeyRevocation() {
/*  516 */     return this.revocation;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public KeyStore getKeyStore() {
/*  527 */     return this.keyStore;
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
/*      */   public abstract LinkedHashMap<String, ?> getRequiredParams();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Base64URL computeThumbprint() throws JOSEException {
/*  553 */     return computeThumbprint("SHA-256");
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
/*      */   public Base64URL computeThumbprint(String hashAlg) throws JOSEException {
/*  571 */     return ThumbprintUtils.compute(hashAlg, this);
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
/*      */   public ThumbprintURI computeThumbprintURI() throws JOSEException {
/*  587 */     return new ThumbprintURI("sha-256", computeThumbprint("SHA-256"));
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
/*      */   public abstract boolean isPrivate();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract JWK toPublicJWK();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract JWK toRevokedJWK(KeyRevocation paramKeyRevocation);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract int size();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RSAKey toRSAKey() {
/*  637 */     return (RSAKey)this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ECKey toECKey() {
/*  647 */     return (ECKey)this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public OctetSequenceKey toOctetSequenceKey() {
/*  657 */     return (OctetSequenceKey)this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public OctetKeyPair toOctetKeyPair() {
/*  667 */     return (OctetKeyPair)this;
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
/*      */   public Map<String, Object> toJSONObject() {
/*  689 */     Map<String, Object> o = JSONObjectUtils.newJSONObject();
/*      */     
/*  691 */     o.put("kty", this.kty.getValue());
/*      */     
/*  693 */     if (this.use != null) {
/*  694 */       o.put("use", this.use.identifier());
/*      */     }
/*      */     
/*  697 */     if (this.ops != null) {
/*  698 */       List<Object> stringValues = JSONArrayUtils.newJSONArray();
/*  699 */       for (KeyOperation op : this.ops) {
/*  700 */         stringValues.add(op.identifier());
/*      */       }
/*  702 */       o.put("key_ops", stringValues);
/*      */     } 
/*      */     
/*  705 */     if (this.alg != null) {
/*  706 */       o.put("alg", this.alg.getName());
/*      */     }
/*      */     
/*  709 */     if (this.kid != null) {
/*  710 */       o.put("kid", this.kid);
/*      */     }
/*      */     
/*  713 */     if (this.x5u != null) {
/*  714 */       o.put("x5u", this.x5u.toString());
/*      */     }
/*      */     
/*  717 */     if (this.x5t != null) {
/*  718 */       o.put("x5t", this.x5t.toString());
/*      */     }
/*      */     
/*  721 */     if (this.x5t256 != null) {
/*  722 */       o.put("x5t#S256", this.x5t256.toString());
/*      */     }
/*      */     
/*  725 */     if (this.x5c != null) {
/*  726 */       List<Object> stringValues = JSONArrayUtils.newJSONArray();
/*  727 */       for (Base64 base64 : this.x5c) {
/*  728 */         stringValues.add(base64.toString());
/*      */       }
/*  730 */       o.put("x5c", stringValues);
/*      */     } 
/*      */     
/*  733 */     if (this.exp != null) {
/*  734 */       o.put("exp", Long.valueOf(DateUtils.toSecondsSinceEpoch(this.exp)));
/*      */     }
/*      */     
/*  737 */     if (this.nbf != null) {
/*  738 */       o.put("nbf", Long.valueOf(DateUtils.toSecondsSinceEpoch(this.nbf)));
/*      */     }
/*      */     
/*  741 */     if (this.iat != null) {
/*  742 */       o.put("iat", Long.valueOf(DateUtils.toSecondsSinceEpoch(this.iat)));
/*      */     }
/*      */     
/*  745 */     if (this.revocation != null) {
/*  746 */       o.put("revoked", this.revocation.toJSONObject());
/*      */     }
/*      */     
/*  749 */     return o;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toJSONString() {
/*  759 */     return JSONObjectUtils.toJSONString(toJSONObject());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*  769 */     return JSONObjectUtils.toJSONString(toJSONObject());
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
/*      */   public static JWK parse(String s) throws ParseException {
/*  788 */     return parse(JSONObjectUtils.parse(s));
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
/*      */   public static JWK parse(Map<String, Object> jsonObject) throws ParseException {
/*  808 */     String ktyString = JSONObjectUtils.getString(jsonObject, "kty");
/*      */     
/*  810 */     if (ktyString == null) {
/*  811 */       throw new ParseException("Missing key type \"kty\" parameter", 0);
/*      */     }
/*      */     
/*  814 */     KeyType kty = KeyType.parse(ktyString);
/*      */     
/*  816 */     if (kty == KeyType.EC)
/*      */     {
/*  818 */       return ECKey.parse(jsonObject);
/*      */     }
/*  820 */     if (kty == KeyType.RSA)
/*      */     {
/*  822 */       return RSAKey.parse(jsonObject);
/*      */     }
/*  824 */     if (kty == KeyType.OCT)
/*      */     {
/*  826 */       return OctetSequenceKey.parse(jsonObject);
/*      */     }
/*  828 */     if (kty == KeyType.OKP)
/*      */     {
/*  830 */       return OctetKeyPair.parse(jsonObject);
/*      */     }
/*      */ 
/*      */     
/*  834 */     throw new ParseException("Unsupported key type \"kty\" parameter: " + kty, 0);
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
/*      */   public static JWK parse(X509Certificate cert) throws JOSEException {
/*  866 */     if (cert.getPublicKey() instanceof RSAPublicKey)
/*  867 */       return RSAKey.parse(cert); 
/*  868 */     if (cert.getPublicKey() instanceof ECPublicKey) {
/*  869 */       return ECKey.parse(cert);
/*      */     }
/*  871 */     throw new JOSEException("Unsupported public key algorithm: " + cert.getPublicKey().getAlgorithm());
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
/*      */   public static JWK parseFromPEMEncodedX509Cert(String pemEncodedCert) throws JOSEException {
/*  904 */     X509Certificate cert = X509CertUtils.parse(pemEncodedCert);
/*      */     
/*  906 */     if (cert == null) {
/*  907 */       throw new JOSEException("Couldn't parse PEM-encoded X.509 certificate");
/*      */     }
/*      */     
/*  910 */     return parse(cert);
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
/*      */   public static JWK load(KeyStore keyStore, String alias, char[] pin) throws KeyStoreException, JOSEException {
/*  937 */     Certificate cert = keyStore.getCertificate(alias);
/*      */     
/*  939 */     if (cert == null)
/*      */     {
/*  941 */       return OctetSequenceKey.load(keyStore, alias, pin);
/*      */     }
/*      */     
/*  944 */     if (cert.getPublicKey() instanceof RSAPublicKey)
/*  945 */       return RSAKey.load(keyStore, alias, pin); 
/*  946 */     if (cert.getPublicKey() instanceof ECPublicKey) {
/*  947 */       return ECKey.load(keyStore, alias, pin);
/*      */     }
/*  949 */     throw new JOSEException("Unsupported public key algorithm: " + cert.getPublicKey().getAlgorithm());
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
/*      */   public static JWK parseFromPEMEncodedObjects(String pemEncodedObjects) throws JOSEException {
/*  977 */     List<KeyPair> keys = PEMEncodedKeyParser.parseKeys(pemEncodedObjects);
/*  978 */     if (keys.isEmpty()) {
/*  979 */       throw new JOSEException("No PEM-encoded keys found");
/*      */     }
/*      */     
/*  982 */     KeyPair pair = mergeKeyPairs(keys);
/*      */     
/*  984 */     PublicKey publicKey = pair.getPublic();
/*  985 */     PrivateKey privateKey = pair.getPrivate();
/*      */     
/*  987 */     if (publicKey == null)
/*      */     {
/*  989 */       throw new JOSEException("Missing PEM-encoded public key to construct JWK");
/*      */     }
/*      */     
/*  992 */     if (publicKey instanceof ECPublicKey) {
/*  993 */       ECPublicKey ecPubKey = (ECPublicKey)publicKey;
/*  994 */       ECParameterSpec pubParams = ecPubKey.getParams();
/*      */       
/*  996 */       if (privateKey instanceof ECPrivateKey) {
/*  997 */         validateEcCurves(ecPubKey, (ECPrivateKey)privateKey);
/*      */       }
/*  999 */       if (privateKey != null && !(privateKey instanceof ECPrivateKey)) {
/* 1000 */         throw new JOSEException("Unsupported " + KeyType.EC.getValue() + " private key type: " + privateKey);
/*      */       }
/*      */       
/* 1003 */       Curve curve = Curve.forECParameterSpec(pubParams);
/* 1004 */       ECKey.Builder builder = new ECKey.Builder(curve, (ECPublicKey)publicKey);
/*      */       
/* 1006 */       if (privateKey != null) {
/* 1007 */         builder.privateKey((ECPrivateKey)privateKey);
/*      */       }
/* 1009 */       return builder.build();
/*      */     } 
/*      */     
/* 1012 */     if (publicKey instanceof RSAPublicKey) {
/* 1013 */       RSAKey.Builder builder = new RSAKey.Builder((RSAPublicKey)publicKey);
/* 1014 */       if (privateKey instanceof RSAPrivateKey) {
/* 1015 */         builder.privateKey((RSAPrivateKey)privateKey);
/* 1016 */       } else if (privateKey != null) {
/* 1017 */         throw new JOSEException("Unsupported " + KeyType.RSA.getValue() + " private key type: " + privateKey);
/*      */       } 
/* 1019 */       return builder.build();
/*      */     } 
/*      */     
/* 1022 */     throw new JOSEException("Unsupported algorithm of PEM-encoded key: " + publicKey.getAlgorithm());
/*      */   }
/*      */ 
/*      */   
/*      */   private static void validateEcCurves(ECPublicKey publicKey, ECPrivateKey privateKey) throws JOSEException {
/* 1027 */     ECParameterSpec pubParams = publicKey.getParams();
/* 1028 */     ECParameterSpec privParams = privateKey.getParams();
/* 1029 */     if (!pubParams.getCurve().equals(privParams.getCurve())) {
/* 1030 */       throw new JOSEException("Public/private " + KeyType.EC.getValue() + " key curve mismatch: " + publicKey);
/*      */     }
/* 1032 */     if (pubParams.getCofactor() != privParams.getCofactor()) {
/* 1033 */       throw new JOSEException("Public/private " + KeyType.EC.getValue() + " key cofactor mismatch: " + publicKey);
/*      */     }
/* 1035 */     if (!pubParams.getGenerator().equals(privParams.getGenerator())) {
/* 1036 */       throw new JOSEException("Public/private " + KeyType.EC.getValue() + " key generator mismatch: " + publicKey);
/*      */     }
/* 1038 */     if (!pubParams.getOrder().equals(privParams.getOrder())) {
/* 1039 */       throw new JOSEException("Public/private " + KeyType.EC.getValue() + " key order mismatch: " + publicKey);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static KeyPair mergeKeyPairs(List<KeyPair> keys) throws JOSEException {
/*      */     KeyPair pair;
/* 1046 */     if (keys.size() == 1) {
/*      */ 
/*      */       
/* 1049 */       pair = keys.get(0);
/* 1050 */     } else if (keys.size() == 2) {
/*      */       
/* 1052 */       pair = twoKeysToKeyPair(keys);
/*      */     } else {
/* 1054 */       throw new JOSEException("Expected key or pair of PEM-encoded keys");
/*      */     } 
/* 1056 */     return pair;
/*      */   }
/*      */ 
/*      */   
/*      */   private static KeyPair twoKeysToKeyPair(List<? extends KeyPair> keys) throws JOSEException {
/* 1061 */     KeyPair key1 = keys.get(0);
/* 1062 */     KeyPair key2 = keys.get(1);
/* 1063 */     if (key1.getPublic() != null && key2.getPrivate() != null)
/* 1064 */       return new KeyPair(key1.getPublic(), key2.getPrivate()); 
/* 1065 */     if (key1.getPrivate() != null && key2.getPublic() != null) {
/* 1066 */       return new KeyPair(key2.getPublic(), key1.getPrivate());
/*      */     }
/* 1068 */     throw new JOSEException("Not a public/private key pair");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object o) {
/* 1075 */     if (this == o) return true; 
/* 1076 */     if (!(o instanceof JWK)) return false; 
/* 1077 */     JWK jwk = (JWK)o;
/* 1078 */     return (Objects.equals(this.kty, jwk.kty) && 
/* 1079 */       Objects.equals(this.use, jwk.use) && 
/* 1080 */       Objects.equals(this.ops, jwk.ops) && 
/* 1081 */       Objects.equals(this.alg, jwk.alg) && 
/* 1082 */       Objects.equals(this.kid, jwk.kid) && 
/* 1083 */       Objects.equals(this.x5u, jwk.x5u) && 
/* 1084 */       Objects.equals(this.x5t, jwk.x5t) && 
/* 1085 */       Objects.equals(this.x5t256, jwk.x5t256) && 
/* 1086 */       Objects.equals(this.x5c, jwk.x5c) && 
/* 1087 */       Objects.equals(this.exp, jwk.exp) && 
/* 1088 */       Objects.equals(this.nbf, jwk.nbf) && 
/* 1089 */       Objects.equals(this.iat, jwk.iat) && 
/* 1090 */       Objects.equals(this.revocation, jwk.revocation) && 
/* 1091 */       Objects.equals(this.keyStore, jwk.keyStore));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1097 */     return Objects.hash(new Object[] { this.kty, this.use, this.ops, this.alg, this.kid, this.x5u, this.x5t, this.x5t256, this.x5c, this.exp, this.nbf, this.iat, this.revocation, this.keyStore });
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\jwk\JWK.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */