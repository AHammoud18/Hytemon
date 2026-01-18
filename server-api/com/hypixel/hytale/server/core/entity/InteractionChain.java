/*     */ package com.hypixel.hytale.server.core.entity;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.protocol.ForkedChainId;
/*     */ import com.hypixel.hytale.protocol.InteractionChainData;
/*     */ import com.hypixel.hytale.protocol.InteractionCooldown;
/*     */ import com.hypixel.hytale.protocol.InteractionState;
/*     */ import com.hypixel.hytale.protocol.InteractionSyncData;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.protocol.packets.interaction.SyncInteractionChain;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.RootInteraction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.operation.Operation;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.longs.Long2LongMap;
/*     */ import it.unimi.dsi.fastutil.longs.Long2LongOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class InteractionChain implements ChainSyncStorage {
/*  28 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */   
/*  30 */   private static final long NULL_FORK_ID = forkedIdToIndex(new ForkedChainId(-1, 2147483647, null));
/*     */   
/*     */   private final InteractionType type;
/*     */   private InteractionType baseType;
/*     */   private final InteractionChainData chainData;
/*     */   private int chainId;
/*     */   private final ForkedChainId forkedChainId;
/*     */   private final ForkedChainId baseForkedChainId;
/*     */   private boolean predicted;
/*     */   private final InteractionContext context;
/*     */   @Nonnull
/*  41 */   private final Long2ObjectMap<InteractionChain> forkedChains = (Long2ObjectMap<InteractionChain>)new Long2ObjectOpenHashMap();
/*     */   
/*     */   @Nonnull
/*  44 */   private final Long2ObjectMap<TempChain> tempForkedChainData = (Long2ObjectMap<TempChain>)new Long2ObjectOpenHashMap();
/*     */   
/*     */   @Nonnull
/*  47 */   private final Long2LongMap forkedChainsMap = (Long2LongMap)new Long2LongOpenHashMap();
/*     */   
/*     */   @Nonnull
/*  50 */   private final List<InteractionChain> newForks = (List<InteractionChain>)new ObjectArrayList();
/*     */   
/*     */   @Nonnull
/*     */   private final RootInteraction initialRootInteraction;
/*     */   
/*     */   private RootInteraction rootInteraction;
/*  56 */   private int operationCounter = 0;
/*     */   @Nonnull
/*  58 */   private final List<CallState> callStack = (List<CallState>)new ObjectArrayList();
/*     */ 
/*     */   
/*  61 */   private int simulatedCallStack = 0;
/*     */ 
/*     */   
/*     */   private final boolean requiresClient;
/*     */   
/*  66 */   private int simulatedOperationCounter = 0;
/*     */ 
/*     */   
/*     */   private RootInteraction simulatedRootInteraction;
/*     */ 
/*     */   
/*  72 */   private int operationIndex = 0;
/*  73 */   private int operationIndexOffset = 0;
/*     */ 
/*     */   
/*  76 */   private int clientOperationIndex = 0;
/*     */   @Nonnull
/*  78 */   private final List<InteractionEntry> interactions = (List<InteractionEntry>)new ObjectArrayList();
/*     */   
/*     */   @Nonnull
/*  81 */   private final List<InteractionSyncData> tempSyncData = (List<InteractionSyncData>)new ObjectArrayList();
/*     */ 
/*     */   
/*  84 */   private int tempSyncDataOffset = 0;
/*     */   
/*  86 */   private long timestamp = System.nanoTime();
/*     */   
/*     */   private long waitingForServerFinished;
/*     */   private long waitingForClientFinished;
/*  90 */   private InteractionState clientState = InteractionState.NotFinished;
/*  91 */   private InteractionState serverState = InteractionState.NotFinished;
/*  92 */   private InteractionState finalState = InteractionState.Finished;
/*     */   
/*     */   @Nullable
/*     */   private Runnable onCompletion;
/*     */   
/*     */   private boolean sentInitial;
/*     */   
/*     */   private boolean desynced;
/*     */   
/*     */   private float timeShift;
/*     */   
/*     */   private boolean firstRun = true;
/*     */   private boolean isFirstRun = true;
/*     */   private boolean completed = false;
/*     */   private boolean preTicked;
/*     */   boolean skipChainOnClick;
/*     */   
/*     */   public InteractionChain(InteractionType type, InteractionContext context, InteractionChainData chainData, @Nonnull RootInteraction rootInteraction, @Nullable Runnable onCompletion, boolean requiresClient) {
/* 110 */     this(null, null, type, context, chainData, rootInteraction, onCompletion, requiresClient);
/*     */   }
/*     */   
/*     */   public InteractionChain(ForkedChainId forkedChainId, ForkedChainId baseForkedChainId, InteractionType type, InteractionContext context, InteractionChainData chainData, @Nonnull RootInteraction rootInteraction, @Nullable Runnable onCompletion, boolean requiresClient) {
/* 114 */     this.type = this.baseType = type;
/* 115 */     this.context = context;
/* 116 */     this.chainData = chainData;
/* 117 */     this.forkedChainId = forkedChainId;
/* 118 */     this.baseForkedChainId = baseForkedChainId;
/* 119 */     this.onCompletion = onCompletion;
/* 120 */     this.initialRootInteraction = this.rootInteraction = this.simulatedRootInteraction = rootInteraction;
/*     */ 
/*     */     
/* 123 */     this.requiresClient = (requiresClient || rootInteraction.needsRemoteSync());
/*     */     
/* 125 */     this.forkedChainsMap.defaultReturnValue(NULL_FORK_ID);
/*     */   }
/*     */   
/*     */   public InteractionType getType() {
/* 129 */     return this.type;
/*     */   }
/*     */   
/*     */   public int getChainId() {
/* 133 */     return this.chainId;
/*     */   }
/*     */   
/*     */   public ForkedChainId getForkedChainId() {
/* 137 */     return this.forkedChainId;
/*     */   }
/*     */   
/*     */   public ForkedChainId getBaseForkedChainId() {
/* 141 */     return this.baseForkedChainId;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public RootInteraction getInitialRootInteraction() {
/* 146 */     return this.initialRootInteraction;
/*     */   }
/*     */   
/*     */   public boolean isPredicted() {
/* 150 */     return this.predicted;
/*     */   }
/*     */   
/*     */   public InteractionContext getContext() {
/* 154 */     return this.context;
/*     */   }
/*     */   
/*     */   public InteractionChainData getChainData() {
/* 158 */     return this.chainData;
/*     */   }
/*     */   
/*     */   public InteractionState getServerState() {
/* 162 */     return this.serverState;
/*     */   }
/*     */   
/*     */   public boolean requiresClient() {
/* 166 */     return this.requiresClient;
/*     */   }
/*     */   
/*     */   public RootInteraction getRootInteraction() {
/* 170 */     return this.rootInteraction;
/*     */   }
/*     */   
/*     */   public RootInteraction getSimulatedRootInteraction() {
/* 174 */     return this.simulatedRootInteraction;
/*     */   }
/*     */   
/*     */   public int getOperationCounter() {
/* 178 */     return this.operationCounter;
/*     */   }
/*     */   
/*     */   public void setOperationCounter(int operationCounter) {
/* 182 */     this.operationCounter = operationCounter;
/*     */   }
/*     */   
/*     */   public int getSimulatedOperationCounter() {
/* 186 */     return this.simulatedOperationCounter;
/*     */   }
/*     */   
/*     */   public void setSimulatedOperationCounter(int simulatedOperationCounter) {
/* 190 */     this.simulatedOperationCounter = simulatedOperationCounter;
/*     */   }
/*     */   
/*     */   public boolean wasPreTicked() {
/* 194 */     return this.preTicked;
/*     */   }
/*     */   
/*     */   public void setPreTicked(boolean preTicked) {
/* 198 */     this.preTicked = preTicked;
/*     */   }
/*     */   
/*     */   public int getOperationIndex() {
/* 202 */     return this.operationIndex;
/*     */   }
/*     */   
/*     */   public void nextOperationIndex() {
/* 206 */     this.operationIndex++;
/* 207 */     this.clientOperationIndex++;
/*     */   }
/*     */   
/*     */   public int getClientOperationIndex() {
/* 211 */     return this.clientOperationIndex;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public InteractionChain findForkedChain(@Nonnull ForkedChainId chainId, @Nullable InteractionChainData data) {
/* 216 */     long id = forkedIdToIndex(chainId);
/* 217 */     long altId = this.forkedChainsMap.get(id);
/* 218 */     if (altId != NULL_FORK_ID) {
/* 219 */       id = altId;
/*     */     }
/* 221 */     InteractionChain chain = (InteractionChain)this.forkedChains.get(id);
/* 222 */     if (chain != null || chainId.subIndex >= 0 || data == null) {
/* 223 */       return chain;
/*     */     }
/*     */     
/* 226 */     InteractionEntry entry = getInteraction(chainId.entryIndex);
/* 227 */     if (entry == null) return null; 
/* 228 */     int rootId = (entry.getServerState()).rootInteraction;
/* 229 */     int opCounter = (entry.getServerState()).operationCounter;
/*     */     
/* 231 */     RootInteraction root = (RootInteraction)RootInteraction.getAssetMap().getAsset(rootId);
/* 232 */     Operation op = root.getOperation(opCounter).getInnerOperation();
/* 233 */     if (op instanceof Interaction) { Interaction interaction = (Interaction)op;
/* 234 */       this.context.initEntry(this, entry, null);
/* 235 */       chain = interaction.mapForkChain(this.context, data);
/* 236 */       this.context.deinitEntry(this, entry, null);
/* 237 */       if (chain != null) {
/* 238 */         this.forkedChainsMap.put(id, forkedIdToIndex(chain.getBaseForkedChainId()));
/*     */       }
/* 240 */       return chain; }
/*     */     
/* 242 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InteractionChain getForkedChain(@Nonnull ForkedChainId chainId) {
/* 252 */     long id = forkedIdToIndex(chainId);
/* 253 */     if (chainId.subIndex < 0) {
/* 254 */       long altId = this.forkedChainsMap.get(id);
/* 255 */       if (altId != NULL_FORK_ID) id = altId; 
/*     */     } 
/* 257 */     return (InteractionChain)this.forkedChains.get(id);
/*     */   }
/*     */   
/*     */   public void putForkedChain(@Nonnull ForkedChainId chainId, @Nonnull InteractionChain chain) {
/* 261 */     this.newForks.add(chain);
/* 262 */     this.forkedChains.put(forkedIdToIndex(chainId), chain);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public TempChain getTempForkedChain(@Nonnull ForkedChainId chainId) {
/* 267 */     InteractionEntry entry = getInteraction(chainId.entryIndex);
/* 268 */     if (entry != null) {
/* 269 */       if (chainId.subIndex < entry.getNextForkId()) {
/* 270 */         return null;
/*     */       }
/* 272 */     } else if (chainId.entryIndex < this.operationIndexOffset) {
/* 273 */       return null;
/*     */     } 
/* 275 */     return (TempChain)this.tempForkedChainData.computeIfAbsent(forkedIdToIndex(chainId), i -> new TempChain());
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   TempChain removeTempForkedChain(@Nonnull ForkedChainId chainId, InteractionChain forkChain) {
/* 280 */     long id = forkedIdToIndex(chainId);
/* 281 */     long altId = this.forkedChainsMap.get(id);
/* 282 */     if (altId != NULL_FORK_ID) {
/* 283 */       id = altId;
/*     */     }
/* 285 */     TempChain found = (TempChain)this.tempForkedChainData.remove(id);
/* 286 */     if (found != null) return found;
/*     */     
/* 288 */     InteractionEntry iEntry = this.context.getEntry();
/* 289 */     RootInteraction root = (RootInteraction)RootInteraction.getAssetMap().getAsset((iEntry.getState()).rootInteraction);
/* 290 */     Operation op = root.getOperation((iEntry.getState()).operationCounter).getInnerOperation();
/* 291 */     if (op instanceof Interaction) { Interaction interaction = (Interaction)op;
/* 292 */       ObjectIterator<Long2ObjectMap.Entry<TempChain>> it = Long2ObjectMaps.fastIterator(getTempForkedChainData());
/* 293 */       while (it.hasNext()) {
/* 294 */         Long2ObjectMap.Entry<TempChain> entry = (Long2ObjectMap.Entry<TempChain>)it.next();
/* 295 */         TempChain tempChain = (TempChain)entry.getValue();
/* 296 */         if (tempChain.baseForkedChainId == null)
/* 297 */           continue;  int entryId = tempChain.baseForkedChainId.entryIndex;
/* 298 */         if (entryId != iEntry.getIndex())
/* 299 */           continue;  InteractionChain chain = interaction.mapForkChain(getContext(), tempChain.chainData);
/* 300 */         if (chain != null) {
/* 301 */           this.forkedChainsMap.put(forkedIdToIndex(tempChain.baseForkedChainId), forkedIdToIndex(chain.getBaseForkedChainId()));
/*     */         }
/* 303 */         if (chain == forkChain) {
/* 304 */           it.remove();
/* 305 */           return tempChain;
/*     */         } 
/*     */       }  }
/*     */     
/* 309 */     return null;
/*     */   }
/*     */   
/*     */   public boolean hasSentInitial() {
/* 313 */     return this.sentInitial;
/*     */   }
/*     */   
/*     */   public void setSentInitial(boolean sentInitial) {
/* 317 */     this.sentInitial = sentInitial;
/*     */   }
/*     */   
/*     */   public float getTimeShift() {
/* 321 */     return this.timeShift;
/*     */   }
/*     */   
/*     */   public void setTimeShift(float timeShift) {
/* 325 */     this.timeShift = timeShift;
/*     */   }
/*     */   
/*     */   public boolean consumeFirstRun() {
/* 329 */     this.isFirstRun = this.firstRun;
/* 330 */     this.firstRun = false;
/* 331 */     return this.isFirstRun;
/*     */   }
/*     */   
/*     */   public boolean isFirstRun() {
/* 335 */     return this.isFirstRun;
/*     */   }
/*     */   
/*     */   public void setFirstRun(boolean firstRun) {
/* 339 */     this.isFirstRun = firstRun;
/*     */   }
/*     */   
/*     */   public int getCallDepth() {
/* 343 */     return this.callStack.size();
/*     */   }
/*     */   
/*     */   public int getSimulatedCallDepth() {
/* 347 */     return this.simulatedCallStack;
/*     */   }
/*     */   
/*     */   public void pushRoot(RootInteraction nextInteraction, boolean simulate) {
/* 351 */     if (simulate) {
/* 352 */       this.simulatedRootInteraction = nextInteraction;
/* 353 */       this.simulatedOperationCounter = 0;
/* 354 */       this.simulatedCallStack++;
/*     */       
/*     */       return;
/*     */     } 
/* 358 */     this.callStack.add(new CallState(this.rootInteraction, this.operationCounter));
/*     */     
/* 360 */     this.operationCounter = 0;
/* 361 */     this.rootInteraction = nextInteraction;
/*     */   }
/*     */   
/*     */   public void popRoot() {
/* 365 */     CallState state = this.callStack.removeLast();
/* 366 */     this.rootInteraction = state.rootInteraction;
/* 367 */     this.operationCounter = state.operationCounter + 1;
/*     */ 
/*     */     
/* 370 */     this.simulatedRootInteraction = this.rootInteraction;
/* 371 */     this.simulatedOperationCounter = this.operationCounter;
/* 372 */     this.simulatedCallStack--;
/*     */   }
/*     */   
/*     */   public float getTimeInSeconds() {
/* 376 */     if (this.timestamp == 0L) return 0.0F; 
/* 377 */     long diff = System.nanoTime() - this.timestamp;
/* 378 */     return (float)diff / 1.0E9F;
/*     */   }
/*     */   
/*     */   public void setOnCompletion(Runnable onCompletion) {
/* 382 */     this.onCompletion = onCompletion;
/*     */   }
/*     */   
/*     */   void onCompletion(CooldownHandler cooldownHandler, boolean isRemote) {
/* 386 */     if (this.completed)
/* 387 */       return;  this.completed = true;
/*     */     
/* 389 */     if (this.onCompletion != null) {
/* 390 */       this.onCompletion.run();
/* 391 */       this.onCompletion = null;
/*     */     } 
/*     */     
/* 394 */     if (isRemote) {
/* 395 */       InteractionCooldown cooldown = this.initialRootInteraction.getCooldown();
/* 396 */       String cooldownId = this.initialRootInteraction.getId();
/*     */       
/* 398 */       if (cooldown != null && 
/* 399 */         cooldown.cooldownId != null) cooldownId = cooldown.cooldownId;
/*     */ 
/*     */       
/* 402 */       CooldownHandler.Cooldown cooldownTracker = cooldownHandler.getCooldown(cooldownId);
/* 403 */       if (cooldownTracker != null)
/*     */       {
/*     */         
/* 406 */         cooldownTracker.tick(0.016666668F);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   void updateServerState() {
/* 412 */     if (this.serverState != InteractionState.NotFinished) {
/*     */       return;
/*     */     }
/* 415 */     if (this.operationCounter >= this.rootInteraction.getOperationMax()) {
/* 416 */       this.serverState = this.finalState;
/*     */       return;
/*     */     } 
/* 419 */     InteractionEntry entry = getOrCreateInteractionEntry(this.operationIndex);
/* 420 */     switch ((entry.getServerState()).state) { case NotFinished: case Finished: default: break; }  this.serverState = 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 426 */       InteractionState.Failed;
/*     */   }
/*     */ 
/*     */   
/*     */   void updateSimulatedState() {
/* 431 */     if (this.clientState != InteractionState.NotFinished) {
/*     */       return;
/*     */     }
/* 434 */     if (this.simulatedOperationCounter >= this.rootInteraction.getOperationMax()) {
/* 435 */       this.clientState = this.finalState;
/*     */       return;
/*     */     } 
/* 438 */     InteractionEntry entry = getOrCreateInteractionEntry(this.clientOperationIndex);
/* 439 */     switch ((entry.getSimulationState()).state) { case NotFinished: case Finished: default: break; }  this.clientState = 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 445 */       InteractionState.Failed;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public InteractionState getClientState() {
/* 451 */     return this.clientState;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setClientState(InteractionState state) {
/* 456 */     this.clientState = state;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public InteractionEntry getOrCreateInteractionEntry(int index) {
/* 461 */     int oIndex = index - this.operationIndexOffset;
/* 462 */     if (oIndex < 0) throw new IllegalArgumentException("Trying to access removed interaction entry"); 
/* 463 */     InteractionEntry entry = (oIndex < this.interactions.size()) ? this.interactions.get(oIndex) : null;
/* 464 */     if (entry == null) {
/* 465 */       if (oIndex != this.interactions.size()) {
/* 466 */         throw new IllegalArgumentException("Trying to add interaction entry at a weird location: " + oIndex + " " + this.interactions.size());
/*     */       }
/* 468 */       entry = new InteractionEntry(index, this.operationCounter, RootInteraction.getRootInteractionIdOrUnknown(this.rootInteraction.getId()));
/* 469 */       this.interactions.add(entry);
/*     */     } 
/* 471 */     return entry;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public InteractionEntry getInteraction(int index) {
/* 477 */     index -= this.operationIndexOffset;
/* 478 */     if (index < 0 || index >= this.interactions.size()) return null; 
/* 479 */     return this.interactions.get(index);
/*     */   }
/*     */   
/*     */   public void removeInteractionEntry(@Nonnull InteractionManager interactionManager, int index) {
/* 483 */     int oIndex = index - this.operationIndexOffset;
/* 484 */     if (oIndex != 0) throw new IllegalArgumentException("Trying to remove out of order"); 
/* 485 */     InteractionEntry entry = this.interactions.remove(oIndex);
/* 486 */     this.operationIndexOffset++;
/*     */ 
/*     */     
/* 489 */     this.tempForkedChainData.values().removeIf(fork -> {
/*     */           if (fork.baseForkedChainId.entryIndex != entry.getIndex())
/*     */             return false; 
/*     */           interactionManager.sendCancelPacket(getChainId(), fork.forkedChainId);
/*     */           return true;
/*     */         });
/*     */   }
/*     */   
/*     */   public void putInteractionSyncData(int index, InteractionSyncData data) {
/* 498 */     index -= this.tempSyncDataOffset;
/* 499 */     if (index < 0) {
/* 500 */       LOGGER.at(Level.SEVERE).log("Attempted to store sync data at %d. Offset: %d, Size: %d", Integer.valueOf(index + this.tempSyncDataOffset), Integer.valueOf(this.tempSyncDataOffset), Integer.valueOf(this.tempSyncData.size()));
/* 501 */     } else if (index < this.tempSyncData.size()) {
/* 502 */       this.tempSyncData.set(index, data);
/* 503 */     } else if (index == this.tempSyncData.size()) {
/* 504 */       this.tempSyncData.add(data);
/*     */     } else {
/*     */       
/* 507 */       LOGGER.at(Level.WARNING).log("Temp sync data sent out of order: " + index + " " + this.tempSyncData.size());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearInteractionSyncData(int operationIndex) {
/* 518 */     int tempIdx = operationIndex - this.tempSyncDataOffset;
/* 519 */     if (!this.tempSyncData.isEmpty()) {
/* 520 */       int end = this.tempSyncData.size() - 1;
/* 521 */       while (end >= tempIdx && end >= 0) {
/* 522 */         this.tempSyncData.remove(end);
/* 523 */         end--;
/*     */       } 
/*     */     } 
/*     */     
/* 527 */     int idx = operationIndex - this.operationIndexOffset;
/* 528 */     for (int i = Math.max(idx, 0); i < this.interactions.size(); i++) {
/* 529 */       ((InteractionEntry)this.interactions.get(i)).setClientState(null);
/*     */     }
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public InteractionSyncData removeInteractionSyncData(int index) {
/* 535 */     index -= this.tempSyncDataOffset;
/* 536 */     if (index != 0) return null; 
/* 537 */     if (this.tempSyncData.isEmpty()) {
/* 538 */       return null;
/*     */     }
/* 540 */     if (this.tempSyncData.get(index) == null) return null; 
/* 541 */     this.tempSyncDataOffset++;
/* 542 */     return this.tempSyncData.remove(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateSyncPosition(int index) {
/* 547 */     if (this.tempSyncDataOffset == index) {
/* 548 */       this.tempSyncDataOffset = index + 1;
/* 549 */     } else if (index > this.tempSyncDataOffset) {
/* 550 */       throw new IllegalArgumentException("Temp sync data sent out of order: " + index + " " + this.tempSyncData.size());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSyncDataOutOfOrder(int index) {
/* 556 */     return (index > this.tempSyncDataOffset + this.tempSyncData.size());
/*     */   }
/*     */ 
/*     */   
/*     */   public void syncFork(@Nonnull Ref<EntityStore> ref, @Nonnull InteractionManager manager, @Nonnull SyncInteractionChain packet) {
/* 561 */     ForkedChainId baseId = packet.forkedId;
/* 562 */     for (; baseId.forkedId != null; baseId = baseId.forkedId);
/*     */     
/* 564 */     InteractionChain fork = findForkedChain(baseId, packet.data);
/* 565 */     if (fork != null) {
/* 566 */       manager.sync(ref, fork, packet);
/*     */     } else {
/* 568 */       TempChain temp = getTempForkedChain(baseId);
/* 569 */       if (temp == null)
/* 570 */         return;  temp.setForkedChainId(packet.forkedId);
/* 571 */       temp.setBaseForkedChainId(baseId);
/* 572 */       temp.setChainData(packet.data);
/* 573 */       manager.sync(ref, temp, packet);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void copyTempFrom(@Nonnull TempChain temp) {
/* 578 */     setClientState(temp.clientState);
/* 579 */     this.tempSyncData.addAll(temp.tempSyncData);
/* 580 */     getTempForkedChainData().putAll((Map)temp.tempForkedChainData);
/*     */   }
/*     */   
/*     */   private static long forkedIdToIndex(@Nonnull ForkedChainId chainId) {
/* 584 */     return chainId.entryIndex << 32L | chainId.subIndex & 0xFFFFFFFFL;
/*     */   }
/*     */   
/*     */   public void setChainId(int chainId) {
/* 588 */     this.chainId = chainId;
/*     */   }
/*     */   
/*     */   public InteractionType getBaseType() {
/* 592 */     return this.baseType;
/*     */   }
/*     */   
/*     */   public void setBaseType(InteractionType baseType) {
/* 596 */     this.baseType = baseType;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Long2ObjectMap<InteractionChain> getForkedChains() {
/* 601 */     return this.forkedChains;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Long2ObjectMap<TempChain> getTempForkedChainData() {
/* 606 */     return this.tempForkedChainData;
/*     */   }
/*     */   
/*     */   public long getTimestamp() {
/* 610 */     return this.timestamp;
/*     */   }
/*     */   
/*     */   public void setTimestamp(long timestamp) {
/* 614 */     this.timestamp = timestamp;
/*     */   }
/*     */   
/*     */   public long getWaitingForServerFinished() {
/* 618 */     return this.waitingForServerFinished;
/*     */   }
/*     */   
/*     */   public void setWaitingForServerFinished(long waitingForServerFinished) {
/* 622 */     this.waitingForServerFinished = waitingForServerFinished;
/*     */   }
/*     */   
/*     */   public long getWaitingForClientFinished() {
/* 626 */     return this.waitingForClientFinished;
/*     */   }
/*     */   
/*     */   public void setWaitingForClientFinished(long waitingForClientFinished) {
/* 630 */     this.waitingForClientFinished = waitingForClientFinished;
/*     */   }
/*     */   
/*     */   public void setServerState(InteractionState serverState) {
/* 634 */     this.serverState = serverState;
/*     */   }
/*     */   
/*     */   public InteractionState getFinalState() {
/* 638 */     return this.finalState;
/*     */   }
/*     */   
/*     */   public void setFinalState(InteractionState finalState) {
/* 642 */     this.finalState = finalState;
/*     */   }
/*     */   
/*     */   void setPredicted(boolean predicted) {
/* 646 */     this.predicted = predicted;
/*     */   }
/*     */   
/*     */   public void flagDesync() {
/* 650 */     this.desynced = true;
/* 651 */     this.forkedChains.forEach((k, c) -> c.flagDesync());
/*     */   }
/*     */   
/*     */   public boolean isDesynced() {
/* 655 */     return this.desynced;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public List<InteractionChain> getNewForks() {
/* 660 */     return this.newForks;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 666 */     return "InteractionChain{type=" + String.valueOf(this.type) + ", chainData=" + String.valueOf(this.chainData) + ", chainId=" + this.chainId + ", forkedChainId=" + String.valueOf(this.forkedChainId) + ", predicted=" + this.predicted + ", context=" + String.valueOf(this.context) + ", forkedChains=" + String.valueOf(this.forkedChains) + ", tempForkedChainData=" + String.valueOf(this.tempForkedChainData) + ", initialRootInteraction=" + String.valueOf(this.initialRootInteraction) + ", rootInteraction=" + String.valueOf(this.rootInteraction) + ", operationCounter=" + this.operationCounter + ", callStack=" + String.valueOf(this.callStack) + ", simulatedCallStack=" + this.simulatedCallStack + ", requiresClient=" + this.requiresClient + ", simulatedOperationCounter=" + this.simulatedOperationCounter + ", simulatedRootInteraction=" + String.valueOf(this.simulatedRootInteraction) + ", operationIndex=" + this.operationIndex + ", operationIndexOffset=" + this.operationIndexOffset + ", clientOperationIndex=" + this.clientOperationIndex + ", interactions=" + String.valueOf(this.interactions) + ", tempSyncData=" + String.valueOf(this.tempSyncData) + ", tempSyncDataOffset=" + this.tempSyncDataOffset + ", timestamp=" + this.timestamp + ", waitingForServerFinished=" + this.waitingForServerFinished + ", waitingForClientFinished=" + this.waitingForClientFinished + ", clientState=" + String.valueOf(this.clientState) + ", serverState=" + String.valueOf(this.serverState) + ", onCompletion=" + String.valueOf(this.onCompletion) + ", sentInitial=" + this.sentInitial + ", desynced=" + this.desynced + ", timeShift=" + this.timeShift + ", firstRun=" + this.firstRun + ", skipChainOnClick=" + this.skipChainOnClick + "}";
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class TempChain
/*     */     implements ChainSyncStorage
/*     */   {
/* 704 */     final Long2ObjectMap<TempChain> tempForkedChainData = (Long2ObjectMap<TempChain>)new Long2ObjectOpenHashMap();
/* 705 */     final List<InteractionSyncData> tempSyncData = (List<InteractionSyncData>)new ObjectArrayList();
/*     */     ForkedChainId forkedChainId;
/* 707 */     InteractionState clientState = InteractionState.NotFinished;
/*     */     ForkedChainId baseForkedChainId;
/*     */     InteractionChainData chainData;
/*     */     
/*     */     @Nonnull
/*     */     public TempChain getOrCreateTempForkedChain(@Nonnull ForkedChainId chainId) {
/* 713 */       return (TempChain)this.tempForkedChainData.computeIfAbsent(InteractionChain.forkedIdToIndex(chainId), i -> new TempChain());
/*     */     }
/*     */ 
/*     */     
/*     */     public InteractionState getClientState() {
/* 718 */       return this.clientState;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setClientState(InteractionState state) {
/* 723 */       this.clientState = state;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public InteractionEntry getInteraction(int index) {
/* 729 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public void putInteractionSyncData(int index, InteractionSyncData data) {
/* 734 */       if (index < this.tempSyncData.size()) {
/* 735 */         this.tempSyncData.set(index, data);
/* 736 */       } else if (index == this.tempSyncData.size()) {
/* 737 */         this.tempSyncData.add(data);
/*     */       } else {
/* 739 */         throw new IllegalArgumentException("Temp sync data sent out of order: " + index + " " + this.tempSyncData.size());
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void updateSyncPosition(int index) {}
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isSyncDataOutOfOrder(int index) {
/* 750 */       return (index > this.tempSyncData.size());
/*     */     }
/*     */ 
/*     */     
/*     */     public void syncFork(@Nonnull Ref<EntityStore> ref, @Nonnull InteractionManager manager, @Nonnull SyncInteractionChain packet) {
/* 755 */       ForkedChainId baseId = packet.forkedId;
/* 756 */       for (; baseId.forkedId != null; baseId = baseId.forkedId);
/* 757 */       TempChain temp = getOrCreateTempForkedChain(baseId);
/* 758 */       temp.setForkedChainId(packet.forkedId);
/* 759 */       temp.setBaseForkedChainId(baseId);
/* 760 */       temp.setChainData(packet.data);
/* 761 */       manager.sync(ref, temp, packet);
/*     */     }
/*     */ 
/*     */     
/*     */     public void clearInteractionSyncData(int index) {
/* 766 */       int end = this.tempSyncData.size() - 1;
/* 767 */       while (end >= index) {
/* 768 */         this.tempSyncData.remove(end);
/* 769 */         end--;
/*     */       } 
/*     */     }
/*     */     
/*     */     public InteractionChainData getChainData() {
/* 774 */       return this.chainData;
/*     */     }
/*     */     
/*     */     public void setChainData(InteractionChainData chainData) {
/* 778 */       this.chainData = chainData;
/*     */     }
/*     */     
/*     */     public ForkedChainId getBaseForkedChainId() {
/* 782 */       return this.baseForkedChainId;
/*     */     }
/*     */     
/*     */     public void setBaseForkedChainId(ForkedChainId baseForkedChainId) {
/* 786 */       this.baseForkedChainId = baseForkedChainId;
/*     */     }
/*     */     
/*     */     public void setForkedChainId(ForkedChainId forkedChainId) {
/* 790 */       this.forkedChainId = forkedChainId;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 796 */       return "TempChain{tempForkedChainData=" + String.valueOf(this.tempForkedChainData) + ", tempSyncData=" + String.valueOf(this.tempSyncData) + ", clientState=" + String.valueOf(this.clientState) + "}";
/*     */     }
/*     */   } private static final class CallState extends Record { private final RootInteraction rootInteraction; private final int operationCounter; public final String toString() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/server/core/entity/InteractionChain$CallState;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #804	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/server/core/entity/InteractionChain$CallState;
/*     */     }
/*     */     public final int hashCode() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/server/core/entity/InteractionChain$CallState;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #804	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/server/core/entity/InteractionChain$CallState;
/*     */     }
/* 804 */     private CallState(RootInteraction rootInteraction, int operationCounter) { this.rootInteraction = rootInteraction; this.operationCounter = operationCounter; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/server/core/entity/InteractionChain$CallState;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #804	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lcom/hypixel/hytale/server/core/entity/InteractionChain$CallState;
/* 804 */       //   0	8	1	o	Ljava/lang/Object; } public RootInteraction rootInteraction() { return this.rootInteraction; } public int operationCounter() { return this.operationCounter; }
/*     */      }
/*     */ 
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\InteractionChain.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */