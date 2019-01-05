package druid;

import basemod.BaseMod;
import basemod.ModPanel;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import druid.cards.common.attack.FillerCommonAttack;
import druid.cards.common.power.FillerCommonPower;
import druid.cards.starter.FierceCompanion;
import druid.cards.common.skill.FillerCommonSkill;
import druid.cards.rare.attack.FillerRareAttack;
import druid.cards.rare.power.FillerRarePower;
import druid.cards.starter.DefendDruid;
import druid.cards.starter.StrikeDruid;
import druid.cards.uncommon.attack.FillerUncommonAttack;
import druid.cards.uncommon.power.FillerUncommonPower;
import druid.cards.rare.skill.FillerRareSkill;
import druid.cards.uncommon.skill.FillerUncommonSkill;
import druid.cards.uncommon.skill.ProtectiveCompanion;
import druid.characters.TheDruid;
import druid.patches.*;
import druid.relics.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;

@SpireInitializer
public class DruidMod implements PostInitializeSubscriber,EditCharactersSubscriber, EditCardsSubscriber, EditRelicsSubscriber, EditStringsSubscriber {

    public static final Logger logger = LogManager.getLogger(DruidMod.class.getName());

    //MOD INFO
    private static final String MOD_NAME = "The Glaidator";
    private static final String AUTHOR = "Residualshade";
    private static final String DESCRIPTOIN = "Adds The Druid as a new playable character.";

    public static final Color MOD_COLOR = Color.MAROON;

    public DruidMod(){
        BaseMod.subscribe(this);

        logger.info("creating the color " + AbstractCardEnum.DRUID_MAROON.toString());
        BaseMod.addColor(
                AbstractCardEnum.DRUID_MAROON
                ,MOD_COLOR
                , Resources.BG_ATTACK_512_PATH
                , Resources.BG_SKILL_512_PATH
                , Resources.BG_POWER_512_PATH
                , Resources.ENERGY_ORB_512_PATH
                , Resources.BG_ATTACK_1024_PATH
                , Resources.BG_SKILL_1024_PATH
                , Resources.BG_POWER_1024_PATH
                , Resources.ENERGY_ORB_1024_PATH
                , Resources.ENERGY_ORB_IN_DESCRIPTION_PATH
        );
    }

    //Used by @SpireInitializer
    public static void initialize(){
        logger.debug("Started initializing " + MOD_NAME);
        DruidMod druidMod = new DruidMod();
        logger.debug("Finished initializing " + MOD_NAME);
    }

    @Override
    public void receiveEditCharacters() {
        logger.info("adding " + TheDruidEnum.THE_DRUID_CLASS.toString() + " character to game");
        BaseMod.addCharacter(
            new TheDruid(
                TheDruid.NAME
                , TheDruidEnum.THE_DRUID_CLASS
            )
            , Resources.BUTTON_PATH
            , Resources.PORTRAIT_PATH
            , TheDruidEnum.THE_DRUID_CLASS
        );
        logger.info("added " + TheDruidEnum.THE_DRUID_CLASS.toString() + " character to game");
    }

    @Override
    public void receiveEditCards() {
        logger.info("adding " + TheDruidEnum.THE_DRUID_CLASS.toString() + " cards to game");
        BaseMod.addCard(new StrikeDruid());
        BaseMod.addCard(new DefendDruid());
        BaseMod.addCard(new FierceCompanion());
        BaseMod.addCard(new ProtectiveCompanion());
        BaseMod.addCard(new FillerCommonAttack());
        BaseMod.addCard(new FillerCommonSkill());
        BaseMod.addCard(new FillerCommonPower());
        BaseMod.addCard(new FillerUncommonAttack());
        BaseMod.addCard(new FillerUncommonSkill());
        BaseMod.addCard(new FillerUncommonPower());
        BaseMod.addCard(new FillerRareAttack());
        BaseMod.addCard(new FillerRareSkill());
        BaseMod.addCard(new FillerRarePower());
        logger.info("added " + TheDruidEnum.THE_DRUID_CLASS.toString() + " cards to game");
    }

    @Override
    public void receiveEditRelics() {
        logger.info("adding " + TheDruidEnum.THE_DRUID_CLASS.toString() + " relics to game");
        BaseMod.addRelicToCustomPool(new AnimalCompanion(),AbstractCardEnum.DRUID_MAROON);
        logger.info("added " + TheDruidEnum.THE_DRUID_CLASS.toString() + " relics to game");
    }

    @Override
    public void receiveEditStrings() {
        logger.info("adding " + TheDruidEnum.THE_DRUID_CLASS.toString() + " card strings to game");
        String cardStrings = Gdx.files.internal(Resources.CARD_STRINGS_PATH).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(CardStrings.class, cardStrings);
        String powerStrings = Gdx.files.internal(Resources.POWER_STRINGS_PATH).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(PowerStrings.class, powerStrings);
        String relicStrings = Gdx.files.internal(Resources.RELIC_STRINGS_PATH).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);
        logger.info("added " + TheDruidEnum.THE_DRUID_CLASS.toString() + " card strings to game");
    }

    @Override
    public void receivePostInitialize() {
        Texture badgeTexture = new Texture(Resources.MOD_BADGE_PATH);
        ModPanel settingsPanel = new ModPanel();
        BaseMod.registerModBadge(badgeTexture,MOD_NAME,AUTHOR,DESCRIPTOIN,settingsPanel);

        Settings.isDailyRun = false;
        Settings.isTrial = false;
        Settings.isDemo = false;
    }
}
