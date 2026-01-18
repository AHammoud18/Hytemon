/*    */ package com.hypixel.hytale.server.core.entity.entities.player.windows;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import com.hypixel.hytale.protocol.packets.window.WindowType;
/*    */ import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
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
/*    */ public class ContainerWindow
/*    */   extends Window
/*    */   implements ItemContainerWindow
/*    */ {
/*    */   @Nonnull
/*    */   private final JsonObject windowData;
/*    */   @Nonnull
/*    */   private final ItemContainer itemContainer;
/*    */   
/*    */   public ContainerWindow(@Nonnull ItemContainer itemContainer) {
/* 31 */     super(WindowType.Container);
/* 32 */     this.itemContainer = itemContainer;
/* 33 */     this.windowData = new JsonObject();
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public JsonObject getData() {
/* 38 */     return this.windowData;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onOpen0() {
/* 43 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onClose0() {}
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public ItemContainer getItemContainer() {
/* 53 */     return this.itemContainer;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\entities\player\windows\ContainerWindow.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */