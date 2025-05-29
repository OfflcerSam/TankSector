package org.officersam.tanks.scripts.world.systems;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.DerelictShipEntityPlugin;
import com.fs.starfarer.api.impl.campaign.ids.*;
import com.fs.starfarer.api.impl.campaign.procgen.PlanetConditionGenerator;
import com.fs.starfarer.api.impl.campaign.procgen.StarAge;
import com.fs.starfarer.api.impl.campaign.procgen.themes.BaseThemeGenerator;
import com.fs.starfarer.api.impl.campaign.procgen.themes.SalvageSpecialAssigner;
import com.fs.starfarer.api.impl.campaign.terrain.AsteroidFieldTerrainPlugin;
import com.fs.starfarer.api.impl.campaign.procgen.themes.DerelictThemeGenerator;
import com.fs.starfarer.api.impl.campaign.rulecmd.salvage.special.ShipRecoverySpecial;
import com.fs.starfarer.api.impl.campaign.procgen.DefenderDataOverride;
import com.fs.starfarer.api.util.Misc;
import org.lazywizard.lazylib.MathUtils;

import java.awt.*;
import java.util.Arrays;

import static org.officersam.tanks.scripts.world.systems.ot_addmarket.addMarketplace;

public class GER_SonneDeutschlands {
    // Star Orbits
    //asteroids
    final float asteroids1Dist = 7200f;
    final float stable1Dist = 4000f;
    final float asteroidBelt1Dist = 2050f;
    final float asteroidBelt2Dist = 9200f;
    //relays
    final float buoy1Dist = 4550f;
    final float relay1Dist = 5650f;
    final float sensor1Dist = 9350f;
    //planets
    final float gksDist = 9200f;
    final float vengusDist = 2900f;
    final float doggenDist = 4200f;
    final float germaniaDist = 6800f;
    final float meeresresortDist = 5000f;
    final float furkDist = 9800f;
    //jumps
    final float jumpInnerDist = 3050f;
    final float jumpOuterDist = 9300;
    final float jumpFringeDist = 16700f;


