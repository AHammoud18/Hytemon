/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MountedUpdate
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 48;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 48;
/*     */   public static final int MAX_SIZE = 48;
/*     */   public int mountedToEntity;
/*     */   @Nullable
/*     */   public Vector3f attachmentOffset;
/*     */   @Nonnull
/*  22 */   public MountController controller = MountController.Minecart;
/*     */   
/*     */   @Nullable
/*     */   public BlockMount block;
/*     */ 
/*     */   
/*     */   public MountedUpdate(int mountedToEntity, @Nullable Vector3f attachmentOffset, @Nonnull MountController controller, @Nullable BlockMount block) {
/*  29 */     this.mountedToEntity = mountedToEntity;
/*  30 */     this.attachmentOffset = attachmentOffset;
/*  31 */     this.controller = controller;
/*  32 */     this.block = block;
/*     */   }
/*     */   
/*     */   public MountedUpdate(@Nonnull MountedUpdate other) {
/*  36 */     this.mountedToEntity = other.mountedToEntity;
/*  37 */     this.attachmentOffset = other.attachmentOffset;
/*  38 */     this.controller = other.controller;
/*  39 */     this.block = other.block;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static MountedUpdate deserialize(@Nonnull ByteBuf buf, int offset) {
/*  44 */     MountedUpdate obj = new MountedUpdate();
/*  45 */     byte nullBits = buf.getByte(offset);
/*  46 */     obj.mountedToEntity = buf.getIntLE(offset + 1);
/*  47 */     if ((nullBits & 0x1) != 0) obj.attachmentOffset = Vector3f.deserialize(buf, offset + 5); 
/*  48 */     obj.controller = MountController.fromValue(buf.getByte(offset + 17));
/*  49 */     if ((nullBits & 0x2) != 0) obj.block = BlockMount.deserialize(buf, offset + 18);
/*     */ 
/*     */     
/*  52 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  56 */     return 48;
/*     */   }
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  60 */     byte nullBits = 0;
/*  61 */     if (this.attachmentOffset != null) nullBits = (byte)(nullBits | 0x1); 
/*  62 */     if (this.block != null) nullBits = (byte)(nullBits | 0x2); 
/*  63 */     buf.writeByte(nullBits);
/*     */     
/*  65 */     buf.writeIntLE(this.mountedToEntity);
/*  66 */     if (this.attachmentOffset != null) { this.attachmentOffset.serialize(buf); } else { buf.writeZero(12); }
/*  67 */      buf.writeByte(this.controller.getValue());
/*  68 */     if (this.block != null) { this.block.serialize(buf); } else { buf.writeZero(30); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  74 */     return 48;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  78 */     if (buffer.readableBytes() - offset < 48) {
/*  79 */       return ValidationResult.error("Buffer too small: expected at least 48 bytes");
/*     */     }
/*     */ 
/*     */     
/*  83 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public MountedUpdate clone() {
/*  87 */     MountedUpdate copy = new MountedUpdate();
/*  88 */     copy.mountedToEntity = this.mountedToEntity;
/*  89 */     copy.attachmentOffset = (this.attachmentOffset != null) ? this.attachmentOffset.clone() : null;
/*  90 */     copy.controller = this.controller;
/*  91 */     copy.block = (this.block != null) ? this.block.clone() : null;
/*  92 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     MountedUpdate other;
/*  98 */     if (this == obj) return true; 
/*  99 */     if (obj instanceof MountedUpdate) { other = (MountedUpdate)obj; } else { return false; }
/* 100 */      return (this.mountedToEntity == other.mountedToEntity && Objects.equals(this.attachmentOffset, other.attachmentOffset) && Objects.equals(this.controller, other.controller) && Objects.equals(this.block, other.block));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 105 */     return Objects.hash(new Object[] { Integer.valueOf(this.mountedToEntity), this.attachmentOffset, this.controller, this.block });
/*     */   }
/*     */   
/*     */   public MountedUpdate() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\MountedUpdate.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */