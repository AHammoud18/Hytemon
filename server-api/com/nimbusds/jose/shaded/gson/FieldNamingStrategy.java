/*    */ package com.nimbusds.jose.shaded.gson;
/*    */ 
/*    */ import java.lang.reflect.Field;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
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
/*    */ public interface FieldNamingStrategy
/*    */ {
/*    */   String translateName(Field paramField);
/*    */   
/*    */   default List<String> alternateNames(Field f) {
/* 53 */     return Collections.emptyList();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\shaded\gson\FieldNamingStrategy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */