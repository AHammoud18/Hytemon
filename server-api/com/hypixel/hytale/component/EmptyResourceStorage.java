/*    */ package com.hypixel.hytale.component;
/*    */ 
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class EmptyResourceStorage
/*    */   implements IResourceStorage
/*    */ {
/* 10 */   private static final EmptyResourceStorage INSTANCE = new EmptyResourceStorage();
/*    */ 
/*    */   
/*    */   public static EmptyResourceStorage get() {
/* 14 */     return INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public <T extends Resource<ECS_TYPE>, ECS_TYPE> CompletableFuture<T> load(@Nonnull Store<ECS_TYPE> store, @Nonnull ComponentRegistry.Data<ECS_TYPE> data, @Nonnull ResourceType<ECS_TYPE, T> resourceType) {
/* 20 */     return CompletableFuture.completedFuture(data.createResource(resourceType));
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public <T extends Resource<ECS_TYPE>, ECS_TYPE> CompletableFuture<Void> save(@Nonnull Store<ECS_TYPE> store, @Nonnull ComponentRegistry.Data<ECS_TYPE> data, @Nonnull ResourceType<ECS_TYPE, T> resourceType, T resource) {
/* 26 */     return CompletableFuture.completedFuture(null);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public <T extends Resource<ECS_TYPE>, ECS_TYPE> CompletableFuture<Void> remove(@Nonnull Store<ECS_TYPE> store, @Nonnull ComponentRegistry.Data<ECS_TYPE> data, @Nonnull ResourceType<ECS_TYPE, T> resourceType) {
/* 32 */     return CompletableFuture.completedFuture(null);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\EmptyResourceStorage.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */