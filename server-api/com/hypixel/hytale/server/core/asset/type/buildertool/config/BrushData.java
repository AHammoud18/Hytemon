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
/*     */ import com.hypixel.hytale.protocol.packets.buildertools.BuilderToolBlockArg;
/*     */ import com.hypixel.hytale.protocol.packets.buildertools.BuilderToolBrushData;
/*     */ import com.hypixel.hytale.protocol.packets.buildertools.BuilderToolStringArg;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.asset.type.buildertool.config.args.BlockArg;
/*     */ import com.hypixel.hytale.server.core.asset.type.buildertool.config.args.BoolArg;
/*     */ import com.hypixel.hytale.server.core.asset.type.buildertool.config.args.BrushAxisArg;
/*     */ import com.hypixel.hytale.server.core.asset.type.buildertool.config.args.BrushOriginArg;
/*     */ import com.hypixel.hytale.server.core.asset.type.buildertool.config.args.BrushRotationArg;
/*     */ import com.hypixel.hytale.server.core.asset.type.buildertool.config.args.BrushShapeArg;
/*     */ import com.hypixel.hytale.server.core.asset.type.buildertool.config.args.IntArg;
/*     */ import com.hypixel.hytale.server.core.asset.type.buildertool.config.args.MaskArg;
/*     */ import com.hypixel.hytale.server.core.asset.type.buildertool.config.args.StringArg;
/*     */ import com.hypixel.hytale.server.core.asset.type.buildertool.config.args.ToolArg;
/*     */ import com.hypixel.hytale.server.core.asset.type.buildertool.config.args.ToolArgException;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.mask.BlockMask;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.mask.BlockPattern;
/*     */ import java.util.Arrays;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.regex.Pattern;
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
/*     */ public class BrushData
/*     */   implements NetworkSerializable<BuilderToolBrushData>
/*     */ {
/*     */   public static final String WIDTH_KEY = "Width";
/*     */   public static final String HEIGHT_KEY = "Height";
/*     */   public static final String SHAPE_KEY = "Shape";
/*     */   public static final String THICKNESS_KEY = "Thickness";
/*     */   public static final String CAPPED_KEY = "Capped";
/*     */   public static final String ORIGIN_KEY = "Origin";
/*     */   public static final String ORIGIN_ROTATION_KEY = "OriginRotation";
/*     */   public static final String ROTATION_AXIS_KEY = "RotationAxis";
/*     */   public static final String ROTATION_ANGLE_KEY = "RotationAngle";
/*     */   public static final String MIRROR_AXIS_KEY = "MirrorAxis";
/*     */   public static final String MATERIAL_KEY = "Material";
/*     */   public static final String FAVORITE_MATERIALS_KEY = "FavoriteMaterials";
/*     */   public static final String MASK_KEY = "Mask";
/*     */   public static final String MASK_ABOVE_KEY = "MaskAbove";
/*     */   public static final String MASK_NOT_KEY = "MaskNot";
/*     */   public static final String MASK_BELOW_KEY = "MaskBelow";
/*     */   public static final String MASK_ADJACENT_KEY = "MaskAdjacent";
/*     */   public static final String MASK_NEIGHBOR_KEY = "MaskNeighbor";
/*     */   public static final String MASK_COMMANDS_KEY = "MaskCommands";
/*     */   public static final String USE_MASK_COMMANDS_KEY = "UseMaskCommands";
/*     */   public static final String INVERT_MASK_KEY = "InvertMask";
/*     */   private static final String WIDTH_DOC = "The width of the brush shape";
/*  78 */   public static final BrushData DEFAULT = new BrushData(); private static final String HEIGHT_DOC = "The height of the brush shape"; private static final String THICKNESS_DOC = "The number of blocks thick the walls of the brush shape should be"; private static final String CAPPED_DOC = "Controls whether the end(s) of hollow brush shapes are closed or open"; private static final String SHAPE_DOC = "The brush shape"; private static final String ORIGIN_DOC = "The origin of the brush shape"; private static final String ORIGIN_ROTATION_DOC = "Toggles the vertical offset for shapes rotated about the x/z axis"; private static final String ROTATION_AXIS_DOC = "The axis that the brush shape should rotate around"; private static final String ROTATION_ANGLE_DOC = "The angle that the brush shape should be rotated by"; private static final String MIRROR_AXIS_DOC = "The axis that the brush shape should mirror in"; private static final String MATERIAL_DOC = "The material to apply when the brush is used"; private static final String FAVORITE_MATERIALS_DOC = "Materials available for quick selection.\n\nWhen a material is selected from here, it is set on the Material key."; private static final String MASK_DOC = "Limits the selection to blocks matching materials in this mask"; private static final String MASK_ABOVE_DOC = "Limits the selection to blocks above ones matching materials in this mask"; private static final String MASK_NOT_DOC = "Limits the selection to any blocks except ones matching materials in this mask"; private static final String MASK_BELOW_DOC = "Limits the selection to blocks below ones matching materials in this mask"; private static final String MASK_ADJACENT_DOC = "Limits the selection to blocks horizontally adjacent to ones matching materials in this mask"; private static final String MASK_NEIGHBOR_DOC = "Limits the selection to blocks neighboring (in any direction) ones matching materials in this mask"; private static final String MASK_COMMANDS_DOC = "Custom mask commands to apply to the brush, based on /gmask syntax"; private static final String USE_MASK_COMMANDS_DOC = "Specifies whether to use the block selector mask values or custom mask commands"; private static final String INVERT_MASK_DOC = "When enabled, inverts the entire combined mask result";
/*     */   public static final int DEFAULT_WIDTH = 5;
/*     */   public static final int DEFAULT_HEIGHT = 5;
/*     */   public static final int DEFAULT_FAVORITE_MATERIALS_CAPACITY = 5;
/*  82 */   private static final Pattern NEWLINES_PATTERN = Pattern.compile("\\r?\\n");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final BuilderCodec<BrushData> CODEC;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 159 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(BrushData.class, BrushData::new).append(new KeyedCodec("Width", (Codec)IntArg.CODEC), (brushData, o) -> brushData.width = o, brushData -> brushData.width).addValidator(Validators.nonNull()).documentation("The width of the brush shape").add()).append(new KeyedCodec("Height", (Codec)IntArg.CODEC), (brushData, o) -> brushData.height = o, brushData -> brushData.height).addValidator(Validators.nonNull()).documentation("The height of the brush shape").add()).append(new KeyedCodec("Thickness", (Codec)IntArg.CODEC), (data, o) -> data.thickness = o, data -> data.thickness).addValidator(Validators.nonNull()).documentation("The number of blocks thick the walls of the brush shape should be").add()).append(new KeyedCodec("Capped", (Codec)BoolArg.CODEC), (data, o) -> data.capped = o, data -> data.capped).addValidator(Validators.nonNull()).documentation("Controls whether the end(s) of hollow brush shapes are closed or open").add()).append(new KeyedCodec("Shape", (Codec)BrushShapeArg.CODEC), (brushData, o) -> brushData.shape = o, brushData -> brushData.shape).addValidator(Validators.nonNull()).documentation("The brush shape").add()).append(new KeyedCodec("Origin", (Codec)BrushOriginArg.CODEC), (brushData, o) -> brushData.origin = o, brushData -> brushData.origin).addValidator(Validators.nonNull()).documentation("The origin of the brush shape").add()).append(new KeyedCodec("OriginRotation", (Codec)BoolArg.CODEC), (data, o) -> data.originRotation = o, data -> data.originRotation).addValidator(Validators.nonNull()).documentation("Toggles the vertical offset for shapes rotated about the x/z axis").add()).append(new KeyedCodec("RotationAxis", (Codec)BrushAxisArg.CODEC), (data, o) -> data.rotationAxis = o, data -> data.rotationAxis).addValidator(Validators.nonNull()).documentation("The axis that the brush shape should rotate around").add()).append(new KeyedCodec("RotationAngle", (Codec)BrushRotationArg.CODEC), (data, o) -> data.rotationAngle = o, data -> data.rotationAngle).addValidator(Validators.nonNull()).documentation("The angle that the brush shape should be rotated by").add()).append(new KeyedCodec("MirrorAxis", (Codec)BrushAxisArg.CODEC), (data, o) -> data.mirrorAxis = o, data -> data.mirrorAxis).addValidator(Validators.nonNull()).documentation("The axis that the brush shape should mirror in").add()).append(new KeyedCodec("Material", (Codec)BlockArg.CODEC), (brushData, o) -> brushData.material = o, brushData -> brushData.material).addValidator(Validators.nonNull()).documentation("The material to apply when the brush is used").add()).append(new KeyedCodec("FavoriteMaterials", (Codec)new ArrayCodec((Codec)BlockArg.CODEC, x$0 -> new BlockArg[x$0])), (brushData, o) -> brushData.favoriteMaterials = o, brushData -> brushData.favoriteMaterials).documentation("Materials available for quick selection.\n\nWhen a material is selected from here, it is set on the Material key.").add()).append(new KeyedCodec("Mask", (Codec)MaskArg.CODEC), (brushData, o) -> brushData.mask = o, brushData -> brushData.mask).documentation("Limits the selection to blocks matching materials in this mask").add()).append(new KeyedCodec("MaskAbove", (Codec)MaskArg.CODEC), (brushData, o) -> brushData.maskAbove = o, brushData -> brushData.maskAbove).documentation("Limits the selection to blocks above ones matching materials in this mask").add()).append(new KeyedCodec("MaskNot", (Codec)MaskArg.CODEC), (brushData, o) -> brushData.maskNot = o, brushData -> brushData.maskNot).documentation("Limits the selection to any blocks except ones matching materials in this mask").add()).append(new KeyedCodec("MaskBelow", (Codec)MaskArg.CODEC), (brushData, o) -> brushData.maskBelow = o, brushData -> brushData.maskBelow).documentation("Limits the selection to blocks below ones matching materials in this mask").add()).append(new KeyedCodec("MaskAdjacent", (Codec)MaskArg.CODEC), (brushData, o) -> brushData.maskAdjacent = o, brushData -> brushData.maskAdjacent).documentation("Limits the selection to blocks horizontally adjacent to ones matching materials in this mask").add()).append(new KeyedCodec("MaskNeighbor", (Codec)MaskArg.CODEC), (brushData, o) -> brushData.maskNeighbor = o, brushData -> brushData.maskNeighbor).documentation("Limits the selection to blocks neighboring (in any direction) ones matching materials in this mask").add()).append(new KeyedCodec("MaskCommands", (Codec)new ArrayCodec((Codec)StringArg.CODEC, x$0 -> new StringArg[x$0])), (brushData, o) -> brushData.maskCommands = o, brushData -> brushData.maskCommands).documentation("Custom mask commands to apply to the brush, based on /gmask syntax").add()).append(new KeyedCodec("UseMaskCommands", (Codec)BoolArg.CODEC), (brushData, o) -> brushData.useMaskCommands = o, brushData -> brushData.useMaskCommands).documentation("Specifies whether to use the block selector mask values or custom mask commands").add()).append(new KeyedCodec("InvertMask", (Codec)BoolArg.CODEC), (brushData, o) -> brushData.invertMask = o, brushData -> brushData.invertMask).documentation("When enabled, inverts the entire combined mask result").add()).build();
/*     */   }
/* 161 */   protected IntArg width = new IntArg(5, 1, 100);
/* 162 */   protected IntArg height = new IntArg(5, 1, 100);
/* 163 */   protected IntArg thickness = new IntArg(0, 0, 100);
/* 164 */   protected BoolArg capped = new BoolArg(false);
/*     */   
/* 166 */   protected BrushShapeArg shape = new BrushShapeArg(BrushShape.Cube);
/* 167 */   protected BrushOriginArg origin = new BrushOriginArg(BrushOrigin.Center);
/* 168 */   protected BoolArg originRotation = new BoolArg(false);
/* 169 */   protected BrushAxisArg rotationAxis = new BrushAxisArg(BrushAxis.None);
/* 170 */   protected BrushRotationArg rotationAngle = new BrushRotationArg(Rotation.None);
/* 171 */   protected BrushAxisArg mirrorAxis = new BrushAxisArg(BrushAxis.None);
/* 172 */   protected BlockArg material = new BlockArg(BlockPattern.EMPTY, true);
/* 173 */   protected BlockArg[] favoriteMaterials = BlockArg.EMPTY_ARRAY;
/* 174 */   protected MaskArg mask = MaskArg.EMPTY;
/* 175 */   protected MaskArg maskAbove = MaskArg.EMPTY;
/* 176 */   protected MaskArg maskNot = MaskArg.EMPTY;
/* 177 */   protected MaskArg maskBelow = MaskArg.EMPTY;
/* 178 */   protected MaskArg maskAdjacent = MaskArg.EMPTY;
/* 179 */   protected MaskArg maskNeighbor = MaskArg.EMPTY;
/* 180 */   protected StringArg[] maskCommands = StringArg.EMPTY_ARRAY;
/* 181 */   protected BoolArg useMaskCommands = new BoolArg(false);
/* 182 */   protected BoolArg invertMask = new BoolArg(false);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BrushData(IntArg width, IntArg height, IntArg thickness, BoolArg capped, BrushShapeArg shape, BrushOriginArg origin, BoolArg originRotation, BrushAxisArg rotationAxis, BrushRotationArg rotationAngle, BrushAxisArg mirrorAxis, BlockArg material, BlockArg[] favoriteMaterials, MaskArg mask, MaskArg maskAbove, MaskArg maskNot, MaskArg maskBelow, MaskArg maskAdjacent, MaskArg maskNeighbor, StringArg[] maskCommands, BoolArg useMaskCommands) {
/* 188 */     this.width = width;
/* 189 */     this.height = height;
/* 190 */     this.thickness = thickness;
/* 191 */     this.capped = capped;
/* 192 */     this.shape = shape;
/* 193 */     this.origin = origin;
/* 194 */     this.originRotation = originRotation;
/* 195 */     this.rotationAxis = rotationAxis;
/* 196 */     this.rotationAngle = rotationAngle;
/* 197 */     this.mirrorAxis = mirrorAxis;
/* 198 */     this.material = material;
/* 199 */     this.favoriteMaterials = favoriteMaterials;
/* 200 */     this.mask = mask;
/* 201 */     this.maskAbove = maskAbove;
/* 202 */     this.maskNot = maskNot;
/* 203 */     this.maskBelow = maskBelow;
/* 204 */     this.maskAdjacent = maskAdjacent;
/* 205 */     this.maskNeighbor = maskNeighbor;
/* 206 */     this.maskCommands = maskCommands;
/* 207 */     this.useMaskCommands = useMaskCommands;
/*     */   }
/*     */   
/*     */   public IntArg getWidth() {
/* 211 */     return this.width;
/*     */   }
/*     */   
/*     */   public IntArg getHeight() {
/* 215 */     return this.height;
/*     */   }
/*     */   
/*     */   public IntArg getThickness() {
/* 219 */     return this.thickness;
/*     */   }
/*     */   
/*     */   public BoolArg getCapped() {
/* 223 */     return this.capped;
/*     */   }
/*     */   
/*     */   public BrushShapeArg getShape() {
/* 227 */     return this.shape;
/*     */   }
/*     */   
/*     */   public BrushOriginArg getOrigin() {
/* 231 */     return this.origin;
/*     */   }
/*     */   
/*     */   public BoolArg getOriginRotation() {
/* 235 */     return this.originRotation;
/*     */   }
/*     */   
/*     */   public BrushAxisArg getRotationAxis() {
/* 239 */     return this.rotationAxis;
/*     */   }
/*     */   
/*     */   public BrushRotationArg getRotationAngle() {
/* 243 */     return this.rotationAngle;
/*     */   }
/*     */   
/*     */   public BrushAxisArg getMirrorAxis() {
/* 247 */     return this.mirrorAxis;
/*     */   }
/*     */   
/*     */   public BlockArg getMaterial() {
/* 251 */     return this.material;
/*     */   }
/*     */   
/*     */   public BlockArg[] getFavoriteMaterials() {
/* 255 */     return this.favoriteMaterials;
/*     */   }
/*     */   
/*     */   public MaskArg getMask() {
/* 259 */     return this.mask;
/*     */   }
/*     */   
/*     */   public MaskArg getMaskAbove() {
/* 263 */     return this.maskAbove;
/*     */   }
/*     */   
/*     */   public MaskArg getMaskNot() {
/* 267 */     return this.maskNot;
/*     */   }
/*     */   
/*     */   public MaskArg getMaskBelow() {
/* 271 */     return this.maskBelow;
/*     */   }
/*     */   
/*     */   public MaskArg getMaskAdjacent() {
/* 275 */     return this.maskAdjacent;
/*     */   }
/*     */   
/*     */   public MaskArg getMaskNeighbor() {
/* 279 */     return this.maskNeighbor;
/*     */   }
/*     */   
/*     */   public StringArg[] getMaskCommands() {
/* 283 */     return this.maskCommands;
/*     */   }
/*     */   
/*     */   public BoolArg getUseMaskCommands() {
/* 287 */     return this.useMaskCommands;
/*     */   }
/*     */   
/*     */   public BoolArg getInvertMask() {
/* 291 */     return this.invertMask;
/*     */   }
/*     */   
/*     */   public void updateArgValue(@Nonnull Values brush, @Nonnull String id, @Nonnull String value) throws ToolArgException {
/* 295 */     switch (id) { case "Height":
/* 296 */         brush.height = this.height.fromString(value).intValue(); return;
/* 297 */       case "Width": brush.width = this.width.fromString(value).intValue(); return;
/* 298 */       case "Thickness": brush.thickness = this.thickness.fromString(value).intValue(); return;
/* 299 */       case "Capped": brush.capped = this.capped.fromString(value).booleanValue(); return;
/* 300 */       case "Shape": brush.shape = this.shape.fromString(value); return;
/* 301 */       case "Origin": brush.origin = this.origin.fromString(value); return;
/* 302 */       case "OriginRotation": brush.originRotation = this.originRotation.fromString(value).booleanValue(); return;
/* 303 */       case "RotationAxis": brush.rotationAxis = this.rotationAxis.fromString(value); return;
/* 304 */       case "RotationAngle": brush.rotationAngle = this.rotationAngle.fromString(value); return;
/* 305 */       case "MirrorAxis": brush.mirrorAxis = this.mirrorAxis.fromString(value); return;
/* 306 */       case "Material": brush.material = this.material.fromString(value); return;
/* 307 */       case "FavoriteMaterials": brush.favoriteMaterials = value.isEmpty() ? BlockPattern.EMPTY_ARRAY : (BlockPattern[])Arrays.<String>stream(value.split(",")).limit(5L).map(BlockPattern::parse).toArray(x$0 -> new BlockPattern[x$0]); return;
/* 308 */       case "Mask": brush.mask = this.mask.fromString(value); return;
/* 309 */       case "MaskAbove": brush.maskAbove = this.maskAbove.fromString(value); return;
/* 310 */       case "MaskNot": brush.maskNot = this.maskNot.fromString(value); return;
/* 311 */       case "MaskBelow": brush.maskBelow = this.maskBelow.fromString(value); return;
/* 312 */       case "MaskAdjacent": brush.maskAdjacent = this.maskAdjacent.fromString(value); return;
/* 313 */       case "MaskNeighbor": brush.maskNeighbor = this.maskNeighbor.fromString(value); return;
/* 314 */       case "MaskCommands": brush.maskCommands = value.isEmpty() ? ArrayUtil.EMPTY_STRING_ARRAY : NEWLINES_PATTERN.split(value); return;
/* 315 */       case "UseMaskCommands": brush.useMaskCommands = this.useMaskCommands.fromString(value).booleanValue(); return;
/* 316 */       case "InvertMask": brush.invertMask = this.invertMask.fromString(value).booleanValue(); return; }
/* 317 */      throw new ToolArgException(Message.translation("server.builderTools.toolUnknownArg").param("arg", id));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public BuilderToolBrushData toPacket() {
/* 324 */     BuilderToolBrushData packet = new BuilderToolBrushData();
/* 325 */     packet.width = this.width.toIntArgPacket();
/* 326 */     packet.height = this.height.toIntArgPacket();
/* 327 */     packet.thickness = this.thickness.toIntArgPacket();
/* 328 */     packet.capped = this.capped.toBoolArgPacket();
/* 329 */     packet.shape = this.shape.toBrushShapeArgPacket();
/* 330 */     packet.origin = this.origin.toBrushOriginArgPacket();
/* 331 */     packet.originRotation = this.originRotation.toBoolArgPacket();
/* 332 */     packet.rotationAxis = this.rotationAxis.toBrushAxisArgPacket();
/* 333 */     packet.rotationAngle = this.rotationAngle.toRotationArgPacket();
/* 334 */     packet.mirrorAxis = this.mirrorAxis.toBrushAxisArgPacket();
/* 335 */     packet.material = this.material.toBlockArgPacket();
/* 336 */     packet.favoriteMaterials = (BuilderToolBlockArg[])Arrays.<BlockArg>stream(this.favoriteMaterials).filter(Objects::nonNull).map(BlockArg::toBlockArgPacket).toArray(x$0 -> new BuilderToolBlockArg[x$0]);
/* 337 */     packet.mask = this.mask.toMaskArgPacket();
/* 338 */     packet.maskAbove = this.maskAbove.toMaskArgPacket();
/* 339 */     packet.maskNot = this.maskNot.toMaskArgPacket();
/* 340 */     packet.maskBelow = this.maskBelow.toMaskArgPacket();
/* 341 */     packet.maskAdjacent = this.maskAdjacent.toMaskArgPacket();
/* 342 */     packet.maskNeighbor = this.maskNeighbor.toMaskArgPacket();
/* 343 */     packet.maskCommands = (BuilderToolStringArg[])Arrays.<StringArg>stream(this.maskCommands).filter(Objects::nonNull).map(StringArg::toStringArgPacket).toArray(x$0 -> new BuilderToolStringArg[x$0]);
/* 344 */     packet.useMaskCommands = this.useMaskCommands.toBoolArgPacket();
/* 345 */     packet.invertMask = this.invertMask.toBoolArgPacket();
/* 346 */     return packet;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 352 */     return "BrushData{width=" + String.valueOf(this.width) + ", height=" + String.valueOf(this.height) + ", thickness=" + String.valueOf(this.thickness) + ", capped=" + String.valueOf(this.capped) + ", shape=" + String.valueOf(this.shape) + ", origin=" + String.valueOf(this.origin) + ", originRotation=" + String.valueOf(this.originRotation) + ", rotationAxis=" + String.valueOf(this.rotationAxis) + ", rotationAngle=" + String.valueOf(this.rotationAngle) + ", mirrorAxis=" + String.valueOf(this.mirrorAxis) + ", material=" + String.valueOf(this.material) + ", favoriteMaterials=" + 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 364 */       Arrays.toString((Object[])this.favoriteMaterials) + ", mask=" + String.valueOf(this.mask) + ", maskAbove=" + String.valueOf(this.maskAbove) + ", maskNot=" + String.valueOf(this.maskNot) + ", maskBelow=" + String.valueOf(this.maskBelow) + ", maskAdjacent=" + String.valueOf(this.maskAdjacent) + ", maskNeighbor=" + String.valueOf(this.maskNeighbor) + ", maskCommands=" + 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 371 */       Arrays.toString((Object[])this.maskCommands) + ", useMaskCommands=" + String.valueOf(this.useMaskCommands) + ", invertMask=" + String.valueOf(this.invertMask) + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected BrushData() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Values
/*     */   {
/*     */     public static final Codec<Values> CODEC;
/*     */ 
/*     */ 
/*     */     
/*     */     private int width;
/*     */ 
/*     */ 
/*     */     
/*     */     private int height;
/*     */ 
/*     */     
/*     */     private int thickness;
/*     */ 
/*     */     
/*     */     private boolean capped;
/*     */ 
/*     */     
/*     */     private BrushShape shape;
/*     */ 
/*     */     
/*     */     private BrushOrigin origin;
/*     */ 
/*     */     
/*     */     private boolean originRotation;
/*     */ 
/*     */     
/*     */     private BrushAxis rotationAxis;
/*     */ 
/*     */     
/*     */     private Rotation rotationAngle;
/*     */ 
/*     */     
/*     */     private BrushAxis mirrorAxis;
/*     */ 
/*     */     
/*     */     private BlockPattern material;
/*     */ 
/*     */     
/*     */     private BlockPattern[] favoriteMaterials;
/*     */ 
/*     */     
/*     */     private BlockMask mask;
/*     */ 
/*     */     
/*     */     private BlockMask maskAbove;
/*     */ 
/*     */     
/*     */     private BlockMask maskNot;
/*     */ 
/*     */     
/*     */     private BlockMask maskBelow;
/*     */ 
/*     */     
/*     */     private BlockMask maskAdjacent;
/*     */ 
/*     */     
/*     */     private BlockMask maskNeighbor;
/*     */ 
/*     */     
/*     */     private String[] maskCommands;
/*     */ 
/*     */     
/*     */     private boolean useMaskCommands;
/*     */ 
/*     */     
/*     */     private boolean invertMask;
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/* 454 */       CODEC = (Codec<Values>)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(Values.class, Values::new).append(new KeyedCodec("Width", (Codec)Codec.INTEGER), (brushData, o) -> brushData.width = o.intValue(), brushData -> Integer.valueOf(brushData.width)).addValidator(Validators.greaterThan(Integer.valueOf(0))).documentation("The width of the brush shape").add()).append(new KeyedCodec("Height", (Codec)Codec.INTEGER), (brushData, o) -> brushData.height = o.intValue(), brushData -> Integer.valueOf(brushData.height)).addValidator(Validators.greaterThan(Integer.valueOf(0))).documentation("The height of the brush shape").add()).append(new KeyedCodec("Thickness", (Codec)Codec.INTEGER), (data, o) -> data.thickness = o.intValue(), data -> Integer.valueOf(data.thickness)).addValidator(Validators.range(Integer.valueOf(0), Integer.valueOf(100))).documentation("The number of blocks thick the walls of the brush shape should be").add()).append(new KeyedCodec("Capped", (Codec)Codec.BOOLEAN), (data, o) -> data.capped = o.booleanValue(), data -> Boolean.valueOf(data.capped)).addValidator(Validators.nonNull()).documentation("Controls whether the end(s) of hollow brush shapes are closed or open").add()).append(new KeyedCodec("Shape", (Codec)BrushShapeArg.BRUSH_SHAPE_CODEC), (brushData, o) -> brushData.shape = o, brushData -> brushData.shape).addValidator(Validators.nonNull()).documentation("The brush shape").add()).append(new KeyedCodec("Origin", (Codec)BrushOriginArg.BRUSH_ORIGIN_CODEC), (brushData, o) -> brushData.origin = o, brushData -> brushData.origin).addValidator(Validators.nonNull()).documentation("The origin of the brush shape").add()).append(new KeyedCodec("OriginRotation", (Codec)Codec.BOOLEAN), (data, o) -> data.originRotation = o.booleanValue(), data -> Boolean.valueOf(data.originRotation)).addValidator(Validators.nonNull()).documentation("Toggles the vertical offset for shapes rotated about the x/z axis").add()).append(new KeyedCodec("RotationAxis", BrushAxisArg.BRUSH_AXIS_CODEC), (data, o) -> data.rotationAxis = o, data -> data.rotationAxis).addValidator(Validators.nonNull()).documentation("The axis that the brush shape should rotate around").add()).append(new KeyedCodec("RotationAngle", BrushRotationArg.ROTATION_CODEC), (data, o) -> data.rotationAngle = o, data -> data.rotationAngle).addValidator(Validators.nonNull()).documentation("The angle that the brush shape should be rotated by").add()).append(new KeyedCodec("MirrorAxis", BrushAxisArg.BRUSH_AXIS_CODEC), (data, o) -> data.mirrorAxis = o, data -> data.mirrorAxis).addValidator(Validators.nonNull()).documentation("The axis that the brush shape should mirror in").add()).append(new KeyedCodec("Material", BlockPattern.CODEC), (brushData, o) -> brushData.material = o, brushData -> brushData.material).addValidator(Validators.nonNull()).documentation("The material to apply when the brush is used").add()).append(new KeyedCodec("FavoriteMaterials", (Codec)new ArrayCodec(BlockPattern.CODEC, x$0 -> new BlockPattern[x$0])), (brushData, o) -> brushData.favoriteMaterials = o, brushData -> brushData.favoriteMaterials).addValidator(Validators.arraySizeRange(0, 5)).documentation("Materials available for quick selection.\n\nWhen a material is selected from here, it is set on the Material key.").add()).append(new KeyedCodec("Mask", BlockMask.CODEC), (brushData, o) -> brushData.mask = o, brushData -> brushData.mask).documentation("Limits the selection to blocks matching materials in this mask").add()).append(new KeyedCodec("MaskAbove", BlockMask.CODEC), (brushData, o) -> brushData.maskAbove = o, brushData -> brushData.maskAbove).documentation("Limits the selection to blocks above ones matching materials in this mask").add()).append(new KeyedCodec("MaskNot", BlockMask.CODEC), (brushData, o) -> brushData.maskNot = o, brushData -> brushData.maskNot).documentation("Limits the selection to any blocks except ones matching materials in this mask").add()).append(new KeyedCodec("MaskBelow", BlockMask.CODEC), (brushData, o) -> brushData.maskBelow = o, brushData -> brushData.maskBelow).documentation("Limits the selection to blocks below ones matching materials in this mask").add()).append(new KeyedCodec("MaskAdjacent", BlockMask.CODEC), (brushData, o) -> brushData.maskAdjacent = o, brushData -> brushData.maskAdjacent).documentation("Limits the selection to blocks horizontally adjacent to ones matching materials in this mask").add()).append(new KeyedCodec("MaskNeighbor", BlockMask.CODEC), (brushData, o) -> brushData.maskNeighbor = o, brushData -> brushData.maskNeighbor).documentation("Limits the selection to blocks neighboring (in any direction) ones matching materials in this mask").add()).append(new KeyedCodec("MaskCommands", (Codec)Codec.STRING_ARRAY), (brushData, o) -> brushData.maskCommands = o, brushData -> brushData.maskCommands).documentation("Custom mask commands to apply to the brush, based on /gmask syntax").add()).append(new KeyedCodec("UseMaskCommands", (Codec)Codec.BOOLEAN), (brushData, o) -> brushData.useMaskCommands = o.booleanValue(), brushData -> Boolean.valueOf(brushData.useMaskCommands)).documentation("Specifies whether to use the block selector mask values or custom mask commands").add()).append(new KeyedCodec("InvertMask", (Codec)Codec.BOOLEAN), (brushData, o) -> brushData.invertMask = o.booleanValue(), brushData -> Boolean.valueOf(brushData.invertMask)).documentation("When enabled, inverts the entire combined mask result").add()).build();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected Values() {
/* 479 */       this(BrushData.DEFAULT);
/*     */     }
/*     */     
/*     */     public Values(@Nonnull BrushData brushData) {
/* 483 */       this.width = ((Integer)brushData.width.getValue()).intValue();
/* 484 */       this.height = ((Integer)brushData.height.getValue()).intValue();
/* 485 */       this.thickness = ((Integer)brushData.thickness.getValue()).intValue();
/* 486 */       this.capped = ((Boolean)brushData.capped.getValue()).booleanValue();
/* 487 */       this.shape = (BrushShape)brushData.shape.getValue();
/* 488 */       this.origin = (BrushOrigin)brushData.origin.getValue();
/* 489 */       this.originRotation = ((Boolean)brushData.originRotation.getValue()).booleanValue();
/* 490 */       this.rotationAxis = (BrushAxis)brushData.rotationAxis.getValue();
/* 491 */       this.rotationAngle = (Rotation)brushData.rotationAngle.getValue();
/* 492 */       this.mirrorAxis = (BrushAxis)brushData.mirrorAxis.getValue();
/* 493 */       this.material = (BlockPattern)brushData.material.getValue();
/* 494 */       this.favoriteMaterials = (brushData.favoriteMaterials.length == 0) ? BlockPattern.EMPTY_ARRAY : (BlockPattern[])Arrays.<BlockArg>stream(brushData.favoriteMaterials).limit(5L).map(ToolArg::getValue).toArray(x$0 -> new BlockPattern[x$0]);
/* 495 */       this.mask = (BlockMask)brushData.mask.getValue();
/* 496 */       this.maskAbove = (BlockMask)brushData.maskAbove.getValue();
/* 497 */       this.maskNot = (BlockMask)brushData.maskNot.getValue();
/* 498 */       this.maskBelow = (BlockMask)brushData.maskBelow.getValue();
/* 499 */       this.maskAdjacent = (BlockMask)brushData.maskAdjacent.getValue();
/* 500 */       this.maskNeighbor = (BlockMask)brushData.maskNeighbor.getValue();
/* 501 */       this.maskCommands = (brushData.maskCommands.length == 0) ? ArrayUtil.EMPTY_STRING_ARRAY : (String[])Arrays.<StringArg>stream(brushData.maskCommands).map(ToolArg::getValue).toArray(x$0 -> new String[x$0]);
/* 502 */       this.useMaskCommands = ((Boolean)brushData.useMaskCommands.getValue()).booleanValue();
/* 503 */       this.invertMask = ((Boolean)brushData.invertMask.getValue()).booleanValue();
/*     */     }
/*     */     
/*     */     public Values(int width, int height, int thickness, boolean capped, BrushShape shape, BrushOrigin origin, boolean originRotation, BrushAxis rotationAxis, Rotation rotationAngle, BrushAxis mirrorAxis, BlockPattern material, BlockPattern[] favoriteMaterials, BlockMask mask, BlockMask maskAbove, BlockMask maskNot, BlockMask maskBelow, BlockMask maskAdjacent, BlockMask maskNeighbor, String[] maskCommands, boolean useMaskCommands) {
/* 507 */       this.width = width;
/* 508 */       this.height = height;
/* 509 */       this.thickness = thickness;
/* 510 */       this.capped = capped;
/* 511 */       this.shape = shape;
/* 512 */       this.origin = origin;
/* 513 */       this.originRotation = originRotation;
/* 514 */       this.rotationAxis = rotationAxis;
/* 515 */       this.rotationAngle = rotationAngle;
/* 516 */       this.mirrorAxis = mirrorAxis;
/* 517 */       this.material = material;
/* 518 */       this.favoriteMaterials = favoriteMaterials;
/* 519 */       this.mask = mask;
/* 520 */       this.maskAbove = maskAbove;
/* 521 */       this.maskNot = maskNot;
/* 522 */       this.maskBelow = maskBelow;
/* 523 */       this.maskAdjacent = maskAdjacent;
/* 524 */       this.maskNeighbor = maskNeighbor;
/* 525 */       this.maskCommands = maskCommands;
/* 526 */       this.useMaskCommands = useMaskCommands;
/*     */     }
/*     */     
/*     */     public int getWidth() {
/* 530 */       return this.width;
/*     */     }
/*     */     
/*     */     public int getHeight() {
/* 534 */       return this.height;
/*     */     }
/*     */     
/*     */     public int getThickness() {
/* 538 */       return this.thickness;
/*     */     }
/*     */     
/*     */     public boolean isCapped() {
/* 542 */       return this.capped;
/*     */     }
/*     */     
/*     */     public BrushShape getShape() {
/* 546 */       return this.shape;
/*     */     }
/*     */     
/*     */     public BrushOrigin getOrigin() {
/* 550 */       return this.origin;
/*     */     }
/*     */     
/*     */     public boolean getOriginRotation() {
/* 554 */       return this.originRotation;
/*     */     }
/*     */     
/*     */     public BrushAxis getRotationAxis() {
/* 558 */       return this.rotationAxis;
/*     */     }
/*     */     
/*     */     public Rotation getRotationAngle() {
/* 562 */       return this.rotationAngle;
/*     */     }
/*     */     
/*     */     public BrushAxis getMirrorAxis() {
/* 566 */       return this.mirrorAxis;
/*     */     }
/*     */     
/*     */     public BlockPattern getMaterial() {
/* 570 */       return this.material;
/*     */     }
/*     */     
/*     */     public BlockPattern[] getFavoriteMaterials() {
/* 574 */       return this.favoriteMaterials;
/*     */     }
/*     */     
/*     */     public BlockMask getMask() {
/* 578 */       return this.mask;
/*     */     }
/*     */     
/*     */     public BlockMask getMaskAbove() {
/* 582 */       return this.maskAbove;
/*     */     }
/*     */     
/*     */     public BlockMask getMaskNot() {
/* 586 */       return this.maskNot;
/*     */     }
/*     */     
/*     */     public BlockMask getMaskBelow() {
/* 590 */       return this.maskBelow;
/*     */     }
/*     */     
/*     */     public BlockMask getMaskAdjacent() {
/* 594 */       return this.maskAdjacent;
/*     */     }
/*     */     
/*     */     public BlockMask getMaskNeighbor() {
/* 598 */       return this.maskNeighbor;
/*     */     }
/*     */     
/*     */     public String[] getMaskCommands() {
/* 602 */       return this.maskCommands;
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public BlockMask[] getParsedMaskCommands() {
/* 607 */       return (BlockMask[])Arrays.<String>stream(getMaskCommands()).map(m -> m.split(" ")).map(BlockMask::parse).toArray(x$0 -> new BlockMask[x$0]);
/*     */     }
/*     */     
/*     */     public boolean shouldUseMaskCommands() {
/* 611 */       return this.useMaskCommands;
/*     */     }
/*     */     
/*     */     public boolean shouldInvertMask() {
/* 615 */       return this.invertMask;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 621 */       return "Values{width=" + this.width + ", height=" + this.height + ", thickness=" + this.thickness + ", capped=" + this.capped + ", shape=" + String.valueOf(this.shape) + ", origin=" + String.valueOf(this.origin) + ", originRotation=" + this.originRotation + ", rotationAxis=" + String.valueOf(this.rotationAxis) + ", rotationAngle=" + String.valueOf(this.rotationAngle) + ", mirrorAxis=" + String.valueOf(this.mirrorAxis) + ", material=" + String.valueOf(this.material) + ", favoriteMaterials=" + 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 633 */         Arrays.toString((Object[])this.favoriteMaterials) + ", mask=" + String.valueOf(this.mask) + ", maskAbove=" + String.valueOf(this.maskAbove) + ", maskNot=" + String.valueOf(this.maskNot) + ", maskBelow=" + String.valueOf(this.maskBelow) + ", maskAdjacent=" + String.valueOf(this.maskAdjacent) + ", maskNeighbor=" + String.valueOf(this.maskNeighbor) + ", maskCommands=" + 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 640 */         Arrays.toString((Object[])this.maskCommands) + ", useMaskCommands=" + this.useMaskCommands + ", invertMask=" + this.invertMask + "}";
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\buildertool\config\BrushData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */