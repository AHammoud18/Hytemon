/*     */ package com.nimbusds.jose.proc;
/*     */ 
/*     */ import com.nimbusds.jose.JWSAlgorithm;
/*     */ import com.nimbusds.jose.JWSHeader;
/*     */ import com.nimbusds.jose.KeySourceException;
/*     */ import com.nimbusds.jose.jwk.JWK;
/*     */ import com.nimbusds.jose.jwk.JWKMatcher;
/*     */ import com.nimbusds.jose.jwk.JWKSelector;
/*     */ import com.nimbusds.jose.jwk.KeyConverter;
/*     */ import com.nimbusds.jose.jwk.source.JWKSource;
/*     */ import com.nimbusds.jose.shaded.jcip.ThreadSafe;
/*     */ import java.security.Key;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedList;
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
/*     */ @ThreadSafe
/*     */ public class JWSVerificationKeySelector<C extends SecurityContext>
/*     */   extends AbstractJWKSelectorWithSource<C>
/*     */   implements JWSKeySelector<C>
/*     */ {
/*     */   private final Set<JWSAlgorithm> jwsAlgs;
/*     */   private final boolean singleJwsAlgConstructorWasCalled;
/*     */   
/*     */   public JWSVerificationKeySelector(JWSAlgorithm jwsAlg, JWKSource<C> jwkSource) {
/*  67 */     super(jwkSource);
/*  68 */     this.jwsAlgs = Collections.singleton(Objects.requireNonNull(jwsAlg));
/*  69 */     this.singleJwsAlgConstructorWasCalled = true;
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
/*     */   public JWSVerificationKeySelector(Set<JWSAlgorithm> jwsAlgs, JWKSource<C> jwkSource) {
/*  81 */     super(jwkSource);
/*  82 */     if (jwsAlgs.isEmpty()) {
/*  83 */       throw new IllegalArgumentException("The JWS algorithms must not be empty");
/*     */     }
/*  85 */     this.jwsAlgs = Collections.unmodifiableSet(jwsAlgs);
/*  86 */     this.singleJwsAlgConstructorWasCalled = false;
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
/*     */   public boolean isAllowed(JWSAlgorithm jwsAlg) {
/*  98 */     return this.jwsAlgs.contains(jwsAlg);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public JWSAlgorithm getExpectedJWSAlgorithm() {
/* 110 */     if (this.singleJwsAlgConstructorWasCalled) {
/* 111 */       return this.jwsAlgs.iterator().next();
/*     */     }
/* 113 */     throw new UnsupportedOperationException("Since this class was constructed with multiple algorithms, the behavior of this method is undefined.");
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
/*     */   protected JWKMatcher createJWKMatcher(JWSHeader jwsHeader) {
/* 127 */     if (!isAllowed(jwsHeader.getAlgorithm()))
/*     */     {
/* 129 */       return null;
/*     */     }
/* 131 */     return JWKMatcher.forJWSHeader(jwsHeader);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Key> selectJWSKeys(JWSHeader jwsHeader, C context) throws KeySourceException {
/* 140 */     if (!this.jwsAlgs.contains(jwsHeader.getAlgorithm()))
/*     */     {
/* 142 */       return Collections.emptyList();
/*     */     }
/*     */     
/* 145 */     JWKMatcher jwkMatcher = createJWKMatcher(jwsHeader);
/* 146 */     if (jwkMatcher == null) {
/* 147 */       return Collections.emptyList();
/*     */     }
/*     */     
/* 150 */     List<JWK> jwkMatches = getJWKSource().get(new JWKSelector(jwkMatcher), (SecurityContext)context);
/*     */     
/* 152 */     List<Key> sanitizedKeyList = new LinkedList<>();
/*     */     
/* 154 */     for (Key key : KeyConverter.toJavaKeys(jwkMatches)) {
/* 155 */       if (key instanceof java.security.PublicKey || key instanceof javax.crypto.SecretKey) {
/* 156 */         sanitizedKeyList.add(key);
/*     */       }
/*     */     } 
/*     */     
/* 160 */     return sanitizedKeyList;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\proc\JWSVerificationKeySelector.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */