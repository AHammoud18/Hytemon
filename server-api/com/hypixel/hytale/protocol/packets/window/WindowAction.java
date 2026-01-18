/*    */ package com.hypixel.hytale.protocol.packets.window;
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
/*    */ public abstract class WindowAction
/*    */ {
/*    */   public static final int MAX_SIZE = 32768023;
/*    */   
/*    */   @Nonnull
/*    */   public static WindowAction deserialize(@Nonnull ByteBuf buf, int offset) {
/* 20 */     int typeId = VarInt.peek(buf, offset);
/* 21 */     int typeIdLen = VarInt.length(buf, offset);
/*    */     
/* 23 */     switch (typeId) { case 0: 
/*    */       case 1: 
/*    */       case 2: 
/*    */       case 3: 
/*    */       case 4: 
/*    */       case 5: 
/*    */       case 6: 
/*    */       case 7:
/*    */       
/*    */       case 8:
/* 33 */        }  throw ProtocolException.unknownPolymorphicType("WindowAction", typeId);
/*    */   }
/*    */ 
/*    */ 
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
/*    */     //   14: tableswitch default -> 154, 0 -> 64, 1 -> 74, 2 -> 84, 3 -> 94, 4 -> 104, 5 -> 114, 6 -> 124, 7 -> 134, 8 -> 144
/*    */     //   64: aload_0
/*    */     //   65: iload_1
/*    */     //   66: iload_3
/*    */     //   67: iadd
/*    */     //   68: invokestatic computeBytesConsumed : (Lio/netty/buffer/ByteBuf;I)I
/*    */     //   71: goto -> 161
/*    */     //   74: aload_0
/*    */     //   75: iload_1
/*    */     //   76: iload_3
/*    */     //   77: iadd
/*    */     //   78: invokestatic computeBytesConsumed : (Lio/netty/buffer/ByteBuf;I)I
/*    */     //   81: goto -> 161
/*    */     //   84: aload_0
/*    */     //   85: iload_1
/*    */     //   86: iload_3
/*    */     //   87: iadd
/*    */     //   88: invokestatic computeBytesConsumed : (Lio/netty/buffer/ByteBuf;I)I
/*    */     //   91: goto -> 161
/*    */     //   94: aload_0
/*    */     //   95: iload_1
/*    */     //   96: iload_3
/*    */     //   97: iadd
/*    */     //   98: invokestatic computeBytesConsumed : (Lio/netty/buffer/ByteBuf;I)I
/*    */     //   101: goto -> 161
/*    */     //   104: aload_0
/*    */     //   105: iload_1
/*    */     //   106: iload_3
/*    */     //   107: iadd
/*    */     //   108: invokestatic computeBytesConsumed : (Lio/netty/buffer/ByteBuf;I)I
/*    */     //   111: goto -> 161
/*    */     //   114: aload_0
/*    */     //   115: iload_1
/*    */     //   116: iload_3
/*    */     //   117: iadd
/*    */     //   118: invokestatic computeBytesConsumed : (Lio/netty/buffer/ByteBuf;I)I
/*    */     //   121: goto -> 161
/*    */     //   124: aload_0
/*    */     //   125: iload_1
/*    */     //   126: iload_3
/*    */     //   127: iadd
/*    */     //   128: invokestatic computeBytesConsumed : (Lio/netty/buffer/ByteBuf;I)I
/*    */     //   131: goto -> 161
/*    */     //   134: aload_0
/*    */     //   135: iload_1
/*    */     //   136: iload_3
/*    */     //   137: iadd
/*    */     //   138: invokestatic computeBytesConsumed : (Lio/netty/buffer/ByteBuf;I)I
/*    */     //   141: goto -> 161
/*    */     //   144: aload_0
/*    */     //   145: iload_1
/*    */     //   146: iload_3
/*    */     //   147: iadd
/*    */     //   148: invokestatic computeBytesConsumed : (Lio/netty/buffer/ByteBuf;I)I
/*    */     //   151: goto -> 161
/*    */     //   154: ldc 'WindowAction'
/*    */     //   156: iload_2
/*    */     //   157: invokestatic unknownPolymorphicType : (Ljava/lang/String;I)Lcom/hypixel/hytale/protocol/io/ProtocolException;
/*    */     //   160: athrow
/*    */     //   161: iadd
/*    */     //   162: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #38	-> 0
/*    */     //   #39	-> 6
/*    */     //   #41	-> 12
/*    */     //   #42	-> 64
/*    */     //   #43	-> 74
/*    */     //   #44	-> 84
/*    */     //   #45	-> 94
/*    */     //   #46	-> 104
/*    */     //   #47	-> 114
/*    */     //   #48	-> 124
/*    */     //   #49	-> 134
/*    */     //   #50	-> 144
/*    */     //   #51	-> 154
/*    */     //   #52	-> 161
/*    */     //   #41	-> 162
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	163	0	buf	Lio/netty/buffer/ByteBuf;
/*    */     //   0	163	1	offset	I
/*    */     //   6	157	2	typeId	I
/*    */     //   12	151	3	typeIdLen	I
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getTypeId() {
/* 56 */     WindowAction windowAction = this; if (windowAction instanceof CraftRecipeAction) { CraftRecipeAction sub = (CraftRecipeAction)windowAction; return 0; }
/* 57 */      windowAction = this; if (windowAction instanceof TierUpgradeAction) { TierUpgradeAction sub = (TierUpgradeAction)windowAction; return 1; }
/* 58 */      windowAction = this; if (windowAction instanceof SelectSlotAction) { SelectSlotAction sub = (SelectSlotAction)windowAction; return 2; }
/* 59 */      windowAction = this; if (windowAction instanceof ChangeBlockAction) { ChangeBlockAction sub = (ChangeBlockAction)windowAction; return 3; }
/* 60 */      windowAction = this; if (windowAction instanceof SetActiveAction) { SetActiveAction sub = (SetActiveAction)windowAction; return 4; }
/* 61 */      windowAction = this; if (windowAction instanceof CraftItemAction) { CraftItemAction sub = (CraftItemAction)windowAction; return 5; }
/* 62 */      windowAction = this; if (windowAction instanceof UpdateCategoryAction) { UpdateCategoryAction sub = (UpdateCategoryAction)windowAction; return 6; }
/* 63 */      windowAction = this; if (windowAction instanceof CancelCraftingAction) { CancelCraftingAction sub = (CancelCraftingAction)windowAction; return 7; }
/* 64 */      windowAction = this; if (windowAction instanceof SortItemsAction) { SortItemsAction sub = (SortItemsAction)windowAction; return 8; }
/* 65 */      throw new IllegalStateException("Unknown subtype: " + getClass().getName());
/*    */   }
/*    */   public abstract int serialize(@Nonnull ByteBuf paramByteBuf);
/*    */   
/*    */   public abstract int computeSize();
/*    */   
/*    */   public int serializeWithTypeId(@Nonnull ByteBuf buf) {
/* 72 */     int startPos = buf.writerIndex();
/* 73 */     VarInt.write(buf, getTypeId());
/* 74 */     serialize(buf);
/* 75 */     return buf.writerIndex() - startPos;
/*    */   }
/*    */   
/*    */   public int computeSizeWithTypeId() {
/* 79 */     return VarInt.size(getTypeId()) + computeSize();
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 83 */     int typeId = VarInt.peek(buffer, offset);
/* 84 */     int typeIdLen = VarInt.length(buffer, offset);
/*    */     
/* 86 */     switch (typeId) { case 0: case 1: case 2: case 3: case 4: case 5: case 6: case 7: case 8:  }  return 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 96 */       ValidationResult.error("Unknown polymorphic type ID " + typeId + " for WindowAction");
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\window\WindowAction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */