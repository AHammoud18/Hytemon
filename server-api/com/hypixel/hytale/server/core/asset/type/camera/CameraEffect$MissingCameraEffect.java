/*     */ package com.hypixel.hytale.server.core.asset.type.camera;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.packets.camera.CameraShakeEffect;
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
/*     */ public class MissingCameraEffect
/*     */   extends CameraEffect
/*     */ {
/*     */   public MissingCameraEffect(@Nonnull String id) {}
/*     */   
/*     */   @Nonnull
/*     */   public CameraShakeEffect createCameraShakePacket() {
/* 106 */     return new CameraShakeEffect();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public CameraShakeEffect createCameraShakePacket(float intensityContext) {
/* 112 */     return new CameraShakeEffect();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 118 */     return "MissingShakeEffect{}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\camera\CameraEffect$MissingCameraEffect.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */