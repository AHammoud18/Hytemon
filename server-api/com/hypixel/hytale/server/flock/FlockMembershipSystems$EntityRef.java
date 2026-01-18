/*     */ package com.hypixel.hytale.server.flock;
/*     */ 
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.RefSystem;
/*     */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.entity.group.EntityGroup;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import com.hypixel.hytale.server.npc.role.RoleDebugFlags;
/*     */ import java.util.EnumSet;
/*     */ import java.util.UUID;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityRef
/*     */   extends RefSystem<EntityStore>
/*     */ {
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, FlockMembership> flockMembershipComponentType;
/*     */   
/*     */   public EntityRef(@Nonnull ComponentType<EntityStore, FlockMembership> flockMembershipComponentType) {
/*  50 */     this.flockMembershipComponentType = flockMembershipComponentType;
/*     */   }
/*     */ 
/*     */   
/*     */   public Query<EntityStore> getQuery() {
/*  55 */     return (Query)this.flockMembershipComponentType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEntityAdded(@Nonnull Ref<EntityStore> ref, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/*  63 */     commandBuffer.run(_store -> joinOrCreateFlock(ref, _store));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void joinOrCreateFlock(@Nonnull Ref<EntityStore> ref, @Nonnull Store<EntityStore> store) {
/*     */     EntityGroup entityGroup;
/*     */     Flock flock;
/*  73 */     FlockMembership flockMembershipComponent = (FlockMembership)store.getComponent(ref, this.flockMembershipComponentType);
/*  74 */     assert flockMembershipComponent != null;
/*     */     
/*  76 */     UUID flockId = flockMembershipComponent.getFlockId();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  81 */     Ref<EntityStore> flockReference = ((EntityStore)store.getExternalData()).getRefFromUUID(flockId);
/*  82 */     if (flockReference != null) {
/*  83 */       entityGroup = (EntityGroup)store.getComponent(flockReference, EntityGroup.getComponentType());
/*  84 */       assert entityGroup != null;
/*  85 */       flock = (Flock)store.getComponent(flockReference, Flock.getComponentType());
/*  86 */       assert flock != null;
/*     */     } else {
/*  88 */       entityGroup = new EntityGroup();
/*  89 */       flock = new Flock();
/*     */       
/*  91 */       Holder<EntityStore> holder = EntityStore.REGISTRY.newHolder();
/*  92 */       holder.addComponent(UUIDComponent.getComponentType(), (Component)new UUIDComponent(flockId));
/*  93 */       holder.addComponent(EntityGroup.getComponentType(), (Component)entityGroup);
/*  94 */       holder.addComponent(Flock.getComponentType(), flock);
/*  95 */       flockReference = store.addEntity(holder, AddReason.LOAD);
/*     */     } 
/*     */     
/*  98 */     flockMembershipComponent.setFlockRef(flockReference);
/*     */     
/* 100 */     if (entityGroup.isMember(ref)) {
/* 101 */       throw new IllegalStateException(String.format("Entity %s attempting to reload into group with ID %s despite already being a member", new Object[] { ref, flockId }));
/*     */     }
/*     */     
/* 104 */     entityGroup.add(ref);
/*     */     
/* 106 */     if (flockMembershipComponent.getMembershipType() == FlockMembership.Type.LEADER) {
/*     */ 
/*     */       
/* 109 */       PersistentFlockData persistentFlockData = (PersistentFlockData)store.getComponent(ref, PersistentFlockData.getComponentType());
/* 110 */       if (persistentFlockData != null) {
/* 111 */         flock.setFlockData(persistentFlockData);
/*     */       } else {
/* 113 */         PersistentFlockData flockData = flock.getFlockData();
/* 114 */         if (flockData != null) {
/* 115 */           store.putComponent(ref, PersistentFlockData.getComponentType(), flockData);
/*     */         }
/*     */       } 
/*     */       
/* 119 */       Ref<EntityStore> oldLeaderRef = entityGroup.getLeaderRef();
/* 120 */       entityGroup.setLeaderRef(ref);
/*     */ 
/*     */       
/* 123 */       if (oldLeaderRef != null && !oldLeaderRef.equals(ref)) {
/* 124 */         FlockMembership oldLeaderComponent = (FlockMembership)store.getComponent(oldLeaderRef, this.flockMembershipComponentType);
/* 125 */         if (oldLeaderComponent != null) {
/* 126 */           oldLeaderComponent.setMembershipType(FlockMembership.Type.MEMBER);
/*     */         }
/*     */ 
/*     */         
/* 130 */         store.tryRemoveComponent(oldLeaderRef, PersistentFlockData.getComponentType());
/*     */         
/* 132 */         FlockMembershipSystems.markChunkNeedsSaving(oldLeaderRef, store);
/*     */       } 
/*     */       
/* 135 */       markNeedsSave(ref, store, flock);
/*     */       
/* 137 */       if (flock.isTrace()) {
/* 138 */         FlockPlugin.get().getLogger().at(Level.INFO).log("Flock %s: Set new leader, old=%s, new=%s, size=%s", flockId, oldLeaderRef, ref, 
/* 139 */             Integer.valueOf(entityGroup.size()));
/*     */       }
/* 141 */     } else if (entityGroup.getLeaderRef() == null) {
/*     */       
/* 143 */       setInterimLeader(store, flockMembershipComponent, entityGroup, ref, flock, flockId);
/*     */     } 
/*     */     
/* 146 */     if (flock.isTrace()) {
/* 147 */       FlockPlugin.get().getLogger().at(Level.INFO).log("Flock %s: reference=%s, size=%s", flockId, ref, 
/* 148 */           Integer.valueOf(entityGroup.size()));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEntityRemove(@Nonnull Ref<EntityStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 154 */     FlockMembership membership = (FlockMembership)store.getComponent(ref, this.flockMembershipComponentType);
/* 155 */     assert membership != null;
/*     */     
/* 157 */     Ref<EntityStore> flockRef = membership.getFlockRef();
/* 158 */     if (flockRef == null || !flockRef.isValid())
/*     */       return; 
/* 160 */     Flock flockComponent = (Flock)commandBuffer.getComponent(flockRef, Flock.getComponentType());
/* 161 */     assert flockComponent != null;
/*     */     
/* 163 */     EntityGroup entityGroupComponent = (EntityGroup)commandBuffer.getComponent(flockRef, EntityGroup.getComponentType());
/* 164 */     assert entityGroupComponent != null;
/*     */     
/* 166 */     UUIDComponent uuidComponent = (UUIDComponent)commandBuffer.getComponent(flockRef, UUIDComponent.getComponentType());
/* 167 */     assert uuidComponent != null;
/*     */     
/* 169 */     UUID flockId = uuidComponent.getUuid();
/*     */ 
/*     */     
/* 172 */     if (reason == RemoveReason.REMOVE || store.getArchetype(ref).contains(Player.getComponentType())) {
/* 173 */       entityGroupComponent.remove(ref);
/*     */       
/* 175 */       if (flockComponent.isTrace()) {
/* 176 */         FlockPlugin.get().getLogger().at(Level.INFO).log("Flock %s: Left flock, reference=%s, leader=%s, size=%s", flockId, ref, entityGroupComponent
/* 177 */             .getLeaderRef(), Integer.valueOf(entityGroupComponent.size()));
/*     */       }
/*     */ 
/*     */       
/* 181 */       if (!entityGroupComponent.isDissolved() && entityGroupComponent.size() < 2) {
/* 182 */         commandBuffer.removeEntity(flockRef, RemoveReason.REMOVE);
/*     */         
/*     */         return;
/*     */       } 
/* 186 */       if (entityGroupComponent.isDissolved())
/*     */         return; 
/* 188 */       PersistentFlockData flockData = flockComponent.getFlockData();
/* 189 */       if (flockData != null) flockData.decreaseSize();
/*     */       
/* 191 */       Ref<EntityStore> leader = entityGroupComponent.getLeaderRef();
/* 192 */       if (leader != null && !leader.equals(ref)) {
/*     */         
/* 194 */         FlockMembershipSystems.markChunkNeedsSaving(leader, store);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 199 */       Ref<EntityStore> newLeader = entityGroupComponent.testMembers(FlockMembershipSystems::canBecomeLeader, true);
/*     */       
/* 201 */       if (newLeader == null) {
/* 202 */         if (flockComponent.isTrace()) {
/* 203 */           FlockPlugin.get().getLogger().at(Level.INFO).log("Flock %s: Leave failed to get new leader, reference=%s, leader=%s, size=%s", flockId, ref, entityGroupComponent
/* 204 */               .getLeaderRef(), Integer.valueOf(entityGroupComponent.size()));
/*     */         }
/* 206 */         commandBuffer.removeEntity(flockRef, RemoveReason.REMOVE);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 211 */       entityGroupComponent.setLeaderRef(newLeader);
/*     */       
/* 213 */       FlockMembership flockMembershipComponent = (FlockMembership)store.getComponent(newLeader, this.flockMembershipComponentType);
/* 214 */       assert flockMembershipComponent != null;
/*     */       
/* 216 */       flockMembershipComponent.setMembershipType(FlockMembership.Type.LEADER);
/* 217 */       if (flockData != null) {
/* 218 */         commandBuffer.putComponent(newLeader, PersistentFlockData.getComponentType(), flockData);
/*     */       }
/*     */       
/* 221 */       markNeedsSave(newLeader, store, flockComponent);
/*     */       
/* 223 */       if (flockComponent.isTrace()) {
/* 224 */         FlockPlugin.get().getLogger().at(Level.INFO).log("Flock %s: Set new leader, old=%s, new=%s, size=%s", flockId, ref, newLeader, 
/* 225 */             Integer.valueOf(entityGroupComponent.size()));
/*     */       }
/* 227 */     } else if (reason == RemoveReason.UNLOAD) {
/* 228 */       entityGroupComponent.remove(ref);
/*     */       
/* 230 */       if (!entityGroupComponent.isDissolved())
/*     */       {
/* 232 */         if (membership.getMembershipType().isActingAsLeader()) {
/* 233 */           Ref<EntityStore> interimLeader = entityGroupComponent.testMembers(member -> true, true);
/* 234 */           if (interimLeader != null) {
/* 235 */             FlockMembership interimLeaderMembership = (FlockMembership)store.getComponent(interimLeader, this.flockMembershipComponentType);
/* 236 */             if (interimLeaderMembership == null) {
/* 237 */               throw new IllegalStateException("Member is missing FlockMembership component!");
/*     */             }
/* 239 */             setInterimLeader(store, interimLeaderMembership, entityGroupComponent, interimLeader, flockComponent, flockId);
/*     */           } 
/*     */         } 
/*     */       }
/*     */       
/* 244 */       membership.unload();
/*     */       
/* 246 */       if (entityGroupComponent.size() <= 0) {
/* 247 */         commandBuffer.tryRemoveEntity(flockRef, RemoveReason.UNLOAD);
/*     */       }
/*     */       
/* 250 */       if (flockComponent.isTrace()) {
/* 251 */         FlockPlugin.get().getLogger().at(Level.INFO).log("Flock %s: Unloaded from flock, reference=%s, leader=%s, size=%s", flockId, ref, entityGroupComponent
/* 252 */             .getLeaderRef(), Integer.valueOf(entityGroupComponent.size()));
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
/*     */   private static void markNeedsSave(@Nonnull Ref<EntityStore> ref, @Nonnull Store<EntityStore> store, @Nonnull Flock flockComponent) {
/* 265 */     NPCEntity npcComponent = (NPCEntity)store.getComponent(ref, NPCEntity.getComponentType());
/* 266 */     if (npcComponent != null) {
/* 267 */       Role role = npcComponent.getRole();
/* 268 */       if (role != null) {
/* 269 */         EnumSet<RoleDebugFlags> flags = role.getDebugSupport().getDebugFlags();
/* 270 */         flockComponent.setTrace(flags.contains(RoleDebugFlags.Flock));
/*     */       } 
/*     */     } 
/*     */     
/* 274 */     FlockMembershipSystems.markChunkNeedsSaving(ref, store);
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
/*     */   private static void setInterimLeader(@Nonnull Store<EntityStore> store, @Nonnull FlockMembership interimLeaderMembership, @Nonnull EntityGroup entityGroup, Ref<EntityStore> interimLeader, @Nonnull Flock flockComponent, @Nonnull UUID flockId) {
/* 292 */     interimLeaderMembership.setMembershipType(FlockMembership.Type.INTERIM_LEADER);
/* 293 */     entityGroup.setLeaderRef(interimLeader);
/*     */     
/* 295 */     markNeedsSave(interimLeader, store, flockComponent);
/*     */     
/* 297 */     if (flockComponent.isTrace())
/* 298 */       FlockPlugin.get().getLogger().at(Level.INFO).log("Flock %s: Set new interim leader, old=%s, new=%s, size=%s", flockId, entityGroup
/* 299 */           .getLeaderRef(), interimLeader, Integer.valueOf(entityGroup.size())); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\flock\FlockMembershipSystems$EntityRef.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */