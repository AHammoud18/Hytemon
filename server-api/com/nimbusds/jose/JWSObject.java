/*     */ package com.nimbusds.jose;
/*     */ 
/*     */ import com.nimbusds.jose.shaded.jcip.ThreadSafe;
/*     */ import com.nimbusds.jose.util.Base64URL;
/*     */ import com.nimbusds.jose.util.StandardCharset;
/*     */ import java.security.Signature;
/*     */ import java.text.ParseException;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.atomic.AtomicReference;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class JWSObject
/*     */   extends JOSEObject
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private final JWSHeader header;
/*     */   private final String signingInputString;
/*     */   private Base64URL signature;
/*     */   
/*     */   public enum State
/*     */   {
/*  58 */     UNSIGNED,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  65 */     SIGNED,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  72 */     VERIFIED;
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
/*  97 */   private final AtomicReference<State> state = new AtomicReference<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JWSObject(JWSHeader header, Payload payload) {
/* 110 */     this.header = Objects.<JWSHeader>requireNonNull(header);
/* 111 */     setPayload(Objects.<Payload>requireNonNull(payload));
/* 112 */     this.signingInputString = composeSigningInput();
/* 113 */     this.signature = null;
/* 114 */     this.state.set(State.UNSIGNED);
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
/*     */   public JWSObject(Base64URL firstPart, Base64URL secondPart, Base64URL thirdPart) throws ParseException {
/* 134 */     this(firstPart, new Payload(secondPart), thirdPart);
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
/*     */   public JWSObject(Base64URL firstPart, Payload payload, Base64URL thirdPart) throws ParseException {
/*     */     try {
/* 155 */       this.header = JWSHeader.parse(firstPart);
/* 156 */     } catch (ParseException e) {
/* 157 */       throw new ParseException("Invalid JWS header: " + e.getMessage(), 0);
/*     */     } 
/*     */     
/* 160 */     setPayload(Objects.<Payload>requireNonNull(payload));
/*     */     
/* 162 */     this.signingInputString = composeSigningInput();
/*     */     
/* 164 */     if (thirdPart.toString().trim().isEmpty()) {
/* 165 */       throw new ParseException("The signature must not be empty", 0);
/*     */     }
/*     */     
/* 168 */     this.signature = thirdPart;
/* 169 */     this.state.set(State.SIGNED);
/*     */     
/* 171 */     if (getHeader().isBase64URLEncodePayload()) {
/* 172 */       setParsedParts(new Base64URL[] { firstPart, payload.toBase64URL(), thirdPart });
/*     */     } else {
/* 174 */       setParsedParts(new Base64URL[] { firstPart, new Base64URL(""), thirdPart });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public JWSHeader getHeader() {
/* 181 */     return this.header;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String composeSigningInput() {
/* 192 */     if (this.header.isBase64URLEncodePayload()) {
/* 193 */       return getHeader().toBase64URL().toString() + '.' + getPayload().toBase64URL().toString();
/*     */     }
/* 195 */     return getHeader().toBase64URL().toString() + '.' + getPayload().toString();
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
/*     */   public byte[] getSigningInput() {
/* 207 */     return this.signingInputString.getBytes(StandardCharset.UTF_8);
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
/*     */   public Base64URL getSignature() {
/* 219 */     return this.signature;
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
/* 230 */     return this.state.get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void ensureUnsignedState() {
/* 241 */     if (this.state.get() != State.UNSIGNED)
/*     */     {
/* 243 */       throw new IllegalStateException("The JWS object must be in an unsigned state");
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
/*     */   private void ensureSignedOrVerifiedState() {
/* 257 */     if (this.state.get() != State.SIGNED && this.state.get() != State.VERIFIED)
/*     */     {
/* 259 */       throw new IllegalStateException("The JWS object must be in a signed or verified state");
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
/*     */   private void ensureJWSSignerSupport(JWSSigner signer) throws JOSEException {
/* 273 */     if (!signer.supportedJWSAlgorithms().contains(getHeader().getAlgorithm()))
/*     */     {
/* 275 */       throw new JOSEException("The " + getHeader().getAlgorithm() + " algorithm is not allowed or supported by the JWS signer: Supported algorithms: " + signer
/* 276 */           .supportedJWSAlgorithms());
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
/*     */   public synchronized void sign(JWSSigner signer) throws JOSEException {
/* 294 */     ensureUnsignedState();
/*     */     
/* 296 */     ensureJWSSignerSupport(signer);
/*     */     
/*     */     try {
/* 299 */       this.signature = signer.sign(getHeader(), getSigningInput());
/*     */     }
/* 301 */     catch (ActionRequiredForJWSCompletionException e) {
/*     */       
/* 303 */       throw new ActionRequiredForJWSCompletionException(e
/* 304 */           .getMessage(), e
/* 305 */           .getTriggeringOption(), new CompletableJWSObjectSigning()
/*     */           {
/*     */             
/*     */             public Signature getInitializedSignature()
/*     */             {
/* 310 */               return e.getCompletableJWSObjectSigning().getInitializedSignature();
/*     */             }
/*     */ 
/*     */             
/*     */             public Base64URL complete() throws JOSEException {
/* 315 */               JWSObject.this.signature = e.getCompletableJWSObjectSigning().complete();
/* 316 */               JWSObject.this.state.set(JWSObject.State.SIGNED);
/* 317 */               return JWSObject.this.signature;
/*     */             }
/*     */           });
/*     */     
/*     */     }
/* 322 */     catch (JOSEException e) {
/*     */       
/* 324 */       throw e;
/*     */     }
/* 326 */     catch (Exception e) {
/*     */ 
/*     */ 
/*     */       
/* 330 */       throw new JOSEException(e.getMessage(), e);
/*     */     } 
/*     */     
/* 333 */     this.state.set(State.SIGNED);
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
/*     */   public synchronized boolean verify(JWSVerifier verifier) throws JOSEException {
/*     */     boolean verified;
/* 355 */     ensureSignedOrVerifiedState();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 360 */       verified = verifier.verify(getHeader(), getSigningInput(), getSignature());
/*     */     }
/* 362 */     catch (JOSEException e) {
/*     */       
/* 364 */       throw e;
/*     */     }
/* 366 */     catch (Exception e) {
/*     */ 
/*     */ 
/*     */       
/* 370 */       throw new JOSEException(e.getMessage(), e);
/*     */     } 
/*     */     
/* 373 */     if (verified)
/*     */     {
/* 375 */       this.state.set(State.VERIFIED);
/*     */     }
/*     */     
/* 378 */     return verified;
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
/*     */   public String serialize() {
/* 400 */     return serialize(false);
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
/*     */   public String serialize(boolean detachedPayload) {
/* 421 */     ensureSignedOrVerifiedState();
/*     */     
/* 423 */     if (detachedPayload) {
/* 424 */       return this.header.toBase64URL().toString() + '.' + '.' + this.signature.toString();
/*     */     }
/*     */     
/* 427 */     return this.signingInputString + '.' + this.signature.toString();
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
/*     */   public static JWSObject parse(String s) throws ParseException {
/* 445 */     Base64URL[] parts = JOSEObject.split(s);
/*     */     
/* 447 */     if (parts.length != 3)
/*     */     {
/* 449 */       throw new ParseException("Unexpected number of Base64URL parts, must be three", 0);
/*     */     }
/*     */     
/* 452 */     return new JWSObject(parts[0], parts[1], parts[2]);
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
/*     */   public static JWSObject parse(String s, Payload detachedPayload) throws ParseException {
/* 474 */     Base64URL[] parts = JOSEObject.split(s);
/*     */     
/* 476 */     if (parts.length != 3) {
/* 477 */       throw new ParseException("Unexpected number of Base64URL parts, must be three", 0);
/*     */     }
/*     */     
/* 480 */     if (!parts[1].toString().isEmpty()) {
/* 481 */       throw new ParseException("The payload Base64URL part must be empty", 0);
/*     */     }
/*     */     
/* 484 */     return new JWSObject(parts[0], detachedPayload, parts[2]);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\JWSObject.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */