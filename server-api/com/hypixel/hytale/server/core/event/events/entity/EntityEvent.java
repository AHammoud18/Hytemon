/*    */ package com.hypixel.hytale.server.core.event.events.entity;
/*    */ 
/*    */ import com.hypixel.hytale.event.IEvent;
/*    */ import com.hypixel.hytale.server.core.entity.Entity;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class EntityEvent<EntityType extends Entity, KeyType>
/*    */   implements IEvent<KeyType>
/*    */ {
/*    */   private final EntityType entity;
/*    */   
/*    */   public EntityEvent(EntityType entity) {
/* 16 */     this.entity = entity;
/*    */   }
/*    */   
/*    */   public EntityType getEntity() {
/* 20 */     return this.entity;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 26 */     return "EntityEvent{entity=" + String.valueOf(this.entity) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\event\events\entity\EntityEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */