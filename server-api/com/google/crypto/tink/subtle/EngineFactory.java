/*     */ package com.google.crypto.tink.subtle;
/*     */ 
/*     */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.KeyFactory;
/*     */ import java.security.KeyPairGenerator;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.Provider;
/*     */ import java.security.Security;
/*     */ import java.security.Signature;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.KeyAgreement;
/*     */ import javax.crypto.Mac;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class EngineFactory<T_WRAPPER extends EngineWrapper<JcePrimitiveT>, JcePrimitiveT>
/*     */ {
/*     */   private final Policy<JcePrimitiveT> policy;
/*     */   
/*     */   private static class DefaultPolicy<JcePrimitiveT>
/*     */     implements Policy<JcePrimitiveT>
/*     */   {
/*     */     private final EngineWrapper<JcePrimitiveT> jceFactory;
/*     */     
/*     */     private DefaultPolicy(EngineWrapper<JcePrimitiveT> jceFactory) {
/*  64 */       this.jceFactory = jceFactory;
/*     */     }
/*     */ 
/*     */     
/*     */     public JcePrimitiveT getInstance(String algorithm) throws GeneralSecurityException {
/*  69 */       return this.jceFactory.getInstance(algorithm, null);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public JcePrimitiveT getInstance(String algorithm, List<Provider> preferredProviders) throws GeneralSecurityException {
/*  75 */       for (Provider provider : preferredProviders) {
/*     */         try {
/*  77 */           return this.jceFactory.getInstance(algorithm, provider);
/*  78 */         } catch (Exception exception) {}
/*     */       } 
/*     */ 
/*     */       
/*  82 */       return getInstance(algorithm);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class FipsPolicy<JcePrimitiveT>
/*     */     implements Policy<JcePrimitiveT>
/*     */   {
/*     */     private final EngineWrapper<JcePrimitiveT> jceFactory;
/*     */ 
/*     */     
/*     */     private FipsPolicy(EngineWrapper<JcePrimitiveT> jceFactory) {
/*  94 */       this.jceFactory = jceFactory;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public JcePrimitiveT getInstance(String algorithm) throws GeneralSecurityException {
/* 100 */       List<Provider> conscryptProviders = EngineFactory.toProviderList(new String[] { "GmsCore_OpenSSL", "AndroidOpenSSL", "Conscrypt" });
/* 101 */       Exception cause = null;
/* 102 */       for (Provider provider : conscryptProviders) {
/*     */         try {
/* 104 */           return this.jceFactory.getInstance(algorithm, provider);
/* 105 */         } catch (Exception e) {
/* 106 */           if (cause == null) {
/* 107 */             cause = e;
/*     */           }
/*     */         } 
/*     */       } 
/* 111 */       throw new GeneralSecurityException("No good Provider found.", cause);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public JcePrimitiveT getInstance(String algorithm, List<Provider> preferredProviders) throws GeneralSecurityException {
/* 118 */       return getInstance(algorithm);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class AndroidPolicy<JcePrimitiveT>
/*     */     implements Policy<JcePrimitiveT>
/*     */   {
/*     */     private final EngineWrapper<JcePrimitiveT> jceFactory;
/*     */ 
/*     */     
/*     */     private AndroidPolicy(EngineWrapper<JcePrimitiveT> jceFactory) {
/* 130 */       this.jceFactory = jceFactory;
/*     */     }
/*     */ 
/*     */     
/*     */     public JcePrimitiveT getInstance(String algorithm) throws GeneralSecurityException {
/* 135 */       List<Provider> conscryptProviders = EngineFactory.toProviderList(new String[] { "GmsCore_OpenSSL", "AndroidOpenSSL" });
/* 136 */       Exception cause = null;
/* 137 */       for (Provider provider : conscryptProviders) {
/*     */         try {
/* 139 */           return this.jceFactory.getInstance(algorithm, provider);
/* 140 */         } catch (Exception e) {
/* 141 */           if (cause == null) {
/* 142 */             cause = e;
/*     */           }
/*     */         } 
/*     */       } 
/* 146 */       return this.jceFactory.getInstance(algorithm, null);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public JcePrimitiveT getInstance(String algorithm, List<Provider> preferredProviders) throws GeneralSecurityException {
/* 153 */       return getInstance(algorithm);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 159 */   public static final EngineFactory<EngineWrapper.TCipher, Cipher> CIPHER = new EngineFactory((T_WRAPPER)new EngineWrapper.TCipher());
/*     */ 
/*     */   
/* 162 */   public static final EngineFactory<EngineWrapper.TMac, Mac> MAC = new EngineFactory((T_WRAPPER)new EngineWrapper.TMac());
/*     */ 
/*     */   
/* 165 */   public static final EngineFactory<EngineWrapper.TSignature, Signature> SIGNATURE = new EngineFactory((T_WRAPPER)new EngineWrapper.TSignature());
/*     */ 
/*     */   
/* 168 */   public static final EngineFactory<EngineWrapper.TMessageDigest, MessageDigest> MESSAGE_DIGEST = new EngineFactory((T_WRAPPER)new EngineWrapper.TMessageDigest());
/*     */ 
/*     */   
/* 171 */   public static final EngineFactory<EngineWrapper.TKeyAgreement, KeyAgreement> KEY_AGREEMENT = new EngineFactory((T_WRAPPER)new EngineWrapper.TKeyAgreement());
/*     */ 
/*     */ 
/*     */   
/* 175 */   public static final EngineFactory<EngineWrapper.TKeyPairGenerator, KeyPairGenerator> KEY_PAIR_GENERATOR = new EngineFactory((T_WRAPPER)new EngineWrapper.TKeyPairGenerator());
/*     */   
/* 177 */   public static final EngineFactory<EngineWrapper.TKeyFactory, KeyFactory> KEY_FACTORY = new EngineFactory((T_WRAPPER)new EngineWrapper.TKeyFactory());
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<Provider> toProviderList(String... providerNames) {
/* 182 */     List<Provider> providers = new ArrayList<>();
/* 183 */     for (String s : providerNames) {
/* 184 */       Provider p = Security.getProvider(s);
/* 185 */       if (p != null) {
/* 186 */         providers.add(p);
/*     */       }
/*     */     } 
/* 189 */     return providers;
/*     */   }
/*     */   
/*     */   public EngineFactory(T_WRAPPER instanceBuilder) {
/* 193 */     if (TinkFipsUtil.useOnlyFips()) {
/* 194 */       this.policy = new FipsPolicy<>((EngineWrapper)instanceBuilder);
/* 195 */     } else if (SubtleUtil.isAndroid()) {
/* 196 */       this.policy = new AndroidPolicy<>((EngineWrapper)instanceBuilder);
/*     */     } else {
/* 198 */       this.policy = new DefaultPolicy<>((EngineWrapper)instanceBuilder);
/*     */     } 
/*     */   }
/*     */   
/*     */   public JcePrimitiveT getInstance(String algorithm) throws GeneralSecurityException {
/* 203 */     return this.policy.getInstance(algorithm);
/*     */   }
/*     */ 
/*     */   
/*     */   JcePrimitiveT getInstance(String algorithm, List<Provider> preferredProviders) throws GeneralSecurityException {
/* 208 */     return this.policy.getInstance(algorithm, preferredProviders);
/*     */   }
/*     */   
/*     */   private static interface Policy<JcePrimitiveT> {
/*     */     JcePrimitiveT getInstance(String param1String) throws GeneralSecurityException;
/*     */     
/*     */     JcePrimitiveT getInstance(String param1String, List<Provider> param1List) throws GeneralSecurityException;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\subtle\EngineFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */