/*    */ package com.hypixel.hytale.server.core.util;
/*    */ 
/*    */ import com.hypixel.hytale.protocol.Direction;
/*    */ import com.hypixel.hytale.protocol.Position;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ValidateUtil
/*    */ {
/*    */   public static boolean isSafeDouble(double x) {
/* 16 */     return (!Double.isNaN(x) && Double.isFinite(x));
/*    */   }
/*    */   
/*    */   public static boolean isSafeFloat(float x) {
/* 20 */     return (!Float.isNaN(x) && Float.isFinite(x));
/*    */   }
/*    */   
/*    */   public static boolean isSafePosition(@Nonnull Position position) {
/* 24 */     return (isSafeDouble(position.x) && isSafeDouble(position.y) && isSafeDouble(position.z));
/*    */   }
/*    */   
/*    */   public static boolean isSafeDirection(@Nonnull Direction direction) {
/* 28 */     return (isSafeFloat(direction.yaw) && isSafeFloat(direction.pitch) && isSafeFloat(direction.roll));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\util\ValidateUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */