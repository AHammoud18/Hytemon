/*    */ package com.hypixel.hytale.builtin.instances.interactions;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.Archetype;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.protocol.InteractionType;
/*    */ import com.hypixel.hytale.protocol.WaitForDataFrom;
/*    */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.modules.entity.teleport.Teleport;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ExitInstanceInteraction extends SimpleInstantInteraction {
/* 18 */   public static final BuilderCodec<ExitInstanceInteraction> CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(ExitInstanceInteraction.class, ExitInstanceInteraction::new, SimpleInstantInteraction.CODEC)
/* 19 */     .documentation("Teleports the **Entity** out of the current **Instance** and places them at their set return point."))
/*    */     
/* 21 */     .build();
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public WaitForDataFrom getWaitForDataFrom() {
/* 26 */     return WaitForDataFrom.Server;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void firstRun(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull CooldownHandler cooldownHandler) {
/* 31 */     Ref<EntityStore> ref = context.getEntity();
/* 32 */     CommandBuffer<EntityStore> commandBuffer = context.getCommandBuffer();
/*    */     
/* 34 */     Player playerComponent = (Player)commandBuffer.getComponent(ref, Player.getComponentType());
/* 35 */     if (playerComponent != null && playerComponent.isWaitingForClientReady()) {
/*    */       return;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/* 41 */     Archetype<EntityStore> archetype = commandBuffer.getArchetype(ref);
/* 42 */     if (archetype.contains(Teleport.getComponentType()) || archetype.contains(PendingTeleport.getComponentType()))
/*    */       return; 
/* 44 */     InstancesPlugin.exitInstance(ref, (ComponentAccessor)commandBuffer);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\instances\interactions\ExitInstanceInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */