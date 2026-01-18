/*     */ package com.hypixel.hytale.server.npc.asset.builder;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*     */ import java.util.BitSet;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
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
/*     */ class StateMap
/*     */   implements StateMappingHelper.IStateMap
/*     */ {
/* 281 */   private final Int2ObjectOpenHashMap<String> stateNameMap = new Int2ObjectOpenHashMap();
/*     */   @Nonnull
/*     */   private final Object2IntOpenHashMap<String> stateIndexMap;
/*     */   private int stateIndexSource;
/*     */   @Nullable
/* 286 */   private BitSet stateSensors = new BitSet();
/*     */   @Nullable
/* 288 */   private BitSet stateSetters = new BitSet();
/*     */   @Nullable
/* 290 */   private BitSet stateRequirers = new BitSet();
/*     */ 
/*     */   
/*     */   private StateMap() {
/* 294 */     this.stateIndexMap = new Object2IntOpenHashMap();
/* 295 */     this.stateIndexMap.defaultReturnValue(-2147483648);
/*     */   }
/*     */   
/*     */   private int getOrCreateIndex(String name) {
/* 299 */     int index = this.stateIndexMap.getInt(name);
/* 300 */     if (index == Integer.MIN_VALUE) {
/* 301 */       index = this.stateIndexSource++;
/* 302 */       this.stateIndexMap.put(name, index);
/* 303 */       this.stateNameMap.put(index, name);
/*     */     } 
/* 305 */     return index;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAndPutSensorIndex(String state) {
/* 310 */     int index = getOrCreateIndex(state);
/* 311 */     this.stateSensors.set(index);
/* 312 */     return index;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAndPutSetterIndex(String targetState) {
/* 317 */     int index = getOrCreateIndex(targetState);
/* 318 */     this.stateSetters.set(index);
/* 319 */     return index;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAndPutRequirerIndex(String targetState) {
/* 324 */     int index = getOrCreateIndex(targetState);
/* 325 */     this.stateRequirers.set(index);
/* 326 */     return index;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getStateIndex(String state) {
/* 331 */     Objects.requireNonNull(state, "State must not be null when fetching index");
/* 332 */     return this.stateIndexMap.getInt(state);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStateName(int index) {
/* 337 */     return (String)this.stateNameMap.get(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public void validate(String configName, @Nullable String parent, @Nonnull List<String> errors) {
/* 342 */     this.stateSetters.xor(this.stateSensors);
/* 343 */     if (this.stateSetters.cardinality() > 0) {
/* 344 */       errors.add(String.format("%s: State sensor or State setter action/motion exists without accompanying state/setter: %s%s", new Object[] { configName, (parent != null) ? (parent + ".") : "", this.stateNameMap.get(this.stateSetters.nextSetBit(0)) }));
/*     */     }
/* 346 */     this.stateRequirers.andNot(this.stateSensors);
/* 347 */     if (this.stateRequirers.cardinality() > 0) {
/* 348 */       errors.add(String.format("%s: State required by a parameter does not exist: %s%s", new Object[] { configName, (parent != null) ? (parent + ".") : "", this.stateNameMap.get(this.stateRequirers.nextSetBit(0)) }));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 354 */     return this.stateNameMap.isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 359 */     return this.stateNameMap.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public void optimise() {
/* 364 */     this.stateSensors = null;
/* 365 */     this.stateSetters = null;
/* 366 */     this.stateRequirers = null;
/* 367 */     this.stateNameMap.trim();
/* 368 */     this.stateIndexMap.trim();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\StateMappingHelper$StateMap.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */