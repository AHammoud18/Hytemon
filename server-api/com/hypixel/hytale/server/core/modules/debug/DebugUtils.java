/*     */ package com.hypixel.hytale.server.core.modules.debug;
/*     */ 
/*     */ import com.hypixel.hytale.math.matrix.Matrix4d;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.protocol.DebugShape;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.Vector3f;
/*     */ import com.hypixel.hytale.protocol.packets.player.ClearDebugShapes;
/*     */ import com.hypixel.hytale.protocol.packets.player.DisplayDebug;
/*     */ import com.hypixel.hytale.server.core.modules.entity.damage.DamageSystems;
/*     */ import com.hypixel.hytale.server.core.modules.splitvelocity.SplitVelocity;
/*     */ import com.hypixel.hytale.server.core.modules.splitvelocity.VelocityConfig;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nonnull;
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
/*     */ public class DebugUtils
/*     */ {
/*     */   public static boolean DISPLAY_FORCES = false;
/*     */   
/*     */   public static void add(@Nonnull World world, @Nonnull DebugShape shape, @Nonnull Matrix4d matrix, @Nonnull Vector3f color, float time, boolean fade) {
/*  50 */     DisplayDebug packet = new DisplayDebug(shape, matrix.asFloatData(), new Vector3f(color.x, color.y, color.z), time, fade, null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  58 */     for (PlayerRef playerRef : world.getPlayerRefs()) {
/*  59 */       playerRef.getPacketHandler().write((Packet)packet);
/*     */     }
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
/*     */   public static void addFrustum(@Nonnull World world, @Nonnull Matrix4d matrix, @Nonnull Matrix4d frustumProjection, @Nonnull Vector3f color, float time, boolean fade) {
/*  80 */     DisplayDebug packet = new DisplayDebug(DebugShape.Frustum, matrix.asFloatData(), new Vector3f(color.x, color.y, color.z), time, fade, frustumProjection.asFloatData());
/*     */ 
/*     */ 
/*     */     
/*  84 */     for (PlayerRef playerRef : world.getPlayerRefs()) {
/*  85 */       playerRef.getPacketHandler().write((Packet)packet);
/*     */     }
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
/*     */   public static void clear(@Nonnull World world) {
/*  98 */     ClearDebugShapes packet = new ClearDebugShapes();
/*     */     
/* 100 */     for (PlayerRef playerRef : world.getPlayerRefs()) {
/* 101 */       playerRef.getPacketHandler().write((Packet)packet);
/*     */     }
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
/*     */   public static void addArrow(@Nonnull World world, @Nonnull Matrix4d baseMatrix, @Nonnull Vector3f color, double length, float time, boolean fade) {
/* 119 */     double adjustedLength = length - 0.3D;
/*     */     
/* 121 */     if (adjustedLength > 0.0D) {
/* 122 */       Matrix4d matrix4d = new Matrix4d(baseMatrix);
/* 123 */       matrix4d.translate(0.0D, adjustedLength * 0.5D, 0.0D);
/* 124 */       matrix4d.scale(0.10000000149011612D, adjustedLength, 0.10000000149011612D);
/* 125 */       add(world, DebugShape.Cylinder, matrix4d, color, time, fade);
/*     */     } 
/*     */     
/* 128 */     Matrix4d matrix = new Matrix4d(baseMatrix);
/* 129 */     matrix.translate(0.0D, adjustedLength + 0.15D, 0.0D);
/* 130 */     matrix.scale(0.30000001192092896D, 0.30000001192092896D, 0.30000001192092896D);
/* 131 */     add(world, DebugShape.Cone, matrix, color, time, fade);
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
/*     */   public static void addSphere(@Nonnull World world, @Nonnull Vector3d pos, @Nonnull Vector3f color, double scale, float time) {
/* 144 */     Matrix4d matrix = makeMatrix(pos, scale);
/* 145 */     add(world, DebugShape.Sphere, matrix, color, time, true);
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
/*     */   public static void addCone(@Nonnull World world, @Nonnull Vector3d pos, @Nonnull Vector3f color, double scale, float time) {
/* 158 */     Matrix4d matrix = makeMatrix(pos, scale);
/* 159 */     add(world, DebugShape.Cone, matrix, color, time, true);
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
/*     */   public static void addCube(@Nonnull World world, @Nonnull Vector3d pos, @Nonnull Vector3f color, double scale, float time) {
/* 172 */     Matrix4d matrix = makeMatrix(pos, scale);
/* 173 */     add(world, DebugShape.Cube, matrix, color, time, true);
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
/*     */   public static void addCylinder(@Nonnull World world, @Nonnull Vector3d pos, @Nonnull Vector3f color, double scale, float time) {
/* 186 */     Matrix4d matrix = makeMatrix(pos, scale);
/* 187 */     add(world, DebugShape.Cylinder, matrix, color, time, true);
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
/*     */   public static void addArrow(@Nonnull World world, @Nonnull Vector3d position, @Nonnull Vector3d direction, @Nonnull Vector3f color, float time, boolean fade) {
/* 204 */     Vector3d directionClone = direction.clone();
/*     */     
/* 206 */     Matrix4d tmp = new Matrix4d();
/* 207 */     Matrix4d matrix = new Matrix4d();
/* 208 */     matrix.identity();
/* 209 */     matrix.translate(position);
/*     */     
/* 211 */     double angleY = Math.atan2(directionClone.z, directionClone.x);
/* 212 */     matrix.rotateAxis(angleY + 1.5707963267948966D, 0.0D, 1.0D, 0.0D, tmp);
/*     */     
/* 214 */     double angleX = Math.atan2(Math.sqrt(directionClone.x * directionClone.x + directionClone.z * directionClone.z), directionClone.y);
/* 215 */     matrix.rotateAxis(angleX, 1.0D, 0.0D, 0.0D, tmp);
/*     */     
/* 217 */     addArrow(world, matrix, color, directionClone.length(), time, fade);
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
/*     */   public static void addForce(@Nonnull World world, @Nonnull Vector3d position, @Nonnull Vector3d force, @Nullable VelocityConfig velocityConfig) {
/* 234 */     if (!DISPLAY_FORCES)
/*     */       return; 
/* 236 */     Vector3d forceClone = force.clone();
/* 237 */     if (velocityConfig == null || SplitVelocity.SHOULD_MODIFY_VELOCITY) {
/* 238 */       forceClone.x /= DamageSystems.HackKnockbackValues.PLAYER_KNOCKBACK_SCALE;
/* 239 */       forceClone.z /= DamageSystems.HackKnockbackValues.PLAYER_KNOCKBACK_SCALE;
/*     */     } 
/*     */     
/* 242 */     Matrix4d tmp = new Matrix4d();
/* 243 */     Matrix4d matrix = new Matrix4d();
/* 244 */     matrix.identity();
/* 245 */     matrix.translate(position);
/*     */     
/* 247 */     double angleY = Math.atan2(forceClone.z, forceClone.x);
/* 248 */     matrix.rotateAxis(angleY + 1.5707963267948966D, 0.0D, 1.0D, 0.0D, tmp);
/*     */     
/* 250 */     double angleX = Math.atan2(Math.sqrt(forceClone.x * forceClone.x + forceClone.z * forceClone.z), forceClone.y);
/* 251 */     matrix.rotateAxis(angleX, 1.0D, 0.0D, 0.0D, tmp);
/*     */     
/* 253 */     Random random = new Random();
/* 254 */     Vector3f color = new Vector3f(random.nextFloat(), random.nextFloat(), random.nextFloat());
/* 255 */     addArrow(world, matrix, color, forceClone.length(), 10.0F, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private static Matrix4d makeMatrix(@Nonnull Vector3d pos, double scale) {
/* 267 */     Matrix4d matrix = new Matrix4d();
/* 268 */     matrix.identity();
/* 269 */     matrix.translate(pos);
/* 270 */     matrix.scale(scale, scale, scale);
/* 271 */     return matrix;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\debug\DebugUtils.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */