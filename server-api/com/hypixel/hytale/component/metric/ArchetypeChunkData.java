/*    */ package com.hypixel.hytale.component.metric;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
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
/*    */ public class ArchetypeChunkData
/*    */ {
/*    */   @Nonnull
/*    */   public static final Codec<ArchetypeChunkData> CODEC;
/*    */   
/*    */   static {
/* 29 */     CODEC = (Codec<ArchetypeChunkData>)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ArchetypeChunkData.class, ArchetypeChunkData::new).append(new KeyedCodec("ComponentTypes", (Codec)Codec.STRING_ARRAY), (data, o) -> data.componentTypes = o, data -> data.componentTypes).add()).append(new KeyedCodec("EntityCount", (Codec)Codec.INTEGER), (data, o) -> data.entityCount = o.intValue(), data -> Integer.valueOf(data.entityCount)).add()).build();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/* 34 */   private String[] componentTypes = new String[0];
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
/*    */   private int entityCount;
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
/*    */   public ArchetypeChunkData(@Nonnull String[] componentTypes, int entityCount) {
/* 57 */     this.componentTypes = componentTypes;
/* 58 */     this.entityCount = entityCount;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String[] getComponentTypes() {
/* 66 */     return this.componentTypes;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getEntityCount() {
/* 73 */     return this.entityCount;
/*    */   }
/*    */   
/*    */   public ArchetypeChunkData() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\metric\ArchetypeChunkData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */