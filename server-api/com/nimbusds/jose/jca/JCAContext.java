/*     */ package com.nimbusds.jose.jca;
/*     */ 
/*     */ import java.security.Provider;
/*     */ import java.security.SecureRandom;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JCAContext
/*     */ {
/*     */   private Provider provider;
/*     */   private SecureRandom randomGen;
/*     */   
/*     */   public JCAContext() {
/*  53 */     this(null, null);
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
/*     */   public JCAContext(Provider provider, SecureRandom randomGen) {
/*  66 */     this.provider = provider;
/*  67 */     this.randomGen = randomGen;
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
/*     */   public Provider getProvider() {
/*  80 */     return this.provider;
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
/*     */   public void setProvider(Provider provider) {
/*  93 */     this.provider = provider;
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
/*     */   public SecureRandom getSecureRandom() {
/* 107 */     return (this.randomGen != null) ? this.randomGen : new SecureRandom();
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
/*     */   public void setSecureRandom(SecureRandom randomGen) {
/* 120 */     this.randomGen = randomGen;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\jca\JCAContext.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */