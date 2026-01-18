/*    */ package com.hypixel.hytale.server.core.modules.entity.system;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Resource;
/*    */ import com.hypixel.hytale.component.ResourceType;
/*    */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.Set;
/*    */ import java.util.concurrent.ConcurrentHashMap;
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
/*    */ public class QueueResource
/*    */   implements Resource<EntityStore>
/*    */ {
/*    */   public static ResourceType<EntityStore, QueueResource> getResourceType() {
/* 31 */     return EntityModule.get().getRespondToHitQueueResourceType();
/*    */   }
/*    */ 
/*    */   
/* 35 */   private final Set<Ref<EntityStore>> queue = ConcurrentHashMap.newKeySet();
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Resource<EntityStore> clone() {
/* 41 */     return new QueueResource();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\system\RespondToHitSystems$QueueResource.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */