/*    */ package com.hypixel.hytale.server.core.asset.type.blocktype.config;
/*    */ public final class RotationTuple extends Record { private final int index;
/*    */   private final Rotation yaw;
/*    */   private final Rotation pitch;
/*    */   private final Rotation roll;
/*    */   
/*  7 */   public RotationTuple(int index, Rotation yaw, Rotation pitch, Rotation roll) { this.index = index; this.yaw = yaw; this.pitch = pitch; this.roll = roll; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/RotationTuple;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  7 */     //   0	7	0	this	Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/RotationTuple; } public int index() { return this.index; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/RotationTuple;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/RotationTuple; } public final boolean equals(Object o) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/RotationTuple;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lcom/hypixel/hytale/server/core/asset/type/blocktype/config/RotationTuple;
/*  7 */     //   0	8	1	o	Ljava/lang/Object; } public Rotation yaw() { return this.yaw; } public Rotation pitch() { return this.pitch; } public Rotation roll() { return this.roll; }
/*  8 */    public static final RotationTuple[] EMPTY_ARRAY = new RotationTuple[0];
/*  9 */   public static final RotationTuple NONE = new RotationTuple(0, Rotation.None, Rotation.None, Rotation.None);
/*    */   public static final int NONE_INDEX = 0;
/*    */   @Nonnull
/*    */   public static final RotationTuple[] VALUES;
/*    */   
/*    */   static {
/* 15 */     RotationTuple[] arr = new RotationTuple[Rotation.VALUES.length * Rotation.VALUES.length * Rotation.VALUES.length];
/* 16 */     arr[0] = NONE;
/* 17 */     for (Rotation roll : Rotation.VALUES) {
/* 18 */       for (Rotation pitch : Rotation.VALUES) {
/* 19 */         for (Rotation yaw : Rotation.VALUES) {
/* 20 */           if (yaw != Rotation.None || pitch != Rotation.None || roll != Rotation.None) {
/* 21 */             int index = index(yaw, pitch, roll);
/* 22 */             arr[index] = new RotationTuple(index, yaw, pitch, roll);
/*    */           } 
/*    */         } 
/*    */       } 
/* 26 */     }  VALUES = arr;
/*    */   }
/*    */   
/*    */   public static RotationTuple of(@Nonnull Rotation yaw, @Nonnull Rotation pitch, @Nonnull Rotation roll) {
/* 30 */     return VALUES[index(yaw, pitch, roll)];
/*    */   }
/*    */   
/*    */   public static RotationTuple of(@Nonnull Rotation yaw, @Nonnull Rotation pitch) {
/* 34 */     return VALUES[index(yaw, pitch, Rotation.None)];
/*    */   }
/*    */   
/*    */   public static int index(@Nonnull Rotation yaw, @Nonnull Rotation pitch, @Nonnull Rotation roll) {
/* 38 */     return roll.ordinal() * Rotation.VALUES.length * Rotation.VALUES.length + pitch.ordinal() * Rotation.VALUES.length + yaw.ordinal();
/*    */   }
/*    */   
/*    */   public static RotationTuple get(int index) {
/* 42 */     return VALUES[index];
/*    */   }
/*    */   
/*    */   public static RotationTuple getRotation(@Nonnull RotationTuple[] rotations, @Nonnull RotationTuple pair, @Nonnull Rotation rotation) {
/* 46 */     int index = 0;
/* 47 */     for (int i = 0; i < rotations.length; i++) {
/* 48 */       RotationTuple rotationPair = rotations[i];
/* 49 */       if (pair.equals(rotationPair)) {
/* 50 */         index = i;
/*    */         break;
/*    */       } 
/*    */     } 
/* 54 */     return rotations[(index + rotation.ordinal()) % Rotation.VALUES.length];
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Vector3d rotate(@Nonnull Vector3d vector) {
/* 65 */     return Rotation.rotate(vector, this.yaw, this.pitch, this.roll);
/*    */   } }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\blocktype\config\RotationTuple.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */