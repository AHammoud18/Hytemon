/*     */ package com.hypixel.hytale.builtin.model.commands;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.model.pages.ChangeModelPage;
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
/*     */ import com.hypixel.hytale.server.core.cosmetics.CosmeticsModule;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.entity.entities.player.pages.CustomUIPage;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.player.PlayerSkinComponent;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ModelCommand
/*     */   extends AbstractPlayerCommand
/*     */ {
/*     */   public ModelCommand() {
/*  38 */     super("model", "server.commands.model.desc");
/*  39 */     addUsageVariant((AbstractCommand)new ModelOtherCommand());
/*  40 */     addSubCommand((AbstractCommand)new ModelSetCommand());
/*  41 */     addSubCommand((AbstractCommand)new ModelResetCommand());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/*  46 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*  47 */     assert playerComponent != null;
/*     */     
/*  49 */     playerComponent.getPageManager().openCustomPage(ref, store, (CustomUIPage)new ChangeModelPage(playerRef));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class ModelOtherCommand
/*     */     extends CommandBase
/*     */   {
/*     */     @Nonnull
/*  61 */     private final RequiredArg<PlayerRef> playerArg = withRequiredArg("player", "server.commands.argtype.player.desc", (ArgumentType)ArgTypes.PLAYER_REF);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     ModelOtherCommand() {
/*  67 */       super("server.commands.model.other.desc");
/*     */     }
/*     */ 
/*     */     
/*     */     protected void executeSync(@Nonnull CommandContext context) {
/*  72 */       PlayerRef targetPlayerRef = (PlayerRef)this.playerArg.get(context);
/*  73 */       Ref<EntityStore> ref = targetPlayerRef.getReference();
/*     */       
/*  75 */       if (ref == null || !ref.isValid()) {
/*  76 */         context.sendMessage(Message.translation("server.commands.errors.playerNotInWorld"));
/*     */         
/*     */         return;
/*     */       } 
/*  80 */       Store<EntityStore> store = ref.getStore();
/*  81 */       World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */       
/*  83 */       world.execute(() -> {
/*     */             Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*     */             if (playerComponent == null) {
/*     */               context.sendMessage(Message.translation("server.commands.errors.playerNotInWorld"));
/*     */               return;
/*     */             } 
/*     */             playerComponent.getPageManager().openCustomPage(ref, store, (CustomUIPage)new ChangeModelPage(targetPlayerRef));
/*     */           });
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class ModelSetCommand
/*     */     extends AbstractPlayerCommand
/*     */   {
/*     */     @Nonnull
/* 103 */     private final RequiredArg<ModelAsset> modelAssetArg = withRequiredArg("model", "server.commands.model.set.model.desc", (ArgumentType)ArgTypes.MODEL_ASSET);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 109 */     private final OptionalArg<Float> scaleArg = withOptionalArg("scale", "server.commands.model.set.scale.desc", (ArgumentType)ArgTypes.FLOAT);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 115 */     private final FlagArg bypassScaleLimitsFlag = withFlagArg("bypassScaleLimits", "server.commands.model.set.bypassScaleLimits.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     ModelSetCommand() {
/* 121 */       super("set", "server.commands.model.set.desc");
/* 122 */       addUsageVariant((AbstractCommand)new ModelSetOtherCommand());
/*     */     }
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 127 */       ModelAsset modelAsset = (ModelAsset)this.modelAssetArg.get(context);
/*     */       
/* 129 */       float scale = this.scaleArg.provided(context) ? ((Float)this.scaleArg.get(context)).floatValue() : modelAsset.generateRandomScale();
/* 130 */       if (!this.bypassScaleLimitsFlag.provided(context)) {
/* 131 */         scale = MathUtil.clamp(scale, modelAsset.getMinScale(), modelAsset.getMaxScale());
/*     */       }
/*     */       
/* 134 */       Model model = Model.createScaledModel(modelAsset, scale);
/* 135 */       store.putComponent(ref, ModelComponent.getComponentType(), (Component)new ModelComponent(model));
/*     */       
/* 137 */       context.sendMessage(Message.translation("server.commands.model.modelSetForPlayer")
/* 138 */           .param("modelName", modelAsset.getId()));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static class ModelSetOtherCommand
/*     */       extends CommandBase
/*     */     {
/*     */       @Nonnull
/* 149 */       private final RequiredArg<ModelAsset> modelAssetArg = withRequiredArg("model", "server.commands.model.set.model.desc", (ArgumentType)ArgTypes.MODEL_ASSET);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       @Nonnull
/* 155 */       private final RequiredArg<PlayerRef> playerArg = withRequiredArg("player", "server.commands.argtype.player.desc", (ArgumentType)ArgTypes.PLAYER_REF);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       @Nonnull
/* 161 */       private final OptionalArg<Float> scaleArg = withOptionalArg("scale", "server.commands.model.set.scale.desc", (ArgumentType)ArgTypes.FLOAT);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       @Nonnull
/* 167 */       private final FlagArg bypassScaleLimitsFlag = withFlagArg("bypassScaleLimits", "server.commands.model.set.bypassScaleLimits.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       ModelSetOtherCommand() {
/* 173 */         super("server.commands.model.set.other.desc");
/*     */       }
/*     */       
/*     */       protected void executeSync(@Nonnull CommandContext context)
/*     */       {
/* 178 */         PlayerRef targetPlayerRef = (PlayerRef)this.playerArg.get(context);
/* 179 */         Ref<EntityStore> ref = targetPlayerRef.getReference();
/*     */         
/* 181 */         if (ref == null || !ref.isValid()) {
/* 182 */           context.sendMessage(Message.translation("server.commands.errors.playerNotInWorld"));
/*     */           
/*     */           return;
/*     */         } 
/* 186 */         Store<EntityStore> store = ref.getStore();
/* 187 */         World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */         
/* 189 */         world.execute(() -> { Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType()); if (playerComponent == null) { context.sendMessage(Message.translation("server.commands.errors.playerNotInWorld")); return; }  ModelAsset modelAsset = (ModelAsset)this.modelAssetArg.get(context); float scale = this.scaleArg.provided(context) ? ((Float)this.scaleArg.get(context)).floatValue() : modelAsset.generateRandomScale(); if (!this.bypassScaleLimitsFlag.provided(context)) scale = MathUtil.clamp(scale, modelAsset.getMinScale(), modelAsset.getMaxScale());  Model model = Model.createScaledModel(modelAsset, scale); store.putComponent(ref, ModelComponent.getComponentType(), (Component)new ModelComponent(model)); context.sendMessage(Message.translation("server.commands.model.modelSet").param("playerName", targetPlayerRef.getUsername()).param("modelName", modelAsset.getId())); }); } } } private static class ModelSetOtherCommand extends CommandBase { @Nonnull private final RequiredArg<ModelAsset> modelAssetArg = withRequiredArg("model", "server.commands.model.set.model.desc", (ArgumentType)ArgTypes.MODEL_ASSET); @Nonnull private final RequiredArg<PlayerRef> playerArg = withRequiredArg("player", "server.commands.argtype.player.desc", (ArgumentType)ArgTypes.PLAYER_REF); protected void executeSync(@Nonnull CommandContext context) { PlayerRef targetPlayerRef = (PlayerRef)this.playerArg.get(context); Ref<EntityStore> ref = targetPlayerRef.getReference(); if (ref == null || !ref.isValid()) { context.sendMessage(Message.translation("server.commands.errors.playerNotInWorld")); return; }  Store<EntityStore> store = ref.getStore(); World world = ((EntityStore)store.getExternalData()).getWorld(); world.execute(() -> {
/*     */             Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*     */             if (playerComponent == null) {
/*     */               context.sendMessage(Message.translation("server.commands.errors.playerNotInWorld"));
/*     */               return;
/*     */             } 
/*     */             ModelAsset modelAsset = (ModelAsset)this.modelAssetArg.get(context);
/*     */             float scale = this.scaleArg.provided(context) ? ((Float)this.scaleArg.get(context)).floatValue() : modelAsset.generateRandomScale();
/*     */             if (!this.bypassScaleLimitsFlag.provided(context)) {
/*     */               scale = MathUtil.clamp(scale, modelAsset.getMinScale(), modelAsset.getMaxScale());
/*     */             }
/*     */             Model model = Model.createScaledModel(modelAsset, scale);
/*     */             store.putComponent(ref, ModelComponent.getComponentType(), (Component)new ModelComponent(model));
/*     */             context.sendMessage(Message.translation("server.commands.model.modelSet").param("playerName", targetPlayerRef.getUsername()).param("modelName", modelAsset.getId()));
/*     */           }); }
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     private final OptionalArg<Float> scaleArg = withOptionalArg("scale", "server.commands.model.set.scale.desc", (ArgumentType)ArgTypes.FLOAT);
/*     */     
/*     */     @Nonnull
/*     */     private final FlagArg bypassScaleLimitsFlag = withFlagArg("bypassScaleLimits", "server.commands.model.set.bypassScaleLimits.desc");
/*     */ 
/*     */     
/*     */     ModelSetOtherCommand() {
/*     */       super("server.commands.model.set.other.desc");
/*     */     } }
/*     */ 
/*     */   
/*     */   static class ModelResetCommand
/*     */     extends AbstractPlayerCommand
/*     */   {
/*     */     @Nonnull
/* 223 */     private final OptionalArg<Float> scaleArg = withOptionalArg("scale", "server.commands.model.reset.scale.desc", (ArgumentType)ArgTypes.FLOAT);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     ModelResetCommand() {
/* 229 */       super("reset", "server.commands.model.reset.desc");
/* 230 */       addAliases(new String[] { "clear" });
/* 231 */       addUsageVariant((AbstractCommand)new ModelResetOtherCommand());
/*     */     }
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 236 */       PlayerSkinComponent skinComponent = (PlayerSkinComponent)store.getComponent(ref, PlayerSkinComponent.getComponentType());
/* 237 */       if (skinComponent == null) {
/* 238 */         context.sendMessage(Message.translation("server.commands.model.noAuthSkinForPlayer")
/* 239 */             .param("model", "Player"));
/*     */         
/*     */         return;
/*     */       } 
/* 243 */       PlayerSkinComponent playerSkinComponent = (PlayerSkinComponent)store.getComponent(ref, PlayerSkinComponent.getComponentType());
/* 244 */       assert playerSkinComponent != null;
/*     */       
/* 246 */       CosmeticsModule cosmeticsModule = CosmeticsModule.get();
/* 247 */       if (this.scaleArg.provided(context)) {
/* 248 */         Float scale = (Float)this.scaleArg.get(context);
/* 249 */         Model newModel = cosmeticsModule.createModel(playerSkinComponent.getPlayerSkin(), scale.floatValue());
/* 250 */         store.putComponent(ref, ModelComponent.getComponentType(), (Component)new ModelComponent(newModel));
/*     */       } else {
/* 252 */         Model newModel = cosmeticsModule.createModel(playerSkinComponent.getPlayerSkin());
/* 253 */         store.putComponent(ref, ModelComponent.getComponentType(), (Component)new ModelComponent(newModel));
/*     */       } 
/*     */       
/* 256 */       playerSkinComponent.setNetworkOutdated();
/*     */       
/* 258 */       context.sendMessage(Message.translation("server.commands.model.modelResetForPlayer"));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static class ModelResetOtherCommand
/*     */       extends CommandBase
/*     */     {
/*     */       @Nonnull
/* 270 */       private final RequiredArg<PlayerRef> playerArg = withRequiredArg("player", "server.commands.argtype.player.desc", (ArgumentType)ArgTypes.PLAYER_REF);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       @Nonnull
/* 276 */       private final OptionalArg<Float> scaleArg = withOptionalArg("scale", "server.commands.model.reset.scale.desc", (ArgumentType)ArgTypes.FLOAT);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       ModelResetOtherCommand() {
/* 282 */         super("server.commands.model.reset.other.desc");
/*     */       }
/*     */       
/*     */       protected void executeSync(@Nonnull CommandContext context)
/*     */       {
/* 287 */         PlayerRef targetPlayerRef = (PlayerRef)this.playerArg.get(context);
/* 288 */         Ref<EntityStore> ref = targetPlayerRef.getReference();
/*     */         
/* 290 */         if (ref == null || !ref.isValid()) {
/* 291 */           context.sendMessage(Message.translation("server.commands.errors.playerNotInWorld"));
/*     */           
/*     */           return;
/*     */         } 
/* 295 */         Store<EntityStore> store = ref.getStore();
/* 296 */         World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */         
/* 298 */         world.execute(() -> { Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType()); if (playerComponent == null) { context.sendMessage(Message.translation("server.commands.errors.playerNotInWorld")); return; }  PlayerSkinComponent skinComponent = (PlayerSkinComponent)store.getComponent(ref, PlayerSkinComponent.getComponentType()); if (skinComponent == null) { context.sendMessage(Message.translation("server.commands.model.noAuthSkin").param("name", targetPlayerRef.getUsername()).param("model", "Player")); return; }  PlayerSkinComponent playerSkinComponent = (PlayerSkinComponent)store.getComponent(ref, PlayerSkinComponent.getComponentType()); assert playerSkinComponent != null; CosmeticsModule cosmeticsModule = CosmeticsModule.get(); if (this.scaleArg.provided(context)) { Float scale = (Float)this.scaleArg.get(context); Model newModel = cosmeticsModule.createModel(playerSkinComponent.getPlayerSkin(), scale.floatValue()); store.putComponent(ref, ModelComponent.getComponentType(), (Component)new ModelComponent(newModel)); } else { Model newModel = cosmeticsModule.createModel(playerSkinComponent.getPlayerSkin()); store.putComponent(ref, ModelComponent.getComponentType(), (Component)new ModelComponent(newModel)); }  playerSkinComponent.setNetworkOutdated(); context.sendMessage(Message.translation("server.commands.model.modelReset").param("name", targetPlayerRef.getUsername())); }); } } } private static class ModelResetOtherCommand extends CommandBase { protected void executeSync(@Nonnull CommandContext context) { PlayerRef targetPlayerRef = (PlayerRef)this.playerArg.get(context); Ref<EntityStore> ref = targetPlayerRef.getReference(); if (ref == null || !ref.isValid()) { context.sendMessage(Message.translation("server.commands.errors.playerNotInWorld")); return; }  Store<EntityStore> store = ref.getStore(); World world = ((EntityStore)store.getExternalData()).getWorld(); world.execute(() -> {
/*     */             Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*     */             if (playerComponent == null) {
/*     */               context.sendMessage(Message.translation("server.commands.errors.playerNotInWorld"));
/*     */               return;
/*     */             } 
/*     */             PlayerSkinComponent skinComponent = (PlayerSkinComponent)store.getComponent(ref, PlayerSkinComponent.getComponentType());
/*     */             if (skinComponent == null) {
/*     */               context.sendMessage(Message.translation("server.commands.model.noAuthSkin").param("name", targetPlayerRef.getUsername()).param("model", "Player"));
/*     */               return;
/*     */             } 
/*     */             PlayerSkinComponent playerSkinComponent = (PlayerSkinComponent)store.getComponent(ref, PlayerSkinComponent.getComponentType());
/*     */             assert playerSkinComponent != null;
/*     */             CosmeticsModule cosmeticsModule = CosmeticsModule.get();
/*     */             if (this.scaleArg.provided(context)) {
/*     */               Float scale = (Float)this.scaleArg.get(context);
/*     */               Model newModel = cosmeticsModule.createModel(playerSkinComponent.getPlayerSkin(), scale.floatValue());
/*     */               store.putComponent(ref, ModelComponent.getComponentType(), (Component)new ModelComponent(newModel));
/*     */             } else {
/*     */               Model newModel = cosmeticsModule.createModel(playerSkinComponent.getPlayerSkin());
/*     */               store.putComponent(ref, ModelComponent.getComponentType(), (Component)new ModelComponent(newModel));
/*     */             } 
/*     */             playerSkinComponent.setNetworkOutdated();
/*     */             context.sendMessage(Message.translation("server.commands.model.modelReset").param("name", targetPlayerRef.getUsername()));
/*     */           }); }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     private final RequiredArg<PlayerRef> playerArg = withRequiredArg("player", "server.commands.argtype.player.desc", (ArgumentType)ArgTypes.PLAYER_REF);
/*     */     @Nonnull
/*     */     private final OptionalArg<Float> scaleArg = withOptionalArg("scale", "server.commands.model.reset.scale.desc", (ArgumentType)ArgTypes.FLOAT);
/*     */     
/*     */     ModelResetOtherCommand() {
/*     */       super("server.commands.model.reset.other.desc");
/*     */     } }
/*     */ 
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\model\commands\ModelCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */