/*    */ package com.hypixel.hytale.server.core.command.commands.world;
/*    */ 
/*    */ import com.hypixel.hytale.component.AddReason;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Holder;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.math.vector.Vector3f;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.Argument;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.DefaultArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.RelativeDoublePosition;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*    */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*    */ import com.hypixel.hytale.server.core.entity.entities.BlockEntity;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.modules.time.TimeResource;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class SpawnBlockCommand
/*    */   extends AbstractWorldCommand
/*    */ {
/*    */   @Nonnull
/* 31 */   private final RequiredArg<String> blockArg = withRequiredArg("block", "server.commands.spawnblock.arg.block.desc", (ArgumentType)ArgTypes.BLOCK_TYPE_KEY);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 37 */   private final RequiredArg<RelativeDoublePosition> positionArg = withRequiredArg("position", "server.commands.spawnblock.arg.position.desc", ArgTypes.RELATIVE_POSITION);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 43 */   private final DefaultArg<Vector3f> rotationArg = withDefaultArg("rotation", "server.commands.spawnblock.arg.rotation.desc", ArgTypes.ROTATION, Vector3f.FORWARD, "server.commands.spawnblock.arg.rotation.desc");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SpawnBlockCommand() {
/* 50 */     super("spawnblock", "server.commands.spawnblock.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 55 */     String blockTypeKey = (String)context.get((Argument)this.blockArg);
/* 56 */     Vector3d position = ((RelativeDoublePosition)context.get((Argument)this.positionArg)).getRelativePosition(context, world, (ComponentAccessor)store);
/* 57 */     Vector3f rotation = (Vector3f)this.rotationArg.get(context);
/*    */     
/* 59 */     TimeResource timeResource = (TimeResource)world.getEntityStore().getStore().getResource(TimeResource.getResourceType());
/* 60 */     Holder<EntityStore> blockEntityHolder = BlockEntity.assembleDefaultBlockEntity(timeResource, blockTypeKey, position);
/*    */     
/* 62 */     TransformComponent transformComponent = (TransformComponent)blockEntityHolder.ensureAndGetComponent(TransformComponent.getComponentType());
/* 63 */     transformComponent.setPosition(position);
/* 64 */     transformComponent.setRotation(rotation);
/*    */     
/* 66 */     UUIDComponent uuidComponent = (UUIDComponent)blockEntityHolder.getComponent(UUIDComponent.getComponentType());
/* 67 */     String entityIdString = (uuidComponent == null) ? "None" : uuidComponent.getUuid().toString();
/*    */     
/* 69 */     world.getEntityStore().getStore().addEntity(blockEntityHolder, AddReason.SPAWN);
/*    */     
/* 71 */     context.sendMessage(Message.translation("server.commands.spawnblock.success").param("id", entityIdString));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\world\SpawnBlockCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */