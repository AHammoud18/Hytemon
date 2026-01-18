/*     */ package com.hypixel.hytale.server.core.command.commands.debug.component.hitboxcollision;
/*     */ 
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractTargetPlayerCommand;
/*     */ import com.hypixel.hytale.server.core.modules.entity.hitboxcollision.HitboxCollision;
/*     */ import com.hypixel.hytale.server.core.modules.entity.hitboxcollision.HitboxCollisionConfig;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
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
/*     */ public class HitboxCollisionAddSelfCommand
/*     */   extends AbstractTargetPlayerCommand
/*     */ {
/*     */   @Nonnull
/*  93 */   private static final Message MESSAGE_COMMANDS_HIT_BOX_COLLISION_ADD_ALREADY_ADDED = Message.translation("server.commands.hitboxcollision.add.alreadyAdded");
/*     */   @Nonnull
/*  95 */   private static final Message MESSAGE_COMMANDS_HIT_BOX_COLLISION_ADD_SUCCESS = Message.translation("server.commands.hitboxcollision.add.success");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 101 */   private final RequiredArg<HitboxCollisionConfig> hitboxCollisionConfigArg = withRequiredArg("hitboxCollisionConfig", "server.commands.hitboxcollision.add.hitboxCollisionConfig.desc", (ArgumentType)ArgTypes.HITBOX_COLLISION_CONFIG);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HitboxCollisionAddSelfCommand() {
/* 107 */     super("self", "server.commands.hitboxcollision.add.self.desc");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void execute(@Nonnull CommandContext context, @Nullable Ref<EntityStore> sourceRef, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 112 */     if (store.getArchetype(ref).contains(HitboxCollision.getComponentType())) {
/* 113 */       context.sendMessage(MESSAGE_COMMANDS_HIT_BOX_COLLISION_ADD_ALREADY_ADDED);
/*     */       
/*     */       return;
/*     */     } 
/* 117 */     HitboxCollisionConfig hitboxCollisionConfig = (HitboxCollisionConfig)this.hitboxCollisionConfigArg.get(context);
/* 118 */     store.addComponent(ref, HitboxCollision.getComponentType(), (Component)new HitboxCollision(hitboxCollisionConfig));
/* 119 */     context.sendMessage(MESSAGE_COMMANDS_HIT_BOX_COLLISION_ADD_SUCCESS);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\debug\component\hitboxcollision\HitboxCollisionAddCommand$HitboxCollisionAddSelfCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */