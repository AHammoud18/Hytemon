/*     */ package com.hypixel.hytale.protocol.packets.world;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.BlockParticleEvent;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.Position;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ public class SpawnBlockParticleSystem
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 153;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 30;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 30;
/*     */   public static final int MAX_SIZE = 30;
/*     */   public int blockId;
/*     */   
/*     */   public int getId() {
/*  26 */     return 153;
/*     */   }
/*     */   
/*     */   @Nonnull
/*  30 */   public BlockParticleEvent particleType = BlockParticleEvent.Walk;
/*     */   
/*     */   @Nullable
/*     */   public Position position;
/*     */ 
/*     */   
/*     */   public SpawnBlockParticleSystem(int blockId, @Nonnull BlockParticleEvent particleType, @Nullable Position position) {
/*  37 */     this.blockId = blockId;
/*  38 */     this.particleType = particleType;
/*  39 */     this.position = position;
/*     */   }
/*     */   
/*     */   public SpawnBlockParticleSystem(@Nonnull SpawnBlockParticleSystem other) {
/*  43 */     this.blockId = other.blockId;
/*  44 */     this.particleType = other.particleType;
/*  45 */     this.position = other.position;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static SpawnBlockParticleSystem deserialize(@Nonnull ByteBuf buf, int offset) {
/*  50 */     SpawnBlockParticleSystem obj = new SpawnBlockParticleSystem();
/*  51 */     byte nullBits = buf.getByte(offset);
/*  52 */     obj.blockId = buf.getIntLE(offset + 1);
/*  53 */     obj.particleType = BlockParticleEvent.fromValue(buf.getByte(offset + 5));
/*  54 */     if ((nullBits & 0x1) != 0) obj.position = Position.deserialize(buf, offset + 6);
/*     */ 
/*     */     
/*  57 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  61 */     return 30;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  66 */     byte nullBits = 0;
/*  67 */     if (this.position != null) nullBits = (byte)(nullBits | 0x1); 
/*  68 */     buf.writeByte(nullBits);
/*     */     
/*  70 */     buf.writeIntLE(this.blockId);
/*  71 */     buf.writeByte(this.particleType.getValue());
/*  72 */     if (this.position != null) { this.position.serialize(buf); } else { buf.writeZero(24); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  78 */     return 30;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  82 */     if (buffer.readableBytes() - offset < 30) {
/*  83 */       return ValidationResult.error("Buffer too small: expected at least 30 bytes");
/*     */     }
/*     */ 
/*     */     
/*  87 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public SpawnBlockParticleSystem clone() {
/*  91 */     SpawnBlockParticleSystem copy = new SpawnBlockParticleSystem();
/*  92 */     copy.blockId = this.blockId;
/*  93 */     copy.particleType = this.particleType;
/*  94 */     copy.position = (this.position != null) ? this.position.clone() : null;
/*  95 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     SpawnBlockParticleSystem other;
/* 101 */     if (this == obj) return true; 
/* 102 */     if (obj instanceof SpawnBlockParticleSystem) { other = (SpawnBlockParticleSystem)obj; } else { return false; }
/* 103 */      return (this.blockId == other.blockId && Objects.equals(this.particleType, other.particleType) && Objects.equals(this.position, other.position));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 108 */     return Objects.hash(new Object[] { Integer.valueOf(this.blockId), this.particleType, this.position });
/*     */   }
/*     */   
/*     */   public SpawnBlockParticleSystem() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\world\SpawnBlockParticleSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */