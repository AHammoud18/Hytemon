/*    */ package com.hypixel.hytale.server.npc.components.messaging;
/*    */ 
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NPCEntityEventSupport
/*    */   extends EntityEventSupport
/*    */   implements Component<EntityStore>
/*    */ {
/*    */   public static ComponentType<EntityStore, NPCEntityEventSupport> getComponentType() {
/* 18 */     return NPCPlugin.get().getNpcEntityEventSupportComponentType();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Component<EntityStore> clone() {
/* 24 */     NPCEntityEventSupport support = new NPCEntityEventSupport();
/* 25 */     cloneTo(support);
/* 26 */     return support;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\components\messaging\NPCEntityEventSupport.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */