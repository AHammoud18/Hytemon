/*    */ package com.hypixel.hytale.server.core.universe.world.worldmap;
/*    */ 
/*    */ import com.hypixel.hytale.math.shape.Box2D;
/*    */ import com.hypixel.hytale.math.util.MathUtil;
/*    */ import com.hypixel.hytale.protocol.packets.worldmap.UpdateWorldMapSettings;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldMapSettings
/*    */ {
/* 17 */   public static final WorldMapSettings DISABLED = new WorldMapSettings();
/*    */ 
/*    */   
/*    */   private Box2D worldMapArea;
/*    */ 
/*    */   
/* 23 */   private float imageScale = 0.5F;
/* 24 */   private float viewRadiusMultiplier = 1.0F;
/* 25 */   private int viewRadiusMin = 1;
/* 26 */   private int viewRadiusMax = 512;
/*    */   
/*    */   @Nonnull
/*    */   private UpdateWorldMapSettings settingsPacket;
/*    */ 
/*    */   
/*    */   public WorldMapSettings() {
/* 33 */     this.settingsPacket = new UpdateWorldMapSettings();
/* 34 */     this.settingsPacket.enabled = false;
/*    */   }
/*    */   
/*    */   public WorldMapSettings(Box2D worldMapArea, float imageScale, float viewRadiusMultiplier, int viewRadiusMin, int viewRadiusMax, @Nonnull UpdateWorldMapSettings settingsPacket) {
/* 38 */     this.worldMapArea = worldMapArea;
/* 39 */     this.imageScale = imageScale;
/* 40 */     this.viewRadiusMultiplier = viewRadiusMultiplier;
/* 41 */     this.viewRadiusMin = viewRadiusMin;
/* 42 */     this.viewRadiusMax = viewRadiusMax;
/* 43 */     this.settingsPacket = settingsPacket;
/*    */   }
/*    */   
/*    */   public Box2D getWorldMapArea() {
/* 47 */     return this.worldMapArea;
/*    */   }
/*    */   
/*    */   public float getImageScale() {
/* 51 */     return this.imageScale;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public UpdateWorldMapSettings getSettingsPacket() {
/* 56 */     return this.settingsPacket;
/*    */   }
/*    */   
/*    */   public int getViewRadius(int viewRadius) {
/* 60 */     return MathUtil.clamp(
/* 61 */         Math.round(viewRadius * this.viewRadiusMultiplier), this.viewRadiusMin, this.viewRadiusMax);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 70 */     return "WorldMapSettings{worldMapArea=" + String.valueOf(this.worldMapArea) + ", imageScale=" + this.imageScale + ", viewRadiusMultiplier=" + this.viewRadiusMultiplier + ", viewRadiusMin=" + this.viewRadiusMin + ", viewRadiusMax=" + this.viewRadiusMax + ", settingsPacket=" + String.valueOf(this.settingsPacket) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\worldmap\WorldMapSettings.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */