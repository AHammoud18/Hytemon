/*     */ package com.hypixel.hytale.builtin.model.commands;
/*     */ 
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.Model;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset;
/*     */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import javax.annotation.Nonnull;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ModelSetCommand
/*     */   extends AbstractPlayerCommand
/*     */ {
/*     */   @Nonnull
/* 103 */   private final RequiredArg<ModelAsset> modelAssetArg = withRequiredArg("model", "server.commands.model.set.model.desc", (ArgumentType)ArgTypes.MODEL_ASSET);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 109 */   private final OptionalArg<Float> scaleArg = withOptionalArg("scale", "server.commands.model.set.scale.desc", (ArgumentType)ArgTypes.FLOAT);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 115 */   private final FlagArg bypassScaleLimitsFlag = withFlagArg("bypassScaleLimits", "server.commands.model.set.bypassScaleLimits.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ModelSetCommand() {
/* 121 */     super("set", "server.commands.model.set.desc");
/* 122 */     addUsageVariant((AbstractCommand)new ModelSetOtherCommand());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 127 */     ModelAsset modelAsset = (ModelAsset)this.modelAssetArg.get(context);
/*     */     
/* 129 */     float scale = this.scaleArg.provided(context) ? ((Float)this.scaleArg.get(context)).floatValue() : modelAsset.generateRandomScale();
/* 130 */     if (!this.bypassScaleLimitsFlag.provided(context)) {
/* 131 */       scale = MathUtil.clamp(scale, modelAsset.getMinScale(), modelAsset.getMaxScale());
/*     */     }
/*     */     
/* 134 */     Model model = Model.createScaledModel(modelAsset, scale);
/* 135 */     store.putComponent(ref, ModelComponent.getComponentType(), (Component)new ModelComponent(model));
/*     */     
/* 137 */     context.sendMessage(Message.translation("server.commands.model.modelSetForPlayer")
/* 138 */         .param("modelName", modelAsset.getId()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class ModelSetOtherCommand
/*     */     extends CommandBase
/*     */   {
/*     */     @Nonnull
/* 149 */     private final RequiredArg<ModelAsset> modelAssetArg = withRequiredArg("model", "server.commands.model.set.model.desc", (ArgumentType)ArgTypes.MODEL_ASSET);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 155 */     private final RequiredArg<PlayerRef> playerArg = withRequiredArg("player", "server.commands.argtype.player.desc", (ArgumentType)ArgTypes.PLAYER_REF);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 161 */     private final OptionalArg<Float> scaleArg = withOptionalArg("scale", "server.commands.model.set.scale.desc", (ArgumentType)ArgTypes.FLOAT);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 167 */     private final FlagArg bypassScaleLimitsFlag = withFlagArg("bypassScaleLimits", "server.commands.model.set.bypassScaleLimits.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     ModelSetOtherCommand() {
/* 173 */       super("server.commands.model.set.other.desc");
/*     */     }
/*     */ 
/*     */     
/*     */     protected void executeSync(@Nonnull CommandContext context) {
/* 178 */       PlayerRef targetPlayerRef = (PlayerRef)this.playerArg.get(context);
/* 179 */       Ref<EntityStore> ref = targetPlayerRef.getReference();
/*     */       
/* 181 */       if (ref == null || !ref.isValid()) {
/* 182 */         context.sendMessage(Message.translation("server.commands.errors.playerNotInWorld"));
/*     */         
/*     */         return;
/*     */       } 
/* 186 */       Store<EntityStore> store = ref.getStore();
/* 187 */       World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */       
/* 189 */       world.execute(() -> {
/*     */             Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*     */             if (playerComponent == null) {
/*     */               context.sendMessage(Message.translation("server.commands.errors.playerNotInWorld"));
/*     */               return;
/*     */             } 
/*     */             ModelAsset modelAsset = (ModelAsset)this.modelAssetArg.get(context);
/*     */             float scale = this.scaleArg.provided(context) ? ((Float)this.scaleArg.get(context)).floatValue() : modelAsset.generateRandomScale();
/*     */             if (!this.bypassScaleLimitsFlag.provided(context))
/*     */               scale = MathUtil.clamp(scale, modelAsset.getMinScale(), modelAsset.getMaxScale()); 
/*     */             Model model = Model.createScaledModel(modelAsset, scale);
/*     */             store.putComponent(ref, ModelComponent.getComponentType(), (Component)new ModelComponent(model));
/*     */             context.sendMessage(Message.translation("server.commands.model.modelSet").param("playerName", targetPlayerRef.getUsername()).param("modelName", modelAsset.getId()));
/*     */           });
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\model\commands\ModelCommand$ModelSetCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */