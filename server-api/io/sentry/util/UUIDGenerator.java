/*    */ package io.sentry.util;
/*    */ 
/*    */ import java.util.UUID;
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
/*    */ public final class UUIDGenerator
/*    */ {
/*    */   public static long randomHalfLengthUUID() {
/* 31 */     Random ng = SentryRandom.current();
/* 32 */     byte[] randomBytes = new byte[8];
/* 33 */     ng.nextBytes(randomBytes);
/*    */     
/* 35 */     randomBytes[6] = (byte)(randomBytes[6] & 0xF);
/*    */     
/* 37 */     randomBytes[6] = (byte)(randomBytes[6] | 0x40);
/* 38 */     long msb = 0L;
/* 39 */     for (int i = 0; i < 8; i++) {
/* 40 */       msb = msb << 8L | (randomBytes[i] & 0xFF);
/*    */     }
/* 42 */     return msb;
/*    */   }
/*    */ 
/*    */   
/*    */   public static UUID randomUUID() {
/* 47 */     Random ng = SentryRandom.current();
/* 48 */     byte[] randomBytes = new byte[16];
/* 49 */     ng.nextBytes(randomBytes);
/*    */     
/* 51 */     randomBytes[6] = (byte)(randomBytes[6] & 0xF);
/*    */     
/* 53 */     randomBytes[6] = (byte)(randomBytes[6] | 0x40);
/*    */     
/* 55 */     randomBytes[8] = (byte)(randomBytes[8] & 0x3F);
/*    */     
/* 57 */     randomBytes[8] = (byte)(randomBytes[8] | 0x80);
/* 58 */     long msb = 0L;
/* 59 */     long lsb = 0L; int i;
/* 60 */     for (i = 0; i < 8; i++) {
/* 61 */       msb = msb << 8L | (randomBytes[i] & 0xFF);
/*    */     }
/* 63 */     for (i = 8; i < 16; i++) {
/* 64 */       lsb = lsb << 8L | (randomBytes[i] & 0xFF);
/*    */     }
/* 66 */     return new UUID(msb, lsb);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentr\\util\UUIDGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */