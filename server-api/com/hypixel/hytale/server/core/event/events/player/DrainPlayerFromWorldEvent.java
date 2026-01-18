/*    */ package com.hypixel.hytale.server.core.event.events.player;
/*    */ 
/*    */ import com.hypixel.hytale.component.Holder;
/*    */ import com.hypixel.hytale.event.IEvent;
/*    */ import com.hypixel.hytale.math.vector.Transform;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class DrainPlayerFromWorldEvent
/*    */   implements IEvent<String>
/*    */ {
/*    */   private final Holder<EntityStore> holder;
/*    */   private World world;
/*    */   private Transform transform;
/*    */   
/*    */   public DrainPlayerFromWorldEvent(Holder<EntityStore> holder, World world, Transform transform) {
/* 18 */     this.holder = holder;
/* 19 */     this.world = world;
/* 20 */     this.transform = transform;
/*    */   }
/*    */   
/*    */   public Holder<EntityStore> getHolder() {
/* 24 */     return this.holder;
/*    */   }
/*    */   
/*    */   public World getWorld() {
/* 28 */     return this.world;
/*    */   }
/*    */   
/*    */   public void setWorld(World world) {
/* 32 */     this.world = world;
/*    */   }
/*    */   
/*    */   public Transform getTransform() {
/* 36 */     return this.transform;
/*    */   }
/*    */   
/*    */   public void setTransform(Transform transform) {
/* 40 */     this.transform = transform;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 46 */     return "DrainPlayerFromWorldEvent{world=" + String.valueOf(this.world) + ", transform=" + String.valueOf(this.transform) + "} " + super
/*    */ 
/*    */       
/* 49 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\event\events\player\DrainPlayerFromWorldEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */