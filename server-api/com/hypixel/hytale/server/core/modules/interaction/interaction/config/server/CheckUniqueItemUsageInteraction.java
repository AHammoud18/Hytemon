/*    */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.server;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.protocol.InteractionState;
/*    */ import com.hypixel.hytale.protocol.InteractionType;
/*    */ import com.hypixel.hytale.protocol.WaitForDataFrom;
/*    */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*    */ import com.hypixel.hytale.server.core.entity.entities.player.data.UniqueItemUsagesComponent;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.core.util.NotificationUtil;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
/*    */ 
/*    */ public class CheckUniqueItemUsageInteraction extends SimpleInstantInteraction {
/* 20 */   public static final BuilderCodec<CheckUniqueItemUsageInteraction> CODEC = BuilderCodec.builder(CheckUniqueItemUsageInteraction.class, CheckUniqueItemUsageInteraction::new, SimpleInstantInteraction.CODEC)
/* 21 */     .build();
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public WaitForDataFrom getWaitForDataFrom() {
/* 26 */     return WaitForDataFrom.Server;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void firstRun(@NonNullDecl InteractionType type, @NonNullDecl InteractionContext context, @NonNullDecl CooldownHandler cooldownHandler) {
/* 31 */     CommandBuffer<EntityStore> commandBuffer = context.getCommandBuffer();
/* 32 */     assert commandBuffer != null;
/*    */     
/* 34 */     Ref<EntityStore> ref = context.getEntity();
/* 35 */     PlayerRef playerRefComponent = (PlayerRef)commandBuffer.getComponent(ref, PlayerRef.getComponentType());
/* 36 */     if (playerRefComponent == null) {
/* 37 */       (context.getState()).state = InteractionState.Failed;
/*    */       
/*    */       return;
/*    */     } 
/* 41 */     UniqueItemUsagesComponent uniqueItemUsagesComponent = (UniqueItemUsagesComponent)commandBuffer.getComponent(ref, UniqueItemUsagesComponent.getComponentType());
/* 42 */     assert uniqueItemUsagesComponent != null;
/* 43 */     if (uniqueItemUsagesComponent.hasUsedUniqueItem(context.getHeldItem().getItemId())) {
/* 44 */       (context.getState()).state = InteractionState.Failed;
/* 45 */       NotificationUtil.sendNotification(playerRefComponent.getPacketHandler(), 
/* 46 */           Message.translation("server.commands.checkUniqueItemUsage.uniqueItemAlreadyUsed"));
/*    */       
/*    */       return;
/*    */     } 
/* 50 */     uniqueItemUsagesComponent.recordUniqueItemUsage(context.getHeldItem().getItemId());
/* 51 */     (context.getState()).state = InteractionState.Finished;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 56 */     return "CheckUniqueItemUsageInteraction{}" + super
/* 57 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\server\CheckUniqueItemUsageInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */