/*     */ package com.nimbusds.jose;
/*     */ 
/*     */ import com.nimbusds.jose.crypto.impl.AAD;
/*     */ import com.nimbusds.jose.shaded.jcip.ThreadSafe;
/*     */ import com.nimbusds.jose.util.Base64URL;
/*     */ import java.text.ParseException;
/*     */ import java.util.Objects;
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
/*     */ @ThreadSafe
/*     */ public class JWEObject
/*     */   extends JOSEObject
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public static final int MAX_COMPRESSED_CIPHER_TEXT_LENGTH = 100000;
/*     */   private JWEHeader header;
/*     */   private Base64URL encryptedKey;
/*     */   private Base64URL iv;
/*     */   private Base64URL cipherText;
/*     */   private Base64URL authTag;
/*     */   private State state;
/*     */   
/*     */   public enum State
/*     */   {
/*  63 */     UNENCRYPTED,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  69 */     ENCRYPTED,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  75 */     DECRYPTED;
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
/*     */   public JWEObject(JWEHeader header, Payload payload) {
/* 126 */     this.header = Objects.<JWEHeader>requireNonNull(header);
/* 127 */     setPayload(Objects.<Payload>requireNonNull(payload));
/* 128 */     this.encryptedKey = null;
/* 129 */     this.cipherText = null;
/* 130 */     this.state = State.UNENCRYPTED;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public JWEObject(Base64URL firstPart, Base64URL secondPart, Base64URL thirdPart, Base64URL fourthPart, Base64URL fifthPart) throws ParseException {
/*     */     try {
/* 161 */       this.header = JWEHeader.parse(Objects.<Base64URL>requireNonNull(firstPart));
/* 162 */     } catch (ParseException e) {
/* 163 */       throw new ParseException("Invalid JWE header: " + e.getMessage(), 0);
/*     */     } 
/*     */     
/* 166 */     if (secondPart == null || secondPart.toString().isEmpty()) {
/*     */       
/* 168 */       this.encryptedKey = null;
/*     */     }
/*     */     else {
/*     */       
/* 172 */       this.encryptedKey = secondPart;
/*     */     } 
/*     */     
/* 175 */     if (thirdPart == null || thirdPart.toString().isEmpty()) {
/*     */       
/* 177 */       this.iv = null;
/*     */     }
/*     */     else {
/*     */       
/* 181 */       this.iv = thirdPart;
/*     */     } 
/*     */     
/* 184 */     this.cipherText = Objects.<Base64URL>requireNonNull(fourthPart);
/*     */     
/* 186 */     if (fifthPart == null || fifthPart.toString().isEmpty()) {
/*     */       
/* 188 */       this.authTag = null;
/*     */     }
/*     */     else {
/*     */       
/* 192 */       this.authTag = fifthPart;
/*     */     } 
/*     */     
/* 195 */     this.state = State.ENCRYPTED;
/*     */     
/* 197 */     setParsedParts(new Base64URL[] { firstPart, secondPart, thirdPart, fourthPart, fifthPart });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JWEHeader getHeader() {
/* 204 */     return this.header;
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
/*     */   public Base64URL getEncryptedKey() {
/* 216 */     return this.encryptedKey;
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
/*     */   public Base64URL getIV() {
/* 228 */     return this.iv;
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
/*     */   public Base64URL getCipherText() {
/* 240 */     return this.cipherText;
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
/*     */   public Base64URL getAuthTag() {
/* 252 */     return this.authTag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public State getState() {
/* 263 */     return this.state;
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
/*     */   private void ensureUnencryptedState() {
/* 275 */     if (this.state != State.UNENCRYPTED)
/*     */     {
/* 277 */       throw new IllegalStateException("The JWE object must be in an unencrypted state");
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
/*     */   private void ensureEncryptedState() {
/* 289 */     if (this.state != State.ENCRYPTED)
/*     */     {
/* 291 */       throw new IllegalStateException("The JWE object must be in an encrypted state");
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
/*     */   private void ensureEncryptedOrDecryptedState() {
/* 305 */     if (this.state != State.ENCRYPTED && this.state != State.DECRYPTED)
/*     */     {
/* 307 */       throw new IllegalStateException("The JWE object must be in an encrypted or decrypted state");
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
/*     */   private void ensureJWEEncrypterSupport(JWEEncrypter encrypter) throws JOSEException {
/* 321 */     if (!encrypter.supportedJWEAlgorithms().contains(getHeader().getAlgorithm()))
/*     */     {
/* 323 */       throw new JOSEException("The " + getHeader().getAlgorithm() + " algorithm is not supported by the JWE encrypter: Supported algorithms: " + encrypter
/* 324 */           .supportedJWEAlgorithms());
/*     */     }
/*     */     
/* 327 */     if (!encrypter.supportedEncryptionMethods().contains(getHeader().getEncryptionMethod()))
/*     */     {
/* 329 */       throw new JOSEException("The " + getHeader().getEncryptionMethod() + " encryption method or key size is not supported by the JWE encrypter: Supported methods: " + encrypter
/* 330 */           .supportedEncryptionMethods());
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
/*     */   public synchronized void encrypt(JWEEncrypter encrypter) throws JOSEException {
/*     */     JWECryptoParts parts;
/* 350 */     ensureUnencryptedState();
/*     */     
/* 352 */     ensureJWEEncrypterSupport(encrypter);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 357 */       parts = encrypter.encrypt(getHeader(), getPayload().toBytes(), AAD.compute(getHeader()));
/*     */     }
/* 359 */     catch (JOSEException e) {
/*     */       
/* 361 */       throw e;
/*     */     }
/* 363 */     catch (Exception e) {
/*     */ 
/*     */ 
/*     */       
/* 367 */       throw new JOSEException(e.getMessage(), e);
/*     */     } 
/*     */ 
/*     */     
/* 371 */     if (parts.getHeader() != null) {
/* 372 */       this.header = parts.getHeader();
/*     */     }
/*     */     
/* 375 */     this.encryptedKey = parts.getEncryptedKey();
/* 376 */     this.iv = parts.getInitializationVector();
/* 377 */     this.cipherText = parts.getCipherText();
/* 378 */     this.authTag = parts.getAuthenticationTag();
/*     */     
/* 380 */     this.state = State.ENCRYPTED;
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
/*     */   public synchronized void decrypt(JWEDecrypter decrypter) throws JOSEException {
/* 399 */     ensureEncryptedState();
/*     */     
/* 401 */     if (getHeader().getCompressionAlgorithm() != null && 
/* 402 */       getCipherText().toString().length() > 100000)
/*     */     {
/* 404 */       throw new JOSEException("The JWE compressed cipher text exceeds the maximum allowed length of 100000 characters");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 412 */       setPayload(new Payload(decrypter.decrypt(getHeader(), 
/* 413 */               getEncryptedKey(), 
/* 414 */               getIV(), 
/* 415 */               getCipherText(), 
/* 416 */               getAuthTag(), 
/* 417 */               AAD.compute(getHeader()))));
/*     */     }
/* 419 */     catch (JOSEException e) {
/*     */       
/* 421 */       throw e;
/*     */     }
/* 423 */     catch (Exception e) {
/*     */ 
/*     */ 
/*     */       
/* 427 */       throw new JOSEException(e.getMessage(), e);
/*     */     } 
/*     */     
/* 430 */     this.state = State.DECRYPTED;
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
/*     */   public String serialize() {
/* 454 */     ensureEncryptedOrDecryptedState();
/*     */     
/* 456 */     StringBuilder sb = new StringBuilder(this.header.toBase64URL().toString());
/* 457 */     sb.append('.');
/*     */     
/* 459 */     if (this.encryptedKey != null) {
/* 460 */       sb.append(this.encryptedKey);
/*     */     }
/*     */     
/* 463 */     sb.append('.');
/*     */     
/* 465 */     if (this.iv != null) {
/* 466 */       sb.append(this.iv);
/*     */     }
/*     */     
/* 469 */     sb.append('.');
/* 470 */     sb.append(this.cipherText);
/* 471 */     sb.append('.');
/*     */     
/* 473 */     if (this.authTag != null) {
/* 474 */       sb.append(this.authTag);
/*     */     }
/*     */     
/* 477 */     return sb.toString();
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
/*     */   public static JWEObject parse(String s) throws ParseException {
/* 495 */     Base64URL[] parts = JOSEObject.split(s);
/*     */     
/* 497 */     if (parts.length != 5)
/*     */     {
/* 499 */       throw new ParseException("Unexpected number of Base64URL parts, must be five", 0);
/*     */     }
/*     */     
/* 502 */     return new JWEObject(parts[0], parts[1], parts[2], parts[3], parts[4]);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\JWEObject.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */