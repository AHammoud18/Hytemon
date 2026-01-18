/*    */ package com.hypixel.hytale.server.npc.asset.builder.holder;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderParameters;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.validators.DoubleValidator;
/*    */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FloatHolder
/*    */   extends DoubleHolderBase
/*    */ {
/*    */   public void validate(ExecutionContext context) {
/* 17 */     get(context);
/*    */   }
/*    */   
/*    */   public void readJSON(JsonElement optionalJsonElement, float defaultValue, DoubleValidator validator, String name, @Nonnull BuilderParameters builderParameters) {
/* 21 */     readJSON(optionalJsonElement, defaultValue, validator, name, builderParameters);
/*    */   }
/*    */   
/*    */   public float get(ExecutionContext executionContext) {
/* 25 */     double value = rawGet(executionContext);
/* 26 */     validateRelations(executionContext, value);
/*    */ 
/*    */ 
/*    */     
/* 30 */     return (float)value;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\holder\FloatHolder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */