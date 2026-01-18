/*    */ package com.hypixel.hytale.server.core.command.commands.player;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.NameMatching;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandManager;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandSender;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandUtil;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.Universe;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class SudoCommand extends CommandBase {
/*    */   @Nonnull
/* 25 */   private static final Message MESSAGE_COMMANDS_SU_INVALID_USAGE = Message.translation("server.commands.sudo.invalidusage");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 31 */   private final RequiredArg<String> playerArg = withRequiredArg("player", "server.commands.sudo.player.desc", (ArgumentType)ArgTypes.STRING);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SudoCommand() {
/* 37 */     super("sudo", "server.commands.sudo.desc");
/* 38 */     addAliases(new String[] { "su" });
/* 39 */     setAllowsExtraArguments(true);
/*    */   }
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/*    */     ObjectArrayList<PlayerRef> objectArrayList;
/* 44 */     String playerName = (String)this.playerArg.get(context);
/* 45 */     String inputString = context.getInputString();
/*    */ 
/*    */     
/* 48 */     String rawArgs = CommandUtil.stripCommandName(inputString);
/*    */ 
/*    */     
/* 51 */     int commandIndex = rawArgs.indexOf(' ');
/* 52 */     if (commandIndex == -1) {
/* 53 */       context.sendMessage(MESSAGE_COMMANDS_SU_INVALID_USAGE);
/*    */       
/*    */       return;
/*    */     } 
/* 57 */     String commandToExecute = rawArgs.substring(commandIndex + 1).trim();
/* 58 */     if (commandToExecute.isEmpty()) {
/* 59 */       context.sendMessage(MESSAGE_COMMANDS_SU_INVALID_USAGE);
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 64 */     if (commandToExecute.charAt(0) == '/') {
/* 65 */       commandToExecute = commandToExecute.substring(1);
/*    */     }
/*    */ 
/*    */ 
/*    */     
/* 70 */     if (playerName.equals("*")) {
/* 71 */       List<PlayerRef> players = Universe.get().getPlayers();
/*    */     } else {
/* 73 */       PlayerRef player = Universe.get().getPlayer(playerName, NameMatching.DEFAULT);
/* 74 */       if (player == null) {
/* 75 */         context.sendMessage(Message.translation("server.commands.errors.noSuchPlayer")
/* 76 */             .param("username", playerName));
/*    */         return;
/*    */       } 
/* 79 */       objectArrayList = new ObjectArrayList();
/* 80 */       objectArrayList.add(player);
/*    */     } 
/*    */     
/* 83 */     if (objectArrayList.isEmpty()) {
/* 84 */       context.sendMessage(Message.translation("server.commands.errors.noSuchPlayer")
/* 85 */           .param("username", playerName));
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 90 */     String finalCommand = commandToExecute;
/* 91 */     for (PlayerRef player : objectArrayList) {
/* 92 */       Ref<EntityStore> ref = player.getReference();
/* 93 */       if (ref == null || !ref.isValid())
/*    */         continue; 
/* 95 */       Store<EntityStore> store = ref.getStore();
/* 96 */       World world = ((EntityStore)store.getExternalData()).getWorld();
/*    */       
/* 98 */       world.execute(() -> {
/*    */             Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*    */             assert playerComponent != null;
/*    */             CommandManager.get().handleCommand((CommandSender)playerComponent, finalCommand);
/*    */           });
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\player\SudoCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */