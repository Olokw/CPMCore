package net.balacobaco.cpmcore.utils;

import com.google.gson.annotations.Expose;

public class PrisaoConfig {
    @Expose
    private int pontos;

    public int getPontos() {
        return pontos;
    }

    public PrisaoConfig(int pontos) {
        this.pontos = pontos;
    }
}
