/*    */ package com.hypixel.hytale.server.worldgen.util.condition;
/*    */ 
/*    */ import com.hypixel.hytale.server.worldgen.util.BlockFluidEntry;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DefaultBlockMaskCondition
/*    */   implements BlockMaskCondition
/*    */ {
/* 13 */   public static final DefaultBlockMaskCondition DEFAULT_TRUE = new DefaultBlockMaskCondition(true);
/* 14 */   public static final DefaultBlockMaskCondition DEFAULT_FALSE = new DefaultBlockMaskCondition(false);
/*    */   
/*    */   private final boolean result;
/*    */   
/*    */   public DefaultBlockMaskCondition(boolean result) {
/* 19 */     this.result = result;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean eval(int currentBlockId, int currentFluidId, BlockFluidEntry next) {
/* 24 */     return this.result;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 30 */     return "DefaultBiIntCondition{result=" + this.result + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldge\\util\condition\DefaultBlockMaskCondition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */