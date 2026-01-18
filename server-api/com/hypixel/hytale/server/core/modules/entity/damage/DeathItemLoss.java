/*    */ package com.hypixel.hytale.server.core.modules.entity.damage;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ import com.hypixel.hytale.server.core.asset.type.gameplay.DeathConfig;
/*    */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*    */ import java.util.function.Supplier;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DeathItemLoss
/*    */ {
/*    */   public static final BuilderCodec<DeathItemLoss> CODEC;
/*    */   private DeathConfig.ItemsLossMode lossMode;
/*    */   private ItemStack[] itemsLost;
/*    */   private double amountLossPercentage;
/*    */   private double durabilityLossPercentage;
/*    */   
/*    */   static {
/* 39 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(DeathItemLoss.class, DeathItemLoss::new).append(new KeyedCodec("LossMode", (Codec)DeathConfig.LOSS_MODE_CODEC), (loss, o) -> loss.lossMode = o, loss -> loss.lossMode).add()).append(new KeyedCodec("ItemsLostOnDeath", (Codec)new ArrayCodec((Codec)ItemStack.CODEC, x$0 -> new ItemStack[x$0])), (loss, items) -> loss.itemsLost = items, loss -> loss.itemsLost).add()).append(new KeyedCodec("ItemsAmountLossPercentage", (Codec)Codec.DOUBLE), (loss, percent) -> loss.amountLossPercentage = percent.doubleValue(), loss -> Double.valueOf(loss.amountLossPercentage)).add()).append(new KeyedCodec("ItemsDurabilityLossPercentage", (Codec)Codec.DOUBLE), (loss, percent) -> loss.durabilityLossPercentage = percent.doubleValue(), loss -> Double.valueOf(loss.durabilityLossPercentage)).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 46 */   private static final DeathItemLoss NO_LOSS_MODE = new DeathItemLoss(DeathConfig.ItemsLossMode.NONE, ItemStack.EMPTY_ARRAY, 0.0D, 0.0D);
/*    */   
/*    */   private DeathItemLoss() {}
/*    */   
/*    */   public DeathItemLoss(DeathConfig.ItemsLossMode lossMode, ItemStack[] itemsLost, double amountLossPercentage, double durabilityLossPercentage) {
/* 51 */     this.lossMode = lossMode;
/* 52 */     this.itemsLost = itemsLost;
/* 53 */     this.amountLossPercentage = amountLossPercentage;
/* 54 */     this.durabilityLossPercentage = durabilityLossPercentage;
/*    */   }
/*    */   
/*    */   public static DeathItemLoss noLossMode() {
/* 58 */     return NO_LOSS_MODE;
/*    */   }
/*    */   
/*    */   public DeathConfig.ItemsLossMode getLossMode() {
/* 62 */     return this.lossMode;
/*    */   }
/*    */   
/*    */   public ItemStack[] getItemsLost() {
/* 66 */     return (this.itemsLost == null) ? ItemStack.EMPTY_ARRAY : this.itemsLost;
/*    */   }
/*    */   
/*    */   public double getAmountLossPercentage() {
/* 70 */     return this.amountLossPercentage;
/*    */   }
/*    */   
/*    */   public double getDurabilityLossPercentage() {
/* 74 */     return this.durabilityLossPercentage;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\damage\DeathItemLoss.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */