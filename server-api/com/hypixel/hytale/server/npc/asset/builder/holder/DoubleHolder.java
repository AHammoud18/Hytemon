/*    */ package com.hypixel.hytale.server.npc.asset.builder.holder;
/*    */ 
/*    */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DoubleHolder
/*    */   extends DoubleHolderBase
/*    */ {
/*    */   public void validate(ExecutionContext context) {
/* 13 */     get(context);
/*    */   }
/*    */   
/*    */   public double get(ExecutionContext executionContext) {
/* 17 */     double value = rawGet(executionContext);
/* 18 */     validateRelations(executionContext, value);
/*    */ 
/*    */ 
/*    */     
/* 22 */     return value;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\holder\DoubleHolder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */