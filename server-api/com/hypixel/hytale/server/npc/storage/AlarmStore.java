/*    */ package com.hypixel.hytale.server.npc.storage;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.map.MapCodec;
/*    */ import com.hypixel.hytale.server.npc.util.Alarm;
/*    */ import java.util.Map;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AlarmStore
/*    */   extends ParameterStore<Alarm>
/*    */ {
/*    */   public static final BuilderCodec<AlarmStore> CODEC;
/*    */   
/*    */   static {
/* 22 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(AlarmStore.class, AlarmStore::new).append(new KeyedCodec("Parameters", (Codec)new MapCodec((Codec)Alarm.CODEC, java.util.HashMap::new, false)), (store, o) -> store.parameters = o, store -> store.parameters).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected Alarm createParameter() {
/* 31 */     return new Alarm();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\storage\AlarmStore.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */