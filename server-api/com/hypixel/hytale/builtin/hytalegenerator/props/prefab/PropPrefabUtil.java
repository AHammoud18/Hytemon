/*    */ package com.hypixel.hytale.builtin.hytalegenerator.props.prefab;
/*    */ 
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.server.core.prefab.PrefabRotation;
/*    */ import com.hypixel.hytale.server.core.prefab.selection.buffer.impl.PrefabBuffer;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class PropPrefabUtil
/*    */ {
/*    */   @Nonnull
/*    */   public static Vector3i getMin(@Nonnull PrefabBuffer.PrefabBufferAccessor prefab) {
/* 13 */     int minX = prefab.getMinX(PrefabRotation.ROTATION_0);
/* 14 */     int minY = prefab.getMinY();
/* 15 */     int minZ = prefab.getMinZ(PrefabRotation.ROTATION_0);
/* 16 */     return new Vector3i(minX, minY, minZ);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static Vector3i getMax(@Nonnull PrefabBuffer.PrefabBufferAccessor prefab) {
/* 21 */     int maxX = prefab.getMaxX(PrefabRotation.ROTATION_0);
/* 22 */     int maxY = prefab.getMaxY();
/* 23 */     int maxZ = prefab.getMaxZ(PrefabRotation.ROTATION_0);
/* 24 */     return new Vector3i(maxX, maxY, maxZ);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public static Vector3i getMin(@Nonnull PrefabBuffer.PrefabBufferAccessor prefab, @Nonnull PrefabRotation rotation) {
/* 30 */     int minX = prefab.getMinX(rotation);
/* 31 */     int minY = prefab.getMinY();
/* 32 */     int minZ = prefab.getMinZ(rotation);
/* 33 */     return new Vector3i(minX, minY, minZ);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public static Vector3i getMax(@Nonnull PrefabBuffer.PrefabBufferAccessor prefab, @Nonnull PrefabRotation rotation) {
/* 39 */     int maxX = prefab.getMaxX(rotation);
/* 40 */     int maxY = prefab.getMaxY();
/* 41 */     int maxZ = prefab.getMaxZ(rotation);
/* 42 */     return new Vector3i(maxX, maxY, maxZ);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static Vector3i getSize(@Nonnull PrefabBuffer.PrefabBufferAccessor prefab) {
/* 47 */     Vector3i min = getMin(prefab);
/* 48 */     Vector3i max = getMax(prefab);
/* 49 */     return max.addScaled(min, -1);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static Vector3i getAnchor(@Nonnull PrefabBuffer.PrefabBufferAccessor prefab) {
/* 54 */     int x = prefab.getAnchorX();
/* 55 */     int y = prefab.getAnchorY();
/* 56 */     int z = prefab.getAnchorZ();
/* 57 */     return new Vector3i(x, y, z);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\props\prefab\PropPrefabUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */