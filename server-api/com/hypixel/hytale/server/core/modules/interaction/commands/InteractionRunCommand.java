/*    */ package com.hypixel.hytale.server.core.modules.interaction.commands;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.protocol.InteractionType;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
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
/*    */ public class InteractionRunCommand
/*    */   extends AbstractPlayerCommand
/*    */ {
/*    */   @Nonnull
/* 28 */   private static final EnumArgumentType<InteractionType> INTERACTION_TYPE_ARG_TYPE = new EnumArgumentType("server.commands.parsing.argtype.interactiontype.name", InteractionType.class);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 37 */   private final RequiredArg<InteractionType> interactionTypeArg = withRequiredArg("interactionType", "server.commands.interaction.run.interactionType.desc", (ArgumentType)INTERACTION_TYPE_ARG_TYPE);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public InteractionRunCommand() {
/* 43 */     super("run", "server.commands.interaction.run.desc");
/* 44 */     addSubCommand((AbstractCommand)new InteractionRunSpecificCommand());
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 49 */     InteractionType interactionType = (InteractionType)this.interactionTypeArg.get(context);
/* 50 */     InteractionManager interactionManagerComponent = (InteractionManager)store.getComponent(ref, InteractionModule.get().getInteractionManagerComponent());
/* 51 */     assert interactionManagerComponent != null;
/*    */     
/* 53 */     InteractionContext interactionContext = InteractionContext.forInteraction(interactionManagerComponent, ref, interactionType, (ComponentAccessor)store);
/* 54 */     String root = interactionContext.getRootInteractionId(interactionType);
/* 55 */     if (root == null) {
/* 56 */       context.sendMessage(Message.translation("server.commands.interaction.run.rootNotFound")
/* 57 */           .param("type", interactionType.name()));
/*    */       
/*    */       return;
/*    */     } 
/* 61 */     RootInteraction interactionAsset = (RootInteraction)RootInteraction.getAssetMap().getAsset(root);
/* 62 */     if (interactionAsset == null) {
/* 63 */       context.sendMessage(Message.translation("server.commands.interaction.run.interactionAssetNotFound")
/* 64 */           .param("root", root)
/* 65 */           .param("type", interactionType.name()));
/*    */       
/*    */       return;
/*    */     } 
/* 69 */     InteractionChain chain = interactionManagerComponent.initChain(interactionType, interactionContext, interactionAsset, false);
/* 70 */     interactionManagerComponent.queueExecuteChain(chain);
/* 71 */     context.sendMessage(Message.translation("server.commands.interaction.run.started")
/* 72 */         .param("type", interactionType.name()));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\commands\InteractionRunCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */