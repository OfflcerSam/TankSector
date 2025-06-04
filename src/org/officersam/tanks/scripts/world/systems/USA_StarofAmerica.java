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

public class USA_StarofAmerica {
    // Star Orbits
    //asteroids
    final float asteroids1Dist = 10200f;
    final float asteroids2Dist = 19500f;
    final float stable1Dist = 12000f;
    final float asteroidBelt1Dist = 2550f;
    final float asteroidBelt2Dist = 9550f;

    //relays
    final float buoy1Dist = 4550f;
    final float relay1Dist = 5650f;
    final float sensor1Dist = 9350f;

    //planets
    final float dustyDist = 3400f;
    final float providenceDist = 11550f;
    final float eisenDist = 18500f;

    //colonized
    final float appalaxDist = 5200f;
    final float franklinDist = 6800f;
    final float libertyDist = 7500f;

    //stations
    final float eagleDist = 9550f;
    final float monroeDist = 2850f;

    //jumps
    final float jumpInnerDist = 3050f;
    final float jumpOuterDist = 9350;
    final float jumpFringeDist = 16700f;


    public void generate(SectorAPI sector) {

        StarSystemAPI system = sector.createStarSystem("Columbia");
        system.getLocation().set(-18150, 325);

        system.setBackgroundTextureFilename("graphics/backgrounds/StarofAmerica_background.jpg");

        //praise the sun
        PlanetAPI americaStar = system.initStar("StarofAmerica", "star_yellow", 850f, 600, 10, 0.5f, 3f);
        system.setLightColor(new Color(255, 245, 225));
        americaStar.setCustomDescriptionId("usa_starofamerica_columbia");

        //JumppointInner
        JumpPointAPI jumpPoint_inner = Global.getFactory().createJumpPoint(
                "inner_jump",
                "Inner System Jump");

        jumpPoint_inner.setCircularOrbit(system.getEntityById("StarofAmerica"), 360 * (float) Math.random(), jumpInnerDist, 4000f);
        jumpPoint_inner.setStandardWormholeToHyperspaceVisual();

        system.addEntity(jumpPoint_inner);

        //JumppointOuter
        JumpPointAPI jumpPoint_outer = Global.getFactory().createJumpPoint(
                "outer_jump",
                "Outer System Jump");

        jumpPoint_outer.setCircularOrbit(system.getEntityById("StarofAmerica"), 360 * (float) Math.random(), jumpOuterDist, 2000f);
        jumpPoint_outer.setStandardWormholeToHyperspaceVisual();

        system.addEntity(jumpPoint_outer);

        //JumppointFring
        JumpPointAPI jumpPoint_fringe = Global.getFactory().createJumpPoint(
                "fringe_jump",
                "Fringe System Jump");

        jumpPoint_fringe.setCircularOrbit(system.getEntityById("StarofAmerica"), 360 * (float) Math.random(), jumpFringeDist, 6000f);
        jumpPoint_fringe.setStandardWormholeToHyperspaceVisual();

        system.addEntity(jumpPoint_fringe);

        //autogenerate hyperspace points
        system.autogenerateHyperspaceJumpPoints(true, false);

        //stablloc
        SectorEntityToken stableLoc1 = system.addCustomEntity("america_stableloc_1", "Stable Location", "stable_location", Factions.NEUTRAL);
        stableLoc1.setCircularOrbit(americaStar, MathUtils.getRandomNumberInRange(0f, 360f), stable1Dist, 520);

        //asteroid field
        SectorEntityToken americaAF1 = system.addTerrain(Terrain.ASTEROID_FIELD,
                new AsteroidFieldTerrainPlugin.AsteroidFieldParams(250f, 500f, 10, 30, 5f, 20f, "Asteroids Field"));
        americaAF1.setCircularOrbit(americaStar, 360f * (float) Math.random(), asteroids1Dist, 220);

        // AF1 debris fields.
        DebrisFieldTerrainPlugin.DebrisFieldParams params1 = new DebrisFieldTerrainPlugin.DebrisFieldParams(
                300, // field radius - should not go above 1000 for performance reasons
                1.5f, // density, visual - affects number of debris pieces
                10000000f, // duration in days
                0f); // days the field will keep generating glowing pieces
        params1.source = DebrisFieldTerrainPlugin.DebrisFieldSource.MIXED;
        params1.baseSalvageXP = 500; // base XP for scavenging in field
        SectorEntityToken americaAF1Debris1 = Misc.addDebrisField(system, params1, StarSystemGenerator.random);
        americaAF1Debris1.setSensorProfile(1000f);
        americaAF1Debris1.setDiscoverable(true);
        americaAF1Debris1.setCircularOrbit(americaAF1, 360f * (float) Math.random(), 0, 120f);
        americaAF1Debris1.setId("AF1Debris1");

        addDerelict(system, americaAF1, "wolf_d_pirates_Attack", ShipRecoverySpecial.ShipCondition.BATTERED, 270f, (Math.random() < 0.6));
        addDerelict(system, americaAF1, "ot_M4Sherman_Hull_standard", ShipRecoverySpecial.ShipCondition.BATTERED, 200f, (Math.random() < 0.6));
        addDerelict(system, americaAF1, "wolf_d_pirates_Attack", ShipRecoverySpecial.ShipCondition.BATTERED, 240f, (Math.random() < 0.6));
        addDerelict(system, americaAF1, "ot_M2a2Light_Hull_standard", ShipRecoverySpecial.ShipCondition.BATTERED, 270f, (Math.random() < 0.6));
        addDerelict(system, americaAF1, "ot_M2a2Light_Hull_standard", ShipRecoverySpecial.ShipCondition.WRECKED, 220f, (Math.random() < 0.6));
        addDerelict(system, americaAF1, "lasher_PD", ShipRecoverySpecial.ShipCondition.BATTERED, 125f, (Math.random() < 0.6));

        //asteroid field 2
        SectorEntityToken americaAF2 = system.addTerrain(Terrain.ASTEROID_FIELD,
                new AsteroidFieldTerrainPlugin.AsteroidFieldParams(300f, 500f, 30, 60, 5f, 30f, "Asteroids Field"));
        americaAF2.setCircularOrbit(americaStar, 360f * (float) Math.random(), asteroids2Dist, 650);

        // AF2 debris fields.
        DebrisFieldTerrainPlugin.DebrisFieldParams params2 = new DebrisFieldTerrainPlugin.DebrisFieldParams(
                300, // field radius - should not go above 1000 for performance reasons
                1.5f, // density, visual - affects number of debris pieces
                10000000f, // duration in days
                0f); // days the field will keep generating glowing pieces
        params2.source = DebrisFieldTerrainPlugin.DebrisFieldSource.MIXED;
        params2.baseSalvageXP = 500; // base XP for scavenging in field
        SectorEntityToken americaAF2Debris1 = Misc.addDebrisField(system, params2, StarSystemGenerator.random);
        americaAF2Debris1.setSensorProfile(1000f);
        americaAF2Debris1.setDiscoverable(true);
        americaAF2Debris1.setCircularOrbit(americaAF2, 360f * (float) Math.random(), 0, 120f);
        americaAF2Debris1.setId("AF2Debris1");

        addDerelict(system, americaAF2, "ot_M2Medium_Hull_standard", ShipRecoverySpecial.ShipCondition.BATTERED, 220f, (Math.random() < 0.6));
        addDerelict(system, americaAF2, "ot_M4Sherman_Hull_standard", ShipRecoverySpecial.ShipCondition.BATTERED, 290f, (Math.random() < 0.6));
        addDerelict(system, americaAF2, "ot_M4Sherman_Hull_standard", ShipRecoverySpecial.ShipCondition.WRECKED, 400f, (Math.random() < 0.6));
        addDerelict(system, americaAF2, "ot_M4Sherman_Hull_standard", ShipRecoverySpecial.ShipCondition.BATTERED, 350f, (Math.random() < 0.6));
        addDerelict(system, americaAF2, "ot_M2Medium_Hull_standard", ShipRecoverySpecial.ShipCondition.WRECKED, 240f, (Math.random() < 0.6));
        addDerelict(system, americaAF2, "ot_M4Sherman_Hull_standard", ShipRecoverySpecial.ShipCondition.BATTERED, 125f, (Math.random() < 0.6));

        //asteroid belt1 ring
        system.addAsteroidBelt(americaStar, 1250, asteroidBelt1Dist, 800f, 250f, 250f, Terrain.ASTEROID_BELT, "Columbus's Belt");
        system.addRingBand(americaStar, "misc", "rings_asteroids0", 256f, 1, Color.gray, 256f, asteroidBelt1Dist - 200, 250f);
        system.addRingBand(americaStar, "misc", "rings_asteroids0", 256f, 2, Color.gray, 256f, asteroidBelt1Dist, 350f);
        system.addRingBand(americaStar, "misc", "rings_asteroids0", 256f, 0, Color.gray, 256f, asteroidBelt1Dist + 200, 400f);

        //asteroid belt2 ring
        system.addAsteroidBelt(americaStar, 1000, asteroidBelt2Dist, 700f, 300f, 600f, Terrain.ASTEROID_BELT, "Strip Belt");
        system.addRingBand(americaStar, "misc", "rings_asteroids0", 220f, 3, Color.gray, 220f, asteroidBelt2Dist - 180, 300f);
        system.addRingBand(americaStar, "misc", "rings_asteroids0", 220f, 0, Color.gray, 220f, asteroidBelt2Dist, 450f);
        system.addRingBand(americaStar, "misc", "rings_asteroids0", 120f, 0, Color.gray, 120f, asteroidBelt2Dist + 120, 500f);

        // Relays
        SectorEntityToken americaStar_relay = system.addCustomEntity("americaStar_relay", // unique id
                "American Relay", // name - if null, defaultName from custom_entities.json will be used
                "comm_relay", // type of object, defined in custom_entities.json
                "USA"); // faction
        americaStar_relay.setCircularOrbitPointingDown(americaStar, MathUtils.getRandomNumberInRange(0f, 360f), relay1Dist, 520);

        SectorEntityToken americaStar_buoy = system.addCustomEntity("americaStar_buoy", // unique id
                "American Nav Buoy", // name - if null, defaultName from custom_entities.json will be used
                "nav_buoy", // type of object, defined in custom_entities.json
                "USA"); // faction
        americaStar_buoy.setCircularOrbitPointingDown(americaStar, MathUtils.getRandomNumberInRange(0f, 360f), buoy1Dist, 520);

        SectorEntityToken americaStar_sensor = system.addCustomEntity("americaStar_sensor", // unique id
                "American Sensor Array", // name - if null, defaultName from custom_entities.json will be used
                "sensor_array", // type of object, defined in custom_entities.json
                "USA"); // faction
        americaStar_sensor.setCircularOrbitPointingDown(americaStar, MathUtils.getRandomNumberInRange(0f, 360f), sensor1Dist, 520);

        // New Providence Planet
        PlanetAPI providence = system.addPlanet("u_providence", americaStar, "New Providence", "frozen", 360 * (float) Math.random(), 175f, providenceDist, 390f);
        providence.setCustomDescriptionId("usa_starofamerica_providence"); //reference descriptions.csv

        providence.getMarket().addCondition(Conditions.COLD);
        providence.getMarket().addCondition(Conditions.THIN_ATMOSPHERE);
        providence.getMarket().addCondition(Conditions.POOR_LIGHT);
        providence.getMarket().addCondition(Conditions.RUINS_WIDESPREAD);
        providence.getMarket().addCondition(Conditions.ORE_MODERATE);

        addDerelict(system, providence, "kite_pirates_Raider", ShipRecoverySpecial.ShipCondition.BATTERED, 280f, (Math.random() < 0.6));
        addDerelict(system, providence, "ot_M2a2Light_Hull_standard", ShipRecoverySpecial.ShipCondition.BATTERED, 230f, (Math.random() < 0.6));
        addDerelict(system, providence, "ot_M2a2Light_Hull_standard", ShipRecoverySpecial.ShipCondition.BATTERED, 200f, (Math.random() < 0.6));
        addDerelict(system, providence, "kite_pirates_Raider", ShipRecoverySpecial.ShipCondition.WRECKED, 250f, (Math.random() < 0.6));


        // Eisenhowar
        PlanetAPI eisen = system.addPlanet("u_eisenhowar", americaStar, "Blacksite Eisenhowar", "barren-bombarded", 360f * (float) Math.random(), 115f, eisenDist, 620f);
        eisen.setCustomDescriptionId("usa_starofamerica_eisenhowar"); //reference descriptions.csv
       // PlanetConditionGenerator.generateConditionsForPlanet(eisen, StarAge.AVERAGE);
        eisen.getMarket().addCondition(Conditions.NO_ATMOSPHERE);
        eisen.getMarket().addCondition(Conditions.VERY_COLD);
        eisen.getMarket().addCondition(Conditions.DARK);
        eisen.getMarket().addCondition(Conditions.VOLATILES_TRACE);
        eisen.getMarket().addCondition(Conditions.LOW_GRAVITY);
        eisen.getMarket().addCondition(Conditions.RUINS_VAST);

        SectorEntityToken eF1 = system.addTerrain(Terrain.ASTEROID_FIELD,
                new AsteroidFieldTerrainPlugin.AsteroidFieldParams(50f, 250f, 15, 40, 2f, 10f, "Asteroids Field"));
        eF1.setCircularOrbit(eisen, 360f * (float) Math.random(), 1, 45);

        // Eisen debris fields.
        DebrisFieldTerrainPlugin.DebrisFieldParams params3 = new DebrisFieldTerrainPlugin.DebrisFieldParams(
                200, // field radius - should not go above 1000 for performance reasons
                1.5f, // density, visual - affects number of debris pieces
                10000000f, // duration in days
                0f); // days the field will keep generating glowing pieces
        params3.source = DebrisFieldTerrainPlugin.DebrisFieldSource.MIXED;
        params3.baseSalvageXP = 500; // base XP for scavenging in field
        SectorEntityToken eisenDebris1 = Misc.addDebrisField(system, params3, StarSystemGenerator.random);
        eisenDebris1.setSensorProfile(1000f);
        eisenDebris1.setDiscoverable(true);
        eisenDebris1.setCircularOrbit(eisen, 360f * (float) Math.random(), 0, 120f);
        eisenDebris1.setId("eisenDebris1");

        SectorEntityToken scrap1 = DerelictThemeGenerator.addSalvageEntity(system, Entities.SUPPLY_CACHE, Factions.DERELICT);
        scrap1.setId("eisen_scrap1");
        scrap1.setCircularOrbit(eisen, 360f * (float) Math.random(), 150, 25);
        Misc.setDefenderOverride(scrap1, new DefenderDataOverride(Factions.DERELICT, 1, 2, 5));
        scrap1.setDiscoverable(Boolean.TRUE);

        SectorEntityToken scrap2 = DerelictThemeGenerator.addSalvageEntity(system, Entities.SUPPLY_CACHE, Factions.DERELICT);
        scrap2.setId("eisen_scrap2");
        scrap2.setCircularOrbit(eisen, 360f * (float) Math.random(), 115, 10);
        Misc.setDefenderOverride(scrap2, new DefenderDataOverride(Factions.DERELICT, 1, 3, 8));
        scrap2.setDiscoverable(Boolean.TRUE);

        addDerelict(system, eisen, "ot_M4Sherman_Hull_standard", ShipRecoverySpecial.ShipCondition.WRECKED, 160f, (Math.random() < 0.6));
        addDerelict(system, eisen, "ot_M4Sherman_Hull_standard", ShipRecoverySpecial.ShipCondition.BATTERED, 20f, (Math.random() < 0.6));
        addDerelict(system, eisen, "ot_M4Sherman_Hull_standard", ShipRecoverySpecial.ShipCondition.WRECKED, 60f, (Math.random() < 0.6));


        // Dustybell
        PlanetAPI dusty = system.addPlanet("u_dusty", americaStar, "Dustybell", "desert", 360f * (float) Math.random(), 120f, dustyDist, 200f);
        dusty.setCustomDescriptionId("usa_starofamerica_dustybell"); //reference descriptions.csv
        //PlanetConditionGenerator.generateConditionsForPlanet(dusty, StarAge.AVERAGE);
        dusty.getMarket().addCondition(Conditions.HABITABLE);
        dusty.getMarket().addCondition(Conditions.HOT);
        dusty.getMarket().addCondition(Conditions.DECIVILIZED_SUBPOP);
        dusty.getMarket().addCondition(Conditions.RUINS_SCATTERED);
        dusty.getMarket().addCondition(Conditions.ORE_RICH);
        dusty.getMarket().addCondition(Conditions.RARE_ORE_ABUNDANT);

        //Franklin Capital Planet
        PlanetAPI franklin;
        franklin = system.addPlanet("u_franklin",
                americaStar,
                "Franklin",
                "terran",
                360f * (float) Math.random(),
                200f,
                franklinDist,
                365f);

        franklin.setCustomDescriptionId("usa_starofamerica_franklin"); //reference descriptions.csv

        MarketAPI franklin_market = addMarketplace("USA", franklin, null,
                "Franklin",
                10,
                Arrays.asList(
                        Conditions.TERRAN,
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

                        Industries.COMMERCE,
                        Industries.MINING,
                        Industries.LIGHTINDUSTRY,

                        Industries.STARFORTRESS_HIGH,
                        Industries.HEAVYBATTERIES,
                        Industries.HIGHCOMMAND

                ),
                0.18f,
                false,
                false);

        franklin_market.getIndustry(Industries.POPULATION).setAICoreId(Commodities.BETA_CORE);
        franklin_market.getIndustry(Industries.MEGAPORT).setAICoreId(Commodities.BETA_CORE);
        franklin_market.getIndustry(Industries.WAYSTATION).setAICoreId(Commodities.BETA_CORE);

        franklin_market.getIndustry(Industries.COMMERCE).setAICoreId(Commodities.BETA_CORE);
        franklin_market.getIndustry(Industries.MINING).setAICoreId(Commodities.BETA_CORE);
        franklin_market.getIndustry(Industries.LIGHTINDUSTRY).setAICoreId(Commodities.BETA_CORE);

        franklin_market.getIndustry(Industries.STARFORTRESS_HIGH).setAICoreId(Commodities.BETA_CORE);
        franklin_market.getIndustry(Industries.HEAVYBATTERIES).setAICoreId(Commodities.BETA_CORE);
        franklin_market.getIndustry(Industries.HIGHCOMMAND).setAICoreId(Commodities.BETA_CORE);

        // Appalax Industrial Center
        PlanetAPI appalax;
        appalax = system.addPlanet("u_appalax",
                americaStar,
                "Appalax",
                "terran",
                360f * (float) Math.random(),
                220f,
                appalaxDist,
                400f);

        appalax.setCustomDescriptionId("usa_starofamerica_appalax"); //reference descriptions.csv

        MarketAPI appalax_market = addMarketplace("USA", appalax, null,
                "Appalax",
                8,
                Arrays.asList(
                        Conditions.TERRAN,
                        Conditions.HABITABLE,
                        Conditions.POLLUTION,
                        Conditions.TECTONIC_ACTIVITY,

                        Conditions.POPULATION_8,
                        Conditions.INDUSTRIAL_POLITY,

                        Conditions.ORE_ULTRARICH,
                        Conditions.RARE_ORE_RICH,
                        Conditions.VOLATILES_TRACE,
                        Conditions.FARMLAND_POOR,

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

        appalax_market.getIndustry(Industries.POPULATION).setAICoreId(Commodities.BETA_CORE);
        appalax_market.getIndustry(Industries.MEGAPORT).setAICoreId(Commodities.BETA_CORE);
        appalax_market.getIndustry(Industries.WAYSTATION).setAICoreId(Commodities.BETA_CORE);

        appalax_market.getIndustry(Industries.MINING).setAICoreId(Commodities.BETA_CORE);
        appalax_market.getIndustry(Industries.REFINING).setAICoreId(Commodities.BETA_CORE);

        appalax_market.addIndustry(Industries.ORBITALWORKS, Collections.singletonList(Items.PRISTINE_NANOFORGE));
        appalax_market.getIndustry(Industries.ORBITALWORKS).setAICoreId(Commodities.ALPHA_CORE);

        appalax_market.getIndustry(Industries.FUELPROD).setAICoreId(Commodities.BETA_CORE);

        appalax_market.getIndustry(Industries.STARFORTRESS_MID).setAICoreId(Commodities.BETA_CORE);
        appalax_market.getIndustry(Industries.HEAVYBATTERIES).setAICoreId(Commodities.BETA_CORE);
        appalax_market.getIndustry(Industries.MILITARYBASE).setAICoreId(Commodities.BETA_CORE);

        // Liberty Reach Breadbasket
        PlanetAPI liberty;
        liberty = system.addPlanet("u_liberty",
                americaStar,
                "Liberty Reach",
                "terran",
                360f * (float) Math.random(),
                220f,
                libertyDist,
                400f);

        liberty.setCustomDescriptionId("usa_starofamerica_liberty"); //reference descriptions.csv

        MarketAPI liberty_market = addMarketplace("USA", liberty, null,
                "Liberty Reach",
                6,
                Arrays.asList(
                        Conditions.TERRAN,
                        Conditions.HABITABLE,

                        Conditions.POPULATION_6,
                        Conditions.RURAL_POLITY,

                        Conditions.ORE_MODERATE,
                        Conditions.VOLATILES_PLENTIFUL,
                        Conditions.FARMLAND_BOUNTIFUL,
                        Conditions.ORGANICS_ABUNDANT,

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
                        Industries.FUELPROD,

                        Industries.STARFORTRESS_MID,
                        Industries.HEAVYBATTERIES,
                        Industries.MILITARYBASE

                ),
                0.19f,
                false,
                false);

        liberty_market.getIndustry(Industries.POPULATION).setAICoreId(Commodities.BETA_CORE);
        liberty_market.getIndustry(Industries.SPACEPORT).setAICoreId(Commodities.BETA_CORE);
        liberty_market.getIndustry(Industries.WAYSTATION).setAICoreId(Commodities.BETA_CORE);

        liberty_market.getIndustry(Industries.MINING).setAICoreId(Commodities.BETA_CORE);

        liberty_market.addIndustry(Industries.FARMING, Collections.singletonList(Items.SOIL_NANITES));
        liberty_market.getIndustry(Industries.FARMING).setAICoreId(Commodities.ALPHA_CORE);

        liberty_market.getIndustry(Industries.LIGHTINDUSTRY).setAICoreId(Commodities.BETA_CORE);
        liberty_market.getIndustry(Industries.FUELPROD).setAICoreId(Commodities.BETA_CORE);

        liberty_market.getIndustry(Industries.STARFORTRESS_MID).setAICoreId(Commodities.BETA_CORE);
        liberty_market.getIndustry(Industries.HEAVYBATTERIES).setAICoreId(Commodities.BETA_CORE);
        liberty_market.getIndustry(Industries.MILITARYBASE).setAICoreId(Commodities.BETA_CORE);

        // Eagle's Nest
        PlanetAPI eagle;
        eagle = system.addPlanet("u_eagle",
                americaStar,
                "Eagle's Nest",
                "barren",
                360f * (float) Math.random(),
                75f,
                eagleDist,
                550f);

        eagle.setCustomDescriptionId("usa_starofamerica_eagle"); //reference descriptions.csv

        MarketAPI eagle_market = addMarketplace("USA", eagle, null,
                "Eagle's Nest",
                4,
                Arrays.asList(
                        Conditions.NO_ATMOSPHERE,
                        Conditions.LOW_GRAVITY,
                        Conditions.COLD,

                        Conditions.POPULATION_4,
                        Conditions.OUTPOST,

                        Conditions.ORE_MODERATE,
                        Conditions.VOLATILES_ABUNDANT,

                        Conditions.STEALTH_MINEFIELDS
                ),
                Arrays.asList(
                        Submarkets.GENERIC_MILITARY,
                        Submarkets.SUBMARKET_STORAGE,
                        Submarkets.SUBMARKET_BLACK
                ),
                Arrays.asList(
                        Industries.POPULATION,
                        Industries.SPACEPORT,
                        Industries.WAYSTATION,

                        Industries.MINING,
                        Industries.HEAVYINDUSTRY,
                        Industries.FUELPROD,

                       // Industries.STARFORTRESS_MID,
                        Industries.GROUNDDEFENSES,
                        Industries.PATROLHQ

                ),
                0.20f,
                false,
                true);

        eagle_market.getIndustry(Industries.POPULATION).setAICoreId(Commodities.BETA_CORE);
        eagle_market.getIndustry(Industries.SPACEPORT).setAICoreId(Commodities.BETA_CORE);
        eagle_market.getIndustry(Industries.WAYSTATION).setAICoreId(Commodities.BETA_CORE);

        eagle_market.getIndustry(Industries.MINING).setAICoreId(Commodities.BETA_CORE);
        eagle_market.getIndustry(Industries.HEAVYINDUSTRY).setAICoreId(Commodities.BETA_CORE);
        eagle_market.getIndustry(Industries.FUELPROD).setAICoreId(Commodities.BETA_CORE);

        //eagle_market.getIndustry(Industries.STARFORTRESS_MID).setAICoreId(Commodities.BETA_CORE);
        eagle_market.getIndustry(Industries.GROUNDDEFENSES).setAICoreId(Commodities.BETA_CORE);
        eagle_market.getIndustry(Industries.PATROLHQ).setAICoreId(Commodities.BETA_CORE);

        addDerelict(system, eagle, "ot_M2a2Light_Hull_standard", ShipRecoverySpecial.ShipCondition.WRECKED, 250f, (Math.random() < 0.6));
        addDerelict(system, eagle, "ot_M2a2Light_Hull_standard", ShipRecoverySpecial.ShipCondition.BATTERED, 250f, (Math.random() < 0.6));
        addDerelict(system, eagle, "ot_M2a2Light_Hull_standard", ShipRecoverySpecial.ShipCondition.BATTERED, 250f, (Math.random() < 0.6));


        // Monroe Station
        SectorEntityToken monroe = system.addCustomEntity("u_monroe", "Monroe Commerce Station", "station_hightech2", "USA");
        monroe.setCircularOrbitPointingDown(americaStar, 360f * (float) Math.random(), monroeDist, 320f);
        monroe.setCustomDescriptionId("usa_starofamerica_monroe");
        MarketAPI monroe_market = addMarketplace("USA", monroe, null,
                "Monroe Commerce Station",
                4,
                Arrays.asList(
                        Conditions.POPULATION_4,
                        Conditions.NO_ATMOSPHERE,
                        Conditions.OUTPOST,

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

                        Industries.COMMERCE,
                        Industries.ORBITALWORKS,

                        Industries.HEAVYBATTERIES,
                        Industries.PATROLHQ
                ),
                0.15f,
                true,
                false);

        monroe_market.getIndustry(Industries.POPULATION).setAICoreId(Commodities.BETA_CORE);
        monroe_market.getIndustry(Industries.MEGAPORT).setAICoreId(Commodities.BETA_CORE);
        monroe_market.getIndustry(Industries.WAYSTATION).setAICoreId(Commodities.BETA_CORE);

        monroe_market.getIndustry(Industries.COMMERCE).setAICoreId(Commodities.BETA_CORE);
        monroe_market.getIndustry(Industries.ORBITALWORKS).setAICoreId(Commodities.BETA_CORE);

        monroe_market.getIndustry(Industries.HEAVYBATTERIES).setAICoreId(Commodities.BETA_CORE);
        monroe_market.getIndustry(Industries.PATROLHQ).setAICoreId(Commodities.BETA_CORE);

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


