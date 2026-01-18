/*    */ package com.hypixel.hytale.server.worldgen.util.condition;
/*    */ 
/*    */ import com.hypixel.hytale.procedurallib.condition.IBlockFluidCondition;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class FilteredBlockFluidCondition
/*    */   implements IBlockFluidCondition
/*    */ {
/*    */   private final IBlockFluidCondition filter;
/*    */   private final IBlockFluidCondition condition;
/*    */   
/*    */   public FilteredBlockFluidCondition(int blockId, IBlockFluidCondition condition) {
/* 13 */     this((block, fluid) -> 
/* 14 */         (block == blockId && fluid == 0), condition);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public FilteredBlockFluidCondition(IBlockFluidCondition filter, IBlockFluidCondition condition) {
/* 20 */     this.filter = filter;
/* 21 */     this.condition = condition;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean eval(int block, int fluid) {
/* 26 */     if (this.filter.eval(block, fluid)) return false; 
/* 27 */     return this.condition.eval(block, fluid);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 33 */     return "FilteredBlockFluidCondition{filter=" + String.valueOf(this.filter) + ", condition=" + String.valueOf(this.condition) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldge\\util\condition\FilteredBlockFluidCondition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */