/*    */ package com.hypixel.hytale.builtin.parkour.commands;
/*    */ import com.hypixel.hytale.builtin.parkour.ParkourCheckpoint;
/*    */ import com.hypixel.hytale.builtin.parkour.ParkourPlugin;
/*    */ import com.hypixel.hytale.component.AddReason;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.Holder;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.math.vector.Vector3f;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.asset.type.model.config.Model;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.entity.nameplate.Nameplate;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.HiddenFromAdventurePlayers;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.PersistentModel;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*    */ import java.util.UUID;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class CheckpointAddCommand extends AbstractPlayerCommand {
/*    */   @Nonnull
/* 28 */   private static final Message MESSAGE_COMMANDS_CHECKPOINT_ADD_FAILED = Message.translation("server.commands.checkpoint.add.failed");
/*    */   @Nonnull
/* 30 */   private static final Message MESSAGE_COMMANDS_CHECKPOINT_ADD_SUCCESS = Message.translation("server.commands.checkpoint.add.success");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 36 */   private final RequiredArg<Integer> indexArg = withRequiredArg("index", "server.commands.checkpoint.add.index.desc", (ArgumentType)ArgTypes.INTEGER);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CheckpointAddCommand() {
/* 42 */     super("add", "server.commands.checkpoint.add.desc");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 48 */     Integer index = (Integer)this.indexArg.get(context);
/* 49 */     Int2ObjectMap<UUID> checkpointUUIDMap = ParkourPlugin.get().getCheckpointUUIDMap();
/*    */     
/* 51 */     if (checkpointUUIDMap.containsKey(index)) {
/* 52 */       context.sendMessage(MESSAGE_COMMANDS_CHECKPOINT_ADD_FAILED);
/*    */       
/*    */       return;
/*    */     } 
/* 56 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/* 57 */     assert transformComponent != null;
/*    */     
/* 59 */     Vector3d position = transformComponent.getPosition();
/* 60 */     Vector3f rotation = transformComponent.getRotation();
/*    */     
/* 62 */     Holder<EntityStore> holder = EntityStore.REGISTRY.newHolder();
/* 63 */     holder.addComponent(ParkourCheckpoint.getComponentType(), (Component)new ParkourCheckpoint(index.intValue()));
/*    */     
/* 65 */     Model model = ParkourPlugin.get().getParkourCheckpointModel();
/* 66 */     holder.addComponent(ModelComponent.getComponentType(), (Component)new ModelComponent(model));
/* 67 */     holder.addComponent(PersistentModel.getComponentType(), (Component)new PersistentModel(model.toReference()));
/* 68 */     holder.addComponent(Nameplate.getComponentType(), (Component)new Nameplate(Integer.toString(index.intValue())));
/*    */     
/* 70 */     TransformComponent transform = new TransformComponent(position, rotation);
/* 71 */     holder.addComponent(TransformComponent.getComponentType(), (Component)transform);
/*    */     
/* 73 */     holder.ensureComponent(UUIDComponent.getComponentType());
/* 74 */     holder.ensureComponent(Intangible.getComponentType());
/* 75 */     holder.ensureComponent(HiddenFromAdventurePlayers.getComponentType());
/*    */     
/* 77 */     store.addEntity(holder, AddReason.SPAWN);
/*    */     
/* 79 */     context.sendMessage(MESSAGE_COMMANDS_CHECKPOINT_ADD_SUCCESS);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\parkour\commands\CheckpointAddCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */