/*     */ package com.hypixel.hytale.server.core.command.commands.debug.component.hitboxcollision;
/*     */ 
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.EntityWrappedArg;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractTargetPlayerCommand;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*     */ import com.hypixel.hytale.server.core.modules.entity.hitboxcollision.HitboxCollision;
/*     */ import com.hypixel.hytale.server.core.modules.entity.hitboxcollision.HitboxCollisionConfig;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class HitboxCollisionAddCommand
/*     */   extends AbstractCommandCollection
/*     */ {
/*     */   @Nonnull
/*  29 */   private static final Message MESSAGE_COMMANDS_ERRORS_TARGET_NOT_IN_WORLD = Message.translation("server.commands.errors.targetNotInWorld");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HitboxCollisionAddCommand() {
/*  35 */     super("add", "server.commands.hitboxcollision.add.desc");
/*  36 */     addSubCommand((AbstractCommand)new HitboxCollisionAddEntityCommand());
/*  37 */     addSubCommand((AbstractCommand)new HitboxCollisionAddSelfCommand());
/*     */   }
/*     */ 
/*     */   
/*     */   public static class HitboxCollisionAddEntityCommand
/*     */     extends AbstractWorldCommand
/*     */   {
/*     */     @Nonnull
/*  45 */     public static final Message MESSAGE_COMMANDS_HIT_BOX_COLLISION_ADD_ALREADY_ADDED = Message.translation("server.commands.hitboxcollision.add.alreadyAdded");
/*     */     @Nonnull
/*  47 */     public static final Message MESSAGE_COMMANDS_HIT_BOX_COLLISION_ADD_SUCCESS = Message.translation("server.commands.hitboxcollision.add.success");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*  53 */     private final RequiredArg<HitboxCollisionConfig> hitboxCollisionConfigArg = withRequiredArg("hitboxCollisionConfig", "server.commands.hitboxcollision.add.hitboxCollisionConfig.desc", (ArgumentType)ArgTypes.HITBOX_COLLISION_CONFIG);
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*  58 */     private final EntityWrappedArg entityArg = (EntityWrappedArg)
/*  59 */       withOptionalArg("entity", "server.commands.hitboxcollision.add.entity.desc", ArgTypes.ENTITY_ID);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public HitboxCollisionAddEntityCommand() {
/*  65 */       super("entity", "server.commands.hitboxcollision.add.entity.desc");
/*     */     }
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/*  70 */       Ref<EntityStore> entityRef = this.entityArg.get((ComponentAccessor)store, context);
/*  71 */       if (entityRef == null || !entityRef.isValid()) {
/*  72 */         context.sendMessage(HitboxCollisionAddCommand.MESSAGE_COMMANDS_ERRORS_TARGET_NOT_IN_WORLD);
/*     */         
/*     */         return;
/*     */       } 
/*  76 */       HitboxCollisionConfig hitboxCollisionConfig = (HitboxCollisionConfig)this.hitboxCollisionConfigArg.get(context);
/*     */       
/*  78 */       if (store.getArchetype(entityRef).contains(HitboxCollision.getComponentType())) {
/*  79 */         context.sendMessage(MESSAGE_COMMANDS_HIT_BOX_COLLISION_ADD_ALREADY_ADDED);
/*     */         
/*     */         return;
/*     */       } 
/*  83 */       store.addComponent(entityRef, HitboxCollision.getComponentType(), (Component)new HitboxCollision(hitboxCollisionConfig));
/*  84 */       context.sendMessage(MESSAGE_COMMANDS_HIT_BOX_COLLISION_ADD_SUCCESS);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class HitboxCollisionAddSelfCommand
/*     */     extends AbstractTargetPlayerCommand
/*     */   {
/*     */     @Nonnull
/*  93 */     private static final Message MESSAGE_COMMANDS_HIT_BOX_COLLISION_ADD_ALREADY_ADDED = Message.translation("server.commands.hitboxcollision.add.alreadyAdded");
/*     */     @Nonnull
/*  95 */     private static final Message MESSAGE_COMMANDS_HIT_BOX_COLLISION_ADD_SUCCESS = Message.translation("server.commands.hitboxcollision.add.success");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 101 */     private final RequiredArg<HitboxCollisionConfig> hitboxCollisionConfigArg = withRequiredArg("hitboxCollisionConfig", "server.commands.hitboxcollision.add.hitboxCollisionConfig.desc", (ArgumentType)ArgTypes.HITBOX_COLLISION_CONFIG);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public HitboxCollisionAddSelfCommand() {
/* 107 */       super("self", "server.commands.hitboxcollision.add.self.desc");
/*     */     }
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nullable Ref<EntityStore> sourceRef, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 112 */       if (store.getArchetype(ref).contains(HitboxCollision.getComponentType())) {
/* 113 */         context.sendMessage(MESSAGE_COMMANDS_HIT_BOX_COLLISION_ADD_ALREADY_ADDED);
/*     */         
/*     */         return;
/*     */       } 
/* 117 */       HitboxCollisionConfig hitboxCollisionConfig = (HitboxCollisionConfig)this.hitboxCollisionConfigArg.get(context);
/* 118 */       store.addComponent(ref, HitboxCollision.getComponentType(), (Component)new HitboxCollision(hitboxCollisionConfig));
/* 119 */       context.sendMessage(MESSAGE_COMMANDS_HIT_BOX_COLLISION_ADD_SUCCESS);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\debug\component\hitboxcollision\HitboxCollisionAddCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */