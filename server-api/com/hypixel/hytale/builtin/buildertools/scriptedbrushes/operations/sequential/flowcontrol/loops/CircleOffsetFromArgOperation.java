/*     */ package com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.flowcontrol.loops;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfig;
/*     */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfigCommandExecutor;
/*     */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.system.SequenceBrushOperation;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.server.core.asset.type.buildertool.config.BuilderTool;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public class CircleOffsetFromArgOperation
/*     */   extends SequenceBrushOperation
/*     */ {
/*     */   public static final int MAX_REPETITIONS = 100;
/*     */   public static final int IDLE_STATE = -1;
/*     */   public static final double TWO_PI = 6.283185307179586D;
/*     */   public static final BuilderCodec<CircleOffsetFromArgOperation> CODEC;
/*     */   
/*     */   static {
/*  62 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(CircleOffsetFromArgOperation.class, CircleOffsetFromArgOperation::new).append(new KeyedCodec("StoredIndexName", (Codec)Codec.STRING), (op, val) -> op.indexNameArg = val, op -> op.indexNameArg).documentation("The name of the previously stored index to begin the loop at. Note: This can only be an index previous to the current.").add()).append(new KeyedCodec("NumberCirclePointsArg", (Codec)Codec.STRING, true), (op, val) -> op.numCirclePointsArg = val, op -> op.numCirclePointsArg).documentation("The name of the Int tool arg to load the value from").add()).append(new KeyedCodec("CircleRadiusArg", (Codec)Codec.STRING, true), (op, val) -> op.circleRadiusArg = val, op -> op.circleRadiusArg).documentation("The name of the Int tool arg to load the value from").add()).append(new KeyedCodec("FlipDirection", (Codec)Codec.BOOLEAN, true), (op, val) -> op.flipArg = val.booleanValue(), op -> Boolean.valueOf(op.flipArg)).documentation("Whether to invert the direction of the circle. Useful for non-zero offset modifiers.").add()).append(new KeyedCodec("RotateDirection", (Codec)Codec.BOOLEAN, true), (op, val) -> op.rotateArg = val.booleanValue(), op -> Boolean.valueOf(op.rotateArg)).documentation("Whether to invert the direction of the circle. Useful for non-zero offset modifiers.").add()).build();
/*     */   } @Nonnull
/*  64 */   public String indexNameArg = "Undefined";
/*     */ 
/*     */   
/*  67 */   public String numCirclePointsArg = "";
/*  68 */   private int numCirclePointsArgVal = 3;
/*  69 */   public String circleRadiusArg = "";
/*  70 */   private int circleRadiusArgVal = 5;
/*     */   
/*  72 */   private int previousCirclePointsVal = 3;
/*  73 */   private int previousCircleRadiusVal = 5;
/*     */   
/*     */   public boolean flipArg = false;
/*     */   
/*     */   public boolean rotateArg = false;
/*  78 */   private int repetitionsRemaining = -1;
/*     */   @Nonnull
/*  80 */   private List<Vector3i> offsetsInCircle = (List<Vector3i>)new ObjectArrayList();
/*     */   @Nonnull
/*  82 */   private Vector3i offsetWhenFirstReachedOperation = Vector3i.ZERO;
/*     */   @Nonnull
/*  84 */   private Vector3i previousCircleOffset = Vector3i.ZERO;
/*     */ 
/*     */   
/*     */   public CircleOffsetFromArgOperation() {
/*  88 */     super("Loop Previous And Set Offset In Circle", "Loops specified instructions and changes the offset after each loop in order to execute around a circle", false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void resetInternalState() {
/*  93 */     this.repetitionsRemaining = -1;
/*  94 */     this.offsetsInCircle.clear();
/*     */     
/*  96 */     this.offsetWhenFirstReachedOperation = Vector3i.ZERO;
/*  97 */     this.previousCircleOffset = Vector3i.ZERO;
/*     */     
/*  99 */     int numPointsOnCircle = this.numCirclePointsArgVal;
/* 100 */     int circleRadius = this.circleRadiusArgVal;
/*     */ 
/*     */     
/* 103 */     double theta = 6.283185307179586D / numPointsOnCircle;
/*     */     
/* 105 */     for (int i = 0; i < numPointsOnCircle; i++) {
/*     */       
/* 107 */       if (this.rotateArg) {
/* 108 */         this.offsetsInCircle.add(new Vector3i(
/* 109 */               doubleToNearestInt(circleRadius * Math.cos(theta * i)) * -1, 0, 
/*     */               
/* 111 */               doubleToNearestInt(circleRadius * Math.sin(theta * i)) * -1));
/*     */       }
/*     */       else {
/*     */         
/* 115 */         this.offsetsInCircle.add(new Vector3i(
/* 116 */               doubleToNearestInt(circleRadius * Math.cos(theta * i)), 0, 
/*     */               
/* 118 */               doubleToNearestInt(circleRadius * Math.sin(theta * i))));
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 124 */     if (this.flipArg) {
/* 125 */       this.offsetsInCircle = this.offsetsInCircle.reversed();
/*     */     }
/*     */   }
/*     */   
/*     */   private int doubleToNearestInt(double number) {
/* 130 */     return (int)Math.floor(number + 0.5D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void modifyBrushConfig(@Nonnull Ref<EntityStore> ref, @Nonnull BrushConfig brushConfig, @Nonnull BrushConfigCommandExecutor brushConfigCommandExecutor, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 137 */     if (this.repetitionsRemaining == -1) {
/*     */       
/* 139 */       Player playerComponent = (Player)componentAccessor.getComponent(ref, Player.getComponentType());
/* 140 */       assert playerComponent != null;
/*     */       
/* 142 */       BuilderTool builderTool = BuilderTool.getActiveBuilderTool(playerComponent);
/* 143 */       if (builderTool == null) {
/* 144 */         brushConfig.setErrorFlag("CircleOffsetFromArg: No active builder tool");
/*     */         
/*     */         return;
/*     */       } 
/* 148 */       ItemStack itemStack = playerComponent.getInventory().getItemInHand();
/* 149 */       if (itemStack == null) {
/* 150 */         brushConfig.setErrorFlag("CircleOffsetFromArg: No item in hand");
/*     */         
/*     */         return;
/*     */       } 
/* 154 */       BuilderTool.ArgData argData = builderTool.getItemArgData(itemStack);
/* 155 */       Map<String, Object> toolArgs = argData.tool();
/*     */       
/* 157 */       if (toolArgs == null || !toolArgs.containsKey(this.numCirclePointsArg)) {
/* 158 */         brushConfig.setErrorFlag("CircleOffsetFromArg: Tool arg '" + this.numCirclePointsArg + "' not found");
/*     */         return;
/*     */       } 
/* 161 */       if (toolArgs == null || !toolArgs.containsKey(this.numCirclePointsArg)) {
/* 162 */         brushConfig.setErrorFlag("CircleOffsetFromArg: Tool arg '" + this.numCirclePointsArg + "' not found");
/*     */         
/*     */         return;
/*     */       } 
/* 166 */       Object numCirclePointsArgValue = toolArgs.get(this.numCirclePointsArg);
/* 167 */       Object circleRadiusArgValue = toolArgs.get(this.circleRadiusArg);
/*     */       
/* 169 */       if (!(numCirclePointsArgValue instanceof Integer)) {
/* 170 */         brushConfig.setErrorFlag("LoadCircleLoop: Tool arg '" + this.numCirclePointsArg + "' is not an Int type (found " + numCirclePointsArgValue.getClass().getSimpleName() + ")");
/*     */         return;
/*     */       } 
/* 173 */       if (!(circleRadiusArgValue instanceof Integer)) {
/* 174 */         brushConfig.setErrorFlag("LoadCircleLoop: Tool arg '" + this.circleRadiusArg + "' is not an Int type (found " + circleRadiusArgValue.getClass().getSimpleName() + ")");
/*     */         
/*     */         return;
/*     */       } 
/* 178 */       this.numCirclePointsArgVal = ((Integer)numCirclePointsArgValue).intValue();
/* 179 */       this.circleRadiusArgVal = ((Integer)circleRadiusArgValue).intValue();
/*     */       
/* 181 */       this.previousCirclePointsVal = this.numCirclePointsArgVal;
/* 182 */       this.previousCircleRadiusVal = this.circleRadiusArgVal;
/*     */ 
/*     */       
/* 185 */       resetInternalState();
/*     */       
/* 187 */       this.offsetWhenFirstReachedOperation = brushConfig.getOriginOffset();
/* 188 */       if (this.numCirclePointsArgVal > 100) {
/* 189 */         brushConfig.setErrorFlag("Cannot have more than 100 repetitions");
/*     */         return;
/*     */       } 
/* 192 */       this.repetitionsRemaining = this.numCirclePointsArgVal;
/*     */     } 
/*     */ 
/*     */     
/* 196 */     if (this.repetitionsRemaining == 0) {
/* 197 */       this.repetitionsRemaining = -1;
/*     */       
/* 199 */       brushConfig.setOriginOffset(this.offsetWhenFirstReachedOperation);
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 205 */     Vector3i offsetVector = brushConfig.getOriginOffset().subtract(this.previousCircleOffset).add(((Vector3i)this.offsetsInCircle.get(this.repetitionsRemaining - 1)).clone());
/* 206 */     this.previousCircleOffset = ((Vector3i)this.offsetsInCircle.get(this.repetitionsRemaining - 1)).clone();
/*     */     
/* 208 */     brushConfig.setOriginOffset(offsetVector);
/* 209 */     brushConfigCommandExecutor.loadOperatingIndex(this.indexNameArg, false);
/*     */     
/* 211 */     this.repetitionsRemaining--;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\scriptedbrushes\operations\sequential\flowcontrol\loops\CircleOffsetFromArgOperation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */