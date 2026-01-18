/*    */ package com.hypixel.hytale.builtin.portals.utils;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.server.core.asset.type.item.config.metadata.AdventureMetadata;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*    */ import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
/*    */ import java.util.concurrent.atomic.AtomicBoolean;
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class CursedItems
/*    */ {
/*    */   public static boolean uncurseAll(ItemContainer itemContainer) {
/* 15 */     AtomicBoolean uncursedAny = new AtomicBoolean(false);
/* 16 */     itemContainer.replaceAll((slot, existing) -> {
/*    */           AdventureMetadata adventureMeta = (AdventureMetadata)existing.getFromMetadataOrNull("Adventure", (Codec)AdventureMetadata.CODEC);
/*    */           if (adventureMeta == null)
/*    */             return existing; 
/*    */           if (!adventureMeta.isCursed())
/*    */             return existing; 
/*    */           adventureMeta.setCursed(false);
/*    */           uncursedAny.setPlain(true);
/*    */           return existing.withMetadata("Adventure", (Codec)AdventureMetadata.CODEC, adventureMeta);
/*    */         });
/* 26 */     return uncursedAny.get();
/*    */   }
/*    */   
/*    */   public static void deleteAll(Player player) {
/* 30 */     deleteAll((ItemContainer)player.getInventory().getCombinedEverything());
/*    */   }
/*    */   
/*    */   public static void deleteAll(ItemContainer itemContainer) {
/* 34 */     itemContainer.replaceAll((slot, existing) -> {
/*    */           AdventureMetadata adventureMeta = (AdventureMetadata)existing.getFromMetadataOrNull(AdventureMetadata.KEYED_CODEC);
/* 36 */           boolean cursed = (adventureMeta != null && adventureMeta.isCursed());
/*    */           return cursed ? ItemStack.EMPTY : existing;
/*    */         });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\portal\\utils\CursedItems.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */