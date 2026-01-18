/*     */ package com.hypixel.hytale.protocol.packets.player;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class JoinWorld
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 104;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*     */   public static final int FIXED_BLOCK_SIZE = 18;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 18;
/*     */   public static final int MAX_SIZE = 18;
/*     */   public boolean clearWorld;
/*     */   public boolean fadeInOut;
/*     */   
/*     */   public int getId() {
/*  25 */     return 104;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*  30 */   public UUID worldUuid = new UUID(0L, 0L);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JoinWorld(boolean clearWorld, boolean fadeInOut, @Nonnull UUID worldUuid) {
/*  36 */     this.clearWorld = clearWorld;
/*  37 */     this.fadeInOut = fadeInOut;
/*  38 */     this.worldUuid = worldUuid;
/*     */   }
/*     */   
/*     */   public JoinWorld(@Nonnull JoinWorld other) {
/*  42 */     this.clearWorld = other.clearWorld;
/*  43 */     this.fadeInOut = other.fadeInOut;
/*  44 */     this.worldUuid = other.worldUuid;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static JoinWorld deserialize(@Nonnull ByteBuf buf, int offset) {
/*  49 */     JoinWorld obj = new JoinWorld();
/*     */     
/*  51 */     obj.clearWorld = (buf.getByte(offset + 0) != 0);
/*  52 */     obj.fadeInOut = (buf.getByte(offset + 1) != 0);
/*  53 */     obj.worldUuid = PacketIO.readUUID(buf, offset + 2);
/*     */ 
/*     */     
/*  56 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  60 */     return 18;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  66 */     buf.writeByte(this.clearWorld ? 1 : 0);
/*  67 */     buf.writeByte(this.fadeInOut ? 1 : 0);
/*  68 */     PacketIO.writeUUID(buf, this.worldUuid);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  74 */     return 18;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  78 */     if (buffer.readableBytes() - offset < 18) {
/*  79 */       return ValidationResult.error("Buffer too small: expected at least 18 bytes");
/*     */     }
/*     */ 
/*     */     
/*  83 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public JoinWorld clone() {
/*  87 */     JoinWorld copy = new JoinWorld();
/*  88 */     copy.clearWorld = this.clearWorld;
/*  89 */     copy.fadeInOut = this.fadeInOut;
/*  90 */     copy.worldUuid = this.worldUuid;
/*  91 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     JoinWorld other;
/*  97 */     if (this == obj) return true; 
/*  98 */     if (obj instanceof JoinWorld) { other = (JoinWorld)obj; } else { return false; }
/*  99 */      return (this.clearWorld == other.clearWorld && this.fadeInOut == other.fadeInOut && Objects.equals(this.worldUuid, other.worldUuid));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 104 */     return Objects.hash(new Object[] { Boolean.valueOf(this.clearWorld), Boolean.valueOf(this.fadeInOut), this.worldUuid });
/*     */   }
/*     */   
/*     */   public JoinWorld() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\player\JoinWorld.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */