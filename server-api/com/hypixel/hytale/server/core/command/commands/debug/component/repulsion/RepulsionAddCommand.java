/*     */ package com.hypixel.hytale.server.core.command.commands.debug.component.repulsion;
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
/*     */ import com.hypixel.hytale.server.core.modules.entity.repulsion.Repulsion;
/*     */ import com.hypixel.hytale.server.core.modules.entity.repulsion.RepulsionConfig;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class RepulsionAddCommand
/*     */   extends AbstractCommandCollection
/*     */ {
/*  28 */   private static final Message MESSAGE_COMMANDS_ERRORS_TARGET_NOT_IN_WORLD = Message.translation("server.commands.errors.targetNotInWorld");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RepulsionAddCommand() {
/*  34 */     super("add", "server.commands.repulsion.add.desc");
/*  35 */     addSubCommand((AbstractCommand)new RepulsionAddEntityCommand());
/*  36 */     addSubCommand((AbstractCommand)new RepulsionAddSelfCommand());
/*     */   }
/*     */ 
/*     */   
/*     */   public static class RepulsionAddEntityCommand
/*     */     extends AbstractWorldCommand
/*     */   {
/*     */     @Nonnull
/*  44 */     private static final Message MESSAGE_COMMANDS_REPULSION_ADD_ALREADY_ADDED = Message.translation("server.commands.repulsion.add.alreadyAdded");
/*     */     
/*     */     @Nonnull
/*  47 */     private static final Message COMMANDS_REPULSION_ADD_SUCCESS = Message.translation("server.commands.repulsion.add.success");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*  53 */     private final RequiredArg<RepulsionConfig> repulsionConfigArg = withRequiredArg("repulsionConfig", "server.commands.repulsion.add.repulsionConfig.desc", (ArgumentType)ArgTypes.REPULSION_CONFIG);
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*  58 */     private final EntityWrappedArg entityArg = (EntityWrappedArg)
/*  59 */       withRequiredArg("entity", "server.commands.repulsion.add.entity.desc", ArgTypes.ENTITY_ID);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public RepulsionAddEntityCommand() {
/*  65 */       super("entity", "server.commands.repulsion.add.entity.desc");
/*     */     }
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/*  70 */       Ref<EntityStore> entityRef = this.entityArg.get((ComponentAccessor)store, context);
/*  71 */       if (entityRef == null || !entityRef.isValid()) {
/*  72 */         context.sendMessage(RepulsionAddCommand.MESSAGE_COMMANDS_ERRORS_TARGET_NOT_IN_WORLD);
/*     */         
/*     */         return;
/*     */       } 
/*  76 */       RepulsionConfig repulsionConfig = (RepulsionConfig)this.repulsionConfigArg.get(context);
/*     */       
/*  78 */       if (store.getArchetype(entityRef).contains(Repulsion.getComponentType())) {
/*  79 */         context.sendMessage(MESSAGE_COMMANDS_REPULSION_ADD_ALREADY_ADDED);
/*     */         
/*     */         return;
/*     */       } 
/*  83 */       store.addComponent(entityRef, Repulsion.getComponentType(), (Component)new Repulsion(repulsionConfig));
/*  84 */       context.sendMessage(COMMANDS_REPULSION_ADD_SUCCESS);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class RepulsionAddSelfCommand
/*     */     extends AbstractTargetPlayerCommand
/*     */   {
/*     */     @Nonnull
/*  93 */     private static final Message MESSAGE_COMMANDS_REPULSION_ADD_ALREADY_ADDED = Message.translation("server.commands.repulsion.add.alreadyAdded");
/*     */     @Nonnull
/*  95 */     private static final Message MESSAGE_COMMANDS_REPULSION_ADD_SUCCESS = Message.translation("server.commands.repulsion.add.success");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 101 */     private final RequiredArg<RepulsionConfig> repulsionConfigArg = withRequiredArg("repulsionConfig", "server.commands.repulsion.add.repulsionConfig.desc", (ArgumentType)ArgTypes.REPULSION_CONFIG);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public RepulsionAddSelfCommand() {
/* 107 */       super("self", "server.commands.repulsion.add.self.desc");
/*     */     }
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nullable Ref<EntityStore> sourceRef, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 112 */       if (store.getArchetype(ref).contains(Repulsion.getComponentType())) {
/* 113 */         context.sendMessage(MESSAGE_COMMANDS_REPULSION_ADD_ALREADY_ADDED);
/*     */         
/*     */         return;
/*     */       } 
/* 117 */       RepulsionConfig repulsionConfig = (RepulsionConfig)this.repulsionConfigArg.get(context);
/* 118 */       store.addComponent(ref, Repulsion.getComponentType(), (Component)new Repulsion(repulsionConfig));
/* 119 */       context.sendMessage(MESSAGE_COMMANDS_REPULSION_ADD_SUCCESS);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\debug\component\repulsion\RepulsionAddCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */