/*     */ package com.nimbusds.jose.jwk.source;
/*     */ 
/*     */ import com.nimbusds.jose.KeySourceException;
/*     */ import com.nimbusds.jose.RemoteKeySourceException;
/*     */ import com.nimbusds.jose.jwk.JWK;
/*     */ import com.nimbusds.jose.jwk.JWKMatcher;
/*     */ import com.nimbusds.jose.jwk.JWKSelector;
/*     */ import com.nimbusds.jose.jwk.JWKSet;
/*     */ import com.nimbusds.jose.proc.SecurityContext;
/*     */ import com.nimbusds.jose.shaded.jcip.ThreadSafe;
/*     */ import com.nimbusds.jose.util.DefaultResourceRetriever;
/*     */ import com.nimbusds.jose.util.Resource;
/*     */ import com.nimbusds.jose.util.ResourceRetriever;
/*     */ import java.io.IOException;
/*     */ import java.net.URL;
/*     */ import java.text.ParseException;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
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
/*     */ @ThreadSafe
/*     */ @Deprecated
/*     */ public class RemoteJWKSet<C extends SecurityContext>
/*     */   implements JWKSource<C>
/*     */ {
/*     */   public static final int DEFAULT_HTTP_CONNECT_TIMEOUT = 500;
/*     */   public static final int DEFAULT_HTTP_READ_TIMEOUT = 500;
/*     */   public static final int DEFAULT_HTTP_SIZE_LIMIT = 51200;
/*     */   private final URL jwkSetURL;
/*     */   private final JWKSource<C> failoverJWKSource;
/*     */   private final JWKSetCache jwkSetCache;
/*     */   private final ResourceRetriever jwkSetRetriever;
/*     */   
/*     */   public static int resolveDefaultHTTPConnectTimeout() {
/* 113 */     return resolveDefault(RemoteJWKSet.class.getName() + ".defaultHttpConnectTimeout", 500);
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
/*     */   public static int resolveDefaultHTTPReadTimeout() {
/* 127 */     return resolveDefault(RemoteJWKSet.class.getName() + ".defaultHttpReadTimeout", 500);
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
/*     */   public static int resolveDefaultHTTPSizeLimit() {
/* 141 */     return resolveDefault(RemoteJWKSet.class.getName() + ".defaultHttpSizeLimit", 51200);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int resolveDefault(String sysPropertyName, int defaultValue) {
/* 147 */     String value = System.getProperty(sysPropertyName);
/*     */     
/* 149 */     if (value == null) {
/* 150 */       return defaultValue;
/*     */     }
/*     */     
/*     */     try {
/* 154 */       return Integer.parseInt(value);
/* 155 */     } catch (NumberFormatException e) {
/*     */       
/* 157 */       return defaultValue;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RemoteJWKSet(URL jwkSetURL) {
/* 194 */     this(jwkSetURL, (JWKSource<C>)null);
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
/*     */   public RemoteJWKSet(URL jwkSetURL, JWKSource<C> failoverJWKSource) {
/* 209 */     this(jwkSetURL, failoverJWKSource, null, null);
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
/*     */   public RemoteJWKSet(URL jwkSetURL, ResourceRetriever resourceRetriever) {
/* 226 */     this(jwkSetURL, resourceRetriever, null);
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
/*     */   public RemoteJWKSet(URL jwkSetURL, ResourceRetriever resourceRetriever, JWKSetCache jwkSetCache) {
/* 247 */     this(jwkSetURL, null, resourceRetriever, jwkSetCache);
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
/*     */   public RemoteJWKSet(URL jwkSetURL, JWKSource<C> failoverJWKSource, ResourceRetriever resourceRetriever, JWKSetCache jwkSetCache) {
/* 272 */     this.jwkSetURL = Objects.<URL>requireNonNull(jwkSetURL);
/*     */     
/* 274 */     this.failoverJWKSource = failoverJWKSource;
/*     */     
/* 276 */     if (resourceRetriever != null) {
/* 277 */       this.jwkSetRetriever = resourceRetriever;
/*     */     } else {
/* 279 */       this
/*     */ 
/*     */         
/* 282 */         .jwkSetRetriever = (ResourceRetriever)new DefaultResourceRetriever(resolveDefaultHTTPConnectTimeout(), resolveDefaultHTTPReadTimeout(), resolveDefaultHTTPSizeLimit());
/*     */     } 
/*     */     
/* 285 */     if (jwkSetCache != null) {
/* 286 */       this.jwkSetCache = jwkSetCache;
/*     */     } else {
/* 288 */       this.jwkSetCache = new DefaultJWKSetCache();
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
/*     */   private JWKSet updateJWKSetFromURL() throws RemoteKeySourceException {
/*     */     Resource res;
/*     */     JWKSet jwkSet;
/*     */     try {
/* 304 */       res = this.jwkSetRetriever.retrieveResource(this.jwkSetURL);
/* 305 */     } catch (IOException e) {
/* 306 */       throw new RemoteKeySourceException("Couldn't retrieve remote JWK set: " + e.getMessage(), e);
/*     */     } 
/*     */     
/*     */     try {
/* 310 */       jwkSet = JWKSet.parse(res.getContent());
/* 311 */     } catch (ParseException e) {
/* 312 */       throw new RemoteKeySourceException("Couldn't parse remote JWK set: " + e.getMessage(), e);
/*     */     } 
/* 314 */     this.jwkSetCache.put(jwkSet);
/* 315 */     return jwkSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public URL getJWKSetURL() {
/* 326 */     return this.jwkSetURL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JWKSource<C> getFailoverJWKSource() {
/* 337 */     return this.failoverJWKSource;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResourceRetriever getResourceRetriever() {
/* 348 */     return this.jwkSetRetriever;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JWKSetCache getJWKSetCache() {
/* 359 */     return this.jwkSetCache;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JWKSet getCachedJWKSet() {
/* 370 */     return this.jwkSetCache.get();
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
/*     */   protected static String getFirstSpecifiedKeyID(JWKMatcher jwkMatcher) {
/* 383 */     Set<String> keyIDs = jwkMatcher.getKeyIDs();
/*     */     
/* 385 */     if (keyIDs == null || keyIDs.isEmpty()) {
/* 386 */       return null;
/*     */     }
/*     */     
/* 389 */     for (String id : keyIDs) {
/* 390 */       if (id != null) {
/* 391 */         return id;
/*     */       }
/*     */     } 
/* 394 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<JWK> failover(Exception exception, JWKSelector jwkSelector, C context) throws RemoteKeySourceException {
/* 404 */     if (getFailoverJWKSource() == null) {
/* 405 */       return null;
/*     */     }
/*     */     
/*     */     try {
/* 409 */       return getFailoverJWKSource().get(jwkSelector, context);
/* 410 */     } catch (KeySourceException kse) {
/* 411 */       throw new RemoteKeySourceException(exception
/* 412 */           .getMessage() + "; Failover JWK source retrieval failed with: " + kse
/* 413 */           .getMessage(), kse);
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
/*     */   public List<JWK> get(JWKSelector jwkSelector, C context) throws RemoteKeySourceException {
/* 425 */     JWKSet jwkSet = this.jwkSetCache.get();
/*     */     
/* 427 */     if (this.jwkSetCache.requiresRefresh() || jwkSet == null) {
/*     */       
/*     */       try {
/*     */ 
/*     */         
/* 432 */         synchronized (this) {
/* 433 */           jwkSet = this.jwkSetCache.get();
/* 434 */           if (this.jwkSetCache.requiresRefresh() || jwkSet == null)
/*     */           {
/* 436 */             jwkSet = updateJWKSetFromURL();
/*     */           }
/*     */         } 
/* 439 */       } catch (Exception e) {
/*     */         
/* 441 */         List<JWK> failoverMatches = failover(e, jwkSelector, context);
/* 442 */         if (failoverMatches != null) {
/* 443 */           return failoverMatches;
/*     */         }
/*     */         
/* 446 */         if (jwkSet == null)
/*     */         {
/* 448 */           throw e;
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 456 */     List<JWK> matches = jwkSelector.select(jwkSet);
/*     */     
/* 458 */     if (!matches.isEmpty())
/*     */     {
/* 460 */       return matches;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 466 */     String soughtKeyID = getFirstSpecifiedKeyID(jwkSelector.getMatcher());
/* 467 */     if (soughtKeyID == null)
/*     */     {
/* 469 */       return Collections.emptyList();
/*     */     }
/*     */     
/* 472 */     if (jwkSet.getKeyByKeyId(soughtKeyID) != null)
/*     */     {
/*     */       
/* 475 */       return Collections.emptyList();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 482 */       synchronized (this) {
/* 483 */         if (jwkSet == this.jwkSetCache.get()) {
/*     */           
/* 485 */           jwkSet = updateJWKSetFromURL();
/*     */         } else {
/*     */           
/* 488 */           jwkSet = this.jwkSetCache.get();
/*     */         } 
/*     */       } 
/* 491 */     } catch (KeySourceException e) {
/*     */       
/* 493 */       List<JWK> failoverMatches = failover((Exception)e, jwkSelector, context);
/* 494 */       if (failoverMatches != null) {
/* 495 */         return failoverMatches;
/*     */       }
/*     */       
/* 498 */       throw e;
/*     */     } 
/*     */ 
/*     */     
/* 502 */     if (jwkSet == null)
/*     */     {
/* 504 */       return Collections.emptyList();
/*     */     }
/*     */ 
/*     */     
/* 508 */     return jwkSelector.select(jwkSet);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\jwk\source\RemoteJWKSet.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */