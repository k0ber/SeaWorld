package com.sibext.java.junior;

public class WorldHolder {

    public static class SingletonHolder {
        public static final World WORLD_INSTANCE = new World();
    }
        
    public static World getInstance() {
        return SingletonHolder.WORLD_INSTANCE;
    }
}
