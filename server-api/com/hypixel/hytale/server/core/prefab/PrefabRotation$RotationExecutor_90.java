/*     */ package com.hypixel.hytale.server.core.prefab;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class RotationExecutor_90
/*     */   implements PrefabRotation.RotationExecutor
/*     */ {
/*     */   public float getYaw() {
/* 150 */     return -1.5707964F;
/*     */   }
/*     */ 
/*     */   
/*     */   public int rotateIntX(int x, int z) {
/* 155 */     return z;
/*     */   }
/*     */ 
/*     */   
/*     */   public long rotateLongX(long x, long z) {
/* 160 */     return z;
/*     */   }
/*     */ 
/*     */   
/*     */   public double rotateDoubleX(double x, double z) {
/* 165 */     return z;
/*     */   }
/*     */ 
/*     */   
/*     */   public int rotateIntZ(int x, int z) {
/* 170 */     return -x;
/*     */   }
/*     */ 
/*     */   
/*     */   public long rotateLongZ(long x, long z) {
/* 175 */     return -x;
/*     */   }
/*     */ 
/*     */   
/*     */   public double rotateDoubleZ(double x, double z) {
/* 180 */     return -x;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\prefab\PrefabRotation$RotationExecutor_90.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */