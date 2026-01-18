/*     */ package com.google.crypto.tink.internal;
/*     */ 
/*     */ import com.google.crypto.tink.SecretKeyAccess;
/*     */ import com.google.crypto.tink.util.Bytes;
/*     */ import com.google.crypto.tink.util.SecretBytes;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.charset.Charset;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Util
/*     */ {
/*  32 */   public static final Charset UTF_8 = Charset.forName("UTF-8");
/*     */ 
/*     */   
/*     */   public static int randKeyId() {
/*  36 */     int result = 0;
/*  37 */     while (result == 0) {
/*  38 */       byte[] rand = Random.randBytes(4);
/*  39 */       result = (rand[0] & 0xFF) << 24 | (rand[1] & 0xFF) << 16 | (rand[2] & 0xFF) << 8 | rand[3] & 0xFF;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  45 */     return result;
/*     */   }
/*     */   
/*     */   private static final byte toByteFromPrintableAscii(char c) {
/*  49 */     if (c < '!' || c > '~') {
/*  50 */       throw new TinkBugException("Not a printable ASCII character: " + c);
/*     */     }
/*  52 */     return (byte)c;
/*     */   }
/*     */ 
/*     */   
/*     */   private static final byte checkedToByteFromPrintableAscii(char c) throws GeneralSecurityException {
/*  57 */     if (c < '!' || c > '~') {
/*  58 */       throw new GeneralSecurityException("Not a printable ASCII character: " + c);
/*     */     }
/*  60 */     return (byte)c;
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
/*     */   public static final Bytes toBytesFromPrintableAscii(String s) {
/*  73 */     byte[] result = new byte[s.length()];
/*  74 */     for (int i = 0; i < s.length(); i++) {
/*  75 */       result[i] = toByteFromPrintableAscii(s.charAt(i));
/*     */     }
/*  77 */     return Bytes.copyFrom(result);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final Bytes checkedToBytesFromPrintableAscii(String s) throws GeneralSecurityException {
/*  88 */     byte[] result = new byte[s.length()];
/*  89 */     for (int i = 0; i < s.length(); i++) {
/*  90 */       result[i] = checkedToByteFromPrintableAscii(s.charAt(i));
/*     */     }
/*  92 */     return Bytes.copyFrom(result);
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
/*     */   public static boolean isAndroid() {
/* 107 */     return Objects.equals(System.getProperty("java.vendor"), "The Android Project");
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static Integer getAndroidApiLevel() {
/* 113 */     if (!isAndroid()) {
/* 114 */       return null;
/*     */     }
/* 116 */     return BuildDispatchedCode.getApiLevel();
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isPrefix(byte[] prefix, byte[] complete) {
/* 121 */     if (complete.length < prefix.length) {
/* 122 */       return false;
/*     */     }
/* 124 */     for (int i = 0; i < prefix.length; i++) {
/* 125 */       if (complete[i] != prefix[i]) {
/* 126 */         return false;
/*     */       }
/*     */     } 
/* 129 */     return true;
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
/*     */   public static SecretBytes readIntoSecretBytes(InputStream input, int length, SecretKeyAccess access) throws GeneralSecurityException {
/* 144 */     byte[] output = new byte[length];
/*     */     try {
/* 146 */       int len = output.length;
/*     */       
/* 148 */       int readTotal = 0;
/* 149 */       while (readTotal < len) {
/* 150 */         int read = input.read(output, readTotal, len - readTotal);
/* 151 */         if (read == -1) {
/* 152 */           throw new GeneralSecurityException("Not enough pseudorandomness provided");
/*     */         }
/* 154 */         readTotal += read;
/*     */       } 
/* 156 */     } catch (IOException e) {
/* 157 */       throw new GeneralSecurityException("Reading pseudorandomness failed");
/*     */     } 
/* 159 */     return SecretBytes.copyFrom(output, access);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\internal\Util.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */