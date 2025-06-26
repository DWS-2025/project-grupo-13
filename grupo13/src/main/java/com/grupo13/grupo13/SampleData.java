package com.grupo13.grupo13;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Blob;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.grupo13.grupo13.model.Armor;
import com.grupo13.grupo13.model.Character;
import com.grupo13.grupo13.model.User;
import com.grupo13.grupo13.model.Weapon;
import com.grupo13.grupo13.repository.ArmorRepository;
import com.grupo13.grupo13.repository.CharacterRepository;
import com.grupo13.grupo13.repository.UserRepository;
import com.grupo13.grupo13.repository.WeaponRepository;
import jakarta.annotation.PostConstruct;

@Service
public class SampleData {
        private static final Path SOURCE = Paths.get("src/main/resources/init-images");
        private static final Path TARGET = Paths.get("backups/characters");

        private final CharacterRepository characterRepository;

        // attributes
        @Autowired
        private UserRepository userRepository;

        @Autowired
        private WeaponRepository weaponRepository;

        @Autowired
        private ArmorRepository armorRepository;
        
        @Autowired
	private PasswordEncoder passwordEncoder;

        SampleData(CharacterRepository characterRepository) {
                this.characterRepository = characterRepository;
        }

        public Blob localImageToBlob(String localFilePath) {
                File imageFile = new File(localFilePath);
                if (imageFile.exists()) {
                        try {
                                return BlobProxy.generateProxy(imageFile.toURI().toURL().openStream(), imageFile.length());
                        } catch (IOException e) {
                                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error at processing the image");
                        }
                }
                return null;
        }

        // loads the default items
        @PostConstruct
        public void init() throws IOException {

                //u1
                User user1 = new User("Lotti", passwordEncoder.encode("lottipico"),10000, "USER");
                userRepository.save(user1);

                //u2
                User user2 = new User("Raimundo Garcia Fernandez", passwordEncoder.encode("contraseña"),10000, "USER");
                userRepository.save(user2);

                //u3
                User user3 = new User("juan", passwordEncoder.encode("juan"),10000, "USER");
                userRepository.save(user3);

                //u4
                User user4 = new User("admin", passwordEncoder.encode("adminpass"),2147483647, "USER", "ADMIN");
                userRepository.save(user4);

                //u5
                User user5 = new User("user", passwordEncoder.encode("pass"),10000, "USER");
                userRepository.save(user5);

                //c1
                Character character1 = new Character("Spinning Cat", "Uiia Io Uuiia Iu", "uiiaiouuiiaiu-Lotti.jpg");
                characterRepository.save(character1);
                user1.setCharacter(character1);
                character1.setUser(user1);
                userRepository.save(user1);

                //c2
                Character character2 = new Character("In a village of La Mancha, the name of which I have no desire to call to mind, there lived not long since one of those gentlemen that keep a lance in the lance rack, an old buckler, a lean hack, and a greyhound for coursing.", "First chapter", "jd-Raimundo Garcia Fernandez.jpg");
                characterRepository.save(character2);
                user2.setCharacter(character2);
                character2.setUser(user2);
                userRepository.save(user2);

                //c3
                Character character3 = new Character("juan", "juan", "juan-juan.jpg");
                characterRepository.save(character3);
                user3.setCharacter(character3);
                character3.setUser(user3);
                userRepository.save(user3);

                //w1.png
                Weapon weapon = new Weapon("Wood Sword", "A simple blade carved from sturdy oak, this sword is the hallmark of novice adventurers. Though not the sharpest or strongest, it is reliable in a pinch and a good starting weapon for those just beginning their journey.",
                30, 10, 25);    
                weapon.setimageFile(localImageToBlob("images/imp_imgs/w1.png"));
                weaponRepository.save(weapon);

                //w2.png
                Weapon weapon2 = new Weapon("Iron Longsword", "Forged from tempered iron, this longsword offers a great balance between durability and usability. Designed for consistent damage and solid defense, its a favorite among mercenaries and town guards. Reliable in most combats, it rarely disappoints.", 
                10, 15, 50);
                weapon2.setimageFile(localImageToBlob("images/imp_imgs/w2.png"));
                weaponRepository.save(weapon2);

                //w3.png
                Weapon weapon3 = new Weapon("Golden Rapier", "Crafted more for flair than function, the Golden Rapier gleams with elegance. While its golden blade dazzles onlookers, it lacks the durability of steel weapons. Best suited for duels, nobility, or ceremonial combat.", 
                40, 20, 10);
                weapon3.setimageFile(localImageToBlob("images/imp_imgs/w3.png"));
                weaponRepository.save(weapon3);

                //w4.png
                Weapon weapon4 = new Weapon("Dragon Fang blade", "This mystical blade was forged from the fang of an elder dragon in the last age. It radiates a faint, otherworldly energy, and the runes carved into its edge glow faintly in the dark. Wielders of this weapon report a unsettling sense of being watched.", 
                10, 30, 80);
                weapon4.setimageFile(localImageToBlob("images/imp_imgs/w4.png"));
                weaponRepository.save(weapon4);

                //w5.png
                Weapon weapon5 = new Weapon("Shadow Dagger", "Cloaked in shadows and laced with cursed essence, this dagger is ideal for silent takedowns. Its lightweight design allows for quick, consecutive strikes. Enemies wounded by it often feel a chill creeping through their veins long after the blade is gone.", 
                10, 40, 25);
                weapon5.setimageFile(localImageToBlob("images/imp_imgs/w5.png"));
                weaponRepository.save(weapon5);

                //w6.png
                Weapon weapon6 = new Weapon("Flame Sword", "Engulfed in eternal fire, this blade sears the air with each swing. Forged in volcanic heartfire. Enchanted by fire mages. It can ignite enemies upon impact. Its heat can melt through armor, but wielding it requires resistance to burns and steady hands.",
                20, 100, 250);
                weapon6.setimageFile(localImageToBlob("images/imp_imgs/w6.png"));
                weaponRepository.save(weapon6);
                
                //w7.png
                Weapon weapon7 = new Weapon("Bone Club", "A crude but brutally effective weapon carved from the femur of a giant beast. Despite its primitive look, the sheer mass of this club can crush armor and bones alike. Its intimidation factor alone can cause lesser foes to think twice before engaging.", 
                100, 73, 5);
                weapon7.setimageFile(localImageToBlob("images/imp_imgs/w7.png"));
                weaponRepository.save(weapon7);

                //w8.png
                Weapon weapon8 = new Weapon("War Hammer 2.0", "Forged from blackened iron and balanced for devastating force, this hammer is a fearsome tool on the battlefield. Each swing can knock enemies off their feet, dent armor, and shatter shields. Only those with strength enought can wield it effectively.", 
                120, 500, 90);
                weapon8.setimageFile(localImageToBlob("images/imp_imgs/w8.png"));
                weaponRepository.save(weapon8);

                //w9.png
                Weapon weapon9 = new Weapon("Torch", "Mostly a tool for illumination. It can be used in desperate combat situations, though it is far from ideal. The flame offers minor burns to enemies, but its best used to light paths, burn obstacles, or drive away creatures sensitive to light and fire.", 
                1, 10, 10);
                weapon9.setimageFile(localImageToBlob("images/imp_imgs/w9.png"));
                weaponRepository.save(weapon9);
 
                //w10.png
                Weapon weapon10 = new Weapon("Crossbow", "Built for accuracy and ease of use, this crossbow is a favorite among hunters and patrolmen. While it lacks rapid fire, its bolts hit hard and true. Can be modified with poison tips or enchanted bolts for increased lethality.", 
                1080, 1456, 30);
                weapon10.setimageFile(localImageToBlob("images/imp_imgs/w10.png"));
                weaponRepository.save(weapon10);

                //w11.png
                Weapon weapon11 = new Weapon("Flail", "This medieval menace features a spiked iron ball attached to a chain, delivering crushing damage that bypasses shields. Though its weight makes it slower to swing, the sheer impact of a hit often leaves foes stunned or broken.", 
                1003, 4012, 274);
                weapon11.setimageFile(localImageToBlob("images/imp_imgs/w11.png"));
                weaponRepository.save(weapon11);

                //w12.png
                Weapon weapon12 = new Weapon("Magic Staff", "Crafted from enchanted wood. Topped with a glowing orb. This staff channels arcane power. While it lacks the destructive force of advanced staves, it is a reliable conduit for elemental spells and magical bursts. Perfect for apprentice wizards.", 
                81, 12, 6);
                weapon12.setimageFile(localImageToBlob("images/imp_imgs/w12.png"));
                weaponRepository.save(weapon12);

                //w13.png
                Weapon weapon13 = new Weapon("War Hammer", "Forged with a squared iron head, this hammer is designed to smash through enemy lines and fortifications. While not as elegant as a sword or axe, its devastating blows make it a favorite in brute force warfare. Effective, but requires stamina.", 
                20, 6, 5);
                weapon13.setimageFile(localImageToBlob("images/imp_imgs/w13.png"));
                weaponRepository.save(weapon13);

                //w14.png
                Weapon weapon14 = new Weapon("Shark Sword", "This bizarre weapon resembles a shark mid lunge, with jagged teeth forming its serrated blade. Said to be crafted by sea cultists, it s feared both for its savage design and eerie aura. Its unique shape can tear through flesh, armor, and confidence alike.", 
                10, 3401, 802);
                weapon14.setimageFile(localImageToBlob("images/imp_imgs/w14.png"));
                weaponRepository.save(weapon14);

                //w15.png
                Weapon weapon15 = new Weapon("Blade of Eternal Shadows", "A curved, dark sword that seems to absorb the light around it. Forged from obsidian steel and powered by fallen souls, it deals shadow damage to enemies and has a chance to turn the wielder invisible for a few seconds after a critical hit.", 
                1104, 4210, 254);
                weapon15.setimageFile(localImageToBlob("images/imp_imgs/w15.png"));
                weaponRepository.save(weapon15);

                //w16.png
                Weapon weapon16 = new Weapon("Winged Piglet", "Is it a hammer or an adorable little pig. Its both. This unusual,and very cute, hammer is infused with celestial magic, allowing it to deal heavy blows to enemies and occasionally knock them far away.",
                2010, 9170, 20);
                weapon16.setimageFile(localImageToBlob("images/imp_imgs/w16.png"));
                weaponRepository.save(weapon16);
                
                //w17.png
                Weapon weapon17 = new Weapon("Ancient Tome", "Bound in timeworn leather, this ancient book emanates an aura of arcane knowledge. Its cryptic eye sigil is etched into the cover, hinting at the secrets within. Though somewhat tattered, its pages brim with esoteric wisdom.", 
                25, 120, 1);
                weapon17.setimageFile(localImageToBlob("images/imp_imgs/w17.png"));
                weaponRepository.save(weapon17);

                //w18.png
                Weapon weapon18 = new Weapon("Cactus Cannon", "A cannon with a large barrel made from a cactus. It can launch spiky projectiles and is mounted on a sturdy wooden base with a single wheel. The deserts vengeance, loaded and ready. Each shot leaves behind the scent of scorched sap and arid winds.", 
                5641, 5247, 69);
                weapon18.setimageFile(localImageToBlob("images/imp_imgs/w18.png"));
                weaponRepository.save(weapon18);

                //w19.png
                Weapon weapon19 = new Weapon("Mosaic Rifle", "An assault rifle with colorful mosaic patterns adorning the receiver and stock. It features a dark gray barrel and components, and is capable of delivering rapid, accurate fire. Art and destruction, fused seamlessly.",                                         
                7894, 11454, 389);
                weapon19.setimageFile(localImageToBlob("images/imp_imgs/w19.png"));
                weaponRepository.save(weapon19);

                //w20.png
                Weapon weapon20 = new Weapon("Crystal Shard Blade", "Forged from the ancient crystals of the Frostspire Caverns, this shimmering blade channels icy winds with every swing. Light but deadly, it freezes enemies on contact and leaves a trail of frost in its wake.", 
                180, 160, 130);
                weapon20.setimageFile(localImageToBlob("images/imp_imgs/w20.png"));
                weaponRepository.save(weapon20);

                //w21.png
                Weapon weapon21 = new Weapon("Saturns Antlers", "A whimsical staff crowned with a cow head, orbiting rings, and a golden bell. Infused with stardust and pastoral magic, it channels celestial energy with every swing, bringing both charm and cosmic might to its wielder.", 
                2147483647, 142, 274414);
                weapon21.setimageFile(localImageToBlob("images/imp_imgs/w21.png"));
                weaponRepository.save(weapon21);

                //w22.png
                Weapon weapon22 = new Weapon("Dark Saturns Antlers", "A mystical staff born from the depths of the cosmos, featuring a star speckled purple cow head and golden rings. Its eerie bell tolls with dark energy, summoning the silent power of the night sky and forgotten constellations.", 
                980314, 1401, 614454);
                weapon22.setimageFile(localImageToBlob("images/imp_imgs/w22.png"));
                weaponRepository.save(weapon22);

                //w23.png
                Weapon weapon23 = new Weapon("Gum Rapier", "A sword crafted entirely from enchanted bubblegump. Its sticky surface traps enemies, while its chewy core allows for lightning fast strikes. The perfect weapon for warriors who prefer style and surprise in battle. Deadly, delicious, and unpredictable.", 
                2041, 476, 5);
                weapon23.setimageFile(localImageToBlob("images/imp_imgs/w23.png"));
                weaponRepository.save(weapon23);

                //w24.png
                Weapon weapon24 = new Weapon("Rubber Ducky Stick", "A hilariously absurd weapon. A plain stick topped with a cheerful rubber duck. It wont deal much damage, but its perfect for confusing foes or unsettling those with duck related fears. Victory through psychological quackery", 
                11, 7054, 8012);
                weapon24.setimageFile(localImageToBlob("images/imp_imgs/w24.png"));
                weaponRepository.save(weapon24);

                //w25.png
                Weapon weapon25 = new Weapon("Fish Flail", "An unconventional weapon crafted from a slippery fish chained to a wooden handle. It swings wildly and unpredictably, making it hard to master but devastatingly effective. Smells awful, but leaves a lasting impression on enemies.", 
                14, 21, 2);
                weapon25.setimageFile(localImageToBlob("images/imp_imgs/w25.png"));
                weaponRepository.save(weapon25);

                //w26.png
                Weapon weapon26 = new Weapon("Chicken Sword", "An utterly bizarre weapon that fuses poultry with combat. It is not sharp, swift, or smart but it is unforgettable. Causes mild bruises, loud clucking, and occasional existential crises in enemies. A true farmyard fiasco in blade form.",
                2010, 70, 2);
                weapon26.setimageFile(localImageToBlob("images/imp_imgs/w26.png"));
                weaponRepository.save(weapon26);
                
                //w27.png
                Weapon weapon27 = new Weapon("Banana Boomerang", "A bizarre yet surprisingly aerodynamic weapon shaped like a ripe banana. Can be thrown with precision and returns swiftly to the user. However, repeated use may bruise the banana, reducing both damage and appeal over time.", 
                27, 20, 1);
                weapon27.setimageFile(localImageToBlob("images/imp_imgs/w27.png"));
                weaponRepository.save(weapon27);

                //w28.png
                Weapon weapon28 = new Weapon("Solar Roosters Crown", "Forged in the heart of a sun, this blazing staff bears the crowned head of a celestial rooster. Its radiant ring and ember bell summon solar flares and dawnfire, awakening ancient light to scorch the darkness of space.", 
                514411641, 524147, 69141);
                weapon28.setimageFile(localImageToBlob("images/imp_imgs/w28.png"));
                weaponRepository.save(weapon28);

                //w29.png
                Weapon weapon29 = new Weapon("Arcane Entangler", "An enigmatic staff carved from ancient wood, crowned by a radiant crystal entangled in living strands of arcane energy. It pulses with eldritch force, capable of unraveling magic and binding spells with every swirl of its luminous threads.",                                         
                4144, 1144, 89);
                weapon29.setimageFile(localImageToBlob("images/imp_imgs/w29.png"));
                weaponRepository.save(weapon29);

                //w30.png
                Weapon weapon30 = new Weapon("Fiesta Knife", "This legendary folding knife appears every year during the local festivals of Mostoles. While not the sharpest blade in town, it carries the weight of a thousand stories told at 3 a.m. under the glow of cheap beer.", 
                2147483647, 45, 2147483647);
                weapon30.setimageFile(localImageToBlob("images/imp_imgs/w30.png"));
                weaponRepository.save(weapon30);

                //w31.png
                Weapon weapon31 = new Weapon("Paper Plane", "A delicate weapon shaped like a paper airplane. While it may appear flimsy, it can surprise foes with swift, precise attacks when thrown by an expert hand. Its lightness makes it nearly silent in flight, ideal for stealthy strikes from afar.",                                         
                2977, 1, 1);
                weapon31.setimageFile(localImageToBlob("images/imp_imgs/w31.png"));
                weaponRepository.save(weapon31);
                
                //a1.png
                Armor armor = new Armor("Wood Armor", "Crafted from sturdy oak wood, this lightweight armor offers modest protection and great mobility. Ideal for novice adventurers or scouts who need speed over strength. Fragile against strong attacks but easy to replace and affordable.", 
                10, 50, 20);
                armor.setImageFile(localImageToBlob("images/imp_imgs/a1.png"));
                armorRepository.save(armor);

                //a2.png
                Armor armor2 = new Armor("Iron Plate", "Forged from heavy iron, this armor provides excellent protection against physical damage. Though slightly restrictive, it is a favorite among seasoned warriors. Durable, reliable, and built for direct combat in harsh conditions.", 
                10, 65, 50);
                armor2.setImageFile(localImageToBlob("images/imp_imgs/a2.png"));
                armorRepository.save(armor2);

                //a3.png
                Armor armor3 = new Armor("Golden Chestplate", "A dazzling armor piece made of pure gold. Though it lacks real protection, its brilliance and beauty draw admiration. Often worn by nobles or for ceremonial purposes rather than actual battle. A true symbol of wealth and vanity.", 
                10, 40, 70);
                armor3.setImageFile(localImageToBlob("images/imp_imgs/a3.png"));
                armorRepository.save(armor3);

                //a4.png
                Armor armor4 = new Armor("Chainmail Armor", "Made of thousands of interlocking metal rings, this armor balances protection and flexibility. Perfect for fighters who face both melee and ranged threats. It is tough, reliable, and ideal for prolonged use in harsh conditions.", 
                41, 541, 123);
                armor4.setImageFile(localImageToBlob("images/imp_imgs/a4.png"));
                armorRepository.save(armor4);

                //a5.png
                Armor armor5 = new Armor("Hide Armor", "Stitched from thick, tanned animal hides, this armor provides excellent defense for its weight. Great for rangers, hunters, or wilderness survival. Though not flashy, it is durable and tough, standing up to nature s challenges.", 
                1025, 165, 0);
                armor5.setImageFile(localImageToBlob("images/imp_imgs/a5.png"));
                armorRepository.save(armor5);

                //a6.png
                Armor armor6 = new Armor("Leaf Mail", "Woven from magically treated leaves, this armor is shockingly strong and stylish. Preferred by druids or forest guardians, it offers high defense while staying light and flexible. Nature s answer to metal and leather protection.", 
                1047, 2340, 170);
                armor6.setImageFile(localImageToBlob("images/imp_imgs/a6.png"));
                armorRepository.save(armor6);

                //a7.png
                Armor armor7 = new Armor("Water Armor", "Magically formed from living water, this armor molds perfectly to its wearer. It offers surprising defense while remaining weightless and fluid. Highly prized among elemental mages and rare to obtain outside of enchanted realms.", 
                105, 894, 203);
                armor7.setImageFile(localImageToBlob("images/imp_imgs/a7.png"));
                armorRepository.save(armor7);

                //a8.png
                Armor armor8 = new Armor("Cloth Armor", "A basic set of armor made from common fabric. Offers very little protection, but grants complete freedom of movement. Ideal for civilians, beginners, or magic users who rely on speed or spells more than brute strength in combat.", 
                1, 58412, 2512);
                armor8.setImageFile(localImageToBlob("images/imp_imgs/a8.png"));
                armorRepository.save(armor8);

                //a9.png
                Armor armor9 = new Armor("Plastic Armor", "This poorly made armor is fashioned from cheap plastic, offering almost no defense. It is lightweight and flexible, but practically useless in a real fight. More of a novelty or disguise piece than any serious protective gear.", 
                1, 1, 1);
                armor9.setImageFile(localImageToBlob("images/imp_imgs/a9.png"));
                armorRepository.save(armor9);

                //a10.png
                Armor armor10 = new Armor("Shattered Crystal Mail", "Forged from fractured enchanted crystals, this armor glimmers with unstable energy. While fragile, its shards pulse with residual magic, offering brief bursts of power and style unmatched by conventional armor.", 
                37, 1275, 312);
                armor10.setImageFile(localImageToBlob("images/imp_imgs/a10.png"));
                armorRepository.save(armor10);

                //a11.png
                Armor armor11 = new Armor("Harmonic Breastplate", "Crafted from enchanted metal, this armor is imbued with melodic power. Its treble clef motif and musical notes hum with resonance, boosting morale. Though less protective, it elevates the wearer with harmonious energy.", 
                15, 580, 279);
                armor11.setImageFile(localImageToBlob("images/imp_imgs/a11.png"));
                armorRepository.save(armor11);

                //a12.png
                Armor armor12 = new Armor("Bookish Armor", "Fashioned from an enormous antique tome, this breastplate melds literature with defense. A golden emblem of an open book adorns its surface, while layered pages form its left shoulder pauldron.", 
                1012, 625, 6);
                armor12.setImageFile(localImageToBlob("images/imp_imgs/a12.png"));
                armorRepository.save(armor12);

                //a13.png
                Armor armor13 = new Armor("Laurel Armor", "Forged from celestial bronze, this legendary breastplate is crowned with a living laurel wreath whose leaves never wither. Lighter than standard plate armor, it radiates divine favor, bolstering the wearers courage in battle and inspiring allies nearby.", 
                24, 40014, 750);
                armor13.setImageFile(localImageToBlob("images/imp_imgs/a13.png"));
                armorRepository.save(armor13);

                //a14.png
                Armor armor14 = new Armor("Chips Bag", "An odd armor fashioned from a large potato chips bag. Though lacking in actual protection, it is lightweight and attention grabbing. Ideal for casual wear or pranks, but very impractical for serious combat encounters.", 
                2, 5,0 );
                armor14.setImageFile(localImageToBlob("images/imp_imgs/a14.png"));
                armorRepository.save(armor14);

                //a15.png
                Armor armor15 = new Armor("Stegosaurus Armor", "Crafted to mimic the ancient stegosaurus, this armor offers robust defense with its rugged plates. Spiked backplates echo the dinosaur s form, providing added defense and a primal connection to the past.", 
                2547, 5854, 7);
                armor15.setImageFile(localImageToBlob("images/imp_imgs/a15.png"));
                armorRepository.save(armor15);

                //a16.png
                Armor armor16 = new Armor("DVD Armor", "This glimmering armor is entirely crafted from upcycled DVDs, forming a lightweight yet sturdy defense that refracts rainbow light with every move. Its disco ball effect dazzles opponents while offering surprising protection.", 
                548, 541, 54);
                armor16.setImageFile(localImageToBlob("images/imp_imgs/a16.png"));
                armorRepository.save(armor16);
 
                //a17.png
                Armor armor17 = new Armor("Toilet Paper Armor", "This peculiar set of armor is constructed entirely from rolls of toilet paper. It provides an amusing, if thoroughly impractical, form of protection. If you decide to wear it, be aware of water. And fire. And sand. And...", 
                4, 147, 54);
                armor17.setImageFile(localImageToBlob("images/imp_imgs/a17.png"));
                armorRepository.save(armor17);

                //a18.png
                Armor armor18 = new Armor("Gearwork Armor", "Constructed from interlocking gears and cogs, this mechanical armor whirs with intricate precision. Each piece moves in sync, providing reliable defense through the ingenuity of clockwork engineering. Its mechanisms whisper of forgotten craftsmanship.", 
                405, 15, 78);
                armor18.setImageFile(localImageToBlob("images/imp_imgs/a18.png"));
                armorRepository.save(armor18);

                //a19.png
                Armor armor19 = new Armor("Painter s Smock", "A garment worn by artists to protect their clothing during painting. Sturdy and well worn, it is heavily spotted with vibrant paint splatters that tell stories of countless creations. Every stain hides a unique story.", 
                1, 954, 385);
                armor19.setImageFile(localImageToBlob("images/imp_imgs/a19.png"));
                armorRepository.save(armor19);

                //a20.png
                Armor armor20 = new Armor("Lava Armor", "Forged in volcanic chambers, this molten armor pulses with glowing magma veins. Provides intense heat resistance and slight fire damage to attackers. Not recommended in wooden buildings.", 
                37, 1275, 312);
                armor20.setImageFile(localImageToBlob("images/imp_imgs/a20.png"));
                armorRepository.save(armor20);

                //a21.png
                Armor armor21 = new Armor("Jellyfish Armor", "Made from translucent, gelatinous material, this armor glows softly in the dark and pulses like a living creature. Grants slight electric shock to melee attackers and underwater breathing to the wearer.", 
                2147483647, 69, 83647);
                armor21.setImageFile(localImageToBlob("images/imp_imgs/a21.png"));
                armorRepository.save(armor21);

                //a22.png
                Armor armor22 = new Armor("Sunflower Suit", "This vibrant armor is stitched from magical sunflower petals and stems. Absorbs sunlight to slowly regenerate health during the day and boosts morale in allies with its cheerful appearance.", 
                2, 7404, 7510);
                armor22.setImageFile(localImageToBlob("images/imp_imgs/a22.png"));
                armorRepository.save(armor22);

                //a23.png
                Armor armor23 = new Armor("Tea Kettle Mail", "A whimsical suit forged from enchanted teapots and kettle parts. Steam puffs out harmlessly from the spout pauldrons. Grants fire resistance and occasionally brews morale boosting tea mid battle.",
                2145512, 4145, 10);
                armor23.setImageFile(localImageToBlob("images/imp_imgs/a23.png"));
                armorRepository.save(armor23);

                //a24.png
                Armor armor24 = new Armor("Plague Doctor Vestments", "This eerie armor is modeled after historical plague doctor garb. Imbued with antiseptic magic and dark alchemy, it grants poison resistance and a minor aura of dread to nearby foes.",
                2147545, 5112,2 );
                armor24.setImageFile(localImageToBlob("images/imp_imgs/a24.png"));
                armorRepository.save(armor24);

                //a25.png
                Armor armor25 = new Armor("Armadillo Suit", "Crafted to mimic the hardy shell of an armadillo, this armor rolls with the hits, literally. Highly durable and flexible, it provides solid defense while enabling evasive maneuvers. Perfect for unconventional warriors.", 
                458, 54, 17);
                armor25.setImageFile(localImageToBlob("images/imp_imgs/a25.png"));
                armorRepository.save(armor25);

                //a26.png
                Armor armor26 = new Armor("Banana Suit", "A ridiculous full body costume shaped like a peeled banana. While it offers zero real defense, it boosts morale and distracts enemies with sheer absurdity. Worn only by the bravest fools and most committed jesters of battle.", 
                5, 1, 4);
                armor26.setImageFile(localImageToBlob("images/imp_imgs/a26.png"));
                armorRepository.save(armor26);
 
                //a27.png
                Armor armor27 = new Armor("Pizza Sweater", "Warm, greasy, and delicious looking, this pullover resembles a pepperoni pizza slice. It won t stop a sword, but it may stop hunger. Popular among eccentric adventurers who care more about vibes than victory.", 
                27, 17, 4);
                armor27.setImageFile(localImageToBlob("images/imp_imgs/a27.png"));
                armorRepository.save(armor27);

                //a28.png
                Armor armor28 = new Armor("Barrel Armor", "Composed of an aged wooden cask, this makeshift armor offers basic protection in an absurdly comical form. Despite modest defensive capabilities, it stands out for its quirky look and the improvised, almost laughable nature of its design.", 
                47, 41, 38);
                armor28.setImageFile(localImageToBlob("images/imp_imgs/a28.png"));
                armorRepository.save(armor28);

                //a29.png
                Armor armor29 = new Armor("Lattice Plate", "This intricate armor is crafted from interlocking latticework metal, combining structural elegance with moderate protection. Its airy design allows surprising flexibility, while the open mesh gleams under light, favored by elite ceremonial guards.", 
                11, 94, 85);
                armor29.setImageFile(localImageToBlob("images/imp_imgs/a29.png"));
                armorRepository.save(armor29);

        if (!Files.exists(SOURCE)) return;
        Files.createDirectories(TARGET);

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(SOURCE)) {
            for (Path image : stream) {
                if (Files.isRegularFile(image)) {
                    Path destination = TARGET.resolve(image.getFileName()).normalize();
                    if (!destination.startsWith(TARGET)) {
                        throw new IOException("Path traversal detected");
                    }
                    Files.copy(image, destination, StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }
        }
}