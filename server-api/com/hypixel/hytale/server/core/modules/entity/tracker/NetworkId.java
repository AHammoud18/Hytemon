/*    */ package com.hypixel.hytale.server.core.modules.entity.tracker;
/*    */ 
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public final class NetworkId
/*    */   implements Component<EntityStore> {
/*    */   @Nonnull
/*    */   public static ComponentType<EntityStore, NetworkId> getComponentType() {
/* 13 */     return EntityModule.get().getNetworkIdComponentType();
/*    */   }
/*    */   
/*    */   private final int id;
/*    */   
/*    */   public NetworkId(int id) {
/* 19 */     this.id = id;
/*    */   }
/*    */   
/*    */   public int getId() {
/* 23 */     return this.id;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Component<EntityStore> clone() {
/* 29 */     return this;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\tracker\NetworkId.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */