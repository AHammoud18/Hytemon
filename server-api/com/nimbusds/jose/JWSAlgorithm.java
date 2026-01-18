/*     */ package com.nimbusds.jose;
/*     */ 
/*     */ import com.nimbusds.jose.shaded.jcip.Immutable;
/*     */ import com.nimbusds.jose.util.ArrayUtils;
/*     */ import java.util.Collection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public final class JWSAlgorithm
/*     */   extends Algorithm
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  67 */   public static final JWSAlgorithm HS256 = new JWSAlgorithm("HS256", Requirement.REQUIRED);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  73 */   public static final JWSAlgorithm HS384 = new JWSAlgorithm("HS384", Requirement.OPTIONAL);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  79 */   public static final JWSAlgorithm HS512 = new JWSAlgorithm("HS512", Requirement.OPTIONAL);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  85 */   public static final JWSAlgorithm RS256 = new JWSAlgorithm("RS256", Requirement.RECOMMENDED);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  91 */   public static final JWSAlgorithm RS384 = new JWSAlgorithm("RS384", Requirement.OPTIONAL);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  97 */   public static final JWSAlgorithm RS512 = new JWSAlgorithm("RS512", Requirement.OPTIONAL);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 104 */   public static final JWSAlgorithm ES256 = new JWSAlgorithm("ES256", Requirement.RECOMMENDED);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 111 */   public static final JWSAlgorithm ES256K = new JWSAlgorithm("ES256K", Requirement.OPTIONAL);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 117 */   public static final JWSAlgorithm ES384 = new JWSAlgorithm("ES384", Requirement.OPTIONAL);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 123 */   public static final JWSAlgorithm ES512 = new JWSAlgorithm("ES512", Requirement.OPTIONAL);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 130 */   public static final JWSAlgorithm PS256 = new JWSAlgorithm("PS256", Requirement.OPTIONAL);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 137 */   public static final JWSAlgorithm PS384 = new JWSAlgorithm("PS384", Requirement.OPTIONAL);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 144 */   public static final JWSAlgorithm PS512 = new JWSAlgorithm("PS512", Requirement.OPTIONAL);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 150 */   public static final JWSAlgorithm EdDSA = new JWSAlgorithm("EdDSA", Requirement.OPTIONAL);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 156 */   public static final JWSAlgorithm Ed25519 = new JWSAlgorithm("Ed25519", Requirement.OPTIONAL);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 162 */   public static final JWSAlgorithm Ed448 = new JWSAlgorithm("Ed448", Requirement.OPTIONAL);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Family
/*     */     extends AlgorithmFamily<JWSAlgorithm>
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 177 */     public static final Family HMAC_SHA = new Family(new JWSAlgorithm[] { JWSAlgorithm.HS256, JWSAlgorithm.HS384, JWSAlgorithm.HS512 });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 184 */     public static final Family RSA = new Family(new JWSAlgorithm[] { JWSAlgorithm.RS256, JWSAlgorithm.RS384, JWSAlgorithm.RS512, JWSAlgorithm.PS256, JWSAlgorithm.PS384, JWSAlgorithm.PS512 });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 190 */     public static final Family EC = new Family(new JWSAlgorithm[] { JWSAlgorithm.ES256, JWSAlgorithm.ES256K, JWSAlgorithm.ES384, JWSAlgorithm.ES512 });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 196 */     public static final Family ED = new Family(new JWSAlgorithm[] { JWSAlgorithm.EdDSA, JWSAlgorithm.Ed25519, JWSAlgorithm.Ed448 });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 202 */     public static final Family SIGNATURE = new Family(
/* 203 */         (JWSAlgorithm[])ArrayUtils.concat(RSA
/* 204 */           .toArray((Object[])new JWSAlgorithm[0]), (Object[][])new JWSAlgorithm[][] { (JWSAlgorithm[])EC
/* 205 */             .toArray((Object[])new JWSAlgorithm[0]), (JWSAlgorithm[])ED
/* 206 */             .toArray((Object[])new JWSAlgorithm[0]) }));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Family(JWSAlgorithm... algs) {
/* 218 */       super(algs);
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
/*     */   public JWSAlgorithm(String name, Requirement req) {
/* 232 */     super(name, req);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JWSAlgorithm(String name) {
/* 243 */     super(name, null);
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
/*     */   public static JWSAlgorithm parse(String s) {
/* 257 */     if (s.equals(HS256.getName()))
/* 258 */       return HS256; 
/* 259 */     if (s.equals(HS384.getName()))
/* 260 */       return HS384; 
/* 261 */     if (s.equals(HS512.getName()))
/* 262 */       return HS512; 
/* 263 */     if (s.equals(RS256.getName()))
/* 264 */       return RS256; 
/* 265 */     if (s.equals(RS384.getName()))
/* 266 */       return RS384; 
/* 267 */     if (s.equals(RS512.getName()))
/* 268 */       return RS512; 
/* 269 */     if (s.equals(ES256.getName()))
/* 270 */       return ES256; 
/* 271 */     if (s.equals(ES256K.getName()))
/* 272 */       return ES256K; 
/* 273 */     if (s.equals(ES384.getName()))
/* 274 */       return ES384; 
/* 275 */     if (s.equals(ES512.getName()))
/* 276 */       return ES512; 
/* 277 */     if (s.equals(PS256.getName()))
/* 278 */       return PS256; 
/* 279 */     if (s.equals(PS384.getName()))
/* 280 */       return PS384; 
/* 281 */     if (s.equals(PS512.getName()))
/* 282 */       return PS512; 
/* 283 */     if (s.equals(EdDSA.getName()))
/* 284 */       return EdDSA; 
/* 285 */     if (s.equals(Ed25519.getName()))
/* 286 */       return Ed25519; 
/* 287 */     if (s.equals(Ed448.getName())) {
/* 288 */       return Ed448;
/*     */     }
/* 290 */     return new JWSAlgorithm(s);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\JWSAlgorithm.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */