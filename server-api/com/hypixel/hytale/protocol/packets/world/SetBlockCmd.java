/*     */ package com.hypixel.hytale.protocol.packets.world;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SetBlockCmd
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*     */   public static final int FIXED_BLOCK_SIZE = 9;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 9;
/*     */   public static final int MAX_SIZE = 9;
/*     */   public short index;
/*     */   public int blockId;
/*     */   public short filler;
/*     */   public byte rotation;
/*     */   
/*     */   public SetBlockCmd() {}
/*     */   
/*     */   public SetBlockCmd(short index, int blockId, short filler, byte rotation) {
/*  29 */     this.index = index;
/*  30 */     this.blockId = blockId;
/*  31 */     this.filler = filler;
/*  32 */     this.rotation = rotation;
/*     */   }
/*     */   
/*     */   public SetBlockCmd(@Nonnull SetBlockCmd other) {
/*  36 */     this.index = other.index;
/*  37 */     this.blockId = other.blockId;
/*  38 */     this.filler = other.filler;
/*  39 */     this.rotation = other.rotation;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static SetBlockCmd deserialize(@Nonnull ByteBuf buf, int offset) {
/*  44 */     SetBlockCmd obj = new SetBlockCmd();
/*     */     
/*  46 */     obj.index = buf.getShortLE(offset + 0);
/*  47 */     obj.blockId = buf.getIntLE(offset + 2);
/*  48 */     obj.filler = buf.getShortLE(offset + 6);
/*  49 */     obj.rotation = buf.getByte(offset + 8);
/*     */ 
/*     */     
/*  52 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  56 */     return 9;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  61 */     buf.writeShortLE(this.index);
/*  62 */     buf.writeIntLE(this.blockId);
/*  63 */     buf.writeShortLE(this.filler);
/*  64 */     buf.writeByte(this.rotation);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  70 */     return 9;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  74 */     if (buffer.readableBytes() - offset < 9) {
/*  75 */       return ValidationResult.error("Buffer too small: expected at least 9 bytes");
/*     */     }
/*     */ 
/*     */     
/*  79 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public SetBlockCmd clone() {
/*  83 */     SetBlockCmd copy = new SetBlockCmd();
/*  84 */     copy.index = this.index;
/*  85 */     copy.blockId = this.blockId;
/*  86 */     copy.filler = this.filler;
/*  87 */     copy.rotation = this.rotation;
/*  88 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     SetBlockCmd other;
/*  94 */     if (this == obj) return true; 
/*  95 */     if (obj instanceof SetBlockCmd) { other = (SetBlockCmd)obj; } else { return false; }
/*  96 */      return (this.index == other.index && this.blockId == other.blockId && this.filler == other.filler && this.rotation == other.rotation);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 101 */     return Objects.hash(new Object[] { Short.valueOf(this.index), Integer.valueOf(this.blockId), Short.valueOf(this.filler), Byte.valueOf(this.rotation) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\world\SetBlockCmd.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */