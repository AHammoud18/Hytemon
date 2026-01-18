/*     */ package com.hypixel.hytale.builtin.crafting.window;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.hypixel.hytale.builtin.crafting.CraftingPlugin;
/*     */ import com.hypixel.hytale.builtin.crafting.component.CraftingManager;
/*     */ import com.hypixel.hytale.builtin.crafting.state.BenchState;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.event.EventRegistration;
/*     */ import com.hypixel.hytale.protocol.BenchRequirement;
/*     */ import com.hypixel.hytale.protocol.ItemSoundEvent;
/*     */ import com.hypixel.hytale.protocol.SoundCategory;
/*     */ import com.hypixel.hytale.protocol.packets.window.WindowAction;
/*     */ import com.hypixel.hytale.protocol.packets.window.WindowType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.bench.StructuralCraftingBench;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.BlockGroup;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.CraftingRecipe;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*     */ import com.hypixel.hytale.server.core.asset.type.itemsound.config.ItemSoundSet;
/*     */ import com.hypixel.hytale.server.core.asset.type.soundevent.config.SoundEvent;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.entity.entities.player.windows.ItemContainerWindow;
/*     */ import com.hypixel.hytale.server.core.inventory.Inventory;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.inventory.MaterialQuantity;
/*     */ import com.hypixel.hytale.server.core.inventory.container.CombinedItemContainer;
/*     */ import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
/*     */ import com.hypixel.hytale.server.core.inventory.container.SimpleItemContainer;
/*     */ import com.hypixel.hytale.server.core.inventory.container.filter.FilterActionType;
/*     */ import com.hypixel.hytale.server.core.inventory.container.filter.FilterType;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.SoundUtil;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectList;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import org.checkerframework.checker.nullness.compatqual.NullableDecl;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StructuralCraftingWindow
/*     */   extends CraftingWindow
/*     */   implements ItemContainerWindow
/*     */ {
/*     */   private static final int MAX_OPTIONS = 64;
/*     */   private final SimpleItemContainer inputContainer;
/*     */   private final SimpleItemContainer optionsContainer;
/*     */   private final CombinedItemContainer combinedItemContainer;
/*  55 */   private final Int2ObjectMap<String> optionSlotToRecipeMap = (Int2ObjectMap<String>)new Int2ObjectOpenHashMap();
/*     */   
/*     */   private int selectedSlot;
/*     */   
/*     */   @Nullable
/*     */   private EventRegistration inventoryRegistration;
/*     */   
/*     */   public StructuralCraftingWindow(BenchState benchState) {
/*  63 */     super(WindowType.StructuralCrafting, benchState);
/*     */     
/*  65 */     this.inputContainer = new SimpleItemContainer((short)1);
/*  66 */     this.inputContainer.registerChangeEvent(e -> updateRecipes());
/*  67 */     this.inputContainer.setSlotFilter(FilterActionType.ADD, (short)0, this::isValidInput);
/*     */     
/*  69 */     this.optionsContainer = new SimpleItemContainer((short)64);
/*  70 */     this.optionsContainer.setGlobalFilter(FilterType.DENY_ALL);
/*     */     
/*  72 */     this.combinedItemContainer = new CombinedItemContainer(new ItemContainer[] { (ItemContainer)this.inputContainer, (ItemContainer)this.optionsContainer });
/*  73 */     this.windowData.addProperty("selected", Integer.valueOf(this.selectedSlot));
/*     */     
/*  75 */     StructuralCraftingBench structuralBench = (StructuralCraftingBench)this.bench;
/*  76 */     this.windowData.addProperty("allowBlockGroupCycling", Boolean.valueOf(structuralBench.shouldAllowBlockGroupCycling()));
/*  77 */     this.windowData.addProperty("alwaysShowInventoryHints", Boolean.valueOf(structuralBench.shouldAlwaysShowInventoryHints()));
/*     */   }
/*     */   
/*     */   private boolean isValidInput(FilterActionType filterActionType, ItemContainer itemContainer, short i, ItemStack itemStack) {
/*  81 */     if (filterActionType != FilterActionType.ADD) return true;
/*     */     
/*  83 */     ObjectList<CraftingRecipe> matchingRecipes = getMatchingRecipes(itemStack);
/*     */     
/*  85 */     return (matchingRecipes != null && !matchingRecipes.isEmpty());
/*     */   }
/*     */   
/*     */   private static void sortRecipes(ObjectList<CraftingRecipe> matching, StructuralCraftingBench structuralBench) {
/*  89 */     matching.sort((a, b) -> {
/*     */           boolean aHasHeaderCategory = hasHeaderCategory(structuralBench, a);
/*     */           boolean bHasHeaderCategory = hasHeaderCategory(structuralBench, b);
/*     */           if (aHasHeaderCategory != bHasHeaderCategory) {
/*     */             return aHasHeaderCategory ? -1 : 1;
/*     */           }
/*     */           int categoryA = getSortingPriority(structuralBench, a);
/*     */           int categoryB = getSortingPriority(structuralBench, b);
/*     */           int categoryCompare = Integer.compare(categoryA, categoryB);
/*     */           return (categoryCompare != 0) ? categoryCompare : a.getId().compareTo(b.getId());
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean hasHeaderCategory(StructuralCraftingBench bench, CraftingRecipe recipe) {
/* 106 */     for (BenchRequirement requirement : recipe.getBenchRequirement()) {
/* 107 */       if (requirement.type == bench.getType() && requirement.id.equals(bench.getId()) && requirement.categories != null) {
/* 108 */         for (String category : requirement.categories) {
/* 109 */           if (bench.isHeaderCategory(category)) {
/* 110 */             return true;
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/* 115 */     return false;
/*     */   }
/*     */   
/*     */   private static int getSortingPriority(StructuralCraftingBench bench, CraftingRecipe recipe) {
/* 119 */     int priority = Integer.MAX_VALUE;
/*     */     
/* 121 */     for (BenchRequirement requirement : recipe.getBenchRequirement()) {
/* 122 */       if (requirement.type == bench.getType() && requirement.id.equals(bench.getId()) && requirement.categories != null) {
/* 123 */         for (String category : requirement.categories) {
/* 124 */           priority = Math.min(priority, bench.getCategoryIndex(category));
/*     */         }
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 130 */     return priority;
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
/*     */   public void handleAction(@Nonnull Ref<EntityStore> ref, @Nonnull Store<EntityStore> store, @Nonnull WindowAction action) {
/*     */     // Byte code:
/*     */     //   0: aload_2
/*     */     //   1: aload_1
/*     */     //   2: invokestatic getComponentType : ()Lcom/hypixel/hytale/component/ComponentType;
/*     */     //   5: invokevirtual getComponent : (Lcom/hypixel/hytale/component/Ref;Lcom/hypixel/hytale/component/ComponentType;)Lcom/hypixel/hytale/component/Component;
/*     */     //   8: checkcast com/hypixel/hytale/builtin/crafting/component/CraftingManager
/*     */     //   11: astore #4
/*     */     //   13: aload_3
/*     */     //   14: dup
/*     */     //   15: invokestatic requireNonNull : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   18: pop
/*     */     //   19: astore #5
/*     */     //   21: iconst_0
/*     */     //   22: istore #6
/*     */     //   24: aload #5
/*     */     //   26: iload #6
/*     */     //   28: <illegal opcode> typeSwitch : (Ljava/lang/Object;I)I
/*     */     //   33: tableswitch default -> 306, 0 -> 60, 1 -> 123, 2 -> 272
/*     */     //   60: aload #5
/*     */     //   62: checkcast com/hypixel/hytale/protocol/packets/window/SelectSlotAction
/*     */     //   65: astore #7
/*     */     //   67: aload #7
/*     */     //   69: getfield slot : I
/*     */     //   72: iconst_0
/*     */     //   73: aload_0
/*     */     //   74: getfield optionsContainer : Lcom/hypixel/hytale/server/core/inventory/container/SimpleItemContainer;
/*     */     //   77: invokevirtual getCapacity : ()S
/*     */     //   80: invokestatic clamp : (III)I
/*     */     //   83: istore #8
/*     */     //   85: iload #8
/*     */     //   87: aload_0
/*     */     //   88: getfield selectedSlot : I
/*     */     //   91: if_icmpeq -> 120
/*     */     //   94: aload_0
/*     */     //   95: iload #8
/*     */     //   97: putfield selectedSlot : I
/*     */     //   100: aload_0
/*     */     //   101: getfield windowData : Lcom/google/gson/JsonObject;
/*     */     //   104: ldc 'selected'
/*     */     //   106: aload_0
/*     */     //   107: getfield selectedSlot : I
/*     */     //   110: invokestatic valueOf : (I)Ljava/lang/Integer;
/*     */     //   113: invokevirtual addProperty : (Ljava/lang/String;Ljava/lang/Number;)V
/*     */     //   116: aload_0
/*     */     //   117: invokevirtual invalidate : ()V
/*     */     //   120: goto -> 306
/*     */     //   123: aload #5
/*     */     //   125: checkcast com/hypixel/hytale/protocol/packets/window/CraftRecipeAction
/*     */     //   128: astore #8
/*     */     //   130: aload_0
/*     */     //   131: getfield optionsContainer : Lcom/hypixel/hytale/server/core/inventory/container/SimpleItemContainer;
/*     */     //   134: aload_0
/*     */     //   135: getfield selectedSlot : I
/*     */     //   138: i2s
/*     */     //   139: invokevirtual getItemStack : (S)Lcom/hypixel/hytale/server/core/inventory/ItemStack;
/*     */     //   142: astore #9
/*     */     //   144: aload #9
/*     */     //   146: ifnull -> 269
/*     */     //   149: aload #8
/*     */     //   151: getfield quantity : I
/*     */     //   154: istore #10
/*     */     //   156: aload_0
/*     */     //   157: getfield optionSlotToRecipeMap : Lit/unimi/dsi/fastutil/ints/Int2ObjectMap;
/*     */     //   160: aload_0
/*     */     //   161: getfield selectedSlot : I
/*     */     //   164: invokeinterface get : (I)Ljava/lang/Object;
/*     */     //   169: checkcast java/lang/String
/*     */     //   172: astore #11
/*     */     //   174: aload #11
/*     */     //   176: ifnonnull -> 180
/*     */     //   179: return
/*     */     //   180: invokestatic getAssetMap : ()Lcom/hypixel/hytale/assetstore/map/DefaultAssetMap;
/*     */     //   183: aload #11
/*     */     //   185: invokevirtual getAsset : (Ljava/lang/Object;)Lcom/hypixel/hytale/assetstore/JsonAsset;
/*     */     //   188: checkcast com/hypixel/hytale/server/core/asset/type/item/config/CraftingRecipe
/*     */     //   191: astore #12
/*     */     //   193: aload #12
/*     */     //   195: ifnonnull -> 199
/*     */     //   198: return
/*     */     //   199: aload #12
/*     */     //   201: invokevirtual getPrimaryOutput : ()Lcom/hypixel/hytale/server/core/inventory/MaterialQuantity;
/*     */     //   204: astore #13
/*     */     //   206: aload #13
/*     */     //   208: invokevirtual getItemId : ()Ljava/lang/String;
/*     */     //   211: astore #14
/*     */     //   213: aload #14
/*     */     //   215: ifnull -> 244
/*     */     //   218: invokestatic getAssetMap : ()Lcom/hypixel/hytale/assetstore/map/DefaultAssetMap;
/*     */     //   221: aload #14
/*     */     //   223: invokevirtual getAsset : (Ljava/lang/Object;)Lcom/hypixel/hytale/assetstore/JsonAsset;
/*     */     //   226: checkcast com/hypixel/hytale/server/core/asset/type/item/config/Item
/*     */     //   229: astore #15
/*     */     //   231: aload #15
/*     */     //   233: ifnull -> 244
/*     */     //   236: aload_0
/*     */     //   237: aload_1
/*     */     //   238: aload_2
/*     */     //   239: aload #15
/*     */     //   241: invokevirtual playCraftSound : (Lcom/hypixel/hytale/component/Ref;Lcom/hypixel/hytale/component/Store;Lcom/hypixel/hytale/server/core/asset/type/item/config/Item;)V
/*     */     //   244: aload #4
/*     */     //   246: aload_1
/*     */     //   247: aload_2
/*     */     //   248: aload_0
/*     */     //   249: iconst_0
/*     */     //   250: aload #12
/*     */     //   252: iload #10
/*     */     //   254: aload_0
/*     */     //   255: getfield inputContainer : Lcom/hypixel/hytale/server/core/inventory/container/SimpleItemContainer;
/*     */     //   258: getstatic com/hypixel/hytale/builtin/crafting/component/CraftingManager$InputRemovalType.ORDERED : Lcom/hypixel/hytale/builtin/crafting/component/CraftingManager$InputRemovalType;
/*     */     //   261: invokevirtual queueCraft : (Lcom/hypixel/hytale/component/Ref;Lcom/hypixel/hytale/component/ComponentAccessor;Lcom/hypixel/hytale/builtin/crafting/window/CraftingWindow;ILcom/hypixel/hytale/server/core/asset/type/item/config/CraftingRecipe;ILcom/hypixel/hytale/server/core/inventory/container/ItemContainer;Lcom/hypixel/hytale/builtin/crafting/component/CraftingManager$InputRemovalType;)Z
/*     */     //   264: pop
/*     */     //   265: aload_0
/*     */     //   266: invokevirtual invalidate : ()V
/*     */     //   269: goto -> 306
/*     */     //   272: aload #5
/*     */     //   274: checkcast com/hypixel/hytale/protocol/packets/window/ChangeBlockAction
/*     */     //   277: astore #9
/*     */     //   279: aload_0
/*     */     //   280: getfield bench : Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/bench/Bench;
/*     */     //   283: checkcast com/hypixel/hytale/server/core/asset/type/blocktype/config/bench/StructuralCraftingBench
/*     */     //   286: invokevirtual shouldAllowBlockGroupCycling : ()Z
/*     */     //   289: ifeq -> 306
/*     */     //   292: aload_0
/*     */     //   293: aload_1
/*     */     //   294: aload #9
/*     */     //   296: getfield down : Z
/*     */     //   299: aload_2
/*     */     //   300: invokevirtual changeBlockType : (Lcom/hypixel/hytale/component/Ref;ZLcom/hypixel/hytale/component/Store;)V
/*     */     //   303: goto -> 306
/*     */     //   306: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #135	-> 0
/*     */     //   #137	-> 13
/*     */     //   #138	-> 60
/*     */     //   #139	-> 67
/*     */     //   #140	-> 85
/*     */     //   #141	-> 94
/*     */     //   #142	-> 100
/*     */     //   #143	-> 116
/*     */     //   #145	-> 120
/*     */     //   #146	-> 123
/*     */     //   #147	-> 130
/*     */     //   #148	-> 144
/*     */     //   #149	-> 149
/*     */     //   #150	-> 156
/*     */     //   #152	-> 174
/*     */     //   #153	-> 179
/*     */     //   #156	-> 180
/*     */     //   #157	-> 193
/*     */     //   #158	-> 198
/*     */     //   #161	-> 199
/*     */     //   #162	-> 206
/*     */     //   #164	-> 213
/*     */     //   #165	-> 218
/*     */     //   #167	-> 231
/*     */     //   #168	-> 236
/*     */     //   #172	-> 244
/*     */     //   #173	-> 265
/*     */     //   #175	-> 269
/*     */     //   #176	-> 272
/*     */     //   #177	-> 279
/*     */     //   #178	-> 292
/*     */     //   #184	-> 306
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   85	35	8	newSlot	I
/*     */     //   67	56	7	selectAction	Lcom/hypixel/hytale/protocol/packets/window/SelectSlotAction;
/*     */     //   231	13	15	primaryOutputItem	Lcom/hypixel/hytale/server/core/asset/type/item/config/Item;
/*     */     //   156	113	10	quantity	I
/*     */     //   174	95	11	recipeId	Ljava/lang/String;
/*     */     //   193	76	12	recipe	Lcom/hypixel/hytale/server/core/asset/type/item/config/CraftingRecipe;
/*     */     //   206	63	13	primaryOutput	Lcom/hypixel/hytale/server/core/inventory/MaterialQuantity;
/*     */     //   213	56	14	primaryOutputItemId	Ljava/lang/String;
/*     */     //   144	125	9	output	Lcom/hypixel/hytale/server/core/inventory/ItemStack;
/*     */     //   130	142	8	craftAction	Lcom/hypixel/hytale/protocol/packets/window/CraftRecipeAction;
/*     */     //   279	27	9	changeBlockAction	Lcom/hypixel/hytale/protocol/packets/window/ChangeBlockAction;
/*     */     //   0	307	0	this	Lcom/hypixel/hytale/builtin/crafting/window/StructuralCraftingWindow;
/*     */     //   0	307	1	ref	Lcom/hypixel/hytale/component/Ref;
/*     */     //   0	307	2	store	Lcom/hypixel/hytale/component/Store;
/*     */     //   0	307	3	action	Lcom/hypixel/hytale/protocol/packets/window/WindowAction;
/*     */     //   13	294	4	craftingManager	Lcom/hypixel/hytale/builtin/crafting/component/CraftingManager;
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	307	1	ref	Lcom/hypixel/hytale/component/Ref<Lcom/hypixel/hytale/server/core/universe/world/storage/EntityStore;>;
/*     */     //   0	307	2	store	Lcom/hypixel/hytale/component/Store<Lcom/hypixel/hytale/server/core/universe/world/storage/EntityStore;>;
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
/*     */   private void playCraftSound(Ref<EntityStore> ref, Store<EntityStore> store, Item item) {
/* 187 */     ItemSoundSet soundSet = (ItemSoundSet)ItemSoundSet.getAssetMap().getAsset(item.getItemSoundSetIndex());
/* 188 */     if (soundSet == null)
/*     */       return; 
/* 190 */     String dragSound = (String)soundSet.getSoundEventIds().get(ItemSoundEvent.Drop);
/* 191 */     if (dragSound == null)
/*     */       return; 
/* 193 */     int dragSoundIndex = SoundEvent.getAssetMap().getIndex(dragSound);
/* 194 */     if (dragSoundIndex == 0)
/*     */       return; 
/* 196 */     SoundUtil.playSoundEvent2d(ref, dragSoundIndex, SoundCategory.UI, (ComponentAccessor)store);
/*     */   }
/*     */   private void changeBlockType(@Nonnull Ref<EntityStore> ref, boolean down, @Nonnull Store<EntityStore> store) {
/*     */     int newIndex;
/* 200 */     ItemStack item = this.inputContainer.getItemStack((short)0);
/* 201 */     if (item == null) {
/*     */       return;
/*     */     }
/*     */     
/* 205 */     BlockGroup set = BlockGroup.findItemGroup(item.getItem());
/* 206 */     if (set == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 211 */     int currentIndex = -1;
/* 212 */     for (int i = 0; i < set.size(); i++) {
/* 213 */       if (set.get(i).equals(item.getItem().getId())) {
/* 214 */         currentIndex = i;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 219 */     if (currentIndex == -1) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 225 */     if (down) {
/* 226 */       newIndex = (currentIndex - 1 + set.size()) % set.size();
/*     */     } else {
/* 228 */       newIndex = (currentIndex + 1) % set.size();
/*     */     } 
/* 230 */     String next = set.get(newIndex);
/* 231 */     Item desiredItem = (Item)Item.getAssetMap().getAsset(next);
/*     */     
/* 233 */     if (desiredItem == null) {
/*     */       return;
/*     */     }
/*     */     
/* 237 */     this.inputContainer.replaceItemStackInSlot((short)0, item, new ItemStack(next, item.getQuantity()));
/* 238 */     playCraftSound(ref, store, desiredItem);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ItemContainer getItemContainer() {
/* 244 */     return (ItemContainer)this.combinedItemContainer;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onOpen0() {
/* 249 */     super.onOpen0();
/*     */     
/* 251 */     PlayerRef playerRef = getPlayerRef();
/* 252 */     Ref<EntityStore> ref = playerRef.getReference();
/* 253 */     Store<EntityStore> store = ref.getStore();
/*     */     
/* 255 */     Player player = (Player)store.getComponent(ref, Player.getComponentType());
/* 256 */     Inventory inventory = player.getInventory();
/*     */     
/* 258 */     this.inventoryRegistration = inventory.getCombinedHotbarFirst().registerChangeEvent(event -> {
/*     */           this.windowData.add("inventoryHints", (JsonElement)CraftingManager.generateInventoryHints(CraftingPlugin.getBenchRecipes(this.bench), 0, (ItemContainer)inventory.getCombinedHotbarFirst()));
/*     */           
/*     */           invalidate();
/*     */         });
/* 263 */     this.windowData.add("inventoryHints", (JsonElement)CraftingManager.generateInventoryHints(CraftingPlugin.getBenchRecipes(this.bench), 0, (ItemContainer)inventory.getCombinedHotbarFirst()));
/* 264 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onClose0() {
/* 269 */     super.onClose0();
/*     */     
/* 271 */     PlayerRef playerRef = getPlayerRef();
/* 272 */     Ref<EntityStore> ref = playerRef.getReference();
/* 273 */     Store<EntityStore> store = ref.getStore();
/*     */     
/* 275 */     Player player = (Player)store.getComponent(ref, Player.getComponentType());
/* 276 */     List<ItemStack> itemStacks = this.inputContainer.dropAllItemStacks();
/* 277 */     SimpleItemContainer.addOrDropItemStacks((ComponentAccessor)store, ref, (ItemContainer)player.getInventory().getCombinedHotbarFirst(), itemStacks);
/*     */     
/* 279 */     CraftingManager craftingManager = (CraftingManager)store.getComponent(ref, CraftingManager.getComponentType());
/* 280 */     craftingManager.cancelAllCrafting(ref, (ComponentAccessor)store);
/*     */     
/* 282 */     if (this.inventoryRegistration != null) {
/* 283 */       this.inventoryRegistration.unregister();
/* 284 */       this.inventoryRegistration = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateRecipes() {
/* 289 */     invalidate();
/*     */     
/* 291 */     this.optionsContainer.clear();
/* 292 */     this.optionSlotToRecipeMap.clear();
/*     */     
/* 294 */     ItemStack inputStack = this.inputContainer.getItemStack((short)0);
/* 295 */     ObjectList<CraftingRecipe> matchingRecipes = getMatchingRecipes(inputStack);
/*     */     
/* 297 */     if (matchingRecipes == null)
/*     */       return; 
/* 299 */     StructuralCraftingBench structuralBench = (StructuralCraftingBench)this.bench;
/* 300 */     sortRecipes(matchingRecipes, structuralBench);
/*     */     
/* 302 */     int dividerIndex = 0;
/*     */     
/* 304 */     while (dividerIndex < matchingRecipes.size()) {
/* 305 */       CraftingRecipe recipe = (CraftingRecipe)matchingRecipes.get(dividerIndex);
/* 306 */       if (!hasHeaderCategory(structuralBench, recipe)) {
/*     */         break;
/*     */       }
/* 309 */       dividerIndex++;
/*     */     } 
/*     */     
/* 312 */     this.windowData.addProperty("dividerIndex", Integer.valueOf(dividerIndex));
/*     */     
/* 314 */     this.optionsContainer.clear();
/* 315 */     short index = 0;
/*     */     
/* 317 */     for (int i = 0, bound = matchingRecipes.size(); i < bound; i++) {
/* 318 */       CraftingRecipe match = (CraftingRecipe)matchingRecipes.get(i);
/* 319 */       for (BenchRequirement requirement : match.getBenchRequirement()) {
/* 320 */         if (requirement.type == this.bench.getType() && requirement.id.equals(this.bench.getId())) {
/*     */ 
/*     */ 
/*     */           
/* 324 */           List<ItemStack> output = CraftingManager.getOutputItemStacks(match);
/* 325 */           this.optionsContainer.setItemStackForSlot(index, output.getFirst(), false);
/* 326 */           this.optionSlotToRecipeMap.put(index, match.getId());
/* 327 */           index = (short)(index + 1);
/*     */         } 
/*     */       } 
/*     */     } 
/* 331 */     JsonArray optionSlotRecipes = new JsonArray();
/* 332 */     for (int j = 0; j < this.optionsContainer.getCapacity(); j++) {
/* 333 */       String recipeId = (String)this.optionSlotToRecipeMap.get(j);
/* 334 */       if (recipeId != null) {
/* 335 */         optionSlotRecipes.add(recipeId);
/*     */       }
/*     */     } 
/*     */     
/* 339 */     this.windowData.add("optionSlotRecipes", (JsonElement)optionSlotRecipes);
/*     */   }
/*     */   
/*     */   @NullableDecl
/*     */   private ObjectList<CraftingRecipe> getMatchingRecipes(@Nullable ItemStack inputStack) {
/* 344 */     if (inputStack == null) {
/* 345 */       return null;
/*     */     }
/*     */     
/* 348 */     List<CraftingRecipe> recipes = CraftingPlugin.getBenchRecipes(this.bench.getType(), this.bench.getId());
/* 349 */     if (recipes.isEmpty()) {
/* 350 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 354 */     ObjectArrayList objectArrayList = new ObjectArrayList();
/*     */     
/* 356 */     for (int i = 0, bound = recipes.size(); i < bound; i++) {
/* 357 */       CraftingRecipe recipe = recipes.get(i);
/* 358 */       List<MaterialQuantity> inputMaterials = CraftingManager.getInputMaterials(recipe);
/*     */ 
/*     */       
/* 361 */       if (inputMaterials.size() == 1)
/*     */       {
/* 363 */         if (CraftingManager.matches(inputMaterials.getFirst(), inputStack)) {
/* 364 */           objectArrayList.add(recipe);
/*     */         }
/*     */       }
/*     */     } 
/* 368 */     if (objectArrayList.isEmpty()) {
/* 369 */       return null;
/*     */     }
/* 371 */     return (ObjectList<CraftingRecipe>)objectArrayList;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\crafting\window\StructuralCraftingWindow.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */