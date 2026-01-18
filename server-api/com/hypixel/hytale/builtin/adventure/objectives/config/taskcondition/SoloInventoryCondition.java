/*     */ package com.hypixel.hytale.builtin.adventure.objectives.config.taskcondition;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.config.task.BlockTagOrItemIdField;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.inventory.Inventory;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
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
/*     */ public class SoloInventoryCondition
/*     */   extends TaskConditionAsset
/*     */ {
/*     */   public static final BuilderCodec<SoloInventoryCondition> CODEC;
/*     */   protected BlockTagOrItemIdField blockTypeOrTagTask;
/*     */   
/*     */   static {
/*  40 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(SoloInventoryCondition.class, SoloInventoryCondition::new).append(new KeyedCodec("BlockTagOrItemId", (Codec)BlockTagOrItemIdField.CODEC), (soloInventoryCondition, blockTagOrItemIdField) -> soloInventoryCondition.blockTypeOrTagTask = blockTagOrItemIdField, soloInventoryCondition -> soloInventoryCondition.blockTypeOrTagTask).addValidator(Validators.nonNull()).add()).append(new KeyedCodec("Quantity", (Codec)Codec.INTEGER), (soloInventoryCondition, integer) -> soloInventoryCondition.quantity = integer.intValue(), soloInventoryCondition -> Integer.valueOf(soloInventoryCondition.quantity)).addValidator(Validators.greaterThan(Integer.valueOf(0))).add()).append(new KeyedCodec("ConsumeOnCompletion", (Codec)Codec.BOOLEAN), (soloInventoryCondition, aBoolean) -> soloInventoryCondition.consumeOnCompletion = aBoolean.booleanValue(), soloInventoryCondition -> Boolean.valueOf(soloInventoryCondition.consumeOnCompletion)).add()).append(new KeyedCodec("HoldInHand", (Codec)Codec.BOOLEAN), (soloInventoryCondition, aBoolean) -> soloInventoryCondition.holdInHand = aBoolean.booleanValue(), soloInventoryCondition -> Boolean.valueOf(soloInventoryCondition.holdInHand)).add()).build();
/*     */   }
/*     */   
/*  43 */   protected int quantity = 1;
/*     */   protected boolean consumeOnCompletion;
/*     */   protected boolean holdInHand;
/*     */   
/*     */   public BlockTagOrItemIdField getBlockTypeOrTagTask() {
/*  48 */     return this.blockTypeOrTagTask;
/*     */   }
/*     */   
/*     */   public int getQuantity() {
/*  52 */     return this.quantity;
/*     */   }
/*     */   
/*     */   public boolean isConsumeOnCompletion() {
/*  56 */     return this.consumeOnCompletion;
/*     */   }
/*     */   
/*     */   public boolean isHoldInHand() {
/*  60 */     return this.holdInHand;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isConditionFulfilled(@Nonnull ComponentAccessor<EntityStore> componentAccessor, @Nonnull Ref<EntityStore> ref, Set<UUID> objectivePlayers) {
/*  65 */     Player playerComponent = (Player)componentAccessor.getComponent(ref, Player.getComponentType());
/*  66 */     if (playerComponent == null) return false;
/*     */     
/*  68 */     Inventory inventory = playerComponent.getInventory();
/*  69 */     if (this.holdInHand) {
/*  70 */       ItemStack itemInHand = inventory.getItemInHand();
/*  71 */       if (!this.blockTypeOrTagTask.isBlockTypeIncluded(itemInHand.getItemId())) return false; 
/*  72 */       return (inventory.getItemInHand().getQuantity() >= this.quantity);
/*     */     } 
/*  74 */     return (inventory.getCombinedHotbarFirst().countItemStacks(itemStack -> this.blockTypeOrTagTask.isBlockTypeIncluded(itemStack.getItemId())) >= this.quantity);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void consumeCondition(@Nonnull ComponentAccessor<EntityStore> componentAccessor, @Nonnull Ref<EntityStore> ref, Set<UUID> objectivePlayers) {
/*  80 */     Player playerComponent = (Player)componentAccessor.getComponent(ref, Player.getComponentType());
/*  81 */     if (playerComponent == null)
/*     */       return; 
/*  83 */     if (this.consumeOnCompletion) {
/*  84 */       this.blockTypeOrTagTask.consumeItemStacks((ItemContainer)playerComponent.getInventory().getCombinedHotbarFirst(), this.quantity);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object o) {
/*  90 */     if (this == o) return true; 
/*  91 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/*  93 */     SoloInventoryCondition that = (SoloInventoryCondition)o;
/*     */     
/*  95 */     if (this.quantity != that.quantity) return false; 
/*  96 */     if (this.consumeOnCompletion != that.consumeOnCompletion) return false; 
/*  97 */     if (this.holdInHand != that.holdInHand) return false; 
/*  98 */     return (this.blockTypeOrTagTask != null) ? this.blockTypeOrTagTask.equals(that.blockTypeOrTagTask) : ((that.blockTypeOrTagTask == null));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 103 */     int result = (this.blockTypeOrTagTask != null) ? this.blockTypeOrTagTask.hashCode() : 0;
/* 104 */     result = 31 * result + this.quantity;
/* 105 */     result = 31 * result + (this.consumeOnCompletion ? 1 : 0);
/* 106 */     result = 31 * result + (this.holdInHand ? 1 : 0);
/* 107 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 113 */     return "SoloInventoryCondition{blockTypeOrTagTask=" + String.valueOf(this.blockTypeOrTagTask) + ", quantity=" + this.quantity + ", consumeOnCompletion=" + this.consumeOnCompletion + ", holdInHand=" + this.holdInHand + "} " + super
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 118 */       .toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\config\taskcondition\SoloInventoryCondition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */