/*    */ package com.hypixel.hytale.server.core.inventory.container.filter;
/*    */ 
/*    */ import com.hypixel.hytale.protocol.ItemArmorSlot;
/*    */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ArmorSlotAddFilter
/*    */   implements ItemSlotFilter
/*    */ {
/*    */   private final ItemArmorSlot itemArmorSlot;
/*    */   
/*    */   public ArmorSlotAddFilter(ItemArmorSlot itemArmorSlot) {
/* 20 */     this.itemArmorSlot = itemArmorSlot;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(@Nullable Item item) {
/* 25 */     return (item == null || (item.getArmor() != null && item.getArmor().getArmorSlot() == this.itemArmorSlot));
/*    */   }
/*    */   
/*    */   public ItemArmorSlot getItemArmorSlot() {
/* 29 */     return this.itemArmorSlot;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\inventory\container\filter\ArmorSlotAddFilter.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */