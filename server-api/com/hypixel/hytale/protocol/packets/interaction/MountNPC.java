/*     */ package com.hypixel.hytale.protocol.packets.interaction;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class MountNPC
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 293;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*     */   public static final int FIXED_BLOCK_SIZE = 16;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 16;
/*     */   public static final int MAX_SIZE = 16;
/*     */   public float anchorX;
/*     */   public float anchorY;
/*     */   public float anchorZ;
/*     */   public int entityId;
/*     */   
/*     */   public int getId() {
/*  25 */     return 293;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MountNPC() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MountNPC(float anchorX, float anchorY, float anchorZ, int entityId) {
/*  37 */     this.anchorX = anchorX;
/*  38 */     this.anchorY = anchorY;
/*  39 */     this.anchorZ = anchorZ;
/*  40 */     this.entityId = entityId;
/*     */   }
/*     */   
/*     */   public MountNPC(@Nonnull MountNPC other) {
/*  44 */     this.anchorX = other.anchorX;
/*  45 */     this.anchorY = other.anchorY;
/*  46 */     this.anchorZ = other.anchorZ;
/*  47 */     this.entityId = other.entityId;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static MountNPC deserialize(@Nonnull ByteBuf buf, int offset) {
/*  52 */     MountNPC obj = new MountNPC();
/*     */     
/*  54 */     obj.anchorX = buf.getFloatLE(offset + 0);
/*  55 */     obj.anchorY = buf.getFloatLE(offset + 4);
/*  56 */     obj.anchorZ = buf.getFloatLE(offset + 8);
/*  57 */     obj.entityId = buf.getIntLE(offset + 12);
/*     */ 
/*     */     
/*  60 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  64 */     return 16;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  70 */     buf.writeFloatLE(this.anchorX);
/*  71 */     buf.writeFloatLE(this.anchorY);
/*  72 */     buf.writeFloatLE(this.anchorZ);
/*  73 */     buf.writeIntLE(this.entityId);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  79 */     return 16;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  83 */     if (buffer.readableBytes() - offset < 16) {
/*  84 */       return ValidationResult.error("Buffer too small: expected at least 16 bytes");
/*     */     }
/*     */ 
/*     */     
/*  88 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public MountNPC clone() {
/*  92 */     MountNPC copy = new MountNPC();
/*  93 */     copy.anchorX = this.anchorX;
/*  94 */     copy.anchorY = this.anchorY;
/*  95 */     copy.anchorZ = this.anchorZ;
/*  96 */     copy.entityId = this.entityId;
/*  97 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     MountNPC other;
/* 103 */     if (this == obj) return true; 
/* 104 */     if (obj instanceof MountNPC) { other = (MountNPC)obj; } else { return false; }
/* 105 */      return (this.anchorX == other.anchorX && this.anchorY == other.anchorY && this.anchorZ == other.anchorZ && this.entityId == other.entityId);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 110 */     return Objects.hash(new Object[] { Float.valueOf(this.anchorX), Float.valueOf(this.anchorY), Float.valueOf(this.anchorZ), Integer.valueOf(this.entityId) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\interaction\MountNPC.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */