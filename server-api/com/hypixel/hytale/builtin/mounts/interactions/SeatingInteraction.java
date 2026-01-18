/*    */ package com.hypixel.hytale.builtin.mounts.interactions;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.mounts.BlockMountAPI;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.math.vector.Vector3f;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.protocol.BlockPosition;
/*    */ import com.hypixel.hytale.protocol.BlockSoundEvent;
/*    */ import com.hypixel.hytale.protocol.InteractionType;
/*    */ import com.hypixel.hytale.protocol.SoundCategory;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.asset.type.blocksound.config.BlockSoundSet;
/*    */ import com.hypixel.hytale.server.core.asset.type.soundevent.config.SoundEvent;
/*    */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.client.SimpleBlockInteraction;
/*    */ import com.hypixel.hytale.server.core.universe.world.SoundUtil;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SeatingInteraction
/*    */   extends SimpleBlockInteraction
/*    */ {
/*    */   @Nonnull
/* 38 */   public static final BuilderCodec<SeatingInteraction> CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(SeatingInteraction.class, SeatingInteraction::new, SimpleBlockInteraction.CODEC)
/* 39 */     .documentation("Arranges perfect seating accommodations"))
/* 40 */     .build();
/*    */ 
/*    */   
/*    */   protected void interactWithBlock(@Nonnull World world, @Nonnull CommandBuffer<EntityStore> commandBuffer, @Nonnull InteractionType type, @Nonnull InteractionContext context, @Nullable ItemStack itemInHand, @Nonnull Vector3i targetBlock, @Nonnull CooldownHandler cooldownHandler) {
/* 44 */     Ref<EntityStore> ref = context.getEntity();
/*    */     
/* 46 */     Player player = (Player)commandBuffer.getComponent(ref, Player.getComponentType());
/* 47 */     if (player == null)
/*    */       return; 
/* 49 */     BlockPosition rawTarget = (BlockPosition)context.getMetaStore().getMetaObject(TARGET_BLOCK_RAW);
/* 50 */     Vector3f whereWasHit = new Vector3f(rawTarget.x + 0.5F, rawTarget.y + 0.5F, rawTarget.z + 0.5F);
/*    */     
/* 52 */     BlockMountAPI.BlockMountResult result = BlockMountAPI.mountOnBlock(ref, commandBuffer, targetBlock, whereWasHit);
/*    */     
/* 54 */     if (result == BlockMountAPI.DidNotMount.ALREADY_MOUNTED)
/* 55 */     { int soundEventIndex = SoundEvent.getAssetMap().getIndex("Creative_Play_Add_Mask");
/* 56 */       SoundUtil.playSoundEvent2d(ref, soundEventIndex, SoundCategory.SFX, (ComponentAccessor)commandBuffer); }
/* 57 */     else if (result instanceof BlockMountAPI.Mounted) { BlockMountAPI.Mounted mounted = (BlockMountAPI.Mounted)result;
/* 58 */       BlockSoundSet soundSet = (BlockSoundSet)BlockSoundSet.getAssetMap().getAsset(mounted.blockType().getBlockSoundSetIndex());
/* 59 */       String seatSoundId = (soundSet == null) ? null : (String)soundSet.getSoundEventIds().getOrDefault(BlockSoundEvent.Walk, null);
/* 60 */       if (seatSoundId != null) {
/* 61 */         int soundEventIndex = SoundEvent.getAssetMap().getIndex(seatSoundId);
/* 62 */         SoundUtil.playSoundEvent3dToPlayer(ref, soundEventIndex, SoundCategory.SFX, targetBlock.toVector3d(), (ComponentAccessor)commandBuffer);
/*    */       }  }
/*    */     else
/* 65 */     { player.sendMessage(Message.translation("server.interactions.didNotMount").param("state", result.toString())); }
/*    */   
/*    */   }
/*    */   
/*    */   protected void simulateInteractWithBlock(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nullable ItemStack itemInHand, @Nonnull World world, @Nonnull Vector3i targetBlock) {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\mounts\interactions\SeatingInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */