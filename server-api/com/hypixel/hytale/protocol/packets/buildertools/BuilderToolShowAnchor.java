/*     */ package com.hypixel.hytale.protocol.packets.buildertools;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ public class BuilderToolShowAnchor
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 415;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*     */   public static final int FIXED_BLOCK_SIZE = 12;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 12;
/*     */   public static final int MAX_SIZE = 12;
/*     */   public int x;
/*     */   public int y;
/*     */   public int z;
/*     */   
/*     */   public int getId() {
/*  25 */     return 415;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BuilderToolShowAnchor() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public BuilderToolShowAnchor(int x, int y, int z) {
/*  36 */     this.x = x;
/*  37 */     this.y = y;
/*  38 */     this.z = z;
/*     */   }
/*     */   
/*     */   public BuilderToolShowAnchor(@Nonnull BuilderToolShowAnchor other) {
/*  42 */     this.x = other.x;
/*  43 */     this.y = other.y;
/*  44 */     this.z = other.z;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static BuilderToolShowAnchor deserialize(@Nonnull ByteBuf buf, int offset) {
/*  49 */     BuilderToolShowAnchor obj = new BuilderToolShowAnchor();
/*     */     
/*  51 */     obj.x = buf.getIntLE(offset + 0);
/*  52 */     obj.y = buf.getIntLE(offset + 4);
/*  53 */     obj.z = buf.getIntLE(offset + 8);
/*     */ 
/*     */     
/*  56 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  60 */     return 12;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  66 */     buf.writeIntLE(this.x);
/*  67 */     buf.writeIntLE(this.y);
/*  68 */     buf.writeIntLE(this.z);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  74 */     return 12;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  78 */     if (buffer.readableBytes() - offset < 12) {
/*  79 */       return ValidationResult.error("Buffer too small: expected at least 12 bytes");
/*     */     }
/*     */ 
/*     */     
/*  83 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public BuilderToolShowAnchor clone() {
/*  87 */     BuilderToolShowAnchor copy = new BuilderToolShowAnchor();
/*  88 */     copy.x = this.x;
/*  89 */     copy.y = this.y;
/*  90 */     copy.z = this.z;
/*  91 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     BuilderToolShowAnchor other;
/*  97 */     if (this == obj) return true; 
/*  98 */     if (obj instanceof BuilderToolShowAnchor) { other = (BuilderToolShowAnchor)obj; } else { return false; }
/*  99 */      return (this.x == other.x && this.y == other.y && this.z == other.z);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 104 */     return Objects.hash(new Object[] { Integer.valueOf(this.x), Integer.valueOf(this.y), Integer.valueOf(this.z) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\buildertools\BuilderToolShowAnchor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */