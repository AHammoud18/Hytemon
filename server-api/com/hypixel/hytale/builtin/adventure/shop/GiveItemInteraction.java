/*    */ package com.hypixel.hytale.builtin.adventure.shop;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.entity.entities.player.pages.choices.ChoiceInteraction;
/*    */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GiveItemInteraction
/*    */   extends ChoiceInteraction
/*    */ {
/*    */   public static final BuilderCodec<GiveItemInteraction> CODEC;
/*    */   protected String itemId;
/*    */   
/*    */   static {
/* 30 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(GiveItemInteraction.class, GiveItemInteraction::new, ChoiceInteraction.BASE_CODEC).append(new KeyedCodec("ItemId", (Codec)Codec.STRING), (giveItemInteraction, blockTypeKey) -> giveItemInteraction.itemId = blockTypeKey, giveItemInteraction -> giveItemInteraction.itemId).addValidator(Validators.nonNull()).addValidator(Item.VALIDATOR_CACHE.getValidator()).add()).append(new KeyedCodec("Quantity", (Codec)Codec.INTEGER), (giveItemInteraction, integer) -> giveItemInteraction.quantity = integer.intValue(), giveItemInteraction -> Integer.valueOf(giveItemInteraction.quantity)).addValidator(Validators.greaterThanOrEqual(Integer.valueOf(1))).add()).build();
/*    */   }
/*    */   
/* 33 */   protected int quantity = 1;
/*    */   
/*    */   public GiveItemInteraction(String itemId, int quantity) {
/* 36 */     this.itemId = itemId;
/* 37 */     this.quantity = quantity;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getItemId() {
/* 44 */     return this.itemId;
/*    */   }
/*    */   
/*    */   public int getQuantity() {
/* 48 */     return this.quantity;
/*    */   }
/*    */ 
/*    */   
/*    */   public void run(@Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef) {
/* 53 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 54 */     playerComponent.getInventory().getCombinedHotbarFirst().addItemStack(new ItemStack(this.itemId, this.quantity));
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 60 */     return "GiveItemInteraction{itemId=" + this.itemId + ", quantity=" + this.quantity + "} " + super
/*    */ 
/*    */       
/* 63 */       .toString();
/*    */   }
/*    */   
/*    */   protected GiveItemInteraction() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\shop\GiveItemInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */