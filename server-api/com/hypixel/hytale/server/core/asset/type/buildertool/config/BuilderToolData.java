/*    */ package com.hypixel.hytale.server.core.asset.type.buildertool.config;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.protocol.ItemBuilderToolData;
/*    */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*    */ import java.util.Arrays;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class BuilderToolData
/*    */   implements NetworkSerializable<ItemBuilderToolData>
/*    */ {
/* 17 */   public static final BuilderToolData DEFAULT = new BuilderToolData();
/*    */ 
/*    */   
/*    */   public static final BuilderCodec<BuilderToolData> CODEC;
/*    */ 
/*    */   
/*    */   protected String[] ui;
/*    */ 
/*    */   
/*    */   protected BuilderTool[] tools;
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 31 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(BuilderToolData.class, BuilderToolData::new).addField(new KeyedCodec("UI", (Codec)new ArrayCodec((Codec)Codec.STRING, x$0 -> new String[x$0])), (builderToolData, o) -> builderToolData.ui = o, builderToolData -> builderToolData.ui)).append(new KeyedCodec("Tools", (Codec)new ArrayCodec((Codec)BuilderTool.CODEC, x$0 -> new BuilderTool[x$0])), (builderToolData, o) -> builderToolData.tools = o, builderToolData -> builderToolData.tools).addValidator(Validators.nonEmptyArray()).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public BuilderToolData() {}
/*    */ 
/*    */   
/*    */   public BuilderToolData(String[] ui, BuilderTool[] tools) {
/* 40 */     this.ui = ui;
/* 41 */     this.tools = tools;
/*    */   }
/*    */   
/*    */   public String[] getUi() {
/* 45 */     return this.ui;
/*    */   }
/*    */   
/*    */   public BuilderTool[] getTools() {
/* 49 */     return this.tools;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public ItemBuilderToolData toPacket() {
/* 55 */     ItemBuilderToolData packet = new ItemBuilderToolData();
/* 56 */     packet.ui = this.ui;
/* 57 */     packet.tools = new com.hypixel.hytale.protocol.packets.buildertools.BuilderToolState[this.tools.length];
/* 58 */     for (int i = 0; i < this.tools.length; i++) {
/* 59 */       packet.tools[i] = this.tools[i].toPacket();
/*    */     }
/*    */     
/* 62 */     return packet;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 69 */     return "BuilderToolData{ui=" + Arrays.toString((Object[])this.ui) + ", tools=" + 
/* 70 */       Arrays.toString((Object[])this.tools) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\buildertool\config\BuilderToolData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */