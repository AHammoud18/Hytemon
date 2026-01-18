/*    */ package org.bson.codecs;
/*    */ 
/*    */ import java.util.concurrent.atomic.AtomicBoolean;
/*    */ import org.bson.BsonReader;
/*    */ import org.bson.BsonWriter;
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
/*    */ public class AtomicBooleanCodec
/*    */   implements Codec<AtomicBoolean>
/*    */ {
/*    */   public void encode(BsonWriter writer, AtomicBoolean value, EncoderContext encoderContext) {
/* 33 */     writer.writeBoolean(value.get());
/*    */   }
/*    */ 
/*    */   
/*    */   public AtomicBoolean decode(BsonReader reader, DecoderContext decoderContext) {
/* 38 */     return new AtomicBoolean(reader.readBoolean());
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<AtomicBoolean> getEncoderClass() {
/* 43 */     return AtomicBoolean.class;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\AtomicBooleanCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */