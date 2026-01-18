/*    */ package com.hypixel.hytale.server.core.entity.entities.player.pages.choices;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import java.util.function.Supplier;
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
/*    */ public class ChoicePageEventData
/*    */ {
/*    */   static final String ELEMENT_INDEX = "Index";
/*    */   public static final BuilderCodec<ChoicePageEventData> CODEC;
/*    */   private String indexStr;
/*    */   private int index;
/*    */   
/*    */   static {
/* 79 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(ChoicePageEventData.class, ChoicePageEventData::new).append(new KeyedCodec("Index", (Codec)Codec.STRING), (choicePageEventData, s) -> { choicePageEventData.indexStr = s; choicePageEventData.index = Integer.parseInt(s); }choicePageEventData -> choicePageEventData.indexStr).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getIndex() {
/* 85 */     return this.index;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\entities\player\pages\choices\ChoiceBasePage$ChoicePageEventData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */