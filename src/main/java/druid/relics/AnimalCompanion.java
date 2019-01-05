package druid.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.esotericsoftware.spine.AnimationState;
import com.evacipated.cardcrawl.modthespire.lib.ConfigUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.saveAndContinue.SaveFileObfuscator;
import druid.Resources;
import kobting.friendlyminions.characters.AbstractPlayerWithMinions;

import java.io.File;
import java.util.logging.FileHandler;

public class AnimalCompanion extends CustomRelic {

    public static final String ID = "druid:AnimalCompanion";

    private druid.minions.AnimalCompanion animalCompanion;

    //TODO: PLACE HOLDER TEXTURE
    public AnimalCompanion() {
        super(
            ID
            , new Texture(Resources.ANIMAL_COMPANION_PATH)
            , RelicTier.STARTER
            , LandingSound.HEAVY
        );
        animalCompanion = new druid.minions.AnimalCompanion(0,0);
    }

    @Override
    public void atTurnStart() {
        animalCompanion.rollMove();
        animalCompanion.createIntent();
    }

    @Override
    public void onPlayerEndTurn() {
        animalCompanion.takeTurn();
    }

    @Override
    public void onRest() {
        if(animalCompanion.isDead) {
            animalCompanion.isDead = false;
            animalCompanion.isDying = false;
            animalCompanion.tintFadeOutCalled = false;
            animalCompanion.currentHealth = (int)(animalCompanion.maxHealth*.3);
        } else {
            animalCompanion.currentHealth = animalCompanion.currentHealth + (int)(animalCompanion.maxHealth*.3);
            if(animalCompanion.currentHealth > animalCompanion.maxHealth) {
                animalCompanion.currentHealth = animalCompanion.maxHealth;
            }
        }

    }

    @Override
    public void atBattleStartPreDraw() {
        if(animalCompanion.currentHealth > 0) {
            AbstractPlayerWithMinions player = (AbstractPlayerWithMinions) AbstractDungeon.player;
            player.addMinion(animalCompanion);
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

//    public static void getSavedItem() {
//
//    }
//
//    private static String getSavePath() {
//        return ConfigUtils.CONFIG_DIR + File.separator + "Druid" + File.separator + "AnimalCompanion" + ".autosave" + (Settings.isBeta ? "BETA" : "");
//    }
//
//    public static void deleteSaveString(String filePath) {
//        Gdx.files.absolute(getSavePath()).delete();
//    }
//
//    private static String loadSaveString(String filePath) {
//        FileHandle file = Gdx.files.absolute(filePath);
//        String data = file.readString();
//        if(SaveFileObfuscator.isObfuscated(data)) {
//            return SaveFileObfuscator.decode(data,"key");
//        }
//        return data;
//    }
//
//    private class Save
//    {
//        public Save() {
//
//        }
//    }
}
