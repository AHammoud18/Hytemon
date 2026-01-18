/*    */ package com.hypixel.hytale.server.core.command.commands.player.viewradius;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.protocol.Packet;
/*    */ import com.hypixel.hytale.protocol.packets.setup.ViewRadius;
/*    */ import com.hypixel.hytale.server.core.HytaleServer;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractTargetPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.modules.entity.tracker.EntityTrackerSystems;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PlayerViewRadiusSetCommand
/*    */   extends AbstractTargetPlayerCommand
/*    */ {
/*    */   @Nonnull
/* 34 */   private final RequiredArg<String> radiusArg = withRequiredArg("radius", "server.commands.player.viewradius.set.radius.desc", (ArgumentType)ArgTypes.STRING);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 40 */   private final FlagArg blocksArg = withFlagArg("blocks", "server.commands.player.viewradius.set.blocks.desc");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 46 */   private final FlagArg bypassArg = withFlagArg("bypass", "server.commands.player.viewradius.set.bypass.desc");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PlayerViewRadiusSetCommand() {
/* 52 */     super("set", "server.commands.player.viewradius.set.desc");
/*    */   }
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nullable Ref<EntityStore> sourceRef, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/*    */     int viewRadiusChunks;
/* 57 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 58 */     assert playerComponent != null;
/*    */     
/* 60 */     EntityTrackerSystems.EntityViewer entityViewerComponent = (EntityTrackerSystems.EntityViewer)store.getComponent(ref, EntityTrackerSystems.EntityViewer.getComponentType());
/* 61 */     assert entityViewerComponent != null;
/*    */     
/* 63 */     String radiusInput = (String)this.radiusArg.get(context);
/* 64 */     boolean measureInBlocks = ((Boolean)this.blocksArg.get(context)).booleanValue();
/* 65 */     boolean bypass = ((Boolean)this.bypassArg.get(context)).booleanValue();
/*    */ 
/*    */     
/* 68 */     if ("default".equalsIgnoreCase(radiusInput)) {
/* 69 */       viewRadiusChunks = 32;
/*    */     } else {
/*    */       try {
/* 72 */         int value = Integer.parseInt(radiusInput);
/* 73 */         viewRadiusChunks = measureInBlocks ? (int)Math.ceil((value / 32.0F)) : value;
/* 74 */       } catch (NumberFormatException e) {
/* 75 */         context.sendMessage(Message.translation("server.commands.player.viewradius.set.invalidNumber")
/* 76 */             .param("value", radiusInput));
/*    */         
/*    */         return;
/*    */       } 
/*    */     } 
/* 81 */     int maxViewRadius = HytaleServer.get().getConfig().getMaxViewRadius();
/* 82 */     if (viewRadiusChunks > maxViewRadius && !bypass) {
/* 83 */       context.sendMessage(Message.translation("server.commands.player.viewradius.set.noHigherThan")
/* 84 */           .param("radius", maxViewRadius));
/*    */       
/*    */       return;
/*    */     } 
/* 88 */     int viewRadiusBlocks = viewRadiusChunks * 32;
/*    */ 
/*    */     
/* 91 */     playerComponent.setClientViewRadius(viewRadiusChunks);
/* 92 */     entityViewerComponent.viewRadiusBlocks = viewRadiusBlocks;
/*    */ 
/*    */     
/* 95 */     playerRef.getPacketHandler().writeNoCache((Packet)new ViewRadius(viewRadiusBlocks));
/*    */     
/* 97 */     context.sendMessage(Message.translation("server.commands.player.viewradius.set.success")
/* 98 */         .param("radius", viewRadiusChunks)
/* 99 */         .param("radiusBlocks", viewRadiusBlocks));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\player\viewradius\PlayerViewRadiusSetCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */