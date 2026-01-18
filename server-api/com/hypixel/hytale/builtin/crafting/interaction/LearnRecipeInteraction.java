/*     */ package com.hypixel.hytale.builtin.crafting.interaction;
/*     */ import com.hypixel.hytale.builtin.crafting.CraftingPlugin;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.protocol.InteractionState;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.protocol.WaitForDataFrom;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class LearnRecipeInteraction extends SimpleInstantInteraction {
/*  28 */   public static final KeyedCodec<String> ITEM_ID = new KeyedCodec("ItemId", (Codec)Codec.STRING);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static final BuilderCodec<LearnRecipeInteraction> CODEC;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  42 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(LearnRecipeInteraction.class, LearnRecipeInteraction::new, SimpleInstantInteraction.CODEC).documentation("Causes the user to learn the given recipe.")).appendInherited(new KeyedCodec("ItemId", (Codec)Codec.STRING), (data, o) -> data.itemId = o, data -> data.itemId, (data, parent) -> data.itemId = parent.itemId).add()).build();
/*  43 */   } public static final Message MESSAGE_MODULES_LEARN_RECIPE_INVALID_ITEM = Message.translation("server.modules.learnrecipe.invalidItem");
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected String itemId;
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public WaitForDataFrom getWaitForDataFrom() {
/*  54 */     return WaitForDataFrom.Server;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void firstRun(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull CooldownHandler cooldownHandler) {
/*  59 */     CommandBuffer<EntityStore> commandBuffer = context.getCommandBuffer();
/*  60 */     assert commandBuffer != null;
/*     */     
/*  62 */     Ref<EntityStore> ref = context.getEntity();
/*     */     
/*  64 */     PlayerRef playerRefComponent = (PlayerRef)commandBuffer.getComponent(ref, PlayerRef.getComponentType());
/*  65 */     if (playerRefComponent == null) {
/*  66 */       HytaleLogger.getLogger().at(Level.INFO).log("LearnRecipeInteraction requires a Player but was used for: %s", ref);
/*  67 */       (context.getState()).state = InteractionState.Failed;
/*     */       
/*     */       return;
/*     */     } 
/*  71 */     String itemId = null;
/*  72 */     ItemContainer inventory = context.getHeldItemContainer();
/*  73 */     ItemStack itemInHand = context.getHeldItem();
/*  74 */     if (itemInHand != null) itemId = (String)itemInHand.getFromMetadataOrNull(ITEM_ID); 
/*  75 */     if (itemId == null) {
/*  76 */       if (this.itemId == null) {
/*  77 */         playerRefComponent.sendMessage(Message.translation("server.modules.learnrecipe.noIdSet"));
/*  78 */         (context.getState()).state = InteractionState.Failed;
/*     */         return;
/*     */       } 
/*  81 */       itemId = this.itemId;
/*     */     } 
/*     */     
/*  84 */     Item item = (Item)Item.getAssetMap().getAsset(itemId);
/*  85 */     Message itemNameMessage = (item != null) ? Message.translation(item.getTranslationKey()) : Message.raw("?");
/*     */     
/*  87 */     if (CraftingPlugin.learnRecipe(ref, itemId, (ComponentAccessor)commandBuffer)) {
/*  88 */       playerRefComponent.sendMessage(Message.translation("server.modules.learnrecipe.success")
/*  89 */           .param("name", itemNameMessage));
/*     */       
/*     */       return;
/*     */     } 
/*  93 */     playerRefComponent.sendMessage(Message.translation("server.modules.learnrecipe.alreadyKnown")
/*  94 */         .param("name", itemNameMessage));
/*  95 */     (context.getState()).state = InteractionState.Failed;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 101 */     return "LearnRecipeInteraction{itemId=" + this.itemId + "} " + super
/*     */       
/* 103 */       .toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\crafting\interaction\LearnRecipeInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */