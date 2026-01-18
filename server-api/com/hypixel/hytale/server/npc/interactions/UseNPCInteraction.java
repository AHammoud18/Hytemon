/*    */ package com.hypixel.hytale.server.npc.interactions;
/*    */ 
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.logger.HytaleLogger;
/*    */ import com.hypixel.hytale.protocol.InteractionState;
/*    */ import com.hypixel.hytale.protocol.InteractionType;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.RootInteraction;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.blackboard.Blackboard;
/*    */ import com.hypixel.hytale.server.npc.blackboard.view.interaction.InteractionView;
/*    */ import com.hypixel.hytale.server.npc.blackboard.view.interaction.ReservationStatus;
/*    */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*    */ import java.util.function.Supplier;
/*    */ import java.util.logging.Level;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class UseNPCInteraction
/*    */   extends SimpleInstantInteraction
/*    */ {
/* 28 */   public static final BuilderCodec<UseNPCInteraction> CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(UseNPCInteraction.class, UseNPCInteraction::new, SimpleInstantInteraction.CODEC)
/* 29 */     .documentation("Interacts with a target NPC."))
/* 30 */     .build();
/*    */   public static final String DEFAULT_ID = "*UseNPC";
/* 32 */   public static final RootInteraction DEFAULT_ROOT = new RootInteraction("*UseNPC", new String[] { "*UseNPC" });
/*    */   
/*    */   public UseNPCInteraction(String id) {
/* 35 */     super(id);
/*    */   }
/*    */ 
/*    */   
/*    */   protected UseNPCInteraction() {}
/*    */ 
/*    */   
/*    */   protected final void firstRun(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull CooldownHandler cooldownHandler) {
/* 43 */     Ref<EntityStore> ref = context.getEntity();
/* 44 */     CommandBuffer<EntityStore> commandBuffer = context.getCommandBuffer();
/*    */ 
/*    */     
/* 47 */     Player playerComponent = (Player)commandBuffer.getComponent(ref, Player.getComponentType());
/* 48 */     if (playerComponent == null) {
/* 49 */       HytaleLogger.getLogger().at(Level.INFO).log("UseNPCInteraction requires a Player but was used for: %s", ref);
/* 50 */       (context.getState()).state = InteractionState.Failed;
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 55 */     Ref<EntityStore> targetRef = context.getTargetEntity();
/* 56 */     if (targetRef == null) {
/* 57 */       (context.getState()).state = InteractionState.Failed;
/*    */       
/*    */       return;
/*    */     } 
/* 61 */     NPCEntity npcComponent = (NPCEntity)commandBuffer.getComponent(targetRef, NPCEntity.getComponentType());
/* 62 */     if (npcComponent == null) {
/* 63 */       HytaleLogger.getLogger().at(Level.INFO).log("UseNPCInteraction requires a target NPCEntity but was used for: %s", targetRef);
/* 64 */       (context.getState()).state = InteractionState.Failed;
/*    */       
/*    */       return;
/*    */     } 
/* 68 */     if (!npcComponent.getRole().getStateSupport().willInteractWith(ref)) {
/* 69 */       (context.getState()).state = InteractionState.Failed;
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 74 */     InteractionView interactionView = (InteractionView)((Blackboard)commandBuffer.getResource(Blackboard.getResourceType())).getView(InteractionView.class, 0L);
/* 75 */     if (interactionView.getReservationStatus(targetRef, ref, (ComponentAccessor)commandBuffer) == ReservationStatus.RESERVED_OTHER) {
/*    */       
/* 77 */       playerComponent.sendMessage(Message.translation("server.npc.npc.isBusy").param("roleName", npcComponent.getRoleName()));
/* 78 */       (context.getState()).state = InteractionState.Failed;
/*    */       
/*    */       return;
/*    */     } 
/* 82 */     npcComponent.getRole().getStateSupport().addInteraction(playerComponent);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 88 */     return "UseNPCInteraction{} " + super.toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\interactions\UseNPCInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */