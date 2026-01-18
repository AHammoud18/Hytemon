/*    */ package com.hypixel.hytale.server.npc.corecomponents.builders;
/*    */ 
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.ISensorEntityPrioritiser;
/*    */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*    */ import com.hypixel.hytale.server.npc.util.expression.Scope;
/*    */ import com.hypixel.hytale.server.npc.validators.NPCLoadTimeValidationHelper;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class BuilderSensorEntityPrioritiserBase
/*    */   extends BuilderBase<ISensorEntityPrioritiser>
/*    */ {
/*    */   private final Set<String> providedFilterTypes;
/*    */   
/*    */   protected BuilderSensorEntityPrioritiserBase(Set<String> providedFilterTypes) {
/* 21 */     this.providedFilterTypes = providedFilterTypes;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Class<ISensorEntityPrioritiser> category() {
/* 27 */     return ISensorEntityPrioritiser.class;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isEnabled(ExecutionContext context) {
/* 32 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean validate(String configName, @Nonnull NPCLoadTimeValidationHelper validationHelper, ExecutionContext context, Scope globalScope, @Nonnull List<String> errors) {
/* 37 */     validationHelper.setPrioritiserProvidedFilterTypes(this.providedFilterTypes);
/* 38 */     return super.validate(configName, validationHelper, context, globalScope, errors);
/*    */   }
/*    */   
/*    */   protected Set<String> getProvidedFilterTypes() {
/* 42 */     return this.providedFilterTypes;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\builders\BuilderSensorEntityPrioritiserBase.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */