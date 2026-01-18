/*    */ package com.hypixel.hytale.protocol;
/*    */ 
/*    */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Modifier
/*    */ {
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 6;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 6;
/*    */   public static final int MAX_SIZE = 6;
/*    */   @Nonnull
/* 20 */   public ModifierTarget target = ModifierTarget.Min; @Nonnull
/* 21 */   public CalculationType calculationType = CalculationType.Additive;
/*    */ 
/*    */   
/*    */   public float amount;
/*    */ 
/*    */   
/*    */   public Modifier(@Nonnull ModifierTarget target, @Nonnull CalculationType calculationType, float amount) {
/* 28 */     this.target = target;
/* 29 */     this.calculationType = calculationType;
/* 30 */     this.amount = amount;
/*    */   }
/*    */   
/*    */   public Modifier(@Nonnull Modifier other) {
/* 34 */     this.target = other.target;
/* 35 */     this.calculationType = other.calculationType;
/* 36 */     this.amount = other.amount;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static Modifier deserialize(@Nonnull ByteBuf buf, int offset) {
/* 41 */     Modifier obj = new Modifier();
/*    */     
/* 43 */     obj.target = ModifierTarget.fromValue(buf.getByte(offset + 0));
/* 44 */     obj.calculationType = CalculationType.fromValue(buf.getByte(offset + 1));
/* 45 */     obj.amount = buf.getFloatLE(offset + 2);
/*    */ 
/*    */     
/* 48 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 52 */     return 6;
/*    */   }
/*    */ 
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 57 */     buf.writeByte(this.target.getValue());
/* 58 */     buf.writeByte(this.calculationType.getValue());
/* 59 */     buf.writeFloatLE(this.amount);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 65 */     return 6;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 69 */     if (buffer.readableBytes() - offset < 6) {
/* 70 */       return ValidationResult.error("Buffer too small: expected at least 6 bytes");
/*    */     }
/*    */ 
/*    */     
/* 74 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public Modifier clone() {
/* 78 */     Modifier copy = new Modifier();
/* 79 */     copy.target = this.target;
/* 80 */     copy.calculationType = this.calculationType;
/* 81 */     copy.amount = this.amount;
/* 82 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     Modifier other;
/* 88 */     if (this == obj) return true; 
/* 89 */     if (obj instanceof Modifier) { other = (Modifier)obj; } else { return false; }
/* 90 */      return (Objects.equals(this.target, other.target) && Objects.equals(this.calculationType, other.calculationType) && this.amount == other.amount);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 95 */     return Objects.hash(new Object[] { this.target, this.calculationType, Float.valueOf(this.amount) });
/*    */   }
/*    */   
/*    */   public Modifier() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\Modifier.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */