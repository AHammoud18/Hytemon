/*     */ package com.nimbusds.jose;
/*     */ 
/*     */ import com.nimbusds.jose.shaded.jcip.Immutable;
/*     */ import com.nimbusds.jose.shaded.jcip.ThreadSafe;
/*     */ import com.nimbusds.jose.util.Base64URL;
/*     */ import com.nimbusds.jose.util.JSONArrayUtils;
/*     */ import com.nimbusds.jose.util.JSONObjectUtils;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.text.ParseException;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ 
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
/*     */ public class JWEObjectJSON
/*     */   extends JOSEObjectJSON
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private final JWEHeader header;
/*     */   private UnprotectedHeader unprotectedHeader;
/*     */   
/*     */   @Immutable
/*     */   public static final class Recipient
/*     */   {
/*     */     private final UnprotectedHeader unprotectedHeader;
/*     */     private final Base64URL encryptedKey;
/*     */     
/*     */     public Recipient(UnprotectedHeader unprotectedHeader, Base64URL encryptedKey) {
/*  80 */       this.unprotectedHeader = unprotectedHeader;
/*  81 */       this.encryptedKey = encryptedKey;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public UnprotectedHeader getUnprotectedHeader() {
/*  92 */       return this.unprotectedHeader;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Base64URL getEncryptedKey() {
/* 102 */       return this.encryptedKey;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Map<String, Object> toJSONObject() {
/* 113 */       Map<String, Object> jsonObject = JSONObjectUtils.newJSONObject();
/*     */       
/* 115 */       if (this.unprotectedHeader != null && !this.unprotectedHeader.getIncludedParams().isEmpty()) {
/* 116 */         jsonObject.put("header", this.unprotectedHeader.toJSONObject());
/*     */       }
/* 118 */       if (this.encryptedKey != null) {
/* 119 */         jsonObject.put("encrypted_key", this.encryptedKey.toString());
/*     */       }
/* 121 */       return jsonObject;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static Recipient parse(Map<String, Object> jsonObject) throws ParseException {
/* 139 */       UnprotectedHeader header = UnprotectedHeader.parse(JSONObjectUtils.getJSONObject(jsonObject, "header"));
/* 140 */       Base64URL encryptedKey = JSONObjectUtils.getBase64URL(jsonObject, "encrypted_key");
/*     */       
/* 142 */       return new Recipient(header, encryptedKey);
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
/*     */   
/* 162 */   private final List<Recipient> recipients = new LinkedList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Base64URL iv;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Base64URL cipherText;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Base64URL authTag;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final byte[] aad;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JWEObject.State state;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JWEObjectJSON(JWEObject jweObject) {
/* 206 */     super(jweObject.getPayload());
/*     */     
/* 208 */     this.header = jweObject.getHeader();
/* 209 */     this.aad = null;
/* 210 */     this.iv = jweObject.getIV();
/* 211 */     this.cipherText = jweObject.getCipherText();
/* 212 */     this.authTag = jweObject.getAuthTag();
/* 213 */     if (jweObject.getState() == JWEObject.State.ENCRYPTED) {
/* 214 */       this.recipients.add(new Recipient(null, jweObject.getEncryptedKey()));
/* 215 */       this.state = JWEObject.State.ENCRYPTED;
/* 216 */     } else if (jweObject.getState() == JWEObject.State.DECRYPTED) {
/* 217 */       this.recipients.add(new Recipient(null, jweObject.getEncryptedKey()));
/* 218 */       this.state = JWEObject.State.DECRYPTED;
/*     */     } else {
/* 220 */       this.state = JWEObject.State.UNENCRYPTED;
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
/*     */   public JWEObjectJSON(JWEHeader header, Payload payload) {
/* 235 */     this(header, payload, (UnprotectedHeader)null, (byte[])null);
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
/*     */   public JWEObjectJSON(JWEHeader header, Payload payload, UnprotectedHeader unprotectedHeader, byte[] aad) {
/* 258 */     super(payload);
/* 259 */     this.header = Objects.<JWEHeader>requireNonNull(header);
/* 260 */     setPayload(Objects.<Payload>requireNonNull(payload));
/* 261 */     this.unprotectedHeader = unprotectedHeader;
/* 262 */     this.aad = aad;
/* 263 */     this.cipherText = null;
/* 264 */     this.state = JWEObject.State.UNENCRYPTED;
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
/*     */   public JWEObjectJSON(JWEHeader header, Base64URL cipherText, Base64URL iv, Base64URL authTag, List<Recipient> recipients, UnprotectedHeader unprotectedHeader, byte[] aad) {
/* 295 */     super(null);
/*     */     
/* 297 */     this.header = Objects.<JWEHeader>requireNonNull(header);
/* 298 */     this.recipients.addAll(recipients);
/* 299 */     this.unprotectedHeader = unprotectedHeader;
/* 300 */     this.aad = aad;
/* 301 */     this.iv = iv;
/* 302 */     this.cipherText = Objects.<Base64URL>requireNonNull(cipherText);
/* 303 */     this.authTag = authTag;
/*     */     
/* 305 */     this.state = JWEObject.State.ENCRYPTED;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JWEHeader getHeader() {
/* 315 */     return this.header;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnprotectedHeader getUnprotectedHeader() {
/* 326 */     return this.unprotectedHeader;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Base64URL getEncryptedKey() {
/* 337 */     if (this.recipients.isEmpty())
/* 338 */       return null; 
/* 339 */     if (this.recipients.size() == 1) {
/* 340 */       return ((Recipient)this.recipients.get(0)).getEncryptedKey();
/*     */     }
/* 342 */     List<Object> recipientsList = JSONArrayUtils.newJSONArray();
/* 343 */     for (Recipient recipient : this.recipients) {
/* 344 */       recipientsList.add(recipient.toJSONObject());
/*     */     }
/* 346 */     Map<String, Object> recipientsMap = JSONObjectUtils.newJSONObject();
/* 347 */     recipientsMap.put("recipients", recipientsList);
/* 348 */     return Base64URL.encode(JSONObjectUtils.toJSONString(recipientsMap));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Base64URL getIV() {
/* 359 */     return this.iv;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Base64URL getCipherText() {
/* 370 */     return this.cipherText;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Base64URL getAuthTag() {
/* 381 */     return this.authTag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getAAD() {
/* 391 */     StringBuilder aadSB = new StringBuilder(this.header.toBase64URL().toString());
/* 392 */     if (this.aad != null && this.aad.length > 0) {
/* 393 */       aadSB.append(".").append(new String(this.aad, StandardCharsets.US_ASCII));
/*     */     }
/* 395 */     return aadSB.toString().getBytes(StandardCharsets.US_ASCII);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Recipient> getRecipients() {
/* 405 */     return Collections.unmodifiableList(this.recipients);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JWEObject.State getState() {
/* 415 */     return this.state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void ensureUnencryptedState() {
/* 426 */     if (this.state != JWEObject.State.UNENCRYPTED) {
/* 427 */       throw new IllegalStateException("The JWE object must be in an unencrypted state");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void ensureEncryptedState() {
/* 438 */     if (this.state != JWEObject.State.ENCRYPTED) {
/* 439 */       throw new IllegalStateException("The JWE object must be in an encrypted state");
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
/*     */   private void ensureEncryptedOrDecryptedState() {
/* 452 */     if (this.state != JWEObject.State.ENCRYPTED && this.state != JWEObject.State.DECRYPTED) {
/* 453 */       throw new IllegalStateException("The JWE object must be in an encrypted or decrypted state");
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
/* 467 */     if (!encrypter.supportedJWEAlgorithms().contains(getHeader().getAlgorithm())) {
/* 468 */       throw new JOSEException("The " + getHeader().getAlgorithm() + " algorithm is not supported by the JWE encrypter: Supported algorithms: " + encrypter
/* 469 */           .supportedJWEAlgorithms());
/*     */     }
/*     */     
/* 472 */     if (!encrypter.supportedEncryptionMethods().contains(getHeader().getEncryptionMethod())) {
/* 473 */       throw new JOSEException("The " + getHeader().getEncryptionMethod() + " encryption method or key size is not supported by the JWE encrypter: Supported methods: " + encrypter
/* 474 */           .supportedEncryptionMethods());
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
/* 494 */     ensureUnencryptedState();
/*     */     
/* 496 */     ensureJWEEncrypterSupport(encrypter);
/*     */ 
/*     */ 
/*     */     
/* 500 */     JWEHeader jweJoinedHeader = getHeader();
/*     */     try {
/* 502 */       jweJoinedHeader = (JWEHeader)getHeader().join(this.unprotectedHeader);
/* 503 */       parts = encrypter.encrypt(jweJoinedHeader, getPayload().toBytes(), getAAD());
/* 504 */     } catch (JOSEException e) {
/* 505 */       throw e;
/* 506 */     } catch (Exception e) {
/*     */ 
/*     */       
/* 509 */       throw new JOSEException(e.getMessage(), e);
/*     */     } 
/*     */     
/* 512 */     Base64URL encryptedKey = parts.getEncryptedKey();
/*     */     try {
/* 514 */       for (Map<String, Object> recipientMap : JSONObjectUtils.getJSONObjectArray(JSONObjectUtils.parse(encryptedKey.decodeToString()), "recipients")) {
/* 515 */         this.recipients.add(Recipient.parse(recipientMap));
/*     */       }
/* 517 */     } catch (Exception e) {
/* 518 */       Map<String, Object> recipientHeader = parts.getHeader().toJSONObject();
/* 519 */       for (String param : jweJoinedHeader.getIncludedParams()) {
/* 520 */         if (recipientHeader.containsKey(param)) {
/* 521 */           recipientHeader.remove(param);
/*     */         }
/*     */       } 
/*     */       try {
/* 525 */         this.recipients.add(new Recipient(UnprotectedHeader.parse(recipientHeader), encryptedKey));
/* 526 */       } catch (Exception ex) {
/* 527 */         throw new JOSEException(ex.getMessage(), ex);
/*     */       } 
/*     */     } 
/* 530 */     this.iv = parts.getInitializationVector();
/* 531 */     this.cipherText = parts.getCipherText();
/* 532 */     this.authTag = parts.getAuthenticationTag();
/*     */     
/* 534 */     this.state = JWEObject.State.ENCRYPTED;
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
/* 553 */     ensureEncryptedState();
/*     */     
/*     */     try {
/* 556 */       setPayload(new Payload(decrypter.decrypt(getHeader(), 
/* 557 */               getEncryptedKey(), 
/* 558 */               getIV(), 
/* 559 */               getCipherText(), 
/* 560 */               getAuthTag(), 
/* 561 */               getAAD())));
/* 562 */     } catch (JOSEException e) {
/* 563 */       throw e;
/* 564 */     } catch (Exception e) {
/*     */ 
/*     */       
/* 567 */       throw new JOSEException(e.getMessage(), e);
/*     */     } 
/*     */     
/* 570 */     this.state = JWEObject.State.DECRYPTED;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Map<String, Object> toBaseJSONObject() {
/* 579 */     Map<String, Object> jsonObject = JSONObjectUtils.newJSONObject();
/* 580 */     jsonObject.put("protected", this.header.toBase64URL().toString());
/* 581 */     if (this.aad != null) {
/* 582 */       jsonObject.put("aad", new String(this.aad, StandardCharsets.US_ASCII));
/*     */     }
/* 584 */     jsonObject.put("ciphertext", this.cipherText.toString());
/* 585 */     jsonObject.put("iv", this.iv.toString());
/* 586 */     jsonObject.put("tag", this.authTag.toString());
/* 587 */     return jsonObject;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, Object> toGeneralJSONObject() {
/* 594 */     ensureEncryptedOrDecryptedState();
/*     */     
/* 596 */     if (this.recipients.isEmpty() || (((Recipient)this.recipients.get(0)).getUnprotectedHeader() == null && ((Recipient)this.recipients.get(0)).getEncryptedKey() == null)) {
/* 597 */       throw new IllegalStateException("The general JWE JSON serialization requires at least one recipient");
/*     */     }
/*     */     
/* 600 */     Map<String, Object> jsonObject = toBaseJSONObject();
/*     */     
/* 602 */     if (this.unprotectedHeader != null) {
/* 603 */       jsonObject.put("unprotected", this.unprotectedHeader.toJSONObject());
/*     */     }
/*     */     
/* 606 */     List<Object> recipientsJSONArray = JSONArrayUtils.newJSONArray();
/*     */     
/* 608 */     for (Recipient recipient : this.recipients) {
/* 609 */       Map<String, Object> recipientJSONObject = recipient.toJSONObject();
/* 610 */       recipientsJSONArray.add(recipientJSONObject);
/*     */     } 
/*     */     
/* 613 */     jsonObject.put("recipients", recipientsJSONArray);
/* 614 */     return jsonObject;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, Object> toFlattenedJSONObject() {
/* 621 */     ensureEncryptedOrDecryptedState();
/*     */     
/* 623 */     if (this.recipients.size() != 1) {
/* 624 */       throw new IllegalStateException("The flattened JWE JSON serialization requires exactly one recipient");
/*     */     }
/*     */     
/* 627 */     Map<String, Object> jsonObject = toBaseJSONObject();
/*     */     
/* 629 */     Map<String, Object> recipientHeader = JSONObjectUtils.newJSONObject();
/* 630 */     if (((Recipient)this.recipients.get(0)).getUnprotectedHeader() != null) {
/* 631 */       recipientHeader.putAll(((Recipient)this.recipients.get(0)).getUnprotectedHeader().toJSONObject());
/*     */     }
/* 633 */     if (this.unprotectedHeader != null) {
/* 634 */       recipientHeader.putAll(this.unprotectedHeader.toJSONObject());
/*     */     }
/* 636 */     if (recipientHeader.size() > 0) {
/* 637 */       jsonObject.put("unprotected", recipientHeader);
/*     */     }
/* 639 */     if (((Recipient)this.recipients.get(0)).getEncryptedKey() != null) {
/* 640 */       jsonObject.put("encrypted_key", ((Recipient)this.recipients.get(0)).getEncryptedKey().toString());
/*     */     }
/* 642 */     return jsonObject;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String serializeGeneral() {
/* 648 */     return JSONObjectUtils.toJSONString(toGeneralJSONObject());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String serializeFlattened() {
/* 654 */     return JSONObjectUtils.toJSONString(toFlattenedJSONObject());
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
/*     */   public static JWEObjectJSON parse(Map<String, Object> jsonObject) throws ParseException {
/* 672 */     if (!jsonObject.containsKey("protected")) {
/* 673 */       throw new ParseException("The JWE protected header mast be present", 0);
/*     */     }
/*     */     
/* 676 */     List<Recipient> recipientList = new LinkedList<>();
/* 677 */     JWEHeader jweHeader = JWEHeader.parse(JSONObjectUtils.getBase64URL(jsonObject, "protected"));
/* 678 */     UnprotectedHeader unprotected = UnprotectedHeader.parse(JSONObjectUtils.getJSONObject(jsonObject, "unprotected"));
/* 679 */     Base64URL cipherText = JSONObjectUtils.getBase64URL(jsonObject, "ciphertext");
/* 680 */     Base64URL iv = JSONObjectUtils.getBase64URL(jsonObject, "iv");
/* 681 */     Base64URL authTag = JSONObjectUtils.getBase64URL(jsonObject, "tag");
/* 682 */     Base64URL aad = JSONObjectUtils.getBase64URL(jsonObject, "aad");
/* 683 */     JWEHeader jweJoinedHeader = (JWEHeader)jweHeader.join(unprotected);
/*     */     
/* 685 */     if (jsonObject.containsKey("recipients")) {
/* 686 */       Map[] arrayOfMap = JSONObjectUtils.getJSONObjectArray(jsonObject, "recipients");
/* 687 */       if (arrayOfMap == null || arrayOfMap.length == 0) {
/* 688 */         throw new ParseException("The \"recipients\" member must be present in general JSON Serialization", 0);
/*     */       }
/* 690 */       for (Map<String, Object> recipientJSONObject : arrayOfMap) {
/* 691 */         Recipient recipient = Recipient.parse(recipientJSONObject);
/*     */         try {
/* 693 */           HeaderValidation.ensureDisjoint(jweJoinedHeader, recipient.getUnprotectedHeader());
/* 694 */         } catch (IllegalHeaderException e) {
/* 695 */           throw new ParseException(e.getMessage(), 0);
/*     */         } 
/* 697 */         recipientList.add(recipient);
/*     */       } 
/*     */     } else {
/* 700 */       Base64URL encryptedKey = JSONObjectUtils.getBase64URL(jsonObject, "encrypted_key");
/* 701 */       recipientList.add(new Recipient(null, encryptedKey));
/*     */     } 
/*     */     
/* 704 */     return new JWEObjectJSON(jweHeader, cipherText, iv, authTag, recipientList, unprotected, (aad == null) ? null : aad.toString().getBytes(StandardCharsets.US_ASCII));
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
/*     */   public static JWEObjectJSON parse(String json) throws ParseException {
/* 722 */     return parse(JSONObjectUtils.parse(Objects.<String>requireNonNull(json)));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\JWEObjectJSON.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */