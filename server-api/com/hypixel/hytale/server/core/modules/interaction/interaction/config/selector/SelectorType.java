/*    */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.selector;
/*    */ 
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.lookup.CodecMapCodec;
/*    */ import com.hypixel.hytale.protocol.Selector;
/*    */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class SelectorType
/*    */   implements NetworkSerializable<Selector>
/*    */ {
/* 23 */   public static final CodecMapCodec<SelectorType> CODEC = new CodecMapCodec();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 28 */   public static final BuilderCodec<SelectorType> BASE_CODEC = BuilderCodec.abstractBuilder(SelectorType.class)
/* 29 */     .build();
/*    */   
/*    */   public abstract Selector newSelector();
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\selector\SelectorType.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */