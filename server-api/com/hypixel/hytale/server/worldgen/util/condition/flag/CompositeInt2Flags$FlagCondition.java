/*    */ package com.hypixel.hytale.server.worldgen.util.condition.flag;
/*    */ 
/*    */ import com.hypixel.hytale.procedurallib.condition.IIntCondition;
/*    */ import java.util.function.IntBinaryOperator;
/*    */ import javax.annotation.Nonnull;
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
/*    */ public class FlagCondition
/*    */   implements IntBinaryOperator
/*    */ {
/*    */   private final IIntCondition condition;
/*    */   private final FlagOperator operator;
/*    */   private final int flags;
/*    */   
/*    */   public FlagCondition(IIntCondition condition, FlagOperator operator, int flags) {
/* 44 */     this.condition = condition;
/* 45 */     this.operator = operator;
/* 46 */     this.flags = flags;
/*    */   }
/*    */   
/*    */   public int eval(int input, int output) {
/* 50 */     if (this.condition.eval(input)) {
/* 51 */       output = this.operator.apply(output, this.flags);
/*    */     }
/* 53 */     return output;
/*    */   }
/*    */ 
/*    */   
/*    */   public int applyAsInt(int input, int output) {
/* 58 */     return eval(input, output);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 64 */     return "FlagOperator{condition=" + String.valueOf(this.condition) + ", operator=" + String.valueOf(this.operator) + ", flags=" + this.flags + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldge\\util\condition\flag\CompositeInt2Flags$FlagCondition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */