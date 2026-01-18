/*     */ package com.hypixel.hytale.builtin.crafting.state;
/*     */ import com.google.common.flogger.LazyArgs;
/*     */ import com.hypixel.hytale.builtin.crafting.component.CraftingManager;
/*     */ import com.hypixel.hytale.builtin.crafting.window.ProcessingBenchWindow;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.event.EventPriority;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.protocol.Transform;
/*     */ import com.hypixel.hytale.protocol.packets.worldmap.MapMarker;
/*     */ import com.hypixel.hytale.server.core.asset.type.blockhitbox.BlockBoundingBoxes;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.RotationTuple;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.bench.BenchTierLevel;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.bench.ProcessingBench;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.CraftingRecipe;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.inventory.MaterialQuantity;
/*     */ import com.hypixel.hytale.server.core.inventory.ResourceQuantity;
/*     */ import com.hypixel.hytale.server.core.inventory.container.CombinedItemContainer;
/*     */ import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
/*     */ import com.hypixel.hytale.server.core.inventory.container.SimpleItemContainer;
/*     */ import com.hypixel.hytale.server.core.inventory.container.TestRemoveItemSlotResult;
/*     */ import com.hypixel.hytale.server.core.inventory.container.filter.FilterActionType;
/*     */ import com.hypixel.hytale.server.core.inventory.container.filter.ResourceFilter;
/*     */ import com.hypixel.hytale.server.core.inventory.transaction.ItemStackTransaction;
/*     */ import com.hypixel.hytale.server.core.inventory.transaction.ListTransaction;
/*     */ import com.hypixel.hytale.server.core.inventory.transaction.MaterialSlotTransaction;
/*     */ import com.hypixel.hytale.server.core.inventory.transaction.MaterialTransaction;
/*     */ import com.hypixel.hytale.server.core.inventory.transaction.ResourceTransaction;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.item.ItemComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.meta.state.MarkerBlockState;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldmap.WorldMapManager;
/*     */ import it.unimi.dsi.fastutil.ints.IntArrayList;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectListIterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.ThreadLocalRandom;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import org.bson.BsonDocument;
/*     */ 
/*     */ public class ProcessingBenchState extends BenchState implements TickableBlockState, ItemContainerBlockState, DestroyableBlockState, MarkerBlockState, PlacedByBlockState {
/*  63 */   public static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final boolean EXACT_RESOURCE_AMOUNTS = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final Codec<ProcessingBenchState> CODEC;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final float EJECT_VELOCITY = 2.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final float EJECT_SPREAD_VELOCITY = 1.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final float EJECT_VERTICAL_VELOCITY = 3.25F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String PROCESSING = "Processing";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String PROCESS_COMPLETED = "ProcessCompleted";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected WorldMapManager.MarkerReference marker;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 125 */     CODEC = (Codec<ProcessingBenchState>)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ProcessingBenchState.class, ProcessingBenchState::new, BenchState.CODEC).append(new KeyedCodec("InputContainer", (Codec)ItemContainer.CODEC), (state, o) -> state.inputContainer = o, state -> state.inputContainer).add()).append(new KeyedCodec("FuelContainer", (Codec)ItemContainer.CODEC), (state, o) -> state.fuelContainer = o, state -> state.fuelContainer).add()).append(new KeyedCodec("OutputContainer", (Codec)ItemContainer.CODEC), (state, o) -> state.outputContainer = o, state -> state.outputContainer).add()).append(new KeyedCodec("Progress", (Codec)Codec.DOUBLE), (state, d) -> state.inputProgress = d.floatValue(), state -> Double.valueOf(state.inputProgress)).add()).append(new KeyedCodec("FuelTime", (Codec)Codec.DOUBLE), (state, d) -> state.fuelTime = d.floatValue(), state -> Double.valueOf(state.fuelTime)).add()).append(new KeyedCodec("Active", (Codec)Codec.BOOLEAN), (state, b) -> state.active = b.booleanValue(), state -> Boolean.valueOf(state.active)).add()).append(new KeyedCodec("NextExtra", (Codec)Codec.INTEGER), (state, b) -> state.nextExtra = b.intValue(), state -> Integer.valueOf(state.nextExtra)).add()).append(new KeyedCodec("Marker", (Codec)WorldMapManager.MarkerReference.CODEC), (state, o) -> state.marker = o, state -> state.marker).add()).append(new KeyedCodec("RecipeId", (Codec)Codec.STRING), (state, o) -> state.recipeId = o, state -> state.recipeId).add()).build();
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
/* 138 */   private final Map<UUID, ProcessingBenchWindow> windows = new ConcurrentHashMap<>();
/*     */   
/*     */   private ProcessingBench processingBench;
/*     */   
/*     */   private ItemContainer inputContainer;
/*     */   
/*     */   private ItemContainer fuelContainer;
/*     */   
/*     */   private ItemContainer outputContainer;
/*     */   private CombinedItemContainer combinedItemContainer;
/*     */   private float inputProgress;
/*     */   private float fuelTime;
/*     */   private int lastConsumedFuelTotal;
/* 151 */   private int nextExtra = -1;
/* 152 */   private final Set<Short> processingSlots = new HashSet<>();
/* 153 */   private final Set<Short> processingFuelSlots = new HashSet<>();
/*     */   
/*     */   @Nullable
/*     */   private String recipeId;
/*     */   
/*     */   @Nullable
/*     */   private CraftingRecipe recipe;
/*     */   
/*     */   private boolean active = false;
/*     */   
/*     */   public boolean initialize(@Nonnull BlockType blockType) {
/* 164 */     if (!super.initialize(blockType)) {
/* 165 */       if (this.bench == null) {
/* 166 */         ObjectArrayList objectArrayList = new ObjectArrayList();
/* 167 */         if (this.inputContainer != null) objectArrayList.addAll(this.inputContainer.dropAllItemStacks()); 
/* 168 */         if (this.fuelContainer != null) objectArrayList.addAll(this.fuelContainer.dropAllItemStacks()); 
/* 169 */         if (this.outputContainer != null) objectArrayList.addAll(this.outputContainer.dropAllItemStacks());
/*     */         
/* 171 */         World world = getChunk().getWorld();
/* 172 */         Store<EntityStore> store = world.getEntityStore().getStore();
/*     */ 
/*     */         
/* 175 */         Holder[] arrayOfHolder = (Holder[])ejectItems((ComponentAccessor<EntityStore>)store, (List<ItemStack>)objectArrayList);
/*     */         
/* 177 */         if (arrayOfHolder.length > 0) {
/* 178 */           world.execute(() -> store.addEntities(itemEntityHolders, AddReason.SPAWN));
/*     */         }
/*     */       } 
/* 181 */       return false;
/*     */     } 
/*     */     
/* 184 */     if (!(this.bench instanceof ProcessingBench)) {
/* 185 */       LOGGER.at(Level.SEVERE).log("Wrong bench type for processing. Got %s", this.bench.getClass().getName());
/* 186 */       return false;
/*     */     } 
/*     */     
/* 189 */     this.processingBench = (ProcessingBench)this.bench;
/*     */     
/* 191 */     if (this.nextExtra == -1) {
/* 192 */       this.nextExtra = (this.processingBench.getExtraOutput() != null) ? this.processingBench.getExtraOutput().getPerFuelItemsConsumed() : 0;
/*     */     }
/*     */     
/* 195 */     setupSlots();
/* 196 */     return true;
/*     */   }
/*     */   
/*     */   private void setupSlots() {
/* 200 */     ObjectArrayList objectArrayList = new ObjectArrayList();
/* 201 */     int tierLevel = getTierLevel();
/*     */ 
/*     */     
/* 204 */     ProcessingBench.ProcessingSlot[] input = this.processingBench.getInput(tierLevel);
/* 205 */     short inputSlotsCount = (short)input.length;
/* 206 */     this.inputContainer = ItemContainer.ensureContainerCapacity(this.inputContainer, inputSlotsCount, SimpleItemContainer::getNewContainer, (List)objectArrayList);
/* 207 */     this.inputContainer.registerChangeEvent(EventPriority.LAST, this::onItemChange);
/*     */     
/*     */     short slot;
/* 210 */     for (slot = 0; slot < inputSlotsCount; slot = (short)(slot + 1)) {
/* 211 */       ProcessingBench.ProcessingSlot inputSlot = input[slot];
/* 212 */       String resourceTypeId = inputSlot.getResourceTypeId();
/* 213 */       boolean shouldFilterValidIngredients = inputSlot.shouldFilterValidIngredients();
/*     */       
/* 215 */       if (resourceTypeId != null) {
/* 216 */         this.inputContainer.setSlotFilter(FilterActionType.ADD, slot, (SlotFilter)new ResourceFilter(new ResourceQuantity(resourceTypeId, 1)));
/* 217 */       } else if (shouldFilterValidIngredients) {
/* 218 */         ObjectArrayList<MaterialQuantity> validIngredients = new ObjectArrayList();
/* 219 */         List<CraftingRecipe> recipes = CraftingPlugin.getBenchRecipes(this.bench.getType(), this.bench.getId());
/*     */         
/* 221 */         for (CraftingRecipe recipe : recipes) {
/* 222 */           if (recipe.isRestrictedByBenchTierLevel(this.bench.getId(), tierLevel))
/*     */             continue; 
/* 224 */           List<MaterialQuantity> inputMaterials = CraftingManager.getInputMaterials(recipe);
/* 225 */           validIngredients.addAll(inputMaterials);
/*     */         } 
/*     */         
/* 228 */         this.inputContainer.setSlotFilter(FilterActionType.ADD, slot, (actionType, container, slotIndex, itemStack) -> {
/*     */               if (itemStack == null) {
/*     */                 return true;
/*     */               }
/*     */               ObjectListIterator<MaterialQuantity> objectListIterator = validIngredients.iterator();
/*     */               while (objectListIterator.hasNext()) {
/*     */                 MaterialQuantity ingredient = objectListIterator.next();
/*     */                 if (CraftingManager.matches(ingredient, itemStack)) {
/*     */                   return true;
/*     */                 }
/*     */               } 
/*     */               return false;
/*     */             });
/*     */       } 
/*     */     } 
/* 243 */     ProcessingBench.ProcessingSlot[] benchFuel = this.processingBench.getFuel();
/* 244 */     short fuelCapacity = (short)((benchFuel != null) ? benchFuel.length : 0);
/* 245 */     this.fuelContainer = ItemContainer.ensureContainerCapacity(this.fuelContainer, fuelCapacity, SimpleItemContainer::getNewContainer, (List)objectArrayList);
/* 246 */     this.fuelContainer.registerChangeEvent(EventPriority.LAST, this::onItemChange);
/*     */ 
/*     */     
/* 249 */     if (fuelCapacity > 0) {
/* 250 */       for (int i = 0; i < benchFuel.length; i++) {
/* 251 */         ProcessingBench.ProcessingSlot fuel = benchFuel[i];
/* 252 */         String resourceTypeId = fuel.getResourceTypeId();
/*     */         
/* 254 */         if (resourceTypeId != null) {
/* 255 */           this.fuelContainer.setSlotFilter(FilterActionType.ADD, (short)i, (SlotFilter)new ResourceFilter(new ResourceQuantity(resourceTypeId, 1)));
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 262 */     short outputSlotsCount = (short)this.processingBench.getOutputSlotsCount(tierLevel);
/* 263 */     this.outputContainer = ItemContainer.ensureContainerCapacity(this.outputContainer, outputSlotsCount, SimpleItemContainer::getNewContainer, (List)objectArrayList);
/* 264 */     this.outputContainer.registerChangeEvent(EventPriority.LAST, this::onItemChange);
/*     */ 
/*     */     
/* 267 */     if (outputSlotsCount > 0) this.outputContainer.setGlobalFilter(FilterType.ALLOW_OUTPUT_ONLY);
/*     */ 
/*     */     
/* 270 */     this.combinedItemContainer = new CombinedItemContainer(new ItemContainer[] { this.fuelContainer, this.inputContainer, this.outputContainer });
/*     */     
/* 272 */     World world = getChunk().getWorld();
/* 273 */     Store<EntityStore> store = world.getEntityStore().getStore();
/* 274 */     Holder[] arrayOfHolder = (Holder[])ejectItems((ComponentAccessor<EntityStore>)store, (List<ItemStack>)objectArrayList);
/* 275 */     if (arrayOfHolder.length > 0) {
/* 276 */       world.execute(() -> store.addEntities(itemEntityHolders, AddReason.SPAWN));
/*     */     }
/*     */     
/* 279 */     this.inputContainer.registerChangeEvent(EventPriority.LAST, event -> updateRecipe());
/*     */ 
/*     */     
/* 282 */     if (this.processingBench.getFuel() == null) {
/* 283 */       setActive(true);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(float dt, int index, ArchetypeChunk<ChunkStore> archetypeChunk, @Nonnull Store<ChunkStore> store, CommandBuffer<ChunkStore> commandBuffer) {
/* 289 */     World world = ((ChunkStore)store.getExternalData()).getWorld();
/* 290 */     Store<EntityStore> entityStore = world.getEntityStore().getStore();
/*     */     
/* 292 */     BlockType blockType = getBlockType();
/* 293 */     String currentState = BlockAccessor.getCurrentInteractionState(blockType);
/*     */     
/* 295 */     List<ItemStack> outputItemStacks = null;
/* 296 */     List<MaterialQuantity> inputMaterials = null;
/*     */     
/* 298 */     this.processingSlots.clear();
/* 299 */     checkForRecipeUpdate();
/*     */     
/* 301 */     if (this.recipe != null) {
/*     */       
/* 303 */       outputItemStacks = CraftingManager.getOutputItemStacks(this.recipe);
/* 304 */       if (!this.outputContainer.canAddItemStacks(outputItemStacks, false, false)) {
/* 305 */         if ("Processing".equals(currentState)) {
/* 306 */           setBlockInteractionState("default", blockType);
/* 307 */           playSound(world, this.processingBench.getFailedSoundEventIndex(), (ComponentAccessor<EntityStore>)entityStore);
/* 308 */         } else if ("ProcessCompleted".equals(currentState)) {
/* 309 */           setBlockInteractionState("default", blockType);
/* 310 */           playSound(world, this.processingBench.getEndSoundEventIndex(), (ComponentAccessor<EntityStore>)entityStore);
/*     */         } 
/*     */         
/* 313 */         setActive(false);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 318 */       inputMaterials = CraftingManager.getInputMaterials(this.recipe);
/* 319 */       List<TestRemoveItemSlotResult> result = this.inputContainer.getSlotMaterialsToRemove(inputMaterials, true, true);
/*     */       
/* 321 */       if (result.isEmpty()) {
/* 322 */         if ("Processing".equals(currentState)) {
/* 323 */           setBlockInteractionState("default", blockType);
/* 324 */           playSound(world, this.processingBench.getFailedSoundEventIndex(), (ComponentAccessor<EntityStore>)entityStore);
/* 325 */         } else if ("ProcessCompleted".equals(currentState)) {
/* 326 */           setBlockInteractionState("default", blockType);
/* 327 */           playSound(world, this.processingBench.getEndSoundEventIndex(), (ComponentAccessor<EntityStore>)entityStore);
/*     */         } 
/* 329 */         this.inputProgress = 0.0F;
/* 330 */         setActive(false);
/* 331 */         this.recipeId = null;
/* 332 */         this.recipe = null;
/*     */         
/*     */         return;
/*     */       } 
/* 336 */       for (TestRemoveItemSlotResult item : result) {
/* 337 */         this.processingSlots.addAll(item.getPickedSlots());
/*     */       }
/* 339 */       sendProcessingSlots();
/*     */     } else {
/* 341 */       if (this.processingBench.getFuel() == null) {
/*     */         
/* 343 */         if ("Processing".equals(currentState)) {
/* 344 */           setBlockInteractionState("default", blockType);
/* 345 */           playSound(world, this.processingBench.getFailedSoundEventIndex(), (ComponentAccessor<EntityStore>)entityStore);
/* 346 */         } else if ("ProcessCompleted".equals(currentState)) {
/* 347 */           setBlockInteractionState("default", blockType);
/* 348 */           playSound(world, this.processingBench.getEndSoundEventIndex(), (ComponentAccessor<EntityStore>)entityStore);
/*     */         } 
/*     */         return;
/*     */       } 
/* 352 */       boolean allowNoInputProcessing = this.processingBench.shouldAllowNoInputProcessing();
/*     */       
/* 354 */       if (!allowNoInputProcessing && "Processing".equals(currentState)) {
/* 355 */         setBlockInteractionState("default", blockType);
/* 356 */         playSound(world, this.processingBench.getFailedSoundEventIndex(), (ComponentAccessor<EntityStore>)entityStore);
/* 357 */       } else if ("ProcessCompleted".equals(currentState)) {
/* 358 */         setBlockInteractionState("default", blockType);
/* 359 */         playSound(world, this.processingBench.getEndSoundEventIndex(), (ComponentAccessor<EntityStore>)entityStore);
/* 360 */         setActive(false);
/* 361 */         sendProgress(0.0F);
/*     */         
/*     */         return;
/*     */       } 
/* 365 */       sendProgress(0.0F);
/*     */       
/* 367 */       if (!allowNoInputProcessing) {
/* 368 */         setActive(false);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*     */     
/* 374 */     boolean needsUpdate = false;
/* 375 */     if (this.fuelTime > 0.0F && this.active) {
/* 376 */       this.fuelTime -= dt;
/* 377 */       if (this.fuelTime < 0.0F) this.fuelTime = 0.0F; 
/* 378 */       needsUpdate = true;
/*     */     } 
/*     */     
/* 381 */     ProcessingBench.ProcessingSlot[] fuelSlots = this.processingBench.getFuel();
/* 382 */     boolean hasFuelSlots = (fuelSlots != null && fuelSlots.length > 0);
/*     */     
/* 384 */     if ((this.processingBench.getMaxFuel() <= 0 || this.fuelTime < this.processingBench.getMaxFuel()) && !this.fuelContainer.isEmpty()) {
/* 385 */       if (!hasFuelSlots)
/*     */         return; 
/* 387 */       if (this.active)
/*     */       {
/* 389 */         if (this.fuelTime > 0.0F) {
/*     */ 
/*     */           
/* 392 */           for (int i = 0; i < fuelSlots.length; i++) {
/* 393 */             ItemStack itemInSlot = this.fuelContainer.getItemStack((short)i);
/* 394 */             if (itemInSlot != null) {
/* 395 */               this.processingFuelSlots.add(Short.valueOf((short)i));
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } else {
/* 401 */           if (this.fuelTime < 0.0F) this.fuelTime = 0.0F; 
/* 402 */           this.processingFuelSlots.clear();
/*     */ 
/*     */           
/* 405 */           for (int i = 0; i < fuelSlots.length; i++) {
/* 406 */             ProcessingBench.ProcessingSlot fuelSlot = fuelSlots[i];
/*     */             
/* 408 */             String resourceTypeId = (fuelSlot.getResourceTypeId() != null) ? fuelSlot.getResourceTypeId() : "Fuel";
/* 409 */             ResourceQuantity resourceQuantity = new ResourceQuantity(resourceTypeId, 1);
/*     */             
/* 411 */             ItemStack slot = this.fuelContainer.getItemStack((short)i);
/*     */             
/* 413 */             if (slot != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 419 */               double fuelQuality = slot.getItem().getFuelQuality();
/* 420 */               ResourceTransaction transaction = this.fuelContainer.removeResource(resourceQuantity, true, true, true);
/* 421 */               this.processingFuelSlots.add(Short.valueOf((short)i));
/*     */ 
/*     */               
/* 424 */               if (transaction.getRemainder() <= 0) {
/*     */ 
/*     */                 
/* 427 */                 ProcessingBench.ExtraOutput extra = this.processingBench.getExtraOutput();
/* 428 */                 if (extra != null && 
/* 429 */                   !extra.isIgnoredFuelSource(slot.getItem())) {
/* 430 */                   this.nextExtra--;
/* 431 */                   if (this.nextExtra <= 0) {
/* 432 */                     this.nextExtra = extra.getPerFuelItemsConsumed();
/*     */                     
/* 434 */                     ObjectArrayList<ItemStack> extraItemStacks = new ObjectArrayList((extra.getOutputs()).length);
/* 435 */                     for (MaterialQuantity e : extra.getOutputs()) {
/* 436 */                       extraItemStacks.add(e.toItemStack());
/*     */                     }
/* 438 */                     ListTransaction<ItemStackTransaction> addTransaction = this.outputContainer.addItemStacks((List)extraItemStacks, false, false, false);
/*     */ 
/*     */                     
/* 441 */                     ObjectArrayList<ItemStack> objectArrayList1 = new ObjectArrayList();
/* 442 */                     for (ItemStackTransaction itemStackTransaction : addTransaction.getList()) {
/* 443 */                       ItemStack remainder = itemStackTransaction.getRemainder();
/* 444 */                       if (remainder != null && !remainder.isEmpty()) {
/* 445 */                         objectArrayList1.add(remainder);
/*     */                       }
/*     */                     } 
/*     */                     
/* 449 */                     if (!objectArrayList1.isEmpty()) {
/* 450 */                       LOGGER.at(Level.WARNING).log("Dropping excess items at %s", getBlockPosition());
/*     */                       
/* 452 */                       Holder[] arrayOfHolder = (Holder[])ejectItems((ComponentAccessor<EntityStore>)entityStore, (List<ItemStack>)objectArrayList1);
/* 453 */                       entityStore.addEntities(arrayOfHolder, AddReason.SPAWN);
/*     */                     } 
/*     */                   } 
/*     */                 } 
/*     */                 
/* 458 */                 this.fuelTime = (float)(this.fuelTime + transaction.getConsumed() * fuelQuality);
/* 459 */                 needsUpdate = true;
/*     */                 break;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/* 467 */     if (needsUpdate) {
/* 468 */       updateFuelValues();
/*     */     }
/*     */ 
/*     */     
/* 472 */     if (hasFuelSlots && (!this.active || this.fuelTime <= 0.0F)) {
/* 473 */       this.lastConsumedFuelTotal = 0;
/*     */       
/* 475 */       if ("Processing".equals(currentState)) {
/* 476 */         setBlockInteractionState("default", blockType);
/* 477 */         playSound(world, this.processingBench.getFailedSoundEventIndex(), (ComponentAccessor<EntityStore>)entityStore);
/* 478 */         if (this.processingBench.getFuel() != null) {
/* 479 */           setActive(false);
/*     */         }
/* 481 */       } else if ("ProcessCompleted".equals(currentState)) {
/* 482 */         setBlockInteractionState("default", blockType);
/* 483 */         playSound(world, this.processingBench.getFailedSoundEventIndex(), (ComponentAccessor<EntityStore>)entityStore);
/* 484 */         if (this.processingBench.getFuel() != null) {
/* 485 */           setActive(false);
/*     */         }
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/* 491 */     if (!"Processing".equals(currentState)) setBlockInteractionState("Processing", blockType);
/*     */     
/* 493 */     if (this.recipe != null && (this.fuelTime > 0.0F || this.processingBench.getFuel() == null)) {
/* 494 */       this.inputProgress += dt;
/*     */     }
/*     */ 
/*     */     
/* 498 */     if (this.recipe != null) {
/*     */       
/* 500 */       float recipeTime = this.recipe.getTimeSeconds();
/*     */       
/* 502 */       float craftingTimeReductionModifier = getCraftingTimeReductionModifier();
/* 503 */       if (craftingTimeReductionModifier > 0.0F) {
/* 504 */         recipeTime -= recipeTime * craftingTimeReductionModifier;
/*     */       }
/*     */       
/* 507 */       if (this.inputProgress > recipeTime) {
/* 508 */         if (recipeTime > 0.0F) {
/* 509 */           this.inputProgress -= recipeTime;
/* 510 */           float progressPercent = this.inputProgress / recipeTime;
/* 511 */           sendProgress(progressPercent);
/*     */         } else {
/* 513 */           this.inputProgress = 0.0F;
/* 514 */           sendProgress(0.0F);
/*     */         } 
/*     */         
/* 517 */         LOGGER.at(Level.FINE).log("Do Process for %s %s", this.recipeId, this.recipe);
/*     */ 
/*     */         
/* 520 */         if (inputMaterials != null) {
/* 521 */           ObjectArrayList<ItemStack> objectArrayList1 = new ObjectArrayList();
/* 522 */           int success = 0;
/* 523 */           IntArrayList slots = new IntArrayList();
/* 524 */           for (int j = 0; j < this.inputContainer.getCapacity(); j++) {
/* 525 */             slots.add(j);
/*     */           }
/* 527 */           for (MaterialQuantity material : inputMaterials) {
/* 528 */             for (int i = 0; i < slots.size(); i++) {
/* 529 */               int slot = slots.getInt(i);
/* 530 */               MaterialSlotTransaction materialSlotTransaction = this.inputContainer.removeMaterialFromSlot((short)slot, material, true, true, true);
/* 531 */               if (materialSlotTransaction.succeeded()) {
/* 532 */                 success++;
/* 533 */                 slots.removeInt(i);
/*     */                 
/*     */                 break;
/*     */               } 
/*     */             } 
/*     */           } 
/* 539 */           ListTransaction<ItemStackTransaction> listTransaction = this.outputContainer.addItemStacks(outputItemStacks, false, false, false);
/* 540 */           if (!listTransaction.succeeded()) {
/*     */             return;
/*     */           }
/* 543 */           for (ItemStackTransaction itemStackTransaction : listTransaction.getList()) {
/* 544 */             ItemStack remainder = itemStackTransaction.getRemainder();
/* 545 */             if (remainder != null && !remainder.isEmpty()) {
/* 546 */               objectArrayList1.add(remainder);
/*     */             }
/*     */           } 
/*     */           
/* 550 */           if (success == inputMaterials.size()) {
/* 551 */             setBlockInteractionState("ProcessCompleted", blockType);
/* 552 */             playSound(world, this.bench.getCompletedSoundEventIndex(), (ComponentAccessor<EntityStore>)entityStore);
/*     */             
/* 554 */             if (!objectArrayList1.isEmpty()) {
/* 555 */               LOGGER.at(Level.WARNING).log("Dropping excess items at %s", getBlockPosition());
/*     */               
/* 557 */               Holder[] arrayOfHolder1 = (Holder[])ejectItems((ComponentAccessor<EntityStore>)entityStore, (List<ItemStack>)objectArrayList1);
/* 558 */               entityStore.addEntities(arrayOfHolder1, AddReason.SPAWN);
/*     */             } 
/*     */             
/*     */             return;
/*     */           } 
/*     */         } 
/* 564 */         ObjectArrayList<ItemStack> objectArrayList = new ObjectArrayList();
/*     */         
/* 566 */         ListTransaction<MaterialTransaction> transaction = this.inputContainer.removeMaterials(inputMaterials, true, true, true);
/* 567 */         if (!transaction.succeeded()) {
/* 568 */           LOGGER.at(Level.WARNING).log("Failed to remove input materials at %s", getBlockPosition());
/* 569 */           setBlockInteractionState("default", blockType);
/* 570 */           playSound(world, this.processingBench.getFailedSoundEventIndex(), (ComponentAccessor<EntityStore>)entityStore);
/*     */           
/*     */           return;
/*     */         } 
/* 574 */         setBlockInteractionState("ProcessCompleted", blockType);
/* 575 */         playSound(world, this.bench.getCompletedSoundEventIndex(), (ComponentAccessor<EntityStore>)entityStore);
/*     */         
/* 577 */         ListTransaction<ItemStackTransaction> addTransaction = this.outputContainer.addItemStacks(outputItemStacks, false, false, false);
/* 578 */         if (addTransaction.succeeded())
/*     */           return; 
/* 580 */         LOGGER.at(Level.WARNING).log("Dropping excess items at %s", getBlockPosition());
/*     */ 
/*     */         
/* 583 */         for (ItemStackTransaction itemStackTransaction : addTransaction.getList()) {
/* 584 */           ItemStack remainder = itemStackTransaction.getRemainder();
/* 585 */           if (remainder != null && !remainder.isEmpty()) {
/* 586 */             objectArrayList.add(remainder);
/*     */           }
/*     */         } 
/*     */         
/* 590 */         Holder[] arrayOfHolder = (Holder[])ejectItems((ComponentAccessor<EntityStore>)entityStore, (List<ItemStack>)objectArrayList);
/* 591 */         entityStore.addEntities(arrayOfHolder, AddReason.SPAWN);
/*     */       }
/* 593 */       else if (this.recipe != null && recipeTime > 0.0F) {
/* 594 */         float progressPercent = this.inputProgress / recipeTime;
/* 595 */         sendProgress(progressPercent);
/*     */       } else {
/* 597 */         sendProgress(0.0F);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private float getCraftingTimeReductionModifier() {
/* 604 */     BenchTierLevel levelData = this.bench.getTierLevel(getTierLevel());
/* 605 */     return (levelData != null) ? levelData.getCraftingTimeReductionModifier() : 0.0F;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private Holder<EntityStore>[] ejectItems(@Nonnull ComponentAccessor<EntityStore> accessor, @Nonnull List<ItemStack> itemStacks) {
/*     */     Vector3d dropPosition;
/* 611 */     if (itemStacks.isEmpty()) {
/* 612 */       return (Holder<EntityStore>[])Holder.emptyArray();
/*     */     }
/*     */     
/* 615 */     RotationTuple rotation = RotationTuple.get(getRotationIndex());
/* 616 */     Vector3d frontDir = new Vector3d(0.0D, 0.0D, 1.0D);
/* 617 */     rotation.yaw().rotateY(frontDir, frontDir);
/*     */ 
/*     */     
/* 620 */     BlockType blockType = getBlockType();
/* 621 */     if (blockType == null) {
/* 622 */       dropPosition = getBlockPosition().toVector3d().add(0.5D, 0.0D, 0.5D);
/*     */     } else {
/* 624 */       BlockBoundingBoxes hitboxAsset = (BlockBoundingBoxes)BlockBoundingBoxes.getAssetMap().getAsset(blockType.getHitboxTypeIndex());
/* 625 */       if (hitboxAsset == null) {
/* 626 */         dropPosition = getBlockPosition().toVector3d().add(0.5D, 0.0D, 0.5D);
/*     */       } else {
/* 628 */         double depth = hitboxAsset.get(0).getBoundingBox().depth();
/* 629 */         double frontOffset = depth / 2.0D + 0.10000000149011612D;
/*     */         
/* 631 */         dropPosition = getCenteredBlockPosition();
/* 632 */         dropPosition.add(frontDir.x * frontOffset, 0.0D, frontDir.z * frontOffset);
/*     */       } 
/*     */     } 
/*     */     
/* 636 */     ThreadLocalRandom random = ThreadLocalRandom.current();
/* 637 */     ObjectArrayList<Holder<EntityStore>> result = new ObjectArrayList(itemStacks.size());
/*     */     
/* 639 */     for (ItemStack item : itemStacks) {
/* 640 */       float velocityX = (float)(frontDir.x * 2.0D + 2.0D * (random.nextDouble() - 0.5D));
/* 641 */       float velocityZ = (float)(frontDir.z * 2.0D + 2.0D * (random.nextDouble() - 0.5D));
/*     */       
/* 643 */       Holder<EntityStore> holder = ItemComponent.generateItemDrop(accessor, item, dropPosition, Vector3f.ZERO, velocityX, 3.25F, velocityZ);
/*     */       
/* 645 */       if (holder != null) {
/* 646 */         result.add(holder);
/*     */       }
/*     */     } 
/*     */     
/* 650 */     return (Holder<EntityStore>[])result.toArray(x$0 -> new Holder[x$0]);
/*     */   }
/*     */   
/*     */   private void sendProgress(float progress) {
/* 654 */     this.windows.forEach((uuid, window) -> window.setProgress(progress));
/*     */   }
/*     */   
/*     */   private void sendProcessingSlots() {
/* 658 */     this.windows.forEach((uuid, window) -> window.setProcessingSlots(this.processingSlots));
/*     */   }
/*     */   
/*     */   private void sendProcessingFuelSlots() {
/* 662 */     this.windows.forEach((uuid, window) -> window.setProcessingFuelSlots(this.processingFuelSlots));
/*     */   }
/*     */   
/*     */   public boolean isActive() {
/* 666 */     return this.active;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean setActive(boolean active) {
/* 675 */     if (this.active != active) {
/* 676 */       if (active && this.processingBench.getFuel() != null && this.fuelContainer.isEmpty()) return false; 
/* 677 */       this.active = active;
/*     */       
/* 679 */       if (!active) {
/* 680 */         this.processingSlots.clear();
/* 681 */         this.processingFuelSlots.clear();
/* 682 */         sendProcessingSlots();
/* 683 */         sendProcessingFuelSlots();
/*     */       } 
/* 685 */       updateRecipe();
/* 686 */       this.windows.forEach((uuid, window) -> window.setActive(active));
/* 687 */       markNeedsSave();
/* 688 */       return true;
/*     */     } 
/* 690 */     return false;
/*     */   }
/*     */   
/*     */   public void updateFuelValues() {
/* 694 */     if (this.fuelTime > this.lastConsumedFuelTotal) {
/* 695 */       this.lastConsumedFuelTotal = MathUtil.ceil(this.fuelTime);
/*     */     }
/*     */     
/* 698 */     float fuelPercent = (this.lastConsumedFuelTotal > 0) ? (this.fuelTime / this.lastConsumedFuelTotal) : 0.0F;
/* 699 */     this.windows.forEach((uuid, window) -> {
/*     */           window.setFuelTime(fuelPercent);
/*     */           window.setMaxFuel(this.lastConsumedFuelTotal);
/*     */           window.setProcessingFuelSlots(this.processingFuelSlots);
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDestroy() {
/* 708 */     super.onDestroy();
/* 709 */     WindowManager.closeAndRemoveAll(this.windows);
/*     */     
/* 711 */     if (this.combinedItemContainer != null) {
/* 712 */       List<ItemStack> itemStacks = this.combinedItemContainer.dropAllItemStacks();
/* 713 */       dropFuelItems(itemStacks);
/*     */       
/* 715 */       World world = getChunk().getWorld();
/* 716 */       Store<EntityStore> entityStore = world.getEntityStore().getStore();
/* 717 */       Vector3d dropPosition = getBlockPosition().toVector3d().add(0.5D, 0.0D, 0.5D);
/* 718 */       Holder[] arrayOfHolder = ItemComponent.generateItemDrops((ComponentAccessor)entityStore, itemStacks, dropPosition, Vector3f.ZERO);
/* 719 */       if (arrayOfHolder.length > 0) {
/* 720 */         world.execute(() -> entityStore.addEntities(itemEntityHolders, AddReason.SPAWN));
/*     */       }
/*     */     } 
/*     */     
/* 724 */     if (this.marker != null) this.marker.remove();
/*     */   
/*     */   }
/*     */   
/*     */   public CombinedItemContainer getItemContainer() {
/* 729 */     return this.combinedItemContainer;
/*     */   }
/*     */   
/*     */   private void checkForRecipeUpdate() {
/* 733 */     if (this.recipe == null && this.recipeId != null) {
/* 734 */       updateRecipe();
/*     */     }
/*     */   }
/*     */   
/*     */   private void updateRecipe() {
/* 739 */     List<CraftingRecipe> recipes = CraftingPlugin.getBenchRecipes(this.bench.getType(), this.bench.getId());
/* 740 */     if (recipes.isEmpty()) {
/* 741 */       clearRecipe();
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 746 */     ObjectArrayList<CraftingRecipe> objectArrayList = new ObjectArrayList();
/* 747 */     for (CraftingRecipe craftingRecipe : recipes) {
/* 748 */       if (craftingRecipe.isRestrictedByBenchTierLevel(this.bench.getId(), getTierLevel()))
/*     */         continue; 
/* 750 */       MaterialQuantity[] input = craftingRecipe.getInput();
/* 751 */       int matches = 0;
/*     */       
/* 753 */       IntArrayList slots = new IntArrayList();
/* 754 */       for (int j = 0; j < this.inputContainer.getCapacity(); j++) {
/* 755 */         slots.add(j);
/*     */       }
/*     */       
/* 758 */       for (MaterialQuantity craftingMaterial : input) {
/* 759 */         String itemId = craftingMaterial.getItemId();
/* 760 */         String resourceTypeId = craftingMaterial.getResourceTypeId();
/* 761 */         int materialQuantity = craftingMaterial.getQuantity();
/* 762 */         BsonDocument metadata = craftingMaterial.getMetadata();
/* 763 */         MaterialQuantity material = new MaterialQuantity(itemId, resourceTypeId, null, materialQuantity, metadata);
/*     */         
/* 765 */         for (int k = 0; k < slots.size(); k++) {
/* 766 */           int i = slots.getInt(k);
/* 767 */           int out = InternalContainerUtilMaterial.testRemoveMaterialFromSlot(this.inputContainer, (short)i, material, material.getQuantity(), true);
/* 768 */           if (out == 0) {
/* 769 */             matches++;
/* 770 */             slots.removeInt(k);
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/* 776 */       if (matches == input.length) {
/* 777 */         objectArrayList.add(craftingRecipe);
/*     */       }
/*     */     } 
/*     */     
/* 781 */     if (objectArrayList.isEmpty()) {
/* 782 */       clearRecipe();
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 788 */     objectArrayList.sort(Comparator.comparingInt(o -> CraftingManager.getInputMaterials(o).size()));
/* 789 */     Collections.reverse((List<?>)objectArrayList);
/*     */     
/* 791 */     if (this.recipeId != null)
/*     */     {
/* 793 */       for (CraftingRecipe rec : objectArrayList) {
/* 794 */         if (Objects.equals(this.recipeId, rec.getId())) {
/* 795 */           LOGGER.at(Level.FINE).log("%s - Keeping existing Recipe %s %s", LazyArgs.lazy(this::getBlockPosition), this.recipeId, rec);
/* 796 */           this.recipe = rec;
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     }
/* 802 */     CraftingRecipe recipe = objectArrayList.getFirst();
/* 803 */     if (this.recipeId == null || !Objects.equals(this.recipeId, recipe.getId())) {
/* 804 */       this.inputProgress = 0.0F;
/* 805 */       sendProgress(0.0F);
/*     */     } 
/* 807 */     this.recipeId = recipe.getId();
/* 808 */     this.recipe = recipe;
/*     */     
/* 810 */     LOGGER.at(Level.FINE).log("%s - Found Recipe %s %s", LazyArgs.lazy(this::getBlockPosition), this.recipeId, this.recipe);
/*     */   }
/*     */   
/*     */   private void clearRecipe() {
/* 814 */     this.recipeId = null;
/* 815 */     this.recipe = null;
/* 816 */     this.lastConsumedFuelTotal = 0;
/* 817 */     this.inputProgress = 0.0F;
/* 818 */     sendProgress(0.0F);
/* 819 */     LOGGER.at(Level.FINE).log("%s - Cleared Recipe", LazyArgs.lazy(this::getBlockPosition));
/*     */   }
/*     */ 
/*     */   
/*     */   public void dropFuelItems(@Nonnull List<ItemStack> itemStacks) {
/* 824 */     String fuelDropItemId = this.processingBench.getFuelDropItemId();
/* 825 */     if (fuelDropItemId != null) {
/* 826 */       Item item = (Item)Item.getAssetMap().getAsset(fuelDropItemId);
/*     */ 
/*     */       
/* 829 */       int dropAmount = (int)this.fuelTime;
/* 830 */       this.fuelTime = 0.0F;
/*     */       
/* 832 */       while (dropAmount > 0) {
/* 833 */         int quantity = Math.min(dropAmount, item.getMaxStack());
/* 834 */         itemStacks.add(new ItemStack(fuelDropItemId, quantity));
/* 835 */         dropAmount -= quantity;
/*     */       } 
/*     */     } else {
/* 838 */       LOGGER.at(Level.WARNING).log("No FuelDropItemId defined for %s fuel value of %s will be lost!", this.bench.getId(), this.fuelTime);
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public CraftingRecipe getRecipe() {
/* 844 */     return this.recipe;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Map<UUID, ProcessingBenchWindow> getWindows() {
/* 849 */     return this.windows;
/*     */   }
/*     */   
/*     */   public float getInputProgress() {
/* 853 */     return this.inputProgress;
/*     */   }
/*     */   
/*     */   public void onItemChange(ItemContainer.ItemContainerChangeEvent event) {
/* 857 */     markNeedsSave();
/*     */   }
/*     */   
/*     */   public void setBlockInteractionState(@Nonnull String state, @Nonnull BlockType blockType) {
/* 861 */     getChunk().setBlockInteractionState(getBlockPosition(), blockType, state);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMarker(WorldMapManager.MarkerReference marker) {
/* 866 */     this.marker = marker;
/* 867 */     markNeedsSave();
/*     */   }
/*     */ 
/*     */   
/*     */   public void placedBy(@Nonnull Ref<EntityStore> playerRef, @Nonnull String blockTypeKey, @Nonnull BlockState blockState, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 872 */     if (blockTypeKey.equals(this.processingBench.getIconItem()) && this.processingBench.getIcon() != null) {
/* 873 */       Player playerComponent = (Player)componentAccessor.getComponent(playerRef, Player.getComponentType());
/* 874 */       assert playerComponent != null;
/*     */       
/* 876 */       TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(playerRef, TransformComponent.getComponentType());
/* 877 */       assert transformComponent != null;
/*     */       
/* 879 */       Transform transformPacket = PositionUtil.toTransformPacket(transformComponent.getTransform());
/* 880 */       transformPacket.orientation.yaw = 0.0F;
/* 881 */       transformPacket.orientation.pitch = 0.0F;
/* 882 */       transformPacket.orientation.roll = 0.0F;
/*     */       
/* 884 */       MapMarker marker = new MapMarker(this.processingBench.getIconId() + "-" + this.processingBench.getIconId(), this.processingBench.getIconName(), this.processingBench.getIcon(), transformPacket, null);
/*     */       
/* 886 */       ((MarkerBlockState)blockState).setMarker((WorldMapManager.MarkerReference)WorldMapManager.createPlayerMarker(playerRef, marker, componentAccessor));
/*     */     } 
/*     */   }
/*     */   
/*     */   private void playSound(@Nonnull World world, int soundEventIndex, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 891 */     if (soundEventIndex == 0)
/*     */       return; 
/* 893 */     Vector3i pos = getBlockPosition();
/* 894 */     SoundUtil.playSoundEvent3d(soundEventIndex, SoundCategory.SFX, pos.x + 0.5D, pos.y + 0.5D, pos.z + 0.5D, componentAccessor);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onTierLevelChange() {
/* 899 */     super.onTierLevelChange();
/* 900 */     setupSlots();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\crafting\state\ProcessingBenchState.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */