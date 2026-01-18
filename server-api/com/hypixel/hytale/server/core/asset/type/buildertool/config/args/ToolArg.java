/*    */ package com.hypixel.hytale.server.core.asset.type.buildertool.config.args;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.lookup.CodecMapCodec;
/*    */ import com.hypixel.hytale.protocol.packets.buildertools.BuilderToolArg;
/*    */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public abstract class ToolArg<T>
/*    */   implements NetworkSerializable<BuilderToolArg> {
/* 13 */   public static final CodecMapCodec<ToolArg> CODEC = new CodecMapCodec("Type");
/*    */ 
/*    */   
/*    */   public static final BuilderCodec<ToolArg> DEFAULT_CODEC;
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 21 */     DEFAULT_CODEC = ((BuilderCodec.Builder)BuilderCodec.abstractBuilder(ToolArg.class).addField(new KeyedCodec("Required", (Codec)Codec.BOOLEAN), (shapeArg, o) -> shapeArg.required = o.booleanValue(), shapeArg -> Boolean.valueOf(shapeArg.required))).build();
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean required = true;
/*    */   
/*    */   protected T value;
/*    */   
/*    */   public T getValue() {
/* 30 */     return this.value;
/*    */   }
/*    */   
/*    */   public boolean isRequired() {
/* 34 */     return this.required;
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
/*    */   @Nonnull
/*    */   public BuilderToolArg toPacket() {
/* 47 */     BuilderToolArg packet = new BuilderToolArg();
/* 48 */     packet.required = this.required;
/* 49 */     setupPacket(packet);
/* 50 */     return packet;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 56 */     return "ToolArg{required=" + this.required + ", value=" + String.valueOf(this.value) + "}";
/*    */   }
/*    */   
/*    */   public abstract Codec<T> getCodec();
/*    */   
/*    */   @Nonnull
/*    */   public abstract T fromString(@Nonnull String paramString) throws ToolArgException;
/*    */   
/*    */   protected abstract void setupPacket(BuilderToolArg paramBuilderToolArg);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\buildertool\config\args\ToolArg.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */