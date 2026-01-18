/*    */ package com.hypixel.hytale.builtin.adventure.objectives.config.worldlocationproviders;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.math.util.ChunkUtil;
/*    */ import com.hypixel.hytale.math.util.MathUtil;
/*    */ import com.hypixel.hytale.math.util.TrigMathUtil;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LocationRadiusProvider
/*    */   extends WorldLocationProvider
/*    */ {
/*    */   public static final BuilderCodec<LocationRadiusProvider> CODEC;
/*    */   
/*    */   static {
/* 34 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(LocationRadiusProvider.class, LocationRadiusProvider::new, BASE_CODEC).append(new KeyedCodec("MinRadius", (Codec)Codec.INTEGER), (locationRadiusCondition, integer) -> locationRadiusCondition.minRadius = integer.intValue(), locationRadiusCondition -> Integer.valueOf(locationRadiusCondition.minRadius)).addValidator(Validators.greaterThan(Integer.valueOf(0))).add()).append(new KeyedCodec("MaxRadius", (Codec)Codec.INTEGER), (locationRadiusCondition, integer) -> locationRadiusCondition.maxRadius = integer.intValue(), locationRadiusCondition -> Integer.valueOf(locationRadiusCondition.maxRadius)).addValidator(Validators.greaterThanOrEqual(Integer.valueOf(1))).add()).afterDecode(locationRadiusCondition -> { if (locationRadiusCondition.minRadius > locationRadiusCondition.maxRadius) throw new IllegalArgumentException("LocationRadiusCondition.MinRadius (" + locationRadiusCondition.minRadius + ") needs to be greater than LocationRadiusCondition.MaxRadius (" + locationRadiusCondition.maxRadius + ")");  })).build();
/*    */   }
/* 36 */   protected int minRadius = 10;
/* 37 */   protected int maxRadius = 50;
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Vector3i runCondition(@Nonnull World world, @Nonnull Vector3i position) {
/* 42 */     double angle = Math.random() * 6.2831854820251465D;
/* 43 */     int radius = MathUtil.randomInt(this.minRadius, this.maxRadius);
/*    */     
/* 45 */     Vector3i newPosition = position.clone();
/* 46 */     newPosition.add((int)(radius * TrigMathUtil.cos(angle)), 0, (int)(radius * TrigMathUtil.sin(angle)));
/* 47 */     newPosition.y = world.getChunk(ChunkUtil.indexChunkFromBlock(newPosition.x, newPosition.z)).getHeight(newPosition.x, newPosition.z);
/*    */     
/* 49 */     return newPosition;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(@Nullable Object o) {
/* 54 */     if (this == o) return true; 
/* 55 */     if (o == null || getClass() != o.getClass()) return false;
/*    */     
/* 57 */     LocationRadiusProvider that = (LocationRadiusProvider)o;
/*    */     
/* 59 */     if (this.minRadius != that.minRadius) return false; 
/* 60 */     return (this.maxRadius == that.maxRadius);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 65 */     int result = this.minRadius;
/* 66 */     result = 31 * result + this.maxRadius;
/* 67 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 73 */     return "LocationRadiusProvider{minRadius=" + this.minRadius + ", maxRadius=" + this.maxRadius + "} " + super
/*    */ 
/*    */       
/* 76 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\config\worldlocationproviders\LocationRadiusProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */