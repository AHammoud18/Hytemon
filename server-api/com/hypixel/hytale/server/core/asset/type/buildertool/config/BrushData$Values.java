/*     */ package com.hypixel.hytale.server.core.asset.type.buildertool.config;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.common.util.ArrayUtil;
/*     */ import com.hypixel.hytale.protocol.Rotation;
/*     */ import com.hypixel.hytale.protocol.packets.buildertools.BrushAxis;
/*     */ import com.hypixel.hytale.protocol.packets.buildertools.BrushOrigin;
/*     */ import com.hypixel.hytale.protocol.packets.buildertools.BrushShape;
/*     */ import com.hypixel.hytale.server.core.asset.type.buildertool.config.args.BlockArg;
/*     */ import com.hypixel.hytale.server.core.asset.type.buildertool.config.args.BrushAxisArg;
/*     */ import com.hypixel.hytale.server.core.asset.type.buildertool.config.args.BrushOriginArg;
/*     */ import com.hypixel.hytale.server.core.asset.type.buildertool.config.args.BrushRotationArg;
/*     */ import com.hypixel.hytale.server.core.asset.type.buildertool.config.args.BrushShapeArg;
/*     */ import com.hypixel.hytale.server.core.asset.type.buildertool.config.args.StringArg;
/*     */ import com.hypixel.hytale.server.core.asset.type.buildertool.config.args.ToolArg;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.mask.BlockMask;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.mask.BlockPattern;
/*     */ import java.util.Arrays;
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
/*     */ 
/*     */ public class Values
/*     */ {
/*     */   public static final Codec<Values> CODEC;
/*     */   private int width;
/*     */   private int height;
/*     */   private int thickness;
/*     */   private boolean capped;
/*     */   private BrushShape shape;
/*     */   private BrushOrigin origin;
/*     */   private boolean originRotation;
/*     */   private BrushAxis rotationAxis;
/*     */   private Rotation rotationAngle;
/*     */   private BrushAxis mirrorAxis;
/*     */   private BlockPattern material;
/*     */   private BlockPattern[] favoriteMaterials;
/*     */   private BlockMask mask;
/*     */   private BlockMask maskAbove;
/*     */   private BlockMask maskNot;
/*     */   private BlockMask maskBelow;
/*     */   private BlockMask maskAdjacent;
/*     */   private BlockMask maskNeighbor;
/*     */   private String[] maskCommands;
/*     */   private boolean useMaskCommands;
/*     */   private boolean invertMask;
/*     */   
/*     */   static {
/* 454 */     CODEC = (Codec<Values>)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(Values.class, Values::new).append(new KeyedCodec("Width", (Codec)Codec.INTEGER), (brushData, o) -> brushData.width = o.intValue(), brushData -> Integer.valueOf(brushData.width)).addValidator(Validators.greaterThan(Integer.valueOf(0))).documentation("The width of the brush shape").add()).append(new KeyedCodec("Height", (Codec)Codec.INTEGER), (brushData, o) -> brushData.height = o.intValue(), brushData -> Integer.valueOf(brushData.height)).addValidator(Validators.greaterThan(Integer.valueOf(0))).documentation("The height of the brush shape").add()).append(new KeyedCodec("Thickness", (Codec)Codec.INTEGER), (data, o) -> data.thickness = o.intValue(), data -> Integer.valueOf(data.thickness)).addValidator(Validators.range(Integer.valueOf(0), Integer.valueOf(100))).documentation("The number of blocks thick the walls of the brush shape should be").add()).append(new KeyedCodec("Capped", (Codec)Codec.BOOLEAN), (data, o) -> data.capped = o.booleanValue(), data -> Boolean.valueOf(data.capped)).addValidator(Validators.nonNull()).documentation("Controls whether the end(s) of hollow brush shapes are closed or open").add()).append(new KeyedCodec("Shape", (Codec)BrushShapeArg.BRUSH_SHAPE_CODEC), (brushData, o) -> brushData.shape = o, brushData -> brushData.shape).addValidator(Validators.nonNull()).documentation("The brush shape").add()).append(new KeyedCodec("Origin", (Codec)BrushOriginArg.BRUSH_ORIGIN_CODEC), (brushData, o) -> brushData.origin = o, brushData -> brushData.origin).addValidator(Validators.nonNull()).documentation("The origin of the brush shape").add()).append(new KeyedCodec("OriginRotation", (Codec)Codec.BOOLEAN), (data, o) -> data.originRotation = o.booleanValue(), data -> Boolean.valueOf(data.originRotation)).addValidator(Validators.nonNull()).documentation("Toggles the vertical offset for shapes rotated about the x/z axis").add()).append(new KeyedCodec("RotationAxis", BrushAxisArg.BRUSH_AXIS_CODEC), (data, o) -> data.rotationAxis = o, data -> data.rotationAxis).addValidator(Validators.nonNull()).documentation("The axis that the brush shape should rotate around").add()).append(new KeyedCodec("RotationAngle", BrushRotationArg.ROTATION_CODEC), (data, o) -> data.rotationAngle = o, data -> data.rotationAngle).addValidator(Validators.nonNull()).documentation("The angle that the brush shape should be rotated by").add()).append(new KeyedCodec("MirrorAxis", BrushAxisArg.BRUSH_AXIS_CODEC), (data, o) -> data.mirrorAxis = o, data -> data.mirrorAxis).addValidator(Validators.nonNull()).documentation("The axis that the brush shape should mirror in").add()).append(new KeyedCodec("Material", BlockPattern.CODEC), (brushData, o) -> brushData.material = o, brushData -> brushData.material).addValidator(Validators.nonNull()).documentation("The material to apply when the brush is used").add()).append(new KeyedCodec("FavoriteMaterials", (Codec)new ArrayCodec(BlockPattern.CODEC, x$0 -> new BlockPattern[x$0])), (brushData, o) -> brushData.favoriteMaterials = o, brushData -> brushData.favoriteMaterials).addValidator(Validators.arraySizeRange(0, 5)).documentation("Materials available for quick selection.\n\nWhen a material is selected from here, it is set on the Material key.").add()).append(new KeyedCodec("Mask", BlockMask.CODEC), (brushData, o) -> brushData.mask = o, brushData -> brushData.mask).documentation("Limits the selection to blocks matching materials in this mask").add()).append(new KeyedCodec("MaskAbove", BlockMask.CODEC), (brushData, o) -> brushData.maskAbove = o, brushData -> brushData.maskAbove).documentation("Limits the selection to blocks above ones matching materials in this mask").add()).append(new KeyedCodec("MaskNot", BlockMask.CODEC), (brushData, o) -> brushData.maskNot = o, brushData -> brushData.maskNot).documentation("Limits the selection to any blocks except ones matching materials in this mask").add()).append(new KeyedCodec("MaskBelow", BlockMask.CODEC), (brushData, o) -> brushData.maskBelow = o, brushData -> brushData.maskBelow).documentation("Limits the selection to blocks below ones matching materials in this mask").add()).append(new KeyedCodec("MaskAdjacent", BlockMask.CODEC), (brushData, o) -> brushData.maskAdjacent = o, brushData -> brushData.maskAdjacent).documentation("Limits the selection to blocks horizontally adjacent to ones matching materials in this mask").add()).append(new KeyedCodec("MaskNeighbor", BlockMask.CODEC), (brushData, o) -> brushData.maskNeighbor = o, brushData -> brushData.maskNeighbor).documentation("Limits the selection to blocks neighboring (in any direction) ones matching materials in this mask").add()).append(new KeyedCodec("MaskCommands", (Codec)Codec.STRING_ARRAY), (brushData, o) -> brushData.maskCommands = o, brushData -> brushData.maskCommands).documentation("Custom mask commands to apply to the brush, based on /gmask syntax").add()).append(new KeyedCodec("UseMaskCommands", (Codec)Codec.BOOLEAN), (brushData, o) -> brushData.useMaskCommands = o.booleanValue(), brushData -> Boolean.valueOf(brushData.useMaskCommands)).documentation("Specifies whether to use the block selector mask values or custom mask commands").add()).append(new KeyedCodec("InvertMask", (Codec)Codec.BOOLEAN), (brushData, o) -> brushData.invertMask = o.booleanValue(), brushData -> Boolean.valueOf(brushData.invertMask)).documentation("When enabled, inverts the entire combined mask result").add()).build();
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
/*     */   protected Values() {
/* 479 */     this(BrushData.DEFAULT);
/*     */   }
/*     */   
/*     */   public Values(@Nonnull BrushData brushData) {
/* 483 */     this.width = ((Integer)brushData.width.getValue()).intValue();
/* 484 */     this.height = ((Integer)brushData.height.getValue()).intValue();
/* 485 */     this.thickness = ((Integer)brushData.thickness.getValue()).intValue();
/* 486 */     this.capped = ((Boolean)brushData.capped.getValue()).booleanValue();
/* 487 */     this.shape = (BrushShape)brushData.shape.getValue();
/* 488 */     this.origin = (BrushOrigin)brushData.origin.getValue();
/* 489 */     this.originRotation = ((Boolean)brushData.originRotation.getValue()).booleanValue();
/* 490 */     this.rotationAxis = (BrushAxis)brushData.rotationAxis.getValue();
/* 491 */     this.rotationAngle = (Rotation)brushData.rotationAngle.getValue();
/* 492 */     this.mirrorAxis = (BrushAxis)brushData.mirrorAxis.getValue();
/* 493 */     this.material = (BlockPattern)brushData.material.getValue();
/* 494 */     this.favoriteMaterials = (brushData.favoriteMaterials.length == 0) ? BlockPattern.EMPTY_ARRAY : (BlockPattern[])Arrays.<BlockArg>stream(brushData.favoriteMaterials).limit(5L).map(ToolArg::getValue).toArray(x$0 -> new BlockPattern[x$0]);
/* 495 */     this.mask = (BlockMask)brushData.mask.getValue();
/* 496 */     this.maskAbove = (BlockMask)brushData.maskAbove.getValue();
/* 497 */     this.maskNot = (BlockMask)brushData.maskNot.getValue();
/* 498 */     this.maskBelow = (BlockMask)brushData.maskBelow.getValue();
/* 499 */     this.maskAdjacent = (BlockMask)brushData.maskAdjacent.getValue();
/* 500 */     this.maskNeighbor = (BlockMask)brushData.maskNeighbor.getValue();
/* 501 */     this.maskCommands = (brushData.maskCommands.length == 0) ? ArrayUtil.EMPTY_STRING_ARRAY : (String[])Arrays.<StringArg>stream(brushData.maskCommands).map(ToolArg::getValue).toArray(x$0 -> new String[x$0]);
/* 502 */     this.useMaskCommands = ((Boolean)brushData.useMaskCommands.getValue()).booleanValue();
/* 503 */     this.invertMask = ((Boolean)brushData.invertMask.getValue()).booleanValue();
/*     */   }
/*     */   
/*     */   public Values(int width, int height, int thickness, boolean capped, BrushShape shape, BrushOrigin origin, boolean originRotation, BrushAxis rotationAxis, Rotation rotationAngle, BrushAxis mirrorAxis, BlockPattern material, BlockPattern[] favoriteMaterials, BlockMask mask, BlockMask maskAbove, BlockMask maskNot, BlockMask maskBelow, BlockMask maskAdjacent, BlockMask maskNeighbor, String[] maskCommands, boolean useMaskCommands) {
/* 507 */     this.width = width;
/* 508 */     this.height = height;
/* 509 */     this.thickness = thickness;
/* 510 */     this.capped = capped;
/* 511 */     this.shape = shape;
/* 512 */     this.origin = origin;
/* 513 */     this.originRotation = originRotation;
/* 514 */     this.rotationAxis = rotationAxis;
/* 515 */     this.rotationAngle = rotationAngle;
/* 516 */     this.mirrorAxis = mirrorAxis;
/* 517 */     this.material = material;
/* 518 */     this.favoriteMaterials = favoriteMaterials;
/* 519 */     this.mask = mask;
/* 520 */     this.maskAbove = maskAbove;
/* 521 */     this.maskNot = maskNot;
/* 522 */     this.maskBelow = maskBelow;
/* 523 */     this.maskAdjacent = maskAdjacent;
/* 524 */     this.maskNeighbor = maskNeighbor;
/* 525 */     this.maskCommands = maskCommands;
/* 526 */     this.useMaskCommands = useMaskCommands;
/*     */   }
/*     */   
/*     */   public int getWidth() {
/* 530 */     return this.width;
/*     */   }
/*     */   
/*     */   public int getHeight() {
/* 534 */     return this.height;
/*     */   }
/*     */   
/*     */   public int getThickness() {
/* 538 */     return this.thickness;
/*     */   }
/*     */   
/*     */   public boolean isCapped() {
/* 542 */     return this.capped;
/*     */   }
/*     */   
/*     */   public BrushShape getShape() {
/* 546 */     return this.shape;
/*     */   }
/*     */   
/*     */   public BrushOrigin getOrigin() {
/* 550 */     return this.origin;
/*     */   }
/*     */   
/*     */   public boolean getOriginRotation() {
/* 554 */     return this.originRotation;
/*     */   }
/*     */   
/*     */   public BrushAxis getRotationAxis() {
/* 558 */     return this.rotationAxis;
/*     */   }
/*     */   
/*     */   public Rotation getRotationAngle() {
/* 562 */     return this.rotationAngle;
/*     */   }
/*     */   
/*     */   public BrushAxis getMirrorAxis() {
/* 566 */     return this.mirrorAxis;
/*     */   }
/*     */   
/*     */   public BlockPattern getMaterial() {
/* 570 */     return this.material;
/*     */   }
/*     */   
/*     */   public BlockPattern[] getFavoriteMaterials() {
/* 574 */     return this.favoriteMaterials;
/*     */   }
/*     */   
/*     */   public BlockMask getMask() {
/* 578 */     return this.mask;
/*     */   }
/*     */   
/*     */   public BlockMask getMaskAbove() {
/* 582 */     return this.maskAbove;
/*     */   }
/*     */   
/*     */   public BlockMask getMaskNot() {
/* 586 */     return this.maskNot;
/*     */   }
/*     */   
/*     */   public BlockMask getMaskBelow() {
/* 590 */     return this.maskBelow;
/*     */   }
/*     */   
/*     */   public BlockMask getMaskAdjacent() {
/* 594 */     return this.maskAdjacent;
/*     */   }
/*     */   
/*     */   public BlockMask getMaskNeighbor() {
/* 598 */     return this.maskNeighbor;
/*     */   }
/*     */   
/*     */   public String[] getMaskCommands() {
/* 602 */     return this.maskCommands;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public BlockMask[] getParsedMaskCommands() {
/* 607 */     return (BlockMask[])Arrays.<String>stream(getMaskCommands()).map(m -> m.split(" ")).map(BlockMask::parse).toArray(x$0 -> new BlockMask[x$0]);
/*     */   }
/*     */   
/*     */   public boolean shouldUseMaskCommands() {
/* 611 */     return this.useMaskCommands;
/*     */   }
/*     */   
/*     */   public boolean shouldInvertMask() {
/* 615 */     return this.invertMask;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 621 */     return "Values{width=" + this.width + ", height=" + this.height + ", thickness=" + this.thickness + ", capped=" + this.capped + ", shape=" + String.valueOf(this.shape) + ", origin=" + String.valueOf(this.origin) + ", originRotation=" + this.originRotation + ", rotationAxis=" + String.valueOf(this.rotationAxis) + ", rotationAngle=" + String.valueOf(this.rotationAngle) + ", mirrorAxis=" + String.valueOf(this.mirrorAxis) + ", material=" + String.valueOf(this.material) + ", favoriteMaterials=" + 
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
/* 633 */       Arrays.toString((Object[])this.favoriteMaterials) + ", mask=" + String.valueOf(this.mask) + ", maskAbove=" + String.valueOf(this.maskAbove) + ", maskNot=" + String.valueOf(this.maskNot) + ", maskBelow=" + String.valueOf(this.maskBelow) + ", maskAdjacent=" + String.valueOf(this.maskAdjacent) + ", maskNeighbor=" + String.valueOf(this.maskNeighbor) + ", maskCommands=" + 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 640 */       Arrays.toString((Object[])this.maskCommands) + ", useMaskCommands=" + this.useMaskCommands + ", invertMask=" + this.invertMask + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\buildertool\config\BrushData$Values.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */