/*    */ package com.hypixel.hytale.server.core.event.events.entity;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.entity.Entity;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityRemoveEvent
/*    */   extends EntityEvent<Entity, String>
/*    */ {
/*    */   public EntityRemoveEvent(Entity entity) {
/* 12 */     super(entity);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 18 */     return "EntityRemoveEvent{} " + super.toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\event\events\entity\EntityRemoveEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */