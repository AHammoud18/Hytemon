/*     */ package com.hypixel.hytale.server.core.prefab.selection.mask;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.function.FunctionCodec;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.Rotation;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.RotationTuple;
/*     */ import com.hypixel.hytale.server.core.util.FillerBlockUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class BlockEntry
/*     */   extends Record
/*     */ {
/*     */   private final String blockTypeKey;
/*     */   private final int rotation;
/*     */   private final int filler;
/*     */   
/*     */   public final String toString() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/server/core/prefab/selection/mask/BlockPattern$BlockEntry;)Ljava/lang/String;
/*     */     //   6: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #188	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lcom/hypixel/hytale/server/core/prefab/selection/mask/BlockPattern$BlockEntry;
/*     */   }
/*     */   
/*     */   public final int hashCode() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/server/core/prefab/selection/mask/BlockPattern$BlockEntry;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #188	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lcom/hypixel/hytale/server/core/prefab/selection/mask/BlockPattern$BlockEntry;
/*     */   }
/*     */   
/*     */   public final boolean equals(Object o) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/server/core/prefab/selection/mask/BlockPattern$BlockEntry;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #188	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lcom/hypixel/hytale/server/core/prefab/selection/mask/BlockPattern$BlockEntry;
/*     */     //   0	8	1	o	Ljava/lang/Object;
/*     */   }
/*     */   
/*     */   public BlockEntry(String blockTypeKey, int rotation, int filler) {
/* 188 */     this.blockTypeKey = blockTypeKey; this.rotation = rotation; this.filler = filler; } public String blockTypeKey() { return this.blockTypeKey; } public int rotation() { return this.rotation; } public int filler() { return this.filler; }
/*     */   
/*     */   @Deprecated(forRemoval = true)
/* 191 */   public static Codec<BlockEntry> CODEC = (Codec<BlockEntry>)new FunctionCodec((Codec)Codec.STRING, BlockEntry::decode, BlockEntry::encode);
/*     */   
/*     */   @Deprecated(forRemoval = true)
/*     */   private String encode() {
/* 195 */     if (this.filler == 0 && this.rotation == 0) return this.blockTypeKey; 
/* 196 */     StringBuilder out = new StringBuilder(this.blockTypeKey);
/* 197 */     RotationTuple rot = RotationTuple.get(this.rotation);
/* 198 */     if (rot.yaw() != Rotation.None) {
/* 199 */       out.append("|Yaw=").append(rot.yaw().getDegrees());
/*     */     }
/* 201 */     if (rot.pitch() != Rotation.None) {
/* 202 */       out.append("|Pitch=").append(rot.pitch().getDegrees());
/*     */     }
/* 204 */     if (rot.roll() != Rotation.None) {
/* 205 */       out.append("|Roll=").append(rot.roll().getDegrees());
/*     */     }
/* 207 */     if (this.filler != 0) {
/* 208 */       int fillerX = FillerBlockUtil.unpackX(this.filler);
/* 209 */       int fillerY = FillerBlockUtil.unpackY(this.filler);
/* 210 */       int fillerZ = FillerBlockUtil.unpackZ(this.filler);
/* 211 */       out.append("|Filler=").append(fillerX).append(",").append(fillerY).append(",").append(fillerZ);
/*     */     } 
/* 213 */     return out.toString();
/*     */   }
/*     */   
/*     */   @Deprecated(forRemoval = true)
/*     */   public static BlockEntry decode(String key) {
/* 218 */     int filler = 0;
/* 219 */     if (key.contains("|Filler=")) {
/* 220 */       int start = key.indexOf("|Filler=") + "|Filler=".length();
/* 221 */       int firstComma = key.indexOf(',', start);
/* 222 */       if (firstComma == -1) throw new IllegalArgumentException("Invalid filler metadata! Missing comma"); 
/* 223 */       int secondComma = key.indexOf(',', firstComma + 1);
/* 224 */       if (secondComma == -1) throw new IllegalArgumentException("Invalid filler metadata! Missing second comma");
/*     */       
/* 226 */       int i = key.indexOf('|', start);
/* 227 */       if (i == -1) i = key.length();
/*     */       
/* 229 */       int fillerX = Integer.parseInt(key, start, firstComma, 10);
/* 230 */       int fillerY = Integer.parseInt(key, firstComma + 1, secondComma, 10);
/* 231 */       int fillerZ = Integer.parseInt(key, secondComma + 1, i, 10);
/*     */       
/* 233 */       filler = FillerBlockUtil.pack(fillerX, fillerY, fillerZ);
/*     */     } 
/*     */     
/* 236 */     Rotation rotationYaw = Rotation.None;
/* 237 */     Rotation rotationPitch = Rotation.None;
/* 238 */     Rotation rotationRoll = Rotation.None;
/*     */     
/* 240 */     if (key.contains("|Yaw=")) {
/* 241 */       int start = key.indexOf("|Yaw=") + "|Yaw=".length();
/* 242 */       int i = key.indexOf('|', start);
/* 243 */       if (i == -1) i = key.length(); 
/* 244 */       rotationYaw = Rotation.ofDegrees(Integer.parseInt(key, start, i, 10));
/*     */     } 
/* 246 */     if (key.contains("|Pitch=")) {
/* 247 */       int start = key.indexOf("|Pitch=") + "|Pitch=".length();
/* 248 */       int i = key.indexOf('|', start);
/* 249 */       if (i == -1) i = key.length(); 
/* 250 */       rotationPitch = Rotation.ofDegrees(Integer.parseInt(key, start, i, 10));
/*     */     } 
/* 252 */     if (key.contains("|Roll=")) {
/* 253 */       int start = key.indexOf("|Roll=") + "|Roll=".length();
/* 254 */       int i = key.indexOf('|', start);
/* 255 */       if (i == -1) i = key.length(); 
/* 256 */       rotationRoll = Rotation.ofDegrees(Integer.parseInt(key, start, i, 10));
/*     */     } 
/*     */ 
/*     */     
/* 260 */     int end = key.indexOf('|');
/* 261 */     if (end == -1) end = key.length(); 
/* 262 */     String name = key.substring(0, end);
/* 263 */     return new BlockEntry(name, RotationTuple.of(rotationYaw, rotationPitch, rotationRoll).index(), filler);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\prefab\selection\mask\BlockPattern$BlockEntry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */