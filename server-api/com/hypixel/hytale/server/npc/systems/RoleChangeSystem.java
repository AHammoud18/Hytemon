/*     */ package com.hypixel.hytale.server.npc.systems;
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Resource;
/*     */ import com.hypixel.hytale.component.ResourceType;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.dependency.Dependency;
/*     */ import com.hypixel.hytale.component.dependency.Order;
/*     */ import com.hypixel.hytale.component.dependency.SystemDependency;
/*     */ import com.hypixel.hytale.component.system.tick.TickingSystem;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.server.core.modules.entity.repulsion.Repulsion;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*     */ import com.hypixel.hytale.server.npc.components.Timers;
/*     */ import com.hypixel.hytale.server.npc.components.messaging.BeaconSupport;
/*     */ import com.hypixel.hytale.server.npc.components.messaging.NPCBlockEventSupport;
/*     */ import com.hypixel.hytale.server.npc.components.messaging.NPCEntityEventSupport;
/*     */ import com.hypixel.hytale.server.npc.components.messaging.PlayerBlockEventSupport;
/*     */ import com.hypixel.hytale.server.npc.components.messaging.PlayerEntityEventSupport;
/*     */ import com.hypixel.hytale.server.npc.decisionmaker.stateevaluator.StateEvaluator;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import com.hypixel.hytale.server.npc.valuestore.ValueStore;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Deque;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class RoleChangeSystem extends TickingSystem<EntityStore> {
/*     */   @Nonnull
/*  38 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final ResourceType<EntityStore, RoleChangeQueue> roleChangeQueueResourceType;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, BeaconSupport> beaconSupportComponentType;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, PlayerBlockEventSupport> playerBlockEventSupportComponentType;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, NPCBlockEventSupport> npcBlockEventSupportComponentType;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, PlayerEntityEventSupport> playerEntityEventSupportComponentType;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, NPCEntityEventSupport> npcEntityEventSupportComponentType;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, Timers> timersComponentType;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, StateEvaluator> stateEvaluatorComponentType;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, ValueStore> valueStoreComponentType;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  98 */   private final Set<Dependency<EntityStore>> dependencies = (Set)Set.of(new SystemDependency(Order.AFTER, NewSpawnStartTickingSystem.class));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RoleChangeSystem(@Nonnull ResourceType<EntityStore, RoleChangeQueue> roleChangeQueueResourceType, @Nonnull ComponentType<EntityStore, BeaconSupport> beaconSupportComponentType, @Nonnull ComponentType<EntityStore, PlayerBlockEventSupport> playerBlockEventSupportComponentType, @Nonnull ComponentType<EntityStore, NPCBlockEventSupport> npcBlockEventSupportComponentType, @Nonnull ComponentType<EntityStore, PlayerEntityEventSupport> playerEntityEventSupportComponentType, @Nonnull ComponentType<EntityStore, NPCEntityEventSupport> npcEntityEventSupportComponentType, @Nonnull ComponentType<EntityStore, Timers> timersComponentType, @Nonnull ComponentType<EntityStore, StateEvaluator> stateEvaluatorComponentType, @Nonnull ComponentType<EntityStore, ValueStore> valueStoreComponentType) {
/* 122 */     this.roleChangeQueueResourceType = roleChangeQueueResourceType;
/* 123 */     this.beaconSupportComponentType = beaconSupportComponentType;
/* 124 */     this.playerBlockEventSupportComponentType = playerBlockEventSupportComponentType;
/* 125 */     this.npcBlockEventSupportComponentType = npcBlockEventSupportComponentType;
/* 126 */     this.playerEntityEventSupportComponentType = playerEntityEventSupportComponentType;
/* 127 */     this.npcEntityEventSupportComponentType = npcEntityEventSupportComponentType;
/* 128 */     this.timersComponentType = timersComponentType;
/* 129 */     this.stateEvaluatorComponentType = stateEvaluatorComponentType;
/* 130 */     this.valueStoreComponentType = valueStoreComponentType;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Set<Dependency<EntityStore>> getDependencies() {
/* 136 */     return this.dependencies;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(float dt, int systemIndex, @Nonnull Store<EntityStore> store) {
/* 141 */     RoleChangeQueue roleChangeQueueResource = (RoleChangeQueue)store.getResource(this.roleChangeQueueResourceType);
/* 142 */     Deque<RoleChangeRequest> requests = roleChangeQueueResource.requests;
/*     */     
/* 144 */     while (!requests.isEmpty()) {
/* 145 */       Ref<EntityStore> npcEntityReference; RoleChangeRequest request = requests.poll();
/*     */ 
/*     */       
/* 148 */       if (!request.reference.isValid()) {
/*     */         continue;
/*     */       }
/*     */       
/* 152 */       Holder<EntityStore> holder = store.removeEntity(request.reference, RemoveReason.UNLOAD);
/* 153 */       NPCEntity npcComponent = (NPCEntity)holder.getComponent(NPCEntity.getComponentType());
/* 154 */       assert npcComponent != null;
/*     */       
/* 156 */       npcComponent.setRole(null);
/* 157 */       holder.tryRemoveComponent(this.beaconSupportComponentType);
/* 158 */       holder.tryRemoveComponent(this.playerBlockEventSupportComponentType);
/* 159 */       holder.tryRemoveComponent(this.npcBlockEventSupportComponentType);
/* 160 */       holder.tryRemoveComponent(this.playerEntityEventSupportComponentType);
/* 161 */       holder.tryRemoveComponent(this.npcEntityEventSupportComponentType);
/* 162 */       holder.tryRemoveComponent(this.timersComponentType);
/* 163 */       holder.tryRemoveComponent(this.stateEvaluatorComponentType);
/* 164 */       holder.tryRemoveComponent(this.valueStoreComponentType);
/* 165 */       holder.tryRemoveComponent(Repulsion.getComponentType());
/* 166 */       npcComponent.setRoleName(NPCPlugin.get().getName(request.roleIndex));
/* 167 */       npcComponent.setRoleIndex(request.roleIndex);
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 172 */         npcEntityReference = store.addEntity(holder, AddReason.LOAD);
/* 173 */       } catch (Exception e) {
/* 174 */         LOGGER.at(Level.SEVERE).log("Failed to change role: %s", e.getMessage());
/*     */         
/*     */         continue;
/*     */       } 
/* 178 */       if (npcEntityReference == null || !npcEntityReference.isValid()) {
/* 179 */         LOGGER.at(Level.SEVERE).log("Failed to change role: Could not re-add NPC entity to store");
/*     */         
/*     */         continue;
/*     */       } 
/* 183 */       if (request.changeAppearance) {
/* 184 */         Role role = npcComponent.getRole();
/* 185 */         NPCEntity.setAppearance(npcEntityReference, role.getAppearanceName(), (ComponentAccessor)store);
/*     */       } 
/*     */       
/* 188 */       if (request.state != null) {
/* 189 */         Role role = npcComponent.getRole();
/* 190 */         if (role != null) {
/* 191 */           role.getStateSupport().setState(npcEntityReference, request.state, request.subState, (ComponentAccessor)store);
/*     */         }
/*     */       } 
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
/*     */   public static void requestRoleChange(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, int roleIndex, boolean changeAppearance, @Nonnull Store<EntityStore> store) {
/* 207 */     requestRoleChange(ref, role, roleIndex, changeAppearance, null, null, (ComponentAccessor<EntityStore>)store);
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
/*     */   public static void requestRoleChange(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, int roleIndex, boolean changeAppearance, @Nullable String state, @Nullable String subState, @Nonnull ComponentAccessor<EntityStore> store) {
/* 223 */     RoleChangeQueue roleChangeResource = (RoleChangeQueue)store.getResource(RoleChangeQueue.getResourceType());
/* 224 */     Deque<RoleChangeRequest> queue = roleChangeResource.requests;
/* 225 */     queue.add(new RoleChangeRequest(ref, roleIndex, changeAppearance, state, subState));
/* 226 */     role.setRoleChangeRequested();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class RoleChangeQueue
/*     */     implements Resource<EntityStore>
/*     */   {
/*     */     @Nonnull
/*     */     public static ResourceType<EntityStore, RoleChangeQueue> getResourceType() {
/* 239 */       return NPCPlugin.get().getRoleChangeQueueResourceType();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 245 */     private final Deque<RoleChangeSystem.RoleChangeRequest> requests = new ArrayDeque<>();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Resource<EntityStore> clone() {
/* 252 */       RoleChangeQueue roleChangeQueue = new RoleChangeQueue();
/* 253 */       roleChangeQueue.requests.addAll(this.requests);
/* 254 */       return roleChangeQueue;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class RoleChangeRequest
/*     */   {
/*     */     @Nonnull
/*     */     private final Ref<EntityStore> reference;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final int roleIndex;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final boolean changeAppearance;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     private final String state;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     private final String subState;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private RoleChangeRequest(@Nonnull Ref<EntityStore> reference, int roleIndex, boolean changeAppearance, @Nullable String state, @Nullable String subState) {
/* 301 */       this.reference = reference;
/* 302 */       this.roleIndex = roleIndex;
/* 303 */       this.changeAppearance = changeAppearance;
/* 304 */       this.state = state;
/* 305 */       this.subState = subState;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\systems\RoleChangeSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */