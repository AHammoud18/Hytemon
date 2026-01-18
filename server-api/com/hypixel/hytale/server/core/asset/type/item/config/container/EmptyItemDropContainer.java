/*    */ package com.hypixel.hytale.server.core.asset.type.item.config.container;
/*    */ 
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.server.core.asset.type.item.config.ItemDrop;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import java.util.function.DoubleSupplier;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class EmptyItemDropContainer extends ItemDropContainer {
/* 12 */   public static final BuilderCodec<EmptyItemDropContainer> CODEC = BuilderCodec.builder(EmptyItemDropContainer.class, EmptyItemDropContainer::new, ItemDropContainer.DEFAULT_CODEC)
/* 13 */     .build();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void populateDrops(List<ItemDrop> drops, @Nonnull DoubleSupplier chanceProvider, Set<String> droplistReferences) {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<ItemDrop> getAllDrops(List<ItemDrop> list) {
/* 27 */     return list;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 32 */     return "EmptyItemDropContainer{}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\item\config\container\EmptyItemDropContainer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */