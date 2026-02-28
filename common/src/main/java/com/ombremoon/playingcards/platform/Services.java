package com.ombremoon.playingcards.platform;

import com.ombremoon.playingcards.main.PCReference;
import com.ombremoon.playingcards.platform.services.IDataHelper;
import com.ombremoon.playingcards.platform.services.IEntityDataHelper;
import com.ombremoon.playingcards.platform.services.INetworkHelper;
import com.ombremoon.playingcards.platform.services.IRegistryHelper;

import java.util.ServiceLoader;

public class Services {

    public static final IRegistryHelper REGISTRY = load(IRegistryHelper.class);
    public static final IDataHelper DATA = load(IDataHelper.class);
    public static final INetworkHelper NETWORK = load(INetworkHelper.class);
    public static final IEntityDataHelper ENTITY_DATA = load(IEntityDataHelper.class);

    public static <T> T load(Class<T> clazz) {
        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        PCReference.LOG.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}
