/*     */ package com.nimbusds.jose.jwk;
/*     */ 
/*     */ import com.nimbusds.jose.JOSEException;
/*     */ import com.nimbusds.jose.shaded.jcip.Immutable;
/*     */ import com.nimbusds.jose.util.Base64URL;
/*     */ import com.nimbusds.jose.util.DefaultResourceRetriever;
/*     */ import com.nimbusds.jose.util.IOUtils;
/*     */ import com.nimbusds.jose.util.JSONArrayUtils;
/*     */ import com.nimbusds.jose.util.JSONObjectUtils;
/*     */ import com.nimbusds.jose.util.Resource;
/*     */ import com.nimbusds.jose.util.StandardCharset;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.Serializable;
/*     */ import java.net.Proxy;
/*     */ import java.net.URL;
/*     */ import java.security.KeyStore;
/*     */ import java.security.KeyStoreException;
/*     */ import java.security.cert.Certificate;
/*     */ import java.text.ParseException;
/*     */ import java.util.Collections;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class JWKSet
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public static final String MIME_TYPE = "application/jwk-set+json; charset=UTF-8";
/*     */   private final List<JWK> keys;
/*     */   private final Map<String, Object> customMembers;
/*     */   
/*     */   public JWKSet() {
/* 104 */     this(Collections.emptyList());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JWKSet(JWK key) {
/* 115 */     this(Collections.singletonList(Objects.requireNonNull(key, "The JWK must not be null")));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JWKSet(List<JWK> keys) {
/* 126 */     this(keys, Collections.emptyMap());
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
/*     */   public JWKSet(List<JWK> keys, Map<String, Object> customMembers) {
/* 140 */     this.keys = Collections.unmodifiableList(Objects.<List<? extends JWK>>requireNonNull(keys, "The JWK list must not be null"));
/* 141 */     this.customMembers = Collections.unmodifiableMap(customMembers);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<JWK> getKeys() {
/* 152 */     return this.keys;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 162 */     return this.keys.isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 172 */     return this.keys.size();
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
/*     */   public JWK getKeyByKeyId(String kid) {
/* 190 */     for (JWK key : getKeys()) {
/*     */       
/* 192 */       if (key.getKeyID() != null && key.getKeyID().equals(kid)) {
/* 193 */         return key;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 198 */     return null;
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
/*     */   public boolean containsJWK(JWK jwk) throws JOSEException {
/* 215 */     Base64URL thumbprint = jwk.computeThumbprint();
/*     */     
/* 217 */     for (JWK k : getKeys()) {
/* 218 */       if (thumbprint.equals(k.computeThumbprint())) {
/* 219 */         return true;
/*     */       }
/*     */     } 
/* 222 */     return false;
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
/*     */   public Map<String, Object> getAdditionalMembers() {
/* 234 */     return this.customMembers;
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
/*     */   public JWKSet toPublicJWKSet() {
/* 247 */     List<JWK> publicKeyList = new LinkedList<>();
/*     */     
/* 249 */     for (JWK key : this.keys) {
/*     */       
/* 251 */       JWK publicKey = key.toPublicJWK();
/*     */       
/* 253 */       if (publicKey != null) {
/* 254 */         publicKeyList.add(publicKey);
/*     */       }
/*     */     } 
/*     */     
/* 258 */     return new JWKSet(publicKeyList, this.customMembers);
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
/*     */   public JWKSet filter(JWKMatcher jwkMatcher) {
/* 272 */     List<JWK> matches = new LinkedList<>();
/*     */     
/* 274 */     for (JWK key : this.keys) {
/* 275 */       if (jwkMatcher.matches(key)) {
/* 276 */         matches.add(key);
/*     */       }
/*     */     } 
/*     */     
/* 280 */     return new JWKSet(matches, this.customMembers);
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
/*     */   public boolean containsNonPublicKeys() {
/* 292 */     for (JWK jwk : getKeys()) {
/* 293 */       if (jwk.isPrivate()) {
/* 294 */         return true;
/*     */       }
/*     */     } 
/* 297 */     return false;
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
/*     */   public Map<String, Object> toJSONObject() {
/* 310 */     return toJSONObject(true);
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
/*     */   public Map<String, Object> toJSONObject(boolean publicKeysOnly) {
/* 327 */     Map<String, Object> o = JSONObjectUtils.newJSONObject();
/* 328 */     o.putAll(this.customMembers);
/* 329 */     List<Object> a = JSONArrayUtils.newJSONArray();
/*     */     
/* 331 */     for (JWK key : this.keys) {
/*     */       
/* 333 */       if (publicKeysOnly) {
/*     */ 
/*     */         
/* 336 */         JWK publicKey = key.toPublicJWK();
/*     */         
/* 338 */         if (publicKey != null) {
/* 339 */           a.add(publicKey.toJSONObject());
/*     */         }
/*     */         continue;
/*     */       } 
/* 343 */       a.add(key.toJSONObject());
/*     */     } 
/*     */ 
/*     */     
/* 347 */     o.put("keys", a);
/*     */     
/* 349 */     return o;
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
/*     */   public String toString(boolean publicKeysOnly) {
/* 366 */     return JSONObjectUtils.toJSONString(toJSONObject(publicKeysOnly));
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
/*     */   public String toString() {
/* 381 */     return toString(true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 387 */     if (this == o) return true; 
/* 388 */     if (!(o instanceof JWKSet)) return false; 
/* 389 */     JWKSet jwkSet = (JWKSet)o;
/* 390 */     return (getKeys().equals(jwkSet.getKeys()) && this.customMembers.equals(jwkSet.customMembers));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 396 */     return Objects.hash(new Object[] { getKeys(), this.customMembers });
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
/*     */   public static JWKSet parse(String s) throws ParseException {
/* 413 */     return parse(JSONObjectUtils.parse(s));
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
/*     */   public static JWKSet parse(Map<String, Object> json) throws ParseException {
/* 430 */     List<Object> keyArray = JSONObjectUtils.getJSONArray(json, "keys");
/*     */     
/* 432 */     if (keyArray == null) {
/* 433 */       throw new ParseException("Missing required \"keys\" member", 0);
/*     */     }
/*     */     
/* 436 */     List<JWK> keys = new LinkedList<>();
/*     */     
/* 438 */     for (int i = 0; i < keyArray.size(); i++) {
/*     */       
/*     */       try {
/* 441 */         Map<String, Object> keyJSONObject = (Map<String, Object>)keyArray.get(i);
/* 442 */         keys.add(JWK.parse(keyJSONObject));
/*     */       }
/* 444 */       catch (ClassCastException e) {
/*     */         
/* 446 */         throw new ParseException("The \"keys\" JSON array must contain JSON objects only", 0);
/*     */       }
/* 448 */       catch (ParseException e) {
/*     */         
/* 450 */         if (e.getMessage() == null || !e.getMessage().startsWith("Unsupported key type"))
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 456 */           throw new ParseException("Invalid JWK at position " + i + ": " + e.getMessage(), 0);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 461 */     Map<String, Object> additionalMembers = new HashMap<>();
/* 462 */     for (Map.Entry<String, Object> entry : json.entrySet()) {
/*     */       
/* 464 */       if (entry.getKey() == null || ((String)entry.getKey()).equals("keys")) {
/*     */         continue;
/*     */       }
/*     */       
/* 468 */       additionalMembers.put(entry.getKey(), entry.getValue());
/*     */     } 
/*     */     
/* 471 */     return new JWKSet(keys, additionalMembers);
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
/*     */   public static JWKSet load(InputStream inputStream) throws IOException, ParseException {
/* 489 */     return parse(IOUtils.readInputStreamToString(inputStream, StandardCharset.UTF_8));
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
/*     */   public static JWKSet load(File file) throws IOException, ParseException {
/* 507 */     return parse(IOUtils.readFileToString(file, StandardCharset.UTF_8));
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
/*     */   public static JWKSet load(URL url, int connectTimeout, int readTimeout, int sizeLimit) throws IOException, ParseException {
/* 534 */     return load(url, connectTimeout, readTimeout, sizeLimit, null);
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
/*     */   public static JWKSet load(URL url, int connectTimeout, int readTimeout, int sizeLimit, Proxy proxy) throws IOException, ParseException {
/* 565 */     DefaultResourceRetriever resourceRetriever = new DefaultResourceRetriever(connectTimeout, readTimeout, sizeLimit);
/*     */ 
/*     */ 
/*     */     
/* 569 */     resourceRetriever.setProxy(proxy);
/* 570 */     Resource resource = resourceRetriever.retrieveResource(url);
/* 571 */     return parse(resource.getContent());
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
/*     */   public static JWKSet load(URL url) throws IOException, ParseException {
/* 589 */     return load(url, 0, 0, 0);
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
/*     */   public static JWKSet load(KeyStore keyStore, PasswordLookup pwLookup) throws KeyStoreException {
/* 612 */     List<JWK> jwks = new LinkedList<>();
/*     */     
/*     */     Enumeration<String> keyAliases;
/* 615 */     for (keyAliases = keyStore.aliases(); keyAliases.hasMoreElements(); ) {
/*     */       
/* 617 */       String keyAlias = keyAliases.nextElement();
/* 618 */       char[] keyPassword = (pwLookup == null) ? "".toCharArray() : pwLookup.lookupPassword(keyAlias);
/*     */       
/* 620 */       Certificate cert = keyStore.getCertificate(keyAlias);
/* 621 */       if (cert == null) {
/*     */         continue;
/*     */       }
/*     */       
/* 625 */       if (cert.getPublicKey() instanceof java.security.interfaces.RSAPublicKey) {
/*     */         RSAKey rsaJWK;
/*     */         
/*     */         try {
/* 629 */           rsaJWK = RSAKey.load(keyStore, keyAlias, keyPassword);
/* 630 */         } catch (JOSEException e) {
/*     */           continue;
/*     */         } 
/*     */         
/* 634 */         if (rsaJWK == null) {
/*     */           continue;
/*     */         }
/*     */         
/* 638 */         jwks.add(rsaJWK); continue;
/*     */       } 
/* 640 */       if (cert.getPublicKey() instanceof java.security.interfaces.ECPublicKey) {
/*     */         ECKey ecJWK;
/*     */         
/*     */         try {
/* 644 */           ecJWK = ECKey.load(keyStore, keyAlias, keyPassword);
/* 645 */         } catch (JOSEException e) {
/*     */           continue;
/*     */         } 
/*     */         
/* 649 */         if (ecJWK != null) {
/* 650 */           jwks.add(ecJWK);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 657 */     for (keyAliases = keyStore.aliases(); keyAliases.hasMoreElements(); ) {
/*     */       OctetSequenceKey octJWK;
/* 659 */       String keyAlias = keyAliases.nextElement();
/* 660 */       char[] keyPassword = (pwLookup == null) ? "".toCharArray() : pwLookup.lookupPassword(keyAlias);
/*     */ 
/*     */       
/*     */       try {
/* 664 */         octJWK = OctetSequenceKey.load(keyStore, keyAlias, keyPassword);
/* 665 */       } catch (JOSEException e) {
/*     */         ECKey ecJWK;
/*     */         continue;
/*     */       } 
/* 669 */       if (octJWK != null) {
/* 670 */         jwks.add(octJWK);
/*     */       }
/*     */     } 
/*     */     
/* 674 */     return new JWKSet(jwks);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\jwk\JWKSet.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */