/*     */ package com.google.crypto.tink.subtle;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.Aead;
/*     */ import com.google.crypto.tink.InsecureSecretKeyAccess;
/*     */ import com.google.crypto.tink.aead.XChaCha20Poly1305Key;
/*     */ import com.google.crypto.tink.aead.internal.InsecureNonceXChaCha20Poly1305;
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
/*     */ public final class XChaCha20Poly1305
/*     */   implements Aead
/*     */ {
/*     */   private final InsecureNonceXChaCha20Poly1305 cipher;
/*     */   private final byte[] outputPrefix;
/*     */   
/*     */   private XChaCha20Poly1305(byte[] key, byte[] outputPrefix) throws GeneralSecurityException {
/*  41 */     this.cipher = new InsecureNonceXChaCha20Poly1305(key);
/*  42 */     this.outputPrefix = outputPrefix;
/*     */   }
/*     */   
/*     */   public XChaCha20Poly1305(byte[] key) throws GeneralSecurityException {
/*  46 */     this(key, new byte[0]);
/*     */   }
/*     */   
/*     */   @AccessesPartialKey
/*     */   public static Aead create(XChaCha20Poly1305Key key) throws GeneralSecurityException {
/*  51 */     return new XChaCha20Poly1305(key
/*  52 */         .getKeyBytes().toByteArray(InsecureSecretKeyAccess.get()), key
/*  53 */         .getOutputPrefix().toByteArray());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] rawEncrypt(byte[] plaintext, byte[] associatedData) throws GeneralSecurityException {
/*  59 */     ByteBuffer output = ByteBuffer.allocate(24 + plaintext.length + 16);
/*     */     
/*  61 */     byte[] nonce = Random.randBytes(24);
/*  62 */     output.put(nonce);
/*  63 */     this.cipher.encrypt(output, nonce, plaintext, associatedData);
/*  64 */     return output.array();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] encrypt(byte[] plaintext, byte[] associatedData) throws GeneralSecurityException {
/*  70 */     byte[] ciphertext = rawEncrypt(plaintext, associatedData);
/*  71 */     if (this.outputPrefix.length == 0) {
/*  72 */       return ciphertext;
/*     */     }
/*  74 */     return Bytes.concat(new byte[][] { this.outputPrefix, ciphertext });
/*     */   }
/*     */ 
/*     */   
/*     */   private byte[] rawDecrypt(byte[] ciphertext, byte[] associatedData) throws GeneralSecurityException {
/*  79 */     if (ciphertext.length < 40) {
/*  80 */       throw new GeneralSecurityException("ciphertext too short");
/*     */     }
/*  82 */     byte[] nonce = Arrays.copyOf(ciphertext, 24);
/*     */     
/*  84 */     ByteBuffer rawCiphertext = ByteBuffer.wrap(ciphertext, 24, ciphertext.length - 24);
/*     */ 
/*     */ 
/*     */     
/*  88 */     return this.cipher.decrypt(rawCiphertext, nonce, associatedData);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] decrypt(byte[] ciphertext, byte[] associatedData) throws GeneralSecurityException {
/*  94 */     if (this.outputPrefix.length == 0) {
/*  95 */       return rawDecrypt(ciphertext, associatedData);
/*     */     }
/*  97 */     if (!Util.isPrefix(this.outputPrefix, ciphertext)) {
/*  98 */       throw new GeneralSecurityException("Decryption failed (OutputPrefix mismatch).");
/*     */     }
/*     */     
/* 101 */     byte[] copiedCiphertext = Arrays.copyOfRange(ciphertext, this.outputPrefix.length, ciphertext.length);
/* 102 */     return rawDecrypt(copiedCiphertext, associatedData);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\subtle\XChaCha20Poly1305.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */