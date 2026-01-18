/*     */ package io.netty.handler.codec.http.websocketx;
/*     */ 
/*     */ import io.netty.util.concurrent.FastThreadLocal;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.util.Base64;
/*     */ import java.util.concurrent.ThreadLocalRandom;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class WebSocketUtil
/*     */ {
/*  30 */   private static final FastThreadLocal<MessageDigest> MD5 = new FastThreadLocal<MessageDigest>()
/*     */     {
/*     */ 
/*     */       
/*     */       protected MessageDigest initialValue() throws Exception
/*     */       {
/*     */         try {
/*  37 */           return MessageDigest.getInstance("MD5");
/*  38 */         } catch (NoSuchAlgorithmException e) {
/*     */           
/*  40 */           throw new InternalError("MD5 not supported on this platform - Outdated?");
/*     */         } 
/*     */       }
/*     */     };
/*     */   
/*  45 */   private static final FastThreadLocal<MessageDigest> SHA1 = new FastThreadLocal<MessageDigest>()
/*     */     {
/*     */ 
/*     */       
/*     */       protected MessageDigest initialValue() throws Exception
/*     */       {
/*     */         try {
/*  52 */           return MessageDigest.getInstance("SHA1");
/*  53 */         } catch (NoSuchAlgorithmException e) {
/*     */           
/*  55 */           throw new InternalError("SHA-1 not supported on this platform - Outdated?");
/*     */         } 
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static byte[] md5(byte[] data) {
/*  68 */     return digest(MD5, data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static byte[] sha1(byte[] data) {
/*  79 */     return digest(SHA1, data);
/*     */   }
/*     */   
/*     */   private static byte[] digest(FastThreadLocal<MessageDigest> digestFastThreadLocal, byte[] data) {
/*  83 */     MessageDigest digest = (MessageDigest)digestFastThreadLocal.get();
/*  84 */     digest.reset();
/*  85 */     return digest.digest(data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String base64(byte[] data) {
/*  95 */     return Base64.getEncoder().encodeToString(data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static byte[] randomBytes(int size) {
/* 105 */     byte[] bytes = new byte[size];
/* 106 */     ThreadLocalRandom.current().nextBytes(bytes);
/* 107 */     return bytes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int randomNumber(int minimum, int maximum) {
/* 118 */     assert minimum < maximum;
/* 119 */     double fraction = ThreadLocalRandom.current().nextDouble();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 140 */     return (int)(minimum + fraction * (maximum - minimum));
/*     */   }
/*     */   
/*     */   static int byteAtIndex(int mask, int index) {
/* 144 */     return mask >> 8 * (3 - index) & 0xFF;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\websocketx\WebSocketUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */