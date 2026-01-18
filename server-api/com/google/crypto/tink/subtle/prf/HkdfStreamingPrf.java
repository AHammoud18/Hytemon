/*     */ package com.google.crypto.tink.subtle.prf;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.InsecureSecretKeyAccess;
/*     */ import com.google.crypto.tink.internal.EnumTypeProtoConverter;
/*     */ import com.google.crypto.tink.prf.HkdfPrfKey;
/*     */ import com.google.crypto.tink.prf.HkdfPrfParameters;
/*     */ import com.google.crypto.tink.subtle.EngineFactory;
/*     */ import com.google.crypto.tink.subtle.Enums;
/*     */ import com.google.crypto.tink.util.Bytes;
/*     */ import com.google.errorprone.annotations.Immutable;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.Buffer;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.util.Arrays;
/*     */ import javax.crypto.Mac;
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
/*     */ @Immutable
/*     */ @AccessesPartialKey
/*     */ public class HkdfStreamingPrf
/*     */   implements StreamingPrf
/*     */ {
/*  46 */   private static final EnumTypeProtoConverter<Enums.HashType, HkdfPrfParameters.HashType> HASH_TYPE_CONVERTER = EnumTypeProtoConverter.builder()
/*  47 */     .add((Enum)Enums.HashType.SHA1, HkdfPrfParameters.HashType.SHA1)
/*  48 */     .add((Enum)Enums.HashType.SHA224, HkdfPrfParameters.HashType.SHA224)
/*  49 */     .add((Enum)Enums.HashType.SHA256, HkdfPrfParameters.HashType.SHA256)
/*  50 */     .add((Enum)Enums.HashType.SHA384, HkdfPrfParameters.HashType.SHA384)
/*  51 */     .add((Enum)Enums.HashType.SHA512, HkdfPrfParameters.HashType.SHA512)
/*  52 */     .build();
/*     */   
/*     */   private static String getJavaxHmacName(Enums.HashType hashType) throws GeneralSecurityException {
/*  55 */     switch (hashType) {
/*     */       case SHA1:
/*  57 */         return "HmacSha1";
/*     */       case SHA256:
/*  59 */         return "HmacSha256";
/*     */       case SHA384:
/*  61 */         return "HmacSha384";
/*     */       case SHA512:
/*  63 */         return "HmacSha512";
/*     */     } 
/*  65 */     throw new GeneralSecurityException("No getJavaxHmacName for given hash " + hashType + " known");
/*     */   }
/*     */ 
/*     */   
/*     */   private final Enums.HashType hashType;
/*     */   
/*     */   private final byte[] ikm;
/*     */   
/*     */   private final byte[] salt;
/*     */   
/*     */   private static Buffer toBuffer(ByteBuffer b) {
/*  76 */     return b;
/*     */   }
/*     */   
/*     */   public HkdfStreamingPrf(Enums.HashType hashType, byte[] ikm, byte[] salt) {
/*  80 */     this.hashType = hashType;
/*  81 */     this.ikm = Arrays.copyOf(ikm, ikm.length);
/*  82 */     this.salt = Arrays.copyOf(salt, salt.length);
/*     */   }
/*     */   
/*     */   public static StreamingPrf create(HkdfPrfKey key) throws GeneralSecurityException {
/*  86 */     Bytes saltFromKey = key.getParameters().getSalt();
/*  87 */     return new HkdfStreamingPrf((Enums.HashType)HASH_TYPE_CONVERTER
/*  88 */         .toProtoEnum(key.getParameters().getHashType()), key
/*  89 */         .getKeyBytes().toByteArray(InsecureSecretKeyAccess.get()), 
/*  90 */         (saltFromKey == null) ? new byte[0] : saltFromKey.toByteArray());
/*     */   }
/*     */ 
/*     */   
/*     */   private class HkdfInputStream
/*     */     extends InputStream
/*     */   {
/*     */     private final byte[] input;
/*     */     
/*     */     private Mac mac;
/*     */     private byte[] prk;
/*     */     private ByteBuffer buffer;
/*     */     private int ctr;
/*     */     
/*     */     public HkdfInputStream(byte[] input) {
/* 105 */       this.ctr = -1;
/* 106 */       this.input = Arrays.copyOf(input, input.length);
/*     */     }
/*     */ 
/*     */     
/*     */     private void initialize() throws GeneralSecurityException, IOException {
/*     */       try {
/* 112 */         this.mac = (Mac)EngineFactory.MAC.getInstance(HkdfStreamingPrf.getJavaxHmacName(HkdfStreamingPrf.this.hashType));
/* 113 */       } catch (GeneralSecurityException e) {
/* 114 */         throw new IOException("Creating HMac failed", e);
/*     */       } 
/* 116 */       if (HkdfStreamingPrf.this.salt == null || HkdfStreamingPrf.this.salt.length == 0) {
/*     */ 
/*     */         
/* 119 */         this.mac.init(new SecretKeySpec(new byte[this.mac.getMacLength()], HkdfStreamingPrf.getJavaxHmacName(HkdfStreamingPrf.this.hashType)));
/*     */       } else {
/* 121 */         this.mac.init(new SecretKeySpec(HkdfStreamingPrf.this.salt, HkdfStreamingPrf.getJavaxHmacName(HkdfStreamingPrf.this.hashType)));
/*     */       } 
/* 123 */       this.mac.update(HkdfStreamingPrf.this.ikm);
/* 124 */       this.prk = this.mac.doFinal();
/* 125 */       this.buffer = ByteBuffer.allocate(0);
/* 126 */       HkdfStreamingPrf.toBuffer(this.buffer).mark();
/* 127 */       this.ctr = 0;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void updateBuffer() throws GeneralSecurityException, IOException {
/* 133 */       this.mac.init(new SecretKeySpec(this.prk, HkdfStreamingPrf.getJavaxHmacName(HkdfStreamingPrf.this.hashType)));
/* 134 */       HkdfStreamingPrf.toBuffer(this.buffer).reset();
/* 135 */       this.mac.update(this.buffer);
/* 136 */       this.mac.update(this.input);
/* 137 */       this.ctr++;
/* 138 */       this.mac.update((byte)this.ctr);
/* 139 */       this.buffer = ByteBuffer.wrap(this.mac.doFinal());
/* 140 */       HkdfStreamingPrf.toBuffer(this.buffer).mark();
/*     */     }
/*     */ 
/*     */     
/*     */     public int read() throws IOException {
/* 145 */       byte[] oneByte = new byte[1];
/* 146 */       int ret = read(oneByte, 0, 1);
/* 147 */       if (ret == 1)
/* 148 */         return oneByte[0] & 0xFF; 
/* 149 */       if (ret == -1) {
/* 150 */         return ret;
/*     */       }
/* 152 */       throw new IOException("Reading failed");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int read(byte[] dst) throws IOException {
/* 158 */       return read(dst, 0, dst.length);
/*     */     }
/*     */ 
/*     */     
/*     */     public int read(byte[] b, int off, int len) throws IOException {
/* 163 */       int totalRead = 0;
/*     */       try {
/* 165 */         if (this.ctr == -1) {
/* 166 */           initialize();
/*     */         }
/*     */         
/* 169 */         while (totalRead < len) {
/*     */           
/* 171 */           if (!this.buffer.hasRemaining()) {
/* 172 */             if (this.ctr == 255)
/*     */             {
/* 174 */               return totalRead;
/*     */             }
/* 176 */             updateBuffer();
/*     */           } 
/*     */           
/* 179 */           int toRead = Math.min(len - totalRead, this.buffer.remaining());
/* 180 */           this.buffer.get(b, off, toRead);
/* 181 */           off += toRead;
/* 182 */           totalRead += toRead;
/*     */         } 
/* 184 */       } catch (GeneralSecurityException e) {
/* 185 */         this.mac = null;
/* 186 */         throw new IOException("HkdfInputStream failed", e);
/*     */       } 
/* 188 */       return totalRead;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InputStream computePrf(byte[] input) {
/* 208 */     return new HkdfInputStream(input);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\subtle\prf\HkdfStreamingPrf.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */