/*    */ package com.hypixel.hytale.server.core.entity.entities.player.windows;
/*    */ 
/*    */ import com.hypixel.hytale.protocol.ExtraResources;
/*    */ import com.hypixel.hytale.protocol.ItemQuantity;
/*    */ import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
/*    */ 
/*    */ public class MaterialExtraResourcesSection
/*    */ {
/*    */   private boolean valid;
/*    */   private ItemContainer itemContainer;
/*    */   private ItemQuantity[] extraMaterials;
/*    */   
/*    */   public void setExtraMaterials(ItemQuantity[] extraMaterials) {
/* 14 */     this.extraMaterials = extraMaterials;
/*    */   }
/*    */   
/*    */   public boolean isValid() {
/* 18 */     return this.valid;
/*    */   }
/*    */   
/*    */   public void setValid(boolean valid) {
/* 22 */     this.valid = valid;
/*    */   }
/*    */   
/*    */   public ExtraResources toPacket() {
/* 26 */     ExtraResources packet = new ExtraResources();
/* 27 */     packet.resources = this.extraMaterials;
/* 28 */     return packet;
/*    */   }
/*    */   
/*    */   public ItemContainer getItemContainer() {
/* 32 */     return this.itemContainer;
/*    */   }
/*    */   
/*    */   public void setItemContainer(ItemContainer itemContainer) {
/* 36 */     this.itemContainer = itemContainer;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\entities\player\windows\MaterialExtraResourcesSection.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */