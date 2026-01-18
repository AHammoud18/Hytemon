/*      */ package com.nimbusds.jose;
/*      */ 
/*      */ import com.nimbusds.jose.jwk.JWK;
/*      */ import com.nimbusds.jose.shaded.jcip.Immutable;
/*      */ import com.nimbusds.jose.util.Base64;
/*      */ import com.nimbusds.jose.util.Base64URL;
/*      */ import com.nimbusds.jose.util.JSONObjectUtils;
/*      */ import com.nimbusds.jose.util.X509CertChainUtils;
/*      */ import java.net.URI;
/*      */ import java.text.ParseException;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
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
/*      */ @Immutable
/*      */ public final class JWEHeader
/*      */   extends CommonSEHeader
/*      */ {
/*      */   private static final long serialVersionUID = 1L;
/*      */   private static final Set<String> REGISTERED_PARAMETER_NAMES;
/*      */   private final EncryptionMethod enc;
/*      */   private final JWK epk;
/*      */   private final CompressionAlgorithm zip;
/*      */   private final Base64URL apu;
/*      */   private final Base64URL apv;
/*      */   private final Base64URL p2s;
/*      */   private final int p2c;
/*      */   private final Base64URL iv;
/*      */   private final Base64URL tag;
/*      */   private final String skid;
/*      */   private final String iss;
/*      */   private final String sub;
/*      */   private final List<String> aud;
/*      */   
/*      */   static {
/*   95 */     Set<String> p = new HashSet<>();
/*      */     
/*   97 */     p.add("alg");
/*   98 */     p.add("enc");
/*   99 */     p.add("epk");
/*  100 */     p.add("zip");
/*  101 */     p.add("jku");
/*  102 */     p.add("jwk");
/*  103 */     p.add("x5u");
/*  104 */     p.add("x5t");
/*  105 */     p.add("x5t#S256");
/*  106 */     p.add("x5c");
/*  107 */     p.add("kid");
/*  108 */     p.add("typ");
/*  109 */     p.add("cty");
/*  110 */     p.add("crit");
/*  111 */     p.add("apu");
/*  112 */     p.add("apv");
/*  113 */     p.add("p2s");
/*  114 */     p.add("p2c");
/*  115 */     p.add("iv");
/*  116 */     p.add("tag");
/*  117 */     p.add("skid");
/*  118 */     p.add("iss");
/*  119 */     p.add("sub");
/*  120 */     p.add("aud");
/*  121 */     p.add("authTag");
/*      */     
/*  123 */     REGISTERED_PARAMETER_NAMES = Collections.unmodifiableSet(p);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class Builder
/*      */   {
/*      */     private final EncryptionMethod enc;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private JWEAlgorithm alg;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private JOSEObjectType typ;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String cty;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Set<String> crit;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private URI jku;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private JWK jwk;
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
/*      */     private String kid;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private JWK epk;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private CompressionAlgorithm zip;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Base64URL apu;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Base64URL apv;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Base64URL p2s;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int p2c;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Base64URL iv;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Base64URL tag;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String skid;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String iss;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String sub;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private List<String> aud;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Map<String, Object> customParams;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Base64URL parsedBase64URL;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder(JWEAlgorithm alg, EncryptionMethod enc) {
/*  309 */       if (alg.getName().equals(Algorithm.NONE.getName())) {
/*  310 */         throw new IllegalArgumentException("The JWE algorithm \"alg\" cannot be \"none\"");
/*      */       }
/*  312 */       this.alg = alg;
/*      */       
/*  314 */       this.enc = Objects.<EncryptionMethod>requireNonNull(enc);
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
/*      */     public Builder(EncryptionMethod enc) {
/*  326 */       this.enc = Objects.<EncryptionMethod>requireNonNull(enc);
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
/*      */     public Builder(JWEHeader jweHeader) {
/*  339 */       this(jweHeader.getEncryptionMethod());
/*      */       
/*  341 */       this.alg = jweHeader.getAlgorithm();
/*  342 */       this.typ = jweHeader.getType();
/*  343 */       this.cty = jweHeader.getContentType();
/*  344 */       this.crit = jweHeader.getCriticalParams();
/*  345 */       this.customParams = jweHeader.getCustomParams();
/*      */       
/*  347 */       this.jku = jweHeader.getJWKURL();
/*  348 */       this.jwk = jweHeader.getJWK();
/*  349 */       this.x5u = jweHeader.getX509CertURL();
/*  350 */       this.x5t = jweHeader.getX509CertThumbprint();
/*  351 */       this.x5t256 = jweHeader.getX509CertSHA256Thumbprint();
/*  352 */       this.x5c = jweHeader.getX509CertChain();
/*  353 */       this.kid = jweHeader.getKeyID();
/*      */       
/*  355 */       this.epk = jweHeader.getEphemeralPublicKey();
/*  356 */       this.zip = jweHeader.getCompressionAlgorithm();
/*  357 */       this.apu = jweHeader.getAgreementPartyUInfo();
/*  358 */       this.apv = jweHeader.getAgreementPartyVInfo();
/*  359 */       this.p2s = jweHeader.getPBES2Salt();
/*  360 */       this.p2c = jweHeader.getPBES2Count();
/*  361 */       this.iv = jweHeader.getIV();
/*  362 */       this.tag = jweHeader.getAuthTag();
/*      */       
/*  364 */       this.skid = jweHeader.getSenderKeyID();
/*      */       
/*  366 */       this.iss = jweHeader.getIssuer();
/*  367 */       this.sub = jweHeader.getSubject();
/*  368 */       this.aud = jweHeader.getAudience();
/*      */       
/*  370 */       this.customParams = jweHeader.getCustomParams();
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
/*      */     public Builder alg(JWEAlgorithm alg) {
/*  384 */       this.alg = alg;
/*  385 */       return this;
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
/*      */     public Builder type(JOSEObjectType typ) {
/*  399 */       this.typ = typ;
/*  400 */       return this;
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
/*      */     public Builder contentType(String cty) {
/*  414 */       this.cty = cty;
/*  415 */       return this;
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
/*      */     public Builder criticalParams(Set<String> crit) {
/*  430 */       this.crit = crit;
/*  431 */       return this;
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
/*      */     public Builder jwkURL(URI jku) {
/*  446 */       this.jku = jku;
/*  447 */       return this;
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
/*      */     public Builder jwk(JWK jwk) {
/*  461 */       if (jwk != null && jwk.isPrivate()) {
/*  462 */         throw new IllegalArgumentException("The JWK must be public");
/*      */       }
/*      */       
/*  465 */       this.jwk = jwk;
/*  466 */       return this;
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
/*  480 */       this.x5u = x5u;
/*  481 */       return this;
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
/*  497 */       this.x5t = x5t;
/*  498 */       return this;
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
/*  513 */       this.x5t256 = x5t256;
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
/*      */     public Builder x509CertChain(List<Base64> x5c) {
/*  529 */       this.x5c = x5c;
/*  530 */       return this;
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
/*      */     public Builder keyID(String kid) {
/*  544 */       this.kid = kid;
/*  545 */       return this;
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
/*      */     public Builder ephemeralPublicKey(JWK epk) {
/*  559 */       this.epk = epk;
/*  560 */       return this;
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
/*      */     public Builder compressionAlgorithm(CompressionAlgorithm zip) {
/*  574 */       this.zip = zip;
/*  575 */       return this;
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
/*      */     public Builder agreementPartyUInfo(Base64URL apu) {
/*  589 */       this.apu = apu;
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
/*      */     public Builder agreementPartyVInfo(Base64URL apv) {
/*  604 */       this.apv = apv;
/*  605 */       return this;
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
/*      */     public Builder pbes2Salt(Base64URL p2s) {
/*  619 */       this.p2s = p2s;
/*  620 */       return this;
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
/*      */     public Builder pbes2Count(int p2c) {
/*  634 */       if (p2c < 0) {
/*  635 */         throw new IllegalArgumentException("The PBES2 count parameter must not be negative");
/*      */       }
/*  637 */       this.p2c = p2c;
/*  638 */       return this;
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
/*      */     public Builder iv(Base64URL iv) {
/*  652 */       this.iv = iv;
/*  653 */       return this;
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
/*      */     public Builder senderKeyID(String skid) {
/*  667 */       this.skid = skid;
/*  668 */       return this;
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
/*      */     public Builder issuer(String iss) {
/*  682 */       this.iss = iss;
/*  683 */       return this;
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
/*      */     public Builder subject(String sub) {
/*  697 */       this.sub = sub;
/*  698 */       return this;
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
/*      */     public Builder audience(List<String> aud) {
/*  712 */       this.aud = aud;
/*  713 */       return this;
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
/*      */     public Builder authTag(Base64URL tag) {
/*  727 */       this.tag = tag;
/*  728 */       return this;
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
/*      */     public Builder customParam(String name, Object value) {
/*  750 */       if (JWEHeader.getRegisteredParameterNames().contains(name)) {
/*  751 */         throw new IllegalArgumentException("The parameter name \"" + name + "\" matches a registered name");
/*      */       }
/*      */       
/*  754 */       if (this.customParams == null) {
/*  755 */         this.customParams = new HashMap<>();
/*      */       }
/*      */       
/*  758 */       this.customParams.put(name, value);
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
/*      */     public Builder customParams(Map<String, Object> customParameters) {
/*  775 */       this.customParams = customParameters;
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
/*      */     public Builder parsedBase64URL(Base64URL base64URL) {
/*  790 */       this.parsedBase64URL = base64URL;
/*  791 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public JWEHeader build() {
/*  802 */       return new JWEHeader(this.alg, this.enc, this.typ, this.cty, this.crit, this.jku, this.jwk, this.x5u, this.x5t, this.x5t256, this.x5c, this.kid, this.epk, this.zip, this.apu, this.apv, this.p2s, this.p2c, this.iv, this.tag, this.skid, this.iss, this.sub, this.aud, this.customParams, this.parsedBase64URL);
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
/*      */   public JWEHeader(EncryptionMethod enc) {
/*  899 */     this((Algorithm)null, enc, (JOSEObjectType)null, (String)null, (Set<String>)null, (URI)null, (JWK)null, (URI)null, (Base64URL)null, (Base64URL)null, (List<Base64>)null, (String)null, (JWK)null, (CompressionAlgorithm)null, (Base64URL)null, (Base64URL)null, (Base64URL)null, 0, (Base64URL)null, (Base64URL)null, (String)null, (String)null, (String)null, (List<String>)null, (Map<String, Object>)null, (Base64URL)null);
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
/*      */   public JWEHeader(JWEAlgorithm alg, EncryptionMethod enc) {
/*  922 */     this(alg, enc, (JOSEObjectType)null, (String)null, (Set<String>)null, (URI)null, (JWK)null, (URI)null, (Base64URL)null, (Base64URL)null, (List<Base64>)null, (String)null, (JWK)null, (CompressionAlgorithm)null, (Base64URL)null, (Base64URL)null, (Base64URL)null, 0, (Base64URL)null, (Base64URL)null, (String)null, (String)null, (String)null, (List<String>)null, (Map<String, Object>)null, (Base64URL)null);
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
/*      */   @Deprecated
/*      */   public JWEHeader(Algorithm alg, EncryptionMethod enc, JOSEObjectType typ, String cty, Set<String> crit, URI jku, JWK jwk, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c, String kid, JWK epk, CompressionAlgorithm zip, Base64URL apu, Base64URL apv, Base64URL p2s, int p2c, Base64URL iv, Base64URL tag, String skid, Map<String, Object> customParams, Base64URL parsedBase64URL) {
/* 1013 */     this(alg, enc, typ, cty, crit, jku, jwk, x5u, x5t, x5t256, x5c, kid, epk, zip, apu, apv, p2s, p2c, iv, tag, skid, (String)null, (String)null, (List<String>)null, customParams, parsedBase64URL);
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
/*      */   public JWEHeader(Algorithm alg, EncryptionMethod enc, JOSEObjectType typ, String cty, Set<String> crit, URI jku, JWK jwk, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c, String kid, JWK epk, CompressionAlgorithm zip, Base64URL apu, Base64URL apv, Base64URL p2s, int p2c, Base64URL iv, Base64URL tag, String skid, String iss, String sub, List<String> aud, Map<String, Object> customParams, Base64URL parsedBase64URL) {
/* 1112 */     super(alg, typ, cty, crit, jku, jwk, x5u, x5t, x5t256, x5c, kid, customParams, parsedBase64URL);
/*      */     
/* 1114 */     if (alg != null && alg.getName().equals(Algorithm.NONE.getName())) {
/* 1115 */       throw new IllegalArgumentException("The JWE algorithm cannot be \"none\"");
/*      */     }
/*      */     
/* 1118 */     if (epk != null && epk.isPrivate()) {
/* 1119 */       throw new IllegalArgumentException("Ephemeral public key should not be a private key");
/*      */     }
/*      */     
/* 1122 */     this.enc = Objects.<EncryptionMethod>requireNonNull(enc);
/*      */     
/* 1124 */     this.epk = epk;
/* 1125 */     this.zip = zip;
/* 1126 */     this.apu = apu;
/* 1127 */     this.apv = apv;
/* 1128 */     this.p2s = p2s;
/* 1129 */     this.p2c = p2c;
/* 1130 */     this.iv = iv;
/* 1131 */     this.tag = tag;
/* 1132 */     this.skid = skid;
/*      */     
/* 1134 */     this.iss = iss;
/* 1135 */     this.sub = sub;
/* 1136 */     this.aud = aud;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JWEHeader(JWEHeader jweHeader) {
/* 1147 */     this(jweHeader
/* 1148 */         .getAlgorithm(), jweHeader
/* 1149 */         .getEncryptionMethod(), jweHeader
/* 1150 */         .getType(), jweHeader
/* 1151 */         .getContentType(), jweHeader
/* 1152 */         .getCriticalParams(), jweHeader
/* 1153 */         .getJWKURL(), jweHeader
/* 1154 */         .getJWK(), jweHeader
/* 1155 */         .getX509CertURL(), jweHeader
/* 1156 */         .getX509CertThumbprint(), jweHeader
/* 1157 */         .getX509CertSHA256Thumbprint(), jweHeader
/* 1158 */         .getX509CertChain(), jweHeader
/* 1159 */         .getKeyID(), jweHeader
/* 1160 */         .getEphemeralPublicKey(), jweHeader
/* 1161 */         .getCompressionAlgorithm(), jweHeader
/* 1162 */         .getAgreementPartyUInfo(), jweHeader
/* 1163 */         .getAgreementPartyVInfo(), jweHeader
/* 1164 */         .getPBES2Salt(), jweHeader
/* 1165 */         .getPBES2Count(), jweHeader
/* 1166 */         .getIV(), jweHeader
/* 1167 */         .getAuthTag(), jweHeader
/* 1168 */         .getSenderKeyID(), jweHeader
/* 1169 */         .getIssuer(), jweHeader
/* 1170 */         .getSubject(), jweHeader
/* 1171 */         .getAudience(), jweHeader
/* 1172 */         .getCustomParams(), jweHeader
/* 1173 */         .getParsedBase64URL());
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
/*      */   public static Set<String> getRegisteredParameterNames() {
/* 1185 */     return REGISTERED_PARAMETER_NAMES;
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
/*      */   public JWEAlgorithm getAlgorithm() {
/* 1197 */     return (JWEAlgorithm)super.getAlgorithm();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EncryptionMethod getEncryptionMethod() {
/* 1208 */     return this.enc;
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
/*      */   public JWK getEphemeralPublicKey() {
/* 1220 */     return this.epk;
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
/*      */   public CompressionAlgorithm getCompressionAlgorithm() {
/* 1232 */     return this.zip;
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
/*      */   public Base64URL getAgreementPartyUInfo() {
/* 1244 */     return this.apu;
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
/*      */   public Base64URL getAgreementPartyVInfo() {
/* 1256 */     return this.apv;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Base64URL getPBES2Salt() {
/* 1267 */     return this.p2s;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getPBES2Count() {
/* 1278 */     return this.p2c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Base64URL getIV() {
/* 1289 */     return this.iv;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Base64URL getAuthTag() {
/* 1300 */     return this.tag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getSenderKeyID() {
/* 1311 */     return this.skid;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getIssuer() {
/* 1322 */     return this.iss;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getSubject() {
/* 1333 */     return this.sub;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<String> getAudience() {
/* 1344 */     if (this.aud == null) {
/* 1345 */       return Collections.emptyList();
/*      */     }
/*      */     
/* 1348 */     return this.aud;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<String> getIncludedParams() {
/* 1355 */     Set<String> includedParameters = super.getIncludedParams();
/*      */     
/* 1357 */     if (this.enc != null) {
/* 1358 */       includedParameters.add("enc");
/*      */     }
/*      */     
/* 1361 */     if (this.epk != null) {
/* 1362 */       includedParameters.add("epk");
/*      */     }
/*      */     
/* 1365 */     if (this.zip != null) {
/* 1366 */       includedParameters.add("zip");
/*      */     }
/*      */     
/* 1369 */     if (this.apu != null) {
/* 1370 */       includedParameters.add("apu");
/*      */     }
/*      */     
/* 1373 */     if (this.apv != null) {
/* 1374 */       includedParameters.add("apv");
/*      */     }
/*      */     
/* 1377 */     if (this.p2s != null) {
/* 1378 */       includedParameters.add("p2s");
/*      */     }
/*      */     
/* 1381 */     if (this.p2c > 0) {
/* 1382 */       includedParameters.add("p2c");
/*      */     }
/*      */     
/* 1385 */     if (this.iv != null) {
/* 1386 */       includedParameters.add("iv");
/*      */     }
/*      */     
/* 1389 */     if (this.tag != null) {
/* 1390 */       includedParameters.add("tag");
/*      */     }
/*      */     
/* 1393 */     if (this.skid != null) {
/* 1394 */       includedParameters.add("skid");
/*      */     }
/*      */     
/* 1397 */     if (this.iss != null) {
/* 1398 */       includedParameters.add("iss");
/*      */     }
/*      */     
/* 1401 */     if (this.sub != null) {
/* 1402 */       includedParameters.add("sub");
/*      */     }
/*      */     
/* 1405 */     if (this.aud != null) {
/* 1406 */       includedParameters.add("aud");
/*      */     }
/*      */     
/* 1409 */     return includedParameters;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<String, Object> toJSONObject() {
/* 1416 */     Map<String, Object> o = super.toJSONObject();
/*      */     
/* 1418 */     if (this.enc != null) {
/* 1419 */       o.put("enc", this.enc.toString());
/*      */     }
/*      */     
/* 1422 */     if (this.epk != null) {
/* 1423 */       o.put("epk", this.epk.toJSONObject());
/*      */     }
/*      */     
/* 1426 */     if (this.zip != null) {
/* 1427 */       o.put("zip", this.zip.toString());
/*      */     }
/*      */     
/* 1430 */     if (this.apu != null) {
/* 1431 */       o.put("apu", this.apu.toString());
/*      */     }
/*      */     
/* 1434 */     if (this.apv != null) {
/* 1435 */       o.put("apv", this.apv.toString());
/*      */     }
/*      */     
/* 1438 */     if (this.p2s != null) {
/* 1439 */       o.put("p2s", this.p2s.toString());
/*      */     }
/*      */     
/* 1442 */     if (this.p2c > 0) {
/* 1443 */       o.put("p2c", Integer.valueOf(this.p2c));
/*      */     }
/*      */     
/* 1446 */     if (this.iv != null) {
/* 1447 */       o.put("iv", this.iv.toString());
/*      */     }
/*      */     
/* 1450 */     if (this.tag != null) {
/* 1451 */       o.put("tag", this.tag.toString());
/*      */     }
/*      */     
/* 1454 */     if (this.skid != null) {
/* 1455 */       o.put("skid", this.skid);
/*      */     }
/*      */     
/* 1458 */     if (this.iss != null) {
/* 1459 */       o.put("iss", this.iss);
/*      */     }
/*      */     
/* 1462 */     if (this.sub != null) {
/* 1463 */       o.put("sub", this.sub);
/*      */     }
/*      */     
/* 1466 */     if (this.aud != null) {
/* 1467 */       if (this.aud.size() == 1) {
/* 1468 */         o.put("aud", this.aud.get(0));
/* 1469 */       } else if (!this.aud.isEmpty()) {
/* 1470 */         o.put("aud", this.aud);
/*      */       } 
/*      */     }
/*      */     
/* 1474 */     return o;
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
/*      */   private static EncryptionMethod parseEncryptionMethod(Map<String, Object> json) throws ParseException {
/* 1492 */     return EncryptionMethod.parse(JSONObjectUtils.getString(json, "enc"));
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
/*      */   public static JWEHeader parse(Map<String, Object> jsonObject) throws ParseException {
/* 1510 */     return parse(jsonObject, (Base64URL)null);
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
/*      */   public static JWEHeader parse(Map<String, Object> jsonObject, Base64URL parsedBase64URL) throws ParseException {
/* 1532 */     EncryptionMethod enc = parseEncryptionMethod(jsonObject);
/*      */     
/* 1534 */     Builder header = (new Builder(enc)).parsedBase64URL(parsedBase64URL);
/*      */ 
/*      */     
/* 1537 */     for (String name : jsonObject.keySet()) {
/*      */       
/* 1539 */       if ("alg".equals(name)) {
/* 1540 */         header = header.alg(JWEAlgorithm.parse(JSONObjectUtils.getString(jsonObject, name))); continue;
/* 1541 */       }  if ("enc".equals(name))
/*      */         continue; 
/* 1543 */       if ("typ".equals(name)) {
/* 1544 */         String typValue = JSONObjectUtils.getString(jsonObject, name);
/* 1545 */         if (typValue != null)
/* 1546 */           header = header.type(new JOSEObjectType(typValue));  continue;
/*      */       } 
/* 1548 */       if ("cty".equals(name)) {
/* 1549 */         header = header.contentType(JSONObjectUtils.getString(jsonObject, name)); continue;
/* 1550 */       }  if ("crit".equals(name)) {
/* 1551 */         List<String> critValues = JSONObjectUtils.getStringList(jsonObject, name);
/* 1552 */         if (critValues != null)
/* 1553 */           header = header.criticalParams(new HashSet<>(critValues));  continue;
/*      */       } 
/* 1555 */       if ("jku".equals(name)) {
/* 1556 */         header = header.jwkURL(JSONObjectUtils.getURI(jsonObject, name)); continue;
/* 1557 */       }  if ("jwk".equals(name)) {
/* 1558 */         header = header.jwk(CommonSEHeader.parsePublicJWK(JSONObjectUtils.getJSONObject(jsonObject, name))); continue;
/* 1559 */       }  if ("x5u".equals(name)) {
/* 1560 */         header = header.x509CertURL(JSONObjectUtils.getURI(jsonObject, name)); continue;
/* 1561 */       }  if ("x5t".equals(name)) {
/* 1562 */         header = header.x509CertThumbprint(Base64URL.from(JSONObjectUtils.getString(jsonObject, name))); continue;
/* 1563 */       }  if ("x5t#S256".equals(name)) {
/* 1564 */         header = header.x509CertSHA256Thumbprint(Base64URL.from(JSONObjectUtils.getString(jsonObject, name))); continue;
/* 1565 */       }  if ("x5c".equals(name)) {
/* 1566 */         header = header.x509CertChain(X509CertChainUtils.toBase64List(JSONObjectUtils.getJSONArray(jsonObject, name))); continue;
/* 1567 */       }  if ("kid".equals(name)) {
/* 1568 */         header = header.keyID(JSONObjectUtils.getString(jsonObject, name)); continue;
/* 1569 */       }  if ("epk".equals(name)) {
/* 1570 */         header = header.ephemeralPublicKey(JWK.parse(JSONObjectUtils.getJSONObject(jsonObject, name))); continue;
/* 1571 */       }  if ("zip".equals(name)) {
/* 1572 */         String zipValue = JSONObjectUtils.getString(jsonObject, name);
/* 1573 */         if (zipValue != null)
/* 1574 */           header = header.compressionAlgorithm(new CompressionAlgorithm(zipValue));  continue;
/*      */       } 
/* 1576 */       if ("apu".equals(name)) {
/* 1577 */         header = header.agreementPartyUInfo(Base64URL.from(JSONObjectUtils.getString(jsonObject, name))); continue;
/* 1578 */       }  if ("apv".equals(name)) {
/* 1579 */         header = header.agreementPartyVInfo(Base64URL.from(JSONObjectUtils.getString(jsonObject, name))); continue;
/* 1580 */       }  if ("p2s".equals(name)) {
/* 1581 */         header = header.pbes2Salt(Base64URL.from(JSONObjectUtils.getString(jsonObject, name))); continue;
/* 1582 */       }  if ("p2c".equals(name)) {
/* 1583 */         header = header.pbes2Count(JSONObjectUtils.getInt(jsonObject, name)); continue;
/* 1584 */       }  if ("iv".equals(name)) {
/* 1585 */         header = header.iv(Base64URL.from(JSONObjectUtils.getString(jsonObject, name))); continue;
/* 1586 */       }  if ("tag".equals(name)) {
/* 1587 */         header = header.authTag(Base64URL.from(JSONObjectUtils.getString(jsonObject, name))); continue;
/* 1588 */       }  if ("skid".equals(name)) {
/* 1589 */         header = header.senderKeyID(JSONObjectUtils.getString(jsonObject, name)); continue;
/* 1590 */       }  if ("iss".equals(name)) {
/* 1591 */         header = header.issuer(JSONObjectUtils.getString(jsonObject, name)); continue;
/* 1592 */       }  if ("sub".equals(name)) {
/* 1593 */         header = header.subject(JSONObjectUtils.getString(jsonObject, name)); continue;
/* 1594 */       }  if ("aud".equals(name)) {
/* 1595 */         if (jsonObject.get(name) instanceof String) {
/* 1596 */           header = header.audience(Collections.singletonList(JSONObjectUtils.getString(jsonObject, name))); continue;
/*      */         } 
/* 1598 */         header = header.audience(JSONObjectUtils.getStringList(jsonObject, name));
/*      */         continue;
/*      */       } 
/* 1601 */       header = header.customParam(name, jsonObject.get(name));
/*      */     } 
/*      */ 
/*      */     
/* 1605 */     return header.build();
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
/*      */   public static JWEHeader parse(String jsonString) throws ParseException {
/* 1622 */     return parse(JSONObjectUtils.parse(jsonString), (Base64URL)null);
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
/*      */   public static JWEHeader parse(String jsonString, Base64URL parsedBase64URL) throws ParseException {
/* 1643 */     return parse(JSONObjectUtils.parse(jsonString, 20000), parsedBase64URL);
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
/*      */   public static JWEHeader parse(Base64URL base64URL) throws ParseException {
/* 1660 */     return parse(base64URL.decodeToString(), base64URL);
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\JWEHeader.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */