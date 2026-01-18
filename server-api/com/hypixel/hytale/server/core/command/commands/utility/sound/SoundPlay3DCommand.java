/*    */ package com.hypixel.hytale.server.core.command.commands.utility.sound;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.math.util.MathUtil;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.protocol.SoundCategory;
/*    */ import com.hypixel.hytale.server.core.asset.type.soundevent.config.SoundEvent;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.DefaultArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.RelativeVector3i;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractTargetPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.SoundUtil;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SoundPlay3DCommand
/*    */   extends AbstractTargetPlayerCommand
/*    */ {
/*    */   @Nonnull
/* 34 */   private final RequiredArg<SoundEvent> soundEventArg = withRequiredArg("sound", "server.commands.sound.play3d.sound.desc", (ArgumentType)ArgTypes.SOUND_EVENT_ASSET);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 40 */   private final DefaultArg<SoundCategory> categoryArg = withDefaultArg("category", "server.commands.sound.category.desc", (ArgumentType)ArgTypes.SOUND_CATEGORY, SoundCategory.SFX, "server.commands.sound.category.default");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 46 */   private final RequiredArg<RelativeVector3i> positionArg = withRequiredArg("position", "server.commands.sound.play3d.position.desc", ArgTypes.RELATIVE_VECTOR3I);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 52 */   private final FlagArg allFlag = withFlagArg("all", "server.commands.sound.all.desc");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SoundPlay3DCommand() {
/* 58 */     super("3d", "server.commands.sound.3d.desc");
/* 59 */     addAliases(new String[] { "play3d" });
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nullable Ref<EntityStore> sourceRef, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 64 */     SoundEvent soundEvent = (SoundEvent)this.soundEventArg.get(context);
/* 65 */     SoundCategory soundCategory = (SoundCategory)this.categoryArg.get(context);
/* 66 */     RelativeVector3i relativePosition = (RelativeVector3i)this.positionArg.get(context);
/*    */     
/* 68 */     int soundEventIndex = SoundEvent.getAssetMap().getIndex(soundEvent.getId());
/*    */     
/* 70 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/* 71 */     assert transformComponent != null;
/* 72 */     Vector3d basePosition = transformComponent.getPosition();
/* 73 */     Vector3i blockPosition = relativePosition.resolve(MathUtil.floor(basePosition.x), MathUtil.floor(basePosition.y), MathUtil.floor(basePosition.z));
/*    */     
/* 75 */     if (this.allFlag.provided(context)) {
/*    */       
/* 77 */       SoundUtil.playSoundEvent3d(soundEventIndex, soundCategory, blockPosition.x, blockPosition.y, blockPosition.z, (ComponentAccessor)world
/*    */           
/* 79 */           .getEntityStore().getStore());
/*    */     } else {
/*    */       
/* 82 */       SoundUtil.playSoundEvent3dToPlayer(ref, soundEventIndex, soundCategory, blockPosition.x, blockPosition.y, blockPosition.z, (ComponentAccessor)store);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\command\\utility\sound\SoundPlay3DCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */