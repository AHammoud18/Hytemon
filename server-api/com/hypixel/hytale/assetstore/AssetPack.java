/*    */ package com.hypixel.hytale.assetstore;
/*    */ 
/*    */ import com.hypixel.hytale.common.plugin.PluginManifest;
/*    */ import java.nio.file.FileSystem;
/*    */ import java.nio.file.Path;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AssetPack
/*    */ {
/*    */   @Nonnull
/*    */   private final String name;
/*    */   @Nonnull
/*    */   private final Path root;
/*    */   @Nullable
/*    */   private final FileSystem fileSystem;
/*    */   private final boolean isImmutable;
/*    */   private final PluginManifest manifest;
/*    */   private final Path packLocation;
/*    */   
/*    */   public AssetPack(Path packLocation, @Nonnull String name, @Nonnull Path root, @Nullable FileSystem fileSystem, boolean isImmutable, PluginManifest manifest) {
/* 24 */     this.name = name;
/* 25 */     this.root = root;
/* 26 */     this.fileSystem = fileSystem;
/* 27 */     this.isImmutable = isImmutable;
/* 28 */     this.manifest = manifest;
/* 29 */     this.packLocation = packLocation;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public String getName() {
/* 34 */     return this.name;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Path getRoot() {
/* 39 */     return this.root;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public FileSystem getFileSystem() {
/* 44 */     return this.fileSystem;
/*    */   }
/*    */   
/*    */   public PluginManifest getManifest() {
/* 48 */     return this.manifest;
/*    */   }
/*    */   
/*    */   public boolean isImmutable() {
/* 52 */     return this.isImmutable;
/*    */   }
/*    */   
/*    */   public Path getPackLocation() {
/* 56 */     return this.packLocation;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(@Nullable Object o) {
/* 61 */     if (this == o) return true; 
/* 62 */     if (o == null || getClass() != o.getClass()) return false;
/*    */     
/* 64 */     AssetPack assetPack = (AssetPack)o;
/*    */     
/* 66 */     if (!this.name.equals(assetPack.name)) return false; 
/* 67 */     return this.root.equals(assetPack.root);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 72 */     int result = this.name.hashCode();
/* 73 */     result = 31 * result + this.root.hashCode();
/* 74 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 80 */     return "AssetPack{name='" + this.name + "', root=" + String.valueOf(this.root) + ", fileSystem=" + String.valueOf(this.fileSystem) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\assetstore\AssetPack.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */