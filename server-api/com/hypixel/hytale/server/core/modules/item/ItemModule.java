/*     */ package com.hypixel.hytale.server.core.modules.item;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.lookup.Priority;
/*     */ import com.hypixel.hytale.common.plugin.PluginManifest;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.ItemCategory;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.ItemDrop;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.ItemDropList;
/*     */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.inventory.container.EmptyItemContainer;
/*     */ import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
/*     */ import com.hypixel.hytale.server.core.inventory.container.SimpleItemContainer;
/*     */ import com.hypixel.hytale.server.core.modules.item.commands.SpawnItemCommand;
/*     */ import com.hypixel.hytale.server.core.plugin.JavaPlugin;
/*     */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.ThreadLocalRandom;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class ItemModule extends JavaPlugin {
/*  26 */   public static final PluginManifest MANIFEST = PluginManifest.corePlugin(ItemModule.class)
/*  27 */     .build();
/*     */   
/*     */   private static ItemModule instance;
/*     */   
/*     */   public static ItemModule get() {
/*  32 */     return instance;
/*     */   }
/*     */   
/*     */   public ItemModule(@Nonnull JavaPluginInit init) {
/*  36 */     super(init);
/*  37 */     instance = this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setup() {
/*  42 */     getCommandRegistry().registerCommand((AbstractCommand)new SpawnItemCommand());
/*     */     
/*  44 */     ItemContainer.CODEC.register(Priority.DEFAULT, "Simple", SimpleItemContainer.class, (Codec)SimpleItemContainer.CODEC);
/*  45 */     ItemContainer.CODEC.register("Empty", EmptyItemContainer.class, (Codec)EmptyItemContainer.CODEC);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public List<String> getFlatItemCategoryList() {
/*  50 */     ObjectArrayList objectArrayList = new ObjectArrayList();
/*     */     
/*  52 */     ItemCategory[] itemCategories = (ItemCategory[])ItemCategory.getAssetMap().getAssetMap().values().toArray(x$0 -> new ItemCategory[x$0]);
/*     */     
/*  54 */     for (ItemCategory category : itemCategories) {
/*  55 */       ItemCategory[] children = category.getChildren();
/*  56 */       if (children != null) {
/*  57 */         flattenCategories(category.getId() + ".", children, (List<String>)objectArrayList);
/*     */       }
/*     */     } 
/*  60 */     return (List<String>)objectArrayList;
/*     */   }
/*     */   
/*     */   private void flattenCategories(String parent, @Nonnull ItemCategory[] itemCategories, @Nonnull List<String> categoryIds) {
/*  64 */     for (ItemCategory category : itemCategories) {
/*  65 */       String id = parent + parent;
/*  66 */       categoryIds.add(id);
/*     */       
/*  68 */       ItemCategory[] children = category.getChildren();
/*  69 */       if (children != null)
/*  70 */         flattenCategories(id + ".", children, categoryIds); 
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public List<ItemStack> getRandomItemDrops(@Nullable String dropListId) {
/*  76 */     if (isDisabled()) return Collections.emptyList(); 
/*  77 */     if (dropListId == null) return Collections.emptyList();
/*     */     
/*  79 */     ItemDropList itemDropList = (ItemDropList)ItemDropList.getAssetMap().getAsset(dropListId);
/*  80 */     if (itemDropList == null || itemDropList.getContainer() == null) return Collections.emptyList();
/*     */     
/*  82 */     ObjectArrayList<ItemStack> objectArrayList = new ObjectArrayList();
/*  83 */     ThreadLocalRandom random = ThreadLocalRandom.current();
/*     */     
/*  85 */     ObjectArrayList objectArrayList1 = new ObjectArrayList();
/*  86 */     Objects.requireNonNull(random); itemDropList.getContainer().populateDrops((List)objectArrayList1, random::nextDouble, dropListId);
/*     */     
/*  88 */     for (ItemDrop drop : objectArrayList1) {
/*  89 */       if (drop == null || drop.getItemId() == null) {
/*  90 */         ((HytaleLogger.Api)getLogger().atWarning()).log("ItemModule::getRandomItemDrops - Tried to create ItemDrop for non-existent item in drop list id '%s'", dropListId);
/*     */         
/*     */         continue;
/*     */       } 
/*  94 */       int amount = drop.getRandomQuantity(random);
/*  95 */       if (amount > 0) {
/*  96 */         objectArrayList.add(new ItemStack(drop.getItemId(), amount, drop.getMetadata()));
/*     */       }
/*     */     } 
/*     */     
/* 100 */     return (List<ItemStack>)objectArrayList;
/*     */   }
/*     */   
/*     */   public static boolean exists(String key) {
/* 104 */     if ("Empty".equals(key)) return true; 
/* 105 */     if ("Unknown".equals(key)) return true; 
/* 106 */     return (Item.getAssetMap().getAsset(key) != null);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\item\ItemModule.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */