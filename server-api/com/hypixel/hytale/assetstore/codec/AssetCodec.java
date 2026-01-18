package com.hypixel.hytale.assetstore.codec;

import com.hypixel.hytale.assetstore.AssetExtraInfo;
import com.hypixel.hytale.codec.InheritCodec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.util.RawJsonReader;
import com.hypixel.hytale.codec.validation.ValidatableCodec;
import java.io.IOException;
import javax.annotation.Nullable;

public interface AssetCodec<K, T extends com.hypixel.hytale.assetstore.JsonAsset<K>> extends InheritCodec<T>, ValidatableCodec<T> {
  KeyedCodec<K> getKeyCodec();
  
  KeyedCodec<K> getParentCodec();
  
  @Nullable
  AssetExtraInfo.Data getData(T paramT);
  
  T decodeJsonAsset(RawJsonReader paramRawJsonReader, AssetExtraInfo<K> paramAssetExtraInfo) throws IOException;
  
  T decodeAndInheritJsonAsset(RawJsonReader paramRawJsonReader, T paramT, AssetExtraInfo<K> paramAssetExtraInfo) throws IOException;
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\assetstore\codec\AssetCodec.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */