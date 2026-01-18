/*    */ package com.hypixel.hytale.server.worldgen.util.condition.flag;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ConstantInt2Flags
/*    */   implements Int2FlagsCondition {
/*    */   public ConstantInt2Flags(int result) {
/*  8 */     this.result = result;
/*    */   }
/*    */   private final int result;
/*    */   public int eval(int input) {
/* 12 */     return this.result;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 18 */     return "ConstantInt2Flags{result=" + this.result + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldge\\util\condition\flag\ConstantInt2Flags.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */