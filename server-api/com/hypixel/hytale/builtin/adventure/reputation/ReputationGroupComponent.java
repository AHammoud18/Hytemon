/*    */ package com.hypixel.hytale.builtin.adventure.reputation;
/*    */ 
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ReputationGroupComponent
/*    */   implements Component<EntityStore>
/*    */ {
/*    */   @Nonnull
/*    */   private final String reputationGroupId;
/*    */   
/*    */   @Nonnull
/*    */   public static ComponentType<EntityStore, ReputationGroupComponent> getComponentType() {
/* 19 */     return ReputationPlugin.get().getReputationGroupComponentType();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ReputationGroupComponent(@Nonnull String reputationGroupId) {
/* 34 */     this.reputationGroupId = reputationGroupId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String getReputationGroupId() {
/* 42 */     return this.reputationGroupId;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Component<EntityStore> clone() {
/* 48 */     return new ReputationGroupComponent(this.reputationGroupId);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\reputation\ReputationGroupComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */