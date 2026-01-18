/*     */ package com.hypixel.hytale.server.npc.role.support;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.protocol.ComponentUpdate;
/*     */ import com.hypixel.hytale.protocol.GameMode;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.entity.group.EntityGroup;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.Interactable;
/*     */ import com.hypixel.hytale.server.core.modules.entity.player.PlayerSettings;
/*     */ import com.hypixel.hytale.server.core.modules.entity.tracker.EntityTrackerSystems;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.flock.FlockPlugin;
/*     */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.StateMappingHelper;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import com.hypixel.hytale.server.npc.role.builders.BuilderRole;
/*     */ import com.hypixel.hytale.server.npc.statetransition.StateTransitionController;
/*     */ import it.unimi.dsi.fastutil.ints.Int2IntMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.ints.IntSet;
/*     */ import java.util.BitSet;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class StateSupport {
/*     */   @Nullable
/*  37 */   protected static final ComponentType<EntityStore, NPCEntity> NPC_COMPONENT_TYPE = NPCEntity.getComponentType();
/*     */   
/*     */   public static final int NO_STATE = -2147483648;
/*     */   
/*     */   protected final StateMappingHelper stateHelper;
/*     */   
/*     */   protected final int startState;
/*     */   protected final int startSubState;
/*     */   protected int state;
/*     */   protected int subState;
/*     */   protected Int2IntMap componentLocalStateMachines;
/*     */   protected BitSet localStateMachineAutoResetStates;
/*     */   protected final Int2ObjectMap<IntSet> busyStates;
/*  50 */   protected final HashSet<String> missingStates = new HashSet<>();
/*     */ 
/*     */   
/*     */   protected boolean needClearOnce;
/*     */   
/*     */   protected Set<Ref<EntityStore>> interactablePlayers;
/*     */   
/*     */   protected Set<Ref<EntityStore>> interactedPlayers;
/*     */   
/*     */   protected Map<Ref<EntityStore>, String> contextualInteractions;
/*     */   
/*     */   @Nullable
/*     */   protected Ref<EntityStore> interactionIterationTarget;
/*     */   
/*     */   @Nullable
/*     */   protected final StateTransitionController stateTransitionController;
/*     */ 
/*     */   
/*     */   public StateSupport(@Nonnull BuilderRole builder, @Nonnull BuilderSupport support) {
/*  69 */     this.stateHelper = builder.getStateMappingHelper();
/*  70 */     this.busyStates = builder.getBusyStates();
/*  71 */     this.stateTransitionController = builder.getStateTransitionController(support);
/*  72 */     this.startState = builder.getStartStateIndex();
/*  73 */     this.startSubState = builder.getStartSubStateIndex();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public StateTransitionController getStateTransitionController() {
/*  78 */     return this.stateTransitionController;
/*     */   }
/*     */   
/*     */   public StateMappingHelper getStateHelper() {
/*  82 */     return this.stateHelper;
/*     */   }
/*     */   
/*     */   public void postRoleBuilt(@Nonnull BuilderSupport builderSupport) {
/*  86 */     if (builderSupport.hasComponentLocalStateMachines()) {
/*  87 */       this.componentLocalStateMachines = builderSupport.getComponentLocalStateMachines();
/*  88 */       this.localStateMachineAutoResetStates = builderSupport.getLocalStateMachineAutoResetStates();
/*     */     } 
/*  90 */     if (builderSupport.isTrackInteractions()) {
/*  91 */       this.interactedPlayers = new HashSet<>();
/*  92 */       this.interactablePlayers = new HashSet<>();
/*  93 */       this.contextualInteractions = new HashMap<>();
/*     */     } 
/*  95 */     if (this.busyStates != null) {
/*  96 */       String defaultSubState = this.stateHelper.getDefaultSubState();
/*  97 */       this.busyStates.forEach((key, value) -> {
/*     */             int defaultSubStateIndex = this.stateHelper.getSubStateIndex(key.intValue(), defaultSubState);
/*     */             if (value.contains(defaultSubStateIndex) && value.size() == 1) {
/*     */               int maxIndex = this.stateHelper.getHighestSubStateIndex(key.intValue());
/*     */               for (int i = 0; i <= maxIndex; i++) {
/*     */                 value.add(i);
/*     */               }
/*     */             } 
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void update(@Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 111 */     if (this.contextualInteractions != null) this.contextualInteractions.clear(); 
/* 112 */     if (this.interactablePlayers != null) {
/* 113 */       for (Iterator<Ref<EntityStore>> it = this.interactablePlayers.iterator(); it.hasNext(); ) {
/* 114 */         Ref<EntityStore> ref = it.next();
/* 115 */         if (!ref.isValid())
/*     */           continue; 
/* 117 */         Player playerComponent = (Player)componentAccessor.getComponent(ref, Player.getComponentType());
/* 118 */         if (playerComponent == null) {
/* 119 */           it.remove();
/*     */           
/*     */           continue;
/*     */         } 
/* 123 */         if (playerComponent.getGameMode() == GameMode.Creative) {
/* 124 */           PlayerSettings playerSettingsComponent = (PlayerSettings)componentAccessor.getComponent(ref, PlayerSettings.getComponentType());
/* 125 */           if (playerSettingsComponent == null || !playerSettingsComponent.creativeSettings().allowNPCDetection()) {
/* 126 */             it.remove();
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean pollNeedClearOnce() {
/* 134 */     boolean ret = this.needClearOnce;
/* 135 */     this.needClearOnce = false;
/* 136 */     return ret;
/*     */   }
/*     */   
/*     */   public boolean inState(int state) {
/* 140 */     return (this.state == state);
/*     */   }
/*     */   
/*     */   public boolean inSubState(int subState) {
/* 144 */     return (this.subState == subState);
/*     */   }
/*     */   
/*     */   public boolean inState(int state, int subState) {
/* 148 */     return (inState(state) && (subState == Integer.MIN_VALUE || inSubState(subState)));
/*     */   }
/*     */   
/*     */   public boolean inState(String state, String subState) {
/* 152 */     int stateIndex = this.stateHelper.getStateIndex(state);
/* 153 */     if (stateIndex < 0) return false; 
/* 154 */     int subStateIndex = this.stateHelper.getSubStateIndex(stateIndex, subState);
/* 155 */     return (subStateIndex >= 0 && inState(stateIndex, subStateIndex));
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public String getStateName() {
/* 160 */     return getStateName(this.state, this.subState);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public String getStateName(int state, int subState) {
/* 165 */     return this.stateHelper.getStateName(state) + "." + this.stateHelper.getStateName(state);
/*     */   }
/*     */   
/*     */   public int getStateIndex() {
/* 169 */     return this.state;
/*     */   }
/*     */   
/*     */   public int getSubStateIndex() {
/* 173 */     return this.subState;
/*     */   }
/*     */   
/*     */   public void appendStateName(@Nonnull StringBuilder builder) {
/* 177 */     builder.append(this.stateHelper.getStateName(this.state)).append('.').append(this.stateHelper.getSubStateName(this.state, this.subState));
/*     */   }
/*     */   
/*     */   public void setState(int state, int subState, boolean clearOnce, boolean skipTransition) {
/* 181 */     int oldState = this.state;
/* 182 */     this.state = state;
/* 183 */     this.subState = subState;
/* 184 */     if (clearOnce) this.needClearOnce = true; 
/* 185 */     if (!skipTransition && oldState != state && this.stateTransitionController != null) this.stateTransitionController.initiateStateTransition(oldState, state); 
/*     */   }
/*     */   
/*     */   public void setState(@Nonnull Ref<EntityStore> ref, @Nonnull String state, @Nullable String subState, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 189 */     int index = this.stateHelper.getStateIndex(state);
/* 190 */     if (index >= 0) {
/* 191 */       if (subState == null) subState = this.stateHelper.getDefaultSubState(); 
/* 192 */       int subStateIndex = this.stateHelper.getSubStateIndex(index, subState);
/* 193 */       if (subStateIndex >= 0) {
/* 194 */         setState(index, subStateIndex, true, false);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 199 */     if (!this.missingStates.add(state)) {
/* 200 */       NPCEntity npcComponent = (NPCEntity)componentAccessor.getComponent(ref, NPCEntity.getComponentType());
/* 201 */       assert npcComponent != null;
/*     */       
/* 203 */       NPCPlugin.get().getLogger().at(Level.WARNING).log("State '%s.%s' in '%s' does not exist and was set by an external call", state, subState, npcComponent.getRoleName());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setSubState(String subState) {
/* 208 */     int subStateIndex = this.stateHelper.getSubStateIndex(this.state, subState);
/* 209 */     if (subStateIndex >= 0)
/*     */     {
/* 211 */       setState(this.state, subStateIndex, true, true);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isComponentInState(int componentIndex, int targetState) {
/* 216 */     int state = this.componentLocalStateMachines.get(componentIndex);
/* 217 */     if (state == Integer.MIN_VALUE) throw new IllegalArgumentException("Querying for a component index that doesn't exist"); 
/* 218 */     return (state == targetState);
/*     */   }
/*     */   
/*     */   public void setComponentState(int componentIndex, int targetState) {
/* 222 */     this.componentLocalStateMachines.put(componentIndex, targetState);
/*     */   }
/*     */   
/*     */   public void resetLocalStateMachines() {
/* 226 */     if (this.localStateMachineAutoResetStates == null)
/*     */       return; 
/* 228 */     for (int i = this.localStateMachineAutoResetStates.nextSetBit(0); i >= 0; i = this.localStateMachineAutoResetStates.nextSetBit(i + 1)) {
/* 229 */       this.componentLocalStateMachines.put(i, 0);
/* 230 */       if (i == Integer.MAX_VALUE) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void flockSetState(Ref<EntityStore> ref, @Nonnull String state, @Nullable String subState, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 237 */     Ref<EntityStore> flockReference = FlockPlugin.getFlockReference(ref, componentAccessor);
/* 238 */     if (flockReference == null) {
/*     */       return;
/*     */     }
/*     */     
/* 242 */     EntityGroup entityGroupComponent = (EntityGroup)componentAccessor.getComponent(flockReference, EntityGroup.getComponentType());
/* 243 */     assert entityGroupComponent != null;
/*     */     
/* 245 */     entityGroupComponent
/* 246 */       .forEachMemberExcludingSelf((member, sender, _state, _substate) -> {
/*     */           Store<EntityStore> memberStore = member.getStore();
/*     */           NPCEntity npcComponent = (NPCEntity)memberStore.getComponent(member, NPC_COMPONENT_TYPE);
/*     */           if (npcComponent == null) {
/*     */             return;
/*     */           }
/*     */           npcComponent.onFlockSetState(member, _state, _substate, (ComponentAccessor)memberStore);
/*     */         }ref, state, subState);
/*     */   }
/*     */   
/*     */   public boolean isInBusyState() {
/* 257 */     if (this.busyStates == null) return false;
/*     */     
/* 259 */     IntSet busySubStates = (IntSet)this.busyStates.get(this.state);
/* 260 */     return (busySubStates != null && busySubStates.contains(this.subState));
/*     */   }
/*     */   
/*     */   public void addContextualInteraction(@Nonnull Ref<EntityStore> playerRef, @Nonnull String context) {
/* 264 */     if (this.contextualInteractions != null) this.contextualInteractions.put(playerRef, context); 
/*     */   }
/*     */   
/*     */   public boolean hasContextualInteraction(@Nonnull Ref<EntityStore> playerReference, @Nonnull String context) {
/* 268 */     String contextualInteraction = this.contextualInteractions.get(playerReference);
/* 269 */     if (contextualInteraction == null) return false;
/*     */     
/* 271 */     return contextualInteraction.equals(context);
/*     */   }
/*     */   
/*     */   public void addInteraction(@Nonnull Player player) {
/* 275 */     this.interactedPlayers.add(player.getReference());
/*     */   }
/*     */   
/*     */   public boolean consumeInteraction(@Nonnull Ref<EntityStore> playerReference) {
/* 279 */     return this.interactedPlayers.remove(playerReference);
/*     */   }
/*     */   
/*     */   public void setInteractable(@Nonnull Ref<EntityStore> playerReference, boolean interactable) {
/* 283 */     if (interactable) {
/* 284 */       this.interactablePlayers.add(playerReference);
/*     */     } else {
/* 286 */       this.interactablePlayers.remove(playerReference);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInteractable(@Nonnull Ref<EntityStore> entityRef, @Nonnull Ref<EntityStore> playerReference, boolean interactable, @Nullable String hint, boolean showPrompt, @Nonnull Store<EntityStore> store) {
/* 296 */     boolean wasInteractable = this.interactablePlayers.contains(playerReference);
/*     */     
/* 298 */     if (interactable) {
/* 299 */       this.interactablePlayers.add(playerReference);
/*     */     } else {
/* 301 */       this.interactablePlayers.remove(playerReference);
/*     */     } 
/*     */     
/* 304 */     if (showPrompt) {
/* 305 */       boolean hasComponent = store.getArchetype(entityRef).contains(Interactable.getComponentType());
/*     */       
/* 307 */       if (interactable) {
/* 308 */         boolean needsHint = (!wasInteractable && hint != null);
/* 309 */         if (!hasComponent) {
/* 310 */           store.ensureComponent(entityRef, Interactable.getComponentType());
/* 311 */           needsHint = (hint != null);
/*     */         } 
/* 313 */         if (needsHint) {
/* 314 */           sendInteractionHintToPlayer(entityRef, playerReference, hint, store);
/*     */         }
/*     */       }
/* 317 */       else if (hasComponent && this.interactablePlayers.isEmpty()) {
/* 318 */         store.removeComponent(entityRef, Interactable.getComponentType());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void sendInteractionHintToPlayer(@Nonnull Ref<EntityStore> entityRef, @Nonnull Ref<EntityStore> playerReference, @Nonnull String hint, @Nonnull Store<EntityStore> store) {
/* 328 */     EntityTrackerSystems.EntityViewer viewerComponent = (EntityTrackerSystems.EntityViewer)store.getComponent(playerReference, EntityTrackerSystems.EntityViewer.getComponentType());
/* 329 */     if (viewerComponent == null) {
/*     */       return;
/*     */     }
/*     */     
/* 333 */     ComponentUpdate update = new ComponentUpdate();
/* 334 */     update.type = ComponentUpdateType.Interactable;
/* 335 */     update.interactionHint = hint;
/* 336 */     viewerComponent.queueUpdate(entityRef, update);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInteractionIterationTarget(@Nullable Ref<EntityStore> playerReference) {
/* 345 */     this.interactionIterationTarget = playerReference;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Ref<EntityStore> getInteractionIterationTarget() {
/* 353 */     return this.interactionIterationTarget;
/*     */   }
/*     */   
/*     */   public boolean willInteractWith(@Nonnull Ref<EntityStore> playerReference) {
/* 357 */     return (this.interactablePlayers != null && this.interactablePlayers.contains(playerReference) && !isInBusyState());
/*     */   }
/*     */   
/*     */   public boolean runTransitionActions(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, double dt, @Nonnull Store<EntityStore> store) {
/* 361 */     if (this.stateTransitionController == null) return false; 
/* 362 */     return this.stateTransitionController.runTransitionActions(ref, role, dt, store);
/*     */   }
/*     */   
/*     */   public boolean isRunningTransitionActions() {
/* 366 */     return (this.stateTransitionController != null && this.stateTransitionController.isRunningTransitionActions());
/*     */   }
/*     */   
/*     */   public void activate() {
/* 370 */     setState(this.startState, this.startSubState, true, true);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\role\support\StateSupport.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */