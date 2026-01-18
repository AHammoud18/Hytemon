/*    */ package com.hypixel.hytale.server.npc.storage;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class PersistentParameter<Type>
/*    */ {
/*    */   public void set(@Nonnull Ref<EntityStore> ownerRef, @Nullable Type value, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 32 */     set0(value);
/*    */     
/* 34 */     TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(ownerRef, TransformComponent.getComponentType());
/* 35 */     if (transformComponent != null)
/* 36 */       transformComponent.markChunkDirty(componentAccessor); 
/*    */   }
/*    */   
/*    */   protected abstract void set0(@Nullable Type paramType);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\storage\PersistentParameter.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */