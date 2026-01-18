/*     */ package com.hypixel.hytale.protocol.packets.buildertools;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class BuilderToolExtrudeAction implements Packet {
/*     */   public static final int PACKET_ID = 403;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*     */   public static final int FIXED_BLOCK_SIZE = 24;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 24;
/*     */   public static final int MAX_SIZE = 24;
/*     */   public int x;
/*     */   public int y;
/*     */   public int z;
/*     */   public int xNormal;
/*     */   public int yNormal;
/*     */   public int zNormal;
/*     */   
/*     */   public int getId() {
/*  25 */     return 403;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BuilderToolExtrudeAction() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BuilderToolExtrudeAction(int x, int y, int z, int xNormal, int yNormal, int zNormal) {
/*  39 */     this.x = x;
/*  40 */     this.y = y;
/*  41 */     this.z = z;
/*  42 */     this.xNormal = xNormal;
/*  43 */     this.yNormal = yNormal;
/*  44 */     this.zNormal = zNormal;
/*     */   }
/*     */   
/*     */   public BuilderToolExtrudeAction(@Nonnull BuilderToolExtrudeAction other) {
/*  48 */     this.x = other.x;
/*  49 */     this.y = other.y;
/*  50 */     this.z = other.z;
/*  51 */     this.xNormal = other.xNormal;
/*  52 */     this.yNormal = other.yNormal;
/*  53 */     this.zNormal = other.zNormal;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static BuilderToolExtrudeAction deserialize(@Nonnull ByteBuf buf, int offset) {
/*  58 */     BuilderToolExtrudeAction obj = new BuilderToolExtrudeAction();
/*     */     
/*  60 */     obj.x = buf.getIntLE(offset + 0);
/*  61 */     obj.y = buf.getIntLE(offset + 4);
/*  62 */     obj.z = buf.getIntLE(offset + 8);
/*  63 */     obj.xNormal = buf.getIntLE(offset + 12);
/*  64 */     obj.yNormal = buf.getIntLE(offset + 16);
/*  65 */     obj.zNormal = buf.getIntLE(offset + 20);
/*     */ 
/*     */     
/*  68 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  72 */     return 24;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  78 */     buf.writeIntLE(this.x);
/*  79 */     buf.writeIntLE(this.y);
/*  80 */     buf.writeIntLE(this.z);
/*  81 */     buf.writeIntLE(this.xNormal);
/*  82 */     buf.writeIntLE(this.yNormal);
/*  83 */     buf.writeIntLE(this.zNormal);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  89 */     return 24;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  93 */     if (buffer.readableBytes() - offset < 24) {
/*  94 */       return ValidationResult.error("Buffer too small: expected at least 24 bytes");
/*     */     }
/*     */ 
/*     */     
/*  98 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public BuilderToolExtrudeAction clone() {
/* 102 */     BuilderToolExtrudeAction copy = new BuilderToolExtrudeAction();
/* 103 */     copy.x = this.x;
/* 104 */     copy.y = this.y;
/* 105 */     copy.z = this.z;
/* 106 */     copy.xNormal = this.xNormal;
/* 107 */     copy.yNormal = this.yNormal;
/* 108 */     copy.zNormal = this.zNormal;
/* 109 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     BuilderToolExtrudeAction other;
/* 115 */     if (this == obj) return true; 
/* 116 */     if (obj instanceof BuilderToolExtrudeAction) { other = (BuilderToolExtrudeAction)obj; } else { return false; }
/* 117 */      return (this.x == other.x && this.y == other.y && this.z == other.z && this.xNormal == other.xNormal && this.yNormal == other.yNormal && this.zNormal == other.zNormal);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 122 */     return Objects.hash(new Object[] { Integer.valueOf(this.x), Integer.valueOf(this.y), Integer.valueOf(this.z), Integer.valueOf(this.xNormal), Integer.valueOf(this.yNormal), Integer.valueOf(this.zNormal) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\buildertools\BuilderToolExtrudeAction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */