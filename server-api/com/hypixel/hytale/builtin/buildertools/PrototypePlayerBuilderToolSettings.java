/*     */ package com.hypixel.hytale.builtin.buildertools;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfig;
/*     */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfigCommandExecutor;
/*     */ import com.hypixel.hytale.builtin.buildertools.tooloperations.ToolOperation;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.math.block.BlockUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.protocol.packets.interface_.BlockChange;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ public class PrototypePlayerBuilderToolSettings
/*     */ {
/*     */   @Nonnull
/*  25 */   private static final Message MESSAGE_BUILDER_TOOLS_CANNOT_PERFORM_COMMAND_IN_TRANSFORMATION_MODE = Message.translation("server.builderTools.cannotPerformCommandInTransformationMode");
/*     */ 
/*     */   
/*     */   private final UUID player;
/*     */ 
/*     */   
/*  31 */   private final LinkedList<LongOpenHashSet> ignoredPaintOperations = new LinkedList<>();
/*     */   
/*     */   private int maxLengthOfIgnoredPaintOperations;
/*     */   
/*     */   private boolean shouldShowEditorSettings;
/*     */   
/*     */   private boolean isLoadingBrush;
/*     */   
/*     */   private boolean usePrototypeBrushConfigurations;
/*  40 */   private String currentlyLoadedBrushConfigName = "";
/*  41 */   private BrushConfig brushConfig = new BrushConfig();
/*     */   
/*     */   private BrushConfigCommandExecutor brushConfigCommandExecutor;
/*     */   private boolean isInSelectionTransformationMode = false;
/*     */   @Nullable
/*  46 */   private BlockChange[] blockChangesForPlaySelectionToolPasteMode = null;
/*     */   
/*     */   @Nullable
/*  49 */   private FluidChange[] fluidChangesForPlaySelectionToolPasteMode = null;
/*     */   public static final class FluidChange extends Record { private final int x; private final int y;
/*     */     private final int z;
/*     */     private final int fluidId;
/*     */     private final byte fluidLevel;
/*     */     
/*  55 */     public FluidChange(int x, int y, int z, int fluidId, byte fluidLevel) { this.x = x; this.y = y; this.z = z; this.fluidId = fluidId; this.fluidLevel = fluidLevel; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/builtin/buildertools/PrototypePlayerBuilderToolSettings$FluidChange;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #55	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*  55 */       //   0	7	0	this	Lcom/hypixel/hytale/builtin/buildertools/PrototypePlayerBuilderToolSettings$FluidChange; } public int x() { return this.x; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/builtin/buildertools/PrototypePlayerBuilderToolSettings$FluidChange;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #55	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/builtin/buildertools/PrototypePlayerBuilderToolSettings$FluidChange; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/builtin/buildertools/PrototypePlayerBuilderToolSettings$FluidChange;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #55	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lcom/hypixel/hytale/builtin/buildertools/PrototypePlayerBuilderToolSettings$FluidChange;
/*  55 */       //   0	8	1	o	Ljava/lang/Object; } public int y() { return this.y; } public int z() { return this.z; } public int fluidId() { return this.fluidId; } public byte fluidLevel() { return this.fluidLevel; }
/*     */      }
/*     */   @Nullable
/*  58 */   private Vector3i lastBrushPosition = null;
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*  63 */   private Vector3i blockChangeOffsetOrigin = null;
/*     */ 
/*     */   
/*     */   public PrototypePlayerBuilderToolSettings(UUID player) {
/*  67 */     this.player = player;
/*  68 */     this.brushConfigCommandExecutor = new BrushConfigCommandExecutor(this.brushConfig);
/*     */   }
/*     */   
/*     */   public UUID getPlayer() {
/*  72 */     return this.player;
/*     */   }
/*     */   
/*     */   public boolean isInSelectionTransformationMode() {
/*  76 */     return this.isInSelectionTransformationMode;
/*     */   }
/*     */   
/*     */   public void setInSelectionTransformationMode(boolean inSelectionTransformationMode) {
/*  80 */     this.isInSelectionTransformationMode = inSelectionTransformationMode;
/*     */     
/*  82 */     if (!this.isInSelectionTransformationMode) {
/*  83 */       this.blockChangesForPlaySelectionToolPasteMode = null;
/*  84 */       this.fluidChangesForPlaySelectionToolPasteMode = null;
/*  85 */       this.blockChangeOffsetOrigin = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setBlockChangesForPlaySelectionToolPasteMode(@Nullable BlockChange[] blockChangesForPlaySelectionToolPasteMode) {
/*  90 */     this.blockChangesForPlaySelectionToolPasteMode = blockChangesForPlaySelectionToolPasteMode;
/*     */   }
/*     */   
/*     */   public String getCurrentlyLoadedBrushConfigName() {
/*  94 */     return this.currentlyLoadedBrushConfigName;
/*     */   }
/*     */   
/*     */   public void setCurrentlyLoadedBrushConfigName(String currentlyLoadedBrushConfigName) {
/*  98 */     this.currentlyLoadedBrushConfigName = currentlyLoadedBrushConfigName;
/*     */   }
/*     */   
/*     */   public boolean isLoadingBrush() {
/* 102 */     return this.isLoadingBrush;
/*     */   }
/*     */   
/*     */   public void setLoadingBrush(boolean loadingBrush) {
/* 106 */     this.isLoadingBrush = loadingBrush;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public BlockChange[] getBlockChangesForPlaySelectionToolPasteMode() {
/* 111 */     return this.blockChangesForPlaySelectionToolPasteMode;
/*     */   }
/*     */   
/*     */   public void setFluidChangesForPlaySelectionToolPasteMode(@Nullable FluidChange[] fluidChanges) {
/* 115 */     this.fluidChangesForPlaySelectionToolPasteMode = fluidChanges;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public FluidChange[] getFluidChangesForPlaySelectionToolPasteMode() {
/* 120 */     return this.fluidChangesForPlaySelectionToolPasteMode;
/*     */   }
/*     */   
/*     */   public void setBlockChangeOffsetOrigin(@Nullable Vector3i blockChangeOffsetOrigin) {
/* 124 */     this.blockChangeOffsetOrigin = blockChangeOffsetOrigin;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Vector3i getBlockChangeOffsetOrigin() {
/* 129 */     return this.blockChangeOffsetOrigin;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public LongOpenHashSet addIgnoredPaintOperation() {
/* 134 */     LongOpenHashSet longs = new LongOpenHashSet();
/* 135 */     this.ignoredPaintOperations.add(longs);
/* 136 */     clearHistoryUntilFitMaxLength();
/* 137 */     return longs;
/*     */   }
/*     */   
/*     */   public void clearHistoryUntilFitMaxLength() {
/* 141 */     while (this.ignoredPaintOperations.size() > this.maxLengthOfIgnoredPaintOperations) {
/* 142 */       this.ignoredPaintOperations.removeFirst();
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean containsLocation(int x, int y, int z) {
/* 147 */     long packedBlockLocation = BlockUtil.pack(x, y, z);
/* 148 */     for (LongOpenHashSet locations : this.ignoredPaintOperations) {
/* 149 */       if (locations.contains(packedBlockLocation)) return true; 
/*     */     } 
/* 151 */     return false;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public LinkedList<LongOpenHashSet> getIgnoredPaintOperations() {
/* 156 */     return this.ignoredPaintOperations;
/*     */   }
/*     */   
/*     */   public int getMaxLengthOfIgnoredPaintOperations() {
/* 160 */     return this.maxLengthOfIgnoredPaintOperations;
/*     */   }
/*     */   
/*     */   public void setMaxLengthOfIgnoredPaintOperations(int maxLengthOfIgnoredPaintOperations) {
/* 164 */     this.maxLengthOfIgnoredPaintOperations = maxLengthOfIgnoredPaintOperations;
/* 165 */     clearHistoryUntilFitMaxLength();
/*     */   }
/*     */   
/*     */   public boolean usePrototypeBrushConfigurations() {
/* 169 */     return this.usePrototypeBrushConfigurations;
/*     */   }
/*     */   
/*     */   public void setUsePrototypeBrushConfigurations(boolean usePrototypeBrushConfigurations) {
/* 173 */     this.usePrototypeBrushConfigurations = usePrototypeBrushConfigurations;
/*     */   }
/*     */   
/*     */   public BrushConfig getBrushConfig() {
/* 177 */     return this.brushConfig;
/*     */   }
/*     */   
/*     */   public BrushConfigCommandExecutor getBrushConfigCommandExecutor() {
/* 181 */     return this.brushConfigCommandExecutor;
/*     */   }
/*     */   
/*     */   public void setBrushConfig(BrushConfig brushConfig) {
/* 185 */     this.brushConfig = brushConfig;
/*     */   }
/*     */   
/*     */   public boolean isShouldShowEditorSettings() {
/* 189 */     return this.shouldShowEditorSettings;
/*     */   }
/*     */   
/*     */   public void setShouldShowEditorSettings(boolean shouldShowEditorSettings) {
/* 193 */     this.shouldShowEditorSettings = shouldShowEditorSettings;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Vector3i getLastBrushPosition() {
/* 198 */     return this.lastBrushPosition;
/*     */   }
/*     */   
/*     */   public void setLastBrushPosition(@Nullable Vector3i lastBrushPosition) {
/* 202 */     this.lastBrushPosition = lastBrushPosition;
/*     */   }
/*     */   
/*     */   public void clearLastBrushPosition() {
/* 206 */     this.lastBrushPosition = null;
/*     */   }
/*     */   
/*     */   public static boolean isOkayToDoCommandsOnSelection(Ref<EntityStore> ref, @Nonnull Player player, ComponentAccessor<EntityStore> componentAccessor) {
/* 210 */     UUIDComponent uuidComponent = (UUIDComponent)componentAccessor.getComponent(ref, UUIDComponent.getComponentType());
/* 211 */     assert uuidComponent != null;
/*     */     
/* 213 */     PrototypePlayerBuilderToolSettings prototypeSettings = ToolOperation.getOrCreatePrototypeSettings(uuidComponent.getUuid());
/* 214 */     if (prototypeSettings.isInSelectionTransformationMode()) {
/* 215 */       player.sendMessage(MESSAGE_BUILDER_TOOLS_CANNOT_PERFORM_COMMAND_IN_TRANSFORMATION_MODE);
/* 216 */       return false;
/*     */     } 
/* 218 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\PrototypePlayerBuilderToolSettings.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */