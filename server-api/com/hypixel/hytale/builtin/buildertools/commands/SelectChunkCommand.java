/*    */ package com.hypixel.hytale.builtin.buildertools.commands;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin;
/*    */ import com.hypixel.hytale.builtin.buildertools.PrototypePlayerBuilderToolSettings;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.math.util.MathUtil;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.protocol.GameMode;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SelectChunkCommand
/*    */   extends AbstractPlayerCommand
/*    */ {
/*    */   public SelectChunkCommand() {
/* 30 */     super("selectchunk", "server.commands.selectchunk.desc");
/* 31 */     setPermissionGroup(GameMode.Creative);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 40 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 41 */     assert playerComponent != null;
/*    */     
/* 43 */     if (!PrototypePlayerBuilderToolSettings.isOkayToDoCommandsOnSelection(ref, playerComponent, (ComponentAccessor)store))
/*    */       return; 
/* 45 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/* 46 */     assert transformComponent != null;
/*    */     
/* 48 */     Vector3d position = transformComponent.getPosition();
/* 49 */     int chunkX = MathUtil.floor(position.getX()) >> 5;
/* 50 */     int chunkZ = MathUtil.floor(position.getZ()) >> 5;
/*    */     
/* 52 */     Vector3i min = new Vector3i(chunkX << 5, 0, chunkZ << 5);
/*    */ 
/*    */ 
/*    */     
/* 56 */     Vector3i max = new Vector3i((chunkX + 1 << 5) - 1, 319, (chunkZ + 1 << 5) - 1);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 62 */     BuilderToolsPlugin.addToQueue(playerComponent, playerRef, (r, s, componentAccessor) -> s.select(min, max, "server.builderTools.selectReasons.chunk", componentAccessor));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\commands\SelectChunkCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */