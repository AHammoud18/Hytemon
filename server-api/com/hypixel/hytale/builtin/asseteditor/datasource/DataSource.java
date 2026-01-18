package com.hypixel.hytale.builtin.asseteditor.datasource;

import com.hypixel.hytale.builtin.asseteditor.AssetTree;
import com.hypixel.hytale.builtin.asseteditor.EditorClient;
import com.hypixel.hytale.builtin.asseteditor.assettypehandler.AssetTypeHandler;
import com.hypixel.hytale.common.plugin.PluginManifest;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Collection;

public interface DataSource {
  void start();
  
  void shutdown();
  
  AssetTree getAssetTree();
  
  AssetTree loadAssetTree(Collection<AssetTypeHandler> paramCollection);
  
  boolean doesDirectoryExist(Path paramPath);
  
  boolean createDirectory(Path paramPath, EditorClient paramEditorClient);
  
  boolean deleteDirectory(Path paramPath);
  
  boolean moveDirectory(Path paramPath1, Path paramPath2);
  
  boolean doesAssetExist(Path paramPath);
  
  byte[] getAssetBytes(Path paramPath);
  
  boolean updateAsset(Path paramPath, byte[] paramArrayOfbyte, EditorClient paramEditorClient);
  
  boolean createAsset(Path paramPath, byte[] paramArrayOfbyte, EditorClient paramEditorClient);
  
  boolean deleteAsset(Path paramPath, EditorClient paramEditorClient);
  
  boolean moveAsset(Path paramPath1, Path paramPath2, EditorClient paramEditorClient);
  
  boolean shouldReloadAssetFromDisk(Path paramPath);
  
  Instant getLastModificationTimestamp(Path paramPath);
  
  default void updateRuntimeAssets() {}
  
  Path getFullPathToAssetData(Path paramPath);
  
  boolean isImmutable();
  
  Path getRootPath();
  
  PluginManifest getManifest();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\asseteditor\datasource\DataSource.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */