/*     */ package com.hypixel.hytale.server.core.codec;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.EnumCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.schema.metadata.HytaleType;
/*     */ import com.hypixel.hytale.codec.schema.metadata.Metadata;
/*     */ import com.hypixel.hytale.codec.schema.metadata.ui.UIDisplayMode;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.math.vector.Transform;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.protocol.AccumulationMode;
/*     */ import com.hypixel.hytale.protocol.ChangeStatBehaviour;
/*     */ import com.hypixel.hytale.protocol.ChangeVelocityType;
/*     */ import com.hypixel.hytale.protocol.Color;
/*     */ import com.hypixel.hytale.protocol.ColorLight;
/*     */ import com.hypixel.hytale.protocol.Direction;
/*     */ import com.hypixel.hytale.protocol.EasingType;
/*     */ import com.hypixel.hytale.protocol.GameMode;
/*     */ import com.hypixel.hytale.protocol.InitialVelocity;
/*     */ import com.hypixel.hytale.protocol.IntersectionHighlight;
/*     */ import com.hypixel.hytale.protocol.ItemAnimation;
/*     */ import com.hypixel.hytale.protocol.RailConfig;
/*     */ import com.hypixel.hytale.protocol.RailPoint;
/*     */ import com.hypixel.hytale.protocol.Range;
/*     */ import com.hypixel.hytale.protocol.RangeVector2f;
/*     */ import com.hypixel.hytale.protocol.RangeVector3f;
/*     */ import com.hypixel.hytale.protocol.Rangeb;
/*     */ import com.hypixel.hytale.protocol.Rangef;
/*     */ import com.hypixel.hytale.protocol.SavedMovementStates;
/*     */ import com.hypixel.hytale.protocol.Size;
/*     */ import com.hypixel.hytale.protocol.UVMotion;
/*     */ import com.hypixel.hytale.protocol.UVMotionCurveType;
/*     */ import com.hypixel.hytale.protocol.Vector2f;
/*     */ import com.hypixel.hytale.protocol.Vector3f;
/*     */ import com.hypixel.hytale.protocol.packets.worldmap.ContextMenuItem;
/*     */ import com.hypixel.hytale.protocol.packets.worldmap.MapMarker;
/*     */ import com.hypixel.hytale.server.core.asset.common.BlockyAnimationCache;
/*     */ import com.hypixel.hytale.server.core.asset.common.CommonAssetValidator;
/*     */ import com.hypixel.hytale.server.core.asset.util.ColorParseUtil;
/*     */ import com.hypixel.hytale.server.core.codec.protocol.ColorAlphaCodec;
/*     */ import com.hypixel.hytale.server.core.codec.protocol.ColorCodec;
/*     */ import com.hypixel.hytale.server.core.util.PositionUtil;
/*     */ import java.util.function.Supplier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ProtocolCodecs
/*     */ {
/*     */   public static final BuilderCodec<Direction> DIRECTION;
/*     */   public static final BuilderCodec<Vector2f> VECTOR2F;
/*     */   public static final BuilderCodec<Vector3f> VECTOR3F;
/*     */   public static final BuilderCodec<ColorLight> COLOR_LIGHT;
/*     */   
/*     */   static {
/*  71 */     DIRECTION = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(Direction.class, Direction::new).metadata((Metadata)UIDisplayMode.COMPACT)).appendInherited(new KeyedCodec("Yaw", (Codec)Codec.FLOAT), (o, i) -> o.yaw = i.floatValue(), o -> Float.valueOf(o.yaw), (o, p) -> o.yaw = p.yaw).add()).appendInherited(new KeyedCodec("Pitch", (Codec)Codec.FLOAT), (o, i) -> o.pitch = i.floatValue(), o -> Float.valueOf(o.pitch), (o, p) -> o.pitch = p.pitch).add()).appendInherited(new KeyedCodec("Roll", (Codec)Codec.FLOAT), (o, i) -> o.roll = i.floatValue(), o -> Float.valueOf(o.roll), (o, p) -> o.roll = p.roll).add()).build();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  89 */     VECTOR2F = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(Vector2f.class, Vector2f::new).metadata((Metadata)UIDisplayMode.COMPACT)).appendInherited(new KeyedCodec("X", (Codec)Codec.FLOAT), (o, i) -> o.x = i.floatValue(), o -> Float.valueOf(o.x), (o, p) -> o.x = p.x).add()).appendInherited(new KeyedCodec("Y", (Codec)Codec.FLOAT), (o, i) -> o.y = i.floatValue(), o -> Float.valueOf(o.y), (o, p) -> o.y = p.y).add()).build();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 114 */     VECTOR3F = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(Vector3f.class, Vector3f::new).metadata((Metadata)UIDisplayMode.COMPACT)).appendInherited(new KeyedCodec("X", (Codec)Codec.FLOAT), (o, i) -> o.x = i.floatValue(), o -> Float.valueOf(o.x), (o, p) -> o.x = p.x).add()).appendInherited(new KeyedCodec("Y", (Codec)Codec.FLOAT), (o, i) -> o.y = i.floatValue(), o -> Float.valueOf(o.y), (o, p) -> o.y = p.y).add()).appendInherited(new KeyedCodec("Z", (Codec)Codec.FLOAT), (o, i) -> o.z = i.floatValue(), o -> Float.valueOf(o.z), (o, p) -> o.z = p.z).add()).build();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 136 */     COLOR_LIGHT = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ColorLight.class, ColorLight::new).appendInherited(new KeyedCodec("Color", (Codec)Codec.STRING), ColorParseUtil::hexStringToColorLightDirect, ColorParseUtil::colorLightToHexString, (o, p) -> { o.red = p.red; o.green = p.green; o.blue = p.blue; }).metadata((Metadata)new HytaleType("ColorShort")).add()).appendInherited(new KeyedCodec("Radius", (Codec)Codec.BYTE), (o, i) -> o.radius = i.byteValue(), o -> Byte.valueOf(o.radius), (o, p) -> o.radius = p.radius).add()).build();
/*     */   }
/* 138 */   public static final ColorCodec COLOR = new ColorCodec(); public static final ArrayCodec<Color> COLOR_ARRAY; static {
/* 139 */     COLOR_ARRAY = new ArrayCodec((Codec)COLOR, x$0 -> new Color[x$0]);
/* 140 */   } public static final ColorAlphaCodec COLOR_AlPHA = new ColorAlphaCodec();
/*     */   
/* 142 */   public static final EnumCodec<GameMode> GAMEMODE = (new EnumCodec(GameMode.class))
/* 143 */     .documentKey((Enum)GameMode.Creative, "Makes the player invulnerable and grants them the ability to fly.")
/* 144 */     .documentKey((Enum)GameMode.Adventure, "The normal gamemode for players playing the game.");
/* 145 */   public static final EnumCodec<GameMode> GAMEMODE_LEGACY = new EnumCodec(GameMode.class, EnumCodec.EnumStyle.LEGACY); public static final BuilderCodec<Size> SIZE; public static final BuilderCodec<Range> RANGE; public static final BuilderCodec<Rangeb> RANGEB; public static final BuilderCodec<Rangef> RANGEF;
/*     */   public static final BuilderCodec<RangeVector2f> RANGE_VECTOR2F;
/*     */   public static final BuilderCodec<RangeVector3f> RANGE_VECTOR3F;
/*     */   public static final BuilderCodec<InitialVelocity> INITIAL_VELOCITY;
/*     */   public static final BuilderCodec<UVMotion> UV_MOTION;
/*     */   public static final BuilderCodec<IntersectionHighlight> INTERSECTION_HIGHLIGHT;
/*     */   public static final BuilderCodec<SavedMovementStates> SAVED_MOVEMENT_STATES;
/*     */   public static final BuilderCodec<ContextMenuItem> CONTEXT_MENU_ITEM;
/*     */   public static final ArrayCodec<ContextMenuItem> CONTEXT_MENU_ITEM_ARRAY;
/*     */   public static final BuilderCodec<MapMarker> MARKER;
/*     */   public static final ArrayCodec<MapMarker> MARKER_ARRAY;
/*     */   public static final BuilderCodec<ItemAnimation> ITEM_ANIMATION_CODEC;
/*     */   
/*     */   static {
/* 159 */     SIZE = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(Size.class, Size::new).metadata((Metadata)UIDisplayMode.COMPACT)).addField(new KeyedCodec("Width", (Codec)Codec.INTEGER), (size, i) -> size.width = i.intValue(), size -> Integer.valueOf(size.width))).addField(new KeyedCodec("Height", (Codec)Codec.INTEGER), (size, i) -> size.height = i.intValue(), size -> Integer.valueOf(size.height))).build();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 173 */     RANGE = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(Range.class, Range::new).metadata((Metadata)UIDisplayMode.COMPACT)).addField(new KeyedCodec("Min", (Codec)Codec.INTEGER), (rangeb, i) -> rangeb.min = i.intValue(), rangeb -> Integer.valueOf(rangeb.min))).addField(new KeyedCodec("Max", (Codec)Codec.INTEGER), (rangeb, i) -> rangeb.max = i.intValue(), rangeb -> Integer.valueOf(rangeb.max))).build();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 187 */     RANGEB = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(Rangeb.class, Rangeb::new).metadata((Metadata)UIDisplayMode.COMPACT)).addField(new KeyedCodec("Min", (Codec)Codec.BYTE), (rangeb, i) -> rangeb.min = i.byteValue(), rangeb -> Byte.valueOf(rangeb.min))).addField(new KeyedCodec("Max", (Codec)Codec.BYTE), (rangeb, i) -> rangeb.max = i.byteValue(), rangeb -> Byte.valueOf(rangeb.max))).build();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 201 */     RANGEF = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(Rangef.class, Rangef::new).metadata((Metadata)UIDisplayMode.COMPACT)).addField(new KeyedCodec("Min", (Codec)Codec.DOUBLE), (rangef, d) -> rangef.min = d.floatValue(), rangeb -> Double.valueOf(rangeb.min))).addField(new KeyedCodec("Max", (Codec)Codec.DOUBLE), (rangef, d) -> rangef.max = d.floatValue(), rangeb -> Double.valueOf(rangeb.max))).build();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 214 */     RANGE_VECTOR2F = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(RangeVector2f.class, RangeVector2f::new).addField(new KeyedCodec("X", (Codec)RANGEF), (rangeVector2f, d) -> rangeVector2f.x = d, rangeVector2f -> rangeVector2f.x)).addField(new KeyedCodec("Y", (Codec)RANGEF), (rangeVector2f, d) -> rangeVector2f.y = d, rangeVector2f -> rangeVector2f.y)).build();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 232 */     RANGE_VECTOR3F = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(RangeVector3f.class, RangeVector3f::new).addField(new KeyedCodec("X", (Codec)RANGEF), (rangeVector3f, d) -> rangeVector3f.x = d, rangeVector3f -> rangeVector3f.x)).addField(new KeyedCodec("Y", (Codec)RANGEF), (rangeVector3f, d) -> rangeVector3f.y = d, rangeVector3f -> rangeVector3f.y)).addField(new KeyedCodec("Z", (Codec)RANGEF), (rangeVector3f, d) -> rangeVector3f.z = d, rangeVector3f -> rangeVector3f.z)).build();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 250 */     INITIAL_VELOCITY = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(InitialVelocity.class, InitialVelocity::new).addField(new KeyedCodec("Yaw", (Codec)RANGEF), (rangeVector3f, d) -> rangeVector3f.yaw = d, rangeVector3f -> rangeVector3f.yaw)).addField(new KeyedCodec("Pitch", (Codec)RANGEF), (rangeVector3f, d) -> rangeVector3f.pitch = d, rangeVector3f -> rangeVector3f.pitch)).addField(new KeyedCodec("Speed", (Codec)RANGEF), (rangeVector3f, d) -> rangeVector3f.speed = d, rangeVector3f -> rangeVector3f.speed)).build();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 300 */     UV_MOTION = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(UVMotion.class, UVMotion::new).append(new KeyedCodec("Texture", (Codec)Codec.STRING), (uvMotion, s) -> uvMotion.texture = s, uvMotion -> uvMotion.texture).addValidator((Validator)CommonAssetValidator.TEXTURE_PARTICLES).add()).append(new KeyedCodec("AddRandomUVOffset", (Codec)Codec.BOOLEAN), (uvMotion, b) -> uvMotion.addRandomUVOffset = b.booleanValue(), uvMotion -> Boolean.valueOf(uvMotion.addRandomUVOffset)).add()).append(new KeyedCodec("SpeedX", (Codec)Codec.DOUBLE), (uvMotion, s) -> uvMotion.speedX = s.floatValue(), uvMotion -> Double.valueOf(uvMotion.speedX)).addValidator(Validators.range(Double.valueOf(-10.0D), Double.valueOf(10.0D))).add()).append(new KeyedCodec("SpeedY", (Codec)Codec.DOUBLE), (uvMotion, s) -> uvMotion.speedY = s.floatValue(), uvMotion -> Double.valueOf(uvMotion.speedY)).addValidator(Validators.range(Double.valueOf(-10.0D), Double.valueOf(10.0D))).add()).append(new KeyedCodec("Strength", (Codec)Codec.DOUBLE), (uvMotion, s) -> uvMotion.strength = s.floatValue(), uvMotion -> Double.valueOf(uvMotion.strength)).addValidator(Validators.range(Double.valueOf(0.0D), Double.valueOf(50.0D))).add()).append(new KeyedCodec("StrengthCurveType", (Codec)new EnumCodec(UVMotionCurveType.class)), (uvMotion, s) -> uvMotion.strengthCurveType = s, uvMotion -> uvMotion.strengthCurveType).add()).append(new KeyedCodec("Scale", (Codec)Codec.DOUBLE), (uvMotion, s) -> uvMotion.scale = s.floatValue(), uvMotion -> Double.valueOf(uvMotion.scale)).addValidator(Validators.range(Double.valueOf(0.0D), Double.valueOf(10.0D))).add()).build();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 313 */     INTERSECTION_HIGHLIGHT = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(IntersectionHighlight.class, IntersectionHighlight::new).append(new KeyedCodec("HighlightThreshold", (Codec)Codec.FLOAT), (intersectionHighlight, s) -> intersectionHighlight.highlightThreshold = s.floatValue(), intersectionHighlight -> Float.valueOf(intersectionHighlight.highlightThreshold)).addValidator(Validators.range(Float.valueOf(0.0F), Float.valueOf(1.0F))).add()).addField(new KeyedCodec("HighlightColor", (Codec)COLOR), (intersectionHighlight, s) -> intersectionHighlight.highlightColor = s, intersectionHighlight -> intersectionHighlight.highlightColor)).build();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 321 */     SAVED_MOVEMENT_STATES = ((BuilderCodec.Builder)BuilderCodec.builder(SavedMovementStates.class, SavedMovementStates::new).addField(new KeyedCodec("Flying", (Codec)Codec.BOOLEAN), (movementStates, flying) -> movementStates.flying = flying.booleanValue(), movementStates -> Boolean.valueOf(movementStates.flying))).build();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 334 */     CONTEXT_MENU_ITEM = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ContextMenuItem.class, ContextMenuItem::new).addField(new KeyedCodec("Name", (Codec)Codec.STRING), (item, s) -> item.name = s, item -> item.name)).addField(new KeyedCodec("Command", (Codec)Codec.STRING), (item, s) -> item.command = s, item -> item.command)).build();
/*     */     
/* 336 */     CONTEXT_MENU_ITEM_ARRAY = new ArrayCodec((Codec)CONTEXT_MENU_ITEM, x$0 -> new ContextMenuItem[x$0]);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 364 */     MARKER = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(MapMarker.class, MapMarker::new).addField(new KeyedCodec("Id", (Codec)Codec.STRING), (marker, s) -> marker.id = s, marker -> marker.id)).addField(new KeyedCodec("Name", (Codec)Codec.STRING), (marker, s) -> marker.name = s, marker -> marker.name)).addField(new KeyedCodec("Image", (Codec)Codec.STRING), (marker, s) -> marker.markerImage = s, marker -> marker.markerImage)).append(new KeyedCodec("Transform", (Codec)Transform.CODEC), (marker, s) -> marker.transform = PositionUtil.toTransformPacket(s), marker -> PositionUtil.toTransform(marker.transform)).addValidator(Validators.nonNull()).add()).addField(new KeyedCodec("ContextMenuItems", (Codec)CONTEXT_MENU_ITEM_ARRAY), (marker, items) -> marker.contextMenuItems = items, marker -> marker.contextMenuItems)).build();
/*     */     
/* 366 */     MARKER_ARRAY = new ArrayCodec((Codec)MARKER, x$0 -> new MapMarker[x$0]);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 425 */     ITEM_ANIMATION_CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ItemAnimation.class, ItemAnimation::new).append(new KeyedCodec("ThirdPerson", (Codec)Codec.STRING), (itemAnimation, s) -> itemAnimation.thirdPerson = s, itemAnimation -> itemAnimation.thirdPerson).addValidator((Validator)CommonAssetValidator.ANIMATION_ITEM_CHARACTER).add()).append(new KeyedCodec("ThirdPersonMoving", (Codec)Codec.STRING), (itemAnimation, s) -> itemAnimation.thirdPersonMoving = s, itemAnimation -> itemAnimation.thirdPersonMoving).addValidator((Validator)CommonAssetValidator.ANIMATION_ITEM_CHARACTER).add()).append(new KeyedCodec("ThirdPersonFace", (Codec)Codec.STRING), (itemAnimation, s) -> itemAnimation.thirdPersonFace = s, itemAnimation -> itemAnimation.thirdPersonFace).addValidator((Validator)CommonAssetValidator.ANIMATION_ITEM_CHARACTER).add()).append(new KeyedCodec("FirstPerson", (Codec)Codec.STRING), (itemAnimation, s) -> itemAnimation.firstPerson = s, itemAnimation -> itemAnimation.firstPerson).addValidator((Validator)CommonAssetValidator.ANIMATION_ITEM_CHARACTER).add()).append(new KeyedCodec("FirstPersonOverride", (Codec)Codec.STRING), (itemAnimation, s) -> itemAnimation.firstPersonOverride = s, itemAnimation -> itemAnimation.firstPersonOverride).addValidator((Validator)CommonAssetValidator.ANIMATION_ITEM_CHARACTER).add()).addField(new KeyedCodec("KeepPreviousFirstPersonAnimation", (Codec)Codec.BOOLEAN), (itemAnimation, s) -> itemAnimation.keepPreviousFirstPersonAnimation = s.booleanValue(), itemAnimation -> Boolean.valueOf(itemAnimation.keepPreviousFirstPersonAnimation))).addField(new KeyedCodec("Speed", (Codec)Codec.DOUBLE), (itemAnimation, s) -> itemAnimation.speed = s.floatValue(), itemAnimation -> Double.valueOf(itemAnimation.speed))).addField(new KeyedCodec("BlendingDuration", (Codec)Codec.DOUBLE), (itemAnimation, s) -> itemAnimation.blendingDuration = s.floatValue(), itemAnimation -> Double.valueOf(itemAnimation.blendingDuration))).addField(new KeyedCodec("Looping", (Codec)Codec.BOOLEAN), (itemAnimation, s) -> itemAnimation.looping = s.booleanValue(), itemAnimation -> Boolean.valueOf(itemAnimation.looping))).addField(new KeyedCodec("ClipsGeometry", (Codec)Codec.BOOLEAN), (itemAnimation, s) -> itemAnimation.clipsGeometry = s.booleanValue(), itemAnimation -> Boolean.valueOf(itemAnimation.clipsGeometry))).afterDecode(itemAnimation -> { if (itemAnimation.firstPerson != null) BlockyAnimationCache.get(itemAnimation.firstPerson);  if (itemAnimation.firstPersonOverride != null) BlockyAnimationCache.get(itemAnimation.firstPersonOverride);  })).build();
/*     */   }
/* 427 */   public static final EnumCodec<ChangeStatBehaviour> CHANGE_STAT_BEHAVIOUR_CODEC = (new EnumCodec(ChangeStatBehaviour.class))
/* 428 */     .documentKey((Enum)ChangeStatBehaviour.Add, "Adds the value to the stat")
/* 429 */     .documentKey((Enum)ChangeStatBehaviour.Set, "Changes the stat to the given value");
/*     */   
/* 431 */   public static final EnumCodec<AccumulationMode> ACCUMULATION_MODE_CODEC = (new EnumCodec(AccumulationMode.class))
/* 432 */     .documentKey((Enum)AccumulationMode.Set, "Set the current value to the new one")
/* 433 */     .documentKey((Enum)AccumulationMode.Sum, "Add the new value to the current one")
/* 434 */     .documentKey((Enum)AccumulationMode.Average, "Average the new value with current one");
/*     */   
/* 436 */   public static final EnumCodec<EasingType> EASING_TYPE_CODEC = new EnumCodec(EasingType.class);
/*     */   
/* 438 */   public static final EnumCodec<ChangeVelocityType> CHANGE_VELOCITY_TYPE_CODEC = (new EnumCodec(ChangeVelocityType.class))
/* 439 */     .documentKey((Enum)ChangeVelocityType.Add, "Adds the velocity to any existing velocity")
/* 440 */     .documentKey((Enum)ChangeVelocityType.Set, "Changes the velocity to the given value. Overriding existing values.");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final BuilderCodec<RailPoint> RAIL_POINT_CODEC;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final BuilderCodec<RailConfig> RAIL_CONFIG_CODEC;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 468 */     RAIL_POINT_CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(RailPoint.class, RailPoint::new).appendInherited(new KeyedCodec("Point", (Codec)VECTOR3F), (o, v) -> o.point = v, o -> o.point, (o, p) -> o.point = p.point).addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("Normal", (Codec)VECTOR3F), (o, v) -> o.normal = v, o -> o.normal, (o, p) -> o.normal = p.normal).addValidator(Validators.nonNull()).add()).afterDecode(o -> { if (o.normal != null) { Vector3f v = new Vector3f(o.normal.x, o.normal.y, o.normal.z); v = v.normalize(); o.normal.x = v.x; o.normal.y = v.y; o.normal.z = v.z; }  })).build();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 479 */     RAIL_CONFIG_CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(RailConfig.class, RailConfig::new).appendInherited(new KeyedCodec("Points", (Codec)new ArrayCodec((Codec)RAIL_POINT_CODEC, x$0 -> new RailPoint[x$0])), (o, v) -> o.points = v, o -> o.points, (o, p) -> o.points = p.points).addValidator(Validators.nonNull()).addValidator(Validators.arraySizeRange(2, 16)).add()).build();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\codec\ProtocolCodecs.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */