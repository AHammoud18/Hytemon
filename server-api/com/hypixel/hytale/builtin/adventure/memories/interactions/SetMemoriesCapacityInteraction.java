/*    */ package com.hypixel.hytale.builtin.adventure.memories.interactions;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.adventure.memories.component.PlayerMemories;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.protocol.InteractionState;
/*    */ import com.hypixel.hytale.protocol.InteractionType;
/*    */ import com.hypixel.hytale.protocol.Packet;
/*    */ import com.hypixel.hytale.protocol.WaitForDataFrom;
/*    */ import com.hypixel.hytale.protocol.packets.player.UpdateMemoriesFeatureStatus;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*    */ import com.hypixel.hytale.server.core.io.PacketHandler;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.core.util.NotificationUtil;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class SetMemoriesCapacityInteraction
/*    */   extends SimpleInstantInteraction
/*    */ {
/*    */   public static final BuilderCodec<SetMemoriesCapacityInteraction> CODEC;
/*    */   private int capacity;
/*    */   
/*    */   static {
/* 32 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(SetMemoriesCapacityInteraction.class, SetMemoriesCapacityInteraction::new, SimpleInstantInteraction.CODEC).documentation("Sets how many memories a player can store.")).appendInherited(new KeyedCodec("Capacity", (Codec)Codec.INTEGER), (i, s) -> i.capacity = s.intValue(), i -> Integer.valueOf(i.capacity), (i, parent) -> i.capacity = parent.capacity).documentation("Defines the amount of memories that a player can store.").add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void firstRun(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull CooldownHandler cooldownHandler) {
/* 38 */     Ref<EntityStore> ref = context.getEntity();
/* 39 */     CommandBuffer<EntityStore> commandBuffer = context.getCommandBuffer();
/* 40 */     assert commandBuffer != null;
/*    */     
/* 42 */     PlayerMemories memoriesComponent = (PlayerMemories)commandBuffer.ensureAndGetComponent(ref, PlayerMemories.getComponentType());
/* 43 */     if (this.capacity <= memoriesComponent.getMemoriesCapacity()) {
/* 44 */       (context.getState()).state = InteractionState.Failed;
/*    */       
/*    */       return;
/*    */     } 
/* 48 */     int previousCapacity = memoriesComponent.getMemoriesCapacity();
/* 49 */     memoriesComponent.setMemoriesCapacity(this.capacity);
/*    */ 
/*    */     
/* 52 */     if (previousCapacity <= 0) {
/* 53 */       PlayerRef playerRefComponent = (PlayerRef)commandBuffer.getComponent(ref, PlayerRef.getComponentType());
/* 54 */       assert playerRefComponent != null;
/*    */       
/* 56 */       PacketHandler playerConnection = playerRefComponent.getPacketHandler();
/* 57 */       playerConnection.writeNoCache((Packet)new UpdateMemoriesFeatureStatus(true));
/*    */       
/* 59 */       NotificationUtil.sendNotification(playerConnection, Message.translation("server.memories.general.featureUnlockedNotification"), null, "NotificationIcons/MemoriesIcon.png");
/* 60 */       playerRefComponent.sendMessage(Message.translation("server.memories.general.featureUnlockedMessage"));
/*    */     } 
/*    */     
/* 63 */     (context.getState()).state = InteractionState.Finished;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public WaitForDataFrom getWaitForDataFrom() {
/* 69 */     return WaitForDataFrom.Server;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 74 */     return super.toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\memories\interactions\SetMemoriesCapacityInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */