/*    */ package com.hypixel.hytale.server.core.ui;
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
/*    */ public class DropdownEntryInfo
/*    */ {
/*    */   public static final BuilderCodec<DropdownEntryInfo> CODEC;
/*    */   private LocalizableString label;
/*    */   private String value;
/*    */   
/*    */   static {
/* 19 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(DropdownEntryInfo.class, DropdownEntryInfo::new).addField(new KeyedCodec("Label", LocalizableString.CODEC), (p, t) -> p.label = t, p -> p.label)).addField(new KeyedCodec("Value", (Codec)Codec.STRING), (p, t) -> p.value = t, p -> p.value)).build();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public DropdownEntryInfo(LocalizableString label, String value) {
/* 25 */     this.label = label;
/* 26 */     this.value = value;
/*    */   }
/*    */   
/*    */   private DropdownEntryInfo() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\ui\DropdownEntryInfo.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */