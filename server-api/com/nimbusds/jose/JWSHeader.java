/*     */ package com.nimbusds.jose;
/*     */ 
/*     */ import com.nimbusds.jose.jwk.JWK;
/*     */ import com.nimbusds.jose.shaded.jcip.Immutable;
/*     */ import com.nimbusds.jose.util.Base64;
/*     */ import com.nimbusds.jose.util.Base64URL;
/*     */ import com.nimbusds.jose.util.JSONObjectUtils;
/*     */ import com.nimbusds.jose.util.X509CertChainUtils;
/*     */ import java.net.URI;
/*     */ import java.text.ParseException;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public final class JWSHeader
/*     */   extends CommonSEHeader
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private static final Set<String> REGISTERED_PARAMETER_NAMES;
/*     */   private final boolean b64;
/*     */   
/*     */   static {
/*  83 */     Set<String> p = new HashSet<>();
/*     */     
/*  85 */     p.add("alg");
/*  86 */     p.add("jku");
/*  87 */     p.add("jwk");
/*  88 */     p.add("x5u");
/*  89 */     p.add("x5t");
/*  90 */     p.add("x5t#S256");
/*  91 */     p.add("x5c");
/*  92 */     p.add("kid");
/*  93 */     p.add("typ");
/*  94 */     p.add("cty");
/*  95 */     p.add("crit");
/*  96 */     p.add("b64");
/*     */     
/*  98 */     REGISTERED_PARAMETER_NAMES = Collections.unmodifiableSet(p);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Builder
/*     */   {
/*     */     private final JWSAlgorithm alg;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private JOSEObjectType typ;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private String cty;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Set<String> crit;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private URI jku;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private JWK jwk;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private URI x5u;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     private Base64URL x5t;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Base64URL x5t256;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private List<Base64> x5c;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private String kid;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean b64 = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Map<String, Object> customParams;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Base64URL parsedBase64URL;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder(JWSAlgorithm alg) {
/* 212 */       if (alg.getName().equals(Algorithm.NONE.getName())) {
/* 213 */         throw new IllegalArgumentException("The JWS algorithm \"alg\" cannot be \"none\"");
/*     */       }
/*     */       
/* 216 */       this.alg = alg;
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
/*     */     public Builder(JWSHeader jwsHeader) {
/* 229 */       this(jwsHeader.getAlgorithm());
/*     */       
/* 231 */       this.typ = jwsHeader.getType();
/* 232 */       this.cty = jwsHeader.getContentType();
/* 233 */       this.crit = jwsHeader.getCriticalParams();
/*     */       
/* 235 */       this.jku = jwsHeader.getJWKURL();
/* 236 */       this.jwk = jwsHeader.getJWK();
/* 237 */       this.x5u = jwsHeader.getX509CertURL();
/* 238 */       this.x5t = jwsHeader.getX509CertThumbprint();
/* 239 */       this.x5t256 = jwsHeader.getX509CertSHA256Thumbprint();
/* 240 */       this.x5c = jwsHeader.getX509CertChain();
/* 241 */       this.kid = jwsHeader.getKeyID();
/* 242 */       this.b64 = jwsHeader.isBase64URLEncodePayload();
/* 243 */       this.customParams = jwsHeader.getCustomParams();
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
/*     */     public Builder type(JOSEObjectType typ) {
/* 257 */       this.typ = typ;
/* 258 */       return this;
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
/*     */     public Builder contentType(String cty) {
/* 272 */       this.cty = cty;
/* 273 */       return this;
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
/*     */     public Builder criticalParams(Set<String> crit) {
/* 288 */       this.crit = crit;
/* 289 */       return this;
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
/*     */     public Builder jwkURL(URI jku) {
/* 304 */       this.jku = jku;
/* 305 */       return this;
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
/*     */     public Builder jwk(JWK jwk) {
/* 319 */       if (jwk != null && jwk.isPrivate()) {
/* 320 */         throw new IllegalArgumentException("The JWK must be public");
/*     */       }
/*     */       
/* 323 */       this.jwk = jwk;
/* 324 */       return this;
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
/* 338 */       this.x5u = x5u;
/* 339 */       return this;
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
/* 355 */       this.x5t = x5t;
/* 356 */       return this;
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
/* 371 */       this.x5t256 = x5t256;
/* 372 */       return this;
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
/*     */     public Builder x509CertChain(List<Base64> x5c) {
/* 387 */       this.x5c = x5c;
/* 388 */       return this;
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
/*     */     public Builder keyID(String kid) {
/* 402 */       this.kid = kid;
/* 403 */       return this;
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
/*     */     public Builder base64URLEncodePayload(boolean b64) {
/* 418 */       this.b64 = b64;
/* 419 */       return this;
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
/*     */     public Builder customParam(String name, Object value) {
/* 441 */       if (JWSHeader.getRegisteredParameterNames().contains(name)) {
/* 442 */         throw new IllegalArgumentException("The parameter name \"" + name + "\" matches a registered name");
/*     */       }
/*     */       
/* 445 */       if (this.customParams == null) {
/* 446 */         this.customParams = new HashMap<>();
/*     */       }
/*     */       
/* 449 */       this.customParams.put(name, value);
/*     */       
/* 451 */       return this;
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
/*     */     public Builder customParams(Map<String, Object> customParameters) {
/* 466 */       this.customParams = customParameters;
/* 467 */       return this;
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
/*     */     public Builder parsedBase64URL(Base64URL base64URL) {
/* 481 */       this.parsedBase64URL = base64URL;
/* 482 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public JWSHeader build() {
/* 493 */       return new JWSHeader(this.alg, this.typ, this.cty, this.crit, this.jku, this.jwk, this.x5u, this.x5t, this.x5t256, this.x5c, this.kid, this.b64, this.customParams, this.parsedBase64URL);
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
/*     */   public JWSHeader(JWSAlgorithm alg) {
/* 519 */     this(alg, (JOSEObjectType)null, (String)null, (Set<String>)null, (URI)null, (JWK)null, (URI)null, (Base64URL)null, (Base64URL)null, (List<Base64>)null, (String)null, true, (Map<String, Object>)null, (Base64URL)null);
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
/*     */ 
/*     */ 
/*     */ 
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
/*     */   public JWSHeader(JWSAlgorithm alg, JOSEObjectType typ, String cty, Set<String> crit, URI jku, JWK jwk, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c, String kid, Map<String, Object> customParams, Base64URL parsedBase64URL) {
/* 574 */     this(alg, typ, cty, crit, jku, jwk, x5u, x5t, x5t256, x5c, kid, true, customParams, parsedBase64URL);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JWSHeader(JWSAlgorithm alg, JOSEObjectType typ, String cty, Set<String> crit, URI jku, JWK jwk, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c, String kid, boolean b64, Map<String, Object> customParams, Base64URL parsedBase64URL) {
/* 632 */     super(alg, typ, cty, crit, jku, jwk, x5u, x5t, x5t256, x5c, kid, customParams, parsedBase64URL);
/*     */     
/* 634 */     if (alg.getName().equals(Algorithm.NONE.getName())) {
/* 635 */       throw new IllegalArgumentException("The JWS algorithm \"alg\" cannot be \"none\"");
/*     */     }
/*     */     
/* 638 */     this.b64 = b64;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JWSHeader(JWSHeader jwsHeader) {
/* 649 */     this(jwsHeader
/* 650 */         .getAlgorithm(), jwsHeader
/* 651 */         .getType(), jwsHeader
/* 652 */         .getContentType(), jwsHeader
/* 653 */         .getCriticalParams(), jwsHeader
/* 654 */         .getJWKURL(), jwsHeader
/* 655 */         .getJWK(), jwsHeader
/* 656 */         .getX509CertURL(), jwsHeader
/* 657 */         .getX509CertThumbprint(), jwsHeader
/* 658 */         .getX509CertSHA256Thumbprint(), jwsHeader
/* 659 */         .getX509CertChain(), jwsHeader
/* 660 */         .getKeyID(), jwsHeader
/* 661 */         .isBase64URLEncodePayload(), jwsHeader
/* 662 */         .getCustomParams(), jwsHeader
/* 663 */         .getParsedBase64URL());
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
/*     */   public static Set<String> getRegisteredParameterNames() {
/* 675 */     return REGISTERED_PARAMETER_NAMES;
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
/*     */   public JWSAlgorithm getAlgorithm() {
/* 687 */     return (JWSAlgorithm)super.getAlgorithm();
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
/*     */   public boolean isBase64URLEncodePayload() {
/* 700 */     return this.b64;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getIncludedParams() {
/* 706 */     Set<String> includedParams = super.getIncludedParams();
/* 707 */     if (!isBase64URLEncodePayload()) {
/* 708 */       includedParams.add("b64");
/*     */     }
/* 710 */     return includedParams;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, Object> toJSONObject() {
/* 716 */     Map<String, Object> o = super.toJSONObject();
/* 717 */     if (!isBase64URLEncodePayload()) {
/* 718 */       o.put("b64", Boolean.valueOf(false));
/*     */     }
/* 720 */     return o;
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
/*     */   public static JWSHeader parse(Map<String, Object> jsonObject) throws ParseException {
/* 738 */     return parse(jsonObject, (Base64URL)null);
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
/*     */   public static JWSHeader parse(Map<String, Object> jsonObject, Base64URL parsedBase64URL) throws ParseException {
/* 760 */     Algorithm alg = Header.parseAlgorithm(jsonObject);
/*     */     
/* 762 */     if (!(alg instanceof JWSAlgorithm)) {
/* 763 */       throw new ParseException("Not a JWS header", 0);
/*     */     }
/*     */     
/* 766 */     Builder header = (new Builder((JWSAlgorithm)alg)).parsedBase64URL(parsedBase64URL);
/*     */ 
/*     */     
/* 769 */     for (String name : jsonObject.keySet()) {
/*     */       
/* 771 */       if ("alg".equals(name))
/*     */         continue; 
/* 773 */       if ("typ".equals(name)) {
/* 774 */         String typValue = JSONObjectUtils.getString(jsonObject, name);
/* 775 */         if (typValue != null)
/* 776 */           header = header.type(new JOSEObjectType(typValue));  continue;
/*     */       } 
/* 778 */       if ("cty".equals(name)) {
/* 779 */         header = header.contentType(JSONObjectUtils.getString(jsonObject, name)); continue;
/* 780 */       }  if ("crit".equals(name)) {
/* 781 */         List<String> critValues = JSONObjectUtils.getStringList(jsonObject, name);
/* 782 */         if (critValues != null)
/* 783 */           header = header.criticalParams(new HashSet<>(critValues));  continue;
/*     */       } 
/* 785 */       if ("jku".equals(name)) {
/* 786 */         header = header.jwkURL(JSONObjectUtils.getURI(jsonObject, name)); continue;
/* 787 */       }  if ("jwk".equals(name)) {
/* 788 */         header = header.jwk(CommonSEHeader.parsePublicJWK(JSONObjectUtils.getJSONObject(jsonObject, name))); continue;
/* 789 */       }  if ("x5u".equals(name)) {
/* 790 */         header = header.x509CertURL(JSONObjectUtils.getURI(jsonObject, name)); continue;
/* 791 */       }  if ("x5t".equals(name)) {
/* 792 */         header = header.x509CertThumbprint(Base64URL.from(JSONObjectUtils.getString(jsonObject, name))); continue;
/* 793 */       }  if ("x5t#S256".equals(name)) {
/* 794 */         header = header.x509CertSHA256Thumbprint(Base64URL.from(JSONObjectUtils.getString(jsonObject, name))); continue;
/* 795 */       }  if ("x5c".equals(name)) {
/* 796 */         header = header.x509CertChain(X509CertChainUtils.toBase64List(JSONObjectUtils.getJSONArray(jsonObject, name))); continue;
/* 797 */       }  if ("kid".equals(name)) {
/* 798 */         header = header.keyID(JSONObjectUtils.getString(jsonObject, name)); continue;
/* 799 */       }  if ("b64".equals(name)) {
/* 800 */         header = header.base64URLEncodePayload(JSONObjectUtils.getBoolean(jsonObject, name)); continue;
/*     */       } 
/* 802 */       header = header.customParam(name, jsonObject.get(name));
/*     */     } 
/*     */ 
/*     */     
/* 806 */     return header.build();
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
/*     */   public static JWSHeader parse(String jsonString) throws ParseException {
/* 824 */     return parse(jsonString, (Base64URL)null);
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
/*     */   public static JWSHeader parse(String jsonString, Base64URL parsedBase64URL) throws ParseException {
/* 845 */     return parse(JSONObjectUtils.parse(jsonString, 20000), parsedBase64URL);
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
/*     */   public static JWSHeader parse(Base64URL base64URL) throws ParseException {
/* 862 */     return parse(base64URL.decodeToString(), base64URL);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\JWSHeader.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */