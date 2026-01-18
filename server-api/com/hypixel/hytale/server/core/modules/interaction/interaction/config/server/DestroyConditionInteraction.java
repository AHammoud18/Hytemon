/*    */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.server;
/*    */ 
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.math.util.ChunkUtil;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.protocol.InteractionState;
/*    */ import com.hypixel.hytale.protocol.InteractionType;
/*    */ import com.hypixel.hytale.protocol.WaitForDataFrom;
/*    */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*    */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.client.SimpleBlockInteraction;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.meta.BlockState;
/*    */ import com.hypixel.hytale.server.core.universe.world.meta.state.BreakValidatedBlockState;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ @Deprecated
/*    */ public class DestroyConditionInteraction extends SimpleBlockInteraction {
/* 26 */   public static final BuilderCodec<DestroyConditionInteraction> CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(DestroyConditionInteraction.class, DestroyConditionInteraction::new, SimpleBlockInteraction.CODEC)
/* 27 */     .documentation("Checks if the target block is destroyable"))
/* 28 */     .build();
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public WaitForDataFrom getWaitForDataFrom() {
/* 33 */     return WaitForDataFrom.Server;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void interactWithBlock(@Nonnull World world, @Nonnull CommandBuffer<EntityStore> commandBuffer, @Nonnull InteractionType type, @Nonnull InteractionContext context, @Nullable ItemStack itemInHand, @Nonnull Vector3i pos, @Nonnull CooldownHandler cooldownHandler) {
/* 38 */     Ref<EntityStore> ref = context.getEntity();
/* 39 */     BlockState state = world.getChunk(ChunkUtil.indexChunkFromBlock(pos.x, pos.z)).getState(pos.x, pos.y, pos.z);
/* 40 */     if (!(state instanceof BreakValidatedBlockState) || ((BreakValidatedBlockState)state).canDestroy(ref, (ComponentAccessor)commandBuffer)) {
/* 41 */       (context.getState()).state = InteractionState.Finished;
/*    */       return;
/*    */     } 
/* 44 */     (context.getState()).state = InteractionState.Failed;
/*    */   }
/*    */   
/*    */   protected void simulateInteractWithBlock(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nullable ItemStack itemInHand, @Nonnull World world, @Nonnull Vector3i targetBlock) {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\server\DestroyConditionInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */