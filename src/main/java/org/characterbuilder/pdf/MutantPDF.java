package org.characterbuilder.pdf;

import com.itextpdf.text.DocumentException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;
import javax.persistence.EntityManager;
import org.characterbuilder.persist.ThePersister;
import org.characterbuilder.persist.entity.MutantAbilityCharacter;
import org.characterbuilder.persist.entity.MutantArmorCharacter;
import org.characterbuilder.persist.entity.MutantBaseStatCharacter;
import org.characterbuilder.persist.entity.MutantCharacter;
import org.characterbuilder.persist.entity.MutantItemCharacter;
import org.characterbuilder.persist.entity.MutantSkillCharacter;
import org.characterbuilder.persist.entity.MutantWeaponCharacter;

/**
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
public final class MutantPDF {

	public ByteArrayResource getPDF(MutantCharacter character) throws Exception, IOException, DocumentException {
		try {
			String filename = "/pdf/mutantVitTiny2.pdf";

			RollspelPdfManager pdfer = new RollspelPdfManager(filename);

			doTheStamping(pdfer, character);

			ByteArrayResource bar = new ByteArrayResource(
					  pdfer.getOutputStream().toByteArray(),
					  "mutant.pdf",
					  "application/pdf");
			return bar;
			/*DownloadWindow window = new DownloadWindow(bar);

			 return window;*/
		} catch (FileNotFoundException e) {
			throw new Exception("Unable to generate PDF.");
		}
	}

	/**
	 * Perform the imprinting on the PDF
	 *
	 * @param pdf
	 * @param character
	 * @throws IOException
	 * @throws DocumentException
	 */
	private void doTheStamping(RollspelPdfManager pdf, MutantCharacter character) throws IOException, DocumentException, Exception {
		EntityManager em = ThePersister.getEntityManager();
		MutantCharacter gubbe = em.find(MutantCharacter.class, character.getId());

		pdf.writeToField("Namn", gubbe.getName());
		pdf.writeToField("Klass", gubbe.getMutantClass().getName());
		pdf.writeToField("Tidigare yrke", gubbe.getPriorOccupation());
		pdf.writeToField("Hemort", gubbe.getHome());
		pdf.writeToField("Ålder", gubbe.getAge());
		pdf.writeToField("kön", gubbe.getSex());
		pdf.writeToField("Längd", gubbe.getLength());
		pdf.writeToField("Vikt", gubbe.getWeight());
		pdf.writeToField("Utseende", gubbe.getLooks());
		pdf.writeToField("Rykte", gubbe.getRumorPoints());
		pdf.writeToField("Status", gubbe.getStatusPoints());
		pdf.writeToField("KP", gubbe.getHealthPoints());
		pdf.writeToField("TT", gubbe.getTraumaPoints());

		pdf.writeToField("sb1", gubbe.getDamageBonus());
		pdf.writeToField("ib1", gubbe.getInitiativbonus());
		pdf.writeToField("bf1", gubbe.getCarryCap());
		pdf.writeToField("rea1", gubbe.getReactionValue());

		pdf.writeToField("FF-strid", gubbe.getFfStrid());
		pdf.writeToField("FF-springa", gubbe.getFfSpringa());
		pdf.writeToField("FF-sprint", gubbe.getFfSprint());


		//TODO Förflyttning

		//BASE STATS
		for (MutantBaseStatCharacter baseStat : gubbe.getMutantBaseStatCharacters()) {
			pdf.writeToField(
					  baseStat.getMutantBaseStat().getShortName().toLowerCase() + "1",
					  baseStat.getValueTotal());
		}

		//SKILLS
		Integer nf = 0, tf = 0;
		Set<MutantSkillCharacter> skillList = gubbe.getMutantSkillCharacters();
		for (MutantSkillCharacter skill : skillList) {
			if (skill.getNaturalSkill() && nf < 15) {
				++nf;
				//System.out.println(nf);
				pdf.writeToField("nf" + nf, skill.getName() + " ("
						  + skill.getMutantBaseStatCharacter().getMutantBaseStat().getShortName()
						  + ")");
				pdf.writeToField("n-fv" + (nf + 10), skill.getTotal()); //FV
				//pdf.writeToField(fieldName, newVal) //MOD
			} else if (tf < 15) {
				++tf;
				//System.out.println(tf);
				pdf.writeToField("tf" + tf, skill.getName() + " ("
						  + skill.getMutantBaseStatCharacter().getMutantBaseStat().getShortName()
						  + ")");
				pdf.writeToField("t-fv" + (tf + 10), skill.getTotal()); //FV
				//pdf.writeToField(fieldName, newVal) //MOD
			}
		}

		//ABILITIES
		Integer abil = 0;
//System.out.println(gubbe.getName() + " förmågor:");
		Set<MutantAbilityCharacter> abilitySet = gubbe.getMutantAbilityCharacters();
		for (MutantAbilityCharacter ability : abilitySet) {
			String abilityName = ability.getName();
			String abilityDesc = ability.getEffect();
			pdf.writeToField("Förmågor" + ++abil, abilityName);
			pdf.writeToField("Beskr" + abil, abilityDesc);
			if (abil >= 8) {
				break;
			}
		}

		//Weapon
		Integer wepCount = 0;
		for (MutantWeaponCharacter weapon : gubbe.getMutantWeaponCharacters()) {

			if (++wepCount > 8) {
				break;
			}
//System.out.println(weapon.getName());
			
			String wepName = weapon.getName();
			pdf.writeToField("Vapen" + wepCount, wepName);
			String wepGrip = weapon.getGrip();
			pdf.writeToField("fattn" + wepCount, wepGrip);
			Integer wepInit = weapon.getInitiative();
			pdf.writeToField("v-init" + wepCount, wepInit);
			String wepDmg = weapon.getDamage();
			pdf.writeToField("v-skada" + wepCount, wepDmg);
			Integer wepPen = weapon.getPenetration();
			pdf.writeToField("v-pen" + wepCount, wepPen);
			Integer wepReach = weapon.getReach();
			pdf.writeToField("v-rkv" + wepCount, wepReach);
			Integer wepDura = weapon.getDurability();
			pdf.writeToField("v-tål" + wepCount, wepDura);
			Integer wepDep = weapon.getDependability();
			pdf.writeToField("v-pål" + wepCount + "m", wepDep);
			Double wepWeight = (double) weapon.getWeight() / 1000;
			pdf.writeToField("v-vikt" + wepCount, wepWeight);
			Integer wepAmmo = weapon.getAmmo();
			pdf.writeToField("v-ammo" + wepCount, wepAmmo);
			Integer wepAmmoMax = weapon.getAmmoMax();
			pdf.writeToField("v-ammo" + wepCount + "m", wepAmmoMax);

		}

		//Item
		Integer itemCount = 0;
		for (MutantItemCharacter item : gubbe.getMutantItemCharacters()) {
			if (++itemCount > 23) {
				break;
			}
//System.out.println(item.getName());
			//if the ammount is not 1, write n+x<item_name> where n=the number of items
			pdf.writeToField("utrust" + itemCount, (item.getAmmount() != 1 ? (item.getAmmount() + "x ") : "") + item.getName());
			String weight = (((double) item.getWeight() / 1000) * item.getAmmount()) + "";
			pdf.writeToField("u-vikt" + itemCount, weight);
		}

		//Armor
		String[] armorName = new String[]{"", "", "", "", "", ""}; //Size = 6
		int[] armorABS = new int[]{0, 0, 0, 0, 0, 0}; //Size=6
		int[] armorBEG = new int[]{0, 0, 0, 0, 0, 0}; //Size=6
		int[] armorWeight = new int[]{0, 0, 0, 0, 0, 0}; //Size=6
		for (MutantArmorCharacter armor : gubbe.getMutantArmorCharacters()) {
			int bodypart = armor.getMutantArmorBodypart().getId();
			if (armorName[bodypart - 1].equals("")) {
				armorName[bodypart - 1] = armor.getName();
			} else {
				armorName[bodypart - 1] = "Blandat";
			}
			armorABS[bodypart - 1] += armor.getAbs();
			armorBEG[bodypart - 1] += armor.getBeg();
			armorWeight[bodypart - 1] += armor.getWeight();
		}
		for (int i = 0; i < 6; ++i) {
			pdf.writeToField("rustning" + (i + 1), armorName[i]);
			pdf.writeToField("r-abs" + (i + 1), armorABS[i]);
			pdf.writeToField("r-beg" + (i + 1), armorBEG[i]);
			String weight = ((double) armorWeight[i] / 1000) + "";
			pdf.writeToField("r-vikt" + (i + 1), weight);

		}

		//kronkrediter
		pdf.writeToField("pengar" + 1, gubbe.getMoney() / 100);
		//krediter
		pdf.writeToField("pengar" + 2, gubbe.getMoney() % 100 / 10);
		//Jycke
		pdf.writeToField("pengar" + 3, gubbe.getMoney() % 10);
	}
}
