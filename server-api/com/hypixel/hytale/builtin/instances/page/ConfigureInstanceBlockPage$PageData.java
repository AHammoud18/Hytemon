/*     */ package com.hypixel.hytale.builtin.instances.page;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.EnumCodec;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PageData
/*     */ {
/*     */   public static final String INSTANCE = "@Instance";
/*     */   public static final String INSTANCE_KEY = "@InstanceKey";
/*     */   public static final String POSITION_OFFSET = "@PositionOffset";
/*     */   public static final String POSITION_OFFSET_X = "@PositionOffsetX";
/*     */   public static final String POSITION_OFFSET_Y = "@PositionOffsetY";
/*     */   public static final String POSITION_OFFSET_Z = "@PositionOffsetZ";
/*     */   public static final String ROTATION = "@Rotation";
/*     */   public static final String ROTATION_PITCH = "@RotationPitch";
/*     */   public static final String ROTATION_YAW = "@RotationYaw";
/*     */   public static final String ROTATION_ROLL = "@RotationRoll";
/*     */   public static final String PERSONAL_RETURN_POINT = "@PersonalReturnPoint";
/*     */   public static final String CLOSE_ON_BLOCK_REMOVE = "@CloseOnBlockRemove";
/*     */   public static final String REMOVE_BLOCK_AFTER = "@RemoveBlockAfter";
/*     */   public static final BuilderCodec<PageData> CODEC;
/*     */   public ConfigureInstanceBlockPage.Action action;
/*     */   public String instance;
/*     */   public String instanceKey;
/*     */   public boolean positionOffset;
/*     */   public double positionX;
/*     */   public double positionY;
/*     */   public double positionZ;
/*     */   public boolean rotation;
/*     */   public float rotationPitch;
/*     */   public float rotationYaw;
/*     */   public float rotationRoll;
/*     */   public boolean personalReturnPoint;
/*     */   public boolean closeOnBlockRemove;
/*     */   public double removeBlockAfter;
/*     */   
/*     */   static {
/* 289 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(PageData.class, PageData::new).addField(new KeyedCodec("Action", (Codec)new EnumCodec(ConfigureInstanceBlockPage.Action.class)), (o, i) -> o.action = i, o -> o.action)).addField(new KeyedCodec("@Instance", (Codec)Codec.STRING), (o, i) -> o.instance = i, o -> o.instance)).addField(new KeyedCodec("@InstanceKey", (Codec)Codec.STRING), (o, i) -> o.instanceKey = i, o -> o.instanceKey)).addField(new KeyedCodec("@PositionOffset", (Codec)Codec.BOOLEAN), (o, i) -> o.positionOffset = i.booleanValue(), o -> Boolean.valueOf(o.positionOffset))).addField(new KeyedCodec("@PositionOffsetX", (Codec)Codec.DOUBLE), (o, i) -> o.positionX = i.doubleValue(), o -> Double.valueOf(o.positionX))).addField(new KeyedCodec("@PositionOffsetY", (Codec)Codec.DOUBLE), (o, i) -> o.positionY = i.doubleValue(), o -> Double.valueOf(o.positionY))).addField(new KeyedCodec("@PositionOffsetZ", (Codec)Codec.DOUBLE), (o, i) -> o.positionZ = i.doubleValue(), o -> Double.valueOf(o.positionZ))).addField(new KeyedCodec("@Rotation", (Codec)Codec.BOOLEAN), (o, i) -> o.rotation = i.booleanValue(), o -> Boolean.valueOf(o.rotation))).addField(new KeyedCodec("@RotationPitch", (Codec)Codec.FLOAT), (o, i) -> o.rotationPitch = i.floatValue(), o -> Float.valueOf(o.rotationPitch))).addField(new KeyedCodec("@RotationYaw", (Codec)Codec.FLOAT), (o, i) -> o.rotationYaw = i.floatValue(), o -> Float.valueOf(o.rotationYaw))).addField(new KeyedCodec("@RotationRoll", (Codec)Codec.FLOAT), (o, i) -> o.rotationRoll = i.floatValue(), o -> Float.valueOf(o.rotationRoll))).addField(new KeyedCodec("@PersonalReturnPoint", (Codec)Codec.BOOLEAN), (o, i) -> o.personalReturnPoint = i.booleanValue(), o -> Boolean.valueOf(o.personalReturnPoint))).addField(new KeyedCodec("@CloseOnBlockRemove", (Codec)Codec.BOOLEAN), (o, i) -> o.closeOnBlockRemove = i.booleanValue(), o -> Boolean.valueOf(o.closeOnBlockRemove))).addField(new KeyedCodec("@RemoveBlockAfter", (Codec)Codec.DOUBLE), (o, i) -> o.removeBlockAfter = i.doubleValue(), o -> Double.valueOf(o.removeBlockAfter))).build();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\instances\page\ConfigureInstanceBlockPage$PageData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */