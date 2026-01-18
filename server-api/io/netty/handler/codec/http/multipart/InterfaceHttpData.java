/*    */ package io.netty.handler.codec.http.multipart;
/*    */ 
/*    */ import io.netty.util.ReferenceCounted;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface InterfaceHttpData
/*    */   extends Comparable<InterfaceHttpData>, ReferenceCounted
/*    */ {
/*    */   String getName();
/*    */   
/*    */   HttpDataType getHttpDataType();
/*    */   
/*    */   InterfaceHttpData retain();
/*    */   
/*    */   InterfaceHttpData retain(int paramInt);
/*    */   
/*    */   InterfaceHttpData touch();
/*    */   
/*    */   InterfaceHttpData touch(Object paramObject);
/*    */   
/*    */   public enum HttpDataType
/*    */   {
/* 25 */     Attribute, FileUpload, InternalAttribute;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\multipart\InterfaceHttpData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */