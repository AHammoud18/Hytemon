/*     */ package io.netty.handler.ssl.util;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.handler.codec.base64.Base64;
/*     */ import io.netty.util.CharsetUtil;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.SystemPropertyUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.security.KeyPair;
/*     */ import java.security.KeyPairGenerator;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.SecureRandom;
/*     */ import java.security.cert.CertificateEncodingException;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.CertificateFactory;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.Date;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ public final class SelfSignedCertificate
/*     */ {
/*  65 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(SelfSignedCertificate.class);
/*     */ 
/*     */   
/*  68 */   private static final Date DEFAULT_NOT_BEFORE = new Date(SystemPropertyUtil.getLong("io.netty.selfSignedCertificate.defaultNotBefore", 
/*  69 */         System.currentTimeMillis() - 31536000000L));
/*     */   
/*  71 */   private static final Date DEFAULT_NOT_AFTER = new Date(SystemPropertyUtil.getLong("io.netty.selfSignedCertificate.defaultNotAfter", 253402300799000L));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  80 */   private static final int DEFAULT_KEY_LENGTH_BITS = SystemPropertyUtil.getInt("io.netty.handler.ssl.util.selfSignedKeyStrength", 2048);
/*     */   
/*     */   private final File certificate;
/*     */   
/*     */   private final File privateKey;
/*     */   
/*     */   private final X509Certificate cert;
/*     */   
/*     */   private final PrivateKey key;
/*     */ 
/*     */   
/*     */   public SelfSignedCertificate() throws CertificateException {
/*  92 */     this(new Builder(null));
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
/*     */   public SelfSignedCertificate(Date notBefore, Date notAfter) throws CertificateException {
/* 104 */     this((new Builder(null)).notBefore(notBefore).notAfter(notAfter));
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
/*     */   public SelfSignedCertificate(Date notBefore, Date notAfter, String algorithm, int bits) throws CertificateException {
/* 117 */     this((new Builder(null)).notBefore(notBefore).notAfter(notAfter).algorithm(algorithm).bits(bits));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SelfSignedCertificate(String fqdn) throws CertificateException {
/* 127 */     this((new Builder(null)).fqdn(fqdn));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SelfSignedCertificate(String fqdn, String algorithm, int bits) throws CertificateException {
/* 138 */     this((new Builder(null)).fqdn(fqdn).algorithm(algorithm).bits(bits));
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
/*     */   public SelfSignedCertificate(String fqdn, Date notBefore, Date notAfter) throws CertificateException {
/* 150 */     this((new Builder(null)).fqdn(fqdn).notBefore(notBefore).notAfter(notAfter));
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
/*     */   public SelfSignedCertificate(String fqdn, Date notBefore, Date notAfter, String algorithm, int bits) throws CertificateException {
/* 164 */     this((new Builder(null)).fqdn(fqdn).notBefore(notBefore).notAfter(notAfter).algorithm(algorithm).bits(bits));
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
/*     */   public SelfSignedCertificate(String fqdn, SecureRandom random, int bits) throws CertificateException {
/* 177 */     this((new Builder(null)).fqdn(fqdn).random(random).bits(bits));
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
/*     */   public SelfSignedCertificate(String fqdn, SecureRandom random, String algorithm, int bits) throws CertificateException {
/* 190 */     this((new Builder(null)).fqdn(fqdn).random(random).algorithm(algorithm).bits(bits));
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
/*     */   public SelfSignedCertificate(String fqdn, SecureRandom random, int bits, Date notBefore, Date notAfter) throws CertificateException {
/* 205 */     this((new Builder(null)).fqdn(fqdn).notBefore(notBefore).notAfter(notAfter).random(random).bits(bits));
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
/*     */   public SelfSignedCertificate(String fqdn, SecureRandom random, int bits, Date notBefore, Date notAfter, String algorithm) throws CertificateException {
/* 220 */     this((new Builder(null)).fqdn(fqdn).random(random).algorithm(algorithm).bits(bits)
/* 221 */         .notBefore(notBefore).notAfter(notAfter));
/*     */   }
/*     */   
/*     */   private SelfSignedCertificate(Builder builder) throws CertificateException {
/* 225 */     if (!builder.generateCertificateBuilder() && 
/* 226 */       !builder.generateBc() && 
/* 227 */       !builder.generateKeytool() && 
/* 228 */       !builder.generateSunMiscSecurity())
/*     */     {
/* 230 */       throw (CertificateException)builder.failure;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 236 */     this.certificate = new File(builder.paths[0]);
/* 237 */     this.privateKey = new File(builder.paths[1]);
/* 238 */     this.key = builder.privateKey; 
/* 239 */     try { FileInputStream certificateInput = new FileInputStream(this.certificate); 
/* 240 */       try { this.cert = (X509Certificate)CertificateFactory.getInstance("X509").generateCertificate(certificateInput);
/* 241 */         certificateInput.close(); } catch (Throwable throwable) { try { certificateInput.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (Exception e)
/* 242 */     { throw new CertificateEncodingException(e); }
/*     */   
/*     */   }
/*     */   
/*     */   public static Builder builder() {
/* 247 */     return new Builder();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public File certificate() {
/* 254 */     return this.certificate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public File privateKey() {
/* 261 */     return this.privateKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public X509Certificate cert() {
/* 268 */     return this.cert;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PrivateKey key() {
/* 275 */     return this.key;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void delete() {
/* 282 */     safeDelete(this.certificate);
/* 283 */     safeDelete(this.privateKey);
/*     */   }
/*     */ 
/*     */   
/*     */   static String[] newSelfSignedCertificate(String fqdn, PrivateKey key, X509Certificate cert) throws IOException, CertificateEncodingException {
/*     */     String keyText, certText;
/* 289 */     ByteBuf wrappedBuf = Unpooled.wrappedBuffer(key.getEncoded());
/*     */ 
/*     */     
/*     */     try {
/* 293 */       ByteBuf encodedBuf = Base64.encode(wrappedBuf, true);
/*     */       
/*     */       try {
/* 296 */         keyText = "-----BEGIN PRIVATE KEY-----\n" + encodedBuf.toString(CharsetUtil.US_ASCII) + "\n-----END PRIVATE KEY-----\n";
/*     */       } finally {
/*     */         
/* 299 */         encodedBuf.release();
/*     */       } 
/*     */     } finally {
/* 302 */       wrappedBuf.release();
/*     */     } 
/*     */ 
/*     */     
/* 306 */     fqdn = fqdn.replaceAll("[^\\w.-]", "x");
/*     */     
/* 308 */     File keyFile = PlatformDependent.createTempFile("keyutil_" + fqdn + '_', ".key", null);
/* 309 */     keyFile.deleteOnExit();
/*     */     
/* 311 */     OutputStream keyOut = new FileOutputStream(keyFile);
/*     */     try {
/* 313 */       keyOut.write(keyText.getBytes(CharsetUtil.US_ASCII));
/* 314 */       keyOut.close();
/* 315 */       keyOut = null;
/*     */     } finally {
/* 317 */       if (keyOut != null) {
/* 318 */         safeClose(keyFile, keyOut);
/* 319 */         safeDelete(keyFile);
/*     */       } 
/*     */     } 
/*     */     
/* 323 */     wrappedBuf = Unpooled.wrappedBuffer(cert.getEncoded());
/*     */     
/*     */     try {
/* 326 */       ByteBuf encodedBuf = Base64.encode(wrappedBuf, true);
/*     */ 
/*     */       
/*     */       try {
/* 330 */         certText = "-----BEGIN CERTIFICATE-----\n" + encodedBuf.toString(CharsetUtil.US_ASCII) + "\n-----END CERTIFICATE-----\n";
/*     */       } finally {
/*     */         
/* 333 */         encodedBuf.release();
/*     */       } 
/*     */     } finally {
/* 336 */       wrappedBuf.release();
/*     */     } 
/*     */     
/* 339 */     File certFile = PlatformDependent.createTempFile("keyutil_" + fqdn + '_', ".crt", null);
/* 340 */     certFile.deleteOnExit();
/*     */     
/* 342 */     OutputStream certOut = new FileOutputStream(certFile);
/*     */     try {
/* 344 */       certOut.write(certText.getBytes(CharsetUtil.US_ASCII));
/* 345 */       certOut.close();
/* 346 */       certOut = null;
/*     */     } finally {
/* 348 */       if (certOut != null) {
/* 349 */         safeClose(certFile, certOut);
/* 350 */         safeDelete(certFile);
/* 351 */         safeDelete(keyFile);
/*     */       } 
/*     */     } 
/*     */     
/* 355 */     return new String[] { certFile.getPath(), keyFile.getPath() };
/*     */   }
/*     */   
/*     */   private static void safeDelete(File certFile) {
/* 359 */     if (!certFile.delete() && 
/* 360 */       logger.isWarnEnabled()) {
/* 361 */       logger.warn("Failed to delete a file: " + certFile);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static void safeClose(File keyFile, OutputStream keyOut) {
/*     */     try {
/* 368 */       keyOut.close();
/* 369 */     } catch (IOException e) {
/* 370 */       if (logger.isWarnEnabled()) {
/* 371 */         logger.warn("Failed to close a file: " + keyFile, e);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isBouncyCastleAvailable() {
/*     */     try {
/* 379 */       Class.forName("org.bouncycastle.cert.X509v3CertificateBuilder");
/* 380 */       return true;
/* 381 */     } catch (ClassNotFoundException e) {
/* 382 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static final class Builder
/*     */   {
/* 388 */     String fqdn = "localhost";
/*     */     SecureRandom random;
/* 390 */     int bits = SelfSignedCertificate.DEFAULT_KEY_LENGTH_BITS;
/* 391 */     Date notBefore = SelfSignedCertificate.DEFAULT_NOT_BEFORE;
/* 392 */     Date notAfter = SelfSignedCertificate.DEFAULT_NOT_AFTER;
/* 393 */     String algorithm = "RSA";
/*     */ 
/*     */ 
/*     */     
/*     */     Throwable failure;
/*     */ 
/*     */     
/*     */     KeyPair keypair;
/*     */ 
/*     */     
/*     */     PrivateKey privateKey;
/*     */ 
/*     */     
/*     */     String[] paths;
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder fqdn(String fqdn) {
/* 411 */       this.fqdn = (String)ObjectUtil.checkNotNullWithIAE(fqdn, "fqdn");
/* 412 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder random(SecureRandom random) {
/* 422 */       this.random = random;
/* 423 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder bits(int bits) {
/* 433 */       this.bits = bits;
/* 434 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder notBefore(Date notBefore) {
/* 444 */       this.notBefore = (Date)ObjectUtil.checkNotNullWithIAE(notBefore, "notBefore");
/* 445 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder notAfter(Date notAfter) {
/* 455 */       this.notAfter = (Date)ObjectUtil.checkNotNullWithIAE(notAfter, "notAfter");
/* 456 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder algorithm(String algorithm) {
/* 466 */       if ("EC".equalsIgnoreCase(algorithm)) {
/* 467 */         this.algorithm = "EC";
/* 468 */       } else if ("RSA".equalsIgnoreCase(algorithm)) {
/* 469 */         this.algorithm = "RSA";
/*     */       } else {
/* 471 */         throw new IllegalArgumentException("Algorithm not valid: " + algorithm);
/*     */       } 
/* 473 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private SecureRandom randomOrDefault() {
/* 479 */       return (this.random == null) ? ThreadLocalInsecureRandom.current() : this.random;
/*     */     }
/*     */     
/*     */     private void generateKeyPairLocally() {
/* 483 */       if (this.keypair != null) {
/*     */         return;
/*     */       }
/*     */       
/*     */       try {
/* 488 */         KeyPairGenerator keyGen = KeyPairGenerator.getInstance(this.algorithm);
/* 489 */         keyGen.initialize(this.bits, randomOrDefault());
/* 490 */         this.keypair = keyGen.generateKeyPair();
/* 491 */       } catch (NoSuchAlgorithmException e) {
/*     */         
/* 493 */         throw new IllegalStateException(e);
/*     */       } 
/* 495 */       this.privateKey = this.keypair.getPrivate();
/*     */     }
/*     */     
/*     */     private void addFailure(Throwable t) {
/* 499 */       if (this.failure != null) {
/* 500 */         t.addSuppressed(this.failure);
/*     */       }
/* 502 */       this.failure = t;
/*     */     }
/*     */     
/*     */     boolean generateBc() {
/* 506 */       if (!SelfSignedCertificate.isBouncyCastleAvailable()) {
/*     */         
/* 508 */         SelfSignedCertificate.logger.debug("Failed to generate a self-signed X.509 certificate because BouncyCastle PKIX is not available in classpath");
/*     */         
/* 510 */         return false;
/*     */       } 
/* 512 */       generateKeyPairLocally();
/*     */       
/*     */       try {
/* 515 */         this.paths = BouncyCastleSelfSignedCertGenerator.generate(this.fqdn, this.keypair, 
/* 516 */             randomOrDefault(), this.notBefore, this.notAfter, this.algorithm);
/* 517 */         return true;
/* 518 */       } catch (Throwable t) {
/* 519 */         SelfSignedCertificate.logger.debug("Failed to generate a self-signed X.509 certificate using Bouncy Castle:", t);
/* 520 */         addFailure(t);
/* 521 */         return false;
/*     */       } 
/*     */     }
/*     */     
/*     */     boolean generateKeytool() {
/* 526 */       if (!KeytoolSelfSignedCertGenerator.isAvailable()) {
/* 527 */         SelfSignedCertificate.logger.debug("Not attempting to generate certificate with keytool because keytool is missing");
/* 528 */         return false;
/*     */       } 
/* 530 */       if (this.random != null) {
/* 531 */         SelfSignedCertificate.logger.debug("Not attempting to generate certificate with keytool because of explicitly set SecureRandom");
/*     */         
/* 533 */         return false;
/*     */       } 
/*     */       try {
/* 536 */         KeytoolSelfSignedCertGenerator.generate(this);
/* 537 */         return true;
/* 538 */       } catch (Throwable t) {
/* 539 */         SelfSignedCertificate.logger.debug("Failed to generate a self-signed X.509 certificate using keytool:", t);
/* 540 */         addFailure(t);
/* 541 */         return false;
/*     */       } 
/*     */     }
/*     */     
/*     */     boolean generateCertificateBuilder() {
/* 546 */       if (!CertificateBuilderCertGenerator.isAvailable()) {
/* 547 */         SelfSignedCertificate.logger.debug("Not attempting to generate a certificate with CertificateBuilder because it's not available on the classpath");
/*     */         
/* 549 */         return false;
/*     */       } 
/*     */       try {
/* 552 */         CertificateBuilderCertGenerator.generate(this);
/* 553 */         return true;
/* 554 */       } catch (CertificateException ce) {
/* 555 */         SelfSignedCertificate.logger.debug(ce);
/* 556 */         addFailure(ce);
/* 557 */       } catch (Exception e) {
/* 558 */         String msg = "Failed to generate a self-signed X.509 certificate using CertificateBuilder:";
/* 559 */         SelfSignedCertificate.logger.debug(msg, e);
/* 560 */         addFailure(new CertificateException(msg, e));
/*     */       } 
/* 562 */       return false;
/*     */     }
/*     */     
/*     */     boolean generateSunMiscSecurity() {
/* 566 */       generateKeyPairLocally();
/*     */       
/*     */       try {
/* 569 */         this.paths = OpenJdkSelfSignedCertGenerator.generate(this.fqdn, this.keypair, 
/* 570 */             randomOrDefault(), this.notBefore, this.notAfter, this.algorithm);
/* 571 */         return true;
/* 572 */       } catch (Throwable t2) {
/* 573 */         SelfSignedCertificate.logger.debug("Failed to generate a self-signed X.509 certificate using sun.security.x509:", t2);
/* 574 */         CertificateException certificateException = new CertificateException("No provider succeeded to generate a self-signed certificate. See debug log for the root cause.", t2);
/*     */ 
/*     */         
/* 577 */         addFailure(certificateException);
/* 578 */         return false;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public SelfSignedCertificate build() throws CertificateException {
/* 589 */       return new SelfSignedCertificate(this);
/*     */     }
/*     */     
/*     */     private Builder() {}
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ss\\util\SelfSignedCertificate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */