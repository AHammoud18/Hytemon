/*     */ package com.nimbusds.jose;
/*     */ 
/*     */ import com.nimbusds.jose.shaded.jcip.Immutable;
/*     */ import com.nimbusds.jose.shaded.jcip.ThreadSafe;
/*     */ import com.nimbusds.jose.util.Base64URL;
/*     */ import com.nimbusds.jose.util.JSONArrayUtils;
/*     */ import com.nimbusds.jose.util.JSONObjectUtils;
/*     */ import java.text.ParseException;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class JWSObjectJSON
/*     */   extends JOSEObjectJSON
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   
/*     */   @Immutable
/*     */   public static final class Signature
/*     */   {
/*     */     private final Payload payload;
/*     */     private final JWSHeader header;
/*     */     private final UnprotectedHeader unprotectedHeader;
/*     */     private final Base64URL signature;
/*  84 */     private final AtomicBoolean verified = new AtomicBoolean(false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Signature(Payload payload, JWSHeader header, UnprotectedHeader unprotectedHeader, Base64URL signature) {
/* 104 */       Objects.requireNonNull(payload);
/* 105 */       this.payload = payload;
/*     */       
/* 107 */       this.header = header;
/* 108 */       this.unprotectedHeader = unprotectedHeader;
/*     */       
/* 110 */       Objects.requireNonNull(signature);
/* 111 */       this.signature = signature;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public JWSHeader getHeader() {
/* 121 */       return this.header;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public UnprotectedHeader getUnprotectedHeader() {
/* 131 */       return this.unprotectedHeader;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Base64URL getSignature() {
/* 141 */       return this.signature;
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
/*     */     private Map<String, Object> toJSONObject() {
/* 153 */       Map<String, Object> jsonObject = JSONObjectUtils.newJSONObject();
/*     */       
/* 155 */       if (this.header != null) {
/* 156 */         jsonObject.put("protected", this.header.toBase64URL().toString());
/*     */       }
/*     */       
/* 159 */       if (this.unprotectedHeader != null && !this.unprotectedHeader.getIncludedParams().isEmpty()) {
/* 160 */         jsonObject.put("header", this.unprotectedHeader.toJSONObject());
/*     */       }
/*     */       
/* 163 */       jsonObject.put("signature", this.signature.toString());
/*     */       
/* 165 */       return jsonObject;
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
/*     */     public JWSObject toJWSObject() {
/*     */       try {
/* 178 */         return new JWSObject(this.header.toBase64URL(), this.payload.toBase64URL(), this.signature);
/* 179 */       } catch (ParseException e) {
/* 180 */         throw new IllegalStateException();
/*     */       } 
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
/*     */     public boolean isVerified() {
/* 194 */       return this.verified.get();
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
/*     */     public synchronized boolean verify(JWSVerifier verifier) throws JOSEException {
/*     */       try {
/* 212 */         this.verified.set(toJWSObject().verify(verifier));
/* 213 */       } catch (JOSEException e) {
/* 214 */         throw e;
/* 215 */       } catch (Exception e) {
/*     */ 
/*     */         
/* 218 */         throw new JOSEException(e.getMessage(), e);
/*     */       } 
/*     */       
/* 221 */       return this.verified.get();
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
/*     */   public enum State
/*     */   {
/* 236 */     UNSIGNED,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 243 */     SIGNED,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 249 */     VERIFIED;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 256 */   private final List<Signature> signatures = new LinkedList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JWSObjectJSON(Payload payload) {
/* 267 */     super(payload);
/* 268 */     Objects.requireNonNull(payload, "The payload must not be null");
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
/*     */   private JWSObjectJSON(Payload payload, List<Signature> signatures) {
/* 282 */     super(Objects.<Payload>requireNonNull(payload, "The payload must not be null"));
/*     */     
/* 284 */     if (signatures.isEmpty()) {
/* 285 */       throw new IllegalArgumentException("At least one signature required");
/*     */     }
/*     */     
/* 288 */     this.signatures.addAll(signatures);
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
/*     */   public List<Signature> getSignatures() {
/* 300 */     return Collections.unmodifiableList(this.signatures);
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
/*     */   public synchronized void sign(JWSHeader jwsHeader, JWSSigner signer) throws JOSEException {
/* 320 */     sign(jwsHeader, null, signer);
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
/*     */   public synchronized void sign(JWSHeader jwsHeader, UnprotectedHeader unprotectedHeader, JWSSigner signer) throws JOSEException {
/*     */     try {
/* 346 */       HeaderValidation.ensureDisjoint(jwsHeader, unprotectedHeader);
/* 347 */     } catch (IllegalHeaderException e) {
/* 348 */       throw new IllegalArgumentException(e.getMessage(), e);
/*     */     } 
/*     */     
/* 351 */     JWSObject jwsObject = new JWSObject(jwsHeader, getPayload());
/* 352 */     jwsObject.sign(signer);
/*     */     
/* 354 */     this.signatures.add(new Signature(getPayload(), jwsHeader, unprotectedHeader, jwsObject.getSignature()));
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
/* 365 */     if (getSignatures().isEmpty()) {
/* 366 */       return State.UNSIGNED;
/*     */     }
/*     */     
/* 369 */     for (Signature sig : getSignatures()) {
/* 370 */       if (!sig.isVerified()) {
/* 371 */         return State.SIGNED;
/*     */       }
/*     */     } 
/*     */     
/* 375 */     return State.VERIFIED;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, Object> toGeneralJSONObject() {
/* 382 */     if (this.signatures.size() < 1) {
/* 383 */       throw new IllegalStateException("The general JWS JSON serialization requires at least one signature");
/*     */     }
/*     */     
/* 386 */     Map<String, Object> jsonObject = JSONObjectUtils.newJSONObject();
/* 387 */     jsonObject.put("payload", getPayload().toBase64URL().toString());
/*     */     
/* 389 */     List<Object> signaturesJSONArray = JSONArrayUtils.newJSONArray();
/*     */     
/* 391 */     for (Signature signature : getSignatures()) {
/* 392 */       Map<String, Object> signatureJSONObject = signature.toJSONObject();
/* 393 */       signaturesJSONArray.add(signatureJSONObject);
/*     */     } 
/*     */     
/* 396 */     jsonObject.put("signatures", signaturesJSONArray);
/*     */     
/* 398 */     return jsonObject;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, Object> toFlattenedJSONObject() {
/* 405 */     if (this.signatures.size() != 1) {
/* 406 */       throw new IllegalStateException("The flattened JWS JSON serialization requires exactly one signature");
/*     */     }
/*     */     
/* 409 */     Map<String, Object> jsonObject = JSONObjectUtils.newJSONObject();
/* 410 */     jsonObject.put("payload", getPayload().toBase64URL().toString());
/* 411 */     jsonObject.putAll(((Signature)getSignatures().get(0)).toJSONObject());
/* 412 */     return jsonObject;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String serializeGeneral() {
/* 418 */     return JSONObjectUtils.toJSONString(toGeneralJSONObject());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String serializeFlattened() {
/* 424 */     return JSONObjectUtils.toJSONString(toFlattenedJSONObject());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static JWSHeader parseJWSHeader(Map<String, Object> jsonObject) throws ParseException {
/* 431 */     Base64URL protectedHeader = JSONObjectUtils.getBase64URL(jsonObject, "protected");
/*     */     
/* 433 */     if (protectedHeader == null) {
/* 434 */       throw new ParseException("Missing protected header (required by this library)", 0);
/*     */     }
/*     */     
/*     */     try {
/* 438 */       return JWSHeader.parse(protectedHeader);
/* 439 */     } catch (ParseException e) {
/* 440 */       if ("Not a JWS header".equals(e.getMessage()))
/*     */       {
/* 442 */         throw new ParseException("Missing JWS \"alg\" parameter in protected header (required by this library)", 0);
/*     */       }
/* 444 */       throw e;
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
/*     */   public static JWSObjectJSON parse(Map<String, Object> jsonObject) throws ParseException {
/* 465 */     Base64URL payloadB64URL = JSONObjectUtils.getBase64URL(jsonObject, "payload");
/*     */     
/* 467 */     if (payloadB64URL == null) {
/* 468 */       throw new ParseException("Missing payload", 0);
/*     */     }
/*     */     
/* 471 */     Payload payload = new Payload(payloadB64URL);
/*     */ 
/*     */     
/* 474 */     Base64URL topLevelSignatureB64 = JSONObjectUtils.getBase64URL(jsonObject, "signature");
/*     */     
/* 476 */     boolean flattened = (topLevelSignatureB64 != null);
/*     */     
/* 478 */     List<Signature> signatureList = new LinkedList<>();
/*     */     
/* 480 */     if (flattened) {
/*     */       
/* 482 */       JWSHeader jwsHeader = parseJWSHeader(jsonObject);
/*     */       
/* 484 */       UnprotectedHeader unprotectedHeader = UnprotectedHeader.parse(JSONObjectUtils.getJSONObject(jsonObject, "header"));
/*     */ 
/*     */ 
/*     */       
/* 488 */       if (jsonObject.get("signatures") != null) {
/* 489 */         throw new ParseException("The \"signatures\" member must not be present in flattened JWS JSON serialization", 0);
/*     */       }
/*     */       
/*     */       try {
/* 493 */         HeaderValidation.ensureDisjoint(jwsHeader, unprotectedHeader);
/* 494 */       } catch (IllegalHeaderException e) {
/* 495 */         throw new ParseException(e.getMessage(), 0);
/*     */       } 
/*     */       
/* 498 */       signatureList.add(new Signature(payload, jwsHeader, unprotectedHeader, topLevelSignatureB64));
/*     */     } else {
/*     */       
/* 501 */       Map[] arrayOfMap = JSONObjectUtils.getJSONObjectArray(jsonObject, "signatures");
/* 502 */       if (arrayOfMap == null || arrayOfMap.length == 0) {
/* 503 */         throw new ParseException("The \"signatures\" member must be present in general JSON Serialization", 0);
/*     */       }
/*     */       
/* 506 */       for (Map<String, Object> signatureJSONObject : arrayOfMap) {
/*     */         
/* 508 */         JWSHeader jwsHeader = parseJWSHeader(signatureJSONObject);
/*     */         
/* 510 */         UnprotectedHeader unprotectedHeader = UnprotectedHeader.parse(JSONObjectUtils.getJSONObject(signatureJSONObject, "header"));
/*     */         
/*     */         try {
/* 513 */           HeaderValidation.ensureDisjoint(jwsHeader, unprotectedHeader);
/* 514 */         } catch (IllegalHeaderException e) {
/* 515 */           throw new ParseException(e.getMessage(), 0);
/*     */         } 
/*     */         
/* 518 */         Base64URL signatureB64 = JSONObjectUtils.getBase64URL(signatureJSONObject, "signature");
/*     */         
/* 520 */         if (signatureB64 == null) {
/* 521 */           throw new ParseException("Missing \"signature\" member", 0);
/*     */         }
/*     */         
/* 524 */         signatureList.add(new Signature(payload, jwsHeader, unprotectedHeader, signatureB64));
/*     */       } 
/*     */     } 
/*     */     
/* 528 */     return new JWSObjectJSON(payload, signatureList);
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
/*     */   public static JWSObjectJSON parse(String json) throws ParseException {
/* 546 */     return parse(JSONObjectUtils.parse(json));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\JWSObjectJSON.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */