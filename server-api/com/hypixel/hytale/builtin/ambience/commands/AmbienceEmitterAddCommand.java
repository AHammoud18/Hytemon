/*    */ package com.hypixel.hytale.builtin.ambience.commands;
/*    */ import com.hypixel.hytale.builtin.ambience.AmbiencePlugin;
/*    */ import com.hypixel.hytale.builtin.ambience.components.AmbientEmitterComponent;
/*    */ import com.hypixel.hytale.component.AddReason;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.Holder;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.asset.type.model.config.Model;
/*    */ import com.hypixel.hytale.server.core.asset.type.soundevent.config.SoundEvent;
/*    */ import com.hypixel.hytale.server.core.asset.type.soundevent.config.SoundEventLayer;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.AssetArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.exceptions.GeneralCommandException;
/*    */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*    */ import com.hypixel.hytale.server.core.entity.nameplate.Nameplate;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.HiddenFromAdventurePlayers;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.PersistentModel;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class AmbienceEmitterAddCommand extends AbstractPlayerCommand {
/*    */   @Nonnull
/* 29 */   private static final AssetArgumentType<SoundEvent, ?> SOUND_EVENT_ASSET_TYPE = new AssetArgumentType("server.commands.ambience.emitter.add.arg.soundEvent.name", SoundEvent.class, "server.commands.ambience.emitter.add.arg.soundEvent.usage");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 37 */   private final RequiredArg<SoundEvent> soundEventArg = withRequiredArg("soundEvent", "server.commands.ambience.emitter.add.arg.soundEvent.desc", (ArgumentType)SOUND_EVENT_ASSET_TYPE);
/*    */   
/*    */   public AmbienceEmitterAddCommand() {
/* 40 */     super("add", "server.commands.ambience.emitter.add.desc");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 46 */     if (!context.isPlayer()) {
/* 47 */       throw new GeneralCommandException(Message.translation("server.commands.errors.playerOnly"));
/*    */     }
/*    */     
/* 50 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/* 51 */     assert transformComponent != null;
/*    */     
/* 53 */     Holder<EntityStore> holder = EntityStore.REGISTRY.newHolder();
/*    */     
/* 55 */     SoundEvent soundEvent = (SoundEvent)this.soundEventArg.get(context);
/*    */     
/* 57 */     boolean looping = false;
/* 58 */     for (SoundEventLayer layer : soundEvent.getLayers()) {
/* 59 */       if (layer.isLooping()) {
/* 60 */         looping = true;
/*    */         
/*    */         break;
/*    */       } 
/*    */     } 
/* 65 */     if (!looping) {
/* 66 */       context.sendMessage(Message.translation("server.commands.ambience.emitter.add.notLooping")
/* 67 */           .param("soundEventId", soundEvent.getId()));
/*    */       
/*    */       return;
/*    */     } 
/* 71 */     AmbientEmitterComponent emitterComponent = new AmbientEmitterComponent();
/* 72 */     emitterComponent.setSoundEventId(soundEvent.getId());
/* 73 */     holder.addComponent(AmbientEmitterComponent.getComponentType(), (Component)emitterComponent);
/*    */     
/* 75 */     TransformComponent emitterTransform = transformComponent.clone();
/* 76 */     holder.addComponent(TransformComponent.getComponentType(), (Component)emitterTransform);
/* 77 */     holder.addComponent(Nameplate.getComponentType(), (Component)new Nameplate(soundEvent.getId()));
/* 78 */     holder.ensureComponent(UUIDComponent.getComponentType());
/* 79 */     holder.ensureComponent(HiddenFromAdventurePlayers.getComponentType());
/*    */     
/* 81 */     Model model = AmbiencePlugin.get().getAmbientEmitterModel();
/* 82 */     holder.addComponent(ModelComponent.getComponentType(), (Component)new ModelComponent(model));
/* 83 */     holder.addComponent(PersistentModel.getComponentType(), (Component)new PersistentModel(model.toReference()));
/*    */     
/* 85 */     Ref<EntityStore> emitterRef = store.addEntity(holder, AddReason.SPAWN);
/* 86 */     if (emitterRef == null || !emitterRef.isValid()) {
/* 87 */       context.sendMessage(Message.translation("server.commands.ambience.emitter.add.failed")
/* 88 */           .param("soundEventId", soundEvent.getId()));
/*    */       
/*    */       return;
/*    */     } 
/* 92 */     context.sendMessage(Message.translation("server.commands.ambience.emitter.add.added")
/* 93 */         .param("soundEventId", soundEvent.getId()));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\ambience\commands\AmbienceEmitterAddCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */