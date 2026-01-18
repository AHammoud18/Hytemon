/*    */ package com.hypixel.hytale.builtin.path.commands;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin;
/*    */ import com.hypixel.hytale.builtin.path.WorldPathData;
/*    */ import com.hypixel.hytale.builtin.path.entities.PatrolPathMarkerEntity;
/*    */ import com.hypixel.hytale.builtin.path.path.IPrefabPath;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.entity.Entity;
/*    */ import com.hypixel.hytale.server.core.entity.EntityUtils;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.core.util.TargetUtil;
/*    */ import java.util.UUID;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class PrefabPathEditCommand
/*    */   extends AbstractPlayerCommand
/*    */ {
/*    */   @Nonnull
/* 30 */   private static final Message MESSAGE_COMMANDS_NPC_PATH_EDIT_NO_ENTITY_IN_VIEW = Message.translation("server.commands.npcpath.edit.noEntityInView");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 36 */   private final OptionalArg<UUID> pathIdArg = withOptionalArg("pathId", "server.commands.npcpath.edit.pathId.desc", (ArgumentType)ArgTypes.UUID);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PrefabPathEditCommand() {
/* 42 */     super("edit", "server.commands.npcpath.edit.desc");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/*    */     UUID pathId;
/* 51 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 52 */     assert playerComponent != null;
/*    */ 
/*    */     
/* 55 */     if (this.pathIdArg.provided(context)) {
/* 56 */       pathId = (UUID)this.pathIdArg.get(context);
/*    */     } else {
/* 58 */       PatrolPathMarkerEntity pathMarkerEntity; Ref<EntityStore> entityRef = TargetUtil.getTargetEntity(ref, (ComponentAccessor)store);
/* 59 */       if (entityRef == null || !entityRef.isValid())
/*    */         return; 
/* 61 */       Entity entity = EntityUtils.getEntity(ref, (ComponentAccessor)store);
/* 62 */       if (entity instanceof PatrolPathMarkerEntity) { pathMarkerEntity = (PatrolPathMarkerEntity)entity; }
/* 63 */       else { context.sendMessage(MESSAGE_COMMANDS_NPC_PATH_EDIT_NO_ENTITY_IN_VIEW);
/*    */         return; }
/*    */       
/* 66 */       pathId = pathMarkerEntity.getPathId();
/*    */     } 
/*    */     
/* 69 */     WorldPathData worldPathData = (WorldPathData)store.getResource(WorldPathData.getResourceType());
/* 70 */     IPrefabPath path = worldPathData.getPrefabPath(0, pathId, false);
/* 71 */     if (path == null) {
/* 72 */       context.sendMessage(Message.translation("server.npc.npcpath.pathMustBeLoaded")
/* 73 */           .param("path", pathId.toString()));
/*    */       
/*    */       return;
/*    */     } 
/* 77 */     BuilderToolsPlugin.getState(playerComponent, playerRef).setActivePrefabPath(pathId);
/* 78 */     context.sendMessage(Message.translation("server.npc.npcpath.editingPath").param("path", pathId.toString()));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\path\commands\PrefabPathEditCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */