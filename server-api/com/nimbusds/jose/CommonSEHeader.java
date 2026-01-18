/*     */ package com.nimbusds.jose;
/*     */ 
/*     */ import com.nimbusds.jose.jwk.JWK;
/*     */ import com.nimbusds.jose.util.Base64;
/*     */ import com.nimbusds.jose.util.Base64URL;
/*     */ import java.net.URI;
/*     */ import java.text.ParseException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class CommonSEHeader
/*     */   extends Header
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private final URI jku;
/*     */   private final JWK jwk;
/*     */   private final URI x5u;
/*     */   private final Base64URL x5t;
/*     */   private final Base64URL x5t256;
/*     */   private final List<Base64> x5c;
/*     */   private final String kid;
/*     */   
/*     */   protected CommonSEHeader(Algorithm alg, JOSEObjectType typ, String cty, Set<String> crit, URI jku, JWK jwk, URI x5u, Base64URL x5t, Base64URL x5t256, List<Base64> x5c, String kid, Map<String, Object> customParams, Base64URL parsedBase64URL) {
/* 150 */     super(alg, typ, cty, crit, customParams, parsedBase64URL);
/*     */     
/* 152 */     this.jku = jku;
/* 153 */     this.jwk = jwk;
/* 154 */     this.x5u = x5u;
/* 155 */     this.x5t = x5t;
/* 156 */     this.x5t256 = x5t256;
/*     */     
/* 158 */     if (x5c != null) {
/*     */       
/* 160 */       this.x5c = Collections.unmodifiableList(new ArrayList<>(x5c));
/*     */     } else {
/* 162 */       this.x5c = null;
/*     */     } 
/*     */     
/* 165 */     this.kid = kid;
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
/*     */   public URI getJWKURL() {
/* 177 */     return this.jku;
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
/*     */   public JWK getJWK() {
/* 189 */     return this.jwk;
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
/*     */   public URI getX509CertURL() {
/* 201 */     return this.x5u;
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
/*     */   @Deprecated
/*     */   public Base64URL getX509CertThumbprint() {
/* 214 */     return this.x5t;
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
/*     */   public Base64URL getX509CertSHA256Thumbprint() {
/* 227 */     return this.x5t256;
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
/*     */   public List<Base64> getX509CertChain() {
/* 241 */     return this.x5c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getKeyID() {
/* 252 */     return this.kid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getIncludedParams() {
/* 259 */     Set<String> includedParameters = super.getIncludedParams();
/*     */     
/* 261 */     if (this.jku != null) {
/* 262 */       includedParameters.add("jku");
/*     */     }
/*     */     
/* 265 */     if (this.jwk != null) {
/* 266 */       includedParameters.add("jwk");
/*     */     }
/*     */     
/* 269 */     if (this.x5u != null) {
/* 270 */       includedParameters.add("x5u");
/*     */     }
/*     */     
/* 273 */     if (this.x5t != null) {
/* 274 */       includedParameters.add("x5t");
/*     */     }
/*     */     
/* 277 */     if (this.x5t256 != null) {
/* 278 */       includedParameters.add("x5t#S256");
/*     */     }
/*     */     
/* 281 */     if (this.x5c != null && !this.x5c.isEmpty()) {
/* 282 */       includedParameters.add("x5c");
/*     */     }
/*     */     
/* 285 */     if (this.kid != null) {
/* 286 */       includedParameters.add("kid");
/*     */     }
/*     */     
/* 289 */     return includedParameters;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, Object> toJSONObject() {
/* 296 */     Map<String, Object> o = super.toJSONObject();
/*     */     
/* 298 */     if (this.jku != null) {
/* 299 */       o.put("jku", this.jku.toString());
/*     */     }
/*     */     
/* 302 */     if (this.jwk != null) {
/* 303 */       o.put("jwk", this.jwk.toJSONObject());
/*     */     }
/*     */     
/* 306 */     if (this.x5u != null) {
/* 307 */       o.put("x5u", this.x5u.toString());
/*     */     }
/*     */     
/* 310 */     if (this.x5t != null) {
/* 311 */       o.put("x5t", this.x5t.toString());
/*     */     }
/*     */     
/* 314 */     if (this.x5t256 != null) {
/* 315 */       o.put("x5t#S256", this.x5t256.toString());
/*     */     }
/*     */     
/* 318 */     if (this.x5c != null && !this.x5c.isEmpty()) {
/* 319 */       List<String> x5cJson = new ArrayList<>(this.x5c.size());
/* 320 */       for (Base64 item : this.x5c) {
/* 321 */         x5cJson.add(item.toString());
/*     */       }
/* 323 */       o.put("x5c", x5cJson);
/*     */     } 
/*     */     
/* 326 */     if (this.kid != null) {
/* 327 */       o.put("kid", this.kid);
/*     */     }
/*     */     
/* 330 */     return o;
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
/*     */   static JWK parsePublicJWK(Map<String, Object> jwkObject) throws ParseException {
/* 347 */     if (jwkObject == null) {
/* 348 */       return null;
/*     */     }
/*     */     
/* 351 */     JWK jwk = JWK.parse(jwkObject);
/*     */     
/* 353 */     if (jwk.isPrivate()) {
/* 354 */       throw new ParseException("Non-public key in jwk header parameter", 0);
/*     */     }
/*     */     
/* 357 */     return jwk;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\CommonSEHeader.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */