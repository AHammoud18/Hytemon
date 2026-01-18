/*     */ package com.hypixel.hytale.server.npc.commands;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.vector.Transform;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.core.util.TargetUtil;
/*     */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
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
/*     */ public abstract class NPCMultiSelectCommandBase
/*     */   extends NPCWorldCommandBase
/*     */ {
/*     */   protected static final float DEFAULT_CONE_ANGLE = 30.0F;
/*     */   protected static final float DEFAULT_RANGE = 8.0F;
/*     */   protected static final float RANGE_MIN = 0.0F;
/*     */   protected static final float RANGE_MAX = 2048.0F;
/*     */   protected static final float CONE_ANGLE_MIN = 0.0F;
/*     */   protected static final float CONE_ANGLE_MAX = 180.0F;
/*     */   @Nonnull
/*     */   protected final OptionalArg<Float> coneAngleArg;
/*     */   @Nonnull
/*     */   protected final OptionalArg<Float> rangeArg;
/*     */   @Nonnull
/*     */   private final OptionalArg<String> rolesArg;
/*     */   @Nonnull
/*     */   private final FlagArg nearestArg;
/*     */   @Nonnull
/*     */   private final FlagArg presetCone30;
/*     */   @Nonnull
/*     */   private final FlagArg presetCone30all;
/*     */   @Nonnull
/*     */   private final FlagArg presetSphere;
/*     */   @Nonnull
/*     */   private final FlagArg presetRay;
/*     */   
/*     */   public NPCMultiSelectCommandBase(@Nonnull String name, @Nonnull String description) {
/*  68 */     super(name, description);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  94 */     this
/*  95 */       .coneAngleArg = withOptionalArg("angle", "server.commands.npc.command.angle.desc", (ArgumentType)ArgTypes.FLOAT);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 100 */     this
/* 101 */       .rangeArg = withOptionalArg("range", "server.commands.npc.command.range.desc", (ArgumentType)ArgTypes.FLOAT);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 106 */     this
/* 107 */       .rolesArg = withOptionalArg("roles", "server.commands.npc.command.roles.desc", (ArgumentType)ArgTypes.STRING);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 112 */     this
/* 113 */       .nearestArg = withFlagArg("nearest", "server.commands.npc.command.nearest.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 118 */     this
/* 119 */       .presetCone30 = withFlagArg("cone", "server.commands.npc.command.preset.cone.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 124 */     this
/* 125 */       .presetCone30all = withFlagArg("coneAll", "server.commands.npc.command.preset.cone_all.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 130 */     this
/* 131 */       .presetSphere = withFlagArg("sphere", "server.commands.npc.command.preset.sphere.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 136 */     this
/* 137 */       .presetRay = withFlagArg("ray", "server.commands.npc.command.preset.ray.desc"); } public NPCMultiSelectCommandBase(@Nonnull String name, @Nonnull String description, boolean requiresConfirmation) { super(name, description, requiresConfirmation); this.coneAngleArg = withOptionalArg("angle", "server.commands.npc.command.angle.desc", (ArgumentType)ArgTypes.FLOAT); this.rangeArg = withOptionalArg("range", "server.commands.npc.command.range.desc", (ArgumentType)ArgTypes.FLOAT); this.rolesArg = withOptionalArg("roles", "server.commands.npc.command.roles.desc", (ArgumentType)ArgTypes.STRING); this.nearestArg = withFlagArg("nearest", "server.commands.npc.command.nearest.desc"); this.presetCone30 = withFlagArg("cone", "server.commands.npc.command.preset.cone.desc"); this.presetCone30all = withFlagArg("coneAll", "server.commands.npc.command.preset.cone_all.desc"); this.presetSphere = withFlagArg("sphere", "server.commands.npc.command.preset.sphere.desc"); this.presetRay = withFlagArg("ray", "server.commands.npc.command.preset.ray.desc"); } public NPCMultiSelectCommandBase(@Nonnull String description) { super(description); this.coneAngleArg = withOptionalArg("angle", "server.commands.npc.command.angle.desc", (ArgumentType)ArgTypes.FLOAT); this.rangeArg = withOptionalArg("range", "server.commands.npc.command.range.desc", (ArgumentType)ArgTypes.FLOAT); this.rolesArg = withOptionalArg("roles", "server.commands.npc.command.roles.desc", (ArgumentType)ArgTypes.STRING); this.nearestArg = withFlagArg("nearest", "server.commands.npc.command.nearest.desc"); this.presetCone30 = withFlagArg("cone", "server.commands.npc.command.preset.cone.desc"); this.presetCone30all = withFlagArg("coneAll", "server.commands.npc.command.preset.cone_all.desc"); this.presetSphere = withFlagArg("sphere", "server.commands.npc.command.preset.sphere.desc"); this.presetRay = withFlagArg("ray", "server.commands.npc.command.preset.ray.desc"); }
/*     */   
/*     */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/*     */     float coneAngleDeg;
/*     */     boolean nearest;
/*     */     Vector3d eyePosition;
/* 143 */     if (this.entityArg.provided(context)) {
/* 144 */       Ref<EntityStore> ref = this.entityArg.get((ComponentAccessor)store, context);
/* 145 */       if (ref == null)
/*     */         return; 
/* 147 */       NPCEntity npc = ensureIsNPC(context, store, ref);
/* 148 */       if (npc == null)
/*     */         return; 
/* 150 */       execute(context, npc, world, store, ref);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 155 */     Ref<EntityStore> playerRef = null;
/*     */     
/* 157 */     if (context.isPlayer()) {
/* 158 */       playerRef = context.senderAsPlayerRef();
/*     */     }
/*     */     
/* 161 */     if (playerRef == null || !playerRef.isValid()) {
/* 162 */       context.sendMessage(MESSAGE_COMMANDS_ERRORS_PLAYER_OR_ARG);
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/* 170 */     Set<String> roleSet = new HashSet<>();
/* 171 */     if (this.rolesArg.provided(context)) {
/* 172 */       String roleString = (String)this.rolesArg.get(context);
/* 173 */       if (roleString == null || roleString.isEmpty()) {
/* 174 */         context.sendMessage(Message.translation("server.commands.errors.npc.no_role_list_provided"));
/*     */         return;
/*     */       } 
/* 177 */       String[] roles = roleString.split(",");
/* 178 */       for (String role : roles) {
/* 179 */         if (!role.isBlank()) {
/* 180 */           if (!NPCPlugin.get().hasRoleName(role)) {
/* 181 */             context.sendMessage(Message.translation("server.commands.errors.npc.unknown_role").param("role", role));
/*     */             return;
/*     */           } 
/* 184 */           roleSet.add(role);
/*     */         } 
/*     */       } 
/*     */     } 
/* 188 */     float range = this.rangeArg.provided(context) ? ((Float)this.rangeArg.get(context)).floatValue() : 8.0F;
/* 189 */     if (range < 0.0F || range > 2048.0F) {
/* 190 */       context.sendMessage(Message.translation("server.commands.errors.validation.range.between_inclusive")
/* 191 */           .param("param", "range").param("min", 0.0F).param("max", 2048.0F).param("value", range));
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 196 */     if (this.presetCone30.provided(context)) {
/* 197 */       coneAngleDeg = 30.0F;
/* 198 */       nearest = true;
/* 199 */     } else if (this.presetCone30all.provided(context)) {
/* 200 */       coneAngleDeg = 30.0F;
/* 201 */       nearest = false;
/* 202 */     } else if (this.presetSphere.provided(context)) {
/* 203 */       coneAngleDeg = 180.0F;
/* 204 */       nearest = false;
/* 205 */     } else if (this.presetRay.provided(context)) {
/* 206 */       coneAngleDeg = 0.0F;
/* 207 */       nearest = true;
/*     */     } else {
/* 209 */       coneAngleDeg = this.coneAngleArg.provided(context) ? ((Float)this.coneAngleArg.get(context)).intValue() : 30.0F;
/* 210 */       if (coneAngleDeg < 0.0F || coneAngleDeg > 180.0F) {
/* 211 */         context.sendMessage(Message.translation("server.commands.errors.validation.range.between_inclusive")
/* 212 */             .param("param", "angle").param("min", 0.0F).param("max", 180.0F).param("value", coneAngleDeg));
/*     */         return;
/*     */       } 
/* 215 */       nearest = this.nearestArg.provided(context);
/*     */     } 
/*     */     
/* 218 */     List<Ref<EntityStore>> refs = null;
/*     */     
/* 220 */     ComponentType<EntityStore, NPCEntity> npcEntityComponentType = NPCEntity.getComponentType();
/* 221 */     assert npcEntityComponentType != null;
/*     */ 
/*     */ 
/*     */     
/* 225 */     if (coneAngleDeg == 0.0F) {
/*     */       
/* 227 */       Ref<EntityStore> ref = TargetUtil.getTargetEntity(playerRef, range, (ComponentAccessor)store);
/* 228 */       if (ref != null && 
/* 229 */         store.getComponent(ref, npcEntityComponentType) != null) {
/* 230 */         refs = new ArrayList<>();
/* 231 */         refs.add(ref);
/*     */       } 
/*     */ 
/*     */       
/* 235 */       eyePosition = Vector3d.ZERO;
/*     */     } else {
/*     */       
/* 238 */       TransformComponent playerTransform = (TransformComponent)store.getComponent(playerRef, TransformComponent.getComponentType());
/* 239 */       assert playerTransform != null;
/*     */       
/* 241 */       Transform viewTransform = TargetUtil.getLook(playerRef, (ComponentAccessor)store);
/*     */       
/* 243 */       eyePosition = viewTransform.getPosition();
/* 244 */       Vector3d eyeDirection = viewTransform.getDirection();
/*     */       
/* 246 */       assert eyePosition.length() == 1.0D;
/*     */       
/* 248 */       refs = TargetUtil.getAllEntitiesInSphere(eyePosition, range, (ComponentAccessor)store);
/*     */       
/* 250 */       float cosineConeAngle = (float)Math.cos((float)Math.toRadians(coneAngleDeg));
/*     */ 
/*     */       
/* 253 */       assert coneAngleDeg != 180.0F || cosineConeAngle == -1.0F;
/*     */       
/* 255 */       refs.removeIf(entityRef -> {
/*     */             if (store.getComponent(entityRef, npcEntityComponentType) == null) {
/*     */               return true;
/*     */             }
/*     */ 
/*     */             
/*     */             if (cosineConeAngle <= -1.0F) {
/*     */               return false;
/*     */             }
/*     */ 
/*     */             
/*     */             TransformComponent entityTransform = (TransformComponent)store.getComponent(entityRef, TransformComponent.getComponentType());
/*     */             
/*     */             assert entityTransform != null;
/*     */             
/*     */             Vector3d direction = Vector3d.directionTo(eyePosition, entityTransform.getPosition());
/*     */             
/*     */             double lengthDirection = direction.length();
/*     */             
/*     */             return (lengthDirection < 1.0E-4D) ? true : ((eyeDirection.dot(direction) < cosineConeAngle * lengthDirection));
/*     */           });
/*     */     } 
/*     */     
/* 278 */     if (refs != null && !refs.isEmpty() && !roleSet.isEmpty())
/*     */     {
/* 280 */       refs.removeIf(ref -> {
/*     */             NPCEntity npc = (NPCEntity)store.getComponent(ref, npcEntityComponentType);
/*     */             
/*     */             return !roleSet.contains(npc.getRoleName());
/*     */           });
/*     */     }
/* 286 */     if (refs == null || refs.isEmpty()) {
/* 287 */       context.sendMessage(MESSAGE_COMMANDS_ERRORS_NO_ENTITY_IN_VIEW);
/*     */       
/*     */       return;
/*     */     } 
/* 291 */     if (nearest && refs.size() > 1) {
/*     */       
/* 293 */       Ref<EntityStore> nearestRef = refs.getFirst();
/* 294 */       double nearestDistanceSq = Double.MAX_VALUE;
/*     */       
/* 296 */       for (Ref<EntityStore> ref : refs) {
/* 297 */         TransformComponent npcTransform = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/* 298 */         assert npcTransform != null;
/*     */         
/* 300 */         double distanceSq = Vector3d.directionTo(eyePosition, npcTransform.getPosition()).squaredLength();
/*     */         
/* 302 */         if (distanceSq < nearestDistanceSq) {
/* 303 */           nearestDistanceSq = distanceSq;
/* 304 */           nearestRef = ref;
/*     */         } 
/*     */       } 
/*     */       
/* 308 */       refs = List.of(nearestRef);
/*     */     } 
/*     */ 
/*     */     
/* 312 */     processEntityList(context, world, store, refs);
/*     */   }
/*     */   
/*     */   protected void processEntityList(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store, @Nonnull List<Ref<EntityStore>> refs) {
/* 316 */     refs.forEach(ref -> {
/*     */           NPCEntity npc = (NPCEntity)store.getComponent(ref, NPCEntity.getComponentType());
/*     */           assert npc != null;
/*     */           execute(context, npc, world, store, ref);
/*     */         });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\commands\NPCMultiSelectCommandBase.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */