/*    */ package com.hypixel.hytale.server.npc.components;
/*    */ 
/*    */ import com.hypixel.hytale.common.thread.ticking.Tickable;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class Timers
/*    */   implements Component<EntityStore>
/*    */ {
/*    */   private final Tickable[] timers;
/*    */   
/*    */   public static ComponentType<EntityStore, Timers> getComponentType() {
/* 17 */     return NPCPlugin.get().getTimersComponentType();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Timers(Tickable[] timers) {
/* 23 */     this.timers = timers;
/*    */   }
/*    */   
/*    */   public Tickable[] getTimers() {
/* 27 */     return this.timers;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Component<EntityStore> clone() {
/* 34 */     return new Timers(this.timers);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\components\Timers.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */