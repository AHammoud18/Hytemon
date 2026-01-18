/*     */ package com.hypixel.hytale.server.flock.commands;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.spatial.SpatialResource;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.HeadRotation;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.flock.FlockMembership;
/*     */ import com.hypixel.hytale.server.flock.FlockMembershipSystems;
/*     */ import com.hypixel.hytale.server.flock.FlockPlugin;
/*     */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.util.NPCPhysicsMath;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectList;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectListIterator;
/*     */ import java.util.List;
/*     */ import java.util.function.BiPredicate;
/*     */ import java.util.function.Predicate;
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
/*     */ public class NPCFlockCommand
/*     */   extends AbstractCommandCollection
/*     */ {
/*     */   private static final double ENTITY_IN_VIEW_DISTANCE = 8.0D;
/*     */   private static final float ENTITY_IN_VIEW_ANGLE = 30.0F;
/*     */   private static final int ENTITY_IN_VIEW_HEIGHT = 2;
/*     */   
/*     */   public NPCFlockCommand() {
/*  51 */     super("flock", "server.commands.npc.flock.desc");
/*  52 */     addSubCommand((AbstractCommand)new LeaveCommand());
/*  53 */     addSubCommand((AbstractCommand)new GrabCommand());
/*  54 */     addSubCommand((AbstractCommand)new JoinCommand());
/*  55 */     addSubCommand((AbstractCommand)new PlayerLeaveCommand());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class LeaveCommand
/*     */     extends AbstractPlayerCommand
/*     */   {
/*     */     public LeaveCommand() {
/*  67 */       super("leave", "server.commands.npc.flock.leave.desc");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/*  77 */       int count = NPCFlockCommand.forNpcEntitiesInViewCone(ref, store, (targetRef, targetNpcComponent) -> {
/*     */             store.tryRemoveComponent(targetRef, FlockMembership.getComponentType());
/*     */             return true;
/*     */           });
/*  81 */       context.sendMessage(Message.translation("server.commands.npc.flock.removedFromFlock")
/*  82 */           .param("count", count));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class GrabCommand
/*     */     extends AbstractPlayerCommand
/*     */   {
/*     */     public GrabCommand() {
/*  95 */       super("grab", "server.commands.npc.flock.grab.desc");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 105 */       int count = NPCFlockCommand.forNpcEntitiesInViewCone(ref, store, (targetRef, targetNpcComponent) -> {
/*     */             FlockMembership membership = (FlockMembership)store.getComponent(targetRef, FlockMembership.getComponentType());
/*     */             
/*     */             if (membership == null) {
/*     */               Ref<EntityStore> flockReference = FlockPlugin.getFlockReference(ref, (ComponentAccessor)store);
/*     */               
/*     */               if (flockReference == null) {
/*     */                 flockReference = FlockPlugin.createFlock(store, targetNpcComponent.getRole());
/*     */                 
/*     */                 FlockMembershipSystems.join(ref, flockReference, store);
/*     */               } 
/*     */               FlockMembershipSystems.join(targetRef, flockReference, store);
/*     */               return true;
/*     */             } 
/*     */             return false;
/*     */           });
/* 121 */       context.sendMessage(Message.translation("server.commands.npc.flock.addedToFlock")
/* 122 */           .param("count", count));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class JoinCommand
/*     */     extends AbstractPlayerCommand
/*     */   {
/*     */     public JoinCommand() {
/* 134 */       super("join", "server.commands.npc.flock.join.desc");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 144 */       boolean success = NPCFlockCommand.anyEntityInViewCone(ref, store, npcReference -> {
/*     */             Ref<EntityStore> flockReference = FlockPlugin.getFlockReference(npcReference, (ComponentAccessor)store);
/*     */             
/*     */             if (flockReference != null) {
/*     */               FlockMembershipSystems.join(ref, flockReference, store);
/*     */               return true;
/*     */             } 
/*     */             return false;
/*     */           });
/* 153 */       if (!success) {
/* 154 */         context.sendMessage(Message.translation("server.commands.npc.flock.resultJoinFlock")
/* 155 */             .param("status", "Failed"));
/*     */       } else {
/*     */         
/* 158 */         world.execute(() -> {
/*     */               String status = FlockPlugin.isFlockMember(ref, store) ? "Succeeded" : "Failed";
/*     */               context.sendMessage(Message.translation("server.commands.npc.flock.resultJoinFlock").param("status", status));
/*     */             });
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class PlayerLeaveCommand
/*     */     extends AbstractPlayerCommand
/*     */   {
/*     */     @Nonnull
/* 172 */     private static final Message MESSAGE_COMMANDS_NPC_FLOCK_LEFT_FLOCK = Message.translation("server.commands.npc.flock.leftFlock");
/*     */     @Nonnull
/* 174 */     private static final Message MESSAGE_COMMANDS_NPC_FLOCK_FAILED_LEAVE_FLOCK = Message.translation("server.commands.npc.flock.failedLeaveFlock");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public PlayerLeaveCommand() {
/* 180 */       super("playerleave", "server.commands.npc.flock.playerleave.desc");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 190 */       if (store.removeComponentIfExists(ref, FlockMembership.getComponentType())) {
/* 191 */         context.sendMessage(MESSAGE_COMMANDS_NPC_FLOCK_LEFT_FLOCK);
/*     */       } else {
/* 193 */         context.sendMessage(MESSAGE_COMMANDS_NPC_FLOCK_FAILED_LEAVE_FLOCK);
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
/*     */   public static int forNpcEntitiesInViewCone(@Nonnull Ref<EntityStore> playerReference, @Nonnull Store<EntityStore> store, @Nonnull BiPredicate<Ref<EntityStore>, NPCEntity> predicate) {
/* 207 */     ComponentType<EntityStore, TransformComponent> transformComponentType = TransformComponent.getComponentType();
/*     */     
/* 209 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(playerReference, transformComponentType);
/* 210 */     assert transformComponent != null;
/*     */     
/* 212 */     Vector3d position = transformComponent.getPosition();
/*     */     
/* 214 */     HeadRotation headRotationComponent = (HeadRotation)store.getComponent(playerReference, HeadRotation.getComponentType());
/* 215 */     assert headRotationComponent != null;
/*     */     
/* 217 */     Vector3f headRotation = headRotationComponent.getRotation();
/*     */     
/* 219 */     float lookYaw = headRotation.getYaw();
/* 220 */     double x = position.getX();
/* 221 */     double y = position.getY();
/* 222 */     double z = position.getZ();
/*     */     
/* 224 */     SpatialResource<Ref<EntityStore>, EntityStore> spatialResource = (SpatialResource<Ref<EntityStore>, EntityStore>)store.getResource(NPCPlugin.get().getNpcSpatialResource());
/* 225 */     ObjectList<Ref<EntityStore>> results = SpatialResource.getThreadLocalReferenceList();
/* 226 */     spatialResource.getSpatialStructure().collect(position, 8.0D, (List)results);
/*     */     
/* 228 */     ComponentType<EntityStore, NPCEntity> npcComponentType = NPCEntity.getComponentType();
/* 229 */     assert npcComponentType != null;
/*     */     
/* 231 */     int count = 0;
/* 232 */     for (ObjectListIterator<Ref<EntityStore>> objectListIterator = results.iterator(); objectListIterator.hasNext(); ) { Ref<EntityStore> targetRef = objectListIterator.next();
/* 233 */       NPCEntity targetNpcComponent = (NPCEntity)store.getComponent(targetRef, npcComponentType);
/* 234 */       assert targetNpcComponent != null;
/*     */       
/* 236 */       TransformComponent entityTransformComponent = (TransformComponent)store.getComponent(targetRef, transformComponentType);
/* 237 */       assert entityTransformComponent != null;
/*     */       
/* 239 */       Vector3d entityPosition = entityTransformComponent.getPosition();
/* 240 */       if (Math.abs(entityPosition.getY() - y) < 2.0D && NPCPhysicsMath.inViewSector(x, z, lookYaw, 0.5235988F, entityPosition.getX(), entityPosition
/* 241 */           .getZ()) && predicate.test(targetRef, targetNpcComponent)) {
/* 242 */         count++;
/*     */       } }
/*     */     
/* 245 */     return count;
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
/*     */   public static boolean anyEntityInViewCone(@Nonnull Ref<EntityStore> playerReference, @Nonnull Store<EntityStore> store, @Nonnull Predicate<Ref<EntityStore>> predicate) {
/* 259 */     ComponentType<EntityStore, TransformComponent> transformComponentType = TransformComponent.getComponentType();
/*     */     
/* 261 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(playerReference, transformComponentType);
/* 262 */     assert transformComponent != null;
/*     */     
/* 264 */     Vector3d position = transformComponent.getPosition();
/*     */     
/* 266 */     HeadRotation headRotationComponent = (HeadRotation)store.getComponent(playerReference, HeadRotation.getComponentType());
/* 267 */     assert headRotationComponent != null;
/*     */     
/* 269 */     Vector3f headRotation = headRotationComponent.getRotation();
/* 270 */     float lookYaw = headRotation.getYaw();
/* 271 */     double x = position.getX();
/* 272 */     double y = position.getY();
/* 273 */     double z = position.getZ();
/*     */     
/* 275 */     SpatialResource<Ref<EntityStore>, EntityStore> spatialResource = (SpatialResource<Ref<EntityStore>, EntityStore>)store.getResource(NPCPlugin.get().getNpcSpatialResource());
/* 276 */     ObjectList<Ref<EntityStore>> results = SpatialResource.getThreadLocalReferenceList();
/* 277 */     spatialResource.getSpatialStructure().ordered(position, 8.0D, (List)results);
/*     */     
/* 279 */     for (ObjectListIterator<Ref<EntityStore>> objectListIterator = results.iterator(); objectListIterator.hasNext(); ) { Ref<EntityStore> entityRef = objectListIterator.next();
/* 280 */       TransformComponent entityTransformComponent = (TransformComponent)store.getComponent(entityRef, transformComponentType);
/* 281 */       assert entityTransformComponent != null;
/*     */       
/* 283 */       Vector3d entityPosition = entityTransformComponent.getPosition();
/* 284 */       if (Math.abs(entityPosition.getY() - y) < 2.0D && 
/* 285 */         NPCPhysicsMath.inViewSector(x, z, lookYaw, 0.5235988F, entityPosition.getX(), entityPosition
/* 286 */           .getZ()) && predicate.test(entityRef)) {
/* 287 */         return true;
/*     */       } }
/*     */     
/* 290 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\flock\commands\NPCFlockCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */