/*     */ package com.hypixel.hytale.server.npc.role.support;
/*     */ 
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.entity.Entity;
/*     */ import com.hypixel.hytale.server.core.entity.nameplate.Nameplate;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.DisplayNameComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entitystats.EntityStatValue;
/*     */ import com.hypixel.hytale.server.core.modules.entitystats.EntityStatsModule;
/*     */ import com.hypixel.hytale.server.core.modules.entitystats.asset.DefaultEntityStatTypes;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.instructions.Instruction;
/*     */ import com.hypixel.hytale.server.npc.role.builders.BuilderRole;
/*     */ import com.hypixel.hytale.server.npc.util.IComponentExecutionControl;
/*     */ import com.hypixel.hytale.server.npc.util.expression.Scope;
/*     */ import com.hypixel.hytale.server.npc.util.expression.StdLib;
/*     */ import com.hypixel.hytale.server.npc.util.expression.StdScope;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntitySupport
/*     */ {
/*     */   protected final NPCEntity parent;
/*     */   @Nullable
/*     */   protected final String[] displayNames;
/*     */   @Nullable
/*     */   protected String nominatedDisplayName;
/*     */   protected StdScope sensorScope;
/*     */   @Nullable
/*     */   protected Instruction nextBodyMotionStep;
/*     */   @Nullable
/*     */   protected Instruction nextHeadMotionStep;
/*  46 */   protected final List<IComponentExecutionControl> delayingComponents = (List<IComponentExecutionControl>)new ObjectArrayList();
/*     */   
/*     */   @Nullable
/*     */   protected List<String> targetPlayerActiveTasks;
/*     */   
/*     */   public EntitySupport(NPCEntity parent, @Nonnull BuilderRole builder) {
/*  52 */     this.parent = parent;
/*  53 */     this.displayNames = builder.getDisplayNames();
/*     */   }
/*     */   
/*     */   public StdScope getSensorScope() {
/*  57 */     return this.sensorScope;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Instruction getNextBodyMotionStep() {
/*  62 */     return this.nextBodyMotionStep;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean setNextBodyMotionStep(Instruction step) {
/*  67 */     if (this.nextBodyMotionStep != null) return false; 
/*  68 */     this.nextBodyMotionStep = step;
/*  69 */     return true;
/*     */   }
/*     */   
/*     */   public void clearNextBodyMotionStep() {
/*  73 */     this.nextBodyMotionStep = null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Instruction getNextHeadMotionStep() {
/*  78 */     return this.nextHeadMotionStep;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean setNextHeadMotionStep(Instruction step) {
/*  83 */     if (this.nextHeadMotionStep != null) return false; 
/*  84 */     this.nextHeadMotionStep = step;
/*  85 */     return true;
/*     */   }
/*     */   
/*     */   public void clearNextHeadMotionStep() {
/*  89 */     this.nextHeadMotionStep = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void postRoleBuilt(@Nonnull BuilderSupport builderSupport) {
/*  98 */     this.sensorScope = builderSupport.getSensorScope();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void tick(float dt) {
/* 107 */     for (int i = 0; i < this.delayingComponents.size(); ) {
/* 108 */       IComponentExecutionControl component = this.delayingComponents.get(i);
/* 109 */       if (component.processDelay(dt)) {
/* 110 */         this.delayingComponents.remove(i); continue;
/*     */       } 
/* 112 */       i++;
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
/*     */   public void handleNominatedDisplayName(@Nonnull Ref<EntityStore> ref, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 124 */     if (this.nominatedDisplayName != null) {
/* 125 */       setDisplayName(ref, this.nominatedDisplayName, componentAccessor);
/*     */     }
/* 127 */     this.nominatedDisplayName = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void nominateDisplayName(@Nonnull String displayName) {
/* 136 */     this.nominatedDisplayName = displayName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void pickRandomDisplayName(@Nonnull Holder<EntityStore> holder, boolean override) {
/* 146 */     if (this.displayNames == null || this.displayNames.length == 0)
/* 147 */       return;  setDisplayName(holder, this.displayNames[MathUtil.randomInt(0, this.displayNames.length)], override);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setDisplayName(@Nonnull Holder<EntityStore> holder, @Nonnull String displayName) {
/* 157 */     setDisplayName(holder, displayName, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setDisplayName(@Nonnull Holder<EntityStore> holder, @Nullable String displayName, boolean override) {
/* 168 */     DisplayNameComponent displayNameComponent = (DisplayNameComponent)holder.getComponent(DisplayNameComponent.getComponentType());
/* 169 */     if (displayNameComponent != null) {
/* 170 */       Message displayNameMessage = displayNameComponent.getDisplayName();
/*     */ 
/*     */       
/* 173 */       if (displayNameMessage != null && !displayNameMessage.getAnsiMessage().isEmpty() && !override) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */     
/* 178 */     holder.putComponent(DisplayNameComponent.getComponentType(), (Component)new DisplayNameComponent(Message.raw((displayName != null) ? displayName : "")));
/*     */     
/* 180 */     if (displayName != null) {
/* 181 */       Nameplate nameplateComponent = (Nameplate)holder.ensureAndGetComponent(Nameplate.getComponentType());
/* 182 */       nameplateComponent.setText(displayName);
/*     */     } else {
/* 184 */       holder.removeComponent(Nameplate.getComponentType());
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
/*     */   public void pickRandomDisplayName(@Nonnull Ref<EntityStore> ref, boolean override, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 196 */     setRandomDisplayName(ref, this.displayNames, override, componentAccessor);
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
/*     */   public static void setRandomDisplayName(@Nonnull Ref<EntityStore> ref, @Nullable String[] names, boolean override, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 208 */     if (names == null || names.length == 0)
/* 209 */       return;  setDisplayName(ref, names[MathUtil.randomInt(0, names.length)], override, componentAccessor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setDisplayName(@Nonnull Ref<EntityStore> ref, @Nonnull String displayName, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 220 */     setDisplayName(ref, displayName, true, componentAccessor);
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
/*     */   public static void setDisplayName(@Nonnull Ref<EntityStore> ref, @Nullable String displayName, boolean override, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 232 */     if (!ref.isValid())
/*     */       return; 
/* 234 */     DisplayNameComponent displayNameComponent = (DisplayNameComponent)componentAccessor.getComponent(ref, DisplayNameComponent.getComponentType());
/* 235 */     if (displayNameComponent != null) {
/* 236 */       Message displayNameMessage = displayNameComponent.getDisplayName();
/*     */ 
/*     */       
/* 239 */       if (displayNameMessage != null && !displayNameMessage.getAnsiMessage().isEmpty() && !override) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */     
/* 244 */     componentAccessor.putComponent(ref, DisplayNameComponent.getComponentType(), (Component)new DisplayNameComponent(Message.raw((displayName != null) ? displayName : "")));
/*     */     
/* 246 */     if (displayName != null) {
/* 247 */       Nameplate nameplateComponent = (Nameplate)componentAccessor.ensureAndGetComponent(ref, Nameplate.getComponentType());
/* 248 */       nameplateComponent.setText(displayName);
/*     */     } else {
/* 250 */       componentAccessor.removeComponent(ref, Nameplate.getComponentType());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addTargetPlayerActiveTask(@Nonnull String task) {
/* 260 */     if (this.targetPlayerActiveTasks == null) {
/* 261 */       this.targetPlayerActiveTasks = (List<String>)new ObjectArrayList();
/*     */     }
/*     */     
/* 264 */     this.targetPlayerActiveTasks.add(task);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearTargetPlayerActiveTasks() {
/* 271 */     if (this.targetPlayerActiveTasks != null) {
/* 272 */       this.targetPlayerActiveTasks.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public List<String> getTargetPlayerActiveTasks() {
/* 281 */     return this.targetPlayerActiveTasks;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerDelay(@Nonnull IComponentExecutionControl component) {
/* 290 */     this.delayingComponents.add(component);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static StdScope createScope(@Nonnull NPCEntity entity) {
/* 295 */     StdScope scope = new StdScope((Scope)StdLib.getInstance());
/* 296 */     scope.addSupplier("blocked", () -> entity.getRole().getActiveMotionController().isObstructed());
/* 297 */     scope.addSupplier("health", () -> {
/*     */           EntityStatValue healthStat = EntityStatsModule.get((Entity)entity).get(DefaultEntityStatTypes.getHealth());
/*     */           return ((EntityStatValue)Objects.<EntityStatValue>requireNonNull(healthStat)).asPercentage();
/*     */         });
/* 301 */     return scope;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\role\support\EntitySupport.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */