/*    */ package com.hypixel.hytale.server.npc.corecomponents.entity.filters;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.entity.EntityUtils;
/*    */ import com.hypixel.hytale.server.core.entity.LivingEntity;
/*    */ import com.hypixel.hytale.server.core.inventory.Inventory;
/*    */ import com.hypixel.hytale.server.core.inventory.container.CombinedItemContainer;
/*    */ import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.EntityFilterBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.entity.filters.builders.BuilderEntityFilterInventory;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.util.InventoryHelper;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class EntityFilterInventory
/*    */   extends EntityFilterBase
/*    */ {
/*    */   public static final int COST = 300;
/*    */   @Nullable
/*    */   protected final List<String> items;
/*    */   protected final int minCount;
/*    */   
/*    */   public EntityFilterInventory(@Nonnull BuilderEntityFilterInventory builder, @Nonnull BuilderSupport support) {
/* 30 */     String[] itemArray = builder.getItems(support);
/* 31 */     this.items = (itemArray != null) ? List.<String>of(itemArray) : null;
/* 32 */     int[] countRange = builder.getCount(support);
/* 33 */     this.minCount = countRange[0];
/* 34 */     this.maxCount = countRange[1];
/* 35 */     int[] freeSlotsRange = builder.getFreeSlotsRange(support);
/* 36 */     this.minFreeSlots = freeSlotsRange[0];
/* 37 */     this.maxFreeSlots = freeSlotsRange[1];
/*    */     
/* 39 */     this.checkFreeSlots = (this.minFreeSlots != BuilderEntityFilterInventory.DEFAULT_FREE_SLOT_RANGE[0] || this.maxFreeSlots != BuilderEntityFilterInventory.DEFAULT_FREE_SLOT_RANGE[1]);
/*    */   }
/*    */   protected final int maxCount; protected final int minFreeSlots; protected final int maxFreeSlots;
/*    */   protected final boolean checkFreeSlots;
/*    */   
/*    */   public boolean matchesEntity(@Nonnull Ref<EntityStore> ref, @Nonnull Ref<EntityStore> targetRef, @Nonnull Role role, @Nonnull Store<EntityStore> store) {
/* 45 */     LivingEntity entity = (LivingEntity)EntityUtils.getEntity(targetRef, (ComponentAccessor)store);
/* 46 */     Inventory inventory = entity.getInventory();
/*    */     
/* 48 */     CombinedItemContainer container = inventory.getCombinedHotbarUtilityConsumableStorage();
/* 49 */     int count = InventoryHelper.countItems((ItemContainer)container, this.items);
/* 50 */     if (count < this.minCount || count > this.maxCount) return false;
/*    */     
/* 52 */     if (this.checkFreeSlots) {
/* 53 */       int freeSlots = InventoryHelper.countFreeSlots((ItemContainer)container);
/* 54 */       return (freeSlots >= this.minFreeSlots && freeSlots <= this.maxFreeSlots);
/*    */     } 
/*    */     
/* 57 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int cost() {
/* 62 */     return 300;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\entity\filters\EntityFilterInventory.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */