/*    */ package com.hypixel.hytale.server.flock.corecomponents;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.entity.group.EntityGroup;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.flock.FlockMembership;
/*    */ import com.hypixel.hytale.server.flock.FlockMembershipSystems;
/*    */ import com.hypixel.hytale.server.flock.corecomponents.builders.BuilderEntityFilterFlock;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.EntityFilterBase;
/*    */ import com.hypixel.hytale.server.npc.movement.FlockMembershipType;
/*    */ import com.hypixel.hytale.server.npc.movement.FlockPlayerMembership;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class EntityFilterFlock
/*    */   extends EntityFilterBase
/*    */ {
/*    */   public static final int COST = 100;
/* 22 */   protected static final ComponentType<EntityStore, FlockMembership> FLOCK_MEMBERSHIP_COMPONENT_TYPE = FlockMembership.getComponentType();
/* 23 */   protected static final ComponentType<EntityStore, Player> PLAYER_COMPONENT_TYPE = Player.getComponentType();
/* 24 */   protected static final ComponentType<EntityStore, EntityGroup> ENTITY_GROUP_COMPONENT_TYPE = EntityGroup.getComponentType();
/*    */   
/*    */   protected final FlockMembershipType flockMembership;
/*    */   protected final FlockPlayerMembership flockPlayerMembership;
/*    */   protected final int[] size;
/*    */   protected final boolean checkCanJoin;
/*    */   
/*    */   public EntityFilterFlock(@Nonnull BuilderEntityFilterFlock builder) {
/* 32 */     this.flockMembership = builder.getFlockMembership();
/* 33 */     this.flockPlayerMembership = builder.getFlockPlayerMembership();
/* 34 */     this.size = builder.getSize();
/* 35 */     this.checkCanJoin = builder.isCheckCanJoin();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matchesEntity(@Nonnull Ref<EntityStore> ref, @Nonnull Ref<EntityStore> targetRef, @Nonnull Role role, @Nonnull Store<EntityStore> store) {
/* 40 */     FlockMembership membership = (FlockMembership)store.getComponent(targetRef, FLOCK_MEMBERSHIP_COMPONENT_TYPE);
/* 41 */     switch (this.flockMembership) { default: throw new MatchException(null, null);
/* 42 */       case Member: if (membership == null || !membership.getMembershipType().isActingAsLeader())
/*    */         {
/*    */ 
/*    */ 
/*    */           
/* 47 */           return false; }  break;case NotMember: if (membership == null || membership.getMembershipType().isActingAsLeader()) return false;  break;case Any: if (membership == null) return false;  break;case null: if (membership != null) return false;  break;
/*    */       case null:
/* 49 */         break; }  EntityGroup group = null;
/* 50 */     if (membership != null) {
/* 51 */       Ref<EntityStore> flockReference = membership.getFlockRef();
/* 52 */       if (flockReference != null && flockReference.isValid()) {
/* 53 */         group = (EntityGroup)store.getComponent(flockReference, ENTITY_GROUP_COMPONENT_TYPE);
/*    */       }
/*    */     } 
/*    */     
/* 57 */     if (this.size != null && group != null && (
/* 58 */       group.size() < this.size[0] || group.size() > this.size[1])) return false;
/*    */ 
/*    */ 
/*    */     
/* 62 */     if (this.checkCanJoin && (membership == null || !FlockMembershipSystems.canJoinFlock(targetRef, membership.getFlockRef(), store))) {
/* 63 */       return false;
/*    */     }
/*    */     
/* 66 */     Ref<EntityStore> leaderRef = (group != null) ? group.getLeaderRef() : null;
/* 67 */     boolean leaderIsPlayer = (leaderRef != null && leaderRef.isValid() && store.getArchetype(leaderRef).contains(PLAYER_COMPONENT_TYPE));
/* 68 */     switch (this.flockPlayerMembership) { default: throw new MatchException(null, null);case Member: case NotMember: return 
/*    */           
/* 70 */           !leaderIsPlayer;
/*    */       case Any:
/*    */         break; }
/*    */     
/*    */     return true;
/*    */   }
/*    */   public int cost() {
/* 77 */     return 100;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\flock\corecomponents\EntityFilterFlock.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */