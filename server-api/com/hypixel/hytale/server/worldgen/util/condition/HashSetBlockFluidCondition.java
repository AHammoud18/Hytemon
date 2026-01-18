/*    */ package com.hypixel.hytale.server.worldgen.util.condition;
/*    */ 
/*    */ import com.hypixel.hytale.math.util.MathUtil;
/*    */ import com.hypixel.hytale.procedurallib.condition.IBlockFluidCondition;
/*    */ import it.unimi.dsi.fastutil.longs.LongSet;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class HashSetBlockFluidCondition
/*    */   implements IBlockFluidCondition {
/*    */   protected final LongSet set;
/*    */   
/*    */   public HashSetBlockFluidCondition(LongSet set) {
/* 13 */     this.set = set;
/*    */   }
/*    */   
/*    */   public LongSet getSet() {
/* 17 */     return this.set;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean eval(int block, int fluid) {
/* 22 */     return this.set.contains(MathUtil.packLong(block, fluid));
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 28 */     return "HashSetIntCondition{set=" + String.valueOf(this.set) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldge\\util\condition\HashSetBlockFluidCondition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */