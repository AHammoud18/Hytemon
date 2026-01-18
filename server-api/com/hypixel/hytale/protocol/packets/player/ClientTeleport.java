/*     */ package com.hypixel.hytale.protocol.packets.player;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.ModelTransform;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class ClientTeleport implements Packet {
/*     */   public static final int PACKET_ID = 109;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 52;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 52;
/*     */   public static final int MAX_SIZE = 52;
/*     */   public byte teleportId;
/*     */   @Nullable
/*     */   public ModelTransform modelTransform;
/*     */   public boolean resetVelocity;
/*     */   
/*     */   public int getId() {
/*  25 */     return 109;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClientTeleport() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public ClientTeleport(byte teleportId, @Nullable ModelTransform modelTransform, boolean resetVelocity) {
/*  36 */     this.teleportId = teleportId;
/*  37 */     this.modelTransform = modelTransform;
/*  38 */     this.resetVelocity = resetVelocity;
/*     */   }
/*     */   
/*     */   public ClientTeleport(@Nonnull ClientTeleport other) {
/*  42 */     this.teleportId = other.teleportId;
/*  43 */     this.modelTransform = other.modelTransform;
/*  44 */     this.resetVelocity = other.resetVelocity;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ClientTeleport deserialize(@Nonnull ByteBuf buf, int offset) {
/*  49 */     ClientTeleport obj = new ClientTeleport();
/*  50 */     byte nullBits = buf.getByte(offset);
/*  51 */     obj.teleportId = buf.getByte(offset + 1);
/*  52 */     if ((nullBits & 0x1) != 0) obj.modelTransform = ModelTransform.deserialize(buf, offset + 2); 
/*  53 */     obj.resetVelocity = (buf.getByte(offset + 51) != 0);
/*     */ 
/*     */     
/*  56 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  60 */     return 52;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  65 */     byte nullBits = 0;
/*  66 */     if (this.modelTransform != null) nullBits = (byte)(nullBits | 0x1); 
/*  67 */     buf.writeByte(nullBits);
/*     */     
/*  69 */     buf.writeByte(this.teleportId);
/*  70 */     if (this.modelTransform != null) { this.modelTransform.serialize(buf); } else { buf.writeZero(49); }
/*  71 */      buf.writeByte(this.resetVelocity ? 1 : 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  77 */     return 52;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  81 */     if (buffer.readableBytes() - offset < 52) {
/*  82 */       return ValidationResult.error("Buffer too small: expected at least 52 bytes");
/*     */     }
/*     */ 
/*     */     
/*  86 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ClientTeleport clone() {
/*  90 */     ClientTeleport copy = new ClientTeleport();
/*  91 */     copy.teleportId = this.teleportId;
/*  92 */     copy.modelTransform = (this.modelTransform != null) ? this.modelTransform.clone() : null;
/*  93 */     copy.resetVelocity = this.resetVelocity;
/*  94 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ClientTeleport other;
/* 100 */     if (this == obj) return true; 
/* 101 */     if (obj instanceof ClientTeleport) { other = (ClientTeleport)obj; } else { return false; }
/* 102 */      return (this.teleportId == other.teleportId && Objects.equals(this.modelTransform, other.modelTransform) && this.resetVelocity == other.resetVelocity);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 107 */     return Objects.hash(new Object[] { Byte.valueOf(this.teleportId), this.modelTransform, Boolean.valueOf(this.resetVelocity) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\player\ClientTeleport.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */