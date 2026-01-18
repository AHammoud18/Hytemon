/*     */ package io.netty.handler.ssl;
/*     */ 
/*     */ import io.netty.handler.ssl.util.BouncyCastleUtil;
/*     */ import io.netty.util.CharsetUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.security.PrivateKey;
/*     */ import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
/*     */ import org.bouncycastle.openssl.PEMDecryptorProvider;
/*     */ import org.bouncycastle.openssl.PEMEncryptedKeyPair;
/*     */ import org.bouncycastle.openssl.PEMKeyPair;
/*     */ import org.bouncycastle.openssl.PEMParser;
/*     */ import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
/*     */ import org.bouncycastle.openssl.jcajce.JceOpenSSLPKCS8DecryptorProviderBuilder;
/*     */ import org.bouncycastle.openssl.jcajce.JcePEMDecryptorProviderBuilder;
/*     */ import org.bouncycastle.operator.InputDecryptorProvider;
/*     */ import org.bouncycastle.operator.OperatorCreationException;
/*     */ import org.bouncycastle.pkcs.PKCS8EncryptedPrivateKeyInfo;
/*     */ import org.bouncycastle.pkcs.PKCSException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class BouncyCastlePemReader
/*     */ {
/*  44 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(BouncyCastlePemReader.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PrivateKey getPrivateKey(InputStream keyInputStream, String keyPassword) {
/*  55 */     if (!BouncyCastleUtil.isBcPkixAvailable()) {
/*  56 */       if (logger.isDebugEnabled()) {
/*  57 */         logger.debug("Bouncy castle provider is unavailable.", BouncyCastleUtil.unavailabilityCauseBcPkix());
/*     */       }
/*  59 */       return null;
/*     */     } 
/*     */     try {
/*  62 */       PEMParser parser = newParser(keyInputStream);
/*  63 */       return getPrivateKey(parser, keyPassword);
/*  64 */     } catch (Exception e) {
/*  65 */       logger.debug("Unable to extract private key", e);
/*  66 */       return null;
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
/*     */   public static PrivateKey getPrivateKey(File keyFile, String keyPassword) {
/*  79 */     if (!BouncyCastleUtil.isBcPkixAvailable()) {
/*  80 */       if (logger.isDebugEnabled()) {
/*  81 */         logger.debug("Bouncy castle provider is unavailable.", BouncyCastleUtil.unavailabilityCauseBcPkix());
/*     */       }
/*  83 */       return null;
/*     */     } 
/*     */     try {
/*  86 */       PEMParser parser = newParser(keyFile);
/*  87 */       return getPrivateKey(parser, keyPassword);
/*  88 */     } catch (Exception e) {
/*  89 */       logger.debug("Unable to extract private key", e);
/*  90 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static JcaPEMKeyConverter newConverter() {
/*  95 */     return (new JcaPEMKeyConverter()).setProvider(BouncyCastleUtil.getBcProviderJce());
/*     */   }
/*     */ 
/*     */   
/*     */   private static PrivateKey getPrivateKey(PEMParser pemParser, String keyPassword) throws IOException, PKCSException, OperatorCreationException {
/*     */     try {
/* 101 */       JcaPEMKeyConverter converter = newConverter();
/* 102 */       PrivateKey pk = null;
/*     */       
/* 104 */       Object object = pemParser.readObject();
/* 105 */       while (object != null && pk == null) {
/* 106 */         if (logger.isDebugEnabled()) {
/* 107 */           logger.debug("Parsed PEM object of type {} and assume key is {}encrypted", object
/* 108 */               .getClass().getName(), (keyPassword == null) ? "not " : "");
/*     */         }
/*     */         
/* 111 */         if (keyPassword == null) {
/*     */           
/* 113 */           if (object instanceof PrivateKeyInfo) {
/* 114 */             pk = converter.getPrivateKey((PrivateKeyInfo)object);
/* 115 */           } else if (object instanceof PEMKeyPair) {
/* 116 */             pk = converter.getKeyPair((PEMKeyPair)object).getPrivate();
/*     */           } else {
/* 118 */             logger.debug("Unable to handle PEM object of type {} as a non encrypted key", object
/* 119 */                 .getClass());
/*     */           }
/*     */         
/*     */         }
/* 123 */         else if (object instanceof PEMEncryptedKeyPair) {
/*     */ 
/*     */           
/* 126 */           PEMDecryptorProvider decProv = (new JcePEMDecryptorProviderBuilder()).setProvider(BouncyCastleUtil.getBcProviderJce()).build(keyPassword.toCharArray());
/* 127 */           pk = converter.getKeyPair(((PEMEncryptedKeyPair)object).decryptKeyPair(decProv)).getPrivate();
/* 128 */         } else if (object instanceof PKCS8EncryptedPrivateKeyInfo) {
/*     */ 
/*     */ 
/*     */           
/* 132 */           InputDecryptorProvider pkcs8InputDecryptorProvider = (new JceOpenSSLPKCS8DecryptorProviderBuilder()).setProvider(BouncyCastleUtil.getBcProviderJce()).build(keyPassword.toCharArray());
/* 133 */           pk = converter.getPrivateKey(((PKCS8EncryptedPrivateKeyInfo)object)
/* 134 */               .decryptPrivateKeyInfo(pkcs8InputDecryptorProvider));
/*     */         } else {
/* 136 */           logger.debug("Unable to handle PEM object of type {} as a encrypted key", object.getClass());
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 141 */         if (pk == null) {
/* 142 */           object = pemParser.readObject();
/*     */         }
/*     */       } 
/*     */       
/* 146 */       if (pk == null && 
/* 147 */         logger.isDebugEnabled()) {
/* 148 */         logger.debug("No key found");
/*     */       }
/*     */ 
/*     */       
/* 152 */       return pk;
/*     */     } finally {
/* 154 */       if (pemParser != null) {
/*     */         try {
/* 156 */           pemParser.close();
/* 157 */         } catch (Exception exception) {
/* 158 */           logger.debug("Failed closing pem parser", exception);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static PEMParser newParser(File keyFile) throws FileNotFoundException {
/* 165 */     return new PEMParser(new FileReader(keyFile));
/*     */   }
/*     */   
/*     */   private static PEMParser newParser(InputStream keyInputStream) {
/* 169 */     return new PEMParser(new InputStreamReader(keyInputStream, CharsetUtil.US_ASCII));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\BouncyCastlePemReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */