/*     */ package io.netty.handler.ssl;
/*     */ 
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.buffer.UnpooledByteBufAllocator;
/*     */ import io.netty.internal.tcnative.SSL;
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.security.Key;
/*     */ import java.security.KeyStore;
/*     */ import java.security.KeyStoreException;
/*     */ import java.security.KeyStoreSpi;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.Provider;
/*     */ import java.security.UnrecoverableKeyException;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.net.ssl.KeyManager;
/*     */ import javax.net.ssl.KeyManagerFactory;
/*     */ import javax.net.ssl.KeyManagerFactorySpi;
/*     */ import javax.net.ssl.ManagerFactoryParameters;
/*     */ import javax.net.ssl.X509KeyManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class OpenSslX509KeyManagerFactory
/*     */   extends KeyManagerFactory
/*     */ {
/*     */   private final OpenSslKeyManagerFactorySpi spi;
/*     */   
/*     */   public OpenSslX509KeyManagerFactory() {
/*  71 */     this(newOpenSslKeyManagerFactorySpi(null));
/*     */   }
/*     */   
/*     */   public OpenSslX509KeyManagerFactory(Provider provider) {
/*  75 */     this(newOpenSslKeyManagerFactorySpi(provider));
/*     */   }
/*     */   
/*     */   public OpenSslX509KeyManagerFactory(String algorithm, Provider provider) throws NoSuchAlgorithmException {
/*  79 */     this(newOpenSslKeyManagerFactorySpi(algorithm, provider));
/*     */   }
/*     */   
/*     */   private OpenSslX509KeyManagerFactory(OpenSslKeyManagerFactorySpi spi) {
/*  83 */     super(spi, spi.kmf.getProvider(), spi.kmf.getAlgorithm());
/*  84 */     this.spi = spi;
/*     */   }
/*     */   
/*     */   private static OpenSslKeyManagerFactorySpi newOpenSslKeyManagerFactorySpi(Provider provider) {
/*     */     try {
/*  89 */       return newOpenSslKeyManagerFactorySpi(null, provider);
/*  90 */     } catch (NoSuchAlgorithmException e) {
/*     */       
/*  92 */       throw new IllegalStateException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static OpenSslKeyManagerFactorySpi newOpenSslKeyManagerFactorySpi(String algorithm, Provider provider) throws NoSuchAlgorithmException {
/*  98 */     if (algorithm == null) {
/*  99 */       algorithm = KeyManagerFactory.getDefaultAlgorithm();
/*     */     }
/* 101 */     return new OpenSslKeyManagerFactorySpi(
/* 102 */         (provider == null) ? KeyManagerFactory.getInstance(algorithm) : 
/* 103 */         KeyManagerFactory.getInstance(algorithm, provider));
/*     */   }
/*     */   
/*     */   OpenSslKeyMaterialProvider newProvider() {
/* 107 */     return this.spi.newProvider();
/*     */   }
/*     */   
/*     */   private static final class OpenSslKeyManagerFactorySpi extends KeyManagerFactorySpi {
/*     */     final KeyManagerFactory kmf;
/*     */     private volatile ProviderFactory providerFactory;
/*     */     
/*     */     OpenSslKeyManagerFactorySpi(KeyManagerFactory kmf) {
/* 115 */       this.kmf = (KeyManagerFactory)ObjectUtil.checkNotNull(kmf, "kmf");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected synchronized void engineInit(KeyStore keyStore, char[] chars) throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException {
/* 121 */       if (this.providerFactory != null) {
/* 122 */         throw new KeyStoreException("Already initialized");
/*     */       }
/* 124 */       if (!keyStore.aliases().hasMoreElements()) {
/* 125 */         throw new KeyStoreException("No aliases found");
/*     */       }
/*     */       
/* 128 */       this.kmf.init(keyStore, chars);
/* 129 */       this
/* 130 */         .providerFactory = new ProviderFactory(ReferenceCountedOpenSslContext.chooseX509KeyManager(this.kmf.getKeyManagers()), password(chars), Collections.list(keyStore.aliases()));
/*     */     }
/*     */     
/*     */     private static String password(char[] password) {
/* 134 */       if (password == null || password.length == 0) {
/* 135 */         return null;
/*     */       }
/* 137 */       return new String(password);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected void engineInit(ManagerFactoryParameters managerFactoryParameters) throws InvalidAlgorithmParameterException {
/* 143 */       throw new InvalidAlgorithmParameterException("Not supported");
/*     */     }
/*     */ 
/*     */     
/*     */     protected KeyManager[] engineGetKeyManagers() {
/* 148 */       ProviderFactory providerFactory = this.providerFactory;
/* 149 */       if (providerFactory == null) {
/* 150 */         throw new IllegalStateException("engineInit(...) not called yet");
/*     */       }
/* 152 */       return new KeyManager[] { ProviderFactory.access$000(providerFactory) };
/*     */     }
/*     */     
/*     */     OpenSslKeyMaterialProvider newProvider() {
/* 156 */       ProviderFactory providerFactory = this.providerFactory;
/* 157 */       if (providerFactory == null) {
/* 158 */         throw new IllegalStateException("engineInit(...) not called yet");
/*     */       }
/* 160 */       return providerFactory.newProvider();
/*     */     }
/*     */     
/*     */     private static final class ProviderFactory {
/*     */       private final X509KeyManager keyManager;
/*     */       private final String password;
/*     */       private final Iterable<String> aliases;
/*     */       
/*     */       ProviderFactory(X509KeyManager keyManager, String password, Iterable<String> aliases) {
/* 169 */         this.keyManager = keyManager;
/* 170 */         this.password = password;
/* 171 */         this.aliases = aliases;
/*     */       }
/*     */       
/*     */       OpenSslKeyMaterialProvider newProvider() {
/* 175 */         return new OpenSslPopulatedKeyMaterialProvider(this.keyManager, this.password, this.aliases);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       private static final class OpenSslPopulatedKeyMaterialProvider
/*     */         extends OpenSslKeyMaterialProvider
/*     */       {
/*     */         private final Map<String, Object> materialMap;
/*     */ 
/*     */ 
/*     */         
/*     */         OpenSslPopulatedKeyMaterialProvider(X509KeyManager keyManager, String password, Iterable<String> aliases) {
/* 188 */           super(keyManager, password);
/* 189 */           this.materialMap = new HashMap<>();
/* 190 */           boolean initComplete = false;
/*     */           try {
/* 192 */             for (String alias : aliases) {
/* 193 */               if (alias != null && !this.materialMap.containsKey(alias)) {
/*     */                 try {
/* 195 */                   this.materialMap.put(alias, super.chooseKeyMaterial((ByteBufAllocator)UnpooledByteBufAllocator.DEFAULT, alias));
/*     */                 }
/* 197 */                 catch (Exception e) {
/*     */ 
/*     */                   
/* 200 */                   this.materialMap.put(alias, e);
/*     */                 } 
/*     */               }
/*     */             } 
/* 204 */             initComplete = true;
/*     */           } finally {
/* 206 */             if (!initComplete) {
/* 207 */               destroy();
/*     */             }
/*     */           } 
/* 210 */           ObjectUtil.checkNonEmpty(this.materialMap, "materialMap");
/*     */         }
/*     */ 
/*     */         
/*     */         OpenSslKeyMaterial chooseKeyMaterial(ByteBufAllocator allocator, String alias) throws Exception {
/* 215 */           Object value = this.materialMap.get(alias);
/* 216 */           if (value == null)
/*     */           {
/* 218 */             return null;
/*     */           }
/* 220 */           if (value instanceof OpenSslKeyMaterial) {
/* 221 */             return ((OpenSslKeyMaterial)value).retain();
/*     */           }
/* 223 */           throw (Exception)value;
/*     */         }
/*     */ 
/*     */         
/*     */         void destroy() {
/* 228 */           for (Object material : this.materialMap.values()) {
/* 229 */             ReferenceCountUtil.release(material);
/*     */           }
/* 231 */           this.materialMap.clear();
/*     */         }
/*     */       }
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
/*     */   public static OpenSslX509KeyManagerFactory newEngineBased(File certificateChain, String password) throws CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException {
/* 246 */     return newEngineBased(SslContext.toX509Certificates(certificateChain), password);
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
/*     */   public static OpenSslX509KeyManagerFactory newEngineBased(X509Certificate[] certificateChain, String password) throws CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException {
/* 258 */     ObjectUtil.checkNotNull(certificateChain, "certificateChain");
/* 259 */     KeyStore store = new OpenSslKeyStore((X509Certificate[])certificateChain.clone(), false);
/* 260 */     store.load(null, null);
/* 261 */     OpenSslX509KeyManagerFactory factory = new OpenSslX509KeyManagerFactory();
/* 262 */     factory.init(store, (password == null) ? null : password.toCharArray());
/* 263 */     return factory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static OpenSslX509KeyManagerFactory newKeyless(File chain) throws CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException {
/* 272 */     return newKeyless(SslContext.toX509Certificates(chain));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static OpenSslX509KeyManagerFactory newKeyless(InputStream chain) throws CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException {
/* 281 */     return newKeyless(SslContext.toX509Certificates(chain));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static OpenSslX509KeyManagerFactory newKeyless(X509Certificate... certificateChain) throws CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException {
/* 291 */     ObjectUtil.checkNotNull(certificateChain, "certificateChain");
/* 292 */     KeyStore store = new OpenSslKeyStore((X509Certificate[])certificateChain.clone(), true);
/* 293 */     store.load(null, null);
/* 294 */     OpenSslX509KeyManagerFactory factory = new OpenSslX509KeyManagerFactory();
/* 295 */     factory.init(store, null);
/* 296 */     return factory;
/*     */   }
/*     */   
/*     */   private static final class OpenSslKeyStore extends KeyStore {
/*     */     private OpenSslKeyStore(X509Certificate[] certificateChain, boolean keyless) {
/* 301 */       super(new KeyStoreSpi(keyless, certificateChain)
/*     */           {
/* 303 */             private final Date creationDate = new Date();
/*     */ 
/*     */             
/*     */             public Key engineGetKey(String alias, char[] password) throws UnrecoverableKeyException {
/* 307 */               if (engineContainsAlias(alias)) {
/*     */                 long privateKeyAddress;
/* 309 */                 if (keyless) {
/* 310 */                   privateKeyAddress = 0L;
/*     */                 } else {
/*     */                   try {
/* 313 */                     privateKeyAddress = SSL.loadPrivateKeyFromEngine(alias, 
/* 314 */                         (password == null) ? null : new String(password));
/* 315 */                   } catch (Exception e) {
/* 316 */                     UnrecoverableKeyException keyException = new UnrecoverableKeyException("Unable to load key from engine");
/*     */                     
/* 318 */                     keyException.initCause(e);
/* 319 */                     throw keyException;
/*     */                   } 
/*     */                 } 
/* 322 */                 return new OpenSslPrivateKey(privateKeyAddress);
/*     */               } 
/* 324 */               return null;
/*     */             }
/*     */ 
/*     */             
/*     */             public Certificate[] engineGetCertificateChain(String alias) {
/* 329 */               return engineContainsAlias(alias) ? (Certificate[])certificateChain.clone() : null;
/*     */             }
/*     */ 
/*     */             
/*     */             public Certificate engineGetCertificate(String alias) {
/* 334 */               return engineContainsAlias(alias) ? certificateChain[0] : null;
/*     */             }
/*     */ 
/*     */             
/*     */             public Date engineGetCreationDate(String alias) {
/* 339 */               return engineContainsAlias(alias) ? this.creationDate : null;
/*     */             }
/*     */ 
/*     */ 
/*     */             
/*     */             public void engineSetKeyEntry(String alias, Key key, char[] password, Certificate[] chain) throws KeyStoreException {
/* 345 */               throw new KeyStoreException("Not supported");
/*     */             }
/*     */ 
/*     */             
/*     */             public void engineSetKeyEntry(String alias, byte[] key, Certificate[] chain) throws KeyStoreException {
/* 350 */               throw new KeyStoreException("Not supported");
/*     */             }
/*     */ 
/*     */             
/*     */             public void engineSetCertificateEntry(String alias, Certificate cert) throws KeyStoreException {
/* 355 */               throw new KeyStoreException("Not supported");
/*     */             }
/*     */ 
/*     */             
/*     */             public void engineDeleteEntry(String alias) throws KeyStoreException {
/* 360 */               throw new KeyStoreException("Not supported");
/*     */             }
/*     */ 
/*     */             
/*     */             public Enumeration<String> engineAliases() {
/* 365 */               return Collections.enumeration(Collections.singleton("key"));
/*     */             }
/*     */ 
/*     */             
/*     */             public boolean engineContainsAlias(String alias) {
/* 370 */               return "key".equals(alias);
/*     */             }
/*     */ 
/*     */             
/*     */             public int engineSize() {
/* 375 */               return 1;
/*     */             }
/*     */ 
/*     */             
/*     */             public boolean engineIsKeyEntry(String alias) {
/* 380 */               return engineContainsAlias(alias);
/*     */             }
/*     */ 
/*     */             
/*     */             public boolean engineIsCertificateEntry(String alias) {
/* 385 */               return engineContainsAlias(alias);
/*     */             }
/*     */ 
/*     */             
/*     */             public String engineGetCertificateAlias(Certificate cert) {
/* 390 */               if (cert instanceof X509Certificate) {
/* 391 */                 for (X509Certificate x509Certificate : certificateChain) {
/* 392 */                   if (x509Certificate.equals(cert)) {
/* 393 */                     return "key";
/*     */                   }
/*     */                 } 
/*     */               }
/* 397 */               return null;
/*     */             }
/*     */ 
/*     */             
/*     */             public void engineStore(OutputStream stream, char[] password) {
/* 402 */               throw new UnsupportedOperationException();
/*     */             }
/*     */ 
/*     */             
/*     */             public void engineLoad(InputStream stream, char[] password) {
/* 407 */               if (stream != null && password != null) {
/* 408 */                 throw new UnsupportedOperationException();
/*     */               }
/*     */             }
/*     */           }null, "native");
/*     */       
/* 413 */       OpenSsl.ensureAvailability();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\OpenSslX509KeyManagerFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */