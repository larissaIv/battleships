package com.softuni.battleships.DTO;

import javax.validation.constraints.Positive;

public class StartBattleDTO {

    @Positive
    private int attackerId;

    @Positive
    private int defenderID;

    public int getAttackerId() {
        return attackerId;
    }

    public void setAttackerId(int attackerId) {
        this.attackerId = attackerId;
    }

    public int getDefenderID() {
        return defenderID;
    }

    public void setDefenderID(int defenderID) {
        this.defenderID = defenderID;
    }
}
