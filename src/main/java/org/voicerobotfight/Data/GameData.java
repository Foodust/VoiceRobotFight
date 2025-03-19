package org.voicerobotfight.Data;

public class GameData {
    public static Boolean isGame = false;
    public static Boolean isTest = false;

    public static void release(){
        isGame = false;
        isTest = false;
    }
}
