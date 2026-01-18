/*    */ package io.netty.handler.codec.quic;
/*    */ 
/*    */ import io.netty.util.concurrent.FastThreadLocal;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.security.GeneralSecurityException;
/*    */ import java.security.NoSuchAlgorithmException;
/*    */ import java.security.SecureRandom;
/*    */ import java.util.Arrays;
/*    */ import javax.crypto.Mac;
/*    */ import javax.crypto.spec.SecretKeySpec;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class Hmac
/*    */ {
/* 30 */   private static final FastThreadLocal<Mac> MACS = new FastThreadLocal<Mac>()
/*    */     {
/*    */       protected Mac initialValue() {
/* 33 */         return Hmac.newMac();
/*    */       }
/*    */     };
/*    */   
/*    */   private static final String ALGORITM = "HmacSHA256";
/* 38 */   private static final byte[] randomKey = new byte[16];
/*    */   
/*    */   static {
/* 41 */     (new SecureRandom()).nextBytes(randomKey);
/*    */   }
/*    */   
/*    */   private static Mac newMac() {
/*    */     try {
/* 46 */       SecretKeySpec keySpec = new SecretKeySpec(randomKey, "HmacSHA256");
/* 47 */       Mac mac = Mac.getInstance("HmacSHA256");
/* 48 */       mac.init(keySpec);
/* 49 */       return mac;
/* 50 */     } catch (NoSuchAlgorithmException|java.security.InvalidKeyException exception) {
/* 51 */       throw new IllegalStateException(exception);
/*    */     } 
/*    */   }
/*    */   
/*    */   static ByteBuffer sign(ByteBuffer input, int outLength) {
/* 56 */     Mac mac = (Mac)MACS.get();
/* 57 */     mac.reset();
/* 58 */     mac.update(input);
/* 59 */     byte[] signBytes = mac.doFinal();
/* 60 */     if (signBytes.length != outLength) {
/* 61 */       signBytes = Arrays.copyOf(signBytes, outLength);
/*    */     }
/* 63 */     return ByteBuffer.wrap(signBytes);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\Hmac.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */