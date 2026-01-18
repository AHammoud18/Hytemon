/*     */ package com.hypixel.hytale.server.npc.instructions;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.function.consumer.QuadConsumer;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.instructions.builders.BuilderInstruction;
/*     */ import com.hypixel.hytale.server.npc.movement.controllers.MotionController;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import com.hypixel.hytale.server.npc.role.support.DebugSupport;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*     */ import com.hypixel.hytale.server.npc.util.ComponentInfo;
/*     */ import com.hypixel.hytale.server.npc.util.IAnnotatedComponent;
/*     */ import com.hypixel.hytale.server.npc.util.IAnnotatedComponentCollection;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class Instruction implements RoleStateChange, IAnnotatedComponentCollection {
/*  29 */   public static final Instruction[] EMPTY_ARRAY = new Instruction[0];
/*  30 */   public static final HytaleLogger LOGGER = NPCPlugin.get().getLogger();
/*     */   
/*     */   protected IAnnotatedComponent parent;
/*     */   
/*     */   @Nullable
/*     */   protected final String name;
/*     */   
/*     */   @Nullable
/*     */   protected final String tag;
/*     */   
/*     */   protected final Sensor sensor;
/*     */   
/*     */   protected int index;
/*     */   
/*     */   protected final Instruction[] instructionList;
/*     */   
/*     */   @Nullable
/*     */   protected final BodyMotion bodyMotion;
/*     */   
/*     */   @Nullable
/*     */   protected final HeadMotion headMotion;
/*     */   @Nonnull
/*     */   protected final ActionList actions;
/*     */   protected final double weight;
/*     */   protected final boolean treeMode;
/*     */   protected final boolean invertTreeModeResult;
/*     */   protected boolean continueAfter;
/*     */   @Nullable
/*     */   protected Instruction parentTreeModeStep;
/*     */   
/*     */   private Instruction(Instruction[] instructionList, @Nonnull BuilderSupport support) {
/*  61 */     this.tag = null;
/*  62 */     this.name = "Root";
/*  63 */     this.sensor = Sensor.NULL;
/*  64 */     this.instructionList = instructionList;
/*  65 */     this.bodyMotion = null;
/*  66 */     this.headMotion = null;
/*  67 */     this.actions = ActionList.EMPTY_ACTION_LIST;
/*  68 */     this.continueAfter = false;
/*  69 */     this.treeMode = false;
/*  70 */     this.invertTreeModeResult = false;
/*  71 */     this.weight = 1.0D;
/*     */     
/*  73 */     int index = support.getInstructionSlot(this.name);
/*  74 */     support.putInstruction(index, this);
/*     */   }
/*     */   
/*     */   public Instruction(@Nonnull BuilderInstruction builder, Sensor sensor, @Nullable Instruction[] instructionList, @Nonnull BuilderSupport support) {
/*  78 */     this.tag = builder.getTag();
/*  79 */     this.name = builder.getName();
/*  80 */     this.sensor = sensor;
/*  81 */     if (instructionList != null) {
/*  82 */       this.instructionList = instructionList;
/*  83 */       this.bodyMotion = null;
/*  84 */       this.headMotion = null;
/*  85 */       this.actions = ActionList.EMPTY_ACTION_LIST;
/*     */     } else {
/*  87 */       this.instructionList = EMPTY_ARRAY;
/*  88 */       this.bodyMotion = builder.getBodyMotion(support);
/*  89 */       this.headMotion = builder.getHeadMotion(support);
/*  90 */       this.actions = builder.getActionList(support);
/*     */     } 
/*  92 */     this.continueAfter = builder.isContinueAfter();
/*  93 */     this.treeMode = builder.isTreeMode();
/*  94 */     this.invertTreeModeResult = builder.isInvertTreeModeResult(support);
/*  95 */     this.weight = builder.getChance(support);
/*     */     
/*  97 */     int index = support.getInstructionSlot(this.name);
/*  98 */     support.putInstruction(index, this);
/*     */   }
/*     */   
/*     */   public Sensor getSensor() {
/* 102 */     return this.sensor;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getDebugTag() {
/* 107 */     return this.tag;
/*     */   }
/*     */   
/*     */   public double getWeight() {
/* 111 */     return this.weight;
/*     */   }
/*     */   
/*     */   public boolean isContinueAfter() {
/* 115 */     return this.continueAfter;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public BodyMotion getBodyMotion() {
/* 120 */     return this.bodyMotion;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public HeadMotion getHeadMotion() {
/* 125 */     return this.headMotion;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerWithSupport(Role role) {
/* 134 */     this.sensor.registerWithSupport(role);
/* 135 */     for (Instruction instruction : this.instructionList) {
/* 136 */       instruction.registerWithSupport(role);
/*     */     }
/* 138 */     if (this.bodyMotion != null) this.bodyMotion.registerWithSupport(role); 
/* 139 */     if (this.headMotion != null) this.headMotion.registerWithSupport(role); 
/* 140 */     this.actions.registerWithSupport(role);
/*     */   }
/*     */ 
/*     */   
/*     */   public void motionControllerChanged(@Nullable Ref<EntityStore> ref, @Nonnull NPCEntity npcComponent, MotionController motionController, @Nullable ComponentAccessor<EntityStore> componentAccessor) {
/* 145 */     this.sensor.motionControllerChanged(ref, npcComponent, motionController, componentAccessor);
/* 146 */     forEachInstruction((instruction, motionController1) -> instruction.motionControllerChanged(ref, npcComponent, motionController1, componentAccessor), motionController);
/* 147 */     if (this.bodyMotion != null) this.bodyMotion.motionControllerChanged(ref, npcComponent, motionController, componentAccessor); 
/* 148 */     if (this.headMotion != null) this.headMotion.motionControllerChanged(ref, npcComponent, motionController, componentAccessor); 
/* 149 */     this.actions.motionControllerChanged(ref, npcComponent, motionController, componentAccessor);
/*     */   }
/*     */ 
/*     */   
/*     */   public void loaded(Role role) {
/* 154 */     this.sensor.loaded(role);
/* 155 */     forEachInstruction(Instruction::loaded, role);
/* 156 */     if (this.bodyMotion != null) this.bodyMotion.loaded(role); 
/* 157 */     if (this.headMotion != null) this.headMotion.loaded(role); 
/* 158 */     this.actions.loaded(role);
/*     */   }
/*     */ 
/*     */   
/*     */   public void spawned(Role role) {
/* 163 */     this.sensor.spawned(role);
/* 164 */     forEachInstruction(Instruction::spawned, role);
/* 165 */     if (this.bodyMotion != null) this.bodyMotion.spawned(role); 
/* 166 */     if (this.headMotion != null) this.headMotion.spawned(role); 
/* 167 */     this.actions.spawned(role);
/*     */   }
/*     */ 
/*     */   
/*     */   public void unloaded(Role role) {
/* 172 */     this.sensor.unloaded(role);
/* 173 */     forEachInstruction(Instruction::unloaded, role);
/* 174 */     if (this.bodyMotion != null) this.bodyMotion.unloaded(role); 
/* 175 */     if (this.headMotion != null) this.headMotion.unloaded(role); 
/* 176 */     this.actions.unloaded(role);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removed(Role role) {
/* 181 */     this.sensor.removed(role);
/* 182 */     forEachInstruction(Instruction::removed, role);
/* 183 */     if (this.bodyMotion != null) this.bodyMotion.removed(role); 
/* 184 */     if (this.headMotion != null) this.headMotion.removed(role); 
/* 185 */     this.actions.removed(role);
/*     */   }
/*     */ 
/*     */   
/*     */   public void teleported(Role role, World from, World to) {
/* 190 */     this.sensor.teleported(role, from, to);
/* 191 */     forEachInstruction(Instruction::teleported, role, from, to);
/* 192 */     if (this.bodyMotion != null) this.bodyMotion.teleported(role, from, to); 
/* 193 */     if (this.headMotion != null) this.headMotion.teleported(role, from, to); 
/* 194 */     this.actions.teleported(role, from, to);
/*     */   }
/*     */ 
/*     */   
/*     */   public int componentCount() {
/* 199 */     int count = 1;
/* 200 */     count += this.actions.actionCount();
/* 201 */     count += this.instructionList.length;
/* 202 */     if (this.bodyMotion != null) count++; 
/* 203 */     if (this.headMotion != null) count++; 
/* 204 */     return count;
/*     */   }
/*     */ 
/*     */   
/*     */   public IAnnotatedComponent getComponent(int index) {
/* 209 */     if (index < 1) return this.sensor; 
/* 210 */     if (index <= this.actions.actionCount()) return this.actions.getComponent(index - 1); 
/* 211 */     if (index < componentCount()) {
/* 212 */       if (this.bodyMotion != null) {
/* 213 */         if (this.headMotion != null && index == componentCount() - 1) return this.headMotion; 
/* 214 */         return this.bodyMotion;
/*     */       } 
/* 216 */       if (this.headMotion != null) return this.headMotion; 
/* 217 */       return (IAnnotatedComponent)this.instructionList[index - 1];
/*     */     } 
/* 219 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */ 
/*     */   
/*     */   public void getInfo(Role role, @Nonnull ComponentInfo holder) {
/* 224 */     if (this.name != null && !this.name.isEmpty()) holder.addField("Name: " + this.name);
/*     */   
/*     */   }
/*     */   
/*     */   public IAnnotatedComponent getParent() {
/* 229 */     return this.parent;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getIndex() {
/* 234 */     return this.index;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String getLabel() {
/* 240 */     String tag = getDebugTag();
/* 241 */     if (tag != null) {
/* 242 */       return tag;
/*     */     }
/*     */     
/* 245 */     return (this.index >= 0) ? String.format("[%s]%s", new Object[] { Integer.valueOf(this.index), getClass().getSimpleName() }) : getClass().getSimpleName();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContext(IAnnotatedComponent parent, int index) {
/* 250 */     this.parent = parent;
/* 251 */     this.index = index;
/* 252 */     this.sensor.setContext((IAnnotatedComponent)this, -1);
/* 253 */     if (this.bodyMotion != null) this.bodyMotion.setContext((IAnnotatedComponent)this, -1); 
/* 254 */     if (this.headMotion != null) this.headMotion.setContext((IAnnotatedComponent)this, -1); 
/* 255 */     this.actions.setContext((IAnnotatedComponent)this);
/* 256 */     for (int i = 0; i < this.instructionList.length; i++) {
/* 257 */       this.instructionList[i].setContext((IAnnotatedComponent)this, i);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean matches(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, double dt, @Nonnull Store<EntityStore> store) {
/* 262 */     DebugSupport debugSupport = role.getDebugSupport();
/* 263 */     boolean traceSensorFails = debugSupport.isTraceSensorFails();
/* 264 */     if (traceSensorFails) debugSupport.setLastFailingSensor(this.sensor);
/*     */     
/* 266 */     if (this.sensor.matches(ref, role, dt, store)) {
/*     */ 
/*     */ 
/*     */       
/* 270 */       if (!this.treeMode && !this.continueAfter) role.notifySensorMatch(); 
/* 271 */       if (traceSensorFails) debugSupport.setLastFailingSensor(null); 
/* 272 */       return true;
/*     */     } 
/* 274 */     if (debugSupport.isTraceFail()) {
/* 275 */       UUIDComponent uuidComponent = (UUIDComponent)store.getComponent(ref, UUIDComponent.getComponentType());
/* 276 */       assert uuidComponent != null;
/* 277 */       LOGGER.at(Level.INFO).log("Instruction Sensor FAIL uuid=%d, debug=%s", uuidComponent.getUuid(), getBreadCrumbs());
/*     */     } 
/* 279 */     if (traceSensorFails) {
/* 280 */       LOGGER.at(Level.INFO).log("Sensor FAIL, sensor=%s", debugSupport.getLastFailingSensor().getBreadCrumbs());
/* 281 */       debugSupport.setLastFailingSensor(null);
/*     */     } 
/*     */     
/* 284 */     return false;
/*     */   }
/*     */   
/*     */   public void executeActions(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 288 */     if (this.actions.canExecute(ref, role, sensorInfo, dt, store)) {
/* 289 */       this.actions.execute(ref, role, sensorInfo, dt, store);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, double dt, @Nonnull Store<EntityStore> store) {
/* 295 */     if (this.instructionList.length > 0) {
/* 296 */       for (Instruction instruction : this.instructionList) {
/* 297 */         if (instruction.matches(ref, role, dt, store))
/*     */         
/* 299 */         { instruction.onMatched(role);
/* 300 */           instruction.execute(ref, role, dt, store);
/* 301 */           instruction.onCompleted(role);
/*     */           
/* 303 */           if (!instruction.isContinueAfter())
/*     */             break;  } 
/* 305 */       }  this.sensor.setOnce();
/* 306 */       this.sensor.done();
/*     */       
/*     */       return;
/*     */     } 
/* 310 */     InfoProvider sensorInfo = this.sensor.getSensorInfo();
/* 311 */     if (this.headMotion != null && 
/* 312 */       role.getEntitySupport().setNextHeadMotionStep(this)) this.headMotion.preComputeSteering(ref, role, sensorInfo, store);
/*     */ 
/*     */     
/* 315 */     if (this.bodyMotion != null && 
/* 316 */       role.getEntitySupport().setNextBodyMotionStep(this)) this.bodyMotion.preComputeSteering(ref, role, sensorInfo, store);
/*     */ 
/*     */     
/* 319 */     executeActions(ref, role, sensorInfo, dt, store);
/*     */     
/* 321 */     if (this.headMotion == null && this.bodyMotion == null) {
/* 322 */       this.sensor.setOnce();
/* 323 */       this.sensor.done();
/*     */     } 
/*     */     
/* 326 */     if (role.getDebugSupport().isTraceSuccess()) {
/* 327 */       UUIDComponent uuidComponent = (UUIDComponent)store.getComponent(ref, UUIDComponent.getComponentType());
/* 328 */       assert uuidComponent != null;
/* 329 */       LOGGER.at(Level.INFO).log("Instruction SUCC uuid=%d, debug=%s", uuidComponent.getUuid(), getBreadCrumbs());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void clearOnce() {
/* 334 */     this.sensor.clearOnce();
/* 335 */     forEachInstruction(Instruction::clearOnce);
/* 336 */     this.actions.clearOnce();
/*     */   }
/*     */   
/*     */   public void onEndMotion() {
/* 340 */     this.actions.onEndMotion();
/*     */   }
/*     */   
/*     */   public void onMatched(@Nonnull Role role) {
/* 344 */     if (!this.treeMode)
/*     */       return; 
/* 346 */     this.parentTreeModeStep = role.swapTreeModeSteps(this);
/*     */     
/* 348 */     this.continueAfter = true;
/*     */   }
/*     */   
/*     */   public void onCompleted(@Nonnull Role role) {
/* 352 */     if (!this.treeMode)
/*     */       return; 
/* 354 */     role.swapTreeModeSteps(this.parentTreeModeStep);
/*     */     
/* 356 */     if (this.parentTreeModeStep != null) {
/*     */       
/* 358 */       if (this.continueAfter == this.invertTreeModeResult) this.parentTreeModeStep.notifyChildSensorMatch(); 
/* 359 */       this.parentTreeModeStep = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void notifyChildSensorMatch() {
/* 364 */     if (!this.treeMode) {
/*     */       return;
/*     */     }
/* 367 */     this.continueAfter = false;
/*     */   }
/*     */   
/*     */   public void reset() {
/* 371 */     clearOnce();
/*     */   }
/*     */   
/*     */   protected void forEachInstruction(@Nonnull Consumer<Instruction> instructionConsumer) {
/* 375 */     for (Instruction instruction : this.instructionList) {
/* 376 */       instructionConsumer.accept(instruction);
/*     */     }
/*     */   }
/*     */   
/*     */   protected <T> void forEachInstruction(@Nonnull BiConsumer<Instruction, T> instructionConsumer, T obj) {
/* 381 */     for (Instruction instruction : this.instructionList) {
/* 382 */       instructionConsumer.accept(instruction, obj);
/*     */     }
/*     */   }
/*     */   
/*     */   protected <T, U, V> void forEachInstruction(@Nonnull QuadConsumer<Instruction, T, U, V> instructionConsumer, T t, U u, V v) {
/* 387 */     for (Instruction instruction : this.instructionList) {
/* 388 */       instructionConsumer.accept(instruction, t, u, v);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static Instruction createRootInstruction(Instruction[] instructions, @Nonnull BuilderSupport support) {
/* 400 */     return new Instruction(instructions, support);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\instructions\Instruction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */