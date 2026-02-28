package com.ombremoon.playingcards.platform;

import com.ombremoon.playingcards.network.ModNetworking;
import com.ombremoon.playingcards.platform.services.INetworkHelper;

public class NeoForgeNetworkHelper implements INetworkHelper {

    @Override
    public void sendCardInteract(String command) {
        ModNetworking.cardInteract(command);
    }
}
