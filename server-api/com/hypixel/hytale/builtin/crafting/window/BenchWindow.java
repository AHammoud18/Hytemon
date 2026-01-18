/*     */ package com.hypixel.hytale.builtin.crafting.window;
/*     */ 
/*     */ import com.google.gson.JsonObject;
/*     */ import com.hypixel.hytale.builtin.adventure.memories.MemoriesPlugin;
/*     */ import com.hypixel.hytale.builtin.crafting.component.CraftingManager;
/*     */ import com.hypixel.hytale.builtin.crafting.state.BenchState;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.protocol.SoundCategory;
/*     */ import com.hypixel.hytale.protocol.packets.window.WindowType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.bench.Bench;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*     */ import com.hypixel.hytale.server.core.entity.entities.player.windows.BlockWindow;
/*     */ import com.hypixel.hytale.server.core.entity.entities.player.windows.MaterialContainerWindow;
/*     */ import com.hypixel.hytale.server.core.entity.entities.player.windows.MaterialExtraResourcesSection;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.SoundUtil;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public abstract class BenchWindow
/*     */   extends BlockWindow
/*     */   implements MaterialContainerWindow
/*     */ {
/*     */   private static final float CRAFTING_UPDATE_MIN_PERCENT = 0.05F;
/*     */   private static final long CRAFTING_UPDATE_INTERVAL_MS = 500L;
/*     */   protected static final String BENCH_UPGRADING = "BenchUpgrading";
/*  30 */   protected final JsonObject windowData = new JsonObject(); private float lastUpdatePercent; private long lastUpdateTimeMs; protected final Bench bench; protected final BenchState benchState; @Nonnull
/*  31 */   private MaterialExtraResourcesSection extraResourcesSection = new MaterialExtraResourcesSection();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BenchWindow(@Nonnull WindowType windowType, @Nonnull BenchState benchState) {
/*  41 */     super(windowType, benchState.getBlockX(), benchState.getBlockY(), benchState.getBlockZ(), benchState.getRotationIndex(), benchState.getBlockType());
/*     */     
/*  43 */     this.bench = this.blockType.getBench();
/*  44 */     this.benchState = benchState;
/*  45 */     Item item = this.blockType.getItem();
/*  46 */     this.windowData.addProperty("type", Integer.valueOf(this.bench.getType().ordinal()));
/*  47 */     this.windowData.addProperty("id", this.bench.getId());
/*  48 */     this.windowData.addProperty("name", item.getTranslationKey());
/*  49 */     this.windowData.addProperty("blockItemId", item.getId());
/*     */     
/*  51 */     this.windowData.addProperty("tierLevel", Integer.valueOf(getBenchTierLevel()));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public JsonObject getData() {
/*  57 */     return this.windowData;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean onOpen0() {
/*  62 */     PlayerRef playerRef = getPlayerRef();
/*  63 */     Ref<EntityStore> ref = playerRef.getReference();
/*  64 */     Store<EntityStore> store = ref.getStore();
/*     */     
/*  66 */     CraftingManager craftingManager = (CraftingManager)store.getComponent(ref, CraftingManager.getComponentType());
/*  67 */     craftingManager.setBench(this.x, this.y, this.z, this.blockType);
/*     */     
/*  69 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*  70 */     this.windowData.addProperty("worldMemoriesLevel", Integer.valueOf(MemoriesPlugin.get().getMemoriesLevel(world.getGameplayConfig())));
/*     */     
/*  72 */     return true;
/*     */   }
/*     */   
/*     */   protected int getBenchTierLevel() {
/*  76 */     return (this.benchState != null) ? this.benchState.getTierLevel() : 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onClose0() {
/*  81 */     PlayerRef playerRef = getPlayerRef();
/*  82 */     Ref<EntityStore> ref = playerRef.getReference();
/*  83 */     Store<EntityStore> store = ref.getStore();
/*     */     
/*  85 */     CraftingManager craftingManagerComponent = (CraftingManager)store.getComponent(ref, CraftingManager.getComponentType());
/*  86 */     assert craftingManagerComponent != null;
/*     */     
/*  88 */     if (craftingManagerComponent.clearBench(ref, store) && this.bench.getFailedSoundEventIndex() != 0) {
/*  89 */       SoundUtil.playSoundEvent2d(ref, this.bench.getFailedSoundEventIndex(), SoundCategory.UI, (ComponentAccessor)store);
/*     */     }
/*     */   }
/*     */   
/*     */   public void updateCraftingJob(float percent) {
/*  94 */     this.windowData.addProperty("progress", Float.valueOf(percent));
/*  95 */     checkProgressInvalidate(percent);
/*     */   }
/*     */   
/*     */   public void updateBenchUpgradeJob(float percent) {
/*  99 */     this.windowData.addProperty("tierUpgradeProgress", Float.valueOf(percent));
/* 100 */     checkProgressInvalidate(percent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkProgressInvalidate(float percent) {
/* 111 */     if (this.lastUpdatePercent != percent) {
/* 112 */       long time = System.currentTimeMillis();
/* 113 */       if (percent >= 1.0F || percent < this.lastUpdatePercent || percent - this.lastUpdatePercent > 0.05F || time - this.lastUpdateTimeMs > 500L || this.lastUpdateTimeMs == 0L) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 118 */         this.lastUpdatePercent = percent;
/* 119 */         this.lastUpdateTimeMs = time;
/* 120 */         invalidate();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void updateBenchTierLevel(int newValue) {
/* 126 */     this.windowData.addProperty("tierLevel", Integer.valueOf(newValue));
/* 127 */     updateBenchUpgradeJob(0.0F);
/* 128 */     setNeedRebuild();
/* 129 */     invalidate();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public MaterialExtraResourcesSection getExtraResourcesSection() {
/* 135 */     if (!this.extraResourcesSection.isValid()) {
/* 136 */       CraftingManager.feedExtraResourcesSection(this.benchState, this.extraResourcesSection);
/*     */     }
/* 138 */     return this.extraResourcesSection;
/*     */   }
/*     */ 
/*     */   
/*     */   public void invalidateExtraResources() {
/* 143 */     this.extraResourcesSection.setValid(false);
/* 144 */     invalidate();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isValid() {
/* 149 */     return this.extraResourcesSection.isValid();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\crafting\window\BenchWindow.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */