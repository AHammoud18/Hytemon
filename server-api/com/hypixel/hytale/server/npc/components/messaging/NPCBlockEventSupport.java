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
/*    */ public class NPCBlockEventSupport
/*    */   extends EventSupport<BlockEventType, EventNotification>
/*    */   implements Component<EntityStore>
/*    */ {
/*    */   public static ComponentType<EntityStore, NPCBlockEventSupport> getComponentType() {
/* 20 */     return NPCPlugin.get().getNpcBlockEventSupportComponentType();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Component<EntityStore> clone() {
/* 26 */     NPCBlockEventSupport support = new NPCBlockEventSupport();
/* 27 */     cloneTo(support);
/* 28 */     return support;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\components\messaging\NPCBlockEventSupport.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */