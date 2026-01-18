/*    */ package com.hypixel.hytale.server.npc.corecomponents.entity.filters.builders;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.Builder;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderContext;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderDescriptorState;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderObjectArrayHelper;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderObjectListHelper;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.validators.ArrayNotEmptyValidator;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.validators.ArrayValidator;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.IEntityFilter;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderEntityFilterWithToggle;
/*    */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*    */ import com.hypixel.hytale.server.npc.util.expression.Scope;
/*    */ import com.hypixel.hytale.server.npc.validators.NPCLoadTimeValidationHelper;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public abstract class BuilderEntityFilterMany extends BuilderEntityFilterWithToggle {
/*    */   @Nonnull
/* 21 */   protected BuilderObjectListHelper<IEntityFilter> objectListHelper = new BuilderObjectListHelper(IEntityFilter.class, (BuilderContext)this);
/*    */ 
/*    */ 
/*    */   
/*    */   public void registerTags(@Nonnull Set<String> tags) {
/* 26 */     super.registerTags(tags);
/* 27 */     tags.add("logic");
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Builder<IEntityFilter> readConfig(@Nonnull JsonElement data) {
/* 33 */     requireArray(data, "Filters", (BuilderObjectArrayHelper)this.objectListHelper, (ArrayValidator)ArrayNotEmptyValidator.get(), BuilderDescriptorState.Stable, "List of filters", null, this.validationHelper);
/* 34 */     return (Builder<IEntityFilter>)this;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean validate(String configName, @Nonnull NPCLoadTimeValidationHelper validationHelper, @Nonnull ExecutionContext context, Scope globalScope, @Nonnull List<String> errors) {
/* 39 */     boolean result = super.validate(configName, validationHelper, context, globalScope, errors);
/* 40 */     validationHelper.pushFilterSet();
/* 41 */     result &= this.objectListHelper.validate(configName, validationHelper, this.builderManager, context, globalScope, errors);
/* 42 */     validationHelper.popFilterSet();
/* 43 */     return result;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\entity\filters\builders\BuilderEntityFilterMany.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */