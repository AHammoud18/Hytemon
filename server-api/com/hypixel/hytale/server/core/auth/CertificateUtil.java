/*     */ package com.hypixel.hytale.server.core.auth;
/*     */ 
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.cert.CertificateEncodingException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.Base64;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CertificateUtil
/*     */ {
/*  19 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static String computeCertificateFingerprint(@Nonnull X509Certificate certificate) {
/*     */     try {
/*  31 */       MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
/*  32 */       byte[] certBytes = certificate.getEncoded();
/*  33 */       byte[] hash = sha256.digest(certBytes);
/*  34 */       return base64UrlEncode(hash);
/*  35 */     } catch (NoSuchAlgorithmException e) {
/*  36 */       ((HytaleLogger.Api)LOGGER.at(Level.SEVERE).withCause(e)).log("SHA-256 algorithm not available");
/*  37 */       return null;
/*  38 */     } catch (CertificateEncodingException e) {
/*  39 */       ((HytaleLogger.Api)LOGGER.at(Level.WARNING).withCause(e)).log("Failed to encode certificate");
/*  40 */       return null;
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
/*     */   public static boolean validateCertificateBinding(@Nullable String jwtFingerprint, @Nullable X509Certificate clientCert) {
/*  54 */     if (jwtFingerprint == null || jwtFingerprint.isEmpty()) {
/*  55 */       LOGGER.at(Level.WARNING).log("JWT missing certificate fingerprint (cnf.x5t#S256) - rejecting token");
/*  56 */       return false;
/*     */     } 
/*     */     
/*  59 */     if (clientCert == null) {
/*  60 */       LOGGER.at(Level.WARNING).log("No client certificate present in mTLS connection - rejecting token");
/*  61 */       return false;
/*     */     } 
/*     */ 
/*     */     
/*  65 */     String actualFingerprint = computeCertificateFingerprint(clientCert);
/*  66 */     if (actualFingerprint == null) {
/*  67 */       LOGGER.at(Level.WARNING).log("Failed to compute client certificate fingerprint");
/*  68 */       return false;
/*     */     } 
/*     */ 
/*     */     
/*  72 */     boolean matches = timingSafeEquals(jwtFingerprint, actualFingerprint);
/*  73 */     if (!matches) {
/*  74 */       LOGGER.at(Level.WARNING).log("Certificate fingerprint mismatch! JWT: %s, Actual: %s", jwtFingerprint, actualFingerprint);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/*  79 */       LOGGER.at(Level.INFO).log("Certificate binding validated successfully");
/*     */     } 
/*     */     
/*  82 */     return matches;
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
/*     */   public static boolean timingSafeEquals(String a, String b) {
/*  94 */     if (a == null || b == null) {
/*  95 */       return (a == b);
/*     */     }
/*     */ 
/*     */     
/*  99 */     byte[] aBytes = a.getBytes(StandardCharsets.UTF_8);
/* 100 */     byte[] bBytes = b.getBytes(StandardCharsets.UTF_8);
/* 101 */     return MessageDigest.isEqual(aBytes, bBytes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String base64UrlEncode(byte[] input) {
/* 112 */     String base64 = Base64.getEncoder().encodeToString(input);
/* 113 */     return base64.replace('+', '-').replace('/', '_').replace("=", "");
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\auth\CertificateUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */