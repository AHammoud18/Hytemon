/*     */ package com.hypixel.hytale.server.core.asset.type.buildertool.config;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
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
/*     */ public final class ArgData
/*     */   extends Record
/*     */ {
/*     */   private final Map<String, Object> tool;
/*     */   private final BrushData.Values brush;
/*     */   
/*     */   public ArgData(Map<String, Object> tool, BrushData.Values brush) {
/* 232 */     this.tool = tool; this.brush = brush;
/*     */   } public final int hashCode() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/server/core/asset/type/buildertool/config/BuilderTool$ArgData;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #232	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lcom/hypixel/hytale/server/core/asset/type/buildertool/config/BuilderTool$ArgData;
/*     */   } @Nullable
/*     */   public Map<String, Object> tool() {
/* 237 */     return this.tool;
/*     */   } public final boolean equals(Object o) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/server/core/asset/type/buildertool/config/BuilderTool$ArgData;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #232	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lcom/hypixel/hytale/server/core/asset/type/buildertool/config/BuilderTool$ArgData;
/*     */     //   0	8	1	o	Ljava/lang/Object;
/*     */   } @Nonnull
/*     */   public static ArgData setToolArg(@Nonnull ArgData argData, String argId, Object value) {
/* 242 */     Map<String, Object> tool = argData.tool();
/* 243 */     if (tool == null) return argData;
/*     */     
/* 245 */     Object2ObjectOpenHashMap<String, Object> newToolArgs = new Object2ObjectOpenHashMap(tool);
/* 246 */     newToolArgs.put(argId, value);
/*     */     
/* 248 */     return new ArgData((Map<String, Object>)newToolArgs, argData.brush());
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ArgData removeToolArg(@Nonnull ArgData argData, String argId) {
/* 253 */     Map<String, Object> tool = argData.tool();
/* 254 */     if (tool == null) return argData;
/*     */     
/* 256 */     Object2ObjectOpenHashMap<String, Object> newToolArgs = new Object2ObjectOpenHashMap(tool);
/* 257 */     newToolArgs.remove(argId);
/*     */     
/* 259 */     return new ArgData((Map<String, Object>)newToolArgs, argData.brush());
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BrushData.Values brush() {
/* 265 */     return this.brush;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 271 */     return "ArgData{tool=" + String.valueOf(this.tool) + ", brush=" + String.valueOf(this.brush) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\buildertool\config\BuilderTool$ArgData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */