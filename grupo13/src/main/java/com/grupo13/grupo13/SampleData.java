package com.grupo13.grupo13;
import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.grupo13.grupo13.model.Armor;
import com.grupo13.grupo13.model.User;
import com.grupo13.grupo13.model.Weapon;
import com.grupo13.grupo13.repository.ArmorRepository;
import com.grupo13.grupo13.repository.UserRepository;
import com.grupo13.grupo13.repository.WeaponRepository;
import jakarta.annotation.PostConstruct;

@Service
public class SampleData {

        // attributes
        @Autowired
        private UserRepository userRepository;

        @Autowired
        private WeaponRepository weaponRepository;

        @Autowired
        private ArmorRepository armorRepository;
        
        @Autowired
	private PasswordEncoder passwordEncoder;

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
        public void init() {
                userRepository.save(new User("user", passwordEncoder.encode("pass"), "USER"));
                userRepository.save(new User("Lotti", passwordEncoder.encode("lottipico"), "USER"));
		userRepository.save(new User("admin", passwordEncoder.encode("adminpass"), "USER", "ADMIN"));

                //faltan las imagenes

                //w1.png
                Weapon weapon = new Weapon("Wood Sword", "A simple blade carved from sturdy oak, this sword is the hallmark of novice adventurers. Though not the sharpest or strongest, it is reliable in a pinch and a good starting weapon for those just beginning their journey.",
                30, 10, 25);    
                weapon.setimageFile(localImageToBlob("images/imp_imgs/w1.png"));
                weaponRepository.save(weapon);

                //w2.png
                Weapon weapon2 = new Weapon("Iron Longsword", "Forged from tempered iron, this longsword offers a great balance between durability and usability. Designed for consistent damage and solid defense, it's a favorite among mercenaries and town guards. Reliable in most combats, it rarely disappoints.", 
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
                Weapon weapon9 = new Weapon("Torch", "Mostly a tool for illumination. It can be used in desperate combat situations, though it’s far from ideal. The flame offers minor burns to enemies, but it's best used to light paths, burn obstacles, or drive away creatures sensitive to light and fire.", 
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
                Weapon weapon12 = new Weapon("Magic Staff", "Crafted from enchanted wood. Topped with a glowing orb. This staff channels arcane power. While it lacks the destructive force of advanced staves, it’s a reliable conduit for elemental spells and magical bursts. Perfect for apprentice wizards.", 
                81, 12, 6);
                weapon12.setimageFile(localImageToBlob("images/imp_imgs/w12.png"));
                weaponRepository.save(weapon12);

                //w13.png
                Weapon weapon13 = new Weapon("War Hammer", "Forged with a squared iron head, this hammer is designed to smash through enemy lines and fortifications. While not as elegant as a sword or axe, its devastating blows make it a favorite in brute-force warfare. Effective, but requires stamina.", 
                20, 6, 5);
                weapon13.setimageFile(localImageToBlob("images/imp_imgs/w13.png"));
                weaponRepository.save(weapon13);

                //w14.png
                Weapon weapon14 = new Weapon("Shark Sword", "This bizarre weapon resembles a shark mid-lunge, with jagged teeth forming its serrated blade. Said to be crafted by sea cultists, it’s feared both for its savage design and eerie aura. Its unique shape can tear through flesh, armor, and confidence alike.", 
                10, 3401, 802);
                weapon14.setimageFile(localImageToBlob("images/imp_imgs/w14.png"));
                weaponRepository.save(weapon14);

                //w15.png
                Weapon weapon15 = new Weapon("Blade of Eternal Shadows", "A curved, dark sword that seems to absorb the light around it. Forged from obsidian steel and powered by fallen souls, it deals shadow damage to enemies and has a chance to turn the wielder invisible for a few seconds after a critical hit.", 
                1104, 4210, 254);
                weapon15.setimageFile(localImageToBlob("images/imp_imgs/w15.png"));
                weaponRepository.save(weapon15);

                //w16.png
                Weapon weapon16 = new Weapon("Winged Piglet", "Is it a hammer or an adorable little pig? It's both! This unusual (and very cute) hammer is infused with celestial magic, allowing it to deal heavy blows to enemies and occasionally knock them far away.",
                2010, 9170, 20);
                weapon16.setimageFile(localImageToBlob("images/imp_imgs/w16.png"));
                weaponRepository.save(weapon16);
                
                //w17.png
                Weapon weapon17 = new Weapon("Ancient Tome", "Bound in timeworn leather, this ancient book emanates an aura of arcane knowledge. Its cryptic eye sigil is etched into the cover, hinting at the secrets within. Though somewhat tattered, its pages brim with esoteric wisdom.", 
                25, 120, 1);
                weapon17.setimageFile(localImageToBlob("images/imp_imgs/w17.png"));
                weaponRepository.save(weapon17);

                //w18.png
                Weapon weapon18 = new Weapon("Cactus Cannon", "A cannon with a large barrel made from a cactus. It can launch spiky projectiles and is mounted on a sturdy wooden base with a single wheel. The desert's vengeance, loaded and ready. Each shot leaves behind the scent of scorched sap and arid winds.", 
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
                Weapon weapon21 = new Weapon("Saturn's Antlers", "", 
                100314, 142, 274414);
                weapon21.setimageFile(localImageToBlob("images/imp_imgs/w21.png"));
                weaponRepository.save(weapon21);

                //w22.png
                Weapon weapon22 = new Weapon("Dark Saturn's Antlers", "", 
                980314, 1401, 614454);
                weapon22.setimageFile(localImageToBlob("images/imp_imgs/w22.png"));
                weaponRepository.save(weapon22);

                //w23.png
                Weapon weapon23 = new Weapon("", "", 
                20, 6, 5);
                weapon23.setimageFile(localImageToBlob("images/imp_imgs/w23.png"));
                weaponRepository.save(weapon23);

                //w24.png
                Weapon weapon24 = new Weapon("", "", 
                10, 3401, 802);
                weapon24.setimageFile(localImageToBlob("images/imp_imgs/w24.png"));
                weaponRepository.save(weapon24);

                //w25.png
                Weapon weapon25 = new Weapon("", "", 
                1104, 4210, 254);
                weapon25.setimageFile(localImageToBlob("images/imp_imgs/w25.png"));
                weaponRepository.save(weapon25);

                //w26.png
                Weapon weapon26 = new Weapon("", "",
                2010, 9170, 20);
                weapon26.setimageFile(localImageToBlob("images/imp_imgs/w26.png"));
                weaponRepository.save(weapon26);
                
                //w27.png
                Weapon weapon27 = new Weapon("", "", 
                25, 120, 1);
                weapon27.setimageFile(localImageToBlob("images/imp_imgs/w27.png"));
                weaponRepository.save(weapon27);

                //w28.png
                Weapon weapon28 = new Weapon("", "", 
                5641, 5247, 69);
                weapon28.setimageFile(localImageToBlob("images/imp_imgs/w28.png"));
                weaponRepository.save(weapon28);

                //w29.png
                Weapon weapon29 = new Weapon("", "",                                         
                7894, 11454, 389);
                weapon29.setimageFile(localImageToBlob("images/imp_imgs/w29.png"));
                weaponRepository.save(weapon29);
                
                //a1.png
                Armor armor = new Armor("Wood Armor", "Crafted from sturdy oak wood, this lightweight armor offers modest protection and great mobility. Ideal for novice adventurers or scouts who need speed over strength. Fragile against strong attacks but easy to replace and affordable.", 
                10, 50, 20);
                armor.setImageFile(localImageToBlob("images/imp_imgs/a1.png"));
                armorRepository.save(armor);

                //a2.png
                Armor armor2 = new Armor("Iron Plate", "Forged from heavy iron, this armor provides excellent protection against physical damage. Though slightly restrictive, it’s a favorite among seasoned warriors. Durable, reliable, and built for direct combat in harsh conditions.", 
                10, 65, 50);
                armor2.setImageFile(localImageToBlob("images/imp_imgs/a2.png"));
                armorRepository.save(armor2);

                //a3.png
                Armor armor3 = new Armor("Golden Chestplate", "A dazzling armor piece made of pure gold. Though it lacks real protection, its brilliance and beauty draw admiration. Often worn by nobles or for ceremonial purposes rather than actual battle. A true symbol of wealth and vanity.", 
                10, 40, 70);
                armor3.setImageFile(localImageToBlob("images/imp_imgs/a3.png"));
                armorRepository.save(armor3);

                //a4.png
                Armor armor4 = new Armor("Chainmail Armor", "Made of thousands of interlocking metal rings, this armor balances protection and flexibility. Perfect for fighters who face both melee and ranged threats. It’s tough, reliable, and ideal for prolonged use in harsh conditions.", 
                41, 541, 123);
                armor4.setImageFile(localImageToBlob("images/imp_imgs/a4.png"));
                armorRepository.save(armor4);

                //a5.png
                Armor armor5 = new Armor("Hide Armor", "Stitched from thick, tanned animal hides, this armor provides excellent defense for its weight. Great for rangers, hunters, or wilderness survival. Though not flashy, it’s durable and tough, standing up to nature’s challenges.", 
                1025, 165, 0);
                armor5.setImageFile(localImageToBlob("images/imp_imgs/a5.png"));
                armorRepository.save(armor5);

                //a6.png
                Armor armor6 = new Armor("Leaf Mail", "Woven from magically treated leaves, this armor is shockingly strong and stylish. Preferred by druids or forest guardians, it offers high defense while staying light and flexible. Nature’s answer to metal and leather protection.", 
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
                Armor armor9 = new Armor("Plastic Armor", "This poorly made armor is fashioned from cheap plastic, offering almost no defense. It’s lightweight and flexible, but practically useless in a real fight. More of a novelty or disguise piece than any serious protective gear.", 
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
                Armor armor13 = new Armor("Laurel Armor", "Forged from celestial bronze, this legendary breastplate is crowned with a living laurel wreath whose leaves never wither. Lighter than standard plate armor, it radiates divine favor, bolstering the wearer's courage in battle and inspiring allies nearby.", 
                24, 40014, 750);
                armor13.setImageFile(localImageToBlob("images/imp_imgs/a13.png"));
                armorRepository.save(armor13);

                //a14.png
                Armor armor14 = new Armor("Chips Bag", "An odd armor fashioned from a large potato chips bag. Though lacking in actual protection, it is lightweight and attention-grabbing. Ideal for casual wear or pranks, but very impractical for serious combat encounters.", 
                2, 5,0 );
                armor14.setImageFile(localImageToBlob("images/imp_imgs/a14.png"));
                armorRepository.save(armor14);

                //a15.png
                Armor armor15 = new Armor("Stegosaurus Armor", "Crafted to mimic the ancient stegosaurus, this armor offers robust defense with its rugged plates. Spiked backplates echo the dinosaur’s form, providing added defense and a primal connection to the past.", 
                2547, 5854, 7);
                armor15.setImageFile(localImageToBlob("images/imp_imgs/a15.png"));
                armorRepository.save(armor15);

                //a16.png
                Armor armor16 = new Armor("DVD Armor", "This glimmering armor is entirely crafted from upcycled DVDs, forming a lightweight yet sturdy defense that refracts rainbow light with every move. Its disco-ball effect dazzles opponents while offering surprising protection.", 
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
                Armor armor19 = new Armor("Painter’s Smock", "A garment worn by artists to protect their clothing during painting. Sturdy and well-worn, it is heavily spotted with vibrant paint splatters that tell stories of countless creations. Every stain hides a unique story.", 
                1, 954, 385);
                armor19.setImageFile(localImageToBlob("images/imp_imgs/a19.png"));
                armorRepository.save(armor19);

        }

}
