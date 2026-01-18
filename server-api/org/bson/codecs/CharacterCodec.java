/*    */ package org.bson.codecs;
/*    */ 
/*    */ import org.bson.BsonInvalidOperationException;
/*    */ import org.bson.BsonReader;
/*    */ import org.bson.BsonWriter;
/*    */ import org.bson.assertions.Assertions;
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
/*    */ public class CharacterCodec
/*    */   implements Codec<Character>
/*    */ {
/*    */   public void encode(BsonWriter writer, Character value, EncoderContext encoderContext) {
/* 34 */     Assertions.notNull("value", value);
/*    */     
/* 36 */     writer.writeString(value.toString());
/*    */   }
/*    */ 
/*    */   
/*    */   public Character decode(BsonReader reader, DecoderContext decoderContext) {
/* 41 */     String string = reader.readString();
/* 42 */     if (string.length() != 1) {
/* 43 */       throw new BsonInvalidOperationException(String.format("Attempting to decode the string '%s' to a character, but its length is not equal to one", new Object[] { string }));
/*    */     }
/*    */ 
/*    */     
/* 47 */     return Character.valueOf(string.charAt(0));
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<Character> getEncoderClass() {
/* 52 */     return Character.class;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\CharacterCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */