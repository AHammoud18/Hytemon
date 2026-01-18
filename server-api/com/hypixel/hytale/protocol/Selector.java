/*    */ package com.hypixel.hytale.protocol;
/*    */ 
/*    */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*    */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*    */ import com.hypixel.hytale.protocol.io.VarInt;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class Selector
/*    */ {
/*    */   public static final int MAX_SIZE = 42;
/*    */   
/*    */   @Nonnull
/*    */   public static Selector deserialize(@Nonnull ByteBuf buf, int offset) {
/* 20 */     int typeId = VarInt.peek(buf, offset);
/* 21 */     int typeIdLen = VarInt.length(buf, offset);
/*    */     
/* 23 */     switch (typeId) { case 0: 
/*    */       case 1: 
/*    */       case 2: 
/*    */       case 3:
/*    */       
/*    */       case 4:
/* 29 */        }  throw ProtocolException.unknownPolymorphicType("Selector", typeId);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: iload_1
/*    */     //   2: invokestatic peek : (Lio/netty/buffer/ByteBuf;I)I
/*    */     //   5: istore_2
/*    */     //   6: aload_0
/*    */     //   7: iload_1
/*    */     //   8: invokestatic length : (Lio/netty/buffer/ByteBuf;I)I
/*    */     //   11: istore_3
/*    */     //   12: iload_3
/*    */     //   13: iload_2
/*    */     //   14: tableswitch default -> 98, 0 -> 48, 1 -> 58, 2 -> 68, 3 -> 78, 4 -> 88
/*    */     //   48: aload_0
/*    */     //   49: iload_1
/*    */     //   50: iload_3
/*    */     //   51: iadd
/*    */     //   52: invokestatic computeBytesConsumed : (Lio/netty/buffer/ByteBuf;I)I
/*    */     //   55: goto -> 105
/*    */     //   58: aload_0
/*    */     //   59: iload_1
/*    */     //   60: iload_3
/*    */     //   61: iadd
/*    */     //   62: invokestatic computeBytesConsumed : (Lio/netty/buffer/ByteBuf;I)I
/*    */     //   65: goto -> 105
/*    */     //   68: aload_0
/*    */     //   69: iload_1
/*    */     //   70: iload_3
/*    */     //   71: iadd
/*    */     //   72: invokestatic computeBytesConsumed : (Lio/netty/buffer/ByteBuf;I)I
/*    */     //   75: goto -> 105
/*    */     //   78: aload_0
/*    */     //   79: iload_1
/*    */     //   80: iload_3
/*    */     //   81: iadd
/*    */     //   82: invokestatic computeBytesConsumed : (Lio/netty/buffer/ByteBuf;I)I
/*    */     //   85: goto -> 105
/*    */     //   88: aload_0
/*    */     //   89: iload_1
/*    */     //   90: iload_3
/*    */     //   91: iadd
/*    */     //   92: invokestatic computeBytesConsumed : (Lio/netty/buffer/ByteBuf;I)I
/*    */     //   95: goto -> 105
/*    */     //   98: ldc 'Selector'
/*    */     //   100: iload_2
/*    */     //   101: invokestatic unknownPolymorphicType : (Ljava/lang/String;I)Lcom/hypixel/hytale/protocol/io/ProtocolException;
/*    */     //   104: athrow
/*    */     //   105: iadd
/*    */     //   106: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #34	-> 0
/*    */     //   #35	-> 6
/*    */     //   #37	-> 12
/*    */     //   #38	-> 48
/*    */     //   #39	-> 58
/*    */     //   #40	-> 68
/*    */     //   #41	-> 78
/*    */     //   #42	-> 88
/*    */     //   #43	-> 98
/*    */     //   #44	-> 105
/*    */     //   #37	-> 106
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	107	0	buf	Lio/netty/buffer/ByteBuf;
/*    */     //   0	107	1	offset	I
/*    */     //   6	101	2	typeId	I
/*    */     //   12	95	3	typeIdLen	I
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getTypeId() {
/* 48 */     Selector selector = this; if (selector instanceof AOECircleSelector) { AOECircleSelector sub = (AOECircleSelector)selector; return 0; }
/* 49 */      selector = this; if (selector instanceof AOECylinderSelector) { AOECylinderSelector sub = (AOECylinderSelector)selector; return 1; }
/* 50 */      selector = this; if (selector instanceof RaycastSelector) { RaycastSelector sub = (RaycastSelector)selector; return 2; }
/* 51 */      selector = this; if (selector instanceof HorizontalSelector) { HorizontalSelector sub = (HorizontalSelector)selector; return 3; }
/* 52 */      selector = this; if (selector instanceof StabSelector) { StabSelector sub = (StabSelector)selector; return 4; }
/* 53 */      throw new IllegalStateException("Unknown subtype: " + getClass().getName());
/*    */   }
/*    */   public abstract int serialize(@Nonnull ByteBuf paramByteBuf);
/*    */   
/*    */   public abstract int computeSize();
/*    */   
/*    */   public int serializeWithTypeId(@Nonnull ByteBuf buf) {
/* 60 */     int startPos = buf.writerIndex();
/* 61 */     VarInt.write(buf, getTypeId());
/* 62 */     serialize(buf);
/* 63 */     return buf.writerIndex() - startPos;
/*    */   }
/*    */   
/*    */   public int computeSizeWithTypeId() {
/* 67 */     return VarInt.size(getTypeId()) + computeSize();
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 71 */     int typeId = VarInt.peek(buffer, offset);
/* 72 */     int typeIdLen = VarInt.length(buffer, offset);
/*    */     
/* 74 */     switch (typeId) { case 0: case 1: case 2: case 3: case 4:  }  return 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 80 */       ValidationResult.error("Unknown polymorphic type ID " + typeId + " for Selector");
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\Selector.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */