/*     */ package com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.system;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfig;
/*     */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfigCommandExecutor;
/*     */ import com.hypixel.hytale.codec.lookup.CodecMapCodec;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Supplier;
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
/*     */ public abstract class BrushOperation
/*     */ {
/*  39 */   public static final CodecMapCodec<BrushOperation> OPERATION_CODEC = new CodecMapCodec("Id");
/*     */   
/*  41 */   public static final Map<String, Supplier<BrushOperation>> BRUSH_OPERATION_REGISTRY = new ConcurrentHashMap<>();
/*     */   private final String name;
/*     */   
/*     */   static {
/*  45 */     BRUSH_OPERATION_REGISTRY.put("dimensions", com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.dimensions.DimensionsOperation::new);
/*  46 */     BRUSH_OPERATION_REGISTRY.put("randomdimensions", com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.dimensions.RandomizeDimensionsOperation::new);
/*     */     
/*  48 */     BRUSH_OPERATION_REGISTRY.put("runcommand", com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.RunCommandOperation::new);
/*     */     
/*  50 */     BRUSH_OPERATION_REGISTRY.put("historymask", com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.masks.HistoryMaskOperation::new);
/*  51 */     BRUSH_OPERATION_REGISTRY.put("mask", com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.masks.MaskOperation::new);
/*  52 */     BRUSH_OPERATION_REGISTRY.put("clearoperationmask", com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.ClearOperationMaskOperation::new);
/*  53 */     BRUSH_OPERATION_REGISTRY.put("usebrushmask", com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.masks.UseBrushMaskOperation::new);
/*  54 */     BRUSH_OPERATION_REGISTRY.put("useoperationmask", com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.masks.UseOperationMaskOperation::new);
/*  55 */     BRUSH_OPERATION_REGISTRY.put("appendmask", com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.masks.AppendMaskOperation::new);
/*  56 */     BRUSH_OPERATION_REGISTRY.put("appendmaskfromtoolarg", com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.masks.AppendMaskFromToolArgOperation::new);
/*     */     
/*  58 */     BRUSH_OPERATION_REGISTRY.put("ignorebrushsettings", com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.global.IgnoreExistingBrushDataOperation::new);
/*     */     
/*  60 */     BRUSH_OPERATION_REGISTRY.put("debug", com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.global.DebugBrushOperation::new);
/*  61 */     BRUSH_OPERATION_REGISTRY.put("loop", com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.flowcontrol.loops.LoopOperation::new);
/*  62 */     BRUSH_OPERATION_REGISTRY.put("loadloop", com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.flowcontrol.loops.LoadLoopFromToolArgOperation::new);
/*  63 */     BRUSH_OPERATION_REGISTRY.put("looprandom", com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.flowcontrol.loops.LoopRandomOperation::new);
/*  64 */     BRUSH_OPERATION_REGISTRY.put("loopcircle", com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.flowcontrol.loops.CircleOffsetAndLoopOperation::new);
/*  65 */     BRUSH_OPERATION_REGISTRY.put("loopcirclefromarg", com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.flowcontrol.loops.CircleOffsetFromArgOperation::new);
/*     */     
/*  67 */     BRUSH_OPERATION_REGISTRY.put("savebrushconfig", com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.saveandload.SaveBrushConfigOperation::new);
/*  68 */     BRUSH_OPERATION_REGISTRY.put("loadbrushconfig", com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.saveandload.LoadBrushConfigOperation::new);
/*  69 */     BRUSH_OPERATION_REGISTRY.put("saveindex", com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.saveandload.SaveIndexOperation::new);
/*  70 */     BRUSH_OPERATION_REGISTRY.put("loadoperationsfromasset", com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.saveandload.LoadOperationsFromAssetOperation::new);
/*     */     
/*  72 */     BRUSH_OPERATION_REGISTRY.put("jump", com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.flowcontrol.JumpToIndexOperation::new);
/*  73 */     BRUSH_OPERATION_REGISTRY.put("exit", com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.flowcontrol.ExitOperation::new);
/*  74 */     BRUSH_OPERATION_REGISTRY.put("jumprandom", com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.flowcontrol.JumpToRandomIndex::new);
/*  75 */     BRUSH_OPERATION_REGISTRY.put("jumpifequal", com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.flowcontrol.JumpIfStringMatchOperation::new);
/*  76 */     BRUSH_OPERATION_REGISTRY.put("jumpifclicktype", com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.flowcontrol.JumpIfClickType::new);
/*  77 */     BRUSH_OPERATION_REGISTRY.put("jumpifcompare", com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.flowcontrol.JumpIfCompareOperation::new);
/*  78 */     BRUSH_OPERATION_REGISTRY.put("jumpifblocktype", com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.flowcontrol.JumpIfBlockTypeOperation::new);
/*  79 */     BRUSH_OPERATION_REGISTRY.put("jumpiftoolarg", com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.flowcontrol.JumpIfToolArgOperation::new);
/*     */     
/*  81 */     BRUSH_OPERATION_REGISTRY.put("pattern", com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.BlockPatternOperation::new);
/*  82 */     BRUSH_OPERATION_REGISTRY.put("loadmaterial", com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.LoadMaterialFromToolArgOperation::new);
/*  83 */     BRUSH_OPERATION_REGISTRY.put("loadint", com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.LoadIntFromToolArgOperation::new);
/*  84 */     BRUSH_OPERATION_REGISTRY.put("lift", com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.LiftOperation::new);
/*  85 */     BRUSH_OPERATION_REGISTRY.put("density", com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.SetDensity::new);
/*  86 */     BRUSH_OPERATION_REGISTRY.put("set", com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.SetOperation::new);
/*  87 */     BRUSH_OPERATION_REGISTRY.put("smooth", com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.SmoothOperation::new);
/*  88 */     BRUSH_OPERATION_REGISTRY.put("shape", com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.ShapeOperation::new);
/*  89 */     BRUSH_OPERATION_REGISTRY.put("offset", com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.offsets.OffsetOperation::new);
/*  90 */     BRUSH_OPERATION_REGISTRY.put("layer", com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.LayerOperation::new);
/*  91 */     BRUSH_OPERATION_REGISTRY.put("heightmaplayer", com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.HeightmapLayerOperation::new);
/*  92 */     BRUSH_OPERATION_REGISTRY.put("melt", com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.MeltOperation::new);
/*  93 */     BRUSH_OPERATION_REGISTRY.put("material", com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.MaterialOperation::new);
/*  94 */     BRUSH_OPERATION_REGISTRY.put("delete", com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.DeleteOperation::new);
/*  95 */     BRUSH_OPERATION_REGISTRY.put("disableonhold", com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.global.DisableHoldInteractionOperation::new);
/*  96 */     BRUSH_OPERATION_REGISTRY.put("randomoffset", com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.offsets.RandomOffsetOperation::new);
/*     */     
/*  98 */     BRUSH_OPERATION_REGISTRY.put("erode", com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.ErodeOperation::new);
/*     */     
/* 100 */     BRUSH_OPERATION_REGISTRY.put("persistentdata", com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.saveandload.PersistentDataOperation::new);
/*     */     
/* 102 */     BRUSH_OPERATION_REGISTRY.put("pasteprefab", com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.PastePrefabOperation::new);
/* 103 */     BRUSH_OPERATION_REGISTRY.put("echo", com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.EchoOperation::new);
/* 104 */     BRUSH_OPERATION_REGISTRY.put("echoonce", com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.EchoOnceOperation::new);
/* 105 */     BRUSH_OPERATION_REGISTRY.put("replace", com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.ReplaceOperation::new);
/* 106 */     BRUSH_OPERATION_REGISTRY.put("breakpoint", com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.BreakpointOperation::new);
/*     */   }
/*     */ 
/*     */   
/*     */   private final String description;
/* 111 */   private final Map<String, BrushOperationSetting<?>> registeredOperationSettings = new LinkedHashMap<>();
/*     */   
/*     */   public BrushOperation(String name, String description) {
/* 114 */     this.name = name;
/* 115 */     this.description = description;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetInternalState() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void preExecutionModifyBrushConfig(BrushConfigCommandExecutor brushConfigCommandExecutor, int operationIndex) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public <T> BrushOperationSetting<T> createBrushSetting(@Nonnull String name, String description, T defaultValue, ArgumentType<T> argumentType) {
/* 136 */     BrushOperationSetting<T> brushOperationSetting = new BrushOperationSetting<>(name, description, defaultValue, argumentType);
/* 137 */     this.registeredOperationSettings.put(name.toLowerCase(), brushOperationSetting);
/* 138 */     return brushOperationSetting;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public <T> BrushOperationSetting<T> createBrushSetting(@Nonnull String name, String description, T defaultValue, ArgumentType<T> argumentType, Function<BrushOperationSetting<T>, String> toStringFunction) {
/* 143 */     BrushOperationSetting<T> brushOperationSetting = new BrushOperationSetting<>(name, description, defaultValue, argumentType, toStringFunction);
/* 144 */     this.registeredOperationSettings.put(name.toLowerCase(), brushOperationSetting);
/* 145 */     return brushOperationSetting;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 149 */     return this.name;
/*     */   }
/*     */   
/*     */   public String getDescription() {
/* 153 */     return this.description;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Map<String, BrushOperationSetting<?>> getRegisteredOperationSettings() {
/* 158 */     return this.registeredOperationSettings;
/*     */   }
/*     */   
/*     */   public abstract void modifyBrushConfig(@Nonnull Ref<EntityStore> paramRef, @Nonnull BrushConfig paramBrushConfig, @Nonnull BrushConfigCommandExecutor paramBrushConfigCommandExecutor, @Nonnull ComponentAccessor<EntityStore> paramComponentAccessor);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\scriptedbrushes\operations\system\BrushOperation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */