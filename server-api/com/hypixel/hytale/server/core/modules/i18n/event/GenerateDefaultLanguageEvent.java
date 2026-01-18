/*    */ package com.hypixel.hytale.server.core.modules.i18n.event;
/*    */ 
/*    */ import com.hypixel.hytale.event.IEvent;
/*    */ import com.hypixel.hytale.server.core.modules.i18n.generator.TranslationMap;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ import javax.annotation.Nonnull;
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
/*    */ public class GenerateDefaultLanguageEvent
/*    */   implements IEvent<Void>
/*    */ {
/*    */   private final ConcurrentHashMap<String, TranslationMap> translationFiles;
/*    */   
/*    */   public GenerateDefaultLanguageEvent(ConcurrentHashMap<String, TranslationMap> translationFiles) {
/* 26 */     this.translationFiles = translationFiles;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void putTranslationFile(@Nonnull String filename, @Nonnull TranslationMap translations) {
/* 34 */     this.translationFiles.put(filename, translations);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\i18n\event\GenerateDefaultLanguageEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */