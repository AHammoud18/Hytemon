/*     */ package com.hypixel.hytale.builtin.crafting.window;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.hypixel.hytale.builtin.crafting.CraftingPlugin;
/*     */ import com.hypixel.hytale.builtin.crafting.component.CraftingManager;
/*     */ import com.hypixel.hytale.builtin.crafting.state.BenchState;
/*     */ import com.hypixel.hytale.builtin.crafting.state.ProcessingBenchState;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.event.EventRegistration;
/*     */ import com.hypixel.hytale.protocol.packets.window.WindowAction;
/*     */ import com.hypixel.hytale.protocol.packets.window.WindowType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.bench.Bench;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.bench.ProcessingBench;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.CraftingRecipe;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.entity.entities.player.windows.ItemContainerWindow;
/*     */ import com.hypixel.hytale.server.core.inventory.Inventory;
/*     */ import com.hypixel.hytale.server.core.inventory.container.CombinedItemContainer;
/*     */ import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ProcessingBenchWindow
/*     */   extends BenchWindow
/*     */   implements ItemContainerWindow
/*     */ {
/*     */   private CombinedItemContainer itemContainer;
/*     */   @Nullable
/*     */   private EventRegistration<?, ?> inventoryRegistration;
/*     */   private float fuelTime;
/*     */   private int maxFuel;
/*     */   private float progress;
/*     */   private boolean active;
/*  46 */   private final Set<Short> processingSlots = new HashSet<>();
/*  47 */   private final Set<Short> processingFuelSlots = new HashSet<>();
/*     */   
/*     */   public ProcessingBenchWindow(ProcessingBenchState benchState) {
/*  50 */     super(WindowType.Processing, (BenchState)benchState);
/*     */     
/*  52 */     ProcessingBench processingBench = (ProcessingBench)this.blockType.getBench();
/*     */     
/*  54 */     CraftingRecipe recipe = benchState.getRecipe();
/*  55 */     float inputProgress = benchState.getInputProgress();
/*  56 */     float progress = (recipe != null && recipe.getTimeSeconds() > 0.0F) ? (inputProgress / recipe.getTimeSeconds()) : 0.0F;
/*     */     
/*  58 */     this.itemContainer = benchState.getItemContainer();
/*  59 */     this.active = benchState.isActive();
/*  60 */     this.progress = progress;
/*     */     
/*  62 */     this.windowData.addProperty("active", Boolean.valueOf(this.active));
/*  63 */     this.windowData.addProperty("progress", Float.valueOf(progress));
/*     */     
/*  65 */     if (processingBench.getFuel() != null && (processingBench.getFuel()).length > 0) {
/*  66 */       JsonArray fuelArray = new JsonArray();
/*  67 */       for (ProcessingBench.ProcessingSlot benchSlot : processingBench.getFuel()) {
/*  68 */         JsonObject fuelObj = new JsonObject();
/*  69 */         fuelObj.addProperty("icon", benchSlot.getIcon());
/*  70 */         fuelObj.addProperty("resourceTypeId", benchSlot.getResourceTypeId());
/*  71 */         fuelArray.add((JsonElement)fuelObj);
/*     */       } 
/*  73 */       this.windowData.add("fuel", (JsonElement)fuelArray);
/*     */     } 
/*     */     
/*  76 */     if (processingBench.getMaxFuel() > 0) this.maxFuel = processingBench.getMaxFuel();
/*     */     
/*  78 */     this.windowData.addProperty("maxFuel", Integer.valueOf(this.maxFuel));
/*  79 */     this.windowData.addProperty("fuelTime", Float.valueOf(this.fuelTime));
/*  80 */     this.windowData.addProperty("progress", Float.valueOf(progress));
/*     */     
/*  82 */     this.windowData.addProperty("processingFuelSlots", Integer.valueOf(0));
/*  83 */     this.windowData.addProperty("processingSlots", Integer.valueOf(0));
/*     */     
/*  85 */     int tierLevel = getBenchTierLevel();
/*  86 */     updateInputSlots(tierLevel);
/*  87 */     updateOutputSlots(tierLevel);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public JsonObject getData() {
/*  92 */     return this.windowData;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public CombinedItemContainer getItemContainer() {
/*  98 */     return this.itemContainer;
/*     */   }
/*     */   
/*     */   public void setActive(boolean active) {
/* 102 */     if (this.active != active) {
/* 103 */       this.active = active;
/* 104 */       this.windowData.addProperty("active", Boolean.valueOf(active));
/* 105 */       invalidate();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setFuelTime(float fuelTime) {
/* 110 */     if (Float.isInfinite(fuelTime)) throw new IllegalArgumentException("Infinite fuelTime"); 
/* 111 */     if (Float.isNaN(fuelTime)) throw new IllegalArgumentException("Nan fuelTime"); 
/* 112 */     if (this.fuelTime != fuelTime) {
/* 113 */       this.fuelTime = fuelTime;
/* 114 */       this.windowData.addProperty("fuelTime", Float.valueOf(fuelTime));
/* 115 */       invalidate();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setMaxFuel(int maxFuel) {
/* 120 */     this.maxFuel = maxFuel;
/* 121 */     this.windowData.addProperty("maxFuel", Integer.valueOf(maxFuel));
/* 122 */     invalidate();
/*     */   }
/*     */   
/*     */   public void setProgress(float progress) {
/* 126 */     if (Float.isInfinite(progress)) throw new IllegalArgumentException("Infinite progress"); 
/* 127 */     if (Float.isNaN(progress)) throw new IllegalArgumentException("Nan fuelTime"); 
/* 128 */     if (this.progress != progress) {
/* 129 */       this.progress = progress;
/* 130 */       this.windowData.addProperty("progress", Float.valueOf(progress));
/* 131 */       invalidate();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setProcessingSlots(Set<Short> slots) {
/* 136 */     if (this.processingSlots.equals(slots)) {
/*     */       return;
/*     */     }
/* 139 */     this.processingSlots.clear();
/* 140 */     this.processingSlots.addAll(slots);
/*     */     
/* 142 */     int bitMask = 0;
/* 143 */     for (Short processingSlot : slots) {
/* 144 */       bitMask |= 1 << processingSlot.intValue();
/*     */     }
/* 146 */     this.windowData.addProperty("processingSlots", Byte.valueOf((byte)bitMask));
/* 147 */     invalidate();
/*     */   }
/*     */   
/*     */   public void setProcessingFuelSlots(Set<Short> slots) {
/* 151 */     if (this.processingFuelSlots.equals(slots)) {
/*     */       return;
/*     */     }
/* 154 */     this.processingFuelSlots.clear();
/* 155 */     this.processingFuelSlots.addAll(slots);
/*     */     
/* 157 */     int bitMask = 0;
/* 158 */     for (Short processingFuelSlots : slots) {
/* 159 */       bitMask |= 1 << processingFuelSlots.intValue();
/*     */     }
/* 161 */     this.windowData.addProperty("processingFuelSlots", Byte.valueOf((byte)bitMask));
/* 162 */     invalidate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleAction(@Nonnull Ref<EntityStore> ref, @Nonnull Store<EntityStore> store, @Nonnull WindowAction action) {
/*     */     // Byte code:
/*     */     //   0: aload_2
/*     */     //   1: invokevirtual getExternalData : ()Ljava/lang/Object;
/*     */     //   4: checkcast com/hypixel/hytale/server/core/universe/world/storage/EntityStore
/*     */     //   7: invokevirtual getWorld : ()Lcom/hypixel/hytale/server/core/universe/world/World;
/*     */     //   10: astore #4
/*     */     //   12: aload #4
/*     */     //   14: aload_0
/*     */     //   15: getfield x : I
/*     */     //   18: aload_0
/*     */     //   19: getfield z : I
/*     */     //   22: invokestatic indexChunkFromBlock : (II)J
/*     */     //   25: invokevirtual getChunk : (J)Lcom/hypixel/hytale/server/core/universe/world/chunk/WorldChunk;
/*     */     //   28: aload_0
/*     */     //   29: getfield x : I
/*     */     //   32: aload_0
/*     */     //   33: getfield y : I
/*     */     //   36: aload_0
/*     */     //   37: getfield z : I
/*     */     //   40: invokevirtual getState : (III)Lcom/hypixel/hytale/server/core/universe/world/meta/BlockState;
/*     */     //   43: astore #5
/*     */     //   45: aload #5
/*     */     //   47: instanceof com/hypixel/hytale/builtin/crafting/state/ProcessingBenchState
/*     */     //   50: ifeq -> 63
/*     */     //   53: aload #5
/*     */     //   55: checkcast com/hypixel/hytale/builtin/crafting/state/ProcessingBenchState
/*     */     //   58: astore #6
/*     */     //   60: goto -> 64
/*     */     //   63: return
/*     */     //   64: aload_3
/*     */     //   65: dup
/*     */     //   66: invokestatic requireNonNull : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   69: pop
/*     */     //   70: astore #7
/*     */     //   72: iconst_0
/*     */     //   73: istore #8
/*     */     //   75: aload #7
/*     */     //   77: iload #8
/*     */     //   79: <illegal opcode> typeSwitch : (Ljava/lang/Object;I)I
/*     */     //   84: lookupswitch default -> 230, 0 -> 112, 1 -> 139
/*     */     //   112: aload #7
/*     */     //   114: checkcast com/hypixel/hytale/protocol/packets/window/SetActiveAction
/*     */     //   117: astore #9
/*     */     //   119: aload #6
/*     */     //   121: aload #9
/*     */     //   123: getfield state : Z
/*     */     //   126: invokevirtual setActive : (Z)Z
/*     */     //   129: ifne -> 230
/*     */     //   132: aload_0
/*     */     //   133: invokevirtual invalidate : ()V
/*     */     //   136: goto -> 230
/*     */     //   139: aload #7
/*     */     //   141: checkcast com/hypixel/hytale/protocol/packets/window/TierUpgradeAction
/*     */     //   144: astore #10
/*     */     //   146: aload_2
/*     */     //   147: aload_1
/*     */     //   148: invokestatic getComponentType : ()Lcom/hypixel/hytale/component/ComponentType;
/*     */     //   151: invokevirtual getComponent : (Lcom/hypixel/hytale/component/Ref;Lcom/hypixel/hytale/component/ComponentType;)Lcom/hypixel/hytale/component/Component;
/*     */     //   154: checkcast com/hypixel/hytale/builtin/crafting/component/CraftingManager
/*     */     //   157: astore #11
/*     */     //   159: aload #11
/*     */     //   161: ifnonnull -> 165
/*     */     //   164: return
/*     */     //   165: aload #11
/*     */     //   167: aload_1
/*     */     //   168: aload_2
/*     */     //   169: aload_0
/*     */     //   170: invokevirtual startTierUpgrade : (Lcom/hypixel/hytale/component/Ref;Lcom/hypixel/hytale/component/ComponentAccessor;Lcom/hypixel/hytale/builtin/crafting/window/BenchWindow;)Z
/*     */     //   173: ifeq -> 227
/*     */     //   176: aload_0
/*     */     //   177: getfield bench : Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/bench/Bench;
/*     */     //   180: invokevirtual getBenchUpgradeSoundEventIndex : ()I
/*     */     //   183: ifeq -> 227
/*     */     //   186: aload_0
/*     */     //   187: getfield bench : Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/bench/Bench;
/*     */     //   190: invokevirtual getBenchUpgradeSoundEventIndex : ()I
/*     */     //   193: getstatic com/hypixel/hytale/protocol/SoundCategory.SFX : Lcom/hypixel/hytale/protocol/SoundCategory;
/*     */     //   196: aload_0
/*     */     //   197: getfield x : I
/*     */     //   200: i2d
/*     */     //   201: ldc2_w 0.5
/*     */     //   204: dadd
/*     */     //   205: aload_0
/*     */     //   206: getfield y : I
/*     */     //   209: i2d
/*     */     //   210: ldc2_w 0.5
/*     */     //   213: dadd
/*     */     //   214: aload_0
/*     */     //   215: getfield z : I
/*     */     //   218: i2d
/*     */     //   219: ldc2_w 0.5
/*     */     //   222: dadd
/*     */     //   223: aload_2
/*     */     //   224: invokestatic playSoundEvent3d : (ILcom/hypixel/hytale/protocol/SoundCategory;DDDLcom/hypixel/hytale/component/ComponentAccessor;)V
/*     */     //   227: goto -> 230
/*     */     //   230: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #167	-> 0
/*     */     //   #168	-> 12
/*     */     //   #169	-> 45
/*     */     //   #171	-> 64
/*     */     //   #172	-> 112
/*     */     //   #173	-> 119
/*     */     //   #176	-> 132
/*     */     //   #179	-> 139
/*     */     //   #182	-> 146
/*     */     //   #183	-> 159
/*     */     //   #184	-> 165
/*     */     //   #185	-> 176
/*     */     //   #186	-> 186
/*     */     //   #189	-> 227
/*     */     //   #193	-> 230
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   60	3	6	benchState	Lcom/hypixel/hytale/builtin/crafting/state/ProcessingBenchState;
/*     */     //   119	20	9	setActiveAction	Lcom/hypixel/hytale/protocol/packets/window/SetActiveAction;
/*     */     //   159	68	11	craftingManager	Lcom/hypixel/hytale/builtin/crafting/component/CraftingManager;
/*     */     //   146	84	10	ignored	Lcom/hypixel/hytale/protocol/packets/window/TierUpgradeAction;
/*     */     //   0	231	0	this	Lcom/hypixel/hytale/builtin/crafting/window/ProcessingBenchWindow;
/*     */     //   0	231	1	ref	Lcom/hypixel/hytale/component/Ref;
/*     */     //   0	231	2	store	Lcom/hypixel/hytale/component/Store;
/*     */     //   0	231	3	action	Lcom/hypixel/hytale/protocol/packets/window/WindowAction;
/*     */     //   12	219	4	world	Lcom/hypixel/hytale/server/core/universe/world/World;
/*     */     //   45	186	5	blockState	Lcom/hypixel/hytale/server/core/universe/world/meta/BlockState;
/*     */     //   64	167	6	benchState	Lcom/hypixel/hytale/builtin/crafting/state/ProcessingBenchState;
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	231	1	ref	Lcom/hypixel/hytale/component/Ref<Lcom/hypixel/hytale/server/core/universe/world/storage/EntityStore;>;
/*     */     //   0	231	2	store	Lcom/hypixel/hytale/component/Store<Lcom/hypixel/hytale/server/core/universe/world/storage/EntityStore;>;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean onOpen0() {
/* 197 */     super.onOpen0();
/*     */     
/* 199 */     PlayerRef playerRef = getPlayerRef();
/* 200 */     Ref<EntityStore> ref = playerRef.getReference();
/* 201 */     Store<EntityStore> store = ref.getStore();
/*     */     
/* 203 */     Player player = (Player)store.getComponent(ref, Player.getComponentType());
/* 204 */     Inventory inventory = player.getInventory();
/*     */     
/* 206 */     this.inventoryRegistration = inventory.getCombinedHotbarFirst().registerChangeEvent(event -> {
/*     */           this.windowData.add("inventoryHints", (JsonElement)generateInventoryHints(this.bench, inventory.getCombinedHotbarFirst()));
/*     */           invalidate();
/*     */         });
/* 210 */     this.windowData.add("inventoryHints", (JsonElement)generateInventoryHints(this.bench, inventory.getCombinedHotbarFirst()));
/* 211 */     return true;
/*     */   }
/*     */   
/*     */   private void updateOutputSlots(int tierLevel) {
/* 215 */     this.windowData.addProperty("outputSlotsCount", Integer.valueOf(((ProcessingBench)this.blockType.getBench()).getOutputSlotsCount(tierLevel)));
/*     */   }
/*     */   
/*     */   private void updateInputSlots(int tierLevel) {
/* 219 */     ProcessingBench.ProcessingSlot[] input = ((ProcessingBench)this.blockType.getBench()).getInput(tierLevel);
/* 220 */     if (input != null && input.length > 0) {
/* 221 */       JsonArray inputArr = new JsonArray();
/*     */       
/* 223 */       for (ProcessingBench.ProcessingSlot benchSlot : input) {
/* 224 */         if (benchSlot != null) {
/* 225 */           JsonObject slotObj = new JsonObject();
/* 226 */           slotObj.addProperty("icon", benchSlot.getIcon());
/* 227 */           inputArr.add((JsonElement)slotObj);
/*     */         } 
/*     */       } 
/* 230 */       this.windowData.add("input", (JsonElement)inputArr);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateBenchTierLevel(int newValue) {
/* 236 */     super.updateBenchTierLevel(newValue);
/* 237 */     updateInputSlots(newValue);
/* 238 */     updateOutputSlots(newValue);
/* 239 */     BenchState benchState = this.benchState; if (benchState instanceof ProcessingBenchState) { ProcessingBenchState processingBenchState = (ProcessingBenchState)benchState;
/* 240 */       this.itemContainer = processingBenchState.getItemContainer(); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public void onClose0() {
/* 246 */     super.onClose0();
/* 247 */     if (this.inventoryRegistration != null) {
/* 248 */       this.inventoryRegistration.unregister();
/* 249 */       this.inventoryRegistration = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private static JsonArray generateInventoryHints(@Nonnull Bench bench, @Nonnull CombinedItemContainer combinedInputItemContainer) {
/* 255 */     return CraftingManager.generateInventoryHints(CraftingPlugin.getBenchRecipes(bench), 0, (ItemContainer)combinedInputItemContainer);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\crafting\window\ProcessingBenchWindow.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */