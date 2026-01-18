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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StepComponent
/*    */   implements Component<EntityStore>
/*    */ {
/*    */   private final float tickLength;
/*    */   
/*    */   public static ComponentType<EntityStore, StepComponent> getComponentType() {
/* 20 */     return NPCPlugin.get().getStepComponentType();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public StepComponent(float tickLength) {
/* 26 */     this.tickLength = tickLength;
/*    */   }
/*    */   
/*    */   public float getTickLength() {
/* 30 */     return this.tickLength;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Component<EntityStore> clone() {
/* 37 */     return new StepComponent(this.tickLength);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\components\StepComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */