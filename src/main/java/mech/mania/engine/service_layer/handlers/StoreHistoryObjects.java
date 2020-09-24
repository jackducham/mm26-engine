package mech.mania.engine.service_layer.handlers;

import mech.mania.engine.domain.game.characters.Player;
import mech.mania.engine.domain.messages.Event;
import mech.mania.engine.domain.model.CharacterProtos;
import mech.mania.engine.service_layer.UnitOfWorkAbstract;

import java.util.Map;

public class StoreHistoryObjects extends EventHandler {

    public StoreHistoryObjects(UnitOfWorkAbstract uow) {
        super(uow);
    }

    @Override
    public void handle(Event event) {
        Map<String, Player> playersMap = uow.getGameState().getAllPlayers();

        CharacterProtos.PlayerStatsBundle.Builder playerStatsBundleObjBuilder = CharacterProtos.PlayerStatsBundle.newBuilder();
        for (Map.Entry<String, Player> entry : playersMap.entrySet()) {
            playerStatsBundleObjBuilder.putStats(entry.getKey(), entry.getValue().getPlayerStats());
        }
        CharacterProtos.PlayerStatsBundle playerStatsBundleObj = playerStatsBundleObjBuilder.build();

        uow.storeCurrentTurn(uow.getTurn());
        uow.storePlayerStatsBundle(uow.getTurn(), playerStatsBundleObj);
        uow.storeGameState(uow.getTurn(), uow.getGameState());
        uow.storeGameChange(uow.getTurn(), uow.getGameChange());
    }

}
