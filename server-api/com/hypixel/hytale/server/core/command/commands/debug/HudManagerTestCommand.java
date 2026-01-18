/*    */ package com.hypixel.hytale.server.core.command.commands.debug;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.protocol.packets.interface_.HudComponent;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractTargetPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.entity.entities.player.hud.HudManager;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HudManagerTestCommand
/*    */   extends AbstractTargetPlayerCommand
/*    */ {
/*    */   @Nonnull
/* 25 */   private static final Message MESSAGE_COMMANDS_HUD_TEST_SHOWN_SELF = Message.translation("server.commands.hudtest.shown.self");
/*    */   @Nonnull
/* 27 */   private static final Message MESSAGE_COMMANDS_HUT_TEST_HIDDEN_SELF = Message.translation("server.commands.hudtest.hidden.self");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 33 */   private final FlagArg resetHudFlag = withFlagArg("reset", "server.commands.hudtest.reset.desc");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public HudManagerTestCommand() {
/* 39 */     super("hudtest", "server.commands.hudtest.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nullable Ref<EntityStore> sourceRef, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 44 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 45 */     assert playerComponent != null;
/*    */     
/* 47 */     PlayerRef playerRefComponent = (PlayerRef)store.getComponent(ref, PlayerRef.getComponentType());
/* 48 */     assert playerRefComponent != null;
/*    */     
/* 50 */     HudManager hudManager = playerComponent.getHudManager();
/* 51 */     boolean isTargetingOther = !ref.equals(sourceRef);
/*    */     
/* 53 */     if (this.resetHudFlag.provided(context)) {
/* 54 */       hudManager.showHudComponents(playerRef, new HudComponent[] { HudComponent.Hotbar });
/* 55 */       if (isTargetingOther) {
/* 56 */         context.sendMessage(Message.translation("server.commands.hudtest.shown.other")
/* 57 */             .param("username", playerRefComponent.getUsername()));
/*    */       } else {
/* 59 */         context.sendMessage(MESSAGE_COMMANDS_HUD_TEST_SHOWN_SELF);
/*    */       } 
/*    */     } else {
/* 62 */       hudManager.hideHudComponents(playerRef, new HudComponent[] { HudComponent.Hotbar });
/* 63 */       if (isTargetingOther) {
/* 64 */         context.sendMessage(Message.translation("server.commands.hudtest.hidden.other")
/* 65 */             .param("username", playerRefComponent.getUsername()));
/*    */       } else {
/* 67 */         context.sendMessage(MESSAGE_COMMANDS_HUT_TEST_HIDDEN_SELF);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\debug\HudManagerTestCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */