/*    */ package com.hypixel.hytale.builtin.path.commands;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.DefaultArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.UUID;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PrefabPathNewCommand
/*    */   extends AbstractPlayerCommand
/*    */ {
/*    */   @Nonnull
/* 30 */   private final RequiredArg<String> pathNameArg = withRequiredArg("pathName", "server.commands.npcpath.new.pathName.desc", (ArgumentType)ArgTypes.STRING);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 36 */   private final DefaultArg<Double> pauseTimeArg = withDefaultArg("pauseTime", "server.commands.npcpath.new.pauseTime.desc", (ArgumentType)ArgTypes.DOUBLE, Double.valueOf(0.0D), "0.0");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 42 */   private final DefaultArg<Float> observationAngleArg = withDefaultArg("observationAngleDegrees", "server.commands.npcpath.new.observationAngleDegrees.desc", (ArgumentType)ArgTypes.FLOAT, Float.valueOf(0.0F), "0.0");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PrefabPathNewCommand() {
/* 48 */     super("new", "server.commands.npcpath.new.desc");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 57 */     String pathName = (String)this.pathNameArg.get(context);
/* 58 */     Double pauseTime = (Double)this.pauseTimeArg.get(context);
/* 59 */     Float obsvAngle = (Float)this.observationAngleArg.get(context);
/*    */     
/* 61 */     UUID uuid = UUID.randomUUID();
/*    */     
/* 63 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 64 */     assert playerComponent != null;
/*    */     
/* 66 */     PrefabPathHelper.addMarker(store, ref, uuid, pathName, pauseTime.doubleValue(), obsvAngle.floatValue(), (short)-1, 0);
/* 67 */     BuilderToolsPlugin.getState(playerComponent, playerRef).setActivePrefabPath(uuid);
/* 68 */     context.sendMessage(Message.translation("server.npc.npcpath.editingPath").param("path", pathName));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\path\commands\PrefabPathNewCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */