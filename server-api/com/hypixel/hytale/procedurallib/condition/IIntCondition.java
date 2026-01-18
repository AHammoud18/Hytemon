/*    */ package com.hypixel.hytale.procedurallib.condition;
/*    */ 
/*    */ import com.hypixel.hytale.procedurallib.util.IntToIntFunction;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface IIntCondition
/*    */ {
/*    */   boolean eval(int paramInt);
/*    */   
/*    */   default boolean eval(int seed, @Nonnull IntToIntFunction seedFunction) {
/* 16 */     return eval(seedFunction.applyAsInt(seed));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\condition\IIntCondition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */