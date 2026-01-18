/*    */ package com.hypixel.hytale.server.npc.role;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.inventory.Inventory;
/*    */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*    */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*    */ import com.hypixel.hytale.server.npc.util.InventoryHelper;
/*    */ import java.util.logging.Level;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
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
/*    */ public class RoleUtils
/*    */ {
/*    */   public static void setHotbarItems(@Nonnull NPCEntity npcComponent, @Nonnull String[] hotbarItems) {
/* 23 */     Inventory inventory = npcComponent.getInventory();
/* 24 */     for (byte i = 0; i < hotbarItems.length; i = (byte)(i + 1)) {
/* 25 */       InventoryHelper.setHotbarItem(inventory, hotbarItems[i], i);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void setOffHandItems(@Nonnull NPCEntity npcComponent, @Nonnull String[] offHandItems) {
/* 36 */     Inventory inventory = npcComponent.getInventory();
/* 37 */     for (byte i = 0; i < offHandItems.length; i = (byte)(i + 1)) {
/* 38 */       InventoryHelper.setOffHandItem(inventory, offHandItems[i], i);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void setItemInHand(@Nonnull NPCEntity npcComponent, @Nullable String itemInHand) {
/* 49 */     if (!InventoryHelper.useItem(npcComponent.getInventory(), itemInHand)) {
/* 50 */       NPCPlugin.get().getLogger().at(Level.WARNING).log("NPC of type '%s': Failed to use item '%s'", npcComponent.getRoleName(), itemInHand);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void setArmor(@Nonnull NPCEntity npcComponent, @Nullable String armor) {
/* 61 */     if (!InventoryHelper.useArmor(npcComponent.getInventory().getArmor(), armor))
/* 62 */       NPCPlugin.get().getLogger().at(Level.WARNING).log("NPC of type '%s': Failed to use armor '%s'", npcComponent.getRoleName(), armor); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\role\RoleUtils.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */