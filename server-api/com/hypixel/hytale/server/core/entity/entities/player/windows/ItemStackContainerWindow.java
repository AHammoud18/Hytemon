/*    */ package com.hypixel.hytale.server.core.entity.entities.player.windows;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import com.hypixel.hytale.event.EventRegistration;
/*    */ import com.hypixel.hytale.protocol.packets.window.WindowType;
/*    */ import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
/*    */ import com.hypixel.hytale.server.core.inventory.container.ItemStackItemContainer;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemStackContainerWindow
/*    */   extends Window
/*    */   implements ItemContainerWindow
/*    */ {
/*    */   @Nonnull
/* 20 */   private final JsonObject windowData = new JsonObject();
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   private final ItemStackItemContainer itemStackItemContainer;
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   private EventRegistration eventRegistration;
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStackContainerWindow(@Nonnull ItemStackItemContainer itemStackItemContainer) {
/* 33 */     super(WindowType.Container);
/* 34 */     this.itemStackItemContainer = itemStackItemContainer;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public JsonObject getData() {
/* 39 */     return this.windowData;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onOpen0() {
/* 44 */     this.eventRegistration = this.itemStackItemContainer.getParentContainer().registerChangeEvent(event -> {
/*    */           if (!this.itemStackItemContainer.isItemStackValid()) {
/*    */             close();
/*    */           }
/*    */         });
/* 49 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onClose0() {
/* 54 */     this.eventRegistration.unregister();
/* 55 */     this.eventRegistration = null;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public ItemContainer getItemContainer() {
/* 61 */     return (ItemContainer)this.itemStackItemContainer;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\entities\player\windows\ItemStackContainerWindow.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */