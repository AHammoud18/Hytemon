/*    */ package com.hypixel.hytale.server.core.util;
/*    */ 
/*    */ import java.util.UUID;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UUIDUtil
/*    */ {
/* 13 */   public static final UUID EMPTY_UUID = new UUID(0L, 0L);
/*    */   
/*    */   @Nonnull
/*    */   public static UUID generateVersion3UUID() {
/* 17 */     UUID out = UUID.randomUUID();
/*    */     
/* 19 */     if (out.version() != 3) {
/* 20 */       long msb = out.getMostSignificantBits();
/* 21 */       msb &= 0xFFFFFFFFFFFFBFFFL;
/* 22 */       msb |= 0x3000L;
/* 23 */       out = new UUID(msb, out.getLeastSignificantBits());
/*    */     } 
/*    */     
/* 26 */     return out;
/*    */   }
/*    */   
/*    */   public static boolean isEmptyOrNull(UUID uuid) {
/* 30 */     return (uuid == null || uuid.equals(EMPTY_UUID));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\util\UUIDUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */