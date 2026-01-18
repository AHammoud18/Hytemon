/*    */ package com.hypixel.hytale.server.npc.asset.builder;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.holder.StringHolder;
/*    */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BuilderObjectStaticHelper<T>
/*    */   extends BuilderObjectReferenceHelper<T>
/*    */ {
/*    */   public BuilderObjectStaticHelper(Class<?> classType, BuilderContext owner) {
/* 16 */     super(classType, owner);
/*    */   }
/*    */ 
/*    */   
/*    */   public void readConfig(@Nonnull JsonElement data, @Nonnull BuilderManager builderManager, @Nonnull BuilderParameters builderParameters, @Nonnull BuilderValidationHelper builderValidationHelper) {
/* 21 */     super.readConfig(data, builderManager, builderParameters, builderValidationHelper);
/* 22 */     if (!isFinal()) {
/* 23 */       throw new IllegalStateException("Static object must be static, at: " + getBreadCrumbs());
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setInternalReference(StringHolder holder, InternalReferenceResolver referenceResolver) {
/* 29 */     throw new IllegalStateException("Static object cannot contain a reference, at: " + getBreadCrumbs());
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setFileReference(StringHolder holder, JsonObject jsonObject, BuilderManager builderManager) {
/* 34 */     throw new IllegalStateException("Static object cannot contain a reference, at: " + getBreadCrumbs());
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public T staticBuild(@Nonnull BuilderManager manager) {
/* 39 */     return getBuilder(manager, (ExecutionContext)null, (Builder<?>)null).build(null);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\BuilderObjectStaticHelper.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */