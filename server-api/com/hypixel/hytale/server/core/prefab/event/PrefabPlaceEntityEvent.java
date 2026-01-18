/*    */ package com.hypixel.hytale.server.core.prefab.event;
/*    */ 
/*    */ import com.hypixel.hytale.component.Holder;
/*    */ import com.hypixel.hytale.component.system.EcsEvent;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
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
/*    */ public class PrefabPlaceEntityEvent
/*    */   extends EcsEvent
/*    */ {
/*    */   private final int prefabId;
/*    */   @Nonnull
/*    */   private final Holder<EntityStore> holder;
/*    */   
/*    */   public PrefabPlaceEntityEvent(int prefabId, @Nonnull Holder<EntityStore> holder) {
/* 33 */     this.prefabId = prefabId;
/* 34 */     this.holder = holder;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getPrefabId() {
/* 41 */     return this.prefabId;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Holder<EntityStore> getHolder() {
/* 48 */     return this.holder;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\prefab\event\PrefabPlaceEntityEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */