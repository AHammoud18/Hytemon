/*     */ package com.hypixel.hytale.server.npc.asset.builder;
/*     */ import com.hypixel.hytale.common.thread.ticking.Tickable;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.server.core.asset.type.blockset.config.BlockSet;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.blackboard.view.event.block.BlockEventType;
/*     */ import com.hypixel.hytale.server.npc.blackboard.view.event.entity.EntityEventType;
/*     */ import com.hypixel.hytale.server.npc.decisionmaker.stateevaluator.StateEvaluator;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.instructions.Instruction;
/*     */ import com.hypixel.hytale.server.npc.role.support.RoleStats;
/*     */ import com.hypixel.hytale.server.npc.storage.AlarmStore;
/*     */ import com.hypixel.hytale.server.npc.util.Alarm;
/*     */ import com.hypixel.hytale.server.npc.util.Timer;
/*     */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*     */ import com.hypixel.hytale.server.npc.util.expression.Scope;
/*     */ import com.hypixel.hytale.server.npc.util.expression.StdScope;
/*     */ import com.hypixel.hytale.server.npc.valuestore.ValueStore;
/*     */ import it.unimi.dsi.fastutil.ints.Int2IntMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.ints.IntArrayList;
/*     */ import it.unimi.dsi.fastutil.ints.IntCollection;
/*     */ import it.unimi.dsi.fastutil.ints.IntList;
/*     */ import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
/*     */ import it.unimi.dsi.fastutil.ints.IntSet;
/*     */ import it.unimi.dsi.fastutil.ints.IntSets;
/*     */ import it.unimi.dsi.fastutil.ints.IntStack;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.BitSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class BuilderSupport {
/*     */   private final BuilderManager builderManager;
/*     */   @Nonnull
/*     */   private final NPCEntity npcEntity;
/*  44 */   private final SlotMapper flagSlotMapper = new SlotMapper(); private final Holder<EntityStore> holder; private final ExecutionContext executionContext; private boolean requireLeashPosition;
/*  45 */   private final SlotMapper beaconSlotMapper = new SlotMapper();
/*  46 */   private final SlotMapper targetSlotMapper = new SlotMapper(true);
/*  47 */   private final SlotMapper positionSlotMapper = new SlotMapper();
/*  48 */   private final ReferenceSlotMapper<Timer> timerSlotMapper = new ReferenceSlotMapper<>(Timer::new);
/*  49 */   private final SlotMapper searchRaySlotMapper = new SlotMapper();
/*     */   
/*  51 */   private final SlotMapper parameterSlotMapper = new SlotMapper();
/*     */   
/*     */   @Nonnull
/*     */   private final Object2IntMap<String> instructionSlotMappings;
/*  55 */   private final Int2ObjectMap<String> instructionNameMappings = (Int2ObjectMap<String>)new Int2ObjectOpenHashMap();
/*  56 */   private final List<Instruction> instructions = (List<Instruction>)new ObjectArrayList();
/*     */   
/*     */   private EventSlotMapper<BlockEventType> playerBlockEventSlotMapper;
/*     */   
/*     */   private EventSlotMapper<BlockEventType> npcBlockEventSlotMapper;
/*     */   
/*     */   private EventSlotMapper<EntityEventType> playerEntityEventSlotMapper;
/*     */   
/*     */   private EventSlotMapper<EntityEventType> npcEntityEventSlotMapper;
/*     */   
/*     */   private Scope globalScope;
/*     */   
/*     */   private int currentComponentIndex;
/*     */   
/*     */   private IntStack componentIndexStack;
/*     */   
/*     */   private int componentIndexSource;
/*     */   
/*     */   private int currentAttackIndex;
/*     */   private Int2IntMap componentLocalStateMachines;
/*     */   private BitSet localStateMachineAutoResetStates;
/*     */   private final StateMappingHelper stateHelper;
/*     */   private List<Map.Entry<StateMappingHelper, StatePair[]>> modifiedStateMap;
/*     */   private IntSet blackboardBlockSets;
/*     */   private IntSet blockSensorResetBlockSets;
/*     */   private boolean requiresAttitudeOverrideMemory;
/*     */   private boolean trackInteractions;
/*  83 */   private InstructionType currentInstructionContext = InstructionType.Component;
/*     */   
/*     */   private ComponentContext currentComponentContext;
/*     */   
/*     */   @Nonnull
/*     */   private final StdScope sensorScope;
/*     */   
/*     */   @Nonnull
/*     */   private final Builder<?> roleBuilder;
/*     */   
/*     */   private final RoleStats roleStats;
/*     */   
/*     */   private StateEvaluator stateEvaluator;
/*     */   
/*     */   private ValueStore.Builder valueStoreBuilder;
/*  98 */   private final ArrayDeque<String> stateStack = new ArrayDeque<>();
/*     */ 
/*     */   
/*     */   public BuilderSupport(BuilderManager builderManager, @Nonnull NPCEntity npcEntity, Holder<EntityStore> holder, ExecutionContext executionContext, @Nonnull Builder<?> roleBuilder, RoleStats roleStats) {
/* 102 */     this.builderManager = builderManager;
/* 103 */     this.npcEntity = npcEntity;
/* 104 */     this.holder = holder;
/* 105 */     this.executionContext = executionContext;
/* 106 */     this.roleBuilder = roleBuilder;
/* 107 */     this.stateHelper = roleBuilder.getStateMappingHelper();
/* 108 */     this.roleStats = roleStats;
/* 109 */     this.sensorScope = EntitySupport.createScope(npcEntity);
/* 110 */     this.instructionSlotMappings = (Object2IntMap<String>)new Object2IntOpenHashMap();
/* 111 */     this.instructionSlotMappings.defaultReturnValue(-2147483648);
/*     */   }
/*     */   
/*     */   public BuilderManager getBuilderManager() {
/* 115 */     return this.builderManager;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public NPCEntity getEntity() {
/* 120 */     return this.npcEntity;
/*     */   }
/*     */   
/*     */   public Holder<EntityStore> getHolder() {
/* 124 */     return this.holder;
/*     */   }
/*     */   
/*     */   public ExecutionContext getExecutionContext() {
/* 128 */     return this.executionContext;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Builder<?> getParentSpawnable() {
/* 133 */     return this.roleBuilder;
/*     */   }
/*     */   
/*     */   public void setScope(Scope scope) {
/* 137 */     getExecutionContext().setScope(scope);
/*     */   }
/*     */   
/*     */   public void setGlobalScope(Scope scope) {
/* 141 */     this.globalScope = scope;
/*     */   }
/*     */   
/*     */   public Scope getGlobalScope() {
/* 145 */     return this.globalScope;
/*     */   }
/*     */   
/*     */   public void setRequireLeashPosition() {
/* 149 */     this.requireLeashPosition = true;
/*     */   }
/*     */   
/*     */   public int getFlagSlot(String name) {
/* 153 */     return this.flagSlotMapper.getSlot(name);
/*     */   }
/*     */   
/*     */   public Timer getTimerByName(String name) {
/* 157 */     return this.timerSlotMapper.getReference(name);
/*     */   }
/*     */   
/*     */   public int getBeaconMessageSlot(String name) {
/* 161 */     return this.beaconSlotMapper.getSlot(name);
/*     */   }
/*     */   
/*     */   public int getTargetSlot(String name) {
/* 165 */     return this.targetSlotMapper.getSlot(name);
/*     */   }
/*     */   
/*     */   public Alarm getAlarm(String name) {
/* 169 */     NPCEntity npc = (NPCEntity)this.holder.getComponent(NPCEntity.getComponentType());
/* 170 */     AlarmStore alarmStore = npc.getAlarmStore();
/* 171 */     return (Alarm)alarmStore.get((Entity)this.npcEntity, name);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Object2IntMap<String> getTargetSlotMappings() {
/* 176 */     return this.targetSlotMapper.getSlotMappings();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Int2ObjectMap<String> getTargetSlotToNameMap() {
/* 181 */     return this.targetSlotMapper.getNameMap();
/*     */   }
/*     */   
/*     */   public int getPositionSlot(String name) {
/* 185 */     return this.positionSlotMapper.getSlot(name);
/*     */   }
/*     */   
/*     */   public int getParameterSlot(String name) {
/* 189 */     return this.parameterSlotMapper.getSlot(name);
/*     */   }
/*     */   
/*     */   public int getSearchRaySlot(String name) {
/* 193 */     return this.searchRaySlotMapper.getSlot(name);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Vector3d[] allocatePositionSlots() {
/* 198 */     return allocatePositionSlots(this.positionSlotMapper);
/*     */   }
/*     */   
/*     */   public boolean requiresLeashPosition() {
/* 202 */     return this.requireLeashPosition;
/*     */   }
/*     */   
/*     */   public StateEvaluator getStateEvaluator() {
/* 206 */     return this.stateEvaluator;
/*     */   }
/*     */   
/*     */   public void setStateEvaluator(StateEvaluator stateEvaluator) {
/* 210 */     this.stateEvaluator = stateEvaluator;
/*     */   }
/*     */   
/*     */   public boolean[] allocateFlags() {
/* 214 */     int slotCount = this.flagSlotMapper.slotCount();
/* 215 */     if (slotCount == 0) return null;
/*     */     
/* 217 */     return new boolean[slotCount];
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Tickable[] allocateTimers() {
/* 222 */     List<Timer> referenceList = this.timerSlotMapper.getReferenceList();
/* 223 */     if (referenceList.isEmpty()) return null;
/*     */     
/* 225 */     return (Tickable[])referenceList.toArray(x$0 -> new Tickable[x$0]);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Vector3d[] allocateSearchRayPositionSlots() {
/* 230 */     return allocatePositionSlots(this.searchRaySlotMapper);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public StdScope getSensorScope() {
/* 235 */     return this.sensorScope;
/*     */   }
/*     */   
/*     */   public void setToNewComponent() {
/* 239 */     if (this.componentIndexStack == null) this.componentIndexStack = (IntStack)new IntArrayList(); 
/* 240 */     this.componentIndexStack.push(this.currentComponentIndex);
/* 241 */     this.currentComponentIndex = this.componentIndexSource++;
/*     */   }
/*     */   
/*     */   public void addComponentLocalStateMachine(int defaultState) {
/* 245 */     if (this.componentLocalStateMachines == null) {
/* 246 */       this.componentLocalStateMachines = (Int2IntMap)new Int2IntOpenHashMap();
/* 247 */       this.componentLocalStateMachines.defaultReturnValue(-2147483648);
/*     */     } 
/* 249 */     this.componentLocalStateMachines.put(getComponentIndex(), defaultState);
/*     */   }
/*     */   
/*     */   public int getComponentIndex() {
/* 253 */     return this.currentComponentIndex;
/*     */   }
/*     */   
/*     */   public void popComponent() {
/* 257 */     this.currentComponentIndex = this.componentIndexStack.popInt();
/*     */   }
/*     */   
/*     */   public boolean hasComponentLocalStateMachines() {
/* 261 */     return (this.componentLocalStateMachines != null);
/*     */   }
/*     */   
/*     */   public Int2IntMap getComponentLocalStateMachines() {
/* 265 */     return this.componentLocalStateMachines;
/*     */   }
/*     */   
/*     */   public void setLocalStateMachineAutoReset() {
/* 269 */     if (this.localStateMachineAutoResetStates == null) this.localStateMachineAutoResetStates = new BitSet(); 
/* 270 */     this.localStateMachineAutoResetStates.set(getComponentIndex());
/*     */   }
/*     */   
/*     */   public BitSet getLocalStateMachineAutoResetStates() {
/* 274 */     return this.localStateMachineAutoResetStates;
/*     */   }
/*     */   
/*     */   public StateMappingHelper getStateHelper() {
/* 278 */     return this.stateHelper;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Object2IntMap<String> getBeaconSlotMappings() {
/* 283 */     return this.beaconSlotMapper.getSlotMappings();
/*     */   }
/*     */   
/*     */   public boolean hasBlockEventSupport() {
/* 287 */     return (this.playerBlockEventSlotMapper != null || this.npcBlockEventSlotMapper != null);
/*     */   }
/*     */   
/*     */   public EventSlotMapper<BlockEventType> getPlayerBlockEventSlotMapper() {
/* 291 */     return this.playerBlockEventSlotMapper;
/*     */   }
/*     */   
/*     */   public EventSlotMapper<BlockEventType> getNPCBlockEventSlotMapper() {
/* 295 */     return this.npcBlockEventSlotMapper;
/*     */   }
/*     */   
/*     */   public boolean hasEntityEventSupport() {
/* 299 */     return (this.playerEntityEventSlotMapper != null || this.npcEntityEventSlotMapper != null);
/*     */   }
/*     */   
/*     */   public EventSlotMapper<EntityEventType> getPlayerEntityEventSlotMapper() {
/* 303 */     return this.playerEntityEventSlotMapper;
/*     */   }
/*     */   
/*     */   public EventSlotMapper<EntityEventType> getNPCEntityEventSlotMapper() {
/* 307 */     return this.npcEntityEventSlotMapper;
/*     */   }
/*     */   
/*     */   public int getInstructionSlot(@Nullable String name) {
/* 311 */     int slot = this.instructionSlotMappings.getInt(name);
/* 312 */     if (slot == Integer.MIN_VALUE) {
/* 313 */       slot = this.instructions.size();
/* 314 */       if (name != null && !name.isEmpty()) {
/*     */ 
/*     */         
/* 317 */         this.instructionSlotMappings.put(name, slot);
/* 318 */         this.instructionNameMappings.put(slot, name);
/*     */       } 
/* 320 */       this.instructions.add((Instruction)null);
/*     */     } 
/* 322 */     return slot;
/*     */   }
/*     */   
/*     */   public void putInstruction(int slot, Instruction instruction) {
/* 326 */     Objects.requireNonNull(instruction, "Instruction cannot be null when putting instruction");
/* 327 */     if (slot < 0 || slot >= this.instructions.size()) throw new IllegalArgumentException("Slot for putting instruction must be >= 0 and < the size of the list"); 
/* 328 */     if (this.instructions.get(slot) != null)
/* 329 */       throw new IllegalStateException(String.format("Duplicate instruction with name: %s", new Object[] { this.instructionNameMappings.get(slot) })); 
/* 330 */     this.instructions.set(slot, instruction);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Instruction[] getInstructionSlotMappings() {
/* 335 */     Instruction[] slots = (Instruction[])this.instructions.toArray(x$0 -> new Instruction[x$0]);
/*     */     
/* 337 */     for (int i = 0; i < slots.length; i++) {
/* 338 */       Instruction instruction = slots[i];
/* 339 */       if (instruction == null) throw new IllegalStateException("Instruction: " + (String)this.instructionNameMappings.get(i) + " doesn't exist"); 
/*     */     } 
/* 341 */     return slots;
/*     */   }
/*     */   
/*     */   public void setModifiedStateMap(@Nonnull StateMappingHelper helper, @Nonnull StatePair[] map) {
/* 345 */     if (this.modifiedStateMap == null) this.modifiedStateMap = (List<Map.Entry<StateMappingHelper, StatePair[]>>)new ObjectArrayList(); 
/* 346 */     this.modifiedStateMap.add((Map.Entry)Map.entry(helper, map));
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public StatePair getMappedStatePair(int index) {
/* 351 */     StatePair result = null;
/* 352 */     for (int i = this.modifiedStateMap.size() - 1; i >= 0; i--) {
/* 353 */       Map.Entry<StateMappingHelper, StatePair[]> entry = this.modifiedStateMap.get(i);
/* 354 */       result = ((StatePair[])entry.getValue())[index];
/* 355 */       index = ((StateMappingHelper)entry.getKey()).getComponentImportStateIndex(result.getFullStateName());
/* 356 */       if (index < 0)
/*     */         break; 
/* 358 */     }  Objects.requireNonNull(result, "Result should not be null after iterating mapped state pairs");
/* 359 */     return result;
/*     */   }
/*     */   
/*     */   public void popModifiedStateMap() {
/* 363 */     this.modifiedStateMap.removeLast();
/*     */   }
/*     */   
/*     */   public void requireBlockTypeBlackboard(int blockSet) {
/* 367 */     if (this.blackboardBlockSets == null) this.blackboardBlockSets = (IntSet)new IntOpenHashSet(); 
/* 368 */     this.blackboardBlockSets.add(blockSet);
/*     */   }
/*     */   
/*     */   public void registerBlockSensorResetAction(int blockSet) {
/* 372 */     if (this.blockSensorResetBlockSets == null) this.blockSensorResetBlockSets = (IntSet)new IntOpenHashSet(); 
/* 373 */     this.blockSensorResetBlockSets.add(blockSet);
/*     */   }
/*     */   
/*     */   public boolean requiresBlockTypeBlackboard() {
/* 377 */     return (this.blackboardBlockSets != null);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public IntList getBlockTypeBlackboardBlockSets() {
/* 382 */     if (this.blockSensorResetBlockSets != null) {
/* 383 */       this.blockSensorResetBlockSets.forEach(blockSet -> {
/*     */             if (!this.blackboardBlockSets.contains(blockSet)) {
/*     */               throw new IllegalStateException(String.format("No block sensors match BlockSet %s in ResetBlockSensors action", new Object[] { ((BlockSet)BlockSet.getAssetMap().getAsset(blockSet)).getId() }));
/*     */             }
/*     */           });
/*     */     }
/*     */     
/* 390 */     IntArrayList blockSets = new IntArrayList();
/* 391 */     blockSets.addAll((IntCollection)this.blackboardBlockSets);
/* 392 */     blockSets.trim();
/* 393 */     return IntLists.unmodifiable((IntList)blockSets);
/*     */   }
/*     */   
/*     */   public int getBlockEventSlot(BlockEventType type, int blockSet, double maxRange, boolean player) {
/* 397 */     if (player) {
/* 398 */       if (this.playerBlockEventSlotMapper == null) this.playerBlockEventSlotMapper = new EventSlotMapper<>(BlockEventType.class, BlockEventType.VALUES); 
/* 399 */       return this.playerBlockEventSlotMapper.getEventSlot(type, blockSet, maxRange);
/*     */     } 
/*     */     
/* 402 */     if (this.npcBlockEventSlotMapper == null) this.npcBlockEventSlotMapper = new EventSlotMapper<>(BlockEventType.class, BlockEventType.VALUES); 
/* 403 */     return this.npcBlockEventSlotMapper.getEventSlot(type, blockSet, maxRange);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public IntSet getBlockChangeSets(BlockEventType type) {
/* 408 */     IntSet playerEventSets = (this.playerBlockEventSlotMapper != null) ? (IntSet)this.playerBlockEventSlotMapper.getEventSets().get(type) : null;
/* 409 */     IntSet npcEventSets = (this.npcBlockEventSlotMapper != null) ? (IntSet)this.npcBlockEventSlotMapper.getEventSets().get(type) : null;
/* 410 */     if (playerEventSets == null && npcEventSets == null) return null;
/*     */     
/* 412 */     IntOpenHashSet set = new IntOpenHashSet();
/* 413 */     if (playerEventSets != null) set.addAll((IntCollection)playerEventSets); 
/* 414 */     if (npcEventSets != null) set.addAll((IntCollection)npcEventSets); 
/* 415 */     set.trim();
/* 416 */     return IntSets.unmodifiable((IntSet)set);
/*     */   }
/*     */   
/*     */   public int getEntityEventSlot(EntityEventType type, int npcGroup, double maxRange, boolean player) {
/* 420 */     if (player) {
/* 421 */       if (this.playerEntityEventSlotMapper == null) this.playerEntityEventSlotMapper = new EventSlotMapper<>(EntityEventType.class, EntityEventType.VALUES); 
/* 422 */       return this.playerEntityEventSlotMapper.getEventSlot(type, npcGroup, maxRange);
/*     */     } 
/*     */     
/* 425 */     if (this.npcEntityEventSlotMapper == null) this.npcEntityEventSlotMapper = new EventSlotMapper<>(EntityEventType.class, EntityEventType.VALUES); 
/* 426 */     return this.npcEntityEventSlotMapper.getEventSlot(type, npcGroup, maxRange);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public IntSet getEventNPCGroups(EntityEventType type) {
/* 431 */     IntSet playerEventSets = (this.playerEntityEventSlotMapper != null) ? (IntSet)this.playerEntityEventSlotMapper.getEventSets().get(type) : null;
/* 432 */     IntSet npcEventSets = (this.npcEntityEventSlotMapper != null) ? (IntSet)this.npcEntityEventSlotMapper.getEventSets().get(type) : null;
/* 433 */     if (playerEventSets == null && npcEventSets == null) return null;
/*     */     
/* 435 */     IntOpenHashSet set = new IntOpenHashSet();
/* 436 */     if (playerEventSets != null) set.addAll((IntCollection)playerEventSets); 
/* 437 */     if (npcEventSets != null) set.addAll((IntCollection)npcEventSets); 
/* 438 */     set.trim();
/* 439 */     return IntSets.unmodifiable((IntSet)set);
/*     */   }
/*     */   
/*     */   public void requireAttitudeOverrideMemory() {
/* 443 */     this.requiresAttitudeOverrideMemory = true;
/*     */   }
/*     */   
/*     */   public void trackInteractions() {
/* 447 */     this.trackInteractions = true;
/*     */   }
/*     */   
/*     */   public boolean isTrackInteractions() {
/* 451 */     return this.trackInteractions;
/*     */   }
/*     */   
/*     */   public boolean requiresAttitudeOverrideMemory() {
/* 455 */     return this.requiresAttitudeOverrideMemory;
/*     */   }
/*     */   
/*     */   public void setCurrentInstructionContext(InstructionType context) {
/* 459 */     this.currentInstructionContext = context;
/*     */   }
/*     */   
/*     */   public InstructionType getCurrentInstructionContext() {
/* 463 */     return this.currentInstructionContext;
/*     */   }
/*     */   
/*     */   public ComponentContext getCurrentComponentContext() {
/* 467 */     return this.currentComponentContext;
/*     */   }
/*     */   
/*     */   public void setCurrentComponentContext(ComponentContext currentComponentContext) {
/* 471 */     this.currentComponentContext = currentComponentContext;
/*     */   }
/*     */   
/*     */   public RoleStats getRoleStats() {
/* 475 */     return this.roleStats;
/*     */   }
/*     */   
/*     */   public int getNextAttackIndex() {
/* 479 */     return this.currentAttackIndex++;
/*     */   }
/*     */   
/*     */   public int getValueStoreStringSlot(String name) {
/* 483 */     if (this.valueStoreBuilder == null) this.valueStoreBuilder = new ValueStore.Builder(); 
/* 484 */     return this.valueStoreBuilder.getStringSlot(name);
/*     */   }
/*     */   
/*     */   public int getValueStoreIntSlot(String name) {
/* 488 */     if (this.valueStoreBuilder == null) this.valueStoreBuilder = new ValueStore.Builder(); 
/* 489 */     return this.valueStoreBuilder.getIntSlot(name);
/*     */   }
/*     */   
/*     */   public int getValueStoreDoubleSlot(String name) {
/* 493 */     if (this.valueStoreBuilder == null) this.valueStoreBuilder = new ValueStore.Builder(); 
/* 494 */     return this.valueStoreBuilder.getDoubleSlot(name);
/*     */   }
/*     */   
/*     */   public ValueStore.Builder getValueStoreBuilder() {
/* 498 */     return this.valueStoreBuilder;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getCurrentStateName() {
/* 503 */     return this.stateStack.peek();
/*     */   }
/*     */   
/*     */   public void pushCurrentStateName(@Nonnull String currentStateName) {
/* 507 */     this.stateStack.push(currentStateName);
/*     */   }
/*     */   
/*     */   public void popCurrentStateName() {
/* 511 */     this.stateStack.pop();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static Vector3d[] allocatePositionSlots(@Nonnull SlotMapper mapper) {
/* 516 */     int slotCount = mapper.slotCount();
/* 517 */     if (slotCount == 0) return null;
/*     */     
/* 519 */     Vector3d[] slots = new Vector3d[slotCount];
/* 520 */     for (int i = 0; i < slots.length; i++) {
/* 521 */       slots[i] = new Vector3d();
/*     */     }
/* 523 */     return slots;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\BuilderSupport.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */