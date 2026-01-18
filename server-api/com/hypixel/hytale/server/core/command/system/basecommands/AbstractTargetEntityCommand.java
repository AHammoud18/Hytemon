/*     */ package com.hypixel.hytale.server.core.command.system.basecommands;
/*     */ 
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.spatial.SpatialResource;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.EntityWrappedArg;
/*     */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.core.util.TargetUtil;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectList;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectLists;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.Executor;
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
/*     */ public abstract class AbstractTargetEntityCommand
/*     */   extends AbstractAsyncCommand
/*     */ {
/*  43 */   private static final Message MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD = Message.translation("server.commands.errors.playerNotInWorld");
/*  44 */   private static final Message MESSAGE_GENERAL_NO_ENTITY_IN_VIEW = Message.translation("server.general.noEntityInView");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  50 */   private final OptionalArg<World> worldArg = withOptionalArg("world", "server.commands.worldthread.arg.desc", (ArgumentType)ArgTypes.WORLD);
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  55 */   private final OptionalArg<Double> radiusArg = (OptionalArg<Double>)
/*  56 */     withOptionalArg("radius", "server.commands.entity.radius.desc", (ArgumentType)ArgTypes.DOUBLE)
/*  57 */     .addValidator(Validators.greaterThan(Double.valueOf(0.0D)));
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  63 */   private final OptionalArg<PlayerRef> playerArg = withOptionalArg("player", "server.commands.argtype.player.desc", (ArgumentType)ArgTypes.PLAYER_REF);
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  68 */   private final EntityWrappedArg entityArg = (EntityWrappedArg)
/*  69 */     withOptionalArg("entity", "server.commands.entity.entity.desc", ArgTypes.ENTITY_ID);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractTargetEntityCommand(@Nonnull String name, @Nonnull String description) {
/*  78 */     super(name, description);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractTargetEntityCommand(@Nonnull String name, @Nonnull String description, boolean requiresConfirmation) {
/*  89 */     super(name, description, requiresConfirmation);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractTargetEntityCommand(@Nonnull String description) {
/*  98 */     super(description);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected final CompletableFuture<Void> executeAsync(@Nonnull CommandContext context) {
/*     */     World world;
/* 106 */     if (this.worldArg.provided(context)) {
/* 107 */       world = (World)this.worldArg.get(context);
/* 108 */     } else if (context.isPlayer()) {
/* 109 */       Ref<EntityStore> ref = context.senderAsPlayerRef();
/* 110 */       if (ref == null || !ref.isValid()) {
/* 111 */         context.sendMessage(MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD);
/* 112 */         return CompletableFuture.completedFuture(null);
/*     */       } 
/* 114 */       world = ((EntityStore)ref.getStore().getExternalData()).getWorld();
/*     */     } else {
/* 116 */       context.sendMessage(Message.translation("server.commands.errors.playerOrArg")
/* 117 */           .param("option", "world"));
/* 118 */       return CompletableFuture.completedFuture(null);
/*     */     } 
/*     */     
/* 121 */     Store<EntityStore> store = world.getEntityStore().getStore();
/*     */ 
/*     */     
/* 124 */     return runAsync(context, () -> { ObjectList<Ref<EntityStore>> entitiesToOperateOn; if (this.radiusArg.provided(context)) { if (!context.isPlayer()) { context.sendMessage(Message.translation("server.commands.errors.playerOrArg").param("option", "radius")); return; }  Ref<EntityStore> playerRef = context.senderAsPlayerRef(); if (playerRef == null || !playerRef.isValid()) { context.sendMessage(MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD); return; }  TransformComponent transformComponent = (TransformComponent)store.getComponent(playerRef, TransformComponent.getComponentType()); if (transformComponent == null) { context.sendMessage(MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD); return; }  double radius = ((Double)this.radiusArg.get(context)).doubleValue(); Vector3d position = transformComponent.getPosition(); ObjectArrayList objectArrayList = new ObjectArrayList(); ObjectList<Ref<EntityStore>> results = SpatialResource.getThreadLocalReferenceList(); SpatialResource<Ref<EntityStore>, EntityStore> entitySpatialResource = (SpatialResource<Ref<EntityStore>, EntityStore>)store.getResource(EntityModule.get().getEntitySpatialResourceType()); entitySpatialResource.getSpatialStructure().collect(position, radius, (List)results); objectArrayList.addAll(results); SpatialResource<Ref<EntityStore>, EntityStore> playerSpatialResource = (SpatialResource<Ref<EntityStore>, EntityStore>)store.getResource(EntityModule.get().getPlayerSpatialResourceType()); playerSpatialResource.getSpatialStructure().collect(position, radius, (List)results); objectArrayList.addAll(results); } else if (this.playerArg.provided(context)) { PlayerRef targetPlayerRef = (PlayerRef)this.playerArg.get(context); Ref<EntityStore> targetRef = targetPlayerRef.getReference(); if (targetRef == null || !targetRef.isValid()) { context.sendMessage(MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD); return; }  entitiesToOperateOn = ObjectLists.singleton(targetRef); } else if (this.entityArg.provided(context)) { Ref<EntityStore> entityRef = this.entityArg.get((ComponentAccessor)store, context); if (entityRef == null || !entityRef.isValid()) { context.sendMessage(MESSAGE_GENERAL_NO_ENTITY_IN_VIEW); return; }  entitiesToOperateOn = ObjectLists.singleton(entityRef); } else if (context.isPlayer()) { Ref<EntityStore> playerRef = context.senderAsPlayerRef(); if (playerRef == null || !playerRef.isValid()) { context.sendMessage(MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD); return; }  Ref<EntityStore> entityRef = TargetUtil.getTargetEntity(playerRef, (ComponentAccessor)store); if (entityRef == null) { context.sendMessage(MESSAGE_GENERAL_NO_ENTITY_IN_VIEW); return; }  entitiesToOperateOn = ObjectLists.singleton(entityRef); } else { context.sendMessage(Message.translation("server.commands.errors.playerOrArg").param("option", "entity")); return; }  execute(context, entitiesToOperateOn, world, store); }(Executor)world);
/*     */   }
/*     */   
/*     */   protected abstract void execute(@Nonnull CommandContext paramCommandContext, @Nonnull ObjectList<Ref<EntityStore>> paramObjectList, @Nonnull World paramWorld, @Nonnull Store<EntityStore> paramStore);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\system\basecommands\AbstractTargetEntityCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */