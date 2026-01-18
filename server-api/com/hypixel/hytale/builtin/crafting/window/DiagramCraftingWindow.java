/*     */ package com.hypixel.hytale.builtin.crafting.window;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.hypixel.hytale.builtin.crafting.CraftingPlugin;
/*     */ import com.hypixel.hytale.builtin.crafting.component.CraftingManager;
/*     */ import com.hypixel.hytale.builtin.crafting.state.BenchState;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.event.EventPriority;
/*     */ import com.hypixel.hytale.event.EventRegistration;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.protocol.packets.window.WindowAction;
/*     */ import com.hypixel.hytale.protocol.packets.window.WindowType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.bench.CraftingBench;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.bench.DiagramCraftingBench;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.CraftingRecipe;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.entity.entities.player.windows.ItemContainerWindow;
/*     */ import com.hypixel.hytale.server.core.inventory.Inventory;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.inventory.container.CombinedItemContainer;
/*     */ import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
/*     */ import com.hypixel.hytale.server.core.inventory.container.SimpleItemContainer;
/*     */ import com.hypixel.hytale.server.core.inventory.container.filter.FilterActionType;
/*     */ import com.hypixel.hytale.server.core.inventory.container.filter.FilterType;
/*     */ import com.hypixel.hytale.server.core.inventory.container.filter.SlotFilter;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DiagramCraftingWindow
/*     */   extends CraftingWindow
/*     */   implements ItemContainerWindow
/*     */ {
/*  54 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */   
/*     */   private String category;
/*     */   
/*     */   private String itemCategory;
/*     */   
/*     */   private CraftingBench.BenchItemCategory benchItemCategory;
/*     */   
/*     */   private SimpleItemContainer inputPrimaryContainer;
/*     */   private SimpleItemContainer inputSecondaryContainer;
/*     */   private CombinedItemContainer combinedInputItemContainer;
/*     */   private SimpleItemContainer outputContainer;
/*     */   private CombinedItemContainer combinedItemContainer;
/*     */   private EventRegistration inventoryRegistration;
/*     */   
/*     */   public DiagramCraftingWindow(@Nonnull ComponentAccessor<EntityStore> store, BenchState benchState) {
/*  70 */     super(WindowType.DiagramCrafting, benchState);
/*  71 */     DiagramCraftingBench bench = (DiagramCraftingBench)this.bench;
/*     */     
/*  73 */     if (bench.getCategories() != null && (bench.getCategories()).length > 0) {
/*  74 */       CraftingBench.BenchCategory benchCategory = bench.getCategories()[0];
/*  75 */       this.category = benchCategory.getId();
/*  76 */       if (benchCategory.getItemCategories() != null && (benchCategory.getItemCategories()).length > 0) {
/*  77 */         this.itemCategory = benchCategory.getItemCategories()[0].getId();
/*     */       }
/*     */     } 
/*     */     
/*  81 */     this.benchItemCategory = getBenchItemCategory(this.category, this.itemCategory);
/*  82 */     if (this.benchItemCategory == null) throw new IllegalArgumentException("Failed to get category!");
/*     */     
/*  84 */     updateInventory(store, this.benchItemCategory);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void finalize() {
/*  89 */     if (this.inventoryRegistration.isRegistered()) throw new IllegalStateException("Failed to unregister inventory event!");
/*     */   
/*     */   }
/*     */   
/*     */   public boolean onOpen0() {
/*  94 */     boolean result = super.onOpen0();
/*     */     
/*  96 */     PlayerRef playerRef = getPlayerRef();
/*  97 */     Ref<EntityStore> ref = playerRef.getReference();
/*  98 */     Store<EntityStore> store = ref.getStore();
/*     */     
/* 100 */     Player player = (Player)store.getComponent(ref, Player.getComponentType());
/* 101 */     Inventory inventory = player.getInventory();
/*     */     
/* 103 */     updateInput((ItemContainer)null);
/* 104 */     this.inventoryRegistration = inventory.getCombinedHotbarFirst().registerChangeEvent(event -> {
/*     */           ObjectArrayList objectArrayList = new ObjectArrayList();
/*     */           
/*     */           this.windowData.add("slots", (JsonElement)generateSlots(inventory.getCombinedHotbarFirst(), (List<CraftingRecipe>)objectArrayList));
/*     */           invalidate();
/*     */         });
/* 110 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onClose0() {
/* 115 */     PlayerRef playerRef = getPlayerRef();
/* 116 */     Ref<EntityStore> ref = playerRef.getReference();
/* 117 */     Store<EntityStore> store = ref.getStore();
/*     */     
/* 119 */     Player player = (Player)store.getComponent(ref, Player.getComponentType());
/*     */     
/* 121 */     List<ItemStack> itemStacks = this.combinedInputItemContainer.dropAllItemStacks();
/* 122 */     SimpleItemContainer.addOrDropItemStacks((ComponentAccessor)store, ref, (ItemContainer)player.getInventory().getCombinedHotbarFirst(), itemStacks);
/*     */     
/* 124 */     CraftingManager craftingManager = (CraftingManager)store.getComponent(ref, CraftingManager.getComponentType());
/* 125 */     craftingManager.cancelAllCrafting(ref, (ComponentAccessor)store);
/*     */     
/* 127 */     this.inventoryRegistration.unregister();
/*     */     
/* 129 */     super.onClose0();
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
/*     */     //   12: aload_0
/*     */     //   13: invokevirtual getPlayerRef : ()Lcom/hypixel/hytale/server/core/universe/PlayerRef;
/*     */     //   16: astore #5
/*     */     //   18: aload_2
/*     */     //   19: aload_1
/*     */     //   20: invokestatic getComponentType : ()Lcom/hypixel/hytale/component/ComponentType;
/*     */     //   23: invokevirtual getComponent : (Lcom/hypixel/hytale/component/Ref;Lcom/hypixel/hytale/component/ComponentType;)Lcom/hypixel/hytale/component/Component;
/*     */     //   26: checkcast com/hypixel/hytale/builtin/crafting/component/CraftingManager
/*     */     //   29: astore #6
/*     */     //   31: aload_3
/*     */     //   32: dup
/*     */     //   33: invokestatic requireNonNull : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   36: pop
/*     */     //   37: astore #7
/*     */     //   39: iconst_0
/*     */     //   40: istore #8
/*     */     //   42: aload #7
/*     */     //   44: iload #8
/*     */     //   46: <illegal opcode> typeSwitch : (Ljava/lang/Object;I)I
/*     */     //   51: tableswitch default -> 396, 0 -> 76, 1 -> 94, 2 -> 173
/*     */     //   76: aload #7
/*     */     //   78: checkcast com/hypixel/hytale/protocol/packets/window/CancelCraftingAction
/*     */     //   81: astore #9
/*     */     //   83: aload #6
/*     */     //   85: aload_1
/*     */     //   86: aload_2
/*     */     //   87: invokevirtual cancelAllCrafting : (Lcom/hypixel/hytale/component/Ref;Lcom/hypixel/hytale/component/ComponentAccessor;)Z
/*     */     //   90: pop
/*     */     //   91: goto -> 396
/*     */     //   94: aload #7
/*     */     //   96: checkcast com/hypixel/hytale/protocol/packets/window/UpdateCategoryAction
/*     */     //   99: astore #10
/*     */     //   101: aload_0
/*     */     //   102: aload #10
/*     */     //   104: getfield category : Ljava/lang/String;
/*     */     //   107: putfield category : Ljava/lang/String;
/*     */     //   110: aload_0
/*     */     //   111: aload #10
/*     */     //   113: getfield itemCategory : Ljava/lang/String;
/*     */     //   116: putfield itemCategory : Ljava/lang/String;
/*     */     //   119: aload_0
/*     */     //   120: aload_0
/*     */     //   121: aload_0
/*     */     //   122: getfield category : Ljava/lang/String;
/*     */     //   125: aload_0
/*     */     //   126: getfield itemCategory : Ljava/lang/String;
/*     */     //   129: invokevirtual getBenchItemCategory : (Ljava/lang/String;Ljava/lang/String;)Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/bench/CraftingBench$BenchItemCategory;
/*     */     //   132: putfield benchItemCategory : Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/bench/CraftingBench$BenchItemCategory;
/*     */     //   135: aload_0
/*     */     //   136: getfield benchItemCategory : Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/bench/CraftingBench$BenchItemCategory;
/*     */     //   139: ifnull -> 154
/*     */     //   142: aload_0
/*     */     //   143: aload_2
/*     */     //   144: aload_0
/*     */     //   145: getfield benchItemCategory : Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/bench/CraftingBench$BenchItemCategory;
/*     */     //   148: invokevirtual updateInventory : (Lcom/hypixel/hytale/component/ComponentAccessor;Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/bench/CraftingBench$BenchItemCategory;)V
/*     */     //   151: goto -> 396
/*     */     //   154: aload_0
/*     */     //   155: invokevirtual getPlayerRef : ()Lcom/hypixel/hytale/server/core/universe/PlayerRef;
/*     */     //   158: ldc 'server.ui.diagramcraftingwindow.invalidCategory'
/*     */     //   160: invokestatic translation : (Ljava/lang/String;)Lcom/hypixel/hytale/server/core/Message;
/*     */     //   163: invokevirtual sendMessage : (Lcom/hypixel/hytale/server/core/Message;)V
/*     */     //   166: aload_0
/*     */     //   167: invokevirtual close : ()V
/*     */     //   170: goto -> 396
/*     */     //   173: aload #7
/*     */     //   175: checkcast com/hypixel/hytale/protocol/packets/window/CraftItemAction
/*     */     //   178: astore #11
/*     */     //   180: aload_0
/*     */     //   181: getfield outputContainer : Lcom/hypixel/hytale/server/core/inventory/container/SimpleItemContainer;
/*     */     //   184: iconst_0
/*     */     //   185: invokevirtual getItemStack : (S)Lcom/hypixel/hytale/server/core/inventory/ItemStack;
/*     */     //   188: astore #12
/*     */     //   190: aload #12
/*     */     //   192: ifnull -> 203
/*     */     //   195: aload #12
/*     */     //   197: invokevirtual isEmpty : ()Z
/*     */     //   200: ifeq -> 214
/*     */     //   203: aload #5
/*     */     //   205: ldc 'server.ui.diagramcraftingwindow.noOutputItem'
/*     */     //   207: invokestatic translation : (Ljava/lang/String;)Lcom/hypixel/hytale/server/core/Message;
/*     */     //   210: invokevirtual sendMessage : (Lcom/hypixel/hytale/server/core/Message;)V
/*     */     //   213: return
/*     */     //   214: new it/unimi/dsi/fastutil/objects/ObjectArrayList
/*     */     //   217: dup
/*     */     //   218: invokespecial <init> : ()V
/*     */     //   221: astore #13
/*     */     //   223: aload_0
/*     */     //   224: aload #13
/*     */     //   226: invokevirtual collectRecipes : (Ljava/util/List;)Z
/*     */     //   229: istore #14
/*     */     //   231: aload #13
/*     */     //   233: invokeinterface size : ()I
/*     */     //   238: iconst_1
/*     */     //   239: if_icmpne -> 247
/*     */     //   242: iload #14
/*     */     //   244: ifne -> 258
/*     */     //   247: aload #5
/*     */     //   249: ldc 'server.ui.diagramcraftingwindow.failedVerifyRecipy'
/*     */     //   251: invokestatic translation : (Ljava/lang/String;)Lcom/hypixel/hytale/server/core/Message;
/*     */     //   254: invokevirtual sendMessage : (Lcom/hypixel/hytale/server/core/Message;)V
/*     */     //   257: return
/*     */     //   258: aload #13
/*     */     //   260: invokeinterface getFirst : ()Ljava/lang/Object;
/*     */     //   265: checkcast com/hypixel/hytale/server/core/asset/type/item/config/CraftingRecipe
/*     */     //   268: astore #15
/*     */     //   270: aload #6
/*     */     //   272: aload_1
/*     */     //   273: aload_2
/*     */     //   274: aload_0
/*     */     //   275: iconst_0
/*     */     //   276: aload #15
/*     */     //   278: iconst_1
/*     */     //   279: aload_0
/*     */     //   280: getfield combinedInputItemContainer : Lcom/hypixel/hytale/server/core/inventory/container/CombinedItemContainer;
/*     */     //   283: getstatic com/hypixel/hytale/builtin/crafting/component/CraftingManager$InputRemovalType.ORDERED : Lcom/hypixel/hytale/builtin/crafting/component/CraftingManager$InputRemovalType;
/*     */     //   286: invokevirtual queueCraft : (Lcom/hypixel/hytale/component/Ref;Lcom/hypixel/hytale/component/ComponentAccessor;Lcom/hypixel/hytale/builtin/crafting/window/CraftingWindow;ILcom/hypixel/hytale/server/core/asset/type/item/config/CraftingRecipe;ILcom/hypixel/hytale/server/core/inventory/container/ItemContainer;Lcom/hypixel/hytale/builtin/crafting/component/CraftingManager$InputRemovalType;)Z
/*     */     //   289: pop
/*     */     //   290: aload #15
/*     */     //   292: invokevirtual getTimeSeconds : ()F
/*     */     //   295: fconst_0
/*     */     //   296: fcmpl
/*     */     //   297: ifle -> 306
/*     */     //   300: ldc_w 'CraftCompleted'
/*     */     //   303: goto -> 309
/*     */     //   306: ldc_w 'CraftCompletedInstant'
/*     */     //   309: astore #16
/*     */     //   311: aload_0
/*     */     //   312: aload #16
/*     */     //   314: aload #4
/*     */     //   316: bipush #70
/*     */     //   318: invokevirtual setBlockInteractionState : (Ljava/lang/String;Lcom/hypixel/hytale/server/core/universe/world/World;I)V
/*     */     //   321: aload_0
/*     */     //   322: getfield bench : Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/bench/Bench;
/*     */     //   325: invokevirtual getCompletedSoundEventIndex : ()I
/*     */     //   328: ifeq -> 372
/*     */     //   331: aload_0
/*     */     //   332: getfield bench : Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/bench/Bench;
/*     */     //   335: invokevirtual getCompletedSoundEventIndex : ()I
/*     */     //   338: getstatic com/hypixel/hytale/protocol/SoundCategory.SFX : Lcom/hypixel/hytale/protocol/SoundCategory;
/*     */     //   341: aload_0
/*     */     //   342: getfield x : I
/*     */     //   345: i2d
/*     */     //   346: ldc2_w 0.5
/*     */     //   349: dadd
/*     */     //   350: aload_0
/*     */     //   351: getfield y : I
/*     */     //   354: i2d
/*     */     //   355: ldc2_w 0.5
/*     */     //   358: dadd
/*     */     //   359: aload_0
/*     */     //   360: getfield z : I
/*     */     //   363: i2d
/*     */     //   364: ldc2_w 0.5
/*     */     //   367: dadd
/*     */     //   368: aload_2
/*     */     //   369: invokestatic playSoundEvent3d : (ILcom/hypixel/hytale/protocol/SoundCategory;DDDLcom/hypixel/hytale/component/ComponentAccessor;)V
/*     */     //   372: aload_1
/*     */     //   373: aload #15
/*     */     //   375: invokevirtual getId : ()Ljava/lang/String;
/*     */     //   378: aload_2
/*     */     //   379: invokestatic learnRecipe : (Lcom/hypixel/hytale/component/Ref;Ljava/lang/String;Lcom/hypixel/hytale/component/ComponentAccessor;)Z
/*     */     //   382: ifeq -> 393
/*     */     //   385: aload_0
/*     */     //   386: aload_0
/*     */     //   387: getfield outputContainer : Lcom/hypixel/hytale/server/core/inventory/container/SimpleItemContainer;
/*     */     //   390: invokevirtual updateInput : (Lcom/hypixel/hytale/server/core/inventory/container/ItemContainer;)V
/*     */     //   393: goto -> 396
/*     */     //   396: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #134	-> 0
/*     */     //   #135	-> 12
/*     */     //   #136	-> 18
/*     */     //   #138	-> 31
/*     */     //   #139	-> 76
/*     */     //   #140	-> 94
/*     */     //   #141	-> 101
/*     */     //   #142	-> 110
/*     */     //   #143	-> 119
/*     */     //   #144	-> 135
/*     */     //   #145	-> 142
/*     */     //   #147	-> 154
/*     */     //   #148	-> 166
/*     */     //   #150	-> 170
/*     */     //   #151	-> 173
/*     */     //   #152	-> 180
/*     */     //   #153	-> 190
/*     */     //   #154	-> 203
/*     */     //   #155	-> 213
/*     */     //   #158	-> 214
/*     */     //   #159	-> 223
/*     */     //   #161	-> 231
/*     */     //   #162	-> 247
/*     */     //   #163	-> 257
/*     */     //   #166	-> 258
/*     */     //   #167	-> 270
/*     */     //   #169	-> 290
/*     */     //   #170	-> 311
/*     */     //   #172	-> 321
/*     */     //   #173	-> 331
/*     */     //   #177	-> 372
/*     */     //   #178	-> 385
/*     */     //   #180	-> 393
/*     */     //   #184	-> 396
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   83	11	9	ignored	Lcom/hypixel/hytale/protocol/packets/window/CancelCraftingAction;
/*     */     //   101	72	10	updateAction	Lcom/hypixel/hytale/protocol/packets/window/UpdateCategoryAction;
/*     */     //   190	203	12	itemStack	Lcom/hypixel/hytale/server/core/inventory/ItemStack;
/*     */     //   223	170	13	recipes	Lit/unimi/dsi/fastutil/objects/ObjectList;
/*     */     //   231	162	14	allSlotsFull	Z
/*     */     //   270	123	15	recipe	Lcom/hypixel/hytale/server/core/asset/type/item/config/CraftingRecipe;
/*     */     //   311	82	16	completedState	Ljava/lang/String;
/*     */     //   180	216	11	ignored	Lcom/hypixel/hytale/protocol/packets/window/CraftItemAction;
/*     */     //   0	397	0	this	Lcom/hypixel/hytale/builtin/crafting/window/DiagramCraftingWindow;
/*     */     //   0	397	1	ref	Lcom/hypixel/hytale/component/Ref;
/*     */     //   0	397	2	store	Lcom/hypixel/hytale/component/Store;
/*     */     //   0	397	3	action	Lcom/hypixel/hytale/protocol/packets/window/WindowAction;
/*     */     //   12	385	4	world	Lcom/hypixel/hytale/server/core/universe/world/World;
/*     */     //   18	379	5	playerRef	Lcom/hypixel/hytale/server/core/universe/PlayerRef;
/*     */     //   31	366	6	craftingManager	Lcom/hypixel/hytale/builtin/crafting/component/CraftingManager;
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   223	170	13	recipes	Lit/unimi/dsi/fastutil/objects/ObjectList<Lcom/hypixel/hytale/server/core/asset/type/item/config/CraftingRecipe;>;
/*     */     //   0	397	1	ref	Lcom/hypixel/hytale/component/Ref<Lcom/hypixel/hytale/server/core/universe/world/storage/EntityStore;>;
/*     */     //   0	397	2	store	Lcom/hypixel/hytale/component/Store<Lcom/hypixel/hytale/server/core/universe/world/storage/EntityStore;>;
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
/*     */   @Nonnull
/*     */   public ItemContainer getItemContainer() {
/* 189 */     return (ItemContainer)this.combinedItemContainer;
/*     */   }
/*     */   
/*     */   private CraftingBench.BenchItemCategory getBenchItemCategory(@Nullable String category, @Nullable String itemCategory) {
/* 193 */     if (category == null || itemCategory == null) return null; 
/* 194 */     DiagramCraftingBench craftingBench = (DiagramCraftingBench)this.bench;
/* 195 */     for (CraftingBench.BenchCategory benchCategory : craftingBench.getCategories()) {
/* 196 */       if (category.equals(benchCategory.getId())) {
/* 197 */         for (CraftingBench.BenchItemCategory benchItemCategory : benchCategory.getItemCategories()) {
/* 198 */           if (itemCategory.equals(benchItemCategory.getId())) {
/* 199 */             return benchItemCategory;
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/* 204 */     return null;
/*     */   }
/*     */   
/*     */   private void updateInventory(@Nonnull ComponentAccessor<EntityStore> store, @Nonnull CraftingBench.BenchItemCategory benchItemCategory) {
/* 208 */     if (this.combinedInputItemContainer != null) {
/* 209 */       PlayerRef playerRef = getPlayerRef();
/* 210 */       Ref<EntityStore> ref = playerRef.getReference();
/* 211 */       Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 212 */       List<ItemStack> itemStacks = this.combinedInputItemContainer.dropAllItemStacks();
/* 213 */       SimpleItemContainer.addOrDropItemStacks(store, ref, (ItemContainer)playerComponent.getInventory().getCombinedHotbarFirst(), itemStacks);
/*     */     } 
/*     */     
/* 216 */     this.inputPrimaryContainer = new SimpleItemContainer((short)1);
/* 217 */     this
/*     */       
/* 219 */       .inputSecondaryContainer = new SimpleItemContainer((short)(benchItemCategory.getSlots() + (benchItemCategory.isSpecialSlot() ? 1 : 0)));
/*     */     
/* 221 */     this.inputSecondaryContainer.setGlobalFilter(FilterType.ALLOW_OUTPUT_ONLY);
/* 222 */     this.combinedInputItemContainer = new CombinedItemContainer(new ItemContainer[] { (ItemContainer)this.inputPrimaryContainer, (ItemContainer)this.inputSecondaryContainer });
/* 223 */     this.combinedInputItemContainer.registerChangeEvent(EventPriority.LAST, this::updateInput);
/*     */     
/* 225 */     this.outputContainer = new SimpleItemContainer((short)1);
/* 226 */     this.outputContainer.setGlobalFilter(FilterType.DENY_ALL);
/*     */     
/* 228 */     this.combinedItemContainer = new CombinedItemContainer(new ItemContainer[] { (ItemContainer)this.combinedInputItemContainer, (ItemContainer)this.outputContainer });
/*     */   }
/*     */   
/*     */   private void updateInput(@Nonnull ItemContainer.ItemContainerChangeEvent event) {
/* 232 */     updateInput(event.container());
/*     */   }
/*     */   
/*     */   private void updateInput(@Nullable ItemContainer container) {
/* 236 */     PlayerRef playerRef = getPlayerRef();
/* 237 */     Ref<EntityStore> ref = playerRef.getReference();
/* 238 */     Store<EntityStore> store = ref.getStore();
/*     */     
/* 240 */     Player player = (Player)store.getComponent(ref, Player.getComponentType());
/*     */     
/* 242 */     ItemStack primaryItemStack = this.inputPrimaryContainer.getItemStack((short)0);
/* 243 */     CombinedItemContainer combinedStorage = player.getInventory().getCombinedHotbarFirst();
/* 244 */     if (primaryItemStack != null && !primaryItemStack.isEmpty()) {
/*     */       
/* 246 */       this.inputSecondaryContainer.setGlobalFilter(FilterType.ALLOW_ALL);
/*     */ 
/*     */       
/* 249 */       boolean needsDropSlot = true; short i;
/* 250 */       for (i = 0; i < this.inputSecondaryContainer.getCapacity(); i = (short)(i + 1)) {
/* 251 */         ItemStack itemStack = this.inputSecondaryContainer.getItemStack(i);
/* 252 */         if (itemStack != null && !itemStack.isEmpty()) {
/* 253 */           this.inputSecondaryContainer.setSlotFilter(FilterActionType.ADD, i, null);
/* 254 */         } else if (needsDropSlot) {
/* 255 */           this.inputSecondaryContainer.setSlotFilter(FilterActionType.ADD, i, null);
/* 256 */           needsDropSlot = false;
/*     */         } else {
/* 258 */           this.inputSecondaryContainer.setSlotFilter(FilterActionType.ADD, i, SlotFilter.DENY);
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       
/* 263 */       this.inputSecondaryContainer.setGlobalFilter(FilterType.ALLOW_OUTPUT_ONLY);
/*     */ 
/*     */       
/* 266 */       if (container != this.inputSecondaryContainer && !this.inputSecondaryContainer.isEmpty()) {
/*     */         
/* 268 */         List<ItemStack> itemStacks = this.inputSecondaryContainer.dropAllItemStacks();
/* 269 */         SimpleItemContainer.addOrDropItemStacks((ComponentAccessor)store, ref, (ItemContainer)combinedStorage, itemStacks);
/*     */       } 
/*     */     } 
/*     */     
/* 273 */     ObjectArrayList<CraftingRecipe> objectArrayList = new ObjectArrayList();
/* 274 */     boolean allSlotsFull = collectRecipes((List<CraftingRecipe>)objectArrayList);
/*     */     
/* 276 */     this.windowData.add("slots", (JsonElement)generateSlots(combinedStorage, (List<CraftingRecipe>)objectArrayList));
/*     */     
/* 278 */     if (objectArrayList.size() == 1 && allSlotsFull) {
/* 279 */       CraftingRecipe recipe = objectArrayList.getFirst();
/* 280 */       ItemStack output = CraftingManager.getOutputItemStacks(recipe).getFirst();
/*     */       
/* 282 */       if (player.getPlayerConfigData().getKnownRecipes().contains(recipe.getId())) {
/* 283 */         this.outputContainer.setItemStackForSlot((short)0, output);
/*     */       } else {
/*     */         
/* 286 */         this.outputContainer.setItemStackForSlot((short)0, new ItemStack("Unknown", 1));
/*     */       } 
/*     */     } else {
/* 289 */       if (!objectArrayList.isEmpty() && allSlotsFull) {
/* 290 */         LOGGER.at(Level.WARNING).log("Multiple recipes defined for the same materials! %s", objectArrayList);
/*     */       }
/* 292 */       this.outputContainer.setItemStackForSlot((short)0, ItemStack.EMPTY);
/*     */     } 
/*     */ 
/*     */     
/* 296 */     invalidate();
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean collectRecipes(@Nonnull List<CraftingRecipe> recipes) {
/* 301 */     ItemStack primaryItemStack = this.inputPrimaryContainer.getItemStack((short)0);
/* 302 */     if (primaryItemStack == null || primaryItemStack.isEmpty()) return false;
/*     */     
/* 304 */     PlayerRef playerRef = getPlayerRef();
/* 305 */     Ref<EntityStore> ref = playerRef.getReference();
/* 306 */     Store<EntityStore> store = ref.getStore();
/* 307 */     Player player = (Player)store.getComponent(ref, Player.getComponentType());
/*     */     
/* 309 */     Set<String> knownRecipes = player.getPlayerConfigData().getKnownRecipes();
/*     */     
/* 311 */     short inputCapacity = this.combinedInputItemContainer.getCapacity();
/* 312 */     boolean allSlotsFull = true;
/*     */ 
/*     */ 
/*     */     
/* 316 */     label34: for (CraftingRecipe recipe : getBenchRecipes()) {
/*     */       
/* 318 */       if ((recipe.getInput()).length != inputCapacity && (!this.benchItemCategory.isSpecialSlot() || (recipe.getInput()).length != inputCapacity - 1)) {
/* 319 */         LOGGER.at(Level.WARNING).log("Recipe for %s has different input length than the diagram! %s - %s, %s, %s", recipe.getId(), recipe, this.bench, this.category, this.itemCategory);
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 324 */       if (recipe.isKnowledgeRequired() && !knownRecipes.contains(recipe.getId())) {
/*     */         continue;
/*     */       }
/*     */       short i;
/* 328 */       for (i = 0; i < inputCapacity; i = (short)(i + 1)) {
/* 329 */         ItemStack itemStack = this.combinedInputItemContainer.getItemStack(i);
/* 330 */         if (itemStack == null || itemStack.isEmpty()) {
/* 331 */           if (!this.benchItemCategory.isSpecialSlot() && i == inputCapacity - 1) {
/* 332 */             allSlotsFull = false;
/*     */ 
/*     */           
/*     */           }
/*     */         
/*     */         }
/* 338 */         else if (!CraftingManager.matches(recipe.getInput()[i], itemStack)) {
/*     */           continue label34;
/*     */         } 
/*     */       } 
/*     */       
/* 343 */       recipes.add(recipe);
/*     */     } 
/*     */     
/* 346 */     return allSlotsFull;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private JsonArray generateSlots(@Nonnull CombinedItemContainer combinedStorage, @Nonnull List<CraftingRecipe> recipes) {
/* 351 */     JsonArray slots = new JsonArray();
/* 352 */     if (recipes.isEmpty()) {
/* 353 */       List<CraftingRecipe> benchRecipes = getBenchRecipes();
/*     */       
/* 355 */       JsonObject slot = new JsonObject();
/* 356 */       slot.add("inventoryHints", (JsonElement)CraftingManager.generateInventoryHints(benchRecipes, 0, (ItemContainer)combinedStorage));
/* 357 */       slots.add((JsonElement)slot);
/*     */     } else {
/* 359 */       short i; for (i = 0; i < this.combinedInputItemContainer.getCapacity(); i = (short)(i + 1)) {
/* 360 */         JsonObject slot = new JsonObject();
/*     */         
/* 362 */         ItemStack itemStack = this.combinedInputItemContainer.getItemStack(i);
/*     */         
/* 364 */         if (itemStack == null || itemStack.isEmpty()) {
/* 365 */           slot.add("inventoryHints", (JsonElement)CraftingManager.generateInventoryHints(recipes, i, (ItemContainer)combinedStorage));
/*     */         }
/*     */         
/* 368 */         int requiredAmount = -1;
/* 369 */         if (recipes.size() == 1) {
/* 370 */           CraftingRecipe recipe = recipes.getFirst();
/* 371 */           if (i < (recipe.getInput()).length) {
/* 372 */             requiredAmount = recipe.getInput()[i].getQuantity();
/*     */           }
/*     */         } 
/* 375 */         slot.addProperty("requiredAmount", Integer.valueOf(requiredAmount));
/*     */         
/* 377 */         slots.add((JsonElement)slot);
/*     */       } 
/*     */     } 
/* 380 */     return slots;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public List<CraftingRecipe> getBenchRecipes() {
/* 385 */     return CraftingPlugin.getBenchRecipes(this.bench.getType(), this.bench.getId(), this.category + "." + this.category);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\crafting\window\DiagramCraftingWindow.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */