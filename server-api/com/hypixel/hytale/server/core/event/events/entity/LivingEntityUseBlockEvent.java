/*    */ package com.hypixel.hytale.server.core.event.events.entity;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.event.IEvent;
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
/*    */ @Deprecated(forRemoval = true)
/*    */ public class LivingEntityUseBlockEvent
/*    */   implements IEvent<String>
/*    */ {
/*    */   private Ref<EntityStore> ref;
/*    */   private String blockType;
/*    */   
/*    */   public LivingEntityUseBlockEvent(Ref<EntityStore> ref, String blockType) {
/* 23 */     this.ref = ref;
/* 24 */     this.blockType = blockType;
/*    */   }
/*    */   
/*    */   public String getBlockType() {
/* 28 */     return this.blockType;
/*    */   }
/*    */   
/*    */   public Ref<EntityStore> getRef() {
/* 32 */     return this.ref;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 38 */     return "LivingEntityUseBlockEvent{blockType=" + this.blockType + "} " + super
/*    */       
/* 40 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\event\events\entity\LivingEntityUseBlockEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */