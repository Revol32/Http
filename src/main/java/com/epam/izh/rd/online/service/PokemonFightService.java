package com.epam.izh.rd.online.service;

import com.epam.izh.rd.online.entity.Pokemon;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public class PokemonFightService implements PokemonFightingClubService {

    PokemonAPIService pokemonAPIService = new PokemonAPIService();
    String p1id = "", p2id = "";

    @Override
    public Pokemon doBattle(Pokemon p1, Pokemon p2) {
        if (p1.getPokemonName().equals(p2.getPokemonName())){
            p1id = "1";
            p2id = "2";
        }
        System.out.println("Начинается бой между покемонами " + p1.getPokemonName() +p1id+ " и " + p2.getPokemonName()+p2id);
        if (p1.getPokemonId() > p2.getPokemonId()) {
            System.out.println("Первый ход за " + p1.getPokemonName());
            while (true) {
                doDamage(p1, p2);
                if (p2.getHp() <= 0) {
                    System.out.println("Побеждает покемон " + p1.getPokemonName()+p1id);
                    return p1;
                } else {
                    doDamage(p2, p1);
                    if (p1.getHp() <= 0) {
                        System.out.println("Побеждает покемон " + p2.getPokemonName()+p2id);
                        return p2;
                    }
                }
            }
        } else {
            System.out.println("Первый ход за " + p2.getPokemonName()+p2id);
            while (true) {
                doDamage(p2, p1);
                if (p1.getHp() <= 0) {
                    System.out.println("Побеждает покемон " + p2.getPokemonName()+p2id);
                    return p2;
                } else {
                    doDamage(p1, p2);
                    if (p2.getHp() <= 0) {
                        System.out.println("Побеждает покемон " + p1.getPokemonName()+p1id);
                        return p1;
                    }
                }
            }
        }
    }

    @Override
    public void showWinner(Pokemon winner) {
        File file = new File("src/" + winner.getPokemonName() + ".png");
        byte[] imageFile = pokemonAPIService.getPokemonImage(winner.getPokemonName());
        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(new ByteArrayInputStream(imageFile));
            ImageIO.write(bufferedImage, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void doDamage(Pokemon from, Pokemon to) {
        short damage = (short) (from.getAttack() - from.getAttack() * to.getDefense() / 100);
        System.out.println("Покемон " + from.getPokemonName() + " наносит " + damage + " урона.");
        to.setHp((short) (to.getHp() - damage));
        System.out.println("У покемона " + to.getPokemonName() + " осталось " + to.getHp() + " здоровья.");
    }
}
