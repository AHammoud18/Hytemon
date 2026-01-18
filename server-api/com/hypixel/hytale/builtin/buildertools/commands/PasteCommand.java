/*    */ package com.hypixel.hytale.builtin.buildertools.commands;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.math.util.MathUtil;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.protocol.GameMode;
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.RelativeIntPosition;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PasteCommand
/*    */   extends AbstractPlayerCommand
/*    */ {
/*    */   public PasteCommand() {
/* 32 */     super("paste", "server.commands.paste.desc");
/* 33 */     setPermissionGroup(GameMode.Creative);
/* 34 */     requirePermission("hytale.editor.selection.clipboard");
/* 35 */     addUsageVariant((AbstractCommand)new PasteAtPositionCommand());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 44 */     ChunkStore chunkStore = world.getChunkStore();
/*    */     
/* 46 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 47 */     assert playerComponent != null;
/*    */     
/* 49 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/* 50 */     assert transformComponent != null;
/*    */     
/* 52 */     Vector3d position = transformComponent.getPosition();
/* 53 */     int x = MathUtil.floor(position.x);
/* 54 */     int y = MathUtil.floor(position.y);
/* 55 */     int z = MathUtil.floor(position.z);
/*    */     
/* 57 */     BuilderToolsPlugin.addToQueue(playerComponent, playerRef, (r, s, componentAccessor) -> s.paste(r, x, y, z, componentAccessor));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static class PasteAtPositionCommand
/*    */     extends AbstractPlayerCommand
/*    */   {
/*    */     @Nonnull
/* 70 */     private final RequiredArg<RelativeIntPosition> positionArg = withRequiredArg("position", "server.commands.paste.position.desc", ArgTypes.RELATIVE_BLOCK_POSITION);
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public PasteAtPositionCommand() {
/* 76 */       super("server.commands.paste.desc");
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 85 */       ChunkStore chunkStore = world.getChunkStore();
/*    */       
/* 87 */       Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 88 */       assert playerComponent != null;
/*    */       
/* 90 */       TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/* 91 */       assert transformComponent != null;
/*    */       
/* 93 */       Vector3d position = transformComponent.getPosition();
/* 94 */       RelativeIntPosition relativePos = (RelativeIntPosition)this.positionArg.get(context);
/* 95 */       Vector3i blockPos = relativePos.getBlockPosition(position, chunkStore);
/*    */       
/* 97 */       BuilderToolsPlugin.addToQueue(playerComponent, playerRef, (r, s, componentAccessor) -> s.paste(r, blockPos.x, blockPos.y, blockPos.z, componentAccessor));
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\commands\PasteCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */