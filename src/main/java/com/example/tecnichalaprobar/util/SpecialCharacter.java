package com.example.tecnichalaprobar.util;

public enum SpecialCharacter {
    SIGNOPOSITIVO("+", "-10-"),
    SIGNONUMERAL("#", "-11-"),
    SIGNOAMP("&","-12-");

    private String character;
    private String characterReemplazo;

    SpecialCharacter(String character, String characterReemplazo) {
        this.character = character;
        this.characterReemplazo = characterReemplazo;
    }

    public String getCharacter() {
        return character;
    }

    public String getCharacterReemplazo() {
        return characterReemplazo;
    }

    public static String changeString(String texto) {
        if(texto.contains(SpecialCharacter.SIGNOPOSITIVO.getCharacter())) {
            texto = texto.replace(SpecialCharacter.SIGNOPOSITIVO.getCharacter(),
                    SpecialCharacter.SIGNOPOSITIVO.getCharacterReemplazo());
        } else if (texto.contains(SpecialCharacter.SIGNONUMERAL.getCharacter())) {
            texto = texto.replace(SpecialCharacter.SIGNONUMERAL.getCharacter(),
                    SpecialCharacter.SIGNONUMERAL.getCharacterReemplazo());
        } else if (texto.contains(SpecialCharacter.SIGNOAMP.getCharacter())) {
            texto = texto.replace(SpecialCharacter.SIGNOAMP.getCharacter(),
                    SpecialCharacter.SIGNOAMP.getCharacterReemplazo());
        }
        return texto;
    }

    public static String deschangeString(String texto) {
        if(texto.contains(SpecialCharacter.SIGNOPOSITIVO.getCharacterReemplazo())) {
            texto = texto.replace(SpecialCharacter.SIGNOPOSITIVO.getCharacterReemplazo(),
                    SpecialCharacter.SIGNOPOSITIVO.getCharacter());
        } else if (texto.contains(SpecialCharacter.SIGNONUMERAL.getCharacterReemplazo())) {
            texto = texto.replace(SpecialCharacter.SIGNONUMERAL.getCharacterReemplazo(),
                    SpecialCharacter.SIGNONUMERAL.getCharacter());
        } else if (texto.contains(SpecialCharacter.SIGNOAMP.getCharacterReemplazo())) {
            texto = texto.replace(SpecialCharacter.SIGNOAMP.getCharacterReemplazo(),
                    SpecialCharacter.SIGNOAMP.getCharacter());
        }
        return texto;
    }
}
