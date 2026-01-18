/*    */ package com.hypixel.hytale.server.npc.components.messaging;
/*    */ 
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*    */ import com.hypixel.hytale.server.npc.blackboard.view.event.EventNotification;
/*    */ import com.hypixel.hytale.server.npc.blackboard.view.event.block.BlockEventType;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PlayerBlockEventSupport
/*    */   extends EventSupport<BlockEventType, EventNotification>
/*    */   implements Component<EntityStore>
/*    */ {
/*    */   public static ComponentType<EntityStore, PlayerBlockEventSupport> getComponentType() {
/* 20 */     return NPCPlugin.get().getPlayerBlockEventSupportComponentType();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Component<EntityStore> clone() {
/* 26 */     PlayerBlockEventSupport support = new PlayerBlockEventSupport();
/* 27 */     cloneTo(support);
/* 28 */     return support;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\components\messaging\PlayerBlockEventSupport.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */