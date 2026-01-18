/*     */ package com.nimbusds.jose.jwk.source;
/*     */ 
/*     */ import com.nimbusds.jose.KeySourceException;
/*     */ import com.nimbusds.jose.jwk.JWKSet;
/*     */ import com.nimbusds.jose.proc.SecurityContext;
/*     */ import com.nimbusds.jose.shaded.jcip.ThreadSafe;
/*     */ import com.nimbusds.jose.util.Resource;
/*     */ import com.nimbusds.jose.util.ResourceRetriever;
/*     */ import java.io.IOException;
/*     */ import java.net.URL;
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
/*     */ @ThreadSafe
/*     */ public class URLBasedJWKSetSource<C extends SecurityContext>
/*     */   implements JWKSetSource<C>
/*     */ {
/*     */   private final URL url;
/*     */   private final ResourceRetriever resourceRetriever;
/*     */   
/*     */   public URLBasedJWKSetSource(URL url, ResourceRetriever resourceRetriever) {
/*  57 */     Objects.requireNonNull(url, "The URL must not be null");
/*  58 */     this.url = url;
/*  59 */     Objects.requireNonNull(resourceRetriever, "The resource retriever must not be null");
/*  60 */     this.resourceRetriever = resourceRetriever;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public URL getJWKSetURL() {
/*  70 */     return this.url;
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
/*  81 */     return this.resourceRetriever;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JWKSet getJWKSet(JWKSetCacheRefreshEvaluator refreshEvaluator, long currentTime, C context) throws KeySourceException {
/*     */     Resource resource;
/*     */     try {
/*  90 */       resource = getResourceRetriever().retrieveResource(getJWKSetURL());
/*  91 */     } catch (IOException e) {
/*  92 */       throw new JWKSetRetrievalException("Couldn't retrieve JWK set from URL: " + e.getMessage(), e);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 101 */       return JWKSet.parse(resource.getContent());
/*     */     }
/* 103 */     catch (Exception e) {
/*     */       
/* 105 */       throw new JWKSetParseException("Unable to parse JWK set", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void close() throws IOException {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\jwk\source\URLBasedJWKSetSource.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */