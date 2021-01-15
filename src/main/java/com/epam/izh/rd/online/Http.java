package com.epam.izh.rd.online;

import com.epam.izh.rd.online.entity.Pokemon;
import com.epam.izh.rd.online.service.PokemonAPIService;
import com.epam.izh.rd.online.service.PokemonFightService;

public class Http {
    public static void main(String[] args) {
        PokemonAPIService pokemonAPIService = new PokemonAPIService();
        PokemonFightService pokemonFightService = new PokemonFightService();
        Pokemon p1 = pokemonAPIService.fetchByName("slowpoke");
        Pokemon p2 = pokemonAPIService.fetchByName("pikachu");
        Pokemon winner = pokemonFightService.doBattle(p1, p2);
        pokemonFightService.showWinner(winner);
    }
}
