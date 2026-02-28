package com.ombremoon.playingcards.platform;

import com.ombremoon.playingcards.network.FabricNetworking;
import com.ombremoon.playingcards.platform.services.INetworkHelper;

public class FabricNetworkHelper implements INetworkHelper {

    @Override
    public void sendCardInteract(String command) {
        FabricNetworking.sendCardInteract(command);
    }
}
