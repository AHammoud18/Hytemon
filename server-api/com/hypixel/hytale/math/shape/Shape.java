/*    */ package com.hypixel.hytale.math.shape;
/*    */ 
/*    */ import com.hypixel.hytale.function.predicate.TriIntObjPredicate;
/*    */ import com.hypixel.hytale.function.predicate.TriIntPredicate;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface Shape
/*    */ {
/*    */   default Box getBox(@Nonnull Vector3d position) {
/* 16 */     return getBox(position.getX(), position.getY(), position.getZ());
/*    */   }
/*    */   
/*    */   Box getBox(double paramDouble1, double paramDouble2, double paramDouble3);
/*    */   
/*    */   default boolean containsPosition(@Nonnull Vector3d origin, @Nonnull Vector3d position) {
/* 22 */     return containsPosition(position.getX() - origin.getX(), position.getY() - origin.getY(), position.getZ() - origin.getZ());
/*    */   }
/*    */   
/*    */   default boolean containsPosition(@Nonnull Vector3d position) {
/* 26 */     return containsPosition(position.getX(), position.getY(), position.getZ());
/*    */   }
/*    */   
/*    */   boolean containsPosition(double paramDouble1, double paramDouble2, double paramDouble3);
/*    */   
/*    */   void expand(double paramDouble);
/*    */   
/*    */   default boolean forEachBlock(@Nonnull Vector3d origin, TriIntPredicate consumer) {
/* 34 */     return forEachBlock(origin.getX(), origin.getY(), origin.getZ(), consumer);
/*    */   }
/*    */   
/*    */   default boolean forEachBlock(@Nonnull Vector3d origin, double epsilon, TriIntPredicate consumer) {
/* 38 */     return forEachBlock(origin.getX(), origin.getY(), origin.getZ(), epsilon, consumer);
/*    */   }
/*    */   
/*    */   default boolean forEachBlock(double x, double y, double z, TriIntPredicate consumer) {
/* 42 */     return forEachBlock(x, y, z, 0.0D, consumer);
/*    */   }
/*    */   
/*    */   boolean forEachBlock(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, TriIntPredicate paramTriIntPredicate);
/*    */   
/*    */   default <T> boolean forEachBlock(@Nonnull Vector3d origin, T t, TriIntObjPredicate<T> consumer) {
/* 48 */     return forEachBlock(origin.getX(), origin.getY(), origin.getZ(), t, consumer);
/*    */   }
/*    */   
/*    */   default <T> boolean forEachBlock(@Nonnull Vector3d origin, double epsilon, T t, TriIntObjPredicate<T> consumer) {
/* 52 */     return forEachBlock(origin.getX(), origin.getY(), origin.getZ(), epsilon, t, consumer);
/*    */   }
/*    */   
/*    */   default <T> boolean forEachBlock(double x, double y, double z, T t, TriIntObjPredicate<T> consumer) {
/* 56 */     return forEachBlock(x, y, z, 0.0D, t, consumer);
/*    */   }
/*    */   
/*    */   <T> boolean forEachBlock(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, T paramT, TriIntObjPredicate<T> paramTriIntObjPredicate);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\math\shape\Shape.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */