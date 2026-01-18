/*    */ package com.hypixel.hytale.builtin.path.commands;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin;
/*    */ import com.hypixel.hytale.builtin.path.WorldPathData;
/*    */ import com.hypixel.hytale.builtin.path.path.IPrefabPath;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.DefaultArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.exceptions.GeneralCommandException;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.UUID;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class PrefabPathAddCommand
/*    */   extends AbstractPlayerCommand
/*    */ {
/*    */   @Nonnull
/* 27 */   private static final Message MESSAGE_COMMANDS_NPC_PATH_ADD_NO_ACTIVE_PATH = Message.translation("server.commands.npcpath.add.noActivePath");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 33 */   private final DefaultArg<Double> pauseTimeArg = withDefaultArg("pauseTime", "server.commands.npcpath.add.pauseTime.desc", (ArgumentType)ArgTypes.DOUBLE, Double.valueOf(0.0D), "0.0");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 39 */   private final DefaultArg<Float> observationAngleArg = withDefaultArg("observationAngleDegrees", "server.commands.npcpath.add.observationAngleDegrees.desc", (ArgumentType)ArgTypes.FLOAT, Float.valueOf(0.0F), "0.0");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 45 */   private final DefaultArg<Integer> indexArg = withDefaultArg("index", "server.commands.npcpath.add.index.desc", (ArgumentType)ArgTypes.INTEGER, Integer.valueOf(-1), "-1");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PrefabPathAddCommand() {
/* 51 */     super("add", "server.commands.npcpath.add.desc");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 60 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 61 */     assert playerComponent != null;
/*    */     
/* 63 */     UUID path = BuilderToolsPlugin.getState(playerComponent, playerRef).getActivePrefabPath();
/* 64 */     if (path == null) {
/* 65 */       throw new GeneralCommandException(MESSAGE_COMMANDS_NPC_PATH_ADD_NO_ACTIVE_PATH);
/*    */     }
/*    */     
/* 68 */     Double pauseTime = (Double)this.pauseTimeArg.get(context);
/* 69 */     Float obsvAngle = (Float)this.observationAngleArg.get(context);
/* 70 */     short targetIndex = ((Integer)this.indexArg.get(context)).shortValue();
/*    */     
/* 72 */     WorldPathData worldPathData = (WorldPathData)store.getResource(WorldPathData.getResourceType());
/* 73 */     IPrefabPath parentPath = worldPathData.getPrefabPath(0, path, false);
/* 74 */     if (parentPath == null || !parentPath.isFullyLoaded()) {
/* 75 */       context.sendMessage(Message.translation("server.npc.npcpath.pathMustBeLoaded")
/* 76 */           .param("path", path.toString()));
/*    */       
/*    */       return;
/*    */     } 
/* 80 */     PrefabPathHelper.addMarker(store, ref, path, parentPath.getName(), pauseTime.doubleValue(), obsvAngle.floatValue(), targetIndex, parentPath.getWorldGenId());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\path\commands\PrefabPathAddCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */