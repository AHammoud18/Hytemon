/*     */ package com.google.crypto.tink.subtle;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.InsecureSecretKeyAccess;
/*     */ import com.google.crypto.tink.StreamingAead;
/*     */ import com.google.crypto.tink.streamingaead.AesGcmHkdfStreamingKey;
/*     */ import com.google.crypto.tink.streamingaead.AesGcmHkdfStreamingParameters;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.Buffer;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.channels.ReadableByteChannel;
/*     */ import java.nio.channels.SeekableByteChannel;
/*     */ import java.nio.channels.WritableByteChannel;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.util.Arrays;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.spec.GCMParameterSpec;
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
/*     */ @AccessesPartialKey
/*     */ public final class AesGcmHkdfStreaming
/*     */   extends NonceBasedStreamingAead
/*     */ {
/*     */   private static final int NONCE_SIZE_IN_BYTES = 12;
/*     */   private static final int NONCE_PREFIX_IN_BYTES = 7;
/*     */   private static final int TAG_SIZE_IN_BYTES = 16;
/*     */   private final int keySizeInBytes;
/*     */   private final int ciphertextSegmentSize;
/*     */   private final int plaintextSegmentSize;
/*     */   private final int firstSegmentOffset;
/*     */   private final String hkdfAlg;
/*     */   private final byte[] ikm;
/*     */   
/*     */   private static Buffer toBuffer(ByteBuffer b) {
/*  95 */     return b;
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
/*     */   public AesGcmHkdfStreaming(byte[] ikm, String hkdfAlg, int keySizeInBytes, int ciphertextSegmentSize, int firstSegmentOffset) throws InvalidAlgorithmParameterException {
/* 113 */     if (ikm.length < 16 || ikm.length < keySizeInBytes) {
/* 114 */       throw new InvalidAlgorithmParameterException("ikm too short, must be >= " + 
/* 115 */           Math.max(16, keySizeInBytes));
/*     */     }
/* 117 */     Validators.validateAesKeySize(keySizeInBytes);
/* 118 */     if (ciphertextSegmentSize <= firstSegmentOffset + getHeaderLength() + 16) {
/* 119 */       throw new InvalidAlgorithmParameterException("ciphertextSegmentSize too small");
/*     */     }
/* 121 */     this.ikm = Arrays.copyOf(ikm, ikm.length);
/* 122 */     this.hkdfAlg = hkdfAlg;
/* 123 */     this.keySizeInBytes = keySizeInBytes;
/* 124 */     this.ciphertextSegmentSize = ciphertextSegmentSize;
/* 125 */     this.firstSegmentOffset = firstSegmentOffset;
/* 126 */     this.plaintextSegmentSize = ciphertextSegmentSize - 16;
/*     */   }
/*     */ 
/*     */   
/*     */   private AesGcmHkdfStreaming(AesGcmHkdfStreamingKey key) throws GeneralSecurityException {
/* 131 */     this.ikm = key.getInitialKeyMaterial().toByteArray(InsecureSecretKeyAccess.get());
/* 132 */     String hkdfAlgString = "";
/* 133 */     if (key.getParameters().getHkdfHashType().equals(AesGcmHkdfStreamingParameters.HashType.SHA1)) {
/* 134 */       hkdfAlgString = "HmacSha1";
/* 135 */     } else if (key.getParameters().getHkdfHashType().equals(AesGcmHkdfStreamingParameters.HashType.SHA256)) {
/* 136 */       hkdfAlgString = "HmacSha256";
/* 137 */     } else if (key.getParameters().getHkdfHashType().equals(AesGcmHkdfStreamingParameters.HashType.SHA512)) {
/* 138 */       hkdfAlgString = "HmacSha512";
/*     */     } else {
/* 140 */       throw new GeneralSecurityException("Unknown HKDF algorithm " + key
/* 141 */           .getParameters().getHkdfHashType());
/*     */     } 
/* 143 */     this.hkdfAlg = hkdfAlgString;
/* 144 */     this.keySizeInBytes = key.getParameters().getDerivedAesGcmKeySizeBytes();
/* 145 */     this.ciphertextSegmentSize = key.getParameters().getCiphertextSegmentSizeBytes();
/*     */     
/* 147 */     this.firstSegmentOffset = 0;
/* 148 */     this.plaintextSegmentSize = this.ciphertextSegmentSize - 16;
/*     */   }
/*     */ 
/*     */   
/*     */   public static StreamingAead create(AesGcmHkdfStreamingKey key) throws GeneralSecurityException {
/* 153 */     return new AesGcmHkdfStreaming(key);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AesGcmHkdfStreamEncrypter newStreamSegmentEncrypter(byte[] aad) throws GeneralSecurityException {
/* 159 */     return new AesGcmHkdfStreamEncrypter(aad);
/*     */   }
/*     */ 
/*     */   
/*     */   public AesGcmHkdfStreamDecrypter newStreamSegmentDecrypter() throws GeneralSecurityException {
/* 164 */     return new AesGcmHkdfStreamDecrypter();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPlaintextSegmentSize() {
/* 169 */     return this.plaintextSegmentSize;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCiphertextSegmentSize() {
/* 174 */     return this.ciphertextSegmentSize;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHeaderLength() {
/* 179 */     return 1 + this.keySizeInBytes + 7;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCiphertextOffset() {
/* 184 */     return getHeaderLength() + this.firstSegmentOffset;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCiphertextOverhead() {
/* 189 */     return 16;
/*     */   }
/*     */   
/*     */   public int getFirstSegmentOffset() {
/* 193 */     return this.firstSegmentOffset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long expectedCiphertextSize(long plaintextSize) {
/* 201 */     long offset = getCiphertextOffset();
/* 202 */     long fullSegments = (plaintextSize + offset) / this.plaintextSegmentSize;
/* 203 */     long ciphertextSize = fullSegments * this.ciphertextSegmentSize;
/* 204 */     long lastSegmentSize = (plaintextSize + offset) % this.plaintextSegmentSize;
/* 205 */     if (lastSegmentSize > 0L) {
/* 206 */       ciphertextSize += lastSegmentSize + 16L;
/*     */     }
/* 208 */     return ciphertextSize;
/*     */   }
/*     */   
/*     */   private static Cipher cipherInstance() throws GeneralSecurityException {
/* 212 */     return EngineFactory.CIPHER.getInstance("AES/GCM/NoPadding");
/*     */   }
/*     */   
/*     */   private byte[] randomSalt() {
/* 216 */     return Random.randBytes(this.keySizeInBytes);
/*     */   }
/*     */ 
/*     */   
/*     */   private static GCMParameterSpec paramsForSegment(byte[] prefix, long segmentNr, boolean last) throws GeneralSecurityException {
/* 221 */     ByteBuffer nonce = ByteBuffer.allocate(12);
/* 222 */     nonce.order(ByteOrder.BIG_ENDIAN);
/* 223 */     nonce.put(prefix);
/* 224 */     SubtleUtil.putAsUnsigedInt(nonce, segmentNr);
/* 225 */     nonce.put((byte)(last ? 1 : 0));
/* 226 */     return new GCMParameterSpec(128, nonce.array());
/*     */   }
/*     */   
/*     */   private static byte[] randomNonce() {
/* 230 */     return Random.randBytes(7);
/*     */   }
/*     */   
/*     */   private SecretKeySpec deriveKeySpec(byte[] salt, byte[] aad) throws GeneralSecurityException {
/* 234 */     byte[] key = Hkdf.computeHkdf(this.hkdfAlg, this.ikm, salt, aad, this.keySizeInBytes);
/* 235 */     return new SecretKeySpec(key, "AES");
/*     */   }
/*     */ 
/*     */   
/*     */   class AesGcmHkdfStreamEncrypter
/*     */     implements StreamSegmentEncrypter
/*     */   {
/*     */     private final SecretKeySpec keySpec;
/*     */     
/*     */     private final Cipher cipher;
/*     */     
/*     */     private final byte[] noncePrefix;
/*     */     
/*     */     private final ByteBuffer header;
/* 249 */     private long encryptedSegments = 0L;
/*     */     
/*     */     public AesGcmHkdfStreamEncrypter(byte[] aad) throws GeneralSecurityException {
/* 252 */       this.cipher = AesGcmHkdfStreaming.cipherInstance();
/* 253 */       this.encryptedSegments = 0L;
/* 254 */       byte[] salt = AesGcmHkdfStreaming.this.randomSalt();
/* 255 */       this.noncePrefix = AesGcmHkdfStreaming.randomNonce();
/* 256 */       this.header = ByteBuffer.allocate(AesGcmHkdfStreaming.this.getHeaderLength());
/* 257 */       this.header.put((byte)AesGcmHkdfStreaming.this.getHeaderLength());
/* 258 */       this.header.put(salt);
/* 259 */       this.header.put(this.noncePrefix);
/* 260 */       AesGcmHkdfStreaming.toBuffer(this.header).flip();
/* 261 */       this.keySpec = AesGcmHkdfStreaming.this.deriveKeySpec(salt, aad);
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteBuffer getHeader() {
/* 266 */       return this.header.asReadOnlyBuffer();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public synchronized void encryptSegment(ByteBuffer plaintext, boolean isLastSegment, ByteBuffer ciphertext) throws GeneralSecurityException {
/* 277 */       this.cipher.init(1, this.keySpec, AesGcmHkdfStreaming
/*     */ 
/*     */           
/* 280 */           .paramsForSegment(this.noncePrefix, this.encryptedSegments, isLastSegment));
/* 281 */       this.encryptedSegments++;
/* 282 */       this.cipher.doFinal(plaintext, ciphertext);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public synchronized void encryptSegment(ByteBuffer part1, ByteBuffer part2, boolean isLastSegment, ByteBuffer ciphertext) throws GeneralSecurityException {
/* 293 */       this.cipher.init(1, this.keySpec, AesGcmHkdfStreaming
/*     */ 
/*     */           
/* 296 */           .paramsForSegment(this.noncePrefix, this.encryptedSegments, isLastSegment));
/* 297 */       this.encryptedSegments++;
/*     */ 
/*     */       
/* 300 */       if (part2.hasRemaining()) {
/* 301 */         this.cipher.update(part1, ciphertext);
/* 302 */         this.cipher.doFinal(part2, ciphertext);
/*     */       } else {
/* 304 */         this.cipher.doFinal(part1, ciphertext);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   class AesGcmHkdfStreamDecrypter
/*     */     implements StreamSegmentDecrypter
/*     */   {
/*     */     private SecretKeySpec keySpec;
/*     */     
/*     */     private Cipher cipher;
/*     */     private byte[] noncePrefix;
/*     */     
/*     */     public synchronized void init(ByteBuffer header, byte[] aad) throws GeneralSecurityException {
/* 319 */       if (header.remaining() != AesGcmHkdfStreaming.this.getHeaderLength()) {
/* 320 */         throw new InvalidAlgorithmParameterException("Invalid header length");
/*     */       }
/* 322 */       byte firstByte = header.get();
/* 323 */       if (firstByte != AesGcmHkdfStreaming.this.getHeaderLength())
/*     */       {
/*     */ 
/*     */         
/* 327 */         throw new GeneralSecurityException("Invalid ciphertext");
/*     */       }
/* 329 */       this.noncePrefix = new byte[7];
/* 330 */       byte[] salt = new byte[AesGcmHkdfStreaming.this.keySizeInBytes];
/* 331 */       header.get(salt);
/* 332 */       header.get(this.noncePrefix);
/* 333 */       this.keySpec = AesGcmHkdfStreaming.this.deriveKeySpec(salt, aad);
/* 334 */       this.cipher = AesGcmHkdfStreaming.cipherInstance();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public synchronized void decryptSegment(ByteBuffer ciphertext, int segmentNr, boolean isLastSegment, ByteBuffer plaintext) throws GeneralSecurityException {
/* 341 */       GCMParameterSpec params = AesGcmHkdfStreaming.paramsForSegment(this.noncePrefix, segmentNr, isLastSegment);
/* 342 */       this.cipher.init(2, this.keySpec, params);
/* 343 */       this.cipher.doFinal(ciphertext, plaintext);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\subtle\AesGcmHkdfStreaming.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */