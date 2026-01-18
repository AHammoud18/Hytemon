/*    */ package com.hypixel.hytale.builtin.model.commands;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.model.pages.ChangeModelPage;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.entity.entities.player.pages.CustomUIPage;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class ModelOtherCommand
/*    */   extends CommandBase
/*    */ {
/*    */   @Nonnull
/* 61 */   private final RequiredArg<PlayerRef> playerArg = withRequiredArg("player", "server.commands.argtype.player.desc", (ArgumentType)ArgTypes.PLAYER_REF);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   ModelOtherCommand() {
/* 67 */     super("server.commands.model.other.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 72 */     PlayerRef targetPlayerRef = (PlayerRef)this.playerArg.get(context);
/* 73 */     Ref<EntityStore> ref = targetPlayerRef.getReference();
/*    */     
/* 75 */     if (ref == null || !ref.isValid()) {
/* 76 */       context.sendMessage(Message.translation("server.commands.errors.playerNotInWorld"));
/*    */       
/*    */       return;
/*    */     } 
/* 80 */     Store<EntityStore> store = ref.getStore();
/* 81 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*    */     
/* 83 */     world.execute(() -> {
/*    */           Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*    */           if (playerComponent == null) {
/*    */             context.sendMessage(Message.translation("server.commands.errors.playerNotInWorld"));
/*    */             return;
/*    */           } 
/*    */           playerComponent.getPageManager().openCustomPage(ref, store, (CustomUIPage)new ChangeModelPage(targetPlayerRef));
/*    */         });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\model\commands\ModelCommand$ModelOtherCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */