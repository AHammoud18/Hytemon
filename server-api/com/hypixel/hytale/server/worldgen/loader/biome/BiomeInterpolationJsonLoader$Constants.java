package com.hypixel.hytale.server.worldgen.loader.biome;

public interface Constants {
  public static final String KEY_DEFAULT_RADIUS = "DefaultRadius";
  
  public static final String KEY_RADIUS = "Radius";
  
  public static final String KEY_BIOMES = "Biomes";
  
  public static final String KEY_MASK = "Mask";
  
  public static final String SEED_OFFSET_MASK = "InterpolationMask";
  
  public static final String ERROR_MISSING_PROPERTY = "Missing property %s";
  
  public static final String ERROR_INVALID_BIOME_LIST = "Invalid json-type for Biomes property. Must be an array!";
  
  public static final String ERROR_INVALID_BIOME_ENTRY = "Invalid json-type for biome entry. Must be an object!";
  
  public static final String ERROR_DUPLICATE_BIOME = "Duplicate biome detected in interpolation rules";
  
  public static final String ERROR_BIOME_RADIUS = "Biome interpolation radius %s is outside the range 0-%s";
  
  public static final String ERROR_DEFAULT_RADIUS = "Default biome interpolation radius %s lies outside the range 0-5";
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\biome\BiomeInterpolationJsonLoader$Constants.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */