/*     */ package com.nimbusds.jose.util;
/*     */ 
/*     */ import com.nimbusds.jose.shaded.jcip.Immutable;
/*     */ import java.math.BigInteger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class Base64URL
/*     */   extends Base64
/*     */ {
/*     */   public Base64URL(String base64URL) {
/*  51 */     super(base64URL);
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
/*     */   public boolean equals(Object object) {
/*  66 */     return (object instanceof Base64URL && 
/*  67 */       toString().equals(object.toString()));
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
/*     */   public static Base64URL from(String base64URL) {
/*  82 */     if (base64URL == null) {
/*  83 */       return null;
/*     */     }
/*     */     
/*  86 */     return new Base64URL(base64URL);
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
/*     */   public static Base64URL encode(byte[] bytes) {
/*  99 */     return new Base64URL(Base64Codec.encodeToString(bytes, true));
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
/*     */   public static Base64URL encode(BigInteger bigInt) {
/* 112 */     return encode(BigIntegerUtils.toBytesUnsigned(bigInt));
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
/*     */   public static Base64URL encode(String text) {
/* 126 */     return encode(text.getBytes(StandardCharset.UTF_8));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jos\\util\Base64URL.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */