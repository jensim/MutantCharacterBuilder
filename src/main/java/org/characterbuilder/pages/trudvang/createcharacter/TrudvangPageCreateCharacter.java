package org.characterbuilder.pages.trudvang.createcharacter;

import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.characterbuilder.CharbuildApp;
import org.characterbuilder.pages.abst.WorkspaceLayout;
import org.characterbuilder.persist.ThePersister;
import org.characterbuilder.persist.entity.TrudvangArktype;
import org.characterbuilder.persist.entity.TrudvangCharacter;
import org.characterbuilder.persist.entity.TrudvangExceptionalCharacter;
import org.characterbuilder.persist.entity.TrudvangExceptionalLevel;
import org.characterbuilder.persist.entity.TrudvangPeople;
import org.characterbuilder.persist.entity.TrudvangSkill;
import org.characterbuilder.persist.entity.TrudvangSkillCharacter;
import org.characterbuilder.persist.entity.TrudvangSkillStart;
import org.characterbuilder.persist.entity.TrudvangUpbringing;

/**
 * TrudvangPageCreateCharacter
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
public final class TrudvangPageCreateCharacter extends WorkspaceLayout {

	private enum State {

		Background,
		Exceptional,
		Skill
	}
	private State state = State.Background;
	private final TrudvangInnerPageBackgroundPicker bckGrndPicker = new TrudvangInnerPageBackgroundPicker();
	private final TrudvangInnerPageExceptionalAssign excepPicker = new TrudvangInnerPageExceptionalAssign();
	private TrudvangInnerPageAssignStartSkills skillPicker = null;
	private final VerticalLayout layout = new VerticalLayout();
	private final Button nextButton = new Button("Nästa", new KlikkList()),
			  backButton = new Button("Bakåt", new KlikkList()),
			  doneButton = new Button("Färdig", new KlikkList());

	public TrudvangPageCreateCharacter() {
		super();

		backButton.setVisible(false);
		doneButton.setVisible(false);

		addComponent(new Label("<h1>Skapa karaktär</h1>", ContentMode.HTML));
		addComponent(layout);
		layout.addComponent(bckGrndPicker);
		addComponent(new HorizontalLayout(backButton, nextButton, doneButton));
	}

	private class KlikkList implements Button.ClickListener {

		@Override
		public void buttonClick(ClickEvent event) {
			if (event.getButton() == nextButton) { //Button-NEXT
				if (state == State.Background) { //Dest-Exceptional
					layout.removeAllComponents();
					layout.addComponent(excepPicker);
					backButton.setVisible(true);
					state = State.Exceptional;
				} else if (state == State.Exceptional) { //Dest-Skillassign
					TrudvangArktype ark = bckGrndPicker.getSelectedArktype();
					TrudvangPeople peo = bckGrndPicker.getSelectedPeople();
					TrudvangUpbringing upb = bckGrndPicker.getSelectedUpbringing();
					if (ark != null && peo != null && upb != null) {

						List<TrudvangSkillStart> skillList = addSkillListsTogether(
								  //ark.getTrudvangSkillStartList(),
								  peo.getTrudvangSkillStartList(),
								  upb.getTrudvangSkillStartList());

						if (skillList != null) {
							//System.out.println("Skill list size: " + skillList.size());
							skillPicker = new TrudvangInnerPageAssignStartSkills(skillList);
							layout.removeAllComponents();
							layout.addComponent(skillPicker);

							state = State.Skill;
							doneButton.setVisible(false);
							nextButton.setVisible(true);
						} else {
							Notification.show("Skills not done yet");
						}
					} else {
						Notification.show("Background not done yet");
					}
				}
			} else if (event.getButton() == backButton) { //Button-BACK
				if (state == State.Exceptional) { //Dest-Background
					layout.removeAllComponents();
					layout.addComponent(bckGrndPicker);
					backButton.setVisible(false);
					state = State.Background;
				} else if (state == State.Skill) { //Dest-Exceptional
					layout.removeAllComponents();
					layout.addComponent(excepPicker);

					state = State.Exceptional;
					doneButton.setVisible(false);
					nextButton.setVisible(true);
				}
			} else if (event.getButton() == doneButton) {//Button-DONE
				if (state == State.Skill && skillPicker != null) {
					try {
						TrudvangCharacter newCharacter = new TrudvangCharacter();
						newCharacter.setUser(ThePersister.getRollspelUser(VaadinService.getCurrentRequest().getUserPrincipal().getName()));

						//FIXME: Exceptionals
						TrudvangExceptionalLevel exPos1 = excepPicker.pos1.getTrudvangExcepLevel();
						TrudvangExceptionalCharacter exPos1Char = new TrudvangExceptionalCharacter();
						TrudvangExceptionalLevel exPos2 = excepPicker.pos2.getTrudvangExcepLevel();
						
						TrudvangExceptionalLevel exNeg = excepPicker.neg.getTrudvangExcepLevel();
						
						
						//FIXME: Free elabs
						

						//Skills
						Map<TrudvangSkill, Integer> map = skillPicker.getSelectedSkillsAndValues();
						List<TrudvangSkill> fullSkillList = ThePersister.getEntityManager().createQuery(
								  "SELECT skill FROM TrudvangSkill skill",
								  TrudvangSkill.class).getResultList();
						for (TrudvangSkill skill : fullSkillList) {
							TrudvangSkillCharacter charSkill = new TrudvangSkillCharacter();
							if (map.containsKey(skill)) {
								charSkill.setBasevalue(map.get(skill));
							}
							newCharacter.getTrudvangSkillList().add(charSkill);
						}

					} catch (Exception ex) {
						Notification.show("Not done.");
					}
				}
			}
		}

		private List<TrudvangSkillStart> addSkillListsTogether(
				  List<TrudvangSkillStart>... skillLists) {
			List<TrudvangSkillStart> totalList = new ArrayList();
			for (List<TrudvangSkillStart> list : skillLists) {
				if (list == null) {
					return null;
				}
				totalList.addAll(list);
			}
			return totalList;
		}
	}
}
