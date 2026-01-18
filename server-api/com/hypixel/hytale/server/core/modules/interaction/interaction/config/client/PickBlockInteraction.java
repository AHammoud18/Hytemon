/*    */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.client;
/*    */ 
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.protocol.Interaction;
/*    */ import com.hypixel.hytale.protocol.InteractionType;
/*    */ import com.hypixel.hytale.protocol.WaitForDataFrom;
/*    */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*    */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
/*    */ import org.checkerframework.checker.nullness.compatqual.NullableDecl;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PickBlockInteraction
/*    */   extends SimpleBlockInteraction
/*    */ {
/*    */   @Nonnull
/* 25 */   public static final BuilderCodec<PickBlockInteraction> CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(PickBlockInteraction.class, PickBlockInteraction::new, SimpleBlockInteraction.CODEC)
/* 26 */     .documentation("Performs a 'block pick', moving a the target block to the user's hand if they have it in their inventory or are in creative."))
/*    */     
/* 28 */     .build();
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public WaitForDataFrom getWaitForDataFrom() {
/* 33 */     return WaitForDataFrom.Client;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void interactWithBlock(@NonNullDecl World world, @NonNullDecl CommandBuffer<EntityStore> commandBuffer, @NonNullDecl InteractionType type, @NonNullDecl InteractionContext context, @NullableDecl ItemStack itemInHand, @NonNullDecl Vector3i targetBlock, @NonNullDecl CooldownHandler cooldownHandler) {}
/*    */ 
/*    */ 
/*    */   
/*    */   protected void simulateInteractWithBlock(@NonNullDecl InteractionType type, @NonNullDecl InteractionContext context, @NullableDecl ItemStack itemInHand, @NonNullDecl World world, @NonNullDecl Vector3i targetBlock) {}
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected Interaction generatePacket() {
/* 49 */     return (Interaction)new com.hypixel.hytale.protocol.PickBlockInteraction();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean needsRemoteSync() {
/* 54 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 60 */     return "PickBlockInteraction{} " + super.toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\client\PickBlockInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */