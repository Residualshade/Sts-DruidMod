package druid.characters;

import basemod.abstracts.CustomPlayer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import druid.DruidMod;
import druid.Resources;
import druid.cards.starter.DefendDruid;
import druid.cards.starter.FierceCompanion;
import druid.cards.starter.StrikeDruid;
import druid.cards.uncommon.skill.ProtectiveCompanion;
import druid.patches.*;
import druid.relics.*;
import kobting.friendlyminions.characters.AbstractPlayerWithMinions;
import kobting.friendlyminions.characters.CustomCharSelectInfo;

import java.util.ArrayList;

public class TheDruid extends AbstractPlayerWithMinions {

    //CLASS INFO
    public static final String NAME = "The Druid";
    public static final String DESCRIPTION = "N/A";

    //CLASS STATS
    private static final int ENERGY_PER_TURN = 3;
    public static final int STARTING_HP = 75;
    public static final int MAX_HP = 75;
    public static final int STARTING_GOLD = 99;
    public static final int HAND_SIZE = 5;
    private static final int ORB_SLOTS = 0;

    public TheDruid(String name, PlayerClass setClass) {
        super(name, setClass, null, null, (String) null, null);

        initializeClass(
            null
            , Resources.SHOULDER_2_PATH
            , Resources.SHOULDER_1_PATH
            , Resources.CORPSE_PATH
            , getLoadout()
            ,-4.0F
            ,-16.0F
            ,220.0F
            ,290.0F
            , new EnergyManager(ENERGY_PER_TURN)
        );
        loadAnimation(Resources.SKELETON_ATLAS_PATH,Resources.SKELETON_JSON_PATH,1.0F);
        AnimationState.TrackEntry trackEntry = state.setAnimation(0,"Idle", true);
        trackEntry.setTime(trackEntry.getEndTime()*MathUtils.random());
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> startingDeck = new ArrayList<>();
        startingDeck.add(StrikeDruid.ID);
        startingDeck.add(StrikeDruid.ID);
        startingDeck.add(StrikeDruid.ID);
        startingDeck.add(StrikeDruid.ID);
        startingDeck.add(StrikeDruid.ID);
        startingDeck.add(DefendDruid.ID);
        startingDeck.add(DefendDruid.ID);
        startingDeck.add(DefendDruid.ID);
        startingDeck.add(DefendDruid.ID);
        startingDeck.add(FierceCompanion.ID);
        startingDeck.add(ProtectiveCompanion.ID);
        return startingDeck;
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> startingRelics = new ArrayList<>();
        startingRelics.add(AnimalCompanion.ID);
        UnlockTracker.markRelicAsSeen(AnimalCompanion.ID);
        return startingRelics;
    }


    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAME, DESCRIPTION,STARTING_HP,MAX_HP,ORB_SLOTS,STARTING_GOLD,HAND_SIZE,this,getStartingRelics(),getStartingDeck(),false);
    }

    @Override
    public String getTitle(PlayerClass playerClass) {
        return NAME;
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return AbstractCardEnum.DRUID_MAROON;
    }

    @Override
    public Color getCardRenderColor() {
        return DruidMod.MOD_COLOR;
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new FierceCompanion();
    }

    @Override
    public Color getCardTrailColor() {
        return DruidMod.MOD_COLOR;
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 4;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("ATTACK_HEAVY", MathUtils.random(-0.2f, 0.2f));
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, true);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "ATTACK_HEAVY";
    }

    @Override
    public String getLocalizedCharacterName() {
        return NAME;
    }

    @Override
    public AbstractPlayer newInstance() {
        return new TheDruid(NAME, TheDruidEnum.THE_DRUID_CLASS);
    }

    @Override
    public String getSpireHeartText() {
        return "NL You ready your Weapon...";
    }

    @Override
    public Color getSlashAttackColor() {
        return DruidMod.MOD_COLOR;
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
            AbstractGameAction.AttackEffect.SLASH_DIAGONAL
            , AbstractGameAction.AttackEffect.SLASH_HORIZONTAL
            , AbstractGameAction.AttackEffect.SLASH_VERTICAL
            , AbstractGameAction.AttackEffect.SLASH_HEAVY
        };
    }

    //TODO: Character Specific Dialog
    @Override
    public String getVampireText() {
        return "Navigating an unlit street, you come across several hooded figures in the midst of some dark ritual. As you approach, they turn to you in eerie unison. The tallest among them bares fanged teeth and extends a long, pale hand towards you. NL ~\"Join~ ~us,~ ~oh Mighty Warrior,~ ~and~ ~feel~ ~the~ ~warmth~ ~of~ ~the~ ~Spire.\"~";
    }

    @Override
    public CustomCharSelectInfo getInfo() {
        return new CustomCharSelectInfo(NAME, DESCRIPTION,STARTING_HP,MAX_HP,ORB_SLOTS,4,STARTING_GOLD,HAND_SIZE,this,getStartingRelics(),getStartingDeck(),false);
    }
}
