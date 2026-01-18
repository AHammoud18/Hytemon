/*     */ package com.hypixel.hytale.builtin.teleport;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.function.BsonFunctionCodec;
/*     */ import com.hypixel.hytale.codec.validation.LegacyValidator;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.math.vector.Transform;
/*     */ import com.hypixel.hytale.server.core.modules.entity.teleport.Teleport;
/*     */ import com.hypixel.hytale.server.core.universe.Universe;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import java.time.Instant;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import org.bson.BsonValue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ public class Warp
/*     */ {
/*     */   public static final Codec<Warp> CODEC;
/*     */   public static final ArrayCodec<Warp> ARRAY_CODEC;
/*     */   private String id;
/*     */   private String world;
/*     */   @Nullable
/*     */   private Transform transform;
/*     */   private String creator;
/*     */   private Instant creationDate;
/*     */   
/*     */   static {
/*  46 */     CODEC = (Codec<Warp>)new BsonFunctionCodec((Codec)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(Warp.class, Warp::new).addField(new KeyedCodec("Id", (Codec)Codec.STRING), (warp, s) -> warp.id = s, warp -> warp.id)).addField(new KeyedCodec("World", (Codec)Codec.STRING), (warp, s) -> warp.world = s, warp -> warp.world)).addField(new KeyedCodec("Creator", (Codec)Codec.STRING), (warp, s) -> warp.creator = s, warp -> warp.creator)).append(new KeyedCodec("Date", (Codec)Codec.LONG), (warp, s) -> warp.creationDate = Instant.ofEpochMilli(s.longValue()), warp -> null).addValidator((LegacyValidator)Validators.deprecated()).add()).append(new KeyedCodec("CreationDate", (Codec)Codec.INSTANT), (o, i) -> o.creationDate = i, o -> o.creationDate).add()).build(), (warp, bsonValue) -> {
/*     */           warp.transform = (Transform)Transform.CODEC.decode(bsonValue);
/*     */           
/*     */           return warp;
/*     */         }(bsonValue, warp) -> {
/*     */           bsonValue.asDocument().putAll((Map)Transform.CODEC.encode(warp.transform).asDocument());
/*     */           
/*     */           return bsonValue;
/*     */         });
/*  55 */     ARRAY_CODEC = new ArrayCodec(CODEC, x$0 -> new Warp[x$0]);
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
/*     */   public Warp() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Warp(double locX, double locY, double locZ, float yaw, float pitch, float roll, String id, @Nonnull World world, String creator, Instant creationDate) {
/* 102 */     this.id = id;
/* 103 */     this.world = world.getName();
/* 104 */     this.transform = new Transform(locX, locY, locZ, pitch, yaw, roll);
/* 105 */     this.creator = creator;
/* 106 */     this.creationDate = creationDate;
/*     */   }
/*     */   
/*     */   public String getId() {
/* 110 */     return this.id;
/*     */   }
/*     */   
/*     */   public String getWorld() {
/* 114 */     return this.world;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Transform getTransform() {
/* 119 */     return this.transform;
/*     */   }
/*     */   
/*     */   public String getCreator() {
/* 123 */     return this.creator;
/*     */   }
/*     */   
/*     */   public Instant getCreationDate() {
/* 127 */     return this.creationDate;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object o) {
/* 132 */     if (this == o) return true; 
/* 133 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/* 135 */     Warp warp = (Warp)o;
/*     */     
/* 137 */     if (!Objects.equals(this.id, warp.id)) return false; 
/* 138 */     if (!Objects.equals(this.world, warp.world)) return false; 
/* 139 */     if (!Objects.equals(this.transform, warp.transform)) return false; 
/* 140 */     if (!Objects.equals(this.creator, warp.creator)) return false; 
/* 141 */     return Objects.equals(this.creationDate, warp.creationDate);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 146 */     int result = (this.id != null) ? this.id.hashCode() : 0;
/* 147 */     result = 31 * result + ((this.world != null) ? this.world.hashCode() : 0);
/* 148 */     result = 31 * result + ((this.transform != null) ? this.transform.hashCode() : 0);
/* 149 */     result = 31 * result + ((this.creator != null) ? this.creator.hashCode() : 0);
/* 150 */     result = 31 * result + ((this.creationDate != null) ? this.creationDate.hashCode() : 0);
/* 151 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 157 */     return "Warp{id='" + this.id + "', transform=" + String.valueOf(this.transform) + ", creator='" + this.creator + "', creationDate=" + String.valueOf(this.creationDate) + "}";
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
/*     */   @Nullable
/*     */   public Teleport toTeleport() {
/* 172 */     World worldInstance = Universe.get().getWorld(this.world);
/* 173 */     if (worldInstance == null) return null; 
/* 174 */     return new Teleport(worldInstance, this.transform);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\teleport\Warp.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */