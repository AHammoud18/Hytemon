/*    */ package com.hypixel.hytale.server.core.entity.entities.player.windows;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.protocol.packets.window.SortItemsAction;
/*    */ import com.hypixel.hytale.protocol.packets.window.WindowAction;
/*    */ import com.hypixel.hytale.protocol.packets.window.WindowType;
/*    */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*    */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
/*    */ import com.hypixel.hytale.server.core.inventory.container.SortType;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ContainerBlockWindow
/*    */   extends BlockWindow
/*    */   implements ItemContainerWindow
/*    */ {
/*    */   @Nonnull
/*    */   private final JsonObject windowData;
/*    */   @Nonnull
/*    */   private final ItemContainer itemContainer;
/*    */   
/*    */   public ContainerBlockWindow(int x, int y, int z, int rotationIndex, @Nonnull BlockType blockType, @Nonnull ItemContainer itemContainer) {
/* 49 */     super(WindowType.Container, x, y, z, rotationIndex, blockType);
/* 50 */     this.itemContainer = itemContainer;
/*    */     
/* 52 */     this.windowData = new JsonObject();
/* 53 */     Item item = blockType.getItem();
/* 54 */     this.windowData.addProperty("blockItemId", item.getId());
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public JsonObject getData() {
/* 59 */     return this.windowData;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onOpen0() {
/* 64 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onClose0() {}
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public ItemContainer getItemContainer() {
/* 74 */     return this.itemContainer;
/*    */   }
/*    */   
/*    */   public void handleAction(@Nonnull Ref<EntityStore> ref, @Nonnull Store<EntityStore> store, @Nonnull WindowAction action) {
/*    */     SortItemsAction sortAction;
/* 79 */     if (action instanceof SortItemsAction) { sortAction = (SortItemsAction)action; } else { return; }
/* 80 */      SortType sortType = SortType.fromPacket(sortAction.sortType);
/*    */     
/* 82 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 83 */     assert playerComponent != null;
/*    */     
/* 85 */     playerComponent.getInventory().setSortType(sortType);
/* 86 */     this.itemContainer.sortItems(sortType);
/* 87 */     invalidate();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\entities\player\windows\ContainerBlockWindow.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */