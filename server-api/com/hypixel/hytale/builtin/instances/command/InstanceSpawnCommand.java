/*     */ package com.hypixel.hytale.builtin.instances.command;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.instances.InstanceValidator;
/*     */ import com.hypixel.hytale.builtin.instances.InstancesPlugin;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.vector.Transform;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.DefaultArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.RelativeDoublePosition;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InstanceSpawnCommand
/*     */   extends AbstractPlayerCommand
/*     */ {
/*     */   @Nonnull
/*  34 */   private final RequiredArg<String> instanceNameArg = (RequiredArg<String>)
/*  35 */     withRequiredArg("instanceName", "server.commands.instances.spawn.arg.name", (ArgumentType)ArgTypes.STRING)
/*  36 */     .addValidator((Validator)new InstanceValidator());
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  42 */   private final OptionalArg<RelativeDoublePosition> positionArg = withOptionalArg("position", "server.commands.instances.spawn.arg.position", ArgTypes.RELATIVE_POSITION);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  48 */   private final DefaultArg<Vector3f> rotationArg = withDefaultArg("rotation", "server.commands.instances.spawn.arg.rotation", ArgTypes.ROTATION, Vector3f.FORWARD, "server.commands.instances.spawn.arg.rotation.default");
/*     */ 
/*     */   
/*     */   public InstanceSpawnCommand() {
/*  52 */     super("spawn", "server.commands.instances.spawn.desc");
/*  53 */     addAliases(new String[] { "sp" });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Vector3f getSpawnRotation(@Nonnull Ref<EntityStore> ref, @Nonnull CommandContext context, @Nonnull DefaultArg<Vector3f> rotationArg, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  69 */     if (rotationArg.provided(context) || !context.isPlayer()) {
/*  70 */       return (Vector3f)rotationArg.get(context);
/*     */     }
/*  72 */     TransformComponent headRotationComponent = (TransformComponent)componentAccessor.getComponent(ref, TransformComponent.getComponentType());
/*  73 */     assert headRotationComponent != null;
/*     */     
/*  75 */     return headRotationComponent.getRotation().clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/*     */     Vector3d position;
/*  86 */     if (!this.positionArg.provided(context)) {
/*  87 */       TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/*  88 */       assert transformComponent != null;
/*     */       
/*  90 */       position = transformComponent.getPosition();
/*     */     } else {
/*  92 */       position = ((RelativeDoublePosition)this.positionArg.get(context)).getRelativePosition(context, world, (ComponentAccessor)store);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  97 */     Transform returnLocation = new Transform(position.clone(), getSpawnRotation(ref, context, this.rotationArg, (ComponentAccessor<EntityStore>)store).clone());
/*     */ 
/*     */     
/* 100 */     String instanceName = (String)this.instanceNameArg.get(context);
/* 101 */     CompletableFuture<World> instanceWorld = InstancesPlugin.get().spawnInstance(instanceName, world, returnLocation);
/* 102 */     InstancesPlugin.teleportPlayerToLoadingInstance(ref, (ComponentAccessor)store, instanceWorld, null);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\instances\command\InstanceSpawnCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */