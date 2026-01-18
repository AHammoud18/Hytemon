/*    */ package com.hypixel.hytale.server.npc.asset.builder;
/*    */ 
/*    */ import com.google.gson.JsonArray;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*    */ import com.hypixel.hytale.server.npc.util.expression.Scope;
/*    */ import com.hypixel.hytale.server.npc.validators.NPCLoadTimeValidationHelper;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class BuilderObjectArrayHelper<T, U>
/*    */   extends BuilderObjectHelper<T>
/*    */ {
/*    */   @Nullable
/*    */   protected BuilderObjectReferenceHelper[] builders;
/*    */   protected String label;
/*    */   
/*    */   public BuilderObjectArrayHelper(Class<?> classType, BuilderContext owner) {
/* 22 */     super(classType, owner);
/*    */   }
/*    */ 
/*    */   
/*    */   public void readConfig(@Nonnull JsonElement data, @Nonnull BuilderManager builderManager, @Nonnull BuilderParameters builderParameters, @Nonnull BuilderValidationHelper builderValidationHelper) {
/* 27 */     super.readConfig(data, builderManager, builderParameters, builderValidationHelper);
/* 28 */     if (data.isJsonNull()) {
/* 29 */       this.builders = null;
/*    */     } else {
/* 31 */       if (!data.isJsonArray()) {
/* 32 */         String string = data.toString();
/* 33 */         throw new IllegalArgumentException(String.format("Expected a JSON array of '%s' at %s (JSON: %s)", new Object[] { this.classType
/* 34 */                 .getSimpleName(), getBreadCrumbs(), (string.length() > 60) ? (string.substring(60) + "...") : string }));
/*    */       } 
/* 36 */       JsonArray array = data.getAsJsonArray();
/* 37 */       BuilderFactory<U> factory = builderManager.getFactory(this.classType);
/* 38 */       this.builders = new BuilderObjectReferenceHelper[array.size()];
/* 39 */       int index = 0;
/* 40 */       for (JsonElement element : array) {
/* 41 */         BuilderObjectReferenceHelper<U> builderObjectReferenceHelper = createReferenceHelper();
/* 42 */         builderObjectReferenceHelper.readConfig(element, factory, builderManager, builderParameters, builderValidationHelper);
/* 43 */         if (!builderObjectReferenceHelper.isPresent()) {
/* 44 */           throw new IllegalStateException("Missing builder reference at " + getBreadCrumbs() + ": " + builderParameters.getFileName());
/*    */         }
/* 46 */         this.builders[index++] = builderObjectReferenceHelper;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean validate(String configName, NPCLoadTimeValidationHelper loadTimeValidationHelper, @Nonnull BuilderManager manager, @Nonnull ExecutionContext context, Scope globalScope, @Nonnull List<String> errors) {
/* 53 */     if (hasNoElements()) return true;
/*    */     
/* 55 */     boolean result = true;
/* 56 */     for (BuilderObjectReferenceHelper builder : this.builders) {
/* 57 */       if (!builder.excludeFromRegularBuild())
/* 58 */         result &= builder.validate(configName, loadTimeValidationHelper, manager, context, globalScope, errors); 
/*    */     } 
/* 60 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isPresent() {
/* 65 */     return (this.builders != null);
/*    */   }
/*    */   
/*    */   public boolean isEmpty() {
/* 69 */     return (isPresent() && this.builders.length == 0);
/*    */   }
/*    */   
/*    */   public boolean hasNoElements() {
/* 73 */     return (this.builders == null || this.builders.length == 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getLabel() {
/* 78 */     return this.label;
/*    */   }
/*    */   
/*    */   public void setLabel(String label) {
/* 82 */     this.label = label;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   protected BuilderObjectReferenceHelper<U> createReferenceHelper() {
/* 87 */     return new BuilderObjectReferenceHelper<>(this.classType, this);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\BuilderObjectArrayHelper.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */