/*     */ package com.google.crypto.tink.subtle;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.InsecureSecretKeyAccess;
/*     */ import com.google.crypto.tink.StreamingAead;
/*     */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*     */ import com.google.crypto.tink.streamingaead.AesCtrHmacStreamingKey;
/*     */ import com.google.crypto.tink.streamingaead.AesCtrHmacStreamingParameters;
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
/*     */ import javax.crypto.Mac;
/*     */ import javax.crypto.spec.IvParameterSpec;
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
/*     */ @AccessesPartialKey
/*     */ public final class AesCtrHmacStreaming
/*     */   extends NonceBasedStreamingAead
/*     */ {
/*  69 */   public static final TinkFipsUtil.AlgorithmFipsCompatibility FIPS = TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_NOT_FIPS;
/*     */ 
/*     */   
/*     */   private static final int NONCE_SIZE_IN_BYTES = 16;
/*     */ 
/*     */   
/*     */   private static final int NONCE_PREFIX_IN_BYTES = 7;
/*     */ 
/*     */   
/*     */   private static final int HMAC_KEY_SIZE_IN_BYTES = 32;
/*     */   
/*     */   private final int keySizeInBytes;
/*     */   
/*     */   private final String tagAlgo;
/*     */   
/*     */   private final int tagSizeInBytes;
/*     */   
/*     */   private final int ciphertextSegmentSize;
/*     */   
/*     */   private final int plaintextSegmentSize;
/*     */   
/*     */   private final int firstSegmentOffset;
/*     */   
/*     */   private final String hkdfAlgo;
/*     */   
/*     */   private final byte[] ikm;
/*     */ 
/*     */   
/*     */   private static Buffer toBuffer(ByteBuffer b) {
/*  98 */     return b;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AesCtrHmacStreaming(byte[] ikm, String hkdfAlgo, int keySizeInBytes, String tagAlgo, int tagSizeInBytes, int ciphertextSegmentSize, int firstSegmentOffset) throws GeneralSecurityException {
/* 125 */     if (!FIPS.isCompatible()) {
/* 126 */       throw new GeneralSecurityException("Can not use AES-CTR-HMAC streaming in FIPS-mode.");
/*     */     }
/* 128 */     validateParameters(ikm.length, keySizeInBytes, tagAlgo, tagSizeInBytes, ciphertextSegmentSize, firstSegmentOffset);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 135 */     this.ikm = Arrays.copyOf(ikm, ikm.length);
/* 136 */     this.hkdfAlgo = hkdfAlgo;
/* 137 */     this.keySizeInBytes = keySizeInBytes;
/* 138 */     this.tagAlgo = tagAlgo;
/* 139 */     this.tagSizeInBytes = tagSizeInBytes;
/* 140 */     this.ciphertextSegmentSize = ciphertextSegmentSize;
/* 141 */     this.firstSegmentOffset = firstSegmentOffset;
/* 142 */     this.plaintextSegmentSize = ciphertextSegmentSize - tagSizeInBytes;
/*     */   }
/*     */   
/*     */   private AesCtrHmacStreaming(AesCtrHmacStreamingKey key) throws GeneralSecurityException {
/* 146 */     if (!FIPS.isCompatible()) {
/* 147 */       throw new GeneralSecurityException("Can not use AES-CTR-HMAC streaming in FIPS-mode.");
/*     */     }
/* 149 */     this.ikm = key.getInitialKeyMaterial().toByteArray(InsecureSecretKeyAccess.get());
/* 150 */     String hkdfAlgString = "";
/* 151 */     if (key.getParameters().getHkdfHashType().equals(AesCtrHmacStreamingParameters.HashType.SHA1)) {
/* 152 */       hkdfAlgString = "HmacSha1";
/* 153 */     } else if (key.getParameters().getHkdfHashType().equals(AesCtrHmacStreamingParameters.HashType.SHA256)) {
/* 154 */       hkdfAlgString = "HmacSha256";
/* 155 */     } else if (key.getParameters().getHkdfHashType().equals(AesCtrHmacStreamingParameters.HashType.SHA512)) {
/* 156 */       hkdfAlgString = "HmacSha512";
/*     */     } 
/* 158 */     this.hkdfAlgo = hkdfAlgString;
/* 159 */     this.keySizeInBytes = key.getParameters().getDerivedKeySizeBytes();
/* 160 */     String tagAlgString = "";
/* 161 */     if (key.getParameters().getHmacHashType().equals(AesCtrHmacStreamingParameters.HashType.SHA1)) {
/* 162 */       tagAlgString = "HmacSha1";
/* 163 */     } else if (key.getParameters().getHmacHashType().equals(AesCtrHmacStreamingParameters.HashType.SHA256)) {
/* 164 */       tagAlgString = "HmacSha256";
/* 165 */     } else if (key.getParameters().getHmacHashType().equals(AesCtrHmacStreamingParameters.HashType.SHA512)) {
/* 166 */       tagAlgString = "HmacSha512";
/*     */     } 
/* 168 */     this.tagAlgo = tagAlgString;
/* 169 */     this.tagSizeInBytes = key.getParameters().getHmacTagSizeBytes();
/* 170 */     this.ciphertextSegmentSize = key.getParameters().getCiphertextSegmentSizeBytes();
/*     */     
/* 172 */     this.firstSegmentOffset = 0;
/* 173 */     this.plaintextSegmentSize = this.ciphertextSegmentSize - this.tagSizeInBytes;
/*     */   }
/*     */   
/*     */   public static StreamingAead create(AesCtrHmacStreamingKey key) throws GeneralSecurityException {
/* 177 */     return new AesCtrHmacStreaming(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void validateParameters(int ikmSize, int keySizeInBytes, String tagAlgo, int tagSizeInBytes, int ciphertextSegmentSize, int firstSegmentOffset) throws InvalidAlgorithmParameterException {
/* 188 */     if (ikmSize < 16 || ikmSize < keySizeInBytes) {
/* 189 */       throw new InvalidAlgorithmParameterException("ikm too short, must be >= " + 
/* 190 */           Math.max(16, keySizeInBytes));
/*     */     }
/* 192 */     if (firstSegmentOffset < 0) {
/* 193 */       throw new InvalidAlgorithmParameterException("firstSegmentOffset must not be negative");
/*     */     }
/* 195 */     Validators.validateAesKeySize(keySizeInBytes);
/* 196 */     if (tagSizeInBytes < 10) {
/* 197 */       throw new InvalidAlgorithmParameterException("tag size too small " + tagSizeInBytes);
/*     */     }
/* 199 */     if ((tagAlgo.equals("HmacSha1") && tagSizeInBytes > 20) || (tagAlgo
/* 200 */       .equals("HmacSha256") && tagSizeInBytes > 32) || (tagAlgo
/* 201 */       .equals("HmacSha512") && tagSizeInBytes > 64)) {
/* 202 */       throw new InvalidAlgorithmParameterException("tag size too big");
/*     */     }
/*     */     
/* 205 */     int firstPlaintextSegment = ciphertextSegmentSize - firstSegmentOffset - tagSizeInBytes - keySizeInBytes - 7 - 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 212 */     if (firstPlaintextSegment <= 0) {
/* 213 */       throw new InvalidAlgorithmParameterException("ciphertextSegmentSize too small");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AesCtrHmacStreamEncrypter newStreamSegmentEncrypter(byte[] aad) throws GeneralSecurityException {
/* 220 */     return new AesCtrHmacStreamEncrypter(aad);
/*     */   }
/*     */ 
/*     */   
/*     */   public AesCtrHmacStreamDecrypter newStreamSegmentDecrypter() throws GeneralSecurityException {
/* 225 */     return new AesCtrHmacStreamDecrypter();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCiphertextSegmentSize() {
/* 230 */     return this.ciphertextSegmentSize;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPlaintextSegmentSize() {
/* 235 */     return this.plaintextSegmentSize;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHeaderLength() {
/* 240 */     return 1 + this.keySizeInBytes + 7;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCiphertextOffset() {
/* 245 */     return getHeaderLength() + this.firstSegmentOffset;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCiphertextOverhead() {
/* 250 */     return this.tagSizeInBytes;
/*     */   }
/*     */   
/*     */   public int getFirstSegmentOffset() {
/* 254 */     return this.firstSegmentOffset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long expectedCiphertextSize(long plaintextSize) {
/* 262 */     long offset = getCiphertextOffset();
/* 263 */     long fullSegments = (plaintextSize + offset) / this.plaintextSegmentSize;
/* 264 */     long ciphertextSize = fullSegments * this.ciphertextSegmentSize;
/* 265 */     long lastSegmentSize = (plaintextSize + offset) % this.plaintextSegmentSize;
/* 266 */     if (lastSegmentSize > 0L) {
/* 267 */       ciphertextSize += lastSegmentSize + this.tagSizeInBytes;
/*     */     }
/* 269 */     return ciphertextSize;
/*     */   }
/*     */   
/*     */   private static Cipher cipherInstance() throws GeneralSecurityException {
/* 273 */     return EngineFactory.CIPHER.getInstance("AES/CTR/NoPadding");
/*     */   }
/*     */   
/*     */   private Mac macInstance() throws GeneralSecurityException {
/* 277 */     return EngineFactory.MAC.getInstance(this.tagAlgo);
/*     */   }
/*     */   
/*     */   private byte[] randomSalt() {
/* 281 */     return Random.randBytes(this.keySizeInBytes);
/*     */   }
/*     */ 
/*     */   
/*     */   private byte[] nonceForSegment(byte[] prefix, long segmentNr, boolean last) throws GeneralSecurityException {
/* 286 */     ByteBuffer nonce = ByteBuffer.allocate(16);
/* 287 */     nonce.order(ByteOrder.BIG_ENDIAN);
/* 288 */     nonce.put(prefix);
/* 289 */     SubtleUtil.putAsUnsigedInt(nonce, segmentNr);
/* 290 */     nonce.put((byte)(last ? 1 : 0));
/* 291 */     nonce.putInt(0);
/* 292 */     return nonce.array();
/*     */   }
/*     */   
/*     */   private byte[] randomNonce() {
/* 296 */     return Random.randBytes(7);
/*     */   }
/*     */   
/*     */   private byte[] deriveKeyMaterial(byte[] salt, byte[] aad) throws GeneralSecurityException {
/* 300 */     int keyMaterialSize = this.keySizeInBytes + 32;
/* 301 */     return Hkdf.computeHkdf(this.hkdfAlgo, this.ikm, salt, aad, keyMaterialSize);
/*     */   }
/*     */   
/*     */   private SecretKeySpec deriveKeySpec(byte[] keyMaterial) throws GeneralSecurityException {
/* 305 */     return new SecretKeySpec(keyMaterial, 0, this.keySizeInBytes, "AES");
/*     */   }
/*     */   
/*     */   private SecretKeySpec deriveHmacKeySpec(byte[] keyMaterial) throws GeneralSecurityException {
/* 309 */     return new SecretKeySpec(keyMaterial, this.keySizeInBytes, 32, this.tagAlgo);
/*     */   }
/*     */ 
/*     */   
/*     */   class AesCtrHmacStreamEncrypter
/*     */     implements StreamSegmentEncrypter
/*     */   {
/*     */     private final SecretKeySpec keySpec;
/*     */     
/*     */     private final SecretKeySpec hmacKeySpec;
/*     */     
/*     */     private final Cipher cipher;
/*     */     
/*     */     private final Mac mac;
/*     */     private final byte[] noncePrefix;
/*     */     private ByteBuffer header;
/* 325 */     private long encryptedSegments = 0L;
/*     */     
/*     */     public AesCtrHmacStreamEncrypter(byte[] aad) throws GeneralSecurityException {
/* 328 */       this.cipher = AesCtrHmacStreaming.cipherInstance();
/* 329 */       this.mac = AesCtrHmacStreaming.this.macInstance();
/* 330 */       this.encryptedSegments = 0L;
/* 331 */       byte[] salt = AesCtrHmacStreaming.this.randomSalt();
/* 332 */       this.noncePrefix = AesCtrHmacStreaming.this.randomNonce();
/* 333 */       this.header = ByteBuffer.allocate(AesCtrHmacStreaming.this.getHeaderLength());
/* 334 */       this.header.put((byte)AesCtrHmacStreaming.this.getHeaderLength());
/* 335 */       this.header.put(salt);
/* 336 */       this.header.put(this.noncePrefix);
/* 337 */       AesCtrHmacStreaming.toBuffer(this.header).flip();
/* 338 */       byte[] keymaterial = AesCtrHmacStreaming.this.deriveKeyMaterial(salt, aad);
/* 339 */       this.keySpec = AesCtrHmacStreaming.this.deriveKeySpec(keymaterial);
/* 340 */       this.hmacKeySpec = AesCtrHmacStreaming.this.deriveHmacKeySpec(keymaterial);
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteBuffer getHeader() {
/* 345 */       return this.header.asReadOnlyBuffer();
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
/* 356 */       int position = AesCtrHmacStreaming.toBuffer(ciphertext).position();
/* 357 */       byte[] nonce = AesCtrHmacStreaming.this.nonceForSegment(this.noncePrefix, this.encryptedSegments, isLastSegment);
/* 358 */       this.cipher.init(1, this.keySpec, new IvParameterSpec(nonce));
/* 359 */       this.encryptedSegments++;
/* 360 */       this.cipher.doFinal(plaintext, ciphertext);
/* 361 */       ByteBuffer ctCopy = ciphertext.duplicate();
/* 362 */       AesCtrHmacStreaming.toBuffer(ctCopy).flip();
/* 363 */       AesCtrHmacStreaming.toBuffer(ctCopy).position(position);
/* 364 */       this.mac.init(this.hmacKeySpec);
/* 365 */       this.mac.update(nonce);
/* 366 */       this.mac.update(ctCopy);
/* 367 */       byte[] tag = this.mac.doFinal();
/* 368 */       ciphertext.put(tag, 0, AesCtrHmacStreaming.this.tagSizeInBytes);
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
/* 379 */       int position = AesCtrHmacStreaming.toBuffer(ciphertext).position();
/* 380 */       byte[] nonce = AesCtrHmacStreaming.this.nonceForSegment(this.noncePrefix, this.encryptedSegments, isLastSegment);
/* 381 */       this.cipher.init(1, this.keySpec, new IvParameterSpec(nonce));
/* 382 */       this.encryptedSegments++;
/* 383 */       this.cipher.update(part1, ciphertext);
/* 384 */       this.cipher.doFinal(part2, ciphertext);
/* 385 */       ByteBuffer ctCopy = ciphertext.duplicate();
/* 386 */       AesCtrHmacStreaming.toBuffer(ctCopy).flip();
/* 387 */       AesCtrHmacStreaming.toBuffer(ctCopy).position(position);
/* 388 */       this.mac.init(this.hmacKeySpec);
/* 389 */       this.mac.update(nonce);
/* 390 */       this.mac.update(ctCopy);
/* 391 */       byte[] tag = this.mac.doFinal();
/* 392 */       ciphertext.put(tag, 0, AesCtrHmacStreaming.this.tagSizeInBytes);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   class AesCtrHmacStreamDecrypter
/*     */     implements StreamSegmentDecrypter
/*     */   {
/*     */     private SecretKeySpec keySpec;
/*     */     
/*     */     private SecretKeySpec hmacKeySpec;
/*     */     private Cipher cipher;
/*     */     private Mac mac;
/*     */     private byte[] noncePrefix;
/*     */     
/*     */     public synchronized void init(ByteBuffer header, byte[] aad) throws GeneralSecurityException {
/* 408 */       if (header.remaining() != AesCtrHmacStreaming.this.getHeaderLength()) {
/* 409 */         throw new InvalidAlgorithmParameterException("Invalid header length");
/*     */       }
/* 411 */       byte firstByte = header.get();
/* 412 */       if (firstByte != AesCtrHmacStreaming.this.getHeaderLength())
/*     */       {
/*     */ 
/*     */         
/* 416 */         throw new GeneralSecurityException("Invalid ciphertext");
/*     */       }
/* 418 */       this.noncePrefix = new byte[7];
/* 419 */       byte[] salt = new byte[AesCtrHmacStreaming.this.keySizeInBytes];
/* 420 */       header.get(salt);
/* 421 */       header.get(this.noncePrefix);
/* 422 */       byte[] keymaterial = AesCtrHmacStreaming.this.deriveKeyMaterial(salt, aad);
/* 423 */       this.keySpec = AesCtrHmacStreaming.this.deriveKeySpec(keymaterial);
/* 424 */       this.hmacKeySpec = AesCtrHmacStreaming.this.deriveHmacKeySpec(keymaterial);
/* 425 */       this.cipher = AesCtrHmacStreaming.cipherInstance();
/* 426 */       this.mac = AesCtrHmacStreaming.this.macInstance();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public synchronized void decryptSegment(ByteBuffer ciphertext, int segmentNr, boolean isLastSegment, ByteBuffer plaintext) throws GeneralSecurityException {
/* 433 */       int position = AesCtrHmacStreaming.toBuffer(ciphertext).position();
/* 434 */       byte[] nonce = AesCtrHmacStreaming.this.nonceForSegment(this.noncePrefix, segmentNr, isLastSegment);
/* 435 */       int ctLength = ciphertext.remaining();
/* 436 */       if (ctLength < AesCtrHmacStreaming.this.tagSizeInBytes) {
/* 437 */         throw new GeneralSecurityException("Ciphertext too short");
/*     */       }
/* 439 */       int ptLength = ctLength - AesCtrHmacStreaming.this.tagSizeInBytes;
/* 440 */       int startOfTag = position + ptLength;
/* 441 */       ByteBuffer ct = ciphertext.duplicate();
/* 442 */       AesCtrHmacStreaming.toBuffer(ct).limit(startOfTag);
/* 443 */       ByteBuffer tagBuffer = ciphertext.duplicate();
/* 444 */       AesCtrHmacStreaming.toBuffer(tagBuffer).position(startOfTag);
/*     */       
/* 446 */       assert this.mac != null;
/* 447 */       assert this.hmacKeySpec != null;
/* 448 */       this.mac.init(this.hmacKeySpec);
/* 449 */       this.mac.update(nonce);
/* 450 */       this.mac.update(ct);
/* 451 */       byte[] tag = this.mac.doFinal();
/* 452 */       tag = Arrays.copyOf(tag, AesCtrHmacStreaming.this.tagSizeInBytes);
/* 453 */       byte[] expectedTag = new byte[AesCtrHmacStreaming.this.tagSizeInBytes];
/* 454 */       assert tagBuffer.remaining() == AesCtrHmacStreaming.this.tagSizeInBytes;
/* 455 */       tagBuffer.get(expectedTag);
/* 456 */       assert expectedTag.length == tag.length;
/* 457 */       if (!Bytes.equal(expectedTag, tag)) {
/* 458 */         throw new GeneralSecurityException("Tag mismatch");
/*     */       }
/*     */       
/* 461 */       AesCtrHmacStreaming.toBuffer(ciphertext).limit(startOfTag);
/* 462 */       this.cipher.init(1, this.keySpec, new IvParameterSpec(nonce));
/* 463 */       this.cipher.doFinal(ciphertext, plaintext);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\subtle\AesCtrHmacStreaming.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */