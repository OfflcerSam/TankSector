package org.officersam.tanks.scripts.world.systems;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.DerelictShipEntityPlugin;
import com.fs.starfarer.api.impl.campaign.ids.*;
import com.fs.starfarer.api.impl.campaign.procgen.PlanetConditionGenerator;
import com.fs.starfarer.api.impl.campaign.procgen.StarAge;
import com.fs.starfarer.api.impl.campaign.procgen.StarSystemGenerator;
import com.fs.starfarer.api.impl.campaign.procgen.themes.BaseThemeGenerator;
import com.fs.starfarer.api.impl.campaign.procgen.themes.SalvageSpecialAssigner;
import com.fs.starfarer.api.impl.campaign.terrain.AsteroidFieldTerrainPlugin;
import com.fs.starfarer.api.impl.campaign.procgen.themes.DerelictThemeGenerator;
import com.fs.starfarer.api.impl.campaign.rulecmd.salvage.special.ShipRecoverySpecial;
import com.fs.starfarer.api.impl.campaign.procgen.DefenderDataOverride;
import com.fs.starfarer.api.impl.campaign.terrain.DebrisFieldTerrainPlugin;
import com.fs.starfarer.api.util.Misc;
import org.lazywizard.lazylib.MathUtils;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
//import java.util.Locale;

import static org.officersam.tanks.scripts.world.systems.ot_addmarket.addMarketplace;

public class GER_SonneDeutschlands {
    // Star Orbits
    //asteroids
    final float asteroidBelt2Dist = 9600f;

    //relays
    final float buoy1Dist = 4150f;
    final float relay1Dist = 5500f;
    final float sensor1Dist = 9550f;

    //planets
    final float kriegheimDist = 2850f;
    final float dunkelDist = 9850f;
    final float sonnenradDist = 9000f;

    //colonized
    final float eisenmarkDist = 4400f;
    final float schwarweltDist = 6800f;
    final float gotterDist = 7200f;

    //jumps
    final float jumpInnerDist = 2700f;
    final float jumpOuterDist = 9500;
    final float jumpFringeDist = 16500f;


    public void generate(SectorAPI sector) {

        StarSystemAPI system = sector.createStarSystem("Wölfram");
        system.getLocation().set(300, -11750);

        system.setBackgroundTextureFilename("graphics/backgrounds/StarofAmerica_background.jpg");

        //praise the sun
        PlanetAPI germanStar = system.initStar("SonneDeutschlands", "star_yellow", 800f, 450, 10, 0.5f, 3f);
        system.setLightColor(new Color(255, 245, 225));
        germanStar.setCustomDescriptionId("ger_sonnedeutschlands_wolfram");

        //JumppointInner
        JumpPointAPI jumpPoint_inner = Global.getFactory().createJumpPoint(
                "inner_jump",
                "Inner System Jump");

        jumpPoint_inner.setCircularOrbit(system.getEntityById("SonneDeutschlands"), 360 * (float) Math.random(), jumpInnerDist, 4000f);
        jumpPoint_inner.setStandardWormholeToHyperspaceVisual();

        system.addEntity(jumpPoint_inner);

        //JumppointOuter
        JumpPointAPI jumpPoint_outer = Global.getFactory().createJumpPoint(
                "outer_jump",
                "Outer System Jump");

        jumpPoint_outer.setCircularOrbit(system.getEntityById("SonneDeutschlands"), 360 * (float) Math.random(), jumpOuterDist, 2000f);
        jumpPoint_outer.setStandardWormholeToHyperspaceVisual();

        system.addEntity(jumpPoint_outer);

        //JumppointFring
        JumpPointAPI jumpPoint_fringe = Global.getFactory().createJumpPoint(
                "fringe_jump",
                "Fringe System Jump");

        jumpPoint_fringe.setCircularOrbit(system.getEntityById("SonneDeutschlands"), 360 * (float) Math.random(), jumpFringeDist, 6000f);
        jumpPoint_fringe.setStandardWormholeToHyperspaceVisual();

        system.addEntity(jumpPoint_fringe);

        //autogenerate hyperspace points
        system.autogenerateHyperspaceJumpPoints(true, false);

        //asteroid field


        //asteroid belt2 ring
        system.addAsteroidBelt(germanStar, 1250, asteroidBelt2Dist, 800f, 300f, 600f, Terrain.ASTEROID_BELT, "DE_Belt");
        system.addRingBand(germanStar, "misc", "rings_asteroids0", 256f, 3, Color.gray, 230f, asteroidBelt2Dist - 190, 300f);
        system.addRingBand(germanStar, "misc", "rings_asteroids0", 256f, 1, Color.gray, 256f, asteroidBelt2Dist, 450f);
        system.addRingBand(germanStar, "misc", "rings_asteroids0", 256f, 2, Color.gray, 230f, asteroidBelt2Dist + 190, 500f);

        // Relays
        SectorEntityToken germanStar_relay = system.addCustomEntity("germanStar_relay", // unique id
                "German Relay", // name - if null, defaultName from custom_entities.json will be used
                "comm_relay", // type of object, defined in custom_entities.json
                "GER"); // faction
        germanStar_relay.setCircularOrbitPointingDown(germanStar, MathUtils.getRandomNumberInRange(0f, 360f), relay1Dist, 520);

        SectorEntityToken germanStar_buoy = system.addCustomEntity("germanStar_buoy", // unique id
                "German Nav Buoy", // name - if null, defaultName from custom_entities.json will be used
                "nav_buoy", // type of object, defined in custom_entities.json
                "GER"); // faction
        germanStar_buoy.setCircularOrbitPointingDown(germanStar, MathUtils.getRandomNumberInRange(0f, 360f), buoy1Dist, 520);

        SectorEntityToken germanStar_sensor = system.addCustomEntity("germanStar_sensor", // unique id
                "German Sensor Array", // name - if null, defaultName from custom_entities.json will be used
                "sensor_array", // type of object, defined in custom_entities.json
                "GER"); // faction
        germanStar_sensor.setCircularOrbitPointingDown(germanStar, MathUtils.getRandomNumberInRange(0f, 360f), sensor1Dist, 520);


        // kriegheim
        PlanetAPI kriegheim = system.addPlanet("u_kriegheim ", germanStar, "Kriegheim Testgelände", "barren-bombarded", 360f * (float) Math.random(), 115f, kriegheimDist, 220f);
        kriegheim.setCustomDescriptionId("ger_sonnedeutschlands_kriegheim"); //reference descriptions.csv
        //PlanetConditionGenerator.generateConditionsForPlanet(kriegheim, StarAge.AVERAGE);
        kriegheim.getMarket().addCondition(Conditions.TOXIC_ATMOSPHERE);
        kriegheim.getMarket().addCondition(Conditions.THIN_ATMOSPHERE);
        kriegheim.getMarket().addCondition(Conditions.IRRADIATED);
        kriegheim.getMarket().addCondition(Conditions.VERY_HOT);
        kriegheim.getMarket().addCondition(Conditions.VOLATILES_TRACE);
        kriegheim.getMarket().addCondition(Conditions.RUINS_SCATTERED);

        SectorEntityToken kF1 = system.addTerrain(Terrain.ASTEROID_FIELD,
                new AsteroidFieldTerrainPlugin.AsteroidFieldParams(50f, 250f, 15, 40, 2f, 10f, "Asteroids Field"));
        kF1.setCircularOrbit(kriegheim, 360f * (float) Math.random(), 1, 45);

        // kriegheim debris fields.
        DebrisFieldTerrainPlugin.DebrisFieldParams params1 = new DebrisFieldTerrainPlugin.DebrisFieldParams(
                200, // field radius - should not go above 1000 for performance reasons
                1.5f, // density, visual - affects number of debris pieces
                10000000f, // duration in days
                0f); // days the field will keep generating glowing pieces
        params1.source = DebrisFieldTerrainPlugin.DebrisFieldSource.MIXED;
        params1.baseSalvageXP = 500; // base XP for scavenging in field
        SectorEntityToken kriegDebris1 = Misc.addDebrisField(system, params1, StarSystemGenerator.random);
        kriegDebris1.setSensorProfile(1000f);
        kriegDebris1.setDiscoverable(true);
        kriegDebris1.setCircularOrbit(kriegheim, 360f * (float) Math.random(), 0, 120f);
        kriegDebris1.setId("kriegheimDebris1");

        SectorEntityToken scrap1 = DerelictThemeGenerator.addSalvageEntity(system, Entities.SUPPLY_CACHE, Factions.DERELICT);
        scrap1.setId("kriegheim_scrap1");
        scrap1.setCircularOrbit(kriegheim, 360f * (float) Math.random(), 150, 25);
        Misc.setDefenderOverride(scrap1, new DefenderDataOverride(Factions.DERELICT, 1, 2, 5));
        scrap1.setDiscoverable(Boolean.TRUE);

        SectorEntityToken scrap2 = DerelictThemeGenerator.addSalvageEntity(system, Entities.SUPPLY_CACHE, Factions.DERELICT);
        scrap2.setId("kriegheim_scrap2");
        scrap2.setCircularOrbit(kriegheim, 360f * (float) Math.random(), 115, 10);
        Misc.setDefenderOverride(scrap2, new DefenderDataOverride(Factions.DERELICT, 1, 3, 8));
        scrap2.setDiscoverable(Boolean.TRUE);

        addDerelict(system, kriegheim, "ot_Pz2FLight_Hull_standard", ShipRecoverySpecial.ShipCondition.WRECKED, 160f, (Math.random() < 0.6));
        addDerelict(system, kriegheim, "ot_M4Sherman_Hull_standard", ShipRecoverySpecial.ShipCondition.BATTERED, 20f, (Math.random() < 0.6));
        addDerelict(system, kriegheim, "ot_Pz3EMedium_Hull_standard", ShipRecoverySpecial.ShipCondition.WRECKED, 60f, (Math.random() < 0.6));
        addDerelict(system, kriegheim, "kite_pirates_Raider", ShipRecoverySpecial.ShipCondition.BATTERED, 280f, (Math.random() < 0.6));
        addDerelict(system, kriegheim, "ot_Pz2FLight_Hull_standard", ShipRecoverySpecial.ShipCondition.BATTERED, 230f, (Math.random() < 0.6));
        addDerelict(system, kriegheim, "ot_Pz3EMedium_Hull_standard", ShipRecoverySpecial.ShipCondition.BATTERED, 200f, (Math.random() < 0.6));
        addDerelict(system, kriegheim, "kite_pirates_Raider", ShipRecoverySpecial.ShipCondition.WRECKED, 250f, (Math.random() < 0.6));

        // Sonnenrad
        PlanetAPI sonnen = system.addPlanet("g_sonnen", germanStar, "Sonnenrad", "gas_giant", 360f * (float) Math.random(), 400f, sonnenradDist, 550f);
        sonnen.setCustomDescriptionId("ger_sonnedeutschlands_sonnenrad"); //reference descriptions.csv
        //PlanetConditionGenerator.generateConditionsForPlanet(sonnen, StarAge.AVERAGE);

        MarketAPI sonnenrad_market = addMarketplace("GER", sonnen, null,
                "Sonnenrad Station",
                2,
                Arrays.asList(
                        Conditions.HIGH_GRAVITY,
                        Conditions.DENSE_ATMOSPHERE,
                        Conditions.EXTREME_WEATHER,

                        Conditions.POPULATION_2,
                        Conditions.OUTPOST,
                        Conditions.VOLATILES_ABUNDANT,

                        Conditions.STEALTH_MINEFIELDS,
                        Conditions.AI_CORE_ADMIN
                ),
                Arrays.asList(
                        Submarkets.GENERIC_MILITARY,
                        Submarkets.SUBMARKET_STORAGE
                ),
                Arrays.asList(
                        Industries.POPULATION,
                        Industries.SPACEPORT,
                        Industries.WAYSTATION,

                        Industries.MINING,
                        Industries.FUELPROD,

                        Industries.STARFORTRESS_HIGH,
                        Industries.HEAVYBATTERIES,
                        Industries.HIGHCOMMAND

                ),
                0.20f,
                false,
                false);

        sonnenrad_market.getIndustry(Industries.POPULATION).setAICoreId(Commodities.ALPHA_CORE);
        sonnenrad_market.getIndustry(Industries.SPACEPORT).setAICoreId(Commodities.BETA_CORE);
        sonnenrad_market.getIndustry(Industries.WAYSTATION).setAICoreId(Commodities.BETA_CORE);

        sonnenrad_market.getIndustry(Industries.MINING).setAICoreId(Commodities.BETA_CORE);
        sonnenrad_market.getIndustry(Industries.FUELPROD).setAICoreId(Commodities.BETA_CORE);

        sonnenrad_market.getIndustry(Industries.STARFORTRESS_HIGH).setAICoreId(Commodities.BETA_CORE);
        sonnenrad_market.getIndustry(Industries.HEAVYBATTERIES).setAICoreId(Commodities.BETA_CORE);
        sonnenrad_market.getIndustry(Industries.HIGHCOMMAND).setAICoreId(Commodities.ALPHA_CORE);

        // Dunkeltraum
        PlanetAPI dunkel = system.addPlanet("g_dunkel", germanStar, "Dunkeltraum", "frozen3", 360f * (float) Math.random(), 150f, dunkelDist, 425f);
        dunkel.setCustomDescriptionId("ger_sonnedeutschlands_dunkel"); //reference descriptions.csv
        //PlanetConditionGenerator.generateConditionsForPlanet(dunkel, StarAge.AVERAGE);

        MarketAPI dunkel_market = addMarketplace("GER", dunkel, null,
                "Dunkeltraum",
                2,
                Arrays.asList(
                        Conditions.LOW_GRAVITY,
                        Conditions.POOR_LIGHT,
                        Conditions.VERY_COLD,

                        Conditions.POPULATION_2,
                        Conditions.OUTPOST,
                        Conditions.ORE_SPARSE,

                        Conditions.STEALTH_MINEFIELDS,
                        Conditions.AI_CORE_ADMIN
                ),
                Arrays.asList(
                        Submarkets.GENERIC_MILITARY,
                        Submarkets.SUBMARKET_BLACK
                ),
                Arrays.asList(
                        Industries.POPULATION,
                        Industries.SPACEPORT,
                        Industries.WAYSTATION,

                        Industries.MINING,
                        Industries.FUELPROD,

                        Industries.STARFORTRESS_MID,
                        Industries.HEAVYBATTERIES,
                        Industries.PATROLHQ

                ),
                0.25f,
                false,
                true);

        dunkel_market.getIndustry(Industries.POPULATION).setAICoreId(Commodities.ALPHA_CORE);
        dunkel_market.getIndustry(Industries.SPACEPORT).setAICoreId(Commodities.BETA_CORE);
        dunkel_market.getIndustry(Industries.WAYSTATION).setAICoreId(Commodities.BETA_CORE);

        dunkel_market.getIndustry(Industries.MINING).setAICoreId(Commodities.GAMMA_CORE);

        dunkel_market.getIndustry(Industries.STARFORTRESS_MID).setAICoreId(Commodities.BETA_CORE);
        dunkel_market.getIndustry(Industries.HEAVYBATTERIES).setAICoreId(Commodities.BETA_CORE);
        dunkel_market.getIndustry(Industries.PATROLHQ).setAICoreId(Commodities.ALPHA_CORE);

        // Schwarzwelt
        PlanetAPI schwarwelt;
        schwarwelt = system.addPlanet("g_schwarwelt",
                germanStar,
                "Schwarzwelt",
                "terran",
                360f * (float) Math.random(),
                220f,
                schwarweltDist,
                365f);

        schwarwelt.setCustomDescriptionId("ger_sonnedeutschlands_schwarwelt"); //reference descriptions.csv

        MarketAPI schwarwelt_market = addMarketplace("GER", schwarwelt, null,
                "Schwarzwelt",
                4,
                Arrays.asList(
                        Conditions.TERRAN,
                        Conditions.HABITABLE,

                        Conditions.POPULATION_4,
                        Conditions.OUTPOST,

                        Conditions.ORE_SPARSE,
                        Conditions.FARMLAND_BOUNTIFUL,
                        Conditions.ORGANICS_PLENTIFUL,
                        Conditions.HABITABLE,

                        Conditions.STEALTH_MINEFIELDS,
                        Conditions.AI_CORE_ADMIN
                ),
                Arrays.asList(
                        Submarkets.GENERIC_MILITARY,
                        Submarkets.SUBMARKET_OPEN,
                        Submarkets.SUBMARKET_STORAGE,
                        Submarkets.SUBMARKET_BLACK
                ),
                Arrays.asList(
                        Industries.POPULATION,
                        Industries.MEGAPORT,
                        Industries.WAYSTATION,

                        Industries.FARMING,
                        Industries.MINING,
                        Industries.LIGHTINDUSTRY,

                        Industries.STARFORTRESS_HIGH,
                        Industries.HEAVYBATTERIES,
                        Industries.MILITARYBASE

                ),
                0.18f,
                false,
                false);

        schwarwelt_market.getIndustry(Industries.POPULATION).setAICoreId(Commodities.ALPHA_CORE);
        schwarwelt_market.getIndustry(Industries.MEGAPORT).setAICoreId(Commodities.BETA_CORE);
        schwarwelt_market.getIndustry(Industries.WAYSTATION).setAICoreId(Commodities.BETA_CORE);

        schwarwelt_market.getIndustry(Industries.FARMING).setAICoreId(Commodities.ALPHA_CORE);
        schwarwelt_market.getIndustry(Industries.MINING).setAICoreId(Commodities.BETA_CORE);
        schwarwelt_market.getIndustry(Industries.LIGHTINDUSTRY).setAICoreId(Commodities.BETA_CORE);

        schwarwelt_market.getIndustry(Industries.STARFORTRESS_HIGH).setAICoreId(Commodities.BETA_CORE);
        schwarwelt_market.getIndustry(Industries.HEAVYBATTERIES).setAICoreId(Commodities.BETA_CORE);
        schwarwelt_market.getIndustry(Industries.MILITARYBASE).setAICoreId(Commodities.ALPHA_CORE);

        // Götterdämmerung
        PlanetAPI gotter;
        gotter = system.addPlanet("g_gotter",
                germanStar,
                "Götterdämmerung",
                "terran",
                360f * (float) Math.random(),
                220f,
                gotterDist,
                370f);

        gotter.setCustomDescriptionId("ger_sonnedeutschlands_gotter"); //reference descriptions.csv

        MarketAPI gotter_market = addMarketplace("GER", gotter, null,
                "Götterdämmerung ",
                9,
                Arrays.asList(
                        Conditions.TERRAN,
                        Conditions.HABITABLE,
                        Conditions.POLLUTION,

                        Conditions.POPULATION_9,
                        Conditions.INDUSTRIAL_POLITY,

                        Conditions.ORE_MODERATE,
                        Conditions.FARMLAND_POOR,
                        Conditions.ORGANICS_TRACE,
                        Conditions.HABITABLE,

                        Conditions.STEALTH_MINEFIELDS
                ),
                Arrays.asList(
                        Submarkets.GENERIC_MILITARY,
                        Submarkets.SUBMARKET_OPEN,
                        Submarkets.SUBMARKET_STORAGE,
                        Submarkets.SUBMARKET_BLACK
                ),
                Arrays.asList(
                        Industries.POPULATION,
                        Industries.MEGAPORT,
                        Industries.WAYSTATION,

                        Industries.HEAVYINDUSTRY,
                        Industries.MINING,
                        Industries.LIGHTINDUSTRY,

                        Industries.STARFORTRESS_HIGH,
                        Industries.HEAVYBATTERIES,
                        Industries.HIGHCOMMAND

                ),
                0.18f,
                false,
                false);

        gotter_market.getIndustry(Industries.POPULATION).setAICoreId(Commodities.ALPHA_CORE);
        gotter_market.getIndustry(Industries.MEGAPORT).setAICoreId(Commodities.BETA_CORE);
        gotter_market.getIndustry(Industries.WAYSTATION).setAICoreId(Commodities.BETA_CORE);

        gotter_market.getIndustry(Industries.HEAVYINDUSTRY).setAICoreId(Commodities.BETA_CORE);
        gotter_market.getIndustry(Industries.MINING).setAICoreId(Commodities.BETA_CORE);
        gotter_market.getIndustry(Industries.LIGHTINDUSTRY).setAICoreId(Commodities.BETA_CORE);

        gotter_market.getIndustry(Industries.STARFORTRESS_HIGH).setAICoreId(Commodities.BETA_CORE);
        gotter_market.getIndustry(Industries.HEAVYBATTERIES).setAICoreId(Commodities.BETA_CORE);
        gotter_market.getIndustry(Industries.HIGHCOMMAND).setAICoreId(Commodities.ALPHA_CORE);

        // Eisenmark
        PlanetAPI eisenmark;
        eisenmark = system.addPlanet("g_eisenmark",
                germanStar,
                "Eisenmark",
                "toxic",
                360f * (float) Math.random(),
                200f,
                eisenmarkDist,
                300f);

        eisenmark.setCustomDescriptionId("ger_sonnedeutschlands_eisenmark"); //reference descriptions.csv

        MarketAPI eisenmark_market = addMarketplace("GER", eisenmark, null,
                "Eisenmark",
                7,
                Arrays.asList(
                        Conditions.TOXIC_ATMOSPHERE,
                        //Conditions.HOT,
                        Conditions.TECTONIC_ACTIVITY,

                        Conditions.POPULATION_7,
                        Conditions.INDUSTRIAL_POLITY,

                        Conditions.ORE_ABUNDANT,
                        Conditions.RARE_ORE_RICH,
                        Conditions.VOLATILES_TRACE,
                        Conditions.ORGANICS_TRACE,

                        Conditions.STEALTH_MINEFIELDS,
                        Conditions.AI_CORE_ADMIN
                ),
                Arrays.asList(
                        Submarkets.GENERIC_MILITARY,
                        Submarkets.SUBMARKET_OPEN,
                        Submarkets.SUBMARKET_STORAGE,
                        Submarkets.SUBMARKET_BLACK
                ),
                Arrays.asList(
                        Industries.POPULATION,
                        Industries.MEGAPORT,
                        Industries.WAYSTATION,

                        Industries.MINING,
                        Industries.REFINING,
                        //Industries.HEAVYINDUSTRY,
                        Industries.ORBITALWORKS,
                        Industries.FUELPROD,

                        Industries.STARFORTRESS_MID,
                        Industries.HEAVYBATTERIES,
                        Industries.MILITARYBASE

                ),
                0.19f,
                false,
                true);

        eisenmark_market.getIndustry(Industries.POPULATION).setAICoreId(Commodities.ALPHA_CORE);
        eisenmark_market.getIndustry(Industries.MEGAPORT).setAICoreId(Commodities.BETA_CORE);
        eisenmark_market.getIndustry(Industries.WAYSTATION).setAICoreId(Commodities.BETA_CORE);

        eisenmark_market.getIndustry(Industries.MINING).setAICoreId(Commodities.BETA_CORE);
        eisenmark_market.getIndustry(Industries.REFINING).setAICoreId(Commodities.BETA_CORE);

        eisenmark_market.addIndustry(Industries.ORBITALWORKS, Collections.singletonList(Items.PRISTINE_NANOFORGE));
        eisenmark_market.getIndustry(Industries.ORBITALWORKS).setAICoreId(Commodities.ALPHA_CORE);

        eisenmark_market.getIndustry(Industries.FUELPROD).setAICoreId(Commodities.BETA_CORE);

        eisenmark_market.getIndustry(Industries.STARFORTRESS_MID).setAICoreId(Commodities.BETA_CORE);
        eisenmark_market.getIndustry(Industries.HEAVYBATTERIES).setAICoreId(Commodities.BETA_CORE);
        eisenmark_market.getIndustry(Industries.MILITARYBASE).setAICoreId(Commodities.ALPHA_CORE);


    }

    private void addDerelict(StarSystemAPI system, SectorEntityToken focus, String variantId, ShipRecoverySpecial.ShipCondition condition, float orbitRadius, boolean recoverable) {

        DerelictShipEntityPlugin.DerelictShipData params = new DerelictShipEntityPlugin.DerelictShipData(new ShipRecoverySpecial.PerShipData(variantId, condition), true);
        SectorEntityToken ship = BaseThemeGenerator.addSalvageEntity(system, Entities.WRECK, Factions.NEUTRAL, params);
        ship.setDiscoverable(true);

        float orbitDays = 60f;
        ship.setCircularOrbit(focus, (float) MathUtils.getRandomNumberInRange(-2, 2) + 90f, orbitRadius, orbitDays);

        if (recoverable) {
            SalvageSpecialAssigner.ShipRecoverySpecialCreator creator = new SalvageSpecialAssigner.ShipRecoverySpecialCreator(null, 0, 0, false, null, null);
            Misc.setSalvageSpecial(ship, creator.createSpecial(ship, null));
        }
    }

}