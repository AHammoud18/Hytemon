package com.hypixel.hytale.server.npc.asset.builder;

import java.util.List;

interface IStateMap {
  int getAndPutSensorIndex(String paramString);
  
  int getAndPutSetterIndex(String paramString);
  
  int getAndPutRequirerIndex(String paramString);
  
  int getStateIndex(String paramString);
  
  String getStateName(int paramInt);
  
  void validate(String paramString1, String paramString2, List<String> paramList);
  
  boolean isEmpty();
  
  int size();
  
  void optimise();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\StateMappingHelper$IStateMap.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */