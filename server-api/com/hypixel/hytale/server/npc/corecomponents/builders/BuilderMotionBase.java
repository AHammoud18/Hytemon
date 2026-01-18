/*    */ package com.hypixel.hytale.server.npc.corecomponents.builders;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.Builder;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderBase;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.InstructionType;
/*    */ import com.hypixel.hytale.server.npc.instructions.Motion;
/*    */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*    */ import com.hypixel.hytale.server.npc.util.expression.Scope;
/*    */ import com.hypixel.hytale.server.npc.validators.NPCLoadTimeValidationHelper;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class BuilderMotionBase<T extends Motion>
/*    */   extends BuilderBase<T>
/*    */ {
/*    */   public boolean canRequireFeature() {
/* 22 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public Builder<T> readCommonConfig(JsonElement data) {
/* 27 */     requireInstructionType(InstructionType.MotionAllowedInstructions);
/* 28 */     return super.readCommonConfig(data);
/*    */   }
/*    */ 
/*    */   
/*    */   public final boolean isEnabled(ExecutionContext context) {
/* 33 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean validate(String configName, @Nonnull NPCLoadTimeValidationHelper validationHelper, ExecutionContext context, Scope globalScope, @Nonnull List<String> errors) {
/* 39 */     boolean result = super.validate(configName, validationHelper, context, globalScope, errors);
/* 40 */     if (validationHelper.isParentSensorOnce()) {
/* 41 */       errors.add(String.format("%s: Once is set on a sensor controlling a step with a motion at: %s", new Object[] { configName, getBreadCrumbs() }));
/* 42 */       return false;
/*    */     } 
/* 44 */     return result;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\builders\BuilderMotionBase.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */