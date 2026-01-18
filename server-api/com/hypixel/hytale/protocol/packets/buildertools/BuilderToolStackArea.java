/*     */ package com.hypixel.hytale.protocol.packets.buildertools;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.BlockPosition;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BuilderToolStackArea
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 404;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 41;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 41;
/*     */   
/*     */   public int getId() {
/*  25 */     return 404;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final int MAX_SIZE = 41;
/*     */   
/*     */   @Nullable
/*     */   public BlockPosition selectionMin;
/*     */   
/*     */   @Nullable
/*     */   public BlockPosition selectionMax;
/*     */   public int xNormal;
/*     */   
/*     */   public BuilderToolStackArea(@Nullable BlockPosition selectionMin, @Nullable BlockPosition selectionMax, int xNormal, int yNormal, int zNormal, int numStacks) {
/*  39 */     this.selectionMin = selectionMin;
/*  40 */     this.selectionMax = selectionMax;
/*  41 */     this.xNormal = xNormal;
/*  42 */     this.yNormal = yNormal;
/*  43 */     this.zNormal = zNormal;
/*  44 */     this.numStacks = numStacks;
/*     */   } public int yNormal; public int zNormal; public int numStacks;
/*     */   public BuilderToolStackArea() {}
/*     */   public BuilderToolStackArea(@Nonnull BuilderToolStackArea other) {
/*  48 */     this.selectionMin = other.selectionMin;
/*  49 */     this.selectionMax = other.selectionMax;
/*  50 */     this.xNormal = other.xNormal;
/*  51 */     this.yNormal = other.yNormal;
/*  52 */     this.zNormal = other.zNormal;
/*  53 */     this.numStacks = other.numStacks;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static BuilderToolStackArea deserialize(@Nonnull ByteBuf buf, int offset) {
/*  58 */     BuilderToolStackArea obj = new BuilderToolStackArea();
/*  59 */     byte nullBits = buf.getByte(offset);
/*  60 */     if ((nullBits & 0x1) != 0) obj.selectionMin = BlockPosition.deserialize(buf, offset + 1); 
/*  61 */     if ((nullBits & 0x2) != 0) obj.selectionMax = BlockPosition.deserialize(buf, offset + 13); 
/*  62 */     obj.xNormal = buf.getIntLE(offset + 25);
/*  63 */     obj.yNormal = buf.getIntLE(offset + 29);
/*  64 */     obj.zNormal = buf.getIntLE(offset + 33);
/*  65 */     obj.numStacks = buf.getIntLE(offset + 37);
/*     */ 
/*     */     
/*  68 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  72 */     return 41;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  77 */     byte nullBits = 0;
/*  78 */     if (this.selectionMin != null) nullBits = (byte)(nullBits | 0x1); 
/*  79 */     if (this.selectionMax != null) nullBits = (byte)(nullBits | 0x2); 
/*  80 */     buf.writeByte(nullBits);
/*     */     
/*  82 */     if (this.selectionMin != null) { this.selectionMin.serialize(buf); } else { buf.writeZero(12); }
/*  83 */      if (this.selectionMax != null) { this.selectionMax.serialize(buf); } else { buf.writeZero(12); }
/*  84 */      buf.writeIntLE(this.xNormal);
/*  85 */     buf.writeIntLE(this.yNormal);
/*  86 */     buf.writeIntLE(this.zNormal);
/*  87 */     buf.writeIntLE(this.numStacks);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  93 */     return 41;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  97 */     if (buffer.readableBytes() - offset < 41) {
/*  98 */       return ValidationResult.error("Buffer too small: expected at least 41 bytes");
/*     */     }
/*     */ 
/*     */     
/* 102 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public BuilderToolStackArea clone() {
/* 106 */     BuilderToolStackArea copy = new BuilderToolStackArea();
/* 107 */     copy.selectionMin = (this.selectionMin != null) ? this.selectionMin.clone() : null;
/* 108 */     copy.selectionMax = (this.selectionMax != null) ? this.selectionMax.clone() : null;
/* 109 */     copy.xNormal = this.xNormal;
/* 110 */     copy.yNormal = this.yNormal;
/* 111 */     copy.zNormal = this.zNormal;
/* 112 */     copy.numStacks = this.numStacks;
/* 113 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     BuilderToolStackArea other;
/* 119 */     if (this == obj) return true; 
/* 120 */     if (obj instanceof BuilderToolStackArea) { other = (BuilderToolStackArea)obj; } else { return false; }
/* 121 */      return (Objects.equals(this.selectionMin, other.selectionMin) && Objects.equals(this.selectionMax, other.selectionMax) && this.xNormal == other.xNormal && this.yNormal == other.yNormal && this.zNormal == other.zNormal && this.numStacks == other.numStacks);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 126 */     return Objects.hash(new Object[] { this.selectionMin, this.selectionMax, Integer.valueOf(this.xNormal), Integer.valueOf(this.yNormal), Integer.valueOf(this.zNormal), Integer.valueOf(this.numStacks) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\buildertools\BuilderToolStackArea.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */