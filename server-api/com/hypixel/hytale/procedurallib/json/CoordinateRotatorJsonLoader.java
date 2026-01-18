/*    */ package com.hypixel.hytale.procedurallib.json;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.procedurallib.random.CoordinateOriginRotator;
/*    */ import com.hypixel.hytale.procedurallib.random.CoordinateRotator;
/*    */ import java.nio.file.Path;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CoordinateRotatorJsonLoader<K extends SeedResource>
/*    */   extends JsonLoader<K, CoordinateRotator>
/*    */ {
/*    */   public CoordinateRotatorJsonLoader(SeedString<K> seed, Path dataFolder, JsonElement json) {
/* 15 */     super(seed, dataFolder, json);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public CoordinateRotator load() {
/* 21 */     double pitch = has("Pitch") ? (get("Pitch").getAsDouble() * 0.01745329238474369D) : 0.0D;
/* 22 */     double yaw = has("Yaw") ? (get("Yaw").getAsDouble() * 0.01745329238474369D) : 0.0D;
/*    */     
/* 24 */     if (pitch == 0.0D && yaw == 0.0D) return CoordinateRotator.NONE;
/*    */     
/* 26 */     double originX = has("OriginX") ? get("OriginX").getAsDouble() : 0.0D;
/* 27 */     double originY = has("OriginY") ? get("OriginY").getAsDouble() : 0.0D;
/* 28 */     double originZ = has("OriginZ") ? get("OriginZ").getAsDouble() : 0.0D;
/*    */     
/* 30 */     if (originX == 0.0D && originY == 0.0D && originZ == 0.0D) return new CoordinateRotator(pitch, yaw);
/*    */     
/* 32 */     return (CoordinateRotator)new CoordinateOriginRotator(pitch, yaw, originX, originY, originZ);
/*    */   }
/*    */   
/*    */   public static interface Constants {
/*    */     public static final String KEY_ROTATE = "Rotate";
/*    */     public static final String KEY_PITCH = "Pitch";
/*    */     public static final String KEY_YAW = "Yaw";
/*    */     public static final String KEY_ORIGIN_X = "OriginX";
/*    */     public static final String KEY_ORIGIN_Y = "OriginY";
/*    */     public static final String KEY_ORIGIN_Z = "OriginZ";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\json\CoordinateRotatorJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */