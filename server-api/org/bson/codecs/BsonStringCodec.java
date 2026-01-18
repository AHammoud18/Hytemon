/*    */ package org.bson.codecs;
/*    */ 
/*    */ import org.bson.BsonReader;
/*    */ import org.bson.BsonString;
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
/*    */ public class BsonStringCodec
/*    */   implements Codec<BsonString>
/*    */ {
/*    */   public BsonString decode(BsonReader reader, DecoderContext decoderContext) {
/* 31 */     return new BsonString(reader.readString());
/*    */   }
/*    */ 
/*    */   
/*    */   public void encode(BsonWriter writer, BsonString value, EncoderContext encoderContext) {
/* 36 */     writer.writeString(value.getValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<BsonString> getEncoderClass() {
/* 41 */     return BsonString.class;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\BsonStringCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */