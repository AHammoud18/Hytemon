/*    */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.server;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.spatial.SpatialResource;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.protocol.BlockPosition;
/*    */ import com.hypixel.hytale.protocol.InteractionType;
/*    */ import com.hypixel.hytale.protocol.WaitForDataFrom;
/*    */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*    */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.client.SimpleBlockInteraction;
/*    */ import com.hypixel.hytale.server.core.modules.physics.component.Velocity;
/*    */ import com.hypixel.hytale.server.core.universe.world.ParticleUtil;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*    */ import com.hypixel.hytale.server.core.universe.world.meta.state.LaunchPad;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectList;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class LaunchPadInteraction extends SimpleBlockInteraction {
/* 31 */   public static final BuilderCodec<LaunchPadInteraction> CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(LaunchPadInteraction.class, LaunchPadInteraction::new, SimpleBlockInteraction.CODEC)
/* 32 */     .documentation("Applies the launchpad forces."))
/* 33 */     .build();
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public WaitForDataFrom getWaitForDataFrom() {
/* 38 */     return WaitForDataFrom.Server;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void interactWithBlock(@Nonnull World world, @Nonnull CommandBuffer<EntityStore> commandBuffer, @Nonnull InteractionType type, @Nonnull InteractionContext context, @Nullable ItemStack itemInHand, @Nonnull Vector3i targetBlock, @Nonnull CooldownHandler cooldownHandler) {
/* 43 */     WorldChunk chunk = world.getChunkIfInMemory(ChunkUtil.indexChunkFromBlock(targetBlock.x, targetBlock.z));
/* 44 */     if (chunk == null)
/*    */       return; 
/* 46 */     BlockPosition baseTargetBlock = world.getBaseBlock(new BlockPosition(targetBlock.x, targetBlock.y, targetBlock.z));
/* 47 */     Ref<ChunkStore> blockEntityRef = chunk.getBlockComponentEntity(baseTargetBlock.x, baseTargetBlock.y, baseTargetBlock.z);
/* 48 */     if (blockEntityRef == null)
/*    */       return; 
/* 50 */     LaunchPad launchPadState = (LaunchPad)blockEntityRef.getStore().getComponent(blockEntityRef, LaunchPad.getComponentType());
/* 51 */     if (launchPadState == null)
/*    */       return; 
/* 53 */     Ref<EntityStore> ref = context.getEntity();
/*    */     
/* 55 */     Player playerComponent = (Player)commandBuffer.getComponent(ref, Player.getComponentType());
/*    */ 
/*    */     
/* 58 */     if (launchPadState.isPlayersOnly() && playerComponent == null)
/*    */       return; 
/* 60 */     Velocity velocityComponent = (Velocity)commandBuffer.getComponent(ref, Velocity.getComponentType());
/* 61 */     assert velocityComponent != null;
/*    */     
/* 63 */     velocityComponent.addInstruction(new Vector3d(launchPadState
/* 64 */           .getVelocityX(), launchPadState.getVelocityY(), launchPadState.getVelocityZ()), null, ChangeVelocityType.Set);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 71 */     Vector3d particlePos = targetBlock.toVector3d().add(0.5D, 0.5D, 0.5D);
/*    */     
/* 73 */     SpatialResource<Ref<EntityStore>, EntityStore> playerSpatialResource = (SpatialResource<Ref<EntityStore>, EntityStore>)commandBuffer.getResource(EntityModule.get().getPlayerSpatialResourceType());
/* 74 */     ObjectList<Ref<EntityStore>> results = SpatialResource.getThreadLocalReferenceList();
/* 75 */     playerSpatialResource.getSpatialStructure().collect(particlePos, 75.0D, (List)results);
/*    */     
/* 77 */     ParticleUtil.spawnParticleEffect("Splash", particlePos, (List)results, (ComponentAccessor)commandBuffer);
/*    */   }
/*    */   
/*    */   protected void simulateInteractWithBlock(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nullable ItemStack itemInHand, @Nonnull World world, @Nonnull Vector3i targetBlock) {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\server\LaunchPadInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */