/*     */ package com.hypixel.hytale.builtin.teleport.commands.teleport;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.vector.Transform;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.DefaultArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.RelativeDoublePosition;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*     */ import com.hypixel.hytale.server.core.command.system.exceptions.GeneralCommandException;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.HeadRotation;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.WorldConfig;
/*     */ import com.hypixel.hytale.server.core.universe.world.spawn.GlobalSpawnProvider;
/*     */ import com.hypixel.hytale.server.core.universe.world.spawn.ISpawnProvider;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class SpawnSetCommand
/*     */   extends AbstractWorldCommand
/*     */ {
/*     */   @Nonnull
/*  32 */   private static final DecimalFormat DECIMAL = new DecimalFormat("#.###");
/*     */   @Nonnull
/*  34 */   private static final Message MESSAGE_COMMANDS_ERROR_PROVIDE_POSITION = Message.translation("server.commands.errors.providePosition");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  40 */   private final OptionalArg<RelativeDoublePosition> positionArg = withOptionalArg("position", "server.commands.spawn.set.position.desc", ArgTypes.RELATIVE_POSITION);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  46 */   private final DefaultArg<Vector3f> rotationArg = withDefaultArg("rotation", "server.commands.spawn.set.rotation.desc", ArgTypes.ROTATION, Vector3f.FORWARD, "server.commands.spawn.set.rotation.default.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpawnSetCommand() {
/*  52 */     super("set", "server.commands.spawn.set.desc");
/*     */   }
/*     */   
/*     */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/*     */     Vector3d position;
/*     */     Vector3f rotation;
/*  58 */     if (this.positionArg.provided(context)) {
/*  59 */       RelativeDoublePosition relativePosition = (RelativeDoublePosition)this.positionArg.get(context);
/*  60 */       position = relativePosition.getRelativePosition(context, world, (ComponentAccessor)store);
/*     */     }
/*  62 */     else if (context.isPlayer()) {
/*     */       
/*  64 */       Ref<EntityStore> playerRef = context.senderAsPlayerRef();
/*  65 */       if (playerRef != null && playerRef.isValid()) {
/*     */         
/*  67 */         TransformComponent transformComponent = (TransformComponent)store.getComponent(playerRef, TransformComponent.getComponentType());
/*  68 */         assert transformComponent != null;
/*     */         
/*  70 */         position = transformComponent.getPosition().clone();
/*     */       } else {
/*  72 */         throw new GeneralCommandException(MESSAGE_COMMANDS_ERROR_PROVIDE_POSITION);
/*     */       } 
/*     */     } else {
/*  75 */       throw new GeneralCommandException(MESSAGE_COMMANDS_ERROR_PROVIDE_POSITION);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  80 */     if (this.rotationArg.provided(context)) {
/*  81 */       rotation = (Vector3f)this.rotationArg.get(context);
/*     */     }
/*  83 */     else if (context.isPlayer()) {
/*     */       
/*  85 */       Ref<EntityStore> playerRef = context.senderAsPlayerRef();
/*  86 */       if (playerRef != null && playerRef.isValid()) {
/*     */         
/*  88 */         HeadRotation headRotationComponent = (HeadRotation)store.getComponent(playerRef, HeadRotation.getComponentType());
/*  89 */         assert headRotationComponent != null;
/*     */         
/*  91 */         rotation = headRotationComponent.getRotation();
/*     */       } else {
/*  93 */         rotation = (Vector3f)this.rotationArg.get(context);
/*     */       } 
/*     */     } else {
/*  96 */       rotation = (Vector3f)this.rotationArg.get(context);
/*     */     } 
/*     */ 
/*     */     
/* 100 */     Transform transform = new Transform(position, rotation);
/* 101 */     WorldConfig worldConfig = world.getWorldConfig();
/* 102 */     worldConfig.setSpawnProvider((ISpawnProvider)new GlobalSpawnProvider(transform));
/* 103 */     worldConfig.markChanged();
/* 104 */     world.getLogger().at(Level.INFO).log("Set spawn provider to: %s", worldConfig.getSpawnProvider());
/*     */     
/* 106 */     context.sendMessage(
/* 107 */         Message.translation("server.universe.setspawn.info")
/* 108 */         .param("posX", DECIMAL.format(position.getX()))
/* 109 */         .param("posY", DECIMAL.format(position.getY()))
/* 110 */         .param("posZ", DECIMAL.format(position.getZ()))
/* 111 */         .param("rotX", DECIMAL.format(rotation.getX()))
/* 112 */         .param("rotY", DECIMAL.format(rotation.getY()))
/* 113 */         .param("rotZ", DECIMAL.format(rotation.getZ())));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\teleport\commands\teleport\SpawnSetCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */