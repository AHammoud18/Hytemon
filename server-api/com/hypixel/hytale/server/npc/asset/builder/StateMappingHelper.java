/*     */ package com.hypixel.hytale.server.npc.asset.builder;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.validators.StateStringValidator;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.BitSet;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ public class StateMappingHelper
/*     */ {
/*     */   public static final String DEFAULT_STATE = "start";
/*     */   public static final String DEFAULT_SUB_STATE = "Default";
/*     */   public static final String DEFAULT_STATE_PARAMETER = "DefaultState";
/*     */   public static final String STATE_CHANGE_RESET_PARAMETER = "ResetOnStateChange";
/*     */   @Nullable
/*  30 */   private StateMap mainStateMap = new StateMap();
/*     */   
/*     */   private int[] allMainStates;
/*     */   @Nullable
/*  34 */   private Int2ObjectOpenHashMap<IStateMap> subStateMap = new Int2ObjectOpenHashMap();
/*     */   
/*     */   private int depth;
/*     */   @Nullable
/*  38 */   private ArrayDeque<StateDepth> currentParentState = new ArrayDeque<>();
/*     */   
/*     */   private boolean component = true;
/*     */   
/*     */   private boolean hasStateEvaluator;
/*     */   
/*     */   private boolean requiresStateEvaluator;
/*     */   
/*     */   private String defaultSubState;
/*     */   
/*     */   private String defaultComponentLocalState;
/*     */   private int defaultComponentLocalStateIndex;
/*     */   private boolean componentLocalStateAutoReset;
/*     */   private Object2IntOpenHashMap<String> componentImportStateMappings;
/*     */   private SingletonStateMap singletonDefaultStateMap;
/*     */   
/*     */   public int[] getAllMainStates() {
/*  55 */     return this.allMainStates;
/*     */   }
/*     */   
/*     */   public int getHighestSubStateIndex(int mainStateIndex) {
/*  59 */     return ((IStateMap)this.subStateMap.get(mainStateIndex)).size() - 1;
/*     */   }
/*     */   
/*     */   public void getAndPutSensorIndex(String state, String subState, @Nonnull BiConsumer<Integer, Integer> setter) {
/*  63 */     this.currentParentState.push(new StateDepth(this.depth, state));
/*  64 */     Objects.requireNonNull(this.mainStateMap); getAndPutIndex(state, subState, setter, this.mainStateMap::getAndPutSensorIndex, (i, s) -> {
/*     */           IStateMap helper = initialiseDefaultSubStates(i.intValue());
/*     */           return Integer.valueOf(helper.getAndPutSensorIndex(s));
/*     */         });
/*     */   }
/*     */   
/*     */   public void getAndPutSetterIndex(String state, String subState, @Nonnull BiConsumer<Integer, Integer> setter) {
/*  71 */     Objects.requireNonNull(this.mainStateMap); getAndPutIndex(state, subState, setter, this.mainStateMap::getAndPutSetterIndex, (i, s) -> {
/*     */           IStateMap helper = initialiseDefaultSubStates(i.intValue());
/*     */           return Integer.valueOf(helper.getAndPutSetterIndex(s));
/*     */         });
/*     */   }
/*     */   
/*     */   public void getAndPutStateRequirerIndex(String state, String subState, @Nonnull BiConsumer<Integer, Integer> setter) {
/*  78 */     Objects.requireNonNull(this.mainStateMap); getAndPutIndex(state, subState, setter, this.mainStateMap::getAndPutRequirerIndex, (i, s) -> {
/*     */           IStateMap helper = initialiseDefaultSubStates(i.intValue());
/*     */           return Integer.valueOf(helper.getAndPutRequirerIndex(s));
/*     */         });
/*     */   }
/*     */   
/*     */   private void getAndPutIndex(String state, @Nullable String subState, @Nonnull BiConsumer<Integer, Integer> setter, @Nonnull Function<String, Integer> mainStateFunction, @Nonnull BiFunction<Integer, String, Integer> subStateFunction) {
/*  85 */     Integer index = mainStateFunction.apply(state);
/*     */     
/*  87 */     if (subState == null) {
/*  88 */       setter.accept(index, Integer.valueOf(-1));
/*     */       
/*     */       return;
/*     */     } 
/*  92 */     Integer subStateIndex = subStateFunction.apply(index, subState);
/*  93 */     setter.accept(index, subStateIndex);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private IStateMap initialiseDefaultSubStates(int index) {
/*  98 */     return (IStateMap)this.subStateMap.computeIfAbsent(index, v -> {
/*     */           StateMap map = new StateMap();
/*     */           map.getAndPutSensorIndex(this.defaultSubState);
/*     */           map.getAndPutSetterIndex(this.defaultSubState);
/*     */           return map;
/*     */         });
/*     */   }
/*     */   
/*     */   public void validate(String configName, @Nonnull List<String> errors) {
/* 107 */     this.mainStateMap.validate(configName, null, errors);
/* 108 */     this.subStateMap.forEach((i, v) -> v.validate(configName, this.mainStateMap.getStateName(i.intValue()), errors));
/* 109 */     if (!this.hasStateEvaluator && this.requiresStateEvaluator) {
/* 110 */       errors.add(String.format("%s: Expects a state evaluator but does not have one defined", new Object[] { configName }));
/*     */     }
/*     */   }
/*     */   
/*     */   public int getStateIndex(String state) {
/* 115 */     return this.mainStateMap.getStateIndex(state);
/*     */   }
/*     */   
/*     */   public int getSubStateIndex(int index, String subState) {
/* 119 */     return ((IStateMap)this.subStateMap.get(index)).getStateIndex(subState);
/*     */   }
/*     */   
/*     */   public String getStateName(int index) {
/* 123 */     return this.mainStateMap.getStateName(index);
/*     */   }
/*     */   
/*     */   public String getSubStateName(int index, int subState) {
/* 127 */     return ((IStateMap)this.subStateMap.get(index)).getStateName(subState);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getCurrentParentState() {
/* 132 */     if (this.currentParentState.isEmpty()) return null;
/*     */     
/* 134 */     return ((StateDepth)this.currentParentState.peek()).state;
/*     */   }
/*     */   
/*     */   public void increaseDepth() {
/* 138 */     this.depth++;
/*     */   }
/*     */   
/*     */   public void decreaseDepth() {
/* 142 */     this.depth--;
/* 143 */     if (!this.currentParentState.isEmpty() && this.depth < ((StateDepth)this.currentParentState.peek()).depth) this.currentParentState.pop(); 
/*     */   }
/*     */   
/*     */   public void setDefaultSubState(String subState) {
/* 147 */     this.defaultSubState = subState;
/*     */   }
/*     */   
/*     */   public String getDefaultSubState() {
/* 151 */     return this.defaultSubState;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setNotComponent() {
/* 156 */     this.mainStateMap.getAndPutSensorIndex("start");
/* 157 */     this.mainStateMap.getAndPutSetterIndex("start");
/* 158 */     this.component = false;
/*     */   }
/*     */   
/*     */   public boolean isComponent() {
/* 162 */     return this.component;
/*     */   }
/*     */   
/*     */   public boolean hasComponentStates() {
/* 166 */     return (this.component && this.mainStateMap != null);
/*     */   }
/*     */   
/*     */   public void initialiseComponentState(@Nonnull BuilderSupport support) {
/* 170 */     support.setToNewComponent();
/* 171 */     support.addComponentLocalStateMachine(this.defaultComponentLocalStateIndex);
/* 172 */     if (this.componentLocalStateAutoReset) support.setLocalStateMachineAutoReset(); 
/*     */   }
/*     */   
/*     */   public void popComponentState(@Nonnull BuilderSupport support) {
/* 176 */     support.popComponent();
/*     */   }
/*     */ 
/*     */   
/*     */   public void readComponentDefaultLocalState(@Nonnull JsonObject data) {
/* 181 */     String state = BuilderBase.readString(data, "DefaultState", null);
/* 182 */     if (state != null) {
/* 183 */       StateStringValidator validator = StateStringValidator.get();
/* 184 */       if (!validator.test(state)) throw new IllegalStateException(validator.errorMessage(state)); 
/* 185 */       if (validator.hasMainState()) throw new IllegalStateException(String.format("Default component local state must be defined with a '.' prefix: %s", new Object[] { validator.getMainState() })); 
/* 186 */       this.defaultComponentLocalState = validator.getSubState();
/* 187 */       this.defaultComponentLocalStateIndex = this.mainStateMap.getAndPutSetterIndex(this.defaultComponentLocalState);
/*     */     } 
/* 189 */     JsonElement resetValue = data.get("ResetOnStateChange");
/* 190 */     if (resetValue != null) {
/* 191 */       this.componentLocalStateAutoReset = BuilderBase.expectBooleanElement(resetValue, "ResetOnStateChange");
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean hasDefaultLocalState() {
/* 196 */     return (this.defaultComponentLocalState != null);
/*     */   }
/*     */   
/*     */   public String getDefaultLocalState() {
/* 200 */     return this.defaultComponentLocalState;
/*     */   }
/*     */   
/*     */   public void setComponentImportStateMappings(@Nonnull JsonArray states) {
/* 204 */     this.componentImportStateMappings = new Object2IntOpenHashMap();
/* 205 */     this.componentImportStateMappings.defaultReturnValue(-2147483648);
/* 206 */     StateStringValidator validator = StateStringValidator.mainStateOnly();
/* 207 */     for (int i = 0; i < states.size(); i++) {
/* 208 */       String string = states.get(i).getAsString();
/* 209 */       if (!validator.test(string)) {
/* 210 */         throw new IllegalStateException(validator.errorMessage(string));
/*     */       }
/* 212 */       getAndPutSensorIndex(validator.getMainState(), null, (m, s) -> { 
/* 213 */           }); this.componentImportStateMappings.put(validator.getMainState(), i);
/*     */     } 
/* 215 */     this.componentImportStateMappings.trim();
/*     */   }
/*     */   
/*     */   public int getComponentImportStateIndex(String state) {
/* 219 */     return (this.componentImportStateMappings == null) ? Integer.MIN_VALUE : this.componentImportStateMappings.getInt(state);
/*     */   }
/*     */   
/*     */   public int importedStateCount() {
/* 223 */     return (this.componentImportStateMappings == null) ? 0 : this.componentImportStateMappings.size();
/*     */   }
/*     */   
/*     */   public void setRequiresStateEvaluator() {
/* 227 */     this.requiresStateEvaluator = true;
/*     */   }
/*     */   
/*     */   public void setHasStateEvaluator() {
/* 231 */     this.hasStateEvaluator = true;
/*     */   }
/*     */   
/*     */   public void optimise() {
/* 235 */     this.currentParentState = null;
/* 236 */     if (this.mainStateMap.isEmpty()) {
/* 237 */       this.mainStateMap = null;
/* 238 */       this.subStateMap = null;
/*     */       return;
/*     */     } 
/* 241 */     ObjectIterator<Int2ObjectMap.Entry<IStateMap>> iterator = Int2ObjectMaps.fastIterator((Int2ObjectMap)this.subStateMap);
/* 242 */     while (iterator.hasNext()) {
/* 243 */       Int2ObjectMap.Entry<IStateMap> next = (Int2ObjectMap.Entry<IStateMap>)iterator.next();
/* 244 */       IStateMap map = (IStateMap)next.getValue();
/* 245 */       if (map.size() == 1) {
/* 246 */         if (this.singletonDefaultStateMap == null) this.singletonDefaultStateMap = new SingletonStateMap(this.defaultSubState); 
/* 247 */         next.setValue(this.singletonDefaultStateMap); continue;
/*     */       } 
/* 249 */       map.optimise();
/*     */     } 
/*     */     
/* 252 */     this.subStateMap.trim();
/* 253 */     this.mainStateMap.optimise();
/*     */     
/* 255 */     this.allMainStates = this.mainStateMap.stateNameMap.keySet().toIntArray();
/*     */   }
/*     */   
/*     */   private static class StateDepth {
/*     */     private final int depth;
/*     */     private final String state;
/*     */     
/*     */     private StateDepth(int depth, String state) {
/* 263 */       this.depth = depth;
/* 264 */       this.state = state;
/*     */     }
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
/*     */   private static class StateMap
/*     */     implements IStateMap
/*     */   {
/* 281 */     private final Int2ObjectOpenHashMap<String> stateNameMap = new Int2ObjectOpenHashMap();
/*     */     @Nonnull
/*     */     private final Object2IntOpenHashMap<String> stateIndexMap;
/*     */     private int stateIndexSource;
/*     */     @Nullable
/* 286 */     private BitSet stateSensors = new BitSet();
/*     */     @Nullable
/* 288 */     private BitSet stateSetters = new BitSet();
/*     */     @Nullable
/* 290 */     private BitSet stateRequirers = new BitSet();
/*     */ 
/*     */     
/*     */     private StateMap() {
/* 294 */       this.stateIndexMap = new Object2IntOpenHashMap();
/* 295 */       this.stateIndexMap.defaultReturnValue(-2147483648);
/*     */     }
/*     */     
/*     */     private int getOrCreateIndex(String name) {
/* 299 */       int index = this.stateIndexMap.getInt(name);
/* 300 */       if (index == Integer.MIN_VALUE) {
/* 301 */         index = this.stateIndexSource++;
/* 302 */         this.stateIndexMap.put(name, index);
/* 303 */         this.stateNameMap.put(index, name);
/*     */       } 
/* 305 */       return index;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getAndPutSensorIndex(String state) {
/* 310 */       int index = getOrCreateIndex(state);
/* 311 */       this.stateSensors.set(index);
/* 312 */       return index;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getAndPutSetterIndex(String targetState) {
/* 317 */       int index = getOrCreateIndex(targetState);
/* 318 */       this.stateSetters.set(index);
/* 319 */       return index;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getAndPutRequirerIndex(String targetState) {
/* 324 */       int index = getOrCreateIndex(targetState);
/* 325 */       this.stateRequirers.set(index);
/* 326 */       return index;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getStateIndex(String state) {
/* 331 */       Objects.requireNonNull(state, "State must not be null when fetching index");
/* 332 */       return this.stateIndexMap.getInt(state);
/*     */     }
/*     */ 
/*     */     
/*     */     public String getStateName(int index) {
/* 337 */       return (String)this.stateNameMap.get(index);
/*     */     }
/*     */ 
/*     */     
/*     */     public void validate(String configName, @Nullable String parent, @Nonnull List<String> errors) {
/* 342 */       this.stateSetters.xor(this.stateSensors);
/* 343 */       if (this.stateSetters.cardinality() > 0) {
/* 344 */         errors.add(String.format("%s: State sensor or State setter action/motion exists without accompanying state/setter: %s%s", new Object[] { configName, (parent != null) ? (parent + ".") : "", this.stateNameMap.get(this.stateSetters.nextSetBit(0)) }));
/*     */       }
/* 346 */       this.stateRequirers.andNot(this.stateSensors);
/* 347 */       if (this.stateRequirers.cardinality() > 0) {
/* 348 */         errors.add(String.format("%s: State required by a parameter does not exist: %s%s", new Object[] { configName, (parent != null) ? (parent + ".") : "", this.stateNameMap.get(this.stateRequirers.nextSetBit(0)) }));
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 354 */       return this.stateNameMap.isEmpty();
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 359 */       return this.stateNameMap.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void optimise() {
/* 364 */       this.stateSensors = null;
/* 365 */       this.stateSetters = null;
/* 366 */       this.stateRequirers = null;
/* 367 */       this.stateNameMap.trim();
/* 368 */       this.stateIndexMap.trim();
/*     */     }
/*     */   }
/*     */   
/*     */   private static class SingletonStateMap implements IStateMap {
/*     */     private final String stateName;
/*     */     
/*     */     private SingletonStateMap(String name) {
/* 376 */       this.stateName = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getAndPutSensorIndex(String state) {
/* 381 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getAndPutSetterIndex(String targetState) {
/* 386 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getAndPutRequirerIndex(String targetState) {
/* 391 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getStateIndex(@Nonnull String state) {
/* 396 */       if (!state.equals(this.stateName)) return Integer.MIN_VALUE; 
/* 397 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getStateName(int index) {
/* 402 */       return this.stateName;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void validate(String configName, String parent, List<String> errors) {}
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 411 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 416 */       return 1;
/*     */     }
/*     */     
/*     */     public void optimise() {}
/*     */   }
/*     */   
/*     */   private static interface IStateMap {
/*     */     int getAndPutSensorIndex(String param1String);
/*     */     
/*     */     int getAndPutSetterIndex(String param1String);
/*     */     
/*     */     int getAndPutRequirerIndex(String param1String);
/*     */     
/*     */     int getStateIndex(String param1String);
/*     */     
/*     */     String getStateName(int param1Int);
/*     */     
/*     */     void validate(String param1String1, String param1String2, List<String> param1List);
/*     */     
/*     */     boolean isEmpty();
/*     */     
/*     */     int size();
/*     */     
/*     */     void optimise();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\StateMappingHelper.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */