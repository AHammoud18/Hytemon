/*    */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.server;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.protocol.Interaction;
/*    */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*    */ import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ChangeStatInteraction extends ChangeStatBaseInteraction {
/* 13 */   public static final BuilderCodec<ChangeStatInteraction> CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(ChangeStatInteraction.class, ChangeStatInteraction::new, ChangeStatBaseInteraction.CODEC)
/* 14 */     .documentation("Changes the given stats."))
/* 15 */     .build();
/*    */ 
/*    */   
/*    */   protected void firstRun(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull CooldownHandler cooldownHandler) {
/* 19 */     CommandBuffer<EntityStore> commandBuffer = context.getCommandBuffer();
/* 20 */     Ref<EntityStore> ref = context.getEntity();
/*    */     
/* 22 */     EntityStatMap entityStatMapComponent = (EntityStatMap)commandBuffer.getComponent(ref, EntityStatMap.getComponentType());
/* 23 */     if (entityStatMapComponent == null)
/*    */       return; 
/* 25 */     entityStatMapComponent.processStatChanges(EntityStatMap.Predictable.SELF, this.entityStats, this.valueType, this.changeStatBehaviour);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected Interaction generatePacket() {
/* 31 */     return (Interaction)new com.hypixel.hytale.protocol.ChangeStatInteraction();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void configurePacket(Interaction packet) {
/* 36 */     super.configurePacket(packet);
/* 37 */     com.hypixel.hytale.protocol.ChangeStatInteraction p = (com.hypixel.hytale.protocol.ChangeStatInteraction)packet;
/* 38 */     p.statModifiers = (Map)this.entityStats;
/* 39 */     p.valueType = this.valueType;
/* 40 */     p.changeStatBehaviour = this.changeStatBehaviour;
/* 41 */     p.entityTarget = this.entityTarget.toProtocol();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 47 */     return "ChangeStatInteraction{}" + super.toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\server\ChangeStatInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */