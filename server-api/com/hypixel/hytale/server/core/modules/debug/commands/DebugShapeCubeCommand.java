/*    */ package com.hypixel.hytale.server.core.modules.debug.commands;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.math.vector.Vector3f;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.modules.debug.DebugUtils;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.concurrent.ThreadLocalRandom;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class DebugShapeCubeCommand
/*    */   extends AbstractPlayerCommand
/*    */ {
/*    */   @Nonnull
/* 23 */   private static final Message MESSAGE_COMMANDS_DEBUG_SHAPE_CUBE_SUCCESS = Message.translation("server.commands.debug.shape.cube.success");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DebugShapeCubeCommand() {
/* 29 */     super("cube", "server.commands.debug.shape.cube.desc");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 40 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/* 41 */     assert transformComponent != null;
/*    */     
/* 43 */     Vector3d position = transformComponent.getPosition();
/* 44 */     ThreadLocalRandom random = ThreadLocalRandom.current();
/* 45 */     Vector3f color = new Vector3f(random.nextFloat(), random.nextFloat(), random.nextFloat());
/*    */     
/* 47 */     DebugUtils.addCube(world, position, color, 2.0D, 30.0F);
/* 48 */     context.sendMessage(MESSAGE_COMMANDS_DEBUG_SHAPE_CUBE_SUCCESS);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\debug\commands\DebugShapeCubeCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */