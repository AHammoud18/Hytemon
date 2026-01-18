/*    */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.data;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StringTag
/*    */   implements CollectorTag
/*    */ {
/*    */   private final String tag;
/*    */   
/*    */   private StringTag(String tag) {
/* 15 */     this.tag = tag;
/*    */   }
/*    */   
/*    */   public String getTag() {
/* 19 */     return this.tag;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(@Nullable Object o) {
/* 24 */     if (this == o) return true; 
/* 25 */     if (o == null || getClass() != o.getClass()) return false;
/*    */     
/* 27 */     StringTag stringTag = (StringTag)o;
/* 28 */     return (this.tag != null) ? this.tag.equals(stringTag.tag) : ((stringTag.tag == null));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 33 */     return (this.tag != null) ? this.tag.hashCode() : 0;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 39 */     return "StringTag{tag='" + this.tag + "'}";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public static StringTag of(String tag) {
/* 46 */     return new StringTag(tag);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\data\StringTag.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */