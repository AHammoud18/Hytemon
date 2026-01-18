/*    */ package com.hypixel.hytale.builtin.adventure.objectives.interactions;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.Objective;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.ObjectivePlugin;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.config.objectivesetup.ObjectiveTypeSetup;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.protocol.InteractionType;
/*    */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*    */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.Set;
/*    */ import java.util.UUID;
/*    */ import java.util.function.Supplier;
/*    */ import java.util.logging.Level;
/*    */ import javax.annotation.Nonnull;
/*    */ import org.bson.BsonDocument;
/*    */ 
/*    */ public class StartObjectiveInteraction
/*    */   extends SimpleInstantInteraction {
/*    */   public static final BuilderCodec<StartObjectiveInteraction> CODEC;
/*    */   
/*    */   static {
/* 33 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(StartObjectiveInteraction.class, StartObjectiveInteraction::new, SimpleInstantInteraction.CODEC).documentation("Starts the given objective or adds the player to an existing one.")).appendInherited(new KeyedCodec("Setup", (Codec)ObjectiveTypeSetup.CODEC), (startObjectiveInteraction, objectiveTypeSetup) -> startObjectiveInteraction.objectiveTypeSetup = objectiveTypeSetup, startObjectiveInteraction -> startObjectiveInteraction.objectiveTypeSetup, (startObjectiveInteraction, parent) -> startObjectiveInteraction.objectiveTypeSetup = parent.objectiveTypeSetup).addValidator(Validators.nonNull()).add()).build();
/*    */   }
/* 35 */   public static final KeyedCodec<UUID> OBJECTIVE_UUID = new KeyedCodec("ObjectiveUUID", (Codec)Codec.UUID_BINARY);
/*    */ 
/*    */   
/*    */   protected ObjectiveTypeSetup objectiveTypeSetup;
/*    */ 
/*    */ 
/*    */   
/*    */   protected void firstRun(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull CooldownHandler cooldownHandler) {
/* 43 */     CommandBuffer<EntityStore> commandBuffer = context.getCommandBuffer();
/* 44 */     Ref<EntityStore> ref = context.getEntity();
/*    */     
/* 46 */     PlayerRef playerRefComponent = (PlayerRef)commandBuffer.getComponent(ref, PlayerRef.getComponentType());
/* 47 */     if (playerRefComponent == null)
/*    */       return; 
/* 49 */     ItemStack itemStack = context.getHeldItem();
/*    */     
/* 51 */     Store<EntityStore> store = commandBuffer.getStore();
/* 52 */     UUID objectiveUUID = (UUID)itemStack.getFromMetadataOrNull(OBJECTIVE_UUID);
/* 53 */     if (objectiveUUID == null) {
/* 54 */       startObjective(playerRefComponent, context, itemStack, store);
/*    */     } else {
/* 56 */       ObjectivePlugin.get().addPlayerToExistingObjective(store, playerRefComponent.getUuid(), objectiveUUID);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private void startObjective(@Nonnull PlayerRef player, @Nonnull InteractionContext context, @Nonnull ItemStack itemStack, @Nonnull Store<EntityStore> store) {
/* 65 */     BsonDocument itemStackMetadata = itemStack.getMetadata();
/* 66 */     if (itemStackMetadata == null) itemStackMetadata = new BsonDocument();
/*    */     
/* 68 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/* 69 */     Objective objective = this.objectiveTypeSetup.setup(Set.of(player.getUuid()), world.getWorldConfig().getUuid(), null, store);
/* 70 */     if (objective == null) {
/* 71 */       ObjectivePlugin.get().getLogger().at(Level.WARNING).log("Failed to start objective '%s' from item: %s", this.objectiveTypeSetup.getObjectiveIdToStart(), itemStack);
/*    */       
/*    */       return;
/*    */     } 
/* 75 */     OBJECTIVE_UUID.put(itemStackMetadata, objective.getObjectiveUUID());
/* 76 */     ItemStack clonedItemStack = itemStack.withMetadata(itemStackMetadata);
/*    */     
/* 78 */     objective.setObjectiveItemStarter(clonedItemStack);
/* 79 */     context.setHeldItem(clonedItemStack);
/* 80 */     context.getHeldItemContainer().replaceItemStackInSlot((short)context.getHeldItemSlot(), itemStack, clonedItemStack);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 86 */     return "StartObjectiveInteraction{objectiveTypeSetup=" + String.valueOf(this.objectiveTypeSetup) + "} " + super
/*    */       
/* 88 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\interactions\StartObjectiveInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */