package com.hypixel.hytale.server.core.asset.packet;

import com.hypixel.hytale.assetstore.AssetMap;
import com.hypixel.hytale.assetstore.AssetUpdateQuery;
import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
import com.hypixel.hytale.protocol.Packet;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class AssetPacketGenerator<K, T extends JsonAssetWithMap<K, M>, M extends AssetMap<K, T>> {
  public abstract Packet generateInitPacket(M paramM, Map<K, T> paramMap);
  
  public abstract Packet generateUpdatePacket(M paramM, Map<K, T> paramMap, @Nonnull AssetUpdateQuery paramAssetUpdateQuery);
  
  @Nullable
  public abstract Packet generateRemovePacket(M paramM, Set<K> paramSet, @Nonnull AssetUpdateQuery paramAssetUpdateQuery);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\packet\AssetPacketGenerator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */