/*    */ package com.hypixel.hytale.server.core.asset.type.item.config.container;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.LateValidator;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.server.core.asset.type.item.config.ItemDrop;
/*    */ import com.hypixel.hytale.server.core.asset.type.item.config.ItemDropList;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import java.util.function.DoubleSupplier;
/*    */ import java.util.function.Supplier;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DroplistItemDropContainer
/*    */   extends ItemDropContainer
/*    */ {
/*    */   public static final BuilderCodec<DroplistItemDropContainer> CODEC;
/*    */   String droplistId;
/*    */   
/*    */   static {
/* 24 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(DroplistItemDropContainer.class, DroplistItemDropContainer::new, ItemDropContainer.DEFAULT_CODEC).append(new KeyedCodec("DroplistId", (Codec)Codec.STRING), (droplistItemDropContainer, s) -> droplistItemDropContainer.droplistId = s, droplistItemDropContainer -> droplistItemDropContainer.droplistId).addValidator(Validators.nonNull()).addValidatorLate(() -> ItemDropList.VALIDATOR_CACHE.getValidator().late()).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void populateDrops(List<ItemDrop> drops, DoubleSupplier chanceProvider, Set<String> droplistReferences) {
/* 30 */     if (!droplistReferences.add(this.droplistId)) {
/*    */       return;
/*    */     }
/*    */     
/* 34 */     ItemDropList droplist = (ItemDropList)ItemDropList.getAssetMap().getAsset(this.droplistId);
/* 35 */     if (droplist == null) {
/*    */       return;
/*    */     }
/*    */     
/* 39 */     droplist.getContainer().populateDrops(drops, chanceProvider, droplistReferences);
/*    */   }
/*    */ 
/*    */   
/*    */   public List<ItemDrop> getAllDrops(List<ItemDrop> list) {
/* 44 */     ItemDropList droplist = (ItemDropList)ItemDropList.getAssetMap().getAsset(this.droplistId);
/* 45 */     if (droplist == null) {
/* 46 */       return list;
/*    */     }
/*    */     
/* 49 */     droplist.getContainer().getAllDrops(list);
/*    */     
/* 51 */     return list;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 56 */     return "DroplistItemDropContainer{droplistId='" + this.droplistId + "', weight=" + this.weight + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\item\config\container\DroplistItemDropContainer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */