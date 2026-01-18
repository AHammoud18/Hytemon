/*     */ package com.hypixel.hytale.server.npc.util;
/*     */ 
/*     */ import com.hypixel.hytale.common.util.StringUtil;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.ItemArmor;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.ItemDropList;
/*     */ import com.hypixel.hytale.server.core.inventory.Inventory;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.inventory.container.CombinedItemContainer;
/*     */ import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
/*     */ import com.hypixel.hytale.server.core.modules.item.ItemModule;
/*     */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InventoryHelper
/*     */ {
/*     */   public static final short DEFAULT_NPC_HOTBAR_SLOTS = 3;
/*     */   public static final short MAX_NPC_HOTBAR_SLOTS = 8;
/*     */   public static final short DEFAULT_NPC_INVENTORY_SLOTS = 0;
/*     */   public static final short DEFAULT_NPC_UTILITY_SLOTS = 0;
/*     */   public static final short MAX_NPC_UTILITY_SLOTS = 4;
/*     */   public static final short DEFAULT_NPC_TOOL_SLOTS = 0;
/*     */   public static final short MAX_NPC_INVENTORY_SLOTS = 36;
/*     */   
/*     */   public static boolean matchesItem(@Nullable String pattern, @Nonnull ItemStack itemStack) {
/*  35 */     if (pattern == null || pattern.isEmpty() || ItemStack.isEmpty(itemStack)) {
/*  36 */       return false;
/*     */     }
/*  38 */     return StringUtil.isGlobMatching(pattern, itemStack.getItem().getId());
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean matchesItem(@Nullable List<String> patterns, @Nonnull ItemStack itemStack) {
/*  43 */     if (patterns == null || patterns.isEmpty() || ItemStack.isEmpty(itemStack)) {
/*  44 */       return false;
/*     */     }
/*  46 */     return matchesPatterns(patterns, itemStack.getItem().getId());
/*     */   }
/*     */ 
/*     */   
/*     */   protected static boolean matchesPatterns(@Nonnull List<String> patterns, @Nullable String name) {
/*  51 */     if (name == null || name.isEmpty()) {
/*  52 */       return false;
/*     */     }
/*     */     
/*  55 */     for (int i = 0; i < patterns.size(); i++) {
/*  56 */       String pattern = patterns.get(i);
/*  57 */       if (pattern != null && !pattern.isEmpty() && StringUtil.isGlobMatching(pattern, name)) {
/*  58 */         return true;
/*     */       }
/*     */     } 
/*  61 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean itemKeyExists(@Nullable String name) {
/*  65 */     if (name != null && !name.isEmpty()) {
/*  66 */       return ItemModule.exists(name);
/*     */     }
/*  68 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean itemKeyIsBlockType(@Nullable String name) {
/*  72 */     if (name != null && !name.isEmpty()) {
/*  73 */       Item item = (Item)Item.getAssetMap().getAsset(name);
/*  74 */       if (item != null && item.hasBlockType()) {
/*  75 */         return true;
/*     */       }
/*     */     } 
/*  78 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean itemDropListKeyExists(@Nullable String name) {
/*  82 */     if (name != null && !name.isEmpty()) {
/*  83 */       ItemDropList dropList = (ItemDropList)ItemDropList.getAssetMap().getAsset(name);
/*  84 */       return (dropList != null);
/*     */     } 
/*  86 */     return false;
/*     */   }
/*     */   
/*     */   public static byte findHotbarSlotWithItem(@Nonnull Inventory inventory, String name) {
/*  90 */     ItemContainer hotbar = inventory.getHotbar();
/*     */     
/*  92 */     for (byte i = 0; i < hotbar.getCapacity(); i = (byte)(i + 1)) {
/*  93 */       if (matchesItem(name, hotbar.getItemStack((short)i))) {
/*  94 */         return i;
/*     */       }
/*     */     } 
/*  97 */     return -1;
/*     */   }
/*     */   
/*     */   public static short findHotbarSlotWithItem(@Nonnull Inventory inventory, List<String> name) {
/* 101 */     ItemContainer hotbar = inventory.getHotbar();
/*     */     
/* 103 */     for (short i = 0; i < hotbar.getCapacity(); i = (short)(i + 1)) {
/* 104 */       if (matchesItem(name, hotbar.getItemStack(i))) {
/* 105 */         return i;
/*     */       }
/*     */     } 
/* 108 */     return -1;
/*     */   }
/*     */   
/*     */   public static byte findHotbarEmptySlot(@Nonnull Inventory inventory) {
/* 112 */     ItemContainer hotbar = inventory.getHotbar();
/*     */     
/* 114 */     for (byte i = 0; i < hotbar.getCapacity(); i = (byte)(i + 1)) {
/* 115 */       if (ItemStack.isEmpty(hotbar.getItemStack((short)i))) {
/* 116 */         return i;
/*     */       }
/*     */     } 
/* 119 */     return -1;
/*     */   }
/*     */   
/*     */   public static short findInventorySlotWithItem(@Nonnull Inventory inventory, String name) {
/* 123 */     CombinedItemContainer container = inventory.getCombinedHotbarFirst();
/*     */     
/* 125 */     for (short i = 0; i < container.getCapacity(); i = (short)(i + 1)) {
/* 126 */       if (matchesItem(name, container.getItemStack(i))) {
/* 127 */         return i;
/*     */       }
/*     */     } 
/* 130 */     return -1;
/*     */   }
/*     */   
/*     */   public static short findInventorySlotWithItem(@Nonnull Inventory inventory, List<String> name) {
/* 134 */     CombinedItemContainer container = inventory.getCombinedHotbarFirst();
/*     */     
/* 136 */     for (short i = 0; i < container.getCapacity(); i = (short)(i + 1)) {
/* 137 */       if (matchesItem(name, container.getItemStack(i))) {
/* 138 */         return i;
/*     */       }
/*     */     } 
/* 141 */     return -1;
/*     */   }
/*     */   
/*     */   public static int countItems(@Nonnull ItemContainer container, List<String> name) {
/* 145 */     int count = 0;
/* 146 */     for (short i = 0; i < container.getCapacity(); i = (short)(i + 1)) {
/* 147 */       ItemStack item = container.getItemStack(i);
/* 148 */       if (matchesItem(name, item)) {
/* 149 */         count += item.getQuantity();
/*     */       }
/*     */     } 
/* 152 */     return count;
/*     */   }
/*     */   
/*     */   public static int countFreeSlots(@Nonnull ItemContainer container) {
/* 156 */     int count = 0;
/* 157 */     for (short i = 0; i < container.getCapacity(); i = (short)(i + 1)) {
/* 158 */       ItemStack item = container.getItemStack(i);
/* 159 */       if (item == null || item.isEmpty()) {
/* 160 */         count++;
/*     */       }
/*     */     } 
/* 163 */     return count;
/*     */   }
/*     */   
/*     */   public static boolean hotbarContainsItem(@Nonnull Inventory inventory, String name) {
/* 167 */     return (findHotbarSlotWithItem(inventory, name) != -1);
/*     */   }
/*     */   
/*     */   public static boolean hotbarContainsItem(@Nonnull Inventory inventory, List<String> name) {
/* 171 */     return (findHotbarSlotWithItem(inventory, name) != -1);
/*     */   }
/*     */   
/*     */   public static boolean holdsItem(@Nonnull Inventory inventory, String name) {
/* 175 */     return matchesItem(name, inventory.getItemInHand());
/*     */   }
/*     */   
/*     */   public static boolean containsItem(@Nonnull Inventory inventory, String name) {
/* 179 */     return (findInventorySlotWithItem(inventory, name) != -1);
/*     */   }
/*     */   
/*     */   public static boolean containsItem(@Nonnull Inventory inventory, List<String> name) {
/* 183 */     return (findInventorySlotWithItem(inventory, name) != -1);
/*     */   }
/*     */   
/*     */   public static boolean clearItemInHand(@Nonnull Inventory inventory, byte slotHint) {
/* 187 */     if (ItemStack.isEmpty(inventory.getItemInHand())) return true;
/*     */     
/* 189 */     byte slot = findHotbarEmptySlot(inventory);
/* 190 */     if (slot >= 0) {
/* 191 */       inventory.setActiveHotbarSlot(slot);
/* 192 */       return true;
/*     */     } 
/* 194 */     slot = (slotHint != -1) ? slotHint : 0;
/*     */     
/* 196 */     inventory.getHotbar().removeItemStackFromSlot((short)slot);
/* 197 */     inventory.setActiveHotbarSlot(slot);
/* 198 */     return true;
/*     */   }
/*     */   
/*     */   public static void removeItemInHand(@Nonnull Inventory inventory) {
/* 202 */     if (ItemStack.isEmpty(inventory.getItemInHand()))
/*     */       return; 
/* 204 */     byte activeHotbarSlot = inventory.getActiveHotbarSlot();
/* 205 */     if (activeHotbarSlot == -1)
/*     */       return; 
/* 207 */     inventory.getHotbar().removeItemStackFromSlot((short)activeHotbarSlot);
/*     */   }
/*     */   
/*     */   public static boolean checkHotbarSlot(@Nonnull Inventory inventory, byte slot) {
/* 211 */     ItemContainer hotbar = inventory.getHotbar();
/* 212 */     if (slot >= hotbar.getCapacity() || slot < 0) {
/* 213 */       NPCPlugin.get().getLogger().at(Level.WARNING).log("Invalid hotbar slot %s. Max is %s", slot, hotbar.getCapacity() - 1);
/* 214 */       return false;
/*     */     } 
/* 216 */     return true;
/*     */   }
/*     */   
/*     */   public static boolean checkOffHandSlot(@Nonnull Inventory inventory, byte slot) {
/* 220 */     ItemContainer utility = inventory.getUtility();
/* 221 */     if (slot >= utility.getCapacity() || slot < -1) {
/* 222 */       NPCPlugin.get().getLogger().at(Level.WARNING).log("Invalid utility slot %s. Max is %s, Min is %s", Byte.valueOf(slot), Integer.valueOf(utility.getCapacity() - 1), Integer.valueOf(-1));
/* 223 */       return false;
/*     */     } 
/* 225 */     return true;
/*     */   }
/*     */   
/*     */   public static void setHotbarSlot(@Nonnull Inventory inventory, byte slot) {
/* 229 */     if (inventory.getActiveHotbarSlot() == slot)
/*     */       return; 
/* 231 */     if (checkHotbarSlot(inventory, slot)) {
/* 232 */       inventory.setActiveHotbarSlot(slot);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void setOffHandSlot(@Nonnull Inventory inventory, byte slot) {
/* 237 */     if (inventory.getActiveUtilitySlot() == slot)
/*     */       return; 
/* 239 */     if (checkOffHandSlot(inventory, slot)) {
/* 240 */       inventory.setActiveUtilitySlot(slot);
/*     */     }
/*     */   }
/*     */   
/*     */   public static boolean setHotbarItem(@Nonnull Inventory inventory, @Nullable String name, byte slot) {
/* 245 */     if (name == null || name.isEmpty() || !itemKeyExists(name)) return false;
/*     */     
/* 247 */     ItemContainer hotbar = inventory.getHotbar();
/* 248 */     if (!checkHotbarSlot(inventory, slot)) return false;
/*     */     
/* 250 */     if (matchesItem(name, hotbar.getItemStack((short)slot))) return true;
/*     */     
/* 252 */     hotbar.setItemStackForSlot((short)slot, createItem(name));
/* 253 */     return true;
/*     */   }
/*     */   
/*     */   public static boolean setOffHandItem(@Nonnull Inventory inventory, @Nullable String name, byte slot) {
/* 257 */     if (name == null || name.isEmpty() || !itemKeyExists(name)) return false;
/*     */     
/* 259 */     ItemContainer utility = inventory.getUtility();
/* 260 */     if (!checkOffHandSlot(inventory, slot)) return false;
/*     */     
/* 262 */     if (matchesItem(name, utility.getItemStack((short)slot))) return true;
/*     */     
/* 264 */     utility.setItemStackForSlot((short)slot, createItem(name));
/* 265 */     return true;
/*     */   }
/*     */   
/*     */   public static boolean useItem(@Nonnull Inventory inventory, @Nullable String name, byte slotHint) {
/* 269 */     if (name == null || name.isEmpty() || !itemKeyExists(name)) return false;
/*     */     
/* 271 */     if (holdsItem(inventory, name)) return true;
/*     */     
/* 273 */     byte slot = findHotbarSlotWithItem(inventory, name);
/* 274 */     if (slot >= 0) {
/* 275 */       inventory.setActiveHotbarSlot(slot);
/* 276 */       return true;
/*     */     } 
/*     */     
/* 279 */     if (slotHint == -1) {
/* 280 */       slotHint = findHotbarEmptySlot(inventory);
/*     */     }
/* 282 */     if (slotHint == -1) {
/* 283 */       slotHint = 0;
/*     */     }
/* 285 */     inventory.getHotbar().setItemStackForSlot((short)slotHint, createItem(name));
/* 286 */     inventory.setActiveHotbarSlot(slotHint);
/* 287 */     return true;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static ItemStack createItem(@Nullable String name) {
/* 292 */     if (!itemKeyExists(name)) {
/* 293 */       return null;
/*     */     }
/* 295 */     return new ItemStack(name, 1);
/*     */   }
/*     */   
/*     */   public static boolean useItem(@Nonnull Inventory inventory, @Nullable String name) {
/* 299 */     return (name == null || name.isEmpty()) ? clearItemInHand(inventory, (byte)-1) : useItem(inventory, name, (byte)-1);
/*     */   }
/*     */   
/*     */   public static boolean useArmor(@Nonnull ItemContainer armorInventory, @Nullable String armorItem) {
/* 303 */     ItemStack itemStack = createItem(armorItem);
/* 304 */     return useArmor(armorInventory, itemStack);
/*     */   }
/*     */   
/*     */   public static boolean useArmor(@Nonnull ItemContainer armorInventory, @Nullable ItemStack itemStack) {
/* 308 */     if (itemStack == null) {
/* 309 */       return false;
/*     */     }
/*     */     
/* 312 */     Item item = itemStack.getItem();
/* 313 */     if (item == null) {
/* 314 */       return false;
/*     */     }
/*     */     
/* 317 */     ItemArmor armor = item.getArmor();
/* 318 */     if (armor == null) {
/* 319 */       return false;
/*     */     }
/*     */     
/* 322 */     short slot = (short)armor.getArmorSlot().ordinal();
/* 323 */     if (slot < 0 || slot > armorInventory.getCapacity()) {
/* 324 */       return false;
/*     */     }
/* 326 */     return armorInventory.setItemStackForSlot(slot, itemStack).succeeded();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\np\\util\InventoryHelper.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */