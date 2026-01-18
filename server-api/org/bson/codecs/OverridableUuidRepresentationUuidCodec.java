/*    */ package org.bson.codecs;
/*    */ 
/*    */ import java.util.UUID;
/*    */ import org.bson.UuidRepresentation;
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
/*    */ public class OverridableUuidRepresentationUuidCodec
/*    */   extends UuidCodec
/*    */   implements OverridableUuidRepresentationCodec<UUID>
/*    */ {
/*    */   public OverridableUuidRepresentationUuidCodec() {}
/*    */   
/*    */   public OverridableUuidRepresentationUuidCodec(UuidRepresentation uuidRepresentation) {
/* 43 */     super(uuidRepresentation);
/*    */   }
/*    */ 
/*    */   
/*    */   public Codec<UUID> withUuidRepresentation(UuidRepresentation uuidRepresentation) {
/* 48 */     return new OverridableUuidRepresentationUuidCodec(uuidRepresentation);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\OverridableUuidRepresentationUuidCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */