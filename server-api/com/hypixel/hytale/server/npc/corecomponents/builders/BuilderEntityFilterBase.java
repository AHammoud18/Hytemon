/*    */ package com.hypixel.hytale.server.npc.corecomponents.builders;
/*    */ 
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
/*    */ public abstract class BuilderEntityFilterBase
/*    */   extends BuilderEntityFilterWithToggle
/*    */ {
/*    */   public boolean validate(String configName, @Nonnull NPCLoadTimeValidationHelper validationHelper, ExecutionContext context, Scope globalScope, @Nonnull List<String> errors) {
/* 17 */     boolean result = super.validate(configName, validationHelper, context, globalScope, errors);
/* 18 */     String type = getTypeName();
/* 19 */     if (validationHelper.isFilterExternallyProvided(type)) {
/* 20 */       result = false;
/* 21 */       errors.add(String.format("%s: includes a filter of type %s which is already externally provided (such as by a prioritiser) at %s", new Object[] { configName, type, getBreadCrumbs() }));
/*    */     } 
/* 23 */     if (validationHelper.hasSeenFilter(type)) {
/* 24 */       result = false;
/* 25 */       errors.add(String.format("%s: has defined a filter of type %s more than once at %s", new Object[] { configName, type, getBreadCrumbs() }));
/*    */     } 
/* 27 */     return result;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\builders\BuilderEntityFilterBase.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */