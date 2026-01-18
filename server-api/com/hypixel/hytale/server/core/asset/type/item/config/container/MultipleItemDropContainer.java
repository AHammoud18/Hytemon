/*    */ package com.hypixel.hytale.server.core.asset.type.item.config.container;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ import com.hypixel.hytale.math.util.MathUtil;
/*    */ import com.hypixel.hytale.server.core.asset.type.item.config.ItemDrop;
/*    */ import java.util.Arrays;
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
/*    */ public class MultipleItemDropContainer
/*    */   extends ItemDropContainer
/*    */ {
/*    */   public static final BuilderCodec<MultipleItemDropContainer> CODEC;
/*    */   protected ItemDropContainer[] containers;
/*    */   
/*    */   static {
/* 37 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(MultipleItemDropContainer.class, MultipleItemDropContainer::new, ItemDropContainer.DEFAULT_CODEC).addField(new KeyedCodec("MinCount", (Codec)Codec.INTEGER), (multipleItemDropContainer, integer) -> multipleItemDropContainer.minCount = integer.intValue(), multipleItemDropContainer -> Integer.valueOf(multipleItemDropContainer.minCount))).addField(new KeyedCodec("MaxCount", (Codec)Codec.INTEGER), (multipleItemDropContainer, integer) -> multipleItemDropContainer.maxCount = integer.intValue(), multipleItemDropContainer -> Integer.valueOf(multipleItemDropContainer.maxCount))).addField(new KeyedCodec("Containers", (Codec)new ArrayCodec((Codec)ItemDropContainer.CODEC, x$0 -> new ItemDropContainer[x$0])), (multipleItemDropContainer, o) -> multipleItemDropContainer.containers = o, multipleItemDropContainer -> multipleItemDropContainer.containers)).build();
/*    */   }
/*    */   
/* 40 */   protected int minCount = 1;
/* 41 */   protected int maxCount = 1;
/*    */   
/*    */   public MultipleItemDropContainer(ItemDropContainer[] containers, double chance, int minCount, int maxCount) {
/* 44 */     super(chance);
/* 45 */     this.containers = containers;
/* 46 */     this.minCount = minCount;
/* 47 */     this.maxCount = maxCount;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void populateDrops(List<ItemDrop> drops, @Nonnull DoubleSupplier chanceProvider, Set<String> droplistReferences) {
/* 55 */     int count = (int)MathUtil.fastRound(chanceProvider.getAsDouble() * (this.maxCount - this.minCount) + this.minCount);
/* 56 */     for (int i = 0; i < count; i++) {
/* 57 */       for (ItemDropContainer container : this.containers) {
/* 58 */         if (container.getWeight() >= chanceProvider.getAsDouble() * 100.0D) {
/* 59 */           container.populateDrops(drops, chanceProvider, droplistReferences);
/*    */         }
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public List<ItemDrop> getAllDrops(List<ItemDrop> list) {
/* 67 */     for (ItemDropContainer container : this.containers) {
/* 68 */       container.getAllDrops(list);
/*    */     }
/* 70 */     return list;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 76 */     return "MultipleItemDropContainer{weight=" + this.weight + ", containers=" + 
/*    */       
/* 78 */       Arrays.toString((Object[])this.containers) + "}";
/*    */   }
/*    */   
/*    */   public MultipleItemDropContainer() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\item\config\container\MultipleItemDropContainer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */