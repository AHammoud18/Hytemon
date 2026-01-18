/*     */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.server;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.protocol.GameMode;
/*     */ import com.hypixel.hytale.protocol.Interaction;
/*     */ import com.hypixel.hytale.protocol.InteractionState;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.protocol.SoundCategory;
/*     */ import com.hypixel.hytale.protocol.WaitForDataFrom;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.codec.ProtocolCodecs;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.inventory.container.CombinedItemContainer;
/*     */ import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
/*     */ import com.hypixel.hytale.server.core.inventory.container.SimpleItemContainer;
/*     */ import com.hypixel.hytale.server.core.inventory.transaction.ItemStackSlotTransaction;
/*     */ import com.hypixel.hytale.server.core.inventory.transaction.ItemStackTransaction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.SoundUtil;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.core.util.TempAssetIdUtil;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ModifyInventoryInteraction
/*     */   extends SimpleInstantInteraction
/*     */ {
/*     */   public static final BuilderCodec<ModifyInventoryInteraction> CODEC;
/*     */   private GameMode requiredGameMode;
/*     */   private ItemStack itemToRemove;
/*     */   private int adjustHeldItemQuantity;
/*     */   private ItemStack itemToAdd;
/*     */   private double adjustHeldItemDurability;
/*     */   private String brokenItem;
/*     */   private Boolean notifyOnBreak;
/*     */   private String notifyOnBreakMessage;
/*     */   
/*     */   static {
/*  84 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ModifyInventoryInteraction.class, ModifyInventoryInteraction::new, SimpleInstantInteraction.CODEC).documentation("Modifies an item in the inventory.")).appendInherited(new KeyedCodec("RequiredGameMode", (Codec)ProtocolCodecs.GAMEMODE), (interaction, s) -> interaction.requiredGameMode = s, interaction -> interaction.requiredGameMode, (interaction, parent) -> interaction.requiredGameMode = parent.requiredGameMode).add()).appendInherited(new KeyedCodec("ItemToRemove", (Codec)ItemStack.CODEC), (interaction, s) -> interaction.itemToRemove = s, interaction -> interaction.itemToRemove, (interaction, parent) -> interaction.itemToRemove = parent.itemToRemove).add()).appendInherited(new KeyedCodec("AdjustHeldItemQuantity", (Codec)Codec.INTEGER), (interaction, s) -> interaction.adjustHeldItemQuantity = s.intValue(), interaction -> Integer.valueOf(interaction.adjustHeldItemQuantity), (interaction, parent) -> interaction.adjustHeldItemQuantity = parent.adjustHeldItemQuantity).add()).appendInherited(new KeyedCodec("ItemToAdd", (Codec)ItemStack.CODEC), (interaction, s) -> interaction.itemToAdd = s, interaction -> interaction.itemToAdd, (interaction, parent) -> interaction.itemToAdd = parent.itemToAdd).add()).appendInherited(new KeyedCodec("AdjustHeldItemDurability", (Codec)Codec.DOUBLE), (interaction, s) -> interaction.adjustHeldItemDurability = s.doubleValue(), interaction -> Double.valueOf(interaction.adjustHeldItemDurability), (interaction, parent) -> interaction.adjustHeldItemDurability = parent.adjustHeldItemDurability).add()).appendInherited(new KeyedCodec("BrokenItem", (Codec)Codec.STRING), (interaction, s) -> interaction.brokenItem = s, interaction -> interaction.brokenItem, (interaction, parent) -> interaction.brokenItem = parent.brokenItem).add()).appendInherited(new KeyedCodec("NotifyOnBreak", (Codec)Codec.BOOLEAN), (interaction, s) -> interaction.notifyOnBreak = s, interaction -> interaction.notifyOnBreak, (interaction, parent) -> interaction.notifyOnBreak = parent.notifyOnBreak).documentation("If true, shows the 'item broken' message and plays the break sound when durability reaches 0. Defaults to true for tools (no BrokenItem or same item), false for transformations (different BrokenItem).").add()).appendInherited(new KeyedCodec("NotifyOnBreakMessage", (Codec)Codec.STRING), (interaction, s) -> interaction.notifyOnBreakMessage = s, interaction -> interaction.notifyOnBreakMessage, (interaction, parent) -> interaction.notifyOnBreakMessage = parent.notifyOnBreakMessage).documentation("Custom translation key for the break notification message. Supports {itemName} parameter. Defaults to 'server.general.repair.itemBroken' if not specified.").add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public WaitForDataFrom getWaitForDataFrom() {
/*  98 */     return WaitForDataFrom.Server;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void firstRun(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull CooldownHandler cooldownHandler) {
/* 103 */     Ref<EntityStore> ref = context.getEntity();
/* 104 */     CommandBuffer<EntityStore> commandBuffer = context.getCommandBuffer();
/*     */ 
/*     */     
/* 107 */     Player playerComponent = (Player)commandBuffer.getComponent(ref, Player.getComponentType());
/* 108 */     if (playerComponent == null)
/*     */       return; 
/* 110 */     boolean hasRequiredGameMode = (this.requiredGameMode == null || playerComponent.getGameMode() == this.requiredGameMode);
/* 111 */     if (!hasRequiredGameMode)
/* 112 */       return;  CombinedItemContainer combinedHotbarFirst = playerComponent.getInventory().getCombinedHotbarFirst();
/*     */     
/* 114 */     if (this.itemToRemove != null) {
/* 115 */       ItemStackTransaction removeItemStack = combinedHotbarFirst.removeItemStack(this.itemToRemove, true, true);
/* 116 */       if (!removeItemStack.succeeded()) {
/* 117 */         (context.getState()).state = InteractionState.Failed;
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 122 */     ItemStack heldItem = context.getHeldItem();
/* 123 */     if (heldItem != null && this.adjustHeldItemQuantity != 0) {
/* 124 */       if (this.adjustHeldItemQuantity < 0) {
/* 125 */         ItemStackSlotTransaction itemStackSlotTransaction = context.getHeldItemContainer().removeItemStackFromSlot((short)context.getHeldItemSlot(), heldItem, -this.adjustHeldItemQuantity);
/* 126 */         if (!itemStackSlotTransaction.succeeded()) {
/* 127 */           (context.getState()).state = InteractionState.Failed;
/*     */           
/*     */           return;
/*     */         } 
/* 131 */         context.setHeldItem(itemStackSlotTransaction.getSlotAfter());
/*     */       } else {
/* 133 */         SimpleItemContainer.addOrDropItemStack((ComponentAccessor)commandBuffer, ref, (ItemContainer)combinedHotbarFirst, heldItem.withQuantity(this.adjustHeldItemQuantity));
/*     */       } 
/*     */     }
/*     */     
/* 137 */     if (this.itemToAdd != null) SimpleItemContainer.addOrDropItemStack((ComponentAccessor)commandBuffer, ref, (ItemContainer)combinedHotbarFirst, this.itemToAdd);
/*     */     
/* 139 */     if (this.adjustHeldItemDurability == 0.0D)
/*     */       return; 
/* 141 */     ItemStack item = context.getHeldItem();
/* 142 */     if (item == null)
/*     */       return; 
/* 144 */     ItemStack newItem = item.withIncreasedDurability(this.adjustHeldItemDurability);
/* 145 */     boolean justBroke = (newItem.isBroken() && !item.isBroken());
/* 146 */     if (newItem.isBroken())
/*     */     {
/* 148 */       if (this.brokenItem != null) {
/* 149 */         if (this.brokenItem.equals("Empty")) {
/*     */           
/* 151 */           newItem = null;
/* 152 */         } else if (!this.brokenItem.equals(item.getItemId())) {
/*     */           
/* 154 */           newItem = new ItemStack(this.brokenItem, 1);
/*     */         } 
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 162 */     boolean isTransformation = (this.brokenItem != null && !this.brokenItem.equals(item.getItemId()));
/* 163 */     boolean shouldNotify = (this.notifyOnBreak != null) ? this.notifyOnBreak.booleanValue() : (!isTransformation);
/* 164 */     if (justBroke && shouldNotify) {
/* 165 */       Message itemNameMessage = Message.translation(item.getItem().getTranslationKey());
/* 166 */       String messageKey = (this.notifyOnBreakMessage != null) ? this.notifyOnBreakMessage : "server.general.repair.itemBroken";
/* 167 */       playerComponent.sendMessage(Message.translation(messageKey).param("itemName", itemNameMessage).color("#ff5555"));
/*     */       
/* 169 */       PlayerRef playerRefComponent = (PlayerRef)commandBuffer.getComponent(ref, PlayerRef.getComponentType());
/* 170 */       if (playerRefComponent != null) {
/* 171 */         int soundEventIndex = TempAssetIdUtil.getSoundEventIndex("SFX_Item_Break");
/* 172 */         SoundUtil.playSoundEvent2dToPlayer(playerRefComponent, soundEventIndex, SoundCategory.SFX);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 177 */     ItemStackSlotTransaction slotTransaction = context.getHeldItemContainer().setItemStackForSlot((short)context.getHeldItemSlot(), newItem);
/* 178 */     if (!slotTransaction.succeeded()) {
/* 179 */       (context.getState()).state = InteractionState.Failed;
/*     */       
/*     */       return;
/*     */     } 
/* 183 */     context.setHeldItem(newItem);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected Interaction generatePacket() {
/* 189 */     return (Interaction)new com.hypixel.hytale.protocol.ModifyInventoryInteraction();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void configurePacket(Interaction packet) {
/* 194 */     super.configurePacket(packet);
/* 195 */     com.hypixel.hytale.protocol.ModifyInventoryInteraction p = (com.hypixel.hytale.protocol.ModifyInventoryInteraction)packet;
/* 196 */     if (this.itemToRemove != null) p.itemToRemove = this.itemToRemove.toPacket(); 
/* 197 */     p.adjustHeldItemQuantity = this.adjustHeldItemQuantity;
/* 198 */     if (this.itemToAdd != null) p.itemToAdd = this.itemToAdd.toPacket(); 
/* 199 */     if (this.brokenItem != null) p.brokenItem = this.brokenItem.toString(); 
/* 200 */     p.adjustHeldItemDurability = this.adjustHeldItemDurability;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 206 */     return "ModifyInventoryInteraction{requiredGameMode=" + String.valueOf(this.requiredGameMode) + ", itemToRemove=" + String.valueOf(this.itemToRemove) + ", adjustHeldItemQuantity=" + this.adjustHeldItemQuantity + ", itemToAdd=" + String.valueOf(this.itemToAdd) + ", adjustHeldItemDurability=" + this.adjustHeldItemDurability + ", brokenItem=" + this.brokenItem + ", notifyOnBreak=" + this.notifyOnBreak + ", notifyOnBreakMessage='" + this.notifyOnBreakMessage + "'} " + super
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 215 */       .toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\server\ModifyInventoryInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */