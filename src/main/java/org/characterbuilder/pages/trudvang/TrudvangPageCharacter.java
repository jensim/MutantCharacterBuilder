package org.characterbuilder.pages.trudvang;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import org.characterbuilder.pages.abst.trudvang.TrudvangCharacterWorkspaceLayout;
import org.characterbuilder.persist.entity.TrudvangCharacter;

/**
 * TrudvangPageCharacter
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
public class TrudvangPageCharacter extends TrudvangCharacterWorkspaceLayout{
	
	public TrudvangPageCharacter(TrudvangCharacter character ) {
		super(character);
		addComponent(new Label("<h1>MANAGE CHARACTER</h1>", ContentMode.HTML));
		if(character != null && character.getName() != null){
			addComponent(new Label(character.getName(), ContentMode.HTML));
		}
		/*TODO:
		 * make just about everything editable
		 */
		
	}

}