    public void generate(SectorAPI sector) {

        StarSystemAPI system = sector.createStarSystem("Sonne Deutschlands");
        system.getLocation().set(-5000, -6000);

        system.setBackgroundTextureFilename("graphics/backgrounds/StarofAmerica_background.jpg");

        //praise the sun
        PlanetAPI germanStar = system.initStar("SonneDeutschlands", "star_yellow", 800f, 700, 10, 0.5f, 3f);
        system.setLightColor(new Color(255, 245, 225));

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
        SectorEntityToken americaAF1 = system.addTerrain(Terrain.ASTEROID_FIELD,
                new AsteroidFieldTerrainPlugin.AsteroidFieldParams(250f, 350f, 10, 24, 4f, 16f, "Asteroids Field"));
        americaAF1.setCircularOrbit(germanStar, 150, asteroids1Dist, 220);

        SectorEntityToken stableLoc1 = system.addCustomEntity("german_stableloc_1", "Stable Location", "stable_location", Factions.NEUTRAL);
        stableLoc1.setCircularOrbit(germanStar, MathUtils.getRandomNumberInRange(0f, 360f), stable1Dist, 520);

        addDerelict(system, americaAF1, "wolf_d_pirates_Attack", ShipRecoverySpecial.ShipCondition.BATTERED, 270f, (Math.random() < 0.6));
        addDerelict(system, americaAF1, "ot_M4Sherman_Hull_standard", ShipRecoverySpecial.ShipCondition.BATTERED, 200f, (Math.random() < 0.6));
        addDerelict(system, americaAF1, "wolf_d_pirates_Attack", ShipRecoverySpecial.ShipCondition.BATTERED, 240f, (Math.random() < 0.6));
        addDerelict(system, americaAF1, "ot_Pz2FLight_Hull_standard", ShipRecoverySpecial.ShipCondition.BATTERED, 270f, (Math.random() < 0.6));
        addDerelict(system, americaAF1, "ot_Pz2FLight_Hull_standard", ShipRecoverySpecial.ShipCondition.WRECKED, 220f, (Math.random() < 0.6));
        addDerelict(system, americaAF1, "ot_Pz2FLight_Hull_standard", ShipRecoverySpecial.ShipCondition.WRECKED, 210f, (Math.random() < 0.6));
        addDerelict(system, americaAF1, "lasher_PD", ShipRecoverySpecial.ShipCondition.BATTERED, 125f, (Math.random() < 0.6));

        //asteroid belt1 ring
        system.addAsteroidBelt(germanStar, 1000, asteroidBelt1Dist, 800, 250, 400, Terrain.ASTEROID_BELT, "Inner Band");
        system.addRingBand(germanStar, "misc", "rings_asteroids0", 256f, 3, Color.gray, 256f, asteroidBelt1Dist - 200, 250f);
        system.addRingBand(germanStar, "misc", "rings_asteroids0", 256f, 0, Color.gray, 256f, asteroidBelt1Dist, 350f);
        system.addRingBand(germanStar, "misc", "rings_asteroids0", 256f, 2, Color.gray, 256f, asteroidBelt1Dist + 200, 400f);

        //asteroid belt2 ring
        system.addAsteroidBelt(germanStar, 1000, asteroidBelt2Dist, 800, 250, 400, Terrain.ASTEROID_BELT, "Outer Band");
        system.addRingBand(germanStar, "misc", "rings_asteroids0", 256f, 3, Color.gray, 256f, asteroidBelt2Dist - 200, 250f);
        system.addRingBand(germanStar, "misc", "rings_asteroids0", 256f, 0, Color.gray, 256f, asteroidBelt1Dist, 350f);
        system.addRingBand(germanStar, "misc", "rings_asteroids0", 256f, 1, Color.gray, 256f, asteroidBelt2Dist + 200, 400f);

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

        // furk Planet
        PlanetAPI furk = system.addPlanet("g_furk", germanStar, "Furk", "frozen", 360 * (float) Math.random(), 190f, furkDist, 390f);
        furk.setCustomDescriptionId("ger_sonnedeutschlands_furk"); //reference descriptions.csv
        furk.getMarket().addCondition(Conditions.RUINS_WIDESPREAD);
        furk.getMarket().addCondition(Conditions.VERY_COLD);
        furk.getMarket().addCondition(Conditions.DECIVILIZED);
        furk.getMarket().addCondition(Conditions.DARK);
        furk.getMarket().addCondition(Conditions.ORE_ULTRARICH);
        furk.getMarket().addCondition(Conditions.RARE_ORE_MODERATE);

        addDerelict(system, furk, "kite_pirates_Raider", ShipRecoverySpecial.ShipCondition.BATTERED, 280f, (Math.random() < 0.6));
        addDerelict(system, furk, "ot_Pz2FLight_Hull_standard", ShipRecoverySpecial.ShipCondition.BATTERED, 230f, (Math.random() < 0.6));
        addDerelict(system, furk, "ot_Pz2FLight_Hull_standard", ShipRecoverySpecial.ShipCondition.BATTERED, 200f, (Math.random() < 0.6));
        addDerelict(system, furk, "kite_pirates_Raider", ShipRecoverySpecial.ShipCondition.WRECKED, 250f, (Math.random() < 0.6));

        SectorEntityToken scrap1 = DerelictThemeGenerator.addSalvageEntity(system, Entities.SUPPLY_CACHE, Factions.DERELICT);
        scrap1.setId("furk_scrap4");
        scrap1.setCircularOrbit(furk, 105, 195, 150);
        Misc.setDefenderOverride(scrap1, new DefenderDataOverride(Factions.DERELICT, 0, 0, 0));
        scrap1.setDiscoverable(Boolean.TRUE);

        // Abadon
        PlanetAPI abadon = system.addPlanet("g_doggen", germanStar, "Doggen", "arid", 360f * (float) Math.random(), 320f, doggenDist, 380f);
        abadon.setCustomDescriptionId("ger_sonnedeutschlands_doggen"); //reference descriptions.csv
        PlanetConditionGenerator.generateConditionsForPlanet(abadon, StarAge.AVERAGE);

        // Vengus
        PlanetAPI vengus = system.addPlanet("g_vengus", germanStar, "Vengus", "desert", 360f * (float) Math.random(), 120f, vengusDist, 200f);
        vengus.setCustomDescriptionId("ger_sonnedeutschlands_vengus"); //reference descriptions.csv
        PlanetConditionGenerator.generateConditionsForPlanet(vengus, StarAge.AVERAGE);

        // Germania
        PlanetAPI germania;
        germania = system.addPlanet("g_germania",
                germanStar,
                "Planet Deutschland",
                "terran",
                360f * (float) Math.random(),
                220f,
                germaniaDist,
                365f);

        germania.setCustomDescriptionId("ger_starofamerica_germania"); //reference descriptions.csv

        MarketAPI germania_market = addMarketplace("GER", germania, null,
                "Planet Deutschland",
                10,
                Arrays.asList(
                        Conditions.POPULATION_10,
                        Conditions.URBANIZED_POLITY,
                        Conditions.ORE_ABUNDANT,
                        Conditions.RARE_ORE_ABUNDANT,
                        Conditions.FARMLAND_BOUNTIFUL,
                        Conditions.HABITABLE,
                        Conditions.ORGANIZED_CRIME,
                        Conditions.TERRAN,
                        Conditions.REGIONAL_CAPITAL,
                        Conditions.VOLATILES_ABUNDANT,
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
                        Industries.MINING,
                        Industries.STARFORTRESS_HIGH,
                        Industries.HEAVYBATTERIES,
                        Industries.HEAVYINDUSTRY,
                        Industries.LIGHTINDUSTRY,
                        Industries.HIGHCOMMAND,
                        Industries.WAYSTATION
                ),
                0.17f,
                false,
                false);

        //amerierra_market.addIndustry(Industries.ORBITALWORKS, Collections.singletonList(Items.PRISTINE_NANOFORGE));
        germania_market.getIndustry(Industries.HIGHCOMMAND).setAICoreId(Commodities.ALPHA_CORE);
        germania_market.getIndustry(Industries.STARFORTRESS_HIGH).setAICoreId(Commodities.ALPHA_CORE);
        germania_market.getIndustry(Industries.MEGAPORT).setAICoreId(Commodities.ALPHA_CORE);
        germania_market.getIndustry(Industries.MINING).setAICoreId(Commodities.ALPHA_CORE);
        germania_market.getIndustry(Industries.LIGHTINDUSTRY).setAICoreId(Commodities.ALPHA_CORE);
        germania_market.getIndustry(Industries.HEAVYINDUSTRY).setAICoreId(Commodities.ALPHA_CORE);
        //amerierra_market.getIndustry(Industries.ORBITALWORKS).setAICoreId(Commodities.ALPHA_CORE);
        germania_market.getIndustry(Industries.POPULATION).setAICoreId(Commodities.BETA_CORE);
        germania_market.getIndustry(Industries.WAYSTATION).setAICoreId(Commodities.GAMMA_CORE);

        // meeresresort
        PlanetAPI meeresresort;
        meeresresort = system.addPlanet("g_meeresresort",
                germanStar,
                "Meeresresort",
                "water",
                360f * (float) Math.random(),
                220f,
                meeresresortDist,
                360f);

        meeresresort.setCustomDescriptionId("ger_sonnedeutschlands_meeresresort"); //reference descriptions.csv

        MarketAPI meeresresort_market = addMarketplace("GER", meeresresort, null,
                "Meeresresort",
                8,
                Arrays.asList(
                        Conditions.POPULATION_8,
                        Conditions.ORE_RICH,
                        Conditions.WATER,
                        Conditions.INDUSTRIAL_POLITY,
                        Conditions.RARE_ORE_RICH,
                        Conditions.VOLATILES_PLENTIFUL,
                        Conditions.VOLTURNIAN_LOBSTER_PENS,
                        Conditions.HABITABLE,
                        Conditions.TERRAN,
                        Conditions.REGIONAL_CAPITAL,
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
                        Industries.MINING,
                        Industries.REFINING,
                        Industries.HEAVYINDUSTRY,
                        Industries.AQUACULTURE,
                        Industries.FUELPROD,
                        Industries.STARFORTRESS_MID,
                        Industries.HEAVYBATTERIES,
                        Industries.HIGHCOMMAND,
                        Industries.WAYSTATION
                ),
                0.18f,
                false,
                true);


        meeresresort_market.getIndustry(Industries.HIGHCOMMAND).setAICoreId(Commodities.ALPHA_CORE);
        meeresresort_market.getIndustry(Industries.STARFORTRESS_MID).setAICoreId(Commodities.ALPHA_CORE);
        meeresresort_market.getIndustry(Industries.MEGAPORT).setAICoreId(Commodities.ALPHA_CORE);
        meeresresort_market.getIndustry(Industries.HEAVYINDUSTRY).setAICoreId(Commodities.ALPHA_CORE);
        meeresresort_market.getIndustry(Industries.MINING).setAICoreId(Commodities.ALPHA_CORE);
        meeresresort_market.getIndustry(Industries.FUELPROD).setAICoreId(Commodities.ALPHA_CORE);
        meeresresort_market.getIndustry(Industries.POPULATION).setAICoreId(Commodities.BETA_CORE);
        meeresresort_market.getIndustry(Industries.WAYSTATION).setAICoreId(Commodities.GAMMA_CORE);

        // GER Kriegsmarine Sternfestung
        SectorEntityToken GKS = system.addCustomEntity("g_gks", "GGSR Kriegsmarine Sternfestung", "station_hightech2", "GER");
        GKS.setCircularOrbitPointingDown(germanStar, 0, gksDist, 410f);
        GKS.setCustomDescriptionId("ger_sonnedeutschlands_gks");
        MarketAPI GKS_market = addMarketplace("GER", GKS, null,
                "GGSR Kriegsmarine Sternfestung",
                4,
                Arrays.asList(
                        Conditions.POPULATION_4,
                        Conditions.NO_ATMOSPHERE,
                        Conditions.OUTPOST,
                        Conditions.AI_CORE_ADMIN
                ),
                Arrays.asList(
                        Submarkets.GENERIC_MILITARY,
                        Submarkets.SUBMARKET_STORAGE,
                        Submarkets.SUBMARKET_BLACK
                ),
                Arrays.asList(
                        Industries.POPULATION,
                        Industries.SPACEPORT,
                        Industries.BATTLESTATION_HIGH,
                        Industries.HEAVYBATTERIES,
                        Industries.MILITARYBASE,
                        Industries.ORBITALWORKS,
                        Industries.WAYSTATION
                ),
                0.20f,
                false,
                true);

        GKS_market.getIndustry(Industries.MILITARYBASE).setAICoreId(Commodities.BETA_CORE);
        GKS_market.getIndustry(Industries.BATTLESTATION_HIGH).setAICoreId(Commodities.ALPHA_CORE);
        GKS_market.getIndustry(Industries.ORBITALWORKS).setAICoreId(Commodities.BETA_CORE);

        addDerelict(system, GKS, "ot_Pz2FLight_Hull_standard", ShipRecoverySpecial.ShipCondition.WRECKED, 250f, (Math.random() < 0.6));
        addDerelict(system, GKS, "ot_Pz2FLight_Hull_standard", ShipRecoverySpecial.ShipCondition.BATTERED, 250f, (Math.random() < 0.6));
        addDerelict(system, GKS, "ot_Pz2FLight_Hull_standard", ShipRecoverySpecial.ShipCondition.BATTERED, 250f, (Math.random() < 0.6));


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