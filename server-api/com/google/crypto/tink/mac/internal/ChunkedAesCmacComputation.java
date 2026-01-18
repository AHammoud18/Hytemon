/*     */ package com.google.crypto.tink.mac.internal;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.InsecureSecretKeyAccess;
/*     */ import com.google.crypto.tink.mac.AesCmacKey;
/*     */ import com.google.crypto.tink.mac.AesCmacParameters;
/*     */ import com.google.crypto.tink.mac.ChunkedMacComputation;
/*     */ import com.google.crypto.tink.subtle.Bytes;
/*     */ import com.google.crypto.tink.subtle.EngineFactory;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.util.Arrays;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.spec.SecretKeySpec;
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
/*     */ @AccessesPartialKey
/*     */ final class ChunkedAesCmacComputation
/*     */   implements ChunkedMacComputation
/*     */ {
/*  42 */   private static final byte[] formatVersion = new byte[] { 0 };
/*     */ 
/*     */   
/*     */   private final Cipher aes;
/*     */ 
/*     */   
/*     */   private final AesCmacKey key;
/*     */   
/*     */   private final byte[] subKey1;
/*     */   
/*     */   private final byte[] subKey2;
/*     */   
/*     */   private final ByteBuffer localStash;
/*     */   
/*     */   private final byte[] x;
/*     */   
/*     */   private final byte[] y;
/*     */   
/*     */   private final byte[] dataBlock;
/*     */   
/*     */   private boolean finalized = false;
/*     */ 
/*     */   
/*     */   ChunkedAesCmacComputation(AesCmacKey key) throws GeneralSecurityException {
/*  66 */     this.key = key;
/*  67 */     this.aes = (Cipher)EngineFactory.CIPHER.getInstance("AES/ECB/NoPadding");
/*  68 */     this.aes.init(1, new SecretKeySpec(this.key
/*     */           
/*  70 */           .getAesKey().toByteArray(InsecureSecretKeyAccess.get()), "AES"));
/*     */ 
/*     */     
/*  73 */     byte[] zeroes = new byte[16];
/*     */ 
/*     */     
/*  76 */     byte[] l = this.aes.doFinal(zeroes);
/*  77 */     this.subKey1 = AesUtil.dbl(l);
/*  78 */     this.subKey2 = AesUtil.dbl(this.subKey1);
/*     */     
/*  80 */     this.localStash = ByteBuffer.allocate(16);
/*  81 */     this.x = new byte[16];
/*  82 */     this.y = new byte[16];
/*  83 */     this.dataBlock = new byte[16];
/*     */   }
/*     */   
/*     */   private void munch(ByteBuffer data) throws GeneralSecurityException {
/*  87 */     data.get(this.dataBlock);
/*  88 */     for (int i = 0; i < 16; i++) {
/*  89 */       this.y[i] = (byte)(this.x[i] ^ this.dataBlock[i]);
/*     */     }
/*  91 */     this.aes.doFinal(this.y, 0, 16, this.x);
/*     */   }
/*     */ 
/*     */   
/*     */   public void update(ByteBuffer data) throws GeneralSecurityException {
/*  96 */     if (this.finalized) {
/*  97 */       throw new IllegalStateException("Can not update after computing the MAC tag. Please create a new object.");
/*     */     }
/*     */ 
/*     */     
/* 101 */     if (this.localStash.remaining() != 16) {
/*     */       
/* 103 */       int bytesToCopy = Math.min(this.localStash.remaining(), data.remaining());
/* 104 */       for (int i = 0; i < bytesToCopy; i++) {
/* 105 */         this.localStash.put(data.get());
/*     */       }
/*     */     } 
/* 108 */     if (this.localStash.remaining() == 0 && data.remaining() > 0) {
/*     */       
/* 110 */       this.localStash.rewind();
/* 111 */       munch(this.localStash);
/* 112 */       this.localStash.rewind();
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 119 */     while (data.remaining() > 16) {
/* 120 */       munch(data);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 126 */     this.localStash.put(data);
/*     */   }
/*     */   
/*     */   public byte[] computeMac() throws GeneralSecurityException {
/*     */     byte[] mLast;
/* 131 */     if (this.finalized) {
/* 132 */       throw new IllegalStateException("Can not compute after computing the MAC tag. Please create a new object.");
/*     */     }
/*     */     
/* 135 */     if (this.key.getParameters().getVariant() == AesCmacParameters.Variant.LEGACY) {
/* 136 */       update(ByteBuffer.wrap(formatVersion));
/*     */     }
/* 138 */     this.finalized = true;
/*     */ 
/*     */     
/* 141 */     if (this.localStash.remaining() > 0) {
/*     */       
/* 143 */       byte[] lastChunkToPad = Arrays.copyOf(this.localStash.array(), this.localStash.position());
/* 144 */       mLast = Bytes.xor(AesUtil.cmacPad(lastChunkToPad), this.subKey2);
/*     */     } else {
/*     */       
/* 147 */       mLast = Bytes.xor(this.localStash.array(), 0, this.subKey1, 0, 16);
/*     */     } 
/*     */     
/* 150 */     return Bytes.concat(new byte[][] { this.key
/* 151 */           .getOutputPrefix().toByteArray(), 
/* 152 */           Arrays.copyOf(this.aes
/* 153 */             .doFinal(Bytes.xor(mLast, this.x)), this.key
/* 154 */             .getParameters().getCryptographicTagSizeBytes()) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\mac\internal\ChunkedAesCmacComputation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */