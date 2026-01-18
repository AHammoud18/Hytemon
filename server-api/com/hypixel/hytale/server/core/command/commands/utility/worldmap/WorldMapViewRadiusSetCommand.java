/*    */ package com.hypixel.hytale.server.core.command.commands.utility.worldmap;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractTargetPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
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
/*    */ public class WorldMapViewRadiusSetCommand
/*    */   extends AbstractTargetPlayerCommand
/*    */ {
/*    */   @Nonnull
/* 27 */   private final RequiredArg<Integer> radiusArg = withRequiredArg("radius", "server.commands.worldmap.viewradius.set.radius.desc", (ArgumentType)ArgTypes.INTEGER);
/*    */   @Nonnull
/* 29 */   private final FlagArg bypassArg = (FlagArg)
/* 30 */     withFlagArg("bypass", "server.commands.worldmap.viewradius.set.bypass.desc")
/* 31 */     .setPermission("server.commands.worldmap.viewradius.set.bypass");
/*    */   
/*    */   public WorldMapViewRadiusSetCommand() {
/* 34 */     super("set", "server.commands.worldmap.viewradius.set.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nullable Ref<EntityStore> sourceRef, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 39 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 40 */     assert playerComponent != null;
/*    */     
/* 42 */     int viewRadius = ((Integer)this.radiusArg.get(context)).intValue();
/* 43 */     boolean bypass = ((Boolean)this.bypassArg.get(context)).booleanValue();
/*    */     
/* 45 */     if (viewRadius < 0) {
/* 46 */       context.sendMessage(Message.translation("server.commands.worldmap.viewradius.set.mustBePositive"));
/*    */       
/*    */       return;
/*    */     } 
/* 50 */     if (viewRadius > 512 && !bypass) {
/* 51 */       context.sendMessage(Message.translation("server.commands.worldmap.viewradius.set.noHigherThan")
/* 52 */           .param("radius", 512));
/*    */       
/*    */       return;
/*    */     } 
/* 56 */     playerComponent.getWorldMapTracker().setViewRadiusOverride(Integer.valueOf(viewRadius));
/*    */     
/* 58 */     context.sendMessage(Message.translation("server.commands.worldmap.viewradius.set.success")
/* 59 */         .param("radius", viewRadius));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\command\\utility\worldmap\WorldMapViewRadiusSetCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */