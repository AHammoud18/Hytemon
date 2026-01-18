/*     */ package com.hypixel.hytale.server.core.command.commands.player;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.Axis;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.protocol.GameMode;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.HeadRotation;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.permissions.HytalePermissions;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class WhereAmICommand extends AbstractPlayerCommand {
/*     */   @Nonnull
/*  32 */   private static final Message MESSAGE_COMMANDS_WHERE_AM_I_CHUNK_NOT_LOADED = Message.translation("server.commands.whereami.chunkNotLoaded");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WhereAmICommand() {
/*  38 */     super("whereami", "server.commands.whereami.desc");
/*  39 */     setPermissionGroup(GameMode.Creative);
/*  40 */     requirePermission(HytalePermissions.fromCommand("whereami.self"));
/*  41 */     addUsageVariant((AbstractCommand)new WhereAmIOtherCommand());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/*  50 */     sendLocationInfo(context, store, ref, world, (String)null);
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
/*     */   private static void sendLocationInfo(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull World world, @Nullable String targetUsername) {
/*  63 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/*  64 */     assert transformComponent != null;
/*     */     
/*  66 */     HeadRotation headRotationComponent = (HeadRotation)store.getComponent(ref, HeadRotation.getComponentType());
/*  67 */     assert headRotationComponent != null;
/*     */     
/*  69 */     Vector3d position = transformComponent.getPosition();
/*  70 */     Vector3f headRotation = headRotationComponent.getRotation();
/*  71 */     Vector3i axisDirection = headRotationComponent.getAxisDirection();
/*  72 */     Axis axis = headRotationComponent.getAxis();
/*  73 */     Vector3d direction = headRotationComponent.getDirection();
/*     */     
/*  75 */     int chunkX = MathUtil.floor(position.getX()) >> 5;
/*  76 */     int chunkY = MathUtil.floor(position.getY()) >> 5;
/*  77 */     int chunkZ = MathUtil.floor(position.getZ()) >> 5;
/*     */     
/*  79 */     long chunkIndex = ChunkUtil.indexChunk(chunkX, chunkZ);
/*  80 */     WorldChunk playerChunk = world.getChunkIfInMemory(chunkIndex);
/*     */ 
/*     */ 
/*     */     
/*  84 */     String headerKey = (targetUsername != null) ? "server.commands.whereami.header.other" : "server.commands.whereami.header";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  94 */     Message message = Message.translation(headerKey).param("username", targetUsername).param("world", world.getName()).param("chunkX", chunkX).param("chunkY", chunkY).param("chunkZ", chunkZ).param("posX", position.getX()).param("posY", position.getY()).param("posZ", position.getZ()).param("yaw", headRotation.getYaw()).param("pitch", headRotation.getPitch()).param("roll", headRotation.getRoll()).param("direction", direction.toString()).param("axisDirection", axisDirection.toString()).param("axis", axis.toString());
/*     */     
/*  96 */     if (playerChunk == null) {
/*  97 */       message.insert(MESSAGE_COMMANDS_WHERE_AM_I_CHUNK_NOT_LOADED);
/*     */     } else {
/*  99 */       message.insert(Message.translation("server.commands.whereami.needsSaving")
/* 100 */           .param("needsSaving", Boolean.toString(playerChunk.getNeedsSaving())));
/*     */     } 
/* 102 */     context.sendMessage(message);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class WhereAmIOtherCommand
/*     */     extends CommandBase
/*     */   {
/* 110 */     private static final Message MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD = Message.translation("server.commands.errors.playerNotInWorld");
/*     */     
/*     */     @Nonnull
/* 113 */     private final RequiredArg<PlayerRef> playerArg = withRequiredArg("player", "server.commands.argtype.player.desc", (ArgumentType)ArgTypes.PLAYER_REF);
/*     */     
/*     */     WhereAmIOtherCommand() {
/* 116 */       super("server.commands.whereami.other.desc");
/* 117 */       setPermissionGroup(GameMode.Creative);
/* 118 */       requirePermission(HytalePermissions.fromCommand("whereami.other"));
/*     */     }
/*     */ 
/*     */     
/*     */     protected void executeSync(@Nonnull CommandContext context) {
/* 123 */       PlayerRef targetPlayerRef = (PlayerRef)this.playerArg.get(context);
/* 124 */       Ref<EntityStore> ref = targetPlayerRef.getReference();
/*     */       
/* 126 */       if (ref == null || !ref.isValid()) {
/* 127 */         context.sendMessage(MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD);
/*     */         
/*     */         return;
/*     */       } 
/* 131 */       Store<EntityStore> store = ref.getStore();
/* 132 */       World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */       
/* 134 */       world.execute(() -> {
/*     */             Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*     */             if (playerComponent == null) {
/*     */               context.sendMessage(MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD);
/*     */               return;
/*     */             } 
/*     */             PlayerRef playerRefComponent = (PlayerRef)store.getComponent(ref, PlayerRef.getComponentType());
/*     */             assert playerRefComponent != null;
/*     */             WhereAmICommand.sendLocationInfo(context, store, ref, world, playerRefComponent.getUsername());
/*     */           });
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\player\WhereAmICommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */