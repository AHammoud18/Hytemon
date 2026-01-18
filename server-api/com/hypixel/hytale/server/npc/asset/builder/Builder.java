/*    */ package com.hypixel.hytale.server.npc.asset.builder;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.codec.schema.NamedSchema;
/*    */ import com.hypixel.hytale.codec.schema.SchemaContext;
/*    */ import com.hypixel.hytale.codec.schema.SchemaConvertable;
/*    */ import com.hypixel.hytale.codec.schema.config.Schema;
/*    */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*    */ import com.hypixel.hytale.server.npc.util.expression.Scope;
/*    */ import com.hypixel.hytale.server.npc.validators.NPCLoadTimeValidationHelper;
/*    */ import it.unimi.dsi.fastutil.ints.IntSet;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
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
/*    */ public interface Builder<T>
/*    */   extends BuilderContext, SchemaConvertable<Void>, NamedSchema
/*    */ {
/*    */   @Nullable
/*    */   T build(BuilderSupport paramBuilderSupport);
/*    */   
/*    */   boolean validate(String paramString, NPCLoadTimeValidationHelper paramNPCLoadTimeValidationHelper, ExecutionContext paramExecutionContext, Scope paramScope, List<String> paramList);
/*    */   
/*    */   void readConfig(BuilderContext paramBuilderContext, JsonElement paramJsonElement, BuilderManager paramBuilderManager, BuilderParameters paramBuilderParameters, BuilderValidationHelper paramBuilderValidationHelper);
/*    */   
/*    */   void ignoreAttribute(String paramString);
/*    */   
/*    */   Class<T> category();
/*    */   
/*    */   void setTypeName(String paramString);
/*    */   
/*    */   String getTypeName();
/*    */   
/*    */   void setLabel(String paramString);
/*    */   
/*    */   @Nonnull
/*    */   Schema toSchema(@Nonnull SchemaContext paramSchemaContext);
/*    */   
/*    */   BuilderDescriptor getDescriptor(String paramString1, String paramString2, BuilderManager paramBuilderManager);
/*    */   
/*    */   default boolean isDeprecated() {
/* 55 */     return (getBuilderDescriptorState() == BuilderDescriptorState.Deprecated);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   BuilderDescriptorState getBuilderDescriptorState();
/*    */   
/*    */   IntSet getDependencies();
/*    */   
/*    */   default boolean hasDynamicDependencies() {
/* 64 */     return false;
/*    */   }
/*    */   
/*    */   default void addDynamicDependency(int builderIndex) {
/* 68 */     throw new IllegalStateException("Builder: Adding dynamic dependencies is not supported");
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   default IntSet getDynamicDependencies() {
/* 73 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   default void clearDynamicDependencies() {}
/*    */   
/*    */   BuilderParameters getBuilderParameters();
/*    */   
/*    */   FeatureEvaluatorHelper getEvaluatorHelper();
/*    */   
/*    */   StateMappingHelper getStateMappingHelper();
/*    */   
/*    */   InstructionContextHelper getInstructionContextHelper();
/*    */   
/*    */   boolean canRequireFeature();
/*    */   
/*    */   void validateReferencedProvidedFeatures(BuilderManager paramBuilderManager, ExecutionContext paramExecutionContext);
/*    */   
/*    */   boolean excludeFromRegularBuilding();
/*    */   
/*    */   boolean isEnabled(ExecutionContext paramExecutionContext);
/*    */   
/*    */   default boolean isSpawnable() {
/* 96 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\Builder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */