/*    */ package com.hypixel.hytale.server.core.modules.i18n.parser;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TranslationParseException
/*    */   extends Exception
/*    */ {
/*    */   TranslationParseException(String message, int lineNumber, String lineContent) {
/* 12 */     super(message + " (at line " + message + "): " + lineNumber);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\i18n\parser\LangFileParser$TranslationParseException.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */