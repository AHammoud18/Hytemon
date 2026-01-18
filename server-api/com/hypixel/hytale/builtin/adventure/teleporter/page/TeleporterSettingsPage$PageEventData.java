/*     */ package com.hypixel.hytale.builtin.adventure.teleporter.page;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PageEventData
/*     */ {
/*     */   public static final String KEY_BLOCK_RELATIVE = "@BlockRelative";
/*     */   public static final String KEY_X = "@X";
/*     */   public static final String KEY_Y = "@Y";
/*     */   public static final String KEY_Z = "@Z";
/*     */   public static final String KEY_X_IS_RELATIVE = "@XIsRelative";
/*     */   public static final String KEY_Y_IS_RELATIVE = "@YIsRelative";
/*     */   public static final String KEY_Z_IS_RELATIVE = "@ZIsRelative";
/*     */   public static final String KEY_YAW = "@Yaw";
/*     */   public static final String KEY_PITCH = "@Pitch";
/*     */   public static final String KEY_ROLL = "@Roll";
/*     */   public static final String KEY_YAW_IS_RELATIVE = "@YawIsRelative";
/*     */   public static final String KEY_PITCH_IS_RELATIVE = "@PitchIsRelative";
/*     */   public static final String KEY_ROLL_IS_RELATIVE = "@RollIsRelative";
/*     */   public static final String KEY_WORLD = "@World";
/*     */   public static final String KEY_WARP = "@Warp";
/*     */   public static final String KEY_NEW_WARP = "@NewWarp";
/*     */   public static final BuilderCodec<PageEventData> CODEC;
/*     */   public boolean isBlockRelative;
/*     */   public double x;
/*     */   public double y;
/*     */   public double z;
/*     */   public boolean xIsRelative;
/*     */   public boolean yIsRelative;
/*     */   public boolean zIsRelative;
/*     */   public float yaw;
/*     */   public float pitch;
/*     */   public float roll;
/*     */   public boolean yawIsRelative;
/*     */   public boolean pitchIsRelative;
/*     */   public boolean rollIsRelative;
/*     */   public String world;
/*     */   public String warp;
/*     */   @Nullable
/*     */   public String ownedWarp;
/*     */   
/*     */   static {
/* 411 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(PageEventData.class, PageEventData::new).append(new KeyedCodec("@BlockRelative", (Codec)Codec.BOOLEAN), (pageEventData, o) -> pageEventData.isBlockRelative = o.booleanValue(), pageEventData -> Boolean.valueOf(pageEventData.isBlockRelative)).add()).append(new KeyedCodec("@X", (Codec)Codec.DOUBLE), (pageEventData, o) -> pageEventData.x = o.doubleValue(), pageEventData -> Double.valueOf(pageEventData.x)).add()).append(new KeyedCodec("@Y", (Codec)Codec.DOUBLE), (pageEventData, o) -> pageEventData.y = o.doubleValue(), pageEventData -> Double.valueOf(pageEventData.y)).add()).append(new KeyedCodec("@Z", (Codec)Codec.DOUBLE), (pageEventData, o) -> pageEventData.z = o.doubleValue(), pageEventData -> Double.valueOf(pageEventData.z)).add()).append(new KeyedCodec("@XIsRelative", (Codec)Codec.BOOLEAN), (pageEventData, o) -> pageEventData.xIsRelative = o.booleanValue(), pageEventData -> Boolean.valueOf(pageEventData.xIsRelative)).add()).append(new KeyedCodec("@YIsRelative", (Codec)Codec.BOOLEAN), (pageEventData, o) -> pageEventData.yIsRelative = o.booleanValue(), pageEventData -> Boolean.valueOf(pageEventData.yIsRelative)).add()).append(new KeyedCodec("@ZIsRelative", (Codec)Codec.BOOLEAN), (pageEventData, o) -> pageEventData.zIsRelative = o.booleanValue(), pageEventData -> Boolean.valueOf(pageEventData.zIsRelative)).add()).append(new KeyedCodec("@Yaw", (Codec)Codec.FLOAT), (pageEventData, o) -> pageEventData.yaw = o.floatValue(), pageEventData -> Float.valueOf(pageEventData.yaw)).add()).append(new KeyedCodec("@Pitch", (Codec)Codec.FLOAT), (pageEventData, o) -> pageEventData.pitch = o.floatValue(), pageEventData -> Float.valueOf(pageEventData.pitch)).add()).append(new KeyedCodec("@Roll", (Codec)Codec.FLOAT), (pageEventData, o) -> pageEventData.roll = o.floatValue(), pageEventData -> Float.valueOf(pageEventData.roll)).add()).append(new KeyedCodec("@YawIsRelative", (Codec)Codec.BOOLEAN), (pageEventData, o) -> pageEventData.yawIsRelative = o.booleanValue(), pageEventData -> Boolean.valueOf(pageEventData.yawIsRelative)).add()).append(new KeyedCodec("@PitchIsRelative", (Codec)Codec.BOOLEAN), (pageEventData, o) -> pageEventData.pitchIsRelative = o.booleanValue(), pageEventData -> Boolean.valueOf(pageEventData.pitchIsRelative)).add()).append(new KeyedCodec("@RollIsRelative", (Codec)Codec.BOOLEAN), (pageEventData, o) -> pageEventData.rollIsRelative = o.booleanValue(), pageEventData -> Boolean.valueOf(pageEventData.pitchIsRelative)).add()).append(new KeyedCodec("@World", (Codec)Codec.STRING), (pageEventData, o) -> pageEventData.world = o, pageEventData -> pageEventData.world).add()).append(new KeyedCodec("@Warp", (Codec)Codec.STRING), (pageEventData, o) -> pageEventData.warp = o, pageEventData -> pageEventData.warp).add()).append(new KeyedCodec("@NewWarp", (Codec)Codec.STRING), (pageEventData, o) -> pageEventData.ownedWarp = o, pageEventData -> pageEventData.ownedWarp).add()).build();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\teleporter\page\TeleporterSettingsPage$PageEventData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */