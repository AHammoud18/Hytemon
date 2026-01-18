/*     */ package com.hypixel.hytale.math.iterator;
/*     */ 
/*     */ import com.hypixel.hytale.math.shape.Box;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import java.util.function.Supplier;
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
/*     */ public final class BoxBlockIterator
/*     */ {
/*     */   @Nonnull
/*  22 */   private static ThreadLocal<BoxIterationBuffer> THREAD_LOCAL_BUFFER = ThreadLocal.withInitial(BoxIterationBuffer::new);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BoxBlockIterator() {
/*  28 */     throw new UnsupportedOperationException("This is a utility class. Do not instantiate.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BoxIterationBuffer getBuffer() {
/*  37 */     return THREAD_LOCAL_BUFFER.get();
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
/*     */   public static boolean iterate(@Nonnull Box box, @Nonnull Vector3d position, @Nonnull Vector3d d, double maxDistance, @Nonnull BoxIterationConsumer consumer) {
/*  53 */     return iterate(box, position, d, maxDistance, consumer, getBuffer());
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
/*     */   public static boolean iterate(@Nonnull Box box, @Nonnull Vector3d pos, @Nonnull Vector3d d, double maxDistance, @Nonnull BoxIterationConsumer consumer, @Nonnull BoxIterationBuffer buffer) {
/*  70 */     return iterate(box.min, box.max, pos, d, maxDistance, consumer, buffer);
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
/*     */ 
/*     */   
/*     */   public static boolean iterate(@Nonnull Box box, double px, double py, double pz, double dx, double dy, double dz, double maxDistance, @Nonnull BoxIterationConsumer consumer) {
/*  92 */     return iterate(box, px, py, pz, dx, dy, dz, maxDistance, consumer, getBuffer());
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean iterate(@Nonnull Box box, double px, double py, double pz, double dx, double dy, double dz, double maxDistance, @Nonnull BoxIterationConsumer consumer, @Nonnull BoxIterationBuffer buffer) {
/* 115 */     return iterate(box.min, box.max, px, py, pz, dx, dy, dz, maxDistance, consumer, buffer);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean iterate(@Nonnull Vector3d min, @Nonnull Vector3d max, double px, double py, double pz, double dx, double dy, double dz, double maxDistance, @Nonnull BoxIterationConsumer consumer) {
/* 138 */     return iterate(min, max, px, py, pz, dx, dy, dz, maxDistance, consumer, getBuffer());
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean iterate(@Nonnull Vector3d min, @Nonnull Vector3d max, double px, double py, double pz, double dx, double dy, double dz, double maxDistance, @Nonnull BoxIterationConsumer consumer, @Nonnull BoxIterationBuffer buffer) {
/* 162 */     return iterate(min.x, min.y, min.z, max.x, max.y, max.z, px, py, pz, dx, dy, dz, maxDistance, consumer, buffer);
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
/*     */   public static boolean iterate(@Nonnull Vector3d min, @Nonnull Vector3d max, @Nonnull Vector3d pos, @Nonnull Vector3d d, double maxDistance, @Nonnull BoxIterationConsumer consumer) {
/* 180 */     return iterate(min, max, pos, d, maxDistance, consumer, getBuffer());
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
/*     */   public static boolean iterate(@Nonnull Vector3d min, @Nonnull Vector3d max, @Nonnull Vector3d pos, @Nonnull Vector3d d, double maxDistance, @Nonnull BoxIterationConsumer consumer, @Nonnull BoxIterationBuffer buffer) {
/* 198 */     return iterate(min.x, min.y, min.z, max.x, max.y, max.z, pos.x, pos.y, pos.z, d.x, d.y, d.z, maxDistance, consumer, buffer);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean iterate(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, double px, double py, double pz, double dx, double dy, double dz, double maxDistance, @Nonnull BoxIterationConsumer consumer) {
/* 226 */     return iterate(minX, minY, minZ, maxX, maxY, maxZ, px, py, pz, dx, dy, dz, maxDistance, consumer, getBuffer());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean iterate(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, double px, double py, double pz, double dx, double dy, double dz, double maxDistance, @Nonnull BoxIterationConsumer consumer, @Nonnull BoxIterationBuffer buffer) {
/* 255 */     if (minX > maxX) throw new IllegalArgumentException("minX is larger than maxX! Given: " + minX + " > " + maxX); 
/* 256 */     if (minY > maxY) throw new IllegalArgumentException("minY is larger than maxY! Given: " + minY + " > " + maxY); 
/* 257 */     if (minZ > maxZ) throw new IllegalArgumentException("minZ is larger than maxZ! Given: " + minZ + " > " + maxZ);
/*     */     
/* 259 */     if (consumer == null) throw new NullPointerException("consumer is null!"); 
/* 260 */     if (buffer == null) throw new NullPointerException("buffer is null!");
/*     */     
/* 262 */     return iterate0(minX, minY, minZ, maxX, maxY, maxZ, px, py, pz, dx, dy, dz, maxDistance, consumer, buffer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean iterate0(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, double posX, double posY, double posZ, double dx, double dy, double dz, double maxDistance, BoxIterationConsumer consumer, @Nonnull BoxIterationBuffer buffer) {
/* 270 */     buffer.consumer = consumer;
/*     */     
/* 272 */     buffer.mx = maxX - minX;
/* 273 */     buffer.my = maxY - minY;
/* 274 */     buffer.mz = maxZ - minZ;
/*     */     
/* 276 */     buffer.signX = (dx > 0.0D) ? -1 : 1;
/* 277 */     buffer.signY = (dy > 0.0D) ? -1 : 1;
/* 278 */     buffer.signZ = (dz > 0.0D) ? -1 : 1;
/*     */ 
/*     */     
/* 281 */     double bx = posX + ((dx > 0.0D) ? maxX : minX);
/* 282 */     double by = posY + ((dy > 0.0D) ? maxY : minY);
/* 283 */     double bz = posZ + ((dz > 0.0D) ? maxZ : minZ);
/*     */     
/* 285 */     buffer.posX = (long)bx;
/* 286 */     buffer.posY = (long)by;
/* 287 */     buffer.posZ = (long)bz;
/*     */     
/* 289 */     return BlockIterator.iterate(bx, by, bz, dx, dy, dz, maxDistance, (x, y, z, px, py, pz, qx, qy, qz, buf) -> { int tx = (int)MathUtil.fastCeil(((buf.signX < 0) ? (1.0D - px) : px) + buf.mx); int ty = (int)MathUtil.fastCeil(((buf.signY < 0) ? (1.0D - py) : py) + buf.my); int tz = (int)MathUtil.fastCeil(((buf.signZ < 0) ? (1.0D - pz) : pz) + buf.mz); if (x != buf.posX) { for (int iy = 0; iy < ty; iy++) { for (int iz = 0; iz < tz; iz++) { if (!buf.consumer.accept(x, y + iy * buf.signY, z + iz * buf.signZ)) return false;  }  }  buf.posX = x; }  if (y != buf.posY) { for (int iz = 0; iz < tz; iz++) { for (int ix = 0; ix < tx; ix++) { if (!buf.consumer.accept(x + ix * buf.signX, y, z + iz * buf.signZ)) return false;  }  }  buf.posY = y; }  if (z != buf.posZ) { for (int ix = 0; ix < tx; ix++) { for (int iy = 0; iy < ty; iy++) { if (!buf.consumer.accept(x + ix * buf.signX, y + iy * buf.signY, z)) return false;  }  }  buf.posZ = z; }  return buf.consumer.next(); }buffer);
/*     */   }
/*     */   
/*     */   public static class BoxIterationBuffer {
/*     */     BoxBlockIterator.BoxIterationConsumer consumer;
/*     */     double mx;
/*     */     double my;
/*     */     double mz;
/*     */     int signX;
/*     */     int signY;
/*     */     int signZ;
/*     */     long posX;
/*     */     long posY;
/*     */     long posZ;
/*     */   }
/*     */   
/*     */   public static interface BoxIterationConsumer {
/*     */     boolean next();
/*     */     
/*     */     boolean accept(long param1Long1, long param1Long2, long param1Long3);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\math\iterator\BoxBlockIterator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */