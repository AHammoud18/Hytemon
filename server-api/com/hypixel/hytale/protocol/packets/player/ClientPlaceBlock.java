/*     */ package com.hypixel.hytale.protocol.packets.player;
/*     */ import com.hypixel.hytale.protocol.BlockPosition;
/*     */ import com.hypixel.hytale.protocol.BlockRotation;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class ClientPlaceBlock implements Packet {
/*     */   public static final int PACKET_ID = 117;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 20;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 20;
/*     */   public static final int MAX_SIZE = 20;
/*     */   @Nullable
/*     */   public BlockPosition position;
/*     */   @Nullable
/*     */   public BlockRotation rotation;
/*     */   public int placedBlockId;
/*     */   
/*     */   public int getId() {
/*  26 */     return 117;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClientPlaceBlock() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public ClientPlaceBlock(@Nullable BlockPosition position, @Nullable BlockRotation rotation, int placedBlockId) {
/*  37 */     this.position = position;
/*  38 */     this.rotation = rotation;
/*  39 */     this.placedBlockId = placedBlockId;
/*     */   }
/*     */   
/*     */   public ClientPlaceBlock(@Nonnull ClientPlaceBlock other) {
/*  43 */     this.position = other.position;
/*  44 */     this.rotation = other.rotation;
/*  45 */     this.placedBlockId = other.placedBlockId;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ClientPlaceBlock deserialize(@Nonnull ByteBuf buf, int offset) {
/*  50 */     ClientPlaceBlock obj = new ClientPlaceBlock();
/*  51 */     byte nullBits = buf.getByte(offset);
/*  52 */     if ((nullBits & 0x1) != 0) obj.position = BlockPosition.deserialize(buf, offset + 1); 
/*  53 */     if ((nullBits & 0x2) != 0) obj.rotation = BlockRotation.deserialize(buf, offset + 13); 
/*  54 */     obj.placedBlockId = buf.getIntLE(offset + 16);
/*     */ 
/*     */     
/*  57 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  61 */     return 20;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  66 */     byte nullBits = 0;
/*  67 */     if (this.position != null) nullBits = (byte)(nullBits | 0x1); 
/*  68 */     if (this.rotation != null) nullBits = (byte)(nullBits | 0x2); 
/*  69 */     buf.writeByte(nullBits);
/*     */     
/*  71 */     if (this.position != null) { this.position.serialize(buf); } else { buf.writeZero(12); }
/*  72 */      if (this.rotation != null) { this.rotation.serialize(buf); } else { buf.writeZero(3); }
/*  73 */      buf.writeIntLE(this.placedBlockId);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  79 */     return 20;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  83 */     if (buffer.readableBytes() - offset < 20) {
/*  84 */       return ValidationResult.error("Buffer too small: expected at least 20 bytes");
/*     */     }
/*     */ 
/*     */     
/*  88 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ClientPlaceBlock clone() {
/*  92 */     ClientPlaceBlock copy = new ClientPlaceBlock();
/*  93 */     copy.position = (this.position != null) ? this.position.clone() : null;
/*  94 */     copy.rotation = (this.rotation != null) ? this.rotation.clone() : null;
/*  95 */     copy.placedBlockId = this.placedBlockId;
/*  96 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ClientPlaceBlock other;
/* 102 */     if (this == obj) return true; 
/* 103 */     if (obj instanceof ClientPlaceBlock) { other = (ClientPlaceBlock)obj; } else { return false; }
/* 104 */      return (Objects.equals(this.position, other.position) && Objects.equals(this.rotation, other.rotation) && this.placedBlockId == other.placedBlockId);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 109 */     return Objects.hash(new Object[] { this.position, this.rotation, Integer.valueOf(this.placedBlockId) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\player\ClientPlaceBlock.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */