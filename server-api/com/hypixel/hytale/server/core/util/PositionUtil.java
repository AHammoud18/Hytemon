/*    */ package com.hypixel.hytale.server.core.util;
/*    */ 
/*    */ import com.hypixel.hytale.math.vector.Transform;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.math.vector.Vector3f;
/*    */ import com.hypixel.hytale.protocol.Direction;
/*    */ import com.hypixel.hytale.protocol.Position;
/*    */ import com.hypixel.hytale.protocol.Transform;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PositionUtil
/*    */ {
/*    */   @Nonnull
/*    */   public static Transform toTransformPacket(@Nonnull Transform transform) {
/* 20 */     Vector3d position = transform.getPosition();
/* 21 */     Vector3f rotation = transform.getRotation();
/* 22 */     return new Transform(
/* 23 */         toPositionPacket(position), 
/* 24 */         toDirectionPacket(rotation));
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public static Position toPositionPacket(@Nonnull Vector3d position) {
/* 30 */     return new Position(position.x, position.y, position.z);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static Direction toDirectionPacket(@Nonnull Vector3f rotation) {
/* 35 */     return new Direction(rotation.getYaw(), rotation.getPitch(), rotation.getRoll());
/*    */   }
/*    */   
/*    */   public static Transform toTransform(@Nullable Transform transform) {
/* 39 */     if (transform == null) return null; 
/* 40 */     return new Transform(toVector3d(transform.position), toRotation(transform.orientation));
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static Vector3d toVector3d(@Nonnull Position position_) {
/* 45 */     return new Vector3d(position_.x, position_.y, position_.z);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static Vector3f toRotation(@Nonnull Direction orientation) {
/* 50 */     return new Vector3f(orientation.pitch, orientation.yaw, orientation.roll);
/*    */   }
/*    */   
/*    */   public static boolean equals(@Nonnull Vector3d vector, @Nonnull Position position) {
/* 54 */     return (vector.x == position.x && vector.y == position.y && vector.z == position.z);
/*    */   }
/*    */   
/*    */   public static void assign(@Nonnull Position position, @Nonnull Vector3d vector) {
/* 58 */     position.x = vector.x;
/* 59 */     position.y = vector.y;
/* 60 */     position.z = vector.z;
/*    */   }
/*    */   
/*    */   public static boolean equals(@Nonnull Vector3f vector, @Nonnull Direction direction) {
/* 64 */     return (vector.x == direction.pitch && vector.y == direction.yaw && vector.z == direction.roll);
/*    */   }
/*    */   
/*    */   public static void assign(@Nonnull Direction direction, @Nonnull Vector3f vector) {
/* 68 */     direction.pitch = vector.x;
/* 69 */     direction.yaw = vector.y;
/* 70 */     direction.roll = vector.z;
/*    */   }
/*    */   
/*    */   public static void assign(@Nonnull Position position, @Nonnull Position other) {
/* 74 */     position.x = other.x;
/* 75 */     position.y = other.y;
/* 76 */     position.z = other.z;
/*    */   }
/*    */   
/*    */   public static void assign(@Nonnull Direction direction, @Nonnull Direction other) {
/* 80 */     direction.pitch = other.pitch;
/* 81 */     direction.yaw = other.yaw;
/* 82 */     direction.roll = other.roll;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\util\PositionUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */