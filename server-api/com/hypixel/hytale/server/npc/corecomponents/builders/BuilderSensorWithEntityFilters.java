/*    */ package com.hypixel.hytale.server.npc.corecomponents.builders;
/*    */ 
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderContext;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderObjectListHelper;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderValidationHelper;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.ComponentContext;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.InstructionContextHelper;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.IEntityFilter;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.ISensorEntityPrioritiser;
/*    */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*    */ import com.hypixel.hytale.server.npc.util.expression.Scope;
/*    */ import com.hypixel.hytale.server.npc.validators.NPCLoadTimeValidationHelper;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public abstract class BuilderSensorWithEntityFilters extends BuilderSensorBase {
/* 20 */   protected final BuilderObjectListHelper<IEntityFilter> filters = new BuilderObjectListHelper(IEntityFilter.class, (BuilderContext)this);
/*    */ 
/*    */   
/*    */   public boolean validate(String configName, @Nonnull NPCLoadTimeValidationHelper validationHelper, @Nonnull ExecutionContext context, Scope globalScope, @Nonnull List<String> errors) {
/* 24 */     boolean result = super.validate(configName, validationHelper, context, globalScope, errors);
/* 25 */     validationHelper.pushFilterSet();
/* 26 */     result &= this.filters.validate(configName, validationHelper, this.builderManager, context, globalScope, errors);
/* 27 */     validationHelper.popFilterSet();
/* 28 */     return result;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public IEntityFilter[] getFilters(@Nonnull BuilderSupport support, @Nullable ISensorEntityPrioritiser prioritiser, ComponentContext context) {
/* 33 */     if (!this.filters.isPresent() || this.filters.isEmpty()) {
/* 34 */       if (prioritiser == null || !prioritiser.providesFilters()) return IEntityFilter.EMPTY_ARRAY;
/*    */       
/* 36 */       ObjectArrayList<IEntityFilter> objectArrayList = new ObjectArrayList();
/* 37 */       prioritiser.buildProvidedFilters((List)objectArrayList);
/* 38 */       return (IEntityFilter[])objectArrayList.toArray(x$0 -> new IEntityFilter[x$0]);
/*    */     } 
/* 40 */     support.setCurrentComponentContext(context);
/* 41 */     List<IEntityFilter> builtFilters = this.filters.build(support);
/* 42 */     if (prioritiser != null) prioritiser.buildProvidedFilters(builtFilters); 
/* 43 */     support.setCurrentComponentContext(null);
/* 44 */     return (IEntityFilter[])builtFilters.toArray(x$0 -> new IEntityFilter[x$0]);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   protected BuilderValidationHelper createFilterValidationHelper(ComponentContext context) {
/* 49 */     InstructionContextHelper instructionContextHelper = new InstructionContextHelper(isCreatingDescriptor() ? null : getInstructionContextHelper().getInstructionContext());
/* 50 */     instructionContextHelper.setComponentContext(context);
/* 51 */     return new BuilderValidationHelper(this.fileName, null, this.internalReferenceResolver, null, instructionContextHelper, this.extraInfo, this.evaluators, this.readErrors);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\builders\BuilderSensorWithEntityFilters.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */