/*    */ package com.nimbusds.jose.util;
/*    */ 
/*    */ import com.nimbusds.jose.shaded.jcip.Immutable;
/*    */ import java.util.Objects;
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
/*    */ @Immutable
/*    */ public class Resource
/*    */ {
/*    */   private final String content;
/*    */   private final String contentType;
/*    */   
/*    */   public Resource(String content, String contentType) {
/* 55 */     this.content = Objects.<String>requireNonNull(content);
/* 56 */     this.contentType = contentType;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getContent() {
/* 67 */     return this.content;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getContentType() {
/* 78 */     return this.contentType;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jos\\util\Resource.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */