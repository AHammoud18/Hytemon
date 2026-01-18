/*    */ package com.hypixel.hytale.server.core.asset.type.item.config.container;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.lookup.CodecMapCodec;
/*    */ import com.hypixel.hytale.common.map.IWeightedElement;
/*    */ import com.hypixel.hytale.server.core.asset.type.item.config.ItemDrop;
/*    */ import java.util.HashSet;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import java.util.function.DoubleSupplier;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ItemDropContainer
/*    */   implements IWeightedElement
/*    */ {
/*    */   public static final BuilderCodec<ItemDropContainer> DEFAULT_CODEC;
/*    */   
/*    */   static {
/* 26 */     DEFAULT_CODEC = ((BuilderCodec.Builder)BuilderCodec.abstractBuilder(ItemDropContainer.class).addField(new KeyedCodec("Weight", (Codec)Codec.DOUBLE), (itemDropContainer, aDouble) -> itemDropContainer.weight = aDouble.doubleValue(), itemDropContainer -> Double.valueOf(itemDropContainer.weight))).build();
/*    */   }
/* 28 */   public static final CodecMapCodec<ItemDropContainer> CODEC = new CodecMapCodec("Type");
/*    */   
/* 30 */   public static final ItemDropContainer[] EMPTY_ARRAY = new ItemDropContainer[0];
/*    */   
/* 32 */   protected double weight = 100.0D;
/*    */   
/*    */   public ItemDropContainer(double weight) {
/* 35 */     this.weight = weight;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double getWeight() {
/* 43 */     return this.weight;
/*    */   }
/*    */   
/*    */   public void populateDrops(List<ItemDrop> drops, DoubleSupplier chanceProvider, String droplistId) {
/* 47 */     Set<String> droplistReferences = new HashSet<>();
/* 48 */     droplistReferences.add(droplistId);
/* 49 */     populateDrops(drops, chanceProvider, droplistReferences);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 57 */     CODEC.register("Multiple", MultipleItemDropContainer.class, (Codec)MultipleItemDropContainer.CODEC);
/* 58 */     CODEC.register("Choice", ChoiceItemDropContainer.class, (Codec)ChoiceItemDropContainer.CODEC);
/* 59 */     CODEC.register("Single", SingleItemDropContainer.class, (Codec)SingleItemDropContainer.CODEC);
/* 60 */     CODEC.register("Droplist", DroplistItemDropContainer.class, (Codec)DroplistItemDropContainer.CODEC);
/* 61 */     CODEC.register("Empty", EmptyItemDropContainer.class, (Codec)EmptyItemDropContainer.CODEC);
/*    */   }
/*    */   
/*    */   protected ItemDropContainer() {}
/*    */   
/*    */   protected abstract void populateDrops(List<ItemDrop> paramList, DoubleSupplier paramDoubleSupplier, Set<String> paramSet);
/*    */   
/*    */   public abstract List<ItemDrop> getAllDrops(List<ItemDrop> paramList);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\item\config\container\ItemDropContainer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */