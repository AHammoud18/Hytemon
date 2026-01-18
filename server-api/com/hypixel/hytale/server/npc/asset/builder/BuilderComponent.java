/*    */ package com.hypixel.hytale.server.npc.asset.builder;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.codec.schema.SchemaContext;
/*    */ import com.hypixel.hytale.codec.schema.config.BooleanSchema;
/*    */ import com.hypixel.hytale.codec.schema.config.ObjectSchema;
/*    */ import com.hypixel.hytale.codec.schema.config.Schema;
/*    */ import com.hypixel.hytale.codec.schema.config.StringSchema;
/*    */ import com.hypixel.hytale.server.npc.instructions.Action;
/*    */ import com.hypixel.hytale.server.npc.instructions.Motion;
/*    */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*    */ import com.hypixel.hytale.server.npc.util.expression.Scope;
/*    */ import com.hypixel.hytale.server.npc.validators.NPCLoadTimeValidationHelper;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BuilderComponent<T>
/*    */   extends BuilderBase<T>
/*    */ {
/*    */   private final Class<T> classType;
/*    */   @Nonnull
/*    */   private final BuilderObjectReferenceHelper<T> content;
/*    */   
/*    */   public BuilderComponent(Class<T> classType) {
/* 29 */     this.classType = classType;
/* 30 */     this.content = new BuilderObjectReferenceHelper<>(classType, null);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public String getShortDescription() {
/* 36 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public String getLongDescription() {
/* 42 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public T build(@Nonnull BuilderSupport builderSupport) {
/* 47 */     return this.content.build(builderSupport);
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<T> category() {
/* 52 */     return this.classType;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public BuilderDescriptorState getBuilderDescriptorState() {
/* 58 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isEnabled(ExecutionContext context) {
/* 63 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Builder<T> readConfig(@Nonnull JsonElement data) {
/* 69 */     requireObject(data, "Content", this.content, null, null, null, this.validationHelper);
/* 70 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean validate(String configName, @Nonnull NPCLoadTimeValidationHelper validationHelper, @Nonnull ExecutionContext context, Scope globalScope, @Nonnull List<String> errors) {
/* 75 */     return super.validate(configName, validationHelper, context, globalScope, errors) & this.content
/* 76 */       .validate(configName, validationHelper, this.builderManager, context, globalScope, errors);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canRequireFeature() {
/* 81 */     return (this.classType.isAssignableFrom(Action.class) || this.classType.isAssignableFrom(Motion.class));
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Schema toSchema(@Nonnull SchemaContext context) {
/* 87 */     ObjectSchema s = (ObjectSchema)super.toSchema(context);
/* 88 */     Map<String, Schema> props = s.getProperties();
/* 89 */     props.put("Class", new StringSchema());
/* 90 */     props.put("Interface", new StringSchema());
/* 91 */     props.put("Default", new StringSchema());
/* 92 */     props.put("DefaultState", new StringSchema());
/* 93 */     props.put("ResetOnStateChange", new BooleanSchema());
/* 94 */     return (Schema)s;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\BuilderComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */