/*    */ package com.hypixel.hytale.server.core.modules.interaction.commands;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.protocol.InteractionType;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.EnumArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.entity.InteractionChain;
/*    */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*    */ import com.hypixel.hytale.server.core.entity.InteractionManager;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.InteractionModule;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.RootInteraction;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InteractionRunSpecificCommand
/*    */   extends AbstractPlayerCommand
/*    */ {
/*    */   @Nonnull
/* 30 */   private static final EnumArgumentType<InteractionType> INTERACTION_TYPE_ARG_TYPE = new EnumArgumentType("server.commands.parsing.argtype.interactiontype.name", InteractionType.class);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 39 */   private final RequiredArg<InteractionType> interactionTypeArg = withRequiredArg("interactionType", "server.commands.interaction.run.interactionType.desc", (ArgumentType)INTERACTION_TYPE_ARG_TYPE);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 45 */   private final RequiredArg<RootInteraction> rootInteractionArg = withRequiredArg("interaction", "server.commands.interaction.runSpecific.rootinteraction.desc", (ArgumentType)ArgTypes.ROOT_INTERACTION_ASSET);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public InteractionRunSpecificCommand() {
/* 51 */     super("specific", "server.commands.interaction.runSpecific.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 56 */     InteractionManager interactionManager = (InteractionManager)store.getComponent(ref, InteractionModule.get().getInteractionManagerComponent());
/* 57 */     assert interactionManager != null;
/*    */     
/* 59 */     InteractionType interactionType = (InteractionType)this.interactionTypeArg.get(context);
/* 60 */     RootInteraction rootInteraction = (RootInteraction)this.rootInteractionArg.get(context);
/*    */     
/* 62 */     InteractionContext interactionContext = InteractionContext.forInteraction(interactionManager, ref, interactionType, (ComponentAccessor)store);
/* 63 */     InteractionChain chain = interactionManager.initChain(interactionType, interactionContext, rootInteraction, false);
/* 64 */     interactionManager.queueExecuteChain(chain);
/* 65 */     context.sendMessage(Message.translation("server.commands.interaction.runSpecific.started")
/* 66 */         .param("type", interactionType.name())
/* 67 */         .param("root", rootInteraction.getId()));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\commands\InteractionRunSpecificCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */