/*      */ package com.hypixel.hytale.server.core.entity;
/*      */ 
/*      */ import com.hypixel.hytale.common.util.FormatUtil;
/*      */ import com.hypixel.hytale.common.util.ListUtil;
/*      */ import com.hypixel.hytale.component.CommandBuffer;
/*      */ import com.hypixel.hytale.component.Component;
/*      */ import com.hypixel.hytale.component.ComponentAccessor;
/*      */ import com.hypixel.hytale.component.Ref;
/*      */ import com.hypixel.hytale.function.function.TriFunction;
/*      */ import com.hypixel.hytale.logger.HytaleLogger;
/*      */ import com.hypixel.hytale.math.vector.Vector4d;
/*      */ import com.hypixel.hytale.protocol.BlockPosition;
/*      */ import com.hypixel.hytale.protocol.ForkedChainId;
/*      */ import com.hypixel.hytale.protocol.GameMode;
/*      */ import com.hypixel.hytale.protocol.InteractionChainData;
/*      */ import com.hypixel.hytale.protocol.InteractionCooldown;
/*      */ import com.hypixel.hytale.protocol.InteractionState;
/*      */ import com.hypixel.hytale.protocol.InteractionSyncData;
/*      */ import com.hypixel.hytale.protocol.InteractionType;
/*      */ import com.hypixel.hytale.protocol.Packet;
/*      */ import com.hypixel.hytale.protocol.RootInteractionSettings;
/*      */ import com.hypixel.hytale.protocol.Vector3f;
/*      */ import com.hypixel.hytale.protocol.WaitForDataFrom;
/*      */ import com.hypixel.hytale.protocol.packets.interaction.CancelInteractionChain;
/*      */ import com.hypixel.hytale.protocol.packets.interaction.SyncInteractionChain;
/*      */ import com.hypixel.hytale.protocol.packets.inventory.SetActiveSlot;
/*      */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*      */ import com.hypixel.hytale.server.core.inventory.Inventory;
/*      */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*      */ import com.hypixel.hytale.server.core.io.handlers.game.GamePacketHandler;
/*      */ import com.hypixel.hytale.server.core.modules.interaction.IInteractionSimulationHandler;
/*      */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*      */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
/*      */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.InteractionTypeUtils;
/*      */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.RootInteraction;
/*      */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.data.Collector;
/*      */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.data.CollectorTag;
/*      */ import com.hypixel.hytale.server.core.modules.interaction.interaction.operation.Operation;
/*      */ import com.hypixel.hytale.server.core.modules.time.TimeResource;
/*      */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*      */ import com.hypixel.hytale.server.core.universe.world.World;
/*      */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*      */ import com.hypixel.hytale.server.core.util.UUIDUtil;
/*      */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*      */ import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
/*      */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectList;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectListIterator;
/*      */ import java.util.Arrays;
/*      */ import java.util.Deque;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Objects;
/*      */ import java.util.UUID;
/*      */ import java.util.concurrent.TimeUnit;
/*      */ import java.util.function.Predicate;
/*      */ import java.util.logging.Level;
/*      */ import javax.annotation.Nonnull;
/*      */ import javax.annotation.Nullable;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class InteractionManager
/*      */   implements Component<EntityStore>
/*      */ {
/*      */   public static final double MAX_REACH_DISTANCE = 8.0D;
/*  199 */   public static final float[] DEFAULT_CHARGE_TIMES = new float[] { 0.0F };
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  204 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*  209 */   private final Int2ObjectMap<InteractionChain> chains = (Int2ObjectMap<InteractionChain>)new Int2ObjectOpenHashMap();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*  216 */   private final Int2ObjectMap<InteractionChain> unmodifiableChains = Int2ObjectMaps.unmodifiable(this.chains);
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*  221 */   private final CooldownHandler cooldownHandler = new CooldownHandler();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   private final LivingEntity entity;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private final PlayerRef playerRef;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean hasRemoteClient;
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   private final IInteractionSimulationHandler interactionSimulationHandler;
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*  247 */   private final ObjectList<InteractionSyncData> tempSyncDataList = (ObjectList<InteractionSyncData>)new ObjectArrayList();
/*      */   
/*      */   private int lastServerChainId;
/*      */   
/*      */   private int lastClientChainId;
/*      */   
/*      */   private long packetQueueTime;
/*      */   
/*  255 */   private final float[] globalTimeShift = new float[InteractionType.VALUES.length];
/*  256 */   private final boolean[] globalTimeShiftDirty = new boolean[InteractionType.VALUES.length];
/*      */   
/*      */   private boolean timeShiftsDirty;
/*      */   
/*  260 */   private final ObjectList<SyncInteractionChain> syncPackets = (ObjectList<SyncInteractionChain>)new ObjectArrayList();
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*  265 */   private final ObjectList<InteractionChain> chainStartQueue = (ObjectList<InteractionChain>)new ObjectArrayList();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*  273 */   private final Predicate<InteractionChain> cachedTickChain = this::tickChain;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   protected CommandBuffer<EntityStore> commandBuffer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public InteractionManager(@Nonnull LivingEntity entity, @Nullable PlayerRef playerRef, @Nonnull IInteractionSimulationHandler simulationHandler) {
/*  292 */     this.entity = entity;
/*  293 */     this.playerRef = playerRef;
/*  294 */     this.hasRemoteClient = (playerRef != null);
/*  295 */     this.interactionSimulationHandler = simulationHandler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public Int2ObjectMap<InteractionChain> getChains() {
/*  303 */     return this.unmodifiableChains;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public IInteractionSimulationHandler getInteractionSimulationHandler() {
/*  311 */     return this.interactionSimulationHandler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private long getOperationTimeoutThreshold() {
/*  322 */     if (this.playerRef != null) {
/*  323 */       return this.playerRef.getPacketHandler().getOperationTimeoutThreshold();
/*      */     }
/*      */     
/*  326 */     assert this.commandBuffer != null;
/*  327 */     World world = ((EntityStore)this.commandBuffer.getExternalData()).getWorld();
/*  328 */     return (world.getTickStepNanos() / 1000000 * 10);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean waitingForClient(@Nonnull Ref<EntityStore> ref) {
/*  341 */     assert this.commandBuffer != null;
/*  342 */     Player playerComponent = (Player)this.commandBuffer.getComponent(ref, Player.getComponentType());
/*      */     
/*  344 */     if (playerComponent != null) {
/*  345 */       return playerComponent.isWaitingForClientReady();
/*      */     }
/*  347 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated(forRemoval = true)
/*      */   public void setHasRemoteClient(boolean hasRemoteClient) {
/*  358 */     this.hasRemoteClient = hasRemoteClient;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public void copyFrom(@Nonnull InteractionManager interactionManager) {
/*  370 */     this.chains.putAll((Map)interactionManager.chains);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void tick(@Nonnull Ref<EntityStore> ref, @Nonnull CommandBuffer<EntityStore> commandBuffer, float dt) {
/*  382 */     this.commandBuffer = commandBuffer;
/*  383 */     clearAllGlobalTimeShift(dt);
/*  384 */     this.cooldownHandler.tick(dt);
/*      */     
/*  386 */     for (ObjectListIterator<InteractionChain> objectListIterator = this.chainStartQueue.iterator(); objectListIterator.hasNext(); ) { InteractionChain interactionChain = objectListIterator.next();
/*  387 */       executeChain0(ref, interactionChain); }
/*      */     
/*  389 */     this.chainStartQueue.clear();
/*      */     
/*  391 */     Deque<SyncInteractionChain> packetQueue = null;
/*      */     
/*  393 */     if (this.playerRef != null) {
/*  394 */       packetQueue = ((GamePacketHandler)this.playerRef.getPacketHandler()).getInteractionPacketQueue();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  405 */     if (packetQueue != null && !packetQueue.isEmpty()) {
/*  406 */       boolean first = true;
/*  407 */       while (tryConsumePacketQueue(ref, packetQueue) || first) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  412 */         if (!this.chains.isEmpty()) {
/*  413 */           this.chains.values().removeIf(this.cachedTickChain);
/*      */         }
/*  415 */         float cooldownDt = 0.0F;
/*  416 */         for (float shift : this.globalTimeShift) {
/*  417 */           cooldownDt = Math.max(cooldownDt, shift);
/*      */         }
/*  419 */         if (cooldownDt > 0.0F) {
/*  420 */           this.cooldownHandler.tick(cooldownDt);
/*      */         }
/*  422 */         first = false;
/*      */       } 
/*  424 */       this.commandBuffer = null;
/*      */       
/*      */       return;
/*      */     } 
/*  428 */     if (!this.chains.isEmpty()) {
/*  429 */       this.chains.values().removeIf(this.cachedTickChain);
/*      */     }
/*      */     
/*  432 */     this.commandBuffer = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean tryConsumePacketQueue(@Nonnull Ref<EntityStore> ref, @Nonnull Deque<SyncInteractionChain> packetQueue) {
/*  443 */     Iterator<SyncInteractionChain> it = packetQueue.iterator();
/*      */     
/*  445 */     boolean finished = false;
/*  446 */     boolean desynced = false;
/*  447 */     int highestChainId = -1;
/*      */     
/*  449 */     boolean changed = false;
/*      */ 
/*      */     
/*  452 */     label67: while (it.hasNext()) {
/*  453 */       SyncInteractionChain packet = it.next();
/*      */       
/*  455 */       if (packet.desync) {
/*  456 */         HytaleLogger.Api context = LOGGER.at(Level.FINE);
/*  457 */         if (context.isEnabled()) context.log("Client packet flagged as desync"); 
/*  458 */         desynced = true;
/*      */       } 
/*      */       
/*  461 */       InteractionChain chain = (InteractionChain)this.chains.get(packet.chainId);
/*  462 */       if (chain != null && packet.forkedId != null) {
/*      */         
/*  464 */         ForkedChainId id = packet.forkedId;
/*  465 */         while (id != null) {
/*      */           
/*  467 */           InteractionChain subChain = chain.getForkedChain(id);
/*  468 */           if (subChain == null) {
/*      */             
/*  470 */             InteractionChain.TempChain tempChain = chain.getTempForkedChain(id);
/*  471 */             if (tempChain == null)
/*  472 */               continue label67;  tempChain.setBaseForkedChainId(id);
/*  473 */             ForkedChainId lastId = id;
/*  474 */             id = id.forkedId;
/*  475 */             while (id != null) {
/*  476 */               tempChain = tempChain.getOrCreateTempForkedChain(id);
/*  477 */               tempChain.setBaseForkedChainId(id);
/*  478 */               lastId = id;
/*  479 */               id = id.forkedId;
/*      */             } 
/*  481 */             tempChain.setForkedChainId(packet.forkedId);
/*  482 */             tempChain.setBaseForkedChainId(lastId);
/*  483 */             tempChain.setChainData(packet.data);
/*  484 */             sync(ref, tempChain, packet);
/*  485 */             changed = true;
/*  486 */             it.remove();
/*  487 */             this.packetQueueTime = 0L;
/*      */             continue label67;
/*      */           } 
/*  490 */           chain = subChain;
/*  491 */           id = id.forkedId;
/*      */         } 
/*      */       } 
/*      */       
/*  495 */       highestChainId = Math.max(highestChainId, packet.chainId);
/*      */       
/*  497 */       if (chain == null && !finished) {
/*      */         
/*  499 */         if (syncStart(ref, packet)) {
/*      */           
/*  501 */           changed = true;
/*  502 */           it.remove();
/*  503 */           this.packetQueueTime = 0L; continue;
/*      */         } 
/*  505 */         if (!waitingForClient(ref)) {
/*      */           long queuedTime;
/*      */           
/*  508 */           if (this.packetQueueTime == 0L) {
/*  509 */             this.packetQueueTime = System.nanoTime();
/*  510 */             queuedTime = 0L;
/*      */           } else {
/*  512 */             queuedTime = System.nanoTime() - this.packetQueueTime;
/*      */           } 
/*      */ 
/*      */           
/*  516 */           HytaleLogger.Api context = LOGGER.at(Level.FINE);
/*  517 */           if (context.isEnabled()) {
/*  518 */             context.log("Queued chain %d for %s", packet.chainId, FormatUtil.nanosToString(queuedTime));
/*      */           }
/*      */           
/*  521 */           if (queuedTime > TimeUnit.MILLISECONDS.toNanos(getOperationTimeoutThreshold())) {
/*  522 */             sendCancelPacket(packet.chainId, packet.forkedId);
/*  523 */             it.remove();
/*  524 */             context = LOGGER.at(Level.FINE);
/*  525 */             if (context.isEnabled()) context.log("Discarding packet due to queuing for too long: %s", packet); 
/*      */           } 
/*      */         } 
/*  528 */         if (!desynced) finished = true;  continue;
/*  529 */       }  if (chain != null) {
/*      */         
/*  531 */         sync(ref, chain, packet);
/*  532 */         changed = true;
/*  533 */         it.remove();
/*  534 */         this.packetQueueTime = 0L; continue;
/*  535 */       }  if (desynced) {
/*      */ 
/*      */         
/*  538 */         sendCancelPacket(packet.chainId, packet.forkedId);
/*  539 */         it.remove();
/*  540 */         HytaleLogger.Api ctx = LOGGER.at(Level.FINE);
/*  541 */         ctx.log("Discarding packet due to desync: %s", packet);
/*      */       } 
/*      */     } 
/*      */     
/*  545 */     if (desynced && !packetQueue.isEmpty()) {
/*      */       
/*  547 */       HytaleLogger.Api ctx = LOGGER.at(Level.FINE);
/*  548 */       if (ctx.isEnabled()) ctx.log("Discarding previous packets in queue: (before) %d", packetQueue.size()); 
/*  549 */       packetQueue.removeIf(v -> {
/*  550 */             boolean shouldRemove = (getChain(v.chainId, v.forkedId) == null && UUIDUtil.isEmptyOrNull(v.data.proxyId) && v.initial);
/*      */             
/*      */             if (shouldRemove) {
/*      */               HytaleLogger.Api ctx1 = LOGGER.at(Level.FINE);
/*      */               if (ctx1.isEnabled()) {
/*      */                 ctx1.log("Discarding: %s", v);
/*      */               }
/*      */               sendCancelPacket(v.chainId, v.forkedId);
/*      */             } 
/*      */             return shouldRemove;
/*      */           });
/*  561 */       ctx = LOGGER.at(Level.FINE);
/*  562 */       if (ctx.isEnabled()) ctx.log("Discarded previous packets in queue: (after) %d", packetQueue.size());
/*      */     
/*      */     } 
/*  565 */     return changed;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private InteractionChain getChain(int chainId, @Nullable ForkedChainId forkedChainId) {
/*  577 */     InteractionChain chain = (InteractionChain)this.chains.get(chainId);
/*  578 */     if (chain != null && forkedChainId != null) {
/*  579 */       ForkedChainId id = forkedChainId;
/*  580 */       while (id != null) {
/*  581 */         InteractionChain subChain = chain.getForkedChain(id);
/*  582 */         if (subChain == null) {
/*  583 */           return null;
/*      */         }
/*  585 */         chain = subChain;
/*  586 */         id = id.forkedId;
/*      */       } 
/*      */     } 
/*  589 */     return chain;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean tickChain(@Nonnull InteractionChain chain) {
/*  602 */     if (chain.wasPreTicked()) {
/*  603 */       chain.setPreTicked(false);
/*  604 */       return false;
/*      */     } 
/*      */ 
/*      */     
/*  608 */     if (!this.hasRemoteClient) chain.updateSimulatedState();
/*      */     
/*  610 */     chain.getForkedChains().values().removeIf(this.cachedTickChain);
/*      */     
/*  612 */     Ref<EntityStore> ref = this.entity.getReference();
/*  613 */     assert ref != null;
/*      */ 
/*      */     
/*  616 */     if (chain.getServerState() != InteractionState.NotFinished) {
/*      */       
/*  618 */       if (!chain.requiresClient() || chain.getClientState() != InteractionState.NotFinished) {
/*      */         
/*  620 */         LOGGER.at(Level.FINE).log("Remove Chain: %d, %s", chain.getChainId(), chain);
/*  621 */         handleCancelledChain(ref, chain);
/*  622 */         chain.onCompletion(this.cooldownHandler, this.hasRemoteClient);
/*  623 */         return chain.getForkedChains().isEmpty();
/*  624 */       }  if (!waitingForClient(ref)) {
/*      */         
/*  626 */         if (chain.getWaitingForClientFinished() == 0L) {
/*  627 */           chain.setWaitingForClientFinished(System.nanoTime());
/*      */         }
/*      */         
/*  630 */         long waitMillis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - chain.getWaitingForClientFinished());
/*  631 */         HytaleLogger.Api context = LOGGER.at(Level.FINE);
/*  632 */         if (context.isEnabled()) {
/*  633 */           context.log("Server finished chain but client hasn't! %d, %s, %s", Integer.valueOf(chain.getChainId()), chain, Long.valueOf(waitMillis));
/*      */         }
/*  635 */         long threshold = getOperationTimeoutThreshold();
/*      */         
/*  637 */         TimeResource timeResource = (TimeResource)this.commandBuffer.getResource(TimeResource.getResourceType());
/*  638 */         if (timeResource.getTimeDilationModifier() == 1.0F && waitMillis > threshold) {
/*      */           
/*  640 */           sendCancelPacket(chain);
/*  641 */           return chain.getForkedChains().isEmpty();
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  652 */       return false;
/*      */     } 
/*      */     
/*  655 */     int baseOpIndex = chain.getOperationIndex();
/*      */     
/*      */     try {
/*  658 */       doTickChain(ref, chain);
/*  659 */     } catch (ChainCancelledException e) {
/*  660 */       chain.setServerState(e.state);
/*  661 */       chain.setClientState(e.state);
/*      */ 
/*      */ 
/*      */       
/*  665 */       chain.updateServerState();
/*  666 */       if (!this.hasRemoteClient) chain.updateSimulatedState(); 
/*  667 */       if (chain.requiresClient()) {
/*  668 */         sendSyncPacket(chain, baseOpIndex, (List<InteractionSyncData>)this.tempSyncDataList);
/*  669 */         sendCancelPacket(chain);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  675 */     if (chain.getServerState() != InteractionState.NotFinished) {
/*      */       
/*  677 */       HytaleLogger.Api context = LOGGER.at(Level.FINE);
/*  678 */       if (context.isEnabled()) {
/*  679 */         context.log("Server finished chain: %d-%s, %s in %fs", Integer.valueOf(chain.getChainId()), chain.getForkedChainId(), chain, Float.valueOf(chain.getTimeInSeconds()));
/*      */       }
/*      */       
/*  682 */       if (!chain.requiresClient() || chain.getClientState() != InteractionState.NotFinished) {
/*  683 */         context = LOGGER.at(Level.FINE);
/*  684 */         if (context.isEnabled()) context.log("Remove Chain: %d-%s, %s", Integer.valueOf(chain.getChainId()), chain.getForkedChainId(), chain); 
/*  685 */         handleCancelledChain(ref, chain);
/*  686 */         chain.onCompletion(this.cooldownHandler, this.hasRemoteClient);
/*  687 */         return chain.getForkedChains().isEmpty();
/*      */       }
/*      */     
/*  690 */     } else if (chain.getClientState() != InteractionState.NotFinished && !waitingForClient(ref)) {
/*      */       
/*  692 */       if (chain.getWaitingForServerFinished() == 0L) chain.setWaitingForServerFinished(System.nanoTime()); 
/*  693 */       long waitMillis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - chain.getWaitingForServerFinished());
/*  694 */       HytaleLogger.Api context = LOGGER.at(Level.FINE);
/*  695 */       if (context.isEnabled()) {
/*  696 */         context.log("Client finished chain but server hasn't! %d, %s, %s", Integer.valueOf(chain.getChainId()), chain, Long.valueOf(waitMillis));
/*      */       }
/*      */       
/*  699 */       long threshold = getOperationTimeoutThreshold();
/*  700 */       if (waitMillis > threshold)
/*      */       {
/*      */ 
/*      */         
/*  704 */         LOGGER.at(Level.SEVERE).log("Client finished chain earlier than server! %d, %s", chain.getChainId(), chain);
/*      */       }
/*      */     } 
/*      */     
/*  708 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void handleCancelledChain(@Nonnull Ref<EntityStore> ref, @Nonnull InteractionChain chain) {
/*  722 */     assert this.commandBuffer != null;
/*      */     
/*  724 */     RootInteraction root = chain.getRootInteraction();
/*  725 */     int maxOperations = root.getOperationMax();
/*      */ 
/*      */     
/*  728 */     if (chain.getOperationCounter() >= maxOperations) {
/*      */       return;
/*      */     }
/*  731 */     InteractionEntry entry = chain.getInteraction(chain.getOperationIndex());
/*  732 */     if (entry == null)
/*      */       return; 
/*  734 */     Operation operation = root.getOperation(chain.getOperationCounter());
/*  735 */     if (operation == null) {
/*  736 */       throw new IllegalStateException("Failed to find operation during simulation tick of chain '" + root.getId() + "'");
/*      */     }
/*      */     
/*  739 */     InteractionContext context = chain.getContext();
/*      */ 
/*      */     
/*  742 */     (entry.getServerState()).state = InteractionState.Failed;
/*  743 */     if (entry.getClientState() != null) (entry.getClientState()).state = InteractionState.Failed;
/*      */     
/*      */     try {
/*  746 */       context.initEntry(chain, entry, this.entity);
/*      */       
/*  748 */       TimeResource timeResource = (TimeResource)this.commandBuffer.getResource(TimeResource.getResourceType());
/*  749 */       operation.handle(ref, false, entry.getTimeInSeconds(System.nanoTime()) * timeResource.getTimeDilationModifier(), chain.getType(), context);
/*      */     } finally {
/*  751 */       context.deinitEntry(chain, entry, this.entity);
/*      */     } 
/*      */ 
/*      */     
/*  755 */     chain.setOperationCounter(maxOperations);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void doTickChain(@Nonnull Ref<EntityStore> ref, @Nonnull InteractionChain chain) {
/*  772 */     ObjectList<InteractionSyncData> interactionData = this.tempSyncDataList;
/*  773 */     interactionData.clear();
/*      */     
/*  775 */     RootInteraction root = chain.getRootInteraction();
/*  776 */     int maxOperations = root.getOperationMax();
/*      */     
/*  778 */     int currentOp = chain.getOperationCounter();
/*  779 */     int baseOpIndex = chain.getOperationIndex();
/*      */     
/*  781 */     int callDepth = chain.getCallDepth();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  787 */     if (chain.consumeFirstRun()) {
/*  788 */       if (chain.getForkedChainId() == null) {
/*  789 */         chain.setTimeShift(getGlobalTimeShift(chain.getType()));
/*      */       } else {
/*  791 */         InteractionChain parent = (InteractionChain)this.chains.get(chain.getChainId());
/*  792 */         chain.setFirstRun((parent != null && parent.isFirstRun()));
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  798 */       chain.setTimeShift(0.0F);
/*      */     } 
/*      */     
/*  801 */     if (!chain.getContext().getEntity().isValid())
/*      */     {
/*  803 */       throw new ChainCancelledException(chain.getServerState());
/*      */     }
/*      */     
/*      */     while (true) {
/*  807 */       Operation simOp = !this.hasRemoteClient ? root.getOperation(chain.getSimulatedOperationCounter()) : null;
/*  808 */       WaitForDataFrom simWaitFrom = (simOp != null) ? simOp.getWaitForDataFrom() : null;
/*      */       
/*  810 */       long tickTime = System.nanoTime();
/*      */ 
/*      */ 
/*      */       
/*  814 */       if (!this.hasRemoteClient && simWaitFrom != WaitForDataFrom.Server) {
/*  815 */         simulationTick(ref, chain, tickTime);
/*      */       }
/*      */       
/*  818 */       interactionData.add(serverTick(ref, chain, tickTime));
/*      */ 
/*      */ 
/*      */       
/*  822 */       if (!chain.getContext().getEntity().isValid() && chain.getServerState() != InteractionState.Finished && chain.getServerState() != InteractionState.Failed) {
/*  823 */         throw new ChainCancelledException(chain.getServerState());
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  828 */       if (!this.hasRemoteClient && simWaitFrom == WaitForDataFrom.Server) {
/*  829 */         simulationTick(ref, chain, tickTime);
/*      */       }
/*      */       
/*  832 */       if (!this.hasRemoteClient) {
/*      */         
/*  834 */         if (chain.getRootInteraction() != chain.getSimulatedRootInteraction()) {
/*  835 */           throw new IllegalStateException("Simulation and server tick are not in sync (root interaction).\n" + chain
/*  836 */               .getRootInteraction().getId() + " vs " + String.valueOf(chain.getSimulatedRootInteraction()));
/*      */         }
/*  838 */         if (chain.getOperationCounter() != chain.getSimulatedOperationCounter()) {
/*  839 */           throw new IllegalStateException("Simulation and server tick are not in sync (operation position).\nRoot: " + chain
/*  840 */               .getRootInteraction().getId() + "\nCounter: " + chain
/*  841 */               .getOperationCounter() + " vs " + chain.getSimulatedOperationCounter() + "\nIndex: " + chain
/*  842 */               .getOperationIndex());
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  848 */       if (callDepth != chain.getCallDepth()) {
/*  849 */         callDepth = chain.getCallDepth();
/*  850 */         root = chain.getRootInteraction();
/*  851 */         maxOperations = root.getOperationMax();
/*  852 */       } else if (currentOp == chain.getOperationCounter()) {
/*      */         break;
/*      */       } 
/*      */       
/*  856 */       chain.nextOperationIndex();
/*      */       
/*  858 */       currentOp = chain.getOperationCounter();
/*  859 */       if (currentOp >= maxOperations) {
/*      */         
/*  861 */         while (callDepth > 0) {
/*  862 */           chain.popRoot();
/*  863 */           callDepth = chain.getCallDepth();
/*  864 */           currentOp = chain.getOperationCounter();
/*  865 */           root = chain.getRootInteraction();
/*  866 */           maxOperations = root.getOperationMax();
/*      */ 
/*      */           
/*  869 */           if (currentOp < maxOperations || callDepth == 0) {
/*      */             break;
/*      */           }
/*      */         } 
/*      */ 
/*      */         
/*  875 */         if (callDepth == 0 && currentOp >= maxOperations) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */     } 
/*  880 */     chain.updateServerState();
/*  881 */     if (!this.hasRemoteClient) chain.updateSimulatedState(); 
/*  882 */     if (chain.requiresClient()) {
/*  883 */       sendSyncPacket(chain, baseOpIndex, (List<InteractionSyncData>)interactionData);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private InteractionSyncData serverTick(@Nonnull Ref<EntityStore> ref, @Nonnull InteractionChain chain, long tickTime) {
/*      */     int i;
/*  901 */     assert this.commandBuffer != null;
/*      */     
/*  903 */     RootInteraction root = chain.getRootInteraction();
/*  904 */     Operation operation = root.getOperation(chain.getOperationCounter());
/*  905 */     assert operation != null;
/*      */     
/*  907 */     InteractionEntry entry = chain.getOrCreateInteractionEntry(chain.getOperationIndex());
/*      */     
/*  909 */     InteractionSyncData returnData = null;
/*      */ 
/*      */     
/*  912 */     boolean wasWrong = entry.consumeDesyncFlag();
/*  913 */     if (entry.getClientState() == null) {
/*  914 */       i = wasWrong | (!entry.setClientState(chain.removeInteractionSyncData(chain.getOperationIndex())) ? 1 : 0);
/*      */     }
/*      */     
/*  917 */     if (i != 0) {
/*      */       
/*  919 */       returnData = entry.getServerState();
/*  920 */       chain.flagDesync();
/*  921 */       chain.clearInteractionSyncData(chain.getOperationIndex());
/*      */     } 
/*      */     
/*  924 */     TimeResource timeResource = (TimeResource)this.commandBuffer.getResource(TimeResource.getResourceType());
/*  925 */     float tickTimeDilation = timeResource.getTimeDilationModifier();
/*      */ 
/*      */     
/*  928 */     if (operation.getWaitForDataFrom() == WaitForDataFrom.Client && entry.getClientState() == null) {
/*  929 */       if (waitingForClient(ref)) return null; 
/*  930 */       if (entry.getWaitingForSyncData() == 0L) entry.setWaitingForSyncData(System.nanoTime()); 
/*  931 */       long waitMillis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - entry.getWaitingForSyncData());
/*      */       
/*  933 */       HytaleLogger.Api api = LOGGER.at(Level.FINE);
/*  934 */       if (api.isEnabled()) {
/*  935 */         api.log("Wait for interaction clientData: %d, %s, %s", Integer.valueOf(chain.getOperationIndex()), entry, Long.valueOf(waitMillis));
/*      */       }
/*      */       
/*  938 */       long threshold = getOperationTimeoutThreshold();
/*      */       
/*  940 */       if (tickTimeDilation == 1.0F && waitMillis > threshold)
/*      */       {
/*      */         
/*  943 */         throw new RuntimeException("Client took too long to send clientData! Millis: " + waitMillis + ", Threshold: " + threshold + ",\nChain: " + String.valueOf(chain) + ",\nEntry: " + chain.getOperationIndex() + ", " + String.valueOf(entry) + ",\nWaiting for data from: " + String.valueOf(operation.getWaitForDataFrom()));
/*      */       }
/*      */ 
/*      */       
/*  947 */       if (entry.consumeSendInitial() || i != 0) {
/*  948 */         returnData = entry.getServerState();
/*      */       }
/*  950 */       return returnData;
/*      */     } 
/*      */     
/*  953 */     int serverDataHashCode = entry.getServerDataHashCode();
/*  954 */     InteractionContext context = chain.getContext();
/*      */     
/*  956 */     float time = entry.getTimeInSeconds(tickTime);
/*  957 */     boolean firstRun = false;
/*  958 */     if (entry.getTimestamp() == 0L) {
/*  959 */       time = chain.getTimeShift();
/*  960 */       entry.setTimestamp(tickTime, time);
/*  961 */       firstRun = true;
/*      */     } 
/*      */     
/*  964 */     time *= tickTimeDilation;
/*      */     
/*      */     try {
/*  967 */       context.initEntry(chain, entry, this.entity);
/*  968 */       operation.tick(ref, this.entity, firstRun, time, chain.getType(), context, this.cooldownHandler);
/*      */     } finally {
/*  970 */       context.deinitEntry(chain, entry, this.entity);
/*      */     } 
/*      */     
/*  973 */     InteractionSyncData serverData = entry.getServerState();
/*      */     
/*  975 */     if (firstRun || serverDataHashCode != entry.getServerDataHashCode()) returnData = serverData;
/*      */     
/*      */     try {
/*  978 */       context.initEntry(chain, entry, this.entity);
/*  979 */       operation.handle(ref, firstRun, time, chain.getType(), context);
/*      */     } finally {
/*  981 */       context.deinitEntry(chain, entry, this.entity);
/*      */     } 
/*  983 */     removeInteractionIfFinished(ref, chain, entry);
/*      */     
/*  985 */     return returnData;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void removeInteractionIfFinished(@Nonnull Ref<EntityStore> ref, @Nonnull InteractionChain chain, @Nonnull InteractionEntry entry) {
/* 1001 */     if (chain.getOperationIndex() == entry.getIndex() && (entry.getServerState()).state != InteractionState.NotFinished) {
/* 1002 */       chain.setFinalState((entry.getServerState()).state);
/*      */     }
/*      */ 
/*      */     
/* 1006 */     if ((entry.getServerState()).state != InteractionState.NotFinished) {
/*      */       
/* 1008 */       LOGGER.at(Level.FINE).log("Server finished interaction: %d, %s", entry.getIndex(), entry);
/* 1009 */       if (!chain.requiresClient() || (entry.getClientState() != null && (entry.getClientState()).state != InteractionState.NotFinished)) {
/* 1010 */         LOGGER.at(Level.FINER).log("Remove Interaction: %d, %s", entry.getIndex(), entry);
/* 1011 */         chain.removeInteractionEntry(this, entry.getIndex());
/*      */       }
/*      */     
/* 1014 */     } else if (entry.getClientState() != null && (entry.getClientState()).state != InteractionState.NotFinished && !waitingForClient(ref)) {
/*      */       
/* 1016 */       if (entry.getWaitingForServerFinished() == 0L) entry.setWaitingForServerFinished(System.nanoTime()); 
/* 1017 */       long waitMillis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - entry.getWaitingForServerFinished());
/* 1018 */       HytaleLogger.Api context = LOGGER.at(Level.FINE);
/* 1019 */       if (context.isEnabled()) {
/* 1020 */         context.log("Client finished interaction but server hasn't! %s, %d, %s, %s", (entry.getClientState()).state, Integer.valueOf(entry.getIndex()), entry, Long.valueOf(waitMillis));
/*      */       }
/*      */       
/* 1023 */       long threshold = getOperationTimeoutThreshold();
/* 1024 */       if (waitMillis > threshold) {
/*      */ 
/*      */ 
/*      */         
/* 1028 */         HytaleLogger.Api ctx = LOGGER.at(Level.SEVERE);
/* 1029 */         if (ctx.isEnabled()) ctx.log("Client finished interaction earlier than server! %d, %s", entry.getIndex(), entry);
/*      */       
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void simulationTick(@Nonnull Ref<EntityStore> ref, @Nonnull InteractionChain chain, long tickTime) {
/* 1044 */     assert this.commandBuffer != null;
/*      */     
/* 1046 */     RootInteraction rootInteraction = chain.getRootInteraction();
/* 1047 */     Operation operation = rootInteraction.getOperation(chain.getSimulatedOperationCounter());
/* 1048 */     if (operation == null) {
/* 1049 */       throw new IllegalStateException("Failed to find operation during simulation tick of chain '" + rootInteraction.getId() + "'");
/*      */     }
/*      */     
/* 1052 */     InteractionEntry entry = chain.getOrCreateInteractionEntry(chain.getClientOperationIndex());
/* 1053 */     InteractionContext context = chain.getContext();
/*      */     
/* 1055 */     entry.setUseSimulationState(true);
/*      */     try {
/* 1057 */       context.initEntry(chain, entry, this.entity);
/*      */       
/* 1059 */       float time = entry.getTimeInSeconds(tickTime);
/* 1060 */       boolean firstRun = false;
/* 1061 */       if (entry.getTimestamp() == 0L) {
/* 1062 */         time = chain.getTimeShift();
/* 1063 */         entry.setTimestamp(tickTime, time);
/* 1064 */         firstRun = true;
/*      */       } 
/*      */       
/* 1067 */       TimeResource timeResource = (TimeResource)this.commandBuffer.getResource(TimeResource.getResourceType());
/* 1068 */       float tickTimeDilation = timeResource.getTimeDilationModifier();
/* 1069 */       time *= tickTimeDilation;
/*      */       
/* 1071 */       operation.simulateTick(ref, this.entity, firstRun, time, chain.getType(), context, this.cooldownHandler);
/*      */     } finally {
/* 1073 */       context.deinitEntry(chain, entry, this.entity);
/* 1074 */       entry.setUseSimulationState(false);
/*      */     } 
/*      */     
/* 1077 */     if (!entry.setClientState(entry.getSimulationState())) {
/* 1078 */       throw new RuntimeException("Simulation failed");
/*      */     }
/*      */     
/* 1081 */     removeInteractionIfFinished(ref, chain, entry);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean syncStart(@Nonnull Ref<EntityStore> ref, @Nonnull SyncInteractionChain packet) {
/*      */     InteractionContext context;
/* 1095 */     assert this.commandBuffer != null;
/* 1096 */     int index = packet.chainId;
/*      */     
/* 1098 */     if (!packet.initial) {
/* 1099 */       if (packet.forkedId == null) {
/* 1100 */         HytaleLogger.Api ctx = LOGGER.at(Level.FINE);
/* 1101 */         if (ctx.isEnabled()) ctx.log("Got syncStart for %d-%s but packet wasn't the first.", index, packet.forkedId); 
/*      */       } 
/* 1103 */       return true;
/*      */     } 
/*      */     
/* 1106 */     if (packet.forkedId != null) {
/* 1107 */       HytaleLogger.Api ctx = LOGGER.at(Level.FINE);
/* 1108 */       if (ctx.isEnabled()) ctx.log("Can't start a forked chain from the client: %d %s", index, packet.forkedId); 
/* 1109 */       return true;
/*      */     } 
/*      */     
/* 1112 */     InteractionType type = packet.interactionType;
/*      */     
/* 1114 */     if (index <= 0) {
/* 1115 */       HytaleLogger.Api ctx = LOGGER.at(Level.FINE);
/* 1116 */       if (ctx.isEnabled()) ctx.log("Invalid client chainId! Got %d but client id's should be > 0", index); 
/* 1117 */       sendCancelPacket(index, packet.forkedId);
/* 1118 */       return true;
/*      */     } 
/* 1120 */     if (index <= this.lastClientChainId) {
/* 1121 */       HytaleLogger.Api ctx = LOGGER.at(Level.FINE);
/* 1122 */       if (ctx.isEnabled()) ctx.log("Invalid client chainId! The last clientChainId was %d but just got %d", this.lastClientChainId, index); 
/* 1123 */       sendCancelPacket(index, packet.forkedId);
/* 1124 */       return true;
/*      */     } 
/*      */     
/* 1127 */     UUID proxyId = packet.data.proxyId;
/*      */ 
/*      */     
/* 1130 */     if (!UUIDUtil.isEmptyOrNull(proxyId)) {
/* 1131 */       World world1 = ((EntityStore)this.commandBuffer.getExternalData()).getWorld();
/* 1132 */       Ref<EntityStore> proxyTarget = world1.getEntityStore().getRefFromUUID(proxyId);
/*      */       
/* 1134 */       if (proxyTarget == null) {
/* 1135 */         if (this.packetQueueTime != 0L && System.nanoTime() - this.packetQueueTime > TimeUnit.MILLISECONDS.toNanos(getOperationTimeoutThreshold()) / 2L) {
/* 1136 */           HytaleLogger.Api ctx = LOGGER.at(Level.FINE);
/* 1137 */           if (ctx.isEnabled()) ctx.log("Proxy entity never spawned"); 
/* 1138 */           sendCancelPacket(index, packet.forkedId);
/* 1139 */           return true;
/*      */         } 
/* 1141 */         return false;
/*      */       } 
/* 1143 */       context = InteractionContext.forProxyEntity(this, this.entity, proxyTarget);
/*      */     } else {
/*      */       
/* 1146 */       context = InteractionContext.forInteraction(this, ref, type, packet.equipSlot, (ComponentAccessor<EntityStore>)this.commandBuffer);
/*      */     } 
/*      */     
/* 1149 */     String rootInteractionId = context.getRootInteractionId(type);
/* 1150 */     if (rootInteractionId == null) {
/* 1151 */       HytaleLogger.Api ctx = LOGGER.at(Level.FINE);
/* 1152 */       if (ctx.isEnabled()) ctx.log("Missing root interaction: %d, %s, %s", Integer.valueOf(index), this.entity.getInventory().getItemInHand(), type); 
/* 1153 */       sendCancelPacket(index, packet.forkedId);
/* 1154 */       return true;
/*      */     } 
/*      */     
/* 1157 */     RootInteraction rootInteraction = RootInteraction.getRootInteractionOrUnknown(rootInteractionId);
/* 1158 */     if (rootInteraction == null) return false;
/*      */     
/* 1160 */     if (!applyRules(context, packet.data, type, rootInteraction)) {
/* 1161 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1166 */     Inventory entityInventory = this.entity.getInventory();
/* 1167 */     ItemStack itemInHand = entityInventory.getActiveHotbarItem();
/* 1168 */     ItemStack utilityItem = entityInventory.getUtilityItem();
/*      */     
/* 1170 */     String serverItemInHandId = (itemInHand != null) ? itemInHand.getItemId() : null;
/* 1171 */     String serverUtilityItemId = (utilityItem != null) ? utilityItem.getItemId() : null;
/*      */     
/* 1173 */     if (packet.activeHotbarSlot != entityInventory.getActiveHotbarSlot()) {
/* 1174 */       HytaleLogger.Api ctx = LOGGER.at(Level.FINE);
/* 1175 */       if (ctx.isEnabled()) {
/* 1176 */         ctx.log("Active slot miss match: %d, %d != %d, %s, %s, %s", Integer.valueOf(index), Byte.valueOf(entityInventory.getActiveHotbarSlot()), Integer.valueOf(packet.activeHotbarSlot), serverItemInHandId, packet.itemInHandId, type);
/*      */       }
/* 1178 */       sendCancelPacket(index, packet.forkedId);
/*      */ 
/*      */       
/* 1181 */       if (this.playerRef != null) {
/* 1182 */         this.playerRef.getPacketHandler().writeNoCache((Packet)new SetActiveSlot(-1, entityInventory.getActiveHotbarSlot()));
/*      */       }
/* 1184 */       return true;
/*      */     } 
/*      */     
/* 1187 */     if (packet.activeUtilitySlot != entityInventory.getActiveUtilitySlot()) {
/* 1188 */       HytaleLogger.Api ctx = LOGGER.at(Level.FINE);
/* 1189 */       if (ctx.isEnabled()) {
/* 1190 */         ctx.log("Active slot miss match: %d, %d != %d, %s, %s, %s", Integer.valueOf(index), Byte.valueOf(entityInventory.getActiveUtilitySlot()), Integer.valueOf(packet.activeUtilitySlot), serverItemInHandId, packet.itemInHandId, type);
/*      */       }
/* 1192 */       sendCancelPacket(index, packet.forkedId);
/*      */ 
/*      */       
/* 1195 */       if (this.playerRef != null) {
/* 1196 */         this.playerRef.getPacketHandler().writeNoCache((Packet)new SetActiveSlot(-5, entityInventory.getActiveUtilitySlot()));
/*      */       }
/* 1198 */       return true;
/*      */     } 
/*      */     
/* 1201 */     if (!Objects.equals(serverItemInHandId, packet.itemInHandId)) {
/* 1202 */       HytaleLogger.Api ctx = LOGGER.at(Level.FINE);
/* 1203 */       if (ctx.isEnabled()) ctx.log("ItemInHand miss match: %d, %s, %s, %s", Integer.valueOf(index), serverItemInHandId, packet.itemInHandId, type); 
/* 1204 */       sendCancelPacket(index, packet.forkedId);
/* 1205 */       return true;
/*      */     } 
/*      */     
/* 1208 */     if (!Objects.equals(serverUtilityItemId, packet.utilityItemId)) {
/* 1209 */       HytaleLogger.Api ctx = LOGGER.at(Level.FINE);
/* 1210 */       if (ctx.isEnabled()) ctx.log("UtilityItem miss match: %d, %s, %s, %s", Integer.valueOf(index), serverUtilityItemId, packet.utilityItemId, type); 
/* 1211 */       sendCancelPacket(index, packet.forkedId);
/* 1212 */       return true;
/*      */     } 
/*      */     
/* 1215 */     if (isOnCooldown(ref, type, rootInteraction, true)) {
/* 1216 */       return false;
/*      */     }
/*      */     
/* 1219 */     InteractionChain chain = initChain(packet.data, type, context, rootInteraction, (Runnable)null, true);
/* 1220 */     chain.setChainId(index);
/* 1221 */     sync(ref, chain, packet);
/*      */     
/* 1223 */     World world = ((EntityStore)this.commandBuffer.getExternalData()).getWorld();
/*      */ 
/*      */ 
/*      */     
/* 1227 */     if (packet.data.blockPosition != null) {
/*      */       
/* 1229 */       BlockPosition targetBlock = world.getBaseBlock(packet.data.blockPosition);
/* 1230 */       context.getMetaStore().putMetaObject(Interaction.TARGET_BLOCK, targetBlock);
/* 1231 */       context.getMetaStore().putMetaObject(Interaction.TARGET_BLOCK_RAW, packet.data.blockPosition);
/*      */     } 
/* 1233 */     if (packet.data.entityId >= 0) {
/*      */       
/* 1235 */       EntityStore entityComponentStore = world.getEntityStore();
/* 1236 */       Ref<EntityStore> entityReference = entityComponentStore.getRefFromNetworkId(packet.data.entityId);
/* 1237 */       if (entityReference != null) {
/* 1238 */         context.getMetaStore().putMetaObject(Interaction.TARGET_ENTITY, entityReference);
/*      */       }
/*      */     } 
/*      */     
/* 1242 */     if (packet.data.targetSlot != Integer.MIN_VALUE) {
/* 1243 */       context.getMetaStore().putMetaObject(Interaction.TARGET_SLOT, Integer.valueOf(packet.data.targetSlot));
/*      */     }
/*      */     
/* 1246 */     if (packet.data.hitLocation != null) {
/* 1247 */       Vector3f hit = packet.data.hitLocation;
/* 1248 */       context.getMetaStore().putMetaObject(Interaction.HIT_LOCATION, new Vector4d(hit.x, hit.y, hit.z, 1.0D));
/*      */     } 
/*      */     
/* 1251 */     if (packet.data.hitDetail != null) {
/* 1252 */       context.getMetaStore().putMetaObject(Interaction.HIT_DETAIL, packet.data.hitDetail);
/*      */     }
/*      */     
/* 1255 */     this.lastClientChainId = index;
/*      */ 
/*      */     
/* 1258 */     if (!tickChain(chain)) {
/* 1259 */       chain.setPreTicked(true);
/* 1260 */       this.chains.put(index, chain);
/*      */     } 
/*      */     
/* 1263 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sync(@Nonnull Ref<EntityStore> ref, @Nonnull ChainSyncStorage chainSyncStorage, @Nonnull SyncInteractionChain packet) {
/* 1275 */     assert this.commandBuffer != null;
/*      */     
/* 1277 */     if (packet.newForks != null) {
/* 1278 */       for (SyncInteractionChain fork : packet.newForks) {
/* 1279 */         chainSyncStorage.syncFork(ref, this, fork);
/*      */       }
/*      */     }
/*      */     
/* 1283 */     if (packet.interactionData == null) {
/* 1284 */       chainSyncStorage.setClientState(packet.state);
/*      */       
/*      */       return;
/*      */     } 
/* 1288 */     for (int i = 0; i < packet.interactionData.length; i++) {
/* 1289 */       InteractionSyncData syncData = packet.interactionData[i];
/* 1290 */       if (syncData != null) {
/*      */ 
/*      */         
/* 1293 */         int index = packet.operationBaseIndex + i;
/* 1294 */         if (!chainSyncStorage.isSyncDataOutOfOrder(index)) {
/*      */ 
/*      */ 
/*      */           
/* 1298 */           InteractionEntry interaction = chainSyncStorage.getInteraction(index);
/* 1299 */           if (interaction != null && chainSyncStorage instanceof InteractionChain) { InteractionChain interactionChain = (InteractionChain)chainSyncStorage;
/*      */             
/* 1301 */             if ((interaction.getClientState() != null && (interaction.getClientState()).state != InteractionState.NotFinished && syncData.state == InteractionState.NotFinished) || 
/* 1302 */               !interaction.setClientState(syncData)) {
/* 1303 */               chainSyncStorage.clearInteractionSyncData(index);
/*      */ 
/*      */               
/* 1306 */               interaction.flagDesync();
/* 1307 */               interactionChain.flagDesync();
/*      */               
/*      */               return;
/*      */             } 
/* 1311 */             chainSyncStorage.updateSyncPosition(index);
/*      */             
/* 1313 */             HytaleLogger.Api context = LOGGER.at(Level.FINEST);
/* 1314 */             if (context.isEnabled()) {
/* 1315 */               TimeResource timeResource = (TimeResource)this.commandBuffer.getResource(TimeResource.getResourceType());
/* 1316 */               float tickTimeDilation = timeResource.getTimeDilationModifier();
/*      */               
/* 1318 */               context.log("%d, %d: Time (Sync) - Server: %s vs Client: %s", 
/* 1319 */                   Integer.valueOf(packet.chainId), Integer.valueOf(index), 
/* 1320 */                   Float.valueOf(interaction.getTimeInSeconds(System.nanoTime()) * tickTimeDilation), Float.valueOf((interaction.getClientState()).progress));
/*      */             } 
/*      */             
/* 1323 */             removeInteractionIfFinished(ref, interactionChain, interaction); }
/*      */           else
/* 1325 */           { chainSyncStorage.putInteractionSyncData(index, syncData); }
/*      */         
/*      */         } 
/*      */       } 
/* 1329 */     }  int last = packet.operationBaseIndex + packet.interactionData.length;
/* 1330 */     chainSyncStorage.clearInteractionSyncData(last);
/*      */     
/* 1332 */     chainSyncStorage.setClientState(packet.state);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canRun(@Nonnull InteractionType type, @Nonnull RootInteraction rootInteraction) {
/* 1343 */     return canRun(type, (short)-1, rootInteraction);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canRun(@Nonnull InteractionType type, short equipSlot, @Nonnull RootInteraction rootInteraction) {
/* 1355 */     return applyRules(null, type, equipSlot, rootInteraction, (Map)this.chains, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean applyRules(@Nonnull InteractionContext context, @Nonnull InteractionChainData data, @Nonnull InteractionType type, @Nonnull RootInteraction rootInteraction) {
/* 1376 */     ObjectArrayList objectArrayList = new ObjectArrayList();
/*      */     
/* 1378 */     if (!applyRules(data, type, context.getHeldItemSlot(), rootInteraction, (Map)this.chains, (List<InteractionChain>)objectArrayList)) return false;
/*      */ 
/*      */     
/* 1381 */     for (InteractionChain interactionChain : objectArrayList) {
/* 1382 */       cancelChains(interactionChain);
/*      */     }
/* 1384 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void cancelChains(@Nonnull InteractionChain chain) {
/* 1393 */     chain.setServerState(InteractionState.Failed);
/* 1394 */     chain.setClientState(InteractionState.Failed);
/* 1395 */     sendCancelPacket(chain);
/*      */     
/* 1397 */     for (ObjectIterator<InteractionChain> objectIterator = chain.getForkedChains().values().iterator(); objectIterator.hasNext(); ) { InteractionChain fork = objectIterator.next();
/* 1398 */       cancelChains(fork); }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean applyRules(@Nullable InteractionChainData data, @Nonnull InteractionType type, int heldItemSlot, @Nullable RootInteraction rootInteraction, @Nonnull Map<?, InteractionChain> chains, @Nullable List<InteractionChain> chainsToCancel) {
/* 1421 */     if (chains.isEmpty() || rootInteraction == null) return true;
/*      */     
/* 1423 */     for (InteractionChain chain : chains.values()) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1430 */       if (chain.getForkedChainId() != null && !chain.isPredicted()) {
/*      */         continue;
/*      */       }
/*      */       
/* 1434 */       if (data != null && !Objects.equals((chain.getChainData()).proxyId, data.proxyId)) {
/*      */         continue;
/*      */       }
/* 1437 */       if (type == InteractionType.Equipped && chain.getType() == InteractionType.Equipped && chain.getContext().getHeldItemSlot() != heldItemSlot) {
/*      */         continue;
/*      */       }
/*      */       
/* 1441 */       if (chain.getServerState() == InteractionState.NotFinished) {
/*      */         
/* 1443 */         RootInteraction currentRoot = chain.getRootInteraction();
/* 1444 */         Operation currentOp = currentRoot.getOperation(chain.getOperationCounter());
/*      */ 
/*      */         
/* 1447 */         if (rootInteraction.getRules().validateInterrupts(type, rootInteraction.getData().getTags(), chain.getType(), currentRoot.getData().getTags(), currentRoot.getRules())) {
/* 1448 */           if (chainsToCancel != null) chainsToCancel.add(chain); 
/* 1449 */         } else if (currentOp != null && currentOp.getRules() != null && rootInteraction.getRules().validateInterrupts(type, rootInteraction.getData().getTags(), chain.getType(), currentOp.getTags(), currentOp.getRules())) {
/*      */           
/* 1451 */           if (chainsToCancel != null) chainsToCancel.add(chain); 
/*      */         } else {
/* 1453 */           if (rootInteraction.getRules().validateBlocked(type, rootInteraction.getData().getTags(), chain.getType(), currentRoot.getData().getTags(), currentRoot.getRules())) {
/* 1454 */             return false;
/*      */           }
/*      */ 
/*      */           
/* 1458 */           if (currentOp != null && currentOp.getRules() != null && rootInteraction.getRules().validateBlocked(type, rootInteraction.getData().getTags(), chain.getType(), currentOp.getTags(), currentOp.getRules())) {
/* 1459 */             return false;
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/* 1464 */       if ((chainsToCancel == null || chainsToCancel.isEmpty()) && !applyRules(data, type, heldItemSlot, rootInteraction, (Map)chain.getForkedChains(), chainsToCancel)) {
/* 1465 */         return false;
/*      */       }
/*      */     } 
/* 1468 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean tryStartChain(@Nonnull Ref<EntityStore> ref, @Nonnull CommandBuffer<EntityStore> commandBuffer, @Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull RootInteraction rootInteraction) {
/* 1487 */     InteractionChain chain = initChain(type, context, rootInteraction, false);
/* 1488 */     if (!applyRules(context, chain.getChainData(), type, rootInteraction)) {
/* 1489 */       return false;
/*      */     }
/* 1491 */     executeChain(ref, commandBuffer, chain);
/* 1492 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void startChain(@Nonnull Ref<EntityStore> ref, @Nonnull CommandBuffer<EntityStore> commandBuffer, @Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull RootInteraction rootInteraction) {
/* 1510 */     InteractionChain chain = initChain(type, context, rootInteraction, false);
/* 1511 */     executeChain(ref, commandBuffer, chain);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public InteractionChain initChain(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull RootInteraction rootInteraction, boolean forceRemoteSync) {
/* 1530 */     return initChain(type, context, rootInteraction, -1, (BlockPosition)null, forceRemoteSync);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public InteractionChain initChain(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull RootInteraction rootInteraction, int entityId, @Nullable BlockPosition blockPosition, boolean forceRemoteSync) {
/* 1553 */     InteractionChainData data = new InteractionChainData(entityId, UUIDUtil.EMPTY_UUID, null, null, blockPosition, -2147483648, null);
/* 1554 */     return initChain(data, type, context, rootInteraction, (Runnable)null, forceRemoteSync);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public InteractionChain initChain(@Nonnull InteractionChainData data, @Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull RootInteraction rootInteraction, @Nullable Runnable onCompletion, boolean forceRemoteSync) {
/* 1584 */     return new InteractionChain(type, context, data, rootInteraction, onCompletion, (forceRemoteSync || !this.hasRemoteClient));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void queueExecuteChain(@Nonnull InteractionChain chain) {
/* 1593 */     this.chainStartQueue.add(chain);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void executeChain(@Nonnull Ref<EntityStore> ref, @Nonnull CommandBuffer<EntityStore> commandBuffer, @Nonnull InteractionChain chain) {
/* 1608 */     this.commandBuffer = commandBuffer;
/* 1609 */     executeChain0(ref, chain);
/* 1610 */     this.commandBuffer = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void executeChain0(@Nonnull Ref<EntityStore> ref, @Nonnull InteractionChain chain) {
/* 1620 */     if (isOnCooldown(ref, chain.getType(), chain.getInitialRootInteraction(), false)) {
/* 1621 */       chain.setServerState(InteractionState.Failed);
/* 1622 */       chain.setClientState(InteractionState.Failed);
/*      */       
/*      */       return;
/*      */     } 
/* 1626 */     int index = --this.lastServerChainId;
/* 1627 */     if (index >= 0) index = this.lastServerChainId = -1; 
/* 1628 */     chain.setChainId(index);
/*      */     
/* 1630 */     if (tickChain(chain)) {
/*      */       return;
/*      */     }
/* 1633 */     LOGGER.at(Level.FINE).log("Add Chain: %d, %s", index, chain);
/* 1634 */     chain.setPreTicked(true);
/* 1635 */     this.chains.put(index, chain);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isOnCooldown(@Nonnull Ref<EntityStore> ref, @Nonnull InteractionType type, @Nonnull RootInteraction root, boolean remote) {
/* 1650 */     assert this.commandBuffer != null;
/*      */     
/* 1652 */     InteractionCooldown cooldown = root.getCooldown();
/* 1653 */     String cooldownId = root.getId();
/* 1654 */     float cooldownTime = InteractionTypeUtils.getDefaultCooldown(type);
/* 1655 */     float[] cooldownChargeTimes = DEFAULT_CHARGE_TIMES;
/* 1656 */     boolean interruptRecharge = false;
/*      */     
/* 1658 */     if (cooldown != null) {
/* 1659 */       cooldownTime = cooldown.cooldown;
/* 1660 */       if (cooldown.chargeTimes != null && cooldown.chargeTimes.length > 0) {
/* 1661 */         cooldownChargeTimes = cooldown.chargeTimes;
/*      */       }
/* 1663 */       if (cooldown.cooldownId != null) cooldownId = cooldown.cooldownId; 
/* 1664 */       if (cooldown.interruptRecharge) interruptRecharge = true;
/*      */ 
/*      */       
/* 1667 */       if (cooldown.clickBypass && remote) {
/* 1668 */         this.cooldownHandler.resetCooldown(cooldownId, cooldownTime, cooldownChargeTimes, interruptRecharge);
/* 1669 */         return false;
/*      */       } 
/*      */     } 
/*      */     
/* 1673 */     Player playerComponent = (Player)this.commandBuffer.getComponent(ref, Player.getComponentType());
/* 1674 */     GameMode gameMode = (playerComponent != null) ? playerComponent.getGameMode() : GameMode.Adventure;
/* 1675 */     RootInteractionSettings settings = (RootInteractionSettings)root.getSettings().get(gameMode);
/* 1676 */     if (settings != null && settings.allowSkipChainOnClick && remote) {
/* 1677 */       this.cooldownHandler.resetCooldown(cooldownId, cooldownTime, cooldownChargeTimes, interruptRecharge);
/* 1678 */       return false;
/*      */     } 
/*      */     
/* 1681 */     return this.cooldownHandler.isOnCooldown(root, cooldownId, cooldownTime, cooldownChargeTimes, interruptRecharge);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void tryRunHeldInteraction(@Nonnull Ref<EntityStore> ref, @Nonnull CommandBuffer<EntityStore> commandBuffer, @Nonnull InteractionType type) {
/* 1694 */     tryRunHeldInteraction(ref, commandBuffer, type, (short)-1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void tryRunHeldInteraction(@Nonnull Ref<EntityStore> ref, @Nonnull CommandBuffer<EntityStore> commandBuffer, @Nonnull InteractionType type, short equipSlot) {
/*      */     ItemStack itemStack;
/* 1709 */     Inventory inventory = this.entity.getInventory();
/*      */ 
/*      */     
/* 1712 */     switch (type) { case Held:
/* 1713 */         itemStack = inventory.getItemInHand(); break;
/* 1714 */       case HeldOffhand: itemStack = inventory.getUtilityItem(); break;
/*      */       case Equipped:
/* 1716 */         if (equipSlot == -1) throw new IllegalArgumentException(); 
/* 1717 */         itemStack = inventory.getArmor().getItemStack(equipSlot); break;
/*      */       default:
/* 1719 */         throw new IllegalArgumentException(); }
/*      */ 
/*      */     
/* 1722 */     if (itemStack == null || itemStack.isEmpty())
/*      */       return; 
/* 1724 */     String rootId = (String)itemStack.getItem().getInteractions().get(type);
/* 1725 */     if (rootId == null)
/*      */       return; 
/* 1727 */     RootInteraction root = (RootInteraction)RootInteraction.getAssetMap().getAsset(rootId);
/* 1728 */     if (root == null || !canRun(type, equipSlot, root))
/*      */       return; 
/* 1730 */     InteractionContext context = InteractionContext.forInteraction(this, ref, type, equipSlot, (ComponentAccessor<EntityStore>)commandBuffer);
/* 1731 */     startChain(ref, commandBuffer, type, context, root);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendSyncPacket(@Nonnull InteractionChain chain, int operationBaseIndex, @Nullable List<InteractionSyncData> interactionData) {
/* 1742 */     if (chain.hasSentInitial() && (interactionData == null || 
/* 1743 */       ListUtil.emptyOrAllNull(interactionData)) && chain
/* 1744 */       .getNewForks().isEmpty()) {
/*      */       return;
/*      */     }
/*      */     
/* 1748 */     if (this.playerRef != null) {
/* 1749 */       SyncInteractionChain packet = makeSyncPacket(chain, operationBaseIndex, interactionData);
/* 1750 */       this.syncPackets.add(packet);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   private static SyncInteractionChain makeSyncPacket(@Nonnull InteractionChain chain, int operationBaseIndex, @Nullable List<InteractionSyncData> interactionData) {
/* 1764 */     SyncInteractionChain[] forks = null;
/* 1765 */     List<InteractionChain> newForks = chain.getNewForks();
/* 1766 */     if (!newForks.isEmpty()) {
/* 1767 */       forks = new SyncInteractionChain[newForks.size()];
/* 1768 */       for (int i = 0; i < newForks.size(); i++) {
/* 1769 */         InteractionChain fc = newForks.get(i);
/* 1770 */         forks[i] = makeSyncPacket(fc, 0, null);
/*      */       } 
/* 1772 */       newForks.clear();
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1791 */     SyncInteractionChain packet = new SyncInteractionChain(0, 0, 0, null, null, null, !chain.hasSentInitial(), false, chain.hasSentInitial() ? Integer.MIN_VALUE : RootInteraction.getRootInteractionIdOrUnknown(chain.getInitialRootInteraction().getId()), chain.getType(), chain.getContext().getHeldItemSlot(), chain.getChainId(), chain.getForkedChainId(), chain.getChainData(), chain.getServerState(), forks, operationBaseIndex, (interactionData == null) ? null : (InteractionSyncData[])interactionData.toArray(x$0 -> new InteractionSyncData[x$0]));
/*      */     
/* 1793 */     chain.setSentInitial(true);
/* 1794 */     return packet;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void sendCancelPacket(@Nonnull InteractionChain chain) {
/* 1803 */     sendCancelPacket(chain.getChainId(), chain.getForkedChainId());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendCancelPacket(int chainId, @Nonnull ForkedChainId forkedChainId) {
/* 1813 */     if (this.playerRef != null) {
/* 1814 */       this.playerRef.getPacketHandler().writeNoCache((Packet)new CancelInteractionChain(chainId, forkedChainId));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clear() {
/* 1823 */     forEachInteraction((chain, _i, _a) -> { chain.setServerState(InteractionState.Failed); chain.setClientState(InteractionState.Failed); sendCancelPacket(chain); return null; }null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1830 */     this.chainStartQueue.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearAllGlobalTimeShift(float dt) {
/* 1839 */     if (this.timeShiftsDirty) {
/*      */       
/* 1841 */       boolean clearFlag = true;
/* 1842 */       for (int i = 0; i < this.globalTimeShift.length; i++) {
/* 1843 */         if (!this.globalTimeShiftDirty[i]) {
/* 1844 */           this.globalTimeShift[i] = 0.0F;
/*      */         } else {
/* 1846 */           clearFlag = false;
/* 1847 */           this.globalTimeShift[i] = this.globalTimeShift[i] + dt;
/*      */         } 
/*      */       } 
/* 1850 */       Arrays.fill(this.globalTimeShiftDirty, false);
/* 1851 */       if (clearFlag) this.timeShiftsDirty = false;
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setGlobalTimeShift(@Nonnull InteractionType type, float shift) {
/* 1902 */     if (shift < 0.0F) throw new IllegalArgumentException("Can't shift backwards"); 
/* 1903 */     this.globalTimeShift[type.ordinal()] = shift;
/* 1904 */     this.globalTimeShiftDirty[type.ordinal()] = true;
/* 1905 */     this.timeShiftsDirty = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getGlobalTimeShift(@Nonnull InteractionType type) {
/* 1919 */     return this.globalTimeShift[type.ordinal()];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T forEachInteraction(@Nonnull TriFunction<InteractionChain, Interaction, T, T> func, @Nonnull T val) {
/* 1936 */     return forEachInteraction((Map)this.chains, func, val);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static <T> T forEachInteraction(@Nonnull Map<?, InteractionChain> chains, @Nonnull TriFunction<InteractionChain, Interaction, T, T> func, @Nonnull T val) {
/* 1949 */     if (chains.isEmpty()) return val;
/*      */     
/* 1951 */     for (InteractionChain chain : chains.values()) {
/* 1952 */       Operation operation = chain.getRootInteraction().getOperation(chain.getOperationCounter());
/* 1953 */       if (operation != null) {
/* 1954 */         operation = operation.getInnerOperation();
/* 1955 */         if (operation instanceof Interaction) { Interaction interaction = (Interaction)operation;
/* 1956 */           val = (T)func.apply(chain, interaction, val); }
/*      */       
/*      */       } 
/* 1959 */       val = forEachInteraction((Map)chain.getForkedChains(), func, val);
/*      */     } 
/* 1961 */     return val;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void walkChain(@Nonnull Ref<EntityStore> ref, @Nonnull Collector collector, @Nonnull InteractionType type, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 1977 */     walkChain(ref, collector, type, null, componentAccessor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void walkChain(@Nonnull Ref<EntityStore> ref, @Nonnull Collector collector, @Nonnull InteractionType type, @Nullable RootInteraction rootInteraction, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 1994 */     walkChain(collector, type, InteractionContext.forInteraction(this, ref, type, componentAccessor), rootInteraction);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void walkChain(@Nonnull Collector collector, @Nonnull InteractionType type, @Nonnull InteractionContext context, @Nullable RootInteraction rootInteraction) {
/* 2011 */     if (rootInteraction == null) {
/* 2012 */       String rootInteractionId = context.getRootInteractionId(type);
/* 2013 */       if (rootInteractionId == null) {
/* 2014 */         throw new IllegalArgumentException("No interaction ID found for " + String.valueOf(type) + ", " + String.valueOf(context));
/*      */       }
/*      */       
/* 2017 */       rootInteraction = (RootInteraction)RootInteraction.getAssetMap().getAsset(rootInteractionId);
/*      */     } 
/*      */ 
/*      */     
/* 2021 */     if (rootInteraction == null) {
/* 2022 */       throw new IllegalArgumentException("No interactions are defined for " + String.valueOf(type) + ", " + String.valueOf(context));
/*      */     }
/*      */     
/* 2025 */     collector.start();
/* 2026 */     collector.into(context, null);
/* 2027 */     walkInteractions(collector, context, CollectorTag.ROOT, rootInteraction.getInteractionIds());
/* 2028 */     collector.outof();
/* 2029 */     collector.finished();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean walkInteractions(@Nonnull Collector collector, @Nonnull InteractionContext context, @Nonnull CollectorTag tag, @Nonnull String[] interactionIds) {
/* 2045 */     for (String id : interactionIds) {
/* 2046 */       if (walkInteraction(collector, context, tag, id)) return true; 
/*      */     } 
/* 2048 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean walkInteraction(@Nonnull Collector collector, @Nonnull InteractionContext context, @Nonnull CollectorTag tag, @Nullable String id) {
/* 2064 */     if (id == null) return false; 
/* 2065 */     Interaction interaction = (Interaction)Interaction.getAssetMap().getAsset(id);
/* 2066 */     if (interaction == null) throw new IllegalArgumentException("Failed to find interaction: " + id);
/*      */     
/* 2068 */     if (collector.collect(tag, context, interaction)) return true;
/*      */     
/* 2070 */     collector.into(context, interaction);
/* 2071 */     interaction.walk(collector, context);
/* 2072 */     collector.outof();
/* 2073 */     return false;
/*      */   }
/*      */   
/*      */   public ObjectList<SyncInteractionChain> getSyncPackets() {
/* 2077 */     return this.syncPackets;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public Component<EntityStore> clone() {
/* 2083 */     InteractionManager manager = new InteractionManager(this.entity, this.playerRef, this.interactionSimulationHandler);
/* 2084 */     manager.copyFrom(this);
/* 2085 */     return manager;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class ChainCancelledException
/*      */     extends RuntimeException
/*      */   {
/*      */     @Nonnull
/*      */     private final InteractionState state;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ChainCancelledException(@Nonnull InteractionState state) {
/* 2106 */       this.state = state;
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\InteractionManager.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */