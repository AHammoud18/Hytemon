package com.hypixel.hytale.server.worldgen.loader.zone;

public interface Constants {
  public static final String KEY_ZONE = "Zone";
  
  public static final String KEY_COLOR = "Color";
  
  public static final String KEY_PARENT = "Parent";
  
  public static final String KEY_RADIUS = "Radius";
  
  public static final String KEY_PADDING = "Padding";
  
  public static final int DEFAULT_RADIUS = 1;
  
  public static final int DEFAULT_PADDING = 0;
  
  public static final String ERROR_ENTRIES_TYPE = "Unexpected type for 'UniqueZones' field, expected array";
  
  public static final String ERROR_ENTRY_TYPE = "Unexpected type for unique zone entry: #";
  
  public static final String ERROR_PARENT_TYPE = "Unexpected type for 'Parent' field in unique zone entry: #";
  
  public static final String ERROR_MISSING_ZONE = "Missing 'Zone' field in unique zone entry: #";
  
  public static final String ERROR_MISSING_COLOR = "Missing 'Color' field in unique zone entry: #";
  
  public static final String ERROR_MISSING_PARENT = "Missing 'Parent' field in unique zone entry: #";
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\zone\UniqueZoneEntryJsonLoader$Constants.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */