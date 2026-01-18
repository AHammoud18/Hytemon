/*    */ package com.hypixel.hytale.server.npc.asset.builder;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*    */ import com.hypixel.hytale.server.npc.util.expression.Scope;
/*    */ import com.hypixel.hytale.server.npc.validators.NPCLoadTimeValidationHelper;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class BuilderObjectHelper<T>
/*    */   implements BuilderContext
/*    */ {
/*    */   protected final Class<?> classType;
/*    */   protected BuilderParameters builderParameters;
/*    */   protected final BuilderContext owner;
/*    */   
/*    */   protected BuilderObjectHelper(Class<?> classType, BuilderContext owner) {
/* 20 */     this.owner = owner;
/* 21 */     this.classType = classType;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public abstract T build(BuilderSupport paramBuilderSupport);
/*    */   
/*    */   public abstract boolean validate(String paramString, NPCLoadTimeValidationHelper paramNPCLoadTimeValidationHelper, BuilderManager paramBuilderManager, ExecutionContext paramExecutionContext, Scope paramScope, List<String> paramList);
/*    */   
/*    */   public BuilderContext getOwner() {
/* 31 */     return this.owner;
/*    */   }
/*    */   
/*    */   public final Class<?> getClassType() {
/* 35 */     return this.classType;
/*    */   }
/*    */   
/*    */   public void readConfig(JsonElement data, BuilderManager builderManager, BuilderParameters builderParameters, BuilderValidationHelper builderValidationHelper) {
/* 39 */     this.builderParameters = builderParameters;
/*    */   }
/*    */   
/*    */   public abstract boolean isPresent();
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\BuilderObjectHelper.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */