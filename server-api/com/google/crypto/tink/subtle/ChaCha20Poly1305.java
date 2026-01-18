/*     */ package com.google.crypto.tink.subtle;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.Aead;
/*     */ import com.google.crypto.tink.InsecureSecretKeyAccess;
/*     */ import com.google.crypto.tink.aead.ChaCha20Poly1305Key;
/*     */ import com.google.crypto.tink.aead.internal.InsecureNonceChaCha20Poly1305;
/*     */ import com.google.crypto.tink.internal.Util;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ChaCha20Poly1305
/*     */   implements Aead
/*     */ {
/*     */   private final InsecureNonceChaCha20Poly1305 cipher;
/*     */   private final byte[] outputPrefix;
/*     */   
/*     */   private ChaCha20Poly1305(byte[] key, byte[] outputPrefix) throws GeneralSecurityException {
/*  43 */     this.cipher = new InsecureNonceChaCha20Poly1305(key);
/*  44 */     this.outputPrefix = outputPrefix;
/*     */   }
/*     */   
/*     */   public ChaCha20Poly1305(byte[] key) throws GeneralSecurityException {
/*  48 */     this(key, new byte[0]);
/*     */   }
/*     */   
/*     */   @AccessesPartialKey
/*     */   public static Aead create(ChaCha20Poly1305Key key) throws GeneralSecurityException {
/*  53 */     return new ChaCha20Poly1305(key
/*  54 */         .getKeyBytes().toByteArray(InsecureSecretKeyAccess.get()), key
/*  55 */         .getOutputPrefix().toByteArray());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] rawEncrypt(byte[] plaintext, byte[] associatedData) throws GeneralSecurityException {
/*  61 */     ByteBuffer output = ByteBuffer.allocate(12 + plaintext.length + 16);
/*     */     
/*  63 */     byte[] nonce = Random.randBytes(12);
/*  64 */     output.put(nonce);
/*  65 */     this.cipher.encrypt(output, nonce, plaintext, associatedData);
/*  66 */     return output.array();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] encrypt(byte[] plaintext, byte[] associatedData) throws GeneralSecurityException {
/*  72 */     byte[] ciphertext = rawEncrypt(plaintext, associatedData);
/*  73 */     if (this.outputPrefix.length == 0) {
/*  74 */       return ciphertext;
/*     */     }
/*  76 */     return Bytes.concat(new byte[][] { this.outputPrefix, ciphertext });
/*     */   }
/*     */ 
/*     */   
/*     */   private byte[] rawDecrypt(byte[] ciphertext, byte[] associatedData) throws GeneralSecurityException {
/*  81 */     if (ciphertext.length < 28) {
/*  82 */       throw new GeneralSecurityException("ciphertext too short");
/*     */     }
/*  84 */     byte[] nonce = Arrays.copyOf(ciphertext, 12);
/*     */     
/*  86 */     ByteBuffer rawCiphertext = ByteBuffer.wrap(ciphertext, 12, ciphertext.length - 12);
/*     */ 
/*     */ 
/*     */     
/*  90 */     return this.cipher.decrypt(rawCiphertext, nonce, associatedData);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] decrypt(byte[] ciphertext, byte[] associatedData) throws GeneralSecurityException {
/*  96 */     if (this.outputPrefix.length == 0) {
/*  97 */       return rawDecrypt(ciphertext, associatedData);
/*     */     }
/*  99 */     if (!Util.isPrefix(this.outputPrefix, ciphertext)) {
/* 100 */       throw new GeneralSecurityException("Decryption failed (OutputPrefix mismatch).");
/*     */     }
/*     */     
/* 103 */     byte[] copiedCiphertext = Arrays.copyOfRange(ciphertext, this.outputPrefix.length, ciphertext.length);
/* 104 */     return rawDecrypt(copiedCiphertext, associatedData);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\subtle\ChaCha20Poly1305.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */