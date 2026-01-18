/*    */ package com.hypixel.hytale.server.core.asset.type.item.config.container;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.server.core.asset.type.item.config.ItemDrop;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import java.util.function.DoubleSupplier;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SingleItemDropContainer
/*    */   extends ItemDropContainer
/*    */ {
/*    */   public static final BuilderCodec<SingleItemDropContainer> CODEC;
/*    */   @Nonnull
/*    */   protected ItemDrop drop;
/*    */   
/*    */   static {
/* 26 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(SingleItemDropContainer.class, SingleItemDropContainer::new, ItemDropContainer.DEFAULT_CODEC).append(new KeyedCodec("Item", (Codec)ItemDrop.CODEC), (singleItemDropContainer, itemDrop) -> singleItemDropContainer.drop = itemDrop, singleItemDropContainer -> singleItemDropContainer.drop).addValidator(Validators.nonNull()).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public SingleItemDropContainer(@Nonnull ItemDrop drop, double chance) {
/* 32 */     super(chance);
/* 33 */     this.drop = drop;
/*    */   }
/*    */ 
/*    */   
/*    */   protected SingleItemDropContainer() {}
/*    */   
/*    */   @Nonnull
/*    */   public ItemDrop getDrop() {
/* 41 */     return this.drop;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void populateDrops(@Nonnull List<ItemDrop> drops, DoubleSupplier chanceProvider, Set<String> droplistReferences) {
/* 46 */     drops.add(this.drop);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public List<ItemDrop> getAllDrops(@Nonnull List<ItemDrop> list) {
/* 52 */     list.add(this.drop);
/* 53 */     return list;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 59 */     return "SingleItemDropContainer{drop=" + String.valueOf(this.drop) + ", weight=" + this.weight + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\item\config\container\SingleItemDropContainer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */