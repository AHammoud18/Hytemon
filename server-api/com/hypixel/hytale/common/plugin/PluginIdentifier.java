/*    */ package com.hypixel.hytale.common.plugin;
/*    */ 
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
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
/*    */ public class PluginIdentifier
/*    */ {
/*    */   @Nonnull
/*    */   private final String group;
/*    */   @Nonnull
/*    */   private final String name;
/*    */   
/*    */   public PluginIdentifier(@Nonnull String group, @Nonnull String name) {
/* 35 */     this.group = group;
/* 36 */     this.name = name;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PluginIdentifier(@Nonnull PluginManifest manifest) {
/* 45 */     this(manifest.getGroup(), manifest.getName());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String getGroup() {
/* 53 */     return this.group;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String getName() {
/* 61 */     return this.name;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 66 */     int result = this.group.hashCode();
/* 67 */     result = 31 * result + this.name.hashCode();
/* 68 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(@Nullable Object o) {
/* 73 */     if (this == o) return true; 
/* 74 */     if (o == null || getClass() != o.getClass()) return false;
/*    */     
/* 76 */     PluginIdentifier that = (PluginIdentifier)o;
/*    */     
/* 78 */     if (!Objects.equals(this.group, that.group)) return false; 
/* 79 */     return Objects.equals(this.name, that.name);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 85 */     return this.group + ":" + this.group;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public static PluginIdentifier fromString(@Nonnull String str) {
/* 96 */     String[] split = str.split(":");
/* 97 */     if (split.length != 2) throw new IllegalArgumentException("String does not match <group>:<name>"); 
/* 98 */     return new PluginIdentifier(split[0], split[1]);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\common\plugin\PluginIdentifier.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */