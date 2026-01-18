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
/*     */ public class CutCommand
/*     */   extends AbstractPlayerCommand
/*     */ {
/*     */   @Nonnull
/*  33 */   private static final Message MESSAGE_BUILDER_TOOLS_COPY_CUT_NO_SELECTION = Message.translation("server.builderTools.copycut.noSelection");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  39 */   private final FlagArg noEntitiesFlag = withFlagArg("noEntities", "server.commands.cut.noEntities.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  45 */   private final FlagArg entitiesOnlyFlag = withFlagArg("onlyEntities", "server.commands.cut.entitiesonly.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  51 */   private final FlagArg emptyFlag = withFlagArg("empty", "server.commands.cut.empty.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  57 */   private final FlagArg keepAnchorsFlag = withFlagArg("keepanchors", "server.commands.cut.keepanchors.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CutCommand() {
/*  63 */     super("cut", "server.commands.cut.desc");
/*  64 */     setPermissionGroup(GameMode.Creative);
/*  65 */     requirePermission("hytale.editor.selection.clipboard");
/*  66 */     addUsageVariant((AbstractCommand)new CutRegionCommand());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/*  75 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*  76 */     assert playerComponent != null;
/*     */     
/*  78 */     if (!PrototypePlayerBuilderToolSettings.isOkayToDoCommandsOnSelection(ref, playerComponent, (ComponentAccessor)store))
/*     */       return; 
/*  80 */     BuilderToolsPlugin.BuilderState builderState = BuilderToolsPlugin.getState(playerComponent, playerRef);
/*  81 */     boolean entitiesOnly = ((Boolean)this.entitiesOnlyFlag.get(context)).booleanValue();
/*  82 */     boolean noEntities = ((Boolean)this.noEntitiesFlag.get(context)).booleanValue();
/*     */     
/*  84 */     int settings = 2;
/*  85 */     if (!entitiesOnly) settings |= 0x8; 
/*  86 */     if (((Boolean)this.emptyFlag.get(context)).booleanValue()) settings |= 0x4; 
/*  87 */     if (((Boolean)this.keepAnchorsFlag.get(context)).booleanValue()) settings |= 0x40;
/*     */     
/*  89 */     if (!noEntities || entitiesOnly) settings |= 0x10;
/*     */     
/*  91 */     int settingsFinal = settings;
/*  92 */     BuilderToolsPlugin.addToQueue(playerComponent, playerRef, (r, s, componentAccessor) -> {
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
/* 103 */           } catch (PrefabCopyException e) {
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
/*     */   private static class CutRegionCommand
/*     */     extends AbstractPlayerCommand
/*     */   {
/*     */     @Nonnull
/* 119 */     private final RequiredArg<Integer> xMinArg = withRequiredArg("xMin", "server.commands.cut.xMin.desc", (ArgumentType)ArgTypes.INTEGER);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 125 */     private final RequiredArg<Integer> yMinArg = withRequiredArg("yMin", "server.commands.cut.yMin.desc", (ArgumentType)ArgTypes.INTEGER);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 131 */     private final RequiredArg<Integer> zMinArg = withRequiredArg("zMin", "server.commands.cut.zMin.desc", (ArgumentType)ArgTypes.INTEGER);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 137 */     private final RequiredArg<Integer> xMaxArg = withRequiredArg("xMax", "server.commands.cut.xMax.desc", (ArgumentType)ArgTypes.INTEGER);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 143 */     private final RequiredArg<Integer> yMaxArg = withRequiredArg("yMax", "server.commands.cut.yMax.desc", (ArgumentType)ArgTypes.INTEGER);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 149 */     private final RequiredArg<Integer> zMaxArg = withRequiredArg("zMax", "server.commands.cut.zMax.desc", (ArgumentType)ArgTypes.INTEGER);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 155 */     private final FlagArg noEntitiesFlag = withFlagArg("noEntities", "server.commands.cut.noEntities.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 161 */     private final FlagArg entitiesOnlyFlag = withFlagArg("onlyEntities", "server.commands.cut.entitiesonly.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 167 */     private final FlagArg emptyFlag = withFlagArg("empty", "server.commands.cut.empty.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 173 */     private final FlagArg keepAnchorsFlag = withFlagArg("keepanchors", "server.commands.cut.keepanchors.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public CutRegionCommand() {
/* 179 */       super("server.commands.cut.desc");
/*     */     }
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 184 */       Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 185 */       assert playerComponent != null;
/*     */       
/* 187 */       if (!PrototypePlayerBuilderToolSettings.isOkayToDoCommandsOnSelection(ref, playerComponent, (ComponentAccessor)store))
/*     */         return; 
/* 189 */       BuilderToolsPlugin.BuilderState builderState = BuilderToolsPlugin.getState(playerComponent, playerRef);
/* 190 */       boolean entitiesOnly = ((Boolean)this.entitiesOnlyFlag.get(context)).booleanValue();
/* 191 */       boolean noEntities = ((Boolean)this.noEntitiesFlag.get(context)).booleanValue();
/*     */       
/* 193 */       int settings = 2;
/* 194 */       if (!entitiesOnly) settings |= 0x8; 
/* 195 */       if (((Boolean)this.emptyFlag.get(context)).booleanValue()) settings |= 0x4; 
/* 196 */       if (((Boolean)this.keepAnchorsFlag.get(context)).booleanValue()) settings |= 0x40;
/*     */       
/* 198 */       if (!noEntities || entitiesOnly) settings |= 0x10;
/*     */       
/* 200 */       int xMin = ((Integer)this.xMinArg.get(context)).intValue();
/* 201 */       int yMin = ((Integer)this.yMinArg.get(context)).intValue();
/* 202 */       int zMin = ((Integer)this.zMinArg.get(context)).intValue();
/* 203 */       int xMax = ((Integer)this.xMaxArg.get(context)).intValue();
/* 204 */       int yMax = ((Integer)this.yMaxArg.get(context)).intValue();
/* 205 */       int zMax = ((Integer)this.zMaxArg.get(context)).intValue();
/*     */       
/* 207 */       int cutSettings = settings;
/* 208 */       BuilderToolsPlugin.addToQueue(playerComponent, playerRef, (r, s, componentAccessor) -> {
/*     */             try {
/*     */               builderState.copyOrCut(r, xMin, yMin, zMin, xMax, yMax, zMax, cutSettings, componentAccessor);
/* 211 */             } catch (PrefabCopyException e) {
/*     */               context.sendMessage(Message.translation("server.builderTools.copycut.copyFailedReason").param("reason", e.getMessage()));
/*     */               SoundUtil.playSoundEvent2d(r, TempAssetIdUtil.getSoundEventIndex("CREATE_ERROR"), SoundCategory.UI, componentAccessor);
/*     */             } 
/*     */           });
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\commands\CutCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */