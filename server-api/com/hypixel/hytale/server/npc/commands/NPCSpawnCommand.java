/*     */ package com.hypixel.hytale.server.npc.commands;
/*     */ 
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.common.util.RandomUtil;
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.function.consumer.TriConsumer;
/*     */ import com.hypixel.hytale.math.shape.Box;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.protocol.PlayerSkin;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.Model;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset;
/*     */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*     */ import com.hypixel.hytale.server.core.command.system.exceptions.GeneralCommandException;
/*     */ import com.hypixel.hytale.server.core.cosmetics.CosmeticsModule;
/*     */ import com.hypixel.hytale.server.core.entity.Frozen;
/*     */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.entity.entities.player.pages.CustomUIPage;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.BoundingBox;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.HeadRotation;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.player.ApplyRandomSkinPersistedComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.player.PlayerSkinComponent;
/*     */ import com.hypixel.hytale.server.core.modules.physics.util.PhysicsMath;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.flock.FlockPlugin;
/*     */ import com.hypixel.hytale.server.flock.config.FlockAsset;
/*     */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.Builder;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.BuilderInfo;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.pages.EntitySpawnPage;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import com.hypixel.hytale.server.npc.role.RoleDebugFlags;
/*     */ import com.hypixel.hytale.server.spawning.ISpawnableWithModel;
/*     */ import com.hypixel.hytale.server.spawning.SpawnTestResult;
/*     */ import com.hypixel.hytale.server.spawning.SpawningContext;
/*     */ import it.unimi.dsi.fastutil.Pair;
/*     */ import java.util.EnumSet;
/*     */ import java.util.Random;
/*     */ import java.util.concurrent.ThreadLocalRandom;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class NPCSpawnCommand extends AbstractPlayerCommand {
/*     */   private static final double PLAYER_FOOT_POINT_EPSILON = 0.01D;
/*     */   @Nonnull
/*  66 */   private final RequiredArg<BuilderInfo> roleArg = withRequiredArg("role", "server.commands.npc.spawn.role.desc", (ArgumentType)NPCCommand.NPC_ROLE);
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  71 */   private final OptionalArg<Integer> countArg = (OptionalArg<Integer>)
/*  72 */     withOptionalArg("count", "server.commands.npc.spawn.count.desc", (ArgumentType)ArgTypes.INTEGER)
/*  73 */     .addValidator(Validators.greaterThan(Integer.valueOf(0)));
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  78 */   private final OptionalArg<Double> radiusArg = (OptionalArg<Double>)
/*  79 */     withOptionalArg("radius", "server.commands.npc.spawn.radius.desc", (ArgumentType)ArgTypes.DOUBLE)
/*  80 */     .addValidator(Validators.greaterThan(Double.valueOf(0.0D)));
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  86 */   private final OptionalArg<String> flagsArg = withOptionalArg("flags", "server.commands.npc.spawn.flags.desc", (ArgumentType)ArgTypes.STRING);
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  91 */   private final OptionalArg<Double> speedArg = (OptionalArg<Double>)
/*  92 */     withOptionalArg("speed", "server.commands.npc.spawn.speed.desc", (ArgumentType)ArgTypes.DOUBLE)
/*  93 */     .addValidator(Validators.greaterThan(Double.valueOf(0.0D)));
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  99 */   private final FlagArg nonRandomArg = withFlagArg("nonrandom", "server.commands.npc.spawn.random.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 105 */   private final OptionalArg<String> positionSetArg = withOptionalArg("position", "server.commands.npc.spawn.position.desc", (ArgumentType)ArgTypes.STRING);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 111 */   private final OptionalArg<String> posOffsetArg = withOptionalArg("posOffset", "server.commands.npc.spawn.posOffset.desc", (ArgumentType)ArgTypes.STRING);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 117 */   private final OptionalArg<String> headRotationArg = withOptionalArg("headRotation", "server.commands.npc.spawn.headRotation.desc", (ArgumentType)ArgTypes.STRING);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 123 */   private final OptionalArg<String> bodyRotationArg = withOptionalArg("bodyRotation", "server.commands.npc.spawn.bodyRotation.desc", (ArgumentType)ArgTypes.STRING);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 129 */   private final FlagArg randomRotationArg = withFlagArg("randomRotation", "server.commands.npc.spawn.randomRotation.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 135 */   private final FlagArg facingRotationArg = withFlagArg("facingRotation", "server.commands.npc.spawn.facingRotation.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 141 */   private final OptionalArg<String> flockArg = withOptionalArg("flock", "server.commands.npc.spawn.flock.desc", (ArgumentType)ArgTypes.STRING);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 147 */   private final FlagArg testArg = withFlagArg("test", "server.commands.npc.spawn.test.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 153 */   private final FlagArg spawnOnGroundArg = withFlagArg("spawnOnGround", "server.commands.npc.spawn.spawnOnGround.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 159 */   private final FlagArg frozenArg = withFlagArg("frozen", "server.commands.npc.spawn.frozen.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 165 */   private final FlagArg randomModelArg = withFlagArg("randomModel", "server.commands.npc.spawn.randomModel.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 171 */   private final OptionalArg<Float> scaleArg = withOptionalArg("scale", "server.commands.npc.spawn.scale.desc", (ArgumentType)ArgTypes.FLOAT);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 177 */   private final FlagArg bypassScaleLimitsArg = withFlagArg("bypassScaleLimits", "server.commands.npc.spawn.bypassScaleLimits.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NPCSpawnCommand() {
/* 183 */     super("spawn", "server.commands.npc.spawn.desc");
/* 184 */     addUsageVariant((AbstractCommand)new SpawnPageCommand());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 194 */     NPCPlugin npcPlugin = NPCPlugin.get();
/*     */     
/* 196 */     BuilderInfo roleInfo = (BuilderInfo)this.roleArg.get(context);
/* 197 */     int roleIndex = roleInfo.getIndex();
/*     */     
/* 199 */     HeadRotation headRotationComponent = (HeadRotation)store.getComponent(ref, HeadRotation.getComponentType());
/* 200 */     assert headRotationComponent != null;
/* 201 */     Vector3f playerHeadRotation = headRotationComponent.getRotation();
/*     */     
/* 203 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/* 204 */     assert transformComponent != null;
/* 205 */     Vector3d playerPosition = transformComponent.getPosition();
/*     */     
/* 207 */     BoundingBox boundingBoxComponent = (BoundingBox)store.getComponent(ref, BoundingBox.getComponentType());
/* 208 */     assert boundingBoxComponent != null;
/* 209 */     Box playerBoundingBox = boundingBoxComponent.getBoundingBox();
/*     */     
/* 211 */     int count = this.countArg.provided(context) ? ((Integer)this.countArg.get(context)).intValue() : 1;
/* 212 */     double radius = this.radiusArg.provided(context) ? ((Double)this.radiusArg.get(context)).doubleValue() : 8.0D;
/* 213 */     String flagsString = this.flagsArg.provided(context) ? (String)this.flagsArg.get(context) : null;
/* 214 */     EnumSet<RoleDebugFlags> flags = (flagsString != null) ? RoleDebugFlags.getFlags(flagsString.split(",")) : RoleDebugFlags.getPreset("none");
/* 215 */     Vector3d velocity = new Vector3d(Vector3d.ZERO);
/* 216 */     if (this.speedArg.provided(context)) {
/* 217 */       PhysicsMath.vectorFromAngles(playerHeadRotation.getYaw(), playerHeadRotation.getPitch(), velocity);
/* 218 */       velocity.setLength(((Double)this.speedArg.get(context)).doubleValue());
/*     */     } 
/*     */     
/* 221 */     Random random = ((Boolean)this.nonRandomArg.get(context)).booleanValue() ? new Random(0L) : ThreadLocalRandom.current();
/* 222 */     Vector3d posOffset = this.posOffsetArg.provided(context) ? parseVector3d(context, (String)this.posOffsetArg.get(context)) : null;
/* 223 */     Vector3f headRotation = this.headRotationArg.provided(context) ? parseVector3f(context, (String)this.headRotationArg.get(context)) : null;
/*     */     
/* 225 */     boolean randomRotation = false;
/* 226 */     Vector3f rotation = playerHeadRotation;
/* 227 */     if (this.bodyRotationArg.provided(context)) {
/* 228 */       rotation = parseVector3f(context, (String)this.bodyRotationArg.get(context));
/* 229 */     } else if (((Boolean)this.randomRotationArg.get(context)).booleanValue()) {
/* 230 */       randomRotation = true;
/* 231 */     } else if (((Boolean)this.facingRotationArg.get(context)).booleanValue()) {
/*     */       
/* 233 */       rotation.setY(rotation.getY() - 3.1415927F);
/*     */     } 
/*     */     
/* 236 */     String flockSizeString = this.flockArg.provided(context) ? (String)this.flockArg.get(context) : "1";
/* 237 */     Integer flockSize = parseFlockSize(context, flockSizeString);
/* 238 */     if (flockSize == null) {
/*     */       return;
/*     */     }
/* 241 */     Boolean frozen = (Boolean)this.frozenArg.get(context);
/*     */     
/* 243 */     npcPlugin.forceValidation(roleIndex);
/* 244 */     if (!npcPlugin.testAndValidateRole(roleInfo)) {
/* 245 */       throw new GeneralCommandException(Message.translation("server.commands.npc.spawn.validation_failed"));
/*     */     }
/*     */     
/*     */     try {
/* 249 */       for (int i = 0; i < count; i++) {
/* 250 */         ISpawnableWithModel spawnable; Model model; Ref<EntityStore> npcRef; NPCEntity npc; Builder<Role> roleBuilder = npcPlugin.tryGetCachedValidRole(roleIndex);
/* 251 */         if (roleBuilder == null) throw new IllegalArgumentException("Can't find a matching role builder"); 
/* 252 */         if (roleBuilder instanceof ISpawnableWithModel) { spawnable = (ISpawnableWithModel)roleBuilder; }
/* 253 */         else { throw new IllegalArgumentException("Role builder must support ISpawnableWithModel interface"); }
/*     */ 
/*     */         
/* 256 */         if (!roleBuilder.isSpawnable()) {
/* 257 */           throw new IllegalArgumentException("Abstract role templates cannot be spawned directly - a variant needs to be created!");
/*     */         }
/*     */         
/* 260 */         SpawningContext spawningContext = new SpawningContext();
/* 261 */         if (!spawningContext.setSpawnable(spawnable)) throw new GeneralCommandException(Message.translation("server.commands.npc.spawn.cantSetRolebuilder"));
/*     */ 
/*     */         
/* 264 */         TriConsumer<NPCEntity, Ref<EntityStore>, Store<EntityStore>> skinApplyingFunction = null;
/* 265 */         if (((Boolean)this.randomModelArg.get(context)).booleanValue()) {
/* 266 */           PlayerSkin playerSkin = CosmeticsModule.get().generateRandomSkin(RandomUtil.getSecureRandom());
/* 267 */           model = CosmeticsModule.get().createModel(playerSkin);
/* 268 */           skinApplyingFunction = ((npcEntity, entityStoreRef, entityStore) -> {
/*     */               entityStore.putComponent(entityStoreRef, PlayerSkinComponent.getComponentType(), (Component)new PlayerSkinComponent(playerSkin));
/*     */               entityStore.putComponent(entityStoreRef, ApplyRandomSkinPersistedComponent.getComponentType(), (Component)ApplyRandomSkinPersistedComponent.INSTANCE);
/*     */             });
/*     */         } else {
/* 273 */           model = spawningContext.getModel();
/*     */         } 
/*     */ 
/*     */         
/* 277 */         if (randomRotation) {
/* 278 */           rotation = new Vector3f(0.0F, (float)(2.0D * random.nextDouble() * Math.PI), 0.0F);
/*     */         }
/*     */ 
/*     */         
/* 282 */         if (this.scaleArg.provided(context)) {
/* 283 */           ModelAsset modelAsset = (ModelAsset)ModelAsset.getAssetMap().getAsset(model.getModelAssetId());
/* 284 */           assert modelAsset != null;
/*     */           
/* 286 */           Float scale = (Float)this.scaleArg.get(context);
/* 287 */           if (!((Boolean)this.bypassScaleLimitsArg.get(context)).booleanValue()) {
/* 288 */             scale = Float.valueOf(MathUtil.clamp(scale.floatValue(), modelAsset.getMinScale(), modelAsset.getMaxScale()));
/*     */           }
/*     */           
/* 291 */           model = Model.createScaledModel(modelAsset, scale.floatValue());
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 297 */         if (count == 1 && ((Boolean)this.testArg.get(context)).booleanValue()) {
/* 298 */           if (!spawningContext.set(world, playerPosition.x, playerPosition.y, playerPosition.z)) {
/* 299 */             throw new GeneralCommandException(Message.translation("server.commands.npc.spawn.cantSpawnNotEnoughSpace"));
/*     */           }
/* 301 */           if (spawnable.canSpawn(spawningContext) != SpawnTestResult.TEST_OK) {
/* 302 */             throw new GeneralCommandException(Message.translation("server.commands.npc.spawn.cantSpawnNotSuitable"));
/*     */           }
/* 304 */           Vector3d spawnPosition = spawningContext.newPosition();
/* 305 */           if (posOffset != null) {
/* 306 */             spawnPosition.add(posOffset);
/*     */           }
/*     */           
/* 309 */           Pair<Ref<EntityStore>, NPCEntity> npcPair = npcPlugin.spawnEntity(store, roleIndex, spawnPosition, rotation, model, skinApplyingFunction);
/* 310 */           npcRef = (Ref<EntityStore>)npcPair.first();
/* 311 */           npc = (NPCEntity)npcPair.second();
/*     */           
/* 313 */           if (flockSize.intValue() > 1) {
/* 314 */             FlockPlugin.trySpawnFlock(npcRef, npc, store, roleIndex, spawnPosition, rotation, flockSize.intValue(), skinApplyingFunction);
/*     */           }
/*     */         } else {
/*     */           Vector3d position;
/*     */           
/* 319 */           if (this.positionSetArg.provided(context)) {
/* 320 */             position = parseVector3d(context, (String)this.positionSetArg.get(context));
/* 321 */             if (position == null) {
/*     */               return;
/*     */             }
/* 324 */             position.y -= (model.getBoundingBox()).min.y;
/*     */           } else {
/* 326 */             position = new Vector3d(playerPosition);
/* 327 */             position.y = Math.floor(position.y + playerBoundingBox.min.y + 0.01D) - (model.getBoundingBox()).min.y;
/*     */           } 
/* 329 */           if (posOffset != null) {
/* 330 */             position.add(posOffset);
/*     */           }
/*     */           
/* 333 */           Pair<Ref<EntityStore>, NPCEntity> npcPair = npcPlugin.spawnEntity(store, roleIndex, position, rotation, model, skinApplyingFunction);
/* 334 */           npcRef = (Ref<EntityStore>)npcPair.first();
/* 335 */           npc = (NPCEntity)npcPair.second();
/*     */           
/* 337 */           if (flockSize.intValue() > 1) {
/* 338 */             FlockPlugin.trySpawnFlock(npcRef, npc, store, roleIndex, position, rotation, flockSize.intValue(), skinApplyingFunction);
/*     */           }
/*     */         } 
/*     */         
/* 342 */         TransformComponent npcTransformComponent = (TransformComponent)store.getComponent(npcRef, TransformComponent.getComponentType());
/* 343 */         assert npcTransformComponent != null;
/*     */         
/* 345 */         HeadRotation npcHeadRotationComponent = (HeadRotation)store.getComponent(npcRef, HeadRotation.getComponentType());
/* 346 */         assert npcHeadRotationComponent != null;
/*     */         
/* 348 */         UUIDComponent npcUuidComponent = (UUIDComponent)store.getComponent(npcRef, UUIDComponent.getComponentType());
/* 349 */         assert npcUuidComponent != null;
/*     */         
/* 351 */         if (headRotation != null) {
/* 352 */           npcHeadRotationComponent.getRotation().assign(headRotation);
/* 353 */           store.ensureComponent(npcRef, Frozen.getComponentType());
/*     */         } 
/*     */         
/* 356 */         Vector3d npcPosition = npcTransformComponent.getPosition();
/* 357 */         double x = npcPosition.getX();
/* 358 */         double y = npcPosition.getY();
/* 359 */         double z = npcPosition.getZ();
/* 360 */         if (count > 1) {
/* 361 */           x += random.nextDouble() * 2.0D * radius - radius;
/* 362 */           z += random.nextDouble() * 2.0D * radius - radius;
/* 363 */           y += ((Boolean)this.spawnOnGroundArg.get(context)).booleanValue() ? 0.1D : (random.nextDouble() * 2.0D + 5.0D);
/*     */         } else {
/* 365 */           y += 0.1D;
/*     */         } 
/* 367 */         npcPosition.assign(x, y, z);
/* 368 */         npc.saveLeashInformation(npcPosition, npcTransformComponent.getRotation());
/*     */         
/* 370 */         if (!velocity.equals(Vector3d.ZERO)) {
/* 371 */           npc.getRole().forceVelocity(velocity, null, false);
/*     */         }
/* 373 */         if (frozen.booleanValue()) {
/* 374 */           store.ensureComponent(npcRef, Frozen.getComponentType());
/*     */         }
/*     */         
/* 377 */         EnumSet<RoleDebugFlags> debugFlags = npc.getRoleDebugFlags().clone();
/* 378 */         debugFlags.addAll(flags);
/*     */ 
/*     */         
/* 381 */         if (!debugFlags.isEmpty()) {
/* 382 */           Holder<EntityStore> holder = store.removeEntity(npcRef, RemoveReason.UNLOAD);
/* 383 */           npc.setRoleDebugFlags(debugFlags);
/* 384 */           store.addEntity(holder, AddReason.LOAD);
/*     */         } 
/*     */         
/* 387 */         NPCPlugin.get().getLogger().at(Level.INFO).log("%s created with id %s at position %s", npc.getRoleName(), npcUuidComponent.getUuid(), 
/* 388 */             Vector3d.formatShortString(npcPosition));
/*     */       } 
/* 390 */     } catch (IllegalArgumentException|IllegalStateException|NullPointerException e) {
/* 391 */       NPCPlugin.get().getLogger().at(Level.WARNING).log("Spawn failed: " + e.getMessage());
/* 392 */       throw new GeneralCommandException(Message.translation("server.commands.npc.spawn.failed")
/* 393 */           .param("reason", e.getMessage()));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class SpawnPageCommand
/*     */     extends AbstractPlayerCommand
/*     */   {
/*     */     public SpawnPageCommand() {
/* 405 */       super("server.commands.npc.spawn.page.desc");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 415 */       Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 416 */       assert playerComponent != null;
/*     */       
/* 418 */       PlayerRef playerRefComponent = (PlayerRef)store.getComponent(ref, PlayerRef.getComponentType());
/* 419 */       assert playerRefComponent != null;
/*     */       
/* 421 */       playerComponent.getPageManager().openCustomPage(ref, store, (CustomUIPage)new EntitySpawnPage(playerRefComponent));
/*     */     }
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private Vector3d parseVector3d(@Nonnull CommandContext context, @Nonnull String str) {
/* 427 */     String[] parts = str.split(",");
/* 428 */     if (parts.length != 3) {
/* 429 */       context.sendMessage(Message.raw("Invalid Vector3d format: must be three comma-separated doubles"));
/* 430 */       return null;
/*     */     } 
/*     */     try {
/* 433 */       return new Vector3d(Double.parseDouble(parts[0]), Double.parseDouble(parts[1]), Double.parseDouble(parts[2]));
/* 434 */     } catch (NumberFormatException e) {
/* 435 */       context.sendMessage(Message.raw("Invalid Vector3d format: " + e.getMessage()));
/* 436 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private Vector3f parseVector3f(@Nonnull CommandContext context, @Nonnull String str) {
/* 442 */     String[] parts = str.split(",");
/* 443 */     if (parts.length != 3) {
/* 444 */       context.sendMessage(Message.raw("Invalid Vector3f format: must be three comma-separated floats"));
/* 445 */       return null;
/*     */     } 
/*     */     try {
/* 448 */       return new Vector3f(Float.parseFloat(parts[0]), Float.parseFloat(parts[1]), Float.parseFloat(parts[2]));
/* 449 */     } catch (NumberFormatException e) {
/* 450 */       context.sendMessage(Message.raw("Invalid Vector3f format: " + e.getMessage()));
/* 451 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private Integer parseFlockSize(@Nonnull CommandContext context, @Nonnull String str) {
/*     */     try {
/* 458 */       Integer size = Integer.valueOf(str);
/* 459 */       if (size.intValue() <= 0) {
/* 460 */         context.sendMessage(Message.raw("Flock size must be greater than 0!"));
/* 461 */         return null;
/*     */       } 
/* 463 */       return size;
/* 464 */     } catch (NumberFormatException e) {
/* 465 */       FlockAsset flockDefinition = (FlockAsset)FlockAsset.getAssetMap().getAsset(str);
/* 466 */       if (flockDefinition == null) {
/* 467 */         context.sendMessage(Message.raw("No such flock asset: " + str));
/* 468 */         return null;
/*     */       } 
/* 470 */       return Integer.valueOf(flockDefinition.pickFlockSize());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\commands\NPCSpawnCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */