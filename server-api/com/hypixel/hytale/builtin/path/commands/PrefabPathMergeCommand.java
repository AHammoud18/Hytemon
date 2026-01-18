/*    */ package com.hypixel.hytale.builtin.path.commands;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin;
/*    */ import com.hypixel.hytale.builtin.path.WorldPathData;
/*    */ import com.hypixel.hytale.builtin.path.path.IPrefabPath;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
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
/*    */ 
/*    */ public class PrefabPathMergeCommand
/*    */   extends AbstractPlayerCommand
/*    */ {
/* 28 */   public static final Message MESSAGE_COMMANDS_NPC_PATH_MERGE_NO_ACTIVE_PATH = Message.translation("server.commands.npcpath.merge.noActivePath");
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 33 */   private final RequiredArg<UUID> targetPathIdArg = withRequiredArg("pathName", "server.commands.npcpath.merge.pathName.desc", (ArgumentType)ArgTypes.UUID);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PrefabPathMergeCommand() {
/* 39 */     super("merge", "server.commands.npcpath.merge.desc");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 48 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 49 */     assert playerComponent != null;
/*    */     
/* 51 */     UUID activePathId = BuilderToolsPlugin.getState(playerComponent, playerRef).getActivePrefabPath();
/* 52 */     if (activePathId == null) {
/* 53 */       throw new GeneralCommandException(MESSAGE_COMMANDS_NPC_PATH_MERGE_NO_ACTIVE_PATH);
/*    */     }
/*    */     
/* 56 */     UUID targetPathId = (UUID)this.targetPathIdArg.get(context);
/* 57 */     WorldPathData worldPathData = (WorldPathData)store.getResource(WorldPathData.getResourceType());
/*    */     
/* 59 */     IPrefabPath activePath = worldPathData.getPrefabPath(0, activePathId, false);
/* 60 */     if (activePath == null || !activePath.isFullyLoaded()) {
/* 61 */       playerRef.sendMessage(Message.translation("server.npc.npcpath.pathMustBeLoaded")
/* 62 */           .param("path", activePathId.toString()));
/*    */       
/*    */       return;
/*    */     } 
/* 66 */     IPrefabPath targetPath = worldPathData.getPrefabPath(0, targetPathId, false);
/* 67 */     if (targetPath == null || !targetPath.isFullyLoaded()) {
/* 68 */       playerRef.sendMessage(Message.translation("server.npc.npcpath.pathMustBeLoaded")
/* 69 */           .param("path", targetPathId.toString()));
/*    */       
/*    */       return;
/*    */     } 
/* 73 */     targetPath.mergeInto(activePath, targetPath.getWorldGenId(), (ComponentAccessor)store);
/* 74 */     worldPathData.removePrefabPath(0, targetPathId);
/* 75 */     playerRef.sendMessage(Message.translation("server.npc.npcpath.pathMergedInto")
/* 76 */         .param("targetPathName", targetPathId.toString())
/* 77 */         .param("activePathName", activePathId.toString()));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\path\commands\PrefabPathMergeCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */