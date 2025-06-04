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

import static org.officersam.tanks.scripts.world.systems.ot_addmarket.addMarketplace;

public class SOV_ZvezdaRodiny {
    // Star Orbits
    //asteroids
    final float stable1Dist = 14000f;
    final float asteroidBelt1Dist = 3500f;
    final float asteroidBelt2Dist = 15600f;

    //relays
    final float buoy1Dist = 4500f;
    final float relay1Dist = 7650f;
    final float sensor1Dist = 10950f;

    //planets
    final float zarnitsaDist = 5100f;
    final float ozyornayaDist = 10500f;
    final float zvezdDist = 13500f;

    //colonized
    final float volgagradDist = 5500f;
    final float tselinaDist = 7000f;
    final float proletariaDist = 9200f;

    //jumps
    final float jumpInnerDist = 3590f;
    final float jumpOuterDist = 12000;
    final float jumpFringeDist = 19900f;


    public void generate(SectorAPI sector) {

        StarSystemAPI system = sector.createStarSystem("Kosmograd");
        system.getLocation().set(-18150, 325);

        system.setBackgroundTextureFilename("graphics/backgrounds/StarofAmerica_background.jpg");

        //praise the sun
        PlanetAPI sovietStar = system.initStar("ZvezdaRodiny", "star_red_supergiant", 1650f, 600, 5, 1f, 2f);
        system.setLightColor(new Color(255, 180, 150));
        sovietStar.setCustomDescriptionId("sov_zvezdarodiny_kosmograd");

        //JumppointInner
        JumpPointAPI jumpPoint_inner = Global.getFactory().createJumpPoint(
                "inner_jump",
                "Inner System Jump");

        jumpPoint_inner.setCircularOrbit(system.getEntityById("ZvezdaRodiny"), 360 * (float) Math.random(), jumpInnerDist, 4000f);
        jumpPoint_inner.setStandardWormholeToHyperspaceVisual();

        system.addEntity(jumpPoint_inner);

        //JumppointOuter
        JumpPointAPI jumpPoint_outer = Global.getFactory().createJumpPoint(
                "outer_jump",
                "Outer System Jump");

        jumpPoint_outer.setCircularOrbit(system.getEntityById("ZvezdaRodiny"), 360 * (float) Math.random(), jumpOuterDist, 2000f);
        jumpPoint_outer.setStandardWormholeToHyperspaceVisual();

        system.addEntity(jumpPoint_outer);

        //JumppointFring
        JumpPointAPI jumpPoint_fringe = Global.getFactory().createJumpPoint(
                "fringe_jump",
                "Fringe System Jump");

        jumpPoint_fringe.setCircularOrbit(system.getEntityById("ZvezdaRodiny"), 360 * (float) Math.random(), jumpFringeDist, 6000f);
        jumpPoint_fringe.setStandardWormholeToHyperspaceVisual();

        system.addEntity(jumpPoint_fringe);

        //autogenerate hyperspace points
        system.autogenerateHyperspaceJumpPoints(true, false);

        //stablloc
        SectorEntityToken stableLoc1 = system.addCustomEntity("soviet_stableloc_1", "Stable Location", "stable_location", Factions.NEUTRAL);
        stableLoc1.setCircularOrbit(sovietStar, MathUtils.getRandomNumberInRange(0f, 360f), stable1Dist, 520);

        //asteroid belt1 ring
        system.addAsteroidBelt(sovietStar, 950, asteroidBelt1Dist, 550f, 300f, 600f, Terrain.ASTEROID_BELT, "Solntseten");
        system.addRingBand(sovietStar, "misc", "rings_asteroids0", 256f, 1, Color.gray, 256f, asteroidBelt1Dist - 190, 300f);
        system.addRingBand(sovietStar, "misc", "rings_asteroids0", 256f, 0, Color.gray, 256f, asteroidBelt1Dist, 450f);

        //asteroid belt2 ring
        system.addAsteroidBelt(sovietStar, 1250, asteroidBelt2Dist, 750f, 300f, 600f, Terrain.ASTEROID_BELT, "KomIntern");
        system.addRingBand(sovietStar, "misc", "rings_asteroids0", 220f, 3, Color.gray, 220f, asteroidBelt2Dist - 180, 300f);
        system.addRingBand(sovietStar, "misc", "rings_asteroids0", 256f, 1, Color.gray, 256f, asteroidBelt2Dist, 450f);
        system.addRingBand(sovietStar, "misc", "rings_asteroids0", 220f, 3, Color.gray, 220f, asteroidBelt2Dist + 1800, 500f);

        // Relays
        SectorEntityToken americaStar_relay = system.addCustomEntity("sovietStar_relay", // unique id
                "Soviet Relay", // name - if null, defaultName from custom_entities.json will be used
                "comm_relay", // type of object, defined in custom_entities.json
                "SOV"); // faction
        americaStar_relay.setCircularOrbitPointingDown(sovietStar, MathUtils.getRandomNumberInRange(0f, 360f), relay1Dist, 520);

        SectorEntityToken americaStar_buoy = system.addCustomEntity("sovietStar_buoy", // unique id
                "Soviet Nav Buoy", // name - if null, defaultName from custom_entities.json will be used
                "nav_buoy", // type of object, defined in custom_entities.json
                "SOV"); // faction
        americaStar_buoy.setCircularOrbitPointingDown(sovietStar, MathUtils.getRandomNumberInRange(0f, 360f), buoy1Dist, 520);

        SectorEntityToken americaStar_sensor = system.addCustomEntity("sovietStar_sensor", // unique id
                "Soviet Sensor Array", // name - if null, defaultName from custom_entities.json will be used
                "sensor_array", // type of object, defined in custom_entities.json
                "SOV"); // faction
        americaStar_sensor.setCircularOrbitPointingDown(sovietStar, MathUtils.getRandomNumberInRange(0f, 360f), sensor1Dist, 520);

        // Ozyornaya Planet
        PlanetAPI ozyornaya = system.addPlanet("s_ozyornaya", sovietStar, "Ozyornaya", "frozen", 360 * (float) Math.random(), 175f, ozyornayaDist, 390f);
        ozyornaya.setCustomDescriptionId("sov_zvezdarodiny_orzyornaya"); //reference descriptions.csv

        MarketAPI ozyornaya_market = addMarketplace("SOV", ozyornaya, null,
                "Ozyornaya",
                2,
                Arrays.asList(
                        Conditions.HABITABLE,
                        Conditions.COLD,

                        Conditions.POPULATION_2,
                        Conditions.OUTPOST,

                        Conditions.ORE_MODERATE,

                        Conditions.STEALTH_MINEFIELDS
                ),
                Arrays.asList(
                        Submarkets.GENERIC_MILITARY,
                        Submarkets.SUBMARKET_BLACK
                ),
                Arrays.asList(
                        Industries.POPULATION,
                        Industries.SPACEPORT,

                        Industries.MINING,

                        Industries.GROUNDDEFENSES,
                        Industries.PATROLHQ
                ),
                0.20f,
                false,
                true);


        // Zarnitsa
        PlanetAPI zarnitsa = system.addPlanet("s_zarnitsa ", sovietStar, "Zarnitsa", "terran", 360f * (float) Math.random(), 140f, zarnitsaDist, 360f);
        zarnitsa.setCustomDescriptionId("sov_zvezdarodiny_zarnitsa"); //reference descriptions.csv

        MarketAPI zarnitsa_market = addMarketplace("SOV", zarnitsa, null,
                "Zarnitsa",
                2,
                Arrays.asList(
                        Conditions.HABITABLE,
                        Conditions.POLLUTION,

                        Conditions.POPULATION_2,
                        Conditions.OUTPOST,

                        Conditions.ORE_MODERATE,
                        Conditions.ORGANICS_TRACE,

                        Conditions.STEALTH_MINEFIELDS
                ),
                Arrays.asList(
                        Submarkets.GENERIC_MILITARY,
                        Submarkets.SUBMARKET_BLACK
                ),
                Arrays.asList(
                        Industries.POPULATION,
                        Industries.SPACEPORT,

                        Industries.MINING,

                        Industries.ORBITALSTATION_MID,
                        Industries.GROUNDDEFENSES,
                        Industries.MILITARYBASE
                ),
                0.20f,
                false,
                true);


        SectorEntityToken eF1 = system.addTerrain(Terrain.ASTEROID_FIELD,
                new AsteroidFieldTerrainPlugin.AsteroidFieldParams(50f, 250f, 15, 40, 2f, 10f, "Asteroids Field"));
        eF1.setCircularOrbit(zarnitsa, 360f * (float) Math.random(), 1, 45);

        // zarnitsa debris fields.
        DebrisFieldTerrainPlugin.DebrisFieldParams params3 = new DebrisFieldTerrainPlugin.DebrisFieldParams(
                200, // field radius - should not go above 1000 for performance reasons
                1.5f, // density, visual - affects number of debris pieces
                10000000f, // duration in days
                0f); // days the field will keep generating glowing pieces
        params3.source = DebrisFieldTerrainPlugin.DebrisFieldSource.MIXED;
        params3.baseSalvageXP = 500; // base XP for scavenging in field
        SectorEntityToken zarnitsaDebris1 = Misc.addDebrisField(system, params3, StarSystemGenerator.random);
        zarnitsaDebris1.setSensorProfile(1000f);
        zarnitsaDebris1.setDiscoverable(true);
        zarnitsaDebris1.setCircularOrbit(zarnitsa, 360f * (float) Math.random(), 0, 120f);
        zarnitsaDebris1.setId("zarnitsaDebris1");

        SectorEntityToken scrap1 = DerelictThemeGenerator.addSalvageEntity(system, Entities.SUPPLY_CACHE, Factions.DERELICT);
        scrap1.setId("zarnitsa_scrap1");
        scrap1.setCircularOrbit(zarnitsa, 360f * (float) Math.random(), 150, 25);
        Misc.setDefenderOverride(scrap1, new DefenderDataOverride(Factions.DERELICT, 1, 2, 5));
        scrap1.setDiscoverable(Boolean.TRUE);

        SectorEntityToken scrap2 = DerelictThemeGenerator.addSalvageEntity(system, Entities.SUPPLY_CACHE, Factions.DERELICT);
        scrap2.setId("zarnitsa_scrap2");
        scrap2.setCircularOrbit(zarnitsa, 360f * (float) Math.random(), 115, 10);
        Misc.setDefenderOverride(scrap2, new DefenderDataOverride(Factions.DERELICT, 1, 3, 8));
        scrap2.setDiscoverable(Boolean.TRUE);

        addDerelict(system, zarnitsa, "ot_M4Sherman_Hull_standard", ShipRecoverySpecial.ShipCondition.WRECKED, 160f, (Math.random() < 0.6));
        addDerelict(system, zarnitsa, "ot_M4Sherman_Hull_standard", ShipRecoverySpecial.ShipCondition.BATTERED, 20f, (Math.random() < 0.6));
        addDerelict(system, zarnitsa, "ot_Pz4HMedium_Hull_standard", ShipRecoverySpecial.ShipCondition.WRECKED, 60f, (Math.random() < 0.6));

        // Mir Zvezd
        PlanetAPI zvezd = system.addPlanet("s_zvezd", sovietStar, "Mir Zvezd", "gas_giant", 360f * (float) Math.random(), 400f, zvezdDist, 550f);
        zvezd.setCustomDescriptionId("ger_sonnedeutschlands_sonnenrad"); //reference descriptions.csv
        //PlanetConditionGenerator.generateConditionsForPlanet(sonnen, StarAge.AVERAGE);

        MarketAPI zvezd_market = addMarketplace("SOV", zvezd, null,
                "Mir Zvezd",
                3,
                Arrays.asList(
                        Conditions.HIGH_GRAVITY,
                        Conditions.DENSE_ATMOSPHERE,

                        Conditions.POPULATION_3,
                        Conditions.OUTPOST,
                        Conditions.VOLATILES_ABUNDANT,

                        Conditions.STEALTH_MINEFIELDS
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

        // Trotskiya
        PlanetAPI trotskiya;
        trotskiya = system.addPlanet("s_trotskiya",
                zvezd,
                "Trotskiya",
                "barren",
                360f * (float) Math.random(),
                95f,
                550f,
                180f);

        trotskiya.setCustomDescriptionId("sov_zvezdarodiny_trotskiya"); //reference descriptions.csv

        MarketAPI trotskiya_market = addMarketplace("SOV", trotskiya, null,
                "Trotskiya",
                3,
                Arrays.asList(
                        Conditions.NO_ATMOSPHERE,
                        Conditions.LOW_GRAVITY,
                        Conditions.COLD,

                        Conditions.POPULATION_3,
                        Conditions.OUTPOST,

                        Conditions.ORE_MODERATE,

                        Conditions.STEALTH_MINEFIELDS
                ),
                Arrays.asList(
                        Submarkets.GENERIC_MILITARY,
                        Submarkets.SUBMARKET_BLACK
                ),
                Arrays.asList(
                        Industries.POPULATION,
                        Industries.SPACEPORT,

                        Industries.MINING,
                        Industries.REFINING,

                        Industries.GROUNDDEFENSES,
                        Industries.PATROLHQ

                ),
                0.20f,
                false,
                true);

        //Proletaria Capital Planet
        PlanetAPI proletaria;
        proletaria = system.addPlanet("s_proletaria",
                sovietStar,
                "Proletaria",
                "tundra",
                360f * (float) Math.random(),
                200f,
                proletariaDist,
                365f);

        proletaria.setCustomDescriptionId("sov_zvezdarodiny_proletaria"); //reference descriptions.csv

        MarketAPI proletaria_market = addMarketplace("SOV", proletaria, null,
                "Proletaria",
                10,
                Arrays.asList(
                        Conditions.COLD,
                        Conditions.HABITABLE,

                        Conditions.POPULATION_10,
                        Conditions.URBANIZED_POLITY,
                        Conditions.REGIONAL_CAPITAL,

                        Conditions.ORE_MODERATE,
                        Conditions.FARMLAND_ADEQUATE,
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

                        Industries.FARMING,
                        Industries.MINING,
                        Industries.LIGHTINDUSTRY,

                        Industries.STARFORTRESS_HIGH,
                        Industries.HEAVYBATTERIES,
                        Industries.HIGHCOMMAND

                ),
                0.18f,
                false,
                false);

        // Volgagrad Prime Industrial Center
        PlanetAPI volgagrad;
        volgagrad = system.addPlanet("s_volgagrad",
                sovietStar,
                "Volgagrad Prime",
                "arid",
                360f * (float) Math.random(),
                220f,
                volgagradDist,
                400f);

        volgagrad.setCustomDescriptionId("sov_zvezdarodiny_volgagrad"); //reference descriptions.csv

        MarketAPI appalax_market = addMarketplace("SOV", volgagrad, null,
                "Volgagrad Prime",
                9,
                Arrays.asList(
                        Conditions.HOT,
                        Conditions.HABITABLE,
                        Conditions.POLLUTION,
                        Conditions.RUINS_WIDESPREAD,

                        Conditions.POPULATION_8,
                        Conditions.INDUSTRIAL_POLITY,

                        Conditions.ORE_ULTRARICH,
                        Conditions.RARE_ORE_ABUNDANT,
                        Conditions.VOLATILES_TRACE,
                        Conditions.FARMLAND_POOR,

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

                        Industries.MINING,
                        Industries.REFINING,
                        Industries.HEAVYINDUSTRY,
                        //Industries.ORBITALWORKS,
                        Industries.FUELPROD,

                        Industries.STARFORTRESS_MID,
                        Industries.HEAVYBATTERIES,
                        Industries.MILITARYBASE

                ),
                0.19f,
                false,
                true);


        // Tselina Potatobasket
        PlanetAPI tselina;
        tselina = system.addPlanet("s_tselina",
                sovietStar,
                "Tselina",
                "terran",
                360f * (float) Math.random(),
                220f,
                tselinaDist,
                360f);

        tselina.setCustomDescriptionId("sov_zvezdarodiny_tselina"); //reference descriptions.csv

        MarketAPI tselina_market = addMarketplace("SOV", tselina, null,
                "Tselina",
                9,
                Arrays.asList(
                        Conditions.TERRAN,
                        Conditions.HABITABLE,
                        Conditions.SOLAR_ARRAY,

                        Conditions.POPULATION_8,
                        Conditions.RURAL_POLITY,

                        Conditions.ORE_MODERATE,
                        Conditions.VOLATILES_TRACE,
                        Conditions.FARMLAND_BOUNTIFUL,
                        Conditions.ORGANICS_PLENTIFUL,

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
                        Industries.SPACEPORT,
                        Industries.WAYSTATION,

                        Industries.MINING,
                        Industries.FARMING,
                        Industries.LIGHTINDUSTRY,

                        Industries.STARFORTRESS_MID,
                        Industries.HEAVYBATTERIES,
                        Industries.MILITARYBASE

                ),
                0.18f,
                false,
                false);

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



