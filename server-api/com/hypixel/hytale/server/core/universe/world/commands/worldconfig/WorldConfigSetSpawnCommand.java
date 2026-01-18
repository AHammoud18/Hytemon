/*     */ package com.hypixel.hytale.server.core.universe.world.commands.worldconfig;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.vector.Transform;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
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
/*     */ public class WorldConfigSetSpawnCommand
/*     */   extends AbstractWorldCommand {
/*     */   @Nonnull
/*  32 */   private static final DecimalFormat DECIMAL = new DecimalFormat("#.###");
/*     */   @Nonnull
/*  34 */   private static final Message MESSAGE_COMMANDS_ERROR_PROVIDE_POSITION = Message.translation("server.commands.errors.providePosition");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  40 */   private final OptionalArg<RelativeDoublePosition> positionArg = withOptionalArg("position", "server.commands.world.config.setspawn.position.desc", ArgTypes.RELATIVE_POSITION);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  46 */   private final DefaultArg<Vector3f> rotationArg = withDefaultArg("rotation", "server.commands.world.config.setspawn.rotation.desc", ArgTypes.ROTATION, Vector3f.FORWARD, "server.commands.world.config.setspawn.rotation.default.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldConfigSetSpawnCommand() {
/*  52 */     super("setspawn", "server.commands.world.config.setspawn.desc");
/*  53 */     addSubCommand((AbstractCommand)new WorldConfigSetSpawnDefaultCommand());
/*     */   }
/*     */   
/*     */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/*     */     Vector3d position;
/*     */     Vector3f rotation;
/*  59 */     if (this.positionArg.provided(context)) {
/*  60 */       RelativeDoublePosition relativePosition = (RelativeDoublePosition)this.positionArg.get(context);
/*  61 */       position = relativePosition.getRelativePosition(context, world, (ComponentAccessor)store);
/*     */     }
/*  63 */     else if (context.isPlayer()) {
/*  64 */       Ref<EntityStore> playerRef = context.senderAsPlayerRef();
/*  65 */       if (playerRef != null && playerRef.isValid()) {
/*  66 */         TransformComponent transformComponent = (TransformComponent)store.getComponent(playerRef, TransformComponent.getComponentType());
/*  67 */         assert transformComponent != null;
/*  68 */         position = transformComponent.getPosition().clone();
/*     */       } else {
/*  70 */         throw new GeneralCommandException(MESSAGE_COMMANDS_ERROR_PROVIDE_POSITION);
/*     */       } 
/*     */     } else {
/*  73 */       throw new GeneralCommandException(MESSAGE_COMMANDS_ERROR_PROVIDE_POSITION);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  78 */     if (this.rotationArg.provided(context)) {
/*  79 */       rotation = (Vector3f)this.rotationArg.get(context);
/*     */     }
/*  81 */     else if (context.isPlayer()) {
/*  82 */       Ref<EntityStore> playerRef = context.senderAsPlayerRef();
/*  83 */       if (playerRef != null && playerRef.isValid()) {
/*  84 */         HeadRotation headRotationComponent = (HeadRotation)store.getComponent(playerRef, HeadRotation.getComponentType());
/*  85 */         assert headRotationComponent != null;
/*  86 */         rotation = headRotationComponent.getRotation();
/*     */       } else {
/*  88 */         rotation = (Vector3f)this.rotationArg.get(context);
/*     */       } 
/*     */     } else {
/*  91 */       rotation = (Vector3f)this.rotationArg.get(context);
/*     */     } 
/*     */ 
/*     */     
/*  95 */     Transform transform = new Transform(position, rotation);
/*  96 */     WorldConfig worldConfig = world.getWorldConfig();
/*  97 */     worldConfig.setSpawnProvider((ISpawnProvider)new GlobalSpawnProvider(transform));
/*  98 */     worldConfig.markChanged();
/*  99 */     world.getLogger().at(Level.INFO).log("Set spawn provider to: %s", worldConfig.getSpawnProvider());
/*     */     
/* 101 */     context.sendMessage(
/* 102 */         Message.translation("server.universe.setspawn.info")
/* 103 */         .param("posX", DECIMAL.format(position.getX()))
/* 104 */         .param("posY", DECIMAL.format(position.getY()))
/* 105 */         .param("posZ", DECIMAL.format(position.getZ()))
/* 106 */         .param("rotX", DECIMAL.format(rotation.getX()))
/* 107 */         .param("rotY", DECIMAL.format(rotation.getY()))
/* 108 */         .param("rotZ", DECIMAL.format(rotation.getZ())));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\commands\worldconfig\WorldConfigSetSpawnCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */