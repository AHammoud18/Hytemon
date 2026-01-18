/*    */ package com.hypixel.hytale.server.core.modules.debug.commands;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.math.matrix.Matrix4d;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.math.vector.Vector3f;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.asset.type.model.config.Model;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.modules.debug.DebugUtils;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.HeadRotation;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.concurrent.ThreadLocalRandom;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class DebugShapeArrowCommand
/*    */   extends AbstractPlayerCommand {
/*    */   @Nonnull
/* 27 */   private static final Message MESSAGE_COMMANDS_DEBUG_SHAPE_ARROW_SUCCESS = Message.translation("server.commands.debug.shape.arrow.success");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DebugShapeArrowCommand() {
/* 33 */     super("arrow", "server.commands.debug.shape.arrow.desc");
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
/* 44 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 45 */     assert playerComponent != null;
/*    */     
/* 47 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/* 48 */     assert transformComponent != null;
/*    */     
/* 50 */     Vector3d pos = transformComponent.getPosition();
/*    */     
/* 52 */     ModelComponent modelComponent = (ModelComponent)store.getComponent(ref, ModelComponent.getComponentType());
/* 53 */     assert modelComponent != null;
/*    */     
/* 55 */     Model model = modelComponent.getModel();
/*    */     
/* 57 */     HeadRotation headRotationComponent = (HeadRotation)store.getComponent(ref, HeadRotation.getComponentType());
/* 58 */     assert headRotationComponent != null;
/*    */     
/* 60 */     Vector3f headRotation = headRotationComponent.getRotation();
/* 61 */     float lookYaw = headRotation.getYaw();
/* 62 */     float lookPitch = headRotation.getPitch();
/*    */     
/* 64 */     Matrix4d tmp = new Matrix4d();
/* 65 */     float eyeHeight = (model != null) ? model.getEyeHeight(ref, (ComponentAccessor)store) : 0.0F;
/* 66 */     ThreadLocalRandom random = ThreadLocalRandom.current();
/* 67 */     Vector3f color = new Vector3f(random.nextFloat(), random.nextFloat(), random.nextFloat());
/*    */     
/* 69 */     Matrix4d matrix = new Matrix4d();
/*    */     
/* 71 */     matrix.identity();
/* 72 */     matrix.translate(pos.x, pos.y + eyeHeight, pos.z);
/* 73 */     matrix.rotateAxis(-lookYaw, 0.0D, 1.0D, 0.0D, tmp);
/* 74 */     matrix.rotateAxis(1.5707963267948966D - lookPitch, 1.0D, 0.0D, 0.0D, tmp);
/* 75 */     DebugUtils.addArrow(world, matrix, color, 1.0D, 30.0F, true);
/* 76 */     context.sendMessage(MESSAGE_COMMANDS_DEBUG_SHAPE_ARROW_SUCCESS);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\debug\commands\DebugShapeArrowCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */