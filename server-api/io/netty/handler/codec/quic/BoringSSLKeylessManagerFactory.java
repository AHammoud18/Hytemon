/*     */ package io.netty.handler.codec.quic;
/*     */ 
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.file.Files;
/*     */ import java.security.Key;
/*     */ import java.security.KeyStore;
/*     */ import java.security.KeyStoreException;
/*     */ import java.security.KeyStoreSpi;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.UnrecoverableKeyException;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Objects;
/*     */ import javax.net.ssl.KeyManager;
/*     */ import javax.net.ssl.KeyManagerFactory;
/*     */ import javax.net.ssl.KeyManagerFactorySpi;
/*     */ import javax.net.ssl.ManagerFactoryParameters;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class BoringSSLKeylessManagerFactory
/*     */   extends KeyManagerFactory
/*     */ {
/*     */   final BoringSSLAsyncPrivateKeyMethod privateKeyMethod;
/*     */   
/*     */   private BoringSSLKeylessManagerFactory(KeyManagerFactory keyManagerFactory, BoringSSLAsyncPrivateKeyMethod privateKeyMethod) {
/*  54 */     super(new KeylessManagerFactorySpi(keyManagerFactory), keyManagerFactory
/*  55 */         .getProvider(), keyManagerFactory.getAlgorithm());
/*  56 */     this.privateKeyMethod = Objects.<BoringSSLAsyncPrivateKeyMethod>requireNonNull(privateKeyMethod, "privateKeyMethod");
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
/*     */   public static BoringSSLKeylessManagerFactory newKeyless(BoringSSLAsyncPrivateKeyMethod privateKeyMethod, File chain) throws CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException {
/*  74 */     InputStream chainInputStream = Files.newInputStream(chain.toPath(), new java.nio.file.OpenOption[0]); try {
/*  75 */       BoringSSLKeylessManagerFactory boringSSLKeylessManagerFactory = newKeyless(privateKeyMethod, chainInputStream);
/*  76 */       if (chainInputStream != null) chainInputStream.close();
/*     */       
/*     */       return boringSSLKeylessManagerFactory;
/*     */     } catch (Throwable throwable) {
/*     */       if (chainInputStream != null) {
/*     */         try {
/*     */           chainInputStream.close();
/*     */         } catch (Throwable throwable1) {
/*     */           throwable.addSuppressed(throwable1);
/*     */         } 
/*     */       }
/*     */       throw throwable;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BoringSSLKeylessManagerFactory newKeyless(BoringSSLAsyncPrivateKeyMethod privateKeyMethod, InputStream chain) throws CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException {
/*  95 */     return newKeyless(privateKeyMethod, QuicSslContext.toX509Certificates0(chain));
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
/*     */   public static BoringSSLKeylessManagerFactory newKeyless(BoringSSLAsyncPrivateKeyMethod privateKeyMethod, X509Certificate... certificateChain) throws CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException {
/* 114 */     ObjectUtil.checkNotNull(certificateChain, "certificateChain");
/* 115 */     KeyStore store = new KeylessKeyStore((X509Certificate[])certificateChain.clone());
/* 116 */     store.load(null, null);
/*     */     
/* 118 */     BoringSSLKeylessManagerFactory factory = new BoringSSLKeylessManagerFactory(KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm()), privateKeyMethod);
/* 119 */     factory.init(store, null);
/* 120 */     return factory;
/*     */   }
/*     */   
/*     */   private static final class KeylessManagerFactorySpi
/*     */     extends KeyManagerFactorySpi {
/*     */     private final KeyManagerFactory keyManagerFactory;
/*     */     
/*     */     KeylessManagerFactorySpi(KeyManagerFactory keyManagerFactory) {
/* 128 */       this.keyManagerFactory = Objects.<KeyManagerFactory>requireNonNull(keyManagerFactory, "keyManagerFactory");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected void engineInit(KeyStore ks, char[] password) throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException {
/* 134 */       this.keyManagerFactory.init(ks, password);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void engineInit(ManagerFactoryParameters spec) {
/* 139 */       throw new UnsupportedOperationException("Not supported");
/*     */     }
/*     */ 
/*     */     
/*     */     protected KeyManager[] engineGetKeyManagers() {
/* 144 */       return this.keyManagerFactory.getKeyManagers();
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class KeylessKeyStore extends KeyStore {
/*     */     private KeylessKeyStore(X509Certificate[] certificateChain) {
/* 150 */       super(new KeyStoreSpi(certificateChain)
/*     */           {
/* 152 */             private final Date creationDate = new Date();
/*     */ 
/*     */             
/*     */             @Nullable
/*     */             public Key engineGetKey(String alias, char[] password) {
/* 157 */               if (engineContainsAlias(alias)) {
/* 158 */                 return BoringSSLKeylessPrivateKey.INSTANCE;
/*     */               }
/* 160 */               return null;
/*     */             }
/*     */ 
/*     */             
/*     */             public Certificate[] engineGetCertificateChain(String alias) {
/* 165 */               return engineContainsAlias(alias) ? (Certificate[])certificateChain.clone() : null;
/*     */             }
/*     */ 
/*     */             
/*     */             @Nullable
/*     */             public Certificate engineGetCertificate(String alias) {
/* 171 */               return engineContainsAlias(alias) ? certificateChain[0] : null;
/*     */             }
/*     */ 
/*     */             
/*     */             @Nullable
/*     */             public Date engineGetCreationDate(String alias) {
/* 177 */               return engineContainsAlias(alias) ? this.creationDate : null;
/*     */             }
/*     */ 
/*     */ 
/*     */             
/*     */             public void engineSetKeyEntry(String alias, Key key, char[] password, Certificate[] chain) throws KeyStoreException {
/* 183 */               throw new KeyStoreException("Not supported");
/*     */             }
/*     */ 
/*     */             
/*     */             public void engineSetKeyEntry(String alias, byte[] key, Certificate[] chain) throws KeyStoreException {
/* 188 */               throw new KeyStoreException("Not supported");
/*     */             }
/*     */ 
/*     */             
/*     */             public void engineSetCertificateEntry(String alias, Certificate cert) throws KeyStoreException {
/* 193 */               throw new KeyStoreException("Not supported");
/*     */             }
/*     */ 
/*     */             
/*     */             public void engineDeleteEntry(String alias) throws KeyStoreException {
/* 198 */               throw new KeyStoreException("Not supported");
/*     */             }
/*     */ 
/*     */             
/*     */             public Enumeration<String> engineAliases() {
/* 203 */               return Collections.enumeration(Collections.singleton("key"));
/*     */             }
/*     */ 
/*     */             
/*     */             public boolean engineContainsAlias(String alias) {
/* 208 */               return "key".equals(alias);
/*     */             }
/*     */ 
/*     */             
/*     */             public int engineSize() {
/* 213 */               return 1;
/*     */             }
/*     */ 
/*     */             
/*     */             public boolean engineIsKeyEntry(String alias) {
/* 218 */               return engineContainsAlias(alias);
/*     */             }
/*     */ 
/*     */             
/*     */             public boolean engineIsCertificateEntry(String alias) {
/* 223 */               return engineContainsAlias(alias);
/*     */             }
/*     */ 
/*     */             
/*     */             @Nullable
/*     */             public String engineGetCertificateAlias(Certificate cert) {
/* 229 */               if (cert instanceof X509Certificate) {
/* 230 */                 for (X509Certificate x509Certificate : certificateChain) {
/* 231 */                   if (x509Certificate.equals(cert)) {
/* 232 */                     return "key";
/*     */                   }
/*     */                 } 
/*     */               }
/* 236 */               return null;
/*     */             }
/*     */ 
/*     */             
/*     */             public void engineStore(OutputStream stream, char[] password) {
/* 241 */               throw new UnsupportedOperationException();
/*     */             }
/*     */ 
/*     */             
/*     */             public void engineLoad(@Nullable InputStream stream, char[] password) {
/* 246 */               if (stream != null && password != null)
/* 247 */                 throw new UnsupportedOperationException(); 
/*     */             }
/*     */           },  null, "keyless");
/*     */     }
/*     */     private static final String ALIAS = "key";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\BoringSSLKeylessManagerFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */