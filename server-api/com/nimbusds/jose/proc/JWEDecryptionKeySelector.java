/*     */ package com.nimbusds.jose.proc;
/*     */ 
/*     */ import com.nimbusds.jose.EncryptionMethod;
/*     */ import com.nimbusds.jose.JWEAlgorithm;
/*     */ import com.nimbusds.jose.JWEHeader;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class JWEDecryptionKeySelector<C extends SecurityContext>
/*     */   extends AbstractJWKSelectorWithSource<C>
/*     */   implements JWEKeySelector<C>
/*     */ {
/*     */   private final JWEAlgorithm jweAlg;
/*     */   private final EncryptionMethod jweEnc;
/*     */   
/*     */   public JWEDecryptionKeySelector(JWEAlgorithm jweAlg, EncryptionMethod jweEnc, JWKSource<C> jwkSource) {
/*  77 */     super(jwkSource);
/*  78 */     this.jweAlg = Objects.<JWEAlgorithm>requireNonNull(jweAlg);
/*  79 */     this.jweEnc = Objects.<EncryptionMethod>requireNonNull(jweEnc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JWEAlgorithm getExpectedJWEAlgorithm() {
/*  89 */     return this.jweAlg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EncryptionMethod getExpectedJWEEncryptionMethod() {
/*  99 */     return this.jweEnc;
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
/*     */   protected JWKMatcher createJWKMatcher(JWEHeader jweHeader) {
/* 113 */     if (!getExpectedJWEAlgorithm().equals(jweHeader.getAlgorithm())) {
/* 114 */       return null;
/*     */     }
/*     */     
/* 117 */     if (!getExpectedJWEEncryptionMethod().equals(jweHeader.getEncryptionMethod())) {
/* 118 */       return null;
/*     */     }
/*     */     
/* 121 */     return JWKMatcher.forJWEHeader(jweHeader);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Key> selectJWEKeys(JWEHeader jweHeader, C context) throws KeySourceException {
/* 129 */     if (!this.jweAlg.equals(jweHeader.getAlgorithm()) || !this.jweEnc.equals(jweHeader.getEncryptionMethod()))
/*     */     {
/* 131 */       return Collections.emptyList();
/*     */     }
/*     */     
/* 134 */     JWKMatcher jwkMatcher = createJWKMatcher(jweHeader);
/* 135 */     List<JWK> jwkMatches = getJWKSource().get(new JWKSelector(jwkMatcher), (SecurityContext)context);
/* 136 */     List<Key> sanitizedKeyList = new LinkedList<>();
/*     */     
/* 138 */     for (Key key : KeyConverter.toJavaKeys(jwkMatches)) {
/* 139 */       if (key instanceof java.security.PrivateKey || key instanceof javax.crypto.SecretKey) {
/* 140 */         sanitizedKeyList.add(key);
/*     */       }
/*     */     } 
/*     */     
/* 144 */     return sanitizedKeyList;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\proc\JWEDecryptionKeySelector.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */