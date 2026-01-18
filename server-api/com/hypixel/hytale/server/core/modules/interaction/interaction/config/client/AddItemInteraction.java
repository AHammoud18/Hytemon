/*    */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.client;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.protocol.InteractionType;
/*    */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*    */ import com.hypixel.hytale.server.core.entity.Entity;
/*    */ import com.hypixel.hytale.server.core.entity.EntityUtils;
/*    */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*    */ import com.hypixel.hytale.server.core.entity.LivingEntity;
/*    */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AddItemInteraction
/*    */   extends SimpleBlockInteraction
/*    */ {
/*    */   @Nonnull
/*    */   public static final BuilderCodec<AddItemInteraction> CODEC;
/*    */   protected String itemId;
/*    */   protected int quantity;
/*    */   
/*    */   static {
/* 50 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(AddItemInteraction.class, AddItemInteraction::new, SimpleBlockInteraction.CODEC).documentation("Adds an item to the users inventory.")).append(new KeyedCodec("ItemId", (Codec)Codec.STRING), (i, s) -> i.itemId = s, i -> i.itemId).documentation("The id of the item to add.").addValidator(Validators.nonNull()).addValidator(Item.VALIDATOR_CACHE.getValidator()).add()).append(new KeyedCodec("Quantity", (Codec)Codec.INTEGER), (o, v) -> o.quantity = v.intValue(), o -> Integer.valueOf(o.quantity)).documentation("The amount of the item to add.").add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void interactWithBlock(@Nonnull World world, @Nonnull CommandBuffer<EntityStore> commandBuffer, @Nonnull InteractionType type, @Nonnull InteractionContext context, @Nullable ItemStack itemInHand, @Nonnull Vector3i targetBlock, @Nonnull CooldownHandler cooldownHandler) {
/*    */     LivingEntity livingEntity;
/* 64 */     if (this.quantity <= 0 || this.itemId == null)
/*    */       return; 
/* 66 */     Ref<EntityStore> ref = context.getEntity();
/*    */ 
/*    */     
/* 69 */     Entity entity = EntityUtils.getEntity(ref, (ComponentAccessor)commandBuffer); if (entity instanceof LivingEntity) { livingEntity = (LivingEntity)entity; } else { return; }
/* 70 */      livingEntity.getInventory().getCombinedHotbarFirst().addItemStack(new ItemStack(this.itemId, this.quantity));
/*    */   }
/*    */   
/*    */   protected void simulateInteractWithBlock(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nullable ItemStack itemInHand, @Nonnull World world, @Nonnull Vector3i targetBlock) {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\client\AddItemInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */