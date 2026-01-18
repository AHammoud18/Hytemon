/*    */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.none.simple;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.RemoveReason;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.protocol.Interaction;
/*    */ import com.hypixel.hytale.protocol.InteractionType;
/*    */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.util.InteractionTarget;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RemoveEntityInteraction
/*    */   extends SimpleInstantInteraction
/*    */ {
/*    */   @Nonnull
/*    */   public static final BuilderCodec<RemoveEntityInteraction> CODEC;
/*    */   
/*    */   static {
/* 37 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(RemoveEntityInteraction.class, RemoveEntityInteraction::new, SimpleInstantInteraction.CODEC).documentation("Despawns the given entity.")).appendInherited(new KeyedCodec("Entity", (Codec)InteractionTarget.CODEC), (o, i) -> o.entityTarget = i, o -> o.entityTarget, (o, p) -> o.entityTarget = p.entityTarget).documentation("The entity to target for this interaction.").addValidator(Validators.nonNull()).add()).build();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/* 42 */   private InteractionTarget entityTarget = InteractionTarget.USER;
/*    */ 
/*    */ 
/*    */   
/*    */   protected void firstRun(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull CooldownHandler cooldownHandler) {
/* 47 */     CommandBuffer<EntityStore> commandBuffer = context.getCommandBuffer();
/* 48 */     assert commandBuffer != null;
/*    */     
/* 50 */     Ref<EntityStore> ref = context.getEntity();
/* 51 */     Ref<EntityStore> targetRef = this.entityTarget.getEntity(context, ref);
/* 52 */     if (targetRef == null || !targetRef.isValid()) {
/*    */       return;
/*    */     }
/* 55 */     if (commandBuffer.getArchetype(targetRef).contains(Player.getComponentType())) {
/*    */       return;
/*    */     }
/*    */     
/* 59 */     World world = ((EntityStore)commandBuffer.getExternalData()).getWorld();
/* 60 */     world.execute(() -> {
/*    */           if (!targetRef.isValid()) {
/*    */             return;
/*    */           }
/*    */           Store<EntityStore> store = world.getEntityStore().getStore();
/*    */           store.removeEntity(targetRef, RemoveReason.REMOVE);
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void simulateFirstRun(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull CooldownHandler cooldownHandler) {}
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected Interaction generatePacket() {
/* 76 */     return (Interaction)new com.hypixel.hytale.protocol.RemoveEntityInteraction();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void configurePacket(Interaction packet) {
/* 81 */     super.configurePacket(packet);
/* 82 */     com.hypixel.hytale.protocol.RemoveEntityInteraction p = (com.hypixel.hytale.protocol.RemoveEntityInteraction)packet;
/* 83 */     p.entityTarget = this.entityTarget.toProtocol();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 89 */     return "RemoveEntityInteraction{entityTarget=" + String.valueOf(this.entityTarget) + "} " + super
/*    */       
/* 91 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\none\simple\RemoveEntityInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */