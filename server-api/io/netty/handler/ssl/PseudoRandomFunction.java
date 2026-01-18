/*    */ package io.netty.handler.ssl;
/*    */ 
/*    */ import io.netty.util.internal.EmptyArrays;
/*    */ import io.netty.util.internal.ObjectUtil;
/*    */ import java.security.GeneralSecurityException;
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
/*    */ 
/*    */ final class PseudoRandomFunction
/*    */ {
/*    */   static byte[] hash(byte[] secret, byte[] label, byte[] seed, int length, String algo) {
/* 64 */     ObjectUtil.checkPositiveOrZero(length, "length");
/*    */     try {
/* 66 */       Mac hmac = Mac.getInstance(algo);
/* 67 */       hmac.init(new SecretKeySpec(secret, algo));
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 75 */       int iterations = (int)Math.ceil(length / hmac.getMacLength());
/* 76 */       byte[] expansion = EmptyArrays.EMPTY_BYTES;
/* 77 */       byte[] data = concat(label, seed);
/* 78 */       byte[] A = data;
/* 79 */       for (int i = 0; i < iterations; i++) {
/* 80 */         A = hmac.doFinal(A);
/* 81 */         expansion = concat(expansion, hmac.doFinal(concat(A, data)));
/*    */       } 
/* 83 */       return Arrays.copyOf(expansion, length);
/* 84 */     } catch (GeneralSecurityException e) {
/* 85 */       throw new IllegalArgumentException("Could not find algo: " + algo, e);
/*    */     } 
/*    */   }
/*    */   
/*    */   private static byte[] concat(byte[] first, byte[] second) {
/* 90 */     byte[] result = Arrays.copyOf(first, first.length + second.length);
/* 91 */     System.arraycopy(second, 0, result, first.length, second.length);
/* 92 */     return result;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\PseudoRandomFunction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */