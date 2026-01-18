/*     */ package com.nimbusds.jose;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @ThreadSafe
/*     */ public class PlainObject
/*     */   extends JOSEObject
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private final PlainHeader header;
/*     */   
/*     */   public PlainObject(Payload payload) {
/*  59 */     setPayload(Objects.<Payload>requireNonNull(payload));
/*  60 */     this.header = new PlainHeader();
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
/*     */   public PlainObject(PlainHeader header, Payload payload) {
/*  73 */     this.header = Objects.<PlainHeader>requireNonNull(header);
/*  74 */     setPayload(Objects.<Payload>requireNonNull(payload));
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
/*     */   public PlainObject(Base64URL firstPart, Base64URL secondPart) throws ParseException {
/*     */     try {
/*  93 */       this.header = PlainHeader.parse(Objects.<Base64URL>requireNonNull(firstPart));
/*     */     }
/*  95 */     catch (ParseException e) {
/*     */       
/*  97 */       throw new ParseException("Invalid unsecured header: " + e.getMessage(), 0);
/*     */     } 
/*     */     
/* 100 */     setPayload(new Payload(Objects.<Base64URL>requireNonNull(secondPart)));
/*     */     
/* 102 */     setParsedParts(new Base64URL[] { firstPart, secondPart, null });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PlainHeader getHeader() {
/* 109 */     return this.header;
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
/*     */   public String serialize() {
/* 127 */     return this.header.toBase64URL().toString() + '.' + getPayload().toBase64URL().toString() + '.';
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
/*     */   public static PlainObject parse(String s) throws ParseException {
/* 145 */     Base64URL[] parts = JOSEObject.split(s);
/*     */     
/* 147 */     if (!parts[2].toString().isEmpty())
/*     */     {
/* 149 */       throw new ParseException("Unexpected third Base64URL part", 0);
/*     */     }
/*     */     
/* 152 */     return new PlainObject(parts[0], parts[1]);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\PlainObject.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */