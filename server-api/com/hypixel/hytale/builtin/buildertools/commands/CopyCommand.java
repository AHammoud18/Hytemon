/*     */ package com.hypixel.hytale.builtin.buildertools.commands;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin;
/*     */ import com.hypixel.hytale.builtin.buildertools.PrefabCopyException;
/*     */ import com.hypixel.hytale.builtin.buildertools.PrototypePlayerBuilderToolSettings;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.protocol.GameMode;
/*     */ import com.hypixel.hytale.protocol.SoundCategory;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.standard.BlockSelection;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.SoundUtil;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.core.util.TempAssetIdUtil;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ public class CopyCommand
/*     */   extends AbstractPlayerCommand
/*     */ {
/*     */   @Nonnull
/*  34 */   private static final Message MESSAGE_BUILDER_TOOLS_COPY_CUT_NO_SELECTION = Message.translation("server.builderTools.copycut.noSelection");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  40 */   private final FlagArg noEntitiesFlag = withFlagArg("noEntities", "server.commands.copy.noEntities.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  46 */   private final FlagArg entitiesOnlyFlag = withFlagArg("onlyEntities", "server.commands.copy.entitiesonly.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  52 */   private final FlagArg emptyFlag = withFlagArg("empty", "server.commands.copy.empty.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  58 */   private final FlagArg keepAnchorsFlag = withFlagArg("keepanchors", "server.commands.copy.keepanchors.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CopyCommand() {
/*  64 */     super("copy", "server.commands.copy.desc");
/*  65 */     setPermissionGroup(GameMode.Creative);
/*  66 */     requirePermission("hytale.editor.selection.clipboard");
/*  67 */     addUsageVariant((AbstractCommand)new CopyRegionCommand());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/*  76 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*  77 */     assert playerComponent != null;
/*     */     
/*  79 */     if (!PrototypePlayerBuilderToolSettings.isOkayToDoCommandsOnSelection(ref, playerComponent, (ComponentAccessor)store))
/*     */       return; 
/*  81 */     BuilderToolsPlugin.BuilderState builderState = BuilderToolsPlugin.getState(playerComponent, playerRef);
/*  82 */     boolean entitiesOnly = ((Boolean)this.entitiesOnlyFlag.get(context)).booleanValue();
/*  83 */     boolean noEntities = ((Boolean)this.noEntitiesFlag.get(context)).booleanValue();
/*     */     
/*  85 */     int settings = 0;
/*  86 */     if (!entitiesOnly) settings |= 0x8; 
/*  87 */     if (((Boolean)this.emptyFlag.get(context)).booleanValue()) settings |= 0x4; 
/*  88 */     if (((Boolean)this.keepAnchorsFlag.get(context)).booleanValue()) settings |= 0x40;
/*     */     
/*  90 */     if (!noEntities || entitiesOnly) settings |= 0x10;
/*     */     
/*  92 */     int settingsFinal = settings;
/*  93 */     BuilderToolsPlugin.addToQueue(playerComponent, playerRef, (r, s, componentAccessor) -> {
/*     */           try {
/*     */             BlockSelection selection = builderState.getSelection();
/*     */             
/*     */             if (selection == null || !selection.hasSelectionBounds()) {
/*     */               context.sendMessage(MESSAGE_BUILDER_TOOLS_COPY_CUT_NO_SELECTION);
/*     */               return;
/*     */             } 
/*     */             Vector3i min = selection.getSelectionMin();
/*     */             Vector3i max = selection.getSelectionMax();
/*     */             builderState.copyOrCut(r, min.getX(), min.getY(), min.getZ(), max.getX(), max.getY(), max.getZ(), settingsFinal, componentAccessor);
/* 104 */           } catch (PrefabCopyException e) {
/*     */             context.sendMessage(Message.translation("server.builderTools.copycut.copyFailedReason").param("reason", e.getMessage()));
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class CopyRegionCommand
/*     */     extends AbstractPlayerCommand
/*     */   {
/*     */     @Nonnull
/* 120 */     private final RequiredArg<Integer> xMinArg = withRequiredArg("xMin", "server.commands.copy.xMin.desc", (ArgumentType)ArgTypes.INTEGER);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 126 */     private final RequiredArg<Integer> yMinArg = withRequiredArg("yMin", "server.commands.copy.yMin.desc", (ArgumentType)ArgTypes.INTEGER);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 132 */     private final RequiredArg<Integer> zMinArg = withRequiredArg("zMin", "server.commands.copy.zMin.desc", (ArgumentType)ArgTypes.INTEGER);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 138 */     private final RequiredArg<Integer> xMaxArg = withRequiredArg("xMax", "server.commands.copy.xMax.desc", (ArgumentType)ArgTypes.INTEGER);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 144 */     private final RequiredArg<Integer> yMaxArg = withRequiredArg("yMax", "server.commands.copy.yMax.desc", (ArgumentType)ArgTypes.INTEGER);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 150 */     private final RequiredArg<Integer> zMaxArg = withRequiredArg("zMax", "server.commands.copy.zMax.desc", (ArgumentType)ArgTypes.INTEGER);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 156 */     private final FlagArg noEntitiesFlag = withFlagArg("noEntities", "server.commands.copy.noEntities.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 162 */     private final FlagArg entitiesOnlyFlag = withFlagArg("onlyEntities", "server.commands.copy.entitiesonly.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 168 */     private final FlagArg emptyFlag = withFlagArg("empty", "server.commands.copy.empty.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 174 */     private final FlagArg keepAnchorsFlag = withFlagArg("keepanchors", "server.commands.copy.keepanchors.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public CopyRegionCommand() {
/* 180 */       super("server.commands.copy.desc");
/*     */     }
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 185 */       Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 186 */       assert playerComponent != null;
/*     */       
/* 188 */       if (!PrototypePlayerBuilderToolSettings.isOkayToDoCommandsOnSelection(ref, playerComponent, (ComponentAccessor)store))
/*     */         return; 
/* 190 */       BuilderToolsPlugin.BuilderState builderState = BuilderToolsPlugin.getState(playerComponent, playerRef);
/* 191 */       boolean entitiesOnly = ((Boolean)this.entitiesOnlyFlag.get(context)).booleanValue();
/* 192 */       boolean noEntities = ((Boolean)this.noEntitiesFlag.get(context)).booleanValue();
/*     */       
/* 194 */       int settings = 0;
/* 195 */       if (!entitiesOnly) settings |= 0x8; 
/* 196 */       if (((Boolean)this.emptyFlag.get(context)).booleanValue()) settings |= 0x4; 
/* 197 */       if (((Boolean)this.keepAnchorsFlag.get(context)).booleanValue()) settings |= 0x40;
/*     */       
/* 199 */       if (!noEntities || entitiesOnly) settings |= 0x10;
/*     */       
/* 201 */       int xMin = ((Integer)this.xMinArg.get(context)).intValue();
/* 202 */       int yMin = ((Integer)this.yMinArg.get(context)).intValue();
/* 203 */       int zMin = ((Integer)this.zMinArg.get(context)).intValue();
/* 204 */       int xMax = ((Integer)this.xMaxArg.get(context)).intValue();
/* 205 */       int yMax = ((Integer)this.yMaxArg.get(context)).intValue();
/* 206 */       int zMax = ((Integer)this.zMaxArg.get(context)).intValue();
/*     */       
/* 208 */       int copySettings = settings;
/* 209 */       BuilderToolsPlugin.addToQueue(playerComponent, playerRef, (r, s, componentAccessor) -> {
/*     */             try {
/*     */               builderState.copyOrCut(r, xMin, yMin, zMin, xMax, yMax, zMax, copySettings, componentAccessor);
/* 212 */             } catch (PrefabCopyException e) {
/*     */               context.sendMessage(Message.translation("server.builderTools.copycut.copyFailedReason").param("reason", e.getMessage()));
/*     */               SoundUtil.playSoundEvent2d(r, TempAssetIdUtil.getSoundEventIndex("CREATE_ERROR"), SoundCategory.UI, componentAccessor);
/*     */             } 
/*     */           });
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void copySelection(@Nonnull Ref<EntityStore> ref, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 228 */     Player playerComponent = (Player)componentAccessor.getComponent(ref, Player.getComponentType());
/* 229 */     assert playerComponent != null;
/*     */     
/* 231 */     PlayerRef playerRefComponent = (PlayerRef)componentAccessor.getComponent(ref, PlayerRef.getComponentType());
/* 232 */     assert playerRefComponent != null;
/*     */     
/* 234 */     copySelection(ref, componentAccessor, BuilderToolsPlugin.getState(playerComponent, playerRefComponent), 24);
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
/*     */   public static void copySelection(@Nonnull Ref<EntityStore> ref, @Nonnull ComponentAccessor<EntityStore> componentAccessor, @Nonnull BuilderToolsPlugin.BuilderState builderState, int settings) {
/* 246 */     Player playerComponent = (Player)componentAccessor.getComponent(ref, Player.getComponentType());
/* 247 */     assert playerComponent != null;
/*     */     
/* 249 */     PlayerRef playerRefComponent = (PlayerRef)componentAccessor.getComponent(ref, PlayerRef.getComponentType());
/* 250 */     assert playerRefComponent != null;
/*     */     
/* 252 */     BuilderToolsPlugin.addToQueue(playerComponent, playerRefComponent, (r, s, c) -> {
/*     */           try {
/*     */             BlockSelection selection = builderState.getSelection();
/*     */             
/*     */             if (selection == null || !selection.hasSelectionBounds()) {
/*     */               playerComponent.sendMessage(MESSAGE_BUILDER_TOOLS_COPY_CUT_NO_SELECTION);
/*     */               return;
/*     */             } 
/*     */             Vector3i min = selection.getSelectionMin();
/*     */             Vector3i max = selection.getSelectionMax();
/*     */             builderState.copyOrCut(r, min.getX(), min.getY(), min.getZ(), max.getX(), max.getY(), max.getZ(), settings, c);
/* 263 */           } catch (PrefabCopyException e) {
/*     */             playerComponent.sendMessage(Message.translation("server.builderTools.copycut.copyFailedReason").param("reason", e.getMessage()));
/*     */           } 
/*     */         });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\commands\CopyCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */