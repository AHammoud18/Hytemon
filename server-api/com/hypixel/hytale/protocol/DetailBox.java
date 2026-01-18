/*    */ package com.hypixel.hytale.protocol;
/*    */ 
/*    */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DetailBox
/*    */ {
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*    */   public static final int FIXED_BLOCK_SIZE = 37;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 37;
/*    */   public static final int MAX_SIZE = 37;
/*    */   @Nullable
/*    */   public Vector3f offset;
/*    */   @Nullable
/*    */   public Hitbox box;
/*    */   
/*    */   public DetailBox() {}
/*    */   
/*    */   public DetailBox(@Nullable Vector3f offset, @Nullable Hitbox box) {
/* 27 */     this.offset = offset;
/* 28 */     this.box = box;
/*    */   }
/*    */   
/*    */   public DetailBox(@Nonnull DetailBox other) {
/* 32 */     this.offset = other.offset;
/* 33 */     this.box = other.box;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static DetailBox deserialize(@Nonnull ByteBuf buf, int offset) {
/* 38 */     DetailBox obj = new DetailBox();
/* 39 */     byte nullBits = buf.getByte(offset);
/* 40 */     if ((nullBits & 0x1) != 0) obj.offset = Vector3f.deserialize(buf, offset + 1); 
/* 41 */     if ((nullBits & 0x2) != 0) obj.box = Hitbox.deserialize(buf, offset + 13);
/*    */ 
/*    */     
/* 44 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 48 */     return 37;
/*    */   }
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 52 */     byte nullBits = 0;
/* 53 */     if (this.offset != null) nullBits = (byte)(nullBits | 0x1); 
/* 54 */     if (this.box != null) nullBits = (byte)(nullBits | 0x2); 
/* 55 */     buf.writeByte(nullBits);
/*    */     
/* 57 */     if (this.offset != null) { this.offset.serialize(buf); } else { buf.writeZero(12); }
/* 58 */      if (this.box != null) { this.box.serialize(buf); } else { buf.writeZero(24); }
/*    */   
/*    */   }
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 64 */     return 37;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 68 */     if (buffer.readableBytes() - offset < 37) {
/* 69 */       return ValidationResult.error("Buffer too small: expected at least 37 bytes");
/*    */     }
/*    */ 
/*    */     
/* 73 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public DetailBox clone() {
/* 77 */     DetailBox copy = new DetailBox();
/* 78 */     copy.offset = (this.offset != null) ? this.offset.clone() : null;
/* 79 */     copy.box = (this.box != null) ? this.box.clone() : null;
/* 80 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     DetailBox other;
/* 86 */     if (this == obj) return true; 
/* 87 */     if (obj instanceof DetailBox) { other = (DetailBox)obj; } else { return false; }
/* 88 */      return (Objects.equals(this.offset, other.offset) && Objects.equals(this.box, other.box));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 93 */     return Objects.hash(new Object[] { this.offset, this.box });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\DetailBox.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */