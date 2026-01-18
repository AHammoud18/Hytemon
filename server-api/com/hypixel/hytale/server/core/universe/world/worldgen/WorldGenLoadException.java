/*    */ package com.hypixel.hytale.server.core.universe.world.worldgen;
/*    */ 
/*    */ import com.hypixel.hytale.common.util.ExceptionUtil;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenLoadException
/*    */   extends Exception
/*    */ {
/*    */   public WorldGenLoadException(@Nonnull String message) {
/* 15 */     super(Objects.<String>requireNonNull(message));
/*    */   }
/*    */   
/*    */   public WorldGenLoadException(@Nonnull String message, Throwable cause) {
/* 19 */     super(Objects.<String>requireNonNull(message), cause);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public String getTraceMessage() {
/* 24 */     return getTraceMessage(", ");
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public String getTraceMessage(@Nonnull String joiner) {
/* 29 */     return ExceptionUtil.combineMessages(this, joiner);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\worldgen\WorldGenLoadException.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */