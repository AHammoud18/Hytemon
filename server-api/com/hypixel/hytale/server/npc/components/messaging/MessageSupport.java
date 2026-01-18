/*    */ package com.hypixel.hytale.server.npc.components.messaging;
/*    */ 
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ 
/*    */ public abstract class MessageSupport
/*    */   implements Component<EntityStore>
/*    */ {
/*    */   public boolean isMessageQueued(int messageIndex) {
/* 10 */     if (getMessageSlots() == null) return false;
/*    */     
/* 12 */     return getMessageSlots()[messageIndex].isActivated();
/*    */   }
/*    */   
/*    */   public boolean isMessageEnabled(int messageIndex) {
/* 16 */     if (getMessageSlots() == null) return false;
/*    */     
/* 18 */     return getMessageSlots()[messageIndex].isEnabled();
/*    */   }
/*    */   
/*    */   public abstract NPCMessage[] getMessageSlots();
/*    */   
/*    */   public abstract Component<EntityStore> clone();
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\components\messaging\MessageSupport.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */