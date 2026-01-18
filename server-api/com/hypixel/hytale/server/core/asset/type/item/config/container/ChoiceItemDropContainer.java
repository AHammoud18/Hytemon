/*    */ package com.hypixel.hytale.server.core.asset.type.item.config.container;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.common.map.IWeightedElement;
/*    */ import com.hypixel.hytale.common.map.IWeightedMap;
/*    */ import com.hypixel.hytale.common.map.WeightedMap;
/*    */ import com.hypixel.hytale.server.core.asset.type.item.config.ItemDrop;
/*    */ import com.hypixel.hytale.server.core.codec.WeightedMapCodec;
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
/*    */ public class ChoiceItemDropContainer
/*    */   extends ItemDropContainer
/*    */ {
/*    */   public static final BuilderCodec<ChoiceItemDropContainer> CODEC;
/*    */   protected IWeightedMap<ItemDropContainer> containers;
/*    */   
/*    */   static {
/* 38 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ChoiceItemDropContainer.class, ChoiceItemDropContainer::new, ItemDropContainer.DEFAULT_CODEC).addField(new KeyedCodec("Containers", (Codec)new WeightedMapCodec((Codec)ItemDropContainer.CODEC, (IWeightedElement[])ItemDropContainer.EMPTY_ARRAY)), (choiceItemDropContainer, o) -> choiceItemDropContainer.containers = o, choiceItemDropContainer -> choiceItemDropContainer.containers)).addField(new KeyedCodec("RollsMin", (Codec)Codec.INTEGER), (choiceItemDropContainer, i) -> choiceItemDropContainer.rollsMin = i.intValue(), choiceItemDropContainer -> Integer.valueOf(choiceItemDropContainer.rollsMin))).addField(new KeyedCodec("RollsMax", (Codec)Codec.INTEGER), (choiceItemDropContainer, i) -> choiceItemDropContainer.rollsMax = i.intValue(), choiceItemDropContainer -> Integer.valueOf(choiceItemDropContainer.rollsMax))).build();
/*    */   }
/*    */   
/* 41 */   protected int rollsMin = 1;
/* 42 */   protected int rollsMax = 1;
/*    */   
/*    */   public ChoiceItemDropContainer(ItemDropContainer[] containers, double chance) {
/* 45 */     super(chance);
/* 46 */     this
/*    */       
/* 48 */       .containers = WeightedMap.builder((Object[])ItemDropContainer.EMPTY_ARRAY).putAll((Object[])containers, ItemDropContainer::getWeight).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void populateDrops(List<ItemDrop> drops, DoubleSupplier chanceProvider, Set<String> droplistReferences) {
/* 56 */     int count = this.rollsMin + (int)(chanceProvider.getAsDouble() * (this.rollsMax - this.rollsMin + 1));
/*    */     
/* 58 */     for (int i = 0; i < count; i++) {
/* 59 */       ItemDropContainer drop = (ItemDropContainer)this.containers.get(chanceProvider);
/* 60 */       drop.populateDrops(drops, chanceProvider, droplistReferences);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public List<ItemDrop> getAllDrops(List<ItemDrop> list) {
/* 66 */     for (ItemDropContainer container : (ItemDropContainer[])this.containers.internalKeys()) {
/* 67 */       container.getAllDrops(list);
/*    */     }
/* 69 */     return list;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 75 */     return "ChoiceItemDropContainer{weight=" + this.weight + ", containers=" + String.valueOf(this.containers) + "}";
/*    */   }
/*    */   
/*    */   public ChoiceItemDropContainer() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\item\config\container\ChoiceItemDropContainer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */