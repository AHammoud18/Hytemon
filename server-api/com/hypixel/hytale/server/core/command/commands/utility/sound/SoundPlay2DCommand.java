/*    */ package com.hypixel.hytale.server.core.command.commands.utility.sound;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.protocol.SoundCategory;
/*    */ import com.hypixel.hytale.server.core.asset.type.soundevent.config.SoundEvent;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.DefaultArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractTargetPlayerCommand;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SoundPlay2DCommand
/*    */   extends AbstractTargetPlayerCommand
/*    */ {
/*    */   @Nonnull
/* 32 */   private final RequiredArg<SoundEvent> soundEventArg = withRequiredArg("sound", "server.commands.sound.play.sound.desc", (ArgumentType)ArgTypes.SOUND_EVENT_ASSET);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 38 */   private final DefaultArg<SoundCategory> categoryArg = withDefaultArg("category", "server.commands.sound.category.desc", (ArgumentType)ArgTypes.SOUND_CATEGORY, SoundCategory.SFX, "server.commands.sound.category.default");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 44 */   private final FlagArg allFlag = withFlagArg("all", "server.commands.sound.all.desc");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SoundPlay2DCommand() {
/* 50 */     super("2d", "server.commands.sound.2d.desc");
/* 51 */     addAliases(new String[] { "play" });
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nullable Ref<EntityStore> sourceRef, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 56 */     SoundEvent soundEvent = (SoundEvent)this.soundEventArg.get(context);
/* 57 */     SoundCategory soundCategory = (SoundCategory)this.categoryArg.get(context);
/*    */     
/* 59 */     int soundEventIndex = SoundEvent.getAssetMap().getIndex(soundEvent.getId());
/*    */     
/* 61 */     if (this.allFlag.provided(context)) {
/* 62 */       SoundUtil.playSoundEvent2d(soundEventIndex, soundCategory, (ComponentAccessor)store);
/*    */     } else {
/* 64 */       SoundUtil.playSoundEvent2d(ref, soundEventIndex, soundCategory, (ComponentAccessor)store);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\command\\utility\sound\SoundPlay2DCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */