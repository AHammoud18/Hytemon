/*    */ package com.hypixel.hytale.builtin.teleport.commands.teleport;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.teleport.components.TeleportHistory;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.math.util.ChunkUtil;
/*    */ import com.hypixel.hytale.math.util.MathUtil;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.math.vector.Vector3f;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.HeadRotation;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.modules.entity.teleport.Teleport;
/*    */ import com.hypixel.hytale.server.core.permissions.HytalePermissions;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class TeleportTopCommand
/*    */   extends AbstractPlayerCommand
/*    */ {
/*    */   @Nonnull
/* 28 */   private static final Message MESSAGE_COMMANDS_TELEPORT_TOP_CHUNK_NOT_LOADED_AT_POS = Message.translation("server.commands.teleport.top.chunkNotLoadedAtPos");
/*    */   @Nonnull
/* 30 */   private static final Message MESSAGE_COMMANDS_TELEPORT_TELEPORTED_TO_TOP = Message.translation("server.commands.teleport.teleportedToTop");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static final String TELEPORT_HISTORY_KEY = "Underground";
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TeleportTopCommand() {
/* 41 */     super("top", "server.commands.top.desc");
/* 42 */     requirePermission(HytalePermissions.fromCommand("teleport.top"));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 47 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/* 48 */     assert transformComponent != null;
/*    */     
/* 50 */     Vector3d position = transformComponent.getPosition();
/*    */     
/* 52 */     WorldChunk worldChunk = world.getChunkIfInMemory(ChunkUtil.indexChunkFromBlock(position.getX(), position.getZ()));
/* 53 */     if (worldChunk == null) {
/* 54 */       context.sendMessage(MESSAGE_COMMANDS_TELEPORT_TOP_CHUNK_NOT_LOADED_AT_POS);
/*    */       
/*    */       return;
/*    */     } 
/* 58 */     HeadRotation headRotationComponent = (HeadRotation)store.getComponent(ref, HeadRotation.getComponentType());
/* 59 */     assert headRotationComponent != null;
/*    */     
/* 61 */     Vector3f headRotation = headRotationComponent.getRotation().clone();
/*    */     
/* 63 */     int height = worldChunk.getHeight(MathUtil.floor(position.getX()), MathUtil.floor(position.getZ()));
/* 64 */     ((TeleportHistory)store.ensureAndGetComponent(ref, TeleportHistory.getComponentType())).append(world, position.clone(), headRotation.clone(), "Underground");
/*    */     
/* 66 */     store.addComponent(ref, Teleport.getComponentType(), (Component)new Teleport(new Vector3d(position.getX(), (height + 2), position.getZ()), Vector3f.NaN));
/* 67 */     context.sendMessage(MESSAGE_COMMANDS_TELEPORT_TELEPORTED_TO_TOP);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\teleport\commands\teleport\TeleportTopCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */