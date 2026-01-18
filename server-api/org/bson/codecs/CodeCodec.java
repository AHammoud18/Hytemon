/*    */ package org.bson.codecs;
/*    */ 
/*    */ import org.bson.BsonReader;
/*    */ import org.bson.BsonWriter;
/*    */ import org.bson.types.Code;
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
/*    */ public class CodeCodec
/*    */   implements Codec<Code>
/*    */ {
/*    */   public void encode(BsonWriter writer, Code value, EncoderContext encoderContext) {
/* 32 */     writer.writeJavaScript(value.getCode());
/*    */   }
/*    */ 
/*    */   
/*    */   public Code decode(BsonReader bsonReader, DecoderContext decoderContext) {
/* 37 */     return new Code(bsonReader.readJavaScript());
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<Code> getEncoderClass() {
/* 42 */     return Code.class;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\CodeCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */