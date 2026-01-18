/*    */ package com.hypixel.hytale.server.npc.components;
/*    */ 
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FailedSpawnComponent
/*    */   implements Component<EntityStore>
/*    */ {
/*    */   public static ComponentType<EntityStore, FailedSpawnComponent> getComponentType() {
/* 15 */     return NPCPlugin.get().getFailedSpawnComponentType();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Component<EntityStore> clone() {
/* 25 */     return new FailedSpawnComponent();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\components\FailedSpawnComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */