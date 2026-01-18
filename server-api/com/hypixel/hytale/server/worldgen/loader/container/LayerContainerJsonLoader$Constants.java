package com.hypixel.hytale.server.worldgen.loader.container;

public interface Constants {
  public static final String KEY_DEFAULT = "Default";
  
  public static final String KEY_DYNAMIC = "Dynamic";
  
  public static final String KEY_STATIC = "Static";
  
  public static final String KEY_ENTRY_ENTRIES = "Entries";
  
  public static final String KEY_ENTRY_BLOCKS = "Blocks";
  
  public static final String KEY_ENTRY_NOISE_MASK = "NoiseMask";
  
  public static final String KEY_ENTRY_DYNAMIC_OFFSET = "Offset";
  
  public static final String KEY_ENTRY_DYNAMIC_OFFSET_NOISE = "OffsetNoise";
  
  public static final String KEY_ENTRY_STATIC_MIN = "Min";
  
  public static final String KEY_ENTRY_STATIC_MIN_NOISE = "MinNoise";
  
  public static final String KEY_ENTRY_STATIC_MAX = "Max";
  
  public static final String KEY_ENTRY_STATIC_MAX_NOISE = "MaxNoise";
  
  public static final String KEY_ENVIRONMENT = "Environment";
  
  public static final String ERROR_NO_DEFAULT = "Could not find default material. Keyword: Default";
  
  public static final String ERROR_DEFAULT_INVALID = "Default block for LayerContainer could not be found! BlockType: %s";
  
  public static final String ERROR_FAIL_DYNAMIC_LAYER = "Error while loading DynamicLayer #%s";
  
  public static final String ERROR_FAIL_STATIC_LAYER = "Error while loading StaticLayer #%s";
  
  public static final String ERROR_NO_BLOCKS = "Could not find block data for layer entry. Keyword: Blocks";
  
  public static final String ERROR_UNKOWN_STATIC = "Unknown type for static Layer";
  
  public static final String ERROR_UNKOWN_DYNAMIC = "Unknown type for dynamic Layer";
  
  public static final String ERROR_FAIL_DYNAMIC_ENTRY = "Error while loading DynamicLayerEntry #%s";
  
  public static final String ERROR_FAIL_STATIC_ENTRY = "Error while loading StaticLayerEntry #%s";
  
  public static final String ERROR_STATIC_NO_MIN = "Could not find minimum of static layer entry.";
  
  public static final String ERROR_STATIC_NO_MAX = "Could not find maximum of static layer entry.";
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\container\LayerContainerJsonLoader$Constants.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */