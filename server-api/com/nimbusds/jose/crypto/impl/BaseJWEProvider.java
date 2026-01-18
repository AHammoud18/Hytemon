/*     */ package com.nimbusds.jose.crypto.impl;
/*     */ 
/*     */ import com.nimbusds.jose.EncryptionMethod;
/*     */ import com.nimbusds.jose.JOSEException;
/*     */ import com.nimbusds.jose.JWEAlgorithm;
/*     */ import com.nimbusds.jose.JWEProvider;
/*     */ import com.nimbusds.jose.jca.JCAContext;
/*     */ import com.nimbusds.jose.jca.JWEJCAContext;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import javax.crypto.SecretKey;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class BaseJWEProvider
/*     */   implements JWEProvider
/*     */ {
/*  47 */   private static final Set<String> ACCEPTABLE_CEK_ALGS = Collections.unmodifiableSet(new HashSet<>(
/*  48 */         Arrays.asList(new String[] { "AES", "ChaCha20" })));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Set<JWEAlgorithm> algs;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Set<EncryptionMethod> encs;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  67 */   private final JWEJCAContext jcaContext = new JWEJCAContext();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final SecretKey cek;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BaseJWEProvider(Set<JWEAlgorithm> algs, Set<EncryptionMethod> encs) {
/*  88 */     this(algs, encs, null);
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
/*     */   public BaseJWEProvider(Set<JWEAlgorithm> algs, Set<EncryptionMethod> encs, SecretKey cek) {
/* 109 */     if (algs == null) {
/* 110 */       throw new IllegalArgumentException("The supported JWE algorithm set must not be null");
/*     */     }
/*     */     
/* 113 */     this.algs = Collections.unmodifiableSet(algs);
/*     */ 
/*     */     
/* 116 */     if (encs == null) {
/* 117 */       throw new IllegalArgumentException("The supported encryption methods must not be null");
/*     */     }
/*     */     
/* 120 */     this.encs = encs;
/*     */     
/* 122 */     if (cek != null && algs.size() > 1 && (cek.getAlgorithm() == null || !ACCEPTABLE_CEK_ALGS.contains(cek.getAlgorithm()))) {
/* 123 */       throw new IllegalArgumentException("The algorithm of the content encryption key (CEK) must be AES or ChaCha20");
/*     */     }
/*     */     
/* 126 */     this.cek = cek;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<JWEAlgorithm> supportedJWEAlgorithms() {
/* 133 */     return this.algs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<EncryptionMethod> supportedEncryptionMethods() {
/* 140 */     return this.encs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JWEJCAContext getJCAContext() {
/* 147 */     return this.jcaContext;
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
/*     */   protected boolean isCEKProvided() {
/* 159 */     return (this.cek != null);
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
/*     */   protected SecretKey getCEK(EncryptionMethod enc) throws JOSEException {
/* 177 */     return (isCEKProvided() || enc == null) ? this.cek : ContentCryptoProvider.generateCEK(enc, this.jcaContext.getSecureRandom());
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\crypto\impl\BaseJWEProvider.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */